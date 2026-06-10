/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class UploadWaterAndGasEntity {
	private String serial_number;
	private int id_device;
	private List<DeviceParameterWaterAndGasEntity> parameters;
	
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
	public List<DeviceParameterWaterAndGasEntity> getParameters() {
		return parameters;
	}
	public void setParameters(List<DeviceParameterWaterAndGasEntity> parameters) {
		this.parameters = parameters;
	}
	
}
