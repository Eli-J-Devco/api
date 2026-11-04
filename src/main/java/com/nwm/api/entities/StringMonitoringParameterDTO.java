/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

public class StringMonitoringParameterDTO {
	private int id;
	private String slug;
	private String name;
	private Integer rounding_decimals;
	private int parameter_type;
	private Double value;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getRounding_decimals() {
		return rounding_decimals;
	}
	public void setRounding_decimals(Integer rounding_decimals) {
		this.rounding_decimals = rounding_decimals;
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