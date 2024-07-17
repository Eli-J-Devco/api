/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactInfoCompanyEntity{
	
	private int id;
	private int id_company;
	private String first_name;
	private String last_name;
	private String email;
	private String title_position;
	private int primary_contact;
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
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle_position() {
		return title_position;
	}
	public void setTitle_position(String title_position) {
		this.title_position = title_position;
	}
	public int getPrimary_contact() {
		return primary_contact;
	}
	public void setPrimary_contact(int primary_contact) {
		this.primary_contact = primary_contact;
	}
	
	
}
