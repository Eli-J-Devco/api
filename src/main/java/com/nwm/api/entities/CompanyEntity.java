/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyEntity{
	
	private int id;
	private String name;
	private String description;
	private int status;
	private int is_delete;
	private String created_date;
	private String created_by;
	private String updated_date;
	private String updated_by;
	private String text;
	private int active;
	private int limit;
	private int offset;
	private int totalRecord;
	private String order_by;
	private String sort_column;
	private String keyword;
	private int screen_mode;
	private int entity_type;
	private String ein;
	private int state_of_incorporation;
	private int id_country;
	private String ba_street_address;
	private String ba_city;
	private int ba_id_state;
	private String ba_zip_code;
	private int ba_id_country;
	private String bill_street_address;
	private String bill_city;
	private String bill_zip_code;
	private int bill_id_state;
	private int bill_id_country;
	private String logo;
	private List<?> contactInformation;
	private String file_upload;
	private int primary_contact;
	
	
	
	public int getPrimary_contact() {
		return primary_contact;
	}
	public void setPrimary_contact(int primary_contact) {
		this.primary_contact = primary_contact;
	}
	public String getFile_upload() {
		return file_upload;
	}
	public void setFile_upload(String file_upload) {
		this.file_upload = file_upload;
	}
	public List<?> getContactInformation() {
		return contactInformation;
	}
	public void setContactInformation(List<?> contactInformation) {
		this.contactInformation = contactInformation;
	}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
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
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public String getOrder_by() {
		return order_by;
	}
	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}
	public String getSort_column() {
		return sort_column;
	}
	public void setSort_column(String sort_column) {
		this.sort_column = sort_column;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getScreen_mode() {
		return screen_mode;
	}
	public void setScreen_mode(int screen_mode) {
		this.screen_mode = screen_mode;
	}
	public int getEntity_type() {
		return entity_type;
	}
	public void setEntity_type(int entity_type) {
		this.entity_type = entity_type;
	}
	public String getEin() {
		return ein;
	}
	public void setEin(String ein) {
		this.ein = ein;
	}
	public int getState_of_incorporation() {
		return state_of_incorporation;
	}
	public void setState_of_incorporation(int state_of_incorporation) {
		this.state_of_incorporation = state_of_incorporation;
	}
	public int getId_country() {
		return id_country;
	}
	public void setId_country(int id_country) {
		this.id_country = id_country;
	}
	public String getBa_street_address() {
		return ba_street_address;
	}
	public void setBa_street_address(String ba_street_address) {
		this.ba_street_address = ba_street_address;
	}
	public String getBa_city() {
		return ba_city;
	}
	public void setBa_city(String ba_city) {
		this.ba_city = ba_city;
	}
	public int getBa_id_state() {
		return ba_id_state;
	}
	public void setBa_id_state(int ba_id_state) {
		this.ba_id_state = ba_id_state;
	}
	public String getBa_zip_code() {
		return ba_zip_code;
	}
	public void setBa_zip_code(String ba_zip_code) {
		this.ba_zip_code = ba_zip_code;
	}
	public int getBa_id_country() {
		return ba_id_country;
	}
	public void setBa_id_country(int ba_id_country) {
		this.ba_id_country = ba_id_country;
	}
	public String getBill_street_address() {
		return bill_street_address;
	}
	public void setBill_street_address(String bill_street_address) {
		this.bill_street_address = bill_street_address;
	}
	public String getBill_city() {
		return bill_city;
	}
	public void setBill_city(String bill_city) {
		this.bill_city = bill_city;
	}
	public String getBill_zip_code() {
		return bill_zip_code;
	}
	public void setBill_zip_code(String bill_zip_code) {
		this.bill_zip_code = bill_zip_code;
	}
	public int getBill_id_state() {
		return bill_id_state;
	}
	public void setBill_id_state(int bill_id_state) {
		this.bill_id_state = bill_id_state;
	}
	public int getBill_id_country() {
		return bill_id_country;
	}
	public void setBill_id_country(int bill_id_country) {
		this.bill_id_country = bill_id_country;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	
	
}
