package com.nwm.api.entities;

import java.util.List;

public class ScadaAlertEntity {
	private int id_site;
	private int id_device;
	private String hash_id;
	private int id_employee;
	private String sort_column;
	private String order_by;
	private String start_date;
	private String end_date;
	private String date_from;
	private String date_to;
	private String current_time;
	private String datatablename;
	private String view_tablename;
	private List id_levels;
	private List id_types; 
	private List id_status;
	private List id_devices;
	private List id_device_types;
	private int limit;
	private int offset;
	private int totalRecord;
	
	
	
	public List getId_device_types() {
		return id_device_types;
	}
	public void setId_device_types(List id_device_types) {
		this.id_device_types = id_device_types;
	}
	public List getId_devices() {
		return id_devices;
	}
	public void setId_devices(List id_devices) {
		this.id_devices = id_devices;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getView_tablename() {
		return view_tablename;
	}
	public void setView_tablename(String view_tablename) {
		this.view_tablename = view_tablename;
	}
	public String getCurrent_time() {
		return current_time;
	}
	public void setCurrent_time(String current_time) {
		this.current_time = current_time;
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
	public List getId_levels() {
		return id_levels;
	}
	public void setId_levels(List id_levels) {
		this.id_levels = id_levels;
	}
	public List getId_types() {
		return id_types;
	}
	public void setId_types(List id_types) {
		this.id_types = id_types;
	}
	public List getId_status() {
		return id_status;
	}
	public void setId_status(List id_status) {
		this.id_status = id_status;
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
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public String getHash_id() {
		return hash_id;
	}
	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
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
	
	
}
