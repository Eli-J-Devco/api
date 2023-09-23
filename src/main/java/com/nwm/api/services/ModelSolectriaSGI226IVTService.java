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
import com.nwm.api.entities.ModelSolectriaSGI226IVTEntity;
import com.nwm.api.utils.Lib;

public class ModelSolectriaSGI226IVTService extends DB {
	
	/**
	 * @description set data ModelSolectriaSGI226IVT
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelSolectriaSGI226IVTEntity setModelSolectriaSGI226IVT(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSolectriaSGI226IVTEntity dataModelSolectria226 = new ModelSolectriaSGI226IVTEntity();
				
				DecimalFormat df = new DecimalFormat("#.0");
				double power = !Lib.isBlank(words.get(5)) ? Double.parseDouble(df.format(Double.parseDouble(words.get(5)) / 1000)) : 0.001;
				if(power < 0) { power = 0.0; }
				
				dataModelSolectria226.setTime(words.get(0).replace("'", ""));
				dataModelSolectria226.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelSolectria226.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelSolectria226.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelSolectria226.setDCVoltage(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelSolectria226.setACPowerOutput(power);
				dataModelSolectria226.setACGridFrequency(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelSolectria226.setACPowerStageCurrent(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelSolectria226.setL1toL2ACVoltage(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelSolectria226.setL2toL3ACVoltage(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelSolectria226.setL1toL3ACVoltage(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelSolectria226.setPhaseSequence(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelSolectria226.setCumulativeACEnergy(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelSolectria226.setCumulativeOngridHours(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelSolectria226.setFanOntimeHours(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelSolectria226.setACContactorCycles(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelSolectria226.setSlaveID(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelSolectria226.setCriticalAlarms(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelSolectria226.setInformativeAlarms(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));

				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelSolectria226.setNvmActivePower(power);
				dataModelSolectria226.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				
				return dataModelSolectria226;
				
			} else {
				return new ModelSolectriaSGI226IVTEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSolectriaSGI226IVTEntity();
		}
	}

	/**
	 * @description insert data from datalogger to model_advanced_energy_solaron
	 * @author long.pham
	 * @since 2021-12-20
	 * @param data from datalogger
	 */
	
	public boolean insertModelSolectriaSGI226IVT(ModelSolectriaSGI226IVTEntity obj) {
		try {
			ModelSolectriaSGI226IVTEntity dataObj = (ModelSolectriaSGI226IVTEntity) queryForObject("ModelSolectriaSGI226IVT.getLastRow", obj);
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
			 
			 Object insertId = insert("ModelSolectriaSGI226IVT.insertModelSolectriaSGI226IVT", obj);
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
