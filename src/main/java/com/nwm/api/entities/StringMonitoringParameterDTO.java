/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

public class StringMonitoringParameterDTO {
	private int id;
	private int parameter_type;
	private Double value;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParameter_type() {
		return parameter_type;
	}
	public void setParameter_type(int parameter_type) {
		this.parameter_type = parameter_type;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
}