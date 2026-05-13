/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSolarEdgeAPIMeterEntity;
import com.nwm.api.utils.Lib;

import java.util.List;

public class ModelSolarEdgeAPIMeterService extends DB {

	/**
	 * @description set data ModelSolarEdgeAPIMeter
	 * @author Minh Le
	 * @since 2026-05-13
	 * @param data
	 */
	
	public ModelSolarEdgeAPIMeterEntity setModelSolarEdgeAPIMeter(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSolarEdgeAPIMeterEntity dataModel = new ModelSolarEdgeAPIMeterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
							
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));

				dataModel.setTotalEnergy(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setActivePower(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				
				return dataModel;
				
			} else {
				return new ModelSolarEdgeAPIMeterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSolarEdgeAPIMeterEntity();
		}
	}
	/**
	 * @description insert data from datalogger to ModelSolarEdgeAPIMeter
	 * @author Minh Le
	 * @since 2026-05-13
	 * @param data from datalogger
	 */
	
	public boolean insertModelSolarEdgeAPIMeter(ModelSolarEdgeAPIMeterEntity obj) {
		try {
			 Object insertId = insert("ModelSolarEdgeAPIMeter.insertModelSolarEdgeAPIMeter", obj);
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
