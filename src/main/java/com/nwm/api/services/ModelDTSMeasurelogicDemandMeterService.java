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
import com.nwm.api.entities.ModelDTSMeasurelogicDemandMeterEntity;
import com.nwm.api.utils.Lib;

public class ModelDTSMeasurelogicDemandMeterService extends DB {
	
	/**
	 * @description set data ModelDTSMeasurelogicDemandMeter
	 * @author Hung.Bui
	 * @since 2024-01-08
	 * @param data
	 */
	
	public ModelDTSMeasurelogicDemandMeterEntity setModelDTSMeasurelogicDemandMeter(String line, double offset_data_old) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelDTSMeasurelogicDemandMeterEntity dataModelDTSMeter = new ModelDTSMeasurelogicDemandMeterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001");
				if(energy > 0) { energy = energy + offset_data_old; }
				
				dataModelDTSMeter.setTime(words.get(0).replace("'", ""));
				dataModelDTSMeter.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelDTSMeter.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelDTSMeter.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelDTSMeter.setVoltage_LN_1(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelDTSMeter.setVoltage_LN_2(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelDTSMeter.setVoltage_LN_3(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelDTSMeter.setVoltage_LL_Average(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelDTSMeter.setCurrent_1(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelDTSMeter.setCurrent_2(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelDTSMeter.setCurrent_3(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelDTSMeter.setCurrent_Total(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelDTSMeter.setCurrent_Neutral(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelDTSMeter.setFrequency_Average(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelDTSMeter.setPowerP_Total(power);
				dataModelDTSMeter.setPowerS_Total(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelDTSMeter.setPowerQ_Total(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelDTSMeter.setPowerFactor_DTS_Overall(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelDTSMeter.setEnergyP_Total(energy);
				dataModelDTSMeter.setEnergyS_Total(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelDTSMeter.setEnergyQ_Total(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelDTSMeter.setEnergyP_Total_Imp(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelDTSMeter.setEnergyP_Total_Exp(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelDTSMeter.setEnergyQ_Total_Imp(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelDTSMeter.setEnergyQ_Total_Exp(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelDTSMeter.setNvmActivePower(power);
				dataModelDTSMeter.setNvmActiveEnergy(energy);
				
				return dataModelDTSMeter;
				
			} else {
				return new ModelDTSMeasurelogicDemandMeterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelDTSMeasurelogicDemandMeterEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_dts_measurelogic_demand_meter
	 * @author Hung.Bui
	 * @since 2024-01-08
	 * @param data from datalogger
	 */
	
	public boolean insertModelDTSMeasurelogicDemandMeter(ModelDTSMeasurelogicDemandMeterEntity obj) {
		try {
			ModelDTSMeasurelogicDemandMeterEntity dataObj = (ModelDTSMeasurelogicDemandMeterEntity) queryForObject("ModelDTSMeasurelogicDemandMeter.getLastRow", obj);
			double measuredProduction = 0;
			if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
					 obj.setEnergyP_Total(dataObj.getNvmActiveEnergy());
				 }
			}
			obj.setMeasuredProduction(measuredProduction);
			 
			Object insertId = insert("ModelDTSMeasurelogicDemandMeter.insertModelDTSMeasurelogicDemandMeter", obj);
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
