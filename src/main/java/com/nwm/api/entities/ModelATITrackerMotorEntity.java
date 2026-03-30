/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelATITrackerMotorEntity extends ModelBaseEntity {
	
	private double SetpointPosition;
	private double ActualPosition;
	private double Alarms;
	private double Mode;
	private double ManualPosition;
	public double getSetpointPosition() {
		return SetpointPosition;
	}
	public void setSetpointPosition(double setpointPosition) {
		SetpointPosition = setpointPosition;
	}
	public double getActualPosition() {
		return ActualPosition;
	}
	public void setActualPosition(double actualPosition) {
		ActualPosition = actualPosition;
	}
	public double getAlarms() {
		return Alarms;
	}
	public void setAlarms(double alarms) {
		Alarms = alarms;
	}
	public double getMode() {
		return Mode;
	}
	public void setMode(double mode) {
		Mode = mode;
	}
	public double getManualPosition() {
		return ManualPosition;
	}
	public void setManualPosition(double manualPosition) {
		ManualPosition = manualPosition;
	}
	
	
	
	
}
