/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BuildingReportDateEntity;
import com.nwm.api.entities.BuildingReportEntity;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.DevicePanelEntity;
import com.nwm.api.entities.DeviceZoneEntity;
import com.nwm.api.entities.SiteDashboardGenerationEntity;
import com.nwm.api.entities.SiteEnergyFlowEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.entities.ZoneGraphDateEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SecretCards;

public class SitesDashboardService extends DB {


	
	/**
	 * @description get list device by id site
	 * @author long.pham
	 * @since 2022-03-04
	 * @param id_site
	 * @return Object
	 */
	
	public List getListAlertByIdDevice(AlertEntity obj) {
		List dataList, dataListNew = new ArrayList();
		SecretCards secretCard = new SecretCards();
		try {
			dataList = queryForList("SitesDashboard.getListAlertByIdDevice", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	/**
	 * @description get list panel by id_device
	 * @author long.pham
	 * @since 2025-02-05
	 * @param id_device
	 * @return Object
	 */
	
	public List getListPanel(SitesDevicesEntity obj) {
		try {
			List dataList = queryForList("SitesDashboard.getListPanel", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	
	
	/**
	 * @description get list panel by id_device
	 * @author long.pham
	 * @since 2025-02-05
	 * @param id_device
	 * @return Object
	 */
	
	public List getListBreakerAlerts(SitesDevicesEntity obj) {
		try {
			List dataList = queryForList("SitesDashboard.getListBreakerAlerts", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	
	
	/**
	 * @description get zones alert
	 * @author long.pham
	 * @since 2025-02-05
	 * @param {}
	 * @return Object
	 */
	
	public List getListZonesAlerts(SitesDevicesEntity obj) {
		try {
			List dataList = queryForList("SitesDashboard.getListZonesAlerts", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	/**
	 * @description get list panel by id_device
	 * @author long.pham
	 * @since 2025-02-05
	 * @param id_device
	 * @return Object
	 */
	
	public List getListZones(SitesDevicesEntity obj) {
		try {
			List dataList = queryForList("SitesDashboard.getListZones", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	
	/**
	 * @description get list zone graph by id_device
	 * @author long.pham
	 * @since 2025-02-05
	 * @param id_device
	 * @return Object
	 */
	
	public List getListZonesGraph(SitesDevicesEntity obj) {
		try {
			List dataList = queryForList("SitesDashboard.getListZones", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	/**
	 * @description get list data lighting graph by id_device
	 * @author long.pham
	 * @since 2025-02-05
	 * @param id_device
	 * @return Object
	 */
	
	public List getListDataLightingGraph(SitesDevicesEntity obj) {
		try {
			List<ZoneGraphDateEntity> dataList = new ArrayList<>();
			
			dataList = queryForList("SitesDashboard.getListDataLightingGraph", obj);
			// ----- Create DateTime List ----- Begin
			int interval = 0;
			DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
			DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("hh:mm a");
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
			
			
			ChronoUnit timeUnit = ChronoUnit.MINUTES;
			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
			switch (obj.getData_send_time()) {
				case 1: // 5 minutes
					interval = 5;
					timeUnit = ChronoUnit.MINUTES;
					timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
					categoriesTimeFormat = DateTimeFormatter.ofPattern("hh:mm a");
					timeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
					break;
					
				case 2: // 15 minutes
					interval = 15;
					timeUnit = ChronoUnit.MINUTES;
					timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
					categoriesTimeFormat = DateTimeFormatter.ofPattern("hh:mm a");
					timeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
					break;
			}
			
			List<ZoneGraphDateEntity> dateTimeList = new ArrayList<>();
			while (!start.isAfter(end)) {
				ZoneGraphDateEntity dateTime = new ZoneGraphDateEntity();
				dateTime.setTime_full(start.format(timeFullFormat));
				dateTime.setCategories_time(start.format(categoriesTimeFormat));
				dateTime.setTime_format(start.format(timeFormat));
				dateTimeList.add(dateTime);
				start = start.plus(interval, timeUnit);
			}
			
			List<ZoneGraphDateEntity> fulfilledData = Lib.fulfillData(dateTimeList, dataList, "time_full");
			return fulfilledData;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description get list bit map by id_panel
	 * @author long.pham
	 * @since 2025-02-05
	 * @param id_device
	 * @return Object
	 */
	
	public List getListDataBitMap(DevicePanelEntity obj) {
		try {
			List dataList = queryForList("SitesDashboard.getListDataBitMap", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	
	/**
	 * @description get list breaker unresponsive
	 * @author long.pham
	 * @since 2025-02-05
	 * @param id_device
	 * @return Object
	 */
	
	public List getListBreakerUnresponsive(DevicePanelEntity obj) {
		try {
			List dataList = queryForList("SitesDashboard.getListBreakerUnresponsive", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	
	/**
	 * @description get list bit map by id_panel
	 * @author long.pham
	 * @since 2025-02-05
	 * @param id_device
	 * @return Object
	 */
	
	public List getListDataZoneBitMap(DeviceZoneEntity obj) {
		try {
			List dataList = queryForList("SitesDashboard.getListDataZoneBitMap", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description get data 7days
	 * @author long.pham
	 * @since 2022-03-04
	 * @param id_site
	 * @return Object
	 */
	
	public List getData7Days(SitesDevicesEntity obj) {
		try {
			List dataDevices = queryForList("SitesDashboard.getListDeviceByMeterType", obj);
			
			if (dataDevices.size() > 0) {
				obj.setList_device(dataDevices);
				
				int interval = 1;
				DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("E MMM dd, yyyy");
				DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("E MMM dd, yyyy");
				
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
				
				List dataWeatherCurrentMonth = queryForList("SitesDashboard.getData7Days", obj);
				List<BuildingReportDateEntity> fillData = Lib.fulfillData(dateTimeList, dataWeatherCurrentMonth, "time_full");
				
				return fillData;
			} else {
				return new ArrayList();
			}
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	/**
	 * @description get list device by id site
	 * @author long.pham
	 * @since 2022-03-04
	 * @param id_site
	 * @return Object
	 */
	
	public List getListDeviceByIdSite(SitesDevicesEntity obj) {
		try {
			List dataList = queryForList("SitesDevices.getListDeviceByIdSite", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	
	/**
	 * @description get customer view site info
	 * @author long.pham
	 * @since 2022-03-04
	 * @param id_site
	 * @return Object
	 */

	public Object getGeneration(SiteDashboardGenerationEntity obj) {
		Object dataObj = null;
		try {
			dataObj = queryForObject("SitesDashboard.getGeneration", obj);
			
//			List dataMeter =  queryForList("CustomerView.getListDeviceTypeMeter", obj);
//			if(dataMeter.size() > 0) {
//				// Get data by meter
//				obj.setGroupMeter(dataMeter);
//				dataObj = queryForObject("SitesDashboard.getGeneration", obj);
//			} else {
//				// get data by inverter 
//				List dataInverter = queryForList("CustomerView.getListDeviceTypeInverter", obj);
//				if(dataInverter.size() > 0) {
//					obj.setGroupMeter(dataInverter);
//					dataObj = queryForObject("SitesDashboard.getGenerationInverter", obj);
//				}
//			}
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @description Get device status list by site
	 * @author Hung.Bui
	 * @since 2023-05-05
	 * @param id_site
	 * @return Object
	 */
	
	public List getDeviceStatusListBySite(SitesDevicesEntity obj) {
		List dataList = new ArrayList();
		List<SitesDevicesEntity> deviceTableList = new ArrayList();
		
		try {
			deviceTableList = queryForList("SitesDashboard.getDeviceTableListBySite", obj);
			if (deviceTableList.size() > 0)  {
				for (SitesDevicesEntity itemDeviceTable : deviceTableList) {
					Map<String, Object> itemData = (Map<String, Object>) queryForObject("SitesDashboard.getDeviceStatus", itemDeviceTable);
					if (itemData != null) {
						dataList.add(itemData);
					}
				}
			}
			
			return dataList;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description get list widget site overview for leviton
	 * @author long.pham
	 * @since 2024-07-09
	 * @param id_site
	 * @return Object
	 */
	
	public List getListDataDeviceForLeviton(SitesDevicesEntity obj) {
		try {
			List listWidgetOverview = queryForList("SitesDashboard.getListWidgetOverviewLeviton", obj);
			return listWidgetOverview;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	
	/**
	 * @description get list data charting site overview for leviton
	 * @author long.pham
	 * @since 2024-07-09
	 * @param id_site
	 * @return Object
	 */
	
	public List getListDataChartingForLeviton(SitesDevicesEntity obj) {
		List dataList = new ArrayList();
		try {
			List listWidgetOverview = queryForList("SitesDashboard.getListWidgetEnergyUsage", obj);
			if (listWidgetOverview.size() > 0) {
				for (int i = 0; i < listWidgetOverview.size(); i++) {
					// get data device in widget 
					Map<String, Object> itemWidget = (Map<String, Object>) listWidgetOverview.get(i);
					List listWidget = queryForList("SitesDashboard.getListDeviceInWidget", itemWidget);
					itemWidget.put("devices", listWidget);
					itemWidget.put("start_date", obj.getStart_date());
					itemWidget.put("end_date", obj.getEnd_date());
					itemWidget.put("id_filter", obj.getId_filter());
                    itemWidget.put("id_time_filter", obj.getId_time_filter());
					
					if(listWidget.size() > 0) {
						
						int interval = 0;
						DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
						DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm a");
						ChronoUnit timeUnit = ChronoUnit.MINUTES;
						LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
						LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
						// get Energy usage 
						List<ClientMonthlyDateEntity> data = new ArrayList<>();
						
						
						switch (obj.getId_time_filter()) {
							case "hourly": // 1 hour
								interval = 1;
								timeUnit = ChronoUnit.HOURS;
								timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
								categoriesTimeFormat = DateTimeFormatter.ofPattern("hh:mm a");
                                if(!"today".equals(obj.getId_filter() )) {
                                    categoriesTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
                                }
								break;
							
							case "day": // 1 hour
								interval = 1;
								timeUnit = ChronoUnit.DAYS;
								timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
								categoriesTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
								break;
								
							case "this_week": // 1 day
							case "last_week":
							case "this_month":
							case "last_month":
								interval = 1;
								timeUnit = ChronoUnit.DAYS;
								timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
								categoriesTimeFormat = DateTimeFormatter.ofPattern("dd");
								break;
								
							case "month":
								interval = 1;
								timeUnit = ChronoUnit.MONTHS;
								timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM");
								categoriesTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
								start = start.withDayOfMonth(1);
								break;
								
							case "12_month":
								interval = 1;
								timeUnit = ChronoUnit.MONTHS;
								timeFullFormat = DateTimeFormatter.ofPattern("MM-yyyy");
								categoriesTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
								start = start.withDayOfMonth(1);
								break;
								
							case "lifetime": // 1 month
								interval = 1;
								timeUnit = ChronoUnit.MONTHS;
								timeFullFormat = DateTimeFormatter.ofPattern("MM-yyyy");
								categoriesTimeFormat = DateTimeFormatter.ofPattern("MMM. yyyy");
								start = start.withDayOfMonth(1);
								break;
								
							default:
								interval = 1;
								timeUnit = ChronoUnit.DAYS;
								timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
								categoriesTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyyy");
								break;
								
								
						}
						
						List<ClientMonthlyDateEntity> dateTimeList = new ArrayList<>();
						while (!start.isAfter(end)) {
							ClientMonthlyDateEntity dateTime = new ClientMonthlyDateEntity();
							dateTime.setTime_full(start.format(timeFullFormat));
							dateTime.setCategories_time(start.format(categoriesTimeFormat));
							dateTime.setEnergy(0.0);
							dateTimeList.add(dateTime);
							start = start.plus(interval, timeUnit);
						}
						
						data = queryForList("SitesDashboard.getDataChartingForLeviton", itemWidget);
						
						List<ClientMonthlyDateEntity> fulfilledData = Lib.fulfillData(dateTimeList, data, "time_full");
						
						
						itemWidget.put("data", fulfilledData);
					} else {
						itemWidget.put("data", new ArrayList());
					}
					
					
					dataList.add(itemWidget);
				}
			}
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	
	
	/**
	 * @description get data energy flow
	 * @author Long.Pham
	 * @since 2025-11-27
	 * @param id_site
	 */


	public SiteEnergyFlowEntity getDataSiteEnergyFlow(SiteEnergyFlowEntity obj) {
		try {
			// Get device by id_site
			List devices = queryForList("SitesDashboard.getListDeviceBySite", obj);
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



//				int interval = 1;
//				DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//				DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
//				DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
//
//				ChronoUnit timeUnit = ChronoUnit.DAYS;
//				LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//				LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//
//				List<BuildingReportDateEntity> dateTimeList = new ArrayList<>();
//				while (!start.isAfter(end)) {
//					BuildingReportDateEntity dateTime = new BuildingReportDateEntity();
//					dateTime.setTime_full(start.format(timeFullFormat));
//					dateTime.setCategories_time(start.format(categoriesTimeFormat));
//					dateTime.setTime_format(start.format(timeFormat));
//					dateTimeList.add(dateTime);
//					start = start.plus(interval, timeUnit);
//				}
//
//
//				// Build data time expected
//				LocalDateTime startExpected = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//				startExpected = startExpected.plus(-1, ChronoUnit.YEARS);
//				LocalDateTime endExpected = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//				endExpected = endExpected.plus(-1, ChronoUnit.YEARS);
//				List<BuildingReportDateEntity> dateTimeListExpected = new ArrayList<>();
//				while (!startExpected.isAfter(endExpected)) {
//					BuildingReportDateEntity dateTimeExpected = new BuildingReportDateEntity();
//					dateTimeExpected.setTime_full(startExpected.format(timeFullFormat));
//					dateTimeExpected.setCategories_time(startExpected.format(categoriesTimeFormat));
//					dateTimeExpected.setTime_format(startExpected.format(timeFormat));
//					dateTimeListExpected.add(dateTimeExpected);
//					startExpected = startExpected.plus(interval, timeUnit);
//				}
//
//				if(obj.getDevices().size() > 0) {
//					List<BuildingReportDateEntity> dataDaily = new ArrayList<>();
//					dataDaily = queryForList("BuildingReport.getDataReportDailyByType", obj);
//					List<BuildingReportDateEntity> fillDataDaily = Lib.fulfillData(dateTimeList, dataDaily, "time_full");
//					obj.setDataDaily(fillDataDaily);
//
//					List<BuildingReportDateEntity> dataDailyExpected = queryForList("BuildingReport.getDataReportDailyExpectedByType", obj);
//					List<BuildingReportDateEntity> fillDataExpected = Lib.fulfillData(dateTimeListExpected, dataDailyExpected, "time_full");
//					obj.setDataDailyExpected(fillDataExpected);
//				}
//
//
//				// get data History
//				int intervalHistory = 1;
//				DateTimeFormatter timeFullFormatHistory = DateTimeFormatter.ofPattern("yyyy-MM");
//				DateTimeFormatter categoriesTimeFormatHistory = DateTimeFormatter.ofPattern("MMM yyyy");
//				DateTimeFormatter timeFormatHistory = DateTimeFormatter.ofPattern("yyyy-MM");
//				DateTimeFormatter timeDateFormatHistory = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//				ChronoUnit timeUnitHistory = ChronoUnit.MONTHS;
//				LocalDateTime startHistory = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//				startHistory = startHistory.plus(-11, ChronoUnit.MONTHS);
//				LocalDateTime endHistory = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//
//				List<BuildingReportDateEntity> dateTimeListHistory = new ArrayList<>();
//				while (!startHistory.isAfter(endHistory)) {
//					BuildingReportDateEntity dateTimeHistory = new BuildingReportDateEntity();
//					dateTimeHistory.setTime_full(startHistory.format(timeFullFormatHistory));
//					dateTimeHistory.setCategories_time(startHistory.format(categoriesTimeFormatHistory));
//					dateTimeHistory.setTime_format(startHistory.format(timeFormatHistory));
//					dateTimeHistory.setStart_date(startHistory.format(timeDateFormatHistory));
//
//					startHistory = startHistory.plus(intervalHistory, timeUnitHistory);
//					dateTimeHistory.setEnd_date(startHistory.format(timeDateFormatHistory));
//					dateTimeListHistory.add(dateTimeHistory);
//				}
//
//				// Get data history expected
//				// get data History
//				int intervalEx = 1;
//				DateTimeFormatter timeFullFormatEx = DateTimeFormatter.ofPattern("yyyy-MM");
//				DateTimeFormatter categoriesTimeFormatEx = DateTimeFormatter.ofPattern("MMM yyyy");
//				DateTimeFormatter timeFormatEx = DateTimeFormatter.ofPattern("yyyy-MM");
//				DateTimeFormatter timeDateFormatEx = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//				ChronoUnit timeUnitEx = ChronoUnit.MONTHS;
//				LocalDateTime startEx = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//				startEx = startEx.plus(-2, ChronoUnit.YEARS);
//				startEx = startEx.plus(1, ChronoUnit.MONTHS);
//				LocalDateTime endEx = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//				endEx = endEx.plus(-1, ChronoUnit.YEARS);
//
//				List<BuildingReportDateEntity> dateTimeListEx = new ArrayList<>();
//				while (!startEx.isAfter(endEx)) {
//					BuildingReportDateEntity dateTimeEx = new BuildingReportDateEntity();
//					dateTimeEx.setTime_full(startEx.format(timeFullFormatEx));
//					dateTimeEx.setCategories_time(startEx.format(categoriesTimeFormatEx));
//					dateTimeEx.setTime_format(startEx.format(timeFormatEx));
//					dateTimeEx.setStart_date(startEx.format(timeDateFormatEx));
//
//					startEx = startEx.plus(intervalEx, timeUnitEx);
//					dateTimeEx.setEnd_date(startEx.format(timeDateFormatEx));
//					dateTimeListEx.add(dateTimeEx);
//				}
//
//
//
//				if(obj.getDevices().size() > 0) {
//					obj.setDateTimeList(dateTimeListHistory);
//					List<BuildingReportDateEntity>	dataHistory = queryForList("BuildingReport.getDataReportHistory", obj);
//					List<BuildingReportDateEntity> fillDataHistory = Lib.fulfillData(dateTimeListHistory, dataHistory, "time_full");
//					obj.setDataHistory(fillDataHistory);
//
//					// Get data history expected
//					obj.setDateTimeList(dateTimeListEx);
//					List<BuildingReportDateEntity>	dataEx = queryForList("BuildingReport.getDataReportHistory", obj);
//					List<BuildingReportDateEntity> fillDataEx = Lib.fulfillData(dateTimeListEx, dataEx, "time_full");
//					obj.setDataHistoryExpected(fillDataEx);
//
//				}
//
//				BuildingReportEntity dataPeakDemand = (BuildingReportEntity) queryForObject("BuildingReport.getDataPeakDemand", obj);
//				if(dataPeakDemand != null) {
//					obj.setPeak_demand(dataPeakDemand.getPeak_demand());
//					obj.setPeak_demand_date(dataPeakDemand.getPeak_demand_date());
//				}
//
//				BuildingReportEntity dataLastMonth = (BuildingReportEntity) queryForObject("BuildingReport.getDataLastMonth", obj);
//				if(dataLastMonth != null) {
//					obj.setLastMonth(dataLastMonth.getLastMonth());
//				}
//
//				BuildingReportEntity dataMaxAnnualDaily = (BuildingReportEntity) queryForObject("BuildingReport.getDataMaxAnnualDaily", obj);
//				if(dataMaxAnnualDaily != null) {
//					obj.setMax_annual_daily(dataMaxAnnualDaily.getMax_annual_daily());
//					obj.setMax_annual_daily_date(dataMaxAnnualDaily.getMax_annual_daily_date());
//					obj.setLast_year(dataMaxAnnualDaily.getLast_year());
//					obj.setAvg_last_period(dataMaxAnnualDaily.getAvg_last_period());
//				}
//
//				if("electric".equals(obj.getType_group())){
//					BuildingReportEntity dataDaytimeAndNightTime = (BuildingReportEntity) queryForObject("BuildingReport.getDataDaytimeAndNightTime", obj);
//					if(dataDaytimeAndNightTime != null) {
//						obj.setDaytime(dataDaytimeAndNightTime.getDaytime());
//						obj.setNighttime(dataDaytimeAndNightTime.getNighttime());
//						obj.setPower_factor(dataDaytimeAndNightTime.getPower_factor());
//						obj.setMax_annual_daily(dataDaytimeAndNightTime.getMax_annual_demand());
//						obj.setMax_monthly_demand(dataDaytimeAndNightTime.getMax_monthly_demand());
//					}
//
//
//					BuildingReportEntity dataPF = (BuildingReportEntity) queryForObject("BuildingReport.getDataLowestPF", obj);
//					if(dataPF != null) {
//						obj.setPower_factor_pf(dataPF.getPower_factor_pf());
//						obj.setPower_factor_pf_time(dataPF.getPower_factor_pf_time());
//					}
//				}


			}

			return obj;
		} catch (Exception ex) {
			return new SiteEnergyFlowEntity();
		}
	}
	
}
