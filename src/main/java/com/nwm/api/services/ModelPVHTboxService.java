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
import com.nwm.api.entities.ModelPVHTboxEntity;
import com.nwm.api.utils.Lib;

public class ModelPVHTboxService extends DB {

	public ModelPVHTboxEntity setModelPVHTbox(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelPVHTboxEntity dataModel = new ModelPVHTboxEntity();
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModel.setControlTriggersWord(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setManualElevationTarget(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setOperationMode(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setAngleSetpoint(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setAnglePosition(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setBatteryLevel(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setAlarms(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setMaxMotorCurrent(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setWarnings(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setPositionCode(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setNotifications(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				return dataModel;
				
			} else {
				return new ModelPVHTboxEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelPVHTboxEntity();
		}
	}

	/**
	 * @description insert data from datalogger to model_kippzonen_rt1_class8009
	 * @author long.pham
	 * @since 2021-04-02
	 * @param data from datalogger
	 */
	
	public boolean insertModelPVHTbox(ModelPVHTboxEntity obj) {
		try {
			 Object insertId = insert("ModelPVHTbox.insertModelPVHTbox", obj);
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