/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSunTrackTrackerEntity extends ModelBaseEntity {
	private double ProductIdentifier;
	private double Status;
	private double Alarms;
	private double Flags;
	private double PanelVoltage;
	private double CurrentAngleRaw;
	private double CurrentAngleCalculated;
	private double MotorCurrent;
	private double MotorCurrentPeak;
	private double TargetAngleRaw;
	private double TargetAngleCalculated;
	private double PanelCurrent;
	private double BatteryStateOfCharge;
	private double BatteryRemainingCapacity;
	private double BatteryFullCapacity;
	private double BatteryVoltage;
	private double BatteryAverageCurrent;
	private double BatteryCurrent;
	private double BatteryInternalTemperature;
	private double BatteryExternalTemperature;
	private double BatteryStateOfHealth;
	
	
	public double getProductIdentifier() {
		return ProductIdentifier;
	}
	public void setProductIdentifier(double productIdentifier) {
		ProductIdentifier = productIdentifier;
	}
	public double getStatus() {
		return Status;
	}
	public void setStatus(double status) {
		Status = status;
	}
	public double getAlarms() {
		return Alarms;
	}
	public void setAlarms(double alarms) {
		Alarms = alarms;
	}
	public double getFlags() {
		return Flags;
	}
	public void setFlags(double flags) {
		Flags = flags;
	}
	public double getPanelVoltage() {
		return PanelVoltage;
	}
	public void setPanelVoltage(double panelVoltage) {
		PanelVoltage = panelVoltage;
	}
	public double getCurrentAngleRaw() {
		return CurrentAngleRaw;
	}
	public void setCurrentAngleRaw(double currentAngleRaw) {
		CurrentAngleRaw = currentAngleRaw;
	}
	public double getCurrentAngleCalculated() {
		return CurrentAngleCalculated;
	}
	public void setCurrentAngleCalculated(double currentAngleCalculated) {
		CurrentAngleCalculated = currentAngleCalculated;
	}
	public double getMotorCurrent() {
		return MotorCurrent;
	}
	public void setMotorCurrent(double motorCurrent) {
		MotorCurrent = motorCurrent;
	}
	public double getMotorCurrentPeak() {
		return MotorCurrentPeak;
	}
	public void setMotorCurrentPeak(double motorCurrentPeak) {
		MotorCurrentPeak = motorCurrentPeak;
	}
	public double getTargetAngleRaw() {
		return TargetAngleRaw;
	}
	public void setTargetAngleRaw(double targetAngleRaw) {
		TargetAngleRaw = targetAngleRaw;
	}
	public double getTargetAngleCalculated() {
		return TargetAngleCalculated;
	}
	public void setTargetAngleCalculated(double targetAngleCalculated) {
		TargetAngleCalculated = targetAngleCalculated;
	}
	public double getPanelCurrent() {
		return PanelCurrent;
	}
	public void setPanelCurrent(double panelCurrent) {
		PanelCurrent = panelCurrent;
	}
	public double getBatteryStateOfCharge() {
		return BatteryStateOfCharge;
	}
	public void setBatteryStateOfCharge(double batteryStateOfCharge) {
		BatteryStateOfCharge = batteryStateOfCharge;
	}
	public double getBatteryRemainingCapacity() {
		return BatteryRemainingCapacity;
	}
	public void setBatteryRemainingCapacity(double batteryRemainingCapacity) {
		BatteryRemainingCapacity = batteryRemainingCapacity;
	}
	public double getBatteryFullCapacity() {
		return BatteryFullCapacity;
	}
	public void setBatteryFullCapacity(double batteryFullCapacity) {
		BatteryFullCapacity = batteryFullCapacity;
	}
	public double getBatteryVoltage() {
		return BatteryVoltage;
	}
	public void setBatteryVoltage(double batteryVoltage) {
		BatteryVoltage = batteryVoltage;
	}
	public double getBatteryAverageCurrent() {
		return BatteryAverageCurrent;
	}
	public void setBatteryAverageCurrent(double batteryAverageCurrent) {
		BatteryAverageCurrent = batteryAverageCurrent;
	}
	public double getBatteryCurrent() {
		return BatteryCurrent;
	}
	public void setBatteryCurrent(double batteryCurrent) {
		BatteryCurrent = batteryCurrent;
	}
	public double getBatteryInternalTemperature() {
		return BatteryInternalTemperature;
	}
	public void setBatteryInternalTemperature(double batteryInternalTemperature) {
		BatteryInternalTemperature = batteryInternalTemperature;
	}
	public double getBatteryExternalTemperature() {
		return BatteryExternalTemperature;
	}
	public void setBatteryExternalTemperature(double batteryExternalTemperature) {
		BatteryExternalTemperature = batteryExternalTemperature;
	}
	public double getBatteryStateOfHealth() {
		return BatteryStateOfHealth;
	}
	public void setBatteryStateOfHealth(double batteryStateOfHealth) {
		BatteryStateOfHealth = batteryStateOfHealth;
	}
}
