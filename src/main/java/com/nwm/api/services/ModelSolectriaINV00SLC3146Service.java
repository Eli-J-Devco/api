/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.text.DecimalFormat;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSolectriaINV00SLC3146Entity;
import com.nwm.api.utils.Lib;

public class ModelSolectriaINV00SLC3146Service extends DB {
	
	/**
	 * @description set data ModelSolectriaINV00SLC3146
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelSolectriaINV00SLC3146Entity setModelSolectriaINV00SLC3146(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSolectriaINV00SLC3146Entity dataModelSolectriaINV00SLC3146 = new ModelSolectriaINV00SLC3146Entity();
				
				double power = !Lib.isBlank(words.get(5)) ? Double.parseDouble(words.get(5)) : 0.001;
				if(power < 0) { power = 0.0; }
				
				dataModelSolectriaINV00SLC3146.setTime(words.get(0).replace("'", ""));
				dataModelSolectriaINV00SLC3146.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelSolectriaINV00SLC3146.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelSolectriaINV00SLC3146.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelSolectriaINV00SLC3146.setDCVoltage(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelSolectriaINV00SLC3146.setRealACPower(power);
				dataModelSolectriaINV00SLC3146.setACGridFrequency(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelSolectriaINV00SLC3146.setACPowerStageCurrent(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelSolectriaINV00SLC3146.setL1toL2ACVoltage(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelSolectriaINV00SLC3146.setL2toL3ACVoltage(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelSolectriaINV00SLC3146.setL1toL3ACVoltage(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelSolectriaINV00SLC3146.setPhaseSequence(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelSolectriaINV00SLC3146.setACEnergy(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelSolectriaINV00SLC3146.setOnGridHours(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelSolectriaINV00SLC3146.setFanOntimeHours(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelSolectriaINV00SLC3146.setACContactorCycles(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelSolectriaINV00SLC3146.setSlaveID(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelSolectriaINV00SLC3146.setInformativeAlarms(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));

				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelSolectriaINV00SLC3146.setNvmActivePower(power);
				dataModelSolectriaINV00SLC3146.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				
				return dataModelSolectriaINV00SLC3146;
				
			} else {
				return new ModelSolectriaINV00SLC3146Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSolectriaINV00SLC3146Entity();
		}
	}

	/**
	 * @description insert data from datalogger to model_advanced_energy_solaron
	 * @author long.pham
	 * @since 2021-12-20
	 * @param data from datalogger
	 */
	
	public boolean insertModelSolectriaINV00SLC3146(ModelSolectriaINV00SLC3146Entity obj) {
		try {
			ModelSolectriaINV00SLC3146Entity dataObj = (ModelSolectriaINV00SLC3146Entity) queryForObject("ModelSolectriaINV00SLC3146.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelSolectriaINV00SLC3146.insertModelSolectriaINV00SLC3146", obj);
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
