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
	private double nvm_irradiance;
	private double nvm_temperature;
	private double nvm_humid;
	private String start_date;
	private String end_date;
	
	

	public double getNvm_humid() {
		return nvm_humid;
	}

	public void setNvm_humid(double nvm_humid) {
		this.nvm_humid = nvm_humid;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public double getNvm_irradiance() {
		return nvm_irradiance;
	}

	public void setNvm_irradiance(double nvm_irradiance) {
		this.nvm_irradiance = nvm_irradiance;
	}

	public double getNvm_temperature() {
		return nvm_temperature;
	}

	public void setNvm_temperature(double nvm_temperature) {
		this.nvm_temperature = nvm_temperature;
	}

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
