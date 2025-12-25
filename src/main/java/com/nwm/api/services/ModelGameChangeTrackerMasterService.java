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
import com.nwm.api.entities.ModelGameChangeTrackerMasterEntity;
import com.nwm.api.utils.Lib;

public class ModelGameChangeTrackerMasterService extends DB {

	/**
	 * @description set data ModelGameChangeTrackerMaster
	 * @author Hung.Bui
	 * @since 2025-12-25
	 * @param data
	 */
	
	public ModelGameChangeTrackerMasterEntity setModelGameChangeTrackerMaster(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			
			if (words.size() > 0) {
				ModelGameChangeTrackerMasterEntity data = new ModelGameChangeTrackerMasterEntity();
				
				data.setTime(words.get(0).replace("'", ""));
				data.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				data.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				data.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				data.setHardwareVersionMajor(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				data.setHardwareVersionMinor(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				data.setHardwareVersionFix(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				data.setSoftwareVersionMajor(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				data.setSoftwareVersionMinor(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				data.setSoftwareVersionFix(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				data.setYear(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				data.setMonth(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				data.setDay(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				data.setHour(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				data.setMinute(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				data.setSecond(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				data.setTargetAngle(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				data.setAverageTrackerAngle(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				data.setAverageTrackerBatteryVoltage(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				data.setStowEnvelopeEastLimit(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				data.setStowEnvelopeWestLimit(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				data.setNumberoftrackerNodes(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				data.setWindInstant(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				data.setWindPeak(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				data.setWindAverage(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				data.setSnowWaterDepth(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				data.setSnowWaterBaseline(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				data.setEnabledStowModes(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				data.setActiveStowMode(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				data.setBatteryVoltage(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				data.setSystemAlarms(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				data.setStatusFlags(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				data.setTrackingMode(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				data.setHailStowResumeTimer(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				data.setNocommunicationcounter(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				data.setTrackingDisabledCounter(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				data.setEStopPressedCounter(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				data.setOvercurrentLimitCounter(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				data.setLowBatteryCounter(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				data.setCriticalBatteryCounter(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				data.setNoMotionCounter(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				data.setWrongDirectionCounter(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				data.setBatteryOverVoltageCounter(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				data.setFailedEastCounter(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				data.setHighPriorityStowCounter(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				data.setLowPriorityStowCounter(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				data.setMissedStatusCounter(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				data.setNoDataCounter(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				data.setNotChargingCounter(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				data.setBODResetCounter(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				data.setFailedWestCounter(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				data.setAngleDeviationCounter(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				data.setBatteryMaintenance(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				data.setLowTempStow(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				
				return data;
			} else {
				return new ModelGameChangeTrackerMasterEntity();
			}
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelGameChangeTrackerMasterEntity();
		}
	}
	/**
	 * @description insert data from datalogger to ModelGameChangeTrackerMaster
	 * @author Hung.Bui
	 * @since 2025-12-25
	 * @param data from datalogger
	 */
	
	public boolean insertModelGameChangeTrackerMaster(ModelGameChangeTrackerMasterEntity obj) {
		try {
			Object insertId = insert("ModelGameChangeTrackerMaster.insertModelGameChangeTrackerMaster", obj);
			if (insertId == null) return false;
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
}
