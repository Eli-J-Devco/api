/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ScadaDeviceEntity {
	private int id;
	private int id_site;
	private String hash_id_site;
	private String name;
	private String serial_number;
	private Integer modbusdevicenumber;
	private String datatablename;
	private String timezone_value;
	private Double energy_today;
	private Integer rating_ac_power;
	private String start_date;
	private String end_date;
	private String id_filter;
	private String commissioning;
	private int data_send_time;
	
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
	public String getHash_id_site() {
		return hash_id_site;
	}
	public void setHash_id_site(String hash_id_site) {
		this.hash_id_site = hash_id_site;
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
	public String getTimezone_value() {
		return timezone_value;
	}
	public void setTimezone_value(String timezone_value) {
		this.timezone_value = timezone_value;
	}
	public Double getEnergy_today() {
		return energy_today;
	}
	public void setEnergy_today(Double energy_today) {
		this.energy_today = energy_today;
	}
	public Integer getRating_ac_power() {
		return rating_ac_power;
	}
	public void setRating_ac_power(Integer rating_ac_power) {
		this.rating_ac_power = rating_ac_power;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getId_filter() {
		return id_filter;
	}
	public void setId_filter(String id_filter) {
		this.id_filter = id_filter;
	}
	public String getCommissioning() {
		return commissioning;
	}
	public void setCommissioning(String commissioning) {
		this.commissioning = commissioning;
	}
	public int getData_send_time() {
		return data_send_time;
	}
	public void setData_send_time(int data_send_time) {
		this.data_send_time = data_send_time;
	}
	
}
