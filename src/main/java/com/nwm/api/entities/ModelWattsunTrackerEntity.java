/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelWattsunTrackerEntity extends ModelBaseEntity {
	private double MODE;
	private double ST_CLEAR_ACCUMULATOR;
	private double ND_CLEAR_ACCUMULATOR;
	private double TRACKER_ANGLE_SETPOINT;
	private double TRACKER_ANGLE;
	private double NIGHT_STOW_POSITION;
	private double WIND_STOW_POSITION;
	
	public double getMODE() {
		return MODE;
	}
	public void setMODE(double mODE) {
		MODE = mODE;
	}
	public double getST_CLEAR_ACCUMULATOR() {
		return ST_CLEAR_ACCUMULATOR;
	}
	public void setST_CLEAR_ACCUMULATOR(double sT_CLEAR_ACCUMULATOR) {
		ST_CLEAR_ACCUMULATOR = sT_CLEAR_ACCUMULATOR;
	}
	public double getND_CLEAR_ACCUMULATOR() {
		return ND_CLEAR_ACCUMULATOR;
	}
	public void setND_CLEAR_ACCUMULATOR(double nD_CLEAR_ACCUMULATOR) {
		ND_CLEAR_ACCUMULATOR = nD_CLEAR_ACCUMULATOR;
	}
	public double getTRACKER_ANGLE_SETPOINT() {
		return TRACKER_ANGLE_SETPOINT;
	}
	public void setTRACKER_ANGLE_SETPOINT(double tRACKER_ANGLE_SETPOINT) {
		TRACKER_ANGLE_SETPOINT = tRACKER_ANGLE_SETPOINT;
	}
	public double getTRACKER_ANGLE() {
		return TRACKER_ANGLE;
	}
	public void setTRACKER_ANGLE(double tRACKER_ANGLE) {
		TRACKER_ANGLE = tRACKER_ANGLE;
	}
	public double getNIGHT_STOW_POSITION() {
		return NIGHT_STOW_POSITION;
	}
	public void setNIGHT_STOW_POSITION(double nIGHT_STOW_POSITION) {
		NIGHT_STOW_POSITION = nIGHT_STOW_POSITION;
	}
	public double getWIND_STOW_POSITION() {
		return WIND_STOW_POSITION;
	}
	public void setWIND_STOW_POSITION(double wIND_STOW_POSITION) {
		WIND_STOW_POSITION = wIND_STOW_POSITION;
	}
	
	
}
