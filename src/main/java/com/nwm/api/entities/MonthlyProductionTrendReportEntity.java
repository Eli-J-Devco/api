/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class MonthlyProductionTrendReportEntity{
	
	private String time_format;
	private String time_full;
	private String site_name;
	private Double monthlyProduction;
	
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
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public Double getMonthlyProduction() {
		return monthlyProduction;
	}
	public void setMonthlyProduction(Double monthlyProduction) {
		this.monthlyProduction = monthlyProduction;
	}
	
}
