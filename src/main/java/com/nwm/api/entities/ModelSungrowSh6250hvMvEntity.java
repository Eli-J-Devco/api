/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSungrowSh6250hvMvEntity extends ModelBaseEntity {
	
	private double active_power;
	private double amp_phase_a;
	private double amp_phase_b;
	private double amp_phase_c;
	private double apparent_power;
	private double co2_emission;
	private double day_yield;
	private double dc_current_input_1;
	private double dc_current_input_2;
	private double dc_power_input;
	private double dc_voltage_input_1;
	private double dc_voltage_input_2;
	private double frequency;
	private double insulation_neg_mod_1;
	private double insulation_neg_mod_2;
	private double insulation_pos_mod_1;
	private double insulation_pos_mod_2;
	private double operating_time;
	private double operation_stat;
	private double power_factor;
	private double reactive_power;
	private double total_yield;
	private double transformer_oil_temp;
	private double volt_line_ab;
	private double volt_line_bc;
	private double volt_line_ca;
	private double m1_alarm_state_accircuitbreakerabnormal;
	private double m1_alarm_state_acspdalarm;
	private double m1_alarm_state_antipidpowersupplyabnormal;
	private double m1_alarm_state_bypasscircuitbreakerabnormal;
	private double m1_alarm_state_bypassfuseabnormal;
	private double m1_alarm_state_contactorcontactabnormal;
	private double m1_alarm_state_ctunbalance;
	private double m1_alarm_state_dcbypassforwardovercurrentalarm;
	private double m1_alarm_state_dcbypassreserveovercurrentalarm;
	private double m1_alarm_state_dcfuseabnormal;
	private double m1_alarm_state_dcsensorabnormal;
	private double m1_alarm_state_dcspdalarm;
	private double m1_alarm_state_dcswitchabnormal;
	private double m1_alarm_state_dspmdccommunicationabnormal;
	private double m1_alarm_state_energymetercommunicationabnormal;
	private double m1_alarm_state_externalpowersupplyabnormal;
	private double m1_alarm_state_fan2anomaly;
	private double m1_alarm_state_fanabnormal;
	private double m1_alarm_state_frequencydeviationactivepowerregulation;
	private double m1_alarm_state_gfrtoperation;
	private double m1_alarm_state_groundfuseabnormal;
	private double m1_alarm_state_insulationboardcommunicationanomaly;
	private double m1_alarm_state_lowinsulationresistance;
	private double m1_alarm_state_temperatureabnormalalarm;
	private double m1_alarm_state_temperatureandhumidityboardcommerror;
	private double m1_alarm_state_tributaryboardcommunicationerror;
	private double m1_alarm_state_voltagedeviationreactivepowerregulation;
	private double m1_fault_state_1_acleakagecurrentprotection;
	private double m1_fault_state_1_acovercurrent;
	private double m1_fault_state_1_acovervoltage;
	private double m1_fault_state_1_acundervoltage;
	private double m1_fault_state_1_busovervoltage;
	private double m1_fault_state_1_busundervoltage;
	private double m1_fault_state_1_contactorfault;
	private double m1_fault_state_1_dcfusefault;
	private double m1_fault_state_1_dcleakagecurrentprotection;
	private double m1_fault_state_1_dcovercurrent;
	private double m1_fault_state_1_dcovervoltage;
	private double m1_fault_state_1_dcundervoltage;
	private double m1_fault_state_1_detectionfusefault;
	private double m1_fault_state_1_fanfault;
	private double m1_fault_state_1_frequencyabnormal;
	private double m1_fault_state_1_gfdiprotection;
	private double m1_fault_state_1_groundingfault;
	private double m1_fault_state_1_hardwarefault;
	private double m1_fault_state_1_inverterovervoltage;
	private double m1_fault_state_1_islandingprotection;
	private double m1_fault_state_1_moduleovertemperature;
	private double m1_fault_state_1_overfrequency;
	private double m1_fault_state_1_overloadprotection;
	private double m1_fault_state_1_pdpprotection;
	private double m1_fault_state_1_reactorovertemperature;
	private double m1_fault_state_1_sensorfailure;
	private double m1_fault_state_1_temperatureabnormal;
	private double m1_fault_state_1_transformerovertemperature;
	private double m1_fault_state_1_underfrequency;
	private double m1_fault_state_2_accurrentunbalance;
	private double m1_fault_state_2_acspdfault;
	private double m1_fault_state_2_dcspdfault;
	private double m1_fault_state_2_pvpolarityreversed;
	private double m1_fault_state_2_accabinetovertemperature;
	private double m1_fault_state_2_acfusefault;
	private double m1_fault_state_2_acswitchdisconnection;
	private double m1_fault_state_2_acswitchfault;
	private double m1_fault_state_2_backuppowersupplyabnormal;
	private double m1_fault_state_2_buffercontactorfault;
	private double m1_fault_state_2_carriersyncfault;
	private double m1_fault_state_2_controlcabinettemperatureabnormal;
	private double m1_fault_state_2_controlpowersupplyabnormal;
	private double m1_fault_state_2_currentunbalance2;
	private double m1_fault_state_2_currentunbalance3;
	private double m1_fault_state_2_dccabinetovertemperature;
	private double m1_fault_state_2_dcfusegroundingfault;
	private double m1_fault_state_2_dcinjectionfault;
	private double m1_fault_state_2_dcswitchfault;
	private double m1_fault_state_2_dcvoltagesamplingfault;
	private double m1_fault_state_2_devicecoderepeatfault;
	private double m1_fault_state_2_driveboardfault;
	private double m1_fault_state_2_fan2fault;
	private double m1_fault_state_2_gridvoltageunbalance;
	private double m1_fault_state_2_insulationimpedance;
	private double m1_fault_state_2_neutralpointpotentialshift;
	private double m1_fault_state_2_paralleloperation;
	private double m1_fault_state_2_samplingfault;
	private double m1_fault_state_2_softstartfault;
	private double m1_node_state_1_accircuitbreakerstate;
	private double m1_node_state_1_acfusestatus;
	private double m1_node_state_1_acmaincontactorstate;
	private double m1_node_state_1_acbfaultstate;
	private double m1_node_state_1_acbremotelocal;
	private double m1_node_state_2_dcswitchstate1;
	private double m1_node_state_2_dcswitchstate2;
	private double m1_node_state_2_dcswitchstate3;
	private double m1_node_state_2_dcswitchstate4;
	private double m1_node_state_2_spdnodestate;
	private double m1_work_state_alarmrunning;
	private double m1_work_state_antipidrunning;
	private double m1_work_state_deratingrunning;
	private double m1_work_state_emergencystop;
	private double m1_work_state_faultstop;
	private double m1_work_state_initialstandby;
	private double m1_work_state_iodspcommunicationabnormal;
	private double m1_work_state_iomdccommunicationabnormal;
	private double m1_work_state_keystop;
	private double m1_work_state_running;
	private double m1_work_state_standby;
	private double m1_work_state_starting;
	private double m1_work_state_stopped;
	private double m1_work_state_stopping;
	private double m1_work_state_totalsignalbitofrunningstate;
	private double m1_work_state_totalstopbit;
	private double m2_alarm_state_accircuitbreakerabnormal;
	private double m2_alarm_state_acspdalarm;
	private double m2_alarm_state_antipidpowersupplyabnormal;
	private double m2_alarm_state_bypasscircuitbreakerabnormal;
	private double m2_alarm_state_bypassfuseabnormal;
	private double m2_alarm_state_contactorcontactabnormal;
	private double m2_alarm_state_ctunbalance;
	private double m2_alarm_state_dcbypassforwardovercurrentalarm;
	private double m2_alarm_state_dcbypassreserveovercurrentalarm;
	private double m2_alarm_state_dcfuseabnormal;
	private double m2_alarm_state_dcsensorabnormal;
	private double m2_alarm_state_dcspdalarm;
	private double m2_alarm_state_dcswitchabnormal;
	private double m2_alarm_state_dspmdccommunicationabnormal;
	private double m2_alarm_state_energymetercommunicationabnormal;
	private double m2_alarm_state_externalpowersupplyabnormal;
	private double m2_alarm_state_fan2anomaly;
	private double m2_alarm_state_fanabnormal;
	private double m2_alarm_state_frequencydeviationactivepowerregulation;
	private double m2_alarm_state_gfrtoperation;
	private double m2_alarm_state_groundfuseabnormal;
	private double m2_alarm_state_insulationboardcommunicationanomaly;
	private double m2_alarm_state_lowinsulationresistance;
	private double m2_alarm_state_temperatureabnormalalarm;
	private double m2_alarm_state_temperatureandhumidityboardcommerror;
	private double m2_alarm_state_tributaryboardcommunicationerror;
	private double m2_alarm_state_voltagedeviationreactivepowerregulation;
	private double m2_fault_state_1_acleakagecurrentprotection;
	private double m2_fault_state_1_acovercurrent;
	private double m2_fault_state_1_acovervoltage;
	private double m2_fault_state_1_acundervoltage;
	private double m2_fault_state_1_busovervoltage;
	private double m2_fault_state_1_busundervoltage;
	private double m2_fault_state_1_contactorfault;
	private double m2_fault_state_1_dcfusefault;
	private double m2_fault_state_1_dcleakagecurrentprotection;
	private double m2_fault_state_1_dcovercurrent;
	private double m2_fault_state_1_dcovervoltage;
	private double m2_fault_state_1_dcundervoltage;
	private double m2_fault_state_1_detectionfusefault;
	private double m2_fault_state_1_fanfault;
	private double m2_fault_state_1_frequencyabnormal;
	private double m2_fault_state_1_gfdiprotection;
	private double m2_fault_state_1_groundingfault;
	private double m2_fault_state_1_hardwarefault;
	private double m2_fault_state_1_inverterovervoltage;
	private double m2_fault_state_1_islandingprotection;
	private double m2_fault_state_1_moduleovertemperature;
	private double m2_fault_state_1_overfrequency;
	private double m2_fault_state_1_overloadprotection;
	private double m2_fault_state_1_pdpprotection;
	private double m2_fault_state_1_reactorovertemperature;
	private double m2_fault_state_1_sensorfailure;
	private double m2_fault_state_1_temperatureabnormal;
	private double m2_fault_state_1_transformerovertemperature;
	private double m2_fault_state_1_underfrequency;
	private double m2_fault_state_2_accurrentunbalance;
	private double m2_fault_state_2_acspdfault;
	private double m2_fault_state_2_dcspdfault;
	private double m2_fault_state_2_pvpolarityreversed;
	private double m2_fault_state_2_accabinetovertemperature;
	private double m2_fault_state_2_acfusefault;
	private double m2_fault_state_2_acswitchdisconnection;
	private double m2_fault_state_2_acswitchfault;
	private double m2_fault_state_2_backuppowersupplyabnormal;
	private double m2_fault_state_2_buffercontactorfault;
	private double m2_fault_state_2_carriersyncfault;
	private double m2_fault_state_2_controlcabinettemperatureabnormal;
	private double m2_fault_state_2_controlpowersupplyabnormal;
	private double m2_fault_state_2_currentunbalance2;
	private double m2_fault_state_2_currentunbalance3;
	private double m2_fault_state_2_dccabinetovertemperature;
	private double m2_fault_state_2_dcfusegroundingfault;
	private double m2_fault_state_2_dcinjectionfault;
	private double m2_fault_state_2_dcswitchfault;
	private double m2_fault_state_2_dcvoltagesamplingfault;
	private double m2_fault_state_2_devicecoderepeatfault;
	private double m2_fault_state_2_driveboardfault;
	private double m2_fault_state_2_fan2fault;
	private double m2_fault_state_2_gridvoltageunbalance;
	private double m2_fault_state_2_insulationimpedance;
	private double m2_fault_state_2_neutralpointpotentialshift;
	private double m2_fault_state_2_paralleloperation;
	private double m2_fault_state_2_samplingfault;
	private double m2_fault_state_2_softstartfault;
	private double m2_node_state_1_accircuitbreakerstate;
	private double m2_node_state_1_acfusestatus;
	private double m2_node_state_1_acmaincontactorstate;
	private double m2_node_state_1_acbfaultstate;
	private double m2_node_state_1_acbremotelocal;
	private double m2_node_state_2_dcswitchstate1;
	private double m2_node_state_2_dcswitchstate2;
	private double m2_node_state_2_dcswitchstate3;
	private double m2_node_state_2_dcswitchstate4;
	private double m2_node_state_2_spdnodestate;
	private double m2_work_state_alarmrunning;
	private double m2_work_state_antipidrunning;
	private double m2_work_state_deratingrunning;
	private double m2_work_state_emergencystop;
	private double m2_work_state_faultstop;
	private double m2_work_state_initialstandby;
	private double m2_work_state_iodspcommunicationabnormal;
	private double m2_work_state_iomdccommunicationabnormal;
	private double m2_work_state_keystop;
	private double m2_work_state_running;
	private double m2_work_state_standby;
	private double m2_work_state_starting;
	private double m2_work_state_stopped;
	private double m2_work_state_stopping;
	private double m2_work_state_totalsignalbitofrunningstate;
	private double m2_work_state_totalstopbit;
	private double mv_node_state_external_e_stop;
	private double mv_node_state_gas_relay_alarm;
	private double mv_node_state_gas_relay_trip;
	private double mv_node_state_hv_remote_ctrl;
	private double mv_node_state_hv_remote_ctrl_trip;
	private double mv_node_state_hv_room_smoke;
	private double mv_node_state_low_oil_level_trip;
	private double mv_node_state_mv_ds;
	private double mv_node_state_mv_fuse;
	private double mv_node_state_mv_load_switch;
	private double mv_node_state_mv_load_switch_1;
	private double mv_node_state_mv_load_switch_2;
	private double mv_node_state_mv_vcb;
	private double mv_node_state_oil_temp_alarrm;
	private double mv_node_state_oil_temp_trip;
	private double mv_node_state_pressure_relief_trip;
	private double mv_node_state_sf6_low_press_alarm;
	private double mv_node_state_winding_temp_alarm;
	private double mv_node_state_winding_temp_trip;
	private double work_state_door_open_prot;
	private double work_state_external_emer_stop;
	private double work_state_local_emer_stop;
	private double work_state_mv_fault;
	private double work_state_rem_emer_stop;
	private double work_state_running;
	private double work_state_smoke_prot;
	private double work_state_stopped;
	public double getActive_power() {
		return active_power;
	}
	public void setActive_power(double active_power) {
		this.active_power = active_power;
	}
	public double getAmp_phase_a() {
		return amp_phase_a;
	}
	public void setAmp_phase_a(double amp_phase_a) {
		this.amp_phase_a = amp_phase_a;
	}
	public double getAmp_phase_b() {
		return amp_phase_b;
	}
	public void setAmp_phase_b(double amp_phase_b) {
		this.amp_phase_b = amp_phase_b;
	}
	public double getAmp_phase_c() {
		return amp_phase_c;
	}
	public void setAmp_phase_c(double amp_phase_c) {
		this.amp_phase_c = amp_phase_c;
	}
	public double getApparent_power() {
		return apparent_power;
	}
	public void setApparent_power(double apparent_power) {
		this.apparent_power = apparent_power;
	}
	public double getCo2_emission() {
		return co2_emission;
	}
	public void setCo2_emission(double co2_emission) {
		this.co2_emission = co2_emission;
	}
	public double getDay_yield() {
		return day_yield;
	}
	public void setDay_yield(double day_yield) {
		this.day_yield = day_yield;
	}
	public double getDc_current_input_1() {
		return dc_current_input_1;
	}
	public void setDc_current_input_1(double dc_current_input_1) {
		this.dc_current_input_1 = dc_current_input_1;
	}
	public double getDc_current_input_2() {
		return dc_current_input_2;
	}
	public void setDc_current_input_2(double dc_current_input_2) {
		this.dc_current_input_2 = dc_current_input_2;
	}
	public double getDc_power_input() {
		return dc_power_input;
	}
	public void setDc_power_input(double dc_power_input) {
		this.dc_power_input = dc_power_input;
	}
	public double getDc_voltage_input_1() {
		return dc_voltage_input_1;
	}
	public void setDc_voltage_input_1(double dc_voltage_input_1) {
		this.dc_voltage_input_1 = dc_voltage_input_1;
	}
	public double getDc_voltage_input_2() {
		return dc_voltage_input_2;
	}
	public void setDc_voltage_input_2(double dc_voltage_input_2) {
		this.dc_voltage_input_2 = dc_voltage_input_2;
	}
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public double getInsulation_neg_mod_1() {
		return insulation_neg_mod_1;
	}
	public void setInsulation_neg_mod_1(double insulation_neg_mod_1) {
		this.insulation_neg_mod_1 = insulation_neg_mod_1;
	}
	public double getInsulation_neg_mod_2() {
		return insulation_neg_mod_2;
	}
	public void setInsulation_neg_mod_2(double insulation_neg_mod_2) {
		this.insulation_neg_mod_2 = insulation_neg_mod_2;
	}
	public double getInsulation_pos_mod_1() {
		return insulation_pos_mod_1;
	}
	public void setInsulation_pos_mod_1(double insulation_pos_mod_1) {
		this.insulation_pos_mod_1 = insulation_pos_mod_1;
	}
	public double getInsulation_pos_mod_2() {
		return insulation_pos_mod_2;
	}
	public void setInsulation_pos_mod_2(double insulation_pos_mod_2) {
		this.insulation_pos_mod_2 = insulation_pos_mod_2;
	}
	public double getOperating_time() {
		return operating_time;
	}
	public void setOperating_time(double operating_time) {
		this.operating_time = operating_time;
	}
	public double getOperation_stat() {
		return operation_stat;
	}
	public void setOperation_stat(double operation_stat) {
		this.operation_stat = operation_stat;
	}
	public double getPower_factor() {
		return power_factor;
	}
	public void setPower_factor(double power_factor) {
		this.power_factor = power_factor;
	}
	public double getReactive_power() {
		return reactive_power;
	}
	public void setReactive_power(double reactive_power) {
		this.reactive_power = reactive_power;
	}
	public double getTotal_yield() {
		return total_yield;
	}
	public void setTotal_yield(double total_yield) {
		this.total_yield = total_yield;
	}
	public double getTransformer_oil_temp() {
		return transformer_oil_temp;
	}
	public void setTransformer_oil_temp(double transformer_oil_temp) {
		this.transformer_oil_temp = transformer_oil_temp;
	}
	public double getVolt_line_ab() {
		return volt_line_ab;
	}
	public void setVolt_line_ab(double volt_line_ab) {
		this.volt_line_ab = volt_line_ab;
	}
	public double getVolt_line_bc() {
		return volt_line_bc;
	}
	public void setVolt_line_bc(double volt_line_bc) {
		this.volt_line_bc = volt_line_bc;
	}
	public double getVolt_line_ca() {
		return volt_line_ca;
	}
	public void setVolt_line_ca(double volt_line_ca) {
		this.volt_line_ca = volt_line_ca;
	}
	public double getM1_alarm_state_accircuitbreakerabnormal() {
		return m1_alarm_state_accircuitbreakerabnormal;
	}
	public void setM1_alarm_state_accircuitbreakerabnormal(double m1_alarm_state_accircuitbreakerabnormal) {
		this.m1_alarm_state_accircuitbreakerabnormal = m1_alarm_state_accircuitbreakerabnormal;
	}
	public double getM1_alarm_state_acspdalarm() {
		return m1_alarm_state_acspdalarm;
	}
	public void setM1_alarm_state_acspdalarm(double m1_alarm_state_acspdalarm) {
		this.m1_alarm_state_acspdalarm = m1_alarm_state_acspdalarm;
	}
	public double getM1_alarm_state_antipidpowersupplyabnormal() {
		return m1_alarm_state_antipidpowersupplyabnormal;
	}
	public void setM1_alarm_state_antipidpowersupplyabnormal(double m1_alarm_state_antipidpowersupplyabnormal) {
		this.m1_alarm_state_antipidpowersupplyabnormal = m1_alarm_state_antipidpowersupplyabnormal;
	}
	public double getM1_alarm_state_bypasscircuitbreakerabnormal() {
		return m1_alarm_state_bypasscircuitbreakerabnormal;
	}
	public void setM1_alarm_state_bypasscircuitbreakerabnormal(double m1_alarm_state_bypasscircuitbreakerabnormal) {
		this.m1_alarm_state_bypasscircuitbreakerabnormal = m1_alarm_state_bypasscircuitbreakerabnormal;
	}
	public double getM1_alarm_state_bypassfuseabnormal() {
		return m1_alarm_state_bypassfuseabnormal;
	}
	public void setM1_alarm_state_bypassfuseabnormal(double m1_alarm_state_bypassfuseabnormal) {
		this.m1_alarm_state_bypassfuseabnormal = m1_alarm_state_bypassfuseabnormal;
	}
	public double getM1_alarm_state_contactorcontactabnormal() {
		return m1_alarm_state_contactorcontactabnormal;
	}
	public void setM1_alarm_state_contactorcontactabnormal(double m1_alarm_state_contactorcontactabnormal) {
		this.m1_alarm_state_contactorcontactabnormal = m1_alarm_state_contactorcontactabnormal;
	}
	public double getM1_alarm_state_ctunbalance() {
		return m1_alarm_state_ctunbalance;
	}
	public void setM1_alarm_state_ctunbalance(double m1_alarm_state_ctunbalance) {
		this.m1_alarm_state_ctunbalance = m1_alarm_state_ctunbalance;
	}
	public double getM1_alarm_state_dcbypassforwardovercurrentalarm() {
		return m1_alarm_state_dcbypassforwardovercurrentalarm;
	}
	public void setM1_alarm_state_dcbypassforwardovercurrentalarm(double m1_alarm_state_dcbypassforwardovercurrentalarm) {
		this.m1_alarm_state_dcbypassforwardovercurrentalarm = m1_alarm_state_dcbypassforwardovercurrentalarm;
	}
	public double getM1_alarm_state_dcbypassreserveovercurrentalarm() {
		return m1_alarm_state_dcbypassreserveovercurrentalarm;
	}
	public void setM1_alarm_state_dcbypassreserveovercurrentalarm(double m1_alarm_state_dcbypassreserveovercurrentalarm) {
		this.m1_alarm_state_dcbypassreserveovercurrentalarm = m1_alarm_state_dcbypassreserveovercurrentalarm;
	}
	public double getM1_alarm_state_dcfuseabnormal() {
		return m1_alarm_state_dcfuseabnormal;
	}
	public void setM1_alarm_state_dcfuseabnormal(double m1_alarm_state_dcfuseabnormal) {
		this.m1_alarm_state_dcfuseabnormal = m1_alarm_state_dcfuseabnormal;
	}
	public double getM1_alarm_state_dcsensorabnormal() {
		return m1_alarm_state_dcsensorabnormal;
	}
	public void setM1_alarm_state_dcsensorabnormal(double m1_alarm_state_dcsensorabnormal) {
		this.m1_alarm_state_dcsensorabnormal = m1_alarm_state_dcsensorabnormal;
	}
	public double getM1_alarm_state_dcspdalarm() {
		return m1_alarm_state_dcspdalarm;
	}
	public void setM1_alarm_state_dcspdalarm(double m1_alarm_state_dcspdalarm) {
		this.m1_alarm_state_dcspdalarm = m1_alarm_state_dcspdalarm;
	}
	public double getM1_alarm_state_dcswitchabnormal() {
		return m1_alarm_state_dcswitchabnormal;
	}
	public void setM1_alarm_state_dcswitchabnormal(double m1_alarm_state_dcswitchabnormal) {
		this.m1_alarm_state_dcswitchabnormal = m1_alarm_state_dcswitchabnormal;
	}
	public double getM1_alarm_state_dspmdccommunicationabnormal() {
		return m1_alarm_state_dspmdccommunicationabnormal;
	}
	public void setM1_alarm_state_dspmdccommunicationabnormal(double m1_alarm_state_dspmdccommunicationabnormal) {
		this.m1_alarm_state_dspmdccommunicationabnormal = m1_alarm_state_dspmdccommunicationabnormal;
	}
	public double getM1_alarm_state_energymetercommunicationabnormal() {
		return m1_alarm_state_energymetercommunicationabnormal;
	}
	public void setM1_alarm_state_energymetercommunicationabnormal(double m1_alarm_state_energymetercommunicationabnormal) {
		this.m1_alarm_state_energymetercommunicationabnormal = m1_alarm_state_energymetercommunicationabnormal;
	}
	public double getM1_alarm_state_externalpowersupplyabnormal() {
		return m1_alarm_state_externalpowersupplyabnormal;
	}
	public void setM1_alarm_state_externalpowersupplyabnormal(double m1_alarm_state_externalpowersupplyabnormal) {
		this.m1_alarm_state_externalpowersupplyabnormal = m1_alarm_state_externalpowersupplyabnormal;
	}
	public double getM1_alarm_state_fan2anomaly() {
		return m1_alarm_state_fan2anomaly;
	}
	public void setM1_alarm_state_fan2anomaly(double m1_alarm_state_fan2anomaly) {
		this.m1_alarm_state_fan2anomaly = m1_alarm_state_fan2anomaly;
	}
	public double getM1_alarm_state_fanabnormal() {
		return m1_alarm_state_fanabnormal;
	}
	public void setM1_alarm_state_fanabnormal(double m1_alarm_state_fanabnormal) {
		this.m1_alarm_state_fanabnormal = m1_alarm_state_fanabnormal;
	}
	public double getM1_alarm_state_frequencydeviationactivepowerregulation() {
		return m1_alarm_state_frequencydeviationactivepowerregulation;
	}
	public void setM1_alarm_state_frequencydeviationactivepowerregulation(
			double m1_alarm_state_frequencydeviationactivepowerregulation) {
		this.m1_alarm_state_frequencydeviationactivepowerregulation = m1_alarm_state_frequencydeviationactivepowerregulation;
	}
	public double getM1_alarm_state_gfrtoperation() {
		return m1_alarm_state_gfrtoperation;
	}
	public void setM1_alarm_state_gfrtoperation(double m1_alarm_state_gfrtoperation) {
		this.m1_alarm_state_gfrtoperation = m1_alarm_state_gfrtoperation;
	}
	public double getM1_alarm_state_groundfuseabnormal() {
		return m1_alarm_state_groundfuseabnormal;
	}
	public void setM1_alarm_state_groundfuseabnormal(double m1_alarm_state_groundfuseabnormal) {
		this.m1_alarm_state_groundfuseabnormal = m1_alarm_state_groundfuseabnormal;
	}
	public double getM1_alarm_state_insulationboardcommunicationanomaly() {
		return m1_alarm_state_insulationboardcommunicationanomaly;
	}
	public void setM1_alarm_state_insulationboardcommunicationanomaly(
			double m1_alarm_state_insulationboardcommunicationanomaly) {
		this.m1_alarm_state_insulationboardcommunicationanomaly = m1_alarm_state_insulationboardcommunicationanomaly;
	}
	public double getM1_alarm_state_lowinsulationresistance() {
		return m1_alarm_state_lowinsulationresistance;
	}
	public void setM1_alarm_state_lowinsulationresistance(double m1_alarm_state_lowinsulationresistance) {
		this.m1_alarm_state_lowinsulationresistance = m1_alarm_state_lowinsulationresistance;
	}
	public double getM1_alarm_state_temperatureabnormalalarm() {
		return m1_alarm_state_temperatureabnormalalarm;
	}
	public void setM1_alarm_state_temperatureabnormalalarm(double m1_alarm_state_temperatureabnormalalarm) {
		this.m1_alarm_state_temperatureabnormalalarm = m1_alarm_state_temperatureabnormalalarm;
	}
	public double getM1_alarm_state_temperatureandhumidityboardcommerror() {
		return m1_alarm_state_temperatureandhumidityboardcommerror;
	}
	public void setM1_alarm_state_temperatureandhumidityboardcommerror(
			double m1_alarm_state_temperatureandhumidityboardcommerror) {
		this.m1_alarm_state_temperatureandhumidityboardcommerror = m1_alarm_state_temperatureandhumidityboardcommerror;
	}
	public double getM1_alarm_state_tributaryboardcommunicationerror() {
		return m1_alarm_state_tributaryboardcommunicationerror;
	}
	public void setM1_alarm_state_tributaryboardcommunicationerror(double m1_alarm_state_tributaryboardcommunicationerror) {
		this.m1_alarm_state_tributaryboardcommunicationerror = m1_alarm_state_tributaryboardcommunicationerror;
	}
	public double getM1_alarm_state_voltagedeviationreactivepowerregulation() {
		return m1_alarm_state_voltagedeviationreactivepowerregulation;
	}
	public void setM1_alarm_state_voltagedeviationreactivepowerregulation(
			double m1_alarm_state_voltagedeviationreactivepowerregulation) {
		this.m1_alarm_state_voltagedeviationreactivepowerregulation = m1_alarm_state_voltagedeviationreactivepowerregulation;
	}
	public double getM1_fault_state_1_acleakagecurrentprotection() {
		return m1_fault_state_1_acleakagecurrentprotection;
	}
	public void setM1_fault_state_1_acleakagecurrentprotection(double m1_fault_state_1_acleakagecurrentprotection) {
		this.m1_fault_state_1_acleakagecurrentprotection = m1_fault_state_1_acleakagecurrentprotection;
	}
	public double getM1_fault_state_1_acovercurrent() {
		return m1_fault_state_1_acovercurrent;
	}
	public void setM1_fault_state_1_acovercurrent(double m1_fault_state_1_acovercurrent) {
		this.m1_fault_state_1_acovercurrent = m1_fault_state_1_acovercurrent;
	}
	public double getM1_fault_state_1_acovervoltage() {
		return m1_fault_state_1_acovervoltage;
	}
	public void setM1_fault_state_1_acovervoltage(double m1_fault_state_1_acovervoltage) {
		this.m1_fault_state_1_acovervoltage = m1_fault_state_1_acovervoltage;
	}
	public double getM1_fault_state_1_acundervoltage() {
		return m1_fault_state_1_acundervoltage;
	}
	public void setM1_fault_state_1_acundervoltage(double m1_fault_state_1_acundervoltage) {
		this.m1_fault_state_1_acundervoltage = m1_fault_state_1_acundervoltage;
	}
	public double getM1_fault_state_1_busovervoltage() {
		return m1_fault_state_1_busovervoltage;
	}
	public void setM1_fault_state_1_busovervoltage(double m1_fault_state_1_busovervoltage) {
		this.m1_fault_state_1_busovervoltage = m1_fault_state_1_busovervoltage;
	}
	public double getM1_fault_state_1_busundervoltage() {
		return m1_fault_state_1_busundervoltage;
	}
	public void setM1_fault_state_1_busundervoltage(double m1_fault_state_1_busundervoltage) {
		this.m1_fault_state_1_busundervoltage = m1_fault_state_1_busundervoltage;
	}
	public double getM1_fault_state_1_contactorfault() {
		return m1_fault_state_1_contactorfault;
	}
	public void setM1_fault_state_1_contactorfault(double m1_fault_state_1_contactorfault) {
		this.m1_fault_state_1_contactorfault = m1_fault_state_1_contactorfault;
	}
	public double getM1_fault_state_1_dcfusefault() {
		return m1_fault_state_1_dcfusefault;
	}
	public void setM1_fault_state_1_dcfusefault(double m1_fault_state_1_dcfusefault) {
		this.m1_fault_state_1_dcfusefault = m1_fault_state_1_dcfusefault;
	}
	public double getM1_fault_state_1_dcleakagecurrentprotection() {
		return m1_fault_state_1_dcleakagecurrentprotection;
	}
	public void setM1_fault_state_1_dcleakagecurrentprotection(double m1_fault_state_1_dcleakagecurrentprotection) {
		this.m1_fault_state_1_dcleakagecurrentprotection = m1_fault_state_1_dcleakagecurrentprotection;
	}
	public double getM1_fault_state_1_dcovercurrent() {
		return m1_fault_state_1_dcovercurrent;
	}
	public void setM1_fault_state_1_dcovercurrent(double m1_fault_state_1_dcovercurrent) {
		this.m1_fault_state_1_dcovercurrent = m1_fault_state_1_dcovercurrent;
	}
	public double getM1_fault_state_1_dcovervoltage() {
		return m1_fault_state_1_dcovervoltage;
	}
	public void setM1_fault_state_1_dcovervoltage(double m1_fault_state_1_dcovervoltage) {
		this.m1_fault_state_1_dcovervoltage = m1_fault_state_1_dcovervoltage;
	}
	public double getM1_fault_state_1_dcundervoltage() {
		return m1_fault_state_1_dcundervoltage;
	}
	public void setM1_fault_state_1_dcundervoltage(double m1_fault_state_1_dcundervoltage) {
		this.m1_fault_state_1_dcundervoltage = m1_fault_state_1_dcundervoltage;
	}
	public double getM1_fault_state_1_detectionfusefault() {
		return m1_fault_state_1_detectionfusefault;
	}
	public void setM1_fault_state_1_detectionfusefault(double m1_fault_state_1_detectionfusefault) {
		this.m1_fault_state_1_detectionfusefault = m1_fault_state_1_detectionfusefault;
	}
	public double getM1_fault_state_1_fanfault() {
		return m1_fault_state_1_fanfault;
	}
	public void setM1_fault_state_1_fanfault(double m1_fault_state_1_fanfault) {
		this.m1_fault_state_1_fanfault = m1_fault_state_1_fanfault;
	}
	public double getM1_fault_state_1_frequencyabnormal() {
		return m1_fault_state_1_frequencyabnormal;
	}
	public void setM1_fault_state_1_frequencyabnormal(double m1_fault_state_1_frequencyabnormal) {
		this.m1_fault_state_1_frequencyabnormal = m1_fault_state_1_frequencyabnormal;
	}
	public double getM1_fault_state_1_gfdiprotection() {
		return m1_fault_state_1_gfdiprotection;
	}
	public void setM1_fault_state_1_gfdiprotection(double m1_fault_state_1_gfdiprotection) {
		this.m1_fault_state_1_gfdiprotection = m1_fault_state_1_gfdiprotection;
	}
	public double getM1_fault_state_1_groundingfault() {
		return m1_fault_state_1_groundingfault;
	}
	public void setM1_fault_state_1_groundingfault(double m1_fault_state_1_groundingfault) {
		this.m1_fault_state_1_groundingfault = m1_fault_state_1_groundingfault;
	}
	public double getM1_fault_state_1_hardwarefault() {
		return m1_fault_state_1_hardwarefault;
	}
	public void setM1_fault_state_1_hardwarefault(double m1_fault_state_1_hardwarefault) {
		this.m1_fault_state_1_hardwarefault = m1_fault_state_1_hardwarefault;
	}
	public double getM1_fault_state_1_inverterovervoltage() {
		return m1_fault_state_1_inverterovervoltage;
	}
	public void setM1_fault_state_1_inverterovervoltage(double m1_fault_state_1_inverterovervoltage) {
		this.m1_fault_state_1_inverterovervoltage = m1_fault_state_1_inverterovervoltage;
	}
	public double getM1_fault_state_1_islandingprotection() {
		return m1_fault_state_1_islandingprotection;
	}
	public void setM1_fault_state_1_islandingprotection(double m1_fault_state_1_islandingprotection) {
		this.m1_fault_state_1_islandingprotection = m1_fault_state_1_islandingprotection;
	}
	public double getM1_fault_state_1_moduleovertemperature() {
		return m1_fault_state_1_moduleovertemperature;
	}
	public void setM1_fault_state_1_moduleovertemperature(double m1_fault_state_1_moduleovertemperature) {
		this.m1_fault_state_1_moduleovertemperature = m1_fault_state_1_moduleovertemperature;
	}
	public double getM1_fault_state_1_overfrequency() {
		return m1_fault_state_1_overfrequency;
	}
	public void setM1_fault_state_1_overfrequency(double m1_fault_state_1_overfrequency) {
		this.m1_fault_state_1_overfrequency = m1_fault_state_1_overfrequency;
	}
	public double getM1_fault_state_1_overloadprotection() {
		return m1_fault_state_1_overloadprotection;
	}
	public void setM1_fault_state_1_overloadprotection(double m1_fault_state_1_overloadprotection) {
		this.m1_fault_state_1_overloadprotection = m1_fault_state_1_overloadprotection;
	}
	public double getM1_fault_state_1_pdpprotection() {
		return m1_fault_state_1_pdpprotection;
	}
	public void setM1_fault_state_1_pdpprotection(double m1_fault_state_1_pdpprotection) {
		this.m1_fault_state_1_pdpprotection = m1_fault_state_1_pdpprotection;
	}
	public double getM1_fault_state_1_reactorovertemperature() {
		return m1_fault_state_1_reactorovertemperature;
	}
	public void setM1_fault_state_1_reactorovertemperature(double m1_fault_state_1_reactorovertemperature) {
		this.m1_fault_state_1_reactorovertemperature = m1_fault_state_1_reactorovertemperature;
	}
	public double getM1_fault_state_1_sensorfailure() {
		return m1_fault_state_1_sensorfailure;
	}
	public void setM1_fault_state_1_sensorfailure(double m1_fault_state_1_sensorfailure) {
		this.m1_fault_state_1_sensorfailure = m1_fault_state_1_sensorfailure;
	}
	public double getM1_fault_state_1_temperatureabnormal() {
		return m1_fault_state_1_temperatureabnormal;
	}
	public void setM1_fault_state_1_temperatureabnormal(double m1_fault_state_1_temperatureabnormal) {
		this.m1_fault_state_1_temperatureabnormal = m1_fault_state_1_temperatureabnormal;
	}
	public double getM1_fault_state_1_transformerovertemperature() {
		return m1_fault_state_1_transformerovertemperature;
	}
	public void setM1_fault_state_1_transformerovertemperature(double m1_fault_state_1_transformerovertemperature) {
		this.m1_fault_state_1_transformerovertemperature = m1_fault_state_1_transformerovertemperature;
	}
	public double getM1_fault_state_1_underfrequency() {
		return m1_fault_state_1_underfrequency;
	}
	public void setM1_fault_state_1_underfrequency(double m1_fault_state_1_underfrequency) {
		this.m1_fault_state_1_underfrequency = m1_fault_state_1_underfrequency;
	}
	public double getM1_fault_state_2_accurrentunbalance() {
		return m1_fault_state_2_accurrentunbalance;
	}
	public void setM1_fault_state_2_accurrentunbalance(double m1_fault_state_2_accurrentunbalance) {
		this.m1_fault_state_2_accurrentunbalance = m1_fault_state_2_accurrentunbalance;
	}
	public double getM1_fault_state_2_acspdfault() {
		return m1_fault_state_2_acspdfault;
	}
	public void setM1_fault_state_2_acspdfault(double m1_fault_state_2_acspdfault) {
		this.m1_fault_state_2_acspdfault = m1_fault_state_2_acspdfault;
	}
	public double getM1_fault_state_2_dcspdfault() {
		return m1_fault_state_2_dcspdfault;
	}
	public void setM1_fault_state_2_dcspdfault(double m1_fault_state_2_dcspdfault) {
		this.m1_fault_state_2_dcspdfault = m1_fault_state_2_dcspdfault;
	}
	public double getM1_fault_state_2_pvpolarityreversed() {
		return m1_fault_state_2_pvpolarityreversed;
	}
	public void setM1_fault_state_2_pvpolarityreversed(double m1_fault_state_2_pvpolarityreversed) {
		this.m1_fault_state_2_pvpolarityreversed = m1_fault_state_2_pvpolarityreversed;
	}
	public double getM1_fault_state_2_accabinetovertemperature() {
		return m1_fault_state_2_accabinetovertemperature;
	}
	public void setM1_fault_state_2_accabinetovertemperature(double m1_fault_state_2_accabinetovertemperature) {
		this.m1_fault_state_2_accabinetovertemperature = m1_fault_state_2_accabinetovertemperature;
	}
	public double getM1_fault_state_2_acfusefault() {
		return m1_fault_state_2_acfusefault;
	}
	public void setM1_fault_state_2_acfusefault(double m1_fault_state_2_acfusefault) {
		this.m1_fault_state_2_acfusefault = m1_fault_state_2_acfusefault;
	}
	public double getM1_fault_state_2_acswitchdisconnection() {
		return m1_fault_state_2_acswitchdisconnection;
	}
	public void setM1_fault_state_2_acswitchdisconnection(double m1_fault_state_2_acswitchdisconnection) {
		this.m1_fault_state_2_acswitchdisconnection = m1_fault_state_2_acswitchdisconnection;
	}
	public double getM1_fault_state_2_acswitchfault() {
		return m1_fault_state_2_acswitchfault;
	}
	public void setM1_fault_state_2_acswitchfault(double m1_fault_state_2_acswitchfault) {
		this.m1_fault_state_2_acswitchfault = m1_fault_state_2_acswitchfault;
	}
	public double getM1_fault_state_2_backuppowersupplyabnormal() {
		return m1_fault_state_2_backuppowersupplyabnormal;
	}
	public void setM1_fault_state_2_backuppowersupplyabnormal(double m1_fault_state_2_backuppowersupplyabnormal) {
		this.m1_fault_state_2_backuppowersupplyabnormal = m1_fault_state_2_backuppowersupplyabnormal;
	}
	public double getM1_fault_state_2_buffercontactorfault() {
		return m1_fault_state_2_buffercontactorfault;
	}
	public void setM1_fault_state_2_buffercontactorfault(double m1_fault_state_2_buffercontactorfault) {
		this.m1_fault_state_2_buffercontactorfault = m1_fault_state_2_buffercontactorfault;
	}
	public double getM1_fault_state_2_carriersyncfault() {
		return m1_fault_state_2_carriersyncfault;
	}
	public void setM1_fault_state_2_carriersyncfault(double m1_fault_state_2_carriersyncfault) {
		this.m1_fault_state_2_carriersyncfault = m1_fault_state_2_carriersyncfault;
	}
	public double getM1_fault_state_2_controlcabinettemperatureabnormal() {
		return m1_fault_state_2_controlcabinettemperatureabnormal;
	}
	public void setM1_fault_state_2_controlcabinettemperatureabnormal(
			double m1_fault_state_2_controlcabinettemperatureabnormal) {
		this.m1_fault_state_2_controlcabinettemperatureabnormal = m1_fault_state_2_controlcabinettemperatureabnormal;
	}
	public double getM1_fault_state_2_controlpowersupplyabnormal() {
		return m1_fault_state_2_controlpowersupplyabnormal;
	}
	public void setM1_fault_state_2_controlpowersupplyabnormal(double m1_fault_state_2_controlpowersupplyabnormal) {
		this.m1_fault_state_2_controlpowersupplyabnormal = m1_fault_state_2_controlpowersupplyabnormal;
	}
	public double getM1_fault_state_2_currentunbalance2() {
		return m1_fault_state_2_currentunbalance2;
	}
	public void setM1_fault_state_2_currentunbalance2(double m1_fault_state_2_currentunbalance2) {
		this.m1_fault_state_2_currentunbalance2 = m1_fault_state_2_currentunbalance2;
	}
	public double getM1_fault_state_2_currentunbalance3() {
		return m1_fault_state_2_currentunbalance3;
	}
	public void setM1_fault_state_2_currentunbalance3(double m1_fault_state_2_currentunbalance3) {
		this.m1_fault_state_2_currentunbalance3 = m1_fault_state_2_currentunbalance3;
	}
	public double getM1_fault_state_2_dccabinetovertemperature() {
		return m1_fault_state_2_dccabinetovertemperature;
	}
	public void setM1_fault_state_2_dccabinetovertemperature(double m1_fault_state_2_dccabinetovertemperature) {
		this.m1_fault_state_2_dccabinetovertemperature = m1_fault_state_2_dccabinetovertemperature;
	}
	public double getM1_fault_state_2_dcfusegroundingfault() {
		return m1_fault_state_2_dcfusegroundingfault;
	}
	public void setM1_fault_state_2_dcfusegroundingfault(double m1_fault_state_2_dcfusegroundingfault) {
		this.m1_fault_state_2_dcfusegroundingfault = m1_fault_state_2_dcfusegroundingfault;
	}
	public double getM1_fault_state_2_dcinjectionfault() {
		return m1_fault_state_2_dcinjectionfault;
	}
	public void setM1_fault_state_2_dcinjectionfault(double m1_fault_state_2_dcinjectionfault) {
		this.m1_fault_state_2_dcinjectionfault = m1_fault_state_2_dcinjectionfault;
	}
	public double getM1_fault_state_2_dcswitchfault() {
		return m1_fault_state_2_dcswitchfault;
	}
	public void setM1_fault_state_2_dcswitchfault(double m1_fault_state_2_dcswitchfault) {
		this.m1_fault_state_2_dcswitchfault = m1_fault_state_2_dcswitchfault;
	}
	public double getM1_fault_state_2_dcvoltagesamplingfault() {
		return m1_fault_state_2_dcvoltagesamplingfault;
	}
	public void setM1_fault_state_2_dcvoltagesamplingfault(double m1_fault_state_2_dcvoltagesamplingfault) {
		this.m1_fault_state_2_dcvoltagesamplingfault = m1_fault_state_2_dcvoltagesamplingfault;
	}
	public double getM1_fault_state_2_devicecoderepeatfault() {
		return m1_fault_state_2_devicecoderepeatfault;
	}
	public void setM1_fault_state_2_devicecoderepeatfault(double m1_fault_state_2_devicecoderepeatfault) {
		this.m1_fault_state_2_devicecoderepeatfault = m1_fault_state_2_devicecoderepeatfault;
	}
	public double getM1_fault_state_2_driveboardfault() {
		return m1_fault_state_2_driveboardfault;
	}
	public void setM1_fault_state_2_driveboardfault(double m1_fault_state_2_driveboardfault) {
		this.m1_fault_state_2_driveboardfault = m1_fault_state_2_driveboardfault;
	}
	public double getM1_fault_state_2_fan2fault() {
		return m1_fault_state_2_fan2fault;
	}
	public void setM1_fault_state_2_fan2fault(double m1_fault_state_2_fan2fault) {
		this.m1_fault_state_2_fan2fault = m1_fault_state_2_fan2fault;
	}
	public double getM1_fault_state_2_gridvoltageunbalance() {
		return m1_fault_state_2_gridvoltageunbalance;
	}
	public void setM1_fault_state_2_gridvoltageunbalance(double m1_fault_state_2_gridvoltageunbalance) {
		this.m1_fault_state_2_gridvoltageunbalance = m1_fault_state_2_gridvoltageunbalance;
	}
	public double getM1_fault_state_2_insulationimpedance() {
		return m1_fault_state_2_insulationimpedance;
	}
	public void setM1_fault_state_2_insulationimpedance(double m1_fault_state_2_insulationimpedance) {
		this.m1_fault_state_2_insulationimpedance = m1_fault_state_2_insulationimpedance;
	}
	public double getM1_fault_state_2_neutralpointpotentialshift() {
		return m1_fault_state_2_neutralpointpotentialshift;
	}
	public void setM1_fault_state_2_neutralpointpotentialshift(double m1_fault_state_2_neutralpointpotentialshift) {
		this.m1_fault_state_2_neutralpointpotentialshift = m1_fault_state_2_neutralpointpotentialshift;
	}
	public double getM1_fault_state_2_paralleloperation() {
		return m1_fault_state_2_paralleloperation;
	}
	public void setM1_fault_state_2_paralleloperation(double m1_fault_state_2_paralleloperation) {
		this.m1_fault_state_2_paralleloperation = m1_fault_state_2_paralleloperation;
	}
	public double getM1_fault_state_2_samplingfault() {
		return m1_fault_state_2_samplingfault;
	}
	public void setM1_fault_state_2_samplingfault(double m1_fault_state_2_samplingfault) {
		this.m1_fault_state_2_samplingfault = m1_fault_state_2_samplingfault;
	}
	public double getM1_fault_state_2_softstartfault() {
		return m1_fault_state_2_softstartfault;
	}
	public void setM1_fault_state_2_softstartfault(double m1_fault_state_2_softstartfault) {
		this.m1_fault_state_2_softstartfault = m1_fault_state_2_softstartfault;
	}
	public double getM1_node_state_1_accircuitbreakerstate() {
		return m1_node_state_1_accircuitbreakerstate;
	}
	public void setM1_node_state_1_accircuitbreakerstate(double m1_node_state_1_accircuitbreakerstate) {
		this.m1_node_state_1_accircuitbreakerstate = m1_node_state_1_accircuitbreakerstate;
	}
	public double getM1_node_state_1_acfusestatus() {
		return m1_node_state_1_acfusestatus;
	}
	public void setM1_node_state_1_acfusestatus(double m1_node_state_1_acfusestatus) {
		this.m1_node_state_1_acfusestatus = m1_node_state_1_acfusestatus;
	}
	public double getM1_node_state_1_acmaincontactorstate() {
		return m1_node_state_1_acmaincontactorstate;
	}
	public void setM1_node_state_1_acmaincontactorstate(double m1_node_state_1_acmaincontactorstate) {
		this.m1_node_state_1_acmaincontactorstate = m1_node_state_1_acmaincontactorstate;
	}
	public double getM1_node_state_1_acbfaultstate() {
		return m1_node_state_1_acbfaultstate;
	}
	public void setM1_node_state_1_acbfaultstate(double m1_node_state_1_acbfaultstate) {
		this.m1_node_state_1_acbfaultstate = m1_node_state_1_acbfaultstate;
	}
	public double getM1_node_state_1_acbremotelocal() {
		return m1_node_state_1_acbremotelocal;
	}
	public void setM1_node_state_1_acbremotelocal(double m1_node_state_1_acbremotelocal) {
		this.m1_node_state_1_acbremotelocal = m1_node_state_1_acbremotelocal;
	}
	public double getM1_node_state_2_dcswitchstate1() {
		return m1_node_state_2_dcswitchstate1;
	}
	public void setM1_node_state_2_dcswitchstate1(double m1_node_state_2_dcswitchstate1) {
		this.m1_node_state_2_dcswitchstate1 = m1_node_state_2_dcswitchstate1;
	}
	public double getM1_node_state_2_dcswitchstate2() {
		return m1_node_state_2_dcswitchstate2;
	}
	public void setM1_node_state_2_dcswitchstate2(double m1_node_state_2_dcswitchstate2) {
		this.m1_node_state_2_dcswitchstate2 = m1_node_state_2_dcswitchstate2;
	}
	public double getM1_node_state_2_dcswitchstate3() {
		return m1_node_state_2_dcswitchstate3;
	}
	public void setM1_node_state_2_dcswitchstate3(double m1_node_state_2_dcswitchstate3) {
		this.m1_node_state_2_dcswitchstate3 = m1_node_state_2_dcswitchstate3;
	}
	public double getM1_node_state_2_dcswitchstate4() {
		return m1_node_state_2_dcswitchstate4;
	}
	public void setM1_node_state_2_dcswitchstate4(double m1_node_state_2_dcswitchstate4) {
		this.m1_node_state_2_dcswitchstate4 = m1_node_state_2_dcswitchstate4;
	}
	public double getM1_node_state_2_spdnodestate() {
		return m1_node_state_2_spdnodestate;
	}
	public void setM1_node_state_2_spdnodestate(double m1_node_state_2_spdnodestate) {
		this.m1_node_state_2_spdnodestate = m1_node_state_2_spdnodestate;
	}
	public double getM1_work_state_alarmrunning() {
		return m1_work_state_alarmrunning;
	}
	public void setM1_work_state_alarmrunning(double m1_work_state_alarmrunning) {
		this.m1_work_state_alarmrunning = m1_work_state_alarmrunning;
	}
	public double getM1_work_state_antipidrunning() {
		return m1_work_state_antipidrunning;
	}
	public void setM1_work_state_antipidrunning(double m1_work_state_antipidrunning) {
		this.m1_work_state_antipidrunning = m1_work_state_antipidrunning;
	}
	public double getM1_work_state_deratingrunning() {
		return m1_work_state_deratingrunning;
	}
	public void setM1_work_state_deratingrunning(double m1_work_state_deratingrunning) {
		this.m1_work_state_deratingrunning = m1_work_state_deratingrunning;
	}
	public double getM1_work_state_emergencystop() {
		return m1_work_state_emergencystop;
	}
	public void setM1_work_state_emergencystop(double m1_work_state_emergencystop) {
		this.m1_work_state_emergencystop = m1_work_state_emergencystop;
	}
	public double getM1_work_state_faultstop() {
		return m1_work_state_faultstop;
	}
	public void setM1_work_state_faultstop(double m1_work_state_faultstop) {
		this.m1_work_state_faultstop = m1_work_state_faultstop;
	}
	public double getM1_work_state_initialstandby() {
		return m1_work_state_initialstandby;
	}
	public void setM1_work_state_initialstandby(double m1_work_state_initialstandby) {
		this.m1_work_state_initialstandby = m1_work_state_initialstandby;
	}
	public double getM1_work_state_iodspcommunicationabnormal() {
		return m1_work_state_iodspcommunicationabnormal;
	}
	public void setM1_work_state_iodspcommunicationabnormal(double m1_work_state_iodspcommunicationabnormal) {
		this.m1_work_state_iodspcommunicationabnormal = m1_work_state_iodspcommunicationabnormal;
	}
	public double getM1_work_state_iomdccommunicationabnormal() {
		return m1_work_state_iomdccommunicationabnormal;
	}
	public void setM1_work_state_iomdccommunicationabnormal(double m1_work_state_iomdccommunicationabnormal) {
		this.m1_work_state_iomdccommunicationabnormal = m1_work_state_iomdccommunicationabnormal;
	}
	public double getM1_work_state_keystop() {
		return m1_work_state_keystop;
	}
	public void setM1_work_state_keystop(double m1_work_state_keystop) {
		this.m1_work_state_keystop = m1_work_state_keystop;
	}
	public double getM1_work_state_running() {
		return m1_work_state_running;
	}
	public void setM1_work_state_running(double m1_work_state_running) {
		this.m1_work_state_running = m1_work_state_running;
	}
	public double getM1_work_state_standby() {
		return m1_work_state_standby;
	}
	public void setM1_work_state_standby(double m1_work_state_standby) {
		this.m1_work_state_standby = m1_work_state_standby;
	}
	public double getM1_work_state_starting() {
		return m1_work_state_starting;
	}
	public void setM1_work_state_starting(double m1_work_state_starting) {
		this.m1_work_state_starting = m1_work_state_starting;
	}
	public double getM1_work_state_stopped() {
		return m1_work_state_stopped;
	}
	public void setM1_work_state_stopped(double m1_work_state_stopped) {
		this.m1_work_state_stopped = m1_work_state_stopped;
	}
	public double getM1_work_state_stopping() {
		return m1_work_state_stopping;
	}
	public void setM1_work_state_stopping(double m1_work_state_stopping) {
		this.m1_work_state_stopping = m1_work_state_stopping;
	}
	public double getM1_work_state_totalsignalbitofrunningstate() {
		return m1_work_state_totalsignalbitofrunningstate;
	}
	public void setM1_work_state_totalsignalbitofrunningstate(double m1_work_state_totalsignalbitofrunningstate) {
		this.m1_work_state_totalsignalbitofrunningstate = m1_work_state_totalsignalbitofrunningstate;
	}
	public double getM1_work_state_totalstopbit() {
		return m1_work_state_totalstopbit;
	}
	public void setM1_work_state_totalstopbit(double m1_work_state_totalstopbit) {
		this.m1_work_state_totalstopbit = m1_work_state_totalstopbit;
	}
	public double getM2_alarm_state_accircuitbreakerabnormal() {
		return m2_alarm_state_accircuitbreakerabnormal;
	}
	public void setM2_alarm_state_accircuitbreakerabnormal(double m2_alarm_state_accircuitbreakerabnormal) {
		this.m2_alarm_state_accircuitbreakerabnormal = m2_alarm_state_accircuitbreakerabnormal;
	}
	public double getM2_alarm_state_acspdalarm() {
		return m2_alarm_state_acspdalarm;
	}
	public void setM2_alarm_state_acspdalarm(double m2_alarm_state_acspdalarm) {
		this.m2_alarm_state_acspdalarm = m2_alarm_state_acspdalarm;
	}
	public double getM2_alarm_state_antipidpowersupplyabnormal() {
		return m2_alarm_state_antipidpowersupplyabnormal;
	}
	public void setM2_alarm_state_antipidpowersupplyabnormal(double m2_alarm_state_antipidpowersupplyabnormal) {
		this.m2_alarm_state_antipidpowersupplyabnormal = m2_alarm_state_antipidpowersupplyabnormal;
	}
	public double getM2_alarm_state_bypasscircuitbreakerabnormal() {
		return m2_alarm_state_bypasscircuitbreakerabnormal;
	}
	public void setM2_alarm_state_bypasscircuitbreakerabnormal(double m2_alarm_state_bypasscircuitbreakerabnormal) {
		this.m2_alarm_state_bypasscircuitbreakerabnormal = m2_alarm_state_bypasscircuitbreakerabnormal;
	}
	public double getM2_alarm_state_bypassfuseabnormal() {
		return m2_alarm_state_bypassfuseabnormal;
	}
	public void setM2_alarm_state_bypassfuseabnormal(double m2_alarm_state_bypassfuseabnormal) {
		this.m2_alarm_state_bypassfuseabnormal = m2_alarm_state_bypassfuseabnormal;
	}
	public double getM2_alarm_state_contactorcontactabnormal() {
		return m2_alarm_state_contactorcontactabnormal;
	}
	public void setM2_alarm_state_contactorcontactabnormal(double m2_alarm_state_contactorcontactabnormal) {
		this.m2_alarm_state_contactorcontactabnormal = m2_alarm_state_contactorcontactabnormal;
	}
	public double getM2_alarm_state_ctunbalance() {
		return m2_alarm_state_ctunbalance;
	}
	public void setM2_alarm_state_ctunbalance(double m2_alarm_state_ctunbalance) {
		this.m2_alarm_state_ctunbalance = m2_alarm_state_ctunbalance;
	}
	public double getM2_alarm_state_dcbypassforwardovercurrentalarm() {
		return m2_alarm_state_dcbypassforwardovercurrentalarm;
	}
	public void setM2_alarm_state_dcbypassforwardovercurrentalarm(double m2_alarm_state_dcbypassforwardovercurrentalarm) {
		this.m2_alarm_state_dcbypassforwardovercurrentalarm = m2_alarm_state_dcbypassforwardovercurrentalarm;
	}
	public double getM2_alarm_state_dcbypassreserveovercurrentalarm() {
		return m2_alarm_state_dcbypassreserveovercurrentalarm;
	}
	public void setM2_alarm_state_dcbypassreserveovercurrentalarm(double m2_alarm_state_dcbypassreserveovercurrentalarm) {
		this.m2_alarm_state_dcbypassreserveovercurrentalarm = m2_alarm_state_dcbypassreserveovercurrentalarm;
	}
	public double getM2_alarm_state_dcfuseabnormal() {
		return m2_alarm_state_dcfuseabnormal;
	}
	public void setM2_alarm_state_dcfuseabnormal(double m2_alarm_state_dcfuseabnormal) {
		this.m2_alarm_state_dcfuseabnormal = m2_alarm_state_dcfuseabnormal;
	}
	public double getM2_alarm_state_dcsensorabnormal() {
		return m2_alarm_state_dcsensorabnormal;
	}
	public void setM2_alarm_state_dcsensorabnormal(double m2_alarm_state_dcsensorabnormal) {
		this.m2_alarm_state_dcsensorabnormal = m2_alarm_state_dcsensorabnormal;
	}
	public double getM2_alarm_state_dcspdalarm() {
		return m2_alarm_state_dcspdalarm;
	}
	public void setM2_alarm_state_dcspdalarm(double m2_alarm_state_dcspdalarm) {
		this.m2_alarm_state_dcspdalarm = m2_alarm_state_dcspdalarm;
	}
	public double getM2_alarm_state_dcswitchabnormal() {
		return m2_alarm_state_dcswitchabnormal;
	}
	public void setM2_alarm_state_dcswitchabnormal(double m2_alarm_state_dcswitchabnormal) {
		this.m2_alarm_state_dcswitchabnormal = m2_alarm_state_dcswitchabnormal;
	}
	public double getM2_alarm_state_dspmdccommunicationabnormal() {
		return m2_alarm_state_dspmdccommunicationabnormal;
	}
	public void setM2_alarm_state_dspmdccommunicationabnormal(double m2_alarm_state_dspmdccommunicationabnormal) {
		this.m2_alarm_state_dspmdccommunicationabnormal = m2_alarm_state_dspmdccommunicationabnormal;
	}
	public double getM2_alarm_state_energymetercommunicationabnormal() {
		return m2_alarm_state_energymetercommunicationabnormal;
	}
	public void setM2_alarm_state_energymetercommunicationabnormal(double m2_alarm_state_energymetercommunicationabnormal) {
		this.m2_alarm_state_energymetercommunicationabnormal = m2_alarm_state_energymetercommunicationabnormal;
	}
	public double getM2_alarm_state_externalpowersupplyabnormal() {
		return m2_alarm_state_externalpowersupplyabnormal;
	}
	public void setM2_alarm_state_externalpowersupplyabnormal(double m2_alarm_state_externalpowersupplyabnormal) {
		this.m2_alarm_state_externalpowersupplyabnormal = m2_alarm_state_externalpowersupplyabnormal;
	}
	public double getM2_alarm_state_fan2anomaly() {
		return m2_alarm_state_fan2anomaly;
	}
	public void setM2_alarm_state_fan2anomaly(double m2_alarm_state_fan2anomaly) {
		this.m2_alarm_state_fan2anomaly = m2_alarm_state_fan2anomaly;
	}
	public double getM2_alarm_state_fanabnormal() {
		return m2_alarm_state_fanabnormal;
	}
	public void setM2_alarm_state_fanabnormal(double m2_alarm_state_fanabnormal) {
		this.m2_alarm_state_fanabnormal = m2_alarm_state_fanabnormal;
	}
	public double getM2_alarm_state_frequencydeviationactivepowerregulation() {
		return m2_alarm_state_frequencydeviationactivepowerregulation;
	}
	public void setM2_alarm_state_frequencydeviationactivepowerregulation(
			double m2_alarm_state_frequencydeviationactivepowerregulation) {
		this.m2_alarm_state_frequencydeviationactivepowerregulation = m2_alarm_state_frequencydeviationactivepowerregulation;
	}
	public double getM2_alarm_state_gfrtoperation() {
		return m2_alarm_state_gfrtoperation;
	}
	public void setM2_alarm_state_gfrtoperation(double m2_alarm_state_gfrtoperation) {
		this.m2_alarm_state_gfrtoperation = m2_alarm_state_gfrtoperation;
	}
	public double getM2_alarm_state_groundfuseabnormal() {
		return m2_alarm_state_groundfuseabnormal;
	}
	public void setM2_alarm_state_groundfuseabnormal(double m2_alarm_state_groundfuseabnormal) {
		this.m2_alarm_state_groundfuseabnormal = m2_alarm_state_groundfuseabnormal;
	}
	public double getM2_alarm_state_insulationboardcommunicationanomaly() {
		return m2_alarm_state_insulationboardcommunicationanomaly;
	}
	public void setM2_alarm_state_insulationboardcommunicationanomaly(
			double m2_alarm_state_insulationboardcommunicationanomaly) {
		this.m2_alarm_state_insulationboardcommunicationanomaly = m2_alarm_state_insulationboardcommunicationanomaly;
	}
	public double getM2_alarm_state_lowinsulationresistance() {
		return m2_alarm_state_lowinsulationresistance;
	}
	public void setM2_alarm_state_lowinsulationresistance(double m2_alarm_state_lowinsulationresistance) {
		this.m2_alarm_state_lowinsulationresistance = m2_alarm_state_lowinsulationresistance;
	}
	public double getM2_alarm_state_temperatureabnormalalarm() {
		return m2_alarm_state_temperatureabnormalalarm;
	}
	public void setM2_alarm_state_temperatureabnormalalarm(double m2_alarm_state_temperatureabnormalalarm) {
		this.m2_alarm_state_temperatureabnormalalarm = m2_alarm_state_temperatureabnormalalarm;
	}
	public double getM2_alarm_state_temperatureandhumidityboardcommerror() {
		return m2_alarm_state_temperatureandhumidityboardcommerror;
	}
	public void setM2_alarm_state_temperatureandhumidityboardcommerror(
			double m2_alarm_state_temperatureandhumidityboardcommerror) {
		this.m2_alarm_state_temperatureandhumidityboardcommerror = m2_alarm_state_temperatureandhumidityboardcommerror;
	}
	public double getM2_alarm_state_tributaryboardcommunicationerror() {
		return m2_alarm_state_tributaryboardcommunicationerror;
	}
	public void setM2_alarm_state_tributaryboardcommunicationerror(double m2_alarm_state_tributaryboardcommunicationerror) {
		this.m2_alarm_state_tributaryboardcommunicationerror = m2_alarm_state_tributaryboardcommunicationerror;
	}
	public double getM2_alarm_state_voltagedeviationreactivepowerregulation() {
		return m2_alarm_state_voltagedeviationreactivepowerregulation;
	}
	public void setM2_alarm_state_voltagedeviationreactivepowerregulation(
			double m2_alarm_state_voltagedeviationreactivepowerregulation) {
		this.m2_alarm_state_voltagedeviationreactivepowerregulation = m2_alarm_state_voltagedeviationreactivepowerregulation;
	}
	public double getM2_fault_state_1_acleakagecurrentprotection() {
		return m2_fault_state_1_acleakagecurrentprotection;
	}
	public void setM2_fault_state_1_acleakagecurrentprotection(double m2_fault_state_1_acleakagecurrentprotection) {
		this.m2_fault_state_1_acleakagecurrentprotection = m2_fault_state_1_acleakagecurrentprotection;
	}
	public double getM2_fault_state_1_acovercurrent() {
		return m2_fault_state_1_acovercurrent;
	}
	public void setM2_fault_state_1_acovercurrent(double m2_fault_state_1_acovercurrent) {
		this.m2_fault_state_1_acovercurrent = m2_fault_state_1_acovercurrent;
	}
	public double getM2_fault_state_1_acovervoltage() {
		return m2_fault_state_1_acovervoltage;
	}
	public void setM2_fault_state_1_acovervoltage(double m2_fault_state_1_acovervoltage) {
		this.m2_fault_state_1_acovervoltage = m2_fault_state_1_acovervoltage;
	}
	public double getM2_fault_state_1_acundervoltage() {
		return m2_fault_state_1_acundervoltage;
	}
	public void setM2_fault_state_1_acundervoltage(double m2_fault_state_1_acundervoltage) {
		this.m2_fault_state_1_acundervoltage = m2_fault_state_1_acundervoltage;
	}
	public double getM2_fault_state_1_busovervoltage() {
		return m2_fault_state_1_busovervoltage;
	}
	public void setM2_fault_state_1_busovervoltage(double m2_fault_state_1_busovervoltage) {
		this.m2_fault_state_1_busovervoltage = m2_fault_state_1_busovervoltage;
	}
	public double getM2_fault_state_1_busundervoltage() {
		return m2_fault_state_1_busundervoltage;
	}
	public void setM2_fault_state_1_busundervoltage(double m2_fault_state_1_busundervoltage) {
		this.m2_fault_state_1_busundervoltage = m2_fault_state_1_busundervoltage;
	}
	public double getM2_fault_state_1_contactorfault() {
		return m2_fault_state_1_contactorfault;
	}
	public void setM2_fault_state_1_contactorfault(double m2_fault_state_1_contactorfault) {
		this.m2_fault_state_1_contactorfault = m2_fault_state_1_contactorfault;
	}
	public double getM2_fault_state_1_dcfusefault() {
		return m2_fault_state_1_dcfusefault;
	}
	public void setM2_fault_state_1_dcfusefault(double m2_fault_state_1_dcfusefault) {
		this.m2_fault_state_1_dcfusefault = m2_fault_state_1_dcfusefault;
	}
	public double getM2_fault_state_1_dcleakagecurrentprotection() {
		return m2_fault_state_1_dcleakagecurrentprotection;
	}
	public void setM2_fault_state_1_dcleakagecurrentprotection(double m2_fault_state_1_dcleakagecurrentprotection) {
		this.m2_fault_state_1_dcleakagecurrentprotection = m2_fault_state_1_dcleakagecurrentprotection;
	}
	public double getM2_fault_state_1_dcovercurrent() {
		return m2_fault_state_1_dcovercurrent;
	}
	public void setM2_fault_state_1_dcovercurrent(double m2_fault_state_1_dcovercurrent) {
		this.m2_fault_state_1_dcovercurrent = m2_fault_state_1_dcovercurrent;
	}
	public double getM2_fault_state_1_dcovervoltage() {
		return m2_fault_state_1_dcovervoltage;
	}
	public void setM2_fault_state_1_dcovervoltage(double m2_fault_state_1_dcovervoltage) {
		this.m2_fault_state_1_dcovervoltage = m2_fault_state_1_dcovervoltage;
	}
	public double getM2_fault_state_1_dcundervoltage() {
		return m2_fault_state_1_dcundervoltage;
	}
	public void setM2_fault_state_1_dcundervoltage(double m2_fault_state_1_dcundervoltage) {
		this.m2_fault_state_1_dcundervoltage = m2_fault_state_1_dcundervoltage;
	}
	public double getM2_fault_state_1_detectionfusefault() {
		return m2_fault_state_1_detectionfusefault;
	}
	public void setM2_fault_state_1_detectionfusefault(double m2_fault_state_1_detectionfusefault) {
		this.m2_fault_state_1_detectionfusefault = m2_fault_state_1_detectionfusefault;
	}
	public double getM2_fault_state_1_fanfault() {
		return m2_fault_state_1_fanfault;
	}
	public void setM2_fault_state_1_fanfault(double m2_fault_state_1_fanfault) {
		this.m2_fault_state_1_fanfault = m2_fault_state_1_fanfault;
	}
	public double getM2_fault_state_1_frequencyabnormal() {
		return m2_fault_state_1_frequencyabnormal;
	}
	public void setM2_fault_state_1_frequencyabnormal(double m2_fault_state_1_frequencyabnormal) {
		this.m2_fault_state_1_frequencyabnormal = m2_fault_state_1_frequencyabnormal;
	}
	public double getM2_fault_state_1_gfdiprotection() {
		return m2_fault_state_1_gfdiprotection;
	}
	public void setM2_fault_state_1_gfdiprotection(double m2_fault_state_1_gfdiprotection) {
		this.m2_fault_state_1_gfdiprotection = m2_fault_state_1_gfdiprotection;
	}
	public double getM2_fault_state_1_groundingfault() {
		return m2_fault_state_1_groundingfault;
	}
	public void setM2_fault_state_1_groundingfault(double m2_fault_state_1_groundingfault) {
		this.m2_fault_state_1_groundingfault = m2_fault_state_1_groundingfault;
	}
	public double getM2_fault_state_1_hardwarefault() {
		return m2_fault_state_1_hardwarefault;
	}
	public void setM2_fault_state_1_hardwarefault(double m2_fault_state_1_hardwarefault) {
		this.m2_fault_state_1_hardwarefault = m2_fault_state_1_hardwarefault;
	}
	public double getM2_fault_state_1_inverterovervoltage() {
		return m2_fault_state_1_inverterovervoltage;
	}
	public void setM2_fault_state_1_inverterovervoltage(double m2_fault_state_1_inverterovervoltage) {
		this.m2_fault_state_1_inverterovervoltage = m2_fault_state_1_inverterovervoltage;
	}
	public double getM2_fault_state_1_islandingprotection() {
		return m2_fault_state_1_islandingprotection;
	}
	public void setM2_fault_state_1_islandingprotection(double m2_fault_state_1_islandingprotection) {
		this.m2_fault_state_1_islandingprotection = m2_fault_state_1_islandingprotection;
	}
	public double getM2_fault_state_1_moduleovertemperature() {
		return m2_fault_state_1_moduleovertemperature;
	}
	public void setM2_fault_state_1_moduleovertemperature(double m2_fault_state_1_moduleovertemperature) {
		this.m2_fault_state_1_moduleovertemperature = m2_fault_state_1_moduleovertemperature;
	}
	public double getM2_fault_state_1_overfrequency() {
		return m2_fault_state_1_overfrequency;
	}
	public void setM2_fault_state_1_overfrequency(double m2_fault_state_1_overfrequency) {
		this.m2_fault_state_1_overfrequency = m2_fault_state_1_overfrequency;
	}
	public double getM2_fault_state_1_overloadprotection() {
		return m2_fault_state_1_overloadprotection;
	}
	public void setM2_fault_state_1_overloadprotection(double m2_fault_state_1_overloadprotection) {
		this.m2_fault_state_1_overloadprotection = m2_fault_state_1_overloadprotection;
	}
	public double getM2_fault_state_1_pdpprotection() {
		return m2_fault_state_1_pdpprotection;
	}
	public void setM2_fault_state_1_pdpprotection(double m2_fault_state_1_pdpprotection) {
		this.m2_fault_state_1_pdpprotection = m2_fault_state_1_pdpprotection;
	}
	public double getM2_fault_state_1_reactorovertemperature() {
		return m2_fault_state_1_reactorovertemperature;
	}
	public void setM2_fault_state_1_reactorovertemperature(double m2_fault_state_1_reactorovertemperature) {
		this.m2_fault_state_1_reactorovertemperature = m2_fault_state_1_reactorovertemperature;
	}
	public double getM2_fault_state_1_sensorfailure() {
		return m2_fault_state_1_sensorfailure;
	}
	public void setM2_fault_state_1_sensorfailure(double m2_fault_state_1_sensorfailure) {
		this.m2_fault_state_1_sensorfailure = m2_fault_state_1_sensorfailure;
	}
	public double getM2_fault_state_1_temperatureabnormal() {
		return m2_fault_state_1_temperatureabnormal;
	}
	public void setM2_fault_state_1_temperatureabnormal(double m2_fault_state_1_temperatureabnormal) {
		this.m2_fault_state_1_temperatureabnormal = m2_fault_state_1_temperatureabnormal;
	}
	public double getM2_fault_state_1_transformerovertemperature() {
		return m2_fault_state_1_transformerovertemperature;
	}
	public void setM2_fault_state_1_transformerovertemperature(double m2_fault_state_1_transformerovertemperature) {
		this.m2_fault_state_1_transformerovertemperature = m2_fault_state_1_transformerovertemperature;
	}
	public double getM2_fault_state_1_underfrequency() {
		return m2_fault_state_1_underfrequency;
	}
	public void setM2_fault_state_1_underfrequency(double m2_fault_state_1_underfrequency) {
		this.m2_fault_state_1_underfrequency = m2_fault_state_1_underfrequency;
	}
	public double getM2_fault_state_2_accurrentunbalance() {
		return m2_fault_state_2_accurrentunbalance;
	}
	public void setM2_fault_state_2_accurrentunbalance(double m2_fault_state_2_accurrentunbalance) {
		this.m2_fault_state_2_accurrentunbalance = m2_fault_state_2_accurrentunbalance;
	}
	public double getM2_fault_state_2_acspdfault() {
		return m2_fault_state_2_acspdfault;
	}
	public void setM2_fault_state_2_acspdfault(double m2_fault_state_2_acspdfault) {
		this.m2_fault_state_2_acspdfault = m2_fault_state_2_acspdfault;
	}
	public double getM2_fault_state_2_dcspdfault() {
		return m2_fault_state_2_dcspdfault;
	}
	public void setM2_fault_state_2_dcspdfault(double m2_fault_state_2_dcspdfault) {
		this.m2_fault_state_2_dcspdfault = m2_fault_state_2_dcspdfault;
	}
	public double getM2_fault_state_2_pvpolarityreversed() {
		return m2_fault_state_2_pvpolarityreversed;
	}
	public void setM2_fault_state_2_pvpolarityreversed(double m2_fault_state_2_pvpolarityreversed) {
		this.m2_fault_state_2_pvpolarityreversed = m2_fault_state_2_pvpolarityreversed;
	}
	public double getM2_fault_state_2_accabinetovertemperature() {
		return m2_fault_state_2_accabinetovertemperature;
	}
	public void setM2_fault_state_2_accabinetovertemperature(double m2_fault_state_2_accabinetovertemperature) {
		this.m2_fault_state_2_accabinetovertemperature = m2_fault_state_2_accabinetovertemperature;
	}
	public double getM2_fault_state_2_acfusefault() {
		return m2_fault_state_2_acfusefault;
	}
	public void setM2_fault_state_2_acfusefault(double m2_fault_state_2_acfusefault) {
		this.m2_fault_state_2_acfusefault = m2_fault_state_2_acfusefault;
	}
	public double getM2_fault_state_2_acswitchdisconnection() {
		return m2_fault_state_2_acswitchdisconnection;
	}
	public void setM2_fault_state_2_acswitchdisconnection(double m2_fault_state_2_acswitchdisconnection) {
		this.m2_fault_state_2_acswitchdisconnection = m2_fault_state_2_acswitchdisconnection;
	}
	public double getM2_fault_state_2_acswitchfault() {
		return m2_fault_state_2_acswitchfault;
	}
	public void setM2_fault_state_2_acswitchfault(double m2_fault_state_2_acswitchfault) {
		this.m2_fault_state_2_acswitchfault = m2_fault_state_2_acswitchfault;
	}
	public double getM2_fault_state_2_backuppowersupplyabnormal() {
		return m2_fault_state_2_backuppowersupplyabnormal;
	}
	public void setM2_fault_state_2_backuppowersupplyabnormal(double m2_fault_state_2_backuppowersupplyabnormal) {
		this.m2_fault_state_2_backuppowersupplyabnormal = m2_fault_state_2_backuppowersupplyabnormal;
	}
	public double getM2_fault_state_2_buffercontactorfault() {
		return m2_fault_state_2_buffercontactorfault;
	}
	public void setM2_fault_state_2_buffercontactorfault(double m2_fault_state_2_buffercontactorfault) {
		this.m2_fault_state_2_buffercontactorfault = m2_fault_state_2_buffercontactorfault;
	}
	public double getM2_fault_state_2_carriersyncfault() {
		return m2_fault_state_2_carriersyncfault;
	}
	public void setM2_fault_state_2_carriersyncfault(double m2_fault_state_2_carriersyncfault) {
		this.m2_fault_state_2_carriersyncfault = m2_fault_state_2_carriersyncfault;
	}
	public double getM2_fault_state_2_controlcabinettemperatureabnormal() {
		return m2_fault_state_2_controlcabinettemperatureabnormal;
	}
	public void setM2_fault_state_2_controlcabinettemperatureabnormal(
			double m2_fault_state_2_controlcabinettemperatureabnormal) {
		this.m2_fault_state_2_controlcabinettemperatureabnormal = m2_fault_state_2_controlcabinettemperatureabnormal;
	}
	public double getM2_fault_state_2_controlpowersupplyabnormal() {
		return m2_fault_state_2_controlpowersupplyabnormal;
	}
	public void setM2_fault_state_2_controlpowersupplyabnormal(double m2_fault_state_2_controlpowersupplyabnormal) {
		this.m2_fault_state_2_controlpowersupplyabnormal = m2_fault_state_2_controlpowersupplyabnormal;
	}
	public double getM2_fault_state_2_currentunbalance2() {
		return m2_fault_state_2_currentunbalance2;
	}
	public void setM2_fault_state_2_currentunbalance2(double m2_fault_state_2_currentunbalance2) {
		this.m2_fault_state_2_currentunbalance2 = m2_fault_state_2_currentunbalance2;
	}
	public double getM2_fault_state_2_currentunbalance3() {
		return m2_fault_state_2_currentunbalance3;
	}
	public void setM2_fault_state_2_currentunbalance3(double m2_fault_state_2_currentunbalance3) {
		this.m2_fault_state_2_currentunbalance3 = m2_fault_state_2_currentunbalance3;
	}
	public double getM2_fault_state_2_dccabinetovertemperature() {
		return m2_fault_state_2_dccabinetovertemperature;
	}
	public void setM2_fault_state_2_dccabinetovertemperature(double m2_fault_state_2_dccabinetovertemperature) {
		this.m2_fault_state_2_dccabinetovertemperature = m2_fault_state_2_dccabinetovertemperature;
	}
	public double getM2_fault_state_2_dcfusegroundingfault() {
		return m2_fault_state_2_dcfusegroundingfault;
	}
	public void setM2_fault_state_2_dcfusegroundingfault(double m2_fault_state_2_dcfusegroundingfault) {
		this.m2_fault_state_2_dcfusegroundingfault = m2_fault_state_2_dcfusegroundingfault;
	}
	public double getM2_fault_state_2_dcinjectionfault() {
		return m2_fault_state_2_dcinjectionfault;
	}
	public void setM2_fault_state_2_dcinjectionfault(double m2_fault_state_2_dcinjectionfault) {
		this.m2_fault_state_2_dcinjectionfault = m2_fault_state_2_dcinjectionfault;
	}
	public double getM2_fault_state_2_dcswitchfault() {
		return m2_fault_state_2_dcswitchfault;
	}
	public void setM2_fault_state_2_dcswitchfault(double m2_fault_state_2_dcswitchfault) {
		this.m2_fault_state_2_dcswitchfault = m2_fault_state_2_dcswitchfault;
	}
	public double getM2_fault_state_2_dcvoltagesamplingfault() {
		return m2_fault_state_2_dcvoltagesamplingfault;
	}
	public void setM2_fault_state_2_dcvoltagesamplingfault(double m2_fault_state_2_dcvoltagesamplingfault) {
		this.m2_fault_state_2_dcvoltagesamplingfault = m2_fault_state_2_dcvoltagesamplingfault;
	}
	public double getM2_fault_state_2_devicecoderepeatfault() {
		return m2_fault_state_2_devicecoderepeatfault;
	}
	public void setM2_fault_state_2_devicecoderepeatfault(double m2_fault_state_2_devicecoderepeatfault) {
		this.m2_fault_state_2_devicecoderepeatfault = m2_fault_state_2_devicecoderepeatfault;
	}
	public double getM2_fault_state_2_driveboardfault() {
		return m2_fault_state_2_driveboardfault;
	}
	public void setM2_fault_state_2_driveboardfault(double m2_fault_state_2_driveboardfault) {
		this.m2_fault_state_2_driveboardfault = m2_fault_state_2_driveboardfault;
	}
	public double getM2_fault_state_2_fan2fault() {
		return m2_fault_state_2_fan2fault;
	}
	public void setM2_fault_state_2_fan2fault(double m2_fault_state_2_fan2fault) {
		this.m2_fault_state_2_fan2fault = m2_fault_state_2_fan2fault;
	}
	public double getM2_fault_state_2_gridvoltageunbalance() {
		return m2_fault_state_2_gridvoltageunbalance;
	}
	public void setM2_fault_state_2_gridvoltageunbalance(double m2_fault_state_2_gridvoltageunbalance) {
		this.m2_fault_state_2_gridvoltageunbalance = m2_fault_state_2_gridvoltageunbalance;
	}
	public double getM2_fault_state_2_insulationimpedance() {
		return m2_fault_state_2_insulationimpedance;
	}
	public void setM2_fault_state_2_insulationimpedance(double m2_fault_state_2_insulationimpedance) {
		this.m2_fault_state_2_insulationimpedance = m2_fault_state_2_insulationimpedance;
	}
	public double getM2_fault_state_2_neutralpointpotentialshift() {
		return m2_fault_state_2_neutralpointpotentialshift;
	}
	public void setM2_fault_state_2_neutralpointpotentialshift(double m2_fault_state_2_neutralpointpotentialshift) {
		this.m2_fault_state_2_neutralpointpotentialshift = m2_fault_state_2_neutralpointpotentialshift;
	}
	public double getM2_fault_state_2_paralleloperation() {
		return m2_fault_state_2_paralleloperation;
	}
	public void setM2_fault_state_2_paralleloperation(double m2_fault_state_2_paralleloperation) {
		this.m2_fault_state_2_paralleloperation = m2_fault_state_2_paralleloperation;
	}
	public double getM2_fault_state_2_samplingfault() {
		return m2_fault_state_2_samplingfault;
	}
	public void setM2_fault_state_2_samplingfault(double m2_fault_state_2_samplingfault) {
		this.m2_fault_state_2_samplingfault = m2_fault_state_2_samplingfault;
	}
	public double getM2_fault_state_2_softstartfault() {
		return m2_fault_state_2_softstartfault;
	}
	public void setM2_fault_state_2_softstartfault(double m2_fault_state_2_softstartfault) {
		this.m2_fault_state_2_softstartfault = m2_fault_state_2_softstartfault;
	}
	public double getM2_node_state_1_accircuitbreakerstate() {
		return m2_node_state_1_accircuitbreakerstate;
	}
	public void setM2_node_state_1_accircuitbreakerstate(double m2_node_state_1_accircuitbreakerstate) {
		this.m2_node_state_1_accircuitbreakerstate = m2_node_state_1_accircuitbreakerstate;
	}
	public double getM2_node_state_1_acfusestatus() {
		return m2_node_state_1_acfusestatus;
	}
	public void setM2_node_state_1_acfusestatus(double m2_node_state_1_acfusestatus) {
		this.m2_node_state_1_acfusestatus = m2_node_state_1_acfusestatus;
	}
	public double getM2_node_state_1_acmaincontactorstate() {
		return m2_node_state_1_acmaincontactorstate;
	}
	public void setM2_node_state_1_acmaincontactorstate(double m2_node_state_1_acmaincontactorstate) {
		this.m2_node_state_1_acmaincontactorstate = m2_node_state_1_acmaincontactorstate;
	}
	public double getM2_node_state_1_acbfaultstate() {
		return m2_node_state_1_acbfaultstate;
	}
	public void setM2_node_state_1_acbfaultstate(double m2_node_state_1_acbfaultstate) {
		this.m2_node_state_1_acbfaultstate = m2_node_state_1_acbfaultstate;
	}
	public double getM2_node_state_1_acbremotelocal() {
		return m2_node_state_1_acbremotelocal;
	}
	public void setM2_node_state_1_acbremotelocal(double m2_node_state_1_acbremotelocal) {
		this.m2_node_state_1_acbremotelocal = m2_node_state_1_acbremotelocal;
	}
	public double getM2_node_state_2_dcswitchstate1() {
		return m2_node_state_2_dcswitchstate1;
	}
	public void setM2_node_state_2_dcswitchstate1(double m2_node_state_2_dcswitchstate1) {
		this.m2_node_state_2_dcswitchstate1 = m2_node_state_2_dcswitchstate1;
	}
	public double getM2_node_state_2_dcswitchstate2() {
		return m2_node_state_2_dcswitchstate2;
	}
	public void setM2_node_state_2_dcswitchstate2(double m2_node_state_2_dcswitchstate2) {
		this.m2_node_state_2_dcswitchstate2 = m2_node_state_2_dcswitchstate2;
	}
	public double getM2_node_state_2_dcswitchstate3() {
		return m2_node_state_2_dcswitchstate3;
	}
	public void setM2_node_state_2_dcswitchstate3(double m2_node_state_2_dcswitchstate3) {
		this.m2_node_state_2_dcswitchstate3 = m2_node_state_2_dcswitchstate3;
	}
	public double getM2_node_state_2_dcswitchstate4() {
		return m2_node_state_2_dcswitchstate4;
	}
	public void setM2_node_state_2_dcswitchstate4(double m2_node_state_2_dcswitchstate4) {
		this.m2_node_state_2_dcswitchstate4 = m2_node_state_2_dcswitchstate4;
	}
	public double getM2_node_state_2_spdnodestate() {
		return m2_node_state_2_spdnodestate;
	}
	public void setM2_node_state_2_spdnodestate(double m2_node_state_2_spdnodestate) {
		this.m2_node_state_2_spdnodestate = m2_node_state_2_spdnodestate;
	}
	public double getM2_work_state_alarmrunning() {
		return m2_work_state_alarmrunning;
	}
	public void setM2_work_state_alarmrunning(double m2_work_state_alarmrunning) {
		this.m2_work_state_alarmrunning = m2_work_state_alarmrunning;
	}
	public double getM2_work_state_antipidrunning() {
		return m2_work_state_antipidrunning;
	}
	public void setM2_work_state_antipidrunning(double m2_work_state_antipidrunning) {
		this.m2_work_state_antipidrunning = m2_work_state_antipidrunning;
	}
	public double getM2_work_state_deratingrunning() {
		return m2_work_state_deratingrunning;
	}
	public void setM2_work_state_deratingrunning(double m2_work_state_deratingrunning) {
		this.m2_work_state_deratingrunning = m2_work_state_deratingrunning;
	}
	public double getM2_work_state_emergencystop() {
		return m2_work_state_emergencystop;
	}
	public void setM2_work_state_emergencystop(double m2_work_state_emergencystop) {
		this.m2_work_state_emergencystop = m2_work_state_emergencystop;
	}
	public double getM2_work_state_faultstop() {
		return m2_work_state_faultstop;
	}
	public void setM2_work_state_faultstop(double m2_work_state_faultstop) {
		this.m2_work_state_faultstop = m2_work_state_faultstop;
	}
	public double getM2_work_state_initialstandby() {
		return m2_work_state_initialstandby;
	}
	public void setM2_work_state_initialstandby(double m2_work_state_initialstandby) {
		this.m2_work_state_initialstandby = m2_work_state_initialstandby;
	}
	public double getM2_work_state_iodspcommunicationabnormal() {
		return m2_work_state_iodspcommunicationabnormal;
	}
	public void setM2_work_state_iodspcommunicationabnormal(double m2_work_state_iodspcommunicationabnormal) {
		this.m2_work_state_iodspcommunicationabnormal = m2_work_state_iodspcommunicationabnormal;
	}
	public double getM2_work_state_iomdccommunicationabnormal() {
		return m2_work_state_iomdccommunicationabnormal;
	}
	public void setM2_work_state_iomdccommunicationabnormal(double m2_work_state_iomdccommunicationabnormal) {
		this.m2_work_state_iomdccommunicationabnormal = m2_work_state_iomdccommunicationabnormal;
	}
	public double getM2_work_state_keystop() {
		return m2_work_state_keystop;
	}
	public void setM2_work_state_keystop(double m2_work_state_keystop) {
		this.m2_work_state_keystop = m2_work_state_keystop;
	}
	public double getM2_work_state_running() {
		return m2_work_state_running;
	}
	public void setM2_work_state_running(double m2_work_state_running) {
		this.m2_work_state_running = m2_work_state_running;
	}
	public double getM2_work_state_standby() {
		return m2_work_state_standby;
	}
	public void setM2_work_state_standby(double m2_work_state_standby) {
		this.m2_work_state_standby = m2_work_state_standby;
	}
	public double getM2_work_state_starting() {
		return m2_work_state_starting;
	}
	public void setM2_work_state_starting(double m2_work_state_starting) {
		this.m2_work_state_starting = m2_work_state_starting;
	}
	public double getM2_work_state_stopped() {
		return m2_work_state_stopped;
	}
	public void setM2_work_state_stopped(double m2_work_state_stopped) {
		this.m2_work_state_stopped = m2_work_state_stopped;
	}
	public double getM2_work_state_stopping() {
		return m2_work_state_stopping;
	}
	public void setM2_work_state_stopping(double m2_work_state_stopping) {
		this.m2_work_state_stopping = m2_work_state_stopping;
	}
	public double getM2_work_state_totalsignalbitofrunningstate() {
		return m2_work_state_totalsignalbitofrunningstate;
	}
	public void setM2_work_state_totalsignalbitofrunningstate(double m2_work_state_totalsignalbitofrunningstate) {
		this.m2_work_state_totalsignalbitofrunningstate = m2_work_state_totalsignalbitofrunningstate;
	}
	public double getM2_work_state_totalstopbit() {
		return m2_work_state_totalstopbit;
	}
	public void setM2_work_state_totalstopbit(double m2_work_state_totalstopbit) {
		this.m2_work_state_totalstopbit = m2_work_state_totalstopbit;
	}
	public double getMv_node_state_external_e_stop() {
		return mv_node_state_external_e_stop;
	}
	public void setMv_node_state_external_e_stop(double mv_node_state_external_e_stop) {
		this.mv_node_state_external_e_stop = mv_node_state_external_e_stop;
	}
	public double getMv_node_state_gas_relay_alarm() {
		return mv_node_state_gas_relay_alarm;
	}
	public void setMv_node_state_gas_relay_alarm(double mv_node_state_gas_relay_alarm) {
		this.mv_node_state_gas_relay_alarm = mv_node_state_gas_relay_alarm;
	}
	public double getMv_node_state_gas_relay_trip() {
		return mv_node_state_gas_relay_trip;
	}
	public void setMv_node_state_gas_relay_trip(double mv_node_state_gas_relay_trip) {
		this.mv_node_state_gas_relay_trip = mv_node_state_gas_relay_trip;
	}
	public double getMv_node_state_hv_remote_ctrl() {
		return mv_node_state_hv_remote_ctrl;
	}
	public void setMv_node_state_hv_remote_ctrl(double mv_node_state_hv_remote_ctrl) {
		this.mv_node_state_hv_remote_ctrl = mv_node_state_hv_remote_ctrl;
	}
	public double getMv_node_state_hv_remote_ctrl_trip() {
		return mv_node_state_hv_remote_ctrl_trip;
	}
	public void setMv_node_state_hv_remote_ctrl_trip(double mv_node_state_hv_remote_ctrl_trip) {
		this.mv_node_state_hv_remote_ctrl_trip = mv_node_state_hv_remote_ctrl_trip;
	}
	public double getMv_node_state_hv_room_smoke() {
		return mv_node_state_hv_room_smoke;
	}
	public void setMv_node_state_hv_room_smoke(double mv_node_state_hv_room_smoke) {
		this.mv_node_state_hv_room_smoke = mv_node_state_hv_room_smoke;
	}
	public double getMv_node_state_low_oil_level_trip() {
		return mv_node_state_low_oil_level_trip;
	}
	public void setMv_node_state_low_oil_level_trip(double mv_node_state_low_oil_level_trip) {
		this.mv_node_state_low_oil_level_trip = mv_node_state_low_oil_level_trip;
	}
	public double getMv_node_state_mv_ds() {
		return mv_node_state_mv_ds;
	}
	public void setMv_node_state_mv_ds(double mv_node_state_mv_ds) {
		this.mv_node_state_mv_ds = mv_node_state_mv_ds;
	}
	public double getMv_node_state_mv_fuse() {
		return mv_node_state_mv_fuse;
	}
	public void setMv_node_state_mv_fuse(double mv_node_state_mv_fuse) {
		this.mv_node_state_mv_fuse = mv_node_state_mv_fuse;
	}
	public double getMv_node_state_mv_load_switch() {
		return mv_node_state_mv_load_switch;
	}
	public void setMv_node_state_mv_load_switch(double mv_node_state_mv_load_switch) {
		this.mv_node_state_mv_load_switch = mv_node_state_mv_load_switch;
	}
	public double getMv_node_state_mv_load_switch_1() {
		return mv_node_state_mv_load_switch_1;
	}
	public void setMv_node_state_mv_load_switch_1(double mv_node_state_mv_load_switch_1) {
		this.mv_node_state_mv_load_switch_1 = mv_node_state_mv_load_switch_1;
	}
	public double getMv_node_state_mv_load_switch_2() {
		return mv_node_state_mv_load_switch_2;
	}
	public void setMv_node_state_mv_load_switch_2(double mv_node_state_mv_load_switch_2) {
		this.mv_node_state_mv_load_switch_2 = mv_node_state_mv_load_switch_2;
	}
	public double getMv_node_state_mv_vcb() {
		return mv_node_state_mv_vcb;
	}
	public void setMv_node_state_mv_vcb(double mv_node_state_mv_vcb) {
		this.mv_node_state_mv_vcb = mv_node_state_mv_vcb;
	}
	public double getMv_node_state_oil_temp_alarrm() {
		return mv_node_state_oil_temp_alarrm;
	}
	public void setMv_node_state_oil_temp_alarrm(double mv_node_state_oil_temp_alarrm) {
		this.mv_node_state_oil_temp_alarrm = mv_node_state_oil_temp_alarrm;
	}
	public double getMv_node_state_oil_temp_trip() {
		return mv_node_state_oil_temp_trip;
	}
	public void setMv_node_state_oil_temp_trip(double mv_node_state_oil_temp_trip) {
		this.mv_node_state_oil_temp_trip = mv_node_state_oil_temp_trip;
	}
	public double getMv_node_state_pressure_relief_trip() {
		return mv_node_state_pressure_relief_trip;
	}
	public void setMv_node_state_pressure_relief_trip(double mv_node_state_pressure_relief_trip) {
		this.mv_node_state_pressure_relief_trip = mv_node_state_pressure_relief_trip;
	}
	public double getMv_node_state_sf6_low_press_alarm() {
		return mv_node_state_sf6_low_press_alarm;
	}
	public void setMv_node_state_sf6_low_press_alarm(double mv_node_state_sf6_low_press_alarm) {
		this.mv_node_state_sf6_low_press_alarm = mv_node_state_sf6_low_press_alarm;
	}
	public double getMv_node_state_winding_temp_alarm() {
		return mv_node_state_winding_temp_alarm;
	}
	public void setMv_node_state_winding_temp_alarm(double mv_node_state_winding_temp_alarm) {
		this.mv_node_state_winding_temp_alarm = mv_node_state_winding_temp_alarm;
	}
	public double getMv_node_state_winding_temp_trip() {
		return mv_node_state_winding_temp_trip;
	}
	public void setMv_node_state_winding_temp_trip(double mv_node_state_winding_temp_trip) {
		this.mv_node_state_winding_temp_trip = mv_node_state_winding_temp_trip;
	}
	public double getWork_state_door_open_prot() {
		return work_state_door_open_prot;
	}
	public void setWork_state_door_open_prot(double work_state_door_open_prot) {
		this.work_state_door_open_prot = work_state_door_open_prot;
	}
	public double getWork_state_external_emer_stop() {
		return work_state_external_emer_stop;
	}
	public void setWork_state_external_emer_stop(double work_state_external_emer_stop) {
		this.work_state_external_emer_stop = work_state_external_emer_stop;
	}
	public double getWork_state_local_emer_stop() {
		return work_state_local_emer_stop;
	}
	public void setWork_state_local_emer_stop(double work_state_local_emer_stop) {
		this.work_state_local_emer_stop = work_state_local_emer_stop;
	}
	public double getWork_state_mv_fault() {
		return work_state_mv_fault;
	}
	public void setWork_state_mv_fault(double work_state_mv_fault) {
		this.work_state_mv_fault = work_state_mv_fault;
	}
	public double getWork_state_rem_emer_stop() {
		return work_state_rem_emer_stop;
	}
	public void setWork_state_rem_emer_stop(double work_state_rem_emer_stop) {
		this.work_state_rem_emer_stop = work_state_rem_emer_stop;
	}
	public double getWork_state_running() {
		return work_state_running;
	}
	public void setWork_state_running(double work_state_running) {
		this.work_state_running = work_state_running;
	}
	public double getWork_state_smoke_prot() {
		return work_state_smoke_prot;
	}
	public void setWork_state_smoke_prot(double work_state_smoke_prot) {
		this.work_state_smoke_prot = work_state_smoke_prot;
	}
	public double getWork_state_stopped() {
		return work_state_stopped;
	}
	public void setWork_state_stopped(double work_state_stopped) {
		this.work_state_stopped = work_state_stopped;
	}
	
	
	
	
}
