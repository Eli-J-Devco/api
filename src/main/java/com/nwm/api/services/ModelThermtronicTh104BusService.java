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
import com.nwm.api.entities.ModelThermtronicTh104BusEntity;
import com.nwm.api.utils.Lib;

public class ModelThermtronicTh104BusService extends DB {
	/**
	 * @description set data ModelThermtronicTh104Bus
	 * @author duy.phan
	 * @since 2025-08-05
	 * @param data
	 */
	
	public ModelThermtronicTh104BusEntity setModelThermtronicTh104Bus(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelThermtronicTh104BusEntity dataModel = new ModelThermtronicTh104BusEntity();
				
//				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001");
//				if(irradiance < 0) { irradiance = 0.0; };
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setS1AlarmTemperature(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setS1TripTemperature(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setS2AlarmTemperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setS2TripTemperature(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setS3AlarmTemperature(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setS3TripTemperature(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setAmbientAlarmTemperature(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setAmbientTripTemperature(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setUnitsofMeasurement(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setFan1ActivationTemperature(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setFan1DeactivationTemperature(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setSensorStatus(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setFan2ActivationTemperature(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setFan2DeactivationTemperature(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setMinutesActive(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setSensor1Status(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setSensor2Status(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setSensor3Status(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setS1MeasuredMaximumTemperature(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setS2MeasuredMaximumTemperature(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setS3MeasuredMaximumTemperature(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setAmbientMeasuredMaximumTemperature(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setS1CurrentTemperature(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setS2CurrentTemperature(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setS3CurrentTemperature(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setAmbientAirCurrentTemperature(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setFan2Relay(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel.setFan1Relay(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setFaultRelay(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setTripRelay(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setAlarmRelay(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				
				// set custom field nvm_irradiance
				
				dataModel.setNvm_irradiance(0);
				dataModel.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				
				return dataModel;
				
			} else {
				return new ModelThermtronicTh104BusEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelThermtronicTh104BusEntity();
		}
	}

	/**
	 * @description insert data from datalogger to ModelThermtronicTh104Bus
	 * @author duy.phan
	 * @since 2025-08-05
	 * @param data from datalogger
	 */
	
	public boolean insertModelThermtronicTh104Bus(ModelThermtronicTh104BusEntity obj) {
		try {
			 Object insertId = insert("ModelThermtronicTh104Bus.insertModelThermtronicTh104Bus", obj);
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
