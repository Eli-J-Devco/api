/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public abstract class ModelBaseEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	private int enable_alert;
	
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	
	private double nvm_irradiance;
	private double nvm_temperature;
	private double nvm_panel_temperature;
	private double offset_data_old = 0;
	private String timezone_value;
	
	public void setDeviceDetail(int id_device, String datatablename, String view_tablename, String job_tablename, double offset_data_old, int enable_alert, String timezone_value) {
		this.id_device = id_device;
		this.datatablename = datatablename;
		this.view_tablename = view_tablename;
		this.job_tablename = job_tablename;
		this.offset_data_old = offset_data_old;
		this.enable_alert = enable_alert;
		this.timezone_value = timezone_value;
	}
	
	public double getOffset_data_old() {
		return offset_data_old;
	}
	public void setOffset_data_old(double offset_data_old) {
		this.offset_data_old = offset_data_old;
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
	public double getNvm_irradiance() {
		return nvm_irradiance;
	}
	public void setNvm_irradiance(double nvm_irradiance) {
		this.nvm_irradiance = nvm_irradiance;
	}
	public double getNvm_temperature() {
		return nvm_temperature;
	}
	public void setNvm_temperature(double nvm_temperature) {
		this.nvm_temperature = nvm_temperature;
	}
	public double getNvm_panel_temperature() {
		return nvm_panel_temperature;
	}
	public void setNvm_panel_temperature(double nvm_panel_temperature) {
		this.nvm_panel_temperature = nvm_panel_temperature;
	}
	public String getTimezone_value() {
		return timezone_value;
	}
	public void setTimezone_value(String timezone_value) {
		this.timezone_value = timezone_value;
	}
	
}
