/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ScadaDeviceEntity;

public class ScadaDeviceService extends DB {

	/**
	 * @description get Scada devices list by site
	 * @author Hung.Bui
	 * @since 2024-03-26
	 * @param id_site
	 * @return List<ScadaDeviceEntity>
	 */
	public List<ScadaDeviceEntity> getListDeviceBySite(ScadaDeviceEntity obj) {
		List<ScadaDeviceEntity> dataList = new ArrayList<ScadaDeviceEntity>();
		try {
			dataList = queryForList("ScadaDevice.getListDeviceBySite", obj);
			if (dataList == null)
				return new ArrayList<ScadaDeviceEntity>();
		} catch (Exception ex) {
			return new ArrayList<ScadaDeviceEntity>();
		}
		return dataList;
	}

}
