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
import com.nwm.api.entities.ModelShark100Entity;
import com.nwm.api.utils.Lib;

public class ModelShark100Service extends DB {

	/**
	 * @description set data ModelShark100
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelShark100Entity setModelShark100(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelShark100Entity dataModelShark100 = new ModelShark100Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001");
				if(power < 0) { power = 0.0; };
				
				dataModelShark100.setTime(words.get(0).replace("'", ""));
				dataModelShark100.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelShark100.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelShark100.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelShark100.setVolts_a_n(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelShark100.setVolts_b_n(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelShark100.setVolts_c_n(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelShark100.setVolts_a_b(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelShark100.setVolts_b_c(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelShark100.setVolts_c_a(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelShark100.setAmps_a(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				dataModelShark100.setAmps_b(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelShark100.setAmps_c(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelShark100.setWatts_3ph_total(power);
				dataModelShark100.setVars_3ph_total(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelShark100.setVas_3ph_total(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelShark100.setPower_factor_3ph_total(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelShark100.setFrequency(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelShark100.setNeutral_current(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelShark100.setW_hours_received(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				dataModelShark100.setW_hours_delivered(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelShark100.setW_hours_net(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelShark100.setW_hours_total(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelShark100.setVar_hours_positive(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelShark100.setVar_hours_negative(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelShark100.setVar_hours_net(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelShark100.setVar_hours_total(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelShark100.setVa_hours_total(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelShark100.setAmps_a_average(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelShark100.setAmps_b_average(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelShark100.setAmps_c_average(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				
				dataModelShark100.setPositive_watts_3ph_average(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelShark100.setPositive_vars_3ph_average(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelShark100.setNegative_watts_3ph_average(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelShark100.setNegative_vars_3ph_average(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelShark100.setVas_3ph_average(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelShark100.setPositive_pf_3ph_average(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelShark100.setNegative_pf_3ph_average(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelShark100.setVolts_a_n_min(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelShark100.setVolts_b_n_min(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelShark100.setVolts_c_n_min(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				
				dataModelShark100.setVolts_a_b_min(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModelShark100.setVolts_b_c_min(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelShark100.setVolts_c_a_min(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModelShark100.setAmps_a_min_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelShark100.setAmps_b_min_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModelShark100.setAmps_c_min_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelShark100.setPositive_watts_3ph_min_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelShark100.setPositive_vars_3ph_min_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelShark100.setNegative_watts_3ph_min_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModelShark100.setNegative_vars_3ph_min_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				
				dataModelShark100.setVas_3ph_min_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModelShark100.setPositive_pf_3ph_min_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModelShark100.setNegative_pf_3ph_min_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModelShark100.setFrequency_min(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModelShark100.setVolts_a_n_max(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModelShark100.setVolts_b_n_max(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModelShark100.setVolts_c_n_max(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModelShark100.setVolts_a_b_max(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModelShark100.setVolts_b_c_max(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModelShark100.setVolts_c_a_max(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				
				dataModelShark100.setAmps_a_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModelShark100.setAmps_b_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModelShark100.setAmps_c_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModelShark100.setPositive_watts_3ph_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModelShark100.setPositive_vars_3ph_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				dataModelShark100.setNegative_watts_3ph_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001"));
				dataModelShark100.setNegative_vars_3ph_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001"));
				dataModelShark100.setVas_3ph_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001"));
				dataModelShark100.setPositive_pf_3ph_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001"));
				dataModelShark100.setNegative_pf_3ph_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001"));
				
				dataModelShark100.setFrequency_max(Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001"));
				dataModelShark100.setVolts_a_n_thd(Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001"));
				dataModelShark100.setVolts_b_n_thd(Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001"));
				dataModelShark100.setVolts_c_n_thd(Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001"));
				dataModelShark100.setAmps_a_thd(Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001"));
				dataModelShark100.setAmps_b_thd(Double.parseDouble(!Lib.isBlank(words.get(76)) ? words.get(76) : "0.001"));
				dataModelShark100.setAmps_c_thd(Double.parseDouble(!Lib.isBlank(words.get(77)) ? words.get(77) : "0.001"));
				dataModelShark100.setPhase_a_current_0th(Double.parseDouble(!Lib.isBlank(words.get(78)) ? words.get(78) : "0.001"));
				dataModelShark100.setPhase_a_current_1st(Double.parseDouble(!Lib.isBlank(words.get(79)) ? words.get(79) : "0.001"));
				dataModelShark100.setPhase_a_current_2nd(Double.parseDouble(!Lib.isBlank(words.get(80)) ? words.get(80) : "0.001"));
				
				dataModelShark100.setPhase_a_current_3rd(Double.parseDouble(!Lib.isBlank(words.get(81)) ? words.get(81) : "0.001"));
				dataModelShark100.setPhase_a_current_4th(Double.parseDouble(!Lib.isBlank(words.get(82)) ? words.get(82) : "0.001"));
				dataModelShark100.setPhase_a_current_5th(Double.parseDouble(!Lib.isBlank(words.get(83)) ? words.get(83) : "0.001"));
				dataModelShark100.setPhase_a_current_6th(Double.parseDouble(!Lib.isBlank(words.get(84)) ? words.get(84) : "0.001"));
				dataModelShark100.setPhase_a_current_7th(Double.parseDouble(!Lib.isBlank(words.get(85)) ? words.get(85) : "0.001"));
				dataModelShark100.setPhase_a_voltage_0th(Double.parseDouble(!Lib.isBlank(words.get(86)) ? words.get(86) : "0.001"));
				dataModelShark100.setPhase_a_voltage_1st(Double.parseDouble(!Lib.isBlank(words.get(87)) ? words.get(87) : "0.001"));
				dataModelShark100.setPhase_a_voltage_2nd(Double.parseDouble(!Lib.isBlank(words.get(88)) ? words.get(88) : "0.001"));
				dataModelShark100.setPhase_a_voltage_3rd(Double.parseDouble(!Lib.isBlank(words.get(89)) ? words.get(89) : "0.001"));
				dataModelShark100.setPhase_b_current_0th(Double.parseDouble(!Lib.isBlank(words.get(90)) ? words.get(90) : "0.001"));
				
				dataModelShark100.setPhase_b_current_1st(Double.parseDouble(!Lib.isBlank(words.get(91)) ? words.get(91) : "0.001"));
				dataModelShark100.setPhase_b_current_2nd(Double.parseDouble(!Lib.isBlank(words.get(92)) ? words.get(92) : "0.001"));
				dataModelShark100.setPhase_b_current_3rd(Double.parseDouble(!Lib.isBlank(words.get(93)) ? words.get(93) : "0.001"));
				dataModelShark100.setPhase_b_current_4th(Double.parseDouble(!Lib.isBlank(words.get(94)) ? words.get(94) : "0.001"));
				dataModelShark100.setPhase_b_current_5th(Double.parseDouble(!Lib.isBlank(words.get(95)) ? words.get(95) : "0.001"));
				dataModelShark100.setPhase_b_current_6th(Double.parseDouble(!Lib.isBlank(words.get(96)) ? words.get(96) : "0.001"));
				dataModelShark100.setPhase_b_current_7th(Double.parseDouble(!Lib.isBlank(words.get(97)) ? words.get(97) : "0.001"));
				dataModelShark100.setPhase_b_voltage_0th(Double.parseDouble(!Lib.isBlank(words.get(98)) ? words.get(98) : "0.001"));
				dataModelShark100.setPhase_b_voltage_1st(Double.parseDouble(!Lib.isBlank(words.get(99)) ? words.get(99) : "0.001"));
				dataModelShark100.setPhase_b_voltage_2nd(Double.parseDouble(!Lib.isBlank(words.get(100)) ? words.get(100) : "0.001"));
				
				dataModelShark100.setPhase_b_voltage_3rd(Double.parseDouble(!Lib.isBlank(words.get(101)) ? words.get(101) : "0.001"));
				dataModelShark100.setPhase_c_current_0th(Double.parseDouble(!Lib.isBlank(words.get(102)) ? words.get(102) : "0.001"));
				dataModelShark100.setPhase_c_current_1st(Double.parseDouble(!Lib.isBlank(words.get(103)) ? words.get(103) : "0.001"));
				dataModelShark100.setPhase_c_current_2nd(Double.parseDouble(!Lib.isBlank(words.get(104)) ? words.get(104) : "0.001"));
				dataModelShark100.setPhase_c_current_3rd(Double.parseDouble(!Lib.isBlank(words.get(105)) ? words.get(105) : "0.001"));
				dataModelShark100.setPhase_c_current_4th(Double.parseDouble(!Lib.isBlank(words.get(106)) ? words.get(106) : "0.001"));
				dataModelShark100.setPhase_c_current_5th(Double.parseDouble(!Lib.isBlank(words.get(107)) ? words.get(107) : "0.001"));
				dataModelShark100.setPhase_c_current_6th(Double.parseDouble(!Lib.isBlank(words.get(108)) ? words.get(108) : "0.001"));
				dataModelShark100.setPhase_c_current_7th(Double.parseDouble(!Lib.isBlank(words.get(109)) ? words.get(109) : "0.001"));
				dataModelShark100.setPhase_c_voltage_0th(Double.parseDouble(!Lib.isBlank(words.get(110)) ? words.get(110) : "0.001"));
				
				dataModelShark100.setPhase_c_voltage_1st(Double.parseDouble(!Lib.isBlank(words.get(111)) ? words.get(111) : "0.001"));
				dataModelShark100.setPhase_c_voltage_2nd(Double.parseDouble(!Lib.isBlank(words.get(112)) ? words.get(112) : "0.001"));
				dataModelShark100.setPhase_c_voltage_3rd(Double.parseDouble(!Lib.isBlank(words.get(113)) ? words.get(113) : "0.001"));
				dataModelShark100.setAngle_phase_a_current(Double.parseDouble(!Lib.isBlank(words.get(114)) ? words.get(114) : "0.001"));
				dataModelShark100.setAngle_phase_b_current(Double.parseDouble(!Lib.isBlank(words.get(115)) ? words.get(115) : "0.001"));
				dataModelShark100.setAngle_phase_c_current(Double.parseDouble(!Lib.isBlank(words.get(116)) ? words.get(116) : "0.001"));
				dataModelShark100.setAngle_volts_a_b(Double.parseDouble(!Lib.isBlank(words.get(117)) ? words.get(117) : "0.001"));
				dataModelShark100.setAngle_volts_b_c(Double.parseDouble(!Lib.isBlank(words.get(118)) ? words.get(118) : "0.001"));
				dataModelShark100.setAngle_volts_c_a(Double.parseDouble(!Lib.isBlank(words.get(119)) ? words.get(119) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelShark100.setNvmActivePower(power);
				dataModelShark100.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				
				
				return dataModelShark100;
				
			} else {
				return new ModelShark100Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelShark100Entity();
		}
	}
	/**
	 * @description insert data from datalogger to model shark 100
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelShark100(ModelShark100Entity obj) {
		try {
			 ModelShark100Entity dataObj = (ModelShark100Entity) queryForObject("ModelShark100.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelShark100.insertModelShark100", obj);
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
