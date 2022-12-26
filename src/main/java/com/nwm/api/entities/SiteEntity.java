/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;
import java.util.Date;
import java.util.List;

public class SiteEntity {
	private int id;
	private int id_company;
	private int id_country;
	private int id_time_zone;
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
	private double watts_3ph_total;
	private double sensor1_data;
	private double w_hours_total;
	
	private double w_hours_received;
	private double eer_this_month;
	private double total_energy_this_month;
	private double today_kwh;
	private double eer_last_month;
	private int total_error;
	private String alert_list;
	private String kpi_type;
	
	private List irradiance;
	private List power;
	private List energy;
	private String kpi_filter;
	private String offset_timezone;
	private List activeAlarm;
	
	
	private int total_site;
	private double installed_capacity;
	private double expected_last_month;
	private double expected_this_month;
	private double measured_today;
	private double measured_this_month;
	private double measured_last_month;
	private Float err_this_month;
	private Float err_last_month;
	private double err_today;
	private double expected_today;
	private String today;
	private String this_month;
	private String last_month;
	private String gallery;
	private String street_ws;
	private String file_upload;
	private String file_site_logo_upload;
	private String current_time;
	
	private double energy_this_year;
	private double energy_this_month;
	private double energy_today;
	private double ac_power;
	private double energy_lifetime;
	private String filterBy;
	private String start_date;
	private String end_date;
	private int device_type;
	private int id_site;
	private int id_device;
	private String localization_format;
	private String format_sql_short;
	private String format_sql_long;
	private String format_sql_string_short;
	private String format_sql_string_long;
	private String format_sql_string_mdy;
	private String offset_from;
	private String typeView;
	private String keyword;
	private String sort_column;
	private int screen_mode;
	private int is_manage;
	private int id_employee;
	private List id_sites;
	private String hash_id;
	private String view_minute;
	private int data_send_time;
	private int setup_send_time;
	private int start_date_time;
	private int end_date_time;
	private String customer_type;
	private List dataEmployee;
	private String label;
	private int value;
	private double totalGeneration;
	
	private double view_lat;
	private double view_lng;
	private int config_sunset_sunrise;
	private double power_today;
	private String datatablename;
	private List groupMeter;
	private String note;
	
	private String weather_icon;
	private String weather_description;
	private double inverter_power;
	private double meter_power;
	private int low_error;
	private int medium_error;
	private int heigh_error;
	private int is_rec_report;
	private String datalogger_ip;
	private int cf_start_time;
	private int cf_end_time;
	private int cf_alert_threshold;
	private String cf_email_subscribers;
	private String time_zone_value;
	private String rec_id;
	private String about;
	private int kiosk_view;
	private String site_logo;
	private List groupDevices;
	private List weatherStation;
	private double nvm_irradiance;
	private double PerformanceRatioYesterday;
	private double actualPower;
	private double nvm_temperature;
	private int id_site_group;
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
	public String getCommissioning() {
		return commissioning;
	}
	public void setCommissioning(String commissioning) {
		this.commissioning = commissioning;
	}
	public String getEmergency_contact() {
		return emergency_contact;
	}
	public void setEmergency_contact(String emergency_contact) {
		this.emergency_contact = emergency_contact;
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
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
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
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
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
	public String getSort_by() {
		return sort_by;
	}
	public void setSort_by(String sort_by) {
		this.sort_by = sort_by;
	}
	public String getAddress_short() {
		return address_short;
	}
	public void setAddress_short(String address_short) {
		this.address_short = address_short;
	}
	public double getWatts_3ph_total() {
		return watts_3ph_total;
	}
	public void setWatts_3ph_total(double watts_3ph_total) {
		this.watts_3ph_total = watts_3ph_total;
	}
	public double getSensor1_data() {
		return sensor1_data;
	}
	public void setSensor1_data(double sensor1_data) {
		this.sensor1_data = sensor1_data;
	}
	public double getW_hours_total() {
		return w_hours_total;
	}
	public void setW_hours_total(double w_hours_total) {
		this.w_hours_total = w_hours_total;
	}
	public double getW_hours_received() {
		return w_hours_received;
	}
	public void setW_hours_received(double w_hours_received) {
		this.w_hours_received = w_hours_received;
	}
	public double getEer_this_month() {
		return eer_this_month;
	}
	public void setEer_this_month(double eer_this_month) {
		this.eer_this_month = eer_this_month;
	}
	public double getTotal_energy_this_month() {
		return total_energy_this_month;
	}
	public void setTotal_energy_this_month(double total_energy_this_month) {
		this.total_energy_this_month = total_energy_this_month;
	}
	public double getToday_kwh() {
		return today_kwh;
	}
	public void setToday_kwh(double today_kwh) {
		this.today_kwh = today_kwh;
	}
	public double getEer_last_month() {
		return eer_last_month;
	}
	public void setEer_last_month(double eer_last_month) {
		this.eer_last_month = eer_last_month;
	}
	public int getTotal_error() {
		return total_error;
	}
	public void setTotal_error(int total_error) {
		this.total_error = total_error;
	}
	public String getAlert_list() {
		return alert_list;
	}
	public void setAlert_list(String alert_list) {
		this.alert_list = alert_list;
	}
	public String getKpi_type() {
		return kpi_type;
	}
	public void setKpi_type(String kpi_type) {
		this.kpi_type = kpi_type;
	}
	public List getIrradiance() {
		return irradiance;
	}
	public void setIrradiance(List irradiance) {
		this.irradiance = irradiance;
	}
	public List getPower() {
		return power;
	}
	public void setPower(List power) {
		this.power = power;
	}
	public List getEnergy() {
		return energy;
	}
	public void setEnergy(List energy) {
		this.energy = energy;
	}
	public String getKpi_filter() {
		return kpi_filter;
	}
	public void setKpi_filter(String kpi_filter) {
		this.kpi_filter = kpi_filter;
	}
	public String getOffset_timezone() {
		return offset_timezone;
	}
	public void setOffset_timezone(String offset_timezone) {
		this.offset_timezone = offset_timezone;
	}
	public List getActiveAlarm() {
		return activeAlarm;
	}
	public void setActiveAlarm(List activeAlarm) {
		this.activeAlarm = activeAlarm;
	}
	public int getTotal_site() {
		return total_site;
	}
	public void setTotal_site(int total_site) {
		this.total_site = total_site;
	}
	public double getInstalled_capacity() {
		return installed_capacity;
	}
	public void setInstalled_capacity(double installed_capacity) {
		this.installed_capacity = installed_capacity;
	}
	public double getExpected_last_month() {
		return expected_last_month;
	}
	public void setExpected_last_month(double expected_last_month) {
		this.expected_last_month = expected_last_month;
	}
	public double getExpected_this_month() {
		return expected_this_month;
	}
	public void setExpected_this_month(double expected_this_month) {
		this.expected_this_month = expected_this_month;
	}
	public double getMeasured_today() {
		return measured_today;
	}
	public void setMeasured_today(double measured_today) {
		this.measured_today = measured_today;
	}
	public double getMeasured_this_month() {
		return measured_this_month;
	}
	public void setMeasured_this_month(double measured_this_month) {
		this.measured_this_month = measured_this_month;
	}
	public double getMeasured_last_month() {
		return measured_last_month;
	}
	public void setMeasured_last_month(double measured_last_month) {
		this.measured_last_month = measured_last_month;
	}
	public Float getErr_this_month() {
		return err_this_month;
	}
	public void setErr_this_month(Float err_this_month) {
		this.err_this_month = err_this_month;
	}
	public Float getErr_last_month() {
		return err_last_month;
	}
	public void setErr_last_month(Float err_last_month) {
		this.err_last_month = err_last_month;
	}
	public double getErr_today() {
		return err_today;
	}
	public void setErr_today(double err_today) {
		this.err_today = err_today;
	}
	public double getExpected_today() {
		return expected_today;
	}
	public void setExpected_today(double expected_today) {
		this.expected_today = expected_today;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public String getThis_month() {
		return this_month;
	}
	public void setThis_month(String this_month) {
		this.this_month = this_month;
	}
	public String getLast_month() {
		return last_month;
	}
	public void setLast_month(String last_month) {
		this.last_month = last_month;
	}
	public String getGallery() {
		return gallery;
	}
	public void setGallery(String gallery) {
		this.gallery = gallery;
	}
	public String getStreet_ws() {
		return street_ws;
	}
	public void setStreet_ws(String street_ws) {
		this.street_ws = street_ws;
	}
	public String getFile_upload() {
		return file_upload;
	}
	public void setFile_upload(String file_upload) {
		this.file_upload = file_upload;
	}
	public String getFile_site_logo_upload() {
		return file_site_logo_upload;
	}
	public void setFile_site_logo_upload(String file_site_logo_upload) {
		this.file_site_logo_upload = file_site_logo_upload;
	}
	public String getCurrent_time() {
		return current_time;
	}
	public void setCurrent_time(String current_time) {
		this.current_time = current_time;
	}
	public double getEnergy_this_year() {
		return energy_this_year;
	}
	public void setEnergy_this_year(double energy_this_year) {
		this.energy_this_year = energy_this_year;
	}
	public double getEnergy_this_month() {
		return energy_this_month;
	}
	public void setEnergy_this_month(double energy_this_month) {
		this.energy_this_month = energy_this_month;
	}
	public double getEnergy_today() {
		return energy_today;
	}
	public void setEnergy_today(double energy_today) {
		this.energy_today = energy_today;
	}
	public double getAc_power() {
		return ac_power;
	}
	public void setAc_power(double ac_power) {
		this.ac_power = ac_power;
	}
	public double getEnergy_lifetime() {
		return energy_lifetime;
	}
	public void setEnergy_lifetime(double energy_lifetime) {
		this.energy_lifetime = energy_lifetime;
	}
	public String getFilterBy() {
		return filterBy;
	}
	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
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
	public int getDevice_type() {
		return device_type;
	}
	public void setDevice_type(int device_type) {
		this.device_type = device_type;
	}
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
	public String getTypeView() {
		return typeView;
	}
	public void setTypeView(String typeView) {
		this.typeView = typeView;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSort_column() {
		return sort_column;
	}
	public void setSort_column(String sort_column) {
		this.sort_column = sort_column;
	}
	public int getScreen_mode() {
		return screen_mode;
	}
	public void setScreen_mode(int screen_mode) {
		this.screen_mode = screen_mode;
	}
	public int getIs_manage() {
		return is_manage;
	}
	public void setIs_manage(int is_manage) {
		this.is_manage = is_manage;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	public List getId_sites() {
		return id_sites;
	}
	public void setId_sites(List id_sites) {
		this.id_sites = id_sites;
	}
	public String getHash_id() {
		return hash_id;
	}
	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}
	public String getView_minute() {
		return view_minute;
	}
	public void setView_minute(String view_minute) {
		this.view_minute = view_minute;
	}
	public int getData_send_time() {
		return data_send_time;
	}
	public void setData_send_time(int data_send_time) {
		this.data_send_time = data_send_time;
	}
	public int getSetup_send_time() {
		return setup_send_time;
	}
	public void setSetup_send_time(int setup_send_time) {
		this.setup_send_time = setup_send_time;
	}
	public int getStart_date_time() {
		return start_date_time;
	}
	public void setStart_date_time(int start_date_time) {
		this.start_date_time = start_date_time;
	}
	public int getEnd_date_time() {
		return end_date_time;
	}
	public void setEnd_date_time(int end_date_time) {
		this.end_date_time = end_date_time;
	}
	public String getCustomer_type() {
		return customer_type;
	}
	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}
	public List getDataEmployee() {
		return dataEmployee;
	}
	public void setDataEmployee(List dataEmployee) {
		this.dataEmployee = dataEmployee;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public double getTotalGeneration() {
		return totalGeneration;
	}
	public void setTotalGeneration(double totalGeneration) {
		this.totalGeneration = totalGeneration;
	}
	public double getView_lat() {
		return view_lat;
	}
	public void setView_lat(double view_lat) {
		this.view_lat = view_lat;
	}
	public double getView_lng() {
		return view_lng;
	}
	public void setView_lng(double view_lng) {
		this.view_lng = view_lng;
	}
	public int getConfig_sunset_sunrise() {
		return config_sunset_sunrise;
	}
	public void setConfig_sunset_sunrise(int config_sunset_sunrise) {
		this.config_sunset_sunrise = config_sunset_sunrise;
	}
	public double getPower_today() {
		return power_today;
	}
	public void setPower_today(double power_today) {
		this.power_today = power_today;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public List getGroupMeter() {
		return groupMeter;
	}
	public void setGroupMeter(List groupMeter) {
		this.groupMeter = groupMeter;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public double getInverter_power() {
		return inverter_power;
	}
	public void setInverter_power(double inverter_power) {
		this.inverter_power = inverter_power;
	}
	public double getMeter_power() {
		return meter_power;
	}
	public void setMeter_power(double meter_power) {
		this.meter_power = meter_power;
	}
	public int getLow_error() {
		return low_error;
	}
	public void setLow_error(int low_error) {
		this.low_error = low_error;
	}
	public int getMedium_error() {
		return medium_error;
	}
	public void setMedium_error(int medium_error) {
		this.medium_error = medium_error;
	}
	public int getHeigh_error() {
		return heigh_error;
	}
	public void setHeigh_error(int heigh_error) {
		this.heigh_error = heigh_error;
	}
	public int getIs_rec_report() {
		return is_rec_report;
	}
	public void setIs_rec_report(int is_rec_report) {
		this.is_rec_report = is_rec_report;
	}
	public String getDatalogger_ip() {
		return datalogger_ip;
	}
	public void setDatalogger_ip(String datalogger_ip) {
		this.datalogger_ip = datalogger_ip;
	}
	public int getCf_start_time() {
		return cf_start_time;
	}
	public void setCf_start_time(int cf_start_time) {
		this.cf_start_time = cf_start_time;
	}
	public int getCf_end_time() {
		return cf_end_time;
	}
	public void setCf_end_time(int cf_end_time) {
		this.cf_end_time = cf_end_time;
	}
	public int getCf_alert_threshold() {
		return cf_alert_threshold;
	}
	public void setCf_alert_threshold(int cf_alert_threshold) {
		this.cf_alert_threshold = cf_alert_threshold;
	}
	public String getCf_email_subscribers() {
		return cf_email_subscribers;
	}
	public void setCf_email_subscribers(String cf_email_subscribers) {
		this.cf_email_subscribers = cf_email_subscribers;
	}
	public String getTime_zone_value() {
		return time_zone_value;
	}
	public void setTime_zone_value(String time_zone_value) {
		this.time_zone_value = time_zone_value;
	}
	public String getRec_id() {
		return rec_id;
	}
	public void setRec_id(String rec_id) {
		this.rec_id = rec_id;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public int getKiosk_view() {
		return kiosk_view;
	}
	public void setKiosk_view(int kiosk_view) {
		this.kiosk_view = kiosk_view;
	}
	public String getSite_logo() {
		return site_logo;
	}
	public void setSite_logo(String site_logo) {
		this.site_logo = site_logo;
	}
	public List getGroupDevices() {
		return groupDevices;
	}
	public void setGroupDevices(List groupDevices) {
		this.groupDevices = groupDevices;
	}
	public List getWeatherStation() {
		return weatherStation;
	}
	public void setWeatherStation(List weatherStation) {
		this.weatherStation = weatherStation;
	}
	public double getNvm_irradiance() {
		return nvm_irradiance;
	}
	public void setNvm_irradiance(double nvm_irradiance) {
		this.nvm_irradiance = nvm_irradiance;
	}
	public double getPerformanceRatioYesterday() {
		return PerformanceRatioYesterday;
	}
	public void setPerformanceRatioYesterday(double performanceRatioYesterday) {
		PerformanceRatioYesterday = performanceRatioYesterday;
	}
	public double getActualPower() {
		return actualPower;
	}
	public void setActualPower(double actualPower) {
		this.actualPower = actualPower;
	}
	public double getNvm_temperature() {
		return nvm_temperature;
	}
	public void setNvm_temperature(double nvm_temperature) {
		this.nvm_temperature = nvm_temperature;
	}
	public int getId_site_group() {
		return id_site_group;
	}
	public void setId_site_group(int id_site_group) {
		this.id_site_group = id_site_group;
	}
	
	
	
	
	
	
}
