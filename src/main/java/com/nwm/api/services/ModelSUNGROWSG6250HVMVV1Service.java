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
import com.nwm.api.entities.ModelSUNGROWSG6250HVMVV1Entity;
import com.nwm.api.utils.Lib;

public class ModelSUNGROWSG6250HVMVV1Service extends DB {
	/**
	 * @description set data
	 * @author Hung.Bui
	 * @since 2025-02-07
	 * @param data
	 */
	
	public ModelSUNGROWSG6250HVMVV1Entity setModelSUNGROWSG6250HVMVV1(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSUNGROWSG6250HVMVV1Entity dataModel = new ModelSUNGROWSG6250HVMVV1Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setAC_Active_Power(power);
				dataModel.setAC_Current_L1(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setAC_Current_L2(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setAC_Current_L3(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setAC_Apparent_Power(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setCO2_Emission(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setDay_Yield(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setDC_Current_Input_1(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setDC_Current_Input_2(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setDC_Power_Input(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setDC_Voltage_Input_1(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setDC_Voltage_Input_2(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setEmergency_Stop(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setAC_Frequency(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setInsulation_Neg_Mod_1(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setInsulation_Neg_Mod_2(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setInsulation_Pos_Mod_1(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setInsulation_Pos_Mod_2(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setLocal_Remote_Mode(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setOperating_Time(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setOperating_Stat(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setP_Ctrl_Percentage(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setPower_Factor(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setAC_Reactive_Power(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setSTART_STOP(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setTotal_Yield(energy);
				dataModel.setAC_Voltage_AB(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel.setAC_Voltage_BC(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setAC_Voltage_CA(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setM1ASAC_Switch_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setM1ASBypass_Board_Communication_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModel.setM1ASContactor_Contact_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModel.setM1ASCT_Unbalance(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setM1ASDC_Bypass_Forward_Overcurrent_Alarm(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModel.setM1ASDC_Sensor_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel.setM1ASDC_Switch_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setM1ASElectricity_Meter_Communication_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModel.setM1ASExternal_Power_Supply_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModel.setM1ASFan_2_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModel.setM1ASFan_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel.setM1ASGFRT_Operation(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel.setM1ASGround_Fuse_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModel.setM1ASLow_Insulation_Resistance(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModel.setM1ASTemperature_Abnormal_Alarm(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModel.setM1FS1AC_Leakage_Current_Protection(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModel.setM1FS1AC_Overcurrent(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModel.setM1FS1AC_Overvoltage(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				dataModel.setM1FS1AC_Undervoltage(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModel.setM1FS1Contactor_Fault(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModel.setM1FS1DC_Fuse_Fault(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModel.setM1FS1DC_Leakage_Current_Protection(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModel.setM1FS1DC_Overcurrent(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModel.setM1FS1DC_Overvoltage(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModel.setM1FS1DC_Undervoltage(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModel.setM1FS1Detection_Fuse_Fault(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModel.setM1FS1Fan_Fault(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModel.setM1FS1Frequency_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				dataModel.setM1FS1GFDI_Protection(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModel.setM1FS1Hardware_Fault(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModel.setM1FS1Heat_Sik_Over_Temperature(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModel.setM1FS1Islanding_Protection(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModel.setM1FS1Module_Overtemperature(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				dataModel.setM1FS1Overfrequency(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001"));
				dataModel.setM1FS1Overload_Protection(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001"));
				dataModel.setM1FS1PDP_Protection(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001"));
				dataModel.setM1FS1Reactor_Overtemperature(Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001"));
				dataModel.setM1FS1Sensor_Failure(Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001"));
				dataModel.setM1FS1Temperature_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001"));
				dataModel.setM1FS1Transformer_Overtemperature(Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001"));
				dataModel.setM1FS1Underfrequency(Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001"));
				dataModel.setM1FS2AC_Current_Unbalance(Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001"));
				dataModel.setM1FS2AC_SPD_Fault(Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001"));
				dataModel.setM1FS2DC_SPD_Fault(Double.parseDouble(!Lib.isBlank(words.get(76)) ? words.get(76) : "0.001"));
				dataModel.setM1FS2PV_Polarity_Reversed(Double.parseDouble(!Lib.isBlank(words.get(77)) ? words.get(77) : "0.001"));
				dataModel.setM1FS2AC_Fuse_Fault(Double.parseDouble(!Lib.isBlank(words.get(78)) ? words.get(78) : "0.001"));
				dataModel.setM1FS2AC_Switch_Disconnection(Double.parseDouble(!Lib.isBlank(words.get(79)) ? words.get(79) : "0.001"));
				dataModel.setM1FS2AC_Switch_Fault(Double.parseDouble(!Lib.isBlank(words.get(80)) ? words.get(80) : "0.001"));
				dataModel.setM1FS2Backup_Power_Supply_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(81)) ? words.get(81) : "0.001"));
				dataModel.setM1FS2Buffer_Contactor_Fault(Double.parseDouble(!Lib.isBlank(words.get(82)) ? words.get(82) : "0.001"));
				dataModel.setM1FS2Bypass_Reverse_Overcurrent(Double.parseDouble(!Lib.isBlank(words.get(83)) ? words.get(83) : "0.001"));
				dataModel.setM1FS2Control_Cabinet_Temperature_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(84)) ? words.get(84) : "0.001"));
				dataModel.setM1FS2Control_Power_Supply_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(85)) ? words.get(85) : "0.001"));
				dataModel.setM1FS2Current_Unbalance_2(Double.parseDouble(!Lib.isBlank(words.get(86)) ? words.get(86) : "0.001"));
				dataModel.setM1FS2Current_Unbalance_3(Double.parseDouble(!Lib.isBlank(words.get(87)) ? words.get(87) : "0.001"));
				dataModel.setM1FS2DC_Cabinet_Over_Temperature(Double.parseDouble(!Lib.isBlank(words.get(88)) ? words.get(88) : "0.001"));
				dataModel.setM1FS2DC_Fuse_Grounding_Fault(Double.parseDouble(!Lib.isBlank(words.get(89)) ? words.get(89) : "0.001"));
				dataModel.setM1FS2DC_Injection_Fault(Double.parseDouble(!Lib.isBlank(words.get(90)) ? words.get(90) : "0.001"));
				dataModel.setM1FS2DC_Switch_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(91)) ? words.get(91) : "0.001"));
				dataModel.setM1FS2DC_Switch_Fault(Double.parseDouble(!Lib.isBlank(words.get(92)) ? words.get(92) : "0.001"));
				dataModel.setM1FS2DC_Voltage_Sampling_Fault(Double.parseDouble(!Lib.isBlank(words.get(93)) ? words.get(93) : "0.001"));
				dataModel.setM1FS2Drive_Board_Fault(Double.parseDouble(!Lib.isBlank(words.get(94)) ? words.get(94) : "0.001"));
				dataModel.setM1FS2Fan_2_Fault(Double.parseDouble(!Lib.isBlank(words.get(95)) ? words.get(95) : "0.001"));
				dataModel.setM1FS2Grid_DC_Injection_Fault(Double.parseDouble(!Lib.isBlank(words.get(96)) ? words.get(96) : "0.001"));
				dataModel.setM1FS2Grid_Voltage_Unbalance(Double.parseDouble(!Lib.isBlank(words.get(97)) ? words.get(97) : "0.001"));
				dataModel.setM1FS2Insulation_Impedance(Double.parseDouble(!Lib.isBlank(words.get(98)) ? words.get(98) : "0.001"));
				dataModel.setM1FS2Reverse_Charging_Power_Supply_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(99)) ? words.get(99) : "0.001"));
				dataModel.setM1FS2Sampling_Fault(Double.parseDouble(!Lib.isBlank(words.get(100)) ? words.get(100) : "0.001"));
				dataModel.setM1FS2Soft_Start_Fault(Double.parseDouble(!Lib.isBlank(words.get(101)) ? words.get(101) : "0.001"));
				dataModel.setM1FS3Carrier_Sync_Fault(Double.parseDouble(!Lib.isBlank(words.get(102)) ? words.get(102) : "0.001"));
				dataModel.setM1FS3Device_Code_Repeat_Fault(Double.parseDouble(!Lib.isBlank(words.get(103)) ? words.get(103) : "0.001"));
				dataModel.setM1FS3Neutral_Point_Potential_Shift(Double.parseDouble(!Lib.isBlank(words.get(104)) ? words.get(104) : "0.001"));
				dataModel.setM1FS3Parallel_Operation_Communication_Fault(Double.parseDouble(!Lib.isBlank(words.get(105)) ? words.get(105) : "0.001"));
				dataModel.setM1NS1AC_Circuit_Breaker_State(Double.parseDouble(!Lib.isBlank(words.get(106)) ? words.get(106) : "0.001"));
				dataModel.setM1NS1AC_Main_Contactor_State(Double.parseDouble(!Lib.isBlank(words.get(107)) ? words.get(107) : "0.001"));
				dataModel.setM1NS1ACB_Fault_State(Double.parseDouble(!Lib.isBlank(words.get(108)) ? words.get(108) : "0.001"));
				dataModel.setM1NS1DC_Switch_State_1(Double.parseDouble(!Lib.isBlank(words.get(109)) ? words.get(109) : "0.001"));
				dataModel.setM1NS1DC_Switch_State_2(Double.parseDouble(!Lib.isBlank(words.get(110)) ? words.get(110) : "0.001"));
				dataModel.setM1NS1DC_Switch_State_3(Double.parseDouble(!Lib.isBlank(words.get(111)) ? words.get(111) : "0.001"));
				dataModel.setM1NS1DC_Switch_State_4(Double.parseDouble(!Lib.isBlank(words.get(112)) ? words.get(112) : "0.001"));
				dataModel.setM1NS1External_Power_Supply_State(Double.parseDouble(!Lib.isBlank(words.get(113)) ? words.get(113) : "0.001"));
				dataModel.setM1WSAlarm_Running(Double.parseDouble(!Lib.isBlank(words.get(114)) ? words.get(114) : "0.001"));
				dataModel.setM1WSDerating_Running(Double.parseDouble(!Lib.isBlank(words.get(115)) ? words.get(115) : "0.001"));
				dataModel.setM1WSEmergency_Stop(Double.parseDouble(!Lib.isBlank(words.get(116)) ? words.get(116) : "0.001"));
				dataModel.setM1WSFault_Stop(Double.parseDouble(!Lib.isBlank(words.get(117)) ? words.get(117) : "0.001"));
				dataModel.setM1WSInitial_Standby(Double.parseDouble(!Lib.isBlank(words.get(118)) ? words.get(118) : "0.001"));
				dataModel.setM1WSIO_DSP_Communication_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(119)) ? words.get(119) : "0.001"));
				dataModel.setM1WSIO_MDC_Communication_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(120)) ? words.get(120) : "0.001"));
				dataModel.setM1WSKey_Stop(Double.parseDouble(!Lib.isBlank(words.get(121)) ? words.get(121) : "0.001"));
				dataModel.setM1WSRunning(Double.parseDouble(!Lib.isBlank(words.get(122)) ? words.get(122) : "0.001"));
				dataModel.setM1WSSleeping(Double.parseDouble(!Lib.isBlank(words.get(123)) ? words.get(123) : "0.001"));
				dataModel.setM1WSStandby(Double.parseDouble(!Lib.isBlank(words.get(124)) ? words.get(124) : "0.001"));
				dataModel.setM1WSStarting(Double.parseDouble(!Lib.isBlank(words.get(125)) ? words.get(125) : "0.001"));
				dataModel.setM1WSStopped(Double.parseDouble(!Lib.isBlank(words.get(126)) ? words.get(126) : "0.001"));
				dataModel.setM1WSStopping(Double.parseDouble(!Lib.isBlank(words.get(127)) ? words.get(127) : "0.001"));
				dataModel.setM1WSTotal_Signal_Bit_Of_Running_State(Double.parseDouble(!Lib.isBlank(words.get(128)) ? words.get(128) : "0.001"));
				dataModel.setM1WSTotal_Stop_Bit(Double.parseDouble(!Lib.isBlank(words.get(129)) ? words.get(129) : "0.001"));
				dataModel.setM2ASAC_Switch_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(130)) ? words.get(130) : "0.001"));
				dataModel.setM2ASBypass_Board_Communication_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(131)) ? words.get(131) : "0.001"));
				dataModel.setM2ASContactor_Contact_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(132)) ? words.get(132) : "0.001"));
				dataModel.setM2ASCT_Unbalance(Double.parseDouble(!Lib.isBlank(words.get(133)) ? words.get(133) : "0.001"));
				dataModel.setM2ASDC_Bypass_Forward_Overcurrent_Alarm(Double.parseDouble(!Lib.isBlank(words.get(134)) ? words.get(134) : "0.001"));
				dataModel.setM2ASDC_Sensor_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(135)) ? words.get(135) : "0.001"));
				dataModel.setM2ASDC_Switch_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(136)) ? words.get(136) : "0.001"));
				dataModel.setM2ASElectricity_Meter_Communication_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(137)) ? words.get(137) : "0.001"));
				dataModel.setM2ASExternal_Power_Supply_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(138)) ? words.get(138) : "0.001"));
				dataModel.setM2ASFan_2_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(139)) ? words.get(139) : "0.001"));
				dataModel.setM2ASFan_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(140)) ? words.get(140) : "0.001"));
				dataModel.setM2ASGFRT_Operation(Double.parseDouble(!Lib.isBlank(words.get(141)) ? words.get(141) : "0.001"));
				dataModel.setM2ASGround_Fuse_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(142)) ? words.get(142) : "0.001"));
				dataModel.setM2ASLow_Insulation_Resistance(Double.parseDouble(!Lib.isBlank(words.get(143)) ? words.get(143) : "0.001"));
				dataModel.setM2ASTemperature_Abnormal_Alarm(Double.parseDouble(!Lib.isBlank(words.get(144)) ? words.get(144) : "0.001"));
				dataModel.setM2FS1AC_Leakage_Current_Protection(Double.parseDouble(!Lib.isBlank(words.get(145)) ? words.get(145) : "0.001"));
				dataModel.setM2FS1AC_Overcurrent(Double.parseDouble(!Lib.isBlank(words.get(146)) ? words.get(146) : "0.001"));
				dataModel.setM2FS1AC_Overvoltage(Double.parseDouble(!Lib.isBlank(words.get(147)) ? words.get(147) : "0.001"));
				dataModel.setM2FS1AC_Undervoltage(Double.parseDouble(!Lib.isBlank(words.get(148)) ? words.get(148) : "0.001"));
				dataModel.setM2FS1Contactor_Fault(Double.parseDouble(!Lib.isBlank(words.get(149)) ? words.get(149) : "0.001"));
				dataModel.setM2FS1DC_Fuse_Fault(Double.parseDouble(!Lib.isBlank(words.get(150)) ? words.get(150) : "0.001"));
				dataModel.setM2FS1DC_Leakage_Current_Protection(Double.parseDouble(!Lib.isBlank(words.get(151)) ? words.get(151) : "0.001"));
				dataModel.setM2FS1DC_Overcurrent(Double.parseDouble(!Lib.isBlank(words.get(152)) ? words.get(152) : "0.001"));
				dataModel.setM2FS1DC_Overvoltage(Double.parseDouble(!Lib.isBlank(words.get(153)) ? words.get(153) : "0.001"));
				dataModel.setM2FS1DC_Undervoltage(Double.parseDouble(!Lib.isBlank(words.get(154)) ? words.get(154) : "0.001"));
				dataModel.setM2FS1Detection_Fuse_Fault(Double.parseDouble(!Lib.isBlank(words.get(155)) ? words.get(155) : "0.001"));
				dataModel.setM2FS1Fan_Fault(Double.parseDouble(!Lib.isBlank(words.get(156)) ? words.get(156) : "0.001"));
				dataModel.setM2FS1Frequency_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(157)) ? words.get(157) : "0.001"));
				dataModel.setM2FS1GFDI_Protection(Double.parseDouble(!Lib.isBlank(words.get(158)) ? words.get(158) : "0.001"));
				dataModel.setM2FS1Hardware_Fault(Double.parseDouble(!Lib.isBlank(words.get(159)) ? words.get(159) : "0.001"));
				dataModel.setM2FS1Heat_Sik_Over_Temperature(Double.parseDouble(!Lib.isBlank(words.get(160)) ? words.get(160) : "0.001"));
				dataModel.setM2FS1Islanding_Protection(Double.parseDouble(!Lib.isBlank(words.get(161)) ? words.get(161) : "0.001"));
				dataModel.setM2FS1Module_Overtemperature(Double.parseDouble(!Lib.isBlank(words.get(162)) ? words.get(162) : "0.001"));
				dataModel.setM2FS1Overfrequency(Double.parseDouble(!Lib.isBlank(words.get(163)) ? words.get(163) : "0.001"));
				dataModel.setM2FS1Overload_Protection(Double.parseDouble(!Lib.isBlank(words.get(164)) ? words.get(164) : "0.001"));
				dataModel.setM2FS1PDP_Protection(Double.parseDouble(!Lib.isBlank(words.get(165)) ? words.get(165) : "0.001"));
				dataModel.setM2FS1Reactor_Overtemperature(Double.parseDouble(!Lib.isBlank(words.get(166)) ? words.get(166) : "0.001"));
				dataModel.setM2FS1Sensor_Failure(Double.parseDouble(!Lib.isBlank(words.get(167)) ? words.get(167) : "0.001"));
				dataModel.setM2FS1Temperature_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(168)) ? words.get(168) : "0.001"));
				dataModel.setM2FS1Transformer_Overtemperature(Double.parseDouble(!Lib.isBlank(words.get(169)) ? words.get(169) : "0.001"));
				dataModel.setM2FS1Underfrequency(Double.parseDouble(!Lib.isBlank(words.get(170)) ? words.get(170) : "0.001"));
				dataModel.setM2FS2AC_Current_Unbalance(Double.parseDouble(!Lib.isBlank(words.get(171)) ? words.get(171) : "0.001"));
				dataModel.setM2FS2AC_SPD_Fault(Double.parseDouble(!Lib.isBlank(words.get(172)) ? words.get(172) : "0.001"));
				dataModel.setM2FS2DC_SPD_Fault(Double.parseDouble(!Lib.isBlank(words.get(173)) ? words.get(173) : "0.001"));
				dataModel.setM2FS2PV_Polarity_Reversed(Double.parseDouble(!Lib.isBlank(words.get(174)) ? words.get(174) : "0.001"));
				dataModel.setM2FS2AC_Fuse_Fault(Double.parseDouble(!Lib.isBlank(words.get(175)) ? words.get(175) : "0.001"));
				dataModel.setM2FS2AC_Switch_Disconnection(Double.parseDouble(!Lib.isBlank(words.get(176)) ? words.get(176) : "0.001"));
				dataModel.setM2FS2AC_Switch_Fault(Double.parseDouble(!Lib.isBlank(words.get(177)) ? words.get(177) : "0.001"));
				dataModel.setM2FS2Backup_Power_Supply_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(178)) ? words.get(178) : "0.001"));
				dataModel.setM2FS2Buffer_Contactor_Fault(Double.parseDouble(!Lib.isBlank(words.get(179)) ? words.get(179) : "0.001"));
				dataModel.setM2FS2Bypass_Reverse_Overcurrent(Double.parseDouble(!Lib.isBlank(words.get(180)) ? words.get(180) : "0.001"));
				dataModel.setM2FS2Control_Cabinet_Temperature_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(181)) ? words.get(181) : "0.001"));
				dataModel.setM2FS2Control_Power_Supply_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(182)) ? words.get(182) : "0.001"));
				dataModel.setM2FS2Current_Unbalance_2(Double.parseDouble(!Lib.isBlank(words.get(183)) ? words.get(183) : "0.001"));
				dataModel.setM2FS2Current_Unbalance_3(Double.parseDouble(!Lib.isBlank(words.get(184)) ? words.get(184) : "0.001"));
				dataModel.setM2FS2DC_Cabinet_Over_Temperature(Double.parseDouble(!Lib.isBlank(words.get(185)) ? words.get(185) : "0.001"));
				dataModel.setM2FS2DC_Fuse_Grounding_Fault(Double.parseDouble(!Lib.isBlank(words.get(186)) ? words.get(186) : "0.001"));
				dataModel.setM2FS2DC_Injection_Fault(Double.parseDouble(!Lib.isBlank(words.get(187)) ? words.get(187) : "0.001"));
				dataModel.setM2FS2DC_Switch_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(188)) ? words.get(188) : "0.001"));
				dataModel.setM2FS2DC_Switch_Fault(Double.parseDouble(!Lib.isBlank(words.get(189)) ? words.get(189) : "0.001"));
				dataModel.setM2FS2DC_Voltage_Sampling_Fault(Double.parseDouble(!Lib.isBlank(words.get(190)) ? words.get(190) : "0.001"));
				dataModel.setM2FS2Drive_Board_Fault(Double.parseDouble(!Lib.isBlank(words.get(191)) ? words.get(191) : "0.001"));
				dataModel.setM2FS2Fan_2_Fault(Double.parseDouble(!Lib.isBlank(words.get(192)) ? words.get(192) : "0.001"));
				dataModel.setM2FS2Grid_DC_Injection_Fault(Double.parseDouble(!Lib.isBlank(words.get(193)) ? words.get(193) : "0.001"));
				dataModel.setM2FS2Grid_Voltage_Unbalance(Double.parseDouble(!Lib.isBlank(words.get(194)) ? words.get(194) : "0.001"));
				dataModel.setM2FS2Insulation_Impedance(Double.parseDouble(!Lib.isBlank(words.get(195)) ? words.get(195) : "0.001"));
				dataModel.setM2FS2Reverse_Charging_Power_Supply_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(196)) ? words.get(196) : "0.001"));
				dataModel.setM2FS2Sampling_Fault(Double.parseDouble(!Lib.isBlank(words.get(197)) ? words.get(197) : "0.001"));
				dataModel.setM2FS2Soft_Start_Fault(Double.parseDouble(!Lib.isBlank(words.get(198)) ? words.get(198) : "0.001"));
				dataModel.setM2FS3Carrier_Sync_Fault(Double.parseDouble(!Lib.isBlank(words.get(199)) ? words.get(199) : "0.001"));
				dataModel.setM2FS3Device_Code_Repeat_Fault(Double.parseDouble(!Lib.isBlank(words.get(200)) ? words.get(200) : "0.001"));
				dataModel.setM2FS3Neutral_Point_Potential_Shift(Double.parseDouble(!Lib.isBlank(words.get(201)) ? words.get(201) : "0.001"));
				dataModel.setM2FS3Parallel_Operation_Communication_Fault(Double.parseDouble(!Lib.isBlank(words.get(202)) ? words.get(202) : "0.001"));
				dataModel.setM2NS1AC_Circuit_Breaker_State(Double.parseDouble(!Lib.isBlank(words.get(203)) ? words.get(203) : "0.001"));
				dataModel.setM2NS1AC_Main_Contactor_State(Double.parseDouble(!Lib.isBlank(words.get(204)) ? words.get(204) : "0.001"));
				dataModel.setM2NS1ACB_Fault_State(Double.parseDouble(!Lib.isBlank(words.get(205)) ? words.get(205) : "0.001"));
				dataModel.setM2NS1DC_Switch_State_1(Double.parseDouble(!Lib.isBlank(words.get(206)) ? words.get(206) : "0.001"));
				dataModel.setM2NS1DC_Switch_State_2(Double.parseDouble(!Lib.isBlank(words.get(207)) ? words.get(207) : "0.001"));
				dataModel.setM2NS1DC_Switch_State_3(Double.parseDouble(!Lib.isBlank(words.get(208)) ? words.get(208) : "0.001"));
				dataModel.setM2NS1DC_Switch_State_4(Double.parseDouble(!Lib.isBlank(words.get(209)) ? words.get(209) : "0.001"));
				dataModel.setM2NS1External_Power_Supply_State(Double.parseDouble(!Lib.isBlank(words.get(210)) ? words.get(210) : "0.001"));
				dataModel.setM2WSAlarm_Running(Double.parseDouble(!Lib.isBlank(words.get(211)) ? words.get(211) : "0.001"));
				dataModel.setM2WSAnti_PID_Running(Double.parseDouble(!Lib.isBlank(words.get(212)) ? words.get(212) : "0.001"));
				dataModel.setM2WSDerating_Running(Double.parseDouble(!Lib.isBlank(words.get(213)) ? words.get(213) : "0.001"));
				dataModel.setM2WSEmergency_Stop(Double.parseDouble(!Lib.isBlank(words.get(214)) ? words.get(214) : "0.001"));
				dataModel.setM2WSFault_Stop(Double.parseDouble(!Lib.isBlank(words.get(215)) ? words.get(215) : "0.001"));
				dataModel.setM2WSInitial_Standby(Double.parseDouble(!Lib.isBlank(words.get(216)) ? words.get(216) : "0.001"));
				dataModel.setM2WSIO_DSP_Communication_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(217)) ? words.get(217) : "0.001"));
				dataModel.setM2WSIO_MDC_Communication_Abnormal(Double.parseDouble(!Lib.isBlank(words.get(218)) ? words.get(218) : "0.001"));
				dataModel.setM2WSKey_Stop(Double.parseDouble(!Lib.isBlank(words.get(219)) ? words.get(219) : "0.001"));
				dataModel.setM2WSRunning(Double.parseDouble(!Lib.isBlank(words.get(220)) ? words.get(220) : "0.001"));
				dataModel.setM2WSSleeping(Double.parseDouble(!Lib.isBlank(words.get(221)) ? words.get(221) : "0.001"));
				dataModel.setM2WSSleeping1(Double.parseDouble(!Lib.isBlank(words.get(222)) ? words.get(222) : "0.001"));
				dataModel.setM2WSStandby(Double.parseDouble(!Lib.isBlank(words.get(223)) ? words.get(223) : "0.001"));
				dataModel.setM2WSStarting(Double.parseDouble(!Lib.isBlank(words.get(224)) ? words.get(224) : "0.001"));
				dataModel.setM2WSStopped(Double.parseDouble(!Lib.isBlank(words.get(225)) ? words.get(225) : "0.001"));
				dataModel.setM2WSStopping(Double.parseDouble(!Lib.isBlank(words.get(226)) ? words.get(226) : "0.001"));
				dataModel.setM2WSTotal_Signal_Bit_Of_Running_State(Double.parseDouble(!Lib.isBlank(words.get(227)) ? words.get(227) : "0.001"));
				dataModel.setM2WSTotal_Stop_Bit(Double.parseDouble(!Lib.isBlank(words.get(228)) ? words.get(228) : "0.001"));
				dataModel.setMV_NODE_STATE_Coil_Temp_Alarm(Double.parseDouble(!Lib.isBlank(words.get(229)) ? words.get(229) : "0.001"));
				dataModel.setMV_NODE_STATE_Coil_Temp_Trip(Double.parseDouble(!Lib.isBlank(words.get(230)) ? words.get(230) : "0.001"));
				dataModel.setMV_NODE_STATE_Gas_Relay_Alarm(Double.parseDouble(!Lib.isBlank(words.get(231)) ? words.get(231) : "0.001"));
				dataModel.setMV_NODE_STATE_Gas_Relay_Trip(Double.parseDouble(!Lib.isBlank(words.get(232)) ? words.get(232) : "0.001"));
				dataModel.setMV_NODE_STATE_Low_Oil_Level_Alarm(Double.parseDouble(!Lib.isBlank(words.get(233)) ? words.get(233) : "0.001"));
				dataModel.setMV_NODE_STATE_Low_Oil_Level_Trip(Double.parseDouble(!Lib.isBlank(words.get(234)) ? words.get(234) : "0.001"));
				dataModel.setMV_NODE_STATE_MV_DS(Double.parseDouble(!Lib.isBlank(words.get(235)) ? words.get(235) : "0.001"));
				dataModel.setMV_NODE_STATE_MV_Load_Switch_1(Double.parseDouble(!Lib.isBlank(words.get(236)) ? words.get(236) : "0.001"));
				dataModel.setMV_NODE_STATE_MV_Load_Switch_2(Double.parseDouble(!Lib.isBlank(words.get(237)) ? words.get(237) : "0.001"));
				dataModel.setMV_NODE_STATE_MV_VCB(Double.parseDouble(!Lib.isBlank(words.get(238)) ? words.get(238) : "0.001"));
				dataModel.setMV_NODE_STATE_Oil_Temp_Alarm(Double.parseDouble(!Lib.isBlank(words.get(239)) ? words.get(239) : "0.001"));
				dataModel.setMV_NODE_STATE_Oil_Temp_Trip(Double.parseDouble(!Lib.isBlank(words.get(240)) ? words.get(240) : "0.001"));
				dataModel.setMV_NODE_STATE_Overcurrent_Protection(Double.parseDouble(!Lib.isBlank(words.get(241)) ? words.get(241) : "0.001"));
				dataModel.setMV_NODE_STATE_Pressure_Relief_Alarm(Double.parseDouble(!Lib.isBlank(words.get(242)) ? words.get(242) : "0.001"));
				dataModel.setMV_NODE_STATE_Pressure_Relief_Trip(Double.parseDouble(!Lib.isBlank(words.get(243)) ? words.get(243) : "0.001"));
				dataModel.setWORK_STATE_DOOR_OPEN_PROT(Double.parseDouble(!Lib.isBlank(words.get(244)) ? words.get(244) : "0.001"));
				dataModel.setWORK_STATE_EXTERNAL_EMER_STOP(Double.parseDouble(!Lib.isBlank(words.get(245)) ? words.get(245) : "0.001"));
				dataModel.setWORK_STATE_LOCAL_EMER_STOP(Double.parseDouble(!Lib.isBlank(words.get(246)) ? words.get(246) : "0.001"));
				dataModel.setWORK_STATE_MV_FAULT(Double.parseDouble(!Lib.isBlank(words.get(247)) ? words.get(247) : "0.001"));
				dataModel.setWORK_STATE_REM_EMER_STOP(Double.parseDouble(!Lib.isBlank(words.get(248)) ? words.get(248) : "0.001"));
				dataModel.setWORK_STATE_RUNNING(Double.parseDouble(!Lib.isBlank(words.get(249)) ? words.get(249) : "0.001"));
				dataModel.setWORK_STATE_SMOKE_PROT(Double.parseDouble(!Lib.isBlank(words.get(250)) ? words.get(250) : "0.001"));
				dataModel.setWORK_STATE_STOPPED(Double.parseDouble(!Lib.isBlank(words.get(251)) ? words.get(251) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelSUNGROWSG6250HVMVV1Entity();
			}
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSUNGROWSG6250HVMVV1Entity();
		}
	}
	/**
	 * @description insert data from datalogger
	 * @author Hung.Bui
	 * @since 2025-02-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelSUNGROWSG6250HVMVV1(ModelSUNGROWSG6250HVMVV1Entity obj) {
		try {
			Object insertId = insert("ModelSUNGROWSG6250HVMVV1.insert", obj);
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
