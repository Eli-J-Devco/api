/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSmaClusterControllerEntity;

public class ModelSmaClusterControllerService extends DB {

	/**
	 * @description insert data from datalogger to model_sma_cluster_controller
	 * @author long.pham
	 * @since 2023-01-31
	 * @param data from datalogger
	 */
	
	public boolean insertModelSmaClusterController(ModelSmaClusterControllerEntity obj) {
		try {
			ModelSmaClusterControllerEntity dataObj = (ModelSmaClusterControllerEntity) queryForObject("ModelSmaClusterController.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 
//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelSmaClusterController.insertModelSmaClusterController", obj);
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
