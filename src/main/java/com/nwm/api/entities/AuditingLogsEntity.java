/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class AuditingLogsEntity {
	private int id;
	private int id_employee;
	private String modified_date;
	private String modified_page;
	private String offset_timezone;
	private int action_mode;
	private String description;
	private String backup_item;
	private int limit;
	private int offset;
	private String sort_column;
	private String order_by;
	private String latest_records;
	private int id_site;
	private String full_name;
	
	
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
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
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
	public String getModified_page() {
		return modified_page;
	}
	public void setModified_page(String modified_page) {
		this.modified_page = modified_page;
	}
	public String getOffset_timezone() {
		return offset_timezone;
	}
	public void setOffset_timezone(String offset_timezone) {
		this.offset_timezone = offset_timezone;
	}
	public int getAction_mode() {
		return action_mode;
	}
	public void setAction_mode(int action_mode) {
		this.action_mode = action_mode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBackup_item() {
		return backup_item;
	}
	public void setBackup_item(String backup_item) {
		this.backup_item = backup_item;
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
	public String getSort_column() {
		return sort_column;
	}
	public void setSort_column(String sort_column) {
		this.sort_column = sort_column;
	}
	public String getOrder_by() {
		return order_by;
	}
	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}
	public String getLatest_records() {
		return latest_records;
	}
	public void setLatest_records(String latest_records) {
		this.latest_records = latest_records;
	}

}
