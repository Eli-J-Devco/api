/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/

package com.nwm.api.services;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelBaseEntity;
import com.nwm.api.entities.ModelSatconPowergate225InverterEntity;

public class MQTTJsonService extends DB {
	
	/**
	 * @description Process MQTT JSON data và lưu vào database - simplified business logic only
	 * @author Duc.Pham
	 * @since 2025-01-07
	 * @param jsonData - JSON string từ MQTT (already validated by controller)
	 * @return boolean - true nếu thành công, false nếu thất bại
	 */
	public boolean processMQTTJsonData(String jsonData) {
		try {
			// Parse JSON data (validation done at controller level)
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> mqttData = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
			
			// Extract device information
			String serialNumber = (String) mqttData.get("SERIALNUMBER");
			String modbusPort = (String) mqttData.get("MODBUSPORT");
			
			// Business logic: Find device in database
			DeviceEntity deviceInfo = findDeviceBySerialAndModbus(serialNumber, modbusPort);
			if (deviceInfo == null) {
				log.error("Device not found - Serial: " + serialNumber + ", Modbus: " + modbusPort);
				return false;
			}
			
			// Business logic: Process data based on device type
			return processDeviceDataByTableName(mqttData, deviceInfo);
			
		} catch (Exception ex) {
			log.error("Error processing MQTT JSON data: " + ex.getMessage());
			return false;
		}
	}
	
	
	/**
	 * @description Find device by serial number and modbus port using existing mapper
	 * @author Duc.Pham
	 * @since 2025-01-06
	 * @param serialNumber
	 * @param modbusPort
	 * @return DeviceEntity
	 */
	private DeviceEntity findDeviceBySerialAndModbus(String serialNumber, String modbusPort) {
		try {
			// Tái sử dụng existing query pattern
			@SuppressWarnings("unchecked")
			List<DeviceEntity> devicesBySerial = queryForList("Device.getListBySerialNumber", serialNumber);
			
			// Sử dụng stream API để tối ưu hóa performance và code clarity
			return devicesBySerial.stream()
				.filter(device -> modbusPort.equals(device.getModbusdevicenumber()))
				.findFirst()
				.orElse(null);
				
		} catch (Exception ex) {
			log.error("Error finding device by serial: " + serialNumber + ", modbus: " + modbusPort + " - " + ex.getMessage());
			return null;
		}
	}
	
	/**
	 * @description Process device data - tái sử dụng existing business logic
	 * @author Duc.Pham  
	 * @since 2025-01-07
	 */
	private boolean processDeviceDataByTableName(Map<String, Object> mqttData, DeviceEntity deviceInfo) {
		try {
			@SuppressWarnings("unchecked")
			List<String> dataArray = (List<String>) mqttData.get("DATA");
			
			// Convert MQTT format to CSV và process data by model type
			String csvData = convertMQTTDataToCSV(dataArray);
			log.info("Processing " + dataArray.size() + " data points for device: " + deviceInfo.getId());
			
			// Tái sử dụng existing pattern từ UploadFilesController
			String modelType = deviceInfo.getDevice_group_table();
			if ("model_satcon_powergate_225_inverter".equals(modelType)) {
				ModelSatconPowergate225InverterService service = new ModelSatconPowergate225InverterService();
				ModelSatconPowergate225InverterEntity dataEntity = service.setModelSatconPowergate225Inverter(csvData);
				
				if (dataEntity == null) {
					log.error("Failed to parse CSV data for device: " + deviceInfo.getId());
					return false;
				}
				
				setCommonDeviceMetadata(dataEntity, deviceInfo);
				return service.insertModelSatconPowergate225Inverter(dataEntity);
			}
			
			log.warn("Unsupported model type: " + modelType);
			return false;
			
		} catch (Exception ex) {
			log.error("Error processing device " + deviceInfo.getId() + " data: " + ex.getMessage(), ex);
			return false;
		}
	}
	
	/**
	 * @description Convert MQTT DATA array to CSV format - simple format fix for existing service
	 * @author Duc.Pham
	 * @since 2025-01-07
	 * @param dataArray - MQTT DATA array from JSON
	 * @return String - CSV format for setModelSatconPowergate225Inverter service
	 */
	private String convertMQTTDataToCSV(List<String> dataArray) {
		if (dataArray == null || dataArray.isEmpty()) {
			return "";
		}
		
		// Fix datetime format "2025-10-07,02:48:00" -> "2025-10-07 02:48:00"  
		String firstField = dataArray.get(0);
		if (firstField.contains(",")) {
			dataArray.set(0, firstField.replace(",", " "));
		}
		
		// Add dummy OutputPowerLimit field nếu missing (31->32 fields)
		if (dataArray.size() == 31) {
			dataArray.add("0.001"); 
		}
		
		return String.join(",", dataArray);
	}
	
	/**
	 * @description Set device metadata - tái sử dụng exact pattern từ UploadFilesController lines 1840-1843
	 * @author Duc.Pham
	 * @since 2025-01-06
	 */
	private void setCommonDeviceMetadata(ModelBaseEntity entity, DeviceEntity deviceInfo) {
		// Tái sử dụng existing pattern từ UploadFilesController cho device metadata
		entity.setId_device(deviceInfo.getId());
		entity.setDatatablename(deviceInfo.getDatatablename());
		entity.setView_tablename(deviceInfo.getView_tablename());
		entity.setEnable_alert(deviceInfo.getEnable_alert());
	}

}
