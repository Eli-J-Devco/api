/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSungrowUmg604Entity;
import com.nwm.api.entities.ModelSungrowWeatherPvmet75200Entity;

public class ModelSungrowWeatherPvmet75200Service extends DB {

	/**
	 * @description insert data from datalogger to model_sungrow_umg604
	 * @author long.pham
	 * @since 2023-01-11
	 * @param data from datalogger
	 */
	
	public boolean insertModelSungrowWeatherPvmet75200(ModelSungrowWeatherPvmet75200Entity obj) {
		try {
			 Object insertId = insert("ModelSungrowWeatherPvmet75200.insertModelSungrowWeatherPvmet75200", obj);
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
