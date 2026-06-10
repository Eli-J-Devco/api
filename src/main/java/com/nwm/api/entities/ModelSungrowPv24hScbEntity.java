/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSungrowPv24hScbEntity extends ModelBaseEntity {
	private double amp_01;
	private double amp_02;
	private double amp_03;
	private double amp_04;
	private double amp_05;
	private double amp_06;
	private double amp_07;
	private double amp_08;
	private double amp_09;
	private double amp_10;
	private double amp_11;
	private double amp_12;
	private double amp_13;
	private double amp_14;
	private double amp_15;
	private double amp_16;
	private double amp_17;
	private double amp_18;
	private double amp_19;
	private double amp_20;
	private double amp_21;
	private double amp_22;
	private double amp_23;
	private double amp_24;
	private double daily_yield;
	private double dc_amp;
	private double dc_bus_volt;
	private double dc_power;
	private double fuse_blow_01;
	private double fuse_blow_02;
	private double fuse_blow_03;
	private double fuse_blow_04;
	private double fuse_blow_05;
	private double fuse_blow_06;
	private double fuse_blow_07;
	private double fuse_blow_08;
	private double fuse_blow_09;
	private double fuse_blow_10;
	private double fuse_blow_11;
	private double fuse_blow_12;
	private double fuse_blow_13;
	private double fuse_blow_14;
	private double fuse_blow_15;
	private double fuse_blow_16;
	private double fuse_blow_17;
	private double fuse_blow_18;
	private double fuse_blow_19;
	private double fuse_blow_20;
	private double fuse_blow_21;
	private double fuse_blow_22;
	private double fuse_blow_23;
	private double fuse_blow_24;
	private double high_current_01;
	private double high_current_02;
	private double high_current_03;
	private double high_current_04;
	private double high_current_05;
	private double high_current_06;
	private double high_current_07;
	private double high_current_08;
	private double high_current_09;
	private double high_current_10;
	private double high_current_11;
	private double high_current_12;
	private double high_current_13;
	private double high_current_14;
	private double high_current_15;
	private double high_current_16;
	private double high_current_17;
	private double high_current_18;
	private double high_current_19;
	private double high_current_20;
	private double high_current_21;
	private double high_current_22;
	private double high_current_23;
	private double high_current_24;
	private double low_current_01;
	private double low_current_02;
	private double low_current_03;
	private double low_current_04;
	private double low_current_05;
	private double low_current_06;
	private double low_current_07;
	private double low_current_08;
	private double low_current_09;
	private double low_current_10;
	private double low_current_11;
	private double low_current_12;
	private double low_current_13;
	private double low_current_14;
	private double low_current_15;
	private double low_current_16;
	private double low_current_17;
	private double low_current_18;
	private double low_current_19;
	private double low_current_20;
	private double low_current_21;
	private double low_current_22;
	private double low_current_23;
	private double low_current_24;
	private double open_circuit_01;
	private double open_circuit_02;
	private double open_circuit_03;
	private double open_circuit_04;
	private double open_circuit_05;
	private double open_circuit_06;
	private double open_circuit_07;
	private double open_circuit_08;
	private double open_circuit_09;
	private double open_circuit_10;
	private double open_circuit_11;
	private double open_circuit_12;
	private double open_circuit_13;
	private double open_circuit_14;
	private double open_circuit_15;
	private double open_circuit_16;
	private double open_circuit_17;
	private double open_circuit_18;
	private double open_circuit_19;
	private double open_circuit_20;
	private double open_circuit_21;
	private double open_circuit_22;
	private double open_circuit_23;
	private double open_circuit_24;
	private double power_01;
	private double power_02;
	private double power_03;
	private double power_04;
	private double power_05;
	private double power_06;
	private double power_07;
	private double power_08;
	private double power_09;
	private double power_10;
	private double power_11;
	private double power_12;
	private double power_13;
	private double power_14;
	private double power_15;
	private double power_16;
	private double power_17;
	private double power_18;
	private double power_19;
	private double power_20;
	private double power_21;
	private double power_22;
	private double power_23;
	private double power_24;
	private double reverse_current_01;
	private double reverse_current_02;
	private double reverse_current_03;
	private double reverse_current_04;
	private double reverse_current_05;
	private double reverse_current_06;
	private double reverse_current_07;
	private double reverse_current_08;
	private double reverse_current_09;
	private double reverse_current_10;
	private double reverse_current_11;
	private double reverse_current_12;
	private double reverse_current_13;
	private double reverse_current_14;
	private double reverse_current_15;
	private double reverse_current_16;
	private double reverse_current_17;
	private double reverse_current_18;
	private double reverse_current_19;
	private double reverse_current_20;
	private double reverse_current_21;
	private double reverse_current_22;
	private double reverse_current_23;
	private double reverse_current_24;
	private double short_circuit_01;
	private double short_circuit_02;
	private double short_circuit_03;
	private double short_circuit_04;
	private double short_circuit_05;
	private double short_circuit_06;
	private double short_circuit_07;
	private double short_circuit_08;
	private double short_circuit_09;
	private double short_circuit_10;
	private double short_circuit_11;
	private double short_circuit_12;
	private double short_circuit_13;
	private double short_circuit_14;
	private double short_circuit_15;
	private double short_circuit_16;
	private double short_circuit_17;
	private double short_circuit_18;
	private double short_circuit_19;
	private double short_circuit_20;
	private double short_circuit_21;
	private double short_circuit_22;
	private double short_circuit_23;
	private double short_circuit_24;
	private double stat_fuse_blow;
	private double stat_hi_current;
	private double stat_hi_dc_volt;
	private double stat_hi_temp;
	private double stat_low_current;
	private double stat_open_circuit;
	private double stat_rev_current;
	private double stat_self_test_trip;
	private double stat_short_circuit;
	private double stat_shunt_trip_command;
	private double stat_spd_fault;
	private double stat_switch_trip;
	private double stat_switch_trip_enable;
	private double stat_trip;
	private double temp;
	private double total_yield;
	private double work_state;
	public double getAmp_01() {
		return amp_01;
	}
	public void setAmp_01(double amp_01) {
		this.amp_01 = amp_01;
	}
	public double getAmp_02() {
		return amp_02;
	}
	public void setAmp_02(double amp_02) {
		this.amp_02 = amp_02;
	}
	public double getAmp_03() {
		return amp_03;
	}
	public void setAmp_03(double amp_03) {
		this.amp_03 = amp_03;
	}
	public double getAmp_04() {
		return amp_04;
	}
	public void setAmp_04(double amp_04) {
		this.amp_04 = amp_04;
	}
	public double getAmp_05() {
		return amp_05;
	}
	public void setAmp_05(double amp_05) {
		this.amp_05 = amp_05;
	}
	public double getAmp_06() {
		return amp_06;
	}
	public void setAmp_06(double amp_06) {
		this.amp_06 = amp_06;
	}
	public double getAmp_07() {
		return amp_07;
	}
	public void setAmp_07(double amp_07) {
		this.amp_07 = amp_07;
	}
	public double getAmp_08() {
		return amp_08;
	}
	public void setAmp_08(double amp_08) {
		this.amp_08 = amp_08;
	}
	public double getAmp_09() {
		return amp_09;
	}
	public void setAmp_09(double amp_09) {
		this.amp_09 = amp_09;
	}
	public double getAmp_10() {
		return amp_10;
	}
	public void setAmp_10(double amp_10) {
		this.amp_10 = amp_10;
	}
	public double getAmp_11() {
		return amp_11;
	}
	public void setAmp_11(double amp_11) {
		this.amp_11 = amp_11;
	}
	public double getAmp_12() {
		return amp_12;
	}
	public void setAmp_12(double amp_12) {
		this.amp_12 = amp_12;
	}
	public double getAmp_13() {
		return amp_13;
	}
	public void setAmp_13(double amp_13) {
		this.amp_13 = amp_13;
	}
	public double getAmp_14() {
		return amp_14;
	}
	public void setAmp_14(double amp_14) {
		this.amp_14 = amp_14;
	}
	public double getAmp_15() {
		return amp_15;
	}
	public void setAmp_15(double amp_15) {
		this.amp_15 = amp_15;
	}
	public double getAmp_16() {
		return amp_16;
	}
	public void setAmp_16(double amp_16) {
		this.amp_16 = amp_16;
	}
	public double getAmp_17() {
		return amp_17;
	}
	public void setAmp_17(double amp_17) {
		this.amp_17 = amp_17;
	}
	public double getAmp_18() {
		return amp_18;
	}
	public void setAmp_18(double amp_18) {
		this.amp_18 = amp_18;
	}
	public double getAmp_19() {
		return amp_19;
	}
	public void setAmp_19(double amp_19) {
		this.amp_19 = amp_19;
	}
	public double getAmp_20() {
		return amp_20;
	}
	public void setAmp_20(double amp_20) {
		this.amp_20 = amp_20;
	}
	public double getAmp_21() {
		return amp_21;
	}
	public void setAmp_21(double amp_21) {
		this.amp_21 = amp_21;
	}
	public double getAmp_22() {
		return amp_22;
	}
	public void setAmp_22(double amp_22) {
		this.amp_22 = amp_22;
	}
	public double getAmp_23() {
		return amp_23;
	}
	public void setAmp_23(double amp_23) {
		this.amp_23 = amp_23;
	}
	public double getAmp_24() {
		return amp_24;
	}
	public void setAmp_24(double amp_24) {
		this.amp_24 = amp_24;
	}
	public double getDaily_yield() {
		return daily_yield;
	}
	public void setDaily_yield(double daily_yield) {
		this.daily_yield = daily_yield;
	}
	public double getDc_amp() {
		return dc_amp;
	}
	public void setDc_amp(double dc_amp) {
		this.dc_amp = dc_amp;
	}
	public double getDc_bus_volt() {
		return dc_bus_volt;
	}
	public void setDc_bus_volt(double dc_bus_volt) {
		this.dc_bus_volt = dc_bus_volt;
	}
	public double getDc_power() {
		return dc_power;
	}
	public void setDc_power(double dc_power) {
		this.dc_power = dc_power;
	}
	public double getFuse_blow_01() {
		return fuse_blow_01;
	}
	public void setFuse_blow_01(double fuse_blow_01) {
		this.fuse_blow_01 = fuse_blow_01;
	}
	public double getFuse_blow_02() {
		return fuse_blow_02;
	}
	public void setFuse_blow_02(double fuse_blow_02) {
		this.fuse_blow_02 = fuse_blow_02;
	}
	public double getFuse_blow_03() {
		return fuse_blow_03;
	}
	public void setFuse_blow_03(double fuse_blow_03) {
		this.fuse_blow_03 = fuse_blow_03;
	}
	public double getFuse_blow_04() {
		return fuse_blow_04;
	}
	public void setFuse_blow_04(double fuse_blow_04) {
		this.fuse_blow_04 = fuse_blow_04;
	}
	public double getFuse_blow_05() {
		return fuse_blow_05;
	}
	public void setFuse_blow_05(double fuse_blow_05) {
		this.fuse_blow_05 = fuse_blow_05;
	}
	public double getFuse_blow_06() {
		return fuse_blow_06;
	}
	public void setFuse_blow_06(double fuse_blow_06) {
		this.fuse_blow_06 = fuse_blow_06;
	}
	public double getFuse_blow_07() {
		return fuse_blow_07;
	}
	public void setFuse_blow_07(double fuse_blow_07) {
		this.fuse_blow_07 = fuse_blow_07;
	}
	public double getFuse_blow_08() {
		return fuse_blow_08;
	}
	public void setFuse_blow_08(double fuse_blow_08) {
		this.fuse_blow_08 = fuse_blow_08;
	}
	public double getFuse_blow_09() {
		return fuse_blow_09;
	}
	public void setFuse_blow_09(double fuse_blow_09) {
		this.fuse_blow_09 = fuse_blow_09;
	}
	public double getFuse_blow_10() {
		return fuse_blow_10;
	}
	public void setFuse_blow_10(double fuse_blow_10) {
		this.fuse_blow_10 = fuse_blow_10;
	}
	public double getFuse_blow_11() {
		return fuse_blow_11;
	}
	public void setFuse_blow_11(double fuse_blow_11) {
		this.fuse_blow_11 = fuse_blow_11;
	}
	public double getFuse_blow_12() {
		return fuse_blow_12;
	}
	public void setFuse_blow_12(double fuse_blow_12) {
		this.fuse_blow_12 = fuse_blow_12;
	}
	public double getFuse_blow_13() {
		return fuse_blow_13;
	}
	public void setFuse_blow_13(double fuse_blow_13) {
		this.fuse_blow_13 = fuse_blow_13;
	}
	public double getFuse_blow_14() {
		return fuse_blow_14;
	}
	public void setFuse_blow_14(double fuse_blow_14) {
		this.fuse_blow_14 = fuse_blow_14;
	}
	public double getFuse_blow_15() {
		return fuse_blow_15;
	}
	public void setFuse_blow_15(double fuse_blow_15) {
		this.fuse_blow_15 = fuse_blow_15;
	}
	public double getFuse_blow_16() {
		return fuse_blow_16;
	}
	public void setFuse_blow_16(double fuse_blow_16) {
		this.fuse_blow_16 = fuse_blow_16;
	}
	public double getFuse_blow_17() {
		return fuse_blow_17;
	}
	public void setFuse_blow_17(double fuse_blow_17) {
		this.fuse_blow_17 = fuse_blow_17;
	}
	public double getFuse_blow_18() {
		return fuse_blow_18;
	}
	public void setFuse_blow_18(double fuse_blow_18) {
		this.fuse_blow_18 = fuse_blow_18;
	}
	public double getFuse_blow_19() {
		return fuse_blow_19;
	}
	public void setFuse_blow_19(double fuse_blow_19) {
		this.fuse_blow_19 = fuse_blow_19;
	}
	public double getFuse_blow_20() {
		return fuse_blow_20;
	}
	public void setFuse_blow_20(double fuse_blow_20) {
		this.fuse_blow_20 = fuse_blow_20;
	}
	public double getFuse_blow_21() {
		return fuse_blow_21;
	}
	public void setFuse_blow_21(double fuse_blow_21) {
		this.fuse_blow_21 = fuse_blow_21;
	}
	public double getFuse_blow_22() {
		return fuse_blow_22;
	}
	public void setFuse_blow_22(double fuse_blow_22) {
		this.fuse_blow_22 = fuse_blow_22;
	}
	public double getFuse_blow_23() {
		return fuse_blow_23;
	}
	public void setFuse_blow_23(double fuse_blow_23) {
		this.fuse_blow_23 = fuse_blow_23;
	}
	public double getFuse_blow_24() {
		return fuse_blow_24;
	}
	public void setFuse_blow_24(double fuse_blow_24) {
		this.fuse_blow_24 = fuse_blow_24;
	}
	public double getHigh_current_01() {
		return high_current_01;
	}
	public void setHigh_current_01(double high_current_01) {
		this.high_current_01 = high_current_01;
	}
	public double getHigh_current_02() {
		return high_current_02;
	}
	public void setHigh_current_02(double high_current_02) {
		this.high_current_02 = high_current_02;
	}
	public double getHigh_current_03() {
		return high_current_03;
	}
	public void setHigh_current_03(double high_current_03) {
		this.high_current_03 = high_current_03;
	}
	public double getHigh_current_04() {
		return high_current_04;
	}
	public void setHigh_current_04(double high_current_04) {
		this.high_current_04 = high_current_04;
	}
	public double getHigh_current_05() {
		return high_current_05;
	}
	public void setHigh_current_05(double high_current_05) {
		this.high_current_05 = high_current_05;
	}
	public double getHigh_current_06() {
		return high_current_06;
	}
	public void setHigh_current_06(double high_current_06) {
		this.high_current_06 = high_current_06;
	}
	public double getHigh_current_07() {
		return high_current_07;
	}
	public void setHigh_current_07(double high_current_07) {
		this.high_current_07 = high_current_07;
	}
	public double getHigh_current_08() {
		return high_current_08;
	}
	public void setHigh_current_08(double high_current_08) {
		this.high_current_08 = high_current_08;
	}
	public double getHigh_current_09() {
		return high_current_09;
	}
	public void setHigh_current_09(double high_current_09) {
		this.high_current_09 = high_current_09;
	}
	public double getHigh_current_10() {
		return high_current_10;
	}
	public void setHigh_current_10(double high_current_10) {
		this.high_current_10 = high_current_10;
	}
	public double getHigh_current_11() {
		return high_current_11;
	}
	public void setHigh_current_11(double high_current_11) {
		this.high_current_11 = high_current_11;
	}
	public double getHigh_current_12() {
		return high_current_12;
	}
	public void setHigh_current_12(double high_current_12) {
		this.high_current_12 = high_current_12;
	}
	public double getHigh_current_13() {
		return high_current_13;
	}
	public void setHigh_current_13(double high_current_13) {
		this.high_current_13 = high_current_13;
	}
	public double getHigh_current_14() {
		return high_current_14;
	}
	public void setHigh_current_14(double high_current_14) {
		this.high_current_14 = high_current_14;
	}
	public double getHigh_current_15() {
		return high_current_15;
	}
	public void setHigh_current_15(double high_current_15) {
		this.high_current_15 = high_current_15;
	}
	public double getHigh_current_16() {
		return high_current_16;
	}
	public void setHigh_current_16(double high_current_16) {
		this.high_current_16 = high_current_16;
	}
	public double getHigh_current_17() {
		return high_current_17;
	}
	public void setHigh_current_17(double high_current_17) {
		this.high_current_17 = high_current_17;
	}
	public double getHigh_current_18() {
		return high_current_18;
	}
	public void setHigh_current_18(double high_current_18) {
		this.high_current_18 = high_current_18;
	}
	public double getHigh_current_19() {
		return high_current_19;
	}
	public void setHigh_current_19(double high_current_19) {
		this.high_current_19 = high_current_19;
	}
	public double getHigh_current_20() {
		return high_current_20;
	}
	public void setHigh_current_20(double high_current_20) {
		this.high_current_20 = high_current_20;
	}
	public double getHigh_current_21() {
		return high_current_21;
	}
	public void setHigh_current_21(double high_current_21) {
		this.high_current_21 = high_current_21;
	}
	public double getHigh_current_22() {
		return high_current_22;
	}
	public void setHigh_current_22(double high_current_22) {
		this.high_current_22 = high_current_22;
	}
	public double getHigh_current_23() {
		return high_current_23;
	}
	public void setHigh_current_23(double high_current_23) {
		this.high_current_23 = high_current_23;
	}
	public double getHigh_current_24() {
		return high_current_24;
	}
	public void setHigh_current_24(double high_current_24) {
		this.high_current_24 = high_current_24;
	}
	public double getLow_current_01() {
		return low_current_01;
	}
	public void setLow_current_01(double low_current_01) {
		this.low_current_01 = low_current_01;
	}
	public double getLow_current_02() {
		return low_current_02;
	}
	public void setLow_current_02(double low_current_02) {
		this.low_current_02 = low_current_02;
	}
	public double getLow_current_03() {
		return low_current_03;
	}
	public void setLow_current_03(double low_current_03) {
		this.low_current_03 = low_current_03;
	}
	public double getLow_current_04() {
		return low_current_04;
	}
	public void setLow_current_04(double low_current_04) {
		this.low_current_04 = low_current_04;
	}
	public double getLow_current_05() {
		return low_current_05;
	}
	public void setLow_current_05(double low_current_05) {
		this.low_current_05 = low_current_05;
	}
	public double getLow_current_06() {
		return low_current_06;
	}
	public void setLow_current_06(double low_current_06) {
		this.low_current_06 = low_current_06;
	}
	public double getLow_current_07() {
		return low_current_07;
	}
	public void setLow_current_07(double low_current_07) {
		this.low_current_07 = low_current_07;
	}
	public double getLow_current_08() {
		return low_current_08;
	}
	public void setLow_current_08(double low_current_08) {
		this.low_current_08 = low_current_08;
	}
	public double getLow_current_09() {
		return low_current_09;
	}
	public void setLow_current_09(double low_current_09) {
		this.low_current_09 = low_current_09;
	}
	public double getLow_current_10() {
		return low_current_10;
	}
	public void setLow_current_10(double low_current_10) {
		this.low_current_10 = low_current_10;
	}
	public double getLow_current_11() {
		return low_current_11;
	}
	public void setLow_current_11(double low_current_11) {
		this.low_current_11 = low_current_11;
	}
	public double getLow_current_12() {
		return low_current_12;
	}
	public void setLow_current_12(double low_current_12) {
		this.low_current_12 = low_current_12;
	}
	public double getLow_current_13() {
		return low_current_13;
	}
	public void setLow_current_13(double low_current_13) {
		this.low_current_13 = low_current_13;
	}
	public double getLow_current_14() {
		return low_current_14;
	}
	public void setLow_current_14(double low_current_14) {
		this.low_current_14 = low_current_14;
	}
	public double getLow_current_15() {
		return low_current_15;
	}
	public void setLow_current_15(double low_current_15) {
		this.low_current_15 = low_current_15;
	}
	public double getLow_current_16() {
		return low_current_16;
	}
	public void setLow_current_16(double low_current_16) {
		this.low_current_16 = low_current_16;
	}
	public double getLow_current_17() {
		return low_current_17;
	}
	public void setLow_current_17(double low_current_17) {
		this.low_current_17 = low_current_17;
	}
	public double getLow_current_18() {
		return low_current_18;
	}
	public void setLow_current_18(double low_current_18) {
		this.low_current_18 = low_current_18;
	}
	public double getLow_current_19() {
		return low_current_19;
	}
	public void setLow_current_19(double low_current_19) {
		this.low_current_19 = low_current_19;
	}
	public double getLow_current_20() {
		return low_current_20;
	}
	public void setLow_current_20(double low_current_20) {
		this.low_current_20 = low_current_20;
	}
	public double getLow_current_21() {
		return low_current_21;
	}
	public void setLow_current_21(double low_current_21) {
		this.low_current_21 = low_current_21;
	}
	public double getLow_current_22() {
		return low_current_22;
	}
	public void setLow_current_22(double low_current_22) {
		this.low_current_22 = low_current_22;
	}
	public double getLow_current_23() {
		return low_current_23;
	}
	public void setLow_current_23(double low_current_23) {
		this.low_current_23 = low_current_23;
	}
	public double getLow_current_24() {
		return low_current_24;
	}
	public void setLow_current_24(double low_current_24) {
		this.low_current_24 = low_current_24;
	}
	public double getOpen_circuit_01() {
		return open_circuit_01;
	}
	public void setOpen_circuit_01(double open_circuit_01) {
		this.open_circuit_01 = open_circuit_01;
	}
	public double getOpen_circuit_02() {
		return open_circuit_02;
	}
	public void setOpen_circuit_02(double open_circuit_02) {
		this.open_circuit_02 = open_circuit_02;
	}
	public double getOpen_circuit_03() {
		return open_circuit_03;
	}
	public void setOpen_circuit_03(double open_circuit_03) {
		this.open_circuit_03 = open_circuit_03;
	}
	public double getOpen_circuit_04() {
		return open_circuit_04;
	}
	public void setOpen_circuit_04(double open_circuit_04) {
		this.open_circuit_04 = open_circuit_04;
	}
	public double getOpen_circuit_05() {
		return open_circuit_05;
	}
	public void setOpen_circuit_05(double open_circuit_05) {
		this.open_circuit_05 = open_circuit_05;
	}
	public double getOpen_circuit_06() {
		return open_circuit_06;
	}
	public void setOpen_circuit_06(double open_circuit_06) {
		this.open_circuit_06 = open_circuit_06;
	}
	public double getOpen_circuit_07() {
		return open_circuit_07;
	}
	public void setOpen_circuit_07(double open_circuit_07) {
		this.open_circuit_07 = open_circuit_07;
	}
	public double getOpen_circuit_08() {
		return open_circuit_08;
	}
	public void setOpen_circuit_08(double open_circuit_08) {
		this.open_circuit_08 = open_circuit_08;
	}
	public double getOpen_circuit_09() {
		return open_circuit_09;
	}
	public void setOpen_circuit_09(double open_circuit_09) {
		this.open_circuit_09 = open_circuit_09;
	}
	public double getOpen_circuit_10() {
		return open_circuit_10;
	}
	public void setOpen_circuit_10(double open_circuit_10) {
		this.open_circuit_10 = open_circuit_10;
	}
	public double getOpen_circuit_11() {
		return open_circuit_11;
	}
	public void setOpen_circuit_11(double open_circuit_11) {
		this.open_circuit_11 = open_circuit_11;
	}
	public double getOpen_circuit_12() {
		return open_circuit_12;
	}
	public void setOpen_circuit_12(double open_circuit_12) {
		this.open_circuit_12 = open_circuit_12;
	}
	public double getOpen_circuit_13() {
		return open_circuit_13;
	}
	public void setOpen_circuit_13(double open_circuit_13) {
		this.open_circuit_13 = open_circuit_13;
	}
	public double getOpen_circuit_14() {
		return open_circuit_14;
	}
	public void setOpen_circuit_14(double open_circuit_14) {
		this.open_circuit_14 = open_circuit_14;
	}
	public double getOpen_circuit_15() {
		return open_circuit_15;
	}
	public void setOpen_circuit_15(double open_circuit_15) {
		this.open_circuit_15 = open_circuit_15;
	}
	public double getOpen_circuit_16() {
		return open_circuit_16;
	}
	public void setOpen_circuit_16(double open_circuit_16) {
		this.open_circuit_16 = open_circuit_16;
	}
	public double getOpen_circuit_17() {
		return open_circuit_17;
	}
	public void setOpen_circuit_17(double open_circuit_17) {
		this.open_circuit_17 = open_circuit_17;
	}
	public double getOpen_circuit_18() {
		return open_circuit_18;
	}
	public void setOpen_circuit_18(double open_circuit_18) {
		this.open_circuit_18 = open_circuit_18;
	}
	public double getOpen_circuit_19() {
		return open_circuit_19;
	}
	public void setOpen_circuit_19(double open_circuit_19) {
		this.open_circuit_19 = open_circuit_19;
	}
	public double getOpen_circuit_20() {
		return open_circuit_20;
	}
	public void setOpen_circuit_20(double open_circuit_20) {
		this.open_circuit_20 = open_circuit_20;
	}
	public double getOpen_circuit_21() {
		return open_circuit_21;
	}
	public void setOpen_circuit_21(double open_circuit_21) {
		this.open_circuit_21 = open_circuit_21;
	}
	public double getOpen_circuit_22() {
		return open_circuit_22;
	}
	public void setOpen_circuit_22(double open_circuit_22) {
		this.open_circuit_22 = open_circuit_22;
	}
	public double getOpen_circuit_23() {
		return open_circuit_23;
	}
	public void setOpen_circuit_23(double open_circuit_23) {
		this.open_circuit_23 = open_circuit_23;
	}
	public double getOpen_circuit_24() {
		return open_circuit_24;
	}
	public void setOpen_circuit_24(double open_circuit_24) {
		this.open_circuit_24 = open_circuit_24;
	}
	public double getPower_01() {
		return power_01;
	}
	public void setPower_01(double power_01) {
		this.power_01 = power_01;
	}
	public double getPower_02() {
		return power_02;
	}
	public void setPower_02(double power_02) {
		this.power_02 = power_02;
	}
	public double getPower_03() {
		return power_03;
	}
	public void setPower_03(double power_03) {
		this.power_03 = power_03;
	}
	public double getPower_04() {
		return power_04;
	}
	public void setPower_04(double power_04) {
		this.power_04 = power_04;
	}
	public double getPower_05() {
		return power_05;
	}
	public void setPower_05(double power_05) {
		this.power_05 = power_05;
	}
	public double getPower_06() {
		return power_06;
	}
	public void setPower_06(double power_06) {
		this.power_06 = power_06;
	}
	public double getPower_07() {
		return power_07;
	}
	public void setPower_07(double power_07) {
		this.power_07 = power_07;
	}
	public double getPower_08() {
		return power_08;
	}
	public void setPower_08(double power_08) {
		this.power_08 = power_08;
	}
	public double getPower_09() {
		return power_09;
	}
	public void setPower_09(double power_09) {
		this.power_09 = power_09;
	}
	public double getPower_10() {
		return power_10;
	}
	public void setPower_10(double power_10) {
		this.power_10 = power_10;
	}
	public double getPower_11() {
		return power_11;
	}
	public void setPower_11(double power_11) {
		this.power_11 = power_11;
	}
	public double getPower_12() {
		return power_12;
	}
	public void setPower_12(double power_12) {
		this.power_12 = power_12;
	}
	public double getPower_13() {
		return power_13;
	}
	public void setPower_13(double power_13) {
		this.power_13 = power_13;
	}
	public double getPower_14() {
		return power_14;
	}
	public void setPower_14(double power_14) {
		this.power_14 = power_14;
	}
	public double getPower_15() {
		return power_15;
	}
	public void setPower_15(double power_15) {
		this.power_15 = power_15;
	}
	public double getPower_16() {
		return power_16;
	}
	public void setPower_16(double power_16) {
		this.power_16 = power_16;
	}
	public double getPower_17() {
		return power_17;
	}
	public void setPower_17(double power_17) {
		this.power_17 = power_17;
	}
	public double getPower_18() {
		return power_18;
	}
	public void setPower_18(double power_18) {
		this.power_18 = power_18;
	}
	public double getPower_19() {
		return power_19;
	}
	public void setPower_19(double power_19) {
		this.power_19 = power_19;
	}
	public double getPower_20() {
		return power_20;
	}
	public void setPower_20(double power_20) {
		this.power_20 = power_20;
	}
	public double getPower_21() {
		return power_21;
	}
	public void setPower_21(double power_21) {
		this.power_21 = power_21;
	}
	public double getPower_22() {
		return power_22;
	}
	public void setPower_22(double power_22) {
		this.power_22 = power_22;
	}
	public double getPower_23() {
		return power_23;
	}
	public void setPower_23(double power_23) {
		this.power_23 = power_23;
	}
	public double getPower_24() {
		return power_24;
	}
	public void setPower_24(double power_24) {
		this.power_24 = power_24;
	}
	public double getReverse_current_01() {
		return reverse_current_01;
	}
	public void setReverse_current_01(double reverse_current_01) {
		this.reverse_current_01 = reverse_current_01;
	}
	public double getReverse_current_02() {
		return reverse_current_02;
	}
	public void setReverse_current_02(double reverse_current_02) {
		this.reverse_current_02 = reverse_current_02;
	}
	public double getReverse_current_03() {
		return reverse_current_03;
	}
	public void setReverse_current_03(double reverse_current_03) {
		this.reverse_current_03 = reverse_current_03;
	}
	public double getReverse_current_04() {
		return reverse_current_04;
	}
	public void setReverse_current_04(double reverse_current_04) {
		this.reverse_current_04 = reverse_current_04;
	}
	public double getReverse_current_05() {
		return reverse_current_05;
	}
	public void setReverse_current_05(double reverse_current_05) {
		this.reverse_current_05 = reverse_current_05;
	}
	public double getReverse_current_06() {
		return reverse_current_06;
	}
	public void setReverse_current_06(double reverse_current_06) {
		this.reverse_current_06 = reverse_current_06;
	}
	public double getReverse_current_07() {
		return reverse_current_07;
	}
	public void setReverse_current_07(double reverse_current_07) {
		this.reverse_current_07 = reverse_current_07;
	}
	public double getReverse_current_08() {
		return reverse_current_08;
	}
	public void setReverse_current_08(double reverse_current_08) {
		this.reverse_current_08 = reverse_current_08;
	}
	public double getReverse_current_09() {
		return reverse_current_09;
	}
	public void setReverse_current_09(double reverse_current_09) {
		this.reverse_current_09 = reverse_current_09;
	}
	public double getReverse_current_10() {
		return reverse_current_10;
	}
	public void setReverse_current_10(double reverse_current_10) {
		this.reverse_current_10 = reverse_current_10;
	}
	public double getReverse_current_11() {
		return reverse_current_11;
	}
	public void setReverse_current_11(double reverse_current_11) {
		this.reverse_current_11 = reverse_current_11;
	}
	public double getReverse_current_12() {
		return reverse_current_12;
	}
	public void setReverse_current_12(double reverse_current_12) {
		this.reverse_current_12 = reverse_current_12;
	}
	public double getReverse_current_13() {
		return reverse_current_13;
	}
	public void setReverse_current_13(double reverse_current_13) {
		this.reverse_current_13 = reverse_current_13;
	}
	public double getReverse_current_14() {
		return reverse_current_14;
	}
	public void setReverse_current_14(double reverse_current_14) {
		this.reverse_current_14 = reverse_current_14;
	}
	public double getReverse_current_15() {
		return reverse_current_15;
	}
	public void setReverse_current_15(double reverse_current_15) {
		this.reverse_current_15 = reverse_current_15;
	}
	public double getReverse_current_16() {
		return reverse_current_16;
	}
	public void setReverse_current_16(double reverse_current_16) {
		this.reverse_current_16 = reverse_current_16;
	}
	public double getReverse_current_17() {
		return reverse_current_17;
	}
	public void setReverse_current_17(double reverse_current_17) {
		this.reverse_current_17 = reverse_current_17;
	}
	public double getReverse_current_18() {
		return reverse_current_18;
	}
	public void setReverse_current_18(double reverse_current_18) {
		this.reverse_current_18 = reverse_current_18;
	}
	public double getReverse_current_19() {
		return reverse_current_19;
	}
	public void setReverse_current_19(double reverse_current_19) {
		this.reverse_current_19 = reverse_current_19;
	}
	public double getReverse_current_20() {
		return reverse_current_20;
	}
	public void setReverse_current_20(double reverse_current_20) {
		this.reverse_current_20 = reverse_current_20;
	}
	public double getReverse_current_21() {
		return reverse_current_21;
	}
	public void setReverse_current_21(double reverse_current_21) {
		this.reverse_current_21 = reverse_current_21;
	}
	public double getReverse_current_22() {
		return reverse_current_22;
	}
	public void setReverse_current_22(double reverse_current_22) {
		this.reverse_current_22 = reverse_current_22;
	}
	public double getReverse_current_23() {
		return reverse_current_23;
	}
	public void setReverse_current_23(double reverse_current_23) {
		this.reverse_current_23 = reverse_current_23;
	}
	public double getReverse_current_24() {
		return reverse_current_24;
	}
	public void setReverse_current_24(double reverse_current_24) {
		this.reverse_current_24 = reverse_current_24;
	}
	public double getShort_circuit_01() {
		return short_circuit_01;
	}
	public void setShort_circuit_01(double short_circuit_01) {
		this.short_circuit_01 = short_circuit_01;
	}
	public double getShort_circuit_02() {
		return short_circuit_02;
	}
	public void setShort_circuit_02(double short_circuit_02) {
		this.short_circuit_02 = short_circuit_02;
	}
	public double getShort_circuit_03() {
		return short_circuit_03;
	}
	public void setShort_circuit_03(double short_circuit_03) {
		this.short_circuit_03 = short_circuit_03;
	}
	public double getShort_circuit_04() {
		return short_circuit_04;
	}
	public void setShort_circuit_04(double short_circuit_04) {
		this.short_circuit_04 = short_circuit_04;
	}
	public double getShort_circuit_05() {
		return short_circuit_05;
	}
	public void setShort_circuit_05(double short_circuit_05) {
		this.short_circuit_05 = short_circuit_05;
	}
	public double getShort_circuit_06() {
		return short_circuit_06;
	}
	public void setShort_circuit_06(double short_circuit_06) {
		this.short_circuit_06 = short_circuit_06;
	}
	public double getShort_circuit_07() {
		return short_circuit_07;
	}
	public void setShort_circuit_07(double short_circuit_07) {
		this.short_circuit_07 = short_circuit_07;
	}
	public double getShort_circuit_08() {
		return short_circuit_08;
	}
	public void setShort_circuit_08(double short_circuit_08) {
		this.short_circuit_08 = short_circuit_08;
	}
	public double getShort_circuit_09() {
		return short_circuit_09;
	}
	public void setShort_circuit_09(double short_circuit_09) {
		this.short_circuit_09 = short_circuit_09;
	}
	public double getShort_circuit_10() {
		return short_circuit_10;
	}
	public void setShort_circuit_10(double short_circuit_10) {
		this.short_circuit_10 = short_circuit_10;
	}
	public double getShort_circuit_11() {
		return short_circuit_11;
	}
	public void setShort_circuit_11(double short_circuit_11) {
		this.short_circuit_11 = short_circuit_11;
	}
	public double getShort_circuit_12() {
		return short_circuit_12;
	}
	public void setShort_circuit_12(double short_circuit_12) {
		this.short_circuit_12 = short_circuit_12;
	}
	public double getShort_circuit_13() {
		return short_circuit_13;
	}
	public void setShort_circuit_13(double short_circuit_13) {
		this.short_circuit_13 = short_circuit_13;
	}
	public double getShort_circuit_14() {
		return short_circuit_14;
	}
	public void setShort_circuit_14(double short_circuit_14) {
		this.short_circuit_14 = short_circuit_14;
	}
	public double getShort_circuit_15() {
		return short_circuit_15;
	}
	public void setShort_circuit_15(double short_circuit_15) {
		this.short_circuit_15 = short_circuit_15;
	}
	public double getShort_circuit_16() {
		return short_circuit_16;
	}
	public void setShort_circuit_16(double short_circuit_16) {
		this.short_circuit_16 = short_circuit_16;
	}
	public double getShort_circuit_17() {
		return short_circuit_17;
	}
	public void setShort_circuit_17(double short_circuit_17) {
		this.short_circuit_17 = short_circuit_17;
	}
	public double getShort_circuit_18() {
		return short_circuit_18;
	}
	public void setShort_circuit_18(double short_circuit_18) {
		this.short_circuit_18 = short_circuit_18;
	}
	public double getShort_circuit_19() {
		return short_circuit_19;
	}
	public void setShort_circuit_19(double short_circuit_19) {
		this.short_circuit_19 = short_circuit_19;
	}
	public double getShort_circuit_20() {
		return short_circuit_20;
	}
	public void setShort_circuit_20(double short_circuit_20) {
		this.short_circuit_20 = short_circuit_20;
	}
	public double getShort_circuit_21() {
		return short_circuit_21;
	}
	public void setShort_circuit_21(double short_circuit_21) {
		this.short_circuit_21 = short_circuit_21;
	}
	public double getShort_circuit_22() {
		return short_circuit_22;
	}
	public void setShort_circuit_22(double short_circuit_22) {
		this.short_circuit_22 = short_circuit_22;
	}
	public double getShort_circuit_23() {
		return short_circuit_23;
	}
	public void setShort_circuit_23(double short_circuit_23) {
		this.short_circuit_23 = short_circuit_23;
	}
	public double getShort_circuit_24() {
		return short_circuit_24;
	}
	public void setShort_circuit_24(double short_circuit_24) {
		this.short_circuit_24 = short_circuit_24;
	}
	public double getStat_fuse_blow() {
		return stat_fuse_blow;
	}
	public void setStat_fuse_blow(double stat_fuse_blow) {
		this.stat_fuse_blow = stat_fuse_blow;
	}
	public double getStat_hi_current() {
		return stat_hi_current;
	}
	public void setStat_hi_current(double stat_hi_current) {
		this.stat_hi_current = stat_hi_current;
	}
	public double getStat_hi_dc_volt() {
		return stat_hi_dc_volt;
	}
	public void setStat_hi_dc_volt(double stat_hi_dc_volt) {
		this.stat_hi_dc_volt = stat_hi_dc_volt;
	}
	public double getStat_hi_temp() {
		return stat_hi_temp;
	}
	public void setStat_hi_temp(double stat_hi_temp) {
		this.stat_hi_temp = stat_hi_temp;
	}
	public double getStat_low_current() {
		return stat_low_current;
	}
	public void setStat_low_current(double stat_low_current) {
		this.stat_low_current = stat_low_current;
	}
	public double getStat_open_circuit() {
		return stat_open_circuit;
	}
	public void setStat_open_circuit(double stat_open_circuit) {
		this.stat_open_circuit = stat_open_circuit;
	}
	public double getStat_rev_current() {
		return stat_rev_current;
	}
	public void setStat_rev_current(double stat_rev_current) {
		this.stat_rev_current = stat_rev_current;
	}
	public double getStat_self_test_trip() {
		return stat_self_test_trip;
	}
	public void setStat_self_test_trip(double stat_self_test_trip) {
		this.stat_self_test_trip = stat_self_test_trip;
	}
	public double getStat_short_circuit() {
		return stat_short_circuit;
	}
	public void setStat_short_circuit(double stat_short_circuit) {
		this.stat_short_circuit = stat_short_circuit;
	}
	public double getStat_shunt_trip_command() {
		return stat_shunt_trip_command;
	}
	public void setStat_shunt_trip_command(double stat_shunt_trip_command) {
		this.stat_shunt_trip_command = stat_shunt_trip_command;
	}
	public double getStat_spd_fault() {
		return stat_spd_fault;
	}
	public void setStat_spd_fault(double stat_spd_fault) {
		this.stat_spd_fault = stat_spd_fault;
	}
	public double getStat_switch_trip() {
		return stat_switch_trip;
	}
	public void setStat_switch_trip(double stat_switch_trip) {
		this.stat_switch_trip = stat_switch_trip;
	}
	public double getStat_switch_trip_enable() {
		return stat_switch_trip_enable;
	}
	public void setStat_switch_trip_enable(double stat_switch_trip_enable) {
		this.stat_switch_trip_enable = stat_switch_trip_enable;
	}
	public double getStat_trip() {
		return stat_trip;
	}
	public void setStat_trip(double stat_trip) {
		this.stat_trip = stat_trip;
	}
	public double getTemp() {
		return temp;
	}
	public void setTemp(double temp) {
		this.temp = temp;
	}
	public double getTotal_yield() {
		return total_yield;
	}
	public void setTotal_yield(double total_yield) {
		this.total_yield = total_yield;
	}
	public double getWork_state() {
		return work_state;
	}
	public void setWork_state(double work_state) {
		this.work_state = work_state;
	}
	
	
	
	
}
