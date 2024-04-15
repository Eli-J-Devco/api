/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ScadaDeviceEntity {
	private int id;
	private String id_site;
	private String name;
	private String serial_number;
	private Integer modbusdevicenumber;
	private String datatablename;
	private String time_zone_value;
	private String energy_today;
	private Integer rating_ac_power;
	private String start_datetime;
	private String end_datetime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getId_site() {
		return id_site;
	}
	public void setId_site(String id_site) {
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
	public Integer getModbusdevicenumber() {
		return modbusdevicenumber;
	}
	public void setModbusdevicenumber(Integer modbusdevicenumber) {
		this.modbusdevicenumber = modbusdevicenumber;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getTime_zone_value() {
		return time_zone_value;
	}
	public void setTime_zone_value(String time_zone_value) {
		this.time_zone_value = time_zone_value;
	}
	public String getEnergy_today() {
		return energy_today;
	}
	public void setEnergy_today(String energy_today) {
		this.energy_today = energy_today;
	}
	public Integer getRating_ac_power() {
		return rating_ac_power;
	}
	public void setRating_ac_power(Integer rating_ac_power) {
		this.rating_ac_power = rating_ac_power;
	}
	public String getStart_datetime() {
		return start_datetime;
	}
	public void setStart_datetime(String start_datetime) {
		this.start_datetime = start_datetime;
	}
	public String getEnd_datetime() {
		return end_datetime;
	}
	public void setEnd_datetime(String end_datetime) {
		this.end_datetime = end_datetime;
	}
	
}
