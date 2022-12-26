/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelHukselfluxSr30d1DeviceclassV0Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double IrradianceTcs;
	private double IrradianceUs;
	private double SensorBodyTemperature;
	private double SensorElectricalResistance;
	private double ScalingFactorIrradiance;
	private double ScalingFactorTemperature;
	private double SensorSerialNumber;
	private double SensorSensitivity;
	private double SensorCalibrationDate;
	private double InternalHumidity;
	private double TiltAngle;
	private double TiltAngleaverage;
	private double FanSpeedRPM;
	private double HeaterCurrent;
	private double FanCurrent;
	private double nvm_irradiance;
	private double nvm_temperature;
	
	
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
	public double getIrradianceTcs() {
		return IrradianceTcs;
	}
	public void setIrradianceTcs(double IrradianceTcs) {
		this.IrradianceTcs = IrradianceTcs;
	}
	public double getIrradianceUs() {
		return IrradianceUs;
	}
	public void setIrradianceUs(double IrradianceUs) {
		this.IrradianceUs = IrradianceUs;
	}
	public double getSensorBodyTemperature() {
		return SensorBodyTemperature;
	}
	public void setSensorBodyTemperature(double sensorBodyTemperature) {
		SensorBodyTemperature = sensorBodyTemperature;
	}
	public double getSensorElectricalResistance() {
		return SensorElectricalResistance;
	}
	public void setSensorElectricalResistance(double sensorElectricalResistance) {
		SensorElectricalResistance = sensorElectricalResistance;
	}
	public double getScalingFactorIrradiance() {
		return ScalingFactorIrradiance;
	}
	public void setScalingFactorIrradiance(double scalingFactorIrradiance) {
		ScalingFactorIrradiance = scalingFactorIrradiance;
	}
	public double getScalingFactorTemperature() {
		return ScalingFactorTemperature;
	}
	public void setScalingFactorTemperature(double scalingFactorTemperature) {
		ScalingFactorTemperature = scalingFactorTemperature;
	}
	public double getSensorSerialNumber() {
		return SensorSerialNumber;
	}
	public void setSensorSerialNumber(double sensorSerialNumber) {
		SensorSerialNumber = sensorSerialNumber;
	}
	public double getSensorSensitivity() {
		return SensorSensitivity;
	}
	public void setSensorSensitivity(double sensorSensitivity) {
		SensorSensitivity = sensorSensitivity;
	}
	public double getSensorCalibrationDate() {
		return SensorCalibrationDate;
	}
	public void setSensorCalibrationDate(double sensorCalibrationDate) {
		SensorCalibrationDate = sensorCalibrationDate;
	}
	public double getInternalHumidity() {
		return InternalHumidity;
	}
	public void setInternalHumidity(double internalHumidity) {
		InternalHumidity = internalHumidity;
	}
	public double getTiltAngle() {
		return TiltAngle;
	}
	public void setTiltAngle(double tiltAngle) {
		TiltAngle = tiltAngle;
	}
	public double getTiltAngleaverage() {
		return TiltAngleaverage;
	}
	public void setTiltAngleaverage(double tiltAngleaverage) {
		TiltAngleaverage = tiltAngleaverage;
	}
	public double getFanSpeedRPM() {
		return FanSpeedRPM;
	}
	public void setFanSpeedRPM(double fanSpeedRPM) {
		FanSpeedRPM = fanSpeedRPM;
	}
	public double getHeaterCurrent() {
		return HeaterCurrent;
	}
	public void setHeaterCurrent(double heaterCurrent) {
		HeaterCurrent = heaterCurrent;
	}
	public double getFanCurrent() {
		return FanCurrent;
	}
	public void setFanCurrent(double fanCurrent) {
		FanCurrent = fanCurrent;
	}
	
	
}