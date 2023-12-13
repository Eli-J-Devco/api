/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSungrowLogger1000Entity;

public class ModelSungrowLogger1000Service extends DB {
	
	/**
	 * @description insert data from datalogger to model_sungrow_logger1000
	 * @author duy.phan
	 * @since 2023-12-12
	 * @param data from datalogger
	 */
	
	public boolean insertModelSungrowLogger1000(ModelSungrowLogger1000Entity obj) {
		try {
			ModelSungrowLogger1000Entity dataObj = (ModelSungrowLogger1000Entity) queryForObject("ModelSungrowLogger1000.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelSungrowLogger1000.insertModelSungrowLogger1000", obj);
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
