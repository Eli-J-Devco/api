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
import com.nwm.api.entities.ModelPextronUrp6000Entity;
import com.nwm.api.utils.Lib;

public class ModelPextronUrp6000Service extends DB {
	/**
	 * @description set data ModelAbbEmaxCbEkip
	 * @author duy.phan
	 * @since 2025-08-04
	 * @param data
	 */
	
	public ModelPextronUrp6000Entity setModelPextronUrp6000(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelPextronUrp6000Entity dataModel = new ModelPextronUrp6000Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setPhaseACurrent(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setPhaseBCurrent(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setPhaseCCurrent(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setGroundCurrent(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setNeutralCurrent(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setPhaseAVoltage(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setPhaseBVoltage(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setPhaseCVoltage(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setResidualVoltage(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setNegativeSequenceCurrent(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setLineFrequency(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setPhaseAAngle(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setPhaseBAngle(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setPhaseCAngle(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setAuxillaryPowerSupply(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));;
				dataModel.setActivePowerPhaseA(power);
				dataModel.setActivePowerPhaseB(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setActivePowerPhaseC(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setInternalRelayTemperature(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setPhaseAi2t(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setPhaseBi2t(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setPhaseCi2t(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setNumberofCircuitBreakerOpenings(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setInductiveEnergyStorage(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setCapacitiveEnergyStorage(energy);
				dataModel.setInductiveReactiveEnergyStorage(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel.setCapactiveReactiveEnergyStorage(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setOutputRelay1(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setOutputRelay2(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setOutputRelay3(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));				
				dataModel.setOutputRelay4(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));			
				dataModel.setOutputRelay5(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setHotLineTag(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModel.setEnableRemoteReset(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));	
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelPextronUrp6000Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelPextronUrp6000Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_pextron_urp6000
	 * @author duy.phan
	 * @since 2025-08-04
	 * @param data from datalogger
	 */
	
	public boolean insertModelPextronUrp6000(ModelPextronUrp6000Entity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setCapacitiveEnergyStorage(energy);
			}
			
			ModelPextronUrp6000Entity dataObj = (ModelPextronUrp6000Entity) queryForObject("ModelPextronUrp6000.getLastRow", obj);
			// filter data 
			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() < dataObj.getNvmActiveEnergy() || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setCapacitiveEnergyStorage(dataObj.getNvmActiveEnergy());
			}
						
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelPextronUrp6000.insertModelPextronUrp6000", obj);
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
