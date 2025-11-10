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
import com.nwm.api.entities.ModelHoneywellEMON3200Entity;
import com.nwm.api.utils.Lib;

public class ModelHoneywellEMON3200Service extends DB {
	/**
	 * @description set data
	 * @author Hung.Bui
	 * @since 2025-02-07
	 * @param data
	 */
	
	public ModelHoneywellEMON3200Entity setData(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelHoneywellEMON3200Entity data = new ModelHoneywellEMON3200Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001");
				
				data.setTime(words.get(0).replace("'", ""));
				data.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				data.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				data.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				data.setAuxilaryPulseInput1(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				data.setAuxilaryPulseInput2(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				data.setEnergyDelivered(energy);
				data.setEnergyReceived(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				data.setReactiveEnergyDelivered(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				data.setReactiveEnergyReceived(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				data.setRealPower(power);
				data.setReactivePower(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				data.setApparentPower(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				data.setPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				data.setCurrentTotal(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				data.setCurrentAverage(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				data.setVoltageLineNeutral(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				data.setVoltageLineLine(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				data.setFrequency(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				data.setPhaseAngle(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				data.setRealPowerPhaseA(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				data.setRealPowerPhaseB(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				data.setRealPowerPhaseC(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				data.setReactivePowerPhaseA(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				data.setReactivePowerPhaseB(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				data.setReactivePowerPhaseC(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				data.setApparentPowerPhaseA(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				data.setApparentPowerPhaseB(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				data.setApparentPowerPhaseC(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				data.setPowerFactorPhaseA(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				data.setPowerFactorPhaseB(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				data.setPowerFactorPhaseC(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				data.setCurrentPhaseA(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				data.setCurrentPhaseB(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				data.setCurrentPhaseC(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				data.setVoltagePhaseAN(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				data.setVoltagePhaseBN(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				data.setVoltagePhaseCN(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				data.setVoltagePhaseAB(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				data.setVoltagePhaseBC(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				data.setVoltagePhaseCA(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				data.setPhaseAngleA(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				data.setPhaseAngleB(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				data.setPhaseAngleC(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				data.setNvmActivePower(power);
				data.setNvmActiveEnergy(energy);
				
				return data;
				
			} else {
				return new ModelHoneywellEMON3200Entity();
			}
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelHoneywellEMON3200Entity();
		}
	}
	/**
	 * @description insert data from datalogger
	 * @author Hung.Bui
	 * @since 2025-02-07
	 * @param data from datalogger
	 */
	
	public boolean insertData(ModelHoneywellEMON3200Entity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setEnergyDelivered(energy);
			}
			
			 ModelHoneywellEMON3200Entity dataObj = (ModelHoneywellEMON3200Entity) queryForObject("ModelHoneywellEMON3200.getLastRow", obj);
			// filter data 
//				if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() < dataObj.getNvmActiveEnergy() || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
//					obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//					obj.setEnergyDelivered(dataObj.getNvmActiveEnergy());
//				}
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelHoneywellEMON3200.insert", obj);
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
