/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.CalculationMeasuredProductionEntity;
import com.nwm.api.entities.DeviceEntity;

public class CalculationMeasureProductionService extends DB {
	
	
	/**
	 * @description get list device meter or inverter.
	 * @author long.pham
	 * @since 2023-07-13
	 */
	
	public List getListDeviceMoveData(CalculationMeasuredProductionEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CalculationMeasuredProduction.getListDeviceMoveData", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		
		return dataList;
	}
	

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
	
	
	/**
	 * @description insert device
	 * @author long.pham
	 * @since 2021-01-12
	 */
	public CalculationMeasuredProductionEntity insertTable(CalculationMeasuredProductionEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {
//			Object insertId =  session.insert("Device.insertDevice", obj);
//			if(insertId != null && insertId instanceof Integer && obj.getId() > 0) {
				// Create table, view, BJob
				session.insert("Device.createTableDevice", obj);
				session.insert("Device.createViewThreeMonthData", obj);
				session.insert("Device.createBJobData", obj);
				
//				obj.setDatatablename("data" + obj.getId() + "_"+ obj.getDatatablename());
//				obj.setView_tablename("view" + obj.getId() + "_"+ obj.getDatatablename());
//				obj.setJob_tablename("bjob" + obj.getId() + "_"+ obj.getDatatablename());
//				session.update("Device.updateTableDevice", obj);
				
				// Insert data to new table name
				session.insert("Device.insertDataToNewTableName", obj);
				
				
//			} else {
//				throw new Exception();
//			}

			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("Device.insertDevice", ex);
			obj.setId(0);
			return obj;
		} finally {
			session.close();
		}	
		
	}
	
}
