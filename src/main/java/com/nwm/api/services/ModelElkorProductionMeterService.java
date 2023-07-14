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
import com.nwm.api.entities.ModelElkorProductionMeterEntity;
import com.nwm.api.utils.Lib;


public class ModelElkorProductionMeterService extends DB {

	/**
	 * @description set data ModelElkorProductionMeter
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelElkorProductionMeterEntity setModelElkorProductionMeter(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelElkorProductionMeterEntity dataModelElkorP = new ModelElkorProductionMeterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				if(power < 0) { power = 0.0; };
				
				dataModelElkorP.setTime(words.get(0).replace("'", ""));
				dataModelElkorP.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelElkorP.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelElkorP.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelElkorP.setActivePowerTotal(power);
				dataModelElkorP.setReactivePowerTotal(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelElkorP.setApparentPowerTotal(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelElkorP.setVoltageAverage(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelElkorP.setVoltageLLAverage(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelElkorP.setCurrentAverage(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelElkorP.setSystemPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelElkorP.setSystemFrequency(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelElkorP.setVoltageAverageAngle(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelElkorP.setSystemQuadrant(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelElkorP.setVoltageA(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelElkorP.setVoltageB(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelElkorP.setVoltageC(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelElkorP.setVoltageAB(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelElkorP.setVoltageBC(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelElkorP.setVoltageAC(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelElkorP.setCurrentA(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelElkorP.setCurrentB(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelElkorP.setCurrentC(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelElkorP.setActivePowerA(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelElkorP.setActivePowerB(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelElkorP.setActivePowerC(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelElkorP.setReactivePowerA(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelElkorP.setReactivePowerB(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelElkorP.setReactivePowerC(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelElkorP.setApparentPowerA(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelElkorP.setApparentPowerB(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelElkorP.setApparentPowerC(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelElkorP.setPowerFactorA(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelElkorP.setPowerFactorB(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelElkorP.setPowerFactorC(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				
				dataModelElkorP.setVoltageAngleAB(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelElkorP.setVoltageAngleBC(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelElkorP.setVoltageAngleCA(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelElkorP.setQuadrantA(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelElkorP.setQuadrantB(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelElkorP.setQuadrantC(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModelElkorP.setSlidingWindowPower(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModelElkorP.setNetTotalEnergy(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelElkorP.setTotalNetApparentEnergy(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModelElkorP.setTotalImportEnergy(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelElkorP.setTotalExportEnergy(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModelElkorP.setTotalImportApparentEnergy(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelElkorP.setTotalExportApparentEnergy(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelElkorP.setQ1TotalReactiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelElkorP.setQ2TotalReactiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModelElkorP.setQ3TotalReactiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				dataModelElkorP.setQ4TotalReactiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModelElkorP.setQ1Q2TotalInductiveReactiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModelElkorP.setQ3Q4TotalCapacitiveReactiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModelElkorP.setNetEnergyA(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModelElkorP.setNetEnergyB(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModelElkorP.setNetEnergyC(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModelElkorP.setNetApparentEnergyA(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModelElkorP.setNetApparentEnergyB(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModelElkorP.setNetApparentEnergyC(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModelElkorP.setImportEnergyA(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				dataModelElkorP.setImportEnergyB(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModelElkorP.setImportEnergyC(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModelElkorP.setExportEnergyA(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModelElkorP.setExportEnergyB(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModelElkorP.setExportEnergyC(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				dataModelElkorP.setImportApparentEnergyA(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001"));
				dataModelElkorP.setImportApparentEnergyB(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001"));
				dataModelElkorP.setImportApparentEnergyC(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001"));
				dataModelElkorP.setExportApparentEnergyA(Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001"));
				dataModelElkorP.setExportApparentEnergyB(Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001"));
				dataModelElkorP.setExportApparentEnergyC(Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001"));
				dataModelElkorP.setQ1ReactiveEnergyA(Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001"));
				dataModelElkorP.setQ1ReactiveEnergyB(Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001"));
				dataModelElkorP.setQ1ReactiveEnergyC(Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001"));
				dataModelElkorP.setQ2ReactiveEnergyA(Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001"));
				
				dataModelElkorP.setQ2ReactiveEnergyB(Double.parseDouble(!Lib.isBlank(words.get(76)) ? words.get(76) : "0.001"));
				dataModelElkorP.setQ2ReactiveEnergyC(Double.parseDouble(!Lib.isBlank(words.get(77)) ? words.get(77) : "0.001"));
				dataModelElkorP.setQ3ReactiveEnergyA(Double.parseDouble(!Lib.isBlank(words.get(78)) ? words.get(78) : "0.001"));
				dataModelElkorP.setQ3ReactiveEnergyB(Double.parseDouble(!Lib.isBlank(words.get(79)) ? words.get(79) : "0.001"));
				dataModelElkorP.setQ3ReactiveEnergyC(Double.parseDouble(!Lib.isBlank(words.get(80)) ? words.get(80) : "0.001"));
				dataModelElkorP.setQ4ReactiveEnergyA(Double.parseDouble(!Lib.isBlank(words.get(81)) ? words.get(81) : "0.001"));
				dataModelElkorP.setQ4ReactiveEnergyB(Double.parseDouble(!Lib.isBlank(words.get(82)) ? words.get(82) : "0.001"));
				dataModelElkorP.setQ4ReactiveEnergyC(Double.parseDouble(!Lib.isBlank(words.get(83)) ? words.get(83) : "0.001"));
			
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelElkorP.setNvmActivePower(power);
				dataModelElkorP.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				
				return dataModelElkorP;
				
			} else {
				return new ModelElkorProductionMeterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelElkorProductionMeterEntity();
		}
	}
	
	
	/**
	 * @description insert data from datalogger to model_elkor_wattson_pv_meter
	 * @author long.pham
	 * @since 2021-08-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelElkorProductionMeter(ModelElkorProductionMeterEntity obj) {
		try {
			ModelElkorProductionMeterEntity dataObj = (ModelElkorProductionMeterEntity) queryForObject("ModelElkorProductionMeter.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 }
			 }
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelElkorProductionMeter.insertModelElkorProductionMeter", obj);
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
