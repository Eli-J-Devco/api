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
import com.nwm.api.entities.ModelGEMultilinEPM6000Entity;
import com.nwm.api.utils.Lib;

@Service
public class ModelGEMultilinEPM6000Service extends DB {
	/**
	 * @description set data
	 * @author Hung.Bui
	 * @since 2025-02-07
	 * @param data
	 */
	
	public ModelGEMultilinEPM6000Entity setModelGEMultilinEPM6000(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelGEMultilinEPM6000Entity dataModel = new ModelGEMultilinEPM6000Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setHI_WIND_Power_Factor(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setHI_WIND_Frequency(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setHI_WIND_Current_A(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setHI_WIND_Current_B(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setHI_WIND_Current_C(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setHI_WIND_Active_Power(power);
				dataModel.setHI_WIND_Reactive_Power(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setHI_WIND_Apparent_Power(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setHI_WIND_Voltage_AB(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setHI_WIND_Voltage_AN(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setHI_WIND_Voltage_BC(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setHI_WIND_Voltage_BN(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setHI_WIND_Voltage_CA(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setHI_WIND_Voltage_CN(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setHI_WIND_Apparent_Energy_Tot(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setHI_WIND_Reactive_Energy_Neg(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setHI_WIND_Reactive_Energy_Pos(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setHI_WIND_Reactive_Energy_Tot(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setHI_WIND_Active_Energy_Del(energy);
				dataModel.setHI_WIND_Active_Energy_Rec(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setHI_WIND_Active_Energy_Tot(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelGEMultilinEPM6000Entity();
			}
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelGEMultilinEPM6000Entity();
		}
	}
	/**
	 * @description insert data from datalogger
	 * @author Hung.Bui
	 * @since 2025-02-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelGEMultilinEPM6000(ModelGEMultilinEPM6000Entity obj) {
		try {
			Object insertId = insert("ModelGEMultilinEPM6000.insertModelGEMultilinEPM6000", obj);
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
