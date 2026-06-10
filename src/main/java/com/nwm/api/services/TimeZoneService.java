/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DateFormatEntity;
import com.nwm.api.entities.TimeZoneEntity;

public class TimeZoneService extends DB {

	/**
	 * @description get list time zone
	 * @author long.pham
	 * @since 2020-10-30
	 * @returns array
	 */
	
	public List getList(TimeZoneEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("TimeZone.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description get list time zone
	 * @author long.pham
	 * @since 2020-10-30
	 * @returns array
	 */
	
	public List getListDateFormat(DateFormatEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("TimeZone.getListDateFormat", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	
	
	/**
	 * @description update time zone offset
	 * @author long.pham
	 * @since 2023-11-07
	 * @param id
	 */
	public boolean updateTimeZoneOffset(TimeZoneEntity obj){
		try{
			return update("TimeZone.updateTimeZoneOffset", obj)>0;
		}catch (Exception ex) {
			return false;
		}
	}
}
