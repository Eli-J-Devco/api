/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ScadaDeviceChartDataEntity extends DateTimeReportDataEntity {
	private Double active_power;
	
	public Double getActive_power() {
		return active_power;
	}
	public void setActive_power(Double active_power) {
		this.active_power = active_power;
	}
	
}
