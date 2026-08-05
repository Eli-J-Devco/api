/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

public class AnalyticalReportTrackerEntity {
	private int id_site;
	private Integer status;
	private String pause_reason;
	private String notes;
	private String modified_date;
	private Integer modified_by;
	private String modified_by_name;

	public int getId_site() {
		return id_site;
	}

	public void setId_site(int id_site) {
		this.id_site = id_site;
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

	public String getModified_date() {
		return modified_date;
	}

	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}

	public Integer getModified_by() {
		return modified_by;
	}

	public void setModified_by(Integer modified_by) {
		this.modified_by = modified_by;
	}

	public String getModified_by_name() {
		return modified_by_name;
	}

	public void setModified_by_name(String modified_by_name) {
		this.modified_by_name = modified_by_name;
	}
}
