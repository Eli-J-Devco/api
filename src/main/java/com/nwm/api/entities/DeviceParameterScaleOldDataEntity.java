/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.Date;

public class DeviceParameterScaleOldDataEntity {
	private int id;
	private int id_employee;
	private int id_device;
	private int id_device_parameter;
	private String scale;
	private String date_from;
	private String date_to;
	private String datatablename;
	private String slug;
	private boolean is_active_power;
	private boolean is_energy;
	private boolean is_irradiance;
	
	
	
	public boolean isIs_active_power() {
		return is_active_power;
	}
	public void setIs_active_power(boolean is_active_power) {
		this.is_active_power = is_active_power;
	}
	public boolean isIs_energy() {
		return is_energy;
	}
	public void setIs_energy(boolean is_energy) {
		this.is_energy = is_energy;
	}
	public boolean isIs_irradiance() {
		return is_irradiance;
	}
	public void setIs_irradiance(boolean is_irradiance) {
		this.is_irradiance = is_irradiance;
	}
	public String getDate_from() {
		return date_from;
	}
	public void setDate_from(String date_from) {
		this.date_from = date_from;
	}
	public String getDate_to() {
		return date_to;
	}
	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
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
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	
}
