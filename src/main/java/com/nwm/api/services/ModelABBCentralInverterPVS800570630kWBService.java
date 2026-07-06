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
import com.nwm.api.entities.ModelABBCentralInverterPVS800570630kWBEntity;
import com.nwm.api.utils.Lib;

public class ModelABBCentralInverterPVS800570630kWBService extends DB {
	
	/**
	 * @description set data ModelElkorWattsonPVMeter
	 * @author duy.phan
	 * @since 2023-12-12
	 * @param data
	 */
	
	public ModelABBCentralInverterPVS800570630kWBEntity setModelABBCentralInverterPVS800570630kWB(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelABBCentralInverterPVS800570630kWBEntity dataModel = new ModelABBCentralInverterPVS800570630kWBEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setFailSafeMode(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setOperationMode(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setControlzeromode(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setCutOfftime(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setStatusCode(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setInverterstatus(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setACVoltage(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setInverterTemperature(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setActivePower(power);
				dataModel.setkWhCounterLow(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setkWhCounterHigh(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setEnergyTotal(energy);
				dataModel.setTotalInputPower(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setDCVoltage(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setDCCurrent(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelABBCentralInverterPVS800570630kWBEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelABBCentralInverterPVS800570630kWBEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to 
	 * @author duy.phan
	 * @since 2023-12-12
	 * @param data from datalogger
	 */
	
	public boolean insertModelABBCentralInverterPVS800570630kWB(ModelABBCentralInverterPVS800570630kWBEntity obj) {
		try {
			Object insertId = insert("ModelABBCentralInverterPVS800570630kWB.insertModelABBCentralInverterPVS800570630kWB", obj);
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
