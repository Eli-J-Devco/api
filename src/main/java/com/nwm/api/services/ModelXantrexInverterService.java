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
import com.nwm.api.entities.ModelXantrexInverterEntity;
import com.nwm.api.utils.Lib;

public class ModelXantrexInverterService extends DB {
	/**
	 * @description set data ModelXantrexGT100250500
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelXantrexInverterEntity setModelXantrexInverter(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelXantrexInverterEntity dataModelXantrex = new ModelXantrexInverterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001");
				if(power < 0) { power = 0.0; };
				
				dataModelXantrex.setTime(words.get(0).replace("'", ""));
				dataModelXantrex.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelXantrex.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelXantrex.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelXantrex.setVAB(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelXantrex.setVBC(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelXantrex.setVCA(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelXantrex.setCurrentA(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelXantrex.setCurrentB(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelXantrex.setCurrentC(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelXantrex.setReadPower(power);
				dataModelXantrex.setPVVoltage(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelXantrex.setPVCurrent(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelXantrex.setPVPower(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelXantrex.setGridFrequency(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelXantrex.setSystemState(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelXantrex.setGoalState(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelXantrex.setFaultCode(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelXantrex.setkWh(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelXantrex.setNvmActivePower(power);
				dataModelXantrex.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				
				
				return dataModelXantrex;
				
			} else {
				return new ModelXantrexInverterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelXantrexInverterEntity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_xantrex_inverter
	 * @author long.pham
	 * @since 2023-09-13
	 * @param data from datalogger
	 */

	public boolean insertModelXantrexInverter(ModelXantrexInverterEntity obj) {
		try {
			ModelXantrexInverterEntity dataObj = (ModelXantrexInverterEntity) queryForObject("ModelXantrexInverter.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 }
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
			Object insertId = insert("ModelXantrexInverter.insertModelXantrexInverter", obj);
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
