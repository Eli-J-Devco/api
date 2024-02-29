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
import com.nwm.api.entities.ModelAbbUnoDm1250tpPlusEntity;
import com.nwm.api.utils.Lib;

public class ModelAbbUnoDm1250tpPlusService extends DB {
	
	/**
	 * @description set data model_aes_tx_inverter
	 * @author long.pham
	 * @since 2023-08-02
	 * @param data
	 */
	
	public ModelAbbUnoDm1250tpPlusEntity setModelAbbUnoDm1250tpPlus(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelAbbUnoDm1250tpPlusEntity dataModel = new ModelAbbUnoDm1250tpPlusEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001");
				
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				
				dataModel.setPowerfactorcosphi(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setEnergytotal(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setGridfrequency(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setCurrentAC(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setCurrentDCtotal(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setPowerAC(power);
				dataModel.setVoltageAC(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setVoltageACphase1(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				
				dataModel.setVoltageACphase2(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setVoltageACphase3(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setPhasevoltageL1L2(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));;
				dataModel.setPhasevoltageL2L3(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));;
				
				dataModel.setPhasevoltageL3L1(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setVoltageDC(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setCabinetTemperature(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setHeatSinkTemperature(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				dataModel.setOtherTemperature(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setApparentpower(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setReactivepower(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setDCCurrentChanel1(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setDCVoltageChanel1(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setDCPowerChanel1(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setOperatingStateChanel1(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setDCCurrentChanel2(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setDCVoltageChanel2(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setDCPowerChanel2(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setOperatingStateChanel2(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel.setInverterstate(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				
				dataModel.setAlarmstate(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setGlobalState(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setWMax(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				Double nvmActiveEnergy = Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001");
				
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(nvmActiveEnergy);
				
				return dataModel;
				
			} else {
				return new ModelAbbUnoDm1250tpPlusEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelAbbUnoDm1250tpPlusEntity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_abb_uno_dm_1250tp_plus
	 * @author long.pham
	 * @since 2024-02-28
	 * @param data from datalogger
	 */
	
	public boolean insertModelAbbUnoDm1250tpPlus(ModelAbbUnoDm1250tpPlusEntity obj) {
		try {
			ModelAbbUnoDm1250tpPlusEntity dataObj = (ModelAbbUnoDm1250tpPlusEntity) queryForObject("ModelAbbUnoDm1250tpPlus.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelAbbUnoDm1250tpPlus.insertModelAbbUnoDm1250tpPlus", obj);
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
