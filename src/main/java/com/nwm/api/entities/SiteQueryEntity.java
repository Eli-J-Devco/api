/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;
import java.util.Date;
import java.util.List;

public class SiteQueryEntity {
	private int id;
	private int id_customer;
	private int id_country;
	private int id_time_zone;
	private int id_site_type;
	private String name;
	private String street;
	private double lat;
	private double lng;
	private String old_data;
	private String number;
	private String postal_code;
	private String city;
	private String state;
	private String commissioning;
	private String emergency_contact;
	private double ac_capacity;
	private double dc_capacity;
	private int status;
	private int is_delete;
	private Date created_date;
	private String created_by;
	private Date updated_date;
	private String updated_by;
	private String built_since;
	private int limit;
	private int offset;
	private int totalRecord;
	private String order_by;
	private String sort_by;
	private String address_short;
	private String offset_timezone;
	private List list_device;
	private String gallery;
	private String street_ws;
	private String file_upload;
	private String current_time;
	private int id_site;
	private int total_error;
	private String hash_id;
	private int data_send_time;
	private String site_name;
	private String hash_id_site;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the id_customer
	 */
	public int getId_customer() {
		return id_customer;
	}
	/**
	 * @param id_customer the id_customer to set
	 */
	public void setId_customer(int id_customer) {
		this.id_customer = id_customer;
	}
	/**
	 * @return the id_country
	 */
	public int getId_country() {
		return id_country;
	}
	/**
	 * @param id_country the id_country to set
	 */
	public void setId_country(int id_country) {
		this.id_country = id_country;
	}
	/**
	 * @return the id_time_zone
	 */
	public int getId_time_zone() {
		return id_time_zone;
	}
	/**
	 * @param id_time_zone the id_time_zone to set
	 */
	public void setId_time_zone(int id_time_zone) {
		this.id_time_zone = id_time_zone;
	}
	/**
	 * @return the id_site_type
	 */
	public int getId_site_type() {
		return id_site_type;
	}
	/**
	 * @param id_site_type the id_site_type to set
	 */
	public void setId_site_type(int id_site_type) {
		this.id_site_type = id_site_type;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}
	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}
	/**
	 * @return the old_data
	 */
	public String getOld_data() {
		return old_data;
	}
	/**
	 * @param old_data the old_data to set
	 */
	public void setOld_data(String old_data) {
		this.old_data = old_data;
	}
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return the postal_code
	 */
	public String getPostal_code() {
		return postal_code;
	}
	/**
	 * @param postal_code the postal_code to set
	 */
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the commissioning
	 */
	public String getCommissioning() {
		return commissioning;
	}
	/**
	 * @param commissioning the commissioning to set
	 */
	public void setCommissioning(String commissioning) {
		this.commissioning = commissioning;
	}
	/**
	 * @return the emergency_contact
	 */
	public String getEmergency_contact() {
		return emergency_contact;
	}
	/**
	 * @param emergency_contact the emergency_contact to set
	 */
	public void setEmergency_contact(String emergency_contact) {
		this.emergency_contact = emergency_contact;
	}
	/**
	 * @return the ac_capacity
	 */
	public double getAc_capacity() {
		return ac_capacity;
	}
	/**
	 * @param ac_capacity the ac_capacity to set
	 */
	public void setAc_capacity(double ac_capacity) {
		this.ac_capacity = ac_capacity;
	}
	/**
	 * @return the dc_capacity
	 */
	public double getDc_capacity() {
		return dc_capacity;
	}
	/**
	 * @param dc_capacity the dc_capacity to set
	 */
	public void setDc_capacity(double dc_capacity) {
		this.dc_capacity = dc_capacity;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the is_delete
	 */
	public int getIs_delete() {
		return is_delete;
	}
	/**
	 * @param is_delete the is_delete to set
	 */
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	/**
	 * @return the created_date
	 */
	public Date getCreated_date() {
		return created_date;
	}
	/**
	 * @param created_date the created_date to set
	 */
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	/**
	 * @return the created_by
	 */
	public String getCreated_by() {
		return created_by;
	}
	/**
	 * @param created_by the created_by to set
	 */
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	/**
	 * @return the updated_date
	 */
	public Date getUpdated_date() {
		return updated_date;
	}
	/**
	 * @param updated_date the updated_date to set
	 */
	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}
	/**
	 * @return the updated_by
	 */
	public String getUpdated_by() {
		return updated_by;
	}
	/**
	 * @param updated_by the updated_by to set
	 */
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	/**
	 * @return the built_since
	 */
	public String getBuilt_since() {
		return built_since;
	}
	/**
	 * @param built_since the built_since to set
	 */
	public void setBuilt_since(String built_since) {
		this.built_since = built_since;
	}
	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	/**
	 * @return the totalRecord
	 */
	public int getTotalRecord() {
		return totalRecord;
	}
	/**
	 * @param totalRecord the totalRecord to set
	 */
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	/**
	 * @return the order_by
	 */
	public String getOrder_by() {
		return order_by;
	}
	/**
	 * @param order_by the order_by to set
	 */
	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}
	/**
	 * @return the sort_by
	 */
	public String getSort_by() {
		return sort_by;
	}
	/**
	 * @param sort_by the sort_by to set
	 */
	public void setSort_by(String sort_by) {
		this.sort_by = sort_by;
	}
	/**
	 * @return the address_short
	 */
	public String getAddress_short() {
		return address_short;
	}
	/**
	 * @param address_short the address_short to set
	 */
	public void setAddress_short(String address_short) {
		this.address_short = address_short;
	}
	/**
	 * @return the offset_timezone
	 */
	public String getOffset_timezone() {
		return offset_timezone;
	}
	/**
	 * @param offset_timezone the offset_timezone to set
	 */
	public void setOffset_timezone(String offset_timezone) {
		this.offset_timezone = offset_timezone;
	}
	/**
	 * @return the list_device
	 */
	public List getList_device() {
		return list_device;
	}
	/**
	 * @param list_device the list_device to set
	 */
	public void setList_device(List list_device) {
		this.list_device = list_device;
	}
	/**
	 * @return the gallery
	 */
	public String getGallery() {
		return gallery;
	}
	/**
	 * @param gallery the gallery to set
	 */
	public void setGallery(String gallery) {
		this.gallery = gallery;
	}
	/**
	 * @return the street_ws
	 */
	public String getStreet_ws() {
		return street_ws;
	}
	/**
	 * @param street_ws the street_ws to set
	 */
	public void setStreet_ws(String street_ws) {
		this.street_ws = street_ws;
	}
	/**
	 * @return the file_upload
	 */
	public String getFile_upload() {
		return file_upload;
	}
	/**
	 * @param file_upload the file_upload to set
	 */
	public void setFile_upload(String file_upload) {
		this.file_upload = file_upload;
	}
	/**
	 * @return the current_time
	 */
	public String getCurrent_time() {
		return current_time;
	}
	/**
	 * @param current_time the current_time to set
	 */
	public void setCurrent_time(String current_time) {
		this.current_time = current_time;
	}
	/**
	 * @return the id_site
	 */
	public int getId_site() {
		return id_site;
	}
	/**
	 * @param id_site the id_site to set
	 */
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	/**
	 * @return the total_error
	 */
	public int getTotal_error() {
		return total_error;
	}
	/**
	 * @param total_error the total_error to set
	 */
	public void setTotal_error(int total_error) {
		this.total_error = total_error;
	}
	/**
	 * @return the hash_id
	 */
	public String getHash_id() {
		return hash_id;
	}
	/**
	 * @param hash_id the hash_id to set
	 */
	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}
	/**
	 * @return the data_send_time
	 */
	public int getData_send_time() {
		return data_send_time;
	}
	/**
	 * @param data_send_time the data_send_time to set
	 */
	public void setData_send_time(int data_send_time) {
		this.data_send_time = data_send_time;
	}
	/**
	 * @return the site_name
	 */
	public String getSite_name() {
		return site_name;
	}
	/**
	 * @param site_name the site_name to set
	 */
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	/**
	 * @return the hash_id_site
	 */
	public String getHash_id_site() {
		return hash_id_site;
	}
	/**
	 * @param hash_id_site the hash_id_site to set
	 */
	public void setHash_id_site(String hash_id_site) {
		this.hash_id_site = hash_id_site;
	}

	
	
}
