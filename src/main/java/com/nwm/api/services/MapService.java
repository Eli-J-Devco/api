/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SiteEntity;

public class MapService extends DB {

	/**
	 * @description get all site by id employee
	 * @author long.pham
	 * @since 2021-01-14
	 * @param id_employee, id_sites
	 */

	public List getAllSiteByEmployee(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Map.getAllSiteByEmployee", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
}
