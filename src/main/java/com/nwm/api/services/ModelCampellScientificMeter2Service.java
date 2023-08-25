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
import com.nwm.api.entities.ModelCampellScientificMeter2Entity;
import com.nwm.api.utils.Lib;

public class ModelCampellScientificMeter2Service extends DB {

	/**
	 * @description set data ModelCampellScientificMeter2
	 * @author long.pham
	 * @since 2023-04-24
	 * @param data
	 */
	
	public ModelCampellScientificMeter2Entity setModelCampellScientificMeter2(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelCampellScientificMeter2Entity dataModelCSM2 = new ModelCampellScientificMeter2Entity();
				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				if(power < 0) { power = 0.0; };
				dataModelCSM2.setTime(words.get(0).replace("'", ""));
				dataModelCSM2.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelCSM2.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelCSM2.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelCSM2.setMeter2_ACPower(power);
				dataModelCSM2.setTotal_Energy(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelCSM2.setNvmActivePower(power);
				dataModelCSM2.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				return dataModelCSM2;
				
			} else {
				return new ModelCampellScientificMeter2Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelCampellScientificMeter2Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_advanced_energy_solaron
	 * @author long.pham
	 * @since 2023-04-24
	 * @param data from datalogger
	 */

	public boolean insertModelCampellScientificMeter2(ModelCampellScientificMeter2Entity obj) {
		try {
			ModelCampellScientificMeter2Entity dataObj = (ModelCampellScientificMeter2Entity) queryForObject("ModelCampellScientificMeter2.getLastRow", obj);
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
			 
			Object insertId = insert("ModelCampellScientificMeter2.insertModelCampellScientificMeter2", obj);
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
