/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelWattsunTcuEntity extends ModelBaseEntity {
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
