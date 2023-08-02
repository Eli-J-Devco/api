/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSungrowSg50cxEntity;

public class ModelSungrowSg50cxService extends DB {

	/**
	 * @description insert data from datalogger to model_sungrow_umg604
	 * @author long.pham
	 * @since 2023-01-11
	 * @param data from datalogger
	 */
	
	public boolean insertModelSungrowSg50cx(ModelSungrowSg50cxEntity obj) {
		try {
			ModelSungrowSg50cxEntity dataObj = (ModelSungrowSg50cxEntity) queryForObject("ModelSungrowSg50cx.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 }
			 }
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelSungrowSg50cx.insertModelSungrowSg50cx", obj);
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
