/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelPVHTboxEntity extends ModelBaseEntity {
	private double ControlTriggersWord;
	private double ManualElevationTarget;
	private double OperationMode;
	private double AngleSetpoint;
	private double AnglePosition;
	private double BatteryLevel;
	private double Alarms;
	private double MaxMotorCurrent;
	private double Warnings;
	private double PositionCode;
	private double Notifications;
	public double getControlTriggersWord() {
		return ControlTriggersWord;
	}
	public void setControlTriggersWord(double controlTriggersWord) {
		ControlTriggersWord = controlTriggersWord;
	}
	public double getManualElevationTarget() {
		return ManualElevationTarget;
	}
	public void setManualElevationTarget(double manualElevationTarget) {
		ManualElevationTarget = manualElevationTarget;
	}
	public double getOperationMode() {
		return OperationMode;
	}
	public void setOperationMode(double operationMode) {
		OperationMode = operationMode;
	}
	public double getAngleSetpoint() {
		return AngleSetpoint;
	}
	public void setAngleSetpoint(double angleSetpoint) {
		AngleSetpoint = angleSetpoint;
	}
	public double getAnglePosition() {
		return AnglePosition;
	}
	public void setAnglePosition(double anglePosition) {
		AnglePosition = anglePosition;
	}
	public double getBatteryLevel() {
		return BatteryLevel;
	}
	public void setBatteryLevel(double batteryLevel) {
		BatteryLevel = batteryLevel;
	}
	public double getAlarms() {
		return Alarms;
	}
	public void setAlarms(double alarms) {
		Alarms = alarms;
	}
	public double getMaxMotorCurrent() {
		return MaxMotorCurrent;
	}
	public void setMaxMotorCurrent(double maxMotorCurrent) {
		MaxMotorCurrent = maxMotorCurrent;
	}
	public double getWarnings() {
		return Warnings;
	}
	public void setWarnings(double warnings) {
		Warnings = warnings;
	}
	public double getPositionCode() {
		return PositionCode;
	}
	public void setPositionCode(double positionCode) {
		PositionCode = positionCode;
	}
	public double getNotifications() {
		return Notifications;
	}
	public void setNotifications(double notifications) {
		Notifications = notifications;
	}
	
	
	
	
	
}
