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
import com.nwm.api.entities.ModelGEHiWindingEntity;
import com.nwm.api.entities.ModelGEMultilinEPM6000Entity;
import com.nwm.api.entities.ModelHuaweiSmartloggerV1Entity;
import com.nwm.api.entities.ModelIDECPLCEntity;
import com.nwm.api.entities.ModelIDECPLCV1Entity;
import com.nwm.api.entities.ModelIDECPLCV2Entity;
import com.nwm.api.entities.ModelIDECPLCV3Entity;
import com.nwm.api.entities.ModelInaccessPPCEntity;
import com.nwm.api.entities.ModelInaccessPPCV1Entity;
import com.nwm.api.entities.ModelInaccessPPCV2Entity;
import com.nwm.api.entities.ModelMVPSHUAWEIEntity;
import com.nwm.api.entities.ModelMainWeatherStationEntity;
import com.nwm.api.entities.ModelOrionMXAutomationPlatformEntity;
import com.nwm.api.entities.ModelProtectionRelayEntity;
import com.nwm.api.entities.ModelProtectionRelayV1Entity;
import com.nwm.api.entities.ModelProtectionRelayv2Entity;
import com.nwm.api.entities.ModelSMASTRINGCOMBINEREntity;
import com.nwm.api.entities.ModelSMASUNNYCENTRALSC1000CP10Entity;
import com.nwm.api.entities.ModelSMP4DPEntity;
import com.nwm.api.entities.ModelSMP4DPV1Entity;
import com.nwm.api.entities.ModelSUN2000330KTLH1Entity;
import com.nwm.api.entities.ModelSUNGROWSG6250HVMVV1Entity;
import com.nwm.api.entities.ModelSchneiderHiWindingEntity;
import com.nwm.api.entities.ModelSungrowPv24hScbEntity;
import com.nwm.api.entities.ModelSungrowSh6250hvMvEntity;
import com.nwm.api.entities.ModelWKippZonenRT1Entity;
import com.nwm.api.entities.ModelWeatherStationCustomEntity;
import com.nwm.api.entities.ModelHuaweiSmartloggerWeatherEntity;
import com.nwm.api.entities.SiteEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DataloggerSyncService extends DB {
    @Value("${server1.name}")
    private String serverName1;

    @Value("${server2.name}")
    private String serverName2;

    @Value("${server1.run_on_id}")
    private List<Integer> server1_run_on_id;

    @Value("${server2.run_on_id}")
    private List<Integer> server2_run_on_id;

    @Value("${server.local.run_on_id}")
    private List<Integer> server_local_run_on_id;

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
    
    
    @Autowired
    private ModelSUN2000330KTLH1Service modelSUN2000330KTLH1Service;
    @Autowired
    private ModelProtectionRelayv2Service modelProtectionRelayv2Service;
    @Autowired
    private ModelInaccessPPCV1Service modelInaccessPPCV1Service;
    @Autowired
    private ModelIDECPLCV1Service modelIDECPLCV1Service;
    @Autowired
    private ModelOrionMXAutomationPlatformService modelOrionMXAutomationPlatformService;
    @Autowired
    private ModelMVPSHUAWEIService modelMVPSHUAWEIService;
    @Autowired
    private ModelHuaweiSmartloggerV1Service modelHuaweiSmartloggerV1Service;
    @Autowired
    private ModelHuaweiSmartloggerWeatherService modelHuaweiSmartloggerWeatherService;
    
    
    
    
    
    
    @Autowired
    private ModelSMASUNNYCENTRALSC1000CP10Service modelSMASUNNYCENTRALSC1000CP10Service;
    @Autowired
    private ModelSMASTRINGCOMBINERService modelSMASTRINGCOMBINERService;
    @Autowired
    private ModelGEHiWindingService modelGEHiWindingService;
    @Autowired
    private ModelSchneiderHiWindingService modelSchneiderHiWindingService;
    @Autowired
    private ModelIDECPLCV2Service modelIDECPLCV2Service;
    @Autowired
    private ModelMainWeatherStationService modelMainWeatherStationService;
    
    @Autowired
    private ModelIDECPLCV3Service modelIDECPLCV3Service;
    
    @Autowired
    private ModelWeatherStationCustomService modelWeatherStationCustomService;
    
    
    @Autowired
    private ModelSUNGROWSG6250HVMVV1Service modelSUNGROWSG6250HVMVV1Service;
    @Autowired
    private ModelGEMultilinEPM6000Service modelGEMultilinEPM6000Service;
    @Autowired
    private ModelInaccessPPCV2Service modelInaccessPPCV2Service;


    private static final Map<String, List<Integer>> HOSTNAME_TO_SITE_RUNNING = new HashMap<>();
    @PostConstruct
    public void init() {
        String localhost = getPrivateIP();
//        try {
//            localhost = InetAddress.getLocalHost().getHostName();
//        } catch (UnknownHostException e) {
//            throw new RuntimeException(e);
//        }
        
        System.out.println("Private IP: " + getPrivateIP());

//        log.error("testabc: "+localhost);
//        System.out.println(localhost);
        
        HOSTNAME_TO_SITE_RUNNING.put(serverName1, server1_run_on_id);
        HOSTNAME_TO_SITE_RUNNING.put(serverName2, server2_run_on_id);

        if(!localhost.equals(serverName1) && !localhost.equals(serverName2)) {
            HOSTNAME_TO_SITE_RUNNING.put(localhost, server_local_run_on_id);
        }
    }

    private final int TABLE_THREAD = 5;
    private final int INSERT_THREAD = 100;
    private final int DATA_GET_LIMIT = 200;

    private final ExecutorService tableExecutor = Executors.newFixedThreadPool(TABLE_THREAD);
    private final ExecutorService insertExecutor = Executors.newFixedThreadPool(INSERT_THREAD);

    private final Set<String> runningTables = ConcurrentHashMap.newKeySet();

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * @desciption get table name from Postgres DB
     * @author Minh Le
     * @date 26-01-2026
     * @return List
     */
    private List<String> getPostgresTableName(String hostname, Map<String, List<Integer>> hostnameToSiteRunning) {
        List<SiteEntity> siteList;
        List<String> talbeNameList = new ArrayList<>();

        List<Integer> runOnServerList = hostnameToSiteRunning.get(hostname);

        try {
            siteList = this.queryForList("Site.getSiteExistPostgresDb", runOnServerList);
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
     * @desciption insert data
     * @author Minh Le
     * @date 15-01-2026
     * @return boolean
     */
    private boolean insertData(String deviceTableGroup, Map<String, DeviceEntity> deviceByModbusMap, String modbusdevicenumber, String telemetryData) {
        List<DeviceEntity> scaledDeviceParameters = deviceService.getListScaledDeviceParameter(deviceByModbusMap.get(modbusdevicenumber));
        DeviceEntity deviceEntity = deviceByModbusMap.get(modbusdevicenumber);
        String timezone = null;
        if (deviceEntity != null) {
            // Ưu tiên lấy timezone_value, nếu không có thì lấy timezone_offset hoặc thuộc tính khác nếu cần
            timezone = deviceEntity.getTimezone_value();
            if (timezone == null || timezone.isEmpty()) {
                timezone = deviceEntity.getTimezone_offset();
            }
        }
        switch (deviceTableGroup) {
            case "model_chint_solectria_inverter_class9725":
                ModelChintSolectriaInverterClass9725Entity modelChintSolectriaInverterClass9725Entity = modelChintSolectriaInverterClass9725Service.setModelChintSolectriaInverterClass9725(telemetryData);
                DeviceEntity deviceModelChintSolectriaInverterClass9725Entity = deviceByModbusMap.get(modbusdevicenumber);
                

                modelChintSolectriaInverterClass9725Entity.setId_device(deviceModelChintSolectriaInverterClass9725Entity.getId());
                modelChintSolectriaInverterClass9725Entity.setDatatablename(deviceModelChintSolectriaInverterClass9725Entity.getDatatablename());
                modelChintSolectriaInverterClass9725Entity.setView_tablename(deviceModelChintSolectriaInverterClass9725Entity.getView_tablename());
                modelChintSolectriaInverterClass9725Entity.setJob_tablename(deviceModelChintSolectriaInverterClass9725Entity.getJob_tablename());
                modelChintSolectriaInverterClass9725Entity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelChintSolectriaInverterClass9725Entity);

                deviceModelChintSolectriaInverterClass9725Entity.setLast_value(modelChintSolectriaInverterClass9725Entity.getAC_ActivePower() != 0.001 ? modelChintSolectriaInverterClass9725Entity.getAC_ActivePower() : null);
                deviceModelChintSolectriaInverterClass9725Entity.setField_value1(modelChintSolectriaInverterClass9725Entity.getAC_ActivePower() != 0.001 ? modelChintSolectriaInverterClass9725Entity.getAC_ActivePower() : null);

                uploadFilesService.handleEnergyField(deviceModelChintSolectriaInverterClass9725Entity, modelChintSolectriaInverterClass9725Entity, "total_yield");

                boolean insertModelChintSolectriaInverterClass9725Result = modelChintSolectriaInverterClass9725Service.insertModelChintSolectriaInverterClass9725(modelChintSolectriaInverterClass9725Entity);
                if(insertModelChintSolectriaInverterClass9725Result) deviceLastUpdated(deviceModelChintSolectriaInverterClass9725Entity);

                return insertModelChintSolectriaInverterClass9725Result;

            case "model_elkor_wattson_pv_meter":
                ModelElkorWattsonPVMeterEntity modelElkorWattsonPVMeterEntity = modelElkorWattsonPVMeterService.setModelElkorWattsonPVMeter(telemetryData);
                DeviceEntity deviceModelElkorWattsonPVMeterEntity = deviceByModbusMap.get(modbusdevicenumber);

                modelElkorWattsonPVMeterEntity.setId_device(deviceModelElkorWattsonPVMeterEntity.getId());
                modelElkorWattsonPVMeterEntity.setDatatablename(deviceModelElkorWattsonPVMeterEntity.getDatatablename());
                modelElkorWattsonPVMeterEntity.setView_tablename(deviceModelElkorWattsonPVMeterEntity.getView_tablename());
                modelElkorWattsonPVMeterEntity.setJob_tablename(deviceModelElkorWattsonPVMeterEntity.getJob_tablename());
                modelElkorWattsonPVMeterEntity.setTimezone_value(deviceEntity.getTimezone_value());
                

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelElkorWattsonPVMeterEntity);

                deviceModelElkorWattsonPVMeterEntity.setLast_value(modelElkorWattsonPVMeterEntity.getTotalRealPower() != 0.001 ? modelElkorWattsonPVMeterEntity.getTotalRealPower() : null);
                deviceModelElkorWattsonPVMeterEntity.setField_value1(modelElkorWattsonPVMeterEntity.getTotalRealPower() != 0.001 ? modelElkorWattsonPVMeterEntity.getTotalRealPower() : null);

                uploadFilesService.handleEnergyField(deviceModelElkorWattsonPVMeterEntity, modelElkorWattsonPVMeterEntity, "total_yield");

                deviceModelElkorWattsonPVMeterEntity.setLast_updated(modelElkorWattsonPVMeterEntity.getTime());

                boolean insertModelElkorWattsonPVMeterResult = modelElkorWattsonPVMeterService.insertModelElkorWattsonPVMeter(modelElkorWattsonPVMeterEntity);
                if(insertModelElkorWattsonPVMeterResult) deviceLastUpdated(deviceModelElkorWattsonPVMeterEntity);

                return insertModelElkorWattsonPVMeterResult;

            case "model_sungrow_sh6250hv_mv":
            	ModelSungrowSh6250hvMvEntity modelSungrowSh6250hvMvEntity = modelSungrowSh6250hvMvService.setModelSungrowSh6250hvMv(telemetryData);
                DeviceEntity deviceModelSungrowSh6250hvMvEntity = deviceByModbusMap.get(modbusdevicenumber);

            	modelSungrowSh6250hvMvEntity.setId_device(deviceModelSungrowSh6250hvMvEntity.getId());
            	modelSungrowSh6250hvMvEntity.setDatatablename(deviceModelSungrowSh6250hvMvEntity.getDatatablename());
            	modelSungrowSh6250hvMvEntity.setView_tablename(deviceModelSungrowSh6250hvMvEntity.getView_tablename());
            	modelSungrowSh6250hvMvEntity.setJob_tablename(deviceModelSungrowSh6250hvMvEntity.getJob_tablename());
            	modelSungrowSh6250hvMvEntity.setTimezone_value(deviceEntity.getTimezone_value());
            	
                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelSungrowSh6250hvMvEntity);

                deviceModelSungrowSh6250hvMvEntity.setLast_value(modelSungrowSh6250hvMvEntity.getActive_power() != 0.001 ? modelSungrowSh6250hvMvEntity.getActive_power() : null);
                deviceModelSungrowSh6250hvMvEntity.setField_value1(modelSungrowSh6250hvMvEntity.getActive_power() != 0.001 ? modelSungrowSh6250hvMvEntity.getActive_power() : null);

                uploadFilesService.handleEnergyField(deviceModelSungrowSh6250hvMvEntity, modelSungrowSh6250hvMvEntity, "total_yield");

                deviceModelSungrowSh6250hvMvEntity.setLast_updated(modelSungrowSh6250hvMvEntity.getTime());

                boolean insertModelSungrowSh6250hvMvResult = modelSungrowSh6250hvMvService.insertModelSungrowSh6250hvMv(modelSungrowSh6250hvMvEntity);
                if(insertModelSungrowSh6250hvMvResult) deviceLastUpdated(deviceModelSungrowSh6250hvMvEntity);

                return insertModelSungrowSh6250hvMvResult;

            case "model_sungrow_pv_24h_scb":
            	ModelSungrowPv24hScbEntity modelSungrowPv24hScbEntity = modelSungrowPv24hScbService.setModelSungrowPv24hScb(telemetryData);
                DeviceEntity deviceModelSungrowPv24hScbEntity = deviceByModbusMap.get(modbusdevicenumber);

            	modelSungrowPv24hScbEntity.setId_device(deviceModelSungrowPv24hScbEntity.getId());
            	modelSungrowPv24hScbEntity.setDatatablename(deviceModelSungrowPv24hScbEntity.getDatatablename());
            	modelSungrowPv24hScbEntity.setView_tablename(deviceModelSungrowPv24hScbEntity.getView_tablename());
            	modelSungrowPv24hScbEntity.setJob_tablename(deviceModelSungrowPv24hScbEntity.getJob_tablename());
            	modelSungrowPv24hScbEntity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelSungrowPv24hScbEntity);

                deviceModelSungrowPv24hScbEntity.setLast_value(modelSungrowPv24hScbEntity.getDc_power() != 0.001 ? modelSungrowPv24hScbEntity.getDc_power() : null);
                deviceModelSungrowPv24hScbEntity.setField_value1(modelSungrowPv24hScbEntity.getDc_power() != 0.001 ? modelSungrowPv24hScbEntity.getDc_power() : null);

                uploadFilesService.handleEnergyField(deviceModelSungrowPv24hScbEntity, modelSungrowPv24hScbEntity, "total_yield");

                deviceModelSungrowPv24hScbEntity.setLast_updated(modelSungrowPv24hScbEntity.getTime());

                boolean insertModelSungrowPv24hScbResult = modelSungrowPv24hScbService.insertModelSungrowPv24hScb(modelSungrowPv24hScbEntity);
                if(insertModelSungrowPv24hScbResult) deviceLastUpdated(deviceModelSungrowPv24hScbEntity);

                return insertModelSungrowPv24hScbResult;
            	
            case "model_protection_relay":
            	ModelProtectionRelayEntity modelProtectionRelayEntity = modelProtectionRelayService.setModelProtectionRelay(telemetryData);
                DeviceEntity deviceModelProtectionRelayEntity = deviceByModbusMap.get(modbusdevicenumber);

            	modelProtectionRelayEntity.setId_device(deviceModelProtectionRelayEntity.getId());
            	modelProtectionRelayEntity.setDatatablename(deviceModelProtectionRelayEntity.getDatatablename());
            	modelProtectionRelayEntity.setView_tablename(deviceModelProtectionRelayEntity.getView_tablename());
            	modelProtectionRelayEntity.setJob_tablename(deviceModelProtectionRelayEntity.getJob_tablename());
            	modelProtectionRelayEntity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelProtectionRelayEntity);

                deviceModelProtectionRelayEntity.setLast_value(modelProtectionRelayEntity.getP() != 0.001 ? modelProtectionRelayEntity.getP() : null);
                deviceModelProtectionRelayEntity.setField_value1(modelProtectionRelayEntity.getP() != 0.001 ? modelProtectionRelayEntity.getP() : null);

//                uploadFilesService.handleEnergyField(deviceModelProtectionRelayEntity, modelProtectionRelayEntity, "total_yield");

                deviceModelProtectionRelayEntity.setLast_updated(modelProtectionRelayEntity.getTime());

                boolean insertModelProtectionRelayResult = modelProtectionRelayService.insertModelProtectionRelay(modelProtectionRelayEntity);
                if(insertModelProtectionRelayResult) deviceLastUpdated(deviceModelProtectionRelayEntity);

                return insertModelProtectionRelayResult;
            	
            case "model_SMP4_DP":
            	System.out.println(deviceByModbusMap.get("timezone_value"));
            	ModelSMP4DPEntity modelSMP4DPEntity = modelSMP4DPService.setModelSMP4DP(telemetryData);
                DeviceEntity deviceModelSMP4DPEntity = deviceByModbusMap.get(modbusdevicenumber);

            	modelSMP4DPEntity.setId_device(deviceModelSMP4DPEntity.getId());
            	modelSMP4DPEntity.setDatatablename(deviceModelSMP4DPEntity.getDatatablename());
            	modelSMP4DPEntity.setView_tablename(deviceModelSMP4DPEntity.getView_tablename());
            	modelSMP4DPEntity.setJob_tablename(deviceModelSMP4DPEntity.getJob_tablename());
            	modelSMP4DPEntity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelSMP4DPEntity);

                deviceModelSMP4DPEntity.setLast_value(modelSMP4DPEntity.getAI_5XF01ARTHI_WIND_P() != 0.001 ? modelSMP4DPEntity.getAI_5XF01ARTHI_WIND_P() : null);
                deviceModelSMP4DPEntity.setField_value1(modelSMP4DPEntity.getAI_5XF01ARTHI_WIND_P() != 0.001 ? modelSMP4DPEntity.getAI_5XF01ARTHI_WIND_P() : null);

                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "AI_5XF01ARTHi_wind_wh_del");

                deviceModelSMP4DPEntity.setLast_updated(modelSMP4DPEntity.getTime());

                boolean insertModelSMP4DPResult = modelSMP4DPService.insertModelSMP4DP(modelSMP4DPEntity);
                if(insertModelSMP4DPResult) deviceLastUpdated(deviceModelSMP4DPEntity);

                return insertModelSMP4DPResult;
            	
            case "model_IDEC_PLC":
            	ModelIDECPLCEntity modelIDECPLCEntity = modelIDECPLCService.setModelIDECPLC(telemetryData);
                DeviceEntity deviceModelIDECPLCEntity = deviceByModbusMap.get(modbusdevicenumber);

            	modelIDECPLCEntity.setId_device(deviceByModbusMap.get(modbusdevicenumber).getId());
            	modelIDECPLCEntity.setDatatablename(deviceByModbusMap.get(modbusdevicenumber).getDatatablename());
            	modelIDECPLCEntity.setView_tablename(deviceByModbusMap.get(modbusdevicenumber).getView_tablename());
            	modelIDECPLCEntity.setJob_tablename(deviceByModbusMap.get(modbusdevicenumber).getJob_tablename());
            	modelIDECPLCEntity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelIDECPLCEntity);

                deviceModelIDECPLCEntity.setLast_value(modelIDECPLCEntity.getLOCAL_AI_ACTIVE_POWER_FEEDBACK() != 0.001 ? modelIDECPLCEntity.getLOCAL_AI_ACTIVE_POWER_FEEDBACK() : null);
                deviceModelIDECPLCEntity.setField_value1(modelIDECPLCEntity.getLOCAL_AI_ACTIVE_POWER_FEEDBACK() != 0.001 ? modelIDECPLCEntity.getLOCAL_AI_ACTIVE_POWER_FEEDBACK() : null);

//                uploadFilesService.handleEnergyField(deviceModelIDECPLCEntity, modelIDECPLCEntity, "ACTIVE_POWER_W_REF_TO_FREQ_TOGGLE");

                deviceModelIDECPLCEntity.setLast_updated(modelIDECPLCEntity.getTime());

                boolean insertModelIDECPLCResult = modelIDECPLCService.insertModelIDECPLC(modelIDECPLCEntity);;
                if(insertModelIDECPLCResult) deviceLastUpdated(deviceModelIDECPLCEntity);

                return insertModelIDECPLCResult;
            	
            case "model_InaccessPPC":
            	ModelInaccessPPCEntity modelInaccessPPCEntity = modelInaccessPPCService.setModelInaccessPPC(telemetryData);
                DeviceEntity deviceModelInaccessPPCEntity = deviceByModbusMap.get(modbusdevicenumber);

            	modelInaccessPPCEntity.setId_device(deviceModelInaccessPPCEntity.getId());
            	modelInaccessPPCEntity.setDatatablename(deviceModelInaccessPPCEntity.getDatatablename());
            	modelInaccessPPCEntity.setView_tablename(deviceModelInaccessPPCEntity.getView_tablename());
            	modelInaccessPPCEntity.setJob_tablename(deviceModelInaccessPPCEntity.getJob_tablename());
            	modelInaccessPPCEntity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelInaccessPPCEntity);

                deviceModelInaccessPPCEntity.setLast_value(modelInaccessPPCEntity.getANALOG_INPUT_ACTIVE_POWER_FEEDBACK() != 0.001 ? modelInaccessPPCEntity.getANALOG_INPUT_ACTIVE_POWER_FEEDBACK() : null);
                deviceModelInaccessPPCEntity.setField_value1(modelInaccessPPCEntity.getANALOG_INPUT_ACTIVE_POWER_FEEDBACK() != 0.001 ? modelInaccessPPCEntity.getANALOG_INPUT_ACTIVE_POWER_FEEDBACK() : null);

//                uploadFilesService.handleEnergyField(deviceModelInaccessPPCEntity, modelInaccessPPCEntity, "ANALOG_INPUT_ACTIVE_POWER_FEEDBACK");

                deviceModelInaccessPPCEntity.setLast_updated(modelInaccessPPCEntity.getTime());

                boolean insertModelInaccessPPCResult = modelInaccessPPCService.insertModelInaccessPPC(modelInaccessPPCEntity);
                if(insertModelInaccessPPCResult) deviceLastUpdated(deviceModelInaccessPPCEntity);

                return insertModelInaccessPPCResult;

            case "model_w_kipp_zonen_rt1":
            	ModelWKippZonenRT1Entity modelWKippZonenRT1Entity = modelWKippZonenRT1Service.setModelWKippZonenRT1(telemetryData);
                DeviceEntity deviceModelWKippZonenRT1Entity = deviceByModbusMap.get(modbusdevicenumber);

            	modelWKippZonenRT1Entity.setId_device(deviceModelWKippZonenRT1Entity.getId());
            	modelWKippZonenRT1Entity.setDatatablename(deviceModelWKippZonenRT1Entity.getDatatablename());
            	modelWKippZonenRT1Entity.setView_tablename(deviceModelWKippZonenRT1Entity.getView_tablename());
            	modelWKippZonenRT1Entity.setJob_tablename(deviceModelWKippZonenRT1Entity.getJob_tablename());
            	modelWKippZonenRT1Entity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelWKippZonenRT1Entity);

                deviceModelWKippZonenRT1Entity.setLast_value(modelWKippZonenRT1Entity.getSunPOATempComp() != 0.001 ? modelWKippZonenRT1Entity.getSunPOATempComp() : null);
                deviceModelWKippZonenRT1Entity.setField_value1(modelWKippZonenRT1Entity.getSunPOATempComp() != 0.001 ? modelWKippZonenRT1Entity.getSunPOATempComp() : null);

//                uploadFilesService.handleEnergyField(deviceModelWKippZonenRT1Entity, modelWKippZonenRT1Entity, "total_yield");

                deviceModelWKippZonenRT1Entity.setLast_updated(modelWKippZonenRT1Entity.getTime());

                boolean insertModelWKippZonenRT1Result = modelWKippZonenRT1Service.insertModelWKippZonenRT1(modelWKippZonenRT1Entity);
                if(insertModelWKippZonenRT1Result) deviceLastUpdated(deviceModelWKippZonenRT1Entity);

                return insertModelWKippZonenRT1Result;

            case "model_protection_relay_v1":
            	ModelProtectionRelayV1Entity modelProtectionRelayV1Entity = modelProtectionRelayV1Service.setModelProtectionRelayV1(telemetryData);
                DeviceEntity deviceModelProtectionRelayV1Entity = deviceByModbusMap.get(modbusdevicenumber);

            	modelProtectionRelayV1Entity.setId_device(deviceModelProtectionRelayV1Entity.getId());
            	modelProtectionRelayV1Entity.setDatatablename(deviceModelProtectionRelayV1Entity.getDatatablename());
            	modelProtectionRelayV1Entity.setView_tablename(deviceModelProtectionRelayV1Entity.getView_tablename());
            	modelProtectionRelayV1Entity.setJob_tablename(deviceModelProtectionRelayV1Entity.getJob_tablename());
            	modelProtectionRelayV1Entity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelProtectionRelayV1Entity);

                deviceModelProtectionRelayV1Entity.setLast_value(modelProtectionRelayV1Entity.getAI_P() != 0.001 ? modelProtectionRelayV1Entity.getAI_P() : null);
                deviceModelProtectionRelayV1Entity.setField_value1(modelProtectionRelayV1Entity.getAI_P() != 0.001 ? modelProtectionRelayV1Entity.getAI_P() : null);

//                uploadFilesService.handleEnergyField(deviceModelProtectionRelayEntity, modelProtectionRelayEntity, "total_yield");

                deviceModelProtectionRelayV1Entity.setLast_updated(modelProtectionRelayV1Entity.getTime());

                boolean insertModelProtectionRelayV1Result = modelProtectionRelayV1Service.insertModelProtectionRelayV1(modelProtectionRelayV1Entity);;
                if(insertModelProtectionRelayV1Result) deviceLastUpdated(deviceModelProtectionRelayV1Entity);

                return insertModelProtectionRelayV1Result;

            case "model_SMP4_DPV1":
            	ModelSMP4DPV1Entity modelSMP4DPV1Entity = modelSMP4DPV1Service.setModelSMP4DPV1(telemetryData);
                DeviceEntity deviceModelSMP4DPV1Entity = deviceByModbusMap.get(modbusdevicenumber);

            	modelSMP4DPV1Entity.setId_device(deviceModelSMP4DPV1Entity.getId());
            	modelSMP4DPV1Entity.setDatatablename(deviceModelSMP4DPV1Entity.getDatatablename());
            	modelSMP4DPV1Entity.setView_tablename(deviceModelSMP4DPV1Entity.getView_tablename());
            	modelSMP4DPV1Entity.setJob_tablename(deviceModelSMP4DPV1Entity.getJob_tablename());
            	modelSMP4DPV1Entity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelSMP4DPV1Entity);

                deviceModelSMP4DPV1Entity.setLast_value(modelSMP4DPV1Entity.getAI_HI_WINDING_P() != 0.001 ? modelSMP4DPV1Entity.getAI_HI_WINDING_P() : null);
                deviceModelSMP4DPV1Entity.setField_value1(modelSMP4DPV1Entity.getAI_HI_WINDING_P() != 0.001 ? modelSMP4DPV1Entity.getAI_HI_WINDING_P() : null);

                uploadFilesService.handleEnergyField(deviceModelSMP4DPV1Entity, modelSMP4DPV1Entity, "AI_HI_WINDING_kWh_Del");

                deviceModelSMP4DPV1Entity.setLast_updated(modelSMP4DPV1Entity.getTime());

                boolean insertModelSMP4DPV1Result = modelSMP4DPV1Service.insertModelSMP4DPV1(modelSMP4DPV1Entity);
                if(insertModelSMP4DPV1Result) deviceLastUpdated(deviceModelSMP4DPV1Entity);

                return insertModelSMP4DPV1Result;
            	
            case "model_SUN2000330KTLH1":
                ModelSUN2000330KTLH1Entity modelSUN2000330KTLH1Entity = modelSUN2000330KTLH1Service.setModelSUN2000330KTLH1(telemetryData);
                DeviceEntity deviceModelSUN2000330KTLH1Entity = deviceByModbusMap.get(modbusdevicenumber);

                modelSUN2000330KTLH1Entity.setId_device(deviceModelSUN2000330KTLH1Entity.getId());
                modelSUN2000330KTLH1Entity.setDatatablename(deviceModelSUN2000330KTLH1Entity.getDatatablename());
                modelSUN2000330KTLH1Entity.setView_tablename(deviceModelSUN2000330KTLH1Entity.getView_tablename());
                modelSUN2000330KTLH1Entity.setJob_tablename(deviceModelSUN2000330KTLH1Entity.getJob_tablename());
                modelSUN2000330KTLH1Entity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelSUN2000330KTLH1Entity);

                deviceModelSUN2000330KTLH1Entity.setLast_value(modelSUN2000330KTLH1Entity.getActive_Power() != 0.001 ? modelSUN2000330KTLH1Entity.getActive_Power() : null);
                deviceModelSUN2000330KTLH1Entity.setField_value1(modelSUN2000330KTLH1Entity.getActive_Power() != 0.001 ? modelSUN2000330KTLH1Entity.getActive_Power() : null);

                uploadFilesService.handleEnergyField(deviceModelSUN2000330KTLH1Entity, modelSUN2000330KTLH1Entity, "Total_Yield");

                deviceModelSUN2000330KTLH1Entity.setLast_updated(modelSUN2000330KTLH1Entity.getTime());

                boolean insertModelSUN2000330KTLH1Result = modelSUN2000330KTLH1Service.insertModelSUN2000330KTLH1(modelSUN2000330KTLH1Entity);
                if(insertModelSUN2000330KTLH1Result) deviceLastUpdated(deviceModelSUN2000330KTLH1Entity);

                return insertModelSUN2000330KTLH1Result;

            case "model_protection_relay_v2":
            	ModelProtectionRelayv2Entity modelProtectionRelayv2Entity = modelProtectionRelayv2Service.setModelProtectionRelayv2(telemetryData);
                DeviceEntity deviceModelProtectionRelayv2Entity = deviceByModbusMap.get(modbusdevicenumber);

            	modelProtectionRelayv2Entity.setId_device(deviceModelProtectionRelayv2Entity.getId());
            	modelProtectionRelayv2Entity.setDatatablename(deviceModelProtectionRelayv2Entity.getDatatablename());
            	modelProtectionRelayv2Entity.setView_tablename(deviceModelProtectionRelayv2Entity.getView_tablename());
            	modelProtectionRelayv2Entity.setJob_tablename(deviceModelProtectionRelayv2Entity.getJob_tablename());
            	modelProtectionRelayv2Entity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelProtectionRelayv2Entity);

                deviceModelProtectionRelayv2Entity.setLast_value(modelProtectionRelayv2Entity.getMetering_P() != 0.001 ? modelProtectionRelayv2Entity.getMetering_P() : null);
                deviceModelProtectionRelayv2Entity.setField_value1(modelProtectionRelayv2Entity.getMetering_P() != 0.001 ? modelProtectionRelayv2Entity.getMetering_P() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelProtectionRelayv2Entity.setLast_updated(modelProtectionRelayv2Entity.getTime());

                boolean insertModelProtectionRelayv2Result = modelProtectionRelayv2Service.insertModelProtectionRelayv2(modelProtectionRelayv2Entity);
                if(insertModelProtectionRelayv2Result) deviceLastUpdated(deviceModelProtectionRelayv2Entity);

                return insertModelProtectionRelayv2Result;
            	
            case "model_InaccessPPCV1":
                ModelInaccessPPCV1Entity modelInaccessPPCV1Entity = modelInaccessPPCV1Service.setModelInaccessPPCV1(telemetryData);
                DeviceEntity deviceModelInaccessPPCV1Entity = deviceByModbusMap.get(modbusdevicenumber);

                modelInaccessPPCV1Entity.setId_device(deviceModelInaccessPPCV1Entity.getId());
                modelInaccessPPCV1Entity.setDatatablename(deviceModelInaccessPPCV1Entity.getDatatablename());
                modelInaccessPPCV1Entity.setView_tablename(deviceModelInaccessPPCV1Entity.getView_tablename());
                modelInaccessPPCV1Entity.setJob_tablename(deviceModelInaccessPPCV1Entity.getJob_tablename());
                modelInaccessPPCV1Entity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelInaccessPPCV1Entity);

                deviceModelInaccessPPCV1Entity.setLast_value(modelInaccessPPCV1Entity.getANALOG_INPUT_ACTIVE_POWER_FEEDBACK() != 0.001 ? modelInaccessPPCV1Entity.getANALOG_INPUT_ACTIVE_POWER_FEEDBACK() : null);
                deviceModelInaccessPPCV1Entity.setField_value1(modelInaccessPPCV1Entity.getANALOG_INPUT_ACTIVE_POWER_FEEDBACK() != 0.001 ? modelInaccessPPCV1Entity.getANALOG_INPUT_ACTIVE_POWER_FEEDBACK() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelInaccessPPCV1Entity.setLast_updated(modelInaccessPPCV1Entity.getTime());

                boolean insertModelInaccessPPCV1Result = modelInaccessPPCV1Service.insertModelInaccessPPCV1(modelInaccessPPCV1Entity);
                if(insertModelInaccessPPCV1Result) deviceLastUpdated(deviceModelInaccessPPCV1Entity);

                return insertModelInaccessPPCV1Result;

            case "model_IDEC_PLCV1":
                ModelIDECPLCV1Entity modelIDECPLCV1Entity = modelIDECPLCV1Service.setModelIDECPLCV1(telemetryData);
                DeviceEntity deviceModelIDECPLCV1Entity = deviceByModbusMap.get(modbusdevicenumber);

                modelIDECPLCV1Entity.setId_device(deviceModelIDECPLCV1Entity.getId());
                modelIDECPLCV1Entity.setDatatablename(deviceModelIDECPLCV1Entity.getDatatablename());
                modelIDECPLCV1Entity.setView_tablename(deviceModelIDECPLCV1Entity.getView_tablename());
                modelIDECPLCV1Entity.setJob_tablename(deviceModelIDECPLCV1Entity.getJob_tablename());
                modelIDECPLCV1Entity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelIDECPLCV1Entity);

                deviceModelIDECPLCV1Entity.setLast_value(modelIDECPLCV1Entity.getPOA_IRRADIANCE() != 0.001 ? modelIDECPLCV1Entity.getPOA_IRRADIANCE() : null);
                deviceModelIDECPLCV1Entity.setField_value1(modelIDECPLCV1Entity.getPOA_IRRADIANCE() != 0.001 ? modelIDECPLCV1Entity.getPOA_IRRADIANCE() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelIDECPLCV1Entity.setLast_updated(modelIDECPLCV1Entity.getTime());

                boolean insertModelIDECPLCV1Result = modelIDECPLCV1Service.insertModelIDECPLCV1(modelIDECPLCV1Entity);
                if(insertModelIDECPLCV1Result) deviceLastUpdated(deviceModelIDECPLCV1Entity);

                return insertModelIDECPLCV1Result;
            	
            case "model_OrionMX_Automation_Platform":
                ModelOrionMXAutomationPlatformEntity modelOrionMXAutomationPlatformEntity = modelOrionMXAutomationPlatformService.setModelOrionMXAutomationPlatform(telemetryData);
                DeviceEntity deviceModelOrionMXAutomationPlatformEntity = deviceByModbusMap.get(modbusdevicenumber);

                modelOrionMXAutomationPlatformEntity.setId_device(deviceModelOrionMXAutomationPlatformEntity.getId());
                modelOrionMXAutomationPlatformEntity.setDatatablename(deviceModelOrionMXAutomationPlatformEntity.getDatatablename());
                modelOrionMXAutomationPlatformEntity.setView_tablename(deviceModelOrionMXAutomationPlatformEntity.getView_tablename());
                modelOrionMXAutomationPlatformEntity.setJob_tablename(deviceModelOrionMXAutomationPlatformEntity.getJob_tablename());
                modelOrionMXAutomationPlatformEntity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelOrionMXAutomationPlatformEntity);

                deviceModelOrionMXAutomationPlatformEntity.setLast_value(modelOrionMXAutomationPlatformEntity.getAI_HI_WINDING_P() != 0.001 ? modelOrionMXAutomationPlatformEntity.getAI_HI_WINDING_P() : null);
                deviceModelOrionMXAutomationPlatformEntity.setField_value1(modelOrionMXAutomationPlatformEntity.getAI_HI_WINDING_P() != 0.001 ? modelOrionMXAutomationPlatformEntity.getAI_HI_WINDING_P() : null);

                uploadFilesService.handleEnergyField(deviceModelOrionMXAutomationPlatformEntity, modelOrionMXAutomationPlatformEntity, "AI_HI_WINDING_kWh_Del");

                deviceModelOrionMXAutomationPlatformEntity.setLast_updated(modelOrionMXAutomationPlatformEntity.getTime());

                boolean insertModelOrionMXAutomationPlatformResult = modelOrionMXAutomationPlatformService.insertModelOrionMXAutomationPlatform(modelOrionMXAutomationPlatformEntity);
                if(insertModelOrionMXAutomationPlatformResult) deviceLastUpdated(deviceModelOrionMXAutomationPlatformEntity);

                return insertModelOrionMXAutomationPlatformResult;
            	
            case "model_MVPS_HUAWEI":
                ModelMVPSHUAWEIEntity modelMVPSHUAWEIEntity = modelMVPSHUAWEIService.setModelMVPSHUAWEI(telemetryData);
                DeviceEntity deviceModelMVPSHUAWEIEntity = deviceByModbusMap.get(modbusdevicenumber);

                modelMVPSHUAWEIEntity.setId_device(deviceModelMVPSHUAWEIEntity.getId());
                modelMVPSHUAWEIEntity.setDatatablename(deviceModelMVPSHUAWEIEntity.getDatatablename());
                modelMVPSHUAWEIEntity.setView_tablename(deviceModelMVPSHUAWEIEntity.getView_tablename());
                modelMVPSHUAWEIEntity.setJob_tablename(deviceModelMVPSHUAWEIEntity.getJob_tablename());
                modelMVPSHUAWEIEntity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelMVPSHUAWEIEntity);

                deviceModelMVPSHUAWEIEntity.setLast_value(modelMVPSHUAWEIEntity.getAI_LV_Panel_A_P() != 0.001 ? modelMVPSHUAWEIEntity.getAI_LV_Panel_A_P() : null);
                deviceModelMVPSHUAWEIEntity.setField_value1(modelMVPSHUAWEIEntity.getAI_LV_Panel_A_P() != 0.001 ? modelMVPSHUAWEIEntity.getAI_LV_Panel_A_P() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelMVPSHUAWEIEntity.setLast_updated(modelMVPSHUAWEIEntity.getTime());

                boolean insertModelMVPSHUAWEIResult = modelMVPSHUAWEIService.insertModelMVPSHUAWEI(modelMVPSHUAWEIEntity);
                if(insertModelMVPSHUAWEIResult) deviceLastUpdated(deviceModelMVPSHUAWEIEntity);

                return insertModelMVPSHUAWEIResult;
            	
            case "model_Huawei_Smartlogger_V1":
                ModelHuaweiSmartloggerV1Entity modelHuaweiSmartloggerV1Entity = modelHuaweiSmartloggerV1Service.setModelHuaweiSmartloggerV1(telemetryData);
                DeviceEntity deviceModelHuaweiSmartloggerV1Entity = deviceByModbusMap.get(modbusdevicenumber);

                modelHuaweiSmartloggerV1Entity.setId_device(deviceModelHuaweiSmartloggerV1Entity.getId());
                modelHuaweiSmartloggerV1Entity.setDatatablename(deviceModelHuaweiSmartloggerV1Entity.getDatatablename());
                modelHuaweiSmartloggerV1Entity.setView_tablename(deviceModelHuaweiSmartloggerV1Entity.getView_tablename());
                modelHuaweiSmartloggerV1Entity.setJob_tablename(deviceModelHuaweiSmartloggerV1Entity.getJob_tablename());
                modelHuaweiSmartloggerV1Entity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelHuaweiSmartloggerV1Entity);

                deviceModelHuaweiSmartloggerV1Entity.setLast_value(modelHuaweiSmartloggerV1Entity.getTotal_Active_Power() != 0.001 ? modelHuaweiSmartloggerV1Entity.getTotal_Active_Power() : null);
                deviceModelHuaweiSmartloggerV1Entity.setField_value1(modelHuaweiSmartloggerV1Entity.getTotal_Active_Power() != 0.001 ? modelHuaweiSmartloggerV1Entity.getTotal_Active_Power() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelHuaweiSmartloggerV1Entity.setLast_updated(modelHuaweiSmartloggerV1Entity.getTime());

                boolean insertModelHuaweiSmartloggerV1Result = modelHuaweiSmartloggerV1Service.insertModelHuaweiSmartloggerV1(modelHuaweiSmartloggerV1Entity);
                if(insertModelHuaweiSmartloggerV1Result) deviceLastUpdated(deviceModelHuaweiSmartloggerV1Entity);

                return insertModelHuaweiSmartloggerV1Result;

            case "model_huawei_smartlogger_weather":
                ModelHuaweiSmartloggerWeatherEntity modelHuaweiSmartloggerWeatherEntity = modelHuaweiSmartloggerWeatherService.setModelHuaweiSmartloggerWeather(telemetryData);
                DeviceEntity deviceModelHuaweiSmartloggerWeatherEntity  = deviceByModbusMap.get(modbusdevicenumber);

                modelHuaweiSmartloggerWeatherEntity.setId_device(deviceModelHuaweiSmartloggerWeatherEntity.getId());
                modelHuaweiSmartloggerWeatherEntity.setDatatablename(deviceModelHuaweiSmartloggerWeatherEntity.getDatatablename());
                modelHuaweiSmartloggerWeatherEntity.setView_tablename(deviceModelHuaweiSmartloggerWeatherEntity.getView_tablename());
                modelHuaweiSmartloggerWeatherEntity.setJob_tablename(deviceModelHuaweiSmartloggerWeatherEntity.getJob_tablename());
                modelHuaweiSmartloggerWeatherEntity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, modelHuaweiSmartloggerWeatherEntity);

                deviceModelHuaweiSmartloggerWeatherEntity.setLast_value(modelHuaweiSmartloggerWeatherEntity.getTotalirradiance() != 0.001 ? modelHuaweiSmartloggerWeatherEntity.getTotalirradiance() : null);
                deviceModelHuaweiSmartloggerWeatherEntity.setField_value1(modelHuaweiSmartloggerWeatherEntity.getTotalirradiance() != 0.001 ? modelHuaweiSmartloggerWeatherEntity.getTotalirradiance() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelHuaweiSmartloggerWeatherEntity.setLast_updated(modelHuaweiSmartloggerWeatherEntity.getTime());

                boolean insertModelHuaweiSmartloggerWeatherResult = modelHuaweiSmartloggerWeatherService.insertModelHuaweiSmartloggerWeather(modelHuaweiSmartloggerWeatherEntity);
                if(insertModelHuaweiSmartloggerWeatherResult) deviceLastUpdated(deviceModelHuaweiSmartloggerWeatherEntity);

                return insertModelHuaweiSmartloggerWeatherResult;
                
            case "model_SMA_SUNNY_CENTRAL_SC1000CP10":
                ModelSMASUNNYCENTRALSC1000CP10Entity ModelSMASUNNYCENTRALSC1000CP10Entity = modelSMASUNNYCENTRALSC1000CP10Service.setModelSMASUNNYCENTRALSC1000CP10(telemetryData);
                  DeviceEntity deviceModelSMASUNNYCENTRALSC1000CP10Entity = deviceByModbusMap.get(modbusdevicenumber);

                ModelSMASUNNYCENTRALSC1000CP10Entity.setId_device(deviceModelSMASUNNYCENTRALSC1000CP10Entity.getId());
                ModelSMASUNNYCENTRALSC1000CP10Entity.setDatatablename(deviceModelSMASUNNYCENTRALSC1000CP10Entity.getDatatablename());
                ModelSMASUNNYCENTRALSC1000CP10Entity.setView_tablename(deviceModelSMASUNNYCENTRALSC1000CP10Entity.getView_tablename());
                ModelSMASUNNYCENTRALSC1000CP10Entity.setJob_tablename(deviceModelSMASUNNYCENTRALSC1000CP10Entity.getJob_tablename());
                ModelSMASUNNYCENTRALSC1000CP10Entity.setTimezone_value(deviceEntity.getTimezone_value());

                  uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, ModelSMASUNNYCENTRALSC1000CP10Entity);

                  deviceModelSMASUNNYCENTRALSC1000CP10Entity.setLast_value(ModelSMASUNNYCENTRALSC1000CP10Entity.getAC_Active_Power_W() != 0.001 ? ModelSMASUNNYCENTRALSC1000CP10Entity.getAC_Active_Power_W() : null);
                  deviceModelSMASUNNYCENTRALSC1000CP10Entity.setField_value1(ModelSMASUNNYCENTRALSC1000CP10Entity.getAC_Active_Power_W() != 0.001 ? ModelSMASUNNYCENTRALSC1000CP10Entity.getAC_Active_Power_W() : null);

                  uploadFilesService.handleEnergyField(deviceModelSMASUNNYCENTRALSC1000CP10Entity, ModelSMASUNNYCENTRALSC1000CP10Entity, "Total_Yield_kWh");

                  deviceModelSMASUNNYCENTRALSC1000CP10Entity.setLast_updated(ModelSMASUNNYCENTRALSC1000CP10Entity.getTime());

                  boolean insertModelSMASUNNYCENTRALSC1000CP10Result = modelSMASUNNYCENTRALSC1000CP10Service.insertModelSMASUNNYCENTRALSC1000CP10(ModelSMASUNNYCENTRALSC1000CP10Entity);
                  if(insertModelSMASUNNYCENTRALSC1000CP10Result) deviceLastUpdated(deviceModelSMASUNNYCENTRALSC1000CP10Entity);

                  return insertModelSMASUNNYCENTRALSC1000CP10Result;
                  
            case "model_SMA_STRING_COMBINER":
                ModelSMASTRINGCOMBINEREntity ModelSMASTRINGCOMBINEREntity = modelSMASTRINGCOMBINERService.setModelSMASTRINGCOMBINER(telemetryData);
                DeviceEntity deviceModelSMASTRINGCOMBINEREntity = deviceByModbusMap.get(modbusdevicenumber);

                ModelSMASTRINGCOMBINEREntity.setId_device(deviceModelSMASTRINGCOMBINEREntity.getId());
                ModelSMASTRINGCOMBINEREntity.setDatatablename(deviceModelSMASTRINGCOMBINEREntity.getDatatablename());
                ModelSMASTRINGCOMBINEREntity.setView_tablename(deviceModelSMASTRINGCOMBINEREntity.getView_tablename());
                ModelSMASTRINGCOMBINEREntity.setJob_tablename(deviceModelSMASTRINGCOMBINEREntity.getJob_tablename());
                ModelSMASTRINGCOMBINEREntity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, ModelSMASTRINGCOMBINEREntity);

                deviceModelSMASTRINGCOMBINEREntity.setLast_value(ModelSMASTRINGCOMBINEREntity.getDC_Power() != 0.001 ? ModelSMASTRINGCOMBINEREntity.getDC_Power() : null);
                deviceModelSMASTRINGCOMBINEREntity.setField_value1(ModelSMASTRINGCOMBINEREntity.getDC_Power() != 0.001 ? ModelSMASTRINGCOMBINEREntity.getDC_Power() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelSMASTRINGCOMBINEREntity.setLast_updated(ModelSMASTRINGCOMBINEREntity.getTime());

                boolean insertModelSMASTRINGCOMBINERResult = modelSMASTRINGCOMBINERService.insertModelSMASTRINGCOMBINER(ModelSMASTRINGCOMBINEREntity);
                if(insertModelSMASTRINGCOMBINERResult) deviceLastUpdated(deviceModelSMASTRINGCOMBINEREntity);

                return insertModelSMASTRINGCOMBINERResult;
                
                
            case "model_GE_Hi_Winding":
                ModelGEHiWindingEntity ModelGEHiWindingEntity = modelGEHiWindingService.setModelGEHiWinding(telemetryData);
                  DeviceEntity deviceModelGEHiWindingEntity = deviceByModbusMap.get(modbusdevicenumber);

                ModelGEHiWindingEntity.setId_device(deviceModelGEHiWindingEntity.getId());
                ModelGEHiWindingEntity.setDatatablename(deviceModelGEHiWindingEntity.getDatatablename());
                ModelGEHiWindingEntity.setView_tablename(deviceModelGEHiWindingEntity.getView_tablename());
                ModelGEHiWindingEntity.setJob_tablename(deviceModelGEHiWindingEntity.getJob_tablename());
                ModelGEHiWindingEntity.setTimezone_value(deviceEntity.getTimezone_value());

                  uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, ModelGEHiWindingEntity);

                  deviceModelGEHiWindingEntity.setLast_value(ModelGEHiWindingEntity.getP() != 0.001 ? ModelGEHiWindingEntity.getP() : null);
                  deviceModelGEHiWindingEntity.setField_value1(ModelGEHiWindingEntity.getP() != 0.001 ? ModelGEHiWindingEntity.getP() : null);

                  uploadFilesService.handleEnergyField(deviceModelGEHiWindingEntity, ModelGEHiWindingEntity, "Whour_Delivered");

                  deviceModelGEHiWindingEntity.setLast_updated(ModelGEHiWindingEntity.getTime());

                  boolean insertModelGEHiWindingResult = modelGEHiWindingService.insertModelGEHiWinding(ModelGEHiWindingEntity);
                  if(insertModelGEHiWindingResult) deviceLastUpdated(deviceModelGEHiWindingEntity);

                  return insertModelGEHiWindingResult;
                  
                  
            case "model_Schneider_Hi_Winding":
                ModelSchneiderHiWindingEntity ModelSchneiderHiWindingEntity = modelSchneiderHiWindingService.setModelSchneiderHiWinding(telemetryData);
                  DeviceEntity deviceModelSchneiderHiWindingEntity = deviceByModbusMap.get(modbusdevicenumber);

                ModelSchneiderHiWindingEntity.setId_device(deviceModelSchneiderHiWindingEntity.getId());
                ModelSchneiderHiWindingEntity.setDatatablename(deviceModelSchneiderHiWindingEntity.getDatatablename());
                ModelSchneiderHiWindingEntity.setView_tablename(deviceModelSchneiderHiWindingEntity.getView_tablename());
                ModelSchneiderHiWindingEntity.setJob_tablename(deviceModelSchneiderHiWindingEntity.getJob_tablename());
                ModelSchneiderHiWindingEntity.setTimezone_value(deviceEntity.getTimezone_value());

                  uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, ModelSchneiderHiWindingEntity);

                  deviceModelSchneiderHiWindingEntity.setLast_value(ModelSchneiderHiWindingEntity.getP() != 0.001 ? ModelSchneiderHiWindingEntity.getP() : null);
                  deviceModelSchneiderHiWindingEntity.setField_value1(ModelSchneiderHiWindingEntity.getP() != 0.001 ? ModelSchneiderHiWindingEntity.getP() : null);

                  uploadFilesService.handleEnergyField(deviceModelSchneiderHiWindingEntity, ModelSchneiderHiWindingEntity, "Whour_Received");

                  deviceModelSchneiderHiWindingEntity.setLast_updated(ModelSchneiderHiWindingEntity.getTime());

                  boolean insertModelSchneiderHiWindingResult = modelSchneiderHiWindingService.insertModelSchneiderHiWinding(ModelSchneiderHiWindingEntity);
                  if(insertModelSchneiderHiWindingResult) deviceLastUpdated(deviceModelSchneiderHiWindingEntity);

                  return insertModelSchneiderHiWindingResult;
                  
                  
            case "model_IDEC_PLCV2":
                ModelIDECPLCV2Entity ModelIDECPLCV2Entity = modelIDECPLCV2Service.setModelIDECPLCV2(telemetryData);
                DeviceEntity deviceModelIDECPLCV2Entity = deviceByModbusMap.get(modbusdevicenumber);

                ModelIDECPLCV2Entity.setId_device(deviceModelIDECPLCV2Entity.getId());
                ModelIDECPLCV2Entity.setDatatablename(deviceModelIDECPLCV2Entity.getDatatablename());
                ModelIDECPLCV2Entity.setView_tablename(deviceModelIDECPLCV2Entity.getView_tablename());
                ModelIDECPLCV2Entity.setJob_tablename(deviceModelIDECPLCV2Entity.getJob_tablename());
                ModelIDECPLCV2Entity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, ModelIDECPLCV2Entity);

                deviceModelIDECPLCV2Entity.setLast_value(ModelIDECPLCV2Entity.getCmd_Open_SST() != 0.001 ? ModelIDECPLCV2Entity.getCmd_Open_SST() : null);
                deviceModelIDECPLCV2Entity.setField_value1(ModelIDECPLCV2Entity.getCmd_Open_SST() != 0.001 ? ModelIDECPLCV2Entity.getCmd_Open_SST() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelIDECPLCV2Entity.setLast_updated(ModelIDECPLCV2Entity.getTime());

                boolean insertModelIDECPLCV2Result = modelIDECPLCV2Service.insertModelIDECPLCV2(ModelIDECPLCV2Entity);
                if(insertModelIDECPLCV2Result) deviceLastUpdated(deviceModelIDECPLCV2Entity);

                return insertModelIDECPLCV2Result;
                
                
                
            case "model_Main_Weather_Station":
                ModelMainWeatherStationEntity ModelMainWeatherStationEntity = modelMainWeatherStationService.setModelMainWeatherStation(telemetryData);
                DeviceEntity deviceModelMainWeatherStationEntity = deviceByModbusMap.get(modbusdevicenumber);

                ModelMainWeatherStationEntity.setId_device(deviceModelMainWeatherStationEntity.getId());
                ModelMainWeatherStationEntity.setDatatablename(deviceModelMainWeatherStationEntity.getDatatablename());
                ModelMainWeatherStationEntity.setView_tablename(deviceModelMainWeatherStationEntity.getView_tablename());
                ModelMainWeatherStationEntity.setJob_tablename(deviceModelMainWeatherStationEntity.getJob_tablename());
                ModelMainWeatherStationEntity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, ModelMainWeatherStationEntity);

                deviceModelMainWeatherStationEntity.setLast_value(ModelMainWeatherStationEntity.getMain_Pyra_Inclined_Irradiance() != 0.001 ? ModelMainWeatherStationEntity.getMain_Pyra_Inclined_Irradiance() : null);
                deviceModelMainWeatherStationEntity.setField_value1(ModelMainWeatherStationEntity.getMain_Pyra_Inclined_Irradiance() != 0.001 ? ModelMainWeatherStationEntity.getMain_Pyra_Inclined_Irradiance() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelMainWeatherStationEntity.setLast_updated(ModelMainWeatherStationEntity.getTime());

                boolean insertModelMainWeatherStationResult = modelMainWeatherStationService.insertModelMainWeatherStation(ModelMainWeatherStationEntity);
                if(insertModelMainWeatherStationResult) deviceLastUpdated(deviceModelMainWeatherStationEntity);

                return insertModelMainWeatherStationResult;
                
                
            case "model_IDEC_PLCV3":
                ModelIDECPLCV3Entity ModelIDECPLCV3Entity = modelIDECPLCV3Service.setModelIDECPLCV3(telemetryData);
                DeviceEntity deviceModelIDECPLCV3Entity = deviceByModbusMap.get(modbusdevicenumber);

                ModelIDECPLCV3Entity.setId_device(deviceModelIDECPLCV3Entity.getId());
                ModelIDECPLCV3Entity.setDatatablename(deviceModelIDECPLCV3Entity.getDatatablename());
                ModelIDECPLCV3Entity.setView_tablename(deviceModelIDECPLCV3Entity.getView_tablename());
                ModelIDECPLCV3Entity.setJob_tablename(deviceModelIDECPLCV3Entity.getJob_tablename());
                ModelIDECPLCV3Entity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, ModelIDECPLCV3Entity);

                deviceModelIDECPLCV3Entity.setLast_value(ModelIDECPLCV3Entity.getB_RELAY_Stat_1() != 0.001 ? ModelIDECPLCV3Entity.getB_RELAY_Stat_1() : null);
                deviceModelIDECPLCV3Entity.setField_value1(ModelIDECPLCV3Entity.getB_RELAY_Stat_1() != 0.001 ? ModelIDECPLCV3Entity.getB_RELAY_Stat_1() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelIDECPLCV3Entity.setLast_updated(ModelIDECPLCV3Entity.getTime());

                boolean insertModelIDECPLCV3Result = modelIDECPLCV3Service.insertModelIDECPLCV3(ModelIDECPLCV3Entity);
                if(insertModelIDECPLCV3Result) deviceLastUpdated(deviceModelIDECPLCV3Entity);

                return insertModelIDECPLCV3Result;
                
                
            case "model_weather_station_custom":
                ModelWeatherStationCustomEntity ModelWeatherStationCustomEntity = modelWeatherStationCustomService.setModelWeatherStationCustom(telemetryData);
                DeviceEntity deviceModelWeatherStationCustomEntity = deviceByModbusMap.get(modbusdevicenumber);

                ModelWeatherStationCustomEntity.setId_device(deviceModelWeatherStationCustomEntity.getId());
                ModelWeatherStationCustomEntity.setDatatablename(deviceModelWeatherStationCustomEntity.getDatatablename());
                ModelWeatherStationCustomEntity.setView_tablename(deviceModelWeatherStationCustomEntity.getView_tablename());
                ModelWeatherStationCustomEntity.setJob_tablename(deviceModelWeatherStationCustomEntity.getJob_tablename());
                ModelWeatherStationCustomEntity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, ModelWeatherStationCustomEntity);

                deviceModelWeatherStationCustomEntity.setLast_value(ModelWeatherStationCustomEntity.getINCLINED_IRRADIANCE() != 0.001 ? ModelWeatherStationCustomEntity.getINCLINED_IRRADIANCE() : null);
                deviceModelWeatherStationCustomEntity.setField_value1(ModelWeatherStationCustomEntity.getINCLINED_IRRADIANCE() != 0.001 ? ModelWeatherStationCustomEntity.getINCLINED_IRRADIANCE() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelWeatherStationCustomEntity.setLast_updated(ModelWeatherStationCustomEntity.getTime());

                boolean insertModelWeatherStationCustomResult = modelWeatherStationCustomService.insertModelWeatherStationCustom(ModelWeatherStationCustomEntity);
                if(insertModelWeatherStationCustomResult) deviceLastUpdated(deviceModelWeatherStationCustomEntity);

                return insertModelWeatherStationCustomResult;
                
                
            case "model_SUNGROW_SG6250HV_MV_V1":
                ModelSUNGROWSG6250HVMVV1Entity ModelSUNGROWSG6250HVMVV1Entity = modelSUNGROWSG6250HVMVV1Service.setModelSUNGROWSG6250HVMVV1(telemetryData);
                  DeviceEntity deviceModelSUNGROWSG6250HVMVV1Entity = deviceByModbusMap.get(modbusdevicenumber);

                ModelSUNGROWSG6250HVMVV1Entity.setId_device(deviceModelSUNGROWSG6250HVMVV1Entity.getId());
                ModelSUNGROWSG6250HVMVV1Entity.setDatatablename(deviceModelSUNGROWSG6250HVMVV1Entity.getDatatablename());
                ModelSUNGROWSG6250HVMVV1Entity.setView_tablename(deviceModelSUNGROWSG6250HVMVV1Entity.getView_tablename());
                ModelSUNGROWSG6250HVMVV1Entity.setJob_tablename(deviceModelSUNGROWSG6250HVMVV1Entity.getJob_tablename());
                ModelSUNGROWSG6250HVMVV1Entity.setTimezone_value(deviceEntity.getTimezone_value());

                  uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, ModelSUNGROWSG6250HVMVV1Entity);

                  deviceModelSUNGROWSG6250HVMVV1Entity.setLast_value(ModelSUNGROWSG6250HVMVV1Entity.getAC_Active_Power() != 0.001 ? ModelSUNGROWSG6250HVMVV1Entity.getAC_Active_Power() : null);
                  deviceModelSUNGROWSG6250HVMVV1Entity.setField_value1(ModelSUNGROWSG6250HVMVV1Entity.getAC_Active_Power() != 0.001 ? ModelSUNGROWSG6250HVMVV1Entity.getAC_Active_Power() : null);

                  uploadFilesService.handleEnergyField(deviceModelSUNGROWSG6250HVMVV1Entity, ModelSUNGROWSG6250HVMVV1Entity, "Total_Yield");

                  deviceModelSUNGROWSG6250HVMVV1Entity.setLast_updated(ModelSUNGROWSG6250HVMVV1Entity.getTime());

                  boolean insertModelSUNGROWSG6250HVMVV1Result = modelSUNGROWSG6250HVMVV1Service.insertModelSUNGROWSG6250HVMVV1(ModelSUNGROWSG6250HVMVV1Entity);
                  if(insertModelSUNGROWSG6250HVMVV1Result) deviceLastUpdated(deviceModelSUNGROWSG6250HVMVV1Entity);

                  return insertModelSUNGROWSG6250HVMVV1Result;
                  
            case "model_GE_Multilin_EPM_6000":
                ModelGEMultilinEPM6000Entity ModelGEMultilinEPM6000Entity = modelGEMultilinEPM6000Service.setModelGEMultilinEPM6000(telemetryData);
                  DeviceEntity deviceModelGEMultilinEPM6000Entity = deviceByModbusMap.get(modbusdevicenumber);

                ModelGEMultilinEPM6000Entity.setId_device(deviceModelGEMultilinEPM6000Entity.getId());
                ModelGEMultilinEPM6000Entity.setDatatablename(deviceModelGEMultilinEPM6000Entity.getDatatablename());
                ModelGEMultilinEPM6000Entity.setView_tablename(deviceModelGEMultilinEPM6000Entity.getView_tablename());
                ModelGEMultilinEPM6000Entity.setJob_tablename(deviceModelGEMultilinEPM6000Entity.getJob_tablename());
                ModelGEMultilinEPM6000Entity.setTimezone_value(deviceEntity.getTimezone_value());

                  uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, ModelGEMultilinEPM6000Entity);

                  deviceModelGEMultilinEPM6000Entity.setLast_value(ModelGEMultilinEPM6000Entity.getHI_WIND_Active_Power() != 0.001 ? ModelGEMultilinEPM6000Entity.getHI_WIND_Active_Power() : null);
                  deviceModelGEMultilinEPM6000Entity.setField_value1(ModelGEMultilinEPM6000Entity.getHI_WIND_Active_Power() != 0.001 ? ModelGEMultilinEPM6000Entity.getHI_WIND_Active_Power() : null);

                  uploadFilesService.handleEnergyField(deviceModelGEMultilinEPM6000Entity, ModelGEMultilinEPM6000Entity, "HI_WIND_Active_Energy_Del");

                  deviceModelGEMultilinEPM6000Entity.setLast_updated(ModelGEMultilinEPM6000Entity.getTime());

                  boolean insertModelGEMultilinEPM6000Result = modelGEMultilinEPM6000Service.insertModelGEMultilinEPM6000(ModelGEMultilinEPM6000Entity);
                  if(insertModelGEMultilinEPM6000Result) deviceLastUpdated(deviceModelGEMultilinEPM6000Entity);

                  return insertModelGEMultilinEPM6000Result;
                  

            case "model_InaccessPPCV2":
                ModelInaccessPPCV2Entity ModelInaccessPPCV2Entity = modelInaccessPPCV2Service.setModelInaccessPPCV2(telemetryData);
                DeviceEntity deviceModelInaccessPPCV2Entity = deviceByModbusMap.get(modbusdevicenumber);

                ModelInaccessPPCV2Entity.setId_device(deviceModelInaccessPPCV2Entity.getId());
                ModelInaccessPPCV2Entity.setDatatablename(deviceModelInaccessPPCV2Entity.getDatatablename());
                ModelInaccessPPCV2Entity.setView_tablename(deviceModelInaccessPPCV2Entity.getView_tablename());
                ModelInaccessPPCV2Entity.setJob_tablename(deviceModelInaccessPPCV2Entity.getJob_tablename());
                ModelInaccessPPCV2Entity.setTimezone_value(deviceEntity.getTimezone_value());

                uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, ModelInaccessPPCV2Entity);

                deviceModelInaccessPPCV2Entity.setLast_value(ModelInaccessPPCV2Entity.getAI_Power_Factor_Feedback() != 0.001 ? ModelInaccessPPCV2Entity.getAI_Power_Factor_Feedback() : null);
                deviceModelInaccessPPCV2Entity.setField_value1(ModelInaccessPPCV2Entity.getAI_Power_Factor_Feedback() != 0.001 ? ModelInaccessPPCV2Entity.getAI_Power_Factor_Feedback() : null);

//                uploadFilesService.handleEnergyField(deviceModelSMP4DPEntity, modelSMP4DPEntity, "WS_GH_IRRADIANCE");

                deviceModelInaccessPPCV2Entity.setLast_updated(ModelInaccessPPCV2Entity.getTime());

                boolean insertModelInaccessPPCV2Result = modelInaccessPPCV2Service.insertModelInaccessPPCV2(ModelInaccessPPCV2Entity);
                if(insertModelInaccessPPCV2Result) deviceLastUpdated(deviceModelInaccessPPCV2Entity);

                return insertModelInaccessPPCV2Result;
                
                
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
    public void syncData(boolean isFirstRun) throws InterruptedException, UnknownHostException {
//        String hostname = InetAddress.getLocalHost().getHostName();
    	String hostname = getPrivateIP();

        List<String> dataTableNameList = getPostgresTableName(hostname, HOSTNAME_TO_SITE_RUNNING);
        
//        List<String> dataTableNameList = new ArrayList<>();
//        dataTableNameList.add("data673_hw8ulp6oml1jvjxn");

        if(!dataTableNameList.isEmpty()) {
            for(String dataTableName : dataTableNameList) {
                if (runningTables.add(dataTableName)) {
                    tableExecutor.submit(() -> {
                        try {
                            handleData(dataTableName, isFirstRun);
                        } finally {
                            runningTables.remove(dataTableName);
                        }
                    });
                } else {
                    //TO DO: Handle the case when the table is already being processed (e.g., log a warning, skip, or queue for later processing)
                }
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
                                .replace(", ", ",")
                                .trim();

                        if (deviceByModbusMap.containsKey(modbusdevicenumber)) {
                            String deviceTableGroup = deviceByModbusMap.get(modbusdevicenumber).getDevice_group_table();

                            phaser.register();

                            insertExecutor.execute(() -> {
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

    /**
     *@desciption update last value and last updated for device after insert data to mySQL
     * @author Minh Le
     * @date 11-03-2026
     */
    private void deviceLastUpdated(DeviceEntity item) {
        try {
            deviceService.updateLastUpdated(item);
        } catch (Exception e) {
        	log.error(e);
        }
    }
    
    
    /** 
     *@desciption Get private IP
     * @author Long Pham
     * @date 12-03-2026
     */
    
    public static String getPrivateIP() {
        try {
            List<String> candidateIps = new ArrayList<>();

            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();

                String name = ni.getName();

                if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()) {
                    continue;
                }

                // skip container / virtual networks
                if (name.startsWith("docker") || name.startsWith("veth") || name.startsWith("cni")) {
                    continue;
                }

                Enumeration<InetAddress> addresses = ni.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    if (addr instanceof Inet4Address && addr.isSiteLocalAddress()) {

                        String ip = addr.getHostAddress();

                        // ưu tiên interface chính
                        if (name.startsWith("eth") || name.startsWith("ens") || name.startsWith("enp")) {
                            return ip;
                        }

                        candidateIps.add(ip);
                    }
                }
            }

            // fallback
            if (!candidateIps.isEmpty()) {
                return candidateIps.get(0);
            }

        } catch (Exception ignored) {
        }

        return null;
    }
    
}
