/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.UploadN3uronEntity;

public class UploadJSONService extends DB {
	
	/**
	 * @description upload data from N3uron
	 * @author Hung.Bui
	 * @since 2024-05-21
	 * @params obj { site_name, device_name, parameters: List<DeviceParameterN3uronEntity> }
	 */
	public boolean n3euronUploads(UploadN3uronEntity obj) {
		try {
			Integer insert = (Integer) insert("UploadJSON.insertN3euronData", obj);
			if (insert > 0) return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
