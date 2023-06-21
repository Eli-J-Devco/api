/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceTypeEntity;

public class DeviceTypeService extends DB {

	/**
	 * @description get list type device
	 * @author long.pham
	 * @since 2020-11-06
	 * @returns array
	 */
	
	public List getList(DeviceTypeEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("DeviceType.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list device type
	 * @author duy.phan
	 * @since 2023-06-13
	 * @param {}
	 */
	
	
	public List getListManage(DeviceTypeEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("DeviceType.getListManage", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	public int getTotalRecordManage(DeviceTypeEntity obj) {
		try {
			return (int)queryForObject("DeviceType.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/** @description delete device type
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 */
	public boolean delete(DeviceTypeEntity obj) {
		try {
			return delete("DeviceType.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("DeviceType.delete", ex);
			return false;
		}
	}
	
	/**
	 * @description insert device type
	 * @author long.pham
	 * @since 2021-02-26
	 */
	public DeviceTypeEntity insertDeviceType(DeviceTypeEntity obj) 
	{
		try
	    {
	       Object insertId = insert("DeviceType.insertDeviceType", obj);
	       if(insertId != null && insertId instanceof Integer) {
	    	   return obj;
	       }else {
	    	   return null;
	       }
	    }
	    catch(Exception ex)
	    {
	        log.error("insert", ex);
	        return null;
	    }	
	}
	
	
	/**
	 * @description update device type
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 */
	public boolean updateDeviceType(DeviceTypeEntity obj){
		try{
			return update("DeviceType.updateDeviceType", obj)>0;
		}catch (Exception ex) {
			log.error("DeviceType.updateDeviceType", ex);
			return false;
		}
	}
	
	/**
	 * @description update device status
	 * @author long.pham
	 * @since 2021-01-12
	 * @param id
	 */
	public boolean updateIsColor(DeviceTypeEntity obj) {
		try {
			return update("DeviceType.updateIsColor", obj) > 0;
		} catch (Exception ex) {
			log.error("DeviceType.updateIsColor", ex);
			return false;
		}
	}

}
