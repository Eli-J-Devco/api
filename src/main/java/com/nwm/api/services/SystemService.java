/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SystemEntity;

public class SystemService extends DB {

	/**
	 * @description Get system setting
	 * @author Hung.Bui
	 * @since 2023-08-24
	 * @returns array
	 */
	public List getSystemSetting(SystemEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("System.getSystemSetting", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description update system setting
	 * @author Hung.Bui
	 * @since 2023-08-24
	 */
	public boolean update(SystemEntity obj){
		try{
			return update("System.updateLockoutTime", obj)>0 && update("System.updateMaxAttempts", obj)>0;
		}catch (Exception ex) {
			log.error("System.update", ex);
			return false;
		}
	}
	
	
	/**
	 * @description get list device meter and inverter
	 * @author Long.Pham
	 * @since 2024-06-25
	 */
	
	public List getListDeviceINVMeter(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			List dataDevice = queryForList("System.getListMeterAndInverterFindLogAlert", obj);
			if (dataDevice == null)
				return new ArrayList();
			
			for(int i = 0; i < dataDevice.size(); i++) {
				Map<String, Object> dataItem = (Map<String, Object>) dataDevice.get(i);
				dataItem.put("start_date", obj.getStart_date());
				dataItem.put("end_date", obj.getEnd_date());
				List data = queryForList("System.getDataDevice", dataItem);
				dataItem.put("dataDevice", data);
				dataList.add(dataItem);
			}
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
}
