/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSolarEdgeAPIWeatherEntity;
import com.nwm.api.utils.Lib;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelSolarEdgeAPIWeatherService extends DB {
	/**
	 * @description set data ModelSolarEdgeAPIWeather
	 * @author Minh Le
	 * @since 2026-05-13
	 * @param data
	 */
	
	public ModelSolarEdgeAPIWeatherEntity setModelSolarEdgeAPIWeather(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSolarEdgeAPIWeatherEntity dataModel = new  ModelSolarEdgeAPIWeatherEntity();
				
				 Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
					
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));

				dataModel.setAmbientTemperature(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setPlaneOfArrayIrradiance(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				
				// set custom field
				dataModel.setNvm_irradiance(irradiance);
				dataModel.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setNvm_panel_temperature(Double.parseDouble("0.001"));
				
				return dataModel;
				
			} else {
				return new  ModelSolarEdgeAPIWeatherEntity();
			}
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new  ModelSolarEdgeAPIWeatherEntity();
		}
	}

	/**
	 * @description insert data from datalogger to ModelSolarEdgeAPIWeather
	 * @author Minh Le
	 * @since 2026-05-13
	 * @param data from datalogger
	 */
	
	public boolean insertModelSolarEdgeAPIWeather(ModelSolarEdgeAPIWeatherEntity obj) {
		try {
			 Object insertId = insert("ModelSolarEdgeAPIWeather.insertModelSolarEdgeAPIWeather", obj);
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
