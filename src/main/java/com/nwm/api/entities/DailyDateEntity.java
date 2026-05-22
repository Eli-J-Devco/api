/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class DailyDateEntity extends DateTimeReportDataEntity {
	private Double power;
	private Double energy;
	private Double irradiance;
	private Double dc_capacity;
	private String weather;
	
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public Double getDc_capacity() {
		return dc_capacity;
	}
	public void setDc_capacity(Double dc_capacity) {
		this.dc_capacity = dc_capacity;
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
