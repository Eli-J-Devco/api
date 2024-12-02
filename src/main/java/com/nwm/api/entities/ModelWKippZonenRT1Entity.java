/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelWKippZonenRT1Entity extends ModelBaseEntity {
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
	
	
	
	
}
