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
import com.nwm.api.entities.ModelEatonNova6RecloserEntity;
import com.nwm.api.utils.Lib;

public class ModelEatonNova6RecloserService extends DB {

	/**
	 * @description set data ModelEatonNova6Recloser
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelEatonNova6RecloserEntity setModelEatonNova6Recloser(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelEatonNova6RecloserEntity dataModelEatonNova6Recloser = new ModelEatonNova6RecloserEntity();
				
				Double power = 0.0;
				Double energy = 0.0;
				
				dataModelEatonNova6Recloser.setTime(words.get(0).replace("'", ""));
				dataModelEatonNova6Recloser.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelEatonNova6Recloser.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelEatonNova6Recloser.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelEatonNova6Recloser.setCurrentPhase1(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelEatonNova6Recloser.setCurrentPhase2(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelEatonNova6Recloser.setCurrentPhase3(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelEatonNova6Recloser.setVoltagePhase1(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelEatonNova6Recloser.setVoltagePhase2(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelEatonNova6Recloser.setVoltagePhase3(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelEatonNova6Recloser.setPowerFactorPhase1(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				
				dataModelEatonNova6Recloser.setPowerFactorPhase2(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelEatonNova6Recloser.setPowerFactorPhase3(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelEatonNova6Recloser.setApparentPowerPhase1(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelEatonNova6Recloser.setApparentPowerPhase2(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelEatonNova6Recloser.setApparentPowerPhase3(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelEatonNova6Recloser.setRealPowerPhase1(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelEatonNova6Recloser.setRealPowerPhase2(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelEatonNova6Recloser.setRealPowerPhase3(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelEatonNova6Recloser.setReactivePowerPhase1(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				dataModelEatonNova6Recloser.setReactivePowerPhase2(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelEatonNova6Recloser.setReactivePowerPhase3(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelEatonNova6Recloser.setLineFrequency(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelEatonNova6Recloser.setDemandCurrentPhase1(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelEatonNova6Recloser.setDemandCurrentPhase2(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelEatonNova6Recloser.setDemandCurrentPhase3(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelEatonNova6Recloser.setBatteryVoltage(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelEatonNova6Recloser.setBatteryCurrent(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelEatonNova6Recloser.setTotalTripCounter(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelEatonNova6Recloser.setRecloserClosed(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelEatonNova6Recloser.setRecloserOpened(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				
				dataModelEatonNova6Recloser.setControlisLocked(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelEatonNova6Recloser.setControlOK(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelEatonNova6Recloser.setSystemAlarm(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelEatonNova6Recloser.setPhaseATrip(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelEatonNova6Recloser.setPhaseBTrip(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelEatonNova6Recloser.setPhaseCTrip(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelEatonNova6Recloser.setACPowerAlarm(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelEatonNova6Recloser.setBatteryAlarm(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelEatonNova6Recloser.setPhaseOvercurrentAlarm(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelEatonNova6Recloser.setGroundOvercurrentAlarm(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				
				dataModelEatonNova6Recloser.setNegativeSequenceOCAlarm(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModelEatonNova6Recloser.setClose(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelEatonNova6Recloser.setOpen(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelEatonNova6Recloser.setNvmActivePower(power);
				dataModelEatonNova6Recloser.setNvmActiveEnergy(energy);
				
				
				return dataModelEatonNova6Recloser;
				
			} else {
				return new ModelEatonNova6RecloserEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelEatonNova6RecloserEntity();
		}
	}
	/**
	 * @description insert data from datalogger to model shark 100
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelEatonNova6Recloser(ModelEatonNova6RecloserEntity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
			}
			
			 ModelEatonNova6RecloserEntity dataObj = (ModelEatonNova6RecloserEntity) queryForObject("ModelEatonNova6Recloser.getLastRow", obj);
			// filter data 
			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
			}
			 
			Object insertId = insert("ModelEatonNova6Recloser.insertModelEatonNova6Recloser", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        // Update measuredProduction 
 			if (dataObj != null && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy() >= 0 ) {
 				ModelEatonNova6RecloserEntity objUpdateMeasured = new ModelEatonNova6RecloserEntity();
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
