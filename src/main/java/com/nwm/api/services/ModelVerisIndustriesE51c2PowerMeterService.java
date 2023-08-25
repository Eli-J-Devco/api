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
import com.nwm.api.entities.ModelVerisIndustriesE51c2PowerMeterEntity;
import com.nwm.api.utils.Lib;

public class ModelVerisIndustriesE51c2PowerMeterService extends DB {
	
	/**
	 * @description set data ModelVerisIndustriesE51c2PowerMeter
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelVerisIndustriesE51c2PowerMeterEntity setModelChintSolectriaInverterClass9725(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelVerisIndustriesE51c2PowerMeterEntity dataModelVeris = new ModelVerisIndustriesE51c2PowerMeterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001");
				if(power < 0) { power = 0.0; };
				dataModelVeris.setTime(words.get(0).replace("'", ""));
				dataModelVeris.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelVeris.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelVeris.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelVeris.setAccumulatedRealEnergyNet(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelVeris.setRealEnergyQuadrants14Import(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelVeris.setRealEnergyQuadrants23Export(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelVeris.setReactiveEnergyQuadrant1(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelVeris.setReactiveEnergyQuadrant2(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelVeris.setReactiveEnergyQuadrant3(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelVeris.setReactiveEnergyQuadrant4(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelVeris.setApparentEnergyNet(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelVeris.setApparentEnergyQuadrants14(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelVeris.setApparentEnergyQuadrants23(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelVeris.setTotalNetInstantaneousRealPower(power);
				dataModelVeris.setTotalNetInstantaneousReactivePower(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelVeris.setTotalNetInstantaneousApparentPower(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelVeris.setTotalPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelVeris.setVoltageLL3pAve(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelVeris.setVoltageLN3pAve(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelVeris.setCurrent3pAve(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelVeris.setFrequency(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelVeris.setTotalRealPowerPresentDemand(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelVeris.setTotalReactivePowerPresentDemand(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelVeris.setTotalApparentPowerPresentDemand(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelVeris.setTotalRealPowerMaxDemandImport(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelVeris.setTotalReactivePowerMaxDemandImport(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelVeris.setTotalApparentPowerMaxDemandImport(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelVeris.setTotalRealPowerMaxDemandExport(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelVeris.setTotalReactivePowerMaxDemandExport(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelVeris.setTotalApparentPowerMaxDemandExport(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelVeris.setAccumulatedRealEnergyPhaseAImport(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelVeris.setAccumulatedRealEnergyPhaseBImport(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelVeris.setAccumulatedRealEnergyPhaseCImport(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelVeris.setAccumulatedRealEnergyPhaseAExport(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelVeris.setAccumulatedRealEnergyPhaseBExport(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelVeris.setAccumulatedRealEnergyPhaseCExport(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelVeris.setAccumulatedQ1ReactiveEnergyPhaseAImport(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelVeris.setAccumulatedQ1ReactiveEnergyPhaseBImport(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelVeris.setAccumulatedQ1ReactiveEnergyPhaseCImport(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelVeris.setAccumulatedQ2ReactiveEnergyPhaseAImport(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModelVeris.setAccumulatedQ2ReactiveEnergyPhaseBImport(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModelVeris.setAccumulatedQ2ReactiveEnergyPhaseCImport(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelVeris.setAccumulatedQ3ReactiveEnergyPhaseAExport(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModelVeris.setAccumulatedQ3ReactiveEnergyPhaseBExport(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelVeris.setAccumulatedQ3ReactiveEnergyPhaseCExport(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModelVeris.setAccumulatedQ4ReactiveEnergyPhaseAExport(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelVeris.setAccumulatedQ4ReactiveEnergyPhaseBExport(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelVeris.setAccumulatedQ4ReactiveEnergyPhaseCExport(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelVeris.setAccumulatedApparentEnergyPhaseAImport(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModelVeris.setAccumulatedApparentEnergyPhaseBImport(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				dataModelVeris.setAccumulatedApparentEnergyPhaseCImport(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModelVeris.setAccumulatedApparentEnergyPhaseAExport(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModelVeris.setAccumulatedApparentEnergyPhaseBExport(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModelVeris.setAccumulatedApparentEnergyPhaseCExport(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModelVeris.setRealPowerPhaseA(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModelVeris.setRealPowerPhaseB(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModelVeris.setRealPowerPhaseC(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModelVeris.setReactivePowerPhaseA(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModelVeris.setReactivePowerPhaseB(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModelVeris.setReactivePowerPhaseC(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				dataModelVeris.setApparentPowerPhaseA(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModelVeris.setApparentPowerPhaseB(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModelVeris.setApparentPowerPhaseC(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModelVeris.setPowerFactorPhaseA(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModelVeris.setPowerFactorPhaseB(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				dataModelVeris.setPowerFactorPhaseC(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001"));
				dataModelVeris.setVoltagePhaseAB(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001"));
				dataModelVeris.setVoltagePhaseBC(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001"));
				dataModelVeris.setVoltagePhaseAC(Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001"));
				dataModelVeris.setVoltagePhaseAN(Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001"));
				dataModelVeris.setVoltagePhaseBN(Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001"));
				dataModelVeris.setVoltagePhaseCN(Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001"));
				dataModelVeris.setCurrentPhaseA(Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001"));
				dataModelVeris.setCurrentPhaseB(Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001"));
				dataModelVeris.setCurrentPhaseC(Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelVeris.setNvmActivePower(power);
				dataModelVeris.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				return dataModelVeris;
				
			} else {
				return new ModelVerisIndustriesE51c2PowerMeterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelVerisIndustriesE51c2PowerMeterEntity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_veris_industries_e51c2_power_meter
	 * @author long.pham
	 * @since 2021-04-02
	 * @param data from datalogger
	 */
	
	public boolean insertModelVerisIndustriesE51c2PowerMeter(ModelVerisIndustriesE51c2PowerMeterEntity obj) {
		try {
			ModelVerisIndustriesE51c2PowerMeterEntity dataObj = (ModelVerisIndustriesE51c2PowerMeterEntity) queryForObject("ModelVerisIndustriesE51c2PowerMeter.getLastRow", obj);
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
			 
			 Object insertId = insert("ModelVerisIndustriesE51c2PowerMeter.insertModelVerisIndustriesE51c2PowerMeter", obj);
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
