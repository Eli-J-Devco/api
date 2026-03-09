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
import com.nwm.api.entities.ModelSungrowPv24hScbEntity;
import com.nwm.api.utils.Lib;

@Service
public class ModelSungrowPv24hScbService extends DB {
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelSungrowPv24hScbEntity setModelSungrowPv24hScb(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSungrowPv24hScbEntity dataModel = new ModelSungrowPv24hScbEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(215)) ? words.get(215) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setAmp_01(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setAmp_02(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setAmp_03(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setAmp_04(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setAmp_05(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setAmp_06(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setAmp_07(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				
				dataModel.setAmp_08(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setAmp_09(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setAmp_10(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setAmp_11(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setAmp_12(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setAmp_13(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setAmp_14(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setAmp_15(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setAmp_16(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setAmp_17(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				
				dataModel.setAmp_18(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setAmp_19(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setAmp_20(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setAmp_21(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setAmp_22(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setAmp_23(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setAmp_24(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setDaily_yield(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setDc_amp(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setDc_bus_volt(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				
				dataModel.setDc_power(power);
				dataModel.setFuse_blow_01(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setFuse_blow_02(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setFuse_blow_03(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModel.setFuse_blow_04(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModel.setFuse_blow_05(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setFuse_blow_06(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModel.setFuse_blow_07(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel.setFuse_blow_08(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setFuse_blow_09(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				
				dataModel.setFuse_blow_10(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModel.setFuse_blow_11(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModel.setFuse_blow_12(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel.setFuse_blow_13(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel.setFuse_blow_14(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModel.setFuse_blow_15(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModel.setFuse_blow_16(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModel.setFuse_blow_17(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModel.setFuse_blow_18(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModel.setFuse_blow_19(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				
				dataModel.setFuse_blow_20(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModel.setFuse_blow_21(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModel.setFuse_blow_22(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModel.setFuse_blow_23(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModel.setFuse_blow_24(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModel.setHigh_current_01(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModel.setHigh_current_02(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModel.setHigh_current_03(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModel.setHigh_current_04(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModel.setHigh_current_05(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				
				dataModel.setHigh_current_06(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModel.setHigh_current_07(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModel.setHigh_current_08(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModel.setHigh_current_09(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModel.setHigh_current_10(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				dataModel.setHigh_current_11(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001"));
				dataModel.setHigh_current_12(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001"));
				dataModel.setHigh_current_13(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001"));
				dataModel.setHigh_current_14(Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001"));
				dataModel.setHigh_current_15(Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001"));
				
				dataModel.setHigh_current_16(Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001"));
				dataModel.setHigh_current_17(Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001"));
				dataModel.setHigh_current_18(Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001"));
				dataModel.setHigh_current_19(Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001"));
				dataModel.setHigh_current_20(Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001"));
				dataModel.setHigh_current_21(Double.parseDouble(!Lib.isBlank(words.get(76)) ? words.get(76) : "0.001"));
				dataModel.setHigh_current_22(Double.parseDouble(!Lib.isBlank(words.get(77)) ? words.get(77) : "0.001"));
				dataModel.setHigh_current_23(Double.parseDouble(!Lib.isBlank(words.get(78)) ? words.get(78) : "0.001"));
				dataModel.setHigh_current_24(Double.parseDouble(!Lib.isBlank(words.get(79)) ? words.get(79) : "0.001"));
				dataModel.setLow_current_01(Double.parseDouble(!Lib.isBlank(words.get(80)) ? words.get(80) : "0.001"));
				
				dataModel.setLow_current_02(Double.parseDouble(!Lib.isBlank(words.get(81)) ? words.get(81) : "0.001"));
				dataModel.setLow_current_03(Double.parseDouble(!Lib.isBlank(words.get(82)) ? words.get(82) : "0.001"));
				dataModel.setLow_current_04(Double.parseDouble(!Lib.isBlank(words.get(83)) ? words.get(83) : "0.001"));
				dataModel.setLow_current_05(Double.parseDouble(!Lib.isBlank(words.get(84)) ? words.get(84) : "0.001"));
				dataModel.setLow_current_06(Double.parseDouble(!Lib.isBlank(words.get(85)) ? words.get(85) : "0.001"));
				
//				dataModel.setLow_current_06(Double.parseDouble(!Lib.isBlank(words.get(86)) ? words.get(86) : "0.001"));
				
				dataModel.setLow_current_07(Double.parseDouble(!Lib.isBlank(words.get(86)) ? words.get(86) : "0.001"));
				dataModel.setLow_current_08(Double.parseDouble(!Lib.isBlank(words.get(87)) ? words.get(87) : "0.001"));
				dataModel.setLow_current_09(Double.parseDouble(!Lib.isBlank(words.get(88)) ? words.get(88) : "0.001"));
				dataModel.setLow_current_10(Double.parseDouble(!Lib.isBlank(words.get(89)) ? words.get(89) : "0.001"));
				
				dataModel.setLow_current_11(Double.parseDouble(!Lib.isBlank(words.get(90)) ? words.get(90) : "0.001"));
				dataModel.setLow_current_12(Double.parseDouble(!Lib.isBlank(words.get(91)) ? words.get(91) : "0.001"));
				dataModel.setLow_current_13(Double.parseDouble(!Lib.isBlank(words.get(92)) ? words.get(92) : "0.001"));
				dataModel.setLow_current_14(Double.parseDouble(!Lib.isBlank(words.get(93)) ? words.get(93) : "0.001"));
				dataModel.setLow_current_15(Double.parseDouble(!Lib.isBlank(words.get(94)) ? words.get(94) : "0.001"));
				dataModel.setLow_current_16(Double.parseDouble(!Lib.isBlank(words.get(95)) ? words.get(95) : "0.001"));
				dataModel.setLow_current_17(Double.parseDouble(!Lib.isBlank(words.get(96)) ? words.get(96) : "0.001"));
				dataModel.setLow_current_18(Double.parseDouble(!Lib.isBlank(words.get(97)) ? words.get(97) : "0.001"));
				dataModel.setLow_current_19(Double.parseDouble(!Lib.isBlank(words.get(98)) ? words.get(98) : "0.001"));
				dataModel.setLow_current_20(Double.parseDouble(!Lib.isBlank(words.get(99)) ? words.get(99) : "0.001"));
				
				dataModel.setLow_current_21(Double.parseDouble(!Lib.isBlank(words.get(100)) ? words.get(100) : "0.001"));
				dataModel.setLow_current_22(Double.parseDouble(!Lib.isBlank(words.get(101)) ? words.get(101) : "0.001"));
				dataModel.setLow_current_23(Double.parseDouble(!Lib.isBlank(words.get(102)) ? words.get(102) : "0.001"));
				dataModel.setLow_current_24(Double.parseDouble(!Lib.isBlank(words.get(103)) ? words.get(103) : "0.001"));
				dataModel.setOpen_circuit_01(Double.parseDouble(!Lib.isBlank(words.get(104)) ? words.get(104) : "0.001"));
				dataModel.setOpen_circuit_02(Double.parseDouble(!Lib.isBlank(words.get(105)) ? words.get(105) : "0.001"));
				dataModel.setOpen_circuit_03(Double.parseDouble(!Lib.isBlank(words.get(106)) ? words.get(106) : "0.001"));
				dataModel.setOpen_circuit_04(Double.parseDouble(!Lib.isBlank(words.get(107)) ? words.get(107) : "0.001"));
				dataModel.setOpen_circuit_05(Double.parseDouble(!Lib.isBlank(words.get(108)) ? words.get(108) : "0.001"));
				dataModel.setOpen_circuit_06(Double.parseDouble(!Lib.isBlank(words.get(109)) ? words.get(109) : "0.001"));
				
				dataModel.setOpen_circuit_07(Double.parseDouble(!Lib.isBlank(words.get(110)) ? words.get(110) : "0.001"));
				dataModel.setOpen_circuit_08(Double.parseDouble(!Lib.isBlank(words.get(111)) ? words.get(111) : "0.001"));
				dataModel.setOpen_circuit_09(Double.parseDouble(!Lib.isBlank(words.get(112)) ? words.get(112) : "0.001"));
				dataModel.setOpen_circuit_10(Double.parseDouble(!Lib.isBlank(words.get(113)) ? words.get(113) : "0.001"));
				dataModel.setOpen_circuit_11(Double.parseDouble(!Lib.isBlank(words.get(114)) ? words.get(114) : "0.001"));
				dataModel.setOpen_circuit_12(Double.parseDouble(!Lib.isBlank(words.get(115)) ? words.get(115) : "0.001"));
				dataModel.setOpen_circuit_13(Double.parseDouble(!Lib.isBlank(words.get(116)) ? words.get(116) : "0.001"));
				dataModel.setOpen_circuit_14(Double.parseDouble(!Lib.isBlank(words.get(117)) ? words.get(117) : "0.001"));
				dataModel.setOpen_circuit_15(Double.parseDouble(!Lib.isBlank(words.get(118)) ? words.get(118) : "0.001"));
				dataModel.setOpen_circuit_16(Double.parseDouble(!Lib.isBlank(words.get(119)) ? words.get(119) : "0.001"));
				
				dataModel.setOpen_circuit_17(Double.parseDouble(!Lib.isBlank(words.get(120)) ? words.get(120) : "0.001"));
				dataModel.setOpen_circuit_18(Double.parseDouble(!Lib.isBlank(words.get(121)) ? words.get(121) : "0.001"));
				dataModel.setOpen_circuit_19(Double.parseDouble(!Lib.isBlank(words.get(122)) ? words.get(122) : "0.001"));
				dataModel.setOpen_circuit_20(Double.parseDouble(!Lib.isBlank(words.get(123)) ? words.get(123) : "0.001"));
				dataModel.setOpen_circuit_21(Double.parseDouble(!Lib.isBlank(words.get(124)) ? words.get(124) : "0.001"));
				dataModel.setOpen_circuit_22(Double.parseDouble(!Lib.isBlank(words.get(125)) ? words.get(125) : "0.001"));
				dataModel.setOpen_circuit_23(Double.parseDouble(!Lib.isBlank(words.get(126)) ? words.get(126) : "0.001"));
				dataModel.setOpen_circuit_24(Double.parseDouble(!Lib.isBlank(words.get(127)) ? words.get(127) : "0.001"));
				dataModel.setPower_01(Double.parseDouble(!Lib.isBlank(words.get(128)) ? words.get(128) : "0.001"));
				dataModel.setPower_02(Double.parseDouble(!Lib.isBlank(words.get(129)) ? words.get(129) : "0.001"));
				
				dataModel.setPower_03(Double.parseDouble(!Lib.isBlank(words.get(130)) ? words.get(130) : "0.001"));
				dataModel.setPower_04(Double.parseDouble(!Lib.isBlank(words.get(131)) ? words.get(131) : "0.001"));
				dataModel.setPower_05(Double.parseDouble(!Lib.isBlank(words.get(132)) ? words.get(132) : "0.001"));
				dataModel.setPower_06(Double.parseDouble(!Lib.isBlank(words.get(133)) ? words.get(133) : "0.001"));
				dataModel.setPower_07(Double.parseDouble(!Lib.isBlank(words.get(134)) ? words.get(134) : "0.001"));
				dataModel.setPower_08(Double.parseDouble(!Lib.isBlank(words.get(135)) ? words.get(135) : "0.001"));
				dataModel.setPower_09(Double.parseDouble(!Lib.isBlank(words.get(136)) ? words.get(136) : "0.001"));
				dataModel.setPower_10(Double.parseDouble(!Lib.isBlank(words.get(137)) ? words.get(137) : "0.001"));
				dataModel.setPower_11(Double.parseDouble(!Lib.isBlank(words.get(138)) ? words.get(138) : "0.001"));
				dataModel.setPower_12(Double.parseDouble(!Lib.isBlank(words.get(139)) ? words.get(139) : "0.001"));
				
				dataModel.setPower_13(Double.parseDouble(!Lib.isBlank(words.get(140)) ? words.get(140) : "0.001"));
				dataModel.setPower_14(Double.parseDouble(!Lib.isBlank(words.get(141)) ? words.get(141) : "0.001"));
				dataModel.setPower_15(Double.parseDouble(!Lib.isBlank(words.get(142)) ? words.get(142) : "0.001"));
				dataModel.setPower_16(Double.parseDouble(!Lib.isBlank(words.get(143)) ? words.get(143) : "0.001"));
				dataModel.setPower_17(Double.parseDouble(!Lib.isBlank(words.get(144)) ? words.get(144) : "0.001"));
				dataModel.setPower_18(Double.parseDouble(!Lib.isBlank(words.get(145)) ? words.get(145) : "0.001"));
				dataModel.setPower_19(Double.parseDouble(!Lib.isBlank(words.get(146)) ? words.get(146) : "0.001"));
				dataModel.setPower_20(Double.parseDouble(!Lib.isBlank(words.get(147)) ? words.get(147) : "0.001"));
				dataModel.setPower_21(Double.parseDouble(!Lib.isBlank(words.get(148)) ? words.get(148) : "0.001"));
				dataModel.setPower_22(Double.parseDouble(!Lib.isBlank(words.get(149)) ? words.get(149) : "0.001"));
				
				dataModel.setPower_23(Double.parseDouble(!Lib.isBlank(words.get(150)) ? words.get(150) : "0.001"));
				dataModel.setPower_24(Double.parseDouble(!Lib.isBlank(words.get(151)) ? words.get(151) : "0.001"));
				dataModel.setReverse_current_01(Double.parseDouble(!Lib.isBlank(words.get(152)) ? words.get(152) : "0.001"));
				dataModel.setReverse_current_02(Double.parseDouble(!Lib.isBlank(words.get(153)) ? words.get(153) : "0.001"));
				dataModel.setReverse_current_03(Double.parseDouble(!Lib.isBlank(words.get(154)) ? words.get(154) : "0.001"));
				dataModel.setReverse_current_04(Double.parseDouble(!Lib.isBlank(words.get(155)) ? words.get(155) : "0.001"));
				dataModel.setReverse_current_05(Double.parseDouble(!Lib.isBlank(words.get(156)) ? words.get(156) : "0.001"));
				dataModel.setReverse_current_06(Double.parseDouble(!Lib.isBlank(words.get(157)) ? words.get(157) : "0.001"));
				dataModel.setReverse_current_07(Double.parseDouble(!Lib.isBlank(words.get(158)) ? words.get(158) : "0.001"));
				dataModel.setReverse_current_08(Double.parseDouble(!Lib.isBlank(words.get(159)) ? words.get(159) : "0.001"));
				
				dataModel.setReverse_current_09(Double.parseDouble(!Lib.isBlank(words.get(160)) ? words.get(160) : "0.001"));
				dataModel.setReverse_current_10(Double.parseDouble(!Lib.isBlank(words.get(161)) ? words.get(161) : "0.001"));
				dataModel.setReverse_current_11(Double.parseDouble(!Lib.isBlank(words.get(162)) ? words.get(162) : "0.001"));
				dataModel.setReverse_current_12(Double.parseDouble(!Lib.isBlank(words.get(163)) ? words.get(163) : "0.001"));
				dataModel.setReverse_current_13(Double.parseDouble(!Lib.isBlank(words.get(164)) ? words.get(164) : "0.001"));
				dataModel.setReverse_current_14(Double.parseDouble(!Lib.isBlank(words.get(165)) ? words.get(165) : "0.001"));
				dataModel.setReverse_current_15(Double.parseDouble(!Lib.isBlank(words.get(166)) ? words.get(166) : "0.001"));
				dataModel.setReverse_current_16(Double.parseDouble(!Lib.isBlank(words.get(167)) ? words.get(167) : "0.001"));
				dataModel.setReverse_current_17(Double.parseDouble(!Lib.isBlank(words.get(168)) ? words.get(168) : "0.001"));
				dataModel.setReverse_current_18(Double.parseDouble(!Lib.isBlank(words.get(169)) ? words.get(169) : "0.001"));
				
				dataModel.setReverse_current_19(Double.parseDouble(!Lib.isBlank(words.get(170)) ? words.get(170) : "0.001"));
				dataModel.setReverse_current_20(Double.parseDouble(!Lib.isBlank(words.get(171)) ? words.get(171) : "0.001"));
				dataModel.setReverse_current_21(Double.parseDouble(!Lib.isBlank(words.get(172)) ? words.get(172) : "0.001"));
				dataModel.setReverse_current_22(Double.parseDouble(!Lib.isBlank(words.get(173)) ? words.get(173) : "0.001"));
				dataModel.setReverse_current_23(Double.parseDouble(!Lib.isBlank(words.get(174)) ? words.get(174) : "0.001"));
				dataModel.setReverse_current_24(Double.parseDouble(!Lib.isBlank(words.get(175)) ? words.get(175) : "0.001"));
				dataModel.setShort_circuit_01(Double.parseDouble(!Lib.isBlank(words.get(176)) ? words.get(176) : "0.001"));
				dataModel.setShort_circuit_02(Double.parseDouble(!Lib.isBlank(words.get(177)) ? words.get(177) : "0.001"));
				dataModel.setShort_circuit_03(Double.parseDouble(!Lib.isBlank(words.get(178)) ? words.get(178) : "0.001"));
				dataModel.setShort_circuit_04(Double.parseDouble(!Lib.isBlank(words.get(179)) ? words.get(179) : "0.001"));
				
				dataModel.setShort_circuit_05(Double.parseDouble(!Lib.isBlank(words.get(180)) ? words.get(180) : "0.001"));
				dataModel.setShort_circuit_06(Double.parseDouble(!Lib.isBlank(words.get(181)) ? words.get(181) : "0.001"));
				dataModel.setShort_circuit_07(Double.parseDouble(!Lib.isBlank(words.get(182)) ? words.get(182) : "0.001"));
				dataModel.setShort_circuit_08(Double.parseDouble(!Lib.isBlank(words.get(183)) ? words.get(183) : "0.001"));
				dataModel.setShort_circuit_09(Double.parseDouble(!Lib.isBlank(words.get(184)) ? words.get(184) : "0.001"));
				dataModel.setShort_circuit_10(Double.parseDouble(!Lib.isBlank(words.get(185)) ? words.get(185) : "0.001"));
				dataModel.setShort_circuit_11(Double.parseDouble(!Lib.isBlank(words.get(186)) ? words.get(186) : "0.001"));
				dataModel.setShort_circuit_12(Double.parseDouble(!Lib.isBlank(words.get(187)) ? words.get(187) : "0.001"));
				dataModel.setShort_circuit_13(Double.parseDouble(!Lib.isBlank(words.get(188)) ? words.get(188) : "0.001"));
				dataModel.setShort_circuit_14(Double.parseDouble(!Lib.isBlank(words.get(189)) ? words.get(189) : "0.001"));
				
				dataModel.setShort_circuit_15(Double.parseDouble(!Lib.isBlank(words.get(190)) ? words.get(190) : "0.001"));
				dataModel.setShort_circuit_16(Double.parseDouble(!Lib.isBlank(words.get(191)) ? words.get(191) : "0.001"));
				dataModel.setShort_circuit_17(Double.parseDouble(!Lib.isBlank(words.get(192)) ? words.get(192) : "0.001"));
				dataModel.setShort_circuit_18(Double.parseDouble(!Lib.isBlank(words.get(193)) ? words.get(193) : "0.001"));
				dataModel.setShort_circuit_19(Double.parseDouble(!Lib.isBlank(words.get(194)) ? words.get(194) : "0.001"));
				dataModel.setShort_circuit_20(Double.parseDouble(!Lib.isBlank(words.get(195)) ? words.get(195) : "0.001"));
				dataModel.setShort_circuit_21(Double.parseDouble(!Lib.isBlank(words.get(196)) ? words.get(196) : "0.001"));
				dataModel.setShort_circuit_22(Double.parseDouble(!Lib.isBlank(words.get(197)) ? words.get(197) : "0.001"));
				dataModel.setShort_circuit_23(Double.parseDouble(!Lib.isBlank(words.get(198)) ? words.get(198) : "0.001"));
				dataModel.setShort_circuit_24(Double.parseDouble(!Lib.isBlank(words.get(199)) ? words.get(199) : "0.001"));
				
				dataModel.setStat_fuse_blow(Double.parseDouble(!Lib.isBlank(words.get(200)) ? words.get(200) : "0.001"));
				dataModel.setStat_hi_current(Double.parseDouble(!Lib.isBlank(words.get(201)) ? words.get(201) : "0.001"));
				dataModel.setStat_hi_dc_volt(Double.parseDouble(!Lib.isBlank(words.get(202)) ? words.get(202) : "0.001"));
				dataModel.setStat_hi_temp(Double.parseDouble(!Lib.isBlank(words.get(203)) ? words.get(203) : "0.001"));
				dataModel.setStat_low_current(Double.parseDouble(!Lib.isBlank(words.get(204)) ? words.get(204) : "0.001"));
				dataModel.setStat_open_circuit(Double.parseDouble(!Lib.isBlank(words.get(205)) ? words.get(205) : "0.001"));
				dataModel.setStat_rev_current(Double.parseDouble(!Lib.isBlank(words.get(206)) ? words.get(206) : "0.001"));
				dataModel.setStat_self_test_trip(Double.parseDouble(!Lib.isBlank(words.get(207)) ? words.get(207) : "0.001"));
				dataModel.setStat_short_circuit(Double.parseDouble(!Lib.isBlank(words.get(208)) ? words.get(208) : "0.001"));
				dataModel.setStat_shunt_trip_command(Double.parseDouble(!Lib.isBlank(words.get(209)) ? words.get(209) : "0.001"));
				
				dataModel.setStat_spd_fault(Double.parseDouble(!Lib.isBlank(words.get(210)) ? words.get(210) : "0.001"));
				dataModel.setStat_switch_trip(Double.parseDouble(!Lib.isBlank(words.get(211)) ? words.get(211) : "0.001"));
				dataModel.setStat_switch_trip_enable(Double.parseDouble(!Lib.isBlank(words.get(212)) ? words.get(212) : "0.001"));
				dataModel.setStat_trip(Double.parseDouble(!Lib.isBlank(words.get(213)) ? words.get(213) : "0.001"));
				dataModel.setTemp(Double.parseDouble(!Lib.isBlank(words.get(214)) ? words.get(214) : "0.001"));
				dataModel.setTotal_yield(energy);
				dataModel.setWork_state(Double.parseDouble(!Lib.isBlank(words.get(216)) ? words.get(216) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelSungrowPv24hScbEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSungrowPv24hScbEntity();
		}
	}


	/**
	 * @description insert data from datalogger to model_meter_ion_8600
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelSungrowPv24hScb(ModelSungrowPv24hScbEntity obj) {
		try {
			Object insertId = insert("ModelSungrowPv24hScb.insertModelSungrowPv24hScb", obj);
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
