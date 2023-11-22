/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelWattsunTrackerEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double MODE;
	private double ST_CLEAR_ACCUMULATOR;
	private double ND_CLEAR_ACCUMULATOR;
	private double TRACKER_ANGLE_SETPOINT;
	private double TRACKER_ANGLE;
	private double NIGHT_STOW_POSITION;
	private double WIND_STOW_POSITION;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	
	
	
	
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
