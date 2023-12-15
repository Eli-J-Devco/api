/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelShark100Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double volts_a_n;
	private double volts_b_n;
	private double volts_c_n;
	private double volts_a_b;
	private double volts_b_c;
	private double volts_c_a; 
	private double amps_a;
	private double amps_b;
	private double amps_c;
	private double watts_3ph_total;
	private double vars_3ph_total;
	private double vas_3ph_total;
	private double power_factor_3ph_total;
	private double frequency;
	private double neutral_current;
	private double w_hours_received;
	private double w_hours_delivered;
	private double w_hours_net;
	private double w_hours_total;
	private double var_hours_positive;
	private double var_hours_negative;
	private double var_hours_net;
	private double var_hours_total;
	private double va_hours_total;
	private double amps_a_average;
	private double amps_b_average;
	private double amps_c_average;
	private double positive_watts_3ph_average;
	private double positive_vars_3ph_average;
	private double negative_watts_3ph_average;
	private double negative_vars_3ph_average;
	private double vas_3ph_average;
	private double positive_pf_3ph_average;
	private double negative_pf_3ph_average;
	private double volts_a_n_min;
	private double volts_b_n_min;
	private double volts_c_n_min;
	private double volts_a_b_min;
	private double volts_b_c_min;
	private double volts_c_a_min;
	private double amps_a_min_avg_demand;
	private double amps_b_min_avg_demand;
	private double amps_c_min_avg_demand;
	private double positive_watts_3ph_min_avg_demand;
	private double positive_vars_3ph_min_avg_demand;
	private double negative_watts_3ph_min_avg_demand;
	private double negative_vars_3ph_min_avg_demand;
	private double vas_3ph_min_avg_demand;
	private double positive_pf_3ph_min_avg_demand;
	private double negative_pf_3ph_min_avg_demand;
	private double frequency_min;
	private double volts_a_n_max;
	private double volts_b_n_max;
	private double volts_c_n_max;
	private double volts_a_b_max;
	private double volts_b_c_max;
	private double volts_c_a_max;
	private double amps_a_max_avg_demand;
	private double amps_b_max_avg_demand;
	private double amps_c_max_avg_demand;
	private double positive_watts_3ph_max_avg_demand;
	private double positive_vars_3ph_max_avg_demand;
	private double negative_watts_3ph_max_avg_demand;
	private double negative_vars_3ph_max_avg_demand;
	private double vas_3ph_max_avg_demand;
	private double positive_pf_3ph_max_avg_demand;
	private double negative_pf_3ph_max_avg_demand;
	private double frequency_max;
	private double volts_a_n_thd;
	private double volts_b_n_thd;
	private double volts_c_n_thd;
	private double amps_a_thd;
	private double amps_b_thd;
	private double amps_c_thd;
	private double phase_a_current_0th;
	private double phase_a_current_1st;
	private double phase_a_current_2nd;
	private double phase_a_current_3rd;
	private double phase_a_current_4th;
	private double phase_a_current_5th;
	private double phase_a_current_6th;
	private double phase_a_current_7th;
	private double phase_a_voltage_0th;
	private double phase_a_voltage_1st;
	private double phase_a_voltage_2nd;
	private double phase_a_voltage_3rd;
	private double phase_b_current_0th;
	private double phase_b_current_1st;
	private double phase_b_current_2nd;
	private double phase_b_current_3rd;
	private double phase_b_current_4th;
	private double phase_b_current_5th;
	private double phase_b_current_6th;
	private double phase_b_current_7th;
	private double phase_b_voltage_0th;
	private double phase_b_voltage_1st;
	private double phase_b_voltage_2nd;
	private double phase_b_voltage_3rd;
	private double phase_c_current_0th;
	private double phase_c_current_1st;
	private double phase_c_current_2nd;
	private double phase_c_current_3rd;
	private double phase_c_current_4th;
	private double phase_c_current_5th;
	private double phase_c_current_6th;
	private double phase_c_current_7th;
	private double phase_c_voltage_0th;
	private double phase_c_voltage_1st;
	private double phase_c_voltage_2nd;
	private double phase_c_voltage_3rd;
	private double angle_phase_a_current;
	private double angle_phase_b_current;
	private double angle_phase_c_current;
	private double angle_volts_a_b;
	private double angle_volts_b_c;
	private double angle_volts_c_a;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
private int enable_alert;
	
	
	
	public int getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(int enable_alert) {
		this.enable_alert = enable_alert;
	}
	
	
	
	
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getView_tablename() {
		return view_tablename;
	}
	public void setView_tablename(String view_tablename) {
		this.view_tablename = view_tablename;
	}
	public String getJob_tablename() {
		return job_tablename;
	}
	public void setJob_tablename(String job_tablename) {
		this.job_tablename = job_tablename;
	}
	
	
	public double getMeasuredProduction() {
		return MeasuredProduction;
	}
	public void setMeasuredProduction(double measuredProduction) {
		MeasuredProduction = measuredProduction;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public int getLow_alarm() {
		return low_alarm;
	}
	public void setLow_alarm(int low_alarm) {
		this.low_alarm = low_alarm;
	}
	public int getHigh_alarm() {
		return high_alarm;
	}
	public void setHigh_alarm(int high_alarm) {
		this.high_alarm = high_alarm;
	}
	public double getVolts_a_n() {
		return volts_a_n;
	}
	public void setVolts_a_n(double volts_a_n) {
		this.volts_a_n = volts_a_n;
	}
	public double getVolts_b_n() {
		return volts_b_n;
	}
	public void setVolts_b_n(double volts_b_n) {
		this.volts_b_n = volts_b_n;
	}
	public double getVolts_c_n() {
		return volts_c_n;
	}
	public void setVolts_c_n(double volts_c_n) {
		this.volts_c_n = volts_c_n;
	}
	public double getVolts_a_b() {
		return volts_a_b;
	}
	public void setVolts_a_b(double volts_a_b) {
		this.volts_a_b = volts_a_b;
	}
	public double getVolts_b_c() {
		return volts_b_c;
	}
	public void setVolts_b_c(double volts_b_c) {
		this.volts_b_c = volts_b_c;
	}
	public double getVolts_c_a() {
		return volts_c_a;
	}
	public void setVolts_c_a(double volts_c_a) {
		this.volts_c_a = volts_c_a;
	}
	public double getAmps_a() {
		return amps_a;
	}
	public void setAmps_a(double amps_a) {
		this.amps_a = amps_a;
	}
	public double getAmps_b() {
		return amps_b;
	}
	public void setAmps_b(double amps_b) {
		this.amps_b = amps_b;
	}
	public double getAmps_c() {
		return amps_c;
	}
	public void setAmps_c(double amps_c) {
		this.amps_c = amps_c;
	}
	public double getWatts_3ph_total() {
		return watts_3ph_total;
	}
	public void setWatts_3ph_total(double watts_3ph_total) {
		this.watts_3ph_total = watts_3ph_total;
	}
	public double getVars_3ph_total() {
		return vars_3ph_total;
	}
	public void setVars_3ph_total(double vars_3ph_total) {
		this.vars_3ph_total = vars_3ph_total;
	}
	public double getVas_3ph_total() {
		return vas_3ph_total;
	}
	public void setVas_3ph_total(double vas_3ph_total) {
		this.vas_3ph_total = vas_3ph_total;
	}
	public double getPower_factor_3ph_total() {
		return power_factor_3ph_total;
	}
	public void setPower_factor_3ph_total(double power_factor_3ph_total) {
		this.power_factor_3ph_total = power_factor_3ph_total;
	}
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public double getNeutral_current() {
		return neutral_current;
	}
	public void setNeutral_current(double neutral_current) {
		this.neutral_current = neutral_current;
	}
	public double getW_hours_received() {
		return w_hours_received;
	}
	public void setW_hours_received(double w_hours_received) {
		this.w_hours_received = w_hours_received;
	}
	public double getW_hours_delivered() {
		return w_hours_delivered;
	}
	public void setW_hours_delivered(double w_hours_delivered) {
		this.w_hours_delivered = w_hours_delivered;
	}
	public double getW_hours_net() {
		return w_hours_net;
	}
	public void setW_hours_net(double w_hours_net) {
		this.w_hours_net = w_hours_net;
	}
	public double getW_hours_total() {
		return w_hours_total;
	}
	public void setW_hours_total(double w_hours_total) {
		this.w_hours_total = w_hours_total;
	}
	public double getVar_hours_positive() {
		return var_hours_positive;
	}
	public void setVar_hours_positive(double var_hours_positive) {
		this.var_hours_positive = var_hours_positive;
	}
	public double getVar_hours_negative() {
		return var_hours_negative;
	}
	public void setVar_hours_negative(double var_hours_negative) {
		this.var_hours_negative = var_hours_negative;
	}
	public double getVar_hours_net() {
		return var_hours_net;
	}
	public void setVar_hours_net(double var_hours_net) {
		this.var_hours_net = var_hours_net;
	}
	public double getVar_hours_total() {
		return var_hours_total;
	}
	public void setVar_hours_total(double var_hours_total) {
		this.var_hours_total = var_hours_total;
	}
	public double getVa_hours_total() {
		return va_hours_total;
	}
	public void setVa_hours_total(double va_hours_total) {
		this.va_hours_total = va_hours_total;
	}
	public double getAmps_a_average() {
		return amps_a_average;
	}
	public void setAmps_a_average(double amps_a_average) {
		this.amps_a_average = amps_a_average;
	}
	public double getAmps_b_average() {
		return amps_b_average;
	}
	public void setAmps_b_average(double amps_b_average) {
		this.amps_b_average = amps_b_average;
	}
	public double getAmps_c_average() {
		return amps_c_average;
	}
	public void setAmps_c_average(double amps_c_average) {
		this.amps_c_average = amps_c_average;
	}
	public double getPositive_watts_3ph_average() {
		return positive_watts_3ph_average;
	}
	public void setPositive_watts_3ph_average(double positive_watts_3ph_average) {
		this.positive_watts_3ph_average = positive_watts_3ph_average;
	}
	public double getPositive_vars_3ph_average() {
		return positive_vars_3ph_average;
	}
	public void setPositive_vars_3ph_average(double positive_vars_3ph_average) {
		this.positive_vars_3ph_average = positive_vars_3ph_average;
	}
	public double getNegative_watts_3ph_average() {
		return negative_watts_3ph_average;
	}
	public void setNegative_watts_3ph_average(double negative_watts_3ph_average) {
		this.negative_watts_3ph_average = negative_watts_3ph_average;
	}
	public double getNegative_vars_3ph_average() {
		return negative_vars_3ph_average;
	}
	public void setNegative_vars_3ph_average(double negative_vars_3ph_average) {
		this.negative_vars_3ph_average = negative_vars_3ph_average;
	}
	public double getVas_3ph_average() {
		return vas_3ph_average;
	}
	public void setVas_3ph_average(double vas_3ph_average) {
		this.vas_3ph_average = vas_3ph_average;
	}
	public double getPositive_pf_3ph_average() {
		return positive_pf_3ph_average;
	}
	public void setPositive_pf_3ph_average(double positive_pf_3ph_average) {
		this.positive_pf_3ph_average = positive_pf_3ph_average;
	}
	public double getNegative_pf_3ph_average() {
		return negative_pf_3ph_average;
	}
	public void setNegative_pf_3ph_average(double negative_pf_3ph_average) {
		this.negative_pf_3ph_average = negative_pf_3ph_average;
	}
	public double getVolts_a_n_min() {
		return volts_a_n_min;
	}
	public void setVolts_a_n_min(double volts_a_n_min) {
		this.volts_a_n_min = volts_a_n_min;
	}
	public double getVolts_b_n_min() {
		return volts_b_n_min;
	}
	public void setVolts_b_n_min(double volts_b_n_min) {
		this.volts_b_n_min = volts_b_n_min;
	}
	public double getVolts_c_n_min() {
		return volts_c_n_min;
	}
	public void setVolts_c_n_min(double volts_c_n_min) {
		this.volts_c_n_min = volts_c_n_min;
	}
	public double getVolts_a_b_min() {
		return volts_a_b_min;
	}
	public void setVolts_a_b_min(double volts_a_b_min) {
		this.volts_a_b_min = volts_a_b_min;
	}
	public double getVolts_b_c_min() {
		return volts_b_c_min;
	}
	public void setVolts_b_c_min(double volts_b_c_min) {
		this.volts_b_c_min = volts_b_c_min;
	}
	public double getVolts_c_a_min() {
		return volts_c_a_min;
	}
	public void setVolts_c_a_min(double volts_c_a_min) {
		this.volts_c_a_min = volts_c_a_min;
	}
	public double getAmps_a_min_avg_demand() {
		return amps_a_min_avg_demand;
	}
	public void setAmps_a_min_avg_demand(double amps_a_min_avg_demand) {
		this.amps_a_min_avg_demand = amps_a_min_avg_demand;
	}
	public double getAmps_b_min_avg_demand() {
		return amps_b_min_avg_demand;
	}
	public void setAmps_b_min_avg_demand(double amps_b_min_avg_demand) {
		this.amps_b_min_avg_demand = amps_b_min_avg_demand;
	}
	public double getAmps_c_min_avg_demand() {
		return amps_c_min_avg_demand;
	}
	public void setAmps_c_min_avg_demand(double amps_c_min_avg_demand) {
		this.amps_c_min_avg_demand = amps_c_min_avg_demand;
	}
	public double getPositive_watts_3ph_min_avg_demand() {
		return positive_watts_3ph_min_avg_demand;
	}
	public void setPositive_watts_3ph_min_avg_demand(double positive_watts_3ph_min_avg_demand) {
		this.positive_watts_3ph_min_avg_demand = positive_watts_3ph_min_avg_demand;
	}
	public double getPositive_vars_3ph_min_avg_demand() {
		return positive_vars_3ph_min_avg_demand;
	}
	public void setPositive_vars_3ph_min_avg_demand(double positive_vars_3ph_min_avg_demand) {
		this.positive_vars_3ph_min_avg_demand = positive_vars_3ph_min_avg_demand;
	}
	public double getNegative_watts_3ph_min_avg_demand() {
		return negative_watts_3ph_min_avg_demand;
	}
	public void setNegative_watts_3ph_min_avg_demand(double negative_watts_3ph_min_avg_demand) {
		this.negative_watts_3ph_min_avg_demand = negative_watts_3ph_min_avg_demand;
	}
	public double getNegative_vars_3ph_min_avg_demand() {
		return negative_vars_3ph_min_avg_demand;
	}
	public void setNegative_vars_3ph_min_avg_demand(double negative_vars_3ph_min_avg_demand) {
		this.negative_vars_3ph_min_avg_demand = negative_vars_3ph_min_avg_demand;
	}
	public double getVas_3ph_min_avg_demand() {
		return vas_3ph_min_avg_demand;
	}
	public void setVas_3ph_min_avg_demand(double vas_3ph_min_avg_demand) {
		this.vas_3ph_min_avg_demand = vas_3ph_min_avg_demand;
	}
	public double getPositive_pf_3ph_min_avg_demand() {
		return positive_pf_3ph_min_avg_demand;
	}
	public void setPositive_pf_3ph_min_avg_demand(double positive_pf_3ph_min_avg_demand) {
		this.positive_pf_3ph_min_avg_demand = positive_pf_3ph_min_avg_demand;
	}
	public double getNegative_pf_3ph_min_avg_demand() {
		return negative_pf_3ph_min_avg_demand;
	}
	public void setNegative_pf_3ph_min_avg_demand(double negative_pf_3ph_min_avg_demand) {
		this.negative_pf_3ph_min_avg_demand = negative_pf_3ph_min_avg_demand;
	}
	public double getFrequency_min() {
		return frequency_min;
	}
	public void setFrequency_min(double frequency_min) {
		this.frequency_min = frequency_min;
	}
	public double getVolts_a_n_max() {
		return volts_a_n_max;
	}
	public void setVolts_a_n_max(double volts_a_n_max) {
		this.volts_a_n_max = volts_a_n_max;
	}
	public double getVolts_b_n_max() {
		return volts_b_n_max;
	}
	public void setVolts_b_n_max(double volts_b_n_max) {
		this.volts_b_n_max = volts_b_n_max;
	}
	public double getVolts_c_n_max() {
		return volts_c_n_max;
	}
	public void setVolts_c_n_max(double volts_c_n_max) {
		this.volts_c_n_max = volts_c_n_max;
	}
	public double getVolts_a_b_max() {
		return volts_a_b_max;
	}
	public void setVolts_a_b_max(double volts_a_b_max) {
		this.volts_a_b_max = volts_a_b_max;
	}
	public double getVolts_b_c_max() {
		return volts_b_c_max;
	}
	public void setVolts_b_c_max(double volts_b_c_max) {
		this.volts_b_c_max = volts_b_c_max;
	}
	public double getVolts_c_a_max() {
		return volts_c_a_max;
	}
	public void setVolts_c_a_max(double volts_c_a_max) {
		this.volts_c_a_max = volts_c_a_max;
	}
	public double getAmps_a_max_avg_demand() {
		return amps_a_max_avg_demand;
	}
	public void setAmps_a_max_avg_demand(double amps_a_max_avg_demand) {
		this.amps_a_max_avg_demand = amps_a_max_avg_demand;
	}
	public double getAmps_b_max_avg_demand() {
		return amps_b_max_avg_demand;
	}
	public void setAmps_b_max_avg_demand(double amps_b_max_avg_demand) {
		this.amps_b_max_avg_demand = amps_b_max_avg_demand;
	}
	public double getAmps_c_max_avg_demand() {
		return amps_c_max_avg_demand;
	}
	public void setAmps_c_max_avg_demand(double amps_c_max_avg_demand) {
		this.amps_c_max_avg_demand = amps_c_max_avg_demand;
	}
	public double getPositive_watts_3ph_max_avg_demand() {
		return positive_watts_3ph_max_avg_demand;
	}
	public void setPositive_watts_3ph_max_avg_demand(double positive_watts_3ph_max_avg_demand) {
		this.positive_watts_3ph_max_avg_demand = positive_watts_3ph_max_avg_demand;
	}
	public double getPositive_vars_3ph_max_avg_demand() {
		return positive_vars_3ph_max_avg_demand;
	}
	public void setPositive_vars_3ph_max_avg_demand(double positive_vars_3ph_max_avg_demand) {
		this.positive_vars_3ph_max_avg_demand = positive_vars_3ph_max_avg_demand;
	}
	public double getNegative_watts_3ph_max_avg_demand() {
		return negative_watts_3ph_max_avg_demand;
	}
	public void setNegative_watts_3ph_max_avg_demand(double negative_watts_3ph_max_avg_demand) {
		this.negative_watts_3ph_max_avg_demand = negative_watts_3ph_max_avg_demand;
	}
	public double getNegative_vars_3ph_max_avg_demand() {
		return negative_vars_3ph_max_avg_demand;
	}
	public void setNegative_vars_3ph_max_avg_demand(double negative_vars_3ph_max_avg_demand) {
		this.negative_vars_3ph_max_avg_demand = negative_vars_3ph_max_avg_demand;
	}
	public double getVas_3ph_max_avg_demand() {
		return vas_3ph_max_avg_demand;
	}
	public void setVas_3ph_max_avg_demand(double vas_3ph_max_avg_demand) {
		this.vas_3ph_max_avg_demand = vas_3ph_max_avg_demand;
	}
	public double getPositive_pf_3ph_max_avg_demand() {
		return positive_pf_3ph_max_avg_demand;
	}
	public void setPositive_pf_3ph_max_avg_demand(double positive_pf_3ph_max_avg_demand) {
		this.positive_pf_3ph_max_avg_demand = positive_pf_3ph_max_avg_demand;
	}
	public double getNegative_pf_3ph_max_avg_demand() {
		return negative_pf_3ph_max_avg_demand;
	}
	public void setNegative_pf_3ph_max_avg_demand(double negative_pf_3ph_max_avg_demand) {
		this.negative_pf_3ph_max_avg_demand = negative_pf_3ph_max_avg_demand;
	}
	public double getFrequency_max() {
		return frequency_max;
	}
	public void setFrequency_max(double frequency_max) {
		this.frequency_max = frequency_max;
	}
	public double getVolts_a_n_thd() {
		return volts_a_n_thd;
	}
	public void setVolts_a_n_thd(double volts_a_n_thd) {
		this.volts_a_n_thd = volts_a_n_thd;
	}
	public double getVolts_b_n_thd() {
		return volts_b_n_thd;
	}
	public void setVolts_b_n_thd(double volts_b_n_thd) {
		this.volts_b_n_thd = volts_b_n_thd;
	}
	public double getVolts_c_n_thd() {
		return volts_c_n_thd;
	}
	public void setVolts_c_n_thd(double volts_c_n_thd) {
		this.volts_c_n_thd = volts_c_n_thd;
	}
	public double getAmps_a_thd() {
		return amps_a_thd;
	}
	public void setAmps_a_thd(double amps_a_thd) {
		this.amps_a_thd = amps_a_thd;
	}
	public double getAmps_b_thd() {
		return amps_b_thd;
	}
	public void setAmps_b_thd(double amps_b_thd) {
		this.amps_b_thd = amps_b_thd;
	}
	public double getAmps_c_thd() {
		return amps_c_thd;
	}
	public void setAmps_c_thd(double amps_c_thd) {
		this.amps_c_thd = amps_c_thd;
	}
	public double getPhase_a_current_0th() {
		return phase_a_current_0th;
	}
	public void setPhase_a_current_0th(double phase_a_current_0th) {
		this.phase_a_current_0th = phase_a_current_0th;
	}
	public double getPhase_a_current_1st() {
		return phase_a_current_1st;
	}
	public void setPhase_a_current_1st(double phase_a_current_1st) {
		this.phase_a_current_1st = phase_a_current_1st;
	}
	public double getPhase_a_current_2nd() {
		return phase_a_current_2nd;
	}
	public void setPhase_a_current_2nd(double phase_a_current_2nd) {
		this.phase_a_current_2nd = phase_a_current_2nd;
	}
	public double getPhase_a_current_3rd() {
		return phase_a_current_3rd;
	}
	public void setPhase_a_current_3rd(double phase_a_current_3rd) {
		this.phase_a_current_3rd = phase_a_current_3rd;
	}
	public double getPhase_a_current_4th() {
		return phase_a_current_4th;
	}
	public void setPhase_a_current_4th(double phase_a_current_4th) {
		this.phase_a_current_4th = phase_a_current_4th;
	}
	public double getPhase_a_current_5th() {
		return phase_a_current_5th;
	}
	public void setPhase_a_current_5th(double phase_a_current_5th) {
		this.phase_a_current_5th = phase_a_current_5th;
	}
	public double getPhase_a_current_6th() {
		return phase_a_current_6th;
	}
	public void setPhase_a_current_6th(double phase_a_current_6th) {
		this.phase_a_current_6th = phase_a_current_6th;
	}
	public double getPhase_a_current_7th() {
		return phase_a_current_7th;
	}
	public void setPhase_a_current_7th(double phase_a_current_7th) {
		this.phase_a_current_7th = phase_a_current_7th;
	}
	public double getPhase_a_voltage_0th() {
		return phase_a_voltage_0th;
	}
	public void setPhase_a_voltage_0th(double phase_a_voltage_0th) {
		this.phase_a_voltage_0th = phase_a_voltage_0th;
	}
	public double getPhase_a_voltage_1st() {
		return phase_a_voltage_1st;
	}
	public void setPhase_a_voltage_1st(double phase_a_voltage_1st) {
		this.phase_a_voltage_1st = phase_a_voltage_1st;
	}
	public double getPhase_a_voltage_2nd() {
		return phase_a_voltage_2nd;
	}
	public void setPhase_a_voltage_2nd(double phase_a_voltage_2nd) {
		this.phase_a_voltage_2nd = phase_a_voltage_2nd;
	}
	public double getPhase_a_voltage_3rd() {
		return phase_a_voltage_3rd;
	}
	public void setPhase_a_voltage_3rd(double phase_a_voltage_3rd) {
		this.phase_a_voltage_3rd = phase_a_voltage_3rd;
	}
	public double getPhase_b_current_0th() {
		return phase_b_current_0th;
	}
	public void setPhase_b_current_0th(double phase_b_current_0th) {
		this.phase_b_current_0th = phase_b_current_0th;
	}
	public double getPhase_b_current_1st() {
		return phase_b_current_1st;
	}
	public void setPhase_b_current_1st(double phase_b_current_1st) {
		this.phase_b_current_1st = phase_b_current_1st;
	}
	public double getPhase_b_current_2nd() {
		return phase_b_current_2nd;
	}
	public void setPhase_b_current_2nd(double phase_b_current_2nd) {
		this.phase_b_current_2nd = phase_b_current_2nd;
	}
	public double getPhase_b_current_3rd() {
		return phase_b_current_3rd;
	}
	public void setPhase_b_current_3rd(double phase_b_current_3rd) {
		this.phase_b_current_3rd = phase_b_current_3rd;
	}
	public double getPhase_b_current_4th() {
		return phase_b_current_4th;
	}
	public void setPhase_b_current_4th(double phase_b_current_4th) {
		this.phase_b_current_4th = phase_b_current_4th;
	}
	public double getPhase_b_current_5th() {
		return phase_b_current_5th;
	}
	public void setPhase_b_current_5th(double phase_b_current_5th) {
		this.phase_b_current_5th = phase_b_current_5th;
	}
	public double getPhase_b_current_6th() {
		return phase_b_current_6th;
	}
	public void setPhase_b_current_6th(double phase_b_current_6th) {
		this.phase_b_current_6th = phase_b_current_6th;
	}
	public double getPhase_b_current_7th() {
		return phase_b_current_7th;
	}
	public void setPhase_b_current_7th(double phase_b_current_7th) {
		this.phase_b_current_7th = phase_b_current_7th;
	}
	public double getPhase_b_voltage_0th() {
		return phase_b_voltage_0th;
	}
	public void setPhase_b_voltage_0th(double phase_b_voltage_0th) {
		this.phase_b_voltage_0th = phase_b_voltage_0th;
	}
	public double getPhase_b_voltage_1st() {
		return phase_b_voltage_1st;
	}
	public void setPhase_b_voltage_1st(double phase_b_voltage_1st) {
		this.phase_b_voltage_1st = phase_b_voltage_1st;
	}
	public double getPhase_b_voltage_2nd() {
		return phase_b_voltage_2nd;
	}
	public void setPhase_b_voltage_2nd(double phase_b_voltage_2nd) {
		this.phase_b_voltage_2nd = phase_b_voltage_2nd;
	}
	public double getPhase_b_voltage_3rd() {
		return phase_b_voltage_3rd;
	}
	public void setPhase_b_voltage_3rd(double phase_b_voltage_3rd) {
		this.phase_b_voltage_3rd = phase_b_voltage_3rd;
	}
	public double getPhase_c_current_0th() {
		return phase_c_current_0th;
	}
	public void setPhase_c_current_0th(double phase_c_current_0th) {
		this.phase_c_current_0th = phase_c_current_0th;
	}
	public double getPhase_c_current_1st() {
		return phase_c_current_1st;
	}
	public void setPhase_c_current_1st(double phase_c_current_1st) {
		this.phase_c_current_1st = phase_c_current_1st;
	}
	public double getPhase_c_current_2nd() {
		return phase_c_current_2nd;
	}
	public void setPhase_c_current_2nd(double phase_c_current_2nd) {
		this.phase_c_current_2nd = phase_c_current_2nd;
	}
	public double getPhase_c_current_3rd() {
		return phase_c_current_3rd;
	}
	public void setPhase_c_current_3rd(double phase_c_current_3rd) {
		this.phase_c_current_3rd = phase_c_current_3rd;
	}
	public double getPhase_c_current_4th() {
		return phase_c_current_4th;
	}
	public void setPhase_c_current_4th(double phase_c_current_4th) {
		this.phase_c_current_4th = phase_c_current_4th;
	}
	public double getPhase_c_current_5th() {
		return phase_c_current_5th;
	}
	public void setPhase_c_current_5th(double phase_c_current_5th) {
		this.phase_c_current_5th = phase_c_current_5th;
	}
	public double getPhase_c_current_6th() {
		return phase_c_current_6th;
	}
	public void setPhase_c_current_6th(double phase_c_current_6th) {
		this.phase_c_current_6th = phase_c_current_6th;
	}
	public double getPhase_c_current_7th() {
		return phase_c_current_7th;
	}
	public void setPhase_c_current_7th(double phase_c_current_7th) {
		this.phase_c_current_7th = phase_c_current_7th;
	}
	public double getPhase_c_voltage_0th() {
		return phase_c_voltage_0th;
	}
	public void setPhase_c_voltage_0th(double phase_c_voltage_0th) {
		this.phase_c_voltage_0th = phase_c_voltage_0th;
	}
	public double getPhase_c_voltage_1st() {
		return phase_c_voltage_1st;
	}
	public void setPhase_c_voltage_1st(double phase_c_voltage_1st) {
		this.phase_c_voltage_1st = phase_c_voltage_1st;
	}
	public double getPhase_c_voltage_2nd() {
		return phase_c_voltage_2nd;
	}
	public void setPhase_c_voltage_2nd(double phase_c_voltage_2nd) {
		this.phase_c_voltage_2nd = phase_c_voltage_2nd;
	}
	public double getPhase_c_voltage_3rd() {
		return phase_c_voltage_3rd;
	}
	public void setPhase_c_voltage_3rd(double phase_c_voltage_3rd) {
		this.phase_c_voltage_3rd = phase_c_voltage_3rd;
	}
	public double getAngle_phase_a_current() {
		return angle_phase_a_current;
	}
	public void setAngle_phase_a_current(double angle_phase_a_current) {
		this.angle_phase_a_current = angle_phase_a_current;
	}
	public double getAngle_phase_b_current() {
		return angle_phase_b_current;
	}
	public void setAngle_phase_b_current(double angle_phase_b_current) {
		this.angle_phase_b_current = angle_phase_b_current;
	}
	public double getAngle_phase_c_current() {
		return angle_phase_c_current;
	}
	public void setAngle_phase_c_current(double angle_phase_c_current) {
		this.angle_phase_c_current = angle_phase_c_current;
	}
	public double getAngle_volts_a_b() {
		return angle_volts_a_b;
	}
	public void setAngle_volts_a_b(double angle_volts_a_b) {
		this.angle_volts_a_b = angle_volts_a_b;
	}
	public double getAngle_volts_b_c() {
		return angle_volts_b_c;
	}
	public void setAngle_volts_b_c(double angle_volts_b_c) {
		this.angle_volts_b_c = angle_volts_b_c;
	}
	public double getAngle_volts_c_a() {
		return angle_volts_c_a;
	}
	public void setAngle_volts_c_a(double angle_volts_c_a) {
		this.angle_volts_c_a = angle_volts_c_a;
	}
	public double getNvmActivePower() {
		return nvmActivePower;
	}
	public void setNvmActivePower(double nvmActivePower) {
		this.nvmActivePower = nvmActivePower;
	}
	public double getNvmActiveEnergy() {
		return nvmActiveEnergy;
	}
	public void setNvmActiveEnergy(double nvmActiveEnergy) {
		this.nvmActiveEnergy = nvmActiveEnergy;
	}
	
	
	
	
	
}
