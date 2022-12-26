/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelShark100TestEntity {
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
	private float amps_a;
	private float amps_b;
	private float amps_c;
	private float watts_3ph_total;
	private float vars_3ph_total;
	private float vas_3ph_total;
	private float power_factor_3ph_total;
	private float frequency;
	private float neutral_current;
	private double w_hours_received;
	private double w_hours_delivered;
	private double w_hours_net;
	private double w_hours_total;
	private double var_hours_positive;
	private double var_hours_negative;
	private double var_hours_net;
	private double var_hours_total;
	private double va_hours_total;
	private float amps_a_average;
	private float amps_b_average;
	private float amps_c_average;
	private float positive_watts_3ph_average;
	private float positive_vars_3ph_average;
	private float negative_watts_3ph_average;
	private float negative_vars_3ph_average;
	private float vas_3ph_average;
	private float positive_pf_3ph_average;
	private float negative_pf_3ph_average;
	private float volts_a_n_min;
	private float volts_b_n_min;
	private float volts_c_n_min;
	private float volts_a_b_min;
	private float volts_b_c_min;
	private float volts_c_a_min;
	private float amps_a_min_avg_demand;
	private float amps_b_min_avg_demand;
	private float amps_c_min_avg_demand;
	private float positive_watts_3ph_min_avg_demand;
	private float positive_vars_3ph_min_avg_demand;
	private float negative_watts_3ph_min_avg_demand;
	private float negative_vars_3ph_min_avg_demand;
	private float vas_3ph_min_avg_demand;
	private float positive_pf_3ph_min_avg_demand;
	private float negative_pf_3ph_min_avg_demand;
	private float frequency_min;
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
	private float positive_pf_3ph_max_avg_demand;
	private float negative_pf_3ph_max_avg_demand;
	private float frequency_max;
	private float volts_a_n_thd;
	private float volts_b_n_thd;
	private float volts_c_n_thd;
	private float amps_a_thd;
	private float amps_b_thd;
	private float amps_c_thd;
	private float phase_a_current_0th;
	private float phase_a_current_1st;
	private float phase_a_current_2nd;
	private float phase_a_current_3rd;
	private float phase_a_current_4th;
	private float phase_a_current_5th;
	private float phase_a_current_6th;
	private float phase_a_current_7th;
	private float phase_a_voltage_0th;
	private float phase_a_voltage_1st;
	private float phase_a_voltage_2nd;
	private float phase_a_voltage_3rd;
	private float phase_b_current_0th;
	private float phase_b_current_1st;
	private float phase_b_current_2nd;
	private float phase_b_current_3rd;
	private float phase_b_current_4th;
	private float phase_b_current_5th;
	private float phase_b_current_6th;
	private float phase_b_current_7th;
	private float phase_b_voltage_0th;
	private float phase_b_voltage_1st;
	private float phase_b_voltage_2nd;
	private float phase_b_voltage_3rd;
	private float phase_c_current_0th;
	private float phase_c_current_1st;
	private float phase_c_current_2nd;
	private float phase_c_current_3rd;
	private float phase_c_current_4th;
	private float phase_c_current_5th;
	private float phase_c_current_6th;
	private float phase_c_current_7th;
	private float phase_c_voltage_0th;
	private float phase_c_voltage_1st;
	private float phase_c_voltage_2nd;
	private float phase_c_voltage_3rd;
	private float angle_phase_a_current;
	private float angle_phase_b_current;
	private float angle_phase_c_current;
	private float angle_volts_a_b;
	private float angle_volts_b_c;
	private float angle_volts_c_a;
	private String ip_address;
	
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the id_device
	 */
	public int getId_device() {
		return id_device;
	}
	/**
	 * @param id_device the id_device to set
	 */
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	/**
	 * @return the error
	 */
	public int getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(int error) {
		this.error = error;
	}
	/**
	 * @return the low_alarm
	 */
	public int getLow_alarm() {
		return low_alarm;
	}
	/**
	 * @param low_alarm the low_alarm to set
	 */
	public void setLow_alarm(int low_alarm) {
		this.low_alarm = low_alarm;
	}
	/**
	 * @return the high_alarm
	 */
	public int getHigh_alarm() {
		return high_alarm;
	}
	/**
	 * @param high_alarm the high_alarm to set
	 */
	public void setHigh_alarm(int high_alarm) {
		this.high_alarm = high_alarm;
	}
	/**
	 * @return the volts_a_n
	 */
	public double getVolts_a_n() {
		return volts_a_n;
	}
	/**
	 * @param volts_a_n the volts_a_n to set
	 */
	public void setVolts_a_n(double volts_a_n) {
		this.volts_a_n = volts_a_n;
	}
	/**
	 * @return the volts_b_n
	 */
	public double getVolts_b_n() {
		return volts_b_n;
	}
	/**
	 * @param volts_b_n the volts_b_n to set
	 */
	public void setVolts_b_n(double volts_b_n) {
		this.volts_b_n = volts_b_n;
	}
	/**
	 * @return the volts_c_n
	 */
	public double getVolts_c_n() {
		return volts_c_n;
	}
	/**
	 * @param volts_c_n the volts_c_n to set
	 */
	public void setVolts_c_n(double volts_c_n) {
		this.volts_c_n = volts_c_n;
	}
	/**
	 * @return the volts_a_b
	 */
	public double getVolts_a_b() {
		return volts_a_b;
	}
	/**
	 * @param volts_a_b the volts_a_b to set
	 */
	public void setVolts_a_b(double volts_a_b) {
		this.volts_a_b = volts_a_b;
	}
	/**
	 * @return the volts_b_c
	 */
	public double getVolts_b_c() {
		return volts_b_c;
	}
	/**
	 * @param volts_b_c the volts_b_c to set
	 */
	public void setVolts_b_c(double volts_b_c) {
		this.volts_b_c = volts_b_c;
	}
	/**
	 * @return the volts_c_a
	 */
	public double getVolts_c_a() {
		return volts_c_a;
	}
	/**
	 * @param volts_c_a the volts_c_a to set
	 */
	public void setVolts_c_a(double volts_c_a) {
		this.volts_c_a = volts_c_a;
	}
	/**
	 * @return the amps_a
	 */
	public float getAmps_a() {
		return amps_a;
	}
	/**
	 * @param amps_a the amps_a to set
	 */
	public void setAmps_a(float amps_a) {
		this.amps_a = amps_a;
	}
	/**
	 * @return the amps_b
	 */
	public float getAmps_b() {
		return amps_b;
	}
	/**
	 * @param amps_b the amps_b to set
	 */
	public void setAmps_b(float amps_b) {
		this.amps_b = amps_b;
	}
	/**
	 * @return the amps_c
	 */
	public float getAmps_c() {
		return amps_c;
	}
	/**
	 * @param amps_c the amps_c to set
	 */
	public void setAmps_c(float amps_c) {
		this.amps_c = amps_c;
	}
	/**
	 * @return the watts_3ph_total
	 */
	public float getWatts_3ph_total() {
		return watts_3ph_total;
	}
	/**
	 * @param watts_3ph_total the watts_3ph_total to set
	 */
	public void setWatts_3ph_total(float watts_3ph_total) {
		this.watts_3ph_total = watts_3ph_total;
	}
	/**
	 * @return the vars_3ph_total
	 */
	public float getVars_3ph_total() {
		return vars_3ph_total;
	}
	/**
	 * @param vars_3ph_total the vars_3ph_total to set
	 */
	public void setVars_3ph_total(float vars_3ph_total) {
		this.vars_3ph_total = vars_3ph_total;
	}
	/**
	 * @return the vas_3ph_total
	 */
	public float getVas_3ph_total() {
		return vas_3ph_total;
	}
	/**
	 * @param vas_3ph_total the vas_3ph_total to set
	 */
	public void setVas_3ph_total(float vas_3ph_total) {
		this.vas_3ph_total = vas_3ph_total;
	}
	/**
	 * @return the power_factor_3ph_total
	 */
	public float getPower_factor_3ph_total() {
		return power_factor_3ph_total;
	}
	/**
	 * @param power_factor_3ph_total the power_factor_3ph_total to set
	 */
	public void setPower_factor_3ph_total(float power_factor_3ph_total) {
		this.power_factor_3ph_total = power_factor_3ph_total;
	}
	/**
	 * @return the frequency
	 */
	public float getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}
	/**
	 * @return the neutral_current
	 */
	public float getNeutral_current() {
		return neutral_current;
	}
	/**
	 * @param neutral_current the neutral_current to set
	 */
	public void setNeutral_current(float neutral_current) {
		this.neutral_current = neutral_current;
	}
	/**
	 * @return the w_hours_received
	 */
	public double getW_hours_received() {
		return w_hours_received;
	}
	/**
	 * @param w_hours_received the w_hours_received to set
	 */
	public void setW_hours_received(double w_hours_received) {
		this.w_hours_received = w_hours_received;
	}
	/**
	 * @return the w_hours_delivered
	 */
	public double getW_hours_delivered() {
		return w_hours_delivered;
	}
	/**
	 * @param w_hours_delivered the w_hours_delivered to set
	 */
	public void setW_hours_delivered(double w_hours_delivered) {
		this.w_hours_delivered = w_hours_delivered;
	}
	/**
	 * @return the w_hours_net
	 */
	public double getW_hours_net() {
		return w_hours_net;
	}
	/**
	 * @param w_hours_net the w_hours_net to set
	 */
	public void setW_hours_net(double w_hours_net) {
		this.w_hours_net = w_hours_net;
	}
	/**
	 * @return the w_hours_total
	 */
	public double getW_hours_total() {
		return w_hours_total;
	}
	/**
	 * @param w_hours_total the w_hours_total to set
	 */
	public void setW_hours_total(double w_hours_total) {
		this.w_hours_total = w_hours_total;
	}
	/**
	 * @return the var_hours_positive
	 */
	public double getVar_hours_positive() {
		return var_hours_positive;
	}
	/**
	 * @param var_hours_positive the var_hours_positive to set
	 */
	public void setVar_hours_positive(double var_hours_positive) {
		this.var_hours_positive = var_hours_positive;
	}
	/**
	 * @return the var_hours_negative
	 */
	public double getVar_hours_negative() {
		return var_hours_negative;
	}
	/**
	 * @param var_hours_negative the var_hours_negative to set
	 */
	public void setVar_hours_negative(double var_hours_negative) {
		this.var_hours_negative = var_hours_negative;
	}
	/**
	 * @return the var_hours_net
	 */
	public double getVar_hours_net() {
		return var_hours_net;
	}
	/**
	 * @param var_hours_net the var_hours_net to set
	 */
	public void setVar_hours_net(double var_hours_net) {
		this.var_hours_net = var_hours_net;
	}
	/**
	 * @return the var_hours_total
	 */
	public double getVar_hours_total() {
		return var_hours_total;
	}
	/**
	 * @param var_hours_total the var_hours_total to set
	 */
	public void setVar_hours_total(double var_hours_total) {
		this.var_hours_total = var_hours_total;
	}
	/**
	 * @return the va_hours_total
	 */
	public double getVa_hours_total() {
		return va_hours_total;
	}
	/**
	 * @param va_hours_total the va_hours_total to set
	 */
	public void setVa_hours_total(double va_hours_total) {
		this.va_hours_total = va_hours_total;
	}
	/**
	 * @return the amps_a_average
	 */
	public float getAmps_a_average() {
		return amps_a_average;
	}
	/**
	 * @param amps_a_average the amps_a_average to set
	 */
	public void setAmps_a_average(float amps_a_average) {
		this.amps_a_average = amps_a_average;
	}
	/**
	 * @return the amps_b_average
	 */
	public float getAmps_b_average() {
		return amps_b_average;
	}
	/**
	 * @param amps_b_average the amps_b_average to set
	 */
	public void setAmps_b_average(float amps_b_average) {
		this.amps_b_average = amps_b_average;
	}
	/**
	 * @return the amps_c_average
	 */
	public float getAmps_c_average() {
		return amps_c_average;
	}
	/**
	 * @param amps_c_average the amps_c_average to set
	 */
	public void setAmps_c_average(float amps_c_average) {
		this.amps_c_average = amps_c_average;
	}
	/**
	 * @return the positive_watts_3ph_average
	 */
	public float getPositive_watts_3ph_average() {
		return positive_watts_3ph_average;
	}
	/**
	 * @param positive_watts_3ph_average the positive_watts_3ph_average to set
	 */
	public void setPositive_watts_3ph_average(float positive_watts_3ph_average) {
		this.positive_watts_3ph_average = positive_watts_3ph_average;
	}
	/**
	 * @return the positive_vars_3ph_average
	 */
	public float getPositive_vars_3ph_average() {
		return positive_vars_3ph_average;
	}
	/**
	 * @param positive_vars_3ph_average the positive_vars_3ph_average to set
	 */
	public void setPositive_vars_3ph_average(float positive_vars_3ph_average) {
		this.positive_vars_3ph_average = positive_vars_3ph_average;
	}
	/**
	 * @return the negative_watts_3ph_average
	 */
	public float getNegative_watts_3ph_average() {
		return negative_watts_3ph_average;
	}
	/**
	 * @param negative_watts_3ph_average the negative_watts_3ph_average to set
	 */
	public void setNegative_watts_3ph_average(float negative_watts_3ph_average) {
		this.negative_watts_3ph_average = negative_watts_3ph_average;
	}
	/**
	 * @return the negative_vars_3ph_average
	 */
	public float getNegative_vars_3ph_average() {
		return negative_vars_3ph_average;
	}
	/**
	 * @param negative_vars_3ph_average the negative_vars_3ph_average to set
	 */
	public void setNegative_vars_3ph_average(float negative_vars_3ph_average) {
		this.negative_vars_3ph_average = negative_vars_3ph_average;
	}
	/**
	 * @return the vas_3ph_average
	 */
	public float getVas_3ph_average() {
		return vas_3ph_average;
	}
	/**
	 * @param vas_3ph_average the vas_3ph_average to set
	 */
	public void setVas_3ph_average(float vas_3ph_average) {
		this.vas_3ph_average = vas_3ph_average;
	}
	/**
	 * @return the positive_pf_3ph_average
	 */
	public float getPositive_pf_3ph_average() {
		return positive_pf_3ph_average;
	}
	/**
	 * @param positive_pf_3ph_average the positive_pf_3ph_average to set
	 */
	public void setPositive_pf_3ph_average(float positive_pf_3ph_average) {
		this.positive_pf_3ph_average = positive_pf_3ph_average;
	}
	/**
	 * @return the negative_pf_3ph_average
	 */
	public float getNegative_pf_3ph_average() {
		return negative_pf_3ph_average;
	}
	/**
	 * @param negative_pf_3ph_average the negative_pf_3ph_average to set
	 */
	public void setNegative_pf_3ph_average(float negative_pf_3ph_average) {
		this.negative_pf_3ph_average = negative_pf_3ph_average;
	}
	/**
	 * @return the volts_a_n_min
	 */
	public float getVolts_a_n_min() {
		return volts_a_n_min;
	}
	/**
	 * @param volts_a_n_min the volts_a_n_min to set
	 */
	public void setVolts_a_n_min(float volts_a_n_min) {
		this.volts_a_n_min = volts_a_n_min;
	}
	/**
	 * @return the volts_b_n_min
	 */
	public float getVolts_b_n_min() {
		return volts_b_n_min;
	}
	/**
	 * @param volts_b_n_min the volts_b_n_min to set
	 */
	public void setVolts_b_n_min(float volts_b_n_min) {
		this.volts_b_n_min = volts_b_n_min;
	}
	/**
	 * @return the volts_c_n_min
	 */
	public float getVolts_c_n_min() {
		return volts_c_n_min;
	}
	/**
	 * @param volts_c_n_min the volts_c_n_min to set
	 */
	public void setVolts_c_n_min(float volts_c_n_min) {
		this.volts_c_n_min = volts_c_n_min;
	}
	/**
	 * @return the volts_a_b_min
	 */
	public float getVolts_a_b_min() {
		return volts_a_b_min;
	}
	/**
	 * @param volts_a_b_min the volts_a_b_min to set
	 */
	public void setVolts_a_b_min(float volts_a_b_min) {
		this.volts_a_b_min = volts_a_b_min;
	}
	/**
	 * @return the volts_b_c_min
	 */
	public float getVolts_b_c_min() {
		return volts_b_c_min;
	}
	/**
	 * @param volts_b_c_min the volts_b_c_min to set
	 */
	public void setVolts_b_c_min(float volts_b_c_min) {
		this.volts_b_c_min = volts_b_c_min;
	}
	/**
	 * @return the volts_c_a_min
	 */
	public float getVolts_c_a_min() {
		return volts_c_a_min;
	}
	/**
	 * @param volts_c_a_min the volts_c_a_min to set
	 */
	public void setVolts_c_a_min(float volts_c_a_min) {
		this.volts_c_a_min = volts_c_a_min;
	}
	/**
	 * @return the amps_a_min_avg_demand
	 */
	public float getAmps_a_min_avg_demand() {
		return amps_a_min_avg_demand;
	}
	/**
	 * @param amps_a_min_avg_demand the amps_a_min_avg_demand to set
	 */
	public void setAmps_a_min_avg_demand(float amps_a_min_avg_demand) {
		this.amps_a_min_avg_demand = amps_a_min_avg_demand;
	}
	/**
	 * @return the amps_b_min_avg_demand
	 */
	public float getAmps_b_min_avg_demand() {
		return amps_b_min_avg_demand;
	}
	/**
	 * @param amps_b_min_avg_demand the amps_b_min_avg_demand to set
	 */
	public void setAmps_b_min_avg_demand(float amps_b_min_avg_demand) {
		this.amps_b_min_avg_demand = amps_b_min_avg_demand;
	}
	/**
	 * @return the amps_c_min_avg_demand
	 */
	public float getAmps_c_min_avg_demand() {
		return amps_c_min_avg_demand;
	}
	/**
	 * @param amps_c_min_avg_demand the amps_c_min_avg_demand to set
	 */
	public void setAmps_c_min_avg_demand(float amps_c_min_avg_demand) {
		this.amps_c_min_avg_demand = amps_c_min_avg_demand;
	}
	/**
	 * @return the positive_watts_3ph_min_avg_demand
	 */
	public float getPositive_watts_3ph_min_avg_demand() {
		return positive_watts_3ph_min_avg_demand;
	}
	/**
	 * @param positive_watts_3ph_min_avg_demand the positive_watts_3ph_min_avg_demand to set
	 */
	public void setPositive_watts_3ph_min_avg_demand(float positive_watts_3ph_min_avg_demand) {
		this.positive_watts_3ph_min_avg_demand = positive_watts_3ph_min_avg_demand;
	}
	/**
	 * @return the positive_vars_3ph_min_avg_demand
	 */
	public float getPositive_vars_3ph_min_avg_demand() {
		return positive_vars_3ph_min_avg_demand;
	}
	/**
	 * @param positive_vars_3ph_min_avg_demand the positive_vars_3ph_min_avg_demand to set
	 */
	public void setPositive_vars_3ph_min_avg_demand(float positive_vars_3ph_min_avg_demand) {
		this.positive_vars_3ph_min_avg_demand = positive_vars_3ph_min_avg_demand;
	}
	/**
	 * @return the negative_watts_3ph_min_avg_demand
	 */
	public float getNegative_watts_3ph_min_avg_demand() {
		return negative_watts_3ph_min_avg_demand;
	}
	/**
	 * @param negative_watts_3ph_min_avg_demand the negative_watts_3ph_min_avg_demand to set
	 */
	public void setNegative_watts_3ph_min_avg_demand(float negative_watts_3ph_min_avg_demand) {
		this.negative_watts_3ph_min_avg_demand = negative_watts_3ph_min_avg_demand;
	}
	/**
	 * @return the negative_vars_3ph_min_avg_demand
	 */
	public float getNegative_vars_3ph_min_avg_demand() {
		return negative_vars_3ph_min_avg_demand;
	}
	/**
	 * @param negative_vars_3ph_min_avg_demand the negative_vars_3ph_min_avg_demand to set
	 */
	public void setNegative_vars_3ph_min_avg_demand(float negative_vars_3ph_min_avg_demand) {
		this.negative_vars_3ph_min_avg_demand = negative_vars_3ph_min_avg_demand;
	}
	/**
	 * @return the vas_3ph_min_avg_demand
	 */
	public float getVas_3ph_min_avg_demand() {
		return vas_3ph_min_avg_demand;
	}
	/**
	 * @param vas_3ph_min_avg_demand the vas_3ph_min_avg_demand to set
	 */
	public void setVas_3ph_min_avg_demand(float vas_3ph_min_avg_demand) {
		this.vas_3ph_min_avg_demand = vas_3ph_min_avg_demand;
	}
	/**
	 * @return the positive_pf_3ph_min_avg_demand
	 */
	public float getPositive_pf_3ph_min_avg_demand() {
		return positive_pf_3ph_min_avg_demand;
	}
	/**
	 * @param positive_pf_3ph_min_avg_demand the positive_pf_3ph_min_avg_demand to set
	 */
	public void setPositive_pf_3ph_min_avg_demand(float positive_pf_3ph_min_avg_demand) {
		this.positive_pf_3ph_min_avg_demand = positive_pf_3ph_min_avg_demand;
	}
	/**
	 * @return the negative_pf_3ph_min_avg_demand
	 */
	public float getNegative_pf_3ph_min_avg_demand() {
		return negative_pf_3ph_min_avg_demand;
	}
	/**
	 * @param negative_pf_3ph_min_avg_demand the negative_pf_3ph_min_avg_demand to set
	 */
	public void setNegative_pf_3ph_min_avg_demand(float negative_pf_3ph_min_avg_demand) {
		this.negative_pf_3ph_min_avg_demand = negative_pf_3ph_min_avg_demand;
	}
	/**
	 * @return the frequency_min
	 */
	public float getFrequency_min() {
		return frequency_min;
	}
	/**
	 * @param frequency_min the frequency_min to set
	 */
	public void setFrequency_min(float frequency_min) {
		this.frequency_min = frequency_min;
	}
	/**
	 * @return the volts_a_n_max
	 */
	public double getVolts_a_n_max() {
		return volts_a_n_max;
	}
	/**
	 * @param volts_a_n_max the volts_a_n_max to set
	 */
	public void setVolts_a_n_max(double volts_a_n_max) {
		this.volts_a_n_max = volts_a_n_max;
	}
	/**
	 * @return the volts_b_n_max
	 */
	public double getVolts_b_n_max() {
		return volts_b_n_max;
	}
	/**
	 * @param volts_b_n_max the volts_b_n_max to set
	 */
	public void setVolts_b_n_max(double volts_b_n_max) {
		this.volts_b_n_max = volts_b_n_max;
	}
	/**
	 * @return the volts_c_n_max
	 */
	public double getVolts_c_n_max() {
		return volts_c_n_max;
	}
	/**
	 * @param volts_c_n_max the volts_c_n_max to set
	 */
	public void setVolts_c_n_max(double volts_c_n_max) {
		this.volts_c_n_max = volts_c_n_max;
	}
	/**
	 * @return the volts_a_b_max
	 */
	public double getVolts_a_b_max() {
		return volts_a_b_max;
	}
	/**
	 * @param volts_a_b_max the volts_a_b_max to set
	 */
	public void setVolts_a_b_max(double volts_a_b_max) {
		this.volts_a_b_max = volts_a_b_max;
	}
	/**
	 * @return the volts_b_c_max
	 */
	public double getVolts_b_c_max() {
		return volts_b_c_max;
	}
	/**
	 * @param volts_b_c_max the volts_b_c_max to set
	 */
	public void setVolts_b_c_max(double volts_b_c_max) {
		this.volts_b_c_max = volts_b_c_max;
	}
	/**
	 * @return the volts_c_a_max
	 */
	public double getVolts_c_a_max() {
		return volts_c_a_max;
	}
	/**
	 * @param volts_c_a_max the volts_c_a_max to set
	 */
	public void setVolts_c_a_max(double volts_c_a_max) {
		this.volts_c_a_max = volts_c_a_max;
	}
	/**
	 * @return the amps_a_max_avg_demand
	 */
	public double getAmps_a_max_avg_demand() {
		return amps_a_max_avg_demand;
	}
	/**
	 * @param amps_a_max_avg_demand the amps_a_max_avg_demand to set
	 */
	public void setAmps_a_max_avg_demand(double amps_a_max_avg_demand) {
		this.amps_a_max_avg_demand = amps_a_max_avg_demand;
	}
	/**
	 * @return the amps_b_max_avg_demand
	 */
	public double getAmps_b_max_avg_demand() {
		return amps_b_max_avg_demand;
	}
	/**
	 * @param amps_b_max_avg_demand the amps_b_max_avg_demand to set
	 */
	public void setAmps_b_max_avg_demand(double amps_b_max_avg_demand) {
		this.amps_b_max_avg_demand = amps_b_max_avg_demand;
	}
	/**
	 * @return the amps_c_max_avg_demand
	 */
	public double getAmps_c_max_avg_demand() {
		return amps_c_max_avg_demand;
	}
	/**
	 * @param amps_c_max_avg_demand the amps_c_max_avg_demand to set
	 */
	public void setAmps_c_max_avg_demand(double amps_c_max_avg_demand) {
		this.amps_c_max_avg_demand = amps_c_max_avg_demand;
	}
	/**
	 * @return the positive_watts_3ph_max_avg_demand
	 */
	public double getPositive_watts_3ph_max_avg_demand() {
		return positive_watts_3ph_max_avg_demand;
	}
	/**
	 * @param positive_watts_3ph_max_avg_demand the positive_watts_3ph_max_avg_demand to set
	 */
	public void setPositive_watts_3ph_max_avg_demand(double positive_watts_3ph_max_avg_demand) {
		this.positive_watts_3ph_max_avg_demand = positive_watts_3ph_max_avg_demand;
	}
	/**
	 * @return the positive_vars_3ph_max_avg_demand
	 */
	public double getPositive_vars_3ph_max_avg_demand() {
		return positive_vars_3ph_max_avg_demand;
	}
	/**
	 * @param positive_vars_3ph_max_avg_demand the positive_vars_3ph_max_avg_demand to set
	 */
	public void setPositive_vars_3ph_max_avg_demand(double positive_vars_3ph_max_avg_demand) {
		this.positive_vars_3ph_max_avg_demand = positive_vars_3ph_max_avg_demand;
	}
	/**
	 * @return the negative_watts_3ph_max_avg_demand
	 */
	public double getNegative_watts_3ph_max_avg_demand() {
		return negative_watts_3ph_max_avg_demand;
	}
	/**
	 * @param negative_watts_3ph_max_avg_demand the negative_watts_3ph_max_avg_demand to set
	 */
	public void setNegative_watts_3ph_max_avg_demand(double negative_watts_3ph_max_avg_demand) {
		this.negative_watts_3ph_max_avg_demand = negative_watts_3ph_max_avg_demand;
	}
	/**
	 * @return the negative_vars_3ph_max_avg_demand
	 */
	public double getNegative_vars_3ph_max_avg_demand() {
		return negative_vars_3ph_max_avg_demand;
	}
	/**
	 * @param negative_vars_3ph_max_avg_demand the negative_vars_3ph_max_avg_demand to set
	 */
	public void setNegative_vars_3ph_max_avg_demand(double negative_vars_3ph_max_avg_demand) {
		this.negative_vars_3ph_max_avg_demand = negative_vars_3ph_max_avg_demand;
	}
	/**
	 * @return the vas_3ph_max_avg_demand
	 */
	public double getVas_3ph_max_avg_demand() {
		return vas_3ph_max_avg_demand;
	}
	/**
	 * @param vas_3ph_max_avg_demand the vas_3ph_max_avg_demand to set
	 */
	public void setVas_3ph_max_avg_demand(double vas_3ph_max_avg_demand) {
		this.vas_3ph_max_avg_demand = vas_3ph_max_avg_demand;
	}
	/**
	 * @return the positive_pf_3ph_max_avg_demand
	 */
	public float getPositive_pf_3ph_max_avg_demand() {
		return positive_pf_3ph_max_avg_demand;
	}
	/**
	 * @param positive_pf_3ph_max_avg_demand the positive_pf_3ph_max_avg_demand to set
	 */
	public void setPositive_pf_3ph_max_avg_demand(float positive_pf_3ph_max_avg_demand) {
		this.positive_pf_3ph_max_avg_demand = positive_pf_3ph_max_avg_demand;
	}
	/**
	 * @return the negative_pf_3ph_max_avg_demand
	 */
	public float getNegative_pf_3ph_max_avg_demand() {
		return negative_pf_3ph_max_avg_demand;
	}
	/**
	 * @param negative_pf_3ph_max_avg_demand the negative_pf_3ph_max_avg_demand to set
	 */
	public void setNegative_pf_3ph_max_avg_demand(float negative_pf_3ph_max_avg_demand) {
		this.negative_pf_3ph_max_avg_demand = negative_pf_3ph_max_avg_demand;
	}
	/**
	 * @return the frequency_max
	 */
	public float getFrequency_max() {
		return frequency_max;
	}
	/**
	 * @param frequency_max the frequency_max to set
	 */
	public void setFrequency_max(float frequency_max) {
		this.frequency_max = frequency_max;
	}
	/**
	 * @return the volts_a_n_thd
	 */
	public float getVolts_a_n_thd() {
		return volts_a_n_thd;
	}
	/**
	 * @param volts_a_n_thd the volts_a_n_thd to set
	 */
	public void setVolts_a_n_thd(float volts_a_n_thd) {
		this.volts_a_n_thd = volts_a_n_thd;
	}
	/**
	 * @return the volts_b_n_thd
	 */
	public float getVolts_b_n_thd() {
		return volts_b_n_thd;
	}
	/**
	 * @param volts_b_n_thd the volts_b_n_thd to set
	 */
	public void setVolts_b_n_thd(float volts_b_n_thd) {
		this.volts_b_n_thd = volts_b_n_thd;
	}
	/**
	 * @return the volts_c_n_thd
	 */
	public float getVolts_c_n_thd() {
		return volts_c_n_thd;
	}
	/**
	 * @param volts_c_n_thd the volts_c_n_thd to set
	 */
	public void setVolts_c_n_thd(float volts_c_n_thd) {
		this.volts_c_n_thd = volts_c_n_thd;
	}
	/**
	 * @return the amps_a_thd
	 */
	public float getAmps_a_thd() {
		return amps_a_thd;
	}
	/**
	 * @param amps_a_thd the amps_a_thd to set
	 */
	public void setAmps_a_thd(float amps_a_thd) {
		this.amps_a_thd = amps_a_thd;
	}
	/**
	 * @return the amps_b_thd
	 */
	public float getAmps_b_thd() {
		return amps_b_thd;
	}
	/**
	 * @param amps_b_thd the amps_b_thd to set
	 */
	public void setAmps_b_thd(float amps_b_thd) {
		this.amps_b_thd = amps_b_thd;
	}
	/**
	 * @return the amps_c_thd
	 */
	public float getAmps_c_thd() {
		return amps_c_thd;
	}
	/**
	 * @param amps_c_thd the amps_c_thd to set
	 */
	public void setAmps_c_thd(float amps_c_thd) {
		this.amps_c_thd = amps_c_thd;
	}
	/**
	 * @return the phase_a_current_0th
	 */
	public float getPhase_a_current_0th() {
		return phase_a_current_0th;
	}
	/**
	 * @param phase_a_current_0th the phase_a_current_0th to set
	 */
	public void setPhase_a_current_0th(float phase_a_current_0th) {
		this.phase_a_current_0th = phase_a_current_0th;
	}
	/**
	 * @return the phase_a_current_1st
	 */
	public float getPhase_a_current_1st() {
		return phase_a_current_1st;
	}
	/**
	 * @param phase_a_current_1st the phase_a_current_1st to set
	 */
	public void setPhase_a_current_1st(float phase_a_current_1st) {
		this.phase_a_current_1st = phase_a_current_1st;
	}
	/**
	 * @return the phase_a_current_2nd
	 */
	public float getPhase_a_current_2nd() {
		return phase_a_current_2nd;
	}
	/**
	 * @param phase_a_current_2nd the phase_a_current_2nd to set
	 */
	public void setPhase_a_current_2nd(float phase_a_current_2nd) {
		this.phase_a_current_2nd = phase_a_current_2nd;
	}
	/**
	 * @return the phase_a_current_3rd
	 */
	public float getPhase_a_current_3rd() {
		return phase_a_current_3rd;
	}
	/**
	 * @param phase_a_current_3rd the phase_a_current_3rd to set
	 */
	public void setPhase_a_current_3rd(float phase_a_current_3rd) {
		this.phase_a_current_3rd = phase_a_current_3rd;
	}
	/**
	 * @return the phase_a_current_4th
	 */
	public float getPhase_a_current_4th() {
		return phase_a_current_4th;
	}
	/**
	 * @param phase_a_current_4th the phase_a_current_4th to set
	 */
	public void setPhase_a_current_4th(float phase_a_current_4th) {
		this.phase_a_current_4th = phase_a_current_4th;
	}
	/**
	 * @return the phase_a_current_5th
	 */
	public float getPhase_a_current_5th() {
		return phase_a_current_5th;
	}
	/**
	 * @param phase_a_current_5th the phase_a_current_5th to set
	 */
	public void setPhase_a_current_5th(float phase_a_current_5th) {
		this.phase_a_current_5th = phase_a_current_5th;
	}
	/**
	 * @return the phase_a_current_6th
	 */
	public float getPhase_a_current_6th() {
		return phase_a_current_6th;
	}
	/**
	 * @param phase_a_current_6th the phase_a_current_6th to set
	 */
	public void setPhase_a_current_6th(float phase_a_current_6th) {
		this.phase_a_current_6th = phase_a_current_6th;
	}
	/**
	 * @return the phase_a_current_7th
	 */
	public float getPhase_a_current_7th() {
		return phase_a_current_7th;
	}
	/**
	 * @param phase_a_current_7th the phase_a_current_7th to set
	 */
	public void setPhase_a_current_7th(float phase_a_current_7th) {
		this.phase_a_current_7th = phase_a_current_7th;
	}
	/**
	 * @return the phase_a_voltage_0th
	 */
	public float getPhase_a_voltage_0th() {
		return phase_a_voltage_0th;
	}
	/**
	 * @param phase_a_voltage_0th the phase_a_voltage_0th to set
	 */
	public void setPhase_a_voltage_0th(float phase_a_voltage_0th) {
		this.phase_a_voltage_0th = phase_a_voltage_0th;
	}
	/**
	 * @return the phase_a_voltage_1st
	 */
	public float getPhase_a_voltage_1st() {
		return phase_a_voltage_1st;
	}
	/**
	 * @param phase_a_voltage_1st the phase_a_voltage_1st to set
	 */
	public void setPhase_a_voltage_1st(float phase_a_voltage_1st) {
		this.phase_a_voltage_1st = phase_a_voltage_1st;
	}
	/**
	 * @return the phase_a_voltage_2nd
	 */
	public float getPhase_a_voltage_2nd() {
		return phase_a_voltage_2nd;
	}
	/**
	 * @param phase_a_voltage_2nd the phase_a_voltage_2nd to set
	 */
	public void setPhase_a_voltage_2nd(float phase_a_voltage_2nd) {
		this.phase_a_voltage_2nd = phase_a_voltage_2nd;
	}
	/**
	 * @return the phase_a_voltage_3rd
	 */
	public float getPhase_a_voltage_3rd() {
		return phase_a_voltage_3rd;
	}
	/**
	 * @param phase_a_voltage_3rd the phase_a_voltage_3rd to set
	 */
	public void setPhase_a_voltage_3rd(float phase_a_voltage_3rd) {
		this.phase_a_voltage_3rd = phase_a_voltage_3rd;
	}
	/**
	 * @return the phase_b_current_0th
	 */
	public float getPhase_b_current_0th() {
		return phase_b_current_0th;
	}
	/**
	 * @param phase_b_current_0th the phase_b_current_0th to set
	 */
	public void setPhase_b_current_0th(float phase_b_current_0th) {
		this.phase_b_current_0th = phase_b_current_0th;
	}
	/**
	 * @return the phase_b_current_1st
	 */
	public float getPhase_b_current_1st() {
		return phase_b_current_1st;
	}
	/**
	 * @param phase_b_current_1st the phase_b_current_1st to set
	 */
	public void setPhase_b_current_1st(float phase_b_current_1st) {
		this.phase_b_current_1st = phase_b_current_1st;
	}
	/**
	 * @return the phase_b_current_2nd
	 */
	public float getPhase_b_current_2nd() {
		return phase_b_current_2nd;
	}
	/**
	 * @param phase_b_current_2nd the phase_b_current_2nd to set
	 */
	public void setPhase_b_current_2nd(float phase_b_current_2nd) {
		this.phase_b_current_2nd = phase_b_current_2nd;
	}
	/**
	 * @return the phase_b_current_3rd
	 */
	public float getPhase_b_current_3rd() {
		return phase_b_current_3rd;
	}
	/**
	 * @param phase_b_current_3rd the phase_b_current_3rd to set
	 */
	public void setPhase_b_current_3rd(float phase_b_current_3rd) {
		this.phase_b_current_3rd = phase_b_current_3rd;
	}
	/**
	 * @return the phase_b_current_4th
	 */
	public float getPhase_b_current_4th() {
		return phase_b_current_4th;
	}
	/**
	 * @param phase_b_current_4th the phase_b_current_4th to set
	 */
	public void setPhase_b_current_4th(float phase_b_current_4th) {
		this.phase_b_current_4th = phase_b_current_4th;
	}
	/**
	 * @return the phase_b_current_5th
	 */
	public float getPhase_b_current_5th() {
		return phase_b_current_5th;
	}
	/**
	 * @param phase_b_current_5th the phase_b_current_5th to set
	 */
	public void setPhase_b_current_5th(float phase_b_current_5th) {
		this.phase_b_current_5th = phase_b_current_5th;
	}
	/**
	 * @return the phase_b_current_6th
	 */
	public float getPhase_b_current_6th() {
		return phase_b_current_6th;
	}
	/**
	 * @param phase_b_current_6th the phase_b_current_6th to set
	 */
	public void setPhase_b_current_6th(float phase_b_current_6th) {
		this.phase_b_current_6th = phase_b_current_6th;
	}
	/**
	 * @return the phase_b_current_7th
	 */
	public float getPhase_b_current_7th() {
		return phase_b_current_7th;
	}
	/**
	 * @param phase_b_current_7th the phase_b_current_7th to set
	 */
	public void setPhase_b_current_7th(float phase_b_current_7th) {
		this.phase_b_current_7th = phase_b_current_7th;
	}
	/**
	 * @return the phase_b_voltage_0th
	 */
	public float getPhase_b_voltage_0th() {
		return phase_b_voltage_0th;
	}
	/**
	 * @param phase_b_voltage_0th the phase_b_voltage_0th to set
	 */
	public void setPhase_b_voltage_0th(float phase_b_voltage_0th) {
		this.phase_b_voltage_0th = phase_b_voltage_0th;
	}
	/**
	 * @return the phase_b_voltage_1st
	 */
	public float getPhase_b_voltage_1st() {
		return phase_b_voltage_1st;
	}
	/**
	 * @param phase_b_voltage_1st the phase_b_voltage_1st to set
	 */
	public void setPhase_b_voltage_1st(float phase_b_voltage_1st) {
		this.phase_b_voltage_1st = phase_b_voltage_1st;
	}
	/**
	 * @return the phase_b_voltage_2nd
	 */
	public float getPhase_b_voltage_2nd() {
		return phase_b_voltage_2nd;
	}
	/**
	 * @param phase_b_voltage_2nd the phase_b_voltage_2nd to set
	 */
	public void setPhase_b_voltage_2nd(float phase_b_voltage_2nd) {
		this.phase_b_voltage_2nd = phase_b_voltage_2nd;
	}
	/**
	 * @return the phase_b_voltage_3rd
	 */
	public float getPhase_b_voltage_3rd() {
		return phase_b_voltage_3rd;
	}
	/**
	 * @param phase_b_voltage_3rd the phase_b_voltage_3rd to set
	 */
	public void setPhase_b_voltage_3rd(float phase_b_voltage_3rd) {
		this.phase_b_voltage_3rd = phase_b_voltage_3rd;
	}
	/**
	 * @return the phase_c_current_0th
	 */
	public float getPhase_c_current_0th() {
		return phase_c_current_0th;
	}
	/**
	 * @param phase_c_current_0th the phase_c_current_0th to set
	 */
	public void setPhase_c_current_0th(float phase_c_current_0th) {
		this.phase_c_current_0th = phase_c_current_0th;
	}
	/**
	 * @return the phase_c_current_1st
	 */
	public float getPhase_c_current_1st() {
		return phase_c_current_1st;
	}
	/**
	 * @param phase_c_current_1st the phase_c_current_1st to set
	 */
	public void setPhase_c_current_1st(float phase_c_current_1st) {
		this.phase_c_current_1st = phase_c_current_1st;
	}
	/**
	 * @return the phase_c_current_2nd
	 */
	public float getPhase_c_current_2nd() {
		return phase_c_current_2nd;
	}
	/**
	 * @param phase_c_current_2nd the phase_c_current_2nd to set
	 */
	public void setPhase_c_current_2nd(float phase_c_current_2nd) {
		this.phase_c_current_2nd = phase_c_current_2nd;
	}
	/**
	 * @return the phase_c_current_3rd
	 */
	public float getPhase_c_current_3rd() {
		return phase_c_current_3rd;
	}
	/**
	 * @param phase_c_current_3rd the phase_c_current_3rd to set
	 */
	public void setPhase_c_current_3rd(float phase_c_current_3rd) {
		this.phase_c_current_3rd = phase_c_current_3rd;
	}
	/**
	 * @return the phase_c_current_4th
	 */
	public float getPhase_c_current_4th() {
		return phase_c_current_4th;
	}
	/**
	 * @param phase_c_current_4th the phase_c_current_4th to set
	 */
	public void setPhase_c_current_4th(float phase_c_current_4th) {
		this.phase_c_current_4th = phase_c_current_4th;
	}
	/**
	 * @return the phase_c_current_5th
	 */
	public float getPhase_c_current_5th() {
		return phase_c_current_5th;
	}
	/**
	 * @param phase_c_current_5th the phase_c_current_5th to set
	 */
	public void setPhase_c_current_5th(float phase_c_current_5th) {
		this.phase_c_current_5th = phase_c_current_5th;
	}
	/**
	 * @return the phase_c_current_6th
	 */
	public float getPhase_c_current_6th() {
		return phase_c_current_6th;
	}
	/**
	 * @param phase_c_current_6th the phase_c_current_6th to set
	 */
	public void setPhase_c_current_6th(float phase_c_current_6th) {
		this.phase_c_current_6th = phase_c_current_6th;
	}
	/**
	 * @return the phase_c_current_7th
	 */
	public float getPhase_c_current_7th() {
		return phase_c_current_7th;
	}
	/**
	 * @param phase_c_current_7th the phase_c_current_7th to set
	 */
	public void setPhase_c_current_7th(float phase_c_current_7th) {
		this.phase_c_current_7th = phase_c_current_7th;
	}
	/**
	 * @return the phase_c_voltage_0th
	 */
	public float getPhase_c_voltage_0th() {
		return phase_c_voltage_0th;
	}
	/**
	 * @param phase_c_voltage_0th the phase_c_voltage_0th to set
	 */
	public void setPhase_c_voltage_0th(float phase_c_voltage_0th) {
		this.phase_c_voltage_0th = phase_c_voltage_0th;
	}
	/**
	 * @return the phase_c_voltage_1st
	 */
	public float getPhase_c_voltage_1st() {
		return phase_c_voltage_1st;
	}
	/**
	 * @param phase_c_voltage_1st the phase_c_voltage_1st to set
	 */
	public void setPhase_c_voltage_1st(float phase_c_voltage_1st) {
		this.phase_c_voltage_1st = phase_c_voltage_1st;
	}
	/**
	 * @return the phase_c_voltage_2nd
	 */
	public float getPhase_c_voltage_2nd() {
		return phase_c_voltage_2nd;
	}
	/**
	 * @param phase_c_voltage_2nd the phase_c_voltage_2nd to set
	 */
	public void setPhase_c_voltage_2nd(float phase_c_voltage_2nd) {
		this.phase_c_voltage_2nd = phase_c_voltage_2nd;
	}
	/**
	 * @return the phase_c_voltage_3rd
	 */
	public float getPhase_c_voltage_3rd() {
		return phase_c_voltage_3rd;
	}
	/**
	 * @param phase_c_voltage_3rd the phase_c_voltage_3rd to set
	 */
	public void setPhase_c_voltage_3rd(float phase_c_voltage_3rd) {
		this.phase_c_voltage_3rd = phase_c_voltage_3rd;
	}
	/**
	 * @return the angle_phase_a_current
	 */
	public float getAngle_phase_a_current() {
		return angle_phase_a_current;
	}
	/**
	 * @param angle_phase_a_current the angle_phase_a_current to set
	 */
	public void setAngle_phase_a_current(float angle_phase_a_current) {
		this.angle_phase_a_current = angle_phase_a_current;
	}
	/**
	 * @return the angle_phase_b_current
	 */
	public float getAngle_phase_b_current() {
		return angle_phase_b_current;
	}
	/**
	 * @param angle_phase_b_current the angle_phase_b_current to set
	 */
	public void setAngle_phase_b_current(float angle_phase_b_current) {
		this.angle_phase_b_current = angle_phase_b_current;
	}
	/**
	 * @return the angle_phase_c_current
	 */
	public float getAngle_phase_c_current() {
		return angle_phase_c_current;
	}
	/**
	 * @param angle_phase_c_current the angle_phase_c_current to set
	 */
	public void setAngle_phase_c_current(float angle_phase_c_current) {
		this.angle_phase_c_current = angle_phase_c_current;
	}
	/**
	 * @return the angle_volts_a_b
	 */
	public float getAngle_volts_a_b() {
		return angle_volts_a_b;
	}
	/**
	 * @param angle_volts_a_b the angle_volts_a_b to set
	 */
	public void setAngle_volts_a_b(float angle_volts_a_b) {
		this.angle_volts_a_b = angle_volts_a_b;
	}
	/**
	 * @return the angle_volts_b_c
	 */
	public float getAngle_volts_b_c() {
		return angle_volts_b_c;
	}
	/**
	 * @param angle_volts_b_c the angle_volts_b_c to set
	 */
	public void setAngle_volts_b_c(float angle_volts_b_c) {
		this.angle_volts_b_c = angle_volts_b_c;
	}
	/**
	 * @return the angle_volts_c_a
	 */
	public float getAngle_volts_c_a() {
		return angle_volts_c_a;
	}
	/**
	 * @param angle_volts_c_a the angle_volts_c_a to set
	 */
	public void setAngle_volts_c_a(float angle_volts_c_a) {
		this.angle_volts_c_a = angle_volts_c_a;
	}
	
	
	
	
}
