/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ActualDTO extends DateTimeReportDataEntity {
	private Double power;
	private Double energy;
	
	public Double getPower() {
		return power;
	}
	public void setPower(Double power) {
		this.power = power;
	}
	public Double getEnergy() {
		return energy;
	}
	public void setEnergy(Double actual) {
		this.energy = actual;
	}
}
