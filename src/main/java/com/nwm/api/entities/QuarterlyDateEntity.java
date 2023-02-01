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
	private String time_format_by_day;
	private String categories_time;
	private String categories_time_by_day;
	private int month_number;
	private Double actual;
	private Double estimated;
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
	public String getTime_format_by_day() {
		return time_format_by_day;
	}
	public void setTime_format_by_day(String time_format_by_day) {
		this.time_format_by_day = time_format_by_day;
	}
	public String getCategories_time() {
		return categories_time;
	}
	public void setCategories_time(String categories_time) {
		this.categories_time = categories_time;
	}
	public String getCategories_time_by_day() {
		return categories_time_by_day;
	}
	public void setCategories_time_by_day(String categories_time_by_day) {
		this.categories_time_by_day = categories_time_by_day;
	}
	public int getMonth_number() {
		return month_number;
	}
	public void setMonth_number(int month_number) {
		this.month_number = month_number;
	}
	public Double getActual() {
		return actual;
	}
	public void setActual(Double actual) {
		this.actual = actual;
	}
	public Double getEstimated() {
		return estimated;
	}
	public void setEstimated(Double estimated) {
		this.estimated = estimated;
	}
	
	
	
}
