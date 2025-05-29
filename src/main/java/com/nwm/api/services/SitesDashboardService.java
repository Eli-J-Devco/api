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
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.DevicePanelEntity;
import com.nwm.api.entities.DeviceZoneEntity;
import com.nwm.api.entities.SiteDashboardGenerationEntity;
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
	 * @description get list device by id site
	 * @author long.pham
	 * @since 2022-03-04
	 * @param id_site
	 * @return Object
	 */
	
	public List getListDeviceByIdSite(SitesDevicesEntity obj) {
		try {
			List dataList = queryForList("SitesDashboard.getListDeviceByIdSite", obj);
			
			if (dataList.size() > 0) {
				for (int i = 0; i < dataList.size(); i++) {
					Map<String, Object> device = (Map<String, Object>) dataList.get(i);
					String last_updated = (String) device.get("last_updated");
					int id_device_type = (int) device.get("id_device_type");
					long totalError = 0;
					if(device.get("totalError") != null) {
						totalError = (long) device.get("totalError");
					}
					int id_error_level = 0;
					if(device.get("id_error_level") != null) {
						id_error_level = (int) device.get("id_error_level");
					}
					
					String key_indicator = (String) device.get("key_indicator");
					String times_ago_unit = (String) device.get("times_ago_unit");
					if (key_indicator.equals("Never")) {}
					// Find the last value and time
					else if (last_updated.equals("-") || (totalError > 0 && id_error_level == 33) || ((id_device_type == 4) && key_indicator.equals("-"))) {
						Map<String, Object> device_site = (Map<String, Object>) queryForObject("SitesDashboard.getLastUpdated", dataList.get(i));
						if(device_site != null) {
							device.put("last_updated", device_site.get("time"));	
							if((id_device_type == 1 || id_device_type == 3 || id_device_type == 4 || id_device_type == 12) && id_error_level != 33 && (device_site.get("key_indicator") != null  || times_ago_unit.equals("-"))) {
								device.put("key_indicator", device_site.get("key_indicator"));
							} else if (id_error_level == 33) {
								device.put("key_indicator", "-");
								device.put("times_ago_unit", device_site.get("times_ago_unit"));
								device.put("times_ago", device_site.get("times_ago"));
							}
						} else {
							device.put("last_updated", "-");
							device.put("key_indicator", "-");
						}
					}
				}
			}
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
					
					if(listWidget.size() > 0) {
						
						int interval = 0;
						DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
						DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm a");
						ChronoUnit timeUnit = ChronoUnit.MINUTES;
						LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
						LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
						// get Energy usage 
						List<ClientMonthlyDateEntity> data = new ArrayList<>();
						
						switch (obj.getId_filter()) {
							case "today": // 1 hour
								interval = 1;
								timeUnit = ChronoUnit.HOURS;
								timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
								categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm a");
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
								
								
						}
						
						List<ClientMonthlyDateEntity> dateTimeList = new ArrayList<>();
						while (!start.isAfter(end)) {
							ClientMonthlyDateEntity dateTime = new ClientMonthlyDateEntity();
							dateTime.setTime_full(start.format(timeFullFormat));
							dateTime.setCategories_time(start.format(categoriesTimeFormat));
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
	
	
}
