/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSmaInverterStp1200tlus10Entity;

public class ModelSmaInverterStp1200tlus10Service extends DB {

	/**
	 * @description insert data from datalogger to model_sma_inverter_stp1200tlus10
	 * @author long.pham
	 * @since 2023-01-31
	 * @param data from datalogger
	 */
	
	public boolean insertModelSmaInverterStp1200tlus10(ModelSmaInverterStp1200tlus10Entity obj) {
		try {
			ModelSmaInverterStp1200tlus10Entity dataObj = (ModelSmaInverterStp1200tlus10Entity) queryForObject("ModelSmaInverterStp1200tlus10.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 
//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelSmaInverterStp1200tlus10.insertModelSmaInverterStp1200tlus10", obj);
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
