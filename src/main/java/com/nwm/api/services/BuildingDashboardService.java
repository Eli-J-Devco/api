/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceGroupEntity;
import com.nwm.api.entities.SiteEntity;

public class BuildingDashboardService extends DB {

	/**
	 * @description get list load meter device
	 * @author long.pham
	 * @since 2024-10-29
	 * @returns array
	 */
	
	public List getListLoadMeterDevices(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BuildingDashboard.getListLoadMeterDevices", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	
	/**
	 * @description get list data field
	 * @author long.pham
	 * @since 2024-10-29
	 * @returns array
	 */
	
	public List getListDeviceDataField(DeviceEntity obj) throws JsonProcessingException {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BuildingDashboard.getListDeviceDataField", obj);
			if (dataList == null)
				return new ArrayList();
			
			dataList.forEach(item -> {
				try {
					List dataListField = queryForList("BuildingDashboard.getListField", item);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
}
