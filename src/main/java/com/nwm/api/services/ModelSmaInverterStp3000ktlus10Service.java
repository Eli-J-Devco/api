/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSmaInverterStp3000ktlus10Entity;

public class ModelSmaInverterStp3000ktlus10Service extends DB {

	/**
	 * @description insert data from datalogger to model_sma_inverter_stp24ktlus10
	 * @author long.pham
	 * @since 2023-01-31
	 * @param data from datalogger
	 */
	
	public boolean insertModelSmaInverterStp3000ktlus10(ModelSmaInverterStp3000ktlus10Entity obj) {
		try {
			ModelSmaInverterStp3000ktlus10Entity dataObj = (ModelSmaInverterStp3000ktlus10Entity) queryForObject("ModelSmaInverterStp3000ktlus10.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 }
			 }
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelSmaInverterStp3000ktlus10.insertModelSmaInverterStp3000ktlus10", obj);
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
