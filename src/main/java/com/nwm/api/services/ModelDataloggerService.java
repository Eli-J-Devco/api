/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelDataloggerEntity;

public class ModelDataloggerService extends DB {

	/**
	 * @description insert data from datalogger to model_datalogger
	 * @author long.pham
	 * @since 2022-08-04
	 * @param data from datalogger
	 */
	
	public boolean insertModelDatalogger(ModelDataloggerEntity obj) {
		try {
			 Object insertId = insert("ModelDatalogger.insertModelDatalogger", obj);
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
