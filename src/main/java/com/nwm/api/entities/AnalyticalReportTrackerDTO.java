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
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class AnalyticalReportTrackerDTO {
	private Integer id;
	private int id_site;
	private String country;
	private String company;
	private String site_name;
	private Integer cadence;
	private Integer status;
	private String pause_reason;
	private String notes;
	private String start_date;
	private String end_date;
	private boolean keep_cycle;
	private Set<String> recipient_to;
	private Set<String> recipient_cc;
	private String modified_date;
	private Integer modified_by;
	
	public AnalyticalReportTrackerDTO() {}
	
	public AnalyticalReportTrackerDTO(AnalyticalReportTrackerEntity other) {
		this.id = other.getId();
		this.id_site = other.getId_site();
		this.country = other.getCountry();
		this.company = other.getCompany();
		this.site_name = other.getSite_name();
		this.cadence = other.getCadence();
		this.status = other.getStatus();
		this.pause_reason = other.getPause_reason();
		this.notes = other.getNotes();
		this.start_date = other.getStart_date();
		this.end_date = other.getEnd_date();
		this.keep_cycle = other.isKeep_cycle();
		this.recipient_to = new HashSet<String>(Arrays.asList(Optional.ofNullable(other.getRecipient_to()).orElse("").split(",")).stream().filter(StringUtils::isNotBlank).collect(Collectors.toList()));
		this.recipient_cc = new HashSet<String>(Arrays.asList(Optional.ofNullable(other.getRecipient_cc()).orElse("").split(",")).stream().filter(StringUtils::isNotBlank).collect(Collectors.toList()));
		this.modified_date = other.getModified_date();
		this.modified_by = other.getModified_by();
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
}
