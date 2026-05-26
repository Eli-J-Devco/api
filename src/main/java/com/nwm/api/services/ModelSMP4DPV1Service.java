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
import com.nwm.api.entities.ModelSMP4DPV1Entity;
import com.nwm.api.utils.Lib;

@Service
public class ModelSMP4DPV1Service extends DB {
	TriggerAlertService service = new TriggerAlertService();

	enum AlertEnum implements BaseAlertEnum {

		DI_LINE_PROT_M1_Comm_Fail(3392, "DI_LINE_PROT_M1_Comm_Fail"),
		DI_LINE_PROT_M1_General_Start(3393, "DI_LINE_PROT_M1_General_Start"),
		DI_LINE_PROT_M1_Ground_Overcurrent_Trip(3394, "DI_LINE_PROT_M1_Ground_Overcurrent_Trip"),
		DI_LINE_PROT_M1_IRIG_B_Fail(3395, "DI_LINE_PROT_M1_IRIG_B_Fail"),
		DI_LINE_PROT_M1_Phase_Overcurrent_Trip(3396, "DI_LINE_PROT_M1_Phase_Overcurrent_Trip"),
		DI_LINE_PROT_M1_Relay_Alarm(3397, "DI_LINE_PROT_M1_Relay_Alarm"),
		DI_LINE_PROT_M1_Relay_Fail(3398, "DI_LINE_PROT_M1_Relay_Fail"),
		DI_LINE_PROT_M1_VTS_Alarm(3399, "DI_LINE_PROT_M1_VTS_Alarm"),
		DI_LINE_PROT_M2_Any_Start(3400, "DI_LINE_PROT_M2_Any_Start"),
		DI_LINE_PROT_M2_Comm_Fail(3401, "DI_LINE_PROT_M2_Comm_Fail"),
		DI_LINE_PROT_M2_EF_Trip_50_51N_Stage_1(3402, "DI_LINE_PROT_M2_EF_Trip_50_51N_Stage_1"),
		DI_LINE_PROT_M2_EF_Trip_50_51N_Stage_2(3403, "DI_LINE_PROT_M2_EF_Trip_50_51N_Stage_2"),
		DI_LINE_PROT_M2_EF_Trip_50_51N_Stage_3(3404, "DI_LINE_PROT_M2_EF_Trip_50_51N_Stage_3"),
		DI_LINE_PROT_M2_EF_Trip_50_51N_Stage_4(3405, "DI_LINE_PROT_M2_EF_Trip_50_51N_Stage_4"),
		DI_LINE_PROT_M2_IRIG_B_Fail(3406, "DI_LINE_PROT_M2_IRIG_B_Fail"),
		DI_LINE_PROT_M2_OC_Trip_50_51_Stage_1(3407, "DI_LINE_PROT_M2_OC_Trip_50_51_Stage_1"),
		DI_LINE_PROT_M2_OC_Trip_50_51_Stage_2(3408, "DI_LINE_PROT_M2_OC_Trip_50_51_Stage_2"),
		DI_LINE_PROT_M2_OC_Trip_50_51_Stage_3(3409, "DI_LINE_PROT_M2_OC_Trip_50_51_Stage_3"),
		DI_LINE_PROT_M2_OC_Trip_50_51_Stage_4(3410, "DI_LINE_PROT_M2_OC_Trip_50_51_Stage_4"),
		DI_LINE_PROT_M2_Protection_Disabled(3411, "DI_LINE_PROT_M2_Protection_Disabled"),
		DI_LINE_PROT_M2_VT_Fail(3412, "DI_LINE_PROT_M2_VT_Fail"),
		DI_Meter_69kV_Comm_fail(3413, "DI_Meter_69kV_Comm_fail"),
		DI_Meter_Feeder_1_Comm_fail(3414, "DI_Meter_Feeder_1_Comm_fail"),
		DI_Meter_Feeder_2_Comm_fail(3415, "DI_Meter_Feeder_2_Comm_fail"),
		DI_Meter_Feeder_3_Comm_fail(3416, "DI_Meter_Feeder_3_Comm_fail"),
		DI_Meter_Feeder_4_Comm_fail(3417, "DI_Meter_Feeder_4_Comm_fail"),
		DI_Meter_Incomer_Comm_fail(3418, "DI_Meter_Incomer_Comm_fail"),
		DI_PLC_Comm_fail(3419, "DI_PLC_Comm_fail"),
		DI_WS_Comm_fail(3420, "DI_WS_Comm_fail"),
		DI_XF_PROT_M1_Buccholz_of_OLTC_Alarm(3421, "DI_XF_PROT_M1_Buccholz_of_OLTC_Alarm"),
		DI_XF_PROT_M1_Buccholz_of_OLTC_Trip(3422, "DI_XF_PROT_M1_Buccholz_of_OLTC_Trip"),
		DI_XF_PROT_M1_Buccholz_of_Tank_Alarm(3423, "DI_XF_PROT_M1_Buccholz_of_Tank_Alarm"),
		DI_XF_PROT_M1_Buccholz_of_Tank_Trip(3424, "DI_XF_PROT_M1_Buccholz_of_Tank_Trip"),
		DI_XF_PROT_M1_Comm_Fail(3425, "DI_XF_PROT_M1_Comm_Fail"),
		DI_XF_PROT_M1_Cooling_System_Fail(3426, "DI_XF_PROT_M1_Cooling_System_Fail"),
		DI_XF_PROT_M1_Differential_Trip_Biased(3427, "DI_XF_PROT_M1_Differential_Trip_Biased"),
		DI_XF_PROT_M1_Differential_Trip_Instant(3428, "DI_XF_PROT_M1_Differential_Trip_Instant"),
		DI_XF_PROT_M1_General_Start(3429, "DI_XF_PROT_M1_General_Start"),
		DI_XF_PROT_M1_HV_Overcurrent_Trip(3430, "DI_XF_PROT_M1_HV_Overcurrent_Trip"),
		DI_XF_PROT_M1_IRIG_B_Fail(3431, "DI_XF_PROT_M1_IRIG_B_Fail"),
		DI_XF_PROT_M1_LV_Overcurrent_Trip(3432, "DI_XF_PROT_M1_LV_Overcurrent_Trip"),
		DI_XF_PROT_M1_Oil_Level_Abn_of_OLTC_Alarm(3433, "DI_XF_PROT_M1_Oil_Level_Abn_of_OLTC_Alarm"),
		DI_XF_PROT_M1_Oil_Level_Abn_of_Tank_Alarm(3434, "DI_XF_PROT_M1_Oil_Level_Abn_of_Tank_Alarm"),
		DI_XF_PROT_M1_Oil_Temp_High_Alarm(3435, "DI_XF_PROT_M1_Oil_Temp_High_Alarm"),
		DI_XF_PROT_M1_Oil_Temp_High_Trip(3436, "DI_XF_PROT_M1_Oil_Temp_High_Trip"),

		DI_XF_PROT_M1_Pressure_Relief_of_OLTC_Alm(3437, "DI_XF_PROT_M1_Pressure_Relief_of_OLTC_Alarm"),
		DI_XF_PROT_M1_Pressure_Relief_of_Tank_Alm(3438, "DI_XF_PROT_M1_Pressure_Relief_of_Tank_Alarm"),
		DI_XF_PROT_M1_REF_Trip(3439, "DI_XF_PROT_M1_REF_Trip"),
		DI_XF_PROT_M1_Relay_Alarm(3440, "DI_XF_PROT_M1_Relay_Alarm"),
		DI_XF_PROT_M1_Relay_Fail(3441, "DI_XF_PROT_M1_Relay_Fail"),
		DI_XF_PROT_M1_VTS_Alarm(3442, "DI_XF_PROT_M1_VTS_Alarm"),
		DI_XF_PROT_M1_Winding_Temp_High_Alarm(3443, "DI_XF_PROT_M1_Winding_Temp_High_Alarm"),
		DI_XF_PROT_M1_Winding_Temp_High_Trip(3444, "DI_XF_PROT_M1_Winding_Temp_High_Trip"),

		DI_XF_PROT_M2_Any_Start(3445, "DI_XF_PROT_M2_Any_Start"),
		DI_XF_PROT_M2_Comm_Fail(3446, "DI_XF_PROT_M2_Comm_Fail"),
		DI_XF_PROT_M2_HV_GOC_Stage_1(3447, "DI_XF_PROT_M2_HV_GOC_Stage_1"),
		DI_XF_PROT_M2_HV_GOC_Stage_2(3448, "DI_XF_PROT_M2_HV_GOC_Stage_2"),
		DI_XF_PROT_M2_HV_GOC_Stage_3(3449, "DI_XF_PROT_M2_HV_GOC_Stage_3"),
		DI_XF_PROT_M2_HV_GOC_Stage_4(3450, "DI_XF_PROT_M2_HV_GOC_Stage_4"),

		DI_XF_PROT_M2_HV_POC_Stage_1(3451, "DI_XF_PROT_M2_HV_POC_Stage_1"),
		DI_XF_PROT_M2_HV_POC_Stage_2(3452, "DI_XF_PROT_M2_HV_POC_Stage_2"),
		DI_XF_PROT_M2_HV_POC_Stage_3(3453, "DI_XF_PROT_M2_HV_POC_Stage_3"),
		DI_XF_PROT_M2_HV_POC_Stage_4(3454, "DI_XF_PROT_M2_HV_POC_Stage_4"),
		DI_XF_PROT_M2_Idiff_Trip_Biased(3455, "DI_XF_PROT_M2_Idiff_Trip_Biased"),
		DI_XF_PROT_M2_IRIG_B_Fail(3456, "DI_XF_PROT_M2_IRIG_B_Fail"),
		DI_XF_PROT_M2_LV_GOC_Stage_1(3457, "DI_XF_PROT_M2_LV_GOC_Stage_1"),
		DI_XF_PROT_M2_LV_GOC_Stage_2(3458, "DI_XF_PROT_M2_LV_GOC_Stage_2"),
		DI_XF_PROT_M2_LV_GOC_Stage_3(3459, "DI_XF_PROT_M2_LV_GOC_Stage_3"),
		DI_XF_PROT_M2_LV_GOC_Stage_4(3460, "DI_XF_PROT_M2_LV_GOC_Stage_4"),
		DI_XF_PROT_M2_LV_POC_Stage_1(3461, "DI_XF_PROT_M2_LV_POC_Stage_1"),
		DI_XF_PROT_M2_LV_POC_Stage_2(3462, "DI_XF_PROT_M2_LV_POC_Stage_2"),
		DI_XF_PROT_M2_LV_POC_Stage_3(3463, "DI_XF_PROT_M2_LV_POC_Stage_3"),
		DI_XF_PROT_M2_LV_POC_Stage_4(3464, "DI_XF_PROT_M2_LV_POC_Stage_4"),
		DI_XF_PROT_M2_REF_Trip_LV(3465, "DI_XF_PROT_M2_REF_Trip_LV");


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
	
	public ModelSMP4DPV1Entity setModelSMP4DPV1(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSMP4DPV1Entity dataModel = new ModelSMP4DPV1Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(95)) ? words.get(95) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(91)) ? words.get(91) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setAI_CALC_TAPPOS(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setAI_FEED_1_Cos(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setAI_FEED_1_Freq(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setAI_FEED_1_Ia(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setAI_FEED_1_Ib(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setAI_FEED_1_Ic(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setAI_FEED_1_kVAh(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setAI_FEED_1_kVArh_Neg(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setAI_FEED_1_kVArh_Pos(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setAI_FEED_1_kWh_Del(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setAI_FEED_1_kWh_Rec(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setAI_FEED_1_P(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setAI_FEED_1_Q(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setAI_FEED_1_S(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setAI_FEED_1_Uab(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setAI_FEED_1_Uan(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setAI_FEED_1_Ubc(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setAI_FEED_1_Ubn(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setAI_FEED_1_Uca(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setAI_FEED_1_Ucn(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setAI_FEED_2_Cos(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setAI_FEED_2_Freq(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setAI_FEED_2_Ia(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setAI_FEED_2_Ib(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setAI_FEED_2_Ic(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setAI_FEED_2_kVAh(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setAI_FEED_2_kVArh_Neg(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel.setAI_FEED_2_kVArh_Pos(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setAI_FEED_2_kWh_Del(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setAI_FEED_2_kWh_Rec(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setAI_FEED_2_P(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModel.setAI_FEED_2_Q(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModel.setAI_FEED_2_S(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setAI_FEED_2_Uab(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModel.setAI_FEED_2_Uan(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel.setAI_FEED_2_Ubc(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setAI_FEED_2_Ubn(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModel.setAI_FEED_2_Uca(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModel.setAI_FEED_2_Ucn(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModel.setAI_FEED_3_Cos(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel.setAI_FEED_3_Freq(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel.setAI_FEED_3_Ia(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModel.setAI_FEED_3_Ib(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModel.setAI_FEED_3_Ic(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModel.setAI_FEED_3_kVAh(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModel.setAI_FEED_3_kVArh_Neg(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModel.setAI_FEED_3_kVArh_Pos(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				dataModel.setAI_FEED_3_kWh_Del(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModel.setAI_FEED_3_kWh_Rec(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModel.setAI_FEED_3_P(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModel.setAI_FEED_3_Q(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModel.setAI_FEED_3_S(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModel.setAI_FEED_3_Uab(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModel.setAI_FEED_3_Uan(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModel.setAI_FEED_3_Ubc(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModel.setAI_FEED_3_Ubn(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModel.setAI_FEED_3_Uca(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				dataModel.setAI_FEED_3_Ucn(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModel.setAI_FEED_4_Cos(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModel.setAI_FEED_4_Freq(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModel.setAI_FEED_4_Ia(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModel.setAI_FEED_4_Ib(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				dataModel.setAI_FEED_4_Ic(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001"));
				dataModel.setAI_FEED_4_kVAh(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001"));
				dataModel.setAI_FEED_4_kVArh_Neg(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001"));
				dataModel.setAI_FEED_4_kVArh_Pos(Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001"));
				dataModel.setAI_FEED_4_kWh_Del(Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001"));
				dataModel.setAI_FEED_4_kWh_Rec(Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001"));
				dataModel.setAI_FEED_4_P(Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001"));
				dataModel.setAI_FEED_4_Q(Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001"));
				dataModel.setAI_FEED_4_S(Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001"));
				dataModel.setAI_FEED_4_Uab(Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001"));
				dataModel.setAI_FEED_4_Uan(Double.parseDouble(!Lib.isBlank(words.get(76)) ? words.get(76) : "0.001"));
				dataModel.setAI_FEED_4_Ubc(Double.parseDouble(!Lib.isBlank(words.get(77)) ? words.get(77) : "0.001"));
				dataModel.setAI_FEED_4_Ubn(Double.parseDouble(!Lib.isBlank(words.get(78)) ? words.get(78) : "0.001"));
				dataModel.setAI_FEED_4_Uca(Double.parseDouble(!Lib.isBlank(words.get(79)) ? words.get(79) : "0.001"));
				dataModel.setAI_FEED_4_Ucn(Double.parseDouble(!Lib.isBlank(words.get(80)) ? words.get(80) : "0.001"));
				dataModel.setAI_HI_WINDING_Cos(Double.parseDouble(!Lib.isBlank(words.get(81)) ? words.get(81) : "0.001"));
				dataModel.setAI_HI_WINDING_Freq(Double.parseDouble(!Lib.isBlank(words.get(82)) ? words.get(82) : "0.001"));
				dataModel.setAI_HI_WINDING_Ia(Double.parseDouble(!Lib.isBlank(words.get(83)) ? words.get(83) : "0.001"));
				dataModel.setAI_HI_WINDING_Ib(Double.parseDouble(!Lib.isBlank(words.get(84)) ? words.get(84) : "0.001"));
				dataModel.setAI_HI_WINDING_Ic(Double.parseDouble(!Lib.isBlank(words.get(85)) ? words.get(85) : "0.001"));
				dataModel.setAI_HI_WINDING_kVAh(Double.parseDouble(!Lib.isBlank(words.get(86)) ? words.get(86) : "0.001"));
				dataModel.setAI_HI_WINDING_kVArh_Neg(Double.parseDouble(!Lib.isBlank(words.get(87)) ? words.get(87) : "0.001"));
				dataModel.setAI_HI_WINDING_kVArh_Net(Double.parseDouble(!Lib.isBlank(words.get(88)) ? words.get(88) : "0.001"));
				dataModel.setAI_HI_WINDING_kVArh_Pos(Double.parseDouble(!Lib.isBlank(words.get(89)) ? words.get(89) : "0.001"));
				dataModel.setAI_HI_WINDING_kVArh_Tot(Double.parseDouble(!Lib.isBlank(words.get(90)) ? words.get(90) : "0.001"));
				dataModel.setAI_HI_WINDING_kWh_Del(energy);
				dataModel.setAI_HI_WINDING_kWh_Net(Double.parseDouble(!Lib.isBlank(words.get(92)) ? words.get(92) : "0.001"));
				dataModel.setAI_HI_WINDING_kWh_Rec(Double.parseDouble(!Lib.isBlank(words.get(93)) ? words.get(93) : "0.001"));
				dataModel.setAI_HI_WINDING_kWh_Tot(Double.parseDouble(!Lib.isBlank(words.get(94)) ? words.get(94) : "0.001"));
				dataModel.setAI_HI_WINDING_P(power);
				dataModel.setAI_HI_WINDING_P_Revert(Double.parseDouble(!Lib.isBlank(words.get(96)) ? words.get(96) : "0.001"));
				dataModel.setAI_HI_WINDING_Q(Double.parseDouble(!Lib.isBlank(words.get(97)) ? words.get(97) : "0.001"));
				dataModel.setAI_HI_WINDING_S(Double.parseDouble(!Lib.isBlank(words.get(98)) ? words.get(98) : "0.001"));
				dataModel.setAI_HI_WINDING_Uab(Double.parseDouble(!Lib.isBlank(words.get(99)) ? words.get(99) : "0.001"));
				dataModel.setAI_HI_WINDING_Uan(Double.parseDouble(!Lib.isBlank(words.get(100)) ? words.get(100) : "0.001"));
				dataModel.setAI_HI_WINDING_Ubc(Double.parseDouble(!Lib.isBlank(words.get(101)) ? words.get(101) : "0.001"));
				dataModel.setAI_HI_WINDING_Ubn(Double.parseDouble(!Lib.isBlank(words.get(102)) ? words.get(102) : "0.001"));
				dataModel.setAI_HI_WINDING_Uca(Double.parseDouble(!Lib.isBlank(words.get(103)) ? words.get(103) : "0.001"));
				dataModel.setAI_HI_WINDING_Ucn(Double.parseDouble(!Lib.isBlank(words.get(104)) ? words.get(104) : "0.001"));
				dataModel.setAI_LOW_WINDING_Cos(Double.parseDouble(!Lib.isBlank(words.get(105)) ? words.get(105) : "0.001"));
				dataModel.setAI_LOW_WINDING_Freq(Double.parseDouble(!Lib.isBlank(words.get(106)) ? words.get(106) : "0.001"));
				dataModel.setAI_LOW_WINDING_Ia(Double.parseDouble(!Lib.isBlank(words.get(107)) ? words.get(107) : "0.001"));
				dataModel.setAI_LOW_WINDING_Ib(Double.parseDouble(!Lib.isBlank(words.get(108)) ? words.get(108) : "0.001"));
				dataModel.setAI_LOW_WINDING_Ic(Double.parseDouble(!Lib.isBlank(words.get(109)) ? words.get(109) : "0.001"));
				dataModel.setAI_LOW_WINDING_kVAh(Double.parseDouble(!Lib.isBlank(words.get(110)) ? words.get(110) : "0.001"));
				dataModel.setAI_LOW_WINDING_kVArh_Neg(Double.parseDouble(!Lib.isBlank(words.get(111)) ? words.get(111) : "0.001"));
				dataModel.setAI_LOW_WINDING_kVArh_Pos(Double.parseDouble(!Lib.isBlank(words.get(112)) ? words.get(112) : "0.001"));
				dataModel.setAI_LOW_WINDING_kWh_Del(Double.parseDouble(!Lib.isBlank(words.get(113)) ? words.get(113) : "0.001"));
				dataModel.setAI_LOW_WINDING_kWh_Rec(Double.parseDouble(!Lib.isBlank(words.get(114)) ? words.get(114) : "0.001"));
				dataModel.setAI_LOW_WINDING_P(Double.parseDouble(!Lib.isBlank(words.get(115)) ? words.get(115) : "0.001"));
				dataModel.setAI_LOW_WINDING_Q(Double.parseDouble(!Lib.isBlank(words.get(116)) ? words.get(116) : "0.001"));
				dataModel.setAI_LOW_WINDING_S(Double.parseDouble(!Lib.isBlank(words.get(117)) ? words.get(117) : "0.001"));
				dataModel.setAI_LOW_WINDING_Uab(Double.parseDouble(!Lib.isBlank(words.get(118)) ? words.get(118) : "0.001"));
				dataModel.setAI_LOW_WINDING_Uan(Double.parseDouble(!Lib.isBlank(words.get(119)) ? words.get(119) : "0.001"));
				dataModel.setAI_LOW_WINDING_Ubc(Double.parseDouble(!Lib.isBlank(words.get(120)) ? words.get(120) : "0.001"));
				dataModel.setAI_LOW_WINDING_Ubn(Double.parseDouble(!Lib.isBlank(words.get(121)) ? words.get(121) : "0.001"));
				dataModel.setAI_LOW_WINDING_Uca(Double.parseDouble(!Lib.isBlank(words.get(122)) ? words.get(122) : "0.001"));
				dataModel.setAI_LOW_WINDING_Ucn(Double.parseDouble(!Lib.isBlank(words.get(123)) ? words.get(123) : "0.001"));
				dataModel.setDI_LINE_PROT_M1_Comm_Fail(Double.parseDouble(!Lib.isBlank(words.get(124)) ? words.get(124) : "0.001"));
				dataModel.setDI_LINE_PROT_M1_General_Start(Double.parseDouble(!Lib.isBlank(words.get(125)) ? words.get(125) : "0.001"));
				dataModel.setDI_LINE_PROT_M1_Ground_Overcurrent_Trip(Double.parseDouble(!Lib.isBlank(words.get(126)) ? words.get(126) : "0.001"));
				dataModel.setDI_LINE_PROT_M1_IRIG_B_Fail(Double.parseDouble(!Lib.isBlank(words.get(127)) ? words.get(127) : "0.001"));
				dataModel.setDI_LINE_PROT_M1_Phase_Overcurrent_Trip(Double.parseDouble(!Lib.isBlank(words.get(128)) ? words.get(128) : "0.001"));
				dataModel.setDI_LINE_PROT_M1_Relay_Alarm(Double.parseDouble(!Lib.isBlank(words.get(129)) ? words.get(129) : "0.001"));
				dataModel.setDI_LINE_PROT_M1_Relay_Fail(Double.parseDouble(!Lib.isBlank(words.get(130)) ? words.get(130) : "0.001"));
				dataModel.setDI_LINE_PROT_M1_VTS_Alarm(Double.parseDouble(!Lib.isBlank(words.get(131)) ? words.get(131) : "0.001"));
				dataModel.setDI_LINE_PROT_M2_Any_Start(Double.parseDouble(!Lib.isBlank(words.get(132)) ? words.get(132) : "0.001"));
				dataModel.setDI_LINE_PROT_M2_Comm_Fail(Double.parseDouble(!Lib.isBlank(words.get(133)) ? words.get(133) : "0.001"));
				dataModel.setDI_LINE_PROT_M2_EF_Trip_50_51N_Stage_1(Double.parseDouble(!Lib.isBlank(words.get(134)) ? words.get(134) : "0.001"));
				dataModel.setDI_LINE_PROT_M2_EF_Trip_50_51N_Stage_2(Double.parseDouble(!Lib.isBlank(words.get(135)) ? words.get(135) : "0.001"));
				dataModel.setDI_LINE_PROT_M2_EF_Trip_50_51N_Stage_3(Double.parseDouble(!Lib.isBlank(words.get(136)) ? words.get(136) : "0.001"));
				dataModel.setDI_LINE_PROT_M2_EF_Trip_50_51N_Stage_4(Double.parseDouble(!Lib.isBlank(words.get(137)) ? words.get(137) : "0.001"));
				dataModel.setDI_LINE_PROT_M2_IRIG_B_Fail(Double.parseDouble(!Lib.isBlank(words.get(138)) ? words.get(138) : "0.001"));
				dataModel.setDI_LINE_PROT_M2_OC_Trip_50_51_Stage_1(Double.parseDouble(!Lib.isBlank(words.get(139)) ? words.get(139) : "0.001"));
				dataModel.setDI_LINE_PROT_M2_OC_Trip_50_51_Stage_2(Double.parseDouble(!Lib.isBlank(words.get(140)) ? words.get(140) : "0.001"));
				dataModel.setDI_LINE_PROT_M2_OC_Trip_50_51_Stage_3(Double.parseDouble(!Lib.isBlank(words.get(141)) ? words.get(141) : "0.001"));
				dataModel.setDI_LINE_PROT_M2_OC_Trip_50_51_Stage_4(Double.parseDouble(!Lib.isBlank(words.get(142)) ? words.get(142) : "0.001"));
				dataModel.setDI_LINE_PROT_M2_Protection_Disabled(Double.parseDouble(!Lib.isBlank(words.get(143)) ? words.get(143) : "0.001"));
				dataModel.setDI_LINE_PROT_M2_VT_Fail(Double.parseDouble(!Lib.isBlank(words.get(144)) ? words.get(144) : "0.001"));
				dataModel.setDI_Meter_69kV_Comm_fail(Double.parseDouble(!Lib.isBlank(words.get(145)) ? words.get(145) : "0.001"));
				dataModel.setDI_Meter_Feeder_1_Comm_fail(Double.parseDouble(!Lib.isBlank(words.get(146)) ? words.get(146) : "0.001"));
				dataModel.setDI_Meter_Feeder_2_Comm_fail(Double.parseDouble(!Lib.isBlank(words.get(147)) ? words.get(147) : "0.001"));
				dataModel.setDI_Meter_Feeder_3_Comm_fail(Double.parseDouble(!Lib.isBlank(words.get(148)) ? words.get(148) : "0.001"));
				dataModel.setDI_Meter_Feeder_4_Comm_fail(Double.parseDouble(!Lib.isBlank(words.get(149)) ? words.get(149) : "0.001"));
				dataModel.setDI_Meter_Incomer_Comm_fail(Double.parseDouble(!Lib.isBlank(words.get(150)) ? words.get(150) : "0.001"));
				dataModel.setDI_PLC_Comm_fail(Double.parseDouble(!Lib.isBlank(words.get(151)) ? words.get(151) : "0.001"));
				dataModel.setDI_WS_Comm_fail(Double.parseDouble(!Lib.isBlank(words.get(152)) ? words.get(152) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Buccholz_of_OLTC_Alarm(Double.parseDouble(!Lib.isBlank(words.get(153)) ? words.get(153) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Buccholz_of_OLTC_Trip(Double.parseDouble(!Lib.isBlank(words.get(154)) ? words.get(154) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Buccholz_of_Tank_Alarm(Double.parseDouble(!Lib.isBlank(words.get(155)) ? words.get(155) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Buccholz_of_Tank_Trip(Double.parseDouble(!Lib.isBlank(words.get(156)) ? words.get(156) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Comm_Fail(Double.parseDouble(!Lib.isBlank(words.get(157)) ? words.get(157) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Cooling_System_Fail(Double.parseDouble(!Lib.isBlank(words.get(158)) ? words.get(158) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Differential_Trip_Biased(Double.parseDouble(!Lib.isBlank(words.get(159)) ? words.get(159) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Differential_Trip_Instant(Double.parseDouble(!Lib.isBlank(words.get(160)) ? words.get(160) : "0.001"));
				dataModel.setDI_XF_PROT_M1_General_Start(Double.parseDouble(!Lib.isBlank(words.get(161)) ? words.get(161) : "0.001"));
				dataModel.setDI_XF_PROT_M1_HV_Overcurrent_Trip(Double.parseDouble(!Lib.isBlank(words.get(162)) ? words.get(162) : "0.001"));
				dataModel.setDI_XF_PROT_M1_IRIG_B_Fail(Double.parseDouble(!Lib.isBlank(words.get(163)) ? words.get(163) : "0.001"));
				dataModel.setDI_XF_PROT_M1_LV_Overcurrent_Trip(Double.parseDouble(!Lib.isBlank(words.get(164)) ? words.get(164) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Oil_Level_Abn_of_OLTC_Alarm(Double.parseDouble(!Lib.isBlank(words.get(165)) ? words.get(165) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Oil_Level_Abn_of_Tank_Alarm(Double.parseDouble(!Lib.isBlank(words.get(166)) ? words.get(166) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Oil_Temp_High_Alarm(Double.parseDouble(!Lib.isBlank(words.get(167)) ? words.get(167) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Oil_Temp_High_Trip(Double.parseDouble(!Lib.isBlank(words.get(168)) ? words.get(168) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Pressure_Relief_of_OLTC_Alm(Double.parseDouble(!Lib.isBlank(words.get(169)) ? words.get(169) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Pressure_Relief_of_Tank_Alm(Double.parseDouble(!Lib.isBlank(words.get(170)) ? words.get(170) : "0.001"));
				dataModel.setDI_XF_PROT_M1_REF_Trip(Double.parseDouble(!Lib.isBlank(words.get(171)) ? words.get(171) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Relay_Alarm(Double.parseDouble(!Lib.isBlank(words.get(172)) ? words.get(172) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Relay_Fail(Double.parseDouble(!Lib.isBlank(words.get(173)) ? words.get(173) : "0.001"));
				dataModel.setDI_XF_PROT_M1_VTS_Alarm(Double.parseDouble(!Lib.isBlank(words.get(174)) ? words.get(174) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Winding_Temp_High_Alarm(Double.parseDouble(!Lib.isBlank(words.get(175)) ? words.get(175) : "0.001"));
				dataModel.setDI_XF_PROT_M1_Winding_Temp_High_Trip(Double.parseDouble(!Lib.isBlank(words.get(176)) ? words.get(176) : "0.001"));
				dataModel.setDI_XF_PROT_M2_Any_Start(Double.parseDouble(!Lib.isBlank(words.get(177)) ? words.get(177) : "0.001"));
				dataModel.setDI_XF_PROT_M2_Comm_Fail(Double.parseDouble(!Lib.isBlank(words.get(178)) ? words.get(178) : "0.001"));
				dataModel.setDI_XF_PROT_M2_HV_GOC_Stage_1(Double.parseDouble(!Lib.isBlank(words.get(179)) ? words.get(179) : "0.001"));
				dataModel.setDI_XF_PROT_M2_HV_GOC_Stage_2(Double.parseDouble(!Lib.isBlank(words.get(180)) ? words.get(180) : "0.001"));
				dataModel.setDI_XF_PROT_M2_HV_GOC_Stage_3(Double.parseDouble(!Lib.isBlank(words.get(181)) ? words.get(181) : "0.001"));
				dataModel.setDI_XF_PROT_M2_HV_GOC_Stage_4(Double.parseDouble(!Lib.isBlank(words.get(182)) ? words.get(182) : "0.001"));
				dataModel.setDI_XF_PROT_M2_HV_POC_Stage_1(Double.parseDouble(!Lib.isBlank(words.get(183)) ? words.get(183) : "0.001"));
				dataModel.setDI_XF_PROT_M2_HV_POC_Stage_2(Double.parseDouble(!Lib.isBlank(words.get(184)) ? words.get(184) : "0.001"));
				dataModel.setDI_XF_PROT_M2_HV_POC_Stage_3(Double.parseDouble(!Lib.isBlank(words.get(185)) ? words.get(185) : "0.001"));
				dataModel.setDI_XF_PROT_M2_HV_POC_Stage_4(Double.parseDouble(!Lib.isBlank(words.get(186)) ? words.get(186) : "0.001"));
				dataModel.setDI_XF_PROT_M2_Idiff_Trip(Double.parseDouble(!Lib.isBlank(words.get(187)) ? words.get(187) : "0.001"));
				dataModel.setDI_XF_PROT_M2_IRIG_B_Fail(Double.parseDouble(!Lib.isBlank(words.get(188)) ? words.get(188) : "0.001"));
				dataModel.setDI_XF_PROT_M2_LV_GOC_Stage_1(Double.parseDouble(!Lib.isBlank(words.get(189)) ? words.get(189) : "0.001"));
				dataModel.setDI_XF_PROT_M2_LV_GOC_Stage_2(Double.parseDouble(!Lib.isBlank(words.get(190)) ? words.get(190) : "0.001"));
				dataModel.setDI_XF_PROT_M2_LV_GOC_Stage_3(Double.parseDouble(!Lib.isBlank(words.get(191)) ? words.get(191) : "0.001"));
				dataModel.setDI_XF_PROT_M2_LV_GOC_Stage_4(Double.parseDouble(!Lib.isBlank(words.get(192)) ? words.get(192) : "0.001"));
				dataModel.setDI_XF_PROT_M2_LV_POC_Stage_1(Double.parseDouble(!Lib.isBlank(words.get(193)) ? words.get(193) : "0.001"));
				dataModel.setDI_XF_PROT_M2_LV_POC_Stage_2(Double.parseDouble(!Lib.isBlank(words.get(194)) ? words.get(194) : "0.001"));
				dataModel.setDI_XF_PROT_M2_LV_POC_Stage_3(Double.parseDouble(!Lib.isBlank(words.get(195)) ? words.get(195) : "0.001"));
				dataModel.setDI_XF_PROT_M2_LV_POC_Stage_4(Double.parseDouble(!Lib.isBlank(words.get(196)) ? words.get(196) : "0.001"));
				dataModel.setDI_XF_PROT_M2_REF_Trip_LV(Double.parseDouble(!Lib.isBlank(words.get(197)) ? words.get(197) : "0.001"));
				dataModel.setWS_AIR_PRESS(Double.parseDouble(!Lib.isBlank(words.get(198)) ? words.get(198) : "0.001"));
				dataModel.setWS_AIR_TEMP(Double.parseDouble(!Lib.isBlank(words.get(199)) ? words.get(199) : "0.001"));
				dataModel.setWS_BACK_PANEL_TEMP(Double.parseDouble(!Lib.isBlank(words.get(200)) ? words.get(200) : "0.001"));
				dataModel.setWS_DEW_POINT(Double.parseDouble(!Lib.isBlank(words.get(201)) ? words.get(201) : "0.001"));
				dataModel.setWS_GH_IRRADIANCE(Double.parseDouble(!Lib.isBlank(words.get(202)) ? words.get(202) : "0.001"));
				dataModel.setWS_INCLINED_IRRADIANCE(Double.parseDouble(!Lib.isBlank(words.get(203)) ? words.get(203) : "0.001"));
				dataModel.setWS_RELATIVE_HUMIDITY(Double.parseDouble(!Lib.isBlank(words.get(204)) ? words.get(204) : "0.001"));
				dataModel.setWS_WIND_DIRECTION(Double.parseDouble(!Lib.isBlank(words.get(205)) ? words.get(205) : "0.001"));
				dataModel.setWS_WIND_SPEED(Double.parseDouble(!Lib.isBlank(words.get(206)) ? words.get(206) : "0.001"));
				
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelSMP4DPV1Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSMP4DPV1Entity();
		}
	}


	/**
	 * @description insert data from datalogger to model_meter_ion_8600
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelSMP4DPV1(ModelSMP4DPV1Entity obj) {
		try {
			Object insertId = insert("ModelSMP4DPV1.insertModelSMP4DPV1", obj);
	        if(insertId == null ) {
	        	return false;
	        }
			ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
			ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
			int hours = zdtNow.getHour();
			if (hours >= 9 && hours <= 17 && obj.getEnable_alert() >= 1) {
//				service.checkTriggerAlert(obj.getDatatablename(), obj.getTime(), obj.getId_device(), ModelSMP4DPV1Service.AlertEnum.values());
			}

	        return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}

}
