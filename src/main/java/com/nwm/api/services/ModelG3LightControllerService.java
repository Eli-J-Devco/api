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
import com.nwm.api.entities.ModelG3LightControllerEntity;
import com.nwm.api.utils.Lib;

public class ModelG3LightControllerService extends DB {

	/**
	 * @description set data ModelG3LightController
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelG3LightControllerEntity setModelG3LightController(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelG3LightControllerEntity dataModelG3LightController = new ModelG3LightControllerEntity();
				
				dataModelG3LightController.setTime(words.get(0).replace("'", ""));
				dataModelG3LightController.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelG3LightController.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelG3LightController.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelG3LightController.setInputsStatus132(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelG3LightController.setInputsManualFlag132(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelG3LightController.setZonesStatus132(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelG3LightController.setZonesManualFlag132(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelG3LightController.setZonesFeedbackState132(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelG3LightController.setZonesAlarmState132(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelG3LightController.setBreakersPanel0L(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				dataModelG3LightController.setBreakersPanel0R(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelG3LightController.setBreakersPanel1L(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelG3LightController.setBreakersPanel1R(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelG3LightController.setBreakersPanel2L(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelG3LightController.setBreakersPanel2R(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				
				
				dataModelG3LightController.setBreakerPresentPanel0L(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelG3LightController.setBreakerPresentPanel0R(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelG3LightController.setBreakerPresentPanel1L(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				
				dataModelG3LightController.setBreakerPresentPanel1R(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelG3LightController.setBreakerPresentPanel2L(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelG3LightController.setBreakerPresentPanel2R(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				
				
				dataModelG3LightController.setNonRespondingBreakersPanel0L(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelG3LightController.setNonRespondingBreakersPanel0R(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelG3LightController.setNonRespondingBreakersPanel1L(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelG3LightController.setNonRespondingBreakersPanel1R(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelG3LightController.setNonRespondingBreakersPanel2L(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelG3LightController.setNonRespondingBreakersPanel2R(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelG3LightController.setScheduleStatus116(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelG3LightController.setSchedule1PeriodStatus124(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				
				
				return dataModelG3LightController;
				
			} else {
				return new ModelG3LightControllerEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelG3LightControllerEntity();
		}
	}
	/**
	 * @description insert data from datalogger to model shark 100
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelG3LightController(ModelG3LightControllerEntity obj) {
		try {			 
			 Object insertId = insert("ModelG3LightController.insertModelG3LightController", obj);
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
