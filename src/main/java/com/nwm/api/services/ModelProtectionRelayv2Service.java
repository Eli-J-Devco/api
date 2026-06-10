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
import com.nwm.api.entities.ModelProtectionRelayv2Entity;
import com.nwm.api.utils.Lib;

@Service
public class ModelProtectionRelayv2Service extends DB {
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelProtectionRelayv2Entity setModelProtectionRelayv2(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelProtectionRelayv2Entity dataModel = new ModelProtectionRelayv2Entity();
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setCB_Close_Open_Command(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setCB_Close_Open_Stat(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setES_Close_Open_Position(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setMetering_Cos(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setMetering_Freq(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setMetering_Ia(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setMetering_Ib(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setMetering_Ic(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setMetering_P(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setMetering_Q(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setMetering_S(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setMetering_Uab(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setMetering_Uan(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setMetering_Ubc(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setMetering_Ubn(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setMetering_Uca(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setMetering_Ucn(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setRemote_Local_Stat(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setWork_Test_Position(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				
				return dataModel;
				
			} else {
				return new ModelProtectionRelayv2Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelProtectionRelayv2Entity();
		}
	}


	/**
	 * @description insert data from datalogger to model_meter_ion_8600
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelProtectionRelayv2(ModelProtectionRelayv2Entity obj) {
		try {
			Object insertId = insert("ModelProtectionRelayv2.insertModelProtectionRelayv2", obj);
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
