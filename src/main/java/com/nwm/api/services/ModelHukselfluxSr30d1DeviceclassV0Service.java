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
import com.nwm.api.entities.ModelHukselfluxSr30d1DeviceclassV0Entity;
import com.nwm.api.utils.Lib;


public class ModelHukselfluxSr30d1DeviceclassV0Service extends DB {
	
	/**
	 * @description set data insertModelHukselfluxSr30d1DeviceclassV0
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data from weather station
	 */
	
	public ModelHukselfluxSr30d1DeviceclassV0Entity setModelHukselfluxSr30d1DeviceclassV0(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelHukselfluxSr30d1DeviceclassV0Entity dataModelHukselfluxSr30d1DeviceclassV0 = new ModelHukselfluxSr30d1DeviceclassV0Entity();
				
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				dataModelHukselfluxSr30d1DeviceclassV0.setTime(words.get(0).replace("'", ""));
				dataModelHukselfluxSr30d1DeviceclassV0.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelHukselfluxSr30d1DeviceclassV0.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelHukselfluxSr30d1DeviceclassV0.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelHukselfluxSr30d1DeviceclassV0.setIrradianceTcs(irradiance);
				dataModelHukselfluxSr30d1DeviceclassV0.setIrradianceUs(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelHukselfluxSr30d1DeviceclassV0.setSensorBodyTemperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelHukselfluxSr30d1DeviceclassV0.setSensorElectricalResistance(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelHukselfluxSr30d1DeviceclassV0.setScalingFactorIrradiance(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelHukselfluxSr30d1DeviceclassV0.setScalingFactorTemperature(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelHukselfluxSr30d1DeviceclassV0.setSensorSerialNumber(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelHukselfluxSr30d1DeviceclassV0.setSensorSensitivity(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));										
				dataModelHukselfluxSr30d1DeviceclassV0.setSensorCalibrationDate(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelHukselfluxSr30d1DeviceclassV0.setInternalHumidity(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelHukselfluxSr30d1DeviceclassV0.setTiltAngle(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelHukselfluxSr30d1DeviceclassV0.setTiltAngleaverage(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));										
				dataModelHukselfluxSr30d1DeviceclassV0.setFanSpeedRPM(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelHukselfluxSr30d1DeviceclassV0.setHeaterCurrent(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelHukselfluxSr30d1DeviceclassV0.setFanCurrent(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				
				// set custom field nvm_irradiance
				dataModelHukselfluxSr30d1DeviceclassV0.setNvm_irradiance(irradiance);
				dataModelHukselfluxSr30d1DeviceclassV0.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.0"));
				dataModelHukselfluxSr30d1DeviceclassV0.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				
				return dataModelHukselfluxSr30d1DeviceclassV0;
				
			} else {
				return new ModelHukselfluxSr30d1DeviceclassV0Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelHukselfluxSr30d1DeviceclassV0Entity();
		}
	}
	
	
	/**
	 * @description insert data from weather station to model_hukseflux_sr30d1_deviceclass_v0
	 * @author long.pham
	 * @since 2022-12-16
	 * @param data from weather station
	 */
	
	public boolean insertModelHukselfluxSr30d1DeviceclassV0(ModelHukselfluxSr30d1DeviceclassV0Entity obj) {
		try {
			 Object insertId = insert("ModelHukselfluxSr30d1DeviceclassV0.insertModelHukselfluxSr30d1DeviceclassV0", obj);
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
