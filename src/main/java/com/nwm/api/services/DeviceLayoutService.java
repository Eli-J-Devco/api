/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

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
