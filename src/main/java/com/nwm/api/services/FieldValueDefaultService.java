/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.FieldValueDefaultEntity;

public class FieldValueDefaultService extends DB {

	/**
	 * @description get list field value default
	 * @author long.pham
	 * @since 2023-04-17
	 * @returns array
	 */
	
	public List getList(FieldValueDefaultEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("FieldValueDefault.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	
	/**
	 * @description get list field value default
	 * @author long.pham
	 * @since 2023-04-17
	 * @returns array
	 */
	
	public List getListByGroup(FieldValueDefaultEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("FieldValueDefault.getListByGroup", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	

}
