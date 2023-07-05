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
import com.nwm.api.entities.ModelLufftWS501UMBWeatherEntity;
import com.nwm.api.utils.Lib;

public class ModelLufftWS501UMBWeatherService extends DB {
	
	/**
	 * @description set data ModelLufftWS501UMBWeather
	 * @author Hung.Bui
	 * @since 2023-06-07
	 * @param data
	 */
	
	public ModelLufftWS501UMBWeatherEntity setModelLufftWS501UMBWeather(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelLufftWS501UMBWeatherEntity dataModelLufft = new ModelLufftWS501UMBWeatherEntity();
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				dataModelLufft.setTime(words.get(0).replace("'", ""));
				dataModelLufft.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelLufft.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelLufft.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelLufft.setRelativeHumidityActual(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelLufft.setRelativeHumidityMin(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelLufft.setRelativeHumidityMax(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelLufft.setRelativeHumidityAvg(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelLufft.setRelativeAirPressureActual(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelLufft.setRelativeAirPressureMin(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelLufft.setRelativeAirPressureMax(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelLufft.setRelativeAirPressureAvg(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelLufft.setWindDirectionActual(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelLufft.setWindDirectionMin(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelLufft.setWindDirectionMax(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelLufft.setWindDirectionVct(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelLufft.setWindDirectionFast(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelLufft.setWindDirectionCompassCorrected(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelLufft.setCompass(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelLufft.setWindMeasurementQuality(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelLufft.setPrecipitationType(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelLufft.setGlobalRadiation(irradiance);
				dataModelLufft.setGlobalRadiation2(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelLufft.setGlobalRadiation3(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelLufft.setGlobalRadiation4(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelLufft.setAirTemperatureCActual(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelLufft.setAirTemperatureCMin(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelLufft.setAirTemperatureCMax(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelLufft.setAirTemperatureCAvg(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelLufft.setDewPointActual(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelLufft.setDewPointMin(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelLufft.setDewPointMax(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelLufft.setDewPointAvg(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelLufft.setWindChillTemperature(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelLufft.setHeatingTemperatureWind(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelLufft.setHeatingTemperatureR2S(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelLufft.setWindSpeedActual(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelLufft.setWindSpeedMin(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelLufft.setWindSpeedMax(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelLufft.setWindSpeedAvg(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelLufft.setWindSpeedVct(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModelLufft.setWindSpeedFast(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModelLufft.setPrecipitationAbsolute(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelLufft.setPrecipitationDifferential(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModelLufft.setPrecipitationIntensity(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelLufft.setAirTemperatureFActual(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModelLufft.setAirTemperatureFMin(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelLufft.setAirTemperatureFMax(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelLufft.setAirTemperatureFAvg(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelLufft.setExternalTemperatureC(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModelLufft.setExternalTemperatureF(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				
				// set custom field nvm_irradiance
				dataModelLufft.setNvm_irradiance(irradiance);
				dataModelLufft.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				
				return dataModelLufft;
				
			} else {
				return new ModelLufftWS501UMBWeatherEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelLufftWS501UMBWeatherEntity();
		}
	}

	/**
	 * @description insert data from datalogger to ModelLufftWS501UMBWeather
	 * @author  Hung.Bui
	 * @since 2023-06-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelLufftWS501UMBWeather(ModelLufftWS501UMBWeatherEntity obj) {
		try {
			 Object insertId = insert("ModelLufftWS501UMBWeather.insertModelLufftWS501UMBWeather", obj);
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
