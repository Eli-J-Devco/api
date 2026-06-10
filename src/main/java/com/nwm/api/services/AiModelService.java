/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;


public class AiModelService extends DB {

	
	public static String[] push(String[] array, String element) {
        int originalLength = array.length;
        String[] newArray = new String[originalLength + 1];
        System.arraycopy(array, 0, newArray, 0, originalLength);
        newArray[originalLength] = element;
        return newArray;
    }
	
	
	
	/**
	 * @description get all site by id employee
	 * @author long.pham
	 * @since 2022-01-29
	 * @param id_employee
	 */
	

	public List getListDevices(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AiModel.buildDataBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	public List getParameterByDeviceGroup(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AiModel.getParameterByDeviceGroup", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	public List getDataByDevice(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AiModel.getDataByDevice", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
}
