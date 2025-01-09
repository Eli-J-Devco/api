/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.SiteDashboardGenerationEntity;
import com.nwm.api.entities.SitesDevicesEntity;
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
		List dataList = new ArrayList();
		try {
			List listWidgetOverview = queryForList("SitesDashboard.getListWidgetOverviewLeviton", obj);
			
			if (listWidgetOverview.size() > 0) {
				for (int i = 0; i < listWidgetOverview.size(); i++) {
					// get data device in widget 
					Map<String, Object> itemWidget = (Map<String, Object>) listWidgetOverview.get(i);
					List listWidget = queryForList("SitesDashboard.getListDeviceInWidget", itemWidget);
					
					itemWidget.put("devices", listWidget);
					
					// get data today
					if(listWidget.size() > 0) {
						Map<String, Object> dataToday = (Map<String, Object>) queryForObject("SitesDashboard.getDataToday", itemWidget);
						itemWidget.put("today", dataToday.get("today"));
						itemWidget.put("thirtydays", dataToday.get("energy"));
					} else {
						itemWidget.put("today", 0);
						itemWidget.put("thirtydays", 0);
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
	 * @description get list data charting site overview for leviton
	 * @author long.pham
	 * @since 2024-07-09
	 * @param id_site
	 * @return Object
	 */
	
	public List getListDataChartingForLeviton(SitesDevicesEntity obj) {
		List dataList = new ArrayList();
		try {
			List listWidgetOverview = queryForList("SitesDashboard.getListWidgetOverviewLeviton", obj);
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
						List data = queryForList("SitesDashboard.getDataChartingForLeviton", itemWidget);	
						itemWidget.put("data", data);
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
