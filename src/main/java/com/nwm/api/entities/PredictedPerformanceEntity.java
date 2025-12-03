/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class PredictedPerformanceEntity {
	private int month;
	private Double energy;
	private Double insolation;
	
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public Double getEnergy() {
		return energy;
	}
	public void setEnergy(Double energy) {
		this.energy = energy;
	}
	public Double getInsolation() {
		return insolation;
	}
	public void setInsolation(Double insolation) {
		this.insolation = insolation;
	}
}
