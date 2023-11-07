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
import com.nwm.api.entities.ModelPoaTempEntity;
import com.nwm.api.utils.Lib;

public class ModelPoaTempService extends DB {
	/**
	 * @description set data ModelPoaTemp
	 * @author Hung.Bui
	 * @since 2023-10-16
	 * @param data
	 */
	
	public ModelPoaTempEntity setModelPoaTemp(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelPoaTempEntity dataModel = new ModelPoaTempEntity();
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModel.setT_AMB(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setT_MOD(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				
				// set custom field
				dataModel.setNvm_irradiance(0.001);
				dataModel.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				
				return dataModel;
				
			} else {
				return new ModelPoaTempEntity();
			}
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelPoaTempEntity();
		}
	}

	/**
	 * @description insert data from datalogger to model_poa_temp
	 * @author Hung.Bui
	 * @since 2023-10-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelPoaTemp(ModelPoaTempEntity obj) {
		try {
			 Object insertId = insert("ModelPoaTemp.insertModelPoaTemp", obj);
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
