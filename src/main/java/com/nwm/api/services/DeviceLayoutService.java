/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceLayoutEntity;

public class DeviceLayoutService extends DB {
	
	/**
	 * @description Get device layout template list
	 * @author Hung.Bui
	 * @since 2024-07-18
	 */
	public List getList(DeviceLayoutEntity obj) {
		try {
			List dataList = queryForList("DeviceLayout.getList", obj);
			return dataList == null ? new ArrayList() : dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description Get assigned field list
	 * @author Hung.Bui
	 * @since 2024-07-18
	 */
	public List getListAssignedField(DeviceLayoutEntity obj) {
		try {
			List dataList = queryForList("DeviceLayout.getListAssignedField", obj);
			return dataList == null ? new ArrayList() : dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description Get device template list by device group
	 * @author Hung.Bui
	 * @param id_device_group
	 * @since 2024-07-29
	 */
	public List getListDeviceTemplateByDeviceGroup(DeviceLayoutEntity obj) {
		try {
			List<Map<String, Object>> dataList = queryForList("DeviceLayout.getListDeviceTemplateByDeviceGroup", obj);
			if (dataList == null) return new ArrayList();
			
			ObjectMapper mapper = new ObjectMapper();
			dataList.forEach(item -> {
					try {
						item.put("assignedField", mapper.readValue(item.get("assignedField").toString(), new TypeReference<List<Map<String, Object>>>(){}));
					} catch (JsonProcessingException e) {
						item.put("assignedField", new ArrayList<Map<String, Object>>());
					}
					
					try {
						item.put("data", mapper.readValue(item.get("data").toString(), new TypeReference<List<Map<String, Object>>>(){}));
					} catch (JsonProcessingException e) {
						item.put("data", new ArrayList<Map<String, Object>>());
					}
			});
			
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description insert device layout template
	 * @author Hung.Bui
	 * @since 2024-07-18
	 */
	public DeviceLayoutEntity insert(DeviceLayoutEntity obj) {
		try {
	       Object insertId = insert("DeviceLayout.insert", obj);
    	   return insertId != null && insertId instanceof Integer ? obj : null;
	    } catch(Exception ex) {
	        log.error("DeviceLayout.insert", ex);
	        return null;
	    }	
	}
	
	/**
	 * @description update device layout template
	 * @author Hung.Bui
	 * @since 2024-07-18
	 */
	public boolean update(DeviceLayoutEntity obj){
		try {
			return update("DeviceLayout.update", obj) > 0;
		} catch (Exception ex) {
			log.error("DeviceLayout.update", ex);
			return false;
		}
	}
	
	/**
	 * @description Save field assignment for device layout
	 * @author Hung.Bui
	 * @since 2024-07-18
	 */
	public DeviceLayoutEntity saveFieldAssignment(DeviceLayoutEntity obj){
		try {
	       Object insertId = insert("DeviceLayout.saveFieldAssignment", obj);
    	   return insertId != null && insertId instanceof Integer ? obj : null;
	    } catch(Exception ex) {
	        log.error("DeviceLayout.insert", ex);
	        return null;
	    }
	}
}
