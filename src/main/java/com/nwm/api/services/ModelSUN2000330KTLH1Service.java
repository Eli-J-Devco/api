/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.List;

import com.nwm.api.entities.BaseAlertEnum;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSUN2000330KTLH1Entity;
import com.nwm.api.utils.Lib;

@Service
public class ModelSUN2000330KTLH1Service extends DB {

	TriggerAlertService service = new TriggerAlertService();

	enum AlertEnum implements BaseAlertEnum {

		alm_1_Abnormal_String_Power(3484, "alm_1_Abnormal_String_Power"),
		alm_1_AFCI_Self_Check_Fail(3485, "alm_1_AFCI_Self_Check_Fail"),
		alm_1_DC_Arc_Fault(3486, "alm_1_DC_Arc_Fault"),
		alm_1_Grid_Loss(3487, "alm_1_Grid_Loss"),
		alm_1_Grid_Overfrequency(3488, "alm_1_Grid_Overfrequency"),
		alm_1_Grid_Overvoltage(3489, "alm_1_Grid_Overvoltage"),
		alm_1_Grid_Underfrequency(3490, "alm_1_Grid_Underfrequency"),
		alm_1_Grid_Undervoltage(3491, "alm_1_Grid_Undervoltage"),
		alm_1_Grid_Volt_Imbalance(3492, "alm_1_Grid_Volt_Imbalance"),
		alm_1_High_String_Input_Voltage(3493, "alm_1_High_String_Input_Voltage"),
		alm_1_Output_DC_Component(3494, "alm_1_Output_DC_Component"),
		alm_1_Output_Overcurrent(3495, "alm_1_Output_Overcurrent"),
		alm_1_Phase_Wire_Short_Circuited(3496, "alm_1_Phase_Wire_Short_Circuited"),
		alm_1_String_Current_Backfeed(3497, "alm_1_String_Current_Backfeed"),
		alm_1_String_Reverse_Connection(3498, "alm_1_String_Reverse_Connection"),
		alm_1_Unstable_Grid_Frequency(3499, "alm_1_Unstable_Grid_Frequency"),
		alm_2_Abnormal_Grounding(3500, "alm_2_Abnormal_Grounding"),
		alm_2_Abnormal_PV_module_config(3501, "alm_2_Abnormal_PV_module_config"),
		alm_2_Abnormal_Residual_Current(3502, "alm_2_Abnormal_Residual_Current"),
		alm_2_Active_Islanding(3503, "alm_2_Active_Islanding"),
		alm_2_Churn_output_overload(3504, "alm_2_Churn_output_overload"),
		alm_2_Device_Fault(3505, "alm_2_Device_Fault"),
		alm_2_Faulty_Monitoring_Unit(3506, "alm_2_Faulty_Monitoring_Unit"),
		alm_2_Faulty_Power_Collector(3507, "alm_2_Faulty_Power_Collector"),
		alm_2_License_Expired(3508, "alm_2_License_Expired"),
		alm_2_Low_Insulation_Resistance(3509, "alm_2_Low_Insulation_Resistance"),
		alm_2_Overtemperature(3510, "alm_2_Overtemperature"),
		alm_2_Passive_Islanding(3511, "alm_2_Passive_Islanding"),
		alm_2_Peripheral_port_short_circuit(3512, "alm_2_Peripheral_port_short_circuit"),
		alm_2_Transient_AC_Overvoltage(3513, "alm_2_Transient_AC_Overvoltage"),
		alm_2_Upgrade_Failed_or_Version(3514, "alm_2_Upgrade_Failed_or_Version"),
		alm_3_Abnormal_CT_wiring(3515, "alm_3_Abnormal_CT_wiring"),
		alm_3_Active_scheduling_instruction(3516, "alm_3_Active_scheduling_instruction"),
		alm_3_Built_in_PID_operation_abnormal(3517, "alm_3_Built_in_PID_operation_abnormal"),
		alm_3_DC_arc_fault(3518, "alm_3_DC_arc_fault"),
		alm_3_DC_Protection_Unit_Abnormal(3519, "alm_3_DC_Protection_Unit_Abnormal"),
		alm_3_DC_switch_is_abnormal(3520, "alm_3_DC_switch_is_abnormal"),
		alm_3_EL_Cel_Abnormality(3521, "alm_3_EL_Cel_Abnormality"),
		alm_3_External_Fan_Abnormal(3522, "alm_3_External_Fan_Abnormal"),
		alm_3_High_input_string_voltage_to_ground(3523, "alm_3_High_input_string_voltage_to_ground"),
		alm_3_Internal_Fan_Abnormal(3524, "alm_3_Internal_Fan_Abnormal"),
		alm_3_On_grid_Off_grid_controller_abnormal(3525, "alm_3_On_grid_Off_grid_controller_abnormal"),
		alm_3_Optimizer_fault(3526, "alm_3_Optimizer_fault"),
		alm_3_PV_String_Loss(3527, "alm_3_PV_String_Loss"),
		alm_3_Reactive_power_dispatch_instruction_abnormal(3528, "alm_3_Reactive_power_dispatch_instruction_abnormal"),
		alm_4_AC_side_phase_sequence_reversal(3529, "alm_4_AC_side_phase_sequence_reversal"),
		alm_4_Communication_link_failure_protection(3530, "alm_4_Communication_link_failure_protection"),
		alm_4_DC_overvoltage(3531, "alm_4_DC_overvoltage"),
		alm_4_DC_reverse_connection(3532, "alm_4_DC_reverse_connection"),
		alm_4_DC_serial_connection(3533, "alm_4_DC_serial_connection"),
		alm_4_DC_short_circuit_or_reverse_connection(3534, "alm_4_DC_short_circuit_or_reverse_connection"),
		alm_4_DC_unreliable_connection(3535, "alm_4_DC_unreliable_connection"),
		alm_4_Parallel_communication_exception(3536, "alm_4_Parallel_communication_exception"),
		alm_4_String_short_circuit_to_ground(3537, "alm_4_String_short_circuit_to_ground"),
		alm_4_The_AC_terminal_temperature_is_abnormal(3538, "alm_4_The_AC_terminal_temperature_is_abnormal"),
		alm_4_The_local_access_certificate_does_not_take_effect(3539, "alm_4_The_local_access_certificate_does_not_take_effect"),
		alm_4_The_local_access_certificate_is_about_to_expired(3540, "alm_4_The_local_access_certificate_is_about_to_expired"),
		alm_4_The_management_system_does_not_take_effect(3541, "alm_4_The_management_system_does_not_take_effect"),
		alm_4_The_management_system_has_Expired(3542, "alm_4_The_management_system_has_Expired"),
		alm_4_The_management_system_Is_About_to_Expire(3543, "alm_4_The_management_system_Is_About_to_Expire"),
		alm_5_The_DC_terminal_temperature_is_abnormal(3544, "alm_5_The_DC_terminal_temperature_is_abnormal");



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
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelSUN2000330KTLH1Entity setModelSUN2000330KTLH1(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSUN2000330KTLH1Entity dataModel = new ModelSUN2000330KTLH1Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(166)) ? words.get(166) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setActive_Power(power);
				dataModel.setAlarm_1(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setAlarm_2(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setAlarm_3(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setAlarm_4(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setAlarm_5(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setAlm_1_Abnormal_String_Power(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setAlm_1_AFCI_Self_Check_Fail(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setAlm_1_DC_Arc_Fault(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setAlm_1_Grid_Loss(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setAlm_1_Grid_Overfrequency(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setAlm_1_Grid_Overvoltage(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setAlm_1_Grid_Underfrequency(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setAlm_1_Grid_Undervoltage(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setAlm_1_Grid_Volt_Imbalance(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setAlm_1_High_String_Input_Voltage(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setAlm_1_Output_DC_Component(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setAlm_1_Output_Overcurrent(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setAlm_1_Phase_Wire_Short_Circuited(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setAlm_1_String_Current_Backfeed(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setAlm_1_String_Reverse_Connection(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setAlm_1_Unstable_Grid_Frequency(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setAlm_2_Abnormal_Grounding(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setAlm_2_Abnormal_PV_module_config(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setAlm_2_Abnormal_Residual_Current(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setAlm_2_Active_Islanding(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setAlm_2_Churn_output_overload(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel.setAlm_2_Device_Fault(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setAlm_2_Faulty_Monitoring_Unit(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setAlm_2_Faulty_Power_Collector(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setAlm_2_License_Expired(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModel.setAlm_2_Low_Insulation_Resistance(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModel.setAlm_2_Overtemperature(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setAlm_2_Passive_Islanding(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModel.setAlm_2_Peripheral_port_short_circuit(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel.setAlm_2_Transient_AC_Overvoltage(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setAlm_2_Upgrade_Failed_or_Version(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModel.setAlm_3_Abnormal_CT_wiring(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModel.setAlm_3_Active_scheduling_instruction(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModel.setAlm_3_Built_in_PID_operation_abnormal(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel.setAlm_3_DC_arc_fault(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel.setAlm_3_DC_Protection_Unit_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModel.setAlm_3_DC_switch_is_abnormal(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModel.setAlm_3_EL_Cel_Abnormality(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModel.setAlm_3_External_Fan_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModel.setAlm_3_High_input_string_voltage_to_ground(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModel.setAlm_3_Internal_Fan_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				dataModel.setAlm_3_On_grid_Off_grid_controller_abnormal(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModel.setAlm_3_Optimizer_fault(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModel.setAlm_3_PV_String_Loss(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModel.setAlm_3_Reactive_power_dispatch_instruction_abnormal(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModel.setAlm_4_AC_side_phase_sequence_reversal(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModel.setAlm_4_Communication_link_failure_protection(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModel.setAlm_4_DC_overvoltage(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModel.setAlm_4_DC_reverse_connection(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModel.setAlm_4_DC_serial_connection(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModel.setAlm_4_DC_short_circuit_or_reverse_connection(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				dataModel.setAlm_4_DC_unreliable_connection(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModel.setAlm_4_Parallel_communication_exception(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModel.setAlm_4_String_short_circuit_to_ground(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModel.setAlm_4_The_AC_terminal_temperature_is_abnormal(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModel.setAlm_4_The_local_access_certificate_does_not_take_effect(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				dataModel.setAlm_4_The_local_access_certificate_is_about_to_expired(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001"));
				dataModel.setAlm_4_The_management_system_does_not_take_effect(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001"));
				dataModel.setAlm_4_The_management_system_has_Expired(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001"));
				dataModel.setAlm_4_The_management_system_Is_About_to_Expire(Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001"));
				dataModel.setAlm_5_The_DC_terminal_temperature_is_abnormal(Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001"));
				dataModel.setCabinet_Temp(Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001"));
				dataModel.setDaily_Yield(Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001"));
				dataModel.setDC_Amp_01(Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001"));
				dataModel.setDC_Amp_02(Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001"));
				dataModel.setDC_Amp_03(Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001"));
				dataModel.setDC_Amp_04(Double.parseDouble(!Lib.isBlank(words.get(76)) ? words.get(76) : "0.001"));
				dataModel.setDC_Amp_05(Double.parseDouble(!Lib.isBlank(words.get(77)) ? words.get(77) : "0.001"));
				dataModel.setDC_Amp_06(Double.parseDouble(!Lib.isBlank(words.get(78)) ? words.get(78) : "0.001"));
				dataModel.setDC_Amp_07(Double.parseDouble(!Lib.isBlank(words.get(79)) ? words.get(79) : "0.001"));
				dataModel.setDC_Amp_08(Double.parseDouble(!Lib.isBlank(words.get(80)) ? words.get(80) : "0.001"));
				dataModel.setDC_Amp_09(Double.parseDouble(!Lib.isBlank(words.get(81)) ? words.get(81) : "0.001"));
				dataModel.setDC_Amp_10(Double.parseDouble(!Lib.isBlank(words.get(82)) ? words.get(82) : "0.001"));
				dataModel.setDC_Amp_11(Double.parseDouble(!Lib.isBlank(words.get(83)) ? words.get(83) : "0.001"));
				dataModel.setDC_Amp_12(Double.parseDouble(!Lib.isBlank(words.get(84)) ? words.get(84) : "0.001"));
				dataModel.setDC_Amp_13(Double.parseDouble(!Lib.isBlank(words.get(85)) ? words.get(85) : "0.001"));
				dataModel.setDC_Amp_14(Double.parseDouble(!Lib.isBlank(words.get(86)) ? words.get(86) : "0.001"));
				dataModel.setDC_Amp_15(Double.parseDouble(!Lib.isBlank(words.get(87)) ? words.get(87) : "0.001"));
				dataModel.setDC_Amp_16(Double.parseDouble(!Lib.isBlank(words.get(88)) ? words.get(88) : "0.001"));
				dataModel.setDC_Amp_17(Double.parseDouble(!Lib.isBlank(words.get(89)) ? words.get(89) : "0.001"));
				dataModel.setDC_Amp_18(Double.parseDouble(!Lib.isBlank(words.get(90)) ? words.get(90) : "0.001"));
				dataModel.setDC_Amp_19(Double.parseDouble(!Lib.isBlank(words.get(91)) ? words.get(91) : "0.001"));
				dataModel.setDC_Amp_20(Double.parseDouble(!Lib.isBlank(words.get(92)) ? words.get(92) : "0.001"));
				dataModel.setDC_Amp_21(Double.parseDouble(!Lib.isBlank(words.get(93)) ? words.get(93) : "0.001"));
				dataModel.setDC_Amp_22(Double.parseDouble(!Lib.isBlank(words.get(94)) ? words.get(94) : "0.001"));
				dataModel.setDC_Amp_23(Double.parseDouble(!Lib.isBlank(words.get(95)) ? words.get(95) : "0.001"));
				dataModel.setDC_Amp_24(Double.parseDouble(!Lib.isBlank(words.get(96)) ? words.get(96) : "0.001"));
				dataModel.setDC_Amp_25(Double.parseDouble(!Lib.isBlank(words.get(97)) ? words.get(97) : "0.001"));
				dataModel.setDC_Amp_26(Double.parseDouble(!Lib.isBlank(words.get(98)) ? words.get(98) : "0.001"));
				dataModel.setDC_Amp_27(Double.parseDouble(!Lib.isBlank(words.get(99)) ? words.get(99) : "0.001"));
				dataModel.setDC_Amp_28(Double.parseDouble(!Lib.isBlank(words.get(100)) ? words.get(100) : "0.001"));
				dataModel.setDC_Power(Double.parseDouble(!Lib.isBlank(words.get(101)) ? words.get(101) : "0.001"));
				dataModel.setDC_Switch(Double.parseDouble(!Lib.isBlank(words.get(102)) ? words.get(102) : "0.001"));
				dataModel.setDC_Switch_2(Double.parseDouble(!Lib.isBlank(words.get(103)) ? words.get(103) : "0.001"));
				dataModel.setDC_Voltage_01(Double.parseDouble(!Lib.isBlank(words.get(104)) ? words.get(104) : "0.001"));
				dataModel.setDC_Voltage_02(Double.parseDouble(!Lib.isBlank(words.get(105)) ? words.get(105) : "0.001"));
				dataModel.setDC_Voltage_03(Double.parseDouble(!Lib.isBlank(words.get(106)) ? words.get(106) : "0.001"));
				dataModel.setDC_Voltage_04(Double.parseDouble(!Lib.isBlank(words.get(107)) ? words.get(107) : "0.001"));
				dataModel.setDC_Voltage_05(Double.parseDouble(!Lib.isBlank(words.get(108)) ? words.get(108) : "0.001"));
				dataModel.setDC_Voltage_06(Double.parseDouble(!Lib.isBlank(words.get(109)) ? words.get(109) : "0.001"));
				dataModel.setDC_Voltage_07(Double.parseDouble(!Lib.isBlank(words.get(110)) ? words.get(110) : "0.001"));
				dataModel.setDC_Voltage_08(Double.parseDouble(!Lib.isBlank(words.get(111)) ? words.get(111) : "0.001"));
				dataModel.setDC_Voltage_09(Double.parseDouble(!Lib.isBlank(words.get(112)) ? words.get(112) : "0.001"));
				dataModel.setDC_Voltage_10(Double.parseDouble(!Lib.isBlank(words.get(113)) ? words.get(113) : "0.001"));
				dataModel.setDC_Voltage_11(Double.parseDouble(!Lib.isBlank(words.get(114)) ? words.get(114) : "0.001"));
				dataModel.setDC_Voltage_12(Double.parseDouble(!Lib.isBlank(words.get(115)) ? words.get(115) : "0.001"));
				dataModel.setDC_Voltage_13(Double.parseDouble(!Lib.isBlank(words.get(116)) ? words.get(116) : "0.001"));
				dataModel.setDC_Voltage_14(Double.parseDouble(!Lib.isBlank(words.get(117)) ? words.get(117) : "0.001"));
				dataModel.setDC_Voltage_15(Double.parseDouble(!Lib.isBlank(words.get(118)) ? words.get(118) : "0.001"));
				dataModel.setDC_Voltage_16(Double.parseDouble(!Lib.isBlank(words.get(119)) ? words.get(119) : "0.001"));
				dataModel.setDC_Voltage_17(Double.parseDouble(!Lib.isBlank(words.get(120)) ? words.get(120) : "0.001"));
				dataModel.setDC_Voltage_18(Double.parseDouble(!Lib.isBlank(words.get(121)) ? words.get(121) : "0.001"));
				dataModel.setDC_Voltage_19(Double.parseDouble(!Lib.isBlank(words.get(122)) ? words.get(122) : "0.001"));
				dataModel.setDC_Voltage_20(Double.parseDouble(!Lib.isBlank(words.get(123)) ? words.get(123) : "0.001"));
				dataModel.setDC_Voltage_21(Double.parseDouble(!Lib.isBlank(words.get(124)) ? words.get(124) : "0.001"));
				dataModel.setDC_Voltage_22(Double.parseDouble(!Lib.isBlank(words.get(125)) ? words.get(125) : "0.001"));
				dataModel.setDC_Voltage_23(Double.parseDouble(!Lib.isBlank(words.get(126)) ? words.get(126) : "0.001"));
				dataModel.setDC_Voltage_24(Double.parseDouble(!Lib.isBlank(words.get(127)) ? words.get(127) : "0.001"));
				dataModel.setDC_Voltage_25(Double.parseDouble(!Lib.isBlank(words.get(128)) ? words.get(128) : "0.001"));
				dataModel.setDC_Voltage_26(Double.parseDouble(!Lib.isBlank(words.get(129)) ? words.get(129) : "0.001"));
				dataModel.setDC_Voltage_27(Double.parseDouble(!Lib.isBlank(words.get(130)) ? words.get(130) : "0.001"));
				dataModel.setDC_Voltage_28(Double.parseDouble(!Lib.isBlank(words.get(131)) ? words.get(131) : "0.001"));
				dataModel.setDSP_Data_Collection(Double.parseDouble(!Lib.isBlank(words.get(132)) ? words.get(132) : "0.001"));
				dataModel.setFrequency(Double.parseDouble(!Lib.isBlank(words.get(133)) ? words.get(133) : "0.001"));
				dataModel.setGrid_Connect_Derating_Internal(Double.parseDouble(!Lib.isBlank(words.get(134)) ? words.get(134) : "0.001"));
				dataModel.setGrid_Connect_Derating_Power(Double.parseDouble(!Lib.isBlank(words.get(135)) ? words.get(135) : "0.001"));
				dataModel.setGrid_Connect_Normal(Double.parseDouble(!Lib.isBlank(words.get(136)) ? words.get(136) : "0.001"));
				dataModel.setGrid_Contact_Stat(Double.parseDouble(!Lib.isBlank(words.get(137)) ? words.get(137) : "0.001"));
				dataModel.setIa(Double.parseDouble(!Lib.isBlank(words.get(138)) ? words.get(138) : "0.001"));
				dataModel.setIb(Double.parseDouble(!Lib.isBlank(words.get(139)) ? words.get(139) : "0.001"));
				dataModel.setIc(Double.parseDouble(!Lib.isBlank(words.get(140)) ? words.get(140) : "0.001"));
				dataModel.setInsulation_Resistance(Double.parseDouble(!Lib.isBlank(words.get(141)) ? words.get(141) : "0.001"));
				dataModel.setINV_Efficiency(Double.parseDouble(!Lib.isBlank(words.get(142)) ? words.get(142) : "0.001"));
				dataModel.setINV_Fault(Double.parseDouble(!Lib.isBlank(words.get(143)) ? words.get(143) : "0.001"));
				dataModel.setINV_Indication(Double.parseDouble(!Lib.isBlank(words.get(144)) ? words.get(144) : "0.001"));
				dataModel.setINV_Stat(Double.parseDouble(!Lib.isBlank(words.get(145)) ? words.get(145) : "0.001"));
				dataModel.setLocking_Status(Double.parseDouble(!Lib.isBlank(words.get(146)) ? words.get(146) : "0.001"));
				dataModel.setMonthly_Yield(Double.parseDouble(!Lib.isBlank(words.get(147)) ? words.get(147) : "0.001"));
				dataModel.setNormal_Stop(Double.parseDouble(!Lib.isBlank(words.get(148)) ? words.get(148) : "0.001"));
				dataModel.setOperating_Time(Double.parseDouble(!Lib.isBlank(words.get(149)) ? words.get(149) : "0.001"));
				dataModel.setPower_Factor(Double.parseDouble(!Lib.isBlank(words.get(150)) ? words.get(150) : "0.001"));
				dataModel.setReactive_Power(Double.parseDouble(!Lib.isBlank(words.get(151)) ? words.get(151) : "0.001"));
				dataModel.setShutdown(Double.parseDouble(!Lib.isBlank(words.get(152)) ? words.get(152) : "0.001"));
				dataModel.setSpotcheck(Double.parseDouble(!Lib.isBlank(words.get(153)) ? words.get(153) : "0.001"));
				dataModel.setStandby(Double.parseDouble(!Lib.isBlank(words.get(154)) ? words.get(154) : "0.001"));
				dataModel.setSTART(Double.parseDouble(!Lib.isBlank(words.get(155)) ? words.get(155) : "0.001"));
				dataModel.setSTOP(Double.parseDouble(!Lib.isBlank(words.get(156)) ? words.get(156) : "0.001"));
				dataModel.setStop_due_to_faults(Double.parseDouble(!Lib.isBlank(words.get(157)) ? words.get(157) : "0.001"));
				dataModel.setStop_due_to_Power(Double.parseDouble(!Lib.isBlank(words.get(158)) ? words.get(158) : "0.001"));
				dataModel.setTotal_Yield(Double.parseDouble(!Lib.isBlank(words.get(159)) ? words.get(159) : "0.001"));
				dataModel.setUab(Double.parseDouble(!Lib.isBlank(words.get(160)) ? words.get(160) : "0.001"));
				dataModel.setUan(Double.parseDouble(!Lib.isBlank(words.get(161)) ? words.get(161) : "0.001"));
				dataModel.setUbc(Double.parseDouble(!Lib.isBlank(words.get(162)) ? words.get(162) : "0.001"));
				dataModel.setUbn(Double.parseDouble(!Lib.isBlank(words.get(163)) ? words.get(163) : "0.001"));
				dataModel.setUca(Double.parseDouble(!Lib.isBlank(words.get(164)) ? words.get(164) : "0.001"));
				dataModel.setUcn(Double.parseDouble(!Lib.isBlank(words.get(165)) ? words.get(165) : "0.001"));
				dataModel.setYearly_Yield(energy);
				
				
				
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelSUN2000330KTLH1Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSUN2000330KTLH1Entity();
		}
	}


	/**
	 * @description insert data from datalogger to model_meter_ion_8600
	 * @author long.pham
	 * @since 2023-01-16
	 * @param  from datalogger
	 */
	
	public boolean insertModelSUN2000330KTLH1(ModelSUN2000330KTLH1Entity obj) {
		try {
			Object insertId = insert("ModelSUN2000330KTLH1.insertModelSUN2000330KTLH1", obj);
			if(insertId == null ) {
				return false;
			}
			ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
			ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
			int hours = zdtNow.getHour();
			if (hours >= 9 && hours <= 17 && obj.getEnable_alert() >= 1) {
//				service.checkTriggerAlert(obj.getDatatablename(), obj.getTime(), obj.getId_device(), ModelSUN2000330KTLH1Service.AlertEnum.values());
			}
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}


}
