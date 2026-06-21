/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelAtonometricsRC22MEntity;
import com.nwm.api.utils.Lib;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelAtonometricsRC22MService extends DB {
	/**
	 * @description set data ModelAtonometricsRC22M
	 * @author Minh Le
	 * @since 2026-05-13
	 * @param data
	 */
	
	public ModelAtonometricsRC22MEntity setModelAtonometricsRC22M(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelAtonometricsRC22MEntity dataModel = new  ModelAtonometricsRC22MEntity();
				
				 Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
					
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));

				dataModel.setIrradiance(irradiance);
				dataModel.setShortCircuitCurrent(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setPVCellBodyTemperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setBOMSensor(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setAmbientAirTempSensor(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setWindSpeed(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				
				// set custom field
				dataModel.setNvm_irradiance(irradiance);
				dataModel.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				
				return dataModel;
				
			} else {
				return new  ModelAtonometricsRC22MEntity();
			}
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new  ModelAtonometricsRC22MEntity();
		}
	}

	/**
	 * @description insert data from datalogger to ModelAtonometricsRC22M
	 * @author Minh Le
	 * @since 2026-05-13
	 * @param data from datalogger
	 */
	
	public boolean insertModelAtonometricsRC22M(ModelAtonometricsRC22MEntity obj) {
		try {
			 Object insertId = insert("ModelAtonometricsRC22M.insertModelAtonometricsRC22M", obj);
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
