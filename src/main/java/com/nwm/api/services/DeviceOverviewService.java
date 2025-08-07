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
import com.nwm.api.entities.SitesDevicesEntity;

public class DeviceOverviewService extends DB {

	
	/**
	 * @description get list device by id_company
	 * @author long.pham
	 * @since 2025-07-30
	 * @param id_company
	 */
	

	public List getListDeviceByCompany(SitesDevicesEntity obj) {
		try {
			List dataList = queryForList("DeviceOverview.getListDeviceByCompany", obj);
			if (dataList == null) return new ArrayList();
			
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	/**
	 * @description get list device by id_company
	 * @author long.pham
	 * @since 2025-07-30
	 * @param id_company
	 */
	
	public int getTotalRecordByCompany(SitesDevicesEntity obj) {
		try {
			return (int)queryForObject("DeviceOverview.getTotalRecordByCompany", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description get all site by id_employee, id_company
	 * @author long.pham
	 * @since 2022-01-29
	 * @param id_employee
	 */
	

	public List getSiteByEmployee(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("DeviceOverview.getSiteByEmployee", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
}
