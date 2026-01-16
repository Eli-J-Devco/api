/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelChintSolectriaInverterClass9725Entity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataloggerSynchronizeService extends DB {
    /**
     * @desciption get data from Postgres DB data654_1000000094a21ccb
     * @author Minh Le
     * @date 15-01-2026
     * @param databaseName
     * @return List
     */
    public List<Map<String, Object>> getDataLogger_ModelChintSolectriaInverterClass9725(String databaseName) {
        try {
            return this.queryForList_Db_Datalogger("Datalogger.getList_data654_1000000094a21ccb", databaseName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *@desciption handle data insert to mySQL
     * @author Minh Le
     * @date 15-01-2026
     * @param databaseName
     * @return List
     */
    public void handleData_ModelChintSolectriaInverterClass9725(String databaseName) {
        List<Map<String, Object>> dataList = getDataLogger_ModelChintSolectriaInverterClass9725(databaseName);

        ModelChintSolectriaInverterClass9725Service service = new ModelChintSolectriaInverterClass9725Service();

        ObjectMapper mapper = new ObjectMapper();

        try {
            if (!dataList.isEmpty()) {
                String serialNumber = (String) dataList.get(0).get("serialnumber");

                DeviceEntity deviceParams = new DeviceEntity();
                deviceParams.setSerial_number(serialNumber);

                List<DeviceEntity> deviceList = this.queryForList("Device.getListBySerialNumber", deviceParams);

                Map<String, DeviceEntity> deviceByModbusMap = deviceList.stream()
                    .collect(Collectors.toMap(
                        DeviceEntity::getModbusdevicenumber,
                        Function.identity()
                    ));

                for (Map<String, Object> dataLogElement : dataList) {
                    String logId = (String) dataLogElement.get("id");
                    String telemetry = (String) dataLogElement.get("telemetry");

                    Map<String, Object> dataLogMap = (Map<String, Object>) mapper.readValue(telemetry, Map.class);
                    Map<String, Object> dataMap = (Map<String, Object>) dataLogMap.get("data");

                    for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                        String modbusdevicenumber = entry.getKey();
                        String telemetryData = entry.getValue().toString()
                                .replace("[","")
                                .replace("]","")
                                .replaceAll(",\\s*", ",")
                                .trim();

                        ModelChintSolectriaInverterClass9725Entity entity = service.setModelChintSolectriaInverterClass9725(telemetryData);

                        if (deviceByModbusMap.containsKey(modbusdevicenumber)) {
                            entity.setId_device(deviceByModbusMap.get(modbusdevicenumber).getId());
                            entity.setDatatablename(deviceByModbusMap.get(modbusdevicenumber).getDatatablename());
                            entity.setView_tablename(deviceByModbusMap.get(modbusdevicenumber).getView_tablename());
                            entity.setJob_tablename(deviceByModbusMap.get(modbusdevicenumber).getJob_tablename());
                        }

                        service.insertModelChintSolectriaInverterClass9725(entity);
                    }

                    Map<String, Object> deleteParams = new HashMap<>();
                    deleteParams.put("databaseName", databaseName);
                    deleteParams.put("logId", logId);
                    int deletedRows = this.delete_Db_Datalogger("Datalogger.deleteData", deleteParams);

                    log.info("Deleted data from: " + databaseName + ", Affect rows: " +  deletedRows + ", Data: " + dataLogElement);
                }
            }
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
}
