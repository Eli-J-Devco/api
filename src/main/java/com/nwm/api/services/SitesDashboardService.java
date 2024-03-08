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
import com.nwm.api.entities.TablePreferenceEntity;
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
		List dataList, newData = new ArrayList();
		try {
			// get user preference for table sorting column
			TablePreferenceEntity tablePreference = new TablePreferenceEntity();
			tablePreference.setId_employee(obj.getId_employee());
			tablePreference.setTable("SiteDashboard");
			tablePreference = (TablePreferenceEntity) queryForObject("TablePreference.getPreference", tablePreference);
			
			if ((obj.getOrder_by() != null) && (obj.getSort_column() != null)) {
				if (tablePreference != null) {
					tablePreference.setOrder_by(obj.getOrder_by());
					tablePreference.setSort_column(obj.getSort_column());
					update("TablePreference.updatePreference", tablePreference);
				} else {
					tablePreference = new TablePreferenceEntity();
					tablePreference.setId_employee(obj.getId_employee());
					tablePreference.setTable("SiteDashboard");
					tablePreference.setOrder_by(obj.getOrder_by());
					tablePreference.setSort_column(obj.getSort_column());
					insert("TablePreference.insertPreference", tablePreference);
				}
			} else {
				if (tablePreference != null) {
					obj.setOrder_by(tablePreference.getOrder_by());
					obj.setSort_column(tablePreference.getSort_column());
				}
			}
			
			dataList = queryForList("SitesDashboard.getListDeviceByIdSite", obj);
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
					// Find the last value and time
					if (last_updated.equals("N/A") || (totalError > 0) || (id_device_type == 4 && key_indicator.equals("N/A"))) {
						Map<String, Object> device_site = (Map<String, Object>) queryForObject("SitesDashboard.getLastUpdated", dataList.get(i));
						if(device_site != null) {
							device.put("last_updated", device_site.get("time"));
							if((id_device_type == 1 || id_device_type == 3 || id_device_type == 4 || id_device_type == 12) && (device_site.get("key_indicator") != null  || times_ago_unit.equals("N/A"))) {
								device.put("key_indicator", device_site.get("key_indicator"));
							}
						} else {
							device.put("last_updated", "N/A");
						}
					}
					newData.add(device);
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
	 * @description get user preference for table sorting column
	 * @author Hung.Bui
	 * @since 2023-02-27
	 * @param id_customer, id_site
	 */
	public TablePreferenceEntity getPreference(SitesDevicesEntity obj) {
		try {
			// get user preference for table sorting column
			TablePreferenceEntity tablePreference = new TablePreferenceEntity();
			tablePreference.setId_employee(obj.getId_employee());
			tablePreference.setTable("SiteDashboard");
			tablePreference = (TablePreferenceEntity) queryForObject("TablePreference.getPreference", tablePreference);
			
			if (tablePreference == null) {
				return new TablePreferenceEntity();
			}
			return tablePreference;
		} catch (Exception ex) {
			return null;
		}
	}
}
