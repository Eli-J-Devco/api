/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class DeviceEnergyBySiteRequest {
	private String date;
	private int id_site;
	private String deviceTypeId;
	private String granularityId;
	private String domain;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public String getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(String deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public String getGranularityId() {
		return granularityId;
	}
	public void setGranularityId(String granularityId) {
		this.granularityId = granularityId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
}
