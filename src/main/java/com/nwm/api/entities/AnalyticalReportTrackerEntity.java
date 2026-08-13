/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class AnalyticalReportTrackerEntity {
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
	private String timezone;
	private boolean keep_cycle;
	private String recipient_to;
	private String recipient_cc;
	private String modified_date;
	private Integer modified_by;
	private Integer data_send_time;
	private String hash_id;
    private String domain;
	private String timezone_value;
	private String sunrise;
	private String sunset;
	private List<ClientMonthlyDateEntity> productionReportList;
	private List<ClientMonthlyDateEntity> generationSummaryList;
	private List<PerformanceDataChartItemEntity> inverterDataList;
	private Double totalActualGeneration;
	private Double totalExpectedGeneration;
	private Double poaIrradiance;
	private Double totalActual;
	private Double totalExpected;
	private Double actualExpected;

	public AnalyticalReportTrackerEntity() {}

	public AnalyticalReportTrackerEntity(AnalyticalReportTrackerDTO other) {
		this.id = other.getId();
		this.id_site = other.getId_site();
		this.status = other.getStatus();
		this.pause_reason = other.getPause_reason();
		this.notes = other.getNotes();
		this.cadence = other.getCadence();
		this.start_date = other.getStart_date();
		this.end_date = other.getEnd_date();
		this.timezone = other.getTimezone();
		this.keep_cycle = other.isKeep_cycle();
		this.recipient_to = other.getRecipient_to() == null ? "" : String.join(",", other.getRecipient_to());
		this.recipient_cc = other.getRecipient_cc() == null ? "" : String.join(",", other.getRecipient_cc());
		this.modified_date = other.getModified_date();
		this.modified_by = other.getModified_by();
		this.site_name = other.getSite_name();
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

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
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

	public Integer getCadence() {
		return cadence;
	}

	public void setCadence(Integer cadence) {
		this.cadence = cadence;
	}

	public Integer getData_send_time() {
		return data_send_time;
	}

	public void setData_send_time(Integer data_send_time) {
		this.data_send_time = data_send_time;
	}

	public String getHash_id() {
		return hash_id;
	}

	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getTimezone_value() {
		return timezone_value;
	}

	public void setTimezone_value(String timezone_value) {
		this.timezone_value = timezone_value;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public List<ClientMonthlyDateEntity> getProductionReportList() {
		return productionReportList;
	}

	public void setProductionReportList(List<ClientMonthlyDateEntity> productionReportList) {
		this.productionReportList = productionReportList;
	}

	public List<ClientMonthlyDateEntity> getGenerationSummaryList() {
		return generationSummaryList;
	}

	public void setGenerationSummaryList(List<ClientMonthlyDateEntity> generationSummaryList) {
		this.generationSummaryList = generationSummaryList;
	}

	public Double getTotalActualGeneration() {
		return totalActualGeneration;
	}

	public void setTotalActualGeneration(Double totalActualGeneration) {
		this.totalActualGeneration = totalActualGeneration;
	}

	public Double getTotalExpectedGeneration() {
		return totalExpectedGeneration;
	}

	public void setTotalExpectedGeneration(Double totalExpectedGeneration) {
		this.totalExpectedGeneration = totalExpectedGeneration;
	}

	public Double getPoaIrradiance() {
		return poaIrradiance;
	}

	public void setPoaIrradiance(Double poaIrradiance) {
		this.poaIrradiance = poaIrradiance;
	}

	public Double getTotalActual() {
		return totalActual;
	}

	public void setTotalActual(Double totalActual) {
		this.totalActual = totalActual;
	}

	public Double getTotalExpected() {
		return totalExpected;
	}

	public void setTotalExpected(Double totalExpected) {
		this.totalExpected = totalExpected;
	}

	public Double getActualExpected() {
		return actualExpected;
	}

	public void setActualExpected(Double actualExpected) {
		this.actualExpected = actualExpected;
	}

	public List<PerformanceDataChartItemEntity> getInverterDataList() {
		return inverterDataList;
	}

	public void setInverterDataList(List<PerformanceDataChartItemEntity> inverterDataList) {
		this.inverterDataList = inverterDataList;
	}
	
	
	
}
