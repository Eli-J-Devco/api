/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.ibatis.session.SqlSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AuditLog;
import com.nwm.api.entities.BuildingReportEntity;
import com.nwm.api.entities.EmployeeSiteMapEntity;
import com.nwm.api.entities.SiteAreaBuildingFloorRoomEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SiteGasWaterElectricityRateScheduleEntity;
import com.nwm.api.entities.SiteLogs;

public class BuildingReportService extends DB {
	
	
	/**
	 * @description get data building report
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 */
	
	
	public BuildingReportEntity getDataBuildingReport(BuildingReportEntity obj) {
//		BuildingReportEntity dataObj = null;
		try {
			// Get dedvice by id_site
			List devices = queryForList("BuildingReport.getListDeviceBySite", obj);
			if(devices.size() > 0) {
				List<Object> electrics = new ArrayList<>();
				List<Object> gas = new ArrayList<>();
				List<Object> pvProduction = new ArrayList<>();
				List<Object> waters = new ArrayList<>();
				
//				boolean hasInverter = jsonArray.stream().filter(item -> Integer.parseInt(item.get("id_device_type").toString()) == 1).findFirst().isPresent();
				
				for (int j = 0; j < devices.size(); j++) {
					Map<String, Object> item = (Map<String, Object>) devices.get(j);
//					Double comparison_ratio = device.get("comparison_ratio") == null ? null : Double.parseDouble(device.get("comparison_ratio").toString()) ;
					int meterType = Integer.parseInt(item.get("meter_type").toString());
					
					switch (meterType) {
				        case 3:
				        	pvProduction.add(item);
				            break;
				        case 4:
				        	electrics.add(item);
				            break;
				        case 5:
				        	waters.add(item);
				            break;
				        case 7:
				        	gas.add(item);
				            break;
				    }
				}
				
				if(pvProduction.size() > 0) {
					obj.setDevices(pvProduction);
					BuildingReportEntity dataPV = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataPV != null) {
						obj.setPv_current_month(dataPV.getCurrent_month());
						obj.setPv_compare_current_month(dataPV.getCompare_current_month());
					}
				}
				
				if(gas.size() > 0) {
					obj.setDevices(gas);
					BuildingReportEntity dataGas = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataGas != null) {
						obj.setGas_current_month(dataGas.getCurrent_month());
						obj.setGas_compare_current_month(dataGas.getCompare_current_month());
					}
					
				}
				
				if(waters.size() > 0) {
					obj.setDevices(waters);
					BuildingReportEntity dataWater = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataWater != null) {
						obj.setWater_current_month(dataWater.getCurrent_month());
						obj.setWater_compare_current_month(dataWater.getCompare_current_month());
					}
					
				}
				if(electrics.size() > 0) {
					obj.setDevices(electrics);
					BuildingReportEntity dataElectric = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataElectric != null) {
						obj.setElectric_current_month(dataElectric.getCurrent_month());
						obj.setElectric_compare_current_month(dataElectric.getCompare_current_month());
					}
				}
				
				
			}
			
			
//			 dataObj = (BuildingReportEntity) queryForObject("BuildingReport.getDataBuildingReport", obj);
			return obj;
		} catch (Exception ex) {
			return new BuildingReportEntity();
		}
	}
	
	
	
	
	
	
	
}
