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
import com.nwm.api.entities.ModelXantrexGT500EEntity;
import com.nwm.api.utils.Lib;

public class ModelXantrexGT500EService extends DB {
	/**
	 * @description set data ModelXantrexGT500E
	 * @author long.pham
	 * @since 2023-10-20
	 * @param data
	 */
	
	public ModelXantrexGT500EEntity setModelXantrexGT500E(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelXantrexGT500EEntity dataModelXantrex = new ModelXantrexGT500EEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001");
				if(power < 0) { power = 0.0; };
				
				dataModelXantrex.setTime(words.get(0).replace("'", ""));
				dataModelXantrex.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelXantrex.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelXantrex.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelXantrex.setAC_CURRENT_A(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelXantrex.setAC_CURRENT_B(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelXantrex.setAC_CURRENT_C(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelXantrex.setAC_POWER(power);
				dataModelXantrex.setAC_VOLTAGE_AB(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelXantrex.setAC_VOLTAGE_BC(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelXantrex.setAC_VOLTAGE_CA(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(11) : "0.001"));
				dataModelXantrex.setDC_CURRENT(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelXantrex.setDC_POWER(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelXantrex.setDC_VOLTAGE(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelXantrex.setENERGY_DELIVERED(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelXantrex.setFREQUENCY(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelXantrex.setSTATUS_FAULT(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelXantrex.setSTATUS_GOAL(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelXantrex.setSTATUS_INVERTER(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelXantrex.setSTATUS_OPERATING(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelXantrex.setSTATUS_PV(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelXantrex.setT_LEFT_MATRIX(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelXantrex.setT_RIGHT_MATRIX(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));

				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelXantrex.setNvmActivePower(power);
				dataModelXantrex.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				
				
				return dataModelXantrex;
				
			} else {
				return new ModelXantrexGT500EEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelXantrexGT500EEntity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_xantrex_gt500e
	 * @author long.pham
	 * @since 2020-12-11
	 * @param data from datalogger
	 */

	public boolean insertModelXantrexGT500EService(ModelXantrexGT500EEntity obj) {
		try {
			ModelXantrexGT500EEntity dataObj = (ModelXantrexGT500EEntity) queryForObject("ModelXantrexGT500E.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}

//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }
			 obj.setMeasuredProduction(measuredProduction);
			 
			Object insertId = insert("ModelXantrexGT500E.insertModelXantrexGT500E", obj);
			if (insertId == null) {
				return false;
			}
			
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
}
