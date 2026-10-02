/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class StringMonitoringStringDTO {
	private int id;
	private List<StringMonitoringParameterDTO> parameters;
	
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
}