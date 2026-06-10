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
import com.nwm.api.entities.ModelKippZonenCMP214PointEntity;
import com.nwm.api.utils.Lib;

public class ModelKippZonenCMP214PointService extends DB {
	/**
	 * @description set data ModelKippZonenCMP214Point
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelKippZonenCMP214PointEntity setModelKippZonenCMP214Point(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelKippZonenCMP214PointEntity dataModel = new ModelKippZonenCMP214PointEntity();
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				Double temperature = Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001");
				
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setTemperatureCompensatedIrradiance(irradiance);
				dataModel.setInternalRelativeHumidity(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setSensorTemperature(temperature);
				dataModel.setWeatherStationtypeandmodel(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				

				// set custom field nvm_irradiance
				dataModel.setNvm_irradiance(irradiance);
				dataModel.setNvm_temperature(temperature);
				dataModel.setNvm_panel_temperature(temperature);
				
				
				return dataModel;
				
			} else {
				return new ModelKippZonenCMP214PointEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelKippZonenCMP214PointEntity();
		}
	}

	/**
	 * @description insert data from datalogger to model_rt1_class30000
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelKippZonenCMP214Point(ModelKippZonenCMP214PointEntity obj) {
		try {
			 Object insertId = insert("ModelKippZonenCMP214Point.insertModelKippZonenCMP214Point", obj);
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
