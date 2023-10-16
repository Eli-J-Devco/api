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
import com.nwm.api.entities.ModelPyranometerPoaEntity;
import com.nwm.api.utils.Lib;

public class ModelPyranometerPoaService extends DB {

	/**
	 * @description set data ModelPyranometer
	 * @author long.pham
	 * @since 2023-10-16
	 * @param data
	 */
	
	public ModelPyranometerPoaEntity setModelPyranometer(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelPyranometerPoaEntity dataModelPyranometer = new ModelPyranometerPoaEntity();
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				dataModelPyranometer.setTime(words.get(0).replace("'", ""));
				dataModelPyranometer.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelPyranometer.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelPyranometer.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelPyranometer.setPoa(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelPyranometer.setPoint1(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelPyranometer.setPoint2(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelPyranometer.setPoint3(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				
				
				// set custom field nvm_irradiance
				dataModelPyranometer.setNvm_irradiance(irradiance);
				dataModelPyranometer.setNvm_temperature(0.001);
				dataModelPyranometer.setNvm_panel_temperature(0.001);
				return dataModelPyranometer;
				
			} else {
				return new ModelPyranometerPoaEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelPyranometerPoaEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_pyranometer_poa
	 * @author long.pham
	 * @since 2023-10-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelPyranometer(ModelPyranometerPoaEntity obj) {
		try {
			 Object insertId = insert("ModelPyranometer.insertModelPyranometer", obj);
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
