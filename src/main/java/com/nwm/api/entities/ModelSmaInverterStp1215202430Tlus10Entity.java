/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSmaInverterStp1215202430Tlus10Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double Power;
	private double Power_L1;
	private double Power_L2;
	private double Power_L3;
	private double Grid_voltage_phase_L1;
	private double Grid_voltage_phase_L2;
	private double Grid_voltage_phase_L3;
	private double Grid_current_phase_L1;
	private double Grid_current_phase_L2;
	private double Grid_current_phase_L3;
	private double Grid_frequency;
	private double Reactive_power;
	private double Reactive_power_L1;
	private double Reactive_power_L2;
	private double Reactive_power_L3;
	private double Apparent_power;
	private double Apparent_power_L1;
	private double Apparent_power_L2;
	private double Apparent_power_L3;
	private double Daily_yield;
	private double Total_yield;
	private double Operating_time;
	private double Feed_in_time;
	private double DC_current_input_1;
	private double DC_current_input_2;
	private double DC_voltage_input_1;
	private double DC_voltage_input_2;
	private double DC_power_input_1;
	private double DC_power_input_2;
	private double Displacement_Power_Factor;
	private double Event_Message;
	private double Serial_Number;
	
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	private int enable_alert;
	
	
	public double getSerial_Number() {
		return Serial_Number;
	}
	public void setSerial_Number(double serial_Number) {
		Serial_Number = serial_Number;
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
	public double getPower() {
		return Power;
	}
	public void setPower(double power) {
		Power = power;
	}
	public double getPower_L1() {
		return Power_L1;
	}
	public void setPower_L1(double power_L1) {
		Power_L1 = power_L1;
	}
	public double getPower_L2() {
		return Power_L2;
	}
	public void setPower_L2(double power_L2) {
		Power_L2 = power_L2;
	}
	public double getPower_L3() {
		return Power_L3;
	}
	public void setPower_L3(double power_L3) {
		Power_L3 = power_L3;
	}
	public double getGrid_voltage_phase_L1() {
		return Grid_voltage_phase_L1;
	}
	public void setGrid_voltage_phase_L1(double grid_voltage_phase_L1) {
		Grid_voltage_phase_L1 = grid_voltage_phase_L1;
	}
	public double getGrid_voltage_phase_L2() {
		return Grid_voltage_phase_L2;
	}
	public void setGrid_voltage_phase_L2(double grid_voltage_phase_L2) {
		Grid_voltage_phase_L2 = grid_voltage_phase_L2;
	}
	public double getGrid_voltage_phase_L3() {
		return Grid_voltage_phase_L3;
	}
	public void setGrid_voltage_phase_L3(double grid_voltage_phase_L3) {
		Grid_voltage_phase_L3 = grid_voltage_phase_L3;
	}
	public double getGrid_current_phase_L1() {
		return Grid_current_phase_L1;
	}
	public void setGrid_current_phase_L1(double grid_current_phase_L1) {
		Grid_current_phase_L1 = grid_current_phase_L1;
	}
	public double getGrid_current_phase_L2() {
		return Grid_current_phase_L2;
	}
	public void setGrid_current_phase_L2(double grid_current_phase_L2) {
		Grid_current_phase_L2 = grid_current_phase_L2;
	}
	public double getGrid_current_phase_L3() {
		return Grid_current_phase_L3;
	}
	public void setGrid_current_phase_L3(double grid_current_phase_L3) {
		Grid_current_phase_L3 = grid_current_phase_L3;
	}
	public double getGrid_frequency() {
		return Grid_frequency;
	}
	public void setGrid_frequency(double grid_frequency) {
		Grid_frequency = grid_frequency;
	}
	public double getReactive_power() {
		return Reactive_power;
	}
	public void setReactive_power(double reactive_power) {
		Reactive_power = reactive_power;
	}
	public double getReactive_power_L1() {
		return Reactive_power_L1;
	}
	public void setReactive_power_L1(double reactive_power_L1) {
		Reactive_power_L1 = reactive_power_L1;
	}
	public double getReactive_power_L2() {
		return Reactive_power_L2;
	}
	public void setReactive_power_L2(double reactive_power_L2) {
		Reactive_power_L2 = reactive_power_L2;
	}
	public double getReactive_power_L3() {
		return Reactive_power_L3;
	}
	public void setReactive_power_L3(double reactive_power_L3) {
		Reactive_power_L3 = reactive_power_L3;
	}
	public double getApparent_power() {
		return Apparent_power;
	}
	public void setApparent_power(double apparent_power) {
		Apparent_power = apparent_power;
	}
	public double getApparent_power_L1() {
		return Apparent_power_L1;
	}
	public void setApparent_power_L1(double apparent_power_L1) {
		Apparent_power_L1 = apparent_power_L1;
	}
	public double getApparent_power_L2() {
		return Apparent_power_L2;
	}
	public void setApparent_power_L2(double apparent_power_L2) {
		Apparent_power_L2 = apparent_power_L2;
	}
	public double getApparent_power_L3() {
		return Apparent_power_L3;
	}
	public void setApparent_power_L3(double apparent_power_L3) {
		Apparent_power_L3 = apparent_power_L3;
	}
	public double getDaily_yield() {
		return Daily_yield;
	}
	public void setDaily_yield(double daily_yield) {
		Daily_yield = daily_yield;
	}
	public double getTotal_yield() {
		return Total_yield;
	}
	public void setTotal_yield(double total_yield) {
		Total_yield = total_yield;
	}
	public double getOperating_time() {
		return Operating_time;
	}
	public void setOperating_time(double operating_time) {
		Operating_time = operating_time;
	}
	public double getFeed_in_time() {
		return Feed_in_time;
	}
	public void setFeed_in_time(double feed_in_time) {
		Feed_in_time = feed_in_time;
	}
	public double getDC_current_input_1() {
		return DC_current_input_1;
	}
	public void setDC_current_input_1(double dC_current_input_1) {
		DC_current_input_1 = dC_current_input_1;
	}
	public double getDC_current_input_2() {
		return DC_current_input_2;
	}
	public void setDC_current_input_2(double dC_current_input_2) {
		DC_current_input_2 = dC_current_input_2;
	}
	public double getDC_voltage_input_1() {
		return DC_voltage_input_1;
	}
	public void setDC_voltage_input_1(double dC_voltage_input_1) {
		DC_voltage_input_1 = dC_voltage_input_1;
	}
	public double getDC_voltage_input_2() {
		return DC_voltage_input_2;
	}
	public void setDC_voltage_input_2(double dC_voltage_input_2) {
		DC_voltage_input_2 = dC_voltage_input_2;
	}
	public double getDC_power_input_1() {
		return DC_power_input_1;
	}
	public void setDC_power_input_1(double dC_power_input_1) {
		DC_power_input_1 = dC_power_input_1;
	}
	public double getDC_power_input_2() {
		return DC_power_input_2;
	}
	public void setDC_power_input_2(double dC_power_input_2) {
		DC_power_input_2 = dC_power_input_2;
	}
	public double getDisplacement_Power_Factor() {
		return Displacement_Power_Factor;
	}
	public void setDisplacement_Power_Factor(double displacement_Power_Factor) {
		Displacement_Power_Factor = displacement_Power_Factor;
	}
	public double getEvent_Message() {
		return Event_Message;
	}
	public void setEvent_Message(double event_Message) {
		Event_Message = event_Message;
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
	public double getMeasuredProduction() {
		return MeasuredProduction;
	}
	public void setMeasuredProduction(double measuredProduction) {
		MeasuredProduction = measuredProduction;
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
	public int getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(int enable_alert) {
		this.enable_alert = enable_alert;
	}
}
