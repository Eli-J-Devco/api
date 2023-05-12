/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelCellModemEntity;

public class ModelCellModemService extends DB {


	/**
	 * @description insert data from  to model_cell_modem
	 * @author long.pham
	 * @since 2023-05-11
	 * @param data from cell modem
	 */
	
	public boolean insertModelCellModem(ModelCellModemEntity obj) {
		try {
			 Object insertId = insert("ModelCellModem.insertModelCellModem", obj);
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
