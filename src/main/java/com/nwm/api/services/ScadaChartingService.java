/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ScadaChartingEntity;

public class ScadaChartingService extends DB {
	
	/**
	 * @description get site detail
	 * @author Hung.Bui
	 * @since 2024-05-03
	 * @param obj { hash_id_site }
	 * @return
	 */
	public ScadaChartingEntity getSiteDetail(ScadaChartingEntity obj) {
		try {
			return (ScadaChartingEntity) queryForObject("ScadaCharting.getSiteDetail", obj);
		} catch (Exception ex) {
			return null;
		}
	}
}
