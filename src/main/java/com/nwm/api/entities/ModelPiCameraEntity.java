/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelPiCameraEntity extends ModelBaseEntity {
	private String serial_number;
	private double reading_value;
	private String image;
	private String modbus_port;
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public double getReading_value() {
		return reading_value;
	}
	public void setReading_value(double reading_value) {
		this.reading_value = reading_value;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getModbus_port() {
		return modbus_port;
	}
	public void setModbus_port(String modbus_port) {
		this.modbus_port = modbus_port;
	}
	
	
	
	
}
