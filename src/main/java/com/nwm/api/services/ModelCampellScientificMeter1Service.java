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
import com.nwm.api.entities.ModelCampellScientificMeter1Entity;
import com.nwm.api.utils.Lib;

public class ModelCampellScientificMeter1Service extends DB {

	/**
	 * @description set data ModelCampellScientificMeter1
	 * @author long.pham
	 * @since 2023-04-24
	 * @param data
	 */
	
	public ModelCampellScientificMeter1Entity setModelCampellScientificMeter1(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelCampellScientificMeter1Entity dataModelCSM1 = new ModelCampellScientificMeter1Entity();
				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				if(power < 0) { power = 0.0; };
				dataModelCSM1.setTime(words.get(0).replace("'", ""));
				dataModelCSM1.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelCSM1.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelCSM1.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelCSM1.setMeter1_ACPower(power);
				dataModelCSM1.setTotal_Energy(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelCSM1.setNvmActivePower(power);
				dataModelCSM1.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				return dataModelCSM1;
				
			} else {
				return new ModelCampellScientificMeter1Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelCampellScientificMeter1Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_advanced_energy_solaron
	 * @author long.pham
	 * @since 2023-04-24
	 * @param data from datalogger
	 */

	public boolean insertModelCampellScientificMeter1(ModelCampellScientificMeter1Entity obj) {
		try {
			ModelCampellScientificMeter1Entity dataObj = (ModelCampellScientificMeter1Entity) queryForObject("ModelCampellScientificMeter1.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 }
			 }
			 if(measuredProduction < 0 ) { measuredProduction = 0;}
			 obj.setMeasuredProduction(measuredProduction);
			 
			Object insertId = insert("ModelCampellScientificMeter1.insertModelCampellScientificMeter1", obj);
			if (insertId == null) {
				return false;
			}
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}

}
