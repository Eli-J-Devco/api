/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class ReportsEntity {
	private int id;
	private int id_site;
	private int id_employee;
	private int type_report;
	private int cadence_range;
	private String date_from;
	private String date_to;
	private String subscribers;
	private int data_intervals;
	private int file_type;
	private int status;
	private String created_date;
	private String created_by;
	private String updated_date;
	private String updated_by;
	private int limit;
	private int offset;
	private int totalRecord;
	private String order_by;
	private String sort_by;
	private List id_sites;
	private int screen_mode;
	private String sort_column;
	private String start_date;
	private String end_date;
	private List groupDevices;
	private Double chart_energy_kwh;
	private String customer_name;
	private Double energy_this_month;
	private int id_sub_group;
	private int type_option;
	private List dataSite;
	
	
	public Double getEnergy_this_month() {
		return energy_this_month;
	}
	public void setEnergy_this_month(Double energy_this_month) {
		this.energy_this_month = energy_this_month;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	public int getType_report() {
		return type_report;
	}
	public void setType_report(int type_report) {
		this.type_report = type_report;
	}
	public int getCadence_range() {
		return cadence_range;
	}
	public void setCadence_range(int cadence_range) {
		this.cadence_range = cadence_range;
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
	public String getSubscribers() {
		return subscribers;
	}
	public void setSubscribers(String subscribers) {
		this.subscribers = subscribers;
	}
	public int getData_intervals() {
		return data_intervals;
	}
	public void setData_intervals(int data_intervals) {
		this.data_intervals = data_intervals;
	}
	public int getFile_type() {
		return file_type;
	}
	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
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
	public String getOrder_by() {
		return order_by;
	}
	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}
	public String getSort_by() {
		return sort_by;
	}
	public void setSort_by(String sort_by) {
		this.sort_by = sort_by;
	}
	public List getId_sites() {
		return id_sites;
	}
	public void setId_sites(List id_sites) {
		this.id_sites = id_sites;
	}
	public int getScreen_mode() {
		return screen_mode;
	}
	public void setScreen_mode(int screen_mode) {
		this.screen_mode = screen_mode;
	}
	public String getSort_column() {
		return sort_column;
	}
	public void setSort_column(String sort_column) {
		this.sort_column = sort_column;
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
	public List getGroupDevices() {
		return groupDevices;
	}
	public void setGroupDevices(List groupDevices) {
		this.groupDevices = groupDevices;
	}
	public Double getChart_energy_kwh() {
		return chart_energy_kwh;
	}
	public void setChart_energy_kwh(Double chart_energy_kwh) {
		this.chart_energy_kwh = chart_energy_kwh;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public int getId_sub_group() {
		return id_sub_group;
	}
	public void setId_sub_group(int id_sub_group) {
		this.id_sub_group = id_sub_group;
	}
	public int getType_option() {
		return type_option;
	}
	public void setType_option(int type_option) {
		this.type_option = type_option;
	}
	public List getDataSite() {
		return dataSite;
	}
	public void setDataSite(List dataSite) {
		this.dataSite = dataSite;
	}
	
	
	
	
}
