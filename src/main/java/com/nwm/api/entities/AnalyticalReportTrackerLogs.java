/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class AnalyticalReportTrackerLogs extends LogBase {
	private Integer id_analytical_report_tracker;
	private int id_site;
	private Integer status;
	private String pause_reason;
	private String notes;
	private Integer cadence;
	private String start_date;
	private String end_date;
	private boolean keep_cycle;
	private String recipient_to;
	private String recipient_cc;
	
	public Integer getId_analytical_report_tracker() {
		return id_analytical_report_tracker;
	}
	public void setId_analytical_report_tracker(Integer id_analytical_report_tracker) {
		this.id_analytical_report_tracker = id_analytical_report_tracker;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public Integer getCadence() {
		return cadence;
	}
	public void setCadence(Integer cadence) {
		this.cadence = cadence;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPause_reason() {
		return pause_reason;
	}
	public void setPause_reason(String pause_reason) {
		this.pause_reason = pause_reason;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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
	public boolean isKeep_cycle() {
		return keep_cycle;
	}
	public void setKeep_cycle(boolean keep_cycle) {
		this.keep_cycle = keep_cycle;
	}
	public String getRecipient_to() {
		return recipient_to;
	}
	public void setRecipient_to(String recipient_to) {
		this.recipient_to = recipient_to;
	}
	public String getRecipient_cc() {
		return recipient_cc;
	}
	public void setRecipient_cc(String recipient_cc) {
		this.recipient_cc = recipient_cc;
	}
}
