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
import com.nwm.api.entities.ModelHoneywellEMON3200Entity;
import com.nwm.api.utils.Lib;

public class ModelGasMeterService extends DB {
	
	/**
	 * @description set data model_aes_tx_inverter
	 * @author long.pham
	 * @since 2023-08-02
	 * @param data
	 */
	
	public ModelGasMeterEntity setModelGasMeter(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelGasMeterEntity dataModel = new ModelGasMeterEntity();
				
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				
				Double digit1 = Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0");
				Double digit2 = Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0");
				Double digit3 = Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0");
				Double digit4 = Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0");
				Double digit5 = Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0");
				Double digit7 = digit1 * 10000 + digit2 * 1000 + digit3 * 100 + digit4 * 10 + digit5;
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModel.setProcessedValue(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setDigit1(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setDigit2(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setDigit3(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setDigit4(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setDigit5(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setDigit6(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setDigit7(digit7);
				
				dataModel.setNvmActivePower(Double.parseDouble("0.001"));
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelGasMeterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelGasMeterEntity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_gas_meter
	 * @author long.pham
	 * @since 2024-02-28
	 * @param data from datalogger
	 */
	
	public boolean insertModelGasMeter(ModelGasMeterEntity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setProcessedValue(energy);
			}
			
			ModelGasMeterEntity dataObj = (ModelGasMeterEntity) queryForObject("ModelGasMeter.getLastRow", obj);
			// filter data 
//			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() < dataObj.getNvmActiveEnergy() || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
//				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				obj.setProcessedValue(dataObj.getNvmActiveEnergy());
//			}
						
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }

			 
			 obj.setMeasuredProduction(measuredProduction);
			 obj.setNvmActivePower(measuredProduction);
			 
			 Object insertId = insert("ModelGasMeter.insertModelGasMeter", obj);
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
