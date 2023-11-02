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
import com.nwm.api.entities.ModelSevSg110cxEntity;
import com.nwm.api.utils.Lib;

public class ModelSevSg110cxService extends DB {
	/**
	 * @description set data ModelSevSg110cx
	 * @author long.pham
	 * @since 2023-11-01
	 * @param data
	 */
	
	public ModelSevSg110cxEntity setModelSevSg110cx(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSevSg110cxEntity dataModelSev = new ModelSevSg110cxEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001");
				if(power < 0) { power = 0.0; };
				
				dataModelSev.setTime(words.get(0).replace("'", ""));
				dataModelSev.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelSev.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelSev.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelSev.setTotalYield(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelSev.setDailyYield(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelSev.setArrayInsulationResistance(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelSev.setInteriorTemperature(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelSev.setTotalDCPower(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelSev.setTotalApparentPower(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelSev.setTotalActivePower(power);
				dataModelSev.setTotalReactivePower(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelSev.setTotalPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelSev.setGridFrequency(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelSev.setPhaseAVoltage(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelSev.setPhaseBVoltage(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelSev.setPhaseCVoltage(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelSev.setPhaseACurrent(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelSev.setPhaseBCurrent(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelSev.setPhaseCCurrent(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelSev.setNvmActivePower(power);
				dataModelSev.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				
				
				return dataModelSev;
				
			} else {
				return new ModelSevSg110cxEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSevSg110cxEntity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_sev_sg110cx
	 * @author long.pham
	 * @since 2023-11-01
	 * @param data from datalogger
	 */

	public boolean insertModelSevSg110cx(ModelSevSg110cxEntity obj) {
		try {
			ModelSevSg110cxEntity dataObj = (ModelSevSg110cxEntity) queryForObject("ModelSevSg110cx.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
			Object insertId = insert("ModelSevSg110cx.insertModelSevSg110cx", obj);
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
