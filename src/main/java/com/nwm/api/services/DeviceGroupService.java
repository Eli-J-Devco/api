/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceGroupEntity;

public class DeviceGroupService extends DB {

	/**
	 * @description get list group device
	 * @author long.pham
	 * @since 2021-01-11
	 * @returns array
	 */
	
	public List getList(DeviceGroupEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("DeviceGroup.getListDropdown", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list group device for page error
	 * @author long.pham
	 * @since 2021-04-28
	 * @returns array
	 */
	
	public List getListDropdownForPageError(DeviceGroupEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("DeviceGroup.getListDropdownForPageError", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description get list device group by site id
	 * @author long.pham
	 * @since 2021-04-28
	 * @returns array
	 */
	
	public List getListDeviceGroupBySite(DeviceGroupEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("DeviceGroup.getListDeviceGroupBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

}
