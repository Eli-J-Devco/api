/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class TimeZoneEntity{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String value;
	private String offset;
	private String status;
	private String is_delete;
	private String created_date;
	private String created_by;
	private String updated_date;
	private String updated_by;
	private String text;
	private String abbreviation_std;
	private String abbreviation_dst;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAbbreviation_std() {
		return abbreviation_std;
	}
	public void setAbbreviation_std(String abbreviation_std) {
		this.abbreviation_std = abbreviation_std;
	}
	public String getAbbreviation_dst() {
		return abbreviation_dst;
	}
	public void setAbbreviation_dst(String abbreviation_dst) {
		this.abbreviation_dst = abbreviation_dst;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
