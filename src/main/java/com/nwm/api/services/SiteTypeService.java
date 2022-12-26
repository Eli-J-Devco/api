/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SiteTypeEntity;

public class SiteTypeService extends DB {

	/**
	 * @description get list time zone
	 * @author long.pham
	 * @since 2020-10-30
	 * @returns array
	 */
	
	public List getAll(SiteTypeEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("SiteType.getAll", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

}
