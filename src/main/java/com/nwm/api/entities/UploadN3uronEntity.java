/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class UploadN3uronEntity {
	private String site_name;
	private String device_name;
	private List<DeviceParameterN3uronEntity> parameters;
	
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public List<DeviceParameterN3uronEntity> getParameters() {
		return parameters;
	}
	public void setParameters(List<DeviceParameterN3uronEntity> parameters) {
		this.parameters = parameters;
	}
	
}
