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
import com.nwm.api.entities.ErrorEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.WidgetGroupParameterEntity;

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
			// Create table, view, BJob
			session.insert("Device.createTableDevice", obj);
			session.insert("Device.createViewThreeMonthData", obj);
			session.insert("Device.createBJobData", obj);
			String device_group_table = obj.getDevice_group_table();
			
			// Insert data to new table name
			session.insert("Device.insertDataToNewTableName", obj);
			obj.setDatatablename("data" + obj.getId() + "_"+ device_group_table);
			obj.setView_tablename("View" + obj.getId() + "_"+ device_group_table);
			obj.setJob_tablename("BJob" + obj.getId() + "_"+ device_group_table);
			session.update("Device.updateTableDevice", obj);
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
	
	
	
	
	
	/**
	 * @description get site detail
	 * @author long.pham
	 * @since 2020-10-22
	 * @param id_customer, id_site
	 * @return Object
	 */

	public SiteEntity getDetailSite(SiteEntity obj) {
		SiteEntity dataObj = new SiteEntity();
		try {
			dataObj = (SiteEntity) queryForObject("CalculationMeasuredProduction.getDetailSite", obj);
			if (dataObj == null)
				return new SiteEntity();
		} catch (Exception ex) {
			return new SiteEntity();
		}
		return dataObj;
	}
	
	
	/**
	 * @description insert device
	 * @author long.pham
	 * @since 2023-11-07
	 */
	public SiteEntity insertTableReport(SiteEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {
			// Create table site data report
			session.insert("CalculationMeasuredProduction.createTableReport", obj);
			// Move data site data report
			session.insert("CalculationMeasuredProduction.moveSiteDataReport", obj);
			
			// Create table virtual device
			session.insert("CalculationMeasuredProduction.createTableVirtualDevice", obj);
			// Move data table virtual device
			session.insert("CalculationMeasuredProduction.moveSiteDataVirtualDevice", obj);

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
	
	
	/**
	 * @description get list widget group parameter 
	 * @author long.pham
	 * @since 2024-03-27
	 */
	
	public List getListWidgetGroupParameter(CalculationMeasuredProductionEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CalculationMeasuredProduction.getListWidgetGroupParameter", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		
		return dataList;
	}
	
	
	public long getValueField(WidgetGroupParameterEntity obj) {
		try {
			return (long) queryForObject("CalculationMeasuredProduction.getValueField", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	
	/**
	 * @description update widget group parameter value
	 * @author Long.Pham
	 * @since 2023-09-12
	 */
	public boolean updateValueField(WidgetGroupParameterEntity obj){
		try{
			return update("CalculationMeasuredProduction.updateValueField", obj)>0;
		}catch (Exception ex) {
			log.error("CalculationMeasuredProduction.updateValueField", ex);
			return false;
		}
	}
	
}
