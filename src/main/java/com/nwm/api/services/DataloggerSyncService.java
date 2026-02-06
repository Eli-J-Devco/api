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
import com.nwm.api.entities.ModelElkorWattsonPVMeterEntity;
import com.nwm.api.entities.SiteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DataloggerSyncService extends DB {

    @Autowired
    private ModelChintSolectriaInverterClass9725Service modelChintSolectriaInverterClass9725Service;

    @Autowired
    private ModelElkorWattsonPVMeterService modelElkorWattsonPVMeterService;

    private final int INSERT_THREAD = 50;

    private final ExecutorService executor = Executors.newFixedThreadPool(INSERT_THREAD);

    /**
     * @desciption get table name from Postgres DB
     * @author Minh Le
     * @date 26-01-2026
     * @return List
     */
    private List<String> getPostgresTableName() {
        List<SiteEntity> siteList;
        List<String> talbeNameList = new ArrayList<>();

        try {
            siteList = this.queryForList("Site.getSiteExistPostgresDb");
        } catch (SQLException e) {
            log.error("Query for postgres name fail!", e);
            throw new RuntimeException(e);
        }

        if (siteList != null && !siteList.isEmpty()) {
            talbeNameList = siteList.stream().map(s -> s.getPostgres_table()).collect(Collectors.toList());
            return talbeNameList;
        }
        return talbeNameList;
    }

    /**
     * @desciption get data from Postgres DB
     * @author Minh Le
     * @date 15-01-2026
     * @return List<Map>
     */
    private List<Map<String, Object>> getDataLogger(String databaseName) {
        try {
            return this.queryForList_Db_Datalogger("Datalogger.getDataList", databaseName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @desciption insert data for site 1000000094a21cc
     * @author Minh Le
     * @date 15-01-2026
     * @return List<Map>
     */
    private void insertData_site_1000000094a21ccb(String deviceTableGroup, Map<String, DeviceEntity> deviceByModbusMap, String modbusdevicenumber, String telemetryData) {
        switch (deviceTableGroup) {
            case "model_chint_solectria_inverter_class9725":
                ModelChintSolectriaInverterClass9725Entity modelChintSolectriaInverterClass9725Entity = modelChintSolectriaInverterClass9725Service.setModelChintSolectriaInverterClass9725(telemetryData);

                modelChintSolectriaInverterClass9725Entity.setId_device(deviceByModbusMap.get(modbusdevicenumber).getId());
                modelChintSolectriaInverterClass9725Entity.setDatatablename(deviceByModbusMap.get(modbusdevicenumber).getDatatablename());
                modelChintSolectriaInverterClass9725Entity.setView_tablename(deviceByModbusMap.get(modbusdevicenumber).getView_tablename());
                modelChintSolectriaInverterClass9725Entity.setJob_tablename(deviceByModbusMap.get(modbusdevicenumber).getJob_tablename());

                modelChintSolectriaInverterClass9725Service.insertModelChintSolectriaInverterClass9725(modelChintSolectriaInverterClass9725Entity);
                break;

            case "model_elkor_wattson_pv_meter":
                ModelElkorWattsonPVMeterEntity modelElkorWattsonPVMeterEntity = modelElkorWattsonPVMeterService.setModelElkorWattsonPVMeter(telemetryData);

                modelElkorWattsonPVMeterEntity.setId_device(deviceByModbusMap.get(modbusdevicenumber).getId());
                modelElkorWattsonPVMeterEntity.setDatatablename(deviceByModbusMap.get(modbusdevicenumber).getDatatablename());
                modelElkorWattsonPVMeterEntity.setView_tablename(deviceByModbusMap.get(modbusdevicenumber).getView_tablename());
                modelElkorWattsonPVMeterEntity.setJob_tablename(deviceByModbusMap.get(modbusdevicenumber).getJob_tablename());

                modelElkorWattsonPVMeterService.insertModelElkorWattsonPVMeter(modelElkorWattsonPVMeterEntity);
                break;

            default:
                break;
        }
    }

    /**
     *@desciption handle dataname list and call main handler
     * @author Minh Le
     * @date 26-01-2026
     * @return void
     */
    public void syncData() {
        List<String> dataTableNameList = getPostgresTableName();
        if(!dataTableNameList.isEmpty()) {
            for(String dataTableName : dataTableNameList) {
                handleData(dataTableName);
            }
        }
    }

    /**
     *@desciption handle data insert to mySQL
     * @author Minh Le
     * @date 15-01-2026
     * @return List
     */
    public void handleData(String tableName) {
        List<Map<String, Object>> dataList = getDataLogger(tableName);

        ObjectMapper mapper = new ObjectMapper();

        Phaser phaser = new Phaser(1);

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

                        if (deviceByModbusMap.containsKey(modbusdevicenumber)) {
                            String deviceTableGroup = deviceByModbusMap.get(modbusdevicenumber).getDevice_group_table();

                            phaser.register();

                            executor.execute(() -> {
                                try {
                                    switch(tableName) {
                                        case "data654_1000000094a21ccb":
                                            insertData_site_1000000094a21ccb(deviceTableGroup, deviceByModbusMap, modbusdevicenumber, telemetryData);
                                        break;
                                    }
                                } catch (Exception e) {
                                    log.error("Insert to Db failed !", e);
                                } finally {
                                    phaser.arriveAndDeregister();
                                }
                            });
                        }
                    }

                    phaser.arriveAndAwaitAdvance();

                    Map<String, Object> deleteParams = new HashMap<>();
                    deleteParams.put("databaseName", tableName);
                    deleteParams.put("logId", logId);
                    int deletedRows = this.delete_Db_Datalogger("Datalogger.deleteData", deleteParams);

                    log.info("Deleted data from: " + tableName + ", Affect rows: " +  deletedRows + ", Id: " + dataLogElement.get("id"));
                }
            }
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
}
