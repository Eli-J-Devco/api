/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ReportLogs extends LogBase {
	private String site_mapping;
	private Integer id_site;
	private String name;
	private Integer type_report;
	private Integer cadence_range;
	private LocalDate date_from;
	private LocalDate date_to;
	private String subscribers;
	private Integer data_intervals;
	private Integer file_type;
	private Integer id_sub_group;
	private Integer type_option;
	private Boolean schedule_enable;
	private Integer periodicity;
	private String time_schedule;
	private String days_week;
	private Integer duplicated_from;
	
	public String getSite_mapping() {
		return site_mapping;
	}
	public void setSite_mapping(String site_mapping) {
		this.site_mapping = site_mapping;
	}
	public Integer getId_site() {
		return id_site;
	}
	public void setId_site(Integer id_site) {
		this.id_site = id_site;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType_report() {
		return type_report;
	}
	public void setType_report(Integer type_report) {
		this.type_report = type_report;
	}
	public Integer getCadence_range() {
		return cadence_range;
	}
	public void setCadence_range(Integer cadence_range) {
		this.cadence_range = cadence_range;
	}
	public LocalDate getDate_from() {
		return date_from;
	}
	public void setDate_from(LocalDateTime date_from) {
		this.date_from = date_from.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDate();
	}
	public LocalDate getDate_to() {
		return date_to;
	}
	public void setDate_to(LocalDateTime date_to) {
		this.date_to = date_to.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDate();
	}
	public String getSubscribers() {
		return subscribers;
	}
	public void setSubscribers(String subscribers) {
		this.subscribers = subscribers;
	}
	public Integer getData_intervals() {
		return data_intervals;
	}
	public void setData_intervals(Integer data_intervals) {
		this.data_intervals = data_intervals;
	}
	public Integer getFile_type() {
		return file_type;
	}
	public void setFile_type(Integer file_type) {
		this.file_type = file_type;
	}
	public Integer getId_sub_group() {
		return id_sub_group;
	}
	public void setId_sub_group(Integer id_sub_group) {
		this.id_sub_group = id_sub_group;
	}
	public Integer getType_option() {
		return type_option;
	}
	public void setType_option(Integer type_option) {
		this.type_option = type_option;
	}
	public Boolean getSchedule_enable() {
		return schedule_enable;
	}
	public void setSchedule_enable(Boolean schedule_enable) {
		this.schedule_enable = schedule_enable;
	}
	public Integer getPeriodicity() {
		return periodicity;
	}
	public void setPeriodicity(Integer periodicity) {
		this.periodicity = periodicity;
	}
	public String getTime_schedule() {
		return time_schedule;
	}
	public void setTime_schedule(LocalDateTime time_schedule) {
		this.time_schedule = time_schedule.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	public String getDays_week() {
		return days_week;
	}
	public void setDays_week(String days_week) {
		this.days_week = days_week;
	}
	public Integer getDuplicated_from() {
		return duplicated_from;
	}
	public void setDuplicated_from(Integer duplicated_from) {
		this.duplicated_from = duplicated_from;
	}
}
