/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelIMTSolarTmodulClass8006Entity;
import com.nwm.api.utils.Lib;


public class ModelIMTSolarTmodulClass8006Service extends DB {
	
	
	/**
	 * @description set data ModelIMTSolarTmodulClass8006
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data from weather station
	 */
	
	public ModelIMTSolarTmodulClass8006Entity setDataModelIMTSolarTmodulClass8006(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelIMTSolarTmodulClass8006Entity dataModelIMTSolarTmodulClass8006 = new ModelIMTSolarTmodulClass8006Entity();
				dataModelIMTSolarTmodulClass8006.setTime(words.get(0).replace("'", ""));
				dataModelIMTSolarTmodulClass8006.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelIMTSolarTmodulClass8006.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelIMTSolarTmodulClass8006.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelIMTSolarTmodulClass8006.setModuleTemperature(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				// set custom field nvm_irradiance
				dataModelIMTSolarTmodulClass8006.setNvm_irradiance(Double.parseDouble("0.001"));
				dataModelIMTSolarTmodulClass8006.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				return dataModelIMTSolarTmodulClass8006;
				
			} else {
				return new ModelIMTSolarTmodulClass8006Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelIMTSolarTmodulClass8006Entity();
		}
	}
	
	

	/**
	 * @description insert data from weather station to model_imtsolar_tmodul_class8006
	 * @author long.pham
	 * @since 2022-12-16
	 * @param data from weather station
	 */
	
	public boolean insertModelIMTSolarTmodulClass8006(ModelIMTSolarTmodulClass8006Entity obj) {
		try {
			 Object insertId = insert("ModelIMTSolarTmodulClass8006.insertModelIMTSolarTmodulClass8006", obj);
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
