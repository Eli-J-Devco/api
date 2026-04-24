/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.BaseAlertEnum;
import com.nwm.api.entities.ModelAdvancedEnergySolaronEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelAdvancedEnergySolaronService extends DB {

	@Autowired
	private TriggerAlertService triggerAlertService;

	/**
	 * @description Marker enum for binary fault code model registration
	 * This model uses binary fault codes (active_faults1/2/3, status, warnings1, limits)
	 * instead of individual alert fields, so this enum is only used for CronJob registration.
	 * Actual alert processing is done via checkTriggerBinaryFaultCodeAlert()
	 * @since 2026-04-24
	 */
	enum AlertEnum implements BaseAlertEnum {
		// Marker enum - actual alerts are processed via binary fault codes
		BINARY_FAULT_CODE_MODEL(0, "binary_fault_code_marker");

		private final int id;
		private final String column;

		AlertEnum(int id, String column) {
			this.id = id;
			this.column = column;
		}

		public int getId() {
			return id;
		}

		public String getColumn() {
			return column;
		}
	}

	/**
	 * @description Check trigger alert for ModelAdvancedEnergySolaron (called by CronJob)
	 * This method is the entry point for CronJobAlertFieldService
	 * @author duc.pham
	 * @since 2026-04-24
	 * @param tableName Data table name
	 * @param time Current time
	 * @param deviceId Device ID
	 */
	public void checkTriggerAlertFromCronJob(String tableName, String time, int deviceId) {
		try {
			// Define fault code fields
			String[] faultCodeFields = {
				"warnings1",      // Level 6
				"status",         // Level 5
				"limits",         // Level 4
				"active_faults1", // Level 1
				"active_faults2", // Level 2
				"active_faults3"  // Level 3
			};
			
			// Define corresponding mappers
			TriggerAlertService.FaultCodeMapper[] mappers = {
				(bitPosition, level) -> LibErrorCode.GetWarningsCodeModelAdvancedSolaron(bitPosition),
				(bitPosition, level) -> LibErrorCode.GetStatusCodeModelAdvancedSolaron(bitPosition),
				(bitPosition, level) -> LibErrorCode.GetLimitCodeModelAdvancedSolaron(bitPosition),
				(bitPosition, level) -> LibErrorCode.GetErrorCodeModelAdvancedSolaron(bitPosition, 1),
				(bitPosition, level) -> LibErrorCode.GetErrorCodeModelAdvancedSolaron(bitPosition, 2),
				(bitPosition, level) -> LibErrorCode.GetErrorCodeModelAdvancedSolaron(bitPosition, 3)
			};
			
			// Define corresponding fault code levels
			int[] faultCodeLevels = {6, 5, 4, 1, 2, 3};
			
			// Call TriggerAlertService to process binary fault codes
			triggerAlertService.checkTriggerBinaryFaultCodeAlert(
				tableName, 
				time, 
				deviceId, 
				faultCodeFields, 
				mappers, 
				faultCodeLevels
			);
			
		} catch (Exception ex) {
			log.error("ModelAdvancedEnergySolaronService.checkTriggerAlertFromCronJob", ex);
		}
	}

	/**
	 * @description set data ModelAdvancedEnergySolaron
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelAdvancedEnergySolaronEntity setModelAdvancedEnergySolaron(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelAdvancedEnergySolaronEntity dataModelAdvancedEnergySolaron = new ModelAdvancedEnergySolaronEntity();
				Double power = Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001");
		
				dataModelAdvancedEnergySolaron.setTime(words.get(0).replace("'", ""));
				dataModelAdvancedEnergySolaron.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelAdvancedEnergySolaron.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelAdvancedEnergySolaron.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelAdvancedEnergySolaron.setToday_kwh(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelAdvancedEnergySolaron.setYtd_kwh_total(energy);
				dataModelAdvancedEnergySolaron.setLife_kwh_total(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelAdvancedEnergySolaron.setYtd_kwh(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelAdvancedEnergySolaron.setLife_kwh(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelAdvancedEnergySolaron.setLast_15min_kwh(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelAdvancedEnergySolaron.setTimestamp_15minutes(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelAdvancedEnergySolaron.setLast_restart(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				
				dataModelAdvancedEnergySolaron.setUptime(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelAdvancedEnergySolaron.setAc_power(power);
				dataModelAdvancedEnergySolaron.setAc_frequency(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelAdvancedEnergySolaron.setPv_voltage(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelAdvancedEnergySolaron.setPv_current(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelAdvancedEnergySolaron.setCommon_mode(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelAdvancedEnergySolaron.setAmbient_temperature(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelAdvancedEnergySolaron.setCoolant_temperature(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelAdvancedEnergySolaron.setReactor_temperature(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelAdvancedEnergySolaron.setCabinet_temperature(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				
				dataModelAdvancedEnergySolaron.setBus_voltage(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelAdvancedEnergySolaron.setGround_current(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelAdvancedEnergySolaron.setReactive_power(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelAdvancedEnergySolaron.setActive_faults1(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelAdvancedEnergySolaron.setActive_faults2(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelAdvancedEnergySolaron.setActive_faults3(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelAdvancedEnergySolaron.setStatus(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelAdvancedEnergySolaron.setWarnings1(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelAdvancedEnergySolaron.setWarnings2_reserved(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelAdvancedEnergySolaron.setWarnings3_reserved(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				
				dataModelAdvancedEnergySolaron.setLimits(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelAdvancedEnergySolaron.setYear(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelAdvancedEnergySolaron.setMonth(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelAdvancedEnergySolaron.setDay(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelAdvancedEnergySolaron.setHour(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelAdvancedEnergySolaron.setMinutes(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelAdvancedEnergySolaron.setSeconds(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelAdvancedEnergySolaron.setCurrent_time(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelAdvancedEnergySolaron.setNvmActivePower(power);
				dataModelAdvancedEnergySolaron.setNvmActiveEnergy(energy);
				return dataModelAdvancedEnergySolaron;
				
			} else {
				return new ModelAdvancedEnergySolaronEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelAdvancedEnergySolaronEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_advanced_energy_solaron
	 * @author long.pham
	 * @since 2020-12-11
	 * @param data from datalogger
	 */

	public boolean insertModelAdvancedEnergySolaron(ModelAdvancedEnergySolaronEntity obj) {
		try {
			Object insertId = insert("ModelAdvancedEnergySolaron.insertModelAdvancedEnergySolaron", obj);
			if (insertId == null) {
				return false;
			}
			
			// Check alerts using the same logic as CronJob (120-minute window)
			if (obj.getEnable_alert() >= 1) {
				checkTriggerAlertFromCronJob(obj.getDatatablename(), obj.getTime(), obj.getId_device());
			}
			
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
}
