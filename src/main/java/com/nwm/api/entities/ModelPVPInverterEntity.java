/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelPVPInverterEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double total_kwh_delivered;
	private double volts_a_l_n;
	private double volts_b_l_n;
	private double volts_c_l_n;
	private double current_a;
	private double current_b;
	private double current_c;
	private double dc_output_voltage;
	private double dc_output_current;
	private double line_frenquency;
	private double line_kw;
	private String inverter_operating_status;
	private double inverter_fault_word0;
	private double inverter_fault_word1;
	private double inverter_fault_word2;
	private String data_comm_status;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	
	
	
	
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
	public double getTotal_kwh_delivered() {
		return total_kwh_delivered;
	}
	public void setTotal_kwh_delivered(double total_kwh_delivered) {
		this.total_kwh_delivered = total_kwh_delivered;
	}
	public double getVolts_a_l_n() {
		return volts_a_l_n;
	}
	public void setVolts_a_l_n(double volts_a_l_n) {
		this.volts_a_l_n = volts_a_l_n;
	}
	public double getVolts_b_l_n() {
		return volts_b_l_n;
	}
	public void setVolts_b_l_n(double volts_b_l_n) {
		this.volts_b_l_n = volts_b_l_n;
	}
	public double getVolts_c_l_n() {
		return volts_c_l_n;
	}
	public void setVolts_c_l_n(double volts_c_l_n) {
		this.volts_c_l_n = volts_c_l_n;
	}
	public double getCurrent_a() {
		return current_a;
	}
	public void setCurrent_a(double current_a) {
		this.current_a = current_a;
	}
	public double getCurrent_b() {
		return current_b;
	}
	public void setCurrent_b(double current_b) {
		this.current_b = current_b;
	}
	public double getCurrent_c() {
		return current_c;
	}
	public void setCurrent_c(double current_c) {
		this.current_c = current_c;
	}
	public double getDc_output_voltage() {
		return dc_output_voltage;
	}
	public void setDc_output_voltage(double dc_output_voltage) {
		this.dc_output_voltage = dc_output_voltage;
	}
	public double getDc_output_current() {
		return dc_output_current;
	}
	public void setDc_output_current(double dc_output_current) {
		this.dc_output_current = dc_output_current;
	}
	public double getLine_frenquency() {
		return line_frenquency;
	}
	public void setLine_frenquency(double line_frenquency) {
		this.line_frenquency = line_frenquency;
	}
	public double getLine_kw() {
		return line_kw;
	}
	public void setLine_kw(double line_kw) {
		this.line_kw = line_kw;
	}
	public String getInverter_operating_status() {
		return inverter_operating_status;
	}
	public void setInverter_operating_status(String inverter_operating_status) {
		this.inverter_operating_status = inverter_operating_status;
	}
	public double getInverter_fault_word0() {
		return inverter_fault_word0;
	}
	public void setInverter_fault_word0(double inverter_fault_word0) {
		this.inverter_fault_word0 = inverter_fault_word0;
	}
	public double getInverter_fault_word1() {
		return inverter_fault_word1;
	}
	public void setInverter_fault_word1(double inverter_fault_word1) {
		this.inverter_fault_word1 = inverter_fault_word1;
	}
	public double getInverter_fault_word2() {
		return inverter_fault_word2;
	}
	public void setInverter_fault_word2(double inverter_fault_word2) {
		this.inverter_fault_word2 = inverter_fault_word2;
	}
	public String getData_comm_status() {
		return data_comm_status;
	}
	public void setData_comm_status(String data_comm_status) {
		this.data_comm_status = data_comm_status;
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
