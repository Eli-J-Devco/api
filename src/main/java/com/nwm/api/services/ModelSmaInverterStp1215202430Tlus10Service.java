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
import com.nwm.api.entities.ModelSmaInverterStp1215202430Tlus10Entity;
import com.nwm.api.utils.Lib;

public class ModelSmaInverterStp1215202430Tlus10Service extends DB {
	
	/**
	 * @description set data ModelSmaInverterStp1215202430Tlus10
	 * @author Duy.Phan
	 * @since 2024-02-19
	 * @param data
	 */
	
	public ModelSmaInverterStp1215202430Tlus10Entity setModelSmaInverterStp1215202430Tlus10(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSmaInverterStp1215202430Tlus10Entity dataModelSmaTlus10 = new ModelSmaInverterStp1215202430Tlus10Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				double nvmActiveEnergy = Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001");
				
				dataModelSmaTlus10.setTime(words.get(0).replace("'", ""));
				dataModelSmaTlus10.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelSmaTlus10.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelSmaTlus10.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelSmaTlus10.setPower(power);
				dataModelSmaTlus10.setPower_L1(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelSmaTlus10.setPower_L2(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelSmaTlus10.setPower_L3(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelSmaTlus10.setGrid_voltage_phase_L1(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelSmaTlus10.setGrid_voltage_phase_L2(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelSmaTlus10.setGrid_voltage_phase_L3(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelSmaTlus10.setGrid_current_phase_L1(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelSmaTlus10.setGrid_current_phase_L2(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelSmaTlus10.setGrid_current_phase_L3(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelSmaTlus10.setGrid_frequency(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelSmaTlus10.setReactive_power(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelSmaTlus10.setReactive_power_L1(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelSmaTlus10.setReactive_power_L2(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelSmaTlus10.setReactive_power_L3(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelSmaTlus10.setApparent_power(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelSmaTlus10.setApparent_power_L1(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelSmaTlus10.setApparent_power_L2(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelSmaTlus10.setApparent_power_L3(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelSmaTlus10.setDaily_yield(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelSmaTlus10.setTotal_yield(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelSmaTlus10.setOperating_time(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelSmaTlus10.setFeed_in_time(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelSmaTlus10.setDC_current_input_1(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelSmaTlus10.setDC_current_input_2(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelSmaTlus10.setDC_voltage_input_1(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelSmaTlus10.setDC_voltage_input_2(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelSmaTlus10.setDC_power_input_1(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelSmaTlus10.setDC_power_input_2(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelSmaTlus10.setDisplacement_Power_Factor(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelSmaTlus10.setEvent_Message(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelSmaTlus10.setSerial_Number(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));

				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelSmaTlus10.setNvmActivePower(power);
				dataModelSmaTlus10.setNvmActiveEnergy(nvmActiveEnergy);
				
				return dataModelSmaTlus10;
				
			} else {
				return new ModelSmaInverterStp1215202430Tlus10Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSmaInverterStp1215202430Tlus10Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_sma_inverter_stp_12_15_20_24_30_tlus10
	 * @author Duy.Phan
	 * @since 2024-02-19
	 * @param data from datalogger
	 */
	
	public boolean insertModelSmaInverterStp1215202430Tlus10(ModelSmaInverterStp1215202430Tlus10Entity obj) {
		try {
			ModelSmaInverterStp1215202430Tlus10Entity dataObj = (ModelSmaInverterStp1215202430Tlus10Entity) queryForObject("ModelSmaInverterStp1215202430Tlus10.getLastRow", obj);
			double measuredProduction = 0;
			if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
					 obj.setTotal_yield(dataObj.getNvmActiveEnergy());
				 }
			}
			obj.setMeasuredProduction(measuredProduction);
			 
			Object insertId = insert("ModelSmaInverterStp1215202430Tlus10.insertModelSmaInverterStp1215202430Tlus10", obj);
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
