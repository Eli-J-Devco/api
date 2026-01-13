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
import com.nwm.api.entities.ModelHuaweiSmartloggerWeatherEntity;
import com.nwm.api.utils.Lib;

public class ModelHuaweiSmartloggerWeatherService extends DB {

	/**
	 * @description set data ModelHuaweiSmartloggerWeather
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelHuaweiSmartloggerWeatherEntity setModelHuaweiSmartloggerWeather(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelHuaweiSmartloggerWeatherEntity dataModelHuaweiSmartloggerWeather = new ModelHuaweiSmartloggerWeatherEntity();
				
				dataModelHuaweiSmartloggerWeather.setTime(words.get(0).replace("'", ""));
				dataModelHuaweiSmartloggerWeather.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelHuaweiSmartloggerWeather.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelHuaweiSmartloggerWeather.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelHuaweiSmartloggerWeather.setWindspeed(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelHuaweiSmartloggerWeather.setWinddirection(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelHuaweiSmartloggerWeather.setPVmoduletemperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelHuaweiSmartloggerWeather.setAmbienttemperature(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelHuaweiSmartloggerWeather.setTotalirradiance(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelHuaweiSmartloggerWeather.setDailyirradiationamount(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelHuaweiSmartloggerWeather.setTotalirradiance2(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				dataModelHuaweiSmartloggerWeather.setDailyirradiationamount2(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelHuaweiSmartloggerWeather.setCustom1(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelHuaweiSmartloggerWeather.setCustom2(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelHuaweiSmartloggerWeather.setDailyirradiationamount1(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelHuaweiSmartloggerWeather.setDailyirradiationamount3(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));

				// set custom field
				dataModelHuaweiSmartloggerWeather.setNvm_irradiance(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelHuaweiSmartloggerWeather.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelHuaweiSmartloggerWeather.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				
				return dataModelHuaweiSmartloggerWeather;
				
			} else {
				return new ModelHuaweiSmartloggerWeatherEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelHuaweiSmartloggerWeatherEntity();
		}
	}
	/**
	 * @description insert data from datalogger to model shark 100
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelHuaweiSmartloggerWeather(ModelHuaweiSmartloggerWeatherEntity obj) {
		try {			 
			 Object insertId = insert("ModelHuaweiSmartloggerWeather.insertModelHuaweiSmartloggerWeather", obj);
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