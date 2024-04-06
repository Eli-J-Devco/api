/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ScadaDeviceChartDataEntity {
	private String full_time;
	private String category_time;
	private Double active_power;
	
	public String getFull_time() {
		return full_time;
	}
	public void setFull_time(String full_time) {
		this.full_time = full_time;
	}
	public String getCategory_time() {
		return category_time;
	}
	public void setCategory_time(String category_time) {
		this.category_time = category_time;
	}
	public Double getActive_power() {
		return active_power;
	}
	public void setActive_power(Double active_power) {
		this.active_power = active_power;
	}
	
}
