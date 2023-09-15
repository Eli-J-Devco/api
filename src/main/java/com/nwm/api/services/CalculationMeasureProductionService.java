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
	
	
	/**
	 * @description get list device update measured production upload by FTP
	 * @author long.pham
	 * @since 2023-07-13
	 */
	
	public List getListDeviceUpdateMeasuredProductionFTP(CalculationMeasuredProductionEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CalculationMeasuredProduction.getListDeviceUpdateMeasuredProductionFTP", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		
		return dataList;
	}
	
	
	/**
	 * @description update device measured production 
	 * @author Long.Pham
	 * @since 2023-09-12
	 */
	public boolean updateDeviceMeasuredProduction(DeviceEntity obj){
		try{
			return update("CalculationMeasuredProduction.updateDeviceMeasuredProduction", obj)>0;
		}catch (Exception ex) {
			log.error("CalculationMeasuredProduction.updateDeviceMeasuredProduction", ex);
			return false;
		}
	}
	
}
