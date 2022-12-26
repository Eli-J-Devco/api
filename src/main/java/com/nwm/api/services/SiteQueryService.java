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
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceParameterEntity;
import com.nwm.api.entities.ItemLastRowSiteQueryEntity;
import com.nwm.api.entities.SiteQueryEntity;
import com.nwm.api.utils.SecretCards;

public class SiteQueryService extends DB {

	
	
	/**
	 * @description get site detail
	 * @author long.pham
	 * @since 2021-03-12
	 * @param id_site
	 * @return Object
	 */

	public SiteQueryEntity getDetail(SiteQueryEntity obj) {
		SiteQueryEntity dataObj = new SiteQueryEntity();
		try {
			dataObj = (SiteQueryEntity) queryForObject("SiteQuery.getDetail", obj);
			if (dataObj == null)
				return new SiteQueryEntity();
		} catch (Exception ex) {
			return new SiteQueryEntity();
		}
		return dataObj;
	}
	
	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2021-03-12
	 * @param id_site
	 */
	

	public List getListDeviceByIdSite(SiteQueryEntity obj) {
		List dataList, dataListNew = new ArrayList();
		SecretCards secretCard = new SecretCards();
		try {
			dataList = queryForList("SiteQuery.getListDeviceByIdSite", obj);
			if(dataList.size() > 0) {
				for(int i =0; i< dataList.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					String datatablename = (String) item.get("datatablename");
					ItemLastRowSiteQueryEntity dataListRowItem = new ItemLastRowSiteQueryEntity();
					dataListRowItem = (ItemLastRowSiteQueryEntity) queryForObject("SiteQuery.getLastRowItem", item);
					if(dataListRowItem != null && dataListRowItem.getId_device() > 0) {
						item.put("times_ago_unit", dataListRowItem.getTimes_ago_unit());
						item.put("times_ago", dataListRowItem.getTimes_ago());
						item.put("hash_id", secretCard.encrypt(item.get("id").toString()).toLowerCase());
						item.put("hash_id_site", secretCard.encrypt(item.get("id_site").toString()).toLowerCase() );						
						item.put("key_indicator", dataListRowItem.getKey_indicator());
						
					} else {
						item.put("times_ago_unit", "");
						item.put("times_ago", 0);
						item.put("key_indicator", null);
					}
					
					// get energy now
					dataListNew.add(item);
				}
			} else {
				return new ArrayList();
			}
				
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataListNew;
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
			data = (DeviceEntity) queryForObject("SiteQuery.getDeviceDetail", obj);
			if(data == null) {
				return new DeviceEntity();
			}
			obj.setDatatablename(data.getDatatablename());
			
			
			Object dataListRowItem = queryForObject("SiteQuery.getModelLastRowItem", obj);
			if(dataListRowItem != null) {
				ObjectMapper oMapper = new ObjectMapper();
				Map<String, Object> map = oMapper.convertValue(dataListRowItem, Map.class);
				data.setLast_communication(map.get("last_communication").toString());
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss a");
				Date date = new Date();
				Calendar calendar = Calendar.getInstance(); 
				calendar.setTime(date);
				data.setLast_communication(sdf.format(calendar.getTime()));
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
		List dataList, dataListNew = new ArrayList();
		try {
			dataList = queryForList("SiteQuery.getListDeviceParameter", obj);
			if(dataList.size() > 0) {
				Object dataListRowItem = queryForObject("SiteQuery.getModelLastRowItem", obj);
				ObjectMapper oMapper = new ObjectMapper();
				Map<String, Object> map = oMapper.convertValue(dataListRowItem, Map.class);
				for(int i =0; i< dataList.size(); i++) {
					DeviceParameterEntity item = (DeviceParameterEntity)dataList.get(i);
					if(dataListRowItem != null) {
						String valueField = map.get(item.getSlug()).toString();
						item.setValue(valueField);
					} else {
						item.setValue("");
					}
					
					dataListNew.add(item);
				}
			} else {
				return new ArrayList();
			}
				
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataListNew;
	}
	
	
}
