/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class AlertFilterEntity {
	private int id;
	private int id_employee;
	private String name;
	private String alert_filter;
	private int limit;
	private int offset;
	private int totalRecord;
	private String created_date;
	private int is_default;
	private int alert_per_page;

	
	
	public int getAlert_per_page() {
		return alert_per_page;
	}
	public void setAlert_per_page(int alert_per_page) {
		this.alert_per_page = alert_per_page;
	}
	public int getIs_default() {
		return is_default;
	}
	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlert_filter() {
		return alert_filter;
	}
	public void setAlert_filter(String alert_filter) {
		this.alert_filter = alert_filter;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	
	
}
