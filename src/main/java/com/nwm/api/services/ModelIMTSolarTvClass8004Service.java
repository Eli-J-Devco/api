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
import com.nwm.api.entities.ModelIMTSolarTvClass8004Entity;
import com.nwm.api.utils.Lib;

public class ModelIMTSolarTvClass8004Service extends DB {

	/**
	 * @description set data ModelIMTSolarTvClass8004
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelIMTSolarTvClass8004Entity setModelIMTSolarTvClass8004(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelIMTSolarTvClass8004Entity dataModelIMTSolarClass = new ModelIMTSolarTvClass8004Entity();
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				double temperature = !Lib.isBlank(words.get(5)) ? Double.parseDouble(words.get(5)) : 0.001;
				
				dataModelIMTSolarClass.setTime(words.get(0).replace("'", ""));
				dataModelIMTSolarClass.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelIMTSolarClass.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelIMTSolarClass.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelIMTSolarClass.setIrradiance(irradiance);
				dataModelIMTSolarClass.setTcell(temperature);
				dataModelIMTSolarClass.setText(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0"));
				dataModelIMTSolarClass.setWspeed(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0"));
				
				
				// set custom field nvm_irradiance
				dataModelIMTSolarClass.setNvm_irradiance(irradiance);
				dataModelIMTSolarClass.setNvm_temperature(temperature);
				dataModelIMTSolarClass.setNvm_panel_temperature(Double.parseDouble( "0.001"));
				
				return dataModelIMTSolarClass;
				
			} else {
				return new ModelIMTSolarTvClass8004Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelIMTSolarTvClass8004Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to ModelIMTSolarTvClass8004
	 * @author long.pham
	 * @since 2020-12-11
	 * @param data from datalogger
	 */
	
	public boolean insertModelIMTSolarTvClass8004(ModelIMTSolarTvClass8004Entity obj) {
		try {
			 Object insertId = insert("ModelIMTSolarTvClass8004.insertModelIMTSolarTvClass8004", obj);
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
