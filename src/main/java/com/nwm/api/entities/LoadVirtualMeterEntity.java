/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class LoadVirtualMeterEntity extends SortEntity {
	private int id;
	private int id_company;
	private int id_country;
	private int id_time_zone;
	private String name;
	private String pv_table;
	private String load_table;
	private String virtual_table;
	private int id_device_virtual;
	
	public int getId_device_virtual() {
		return id_device_virtual;
	}
	public void setId_device_virtual(int id_device_virtual) {
		this.id_device_virtual = id_device_virtual;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_company() {
		return id_company;
	}
	public void setId_company(int id_company) {
		this.id_company = id_company;
	}
	public int getId_country() {
		return id_country;
	}
	public void setId_country(int id_country) {
		this.id_country = id_country;
	}
	public int getId_time_zone() {
		return id_time_zone;
	}
	public void setId_time_zone(int id_time_zone) {
		this.id_time_zone = id_time_zone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPv_table() {
		return pv_table;
	}
	public void setPv_table(String pv_table) {
		this.pv_table = pv_table;
	}
	public String getLoad_table() {
		return load_table;
	}
	public void setLoad_table(String load_table) {
		this.load_table = load_table;
	}
	public String getVirtual_table() {
		return virtual_table;
	}
	public void setVirtual_table(String virtual_table) {
		this.virtual_table = virtual_table;
	}
	
	
	
	
	
}
