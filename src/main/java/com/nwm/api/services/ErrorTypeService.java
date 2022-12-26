/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ErrorTypeEntity;

public class ErrorTypeService extends DB {

	/**
	 * @description get list error type
	 * @author long.pham
	 * @since 2021-03-30
	 * @returns array
	 */
	
	public List getListDropdown(ErrorTypeEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("ErrorType.getListDropdown", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

}
