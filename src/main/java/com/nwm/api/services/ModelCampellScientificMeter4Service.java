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
import com.nwm.api.entities.ModelCampellScientificMeter4Entity;
import com.nwm.api.utils.Lib;

public class ModelCampellScientificMeter4Service extends DB {

	/**
	 * @description set data ModelCampellScientificMeter4
	 * @author long.pham
	 * @since 2023-04-24
	 * @param data
	 */
	
	public ModelCampellScientificMeter4Entity setModelCampellScientificMeter4(String line, double offset_data_old) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelCampellScientificMeter4Entity dataModelCSM4 = new ModelCampellScientificMeter4Entity();
				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001");
				if(energy > 0) { energy = energy + offset_data_old; }
				
				dataModelCSM4.setTime(words.get(0).replace("'", ""));
				dataModelCSM4.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelCSM4.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelCSM4.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelCSM4.setMeter4_ACPower(power);
				dataModelCSM4.setTotal_Energy(energy);
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelCSM4.setNvmActivePower(power);
				dataModelCSM4.setNvmActiveEnergy(energy);
				return dataModelCSM4;
				
			} else {
				return new ModelCampellScientificMeter4Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelCampellScientificMeter4Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_advanced_energy_solaron
	 * @author long.pham
	 * @since 2023-04-24
	 * @param data from datalogger
	 */

	public boolean insertModelCampellScientificMeter4(ModelCampellScientificMeter4Entity obj) {
		try {
			ModelCampellScientificMeter4Entity dataObj = (ModelCampellScientificMeter4Entity) queryForObject("ModelCampellScientificMeter4.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }
			 
			 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
				 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 obj.setTotal_Energy(dataObj.getNvmActiveEnergy());
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			Object insertId = insert("ModelCampellScientificMeter4.insertModelCampellScientificMeter4", obj);
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
