/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class StringMonitoringMpptDTO {
	private int id;
	private List<StringMonitoringParameterDTO> parameters;
	private List<StringMonitoringStringDTO> strings;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<StringMonitoringParameterDTO> getParameters() {
		return parameters;
	}
	public void setParameters(List<StringMonitoringParameterDTO> parameters) {
		this.parameters = parameters;
	}
	public List<StringMonitoringStringDTO> getStrings() {
		return strings;
	}
	public void setStrings(List<StringMonitoringStringDTO> strings) {
		this.strings = strings;
	}
}