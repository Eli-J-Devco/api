/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceTypeEntity;

public class DeviceTypeService extends DB {

	/**
	 * @description get list type device
	 * @author long.pham
	 * @since 2020-11-06
	 * @returns array
	 */
	
	public List getList(DeviceTypeEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("DeviceType.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

}
