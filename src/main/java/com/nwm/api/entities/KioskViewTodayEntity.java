/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class KioskViewTodayEntity extends DateTimeReportDataEntity {
	
	private String hour_time;
	private Double power;
	private Double energy;
	private Double irradiance;

	public String getHour_time() {
		return hour_time;
	}
	public void setHour_time(String hour_time) {
		this.hour_time = hour_time;
	}
	public Double getPower() {
		return power;
	}
	public void setPower(Double power) {
		this.power = power;
	}
	public Double getEnergy() {
		return energy;
	}
	public void setEnergy(Double energy) {
		this.energy = energy;
	}
	public Double getIrradiance() {
		return irradiance;
	}
	public void setIrradiance(Double irradiance) {
		this.irradiance = irradiance;
	}
	
	
	
	
	
}
