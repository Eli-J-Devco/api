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
						obj.setPv_year_over_year(dataPV.getYear_over_year());
						obj.setPv_year_over_date(dataPV.getYear_over_date());
					}
					
					BuildingReportEntity pvPeakFlowRate = (BuildingReportEntity) queryForObject("BuildingReport.getDataPeakFlowRate", obj);
					if(pvPeakFlowRate != null) {
						obj.setPv_peak_flow_rate(pvPeakFlowRate.getPeak_flow_rate());
						obj.setPv_peak_flow_rate_date(pvPeakFlowRate.getPeak_flow_rate_date());
					}
					
				}
				
				if(gas.size() > 0) {
					obj.setDevices(gas);
					BuildingReportEntity dataGas = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataGas != null) {
						obj.setGas_current_month(dataGas.getCurrent_month());
						obj.setGas_compare_current_month(dataGas.getCompare_current_month());
						obj.setGas_year_over_year(dataGas.getYear_over_year());
						obj.setGas_year_over_date(dataGas.getYear_over_date());
					}
					
					BuildingReportEntity gasPeakFlowRate = (BuildingReportEntity) queryForObject("BuildingReport.getDataPeakFlowRate", obj);
					if(gasPeakFlowRate != null) {
						obj.setGas_peak_flow_rate(gasPeakFlowRate.getPeak_flow_rate());
						obj.setGas_peak_flow_rate_date(gasPeakFlowRate.getPeak_flow_rate_date());
					}
					
					
				}
				
				if(waters.size() > 0) {
					obj.setDevices(waters);
					BuildingReportEntity dataWater = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataWater != null) {
						obj.setWater_current_month(dataWater.getCurrent_month());
						obj.setWater_compare_current_month(dataWater.getCompare_current_month());
						obj.setWater_year_over_year(dataWater.getYear_over_year());
						obj.setWater_year_over_date(dataWater.getYear_over_date());
					}
					
					BuildingReportEntity waterPeakFlowRate = (BuildingReportEntity) queryForObject("BuildingReport.getDataPeakFlowRate", obj);
					if(waterPeakFlowRate != null) {
						obj.setWater_peak_flow_rate(waterPeakFlowRate.getPeak_flow_rate());
						obj.setWater_peak_flow_rate_date(waterPeakFlowRate.getPeak_flow_rate_date());
					}
					
				}
				if(electrics.size() > 0) {
					obj.setDevices(electrics);
					BuildingReportEntity dataElectric = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataElectric != null) {
						obj.setElectric_current_month(dataElectric.getCurrent_month());
						obj.setElectric_compare_current_month(dataElectric.getCompare_current_month());
						obj.setElectric_year_over_year(dataElectric.getYear_over_year());
						obj.setElectric_year_over_date(dataElectric.getYear_over_date());
					}
					
					BuildingReportEntity electricPeakFlowRate = (BuildingReportEntity) queryForObject("BuildingReport.getDataPeakFlowRate", obj);
					if(electricPeakFlowRate != null) {
						obj.setElectric_peak_flow_rate(electricPeakFlowRate.getPeak_flow_rate());
						obj.setElectric_peak_flow_rate_date(electricPeakFlowRate.getPeak_flow_rate_date());
					}
					
				}

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
	public BuildingReportEntity getDataCategoryStatisticsReport(BuildingReportEntity obj) {
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
					List dataPVStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
					obj.setDataPVStatistics(dataPVStatistics);
				}
				
				if(gas.size() > 0) {
					obj.setDevices(gas);
					List dataGasStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
					obj.setDataGasStatistics(dataGasStatistics);
					
				}
				
				if(waters.size() > 0) {
					obj.setDevices(waters);
					List dataWaterStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
					obj.setDataWaterStatistics(dataWaterStatistics);
					
				}
				if(electrics.size() > 0) {
					obj.setDevices(electrics);
					List dataElectricStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
					obj.setDataElectricStatistics(dataElectricStatistics);
				}

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
				List<Object> weather = new ArrayList<>();
				
				for (int j = 0; j < devices.size(); j++) {
					Map<String, Object> item = (Map<String, Object>) devices.get(j);
					int meterType = Integer.parseInt(item.get("meter_type").toString());
					int idDeviceType = Integer.parseInt(item.get("id_device_type").toString());
					if(idDeviceType == 4) {
						weather.add(item);
					}
				}
				
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
				DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
				
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
					List<BuildingReportDateEntity> fillDataExpected = Lib.fulfillData(dateTimeListExpected, dataDailyExpected, "time_full");
					obj.setDataDailyExpected(fillDataExpected);
				}
				
				
				// get data History
				int intervalHistory = 1;
				DateTimeFormatter timeFullFormatHistory = DateTimeFormatter.ofPattern("yyyy-MM");
				DateTimeFormatter categoriesTimeFormatHistory = DateTimeFormatter.ofPattern("MMM yyyy");
				DateTimeFormatter timeFormatHistory = DateTimeFormatter.ofPattern("yyyy-MM");
				DateTimeFormatter timeDateFormatHistory = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				
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
					dateTimeHistory.setStart_date(startHistory.format(timeDateFormatHistory));
					
					startHistory = startHistory.plus(intervalHistory, timeUnitHistory);
					dateTimeHistory.setEnd_date(startHistory.format(timeDateFormatHistory));
					dateTimeListHistory.add(dateTimeHistory);
				}
				
				// Get data history expected 
				// get data History
				int intervalEx = 1;
				DateTimeFormatter timeFullFormatEx = DateTimeFormatter.ofPattern("yyyy-MM");
				DateTimeFormatter categoriesTimeFormatEx = DateTimeFormatter.ofPattern("MMM yyyy");
				DateTimeFormatter timeFormatEx = DateTimeFormatter.ofPattern("yyyy-MM");
				DateTimeFormatter timeDateFormatEx = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				
				ChronoUnit timeUnitEx = ChronoUnit.MONTHS;
				LocalDateTime startEx = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				startEx = startEx.plus(-2, ChronoUnit.YEARS);
				startEx = startEx.plus(1, ChronoUnit.MONTHS);
				LocalDateTime endEx = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				endEx = endEx.plus(-1, ChronoUnit.YEARS);
				
				List<BuildingReportDateEntity> dateTimeListEx = new ArrayList<>();
				while (!startEx.isAfter(endEx)) {
					BuildingReportDateEntity dateTimeEx = new BuildingReportDateEntity();
					dateTimeEx.setTime_full(startEx.format(timeFullFormatEx));
					dateTimeEx.setCategories_time(startEx.format(categoriesTimeFormatEx));
					dateTimeEx.setTime_format(startEx.format(timeFormatEx));
					dateTimeEx.setStart_date(startEx.format(timeDateFormatEx));
					
					startEx = startEx.plus(intervalEx, timeUnitEx);
					dateTimeEx.setEnd_date(startEx.format(timeDateFormatEx));
					dateTimeListEx.add(dateTimeEx);
				}
				
				
				
				if(obj.getDevices().size() > 0) {
					obj.setDateTimeList(dateTimeListHistory);
					List<BuildingReportDateEntity>	dataHistory = queryForList("BuildingReport.getDataReportHistory", obj);
					List<BuildingReportDateEntity> fillDataHistory = Lib.fulfillData(dateTimeListHistory, dataHistory, "time_full");
					obj.setDataHistory(fillDataHistory);
					
					// Get data history expected
					obj.setDateTimeList(dateTimeListEx);
					List<BuildingReportDateEntity>	dataEx = queryForList("BuildingReport.getDataReportHistory", obj);
					List<BuildingReportDateEntity> fillDataEx = Lib.fulfillData(dateTimeListEx, dataEx, "time_full");
					obj.setDataHistoryExpected(fillDataEx);
					
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
				
				BuildingReportEntity dataMaxAnnualDaily = (BuildingReportEntity) queryForObject("BuildingReport.getDataMaxAnnualDaily", obj);
				if(dataMaxAnnualDaily != null) {
					obj.setMax_annual_daily(dataMaxAnnualDaily.getMax_annual_daily());
					obj.setMax_annual_daily_date(dataMaxAnnualDaily.getMax_annual_daily_date());
					obj.setLast_year(dataMaxAnnualDaily.getLast_year());
					obj.setAvg_last_eriod(dataMaxAnnualDaily.getAvg_last_eriod());
				}
				
				
				
			}
			
			return obj;
		} catch (Exception ex) {
			return new BuildingReportEntity();
		}
	}
	
	
}
