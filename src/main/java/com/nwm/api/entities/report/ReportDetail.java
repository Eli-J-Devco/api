package com.nwm.api.entities.report;

import java.util.ArrayList;
import java.util.List;

public class ReportDetail {
	private int id;
	private String name;
	private String cadence_range_name;
	private String subscribers;
	private int data_intervals;
	private int type_report;
	private int type_option;
	private int cadence_range;
	private String report_date;
	private int file_type;
	private String date_from;
	private String date_to;
	private int schedule_enable;
	private String time_schedule;
	private int periodicity;
	private String days_week;
	private String offset_timezone;
	private int id_employee;
	private List<SiteByReport> sites = new ArrayList<>();
	
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
	public String getCadence_range_name() {
		return cadence_range_name;
	}
	public void setCadence_range_name(String cadence_range_name) {
		this.cadence_range_name = cadence_range_name;
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
	public int getCadence_range() {
		return cadence_range;
	}
	public void setCadence_range(int cadence_range) {
		this.cadence_range = cadence_range;
	}
	public String getReport_date() {
		return report_date;
	}
	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}
	public int getFile_type() {
		return file_type;
	}
	public void setFile_type(int file_type) {
		this.file_type = file_type;
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
	public int getSchedule_enable() {
		return schedule_enable;
	}
	public void setSchedule_enable(int schedule_enable) {
		this.schedule_enable = schedule_enable;
	}
	public String getTime_schedule() {
		return time_schedule;
	}
	public void setTime_schedule(String time_schedule) {
		this.time_schedule = time_schedule;
	}
	public int getPeriodicity() {
		return periodicity;
	}
	public void setPeriodicity(int periodicity) {
		this.periodicity = periodicity;
	}
	public String getDays_week() {
		return days_week;
	}
	public void setDays_week(String days_week) {
		this.days_week = days_week;
	}
	public String getOffset_timezone() {
		return offset_timezone;
	}
	public void setOffset_timezone(String offset_timezone) {
		this.offset_timezone = offset_timezone;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	public List<SiteByReport> getSites() {
		return sites;
	}
	public void setSites(List<SiteByReport> sites) {
		this.sites = sites;
	}
}
