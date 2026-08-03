/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelCellModemWanIPEntity;

public class ModelCellModemWanIPService  extends DB{
	/**
	 * @description insert data from  to model_cell_modem_wan_ip
	 * @author duy.phan
	 * @since 2023-05-11
	 * @param data from cell modem
	 */
	
	public boolean insertModelCellModemWanIP(ModelCellModemWanIPEntity obj) {
		try {
			 Object insertId = insert("ModelCellModemWanIP.insertModelCellModemWanIP", obj);
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
