/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuildingReportWeatherEntity extends DateTimeReportDataEntity {
	
	private Integer id;
	private String download_time;
	private Double nvm_irradiance;
	private Double nvm_temperature;
	private Double nvm_panel_temperature;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDownload_time() {
		return download_time;
	}
	public void setDownload_time(String download_time) {
		this.download_time = download_time;
	}
	public Double getNvm_irradiance() {
		return nvm_irradiance;
	}
	public void setNvm_irradiance(Double nvm_irradiance) {
		this.nvm_irradiance = nvm_irradiance;
	}
	public Double getNvm_temperature() {
		return nvm_temperature;
	}
	public void setNvm_temperature(Double nvm_temperature) {
		this.nvm_temperature = nvm_temperature;
	}
	public Double getNvm_panel_temperature() {
		return nvm_panel_temperature;
	}
	public void setNvm_panel_temperature(Double nvm_panel_temperature) {
		this.nvm_panel_temperature = nvm_panel_temperature;
	}
	
}
