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
	public List<SiteByReport> getSites() {
		return sites;
	}
	public void setSites(List<SiteByReport> sites) {
		this.sites = sites;
	}
}
