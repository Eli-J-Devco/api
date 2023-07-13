/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.CalculationMeasuredProductionEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelVirtualMeterOrInverterEntity;
import com.nwm.api.entities.VirtualDeviceEntity;

public class CalculationMeasureProductionService extends DB {
	
	

	/**
	 * @description get list device meter or inverter.
	 * @author long.pham
	 * @since 2023-07-13
	 */
	
	public List getListDevice(CalculationMeasuredProductionEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CalculationMeasuredProduction.getListDevice", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		
		return dataList;
	}
	
	
	
	/**
	 * @description update device
	 * @author long.pham
	 * @since 2023-07-13
	 * @param {}
	 */
	public boolean updateMeasuredProduction(CalculationMeasuredProductionEntity obj) {
		try {
			return update("CalculationMeasuredProduction.updateModelMeasuredProduction", obj) > 0;
		} catch (Exception ex) {
			log.error("CalculationMeasuredProduction.updateModelMeasuredProduction", ex);
			return false;
		}
	}
	
//	
//	
//	
//	
//	/**
//	 * @description get list device weather "POA" for virtual device.
//	 * @author long.pham
//	 * @since 2023-06-27
//	 */
//	
////	public List getListDevicePoa(VirtualDeviceEntity obj) {
////		List dataList = new ArrayList();
////		try {
////			dataList = queryForList("VirtualDevice.getListDevicePoa", obj);
////			if (dataList == null)
////				return new ArrayList();
////		} catch (Exception ex) {
////			return new ArrayList();
////		}
////		
////		return dataList;
////	}
//	
//	
//	
//	
//	
//	/**
//	 * @description get data device
//	 * @author long.pham
//	 * @since 2023-06-27
//	 * @param {}
//	 * @return Object
//	 */
//	
//	public List getDataPower(VirtualDeviceEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("VirtualDevice.getDataPower", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		
//		return dataList;
//	}
//	
//	
//	
//	
//	/**
//	 * @description insert table virtual device
//	 * @author long.pham
//	 * @since 2023-06-30
//	 * @param {}
//	 */
//	public ModelVirtualMeterOrInverterEntity insertVirtualDevice(ModelVirtualMeterOrInverterEntity obj) 
//	{
//		try
//	    {
//	       Object insertId = insert("VirtualDevice.insertVirtualDevice", obj);
//	       if(insertId != null && insertId instanceof Integer) {
//	    	   return obj;
//	       }else {
//	    	   return null;
//	       }
//	    }
//	    catch(Exception ex)
//	    {
//	        log.error("insert", ex);
//	        return null;
//	    }	
//	}
//	
//	/**
//	 * @description get last row virtual device
//	 * @author long.pham
//	 * @since 2023-07-03
//	 * @param {}
//	 */
//	
//	public VirtualDeviceEntity getLastRowVirtualDevice(VirtualDeviceEntity obj) {
//		VirtualDeviceEntity rowItem = new VirtualDeviceEntity();
//		try {
//			rowItem = (VirtualDeviceEntity) queryForObject("VirtualDevice.getLastRowVirtualDevice", obj);
//			if (rowItem == null)
//				return new VirtualDeviceEntity();
//		} catch (Exception ex) {
//			log.error("VirtualDevice.getLastRowVirtualDevice", ex);
//			return new VirtualDeviceEntity();
//		}
//		return rowItem;
//	}
//	
//	

//	
	
}
