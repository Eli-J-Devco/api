/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZoneGraphDateEntity{
	
	private String time_full;
	private String categories_time;
	private double ZonesStatus132;
	private double BreakersPanel0L;
	private int on_time;
	

	public int getOn_time() {
		return on_time;
	}
	public void setOn_time(int on_time) {
		this.on_time = on_time;
	}
	public double getBreakersPanel0L() {
		return BreakersPanel0L;
	}
	public void setBreakersPanel0L(double breakersPanel0L) {
		BreakersPanel0L = breakersPanel0L;
	}
	public double getZonesStatus132() {
		return ZonesStatus132;
	}
	public void setZonesStatus132(double zonesStatus132) {
		ZonesStatus132 = zonesStatus132;
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
	
	
}
