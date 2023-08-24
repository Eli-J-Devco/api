/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class SystemEntity{
	private String type;
	private double value;
	private double lockout_time;
	private int max_attempts; 
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public double getLockout_time() {
		return lockout_time;
	}
	public void setLockout_time(double lockout_time) {
		this.lockout_time = lockout_time;
	}
	public int getMax_attempts() {
		return max_attempts;
	}
	public void setMax_attempts(int max_attempts) {
		this.max_attempts = max_attempts;
	}
}
