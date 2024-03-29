/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ScadaDeviceEntity {
	private int id;
	private int id_site;
	private String name;
	private String serial_number;
	private String modbusdevicenumber;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getModbusdevicenumber() {
		return modbusdevicenumber;
	}
	public void setModbusdevicenumber(String modbusdevicenumber) {
		this.modbusdevicenumber = modbusdevicenumber;
	}
	
}
