/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class KioskViewTodayEntity{
	
	private String time_format;
	private String categories_time;
	private String hour_time;
	private Double power;
	private Double energy;
	private Double irradiance;
	public String getTime_format() {
		return time_format;
	}
	public void setTime_format(String time_format) {
		this.time_format = time_format;
	}
	public String getCategories_time() {
		return categories_time;
	}
	public void setCategories_time(String categories_time) {
		this.categories_time = categories_time;
	}
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
