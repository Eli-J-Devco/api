/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
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
	
}
