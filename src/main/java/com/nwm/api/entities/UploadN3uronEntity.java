/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class UploadN3uronEntity {
	private String serial_number;
	private int id_device;
	private List<DeviceParameterN3uronEntity> parameters;
	
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public List<DeviceParameterN3uronEntity> getParameters() {
		return parameters;
	}
	public void setParameters(List<DeviceParameterN3uronEntity> parameters) {
		this.parameters = parameters;
	}
	
}
