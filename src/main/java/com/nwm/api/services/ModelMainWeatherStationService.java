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
import com.nwm.api.entities.ModelMainWeatherStationEntity;
import com.nwm.api.utils.Lib;

@Service
public class ModelMainWeatherStationService extends DB {
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelMainWeatherStationEntity setModelMainWeatherStation(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelMainWeatherStationEntity dataModel = new ModelMainWeatherStationEntity();
				
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001");
				if(irradiance < 0 || irradiance > 5000) { irradiance = 0.0; };
				
				double temperature = !Lib.isBlank(words.get(4)) ? Double.parseDouble(words.get(4)) : 0.001;
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setMain_Ambient_Temp(temperature);
				dataModel.setMain_Module_Temp(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setMain_Pyra_Horizontal_Body_Temp(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setMain_Pyra_Horizontal_Irradiance(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setMain_Pyra_Horizontal_Supply_Voltage(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setMain_Pyra_Inclined_Body_Temp(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setMain_Pyra_Inclined_Irradiance(irradiance);
				dataModel.setMain_Pyra_Inclined_Supply_Voltage(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setWind_Direction(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setWind_Speed(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setZone_Module_Temp(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setZone_Pyra_Inclined_Body_Temp(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setZone_Pyra_Inclined_Irradiance(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setZone_Pyra_Inclined_Supply_Voltage(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				
				
				// set custom field nvm_irradiance
				dataModel.setNvm_irradiance(irradiance);
				dataModel.setNvm_temperature(temperature);
				dataModel.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0"));
				
				return dataModel;
				
			} else {
				return new ModelMainWeatherStationEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelMainWeatherStationEntity();
		}
	}


	/**
	 * @description insert data from datalogger to model_meter_ion_8600
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelMainWeatherStation(ModelMainWeatherStationEntity obj) {
		try {
			Object insertId = insert("ModelMainWeatherStation.insertModelMainWeatherStation", obj);
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
