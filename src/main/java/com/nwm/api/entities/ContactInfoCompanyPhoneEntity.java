/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactInfoCompanyPhoneEntity{
	
	private int id;
	private int id_conact_info_company;
	private int phone_type;
	private String phone_number = "";
	private String phone_ext = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_conact_info_company() {
		return id_conact_info_company;
	}
	public void setId_conact_info_company(int id_conact_info_company) {
		this.id_conact_info_company = id_conact_info_company;
	}
	public int getPhone_type() {
		return phone_type;
	}
	public void setPhone_type(int phone_type) {
		this.phone_type = phone_type;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getPhone_ext() {
		return phone_ext;
	}
	public void setPhone_ext(String phone_ext) {
		this.phone_ext = phone_ext;
	}
	
	
	
}
