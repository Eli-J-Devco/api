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
import com.nwm.api.entities.ModelIDECPLCEntity;
import com.nwm.api.entities.ModelInaccessPPCEntity;
import com.nwm.api.entities.ModelProtectionRelayEntity;
import com.nwm.api.entities.ModelProtectionRelayV1Entity;
import com.nwm.api.entities.ModelSMP4DPEntity;
import com.nwm.api.entities.ModelSMP4DPV1Entity;
import com.nwm.api.entities.ModelSungrowPv24hScbEntity;
import com.nwm.api.entities.ModelSungrowSh6250hvMvEntity;
import com.nwm.api.entities.ModelWKippZonenRT1Entity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Constants;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DataloggerSyncService extends DB {

    @Autowired
    private ModelChintSolectriaInverterClass9725Service modelChintSolectriaInverterClass9725Service;

    @Autowired
    private ModelElkorWattsonPVMeterService modelElkorWattsonPVMeterService;
    
    @Autowired
    private ModelSungrowSh6250hvMvService modelSungrowSh6250hvMvService;
    
    @Autowired
    private ModelSungrowPv24hScbService modelSungrowPv24hScbService;
    
    
    @Autowired
    private ModelProtectionRelayService modelProtectionRelayService;
    
    @Autowired
    private ModelSMP4DPService modelSMP4DPService;
    
    @Autowired
    private ModelIDECPLCService modelIDECPLCService;
    
    @Autowired
    private ModelInaccessPPCService modelInaccessPPCService;
    
    
    @Autowired
    private ModelWKippZonenRT1Service modelWKippZonenRT1Service;

    private final DeviceService deviceService = new DeviceService();

    @Autowired
    private UploadFilesService uploadFilesService;
    
    @Autowired
    private ModelProtectionRelayV1Service modelProtectionRelayV1Service;
    
    @Autowired
    private ModelSMP4DPV1Service modelSMP4DPV1Service;


    private final int INSERT_THREAD = 100;
    private final int DATA_GET_LIMIT = 200;

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
    private List<Map<String, Object>> getDataLogger(String databaseName, boolean isFirstRun) {
        try {

            Map<String, Object> params = new HashMap<>();
            params.put("databaseName", databaseName);
            params.put("isFirstRun", isFirstRun);
            params.put("limit", DATA_GET_LIMIT);

            return this.queryForList_Db_Datalogger("Datalogger.getDataList", params);
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
    private boolean insertData(String deviceTableGroup, Map<String, DeviceEntity> deviceByModbusMap, String modbusdevicenumber, String telemetryData) {
        List<DeviceEntity> scaledDeviceParameters = deviceService.getListScaledDeviceParameter(deviceByModbusMap.get(modbusdevicenumber));

        switch (deviceTableGroup) {
            case "model_chint_solectria_inverter_class9725":
                ModelChintSolectriaInverterClass9725Entity modelChintSolectriaInverterClass9725Entity = modelChintSolectriaInverterClass9725Service.setModelChintSolectriaInverterClass9725(telemetryData);
                DeviceEntity deviceModelChintSolectriaInverterClass9725Entity = deviceByModbusMap.get(modbusdevicenumber);

                modelChintSolectriaInverterClass9725Entity.setId_device(deviceModelChintSolectriaInverterClass9725Entity.getId());
                modelChintSolectriaInverterClass9725Entity.setDatatablename(deviceModelChintSolectriaInverterClass9725Entity.getDatatablename());
                modelChintSolectriaInverterClass9725Entity.setView_tablename(deviceModelChintSolectriaInverterClass9725Entity.getView_tablename());
                modelChintSolectriaInverterClass9725Entity.setJob_tablename(deviceModelChintSolectriaInverterClass9725Entity.getJob_tablename());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelChintSolectriaInverterClass9725Entity);

                deviceModelChintSolectriaInverterClass9725Entity.setLast_value(modelChintSolectriaInverterClass9725Entity.getAC_ActivePower() != 0.001 ? modelChintSolectriaInverterClass9725Entity.getAC_ActivePower() : null);
                deviceModelChintSolectriaInverterClass9725Entity.setField_value1(modelChintSolectriaInverterClass9725Entity.getAC_ActivePower() != 0.001 ? modelChintSolectriaInverterClass9725Entity.getAC_ActivePower() : null);

                uploadFilesService.handleEnergyField(deviceModelChintSolectriaInverterClass9725Entity, modelChintSolectriaInverterClass9725Entity, "total_yield");

                deviceLastUpdated(deviceModelChintSolectriaInverterClass9725Entity);

                return modelChintSolectriaInverterClass9725Service.insertModelChintSolectriaInverterClass9725(modelChintSolectriaInverterClass9725Entity);

            case "model_elkor_wattson_pv_meter":
                ModelElkorWattsonPVMeterEntity modelElkorWattsonPVMeterEntity = modelElkorWattsonPVMeterService.setModelElkorWattsonPVMeter(telemetryData);
                DeviceEntity deviceModelElkorWattsonPVMeterEntity = deviceByModbusMap.get(modbusdevicenumber);

                modelElkorWattsonPVMeterEntity.setId_device(deviceModelElkorWattsonPVMeterEntity.getId());
                modelElkorWattsonPVMeterEntity.setDatatablename(deviceModelElkorWattsonPVMeterEntity.getDatatablename());
                modelElkorWattsonPVMeterEntity.setView_tablename(deviceModelElkorWattsonPVMeterEntity.getView_tablename());
                modelElkorWattsonPVMeterEntity.setJob_tablename(deviceModelElkorWattsonPVMeterEntity.getJob_tablename());
                

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelElkorWattsonPVMeterEntity);

                deviceModelElkorWattsonPVMeterEntity.setLast_value(modelElkorWattsonPVMeterEntity.getTotalRealPower() != 0.001 ? modelElkorWattsonPVMeterEntity.getTotalRealPower() : null);
                deviceModelElkorWattsonPVMeterEntity.setField_value1(modelElkorWattsonPVMeterEntity.getTotalRealPower() != 0.001 ? modelElkorWattsonPVMeterEntity.getTotalRealPower() : null);

                uploadFilesService.handleEnergyField(deviceModelElkorWattsonPVMeterEntity, modelElkorWattsonPVMeterEntity, "total_yield");

                deviceModelElkorWattsonPVMeterEntity.setLast_updated(modelElkorWattsonPVMeterEntity.getTime());
                deviceLastUpdated(deviceModelElkorWattsonPVMeterEntity);

                return modelElkorWattsonPVMeterService.insertModelElkorWattsonPVMeter(modelElkorWattsonPVMeterEntity);

            case "model_sungrow_sh6250hv_mv":
            	ModelSungrowSh6250hvMvEntity modelSungrowSh6250hvMvEntity = modelSungrowSh6250hvMvService.setModelSungrowSh6250hvMv(telemetryData);
                DeviceEntity deviceModelSungrowSh6250hvMvEntity = deviceByModbusMap.get(modbusdevicenumber);

            	modelSungrowSh6250hvMvEntity.setId_device(deviceModelSungrowSh6250hvMvEntity.getId());
            	modelSungrowSh6250hvMvEntity.setDatatablename(deviceModelSungrowSh6250hvMvEntity.getDatatablename());
            	modelSungrowSh6250hvMvEntity.setView_tablename(deviceModelSungrowSh6250hvMvEntity.getView_tablename());
            	modelSungrowSh6250hvMvEntity.setJob_tablename(deviceModelSungrowSh6250hvMvEntity.getJob_tablename());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelSungrowSh6250hvMvEntity);

                deviceModelSungrowSh6250hvMvEntity.setLast_value(modelSungrowSh6250hvMvEntity.getActive_power() != 0.001 ? modelSungrowSh6250hvMvEntity.getActive_power() : null);
                deviceModelSungrowSh6250hvMvEntity.setField_value1(modelSungrowSh6250hvMvEntity.getActive_power() != 0.001 ? modelSungrowSh6250hvMvEntity.getActive_power() : null);

                uploadFilesService.handleEnergyField(deviceModelSungrowSh6250hvMvEntity, modelSungrowSh6250hvMvEntity, "total_yield");

                deviceModelSungrowSh6250hvMvEntity.setLast_updated(modelSungrowSh6250hvMvEntity.getTime());
                deviceLastUpdated(deviceModelSungrowSh6250hvMvEntity);

                return modelSungrowSh6250hvMvService.insertModelSungrowSh6250hvMv(modelSungrowSh6250hvMvEntity);

            case "model_sungrow_pv_24h_scb":
            	ModelSungrowPv24hScbEntity modelSungrowPv24hScbEntity = modelSungrowPv24hScbService.setModelSungrowPv24hScb(telemetryData);
                DeviceEntity deviceModelSungrowPv24hScbEntity = deviceByModbusMap.get(modbusdevicenumber);

            	modelSungrowPv24hScbEntity.setId_device(deviceModelSungrowPv24hScbEntity.getId());
            	modelSungrowPv24hScbEntity.setDatatablename(deviceModelSungrowPv24hScbEntity.getDatatablename());
            	modelSungrowPv24hScbEntity.setView_tablename(deviceModelSungrowPv24hScbEntity.getView_tablename());
            	modelSungrowPv24hScbEntity.setJob_tablename(deviceModelSungrowPv24hScbEntity.getJob_tablename());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelSungrowPv24hScbEntity);

                deviceModelSungrowPv24hScbEntity.setLast_value(modelSungrowPv24hScbEntity.getDc_power() != 0.001 ? modelSungrowPv24hScbEntity.getDc_power() : null);
                deviceModelSungrowPv24hScbEntity.setField_value1(modelSungrowPv24hScbEntity.getDc_power() != 0.001 ? modelSungrowPv24hScbEntity.getDc_power() : null);

                uploadFilesService.handleEnergyField(deviceModelSungrowPv24hScbEntity, modelSungrowPv24hScbEntity, "total_yield");

                deviceModelSungrowPv24hScbEntity.setLast_updated(modelSungrowPv24hScbEntity.getTime());
                deviceLastUpdated(deviceModelSungrowPv24hScbEntity);

            	return modelSungrowPv24hScbService.insertModelSungrowPv24hScb(modelSungrowPv24hScbEntity);
            	
            case "model_protection_relay":
            	ModelProtectionRelayEntity modelProtectionRelayEntity = modelProtectionRelayService.setModelProtectionRelay(telemetryData);
                DeviceEntity deviceModelProtectionRelayEntity = deviceByModbusMap.get(modbusdevicenumber);

            	modelProtectionRelayEntity.setId_device(deviceModelProtectionRelayEntity.getId());
            	modelProtectionRelayEntity.setDatatablename(deviceModelProtectionRelayEntity.getDatatablename());
            	modelProtectionRelayEntity.setView_tablename(deviceModelProtectionRelayEntity.getView_tablename());
            	modelProtectionRelayEntity.setJob_tablename(deviceModelProtectionRelayEntity.getJob_tablename());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelProtectionRelayEntity);

                deviceModelProtectionRelayEntity.setLast_value(modelProtectionRelayEntity.getP() != 0.001 ? modelProtectionRelayEntity.getP() : null);
                deviceModelProtectionRelayEntity.setField_value1(modelProtectionRelayEntity.getP() != 0.001 ? modelProtectionRelayEntity.getP() : null);

//                uploadFilesService.handleEnergyField(deviceModelProtectionRelayEntity, modelProtectionRelayEntity, "total_yield");

                deviceModelProtectionRelayEntity.setLast_updated(modelProtectionRelayEntity.getTime());
                deviceLastUpdated(deviceModelProtectionRelayEntity);

            	return modelProtectionRelayService.insertModelProtectionRelay(modelProtectionRelayEntity);
            	
            	
            case "model_SMP4_DP":
            	ModelSMP4DPEntity modelSMP4DPEntity = modelSMP4DPService.setModelSMP4DP(telemetryData);
                DeviceEntity deviceModelSMP4DPEntity = deviceByModbusMap.get(modbusdevicenumber);

            	modelSMP4DPEntity.setId_device(deviceModelSMP4DPEntity.getId());
            	modelSMP4DPEntity.setDatatablename(deviceModelSMP4DPEntity.getDatatablename());
            	modelSMP4DPEntity.setView_tablename(deviceModelSMP4DPEntity.getView_tablename());
            	modelSMP4DPEntity.setJob_tablename(deviceModelSMP4DPEntity.getJob_tablename());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelSMP4DPEntity);

                deviceModelSMP4DPEntity.setLast_value(modelSMP4DPEntity.getWS_GH_IRRADIANCE() != 0.001 ? modelSMP4DPEntity.getWS_GH_IRRADIANCE() : null);
                deviceModelSMP4DPEntity.setField_value1(modelSMP4DPEntity.getWS_GH_IRRADIANCE() != 0.001 ? modelSMP4DPEntity.getWS_GH_IRRADIANCE() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelSMP4DPEntity.setLast_updated(modelSMP4DPEntity.getTime());
                deviceLastUpdated(deviceModelSMP4DPEntity);

            	return modelSMP4DPService.insertModelSMP4DP(modelSMP4DPEntity);
            	
            case "model_IDEC_PLC":
            	ModelIDECPLCEntity modelIDECPLCEntity = modelIDECPLCService.setModelIDECPLC(telemetryData);
                DeviceEntity deviceModelIDECPLCEntity = deviceByModbusMap.get(modbusdevicenumber);

            	modelIDECPLCEntity.setId_device(deviceByModbusMap.get(modbusdevicenumber).getId());
            	modelIDECPLCEntity.setDatatablename(deviceByModbusMap.get(modbusdevicenumber).getDatatablename());
            	modelIDECPLCEntity.setView_tablename(deviceByModbusMap.get(modbusdevicenumber).getView_tablename());
            	modelIDECPLCEntity.setJob_tablename(deviceByModbusMap.get(modbusdevicenumber).getJob_tablename());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelIDECPLCEntity);

                deviceModelIDECPLCEntity.setLast_value(modelIDECPLCEntity.getLOCAL_AI_ACTIVE_POWER_FEEDBACK() != 0.001 ? modelIDECPLCEntity.getLOCAL_AI_ACTIVE_POWER_FEEDBACK() : null);
                deviceModelIDECPLCEntity.setField_value1(modelIDECPLCEntity.getLOCAL_AI_ACTIVE_POWER_FEEDBACK() != 0.001 ? modelIDECPLCEntity.getLOCAL_AI_ACTIVE_POWER_FEEDBACK() : null);

//                uploadFilesService.handleEnergyField(deviceModelIDECPLCEntity, modelIDECPLCEntity, "ACTIVE_POWER_W_REF_TO_FREQ_TOGGLE");

                deviceModelIDECPLCEntity.setLast_updated(modelIDECPLCEntity.getTime());
                deviceLastUpdated(deviceModelIDECPLCEntity);

            	return modelIDECPLCService.insertModelIDECPLC(modelIDECPLCEntity);
            	
            case "model_InaccessPPC":
            	ModelInaccessPPCEntity modelInaccessPPCEntity = modelInaccessPPCService.setModelInaccessPPC(telemetryData);
                DeviceEntity deviceModelInaccessPPCEntity = deviceByModbusMap.get(modbusdevicenumber);

            	modelInaccessPPCEntity.setId_device(deviceModelInaccessPPCEntity.getId());
            	modelInaccessPPCEntity.setDatatablename(deviceModelInaccessPPCEntity.getDatatablename());
            	modelInaccessPPCEntity.setView_tablename(deviceModelInaccessPPCEntity.getView_tablename());
            	modelInaccessPPCEntity.setJob_tablename(deviceModelInaccessPPCEntity.getJob_tablename());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelInaccessPPCEntity);

                deviceModelInaccessPPCEntity.setLast_value(modelInaccessPPCEntity.getANALOG_INPUT_ACTIVE_POWER_FEEDBACK() != 0.001 ? modelInaccessPPCEntity.getANALOG_INPUT_ACTIVE_POWER_FEEDBACK() : null);
                deviceModelInaccessPPCEntity.setField_value1(modelInaccessPPCEntity.getANALOG_INPUT_ACTIVE_POWER_FEEDBACK() != 0.001 ? modelInaccessPPCEntity.getANALOG_INPUT_ACTIVE_POWER_FEEDBACK() : null);

//                uploadFilesService.handleEnergyField(deviceModelInaccessPPCEntity, modelInaccessPPCEntity, "ANALOG_INPUT_ACTIVE_POWER_FEEDBACK");

                deviceModelInaccessPPCEntity.setLast_updated(modelInaccessPPCEntity.getTime());
                deviceLastUpdated(deviceModelInaccessPPCEntity);

            	return modelInaccessPPCService.insertModelInaccessPPC(modelInaccessPPCEntity);
                
            	
            case "model_w_kipp_zonen_rt1":
            	ModelWKippZonenRT1Entity modelWKippZonenRT1Entity = modelWKippZonenRT1Service.setModelWKippZonenRT1(telemetryData);
                DeviceEntity deviceModelWKippZonenRT1Entity = deviceByModbusMap.get(modbusdevicenumber);

            	modelWKippZonenRT1Entity.setId_device(deviceModelWKippZonenRT1Entity.getId());
            	modelWKippZonenRT1Entity.setDatatablename(deviceModelWKippZonenRT1Entity.getDatatablename());
            	modelWKippZonenRT1Entity.setView_tablename(deviceModelWKippZonenRT1Entity.getView_tablename());
            	modelWKippZonenRT1Entity.setJob_tablename(deviceModelWKippZonenRT1Entity.getJob_tablename());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelWKippZonenRT1Entity);

                deviceModelWKippZonenRT1Entity.setLast_value(modelWKippZonenRT1Entity.getSunPOATempComp() != 0.001 ? modelWKippZonenRT1Entity.getSunPOATempComp() : null);
                deviceModelWKippZonenRT1Entity.setField_value1(modelWKippZonenRT1Entity.getSunPOATempComp() != 0.001 ? modelWKippZonenRT1Entity.getSunPOATempComp() : null);

//                uploadFilesService.handleEnergyField(deviceModelWKippZonenRT1Entity, modelWKippZonenRT1Entity, "total_yield");

                deviceModelWKippZonenRT1Entity.setLast_updated(modelWKippZonenRT1Entity.getTime());
                deviceLastUpdated(deviceModelWKippZonenRT1Entity);

            	return modelWKippZonenRT1Service.insertModelWKippZonenRT1(modelWKippZonenRT1Entity);
            	
            	
            case "model_protection_relay_v1":
            	ModelProtectionRelayV1Entity modelProtectionRelayV1Entity = modelProtectionRelayV1Service.setModelProtectionRelayV1(telemetryData);
                DeviceEntity deviceModelProtectionRelayV1Entity = deviceByModbusMap.get(modbusdevicenumber);

            	modelProtectionRelayV1Entity.setId_device(deviceModelProtectionRelayV1Entity.getId());
            	modelProtectionRelayV1Entity.setDatatablename(deviceModelProtectionRelayV1Entity.getDatatablename());
            	modelProtectionRelayV1Entity.setView_tablename(deviceModelProtectionRelayV1Entity.getView_tablename());
            	modelProtectionRelayV1Entity.setJob_tablename(deviceModelProtectionRelayV1Entity.getJob_tablename());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelProtectionRelayV1Entity);

                deviceModelProtectionRelayV1Entity.setLast_value(modelProtectionRelayV1Entity.getAI_P() != 0.001 ? modelProtectionRelayV1Entity.getAI_P() : null);
                deviceModelProtectionRelayV1Entity.setField_value1(modelProtectionRelayV1Entity.getAI_P() != 0.001 ? modelProtectionRelayV1Entity.getAI_P() : null);

//                uploadFilesService.handleEnergyField(deviceModelProtectionRelayEntity, modelProtectionRelayEntity, "total_yield");

                deviceModelProtectionRelayV1Entity.setLast_updated(modelProtectionRelayV1Entity.getTime());
                deviceLastUpdated(deviceModelProtectionRelayV1Entity);

            	return modelProtectionRelayV1Service.insertModelProtectionRelayV1(modelProtectionRelayV1Entity);
            	
            	
            case "model_SMP4_DPV1":
            	ModelSMP4DPV1Entity modelSMP4DPV1Entity = modelSMP4DPV1Service.setModelSMP4DPV1(telemetryData);
                DeviceEntity deviceModelSMP4DPV1Entity = deviceByModbusMap.get(modbusdevicenumber);

            	modelSMP4DPV1Entity.setId_device(deviceModelSMP4DPV1Entity.getId());
            	modelSMP4DPV1Entity.setDatatablename(deviceModelSMP4DPV1Entity.getDatatablename());
            	modelSMP4DPV1Entity.setView_tablename(deviceModelSMP4DPV1Entity.getView_tablename());
            	modelSMP4DPV1Entity.setJob_tablename(deviceModelSMP4DPV1Entity.getJob_tablename());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelSMP4DPV1Entity);

                deviceModelSMP4DPV1Entity.setLast_value(modelSMP4DPV1Entity.getWS_GH_IRRADIANCE() != 0.001 ? modelSMP4DPV1Entity.getWS_GH_IRRADIANCE() : null);
                deviceModelSMP4DPV1Entity.setField_value1(modelSMP4DPV1Entity.getWS_GH_IRRADIANCE() != 0.001 ? modelSMP4DPV1Entity.getWS_GH_IRRADIANCE() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelSMP4DPV1Entity.setLast_updated(modelSMP4DPV1Entity.getTime());
                deviceLastUpdated(deviceModelSMP4DPV1Entity);

            	return modelSMP4DPV1Service.insertModelSMP4DPV1(modelSMP4DPV1Entity);
            	
            default:
                return false;
        }
    }

    /**
     *@desciption handle dataname list and call main handler
     * @author Minh Le
     * @date 26-01-2026
     * @return void
     */
    public void syncData(boolean isFirstRun) {
//        List<String> dataTableNameList = getPostgresTableName();
    	List<String> dataTableNameList = new ArrayList<>();
    	dataTableNameList.add("data673_hw8ulp6oml1jvjxn");
        dataTableNameList.add("data673_hw8ulp6oml1jvjxn");
        
        if(!dataTableNameList.isEmpty()) {
            for(String dataTableName : dataTableNameList) {
                handleData(dataTableName, isFirstRun);
            }
        }
    }

    /**
     *@desciption handle data insert to mySQL
     * @author Minh Le
     * @date 15-01-2026
     * @return List
     */
    public void handleData(String tableName, boolean isFirstRun) {
        List<Map<String, Object>> dataList = getDataLogger(tableName, isFirstRun);

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

                    AtomicBoolean isInsertCompleted = new AtomicBoolean(true);

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
                                    boolean insert_success = insertData(deviceTableGroup, deviceByModbusMap, modbusdevicenumber, telemetryData);

                                    if (!insert_success) {
                                        isInsertCompleted.compareAndSet(true, false);
                                    }
                                } catch (Exception e) {
                                    log.error("Insert to Db failed !", e);
                                } finally {
                                    phaser.arriveAndDeregister();
                                }
                            });
                        } else {
                            isInsertCompleted.compareAndSet(true, false);
                        }
                    }

                    phaser.arriveAndAwaitAdvance();

                    int deletedRows = 0;
                    int updatedRows = 0;

                    if(isInsertCompleted.get()) {
                        Map<String, Object> deleteParams = new HashMap<>();
                        deleteParams.put("databaseName", tableName);
                        deleteParams.put("logId", logId);
                        deletedRows = this.delete_Db_Datalogger("Datalogger.deleteData", deleteParams);

                    } else {
                        Map<String, Object> updateParams = new HashMap<>();
                        updateParams.put("databaseName", tableName);
                        updateParams.put("logId", logId);
                        updateParams.put("isInsertCompleted", false);
                        updatedRows = this.update_data_status_Db_Datalogger("Datalogger.updateDataStatus", updateParams);
                    }

                    log.info("Deleted data from: " + tableName + ", Affect rows: " +  deletedRows + ", Id: " + dataLogElement.get("id"));
                }
            }
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    private void deviceLastUpdated(DeviceEntity item) {
        try {
            deviceService.updateLastUpdated(item);
        } catch (Exception e) {
        	log.error(e);
        }
    }
}
