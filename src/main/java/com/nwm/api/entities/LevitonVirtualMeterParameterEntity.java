/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LevitonVirtualMeterParameterEntity{
	
	private int id;
	private int id_device;
	private int id_device_parameter;
	private int id_device_parameter_map;
	private String slug;
	private String name;
	private String unit;
	private int formula;
	private int id_site;
	private int enable_for_report;
	
	
	
	public int getEnable_for_report() {
		return enable_for_report;
	}
	public void setEnable_for_report(int enable_for_report) {
		this.enable_for_report = enable_for_report;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public int getId_device_parameter() {
		return id_device_parameter;
	}
	public void setId_device_parameter(int id_device_parameter) {
		this.id_device_parameter = id_device_parameter;
	}
	public int getId_device_parameter_map() {
		return id_device_parameter_map;
	}
	public void setId_device_parameter_map(int id_device_parameter_map) {
		this.id_device_parameter_map = id_device_parameter_map;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getFormula() {
		return formula;
	}
	public void setFormula(int formula) {
		this.formula = formula;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}

	
}
