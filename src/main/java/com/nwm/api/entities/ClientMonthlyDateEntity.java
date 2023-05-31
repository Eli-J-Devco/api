/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ClientMonthlyDateEntity{
	
	private String download_time;
	private String time_format;
	private String time_full;
	private String categories_time;
	private Double chart_energy_kwh;
	private Double nvm_irradiance;
	private Double expected_power;
	public String getDownload_time() {
		return download_time;
	}
	public void setDownload_time(String download_time) {
		this.download_time = download_time;
	}
	public String getTime_format() {
		return time_format;
	}
	public void setTime_format(String time_format) {
		this.time_format = time_format;
	}
	public String getTime_full() {
		return time_full;
	}
	public void setTime_full(String time_full) {
		this.time_full = time_full;
	}
	public String getCategories_time() {
		return categories_time;
	}
	public void setCategories_time(String categories_time) {
		this.categories_time = categories_time;
	}
	public Double getChart_energy_kwh() {
		return chart_energy_kwh;
	}
	public void setChart_energy_kwh(Double chart_energy_kwh) {
		this.chart_energy_kwh = chart_energy_kwh;
	}
	public Double getNvm_irradiance() {
		return nvm_irradiance;
	}
	public void setNvm_irradiance(Double nvm_irradiance) {
		this.nvm_irradiance = nvm_irradiance;
	}
	public Double getExpected_power() {
		return expected_power;
	}
	public void setExpected_power(Double expected_power) {
		this.expected_power = expected_power;
	}
	
	
	
}
