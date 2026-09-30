/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class StringMonitoringConfiguredDeviceResponse {
	private int id;
	private String name;
	private Double value;
	private String datatablename;
	private List<StringMonitoringMpptDTO> mppts;
	
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
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public List<StringMonitoringMpptDTO> getMppts() {
		return mppts;
	}
	public void setMppts(List<StringMonitoringMpptDTO> mppts) {
		this.mppts = mppts;
	}
}