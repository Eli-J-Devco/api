/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSmaInverterStp24ktlus10Entity;

public class ModelSmaInverterStp24ktlus10Service extends DB {

	/**
	 * @description insert data from datalogger to model_sma_inverter_stp24ktlus10
	 * @author long.pham
	 * @since 2023-01-31
	 * @param data from datalogger
	 */
	
	public boolean insertModelSmaInverterStp24ktlus10(ModelSmaInverterStp24ktlus10Entity obj) {
		try {
			 Object insertId = insert("ModelSmaInverterStp24ktlus10.insertModelSmaInverterStp24ktlus10", obj);
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
