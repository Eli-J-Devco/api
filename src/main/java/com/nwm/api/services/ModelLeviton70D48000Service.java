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
import com.nwm.api.entities.ModelLeviton70D48000Entity;
import com.nwm.api.utils.Lib;

public class ModelLeviton70D48000Service extends DB {
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelLeviton70D48000Entity setModelLeviton70D48000(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelLeviton70D48000Entity dataModel70D48 = new ModelLeviton70D48000Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001");
				
				
				dataModel70D48.setTime(words.get(0).replace("'", ""));
				dataModel70D48.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel70D48.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel70D48.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel70D48.setCurrentAvg(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel70D48.setCurrentCH1A(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel70D48.setCurrentCH2B(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel70D48.setCurrentCH3C(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel70D48.setVoltageLNAvg(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel70D48.setVoltageL1NAN(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel70D48.setVoltageL2NBN(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel70D48.setVoltageL3NCN(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel70D48.setVoltageLLAvg(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				
				dataModel70D48.setVoltageL1L2(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel70D48.setVoltageL2L3(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel70D48.setVoltageL3L1(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel70D48.setLineFrequency(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel70D48.setPowerSum (power);
				dataModel70D48.setPowerCH1A(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel70D48.setPowerCH2B(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel70D48.setPowerCH3C(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel70D48.setVASum(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel70D48.setVACH1A(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				
				dataModel70D48.setVACH2B(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel70D48.setVACH3C(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel70D48.setVARSum(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel70D48.setVARCH1A(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel70D48.setVARCH2B(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel70D48.setVARCH3C(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel70D48.setApparentPFAvg(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel70D48.setApparentPFCH1A(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel70D48.setApparentPFCH2B(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel70D48.setApparentPFCH3C(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				
				dataModel70D48.setExportedEnergySum(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel70D48.setExportedEnergyCH1A(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModel70D48.setExportedEnergyCH2B(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				
				
				dataModel70D48.setExportedEnergyCH3C(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel70D48.setImportedEnergySum(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModel70D48.setImportedEnergyCH1A(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel70D48.setImportedEnergyCH2B(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel70D48.setImportedEnergyCH3C(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModel70D48.setExportedVAhSum(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				
				dataModel70D48.setExportedVAhCH1A(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModel70D48.setExportedVAhCH2B(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel70D48.setExportedVAhCH3C(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel70D48.setImportedVAhSum(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModel70D48.setImportedVAhCH1A(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				
				dataModel70D48.setImportedVAhCH2B(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModel70D48.setImportedVAhCH3C(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModel70D48.setImportedVARhQ1Sum(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModel70D48.setImportedVARhQ1A(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				
				dataModel70D48.setImportedVARhQ1B(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModel70D48.setImportedVARhQ1C(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModel70D48.setImportedVARhQ2Sum(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModel70D48.setImportedVARhQ2A(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModel70D48.setImportedVARhQ2B(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModel70D48.setImportedVARhQ2C(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModel70D48.setExportedVARhQ3Sum(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModel70D48.setExportedVARhQ3A(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModel70D48.setExportedVARhQ3B(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModel70D48.setExportedVARhQ3C(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				
				dataModel70D48.setExportedVARhQ4Sum(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModel70D48.setExportedVARhQ4A(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModel70D48.setExportedVARhQ4B(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				
				dataModel70D48.setExportedVARhQ4C(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModel70D48.setPowerDemandThisInterval(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel70D48.setNvmActivePower(power);
				dataModel70D48.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				
				return dataModel70D48;
				
			} else {
				return new ModelLeviton70D48000Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelLeviton70D48000Entity();
		}
	}


	/**
	 * @description insert data from datalogger to ModelLeviton70D4800
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelLeviton70D48000(ModelLeviton70D48000Entity obj) {
		try {
			ModelLeviton70D48000Entity dataObj = (ModelLeviton70D48000Entity) queryForObject("ModelLeviton70D48000.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelLeviton70D48000.insertModelLeviton70D48000", obj);
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
