/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSMASUNNYCENTRALSC1000CP10Entity;
import com.nwm.api.utils.Lib;

@Service
public class ModelSMASUNNYCENTRALSC1000CP10Service extends DB {
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelSMASUNNYCENTRALSC1000CP10Entity setModelSMASUNNYCENTRALSC1000CP10(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSMASUNNYCENTRALSC1000CP10Entity dataModel = new ModelSMASUNNYCENTRALSC1000CP10Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setAC_Active_Power_W(power);
				dataModel.setAC_Apparent_Power_VA(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setAC_Current_A(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setAC_Current_L1_A(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setAC_Current_L2_A(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setAC_Current_L3_A(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setAC_Frequency_Hz(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setAC_Reactive_Power_VAR(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setAC_Voltage_AB_V(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setAC_Voltage_BC_V(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setAC_Voltage_CA_V(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setDay_Yield_kWh(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setDC_Current_Input_A(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setDC_Power_Input_W(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setDC_Voltage_Input_V(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setDC_Switch(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setEvent_ID(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setExternal_Temp_F(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setGrid_Contactor_Stat(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setHeat_Sink_Temp_F(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setInsulation_Ohms(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setInterior_Temp_F(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setOperating_Hrs(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setOperating_Stat(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setPower_Factor(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setSTART_STOP(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setTotal_Yield_kWh(energy);
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelSMASUNNYCENTRALSC1000CP10Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSMASUNNYCENTRALSC1000CP10Entity();
		}
	}


	/**
	 * @description insert data from datalogger to model_meter_ion_8600
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelSMASUNNYCENTRALSC1000CP10(ModelSMASUNNYCENTRALSC1000CP10Entity obj) {
		try {
			Object insertId = insert("ModelSMASUNNYCENTRALSC1000CP10.insertModelSMASUNNYCENTRALSC1000CP10", obj);
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
