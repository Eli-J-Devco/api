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
import com.nwm.api.entities.ModelWeatherStationBSPEntity;
import com.nwm.api.utils.Lib;

public class ModelWeatherStationBSPService extends DB {

	/**
	 * @description set data ModelWeatherStationBSP
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelWeatherStationBSPEntity setModelWeatherStationBSP(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelWeatherStationBSPEntity dataModel = new ModelWeatherStationBSPEntity();
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				double temperature = !Lib.isBlank(words.get(7)) ? Double.parseDouble(words.get(7)) : 0.001;
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setDailyIrradiationAmount(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
				dataModel.setTotalIrradiance(irradiance);
				dataModel.setPVmoduletemperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0"));
				dataModel.setAmbientTemperature(temperature);
				dataModel.setWindSpeed(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0"));
				dataModel.setWindDirection(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0"));
				
				
				// set custom field nvm_irradiance
				dataModel.setNvm_irradiance(irradiance);
				dataModel.setNvm_temperature(temperature);
				dataModel.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0"));
				
				return dataModel;
				
			} else {
				return new ModelWeatherStationBSPEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelWeatherStationBSPEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_weather_station_bsp
	 * @author long.pham
	 * @since 2020-12-11
	 * @param data from datalogger
	 */
	
	public boolean insertModelWeatherStationBSP(ModelWeatherStationBSPEntity obj) {
		try {
			 Object insertId = insert("ModelWeatherStationBSP.insertModelWeatherStationBSP", obj);
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
