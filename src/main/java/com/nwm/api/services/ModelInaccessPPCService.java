/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelInaccessPPCEntity;
import com.nwm.api.utils.Lib;

@Service
public class ModelInaccessPPCService extends DB {
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelInaccessPPCEntity setModelInaccessPPC(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelInaccessPPCEntity dataModel = new ModelInaccessPPCEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setANALOG_INPUT_ACTIVE_POWER_FEEDBACK(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setANALOG_INPUT_AUTO_VOLT_CTRL_FEEDBACK(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setANALOG_INPUT_POWER_FACTOR_FEEDBACK(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setANALOG_INPUT_REACTIVE_POWER_FEEDBACK(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setANALOG_OUTPUT_ACTIVE_POWER_SETPOINT(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setANALOG_OUTPUT_AUTO_VOLT_CTRL_SETPOINT(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setANALOG_OUTPUT_POWER_FACTOR_SETPOINT(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setANALOG_OUTPUT_REACTIVE_POWER_SETPOINT(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setDIGITAL_INPUT_AUTO_VOLT_REG_STAT(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setDIGITAL_INPUT_FREQ_CTRL_EVENT_STAT(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setDIGITAL_INPUT_FREQ_CTRL_STAT(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setDIGITAL_INPUT_LOCAL_REMOTE_STAT(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setDIGITAL_INPUT_P_CTRL_RAMP_RATE_STAT(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setDIGITAL_INPUT_P_CTRL_STAT(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setDIGITAL_INPUT_PF_CTRL_STAT(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setDIGITAL_INPUT_Q_CTRL_STAT(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setDIGITAL_OUTPUT_ALL_INVERTERS_ON_OFF(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setDIGITAL_OUTPUT_AUTO_VOLT_REG_SET(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setDIGITAL_OUTPUT_FREQ_CTRL_SET(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setDIGITAL_OUTPUT_LOCAL_REMOTE_SET(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setDIGITAL_OUTPUT_P_CTRL_RAMP_RATE_SET(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setDIGITAL_OUTPUT_P_CTRL_SET(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setDIGITAL_OUTPUT_PF_CTRL_SET(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setDIGITAL_OUTPUT_Q_CTRL_SET(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				
				return dataModel;
				
			} else {
				return new ModelInaccessPPCEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelInaccessPPCEntity();
		}
	}


	/**
	 * @description insert data from datalogger to model_meter_ion_8600
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelInaccessPPC(ModelInaccessPPCEntity obj) {
		try {
			Object insertId = insert("ModelInaccessPPC.insertModelInaccessPPC", obj);
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
