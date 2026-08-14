/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class RECReportResponse {
	private int id;
	private String name;
	private String devicename;
	private String ru_id;
	private String gu_id;
	private String start_date;
	private String end_date;
	private String vintage_date;
	private int is_edit;
	private Double energy_this_month;
	
	public RECReportResponse() {}
	
	public RECReportResponse(RECReportResponse other) {
		this.id = other.id;
		this.name = other.name;
		this.devicename = other.devicename;
		this.ru_id = other.ru_id;
		this.gu_id = other.gu_id;
		this.start_date = other.start_date;
		this.end_date = other.end_date;
		this.vintage_date = other.vintage_date;
		this.is_edit = other.is_edit;
		this.energy_this_month = other.energy_this_month;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public String getRu_id() {
		return ru_id;
	}
	public void setRu_id(String ru_id) {
		this.ru_id = ru_id;
	}
	public String getGu_id() {
		return gu_id;
	}
	public void setGu_id(String gu_id) {
		this.gu_id = gu_id;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getVintage_date() {
		return vintage_date;
	}
	public void setVintage_date(String vintage_date) {
		this.vintage_date = vintage_date;
	}
	public int getIs_edit() {
		return is_edit;
	}
	public void setIs_edit(int is_edit) {
		this.is_edit = is_edit;
	}
	public Double getEnergy_this_month() {
		return energy_this_month;
	}
	public void setEnergy_this_month(Double energy_this_month) {
		this.energy_this_month = energy_this_month;
	}
}
