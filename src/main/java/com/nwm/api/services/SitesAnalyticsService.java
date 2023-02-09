/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.EmployeeFilterFavoritesEntity;
import com.nwm.api.entities.EmployeeFilterRecentlyEntity;
import com.nwm.api.entities.SitesDevicesEntity;


public class SitesAnalyticsService extends DB {

	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2022-02-22
	 * @param id_site
	 */
	public List getListDeviceBySite(DeviceEntity obj) {
		try {
			
			List dataListNew = new ArrayList();
			List dataList = queryForList("SitesAnalytics.getListDeviceBySite", obj);
			if(dataList.size() > 0) {
				for(int i =0; i< dataList.size(); i++) {
					SitesDevicesEntity item = (SitesDevicesEntity)dataList.get(i);
					List dataListParameter = queryForList("SitesAnalytics.getListDeviceParameter", item);
					item.setDataParameter(dataListParameter);
					dataListNew.add(item);
				}
			}
			return dataListNew;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	/**
	 * @description get list device parameter
	 * @author long.pham
	 * @since 2022-02-22
	 * @param arr
	 */
	

	public List getChartParameterDevice(DeviceEntity obj) {
		try {
			List dataList = new ArrayList();
			List dataDevice = obj.getDataDevice();
			if(dataDevice.size() > 0) {
				for(int i =0; i< dataDevice.size(); i++) {
					ObjectMapper oMapper = new ObjectMapper();
					Map<String, Object> map = oMapper.convertValue(dataDevice.get(i), Map.class);
					Map<Object, Object> maps = new HashMap<>();
					maps.put("filterBy", obj.getFilterBy());
					maps.put("start_date", obj.getStart_date());
					maps.put("end_date", obj.getEnd_date());
					maps.put("data_send_time", obj.getData_send_time());
					Date dt = new Date();
					Calendar c = Calendar.getInstance(); 
					c.setTime(dt); 
					c.add(Calendar.MONTH, -3);
					SimpleDateFormat dateFor = new SimpleDateFormat("yyyy-MM-dd");
					Date d1 = dateFor.parse(obj.getStart_date());
					Date d2 = dateFor.parse(dateFor.format(c.getTime()));
					if(d1.compareTo(d2) < 0) {
						maps.put("datatablename", map.get("datatablename"));
						System.out.println("a: " + map.get("datatablename"));
					} else {
						maps.put("datatablename", map.get("view_tablename"));
						System.out.println("a: " + map.get("view_tablename"));
					}
					
					
					
					
					maps.put("id", map.get("id"));
					maps.put("device_name", map.get("devicename"));
					maps.put("id_device_group", map.get("id_device_group"));
					
					
					List getDataChartParameter = queryForList("SitesAnalytics.getDataChartParameter", maps);
					
					maps.put("data", getDataChartParameter);
					dataList.add(maps);
				}
			}
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
		
	}
	
	
	
	
	/**
	 * @description insert error level
	 * @author long.pham
	 * @since 2021-02-26
	 */
	public EmployeeFilterFavoritesEntity saveEmployeeFilterFavorites(EmployeeFilterFavoritesEntity obj) {
		try {
			// Save
			Object insertId = insert("EmployeeFilterFavorites.saveFilterFavorites", obj);
			if (insertId != null && insertId instanceof Integer) {
				Object total = queryForObject("EmployeeFilterFavorites.getListCount", obj);
				if((int)total > 10) {
					// Delete one row
					delete("EmployeeFilterFavorites.deleteFilterFavorites", obj);
				}
				return obj;
			} else {
				return null;
			}

		} catch (Exception ex) {
			log.error("insert", ex);
			return null;
		}
	}
	
	
	/**
	 * @description insert error level
	 * @author long.pham
	 * @since 2022-05-03
	 */
	public EmployeeFilterRecentlyEntity saveRecentlyUsedFilter(EmployeeFilterRecentlyEntity obj) {
		try {
			// Save
			Object insertId = insert("EmployeeFilterRecently.saveRecentlyUsedFilter", obj);
			if (insertId != null && insertId instanceof Integer) {
				Object total = queryForObject("EmployeeFilterRecently.getListCount", obj);
				
				if((int)total > 10) {
					// Delete one row
					delete("EmployeeFilterRecently.deleteRecentlyUsedFilter", obj);
				}
				
				return obj;
			} else {
				return null;
			}

		} catch (Exception ex) {
			log.error("insert", ex);
			return null;
		}
	}
	
	
	
	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site, id_device
	 */
	

	public List getListEmployeeFilter(EmployeeFilterFavoritesEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("EmployeeFilterCharting.getListEmployeeFilter", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site, id_device
	 */
	

	public List getListRecently(EmployeeFilterRecentlyEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("EmployeeFilterRecently.getListRecently", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	
	/**
	 * @description get list favorites by id_site
	 * @author long.pham
	 * @since 2022-05-03
	 * @param id_site
	 */
	

	public List getListFavorites(EmployeeFilterFavoritesEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("EmployeeFilterFavorites.getListFavorites", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
}
