/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;
import java.util.Map;

public class AlertsBySiteDeviceRequest {
	private int id;
	private String date_from;
	private String date_to;
	private List<Map<String, Object>> device_list;
	private int data_send_time;
	private String filterBy;
	private String date_format;
	private int time_format;
	private String locale;
	private int is_supper_admin;
	private String domain;
	private boolean isUserNW;
	private int id_employee;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate_from() {
		return date_from;
	}
	public void setDate_from(String date_from) {
		this.date_from = date_from;
	}
	public String getDate_to() {
		return date_to;
	}
	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}
	public List<Map<String, Object>> getDevice_list() {
		return device_list;
	}
	public void setDevice_list(List<Map<String, Object>> device_list) {
		this.device_list = device_list;
	}
	public int getData_send_time() {
		return data_send_time;
	}
	public void setData_send_time(int data_send_time) {
		this.data_send_time = data_send_time;
	}
	public String getFilterBy() {
		return filterBy;
	}
	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
	}
	public String getDate_format() {
		return date_format;
	}
	public void setDate_format(String date_format) {
		this.date_format = date_format;
	}
	public int getTime_format() {
		return time_format;
	}
	public void setTime_format(int time_format) {
		this.time_format = time_format;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public int getIs_supper_admin() {
		return is_supper_admin;
	}
	public void setIs_supper_admin(int is_supper_admin) {
		this.is_supper_admin = is_supper_admin;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public boolean isUserNW() {
		return isUserNW;
	}
	public void setIsUserNW(boolean isUserNW) {
		this.isUserNW = isUserNW;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	
}
