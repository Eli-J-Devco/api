/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelDataloggerEntity;
import com.nwm.api.entities.ModelSatconPowergate225InverterEntity;

@Service
public class MQTTDataloggerService extends DB {
    
    private static final String DATE_FORMAT = "yyyy-MM-dd,HH:mm:ss";
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * @description Process MQTT Datalogger message and save to database
     * @author Duc.Pham
     * @since 2025-09-30
     */
    public void processMQTTDataloggerMessage(String topic, String payload) {
        try {
            log.info("Processing MQTT Datalogger message - Topic: " + topic);
            
            // Parse JSON payload
            @SuppressWarnings("unchecked")
            Map<String, Object> messageData = objectMapper.readValue(payload, Map.class);
            
            String logFile = (String) messageData.get("LOGFILE");
            String serialNumber = (String) messageData.get("SERIALNUMBER");
            String modbusPort = (String) messageData.get("MODBUSPORT");
            String modbusDevice = (String) messageData.get("MODBUSDEVICE");
            String deviceName = (String) messageData.get("DEVICENAME");
            
            @SuppressWarnings("unchecked")
            List<String> dataArray = (List<String>) messageData.get("DATA");
            
            if (dataArray == null || dataArray.isEmpty()) {
                log.warn("No data array found in MQTT message");
                return;
            }
            
            // Get device information by serial number
            DeviceEntity device = getDeviceBySerialNumber(serialNumber, modbusDevice);
            if (device == null) {
                log.warn("Device not found for serial number: " + serialNumber + ", modbus device: " + modbusDevice);
                return;
            }
            
            log.info("Processing device id=" + device.getId() + " device_table='" + device.getDatatablename() + "'");
            
            // Process each data row
            for (String dataRow : dataArray) {
                processDataRow(device, dataRow, logFile, serialNumber, modbusPort, modbusDevice, deviceName);
            }
            
        } catch (Exception ex) {
            log.error("Error processing MQTT Datalogger message", ex);
        }
    }
    
    /**
     * @description Process individual data row
     */
    private void processDataRow(DeviceEntity device, String dataRow, String logFile, 
                               String serialNumber, String modbusPort, String modbusDevice, String deviceName) {
        try {
            String[] dataValues = dataRow.split(",");
            if (dataValues.length < 2) {
                log.warn("Invalid data row format: " + dataRow);
                return;
            }
            
            // Parse timestamp (first two elements: date and time)
            String dateTimeStr = dataValues[0] + "," + dataValues[1];
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Date timestamp = sdf.parse(dateTimeStr);
            
            // Determine device type and process accordingly
            if (deviceName.contains("Satcon_Powergate225")) {
                processSatconPowergate225Data(device, dataValues, timestamp);
            } else {
                log.warn("Unknown device type: " + deviceName);
            }
            
            // Save datalogger metadata
            saveDataloggerMetadata(device, timestamp, logFile, serialNumber, modbusPort, modbusDevice);
            
        } catch (Exception ex) {
            log.error("Error processing data row: " + dataRow, ex);
        }
    }
    
    /**
     * @description Process Satcon Powergate 225 inverter data
     */
    private void processSatconPowergate225Data(DeviceEntity device, String[] dataValues, Date timestamp) {
        try {
            if (dataValues.length < 30) {
                log.warn("Insufficient data values for Satcon Powergate225. Expected >= 30, got: " + dataValues.length);
                return;
            }
            
            ModelSatconPowergate225InverterEntity entity = new ModelSatconPowergate225InverterEntity();
            // Convert timestamp to string format expected by ModelBaseEntity
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            entity.setTime(timeFormat.format(timestamp));
            entity.setId_device(device.getId());
            
            // Map data values to entity fields based on the log structure and actual entity fields
            // Data structure from log: ["2025-09-30,09:48:00","1","0","0","0","0","0","0","59648","164","49230","1","0.000","0.000","0.000","0.000","-0.055","-0.055","0.000","276.214","275.605","278","0.992","0.992","0.066","0.492","6","156","15475671","-0.999","59.919"]
            
            if (!mapDataToSatconEntity(entity, dataValues)) {
                return;
            }
            
            // Insert into device specific table
            String targetTable = "data" + device.getId() + "_model_satcon_powergate_225_inverter";
            log.info("Inserting into table='" + targetTable + "' for device id=" + device.getId());
            
            // Scale device parameters if needed
            List<DeviceEntity> scaledDeviceParameters = getScaledDeviceParameters(device.getId());
            if (scaledDeviceParameters != null && !scaledDeviceParameters.isEmpty()) {
                UploadFilesService uploadService = new UploadFilesService();
                uploadService.scalingDeviceParameters(scaledDeviceParameters, entity);
            }
            
            // Insert data
            insert("ModelSatconPowergate225Inverter.insertModelSatconPowergate225Inverter", entity);
            
            // Check for wrong energy alerts
            UploadFilesService uploadService = new UploadFilesService();
            uploadService.checkWrongEnergy(device, entity);
            
        } catch (Exception ex) {
            log.error("Error processing Satcon Powergate225 data", ex);
        }
    }
    
    /**
     * @description Save datalogger metadata
     */
    private void saveDataloggerMetadata(DeviceEntity device, Date timestamp, String logFile, 
                                       String serialNumber, String modbusPort, String modbusDevice) {
        try {
            ModelDataloggerEntity dataloggerEntity = new ModelDataloggerEntity();
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dataloggerEntity.setTime(timeFormat.format(timestamp));
            dataloggerEntity.setId_device(device.getId());
            dataloggerEntity.setModbusdevice(modbusDevice);
            dataloggerEntity.setModbusport(modbusPort);
            dataloggerEntity.setSerialnumber(serialNumber);
            dataloggerEntity.setLoopname(logFile);
            
            // Get datalogger device ID (assumed to be parent device or specific datalogger device)
            Integer dataloggerDeviceId = getDataloggerDeviceId(device.getId_device_group());
            if (dataloggerDeviceId != null) {
                String dataloggerTable = "data" + dataloggerDeviceId + "_model_datalogger";
                log.info("Inserting datalogger metadata into table='" + dataloggerTable + "' for device id=" + device.getId());
                insert("Datalogger.insertDatalogger", dataloggerEntity);
            }
            
        } catch (Exception ex) {
            log.error("Error saving datalogger metadata", ex);
        }
    }
    
    /**
     * @description Get device by serial number and modbus device
     */
    private DeviceEntity getDeviceBySerialNumber(String serialNumber, String modbusDevice) {
        try {
            DeviceEntity searchDevice = new DeviceEntity();
            searchDevice.setSerialnumber(serialNumber);
            searchDevice.setModbusdevicenumber(modbusDevice);
            
            return (DeviceEntity) queryForObject("Device.getDeviceBySerialAndModbus", searchDevice);
        } catch (Exception ex) {
            log.error("Error getting device by serial number", ex);
            return null;
        }
    }
    
    /**
     * @description Get scaled device parameters
     */
    @SuppressWarnings("unchecked")
    private List<DeviceEntity> getScaledDeviceParameters(Integer deviceId) {
        try {
            return queryForList("Device.getParameterScale", deviceId);
        } catch (Exception ex) {
            log.error("Error getting scaled device parameters", ex);
            return new ArrayList<>();
        }
    }
    
    /**
     * @description Get datalogger device ID for device group
     */
    private Integer getDataloggerDeviceId(Integer deviceGroupId) {
        try {
            return (Integer) queryForObject("Device.getDataloggerDeviceId", deviceGroupId);
        } catch (Exception ex) {
            log.error("Error getting datalogger device ID", ex);
            return null;
        }
    }
    
    /**
     * @description Map data values to Satcon entity
     */
    private boolean mapDataToSatconEntity(ModelSatconPowergate225InverterEntity entity, String[] dataValues) {
        try {
            // Skip index 0,1 (datetime already processed)
            entity.setFault1(parseDouble(dataValues[2]));
            entity.setFault2(parseDouble(dataValues[3]));
            entity.setFault3(parseDouble(dataValues[4]));
            entity.setFault4(parseDouble(dataValues[5]));
            entity.setGridStatus(parseDouble(dataValues[6]));
            entity.setStatus6(parseDouble(dataValues[7]));
            entity.setStatus7(parseDouble(dataValues[8]));
            entity.setPCSState(parseDouble(dataValues[9]));
            entity.setDCInputPower(parseDouble(dataValues[10]));
            entity.setDC_Link_Volts(parseDouble(dataValues[11]));
            entity.setDCInputVoltage(parseDouble(dataValues[12]));
            entity.setDCInputCurrent(parseDouble(dataValues[13]));
            entity.setOutputKVAR(parseDouble(dataValues[14]));
            entity.setOutputKW(parseDouble(dataValues[15]));
            entity.setOutputKVA(parseDouble(dataValues[16]));
            entity.setLine_Volts_A_TEST(parseDouble(dataValues[17]));
            entity.setLine_Volts_B_TEST(parseDouble(dataValues[18]));
            entity.setLine_Volts_C_TEST(parseDouble(dataValues[19]));
            entity.setLine_Amps_A_TEST(parseDouble(dataValues[20]));
            entity.setLine_Amps_B_TEST(parseDouble(dataValues[21]));
            entity.setLine_Amps_C_TEST(parseDouble(dataValues[22]));
            entity.setNeutralCurrent(parseDouble(dataValues[23]));
            entity.setStopCode(parseDouble(dataValues[24]));
            entity.setKWHlow(parseDouble(dataValues[25]));
            entity.setKWH(parseDouble(dataValues[26]));
            entity.setPowerFactor(parseDouble(dataValues[27]));
            entity.setLineFreq(parseDouble(dataValues[28]));
            entity.setOutputPowerLimit(parseDouble(dataValues[29]));
            
            // Set measured production (AC Power)
            entity.setMeasuredProduction(entity.getOutputKW());
            
            // Set NVM values
            entity.setNvmActivePower(entity.getOutputKW());
            entity.setNvmActiveEnergy(entity.getKWH());
            
            return true;
        } catch (Exception ex) {
            log.error("Error mapping data values to entity", ex);
            return false;
        }
    }
    
    /**
     * @description Safe parse double value
     */
    private Double parseDouble(String value) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return 0.0;
            }
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException ex) {
            log.warn("Error parsing double value: " + value + ", using default 0.0");
            return 0.0;
        }
    }
}
