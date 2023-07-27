/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;
import java.util.Map;

public class ViewReportEntity {
	private int id;
	private int id_site;
	private int id_employee;
	private String site_name;
	private String report_date;
	private String date_from;
	private String date_to;
	private int limit;
	private int offset;
	private int totalRecord;
	private int screen_mode;
	private String system_size;
	private String system_status;
	private List dataReports;
	private List dataExpectations;
	private String table_name;
	private int id_site_type;
	private double ac_capacity;
	private double dc_capacity;
	private String start_date;
	private String end_date;
	private List groupDevices;
	private double totalMWH;
	private String report_name;
	private int cadence_range;
	private String subscribers;
	private String address;
	private String Quarterly_month;
	private int data_intervals;
	private List dataAvailability;
	private String deviceType;
	private int file_type;
	private String time_zone;
	private List dataWeatherStation;
	private int type_report;
	private int type_option;
	private int id_sub_group;
	private String ids_site;
	private List<Map<String, Object>> dataSite;

	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getReport_date() {
		return report_date;
	}
	public void setReport_date(String report_date) {
		this.report_date = report_date;
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
	public int getScreen_mode() {
		return screen_mode;
	}
	public void setScreen_mode(int screen_mode) {
		this.screen_mode = screen_mode;
	}
	public String getSystem_size() {
		return system_size;
	}
	public void setSystem_size(String system_size) {
		this.system_size = system_size;
	}
	public String getSystem_status() {
		return system_status;
	}
	public void setSystem_status(String system_status) {
		this.system_status = system_status;
	}
	public List getDataReports() {
		return dataReports;
	}
	public void setDataReports(List dataReports) {
		this.dataReports = dataReports;
	}
	public List getDataExpectations() {
		return dataExpectations;
	}
	public void setDataExpectations(List dataExpectations) {
		this.dataExpectations = dataExpectations;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public int getId_site_type() {
		return id_site_type;
	}
	public void setId_site_type(int id_site_type) {
		this.id_site_type = id_site_type;
	}
	public double getAc_capacity() {
		return ac_capacity;
	}
	public void setAc_capacity(double ac_capacity) {
		this.ac_capacity = ac_capacity;
	}
	public double getDc_capacity() {
		return dc_capacity;
	}
	public void setDc_capacity(double dc_capacity) {
		this.dc_capacity = dc_capacity;
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
	public double getTotalMWH() {
		return totalMWH;
	}
	public void setTotalMWH(double totalMWH) {
		this.totalMWH = totalMWH;
	}
	public String getReport_name() {
		return report_name;
	}
	public void setReport_name(String report_name) {
		this.report_name = report_name;
	}
	public int getCadence_range() {
		return cadence_range;
	}
	public void setCadence_range(int cadence_range) {
		this.cadence_range = cadence_range;
	}
	public String getSubscribers() {
		return subscribers;
	}
	public void setSubscribers(String subscribers) {
		this.subscribers = subscribers;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getQuarterly_month() {
		return Quarterly_month;
	}
	public void setQuarterly_month(String quarterly_month) {
		Quarterly_month = quarterly_month;
	}
	public int getData_intervals() {
		return data_intervals;
	}
	public void setData_intervals(int data_intervals) {
		this.data_intervals = data_intervals;
	}
	public List getDataAvailability() {
		return dataAvailability;
	}
	public void setDataAvailability(List dataAvailability) {
		this.dataAvailability = dataAvailability;
	}
	public int getFile_type() {
		return file_type;
	}
	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}
	public String getTime_zone() {
		return time_zone;
	}
	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}
	public List getDataWeatherStation() {
		return dataWeatherStation;
	}
	public void setDataWeatherStation(List dataWeatherStation) {
		this.dataWeatherStation = dataWeatherStation;
	}
	public int getType_report() {
		return type_report;
	}
	public void setType_report(int type_report) {
		this.type_report = type_report;
	}
	public int getType_option() {
		return type_option;
	}
	public void setType_option(int type_option) {
		this.type_option = type_option;
	}
	public int getId_sub_group() {
		return id_sub_group;
	}
	public void setId_sub_group(int id_sub_group) {
		this.id_sub_group = id_sub_group;
	}
	public String getIds_site() {
		return ids_site;
	}
	public void setIds_site(String ids_site) {
		this.ids_site = ids_site;
	}
	public List<Map<String, Object>> getDataSite() {
		return dataSite;
	}
	public void setDataSite(List<Map<String, Object>> dataSite) {
		this.dataSite = dataSite;
	}
	
	
	
}
