/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class IrradianceDTO extends DateTimeReportDataEntity {
	private Double irradiance;
	private Double temperature;
	
	public Double getIrradiance() {
		return irradiance;
	}
	public void setIrradiance(Double irradiance) {
		this.irradiance = irradiance;
	}
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
}
