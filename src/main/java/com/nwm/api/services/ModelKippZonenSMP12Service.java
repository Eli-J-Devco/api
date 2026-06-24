/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelKippZonenSMP12Entity;
import com.nwm.api.utils.Lib;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelKippZonenSMP12Service extends DB {
	/**
	 * @description set data ModelKippZonenSMP12
	 * @author Minh Le
	 * @since 2026-05-13
	 * @param data
	 */
	
	public ModelKippZonenSMP12Entity setModelKippZonenSMP12(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelKippZonenSMP12Entity dataModel = new  ModelKippZonenSMP12Entity();
				
				 Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
					
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));

				dataModel.setDeviceType(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setDataModelVersion(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setOperationalMode(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setStatusFlags(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setScaleFactor(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setRawIrradiance(irradiance);
				dataModel.setIrradianceStandardDeviation(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setBodyTemperature(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setExternalPowerVoltage(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setTilt(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setInternalSensorRelativeHumidity(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				
				// set custom field
				dataModel.setNvm_irradiance(irradiance);
				dataModel.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setNvm_panel_temperature(Double.parseDouble("0.001"));
				
				return dataModel;
				
			} else {
				return new  ModelKippZonenSMP12Entity();
			}
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new  ModelKippZonenSMP12Entity();
		}
	}

	/**
	 * @description insert data from datalogger to ModelKippZonenSMP12
	 * @author Minh Le
	 * @since 2026-05-13
	 * @param data from datalogger
	 */
	
	public boolean insertModelKippZonenSMP12(ModelKippZonenSMP12Entity obj) {
		try {
			 Object insertId = insert("ModelKippZonenSMP12.insertModelKippZonenSMP12", obj);
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
