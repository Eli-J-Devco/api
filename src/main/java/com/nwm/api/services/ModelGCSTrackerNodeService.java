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
import com.nwm.api.entities.ModelGCSTrackerNodeEntity;
import com.nwm.api.utils.Lib;

public class ModelGCSTrackerNodeService extends DB {

	/**
	 * @description set data ModelGCSTrackerNode
	 * @author Hung.Bui
	 * @since 2026-01-05
	 * @param data
	 */
	
	public ModelGCSTrackerNodeEntity setModelGCSTrackerNode(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			
			if (words.size() > 0) {
				ModelGCSTrackerNodeEntity data = new ModelGCSTrackerNodeEntity();
				
				data.setTime(words.get(0).replace("'", ""));
				data.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				data.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				data.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				data.setTrackernodeID(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				data.setTimestamp(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				data.setTargettrackingangle(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				data.setPaneltableangle(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				data.setBatteryvoltage(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				data.setChargingstatus(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				data.setAveragemotorcurrent(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				data.setPeakmotorcurrent(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				data.setAlarm(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				data.setStatusflag(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				data.setPeakTemperature(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				
				return data;
			} else {
				return new ModelGCSTrackerNodeEntity();
			}
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelGCSTrackerNodeEntity();
		}
	}
	/**
	 * @description insert data from datalogger to ModelGCSTrackerNode
	 * @author Hung.Bui
	 * @since 2026-01-05
	 * @param data from datalogger
	 */
	
	public boolean insertModelGCSTrackerNode(ModelGCSTrackerNodeEntity obj) {
		try {
			Object insertId = insert("ModelGCSTrackerNode.insertModelGCSTrackerNode", obj);
			if (insertId == null) return false;
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
}
