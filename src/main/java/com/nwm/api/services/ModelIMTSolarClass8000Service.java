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
import com.nwm.api.entities.ModelIMTSolarClass8000Entity;
import com.nwm.api.utils.Lib;

public class ModelIMTSolarClass8000Service extends DB {

	/**
	 * @description set data ModelIMTSolarClass8000
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelIMTSolarClass8000Entity setModelIMTSolarClass8000(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelIMTSolarClass8000Entity dataModelIMTSolarClass = new ModelIMTSolarClass8000Entity();
				dataModelIMTSolarClass.setTime(words.get(0).replace("'", ""));
				dataModelIMTSolarClass.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelIMTSolarClass.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelIMTSolarClass.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelIMTSolarClass.setIrradiance(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelIMTSolarClass.setTcell(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				
				// set custom field nvm_irradiance
				dataModelIMTSolarClass.setNvm_irradiance(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelIMTSolarClass.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				
				return dataModelIMTSolarClass;
				
			} else {
				return new ModelIMTSolarClass8000Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelIMTSolarClass8000Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_imt_solar_class8000
	 * @author long.pham
	 * @since 2020-12-11
	 * @param data from datalogger
	 */
	
	public boolean insertModelIMTSolarClass8000(ModelIMTSolarClass8000Entity obj) {
		try {
			 Object insertId = insert("ModelIMTSolarClass8000.insertModelIMTSolarClass8000", obj);
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
