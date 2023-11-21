/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class FileImportDataOldEntity{
	private int id;
	private int id_device;
	private int id_employee;
	private String time_upload;
	private String time_complete;
	private int total_row;
	private int total_error_row;
	private String filename;
	private int status;
	private int total_complete_row;
	private int limit;
	private int offset;
	private int totalRecord;
	private String order_by;
	private String sort_by;
	private String sort_column;
	private String keyword;
	private String datatablename;
	private List dataList;
	private int id_site;
	private int id_device_type;
	private int row; 
	private String start_date;
	private String end_date;
	private String timezone_value;
	private String timezone_offset;
	private String table_data_report;
	
	
	
	
	
	public String getTimezone_value() {
		return timezone_value;
	}
	public void setTimezone_value(String timezone_value) {
		this.timezone_value = timezone_value;
	}
	public String getTimezone_offset() {
		return timezone_offset;
	}
	public void setTimezone_offset(String timezone_offset) {
		this.timezone_offset = timezone_offset;
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
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getId_device_type() {
		return id_device_type;
	}
	public void setId_device_type(int id_device_type) {
		this.id_device_type = id_device_type;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getSort_column() {
		return sort_column;
	}
	public void setSort_column(String sort_column) {
		this.sort_column = sort_column;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
	public int getTotal_complete_row() {
		return total_complete_row;
	}
	public void setTotal_complete_row(int total_complete_row) {
		this.total_complete_row = total_complete_row;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	public String getTime_upload() {
		return time_upload;
	}
	public void setTime_upload(String time_upload) {
		this.time_upload = time_upload;
	}
	public String getTime_complete() {
		return time_complete;
	}
	public void setTime_complete(String time_complete) {
		this.time_complete = time_complete;
	}
	public int getTotal_row() {
		return total_row;
	}
	public void setTotal_row(int total_row) {
		this.total_row = total_row;
	}
	public int getTotal_error_row() {
		return total_error_row;
	}
	public void setTotal_error_row(int total_error_row) {
		this.total_error_row = total_error_row;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTable_data_report() {
		return table_data_report;
	}
	public void setTable_data_report(String table_data_report) {
		this.table_data_report = table_data_report;
	}
	
	
	
	
}
