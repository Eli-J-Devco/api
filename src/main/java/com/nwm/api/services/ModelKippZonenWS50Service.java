/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelKippZonenWS50Entity;
import com.nwm.api.utils.Lib;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelKippZonenWS50Service extends DB {
	/**
	 * @description set data ModelKippZonenWS50
	 * @author Minh Le
	 * @since 2026-05-13
	 * @param data
	 */
	
	public ModelKippZonenWS50Entity setModelKippZonenWS50(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelKippZonenWS50Entity dataModel = new  ModelKippZonenWS50Entity();
					
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));

				dataModel.setDeviceStatus(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setAirTemperature(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setDewPointTemperature(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setRelativeHumidity(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setRelativeAirPressure(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setAbsoluteAirPressure(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setWindDirection(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setWindSpeed(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setWindSpeed1(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setWindSpeed2(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setExternalSensorTemperature(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setTotalPrecipitationHigh(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setTotalPrecipitationLow(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setPrecipitationDifference(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setPrecipitationIntensity(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setPrecipitationTypeCode(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setRunTimeHigh(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setRunTimeLow(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setSupplyVoltage(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setSWResetCounter(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setTotalOperatingHoursHigh(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setTotalOperatingHoursLow(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				
				
				return dataModel;
				
			} else {
				return new  ModelKippZonenWS50Entity();
			}
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new  ModelKippZonenWS50Entity();
		}
	}

	/**
	 * @description insert data from datalogger to ModelKippZonenWS50
	 * @author Minh Le
	 * @since 2026-05-13
	 * @param data from datalogger
	 */
	
	public boolean insertModelKippZonenWS50(ModelKippZonenWS50Entity obj) {
		try {
			 Object insertId = insert("ModelKippZonenWS50.insertModelKippZonenWS50", obj);
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
