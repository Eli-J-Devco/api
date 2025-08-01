/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.CameraImageEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceParameterEntity;
import com.nwm.api.entities.DeviceYieldEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.TOTP;

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
