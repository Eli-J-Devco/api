/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.ModelSMP4DPEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.utils.Lib;

@Service
public class ModelSMP4DPService extends DB {
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelSMP4DPEntity setModelSMP4DP(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSMP4DPEntity dataModel = new ModelSMP4DPEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setAI_5XF01ARTHi_wind_cos(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_freq(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_ia(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_ib(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_ic(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setAI_5XF01ARTHI_WIND_P(power);
				dataModel.setAI_5XF01ARTHi_wind_p_pos(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setAI_5XF01ARTHI_WIND_Q(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setAI_5XF01ARTHI_WIND_S(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_uab(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_uan(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_ubc(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_ubn(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_uca(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_ucn(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_vah_tot(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_varh_neg(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_varh_net(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_varh_pos(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_varh_tot(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_wh_del(energy);
				dataModel.setAI_5XF01ARTHi_wind_wh_net(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_wh_rec(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setAI_5XF01ARTHi_wind_wh_tot(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_cos(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_cos_calc(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_freq(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_ia(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_ib(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_ic(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setAI_5XF01ART_LOW_WIND_P(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModel.setAI_5XF01ART_LOW_WIND_Q(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModel.setAI_5XF01ART_LOW_WIND_S(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setAI_5XF01ART_LOW_WIND_ULL(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModel.setAI_5XF01ART_LOW_WIND_ULN(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_uab(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_uan(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_ubc(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_ubn(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_uca(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_ucn(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_vah_del(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_vah_net(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_vah_rec(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_vah_tot(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_varh_neg(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_varh_net(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_varh_pos(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_varh_tot(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_wh_del(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_wh_net(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_wh_rec(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModel.setAI_5xf01art_low_wind_wh_tot(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModel.setAI_Feed_1_cos(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModel.setAI_Feed_1_cos_calc(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModel.setAI_Feed_1_freq(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModel.setAI_Feed_1_ia(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				dataModel.setAI_Feed_1_ib(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModel.setAI_Feed_1_ic(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModel.setAI_FEED_1_P(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModel.setAI_FEED_1_Q(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModel.setAI_FEED_1_S(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				dataModel.setAI_FEED_1_ULL(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001"));
				dataModel.setAI_FEED_1_ULN(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001"));
				dataModel.setAI_Feed_1_uab(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001"));
				dataModel.setAI_Feed_1_uan(Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001"));
				dataModel.setAI_Feed_1_ubc(Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001"));
				dataModel.setAI_Feed_1_ubn(Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001"));
				dataModel.setAI_Feed_1_uca(Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001"));
				dataModel.setAI_Feed_1_ucn(Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001"));
				dataModel.setAI_Feed_1_vah_del(Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001"));
				dataModel.setAI_Feed_1_vah_net(Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001"));
				dataModel.setAI_Feed_1_vah_rec(Double.parseDouble(!Lib.isBlank(words.get(76)) ? words.get(76) : "0.001"));
				dataModel.setAI_Feed_1_vah_tot(Double.parseDouble(!Lib.isBlank(words.get(77)) ? words.get(77) : "0.001"));
				dataModel.setAI_Feed_1_varh_neg(Double.parseDouble(!Lib.isBlank(words.get(78)) ? words.get(78) : "0.001"));
				dataModel.setAI_Feed_1_varh_net(Double.parseDouble(!Lib.isBlank(words.get(79)) ? words.get(79) : "0.001"));
				dataModel.setAI_Feed_1_varh_pos(Double.parseDouble(!Lib.isBlank(words.get(80)) ? words.get(80) : "0.001"));
				dataModel.setAI_Feed_1_varh_tot(Double.parseDouble(!Lib.isBlank(words.get(81)) ? words.get(81) : "0.001"));
				dataModel.setAI_Feed_1_wh_del(Double.parseDouble(!Lib.isBlank(words.get(82)) ? words.get(82) : "0.001"));
				dataModel.setAI_Feed_1_wh_net(Double.parseDouble(!Lib.isBlank(words.get(83)) ? words.get(83) : "0.001"));
				dataModel.setAI_Feed_1_wh_rec(Double.parseDouble(!Lib.isBlank(words.get(84)) ? words.get(84) : "0.001"));
				dataModel.setAI_Feed_1_wh_tot(Double.parseDouble(!Lib.isBlank(words.get(85)) ? words.get(85) : "0.001"));
				dataModel.setAI_Feed_2_cos(Double.parseDouble(!Lib.isBlank(words.get(86)) ? words.get(86) : "0.001"));
				dataModel.setAI_Feed_2_cos_calc(Double.parseDouble(!Lib.isBlank(words.get(87)) ? words.get(87) : "0.001"));
				dataModel.setAI_Feed_2_freq(Double.parseDouble(!Lib.isBlank(words.get(88)) ? words.get(88) : "0.001"));
				dataModel.setAI_Feed_2_ia(Double.parseDouble(!Lib.isBlank(words.get(89)) ? words.get(89) : "0.001"));
				dataModel.setAI_Feed_2_ib(Double.parseDouble(!Lib.isBlank(words.get(90)) ? words.get(90) : "0.001"));
				dataModel.setAI_Feed_2_ic(Double.parseDouble(!Lib.isBlank(words.get(91)) ? words.get(91) : "0.001"));
				dataModel.setAI_FEED_2_P(Double.parseDouble(!Lib.isBlank(words.get(92)) ? words.get(92) : "0.001"));
				dataModel.setAI_FEED_2_Q(Double.parseDouble(!Lib.isBlank(words.get(93)) ? words.get(93) : "0.001"));
				dataModel.setAI_FEED_2_S(Double.parseDouble(!Lib.isBlank(words.get(94)) ? words.get(94) : "0.001"));
				dataModel.setAI_FEED_2_ULL(Double.parseDouble(!Lib.isBlank(words.get(95)) ? words.get(95) : "0.001"));
				dataModel.setAI_FEED_2_ULN(Double.parseDouble(!Lib.isBlank(words.get(96)) ? words.get(96) : "0.001"));
				dataModel.setAI_Feed_2_uab(Double.parseDouble(!Lib.isBlank(words.get(97)) ? words.get(97) : "0.001"));
				dataModel.setAI_Feed_2_uan(Double.parseDouble(!Lib.isBlank(words.get(98)) ? words.get(98) : "0.001"));
				dataModel.setAI_Feed_2_ubc(Double.parseDouble(!Lib.isBlank(words.get(99)) ? words.get(99) : "0.001"));
				dataModel.setAI_Feed_2_ubn(Double.parseDouble(!Lib.isBlank(words.get(100)) ? words.get(100) : "0.001"));
				dataModel.setAI_Feed_2_uca(Double.parseDouble(!Lib.isBlank(words.get(101)) ? words.get(101) : "0.001"));
				dataModel.setAI_Feed_2_ucn(Double.parseDouble(!Lib.isBlank(words.get(102)) ? words.get(102) : "0.001"));
				dataModel.setAI_Feed_2_vah_del(Double.parseDouble(!Lib.isBlank(words.get(103)) ? words.get(103) : "0.001"));
				dataModel.setAI_Feed_2_vah_net(Double.parseDouble(!Lib.isBlank(words.get(104)) ? words.get(104) : "0.001"));
				dataModel.setAI_Feed_2_vah_rec(Double.parseDouble(!Lib.isBlank(words.get(105)) ? words.get(105) : "0.001"));
				dataModel.setAI_Feed_2_vah_tot(Double.parseDouble(!Lib.isBlank(words.get(106)) ? words.get(106) : "0.001"));
				dataModel.setAI_Feed_2_varh_neg(Double.parseDouble(!Lib.isBlank(words.get(107)) ? words.get(107) : "0.001"));
				dataModel.setAI_Feed_2_varh_net(Double.parseDouble(!Lib.isBlank(words.get(108)) ? words.get(108) : "0.001"));
				dataModel.setAI_Feed_2_varh_pos(Double.parseDouble(!Lib.isBlank(words.get(109)) ? words.get(109) : "0.001"));
				dataModel.setAI_Feed_2_varh_tot(Double.parseDouble(!Lib.isBlank(words.get(110)) ? words.get(110) : "0.001"));
				dataModel.setAI_Feed_2_wh_del(Double.parseDouble(!Lib.isBlank(words.get(111)) ? words.get(111) : "0.001"));
				dataModel.setAI_Feed_2_wh_net(Double.parseDouble(!Lib.isBlank(words.get(112)) ? words.get(112) : "0.001"));
				dataModel.setAI_Feed_2_wh_rec(Double.parseDouble(!Lib.isBlank(words.get(113)) ? words.get(113) : "0.001"));
				dataModel.setAI_Feed_2_wh_tot(Double.parseDouble(!Lib.isBlank(words.get(114)) ? words.get(114) : "0.001"));
				dataModel.setAI_Feed_3_cos(Double.parseDouble(!Lib.isBlank(words.get(115)) ? words.get(115) : "0.001"));
				dataModel.setAI_Feed_3_cos_calc(Double.parseDouble(!Lib.isBlank(words.get(116)) ? words.get(116) : "0.001"));
				dataModel.setAI_Feed_3_freq(Double.parseDouble(!Lib.isBlank(words.get(117)) ? words.get(117) : "0.001"));
				dataModel.setAI_Feed_3_ia(Double.parseDouble(!Lib.isBlank(words.get(118)) ? words.get(118) : "0.001"));
				dataModel.setAI_Feed_3_ib(Double.parseDouble(!Lib.isBlank(words.get(119)) ? words.get(119) : "0.001"));
				dataModel.setAI_Feed_3_ic(Double.parseDouble(!Lib.isBlank(words.get(120)) ? words.get(120) : "0.001"));
				dataModel.setAI_FEED_3_P(Double.parseDouble(!Lib.isBlank(words.get(121)) ? words.get(121) : "0.001"));
				dataModel.setAI_FEED_3_Q(Double.parseDouble(!Lib.isBlank(words.get(122)) ? words.get(122) : "0.001"));
				dataModel.setAI_FEED_3_S(Double.parseDouble(!Lib.isBlank(words.get(123)) ? words.get(123) : "0.001"));
				dataModel.setAI_FEED_3_ULL(Double.parseDouble(!Lib.isBlank(words.get(124)) ? words.get(124) : "0.001"));
				dataModel.setAI_FEED_3_ULN(Double.parseDouble(!Lib.isBlank(words.get(125)) ? words.get(125) : "0.001"));
				dataModel.setAI_Feed_3_uab(Double.parseDouble(!Lib.isBlank(words.get(126)) ? words.get(126) : "0.001"));
				dataModel.setAI_Feed_3_uan(Double.parseDouble(!Lib.isBlank(words.get(127)) ? words.get(127) : "0.001"));
				dataModel.setAI_Feed_3_ubc(Double.parseDouble(!Lib.isBlank(words.get(128)) ? words.get(128) : "0.001"));
				dataModel.setAI_Feed_3_ubn(Double.parseDouble(!Lib.isBlank(words.get(129)) ? words.get(129) : "0.001"));
				dataModel.setAI_Feed_3_uca(Double.parseDouble(!Lib.isBlank(words.get(130)) ? words.get(130) : "0.001"));
				dataModel.setAI_Feed_3_ucn(Double.parseDouble(!Lib.isBlank(words.get(131)) ? words.get(131) : "0.001"));
				dataModel.setAI_Feed_3_vah_del(Double.parseDouble(!Lib.isBlank(words.get(132)) ? words.get(132) : "0.001"));
				dataModel.setAI_Feed_3_vah_net(Double.parseDouble(!Lib.isBlank(words.get(133)) ? words.get(133) : "0.001"));
				dataModel.setAI_Feed_3_vah_rec(Double.parseDouble(!Lib.isBlank(words.get(134)) ? words.get(134) : "0.001"));
				dataModel.setAI_Feed_3_vah_tot(Double.parseDouble(!Lib.isBlank(words.get(135)) ? words.get(135) : "0.001"));
				dataModel.setAI_Feed_3_varh_neg(Double.parseDouble(!Lib.isBlank(words.get(136)) ? words.get(136) : "0.001"));
				dataModel.setAI_Feed_3_varh_net(Double.parseDouble(!Lib.isBlank(words.get(137)) ? words.get(137) : "0.001"));
				dataModel.setAI_Feed_3_varh_pos(Double.parseDouble(!Lib.isBlank(words.get(138)) ? words.get(138) : "0.001"));
				dataModel.setAI_Feed_3_varh_tot(Double.parseDouble(!Lib.isBlank(words.get(139)) ? words.get(139) : "0.001"));
				dataModel.setAI_Feed_3_wh_del(Double.parseDouble(!Lib.isBlank(words.get(140)) ? words.get(140) : "0.001"));
				dataModel.setAI_Feed_3_wh_net(Double.parseDouble(!Lib.isBlank(words.get(141)) ? words.get(141) : "0.001"));
				dataModel.setAI_Feed_3_wh_rec(Double.parseDouble(!Lib.isBlank(words.get(142)) ? words.get(142) : "0.001"));
				dataModel.setAI_Feed_3_wh_tot(Double.parseDouble(!Lib.isBlank(words.get(143)) ? words.get(143) : "0.001"));
				dataModel.setAI_Feed_4_cos(Double.parseDouble(!Lib.isBlank(words.get(144)) ? words.get(144) : "0.001"));
				dataModel.setAI_Feed_4_cos_calc(Double.parseDouble(!Lib.isBlank(words.get(145)) ? words.get(145) : "0.001"));
				dataModel.setAI_Feed_4_freq(Double.parseDouble(!Lib.isBlank(words.get(146)) ? words.get(146) : "0.001"));
				dataModel.setAI_Feed_4_ia(Double.parseDouble(!Lib.isBlank(words.get(147)) ? words.get(147) : "0.001"));
				dataModel.setAI_Feed_4_ib(Double.parseDouble(!Lib.isBlank(words.get(148)) ? words.get(148) : "0.001"));
				dataModel.setAI_Feed_4_ic(Double.parseDouble(!Lib.isBlank(words.get(149)) ? words.get(149) : "0.001"));
				dataModel.setAI_FEED_4_P(Double.parseDouble(!Lib.isBlank(words.get(150)) ? words.get(150) : "0.001"));
				dataModel.setAI_FEED_4_Q(Double.parseDouble(!Lib.isBlank(words.get(151)) ? words.get(151) : "0.001"));
				dataModel.setAI_FEED_4_S(Double.parseDouble(!Lib.isBlank(words.get(152)) ? words.get(152) : "0.001"));
				dataModel.setAI_FEED_4_ULL(Double.parseDouble(!Lib.isBlank(words.get(153)) ? words.get(153) : "0.001"));
				dataModel.setAI_FEED_4_ULN(Double.parseDouble(!Lib.isBlank(words.get(154)) ? words.get(154) : "0.001"));
				dataModel.setAI_Feed_4_uab(Double.parseDouble(!Lib.isBlank(words.get(155)) ? words.get(155) : "0.001"));
				dataModel.setAI_Feed_4_uan(Double.parseDouble(!Lib.isBlank(words.get(156)) ? words.get(156) : "0.001"));
				dataModel.setAI_Feed_4_ubc(Double.parseDouble(!Lib.isBlank(words.get(157)) ? words.get(157) : "0.001"));
				dataModel.setAI_Feed_4_ubn(Double.parseDouble(!Lib.isBlank(words.get(158)) ? words.get(158) : "0.001"));
				dataModel.setAI_Feed_4_uca(Double.parseDouble(!Lib.isBlank(words.get(159)) ? words.get(159) : "0.001"));
				dataModel.setAI_Feed_4_ucn(Double.parseDouble(!Lib.isBlank(words.get(160)) ? words.get(160) : "0.001"));
				dataModel.setAI_Feed_4_vah_del(Double.parseDouble(!Lib.isBlank(words.get(161)) ? words.get(161) : "0.001"));
				dataModel.setAI_Feed_4_vah_net(Double.parseDouble(!Lib.isBlank(words.get(162)) ? words.get(162) : "0.001"));
				dataModel.setAI_Feed_4_vah_rec(Double.parseDouble(!Lib.isBlank(words.get(163)) ? words.get(163) : "0.001"));
				dataModel.setAI_Feed_4_vah_tot(Double.parseDouble(!Lib.isBlank(words.get(164)) ? words.get(164) : "0.001"));
				dataModel.setAI_Feed_4_varh_neg(Double.parseDouble(!Lib.isBlank(words.get(165)) ? words.get(165) : "0.001"));
				dataModel.setAI_Feed_4_varh_net(Double.parseDouble(!Lib.isBlank(words.get(166)) ? words.get(166) : "0.001"));
				dataModel.setAI_Feed_4_varh_pos(Double.parseDouble(!Lib.isBlank(words.get(167)) ? words.get(167) : "0.001"));
				dataModel.setAI_Feed_4_varh_tot(Double.parseDouble(!Lib.isBlank(words.get(168)) ? words.get(168) : "0.001"));
				dataModel.setAI_Feed_4_wh_del(Double.parseDouble(!Lib.isBlank(words.get(169)) ? words.get(169) : "0.001"));
				dataModel.setAI_Feed_4_wh_net(Double.parseDouble(!Lib.isBlank(words.get(170)) ? words.get(170) : "0.001"));
				dataModel.setAI_Feed_4_wh_rec(Double.parseDouble(!Lib.isBlank(words.get(171)) ? words.get(171) : "0.001"));
				dataModel.setAI_Feed_4_wh_tot(Double.parseDouble(!Lib.isBlank(words.get(172)) ? words.get(172) : "0.001"));
				dataModel.setAI_Feed_ss_cos(Double.parseDouble(!Lib.isBlank(words.get(173)) ? words.get(173) : "0.001"));
				dataModel.setAI_Feed_ss_cos_calc(Double.parseDouble(!Lib.isBlank(words.get(174)) ? words.get(174) : "0.001"));
				dataModel.setAI_Feed_ss_freq(Double.parseDouble(!Lib.isBlank(words.get(175)) ? words.get(175) : "0.001"));
				dataModel.setAI_Feed_ss_ia(Double.parseDouble(!Lib.isBlank(words.get(176)) ? words.get(176) : "0.001"));
				dataModel.setAI_Feed_ss_ib(Double.parseDouble(!Lib.isBlank(words.get(177)) ? words.get(177) : "0.001"));
				dataModel.setAI_Feed_ss_ic(Double.parseDouble(!Lib.isBlank(words.get(178)) ? words.get(178) : "0.001"));
				dataModel.setAI_FEED_SS_P(Double.parseDouble(!Lib.isBlank(words.get(179)) ? words.get(179) : "0.001"));
				dataModel.setAI_FEED_SS_Q(Double.parseDouble(!Lib.isBlank(words.get(180)) ? words.get(180) : "0.001"));
				dataModel.setAI_FEED_SS_S(Double.parseDouble(!Lib.isBlank(words.get(181)) ? words.get(181) : "0.001"));
				dataModel.setAI_FEED_SS_ULL(Double.parseDouble(!Lib.isBlank(words.get(182)) ? words.get(182) : "0.001"));
				dataModel.setAI_FEED_SS_ULN(Double.parseDouble(!Lib.isBlank(words.get(183)) ? words.get(183) : "0.001"));
				dataModel.setAI_Feed_ss_uab(Double.parseDouble(!Lib.isBlank(words.get(184)) ? words.get(184) : "0.001"));
				dataModel.setAI_Feed_ss_uan(Double.parseDouble(!Lib.isBlank(words.get(185)) ? words.get(185) : "0.001"));
				dataModel.setAI_Feed_ss_ubc(Double.parseDouble(!Lib.isBlank(words.get(186)) ? words.get(186) : "0.001"));
				dataModel.setAI_Feed_ss_ubn(Double.parseDouble(!Lib.isBlank(words.get(187)) ? words.get(187) : "0.001"));
				dataModel.setAI_Feed_ss_uca(Double.parseDouble(!Lib.isBlank(words.get(188)) ? words.get(188) : "0.001"));
				dataModel.setAI_Feed_ss_ucn(Double.parseDouble(!Lib.isBlank(words.get(189)) ? words.get(189) : "0.001"));
				dataModel.setAI_Feed_ss_vah_del(Double.parseDouble(!Lib.isBlank(words.get(190)) ? words.get(190) : "0.001"));
				dataModel.setAI_Feed_ss_vah_net(Double.parseDouble(!Lib.isBlank(words.get(191)) ? words.get(191) : "0.001"));
				dataModel.setAI_Feed_ss_vah_rec(Double.parseDouble(!Lib.isBlank(words.get(192)) ? words.get(192) : "0.001"));
				dataModel.setAI_Feed_ss_vah_tot(Double.parseDouble(!Lib.isBlank(words.get(193)) ? words.get(193) : "0.001"));
				dataModel.setAI_Feed_ss_varh_neg(Double.parseDouble(!Lib.isBlank(words.get(194)) ? words.get(194) : "0.001"));
				dataModel.setAI_Feed_ss_varh_net(Double.parseDouble(!Lib.isBlank(words.get(195)) ? words.get(195) : "0.001"));
				dataModel.setAI_Feed_ss_varh_pos(Double.parseDouble(!Lib.isBlank(words.get(196)) ? words.get(196) : "0.001"));
				dataModel.setAI_Feed_ss_varh_tot(Double.parseDouble(!Lib.isBlank(words.get(197)) ? words.get(197) : "0.001"));
				dataModel.setAI_Feed_ss_wh_del(Double.parseDouble(!Lib.isBlank(words.get(198)) ? words.get(198) : "0.001"));
				dataModel.setAI_Feed_ss_wh_net(Double.parseDouble(!Lib.isBlank(words.get(199)) ? words.get(199) : "0.001"));
				dataModel.setAI_Feed_ss_wh_rec(Double.parseDouble(!Lib.isBlank(words.get(200)) ? words.get(200) : "0.001"));
				dataModel.setAI_Feed_ss_wh_tot(Double.parseDouble(!Lib.isBlank(words.get(201)) ? words.get(201) : "0.001"));
				dataModel.setAI_TAPPOS(Double.parseDouble(!Lib.isBlank(words.get(202)) ? words.get(202) : "0.001"));
				dataModel.setCOMM_FAIL_34kv_feed_1_meter_comm_fail(Double.parseDouble(!Lib.isBlank(words.get(203)) ? words.get(203) : "0.001"));
				dataModel.setCOMM_FAIL_34kv_feed_2_meter_comm_fail(Double.parseDouble(!Lib.isBlank(words.get(204)) ? words.get(204) : "0.001"));
				dataModel.setCOMM_FAIL_34kv_feed_3_meter_comm_fail(Double.parseDouble(!Lib.isBlank(words.get(205)) ? words.get(205) : "0.001"));
				dataModel.setCOMM_FAIL_34kv_feed_4_meter_comm_fail(Double.parseDouble(!Lib.isBlank(words.get(206)) ? words.get(206) : "0.001"));
				dataModel.setCOMM_FAIL_34kv_outgoing_meter_comm_fail(Double.parseDouble(!Lib.isBlank(words.get(207)) ? words.get(207) : "0.001"));
				dataModel.setCOMM_FAIL_34kv_sst_meter_comm_fail(Double.parseDouble(!Lib.isBlank(words.get(208)) ? words.get(208) : "0.001"));
				dataModel.setCOMM_FAIL_69kv_meter_comm_fail(Double.parseDouble(!Lib.isBlank(words.get(209)) ? words.get(209) : "0.001"));
				dataModel.setCOMM_FAIL_Ge500_rtu_comm_fail(Double.parseDouble(!Lib.isBlank(words.get(210)) ? words.get(210) : "0.001"));
				dataModel.setCOMM_FAIL_Idec_plc_comm_fail(Double.parseDouble(!Lib.isBlank(words.get(211)) ? words.get(211) : "0.001"));
				dataModel.setCOMM_FAIL_Line_prot_m1_comm_fail(Double.parseDouble(!Lib.isBlank(words.get(212)) ? words.get(212) : "0.001"));
				dataModel.setCOMM_FAIL_Ws_comm_fail(Double.parseDouble(!Lib.isBlank(words.get(213)) ? words.get(213) : "0.001"));
				dataModel.setCOMM_FAIL_Xf_prot_m1_comm_fail(Double.parseDouble(!Lib.isBlank(words.get(214)) ? words.get(214) : "0.001"));
				dataModel.setCOMM_FAIL_Xf_prot_m2_comm_fail(Double.parseDouble(!Lib.isBlank(words.get(215)) ? words.get(215) : "0.001"));
				dataModel.setDI_4CB044SS01ARTLocalMode(Double.parseDouble(!Lib.isBlank(words.get(216)) ? words.get(216) : "0.001"));
				dataModel.setDI_4CB044SS01ARTRemoteMode(Double.parseDouble(!Lib.isBlank(words.get(217)) ? words.get(217) : "0.001"));
				dataModel.setDI_4CB045XF01ARTClose(Double.parseDouble(!Lib.isBlank(words.get(218)) ? words.get(218) : "0.001"));
				dataModel.setDI_4CB045XF01ARTLocalMode(Double.parseDouble(!Lib.isBlank(words.get(219)) ? words.get(219) : "0.001"));
				dataModel.setDI_4CB045XF01ARTOpen(Double.parseDouble(!Lib.isBlank(words.get(220)) ? words.get(220) : "0.001"));
				dataModel.setDI_4CB045XF01ARTRemoteMode(Double.parseDouble(!Lib.isBlank(words.get(221)) ? words.get(221) : "0.001"));
				dataModel.setDI_4ES055XF01ARTClose(Double.parseDouble(!Lib.isBlank(words.get(222)) ? words.get(222) : "0.001"));
				dataModel.setDI_4ES055XF01ARTOpen(Double.parseDouble(!Lib.isBlank(words.get(223)) ? words.get(223) : "0.001"));
				dataModel.setDI_5CB045XF01ARTClose(Double.parseDouble(!Lib.isBlank(words.get(224)) ? words.get(224) : "0.001"));
				dataModel.setDI_5CB045XF01ARTLocalMode(Double.parseDouble(!Lib.isBlank(words.get(225)) ? words.get(225) : "0.001"));
				dataModel.setDI_5CB045XF01ARTOpen(Double.parseDouble(!Lib.isBlank(words.get(226)) ? words.get(226) : "0.001"));
				dataModel.setDI_5CB045XF01ARTRemoteMode(Double.parseDouble(!Lib.isBlank(words.get(227)) ? words.get(227) : "0.001"));
				dataModel.setDI_5DS055XF01ARTClose(Double.parseDouble(!Lib.isBlank(words.get(228)) ? words.get(228) : "0.001"));
				dataModel.setDI_5DS055XF01ARTOpen(Double.parseDouble(!Lib.isBlank(words.get(229)) ? words.get(229) : "0.001"));
				dataModel.setDI_5ES055XF01ARTClose(Double.parseDouble(!Lib.isBlank(words.get(230)) ? words.get(230) : "0.001"));
				dataModel.setDI_5ES055XF01ARTOpen(Double.parseDouble(!Lib.isBlank(words.get(231)) ? words.get(231) : "0.001"));
				dataModel.setDI_5XF01ARTBuccholzAlarm(Double.parseDouble(!Lib.isBlank(words.get(232)) ? words.get(232) : "0.001"));
				dataModel.setDI_5XF01ARTBuccholzTrip(Double.parseDouble(!Lib.isBlank(words.get(233)) ? words.get(233) : "0.001"));
				dataModel.setDI_5XF01ARTCoolingFanFailure(Double.parseDouble(!Lib.isBlank(words.get(234)) ? words.get(234) : "0.001"));
				dataModel.setDI_5XF01ARTOilLevelAlarm(Double.parseDouble(!Lib.isBlank(words.get(235)) ? words.get(235) : "0.001"));
				dataModel.setDI_5XF01ARTOilLevelTrip(Double.parseDouble(!Lib.isBlank(words.get(236)) ? words.get(236) : "0.001"));
				dataModel.setDI_5XF01ARTOilTempAlarm(Double.parseDouble(!Lib.isBlank(words.get(237)) ? words.get(237) : "0.001"));
				dataModel.setDI_5XF01ARTOilTempTrip(Double.parseDouble(!Lib.isBlank(words.get(238)) ? words.get(238) : "0.001"));
				dataModel.setDI_5XF01ARTPressureReliefTrip(Double.parseDouble(!Lib.isBlank(words.get(239)) ? words.get(239) : "0.001"));
				dataModel.setDI_5XF01ARTWindingTemp1Alarm(Double.parseDouble(!Lib.isBlank(words.get(240)) ? words.get(240) : "0.001"));
				dataModel.setDI_5XF01ARTWindingTemp1Trip(Double.parseDouble(!Lib.isBlank(words.get(241)) ? words.get(241) : "0.001"));
				dataModel.setDI_5XF01ARTWindingTemp2Alarm(Double.parseDouble(!Lib.isBlank(words.get(242)) ? words.get(242) : "0.001"));
				dataModel.setDI_5XF01ARTWindingTemp2Trip(Double.parseDouble(!Lib.isBlank(words.get(243)) ? words.get(243) : "0.001"));
				dataModel.setDI_DS01LocalMode(Double.parseDouble(!Lib.isBlank(words.get(244)) ? words.get(244) : "0.001"));
				dataModel.setDI_DS01RemoteMode(Double.parseDouble(!Lib.isBlank(words.get(245)) ? words.get(245) : "0.001"));
				dataModel.setDI_DS02LocalMode(Double.parseDouble(!Lib.isBlank(words.get(246)) ? words.get(246) : "0.001"));
				dataModel.setDI_DS02RemoteMode(Double.parseDouble(!Lib.isBlank(words.get(247)) ? words.get(247) : "0.001"));
				dataModel.setDI_DS03LocalMode(Double.parseDouble(!Lib.isBlank(words.get(248)) ? words.get(248) : "0.001"));
				dataModel.setDI_DS03RemoteMode(Double.parseDouble(!Lib.isBlank(words.get(249)) ? words.get(249) : "0.001"));
				dataModel.setDI_DS035LI1MEXARTART1Close(Double.parseDouble(!Lib.isBlank(words.get(250)) ? words.get(250) : "0.001"));
				dataModel.setDI_DS035LI1MEXARTART1Open(Double.parseDouble(!Lib.isBlank(words.get(251)) ? words.get(251) : "0.001"));
				dataModel.setDI_DS035LI1MEXARTART2Close(Double.parseDouble(!Lib.isBlank(words.get(252)) ? words.get(252) : "0.001"));
				dataModel.setDI_DS035LI1MEXARTART2Open(Double.parseDouble(!Lib.isBlank(words.get(253)) ? words.get(253) : "0.001"));
				dataModel.setDI_DS035LI1MEXARTART3Close(Double.parseDouble(!Lib.isBlank(words.get(254)) ? words.get(254) : "0.001"));
				dataModel.setDI_DS035LI1MEXARTART3Open(Double.parseDouble(!Lib.isBlank(words.get(255)) ? words.get(255) : "0.001"));
				dataModel.setDI_ES035LI1MEXARTART1Close(Double.parseDouble(!Lib.isBlank(words.get(256)) ? words.get(256) : "0.001"));
				dataModel.setDI_ES035LI1MEXARTART1Open(Double.parseDouble(!Lib.isBlank(words.get(257)) ? words.get(257) : "0.001"));
				dataModel.setDI_ES035LI1MEXARTART2Close(Double.parseDouble(!Lib.isBlank(words.get(258)) ? words.get(258) : "0.001"));
				dataModel.setDI_ES035LI1MEXARTART2Open(Double.parseDouble(!Lib.isBlank(words.get(259)) ? words.get(259) : "0.001"));
				dataModel.setDI_Feed_1_drawIn(Double.parseDouble(!Lib.isBlank(words.get(260)) ? words.get(260) : "0.001"));
				dataModel.setDI_Feed_1_drawOut(Double.parseDouble(!Lib.isBlank(words.get(261)) ? words.get(261) : "0.001"));
				dataModel.setDI_FEED_1_ESClose(Double.parseDouble(!Lib.isBlank(words.get(262)) ? words.get(262) : "0.001"));
				dataModel.setDI_FEED_1_ESOpen(Double.parseDouble(!Lib.isBlank(words.get(263)) ? words.get(263) : "0.001"));
				dataModel.setDI_Feed_1_generalFault(Double.parseDouble(!Lib.isBlank(words.get(264)) ? words.get(264) : "0.001"));
				dataModel.setDI_Feed_1_localMode(Double.parseDouble(!Lib.isBlank(words.get(265)) ? words.get(265) : "0.001"));
				dataModel.setDI_FEED_1_PCBClose(Double.parseDouble(!Lib.isBlank(words.get(266)) ? words.get(266) : "0.001"));
				dataModel.setDI_FEED_1_PCBOpen(Double.parseDouble(!Lib.isBlank(words.get(267)) ? words.get(267) : "0.001"));
				dataModel.setDI_Feed_1_remoteMode(Double.parseDouble(!Lib.isBlank(words.get(268)) ? words.get(268) : "0.001"));
				dataModel.setDI_Feed_2_drawIn(Double.parseDouble(!Lib.isBlank(words.get(269)) ? words.get(269) : "0.001"));
				dataModel.setDI_Feed_2_drawOut(Double.parseDouble(!Lib.isBlank(words.get(270)) ? words.get(270) : "0.001"));
				dataModel.setDI_FEED_2_ESClose(Double.parseDouble(!Lib.isBlank(words.get(271)) ? words.get(271) : "0.001"));
				dataModel.setDI_FEED_2_ESOpen(Double.parseDouble(!Lib.isBlank(words.get(272)) ? words.get(272) : "0.001"));
				dataModel.setDI_Feed_2_generalFault(Double.parseDouble(!Lib.isBlank(words.get(273)) ? words.get(273) : "0.001"));
				dataModel.setDI_Feed_2_localMode(Double.parseDouble(!Lib.isBlank(words.get(274)) ? words.get(274) : "0.001"));
				dataModel.setDI_FEED_2_PCBClose(Double.parseDouble(!Lib.isBlank(words.get(275)) ? words.get(275) : "0.001"));
				dataModel.setDI_FEED_2_PCBOpen(Double.parseDouble(!Lib.isBlank(words.get(276)) ? words.get(276) : "0.001"));
				dataModel.setDI_Feed_2_remoteMode(Double.parseDouble(!Lib.isBlank(words.get(277)) ? words.get(277) : "0.001"));
				dataModel.setDI_Feed_3_drawIn(Double.parseDouble(!Lib.isBlank(words.get(278)) ? words.get(278) : "0.001"));
				dataModel.setDI_Feed_3_drawOut(Double.parseDouble(!Lib.isBlank(words.get(279)) ? words.get(279) : "0.001"));
				dataModel.setDI_FEED_3_ESClose(Double.parseDouble(!Lib.isBlank(words.get(280)) ? words.get(280) : "0.001"));
				dataModel.setDI_FEED_3_ESOpen(Double.parseDouble(!Lib.isBlank(words.get(281)) ? words.get(281) : "0.001"));
				dataModel.setDI_Feed_3_generalFault(Double.parseDouble(!Lib.isBlank(words.get(282)) ? words.get(282) : "0.001"));
				dataModel.setDI_Feed_3_localMode(Double.parseDouble(!Lib.isBlank(words.get(283)) ? words.get(283) : "0.001"));
				dataModel.setDI_FEED_3_PCBClose(Double.parseDouble(!Lib.isBlank(words.get(284)) ? words.get(284) : "0.001"));
				dataModel.setDI_FEED_3_PCBOpen(Double.parseDouble(!Lib.isBlank(words.get(285)) ? words.get(285) : "0.001"));
				dataModel.setDI_Feed_3_remoteMode(Double.parseDouble(!Lib.isBlank(words.get(286)) ? words.get(286) : "0.001"));
				dataModel.setDI_Feed_4_drawIn(Double.parseDouble(!Lib.isBlank(words.get(287)) ? words.get(287) : "0.001"));
				dataModel.setDI_Feed_4_drawOut(Double.parseDouble(!Lib.isBlank(words.get(288)) ? words.get(288) : "0.001"));
				dataModel.setDI_FEED_4_ESClose(Double.parseDouble(!Lib.isBlank(words.get(289)) ? words.get(289) : "0.001"));
				dataModel.setDI_FEED_4_ESOpen(Double.parseDouble(!Lib.isBlank(words.get(290)) ? words.get(290) : "0.001"));
				dataModel.setDI_Feed_4_generalFault(Double.parseDouble(!Lib.isBlank(words.get(291)) ? words.get(291) : "0.001"));
				dataModel.setDI_Feed_4_localMode(Double.parseDouble(!Lib.isBlank(words.get(292)) ? words.get(292) : "0.001"));
				dataModel.setDI_FEED_4_PCBClose(Double.parseDouble(!Lib.isBlank(words.get(293)) ? words.get(293) : "0.001"));
				dataModel.setDI_FEED_4_PCBOpen(Double.parseDouble(!Lib.isBlank(words.get(294)) ? words.get(294) : "0.001"));
				dataModel.setDI_Feed_4_remoteMode(Double.parseDouble(!Lib.isBlank(words.get(295)) ? words.get(295) : "0.001"));
				dataModel.setDI_FEED_SS_ESClose(Double.parseDouble(!Lib.isBlank(words.get(296)) ? words.get(296) : "0.001"));
				dataModel.setDI_FEED_SS_ESOpen(Double.parseDouble(!Lib.isBlank(words.get(297)) ? words.get(297) : "0.001"));
				dataModel.setDI_FEED_SS_PCBClose(Double.parseDouble(!Lib.isBlank(words.get(298)) ? words.get(298) : "0.001"));
				dataModel.setDI_FEED_SS_PCBOpen(Double.parseDouble(!Lib.isBlank(words.get(299)) ? words.get(299) : "0.001"));
				dataModel.setDI_MVCBDrawIn(Double.parseDouble(!Lib.isBlank(words.get(300)) ? words.get(300) : "0.001"));
				dataModel.setDI_MVCBDrawOut(Double.parseDouble(!Lib.isBlank(words.get(301)) ? words.get(301) : "0.001"));
				dataModel.setDI_MVCBGeneralFault(Double.parseDouble(!Lib.isBlank(words.get(302)) ? words.get(302) : "0.001"));
				dataModel.setDI_Mvps_01_es_01_close(Double.parseDouble(!Lib.isBlank(words.get(303)) ? words.get(303) : "0.001"));
				dataModel.setDI_Mvps_01_es_01_open(Double.parseDouble(!Lib.isBlank(words.get(304)) ? words.get(304) : "0.001"));
				dataModel.setDI_Mvps_01_es_02_close(Double.parseDouble(!Lib.isBlank(words.get(305)) ? words.get(305) : "0.001"));
				dataModel.setDI_Mvps_01_es_02_open(Double.parseDouble(!Lib.isBlank(words.get(306)) ? words.get(306) : "0.001"));
				dataModel.setDI_Mvps_01_es_03_close(Double.parseDouble(!Lib.isBlank(words.get(307)) ? words.get(307) : "0.001"));
				dataModel.setDI_Mvps_01_es_03_open(Double.parseDouble(!Lib.isBlank(words.get(308)) ? words.get(308) : "0.001"));
				dataModel.setDI_Mvps_01_lbs_01_close(Double.parseDouble(!Lib.isBlank(words.get(309)) ? words.get(309) : "0.001"));
				dataModel.setDI_Mvps_01_lbs_01_open(Double.parseDouble(!Lib.isBlank(words.get(310)) ? words.get(310) : "0.001"));
				dataModel.setDI_Mvps_01_lbs_02_close(Double.parseDouble(!Lib.isBlank(words.get(311)) ? words.get(311) : "0.001"));
				dataModel.setDI_Mvps_01_lbs_02_open(Double.parseDouble(!Lib.isBlank(words.get(312)) ? words.get(312) : "0.001"));
				dataModel.setDI_Mvps_01_vcb_close(Double.parseDouble(!Lib.isBlank(words.get(313)) ? words.get(313) : "0.001"));
				dataModel.setDI_Mvps_01_vcb_open(Double.parseDouble(!Lib.isBlank(words.get(314)) ? words.get(314) : "0.001"));
				dataModel.setDI_Mvps_02_es_01_close(Double.parseDouble(!Lib.isBlank(words.get(315)) ? words.get(315) : "0.001"));
				dataModel.setDI_Mvps_02_es_01_open(Double.parseDouble(!Lib.isBlank(words.get(316)) ? words.get(316) : "0.001"));
				dataModel.setDI_Mvps_02_es_02_close(Double.parseDouble(!Lib.isBlank(words.get(317)) ? words.get(317) : "0.001"));
				dataModel.setDI_Mvps_02_es_02_open(Double.parseDouble(!Lib.isBlank(words.get(318)) ? words.get(318) : "0.001"));
				dataModel.setDI_Mvps_02_es_03_close(Double.parseDouble(!Lib.isBlank(words.get(319)) ? words.get(319) : "0.001"));
				dataModel.setDI_Mvps_02_es_03_open(Double.parseDouble(!Lib.isBlank(words.get(320)) ? words.get(320) : "0.001"));
				dataModel.setDI_Mvps_02_lbs_01_close(Double.parseDouble(!Lib.isBlank(words.get(321)) ? words.get(321) : "0.001"));
				dataModel.setDI_Mvps_02_lbs_01_open(Double.parseDouble(!Lib.isBlank(words.get(322)) ? words.get(322) : "0.001"));
				dataModel.setDI_Mvps_02_lbs_02_close(Double.parseDouble(!Lib.isBlank(words.get(323)) ? words.get(323) : "0.001"));
				dataModel.setDI_Mvps_02_lbs_02_open(Double.parseDouble(!Lib.isBlank(words.get(324)) ? words.get(324) : "0.001"));
				dataModel.setDI_Mvps_02_vcb_close(Double.parseDouble(!Lib.isBlank(words.get(325)) ? words.get(325) : "0.001"));
				dataModel.setDI_Mvps_02_vcb_open(Double.parseDouble(!Lib.isBlank(words.get(326)) ? words.get(326) : "0.001"));
				dataModel.setDI_Mvps_03_es_01_close(Double.parseDouble(!Lib.isBlank(words.get(327)) ? words.get(327) : "0.001"));
				dataModel.setDI_Mvps_03_es_01_open(Double.parseDouble(!Lib.isBlank(words.get(328)) ? words.get(328) : "0.001"));
				dataModel.setDI_Mvps_03_es_02_close(Double.parseDouble(!Lib.isBlank(words.get(329)) ? words.get(329) : "0.001"));
				dataModel.setDI_Mvps_03_es_02_open(Double.parseDouble(!Lib.isBlank(words.get(330)) ? words.get(330) : "0.001"));
				dataModel.setDI_Mvps_03_es_03_close(Double.parseDouble(!Lib.isBlank(words.get(331)) ? words.get(331) : "0.001"));
				dataModel.setDI_Mvps_03_es_03_open(Double.parseDouble(!Lib.isBlank(words.get(332)) ? words.get(332) : "0.001"));
				dataModel.setDI_Mvps_03_lbs_01_close(Double.parseDouble(!Lib.isBlank(words.get(333)) ? words.get(333) : "0.001"));
				dataModel.setDI_Mvps_03_lbs_01_open(Double.parseDouble(!Lib.isBlank(words.get(334)) ? words.get(334) : "0.001"));
				dataModel.setDI_Mvps_03_lbs_02_close(Double.parseDouble(!Lib.isBlank(words.get(335)) ? words.get(335) : "0.001"));
				dataModel.setDI_Mvps_03_lbs_02_open(Double.parseDouble(!Lib.isBlank(words.get(336)) ? words.get(336) : "0.001"));
				dataModel.setDI_Mvps_03_vcb_close(Double.parseDouble(!Lib.isBlank(words.get(337)) ? words.get(337) : "0.001"));
				dataModel.setDI_Mvps_03_vcb_open(Double.parseDouble(!Lib.isBlank(words.get(338)) ? words.get(338) : "0.001"));
				dataModel.setDI_Mvps_04_es_01_close(Double.parseDouble(!Lib.isBlank(words.get(339)) ? words.get(339) : "0.001"));
				dataModel.setDI_Mvps_04_es_01_open(Double.parseDouble(!Lib.isBlank(words.get(340)) ? words.get(340) : "0.001"));
				dataModel.setDI_Mvps_04_es_02_close(Double.parseDouble(!Lib.isBlank(words.get(341)) ? words.get(341) : "0.001"));
				dataModel.setDI_Mvps_04_es_02_open(Double.parseDouble(!Lib.isBlank(words.get(342)) ? words.get(342) : "0.001"));
				dataModel.setDI_Mvps_04_es_03_close(Double.parseDouble(!Lib.isBlank(words.get(343)) ? words.get(343) : "0.001"));
				dataModel.setDI_Mvps_04_es_03_open(Double.parseDouble(!Lib.isBlank(words.get(344)) ? words.get(344) : "0.001"));
				dataModel.setDI_Mvps_04_lbs_01_close(Double.parseDouble(!Lib.isBlank(words.get(345)) ? words.get(345) : "0.001"));
				dataModel.setDI_Mvps_04_lbs_01_open(Double.parseDouble(!Lib.isBlank(words.get(346)) ? words.get(346) : "0.001"));
				dataModel.setDI_Mvps_04_lbs_02_close(Double.parseDouble(!Lib.isBlank(words.get(347)) ? words.get(347) : "0.001"));
				dataModel.setDI_Mvps_04_lbs_02_open(Double.parseDouble(!Lib.isBlank(words.get(348)) ? words.get(348) : "0.001"));
				dataModel.setDI_Mvps_04_vcb_close(Double.parseDouble(!Lib.isBlank(words.get(349)) ? words.get(349) : "0.001"));
				dataModel.setDI_Mvps_04_vcb_open(Double.parseDouble(!Lib.isBlank(words.get(350)) ? words.get(350) : "0.001"));
				dataModel.setDI_Mvps_05_es_01_close(Double.parseDouble(!Lib.isBlank(words.get(351)) ? words.get(351) : "0.001"));
				dataModel.setDI_Mvps_05_es_01_open(Double.parseDouble(!Lib.isBlank(words.get(352)) ? words.get(352) : "0.001"));
				dataModel.setDI_Mvps_05_es_02_close(Double.parseDouble(!Lib.isBlank(words.get(353)) ? words.get(353) : "0.001"));
				dataModel.setDI_Mvps_05_es_02_open(Double.parseDouble(!Lib.isBlank(words.get(354)) ? words.get(354) : "0.001"));
				dataModel.setDI_Mvps_05_es_03_close(Double.parseDouble(!Lib.isBlank(words.get(355)) ? words.get(355) : "0.001"));
				dataModel.setDI_Mvps_05_es_03_open(Double.parseDouble(!Lib.isBlank(words.get(356)) ? words.get(356) : "0.001"));
				dataModel.setDI_Mvps_05_lbs_01_close(Double.parseDouble(!Lib.isBlank(words.get(357)) ? words.get(357) : "0.001"));
				dataModel.setDI_Mvps_05_lbs_01_open(Double.parseDouble(!Lib.isBlank(words.get(358)) ? words.get(358) : "0.001"));
				dataModel.setDI_Mvps_05_lbs_02_close(Double.parseDouble(!Lib.isBlank(words.get(359)) ? words.get(359) : "0.001"));
				dataModel.setDI_Mvps_05_lbs_02_open(Double.parseDouble(!Lib.isBlank(words.get(360)) ? words.get(360) : "0.001"));
				dataModel.setDI_Mvps_05_vcb_close(Double.parseDouble(!Lib.isBlank(words.get(361)) ? words.get(361) : "0.001"));
				dataModel.setDI_Mvps_05_vcb_open(Double.parseDouble(!Lib.isBlank(words.get(362)) ? words.get(362) : "0.001"));
				dataModel.setDI_Mvps_06_es_01_close(Double.parseDouble(!Lib.isBlank(words.get(363)) ? words.get(363) : "0.001"));
				dataModel.setDI_Mvps_06_es_01_open(Double.parseDouble(!Lib.isBlank(words.get(364)) ? words.get(364) : "0.001"));
				dataModel.setDI_Mvps_06_es_02_close(Double.parseDouble(!Lib.isBlank(words.get(365)) ? words.get(365) : "0.001"));
				dataModel.setDI_Mvps_06_es_02_open(Double.parseDouble(!Lib.isBlank(words.get(366)) ? words.get(366) : "0.001"));
				dataModel.setDI_Mvps_06_es_03_close(Double.parseDouble(!Lib.isBlank(words.get(367)) ? words.get(367) : "0.001"));
				dataModel.setDI_Mvps_06_es_03_open(Double.parseDouble(!Lib.isBlank(words.get(368)) ? words.get(368) : "0.001"));
				dataModel.setDI_Mvps_06_lbs_01_close(Double.parseDouble(!Lib.isBlank(words.get(369)) ? words.get(369) : "0.001"));
				dataModel.setDI_Mvps_06_lbs_01_open(Double.parseDouble(!Lib.isBlank(words.get(370)) ? words.get(370) : "0.001"));
				dataModel.setDI_Mvps_06_lbs_02_close(Double.parseDouble(!Lib.isBlank(words.get(371)) ? words.get(371) : "0.001"));
				dataModel.setDI_Mvps_06_lbs_02_open(Double.parseDouble(!Lib.isBlank(words.get(372)) ? words.get(372) : "0.001"));
				dataModel.setDI_Mvps_06_vcb_close(Double.parseDouble(!Lib.isBlank(words.get(373)) ? words.get(373) : "0.001"));
				dataModel.setDI_Mvps_06_vcb_open(Double.parseDouble(!Lib.isBlank(words.get(374)) ? words.get(374) : "0.001"));
				dataModel.setDI_Mvps_07_es_01_close(Double.parseDouble(!Lib.isBlank(words.get(375)) ? words.get(375) : "0.001"));
				dataModel.setDI_Mvps_07_es_01_open(Double.parseDouble(!Lib.isBlank(words.get(376)) ? words.get(376) : "0.001"));
				dataModel.setDI_Mvps_07_es_02_close(Double.parseDouble(!Lib.isBlank(words.get(377)) ? words.get(377) : "0.001"));
				dataModel.setDI_Mvps_07_es_02_open(Double.parseDouble(!Lib.isBlank(words.get(378)) ? words.get(378) : "0.001"));
				dataModel.setDI_Mvps_07_es_03_close(Double.parseDouble(!Lib.isBlank(words.get(379)) ? words.get(379) : "0.001"));
				dataModel.setDI_Mvps_07_es_03_open(Double.parseDouble(!Lib.isBlank(words.get(380)) ? words.get(380) : "0.001"));
				dataModel.setDI_Mvps_07_lbs_01_close(Double.parseDouble(!Lib.isBlank(words.get(381)) ? words.get(381) : "0.001"));
				dataModel.setDI_Mvps_07_lbs_01_open(Double.parseDouble(!Lib.isBlank(words.get(382)) ? words.get(382) : "0.001"));
				dataModel.setDI_Mvps_07_lbs_02_close(Double.parseDouble(!Lib.isBlank(words.get(383)) ? words.get(383) : "0.001"));
				dataModel.setDI_Mvps_07_lbs_02_open(Double.parseDouble(!Lib.isBlank(words.get(384)) ? words.get(384) : "0.001"));
				dataModel.setDI_Mvps_07_vcb_close(Double.parseDouble(!Lib.isBlank(words.get(385)) ? words.get(385) : "0.001"));
				dataModel.setDI_Mvps_07_vcb_open(Double.parseDouble(!Lib.isBlank(words.get(386)) ? words.get(386) : "0.001"));
				dataModel.setDI_Mvps_08_es_01_close(Double.parseDouble(!Lib.isBlank(words.get(387)) ? words.get(387) : "0.001"));
				dataModel.setDI_Mvps_08_es_01_open(Double.parseDouble(!Lib.isBlank(words.get(388)) ? words.get(388) : "0.001"));
				dataModel.setDI_Mvps_08_es_02_close(Double.parseDouble(!Lib.isBlank(words.get(389)) ? words.get(389) : "0.001"));
				dataModel.setDI_Mvps_08_es_02_open(Double.parseDouble(!Lib.isBlank(words.get(390)) ? words.get(390) : "0.001"));
				dataModel.setDI_Mvps_08_es_03_close(Double.parseDouble(!Lib.isBlank(words.get(391)) ? words.get(391) : "0.001"));
				dataModel.setDI_Mvps_08_es_03_open(Double.parseDouble(!Lib.isBlank(words.get(392)) ? words.get(392) : "0.001"));
				dataModel.setDI_Mvps_08_lbs_01_close(Double.parseDouble(!Lib.isBlank(words.get(393)) ? words.get(393) : "0.001"));
				dataModel.setDI_Mvps_08_lbs_01_open(Double.parseDouble(!Lib.isBlank(words.get(394)) ? words.get(394) : "0.001"));
				dataModel.setDI_Mvps_08_lbs_02_close(Double.parseDouble(!Lib.isBlank(words.get(395)) ? words.get(395) : "0.001"));
				dataModel.setDI_Mvps_08_lbs_02_open(Double.parseDouble(!Lib.isBlank(words.get(396)) ? words.get(396) : "0.001"));
				dataModel.setDI_Mvps_08_vcb_close(Double.parseDouble(!Lib.isBlank(words.get(397)) ? words.get(397) : "0.001"));
				dataModel.setDI_Mvps_08_vcb_open(Double.parseDouble(!Lib.isBlank(words.get(398)) ? words.get(398) : "0.001"));
				dataModel.setDI_PCB01MCBMotorFailAlarm(Double.parseDouble(!Lib.isBlank(words.get(399)) ? words.get(399) : "0.001"));
				dataModel.setDI_PCB01SF6Alarm(Double.parseDouble(!Lib.isBlank(words.get(400)) ? words.get(400) : "0.001"));
				dataModel.setDI_PCB01SF6LockoutAlarm(Double.parseDouble(!Lib.isBlank(words.get(401)) ? words.get(401) : "0.001"));
				dataModel.setDI_SSTDrawIn(Double.parseDouble(!Lib.isBlank(words.get(402)) ? words.get(402) : "0.001"));
				dataModel.setDI_SSTDrawOut(Double.parseDouble(!Lib.isBlank(words.get(403)) ? words.get(403) : "0.001"));
				dataModel.setDI_SSTGeneralFault(Double.parseDouble(!Lib.isBlank(words.get(404)) ? words.get(404) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_DC_FAIL(Double.parseDouble(!Lib.isBlank(words.get(405)) ? words.get(405) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_GROUND_IOC1_OP(Double.parseDouble(!Lib.isBlank(words.get(406)) ? words.get(406) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_GROUND_TOC1_OP(Double.parseDouble(!Lib.isBlank(words.get(407)) ? words.get(407) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_IRIG_B_FAIL(Double.parseDouble(!Lib.isBlank(words.get(408)) ? words.get(408) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_M2_AC_FAIL(Double.parseDouble(!Lib.isBlank(words.get(409)) ? words.get(409) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_PHASE_IOC1_HIGH_A_OP(Double.parseDouble(!Lib.isBlank(words.get(410)) ? words.get(410) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_PHASE_IOC1_HIGH_B_OP(Double.parseDouble(!Lib.isBlank(words.get(411)) ? words.get(411) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_PHASE_IOC1_HIGH_C_OP(Double.parseDouble(!Lib.isBlank(words.get(412)) ? words.get(412) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_PHASE_IOC1_HIGH_OP(Double.parseDouble(!Lib.isBlank(words.get(413)) ? words.get(413) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_PHASE_TOC1_HIGH_A_OP(Double.parseDouble(!Lib.isBlank(words.get(414)) ? words.get(414) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_PHASE_TOC1_HIGH_B_OP(Double.parseDouble(!Lib.isBlank(words.get(415)) ? words.get(415) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_PHASE_TOC1_HIGH_C_OP(Double.parseDouble(!Lib.isBlank(words.get(416)) ? words.get(416) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_PHASE_TOC1_HIGH_OP(Double.parseDouble(!Lib.isBlank(words.get(417)) ? words.get(417) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_RELAY_FAIL(Double.parseDouble(!Lib.isBlank(words.get(418)) ? words.get(418) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M1_VT_FUSE_FAIL(Double.parseDouble(!Lib.isBlank(words.get(419)) ? words.get(419) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M2_DC_FAIL(Double.parseDouble(!Lib.isBlank(words.get(420)) ? words.get(420) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M2_G_OC_TRIP(Double.parseDouble(!Lib.isBlank(words.get(421)) ? words.get(421) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M2_PH_OC_TRIP(Double.parseDouble(!Lib.isBlank(words.get(422)) ? words.get(422) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M2_PT_FAIL(Double.parseDouble(!Lib.isBlank(words.get(423)) ? words.get(423) : "0.001"));
				dataModel.setPROT_ALARM_LINE_M2_RELAY_FAIL(Double.parseDouble(!Lib.isBlank(words.get(424)) ? words.get(424) : "0.001"));
				dataModel.setPROT_ALARM_XF_M1_ANY_PKP(Double.parseDouble(!Lib.isBlank(words.get(425)) ? words.get(425) : "0.001"));
				dataModel.setPROT_ALARM_XF_M1_ANY_TRIP(Double.parseDouble(!Lib.isBlank(words.get(426)) ? words.get(426) : "0.001"));
				dataModel.setPROT_ALARM_XF_M1_DC_FAIL(Double.parseDouble(!Lib.isBlank(words.get(427)) ? words.get(427) : "0.001"));
				dataModel.setPROT_ALARM_XF_M1_I_DIFF_TRIP(Double.parseDouble(!Lib.isBlank(words.get(428)) ? words.get(428) : "0.001"));
				dataModel.setPROT_ALARM_XF_M1_I_DIFF_TRIP_A(Double.parseDouble(!Lib.isBlank(words.get(429)) ? words.get(429) : "0.001"));
				dataModel.setPROT_ALARM_XF_M1_I_DIFF_TRIP_B(Double.parseDouble(!Lib.isBlank(words.get(430)) ? words.get(430) : "0.001"));
				dataModel.setPROT_ALARM_XF_M1_I_DIFF_TRIP_C(Double.parseDouble(!Lib.isBlank(words.get(431)) ? words.get(431) : "0.001"));
				dataModel.setPROT_ALARM_XF_M1_IRIG_B_FAIL(Double.parseDouble(!Lib.isBlank(words.get(432)) ? words.get(432) : "0.001"));
				dataModel.setPROT_ALARM_XF_M1_LOCKOUT(Double.parseDouble(!Lib.isBlank(words.get(433)) ? words.get(433) : "0.001"));
				dataModel.setPROT_ALARM_XF_M1_M2_AC_FAIL(Double.parseDouble(!Lib.isBlank(words.get(434)) ? words.get(434) : "0.001"));
				dataModel.setPROT_ALARM_XF_M1_RELAY_FAIL(Double.parseDouble(!Lib.isBlank(words.get(435)) ? words.get(435) : "0.001"));
				dataModel.setPROT_ALARM_XF_M2_ANY_START(Double.parseDouble(!Lib.isBlank(words.get(436)) ? words.get(436) : "0.001"));
				dataModel.setPROT_ALARM_XF_M2_ANY_TRIP(Double.parseDouble(!Lib.isBlank(words.get(437)) ? words.get(437) : "0.001"));
				dataModel.setPROT_ALARM_XF_M2_ANY_TRIP_A(Double.parseDouble(!Lib.isBlank(words.get(438)) ? words.get(438) : "0.001"));
				dataModel.setPROT_ALARM_XF_M2_ANY_TRIP_B(Double.parseDouble(!Lib.isBlank(words.get(439)) ? words.get(439) : "0.001"));
				dataModel.setPROT_ALARM_XF_M2_ANY_TRIP_C(Double.parseDouble(!Lib.isBlank(words.get(440)) ? words.get(440) : "0.001"));
				dataModel.setPROT_ALARM_XF_M2_AUX_DC_FAIL(Double.parseDouble(!Lib.isBlank(words.get(441)) ? words.get(441) : "0.001"));
				dataModel.setPROT_ALARM_XF_M2_CT_FAIL_ALARM(Double.parseDouble(!Lib.isBlank(words.get(442)) ? words.get(442) : "0.001"));
				dataModel.setPROT_ALARM_XF_M2_DC_FAIL(Double.parseDouble(!Lib.isBlank(words.get(443)) ? words.get(443) : "0.001"));
				dataModel.setPROT_ALARM_XF_M2_LOCKOUT(Double.parseDouble(!Lib.isBlank(words.get(444)) ? words.get(444) : "0.001"));
				dataModel.setPROT_ALARM_XF_M2_RELAY_FAIL(Double.parseDouble(!Lib.isBlank(words.get(445)) ? words.get(445) : "0.001"));
				dataModel.setWS_AIR_PRESS(Double.parseDouble(!Lib.isBlank(words.get(446)) ? words.get(446) : "0.001"));
				dataModel.setWS_AIR_TEMP(Double.parseDouble(!Lib.isBlank(words.get(447)) ? words.get(447) : "0.001"));
				dataModel.setWS_BACK_PANEL_TEMP(Double.parseDouble(!Lib.isBlank(words.get(448)) ? words.get(448) : "0.001"));
				dataModel.setWS_DEW_POINT(Double.parseDouble(!Lib.isBlank(words.get(449)) ? words.get(449) : "0.001"));
				dataModel.setWS_GH_IRRADIANCE(Double.parseDouble(!Lib.isBlank(words.get(450)) ? words.get(450) : "0.001"));
				dataModel.setWS_INCLINED_IRRADIANCE(Double.parseDouble(!Lib.isBlank(words.get(451)) ? words.get(451) : "0.001"));
				dataModel.setWS_RELATIVE_HUMIDITY(Double.parseDouble(!Lib.isBlank(words.get(452)) ? words.get(452) : "0.001"));
				dataModel.setWS_WIND_DIRECTION(Double.parseDouble(!Lib.isBlank(words.get(453)) ? words.get(453) : "0.001"));
				dataModel.setWS_WIND_SPEED(Double.parseDouble(!Lib.isBlank(words.get(454)) ? words.get(454) : "0.001"));
				
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelSMP4DPEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSMP4DPEntity();
		}
	}


	/**
	 * @description insert data from datalogger to model_meter_ion_8600
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelSMP4DP(ModelSMP4DPEntity obj) {
		try {
			Object insertId = insert("ModelSMP4DP.insertModelSMP4DP", obj);
	        if(insertId == null ) {
	        	return false;
	        }
//			ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
//			ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
//			int hours = zdtNow.getHour();
//			if (hours >= 9 && hours <= 17 && obj.getEnable_alert() >= 1) {
//				checkTriggerCommFailAlert(obj);   // ← Chỉ gọi alert comm_fail
//			}
	        return true;
		} catch (Exception ex) {
			log.error("insertModelSMP4DP", ex);
			return false;
		}

	}
	/**
	 * @description check trigger COMM_FAIL alert
	 * @author
	 * @since 2026-04-14
	 * @param obj
	 */
	public void checkTriggerCommFailAlert(ModelSMP4DPEntity obj) {
		try {
			processAlert(obj, obj.getCOMM_FAIL_34kv_feed_1_meter_comm_fail(), 2553);
			processAlert(obj, obj.getCOMM_FAIL_34kv_feed_2_meter_comm_fail(), 2554);
			processAlert(obj, obj.getCOMM_FAIL_34kv_feed_3_meter_comm_fail(), 2555);
			processAlert(obj, obj.getCOMM_FAIL_34kv_feed_4_meter_comm_fail(), 2556);
			processAlert(obj, obj.getCOMM_FAIL_34kv_outgoing_meter_comm_fail(), 2557);
			processAlert(obj, obj.getCOMM_FAIL_34kv_sst_meter_comm_fail(), 2558);
			processAlert(obj, obj.getCOMM_FAIL_69kv_meter_comm_fail(), 2559);
			processAlert(obj, obj.getCOMM_FAIL_Ge500_rtu_comm_fail(), 2560);
			processAlert(obj, obj.getCOMM_FAIL_Idec_plc_comm_fail(), 2561);
			processAlert(obj, obj.getCOMM_FAIL_Line_prot_m1_comm_fail(), 2562);
			processAlert(obj, obj.getCOMM_FAIL_Ws_comm_fail(), 2563);
			processAlert(obj, obj.getCOMM_FAIL_Xf_prot_m1_comm_fail(), 2564);
			processAlert(obj, obj.getCOMM_FAIL_Xf_prot_m2_comm_fail(), 2565);
			processAlert(obj, obj.getDI_Feed_1_generalFault(), 2596);
			processAlert(obj, obj.getDI_Feed_2_generalFault(), 2597);
			processAlert(obj, obj.getDI_Feed_3_generalFault(), 2598);
			processAlert(obj, obj.getDI_Feed_4_generalFault(), 2599);
			processAlert(obj, obj.getDI_MVCBGeneralFault(), 2600);

		} catch (Exception e) {

			log.error("checkTriggerCommFailAlert", e);
		}
	}
	/**
	 * @description Hàm dùng chung để xử lý logic kiểm tra và lưu/cập nhật Alert
	 */
	private void processAlert(ModelSMP4DPEntity obj, double errorValue, int errorId) {
		AlertEntity alert = new AlertEntity();
		alert.setId_device(obj.getId_device());
		alert.setId_error(errorId);


		try {
			if (errorValue > 0 && errorValue != 0.001) {
				boolean checkAlertExist = (int) queryForObject("ModelSMP4DP.checkAlertlExist", alert) > 0;
				if (!checkAlertExist) {
					alert.setStart_date(obj.getTime());
					insert("ModelSMP4DP.insertAlert", alert);
				}
			} else {

				List<Map<String, Object>> dataList = queryForList("ModelSMP4DP.getOpenAlertByErrorCode", alert);
				if (dataList != null && !dataList.isEmpty()) {
					for (Map<String, Object> item : dataList) {
						alert.setId(Integer.parseInt(item.get("id").toString()));
						alert.setEnd_date(obj.getTime());
						update("Alert.UpdateErrorRow", alert);
					}
				}
			}
		} catch (Exception e) {
			log.error("processAlert", e);
			e.printStackTrace();
		}
	}

}

