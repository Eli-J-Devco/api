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
import com.nwm.api.entities.ModelIMTWINDAMBIENTEntity;
import com.nwm.api.utils.Lib;

public class ModelIMTWINDAMBIENTService extends DB {
	/**
	 * @description set data ModelIMTWINDAMBIENT
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelIMTWINDAMBIENTEntity setModelIMTWINDAMBIENT(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelIMTWINDAMBIENTEntity dataModel = new ModelIMTWINDAMBIENTEntity();
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setIrradiance(irradiance);
				dataModel.setWindSpeed(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setCellTemperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setAmbientTemperature1(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setAmbientTemperature2(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				

				// set custom field nvm_irradiance
				dataModel.setNvm_irradiance(irradiance);
				dataModel.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				
				
				return dataModel;
				
			} else {
				return new ModelIMTWINDAMBIENTEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelIMTWINDAMBIENTEntity();
		}
	}

	/**
	 * @description insert data from datalogger to 
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelIMTWINDAMBIENT(ModelIMTWINDAMBIENTEntity obj) {
		try {
			 Object insertId = insert("ModelIMTWINDAMBIENT.insertModelIMTWINDAMBIENT", obj);
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
