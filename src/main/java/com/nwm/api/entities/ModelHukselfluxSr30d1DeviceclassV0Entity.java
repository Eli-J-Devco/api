/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelHukselfluxSr30d1DeviceclassV0Entity extends ModelBaseEntity {
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