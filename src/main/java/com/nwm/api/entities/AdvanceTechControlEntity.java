/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


public class AdvanceTechControlEntity {
	private int id_site;
	private int id_device;	
	private String advance_tech_pass;
	private String advance_tech_host;
	private String advance_tech_status;
	private String hash_id;
	private int id_employee;
	private String device_type;
	private String status_type;
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public String getAdvance_tech_pass() {
		return advance_tech_pass;
	}
	public void setAdvance_tech_pass(String advance_tech_pass) {
		this.advance_tech_pass = advance_tech_pass;
	}
	public String getAdvance_tech_host() {
		return advance_tech_host;
	}
	public void setAdvance_tech_host(String advance_tech_host) {
		this.advance_tech_host = advance_tech_host;
	}
	public String getAdvance_tech_status() {
		return advance_tech_status;
	}
	public void setAdvance_tech_status(String advance_tech_status) {
		this.advance_tech_status = advance_tech_status;
	}
	public String getHash_id() {
		return hash_id;
	}
	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	public String getStatus_type() {
		return status_type;
	}
	public void setStatus_type(String status_type) {
		this.status_type = status_type;
	}
	
	
	
	
}
