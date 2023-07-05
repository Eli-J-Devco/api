/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.BatchJobTableEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelVirtualMeterOrInverterEntity;
import com.nwm.api.entities.VirtualDeviceEntity;

public class VirtualDeviceService extends DB {
	
	
	/**
	 * @description get list site install virtual device meter
	 * @author long.pham
	 * @since 2023-06-27
	 */
	
	public List getListSiteVirtualDevice(VirtualDeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("VirtualDevice.getListSiteVirtualDevice", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		
		return dataList;
	}
	
	
	/**
	 * @description get list device meter or inverter for virtual device.
	 * @author long.pham
	 * @since 2023-06-27
	 */
	
	public List getListDevice(VirtualDeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("VirtualDevice.getListDevice", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		
		return dataList;
	}
	
	
	

	/**
	 * @description get list device meter or inverter for virtual device.
	 * @author long.pham
	 * @since 2023-06-27
	 */
	
	public List getListDeviceWeather(VirtualDeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("VirtualDevice.getListDeviceWeather", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		
		return dataList;
	}
	
	
	
	
	/**
	 * @description get list device sensor for virtual device.
	 * @author long.pham
	 * @since 2023-06-27
	 */
	
	public List getListDeviceSensorAmbientTemp(VirtualDeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("VirtualDevice.getListDeviceSensorAmbientTemp", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		
		return dataList;
	}
	
	
	/**
	 * @description get data device
	 * @author long.pham
	 * @since 2023-06-27
	 * @param {}
	 * @return Object
	 */
	
	public List getDataPower(VirtualDeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("VirtualDevice.getDataPower", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		
		return dataList;
	}
	
	
	
	
	/**
	 * @description insert table virtual device
	 * @author long.pham
	 * @since 2023-06-30
	 * @param {}
	 */
	public ModelVirtualMeterOrInverterEntity insertVirtualDevice(ModelVirtualMeterOrInverterEntity obj) 
	{
		try
	    {
	       Object insertId = insert("VirtualDevice.insertVirtualDevice", obj);
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
	 * @description get last row virtual device
	 * @author long.pham
	 * @since 2023-07-03
	 * @param {}
	 */
	
	public VirtualDeviceEntity getLastRowVirtualDevice(VirtualDeviceEntity obj) {
		VirtualDeviceEntity rowItem = new VirtualDeviceEntity();
		try {
			rowItem = (VirtualDeviceEntity) queryForObject("VirtualDevice.getLastRowVirtualDevice", obj);
			if (rowItem == null)
				return new VirtualDeviceEntity();
		} catch (Exception ex) {
			log.error("VirtualDevice.getLastRowVirtualDevice", ex);
			return new VirtualDeviceEntity();
		}
		return rowItem;
	}
	
	
	/**
	 * @description update device
	 * @author long.pham
	 * @since 2023-07-03
	 * @param id, last_updated, last_value, field1, field2, field3
	 */
	public boolean updateDeviceVirtualDevice(DeviceEntity obj) {
		try {
			return update("VirtualDevice.updateDeviceVirtualDevice", obj) > 0;
		} catch (Exception ex) {
			log.error("VirtualDevice.updateDeviceVirtualDevice", ex);
			return false;
		}
	}
	
	
}
