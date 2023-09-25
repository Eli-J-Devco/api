/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelWKippZonenRT1Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double DeviceType;
	private double DataModelVersion;
	private double OperationalMode;
	private double StatusFlags;
	private double SunPOATempComp;
	private double PanelTemperature;
	private double ExtPowerSensor;
	private double BatchNumber;
	private double SerialNumber;
	private double CalibrationDateYYMMDD;
	private double nvm_irradiance;
	private double nvm_temperature;
	private double nvm_panel_temperature;
	
	
	public double getNvm_panel_temperature() {
		return nvm_panel_temperature;
	}
	public void setNvm_panel_temperature(double nvm_panel_temperature) {
		this.nvm_panel_temperature = nvm_panel_temperature;
	}
	
	public double getNvm_temperature() {
		return nvm_temperature;
	}
	public void setNvm_temperature(double nvm_temperature) {
		this.nvm_temperature = nvm_temperature;
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
	public double getDeviceType() {
		return DeviceType;
	}
	public void setDeviceType(double deviceType) {
		DeviceType = deviceType;
	}
	public double getDataModelVersion() {
		return DataModelVersion;
	}
	public void setDataModelVersion(double dataModelVersion) {
		DataModelVersion = dataModelVersion;
	}
	public double getOperationalMode() {
		return OperationalMode;
	}
	public void setOperationalMode(double operationalMode) {
		OperationalMode = operationalMode;
	}
	public double getStatusFlags() {
		return StatusFlags;
	}
	public void setStatusFlags(double statusFlags) {
		StatusFlags = statusFlags;
	}
	public double getSunPOATempComp() {
		return SunPOATempComp;
	}
	public void setSunPOATempComp(double sunPOATempComp) {
		SunPOATempComp = sunPOATempComp;
	}
	public double getPanelTemperature() {
		return PanelTemperature;
	}
	public void setPanelTemperature(double panelTemperature) {
		PanelTemperature = panelTemperature;
	}
	public double getExtPowerSensor() {
		return ExtPowerSensor;
	}
	public void setExtPowerSensor(double extPowerSensor) {
		ExtPowerSensor = extPowerSensor;
	}
	public double getBatchNumber() {
		return BatchNumber;
	}
	public void setBatchNumber(double batchNumber) {
		BatchNumber = batchNumber;
	}
	public double getSerialNumber() {
		return SerialNumber;
	}
	public void setSerialNumber(double serialNumber) {
		SerialNumber = serialNumber;
	}
	public double getCalibrationDateYYMMDD() {
		return CalibrationDateYYMMDD;
	}
	public void setCalibrationDateYYMMDD(double calibrationDateYYMMDD) {
		CalibrationDateYYMMDD = calibrationDateYYMMDD;
	}
	public double getNvm_irradiance() {
		return nvm_irradiance;
	}
	public void setNvm_irradiance(double nvm_irradiance) {
		this.nvm_irradiance = nvm_irradiance;
	}
	
	
	
	
}
