/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSungrowSg110cxEntity;

public class ModelSungrowSg110cxService extends DB {

	/**
	 * @description insert data from datalogger to model_sungrow_umg604
	 * @author long.pham
	 * @since 2023-01-11
	 * @param data from datalogger
	 */
	
	public boolean insertModelSungrowSg110cx(ModelSungrowSg110cxEntity obj) {
		try {
			ModelSungrowSg110cxEntity dataObj = (ModelSungrowSg110cxEntity) queryForObject("ModelSungrowSg110cx.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
			 }
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelSungrowSg110cx.insertModelSungrowSg110cx", obj);
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
