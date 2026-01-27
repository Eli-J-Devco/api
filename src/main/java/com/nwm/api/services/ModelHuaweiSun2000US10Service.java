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
import com.nwm.api.entities.ModelHuaweiSun2000US10Entity;
import com.nwm.api.utils.Lib;

public class ModelHuaweiSun2000US10Service extends DB {

	/**
	 * @description set data ModelHuaweiSun2000US10
	 * @author duy.phan
	 * @since 2025-12-20
	 * @param data
	 */
	
	public ModelHuaweiSun2000US10Entity setModelHuaweiSun2000US10(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelHuaweiSun2000US10Entity data = new ModelHuaweiSun2000US10Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001");
							
				data.setTime(words.get(0).replace("'", ""));
				data.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				data.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				data.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				data.setFrequency(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				data.setVoltagePhaseAtoB(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				data.setVoltagePhaseBtoC(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				data.setVoltagePhaseCtoA(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				data.setVoltagePhaseA(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				data.setVoltagePhaseB(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				data.setVoltagePhaseC(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));				
				data.setCurrentPhaseA(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				data.setCurrentPhaseB(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				data.setCurrentPhaseC(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				data.setActivePower(power);
				data.setTotalEnergyDelivered(energy);
				data.setPVVoltage1(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				data.setPVVoltage2(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				data.setPVVoltage3(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				data.setPVVoltage4(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));				
				data.setPVVoltage5(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				data.setPVVoltage6(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				data.setPVCurrent1(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				data.setPVCurrent2(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				data.setPVCurrent3(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				data.setPVCurrent4(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				data.setPVCurrent5(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				data.setPVCurrent6(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				data.setCabinetTemperature(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				data.setPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				data.setInverterEfficiency(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));				
				data.setInverterStatus(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				data.setFaultCode(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				data.setFaultCode1(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				data.setFaultCode2(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				data.setFaultCode3(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				data.setFaultCode4(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				data.setFaultCode5(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				data.setFaultCode6(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				data.setFaultCode7(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				data.setFaultCode8(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));			
				data.setFaultCode9(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				data.setFaultCode10(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				data.setFaultCode11(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));				
				data.setRatedInverterPower(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				data.setActivePowerPeakToday(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				data.setReactivePower(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				data.setTotalInputPower(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				data.setEnergyDeliveredToday(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				data.setInsulationResistance(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				data.setMPPT1Voltage(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				data.setMPPT1Current(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				data.setMPPT1Power(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				data.setMPPT2Voltage(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				data.setMPPT2Current(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				data.setMPPT2Power(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				data.setMPPT3Voltage(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				data.setMPPT3Current(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				data.setMPPT3Power(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				data.setNvmActivePower(power);
				data.setNvmActiveEnergy(energy);
				
				
				return data;
				
			} else {
				return new ModelHuaweiSun2000US10Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelHuaweiSun2000US10Entity();
		}
	}
	/**
	 * @description insert data from datalogger to ModelHuaweiSun2000US10
	 * @author duy.phan
	 * @since 2025-12-12
	 * @param data from datalogger
	 */
	
	public boolean insertModelHuaweiSun2000US10(ModelHuaweiSun2000US10Entity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setTotalEnergyDelivered(energy);
			}
			
			ModelHuaweiSun2000US10Entity dataObj = (ModelHuaweiSun2000US10Entity) queryForObject("ModelHuaweiSun2000US10.getLastRow", obj);
			// filter data 
			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setTotalEnergyDelivered(dataObj.getTotalEnergyDelivered());
			}
			 
			Object insertId = insert("ModelHuaweiSun2000US10.insertModelHuaweiSun2000US10", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        // Update measuredProduction 
 			if (dataObj != null && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy() >= 0 ) {
 				ModelHuaweiSun2000US10Entity objUpdateMeasured = new ModelHuaweiSun2000US10Entity();
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
