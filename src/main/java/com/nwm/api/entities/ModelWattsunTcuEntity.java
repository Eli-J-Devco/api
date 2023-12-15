/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelWattsunTcuEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double TRACKER_ADDRESS;
	private double HOUR;
	private double MINUTE;
	private double DAY;
	private double DIGITAL_INPUTS;
	private double DIGITAL_OUTPUTS;
	private double WATCHDOG_COUNTER;
	private double ANGLE_CALC;
	private double LATITUDE;
	private double LONGITUDE;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
private int enable_alert;
	
	
	
	public int getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(int enable_alert) {
		this.enable_alert = enable_alert;
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
	public double getTRACKER_ADDRESS() {
		return TRACKER_ADDRESS;
	}
	public void setTRACKER_ADDRESS(double tRACKER_ADDRESS) {
		TRACKER_ADDRESS = tRACKER_ADDRESS;
	}
	public double getHOUR() {
		return HOUR;
	}
	public void setHOUR(double hOUR) {
		HOUR = hOUR;
	}
	public double getMINUTE() {
		return MINUTE;
	}
	public void setMINUTE(double mINUTE) {
		MINUTE = mINUTE;
	}
	public double getDAY() {
		return DAY;
	}
	public void setDAY(double dAY) {
		DAY = dAY;
	}
	public double getDIGITAL_INPUTS() {
		return DIGITAL_INPUTS;
	}
	public void setDIGITAL_INPUTS(double dIGITAL_INPUTS) {
		DIGITAL_INPUTS = dIGITAL_INPUTS;
	}
	public double getDIGITAL_OUTPUTS() {
		return DIGITAL_OUTPUTS;
	}
	public void setDIGITAL_OUTPUTS(double dIGITAL_OUTPUTS) {
		DIGITAL_OUTPUTS = dIGITAL_OUTPUTS;
	}
	public double getWATCHDOG_COUNTER() {
		return WATCHDOG_COUNTER;
	}
	public void setWATCHDOG_COUNTER(double wATCHDOG_COUNTER) {
		WATCHDOG_COUNTER = wATCHDOG_COUNTER;
	}
	public double getANGLE_CALC() {
		return ANGLE_CALC;
	}
	public void setANGLE_CALC(double aNGLE_CALC) {
		ANGLE_CALC = aNGLE_CALC;
	}
	public double getLATITUDE() {
		return LATITUDE;
	}
	public void setLATITUDE(double lATITUDE) {
		LATITUDE = lATITUDE;
	}
	public double getLONGITUDE() {
		return LONGITUDE;
	}
	public void setLONGITUDE(double lONGITUDE) {
		LONGITUDE = lONGITUDE;
	}
	
	
	
	
}
