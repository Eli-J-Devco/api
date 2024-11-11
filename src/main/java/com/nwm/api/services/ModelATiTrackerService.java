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
import com.nwm.api.entities.ModelATiTrackerEntity;
import com.nwm.api.utils.Lib;

public class ModelATiTrackerService extends DB {

	/**
	 * @description set data ModelATiTracker
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelATiTrackerEntity setModelATiTracker(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelATiTrackerEntity dataModelATiTracker = new ModelATiTrackerEntity();
				
				dataModelATiTracker.setTime(words.get(0).replace("'", ""));
				dataModelATiTracker.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelATiTracker.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelATiTracker.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelATiTracker.setTracker1Setpoint(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelATiTracker.setTracker1ActualPosition(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelATiTracker.setTracker2Setpoint(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelATiTracker.setTracker2ActualPosition(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelATiTracker.setTracker3Setpoint(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelATiTracker.setTracker3ActualPosition(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelATiTracker.setTracker4Setpoint(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				dataModelATiTracker.setTracker4ActualPosition(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelATiTracker.setTracker5Setpoint(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelATiTracker.setTracker5ActualPosition(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelATiTracker.setTracker6Setpoint(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelATiTracker.setTracker6ActualPosition(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelATiTracker.setScaleFactor(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelATiTracker.setTracker1Alarm(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelATiTracker.setTracker2Alarm(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelATiTracker.setTracker3Alarm(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				dataModelATiTracker.setTracker4Alarm(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelATiTracker.setTracker5Alarm(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelATiTracker.setTracker6Alarm(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelATiTracker.setFieldStowCode(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelATiTracker.setGlobalMode(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelATiTracker.setManualGlobalPosition(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelATiTracker.setTracker1Mode(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelATiTracker.setTracker1ManualPosition(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelATiTracker.setTracker2Mode(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelATiTracker.setTracker2ManualPosition(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelATiTracker.setTracker3Mode(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				
				dataModelATiTracker.setTracker3ManualPosition(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelATiTracker.setTracker4Mode(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelATiTracker.setTracker4ManualPosition(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelATiTracker.setTracker5Mode(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelATiTracker.setTracker5ManualPosition(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelATiTracker.setTracker6Mode(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelATiTracker.setTracker6ManualPosition(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				
				return dataModelATiTracker;
				
			} else {
				return new ModelATiTrackerEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelATiTrackerEntity();
		}
	}
	/**
	 * @description insert data from datalogger to model shark 100
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelATiTracker(ModelATiTrackerEntity obj) {
		try {			 
			 Object insertId = insert("ModelATiTracker.insertModelATiTracker", obj);
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
