/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceParameterEntity;
import com.nwm.api.entities.ItemLastRowSiteQueryEntity;

public class DeviceParameterService extends DB {
	
	/**
	 * @description get list parameter by device
	 * @author long.pham
	 * @since 2020-11-06
	 * @param array id_device
	 * @return array
	 */
	
	public List getListByDevice(DeviceParameterEntity obj) {
		List dataList = new ArrayList();
		try {
			List dataDevice = obj.getId_devices();
			if(dataDevice.size() > 0) {
				for(int i =0; i< dataDevice.size(); i++) {
					Map<String, Object> itemDevice = (Map<String, Object>) dataDevice.get(i);
					// Get parameter by device id
					List dataListParamert = queryForList("DeviceParameter.getListParameterByDevice", itemDevice);
					itemDevice.put("parameter", dataListParamert);
					dataList.add(itemDevice);
				}
			}
			
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	

}
