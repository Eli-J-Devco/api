/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuildingReportDateEntity extends DateTimeReportDataEntity {
	
	private double energy;
	private int days;
	private double previousRead;
	private double currentRead;
	
	

	public double getPreviousRead() {
		return previousRead;
	}

	public void setPreviousRead(double previousRead) {
		this.previousRead = previousRead;
	}

	public double getCurrentRead() {
		return currentRead;
	}

	public void setCurrentRead(double currentRead) {
		this.currentRead = currentRead;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}
	
	
}
