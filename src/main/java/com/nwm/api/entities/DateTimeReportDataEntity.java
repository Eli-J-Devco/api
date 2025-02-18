/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class DateTimeReportDataEntity {
	private String time_format;
	private String categories_time;
	private String time_full;
	private String full_time;
	private String category_time;
	
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
	public String getTime_full() {
		return time_full;
	}
	public void setTime_full(String time_full) {
		this.time_full = time_full;
	}
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
	
}
