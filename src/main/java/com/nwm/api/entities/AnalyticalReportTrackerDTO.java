/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AnalyticalReportTrackerDTO {
	private Integer id;
	private int id_site;
	private Integer status;
	private String pause_reason;
	private String notes;
	private Integer cadence;
	private String start_date;
	private String end_date;
	private boolean keep_cycle;
	private Set<String> recipient_to;
	private Set<String> recipient_cc;
	private String modified_date;
	private Integer modified_by;
	private String modified_by_name;
	
	public AnalyticalReportTrackerDTO() {}
	
	public AnalyticalReportTrackerDTO(AnalyticalReportTrackerEntity other) {
		this.id = other.getId();
		this.id_site = other.getId_site();
		this.status = other.getStatus();
		this.pause_reason = other.getPause_reason();
		this.notes = other.getNotes();
		this.cadence = other.getCadence();
		this.start_date = other.getStart_date();
		this.end_date = other.getEnd_date();
		this.keep_cycle = other.isKeep_cycle();
		this.recipient_to = new HashSet<String>(Arrays.asList(Optional.ofNullable(other.getRecipient_to()).orElse("").split(",")));
		this.recipient_cc = new HashSet<String>(Arrays.asList(Optional.ofNullable(other.getRecipient_cc()).orElse("").split(",")));
		this.modified_date = other.getModified_date();
		this.modified_by = other.getModified_by();
		this.modified_by_name = other.getModified_by_name();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Integer getCadence() {
		return cadence;
	}

	public void setCadence(Integer cadence) {
		this.cadence = cadence;
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

	public Set<String> getRecipient_to() {
		return recipient_to;
	}

	public void setRecipient_to(Set<String> recipient_to) {
		this.recipient_to = recipient_to;
	}

	public Set<String> getRecipient_cc() {
		return recipient_cc;
	}

	public void setRecipient_cc(Set<String> recipient_cc) {
		this.recipient_cc = recipient_cc;
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
