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
import com.nwm.api.entities.ModelWaterMeterKyPulseEntity;
import com.nwm.api.utils.Lib;

public class ModelWaterMeterKyPulseService extends DB {
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2024-08-13
	 * @param data
	 */
	
	public ModelWaterMeterKyPulseEntity setModelWaterMeterKyPulse(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelWaterMeterKyPulseEntity dataModelIon = new ModelWaterMeterKyPulseEntity();
				
				Double power =  0.001; // Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001");
				
				dataModelIon.setTime(words.get(0).replace("'", ""));
				dataModelIon.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelIon.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelIon.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelIon.setMODBUSID(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelIon.setBaudRate(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelIon.setParityDataStopBits(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelIon.setDataOrder(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelIon.setCounterMode(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelIon.setCounterEdge(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelIon.setPulsesPerCubicFootOfWater(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelIon.setDigitalInputBinary(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				
				dataModelIon.setPulseCounter(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				
				dataModelIon.setTrueCounter(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelIon.setTotalWaterUsage(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelIon.setNvmActivePower(power);
				dataModelIon.setNvmActiveEnergy(energy);
				
				return dataModelIon;
				
			} else {
				return new ModelWaterMeterKyPulseEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelWaterMeterKyPulseEntity();
		}
	}


	/**
	 * @description insert data from datalogger to model_ky_pulse_meter
	 * @author long.pham
	 * @since 2024-08-13
	 * @param data from datalogger
	 */
	
	public boolean insertModelWaterMeterKyPulse(ModelWaterMeterKyPulseEntity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setTotalWaterUsage(energy);
			}
			
			ModelWaterMeterKyPulseEntity dataObj = (ModelWaterMeterKyPulseEntity) queryForObject("ModelWaterMeterKyPulse.getLastRow", obj);
			// filter data 
			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setTotalWaterUsage(dataObj.getNvmActiveEnergy());
			}
			double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 obj.setNvmActivePower(measuredProduction);
			 
			 Object insertId = insert("ModelWaterMeterKyPulse.insertModelWaterMeterKyPulse", obj);
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
