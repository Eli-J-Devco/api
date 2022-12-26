/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class QuarterlyDateEntity{
	
	private String month;
	private String year;
	private String name;
	private String time_full;
	private String time_format;
	private String categories_time;
	private int month_number;
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime_full() {
		return time_full;
	}
	public void setTime_full(String time_full) {
		this.time_full = time_full;
	}
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
	public int getMonth_number() {
		return month_number;
	}
	public void setMonth_number(int month_number) {
		this.month_number = month_number;
	}
	
	
	
}
