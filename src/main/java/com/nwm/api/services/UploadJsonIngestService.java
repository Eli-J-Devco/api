package com.nwm.api.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.controllers.UploadJsonDataController.UploadJsonRequest;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelDataloggerEntity;
import com.nwm.api.entities.ModelSatconPowergate225InverterEntity;
import com.nwm.api.utils.Lib;

@Service
public class UploadJsonIngestService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UploadJsonIngestService.class);

    @Autowired(required = false)
    private javax.sql.DataSource dataSource;

    public String ingest(UploadJsonRequest body) {
        String message = "";

        if (body == null || Lib.isBlank(body.mode) || Lib.isBlank(body.serialnumber)) {
            logger.warn("Ingest rejected: missing required fields. mode={}, serialnumber present? {}", body != null ? body.mode : null, body != null && !Lib.isBlank(body.serialnumber));
            return "\nFAILURE\n";
        }

        // Normalize input lines: accept LINES (CSV strings) or DATA (array of values)
        List<String> normalizedLines = new ArrayList<>();
        if (body.lines != null && !body.lines.isEmpty()) {
            normalizedLines.addAll(body.lines);
        } else if (body.data != null && !body.data.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < body.data.size(); i++) {
                if (i > 0) sb.append(',');
                sb.append(body.data.get(i));
            }
            normalizedLines.add(sb.toString());
        }
        if (normalizedLines.isEmpty()) {
            logger.warn("Ingest rejected: no lines to process (LINES/DATA empty). serialnumber={} modbusdevice={}", body.serialnumber, body.modbusdevice);
            return "\nFAILURE\n";
        }

        final String LOGFILEUPLOAD = "LOGFILEUPLOAD";
        if (!LOGFILEUPLOAD.equalsIgnoreCase(body.mode)) {
            logger.warn("Ingest rejected: unsupported MODE='{}'. Expected '{}'.", body.mode, LOGFILEUPLOAD);
            return "\nFAILURE\n";
        }

        try {
            // Log current JDBC URL to verify target DB (MySQL vs H2)
            try {
                if (dataSource != null) {
                    try (java.sql.Connection c = dataSource.getConnection()) {
                        String jdbcUrl = c.getMetaData().getURL();
                        String user = c.getMetaData().getUserName();
                        logger.info("Ingest using DataSource url='{}' user='{}'", jdbcUrl, user);
                    }
                } else {
                    logger.warn("DataSource not available to log JDBC URL");
                }
            } catch (Exception ex) {
                logger.warn("Failed to read JDBC URL: {}", ex.getMessage());
            }

            DeviceService deviceService = new DeviceService();
            DeviceEntity query = new DeviceEntity();
            query.setSerial_number(body.serialnumber);
            @SuppressWarnings("unchecked")
            List<DeviceEntity> devices = deviceService.getDeviceListBySerialNumber(query);

            if (devices == null || devices.isEmpty()) {
                logger.info("No devices found for serialnumber={}. Acknowledge only.", body.serialnumber);
                return "\nSUCCESS\n";
            }

            String lastTimestamp = null;
            List<String> preparedLines = new ArrayList<>();
            logger.info("Preparing lines for serialnumber={} modbusdevice={} overrides: datatable='{}' datalogger_table='{}' linesCount={}",
                    body.serialnumber, body.modbusdevice, body.datatable, body.dataloggerTable, normalizedLines.size());
            for (String line : normalizedLines) {
                if (Lib.isBlank(line)) continue;
                List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
                String canonical;
                if (words.size() > 1 && words.get(1).contains(":")) {
                    String datePart = words.get(0).replace("'", "").trim();
                    String timePart = words.get(1).replace("'", "").trim();
                    String ts = "'" + datePart + " " + timePart + "'";
                    lastTimestamp = datePart + " " + timePart;
                    StringBuilder sb = new StringBuilder();
                    sb.append(ts);
                    for (int i = 2; i < words.size(); i++) {
                        sb.append(',').append(words.get(i));
                    }
                    canonical = sb.toString();
                } else {
                    canonical = line;
                    String ts0 = words.get(0).replace("'", "").trim();
                    lastTimestamp = ts0;
                }
                preparedLines.add(canonical);
            }

            for (DeviceEntity device : devices) {
                if (!Lib.isBlank(body.modbusdevice) && device.getModbusdevicenumber() != null && !body.modbusdevice.equals(device.getModbusdevicenumber())) {
                    logger.debug("Skip device id={} modbusdevicenumber='{}' (payload '{}')", device.getId(), device.getModbusdevicenumber(), body.modbusdevice);
                    continue;
                }

                DeviceEntity toUpdate = new DeviceEntity();
                toUpdate.setId(device.getId());
                toUpdate.setLast_updated(lastTimestamp);
                toUpdate.setLast_value(null);
                toUpdate.setField_value1(null);
                toUpdate.setField_value2(null);
                toUpdate.setField_value3(null);
                deviceService.updateLastUpdated(toUpdate);

                String deviceGroupTable = device.getDevice_group_table();
                if (!Lib.isBlank(deviceGroupTable)) {
                    logger.info("Processing device id={} device_group_table='{}' targetDatatable='{}'", device.getId(), deviceGroupTable,
                            !Lib.isBlank(body.datatable) ? body.datatable : device.getDatatablename());
                    switch (deviceGroupTable) {
                        case "model_satcon_powergate_225_inverter": {
                            ModelSatconPowergate225InverterService modelService = new ModelSatconPowergate225InverterService();
                            @SuppressWarnings("unchecked")
                            List<DeviceEntity> scaledDeviceParameters = deviceService.getListScaledDeviceParameter(device);
                            for (String line : preparedLines) {
                                if (Lib.isBlank(line)) continue;
                                List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
                                if (words.size() == 0) continue;
                                logger.debug("Parsing line for device id={}: {}", device.getId(), line);
                                ModelSatconPowergate225InverterEntity dataEntity = modelService.setModelSatconPowergate225Inverter(line);
                                dataEntity.setId_device(device.getId());
                                // Allow override via payload DATATABLE; otherwise use device config
                                dataEntity.setDatatablename(!Lib.isBlank(body.datatable) ? body.datatable : device.getDatatablename());
                                dataEntity.setView_tablename(device.getView_tablename());
                                dataEntity.setJob_tablename(device.getJob_tablename());

                                UploadFilesService uploadFilesService = new UploadFilesService();
                                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

                                DeviceEntity deviceUpdateE = new DeviceEntity();
                                if (dataEntity.getLineFreq() != 0.001 && dataEntity.getLineFreq() >= 0) {
                                    deviceUpdateE.setLast_updated(dataEntity.getTime());
                                }
                                deviceUpdateE.setLast_value(dataEntity.getOutputKW() != 0.001 ? dataEntity.getOutputKW() : null);
                                deviceUpdateE.setField_value1(dataEntity.getOutputKW() != 0.001 ? dataEntity.getOutputKW() : null);
                                deviceUpdateE.setField_value2(dataEntity.getLineFreq() != 0.001 ? dataEntity.getLineFreq() : null);
                                deviceUpdateE.setField_value3(dataEntity.getDCInputVoltage() != 0.001 ? dataEntity.getDCInputVoltage() : null);
                                deviceUpdateE.setId(device.getId());
                                deviceService.updateLastUpdated(deviceUpdateE);

                                try {
                                    logger.info("Inserting into table='{}' for device id={}", dataEntity.getDatatablename(), device.getId());
                                    modelService.insertModelSatconPowergate225Inverter(dataEntity);
                                } catch (Exception ex) {
                                    logger.error("Insert failed table='{}' device id={} error={}", dataEntity.getDatatablename(), device.getId(), ex.getMessage(), ex);
                                    throw ex;
                                }
                                try {
                                    uploadFilesService.checkWrongEnergy(device, dataEntity);
                                } catch (Exception ex) {
                                    logger.warn("checkWrongEnergy warning device id={} msg={}", device.getId(), ex.getMessage());
                                }
                            }
                            break;
                        }
                        default:
                            logger.info("Unsupported device_group_table for JSON path: {} (device id={})", deviceGroupTable, device.getId());
                            break;
                    }
                }

                ModelDataloggerService dataloggerService = new ModelDataloggerService();
                ModelDataloggerEntity datalogger = new ModelDataloggerEntity();
                datalogger.setId_device(device.getId());
                // Allow override via payload DATALOGGER_TABLE
                datalogger.setDatatablename(!Lib.isBlank(body.dataloggerTable) ? body.dataloggerTable : device.getDatalogger_table());
                datalogger.setView_tablename(device.getView_tablename());
                datalogger.setJob_tablename(device.getJob_tablename());
                Date now = new Date();
                TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
                SimpleDateFormat formatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                TimeZone tzUTC = TimeZone.getTimeZone("UTC");
                formatUTC.setTimeZone(tzUTC);
                String sDateUTC = formatUTC.format(now);
                datalogger.setTime(sDateUTC);
                datalogger.setSerialnumber(body.serialnumber);
                datalogger.setLoopname(body.loopname);
                datalogger.setModbusip(body.modbusip);
                datalogger.setModbusport(body.modbusport);
                datalogger.setModbusdevice(body.modbusdevice);
                datalogger.setModbusdevicename(body.modbusdevicename);
                datalogger.setModbusdevicetype(body.modbusdevicetype);
                datalogger.setModbusdevicetypenumber(body.modbusdevicetypenumber);
                datalogger.setModbusdeviceclass(body.modbusdeviceclass);
                try {
                    logger.info("Inserting datalogger metadata into table='{}' for device id={}", datalogger.getDatatablename(), device.getId());
                    dataloggerService.insertModelDatalogger(datalogger);
                } catch (Exception ex) {
                    logger.warn("Insert datalogger metadata failed table='{}' device id={} msg={}", datalogger.getDatatablename(), device.getId(), ex.getMessage());
                }
            }

            message = "\nSUCCESS\n";
            return message;
        } catch (Exception ex) {
            logger.error("Ingest failed for serialnumber={} modbusdevice={} error={}", body.serialnumber, body.modbusdevice, ex.getMessage(), ex);
            message = "\nFAILURE\n";
            return message;
        }
    }
}


