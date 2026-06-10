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
import com.nwm.api.entities.ModelQuint4UPSEntity;
import com.nwm.api.utils.Lib;

public class ModelQuint4UPSService extends DB {

	/**
	 * @description set data ModelQuint4UPS
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelQuint4UPSEntity setModelQuint4UPS(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelQuint4UPSEntity dataModelQuint4UPS = new ModelQuint4UPSEntity();
				
				dataModelQuint4UPS.setTime(words.get(0).replace("'", ""));
				dataModelQuint4UPS.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelQuint4UPS.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelQuint4UPS.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelQuint4UPS.setActualInputVoltage(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelQuint4UPS.setActualInputCurrent(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelQuint4UPS.setActualOutputVoltage(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelQuint4UPS.setActualOutputCurrent(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelQuint4UPS.setBatteryActualVoltage(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelQuint4UPS.setBatteryChargeCurrent(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelQuint4UPS.setBatteryTemperature(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				dataModelQuint4UPS.setDeviceTemperature(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelQuint4UPS.setStateofCharge(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelQuint4UPS.setStateofChargeRemainingTime(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelQuint4UPS.setStateofHealth(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelQuint4UPS.setStateofHealthRemainingLifetime(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelQuint4UPS.setOperationTime(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelQuint4UPS.setBatteryModeTime(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelQuint4UPS.setStatusAlarm(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelQuint4UPS.setStatusWarning(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				dataModelQuint4UPS.setBattery1StateofCharge(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelQuint4UPS.setBattery1StateofHealth(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelQuint4UPS.setBattery1Temperature(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelQuint4UPS.setBattery1StatusofFuse(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelQuint4UPS.setBattery1ActualInternalVoltage(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelQuint4UPS.setBattery1ActualBlockVoltage(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelQuint4UPS.setBattery1InstalledCapacity(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelQuint4UPS.setBattery1NominalResistance(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelQuint4UPS.setBattery1MaxTemperature(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelQuint4UPS.setBattery1MinTemperature(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelQuint4UPS.setBattery1NominalLifetime(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				
				dataModelQuint4UPS.setBattery1MaxChargeCurrent(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelQuint4UPS.setBattery1ChargeAbsorptionVoltage(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelQuint4UPS.setBattery1ChargeEndvoltage(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelQuint4UPS.setBattery1ChargeTemperatureCoefficient(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelQuint4UPS.setBattery1DischargeEndvoltage(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelQuint4UPS.setBattery1MaxDischargeCurrent(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelQuint4UPS.setBattery1MaxTemperatureWarning(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelQuint4UPS.setBattery1MinTemperatureWarning(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelQuint4UPS.setBattery1DischargeEndvoltageLowCurrent(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				
				
				return dataModelQuint4UPS;
				
			} else {
				return new ModelQuint4UPSEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelQuint4UPSEntity();
		}
	}
	/**
	 * @description insert data from datalogger to model shark 100
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelQuint4UPS(ModelQuint4UPSEntity obj) {
		try {			 
			 Object insertId = insert("ModelQuint4UPS.insertModelQuint4UPS", obj);
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
