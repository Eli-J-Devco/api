/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;
import java.util.Date;
import java.util.List;

public class PortfolioEntity {
	private int id;
	private int id_customer;
	private int id_country;
	private int id_time_zone;
	private int id_company;
	private String name;
	private String street;
	private double lat;
	private double lng;
	private String old_data;
	private String number;
	private String postal_code;
	private String city;
	private String state;
	private double ac_capacity;
	private double dc_capacity;
	private int status;
	private Date created_date;
	private String created_by;
	private Date updated_date;
	private String built_since;
	private int limit;
	private int offset;
	private int totalRecord;
	private String order_by;
	private String sort_column;
	private double irradiance_now;
	private String offset_timezone;
	private String current_time;
	private String gallery;
	private String localization_format;
	private String format_sql_short;
	private String format_sql_long;
	private String format_sql_string_short;
	private String format_sql_string_long;
	private String format_sql_string_mdy;
	private String offset_from;
	private int id_site_type;
	private String keyword;
	private int screen_mode;
	private List id_sites;
	private double energy_now;
	private String weather_icon;
	private String weather_description;
	private List alerts;
	private String icon_alert;
	private String id_error_alert;
	private int id_device;
	private int totalSite;
	private int totalAlert;
	private double totalPower;
	private int column;
	private String value_filter;
	private int id_employee;
	private List inverters;
	private List meters;
	private String note;
	private String hash_id_site_group;
	private String hash_id_site_sub_group;
	private int is_supper_admin;
	
	
	
	public int getIs_supper_admin() {
		return is_supper_admin;
	}
	public void setIs_supper_admin(int is_supper_admin) {
		this.is_supper_admin = is_supper_admin;
	}
	public String getHash_id_site_sub_group() {
		return hash_id_site_sub_group;
	}
	public void setHash_id_site_sub_group(String hash_id_site_sub_group) {
		this.hash_id_site_sub_group = hash_id_site_sub_group;
	}
	public String getHash_id_site_group() {
		return hash_id_site_group;
	}
	public void setHash_id_site_group(String hash_id_site_group) {
		this.hash_id_site_group = hash_id_site_group;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List getInverters() {
		return inverters;
	}
	public void setInverters(List inverters) {
		this.inverters = inverters;
	}
	public List getMeters() {
		return meters;
	}
	public void setMeters(List meters) {
		this.meters = meters;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_customer() {
		return id_customer;
	}
	public void setId_customer(int id_customer) {
		this.id_customer = id_customer;
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
	public int getId_company() {
		return id_company;
	}
	public void setId_company(int id_company) {
		this.id_company = id_company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getOld_data() {
		return old_data;
	}
	public void setOld_data(String old_data) {
		this.old_data = old_data;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public double getAc_capacity() {
		return ac_capacity;
	}
	public void setAc_capacity(double ac_capacity) {
		this.ac_capacity = ac_capacity;
	}
	public double getDc_capacity() {
		return dc_capacity;
	}
	public void setDc_capacity(double dc_capacity) {
		this.dc_capacity = dc_capacity;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public Date getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}
	public String getBuilt_since() {
		return built_since;
	}
	public void setBuilt_since(String built_since) {
		this.built_since = built_since;
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
	public double getIrradiance_now() {
		return irradiance_now;
	}
	public void setIrradiance_now(double irradiance_now) {
		this.irradiance_now = irradiance_now;
	}
	public String getOffset_timezone() {
		return offset_timezone;
	}
	public void setOffset_timezone(String offset_timezone) {
		this.offset_timezone = offset_timezone;
	}
	public String getCurrent_time() {
		return current_time;
	}
	public void setCurrent_time(String current_time) {
		this.current_time = current_time;
	}
	public String getGallery() {
		return gallery;
	}
	public void setGallery(String gallery) {
		this.gallery = gallery;
	}
	public String getLocalization_format() {
		return localization_format;
	}
	public void setLocalization_format(String localization_format) {
		this.localization_format = localization_format;
	}
	public String getFormat_sql_short() {
		return format_sql_short;
	}
	public void setFormat_sql_short(String format_sql_short) {
		this.format_sql_short = format_sql_short;
	}
	public String getFormat_sql_long() {
		return format_sql_long;
	}
	public void setFormat_sql_long(String format_sql_long) {
		this.format_sql_long = format_sql_long;
	}
	public String getFormat_sql_string_short() {
		return format_sql_string_short;
	}
	public void setFormat_sql_string_short(String format_sql_string_short) {
		this.format_sql_string_short = format_sql_string_short;
	}
	public String getFormat_sql_string_long() {
		return format_sql_string_long;
	}
	public void setFormat_sql_string_long(String format_sql_string_long) {
		this.format_sql_string_long = format_sql_string_long;
	}
	public String getFormat_sql_string_mdy() {
		return format_sql_string_mdy;
	}
	public void setFormat_sql_string_mdy(String format_sql_string_mdy) {
		this.format_sql_string_mdy = format_sql_string_mdy;
	}
	public String getOffset_from() {
		return offset_from;
	}
	public void setOffset_from(String offset_from) {
		this.offset_from = offset_from;
	}
	public int getId_site_type() {
		return id_site_type;
	}
	public void setId_site_type(int id_site_type) {
		this.id_site_type = id_site_type;
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
	public List getId_sites() {
		return id_sites;
	}
	public void setId_sites(List id_sites) {
		this.id_sites = id_sites;
	}
	public double getEnergy_now() {
		return energy_now;
	}
	public void setEnergy_now(double energy_now) {
		this.energy_now = energy_now;
	}
	public String getWeather_icon() {
		return weather_icon;
	}
	public void setWeather_icon(String weather_icon) {
		this.weather_icon = weather_icon;
	}
	public String getWeather_description() {
		return weather_description;
	}
	public void setWeather_description(String weather_description) {
		this.weather_description = weather_description;
	}
	public List getAlerts() {
		return alerts;
	}
	public void setAlerts(List alerts) {
		this.alerts = alerts;
	}
	public String getIcon_alert() {
		return icon_alert;
	}
	public void setIcon_alert(String icon_alert) {
		this.icon_alert = icon_alert;
	}
	public String getId_error_alert() {
		return id_error_alert;
	}
	public void setId_error_alert(String id_error_alert) {
		this.id_error_alert = id_error_alert;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public int getTotalSite() {
		return totalSite;
	}
	public void setTotalSite(int totalSite) {
		this.totalSite = totalSite;
	}
	public int getTotalAlert() {
		return totalAlert;
	}
	public void setTotalAlert(int totalAlert) {
		this.totalAlert = totalAlert;
	}
	public double getTotalPower() {
		return totalPower;
	}
	public void setTotalPower(double totalPower) {
		this.totalPower = totalPower;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public String getValue_filter() {
		return value_filter;
	}
	public void setValue_filter(String value_filter) {
		this.value_filter = value_filter;
	}
	
	
	
	
}
