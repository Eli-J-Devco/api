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
import com.nwm.api.entities. ModelWeatherStationCustomEntity;
import com.nwm.api.utils.Lib;

public class ModelWeatherStationCustomService extends DB {
	/**
	 * @description set data  ModelWeatherStationCustom
	 * @author Hung.Bui
	 * @since 2023-10-16
	 * @param data
	 */
	
	public  ModelWeatherStationCustomEntity setModelWeatherStationCustom(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				 ModelWeatherStationCustomEntity dataModel = new  ModelWeatherStationCustomEntity();
				
				 Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
					
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				
				dataModel.setAIR_PRESS(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setAIR_TEMP(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setBACK_PANEL_TEMP(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setDEW_POINT(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setGH_IRRADIANCE(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setINCLINED_IRRADIANCE(irradiance);
				dataModel.setRELATIVE_HUMIDITY(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setWIND_DIRECTION(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setWIND_SPEED(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				
				// set custom field
				dataModel.setNvm_irradiance(irradiance);
				dataModel.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				
				return dataModel;
				
			} else {
				return new  ModelWeatherStationCustomEntity();
			}
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new  ModelWeatherStationCustomEntity();
		}
	}

	/**
	 * @description insert data from datalogger to model_poa_temp
	 * @author Hung.Bui
	 * @since 2023-10-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelWeatherStationCustom(ModelWeatherStationCustomEntity obj) {
		try {
			 Object insertId = insert("ModelWeatherStationCustom.insertModelWeatherStationCustom", obj);
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
