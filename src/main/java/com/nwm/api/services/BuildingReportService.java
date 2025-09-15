/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AuditLog;
import com.nwm.api.entities.BuildingReportDateEntity;
import com.nwm.api.entities.BuildingReportEntity;
import com.nwm.api.entities.BuildingReportWeatherEntity;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.DateTimeReportDataEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.EmployeeSiteMapEntity;
import com.nwm.api.entities.SiteAreaBuildingFloorRoomEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SiteGasWaterElectricityRateScheduleEntity;
import com.nwm.api.entities.SiteLogs;
import com.nwm.api.entities.ZoneGraphDateEntity;
import com.nwm.api.utils.Lib;

public class BuildingReportService extends DB {
	/**
	 * @description get data building report
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 */
	public BuildingReportEntity getDataBuildingReport(BuildingReportEntity obj) {
		try {
			// Get device by id_site
			List devices = queryForList("BuildingReport.getListDeviceBySite", obj);
			if(devices.size() > 0) {
				List<Object> electrics = new ArrayList<>();
				List<Object> gas = new ArrayList<>();
				List<Object> pvProduction = new ArrayList<>();
				List<Object> waters = new ArrayList<>();
				List<Object> weather = new ArrayList<>();
				
				for (int j = 0; j < devices.size(); j++) {
					Map<String, Object> item = (Map<String, Object>) devices.get(j);
					int meterType = Integer.parseInt(item.get("meter_type").toString());
					int idDeviceType = Integer.parseInt(item.get("id_device_type").toString());
					if(idDeviceType == 4) {
						weather.add(item);
					}
					
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
				
					List dataPVStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
					obj.setDataPVStatistics(dataPVStatistics);
				}
				
				if(gas.size() > 0) {
					obj.setDevices(gas);
					BuildingReportEntity dataGas = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataGas != null) {
						obj.setGas_current_month(dataGas.getCurrent_month());
						obj.setGas_compare_current_month(dataGas.getCompare_current_month());
					}
					
					List dataGasStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
					obj.setDataGasStatistics(dataGasStatistics);
					
				}
				
				if(waters.size() > 0) {
					obj.setDevices(waters);
					BuildingReportEntity dataWater = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataWater != null) {
						obj.setWater_current_month(dataWater.getCurrent_month());
						obj.setWater_compare_current_month(dataWater.getCompare_current_month());
					}
					
					List dataWaterStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
					obj.setDataWaterStatistics(dataWaterStatistics);
					
				}
				if(electrics.size() > 0) {
					obj.setDevices(electrics);
					BuildingReportEntity dataElectric = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataElectric != null) {
						obj.setElectric_current_month(dataElectric.getCurrent_month());
						obj.setElectric_compare_current_month(dataElectric.getCompare_current_month());
					}
					
					List dataElectricStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
					obj.setDataElectricStatistics(dataElectricStatistics);
				}
				
				
//				if(weather.size() > 0) {
//					obj.setDevices(weather);
//					List dataWeatherCurrentMonth = queryForList("BuildingReport.getDataWeatherStation", obj);
//					obj.setDataWeatherCurrentMonth(dataWeatherCurrentMonth);
//					
//					List dataWeatherLastMonth = queryForList("BuildingReport.getDataWeatherStationLastMonth", obj);
//					obj.setDataWeatherComapreMonth(dataWeatherLastMonth);
//				}
				
				
			}
			
			return obj;
		} catch (Exception ex) {
			return new BuildingReportEntity();
		}
	}
	

	
	/**
	 * @description get data building report
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 */
	public BuildingReportEntity getDataWeatherStationReport(BuildingReportEntity obj) {
		try {
			// Get device by id_site
			List devices = queryForList("BuildingReport.getListDeviceBySite", obj);
			if(devices.size() > 0) {
//				List<Object> electrics = new ArrayList<>();
//				List<Object> gas = new ArrayList<>();
//				List<Object> pvProduction = new ArrayList<>();
//				List<Object> waters = new ArrayList<>();
				List<Object> weather = new ArrayList<>();
				
				for (int j = 0; j < devices.size(); j++) {
					Map<String, Object> item = (Map<String, Object>) devices.get(j);
					int meterType = Integer.parseInt(item.get("meter_type").toString());
					int idDeviceType = Integer.parseInt(item.get("id_device_type").toString());
					if(idDeviceType == 4) {
						weather.add(item);
					}
					
//					switch (meterType) {
//				        case 3:
//				        	pvProduction.add(item);
//				            break;
//				        case 4:
//				        	electrics.add(item);
//				            break;
//				        case 5:
//				        	waters.add(item);
//				            break;
//				        case 7:
//				        	gas.add(item);
//				            break;
//				    }
				}
				
//				if(pvProduction.size() > 0) {
//					obj.setDevices(pvProduction);
//					BuildingReportEntity dataPV = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
//					if(dataPV != null) {
//						obj.setPv_current_month(dataPV.getCurrent_month());
//						obj.setPv_compare_current_month(dataPV.getCompare_current_month());
//					}
//				
//					List dataPVStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
//					obj.setDataPVStatistics(dataPVStatistics);
//				}
//				
//				if(gas.size() > 0) {
//					obj.setDevices(gas);
//					BuildingReportEntity dataGas = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
//					if(dataGas != null) {
//						obj.setGas_current_month(dataGas.getCurrent_month());
//						obj.setGas_compare_current_month(dataGas.getCompare_current_month());
//					}
//					
//					List dataGasStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
//					obj.setDataGasStatistics(dataGasStatistics);
//					
//				}
				
//				if(waters.size() > 0) {
//					obj.setDevices(waters);
//					BuildingReportEntity dataWater = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
//					if(dataWater != null) {
//						obj.setWater_current_month(dataWater.getCurrent_month());
//						obj.setWater_compare_current_month(dataWater.getCompare_current_month());
//					}
//					
//					List dataWaterStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
//					obj.setDataWaterStatistics(dataWaterStatistics);
//					
//				}
//				if(electrics.size() > 0) {
//					obj.setDevices(electrics);
//					BuildingReportEntity dataElectric = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
//					if(dataElectric != null) {
//						obj.setElectric_current_month(dataElectric.getCurrent_month());
//						obj.setElectric_compare_current_month(dataElectric.getCompare_current_month());
//					}
//					
//					List dataElectricStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
//					obj.setDataElectricStatistics(dataElectricStatistics);
//				}
				
				int interval = 1;
				DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00");
				DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00");
				DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00");
				
				ChronoUnit timeUnit = ChronoUnit.HOURS;
				LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				
				List<BuildingReportDateEntity> dateTimeList = new ArrayList<>();
				while (!start.isAfter(end)) {
					BuildingReportDateEntity dateTime = new BuildingReportDateEntity();
					dateTime.setTime_full(start.format(timeFullFormat));
					dateTime.setCategories_time(start.format(categoriesTimeFormat));
					dateTime.setTime_format(start.format(timeFormat));
					dateTimeList.add(dateTime);
					start = start.plus(interval, timeUnit);
				}
				
				
				
				if(weather.size() > 0) {
					obj.setDevices(weather);
					List dataWeatherCurrentMonth = queryForList("BuildingReport.getDataWeatherStation", obj);
					List<BuildingReportDateEntity> fillData = Lib.fulfillData(dateTimeList, dataWeatherCurrentMonth, "time_full");
					
					obj.setDataWeatherCurrentMonth(fillData);
					
					List dataWeatherLastMonth = queryForList("BuildingReport.getDataWeatherStationLastMonth", obj);
					obj.setDataWeatherComapreMonth(dataWeatherLastMonth);
				}
				
				
			}
			
			return obj;
		} catch (Exception ex) {
			return new BuildingReportEntity();
		}
	}
	
	
	/**
	 * @description get data building report by type
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 */
	
	
	public BuildingReportEntity getDataBuildingReportByType(BuildingReportEntity obj) {
		try {
			// Get device by id_site
			List devices = queryForList("BuildingReport.getListDeviceBySite", obj);
			if(devices.size() > 0) {
				List<Object> electrics = new ArrayList<>();
				List<Object> gas = new ArrayList<>();
				List<Object> pvProduction = new ArrayList<>();
				List<Object> waters = new ArrayList<>();
				
				for (int j = 0; j < devices.size(); j++) {
					Map<String, Object> item = (Map<String, Object>) devices.get(j);
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
				
				switch (obj.getMeter_type()) {
			        case 3:
			        	obj.setDevices(pvProduction);
			            break;
			        case 4:
			        	obj.setDevices(electrics);
			            break;
			        case 5:
			        	obj.setDevices(waters);
			            break;
			        case 7:
			        	obj.setDevices(gas);
			            break;
			    }
				
				
				int interval = 1;
				DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MMM d, yyyy");
				
				ChronoUnit timeUnit = ChronoUnit.DAYS;
				LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				
				List<BuildingReportDateEntity> dateTimeList = new ArrayList<>();
				while (!start.isAfter(end)) {
					BuildingReportDateEntity dateTime = new BuildingReportDateEntity();
					dateTime.setTime_full(start.format(timeFullFormat));
					dateTime.setCategories_time(start.format(categoriesTimeFormat));
					dateTime.setTime_format(start.format(timeFormat));
					dateTimeList.add(dateTime);
					start = start.plus(interval, timeUnit);
				}
				
				
				// Build data time expected
				LocalDateTime startExpected = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				startExpected = startExpected.plus(-1, ChronoUnit.YEARS);
				LocalDateTime endExpected = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				endExpected = endExpected.plus(-1, ChronoUnit.YEARS);
				List<BuildingReportDateEntity> dateTimeListExpected = new ArrayList<>();
				while (!startExpected.isAfter(endExpected)) {
					BuildingReportDateEntity dateTimeExpected = new BuildingReportDateEntity();
					dateTimeExpected.setTime_full(startExpected.format(timeFullFormat));
					dateTimeExpected.setCategories_time(startExpected.format(categoriesTimeFormat));
					dateTimeExpected.setTime_format(startExpected.format(timeFormat));
					dateTimeListExpected.add(dateTimeExpected);
					startExpected = startExpected.plus(interval, timeUnit);
				}
				
				if(obj.getDevices().size() > 0) {
					List<BuildingReportDateEntity> dataDaily = new ArrayList<>();
					dataDaily = queryForList("BuildingReport.getDataReportDailyByType", obj);
					List<BuildingReportDateEntity> fillDataDaily = Lib.fulfillData(dateTimeList, dataDaily, "time_full");
					obj.setDataDaily(fillDataDaily);
					
					List<BuildingReportDateEntity> dataDailyExpected = queryForList("BuildingReport.getDataReportDailyExpectedByType", obj);
					List<BuildingReportDateEntity> fillDataExpected = Lib.fulfillData(dateTimeList, dataDailyExpected, "time_full");
					obj.setDataDailyExpected(fillDataExpected);
				}
				
				
				// Usage History
				int intervalHistory = 1;
				DateTimeFormatter timeFullFormatHistory = DateTimeFormatter.ofPattern("yyyy-MM");
				DateTimeFormatter categoriesTimeFormatHistory = DateTimeFormatter.ofPattern("MMM yyyy");
				DateTimeFormatter timeFormatHistory = DateTimeFormatter.ofPattern("MMM yyyy");
				
				ChronoUnit timeUnitHistory = ChronoUnit.MONTHS;
				LocalDateTime startHistory = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				startHistory = startHistory.plus(-11, ChronoUnit.MONTHS);
				LocalDateTime endHistory = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				
				List<BuildingReportDateEntity> dateTimeListHistory = new ArrayList<>();
				while (!startHistory.isAfter(endHistory)) {
					BuildingReportDateEntity dateTimeHistory = new BuildingReportDateEntity();
					dateTimeHistory.setTime_full(startHistory.format(timeFullFormatHistory));
					dateTimeHistory.setCategories_time(startHistory.format(categoriesTimeFormatHistory));
					dateTimeHistory.setTime_format(startHistory.format(timeFormatHistory));
					dateTimeListHistory.add(dateTimeHistory);
					startHistory = startHistory.plus(intervalHistory, timeUnitHistory);
				}
				
				if(obj.getDevices().size() > 0) {
					List<BuildingReportDateEntity>	dataHistory = queryForList("BuildingReport.getDataReportHistory", obj);
					List<BuildingReportDateEntity> fillDataHistory = Lib.fulfillData(dateTimeListHistory, dataHistory, "time_full");
					obj.setDataHistory(fillDataHistory);
				}
				
				
				BuildingReportEntity dataPeakDemand = (BuildingReportEntity) queryForObject("BuildingReport.getDataPeakDemand", obj);
				if(dataPeakDemand != null) {
					obj.setPeak_demand(dataPeakDemand.getPeak_demand());
					obj.setPeak_demand_date(dataPeakDemand.getPeak_demand_date());
				}
				
				BuildingReportEntity dataLastMonth = (BuildingReportEntity) queryForObject("BuildingReport.getDataLastMonth", obj);
				if(dataLastMonth != null) {
					obj.setLastMonth(dataLastMonth.getLastMonth());
				}
				
				BuildingReportEntity dataCompare3Month = (BuildingReportEntity) queryForObject("BuildingReport.getDataCompare3Month", obj);
				if(dataLastMonth != null) {
					obj.setAvg3Month(dataCompare3Month.getAvg3Month());
				}
				
				
				
				
				
			}
			
			return obj;
		} catch (Exception ex) {
			return new BuildingReportEntity();
		}
	}
	
	
	
//	private List<ClientMonthlyDateEntity> convertDateTimeFormat(SiteEntity obj, List<ClientMonthlyDateEntity> dataList, LocalDateTime start, LocalDateTime end) {
//		try {
//			DeviceEntity chartParams = new DeviceEntity();
//			chartParams.setData_send_time(obj.getData_send_time());
//			chartParams.setFilterBy(obj.getFilterBy());
//			chartParams.setDate_format(obj.getDate_format());
//			chartParams.setTime_format(obj.getTime_format());
//			chartParams.setLocale(obj.getLocale());
//			
//			List<Map<String, Object>> data = dataList
//					.stream()
//					.map(item -> ClientMonthlyDateEntity.convertDateTimeToMap(item))
//					.collect(Collectors.toList());
//			
//			List<ClientMonthlyDateEntity> convertedDateTimeList = sitesAnalyticsService.convertDateTimeFormat(chartParams, data, start, end)
//					.stream()
//					.map(item -> ClientMonthlyDateEntity.convertDateTimeToEntity(item))
//					.collect(Collectors.toList());
//			
//			for (int i = 0; i < dataList.size(); i++) {
//				ClientMonthlyDateEntity item = dataList.get(i);
//				ClientMonthlyDateEntity convertedItem = convertedDateTimeList.get(i);
//				item.setTime_full(convertedItem.getTime_full());
//				item.setCategories_time(convertedItem.getCategories_time());
//			}
//		} catch (Exception e) {
//		}
//		
//		return dataList;
//	}
	
	
}
