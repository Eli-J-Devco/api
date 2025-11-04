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
import com.nwm.api.entities.ModelSunTrackTrackerEntity;
import com.nwm.api.utils.Lib;

public class ModelSunTrackTrackerService extends DB {
	/**
	 * @description set data ModelSunTrackTracker
	 * @author Duy.Phan
	 * @since 2025-05-30
	 * @param data
	 */
	
	public ModelSunTrackTrackerEntity setModelSunTrackTracker(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSunTrackTrackerEntity dataModelSunTrackTracker = new ModelSunTrackTrackerEntity();
				
				dataModelSunTrackTracker.setTime(words.get(0).replace("'", ""));
				dataModelSunTrackTracker.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelSunTrackTracker.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelSunTrackTracker.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelSunTrackTracker.setProductIdentifier(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelSunTrackTracker.setStatus(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelSunTrackTracker.setAlarms(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelSunTrackTracker.setFlags(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelSunTrackTracker.setPanelVoltage(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelSunTrackTracker.setCurrentAngleRaw(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelSunTrackTracker.setCurrentAngleCalculated(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelSunTrackTracker.setMotorCurrent(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelSunTrackTracker.setMotorCurrentPeak(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelSunTrackTracker.setTargetAngleRaw(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelSunTrackTracker.setTargetAngleCalculated(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelSunTrackTracker.setPanelCurrent(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelSunTrackTracker.setBatteryStateOfCharge(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelSunTrackTracker.setBatteryRemainingCapacity(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelSunTrackTracker.setBatteryFullCapacity(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelSunTrackTracker.setBatteryVoltage(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelSunTrackTracker.setBatteryAverageCurrent(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelSunTrackTracker.setBatteryCurrent(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelSunTrackTracker.setBatteryInternalTemperature(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelSunTrackTracker.setBatteryExternalTemperature(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelSunTrackTracker.setBatteryStateOfHealth(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));

				
				return dataModelSunTrackTracker;
				
			} else {
				return new ModelSunTrackTrackerEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSunTrackTrackerEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_sun_track_tracker
	 * @author Duy.Phan
	 * @since 2025-05-30
	 * @param data from datalogger
	 */

	public boolean insertModelSunTrackTracker(ModelSunTrackTrackerEntity obj) {
		try {
			Object insertId = insert("ModelSunTrackTracker.insertModelSunTrackTracker", obj);
			if (insertId == null) {
				return false;
			}
			
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
}
