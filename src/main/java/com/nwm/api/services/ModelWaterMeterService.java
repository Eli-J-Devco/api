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
import com.nwm.api.entities.ModelGasMeterEntity;
import com.nwm.api.entities.ModelWaterMeterEntity;
import com.nwm.api.utils.Lib;

public class ModelWaterMeterService extends DB {
	
	/**
	 * @description set data model_aes_tx_inverter
	 * @author long.pham
	 * @since 2023-08-02
	 * @param data
	 */
	
	public ModelWaterMeterEntity setModelAbbUnoDm1250tpPlus(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelWaterMeterEntity dataModel = new ModelWaterMeterEntity();
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModel.setReadingValue(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setNvmActivePower(Double.parseDouble("0.001"));
				dataModel.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				return dataModel;
				
			} else {
				return new ModelWaterMeterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelWaterMeterEntity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_gas_meter
	 * @author long.pham
	 * @since 2024-02-28
	 * @param data from datalogger
	 */
	
	public boolean insertModelWaterMeter(ModelWaterMeterEntity obj) {
		try {
			ModelWaterMeterEntity dataObj = (ModelWaterMeterEntity) queryForObject("ModelGasMeter.getLastRow", obj);
			double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getReadingValue() > 0 && obj.getReadingValue() > 0 && obj.getReadingValue() != 0.001 ) {
				 measuredProduction = obj.getReadingValue() - dataObj.getReadingValue();
			 }
			 Object insertId = insert("ModelWaterMeter.insertModelWaterMeter", obj);
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
