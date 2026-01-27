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
import com.nwm.api.entities.ModelCampellScientificMeter3Entity;
import com.nwm.api.utils.Lib;

public class ModelCampellScientificMeter3Service extends DB {

	/**
	 * @description set data ModelCampellScientificMeter3
	 * @author long.pham
	 * @since 2023-04-24
	 * @param data
	 */
	
	public ModelCampellScientificMeter3Entity setModelCampellScientificMeter3(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelCampellScientificMeter3Entity dataModelCSM3 = new ModelCampellScientificMeter3Entity();
				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001");
				
				dataModelCSM3.setTime(words.get(0).replace("'", ""));
				dataModelCSM3.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelCSM3.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelCSM3.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelCSM3.setMeter3_ACPower(power);
				dataModelCSM3.setTotal_Energy(energy);
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelCSM3.setNvmActivePower(power);
				dataModelCSM3.setNvmActiveEnergy(energy);
				return dataModelCSM3;
				
			} else {
				return new ModelCampellScientificMeter3Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelCampellScientificMeter3Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_advanced_energy_solaron
	 * @author long.pham
	 * @since 2023-04-24
	 * @param data from datalogger
	 */

	public boolean insertModelCampellScientificMeter3(ModelCampellScientificMeter3Entity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setTotal_Energy(energy);
			}
			
			ModelCampellScientificMeter3Entity dataObj = (ModelCampellScientificMeter3Entity) queryForObject("ModelCampellScientificMeter3.getLastRow", obj);
			// filter data 
			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setTotal_Energy(dataObj.getNvmActiveEnergy());
			}
			 
			Object insertId = insert("ModelCampellScientificMeter3.insertModelCampellScientificMeter3", obj);
			if (insertId == null) {
				return false;
			}
			
			// Update measuredProduction 
 			if (dataObj != null && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy() >= 0 ) {
 				ModelCampellScientificMeter3Entity objUpdateMeasured = new ModelCampellScientificMeter3Entity();
 				objUpdateMeasured.setDatatablename(obj.getDatatablename());
 				objUpdateMeasured.setTime(dataObj.getTime());
 				objUpdateMeasured.setMeasuredProduction(obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy());
 				update("Device.updateMeasuredProduction", objUpdateMeasured);
 			}
 			
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}

}
