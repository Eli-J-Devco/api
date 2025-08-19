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
import com.nwm.api.entities.ModelEC350GasMeterEntity;
import com.nwm.api.utils.Lib;

public class ModelEC350GasMeterService extends DB {
	/**
	 * @description set data model_ec350_gas_meter
	 * @author duy.phan
	 * @since 2025-08-18
	 * @param data
	 */
	
	public ModelEC350GasMeterEntity setModelEC350GasMeter(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelEC350GasMeterEntity dataModel = new ModelEC350GasMeterEntity();
				
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001");
				
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setPressure(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
				dataModel.setTemperature(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setFlowRateCorrectedVolume(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setBatteryVoltage(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setCaseTemperature(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setP1HighAlarmLimit(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setP1LowAlarmLimit(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setFlowRateHighAlarmLimit(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setBasePressure(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setAtmosphericPressure(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setGasEnergyValue(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setTotalCorrectionValue(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setFirmwareVersion(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setCorrectedVolume(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setUncorrectedVolume(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setEnergy(energy);
				dataModel.setMasterAlarmStatus(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setVolumeSensor1Alarm(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setVolumeSensor2Alarm(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setBatteryLowAlarm(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setP1PressureLowAlarm(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setP1HighPressureAlarm(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setTemperatureLowAlarm(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setTemperatureHighAlarm(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setFlowRateHighAlarm(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				
				dataModel.setNvmActivePower(Double.parseDouble("0.001"));
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelEC350GasMeterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelEC350GasMeterEntity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_ec350_gas_meter
	 * @author duy.phan
	 * @since 2025-08-18
	 * @param data from datalogger
	 */
	
	public boolean insertModelEC350GasMeter(ModelEC350GasMeterEntity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setEnergy(energy);
			}
			
			ModelEC350GasMeterEntity dataObj = (ModelEC350GasMeterEntity) queryForObject("ModelEC350GasMeter.getLastRow", obj);
			// filter data 
			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() < dataObj.getNvmActiveEnergy() || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setEnergy(dataObj.getNvmActiveEnergy());
			}
						
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }

			 
			 obj.setMeasuredProduction(measuredProduction);
			 obj.setNvmActivePower(measuredProduction);
			 
			 Object insertId = insert("ModelEC350GasMeter.insertModelEC350GasMeter", obj);
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
