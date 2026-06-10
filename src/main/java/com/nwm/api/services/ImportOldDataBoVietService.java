/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelHuaweiSun200028ktlEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ImportOldDataBoVietService extends DB {
	
	
	/**
	 * @description get list device by id_device_group = 44
	 * @author long.pham
	 * @since 2023-08-23
	 */
	
	public List getListDeviceBySite(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("ImportOldDataBoViet.getListDeviceBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	

	/**
	 * @description insert data from datalogger to db
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelHuaweiSun200028ktl(DeviceEntity obj) {
		try {
		 	Object insertId = insert("ImportOldDataBoViet.insertModelHuaweiSun200028ktl", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
	
	
	
	
	/**
	 * @description insert data from datalogger to db
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelImtsolarTvClass8004(DeviceEntity obj) {
		try {
		 	Object insertId = insert("ImportOldDataBoViet.insertModelImtsolarTvClass8004", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
	

}
