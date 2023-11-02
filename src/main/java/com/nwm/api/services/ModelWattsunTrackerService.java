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
import com.nwm.api.entities.ModelWattsunTrackerEntity;
import com.nwm.api.utils.Lib;

public class ModelWattsunTrackerService extends DB {

	/**
	 * @description set data ModelWattsunTracker
	 * @author long.pham
	 * @since 2023-10-30
	 * @param data
	 */
	
	public ModelWattsunTrackerEntity setModelWattsunTracker(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelWattsunTrackerEntity dataModelTracker = new ModelWattsunTrackerEntity();
				
				dataModelTracker.setTime(words.get(0).replace("'", ""));
				dataModelTracker.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelTracker.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelTracker.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelTracker.setMODE(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelTracker.setST_CLEAR_ACCUMULATOR(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelTracker.setND_CLEAR_ACCUMULATOR(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelTracker.setTRACKER_ANGLE_SETPOINT(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelTracker.setTRACKER_ANGLE(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelTracker.setNIGHT_STOW_POSITION(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelTracker.setWIND_STOW_POSITION(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				return dataModelTracker;
				
			} else {
				return new ModelWattsunTrackerEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelWattsunTrackerEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_wattsun_tracker
	 * @author long.pham
	 * @since 2023-10-30
	 * @param data from datalogger
	 */

	public boolean insertModelWattsunTracker(ModelWattsunTrackerEntity obj) {
		try {
			Object insertId = insert("ModelWattsunTracker.insertModelWattsunTracker", obj);
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
