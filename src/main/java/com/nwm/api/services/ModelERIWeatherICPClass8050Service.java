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
import com.nwm.api.entities.ModelERIWeatherICPClass8050Entity;
import com.nwm.api.utils.Lib;

public class ModelERIWeatherICPClass8050Service extends DB {

	/**
	 * @description set data ModelERIWeatherICPClass8050
	 * @author Hung.Bui
	 * @since 2023-10-18
	 * @param data
	 */
	
	public ModelERIWeatherICPClass8050Entity setModelERIWeatherICPClass8050(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelERIWeatherICPClass8050Entity dataModelERIWeatherICPClass8050 = new ModelERIWeatherICPClass8050Entity();
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				dataModelERIWeatherICPClass8050.setTime(words.get(0).replace("'", ""));
				dataModelERIWeatherICPClass8050.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelERIWeatherICPClass8050.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelERIWeatherICPClass8050.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelERIWeatherICPClass8050.setPanel_temp(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelERIWeatherICPClass8050.setAmbient_temp(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelERIWeatherICPClass8050.setWind_speed(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelERIWeatherICPClass8050.setSolar_irradiation(irradiance);
				dataModelERIWeatherICPClass8050.setWind_direction(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				
				// set custom field nvm_irradiance
				dataModelERIWeatherICPClass8050.setNvm_irradiance(irradiance);
				dataModelERIWeatherICPClass8050.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelERIWeatherICPClass8050.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				
				return dataModelERIWeatherICPClass8050;
				
			} else {
				return new ModelERIWeatherICPClass8050Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelERIWeatherICPClass8050Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_eri_weather_icp_class8050
	 * @author Hung.Bui
	 * @since 2023-10-18
	 * @param data from datalogger
	 */
	
	public boolean insertModelERIWeatherICPClass8050(ModelERIWeatherICPClass8050Entity obj) {
		try {
			 Object insertId = insert("ModelERIWeatherICPClass8050.insertModelERIWeatherICPClass8050", obj);
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
