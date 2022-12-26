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
import com.nwm.api.entities.ModelIVTSolaronEXTEntity;
import com.nwm.api.utils.Lib;

public class ModelIVTSolaronEXTService extends DB {
	/**
	 * @description set data ModelIVTSolaronEXT
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelIVTSolaronEXTEntity setModelIVTSolaronEXT(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelIVTSolaronEXTEntity dataModelIVTSolaronEXT = new ModelIVTSolaronEXTEntity();
				
				dataModelIVTSolaronEXT.setTime(words.get(0).replace("'", ""));
				dataModelIVTSolaronEXT.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelIVTSolaronEXT.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelIVTSolaronEXT.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelIVTSolaronEXT.setToday_kwh(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelIVTSolaronEXT.setYtd_kwh_total(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelIVTSolaronEXT.setLife_kwh_total(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelIVTSolaronEXT.setYtd_kwh(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelIVTSolaronEXT.setLife_kwh(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelIVTSolaronEXT.setLast_15min_kwh(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelIVTSolaronEXT.setTimestamp_15minutes(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelIVTSolaronEXT.setLast_restart(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				
				dataModelIVTSolaronEXT.setUptime(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelIVTSolaronEXT.setAc_power(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelIVTSolaronEXT.setAc_frequency(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelIVTSolaronEXT.setPv_voltage(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelIVTSolaronEXT.setPv_current(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelIVTSolaronEXT.setCommon_mode(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelIVTSolaronEXT.setAmbient_temperature(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelIVTSolaronEXT.setCoolant_temperature(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelIVTSolaronEXT.setReactor_temperature(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelIVTSolaronEXT.setCabinet_temperature(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				
				dataModelIVTSolaronEXT.setBus_voltage(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelIVTSolaronEXT.setGround_current(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelIVTSolaronEXT.setReactive_power(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelIVTSolaronEXT.setActive_faults1(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelIVTSolaronEXT.setActive_faults2(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelIVTSolaronEXT.setActive_faults3(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelIVTSolaronEXT.setStatus(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelIVTSolaronEXT.setWarnings1(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelIVTSolaronEXT.setWarnings2_reserved(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelIVTSolaronEXT.setWarnings3_reserved(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				
				dataModelIVTSolaronEXT.setLimits(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelIVTSolaronEXT.setYear(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelIVTSolaronEXT.setMonth(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelIVTSolaronEXT.setDay(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelIVTSolaronEXT.setHour(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelIVTSolaronEXT.setMinutes(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelIVTSolaronEXT.setSeconds(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelIVTSolaronEXT.setCurrent_time(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelIVTSolaronEXT.setAc_current(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModelIVTSolaronEXT.setRequset_set_ac_power_limit(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				
				dataModelIVTSolaronEXT.setRequest_set_instantaneous_reactive_power_set_point(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelIVTSolaronEXT.setAutostart_status(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModelIVTSolaronEXT.setSet_read_reactive_power_mode(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelIVTSolaronEXT.setSet_read_p_ac_limit(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModelIVTSolaronEXT.setSet_read_instantaneous_reactive_power_set_point(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelIVTSolaronEXT.setSet_read_power_factor_set_point(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelIVTSolaronEXT.setAc_power_ramp_rate(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelIVTSolaronEXT.setReactive_power_ramp_rate(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModelIVTSolaronEXT.setPower_factor_ramp_rate(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelIVTSolaronEXT.setNvmActivePower(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelIVTSolaronEXT.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				
				return dataModelIVTSolaronEXT;
				
			} else {
				return new ModelIVTSolaronEXTEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelIVTSolaronEXTEntity();
		}
	}


	/**
	 * @description insert data from datalogger to model_ivt_solaron_ext
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelIVTSolaronEXT(ModelIVTSolaronEXTEntity obj) {
		try {
			 Object insertId = insert("ModelIVTSolaronEXT.insertModelIVTSolaronEXT", obj);
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
