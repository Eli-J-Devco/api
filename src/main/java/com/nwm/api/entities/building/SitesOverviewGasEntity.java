/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities.building;

import java.util.List;

import com.nwm.api.entities.DeviceEntity;

public class SitesOverviewGasEntity {
	private int id;
	private String id_filter;
	private String domain;
	private String timezone_value;
	private String start_date;
	private String end_date;
	private List<DeviceEntity> devices;
	private int[] id_device_type;
	private int[] meter_type;
	private String reading_field;
	private String domain_role;
	
	public String getDomain_role() {
		return domain_role;
	}
	public void setDomain_role(String domain_role) {
		this.domain_role = domain_role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getId_filter() {
		return id_filter;
	}
	public void setId_filter(String id_filter) {
		this.id_filter = id_filter;
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
	public List<DeviceEntity> getDevices() {
		return devices;
	}
	public void setDevices(List<DeviceEntity> devices) {
		this.devices = devices;
	}
	public int[] getId_device_type() {
		return id_device_type;
	}
	public void setId_device_type(int[] id_device_type) {
		this.id_device_type = id_device_type;
	}
	public int[] getMeter_type() {
		return meter_type;
	}
	public void setMeter_type(int[] meter_type) {
		this.meter_type = meter_type;
	}
	public String getReading_field() {
		return reading_field;
	}
	public void setReading_field(String reading_field) {
		this.reading_field = reading_field;
	}
}
