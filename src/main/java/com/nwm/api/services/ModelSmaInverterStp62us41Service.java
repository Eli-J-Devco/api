/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSmaInverterStp62us41Entity;

public class ModelSmaInverterStp62us41Service extends DB {

	/**
	 * @description insert data from datalogger to model_smaInverter_stp62us41
	 * @author long.pham
	 * @since 2023-06-14
	 * @param data from datalogger
	 */
	
	public boolean insertModelSmaInverterStp62us41(ModelSmaInverterStp62us41Entity obj) {
		try {
			ModelSmaInverterStp62us41Entity dataObj = (ModelSmaInverterStp62us41Entity) queryForObject("ModelSmaInverterStp62us41.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 
//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelSmaInverterStp62us41.insertModelSmaInverterStp62us41", obj);
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
