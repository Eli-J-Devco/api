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
import com.nwm.api.entities.ModelAtonometricsMT2BOMSensorEntity;
import com.nwm.api.utils.Lib;

public class ModelAtonometricsMT2BOMSensorService extends DB {

	public ModelAtonometricsMT2BOMSensorEntity setModelAtonometricsMT2BOMSensor(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelAtonometricsMT2BOMSensorEntity dataModel = new ModelAtonometricsMT2BOMSensorEntity();
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModel.setModuleTemperature(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				
				return dataModel;
				
			} else {
				return new ModelAtonometricsMT2BOMSensorEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelAtonometricsMT2BOMSensorEntity();
		}
	}

	/**
	 * @description insert data from datalogger to model_kippzonen_rt1_class8009
	 * @author long.pham
	 * @since 2021-04-02
	 * @param data from datalogger
	 */
	
	public boolean insertModelAtonometricsMT2BOMSensor(ModelAtonometricsMT2BOMSensorEntity obj) {
		try {
			 Object insertId = insert("ModelAtonometricsMT2BOMSensor.insertModelAtonometricsMT2BOMSensor", obj);
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