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
import com.nwm.api.entities.ModelPVPInverterEntity;
import com.nwm.api.utils.Lib;

public class ModelPVPInverterService extends DB {
	
	/**
	 * @description set data ModelAdvancedEnergySolaron
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelPVPInverterEntity setModelPVPInverter(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelPVPInverterEntity dataModelPVPInverter = new ModelPVPInverterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001");
				if(power < 0) { power = 0.0; };
				
				dataModelPVPInverter.setTime(words.get(0).replace("'", ""));
				dataModelPVPInverter.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelPVPInverter.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelPVPInverter.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelPVPInverter.setTotal_kwh_delivered(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelPVPInverter.setVolts_a_l_n(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelPVPInverter.setVolts_b_l_n(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelPVPInverter.setVolts_c_l_n(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelPVPInverter.setCurrent_a(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelPVPInverter.setCurrent_b(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelPVPInverter.setCurrent_c(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelPVPInverter.setDc_output_voltage(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				
				dataModelPVPInverter.setDc_output_current(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelPVPInverter.setLine_frenquency(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelPVPInverter.setLine_kw(power);
				dataModelPVPInverter.setInverter_operating_status(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001");
				
				dataModelPVPInverter.setInverter_fault_word0(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelPVPInverter.setInverter_fault_word1(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelPVPInverter.setInverter_fault_word2(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				
				dataModelPVPInverter.setData_comm_status(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001");
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelPVPInverter.setNvmActivePower(power);
				dataModelPVPInverter.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				return dataModelPVPInverter;
				
			} else {
				return new ModelPVPInverterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelPVPInverterEntity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_pvp_inverter
	 * @author long.pham
	 * @since 2020-12-11
	 * @param data from datalogger
	 */
	
	public boolean insertModelPVPInverter(ModelPVPInverterEntity obj) {
		try {
			ModelPVPInverterEntity dataObj = (ModelPVPInverterEntity) queryForObject("ModelPVPInverter.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
			 }
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelPVPInverter.insertModelPVPInverter", obj);
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
