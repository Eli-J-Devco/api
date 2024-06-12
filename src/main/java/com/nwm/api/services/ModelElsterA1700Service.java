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
import com.nwm.api.entities.ModelElsterA1700Entity;
import com.nwm.api.utils.Lib;

public class ModelElsterA1700Service extends DB {

	/**
	 * @description set data ModelElsterA1700
	 * @author Hung.Bui
	 * @since 2023-11-02
	 * @param data
	 */
	
	public ModelElsterA1700Entity setModelElsterA1700(String line, double offset_data_old) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelElsterA1700Entity dataModelElsterA1700 = new ModelElsterA1700Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001");
				if(energy > 0) { energy = energy + offset_data_old; }
				
				
				dataModelElsterA1700.setTime(words.get(0).replace("'", ""));
				dataModelElsterA1700.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelElsterA1700.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelElsterA1700.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelElsterA1700.setPhaseAVoltage(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelElsterA1700.setPhaseBVoltage(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelElsterA1700.setPhaseCVoltage(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelElsterA1700.setABLineVoltage(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelElsterA1700.setBCLineVoltage(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelElsterA1700.setCALineVoltage(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelElsterA1700.setPhaseACurrent(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelElsterA1700.setPhaseBCurrent(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelElsterA1700.setPhaseCCurrent(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelElsterA1700.setPhaseAActivePower(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelElsterA1700.setPhaseBActivePower(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelElsterA1700.setPhaseCActivePower(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelElsterA1700.setTotalActivePower(power);
				dataModelElsterA1700.setTotalReactivePower(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelElsterA1700.setTotalApparentPower(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelElsterA1700.setTotalPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelElsterA1700.setGridFrequency(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelElsterA1700.setTotalForwardActiveEnergy(energy);
				dataModelElsterA1700.setTotalReverseActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelElsterA1700.setTotalForwardReactiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelElsterA1700.setTotalReverseReactiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelElsterA1700.setPhaseAForwardActivePower(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelElsterA1700.setPhaseBForwardActivePower(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelElsterA1700.setPhaseCForwardActivePower(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelElsterA1700.setPhaseAReverseActivePower(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelElsterA1700.setPhaseBReverseActivePower(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelElsterA1700.setPhaseCReverseActivePower(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelElsterA1700.setPhaseAForwardReactivePower(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelElsterA1700.setPhaseBForwardReactivePower(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelElsterA1700.setPhaseCForwardReactivePower(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelElsterA1700.setPhaseAReverseReactivePower(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelElsterA1700.setPhaseBReverseReactivePower(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelElsterA1700.setPhaseCReverseReactivePower(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelElsterA1700.setFlatForwardActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelElsterA1700.setPeakForwardActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelElsterA1700.setValleyForwardActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelElsterA1700.setFlatReverseActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModelElsterA1700.setPeakReverseActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModelElsterA1700.setValleyReverseActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelElsterA1700.setNvmActivePower(power);
				dataModelElsterA1700.setNvmActiveEnergy(energy);
				
				
				return dataModelElsterA1700;
				
			} else {
				return new ModelElsterA1700Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelElsterA1700Entity();
		}
	}
	/**
	 * @description insert data from datalogger to ModelElsterA1700
	 * @author Hung.Bui
	 * @since 2023-11-02
	 * @param data from datalogger
	 */
	
	public boolean insertModelElsterA1700(ModelElsterA1700Entity obj) {
		try {
			ModelElsterA1700Entity dataObj = (ModelElsterA1700Entity) queryForObject("ModelElsterA1700.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelElsterA1700.insertModelElsterA1700", obj);
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
