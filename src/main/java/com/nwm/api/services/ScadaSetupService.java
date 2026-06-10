/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SitesDevicesEntity;

public class ScadaSetupService extends DB {

	
	
	/**
	 * @description get site detail
	 * @author long.pham
	 * @since 2024-04-01
	 * @param id_site
	 * @return Object
	 */

	public SitesDevicesEntity getSiteDetail(SitesDevicesEntity obj) {
		SitesDevicesEntity dataObj = new SitesDevicesEntity();
		try {
			dataObj = (SitesDevicesEntity) queryForObject("ScadaSetup.getSiteDetail", obj);
			
			if (dataObj == null)
				return new SitesDevicesEntity();
		} catch (Exception ex) {
			return new SitesDevicesEntity();
		}
		return dataObj;
	}
}
