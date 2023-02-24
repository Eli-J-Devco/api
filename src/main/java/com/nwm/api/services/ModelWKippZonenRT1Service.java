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
import com.nwm.api.entities.ModelWKippZonenRT1Entity;
import com.nwm.api.utils.Lib;

public class ModelWKippZonenRT1Service extends DB {

	/**
	 * @description set data ModelWKippZonenRT1
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelWKippZonenRT1Entity setModelWKippZonenRT1(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelWKippZonenRT1Entity dataModelWkipp = new ModelWKippZonenRT1Entity();
				dataModelWkipp.setTime(words.get(0).replace("'", ""));
				dataModelWkipp.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelWkipp.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelWkipp.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelWkipp.setDeviceType(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelWkipp.setDataModelVersion(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelWkipp.setOperationalMode(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelWkipp.setStatusFlags(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelWkipp.setSunPOATempComp(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelWkipp.setPanelTemperature(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelWkipp.setExtPowerSensor(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelWkipp.setBatchNumber(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelWkipp.setSerialNumber(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelWkipp.setCalibrationDateYYMMDD(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				
				// set custom field nvm_irradiance
				dataModelWkipp.setNvm_irradiance(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelWkipp.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				
				return dataModelWkipp;
				
			} else {
				return new ModelWKippZonenRT1Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelWKippZonenRT1Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_w_kipp_zonen_rt1
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelWKippZonenRT1(ModelWKippZonenRT1Entity obj) {
		try {
			 Object insertId = insert("ModelWKippZonenRT1.insertModelWKippZonenRT1", obj);
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
