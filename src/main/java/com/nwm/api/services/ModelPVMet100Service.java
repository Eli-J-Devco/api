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
import com.nwm.api.entities.ModelPVMet100Entity;
import com.nwm.api.utils.Lib;

public class ModelPVMet100Service extends DB {

	public ModelPVMet100Entity setModelPVMet100(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelPVMet100Entity dataPVMet100 = new ModelPVMet100Entity();
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				dataPVMet100.setTime(words.get(0).replace("'", ""));
				dataPVMet100.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataPVMet100.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataPVMet100.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataPVMet100.setTransientHorizontalIrradiance(irradiance);
				dataPVMet100.setDailyHorizontalIrradiance(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataPVMet100.setAmbientTemperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataPVMet100.setTemperature(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataPVMet100.setWindAngle(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataPVMet100.setWindSpeed(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));

				
				// set custom field nvm_irradiance
				dataPVMet100.setNvm_irradiance(irradiance);
				dataPVMet100.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataPVMet100.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				
				return dataPVMet100;
				
			} else {
				return new ModelPVMet100Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelPVMet100Entity();
		}
	}

	/**
	 * @description insert data from datalogger to model_kippzonen_rt1_class8009
	 * @author long.pham
	 * @since 2021-04-02
	 * @param data from datalogger
	 */
	
	public boolean insertModelPVMet100(ModelPVMet100Entity obj) {
		try {
			 Object insertId = insert("ModelPVMet100.insertModelPVMet100", obj);
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

