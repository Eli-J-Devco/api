/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceParameterEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.utils.SecretCards;

public class SitesDevicesService extends DB {

	
	
	/**
	 * @description get site detail
	 * @author long.pham
	 * @since 2021-03-12
	 * @param id_site
	 * @return Object
	 */

	public SitesDevicesEntity getDetail(SitesDevicesEntity obj) {
		SitesDevicesEntity dataObj = new SitesDevicesEntity();
		try {
			dataObj = (SitesDevicesEntity) queryForObject("SitesDashboard.getDetail", obj);
			List listDeviceDisableAlert = new ArrayList();
			listDeviceDisableAlert = queryForList("SitesDashboard.getListDeviceIsDisableAlert", obj);	
			dataObj.setDeviceDisableAlerts(listDeviceDisableAlert);
			
			if (dataObj == null)
				return new SitesDevicesEntity();
		} catch (Exception ex) {
			return new SitesDevicesEntity();
		}
		return dataObj;
	}
	
	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2021-03-12
	 * @param id_site
	 */
	

	public List getListDeviceByIdSite(SitesDevicesEntity obj) {
		List dataList, dataListNew = new ArrayList();
		SecretCards secretCard = new SecretCards();
		try {
			dataList = queryForList("SitesDevices.getListDeviceByIdSite", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
		
	}
	
	
	/**
	 * @description get device detail by id
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site, id_device
	 * @return Object
	 */

	public DeviceEntity getDeviceDetail(DeviceEntity obj) {
		DeviceEntity data = new DeviceEntity();
		try {
			data = (DeviceEntity) queryForObject("SitesDevices.getDeviceDetail", obj);
			if(data == null) {
				return new DeviceEntity();
			}
			obj.setDatatablename(data.getDatatablename());
			
			
			Object dataListRowItem = queryForObject("SitesDevices.getModelLastRowItem", obj);
			if(dataListRowItem != null) {
				ObjectMapper oMapper = new ObjectMapper();
				Map<String, Object> map = oMapper.convertValue(dataListRowItem, Map.class);
//				data.setLast_communication(map.get("last_communication").toString());
			} else {
				// converting date format for US
				Date date = new Date();
		        SimpleDateFormat sdfAmerica = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss a");
		        TimeZone tzInAmerica = TimeZone.getTimeZone(data.getTimezone());
		        sdfAmerica.setTimeZone(tzInAmerica);
				
				data.setLast_communication(sdfAmerica.format(date) + " " + data.getAbbreviation_std());
			}
			
			
		} catch (Exception ex) {
			return new DeviceEntity();
		}
		return data;
	}
	
	
	
	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site, id_device
	 */
	

	public List getListParameters(DeviceEntity obj) {
		List<Object> dataList, dataListNew = new ArrayList<Object>();
		try {
			dataList = queryForList("SitesDevices.getListDeviceParameter", obj);
			if(dataList.size() > 0) {
				Object dataListRowItem = queryForObject("SitesDevices.getModelLastRowItem", obj);
				ObjectMapper oMapper = new ObjectMapper();
				Map<String, Object> map = oMapper.convertValue(dataListRowItem, Map.class);
				for(int i =0; i< dataList.size(); i++) {
					DeviceParameterEntity item = (DeviceParameterEntity)dataList.get(i);
					if(dataListRowItem != null) {
						String valueField = map.get(item.getSlug()) != null ? map.get(item.getSlug()).toString() : "";
						item.setValue(valueField);
					} else {
						item.setValue("");
					}
					
					dataListNew.add(item);
				}
			} else {
				return new ArrayList<Object>();
			}
				
		} catch (Exception ex) {
			return new ArrayList<Object>();
		}
		return dataListNew;
	}
	
}
