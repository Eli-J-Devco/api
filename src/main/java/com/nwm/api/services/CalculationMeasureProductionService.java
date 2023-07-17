/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.CalculationMeasuredProductionEntity;

public class CalculationMeasureProductionService extends DB {
	
	

	/**
	 * @description get list device meter or inverter.
	 * @author long.pham
	 * @since 2023-07-13
	 */
	
	public List getListDevice(CalculationMeasuredProductionEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CalculationMeasuredProduction.getListDevice", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		
		return dataList;
	}
	
	
	
	/**
	 * @description update device
	 * @author long.pham
	 * @since 2023-07-13
	 * @param {}
	 */
	public boolean updateMeasuredProduction(CalculationMeasuredProductionEntity obj) {
		try {
			return update("CalculationMeasuredProduction.updateModelMeasuredProduction", obj) > 0;
		} catch (Exception ex) {
			log.error("CalculationMeasuredProduction.updateModelMeasuredProduction", ex);
			return false;
		}
	}
	
}
