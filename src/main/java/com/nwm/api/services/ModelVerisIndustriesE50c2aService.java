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
import com.nwm.api.entities.ModelVerisIndustriesE50c2aEntity;
import com.nwm.api.utils.Lib;

public class ModelVerisIndustriesE50c2aService extends DB {
	
	/**
	 * @description set data ModelVerisIndustriesE50c2a
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelVerisIndustriesE50c2aEntity setModelVerisIndustriesE50c2a(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelVerisIndustriesE50c2aEntity dataModelVeris = new ModelVerisIndustriesE50c2aEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001");
				
				dataModelVeris.setTime(words.get(0).replace("'", ""));
				dataModelVeris.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelVeris.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelVeris.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelVeris.setRealEnergyConsumption(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelVeris.setTotalInstantaneousRealPower(power);
				dataModelVeris.setTotalInstantaneousReactivePower(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelVeris.setTotalInstantaneousApparentPower(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelVeris.setTotalPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelVeris.setVoltageLL3pAve(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelVeris.setVoltageLN3pAve(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelVeris.setCurrent3pAve(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelVeris.setRealPowerPhaseA(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelVeris.setRealPowerPhaseB(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelVeris.setRealPowerPhaseC(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelVeris.setPowerFactorPhaseA(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelVeris.setPowerFactorPhaseB(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelVeris.setPowerFactorPhaseC(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelVeris.setVoltagePhaseAB(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelVeris.setVoltagePhaseBC(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelVeris.setVoltagePhaseAC(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelVeris.setVoltagePhaseAN(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelVeris.setVoltagePhaseBN(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelVeris.setVoltagePhaseCN(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelVeris.setCurrentInstantaneousPhaseA(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelVeris.setCurrentInstantaneousPhaseB(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelVeris.setCurrentInstantaneousPhaseC(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelVeris.setFrequency(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelVeris.setApparentEnergyConsumption(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelVeris.setReactiveEnergyConsumption(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelVeris.setApparentPowerPhaseA(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelVeris.setApparentPowerPhaseB(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelVeris.setApparentPowerPhaseC(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelVeris.setReactivePowerPhaseA(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelVeris.setReactivePowerPhaseB(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelVeris.setReactivePowerPhaseC(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelVeris.setTotalRealPowerPresentDemand(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelVeris.setTotalReactivePowerPresentDemand(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelVeris.setTotalApparentPowerPresentDemand(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelVeris.setTotalRealPowerMaxDemand(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelVeris.setTotalReactivePowerMaxDemand(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));

				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelVeris.setNvmActivePower(power);
				dataModelVeris.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				return dataModelVeris;
				
			} else {
				return new ModelVerisIndustriesE50c2aEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelVerisIndustriesE50c2aEntity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_veris_industries_e50c2a
	 * @author long.pham
	 * @since 2021-04-02
	 * @param data from datalogger
	 */
	
	public boolean insertModelVerisIndustriesE50c2a(ModelVerisIndustriesE50c2aEntity obj) {
		try {
			ModelVerisIndustriesE50c2aEntity dataObj = (ModelVerisIndustriesE50c2aEntity) queryForObject("ModelVerisIndustriesE50c2a.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelVerisIndustriesE50c2a.insertModelVerisIndustriesE50c2a", obj);
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
