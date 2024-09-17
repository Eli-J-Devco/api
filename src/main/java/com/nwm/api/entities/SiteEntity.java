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
	private String address;
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
	private String file_diagram_upload;
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
	private int site_default;
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
	private int is_hiding;
	private String tag_device;
	private String tag_site;
	
	private double view_lat;
	private double view_lng;
	private int config_sunset_sunrise;
	private double power_today;
	private String datatablename;
	private List groupMeter;
	private String note;
	private String customer_name;
	private String ids_employee;
	private String company;
	private String site_group;
	private String time_zone;
	private String last_modified;
	
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
	private String diagram;
	private List groupDevices;
	private List weatherStation;
	private double nvm_irradiance;
	private double PerformanceRatioYesterday;
	private double actualPower;
	private double nvm_temperature;
	private int id_site_group;
	private Integer id_site_sub_group;
	private int timezone_datalogger;
	private String read_data_all;
	private List devices;
	private String ru_id;
	private String gu_id;
	private int unit_type_temp;
	private int unit_wind_speed;
	private int pv_model;
	
	private String ftp_server;
	private String ftp_user;
	private String ftp_pass;
	private String ftp_port;
	private String ftp_folder;
	private int datalogger_type;
	
	private String display_timezone;
	private int enable_virtual_device;
	
	private String alert_mail_cc;
	private String alert_mail_bcc;
	
	private List hidden_data_list;
	
	private Double device_ac_rating;
	private String ac_capacity_condition;
	private String dc_capacity_condition;
	private String device_ac_rating_condition;
	private String built_since_condition;
	private String commissioning_condition;
	private List ids_inverter_group;
	private int is_supper_admin;
	private int virtual_status;
	private String table_data_virtual;
	private String table_data_report;
	private String expiration;
	private int is_show_each_meter;
	private int enable_alert;
	private List errorLevel;
	private int totalInverter;
	private int totalMeter;
	private String listGroup;
	private int totalWeatherStation;
	private boolean filterEnabled;
	
	private String serial_number;
	private String mqtt_host;
	private int mqtt_port;
	private String mqtt_protocol;
	private String mqtt_username;
	private String mqtt_password;
	private String sunrise;
	private String sunset;
	private Integer reporting_region;
	private int is_solar_site;
	private String domain;
	private String site_domain_type;
	private boolean have_poa;
	private int time_queue;
	private int threshold_item_max;

	private int site_type; // 1: Solar 2: Building
	private String dashboard_overview_note;
	private String g_company_name;
	private String g_account_number;
	private int g_voltage_level_type; // 1: 120/208V 2: 277/480V 3: Other
	private String g_voltage_level;
	private String g_pcc_location;
	private double g_maximum_capacity;
	private String g_interconnection_agreement_details;
	private String g_grid_contraints;
	private String g_existing_ders;
	private double g_der_capacities;
	private double g_tariff_structure_rate_schedule;
	private double g_net_metering_feed_in_tariff;
	private String g_power_quality_requirements;
	private double g_transformer_size;
	private double g_transformer_type;
	private int g_status;
	private double g_lat;
	private double g_lng;
	
	private List floorList;
	private List areaList;
	private List buildingList;
	private List roomList;
	private String siteAreaJSON;
	private String siteAreaBuildingJSON;
	private String siteAreaBuildingFloorJSON;
	private String siteAreaBuildingFloorRoomJSON;
	private String dataEmployeeJSON;
	private int tab_menu;
	
	private String gas_company_name;
	private String gas_account_number;
	private double gas_average_daily_consumption;
	private double gas_peak_demand;
	private int gas_service_type;
	private double gas_service_pressure;
	private String gas_interconnection_agreement;
	private String gas_complicance_standard;
	private String gas_safety_features;
	private String gas_additional_notes;
	private int gas_billing_cycle;
	private String gas_tariff_structure;
	private String gas_billing_address;
	
	
	
	public int getTab_menu() {
		return tab_menu;
	}
	public void setTab_menu(int tab_menu) {
		this.tab_menu = tab_menu;
	}
	public String getDataEmployeeJSON() {
		return dataEmployeeJSON;
	}
	public void setDataEmployeeJSON(String dataEmployeeJSON) {
		this.dataEmployeeJSON = dataEmployeeJSON;
	}
	public List getRoomList() {
		return roomList;
	}
	public void setRoomList(List roomList) {
		this.roomList = roomList;
	}
	public String getSiteAreaBuildingFloorRoomJSON() {
		return siteAreaBuildingFloorRoomJSON;
	}
	public void setSiteAreaBuildingFloorRoomJSON(String siteAreaBuildingFloorRoomJSON) {
		this.siteAreaBuildingFloorRoomJSON = siteAreaBuildingFloorRoomJSON;
	}
	public String getSiteAreaBuildingFloorJSON() {
		return siteAreaBuildingFloorJSON;
	}
	public void setSiteAreaBuildingFloorJSON(String siteAreaBuildingFloorJSON) {
		this.siteAreaBuildingFloorJSON = siteAreaBuildingFloorJSON;
	}
	public String getSiteAreaBuildingJSON() {
		return siteAreaBuildingJSON;
	}
	public void setSiteAreaBuildingJSON(String siteAreaBuildingJSON) {
		this.siteAreaBuildingJSON = siteAreaBuildingJSON;
	}
	public List getBuildingList() {
		return buildingList;
	}
	public void setBuildingList(List buildingList) {
		this.buildingList = buildingList;
	}
	public String getSiteAreaJSON() {
		return siteAreaJSON;
	}
	public void setSiteAreaJSON(String siteAreaJSON) {
		this.siteAreaJSON = siteAreaJSON;
	}
	public List getAreaList() {
		return areaList;
	}
	public void setAreaList(List areaList) {
		this.areaList = areaList;
	}
	public int getSite_type() {
		return site_type;
	}
	public void setSite_type(int site_type) {
		this.site_type = site_type;
	}
	public String getDashboard_overview_note() {
		return dashboard_overview_note;
	}
	public void setDashboard_overview_note(String dashboard_overview_note) {
		this.dashboard_overview_note = dashboard_overview_note;
	}
	public String getG_company_name() {
		return g_company_name;
	}
	public void setG_company_name(String g_company_name) {
		this.g_company_name = g_company_name;
	}
	public String getG_account_number() {
		return g_account_number;
	}
	public void setG_account_number(String g_account_number) {
		this.g_account_number = g_account_number;
	}
	public int getG_voltage_level_type() {
		return g_voltage_level_type;
	}
	public void setG_voltage_level_type(int g_voltage_level_type) {
		this.g_voltage_level_type = g_voltage_level_type;
	}
	public String getG_voltage_level() {
		return g_voltage_level;
	}
	public void setG_voltage_level(String g_voltage_level) {
		this.g_voltage_level = g_voltage_level;
	}
	public String getG_pcc_location() {
		return g_pcc_location;
	}
	public void setG_pcc_location(String g_pcc_location) {
		this.g_pcc_location = g_pcc_location;
	}
	public double getG_maximum_capacity() {
		return g_maximum_capacity;
	}
	public void setG_maximum_capacity(double g_maximum_capacity) {
		this.g_maximum_capacity = g_maximum_capacity;
	}
	public String getG_interconnection_agreement_details() {
		return g_interconnection_agreement_details;
	}
	public void setG_interconnection_agreement_details(String g_interconnection_agreement_details) {
		this.g_interconnection_agreement_details = g_interconnection_agreement_details;
	}
	public String getG_grid_contraints() {
		return g_grid_contraints;
	}
	public void setG_grid_contraints(String g_grid_contraints) {
		this.g_grid_contraints = g_grid_contraints;
	}
	public String getG_existing_ders() {
		return g_existing_ders;
	}
	public void setG_existing_ders(String g_existing_ders) {
		this.g_existing_ders = g_existing_ders;
	}
	public double getG_der_capacities() {
		return g_der_capacities;
	}
	public void setG_der_capacities(double g_der_capacities) {
		this.g_der_capacities = g_der_capacities;
	}
	public double getG_tariff_structure_rate_schedule() {
		return g_tariff_structure_rate_schedule;
	}
	public void setG_tariff_structure_rate_schedule(double g_tariff_structure_rate_schedule) {
		this.g_tariff_structure_rate_schedule = g_tariff_structure_rate_schedule;
	}
	public double getG_net_metering_feed_in_tariff() {
		return g_net_metering_feed_in_tariff;
	}
	public void setG_net_metering_feed_in_tariff(double g_net_metering_feed_in_tariff) {
		this.g_net_metering_feed_in_tariff = g_net_metering_feed_in_tariff;
	}
	public String getG_power_quality_requirements() {
		return g_power_quality_requirements;
	}
	public void setG_power_quality_requirements(String g_power_quality_requirements) {
		this.g_power_quality_requirements = g_power_quality_requirements;
	}
	public double getG_transformer_size() {
		return g_transformer_size;
	}
	public void setG_transformer_size(double g_transformer_size) {
		this.g_transformer_size = g_transformer_size;
	}
	public double getG_transformer_type() {
		return g_transformer_type;
	}
	public void setG_transformer_type(double g_transformer_type) {
		this.g_transformer_type = g_transformer_type;
	}
	public int getG_status() {
		return g_status;
	}
	public void setG_status(int g_status) {
		this.g_status = g_status;
	}
	public double getG_lat() {
		return g_lat;
	}
	public void setG_lat(double g_lat) {
		this.g_lat = g_lat;
	}
	public double getG_lng() {
		return g_lng;
	}
	public void setG_lng(double g_lng) {
		this.g_lng = g_lng;
	}
	public List getFloorList() {
		return floorList;
	}
	public void setFloorList(List floorList) {
		this.floorList = floorList;
	}
	public String getGas_company_name() {
		return gas_company_name;
	}
	public void setGas_company_name(String gas_company_name) {
		this.gas_company_name = gas_company_name;
	}
	public String getGas_account_number() {
		return gas_account_number;
	}
	public void setGas_account_number(String gas_account_number) {
		this.gas_account_number = gas_account_number;
	}
	public double getGas_average_daily_consumption() {
		return gas_average_daily_consumption;
	}
	public void setGas_average_daily_consumption(double gas_average_daily_consumption) {
		this.gas_average_daily_consumption = gas_average_daily_consumption;
	}
	public double getGas_peak_demand() {
		return gas_peak_demand;
	}
	public void setGas_peak_demand(double gas_peak_demand) {
		this.gas_peak_demand = gas_peak_demand;
	}
	public int getGas_service_type() {
		return gas_service_type;
	}
	public void setGas_service_type(int gas_service_type) {
		this.gas_service_type = gas_service_type;
	}
	public double getGas_service_pressure() {
		return gas_service_pressure;
	}
	public void setGas_service_pressure(double gas_service_pressure) {
		this.gas_service_pressure = gas_service_pressure;
	}
	public String getGas_interconnection_agreement() {
		return gas_interconnection_agreement;
	}
	public void setGas_interconnection_agreement(String gas_interconnection_agreement) {
		this.gas_interconnection_agreement = gas_interconnection_agreement;
	}
	public String getGas_complicance_standard() {
		return gas_complicance_standard;
	}
	public void setGas_complicance_standard(String gas_complicance_standard) {
		this.gas_complicance_standard = gas_complicance_standard;
	}
	public String getGas_safety_features() {
		return gas_safety_features;
	}
	public void setGas_safety_features(String gas_safety_features) {
		this.gas_safety_features = gas_safety_features;
	}
	public String getGas_additional_notes() {
		return gas_additional_notes;
	}
	public void setGas_additional_notes(String gas_additional_notes) {
		this.gas_additional_notes = gas_additional_notes;
	}
	public int getGas_billing_cycle() {
		return gas_billing_cycle;
	}
	public void setGas_billing_cycle(int gas_billing_cycle) {
		this.gas_billing_cycle = gas_billing_cycle;
	}
	public String getGas_tariff_structure() {
		return gas_tariff_structure;
	}
	public void setGas_tariff_structure(String gas_tariff_structure) {
		this.gas_tariff_structure = gas_tariff_structure;
	}
	public String getGas_billing_address() {
		return gas_billing_address;
	}
	public void setGas_billing_address(String gas_billing_address) {
		this.gas_billing_address = gas_billing_address;
	}
	public int getTime_queue() {
		return time_queue;
	}
	public void setTime_queue(int time_queue) {
		this.time_queue = time_queue;
	}
	public int getThreshold_item_max() {
		return threshold_item_max;
	}
	public void setThreshold_item_max(int threshold_item_max) {
		this.threshold_item_max = threshold_item_max;
	}
	public String getSite_domain_type() {
		return site_domain_type;
	}
	public void setSite_domain_type(String site_domain_type) {
		this.site_domain_type = site_domain_type;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getTag_device() {
		return tag_device;
	}
	public void setTag_device(String tag_device) {
		this.tag_device = tag_device;
	}
	public String getTag_site() {
		return tag_site;
	}
	public void setTag_site(String tag_site) {
		this.tag_site = tag_site;
	}
	public String getSunrise() {
		return sunrise;
	}
	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}
	public String getSunset() {
		return sunset;
	}
	public void setSunset(String sunset) {
		this.sunset = sunset;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getMqtt_host() {
		return mqtt_host;
	}
	public void setMqtt_host(String mqtt_host) {
		this.mqtt_host = mqtt_host;
	}
	public int getMqtt_port() {
		return mqtt_port;
	}
	public void setMqtt_port(int mqtt_port) {
		this.mqtt_port = mqtt_port;
	}
	public String getMqtt_protocol() {
		return mqtt_protocol;
	}
	public void setMqtt_protocol(String mqtt_protocol) {
		this.mqtt_protocol = mqtt_protocol;
	}
	public String getMqtt_username() {
		return mqtt_username;
	}
	public void setMqtt_username(String mqtt_username) {
		this.mqtt_username = mqtt_username;
	}
	public String getMqtt_password() {
		return mqtt_password;
	}
	public void setMqtt_password(String mqtt_password) {
		this.mqtt_password = mqtt_password;
	}
	public int getTotalWeatherStation() {
		return totalWeatherStation;
	}
	public void setTotalWeatherStation(int totalWeatherStation) {
		this.totalWeatherStation = totalWeatherStation;
	}
	public int getTotalInverter() {
		return totalInverter;
	}
	public void setTotalInverter(int totalInverter) {
		this.totalInverter = totalInverter;
	}
	public int getTotalMeter() {
		return totalMeter;
	}
	public void setTotalMeter(int totalMeter) {
		this.totalMeter = totalMeter;
	}
	public String getListGroup() {
		return listGroup;
	}
	public void setListGroup(String listGroup) {
		this.listGroup = listGroup;
	}
	public List getErrorLevel() {
		return errorLevel;
	}
	public void setErrorLevel(List errorLevel) {
		this.errorLevel = errorLevel;
	}
	public int getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(int enable_alert) {
		this.enable_alert = enable_alert;
	}
	public int getIs_show_each_meter() {
		return is_show_each_meter;
	}
	public void setIs_show_each_meter(int is_show_each_meter) {
		this.is_show_each_meter = is_show_each_meter;
	}
	public String getTable_data_virtual() {
		return table_data_virtual;
	}
	public void setTable_data_virtual(String table_data_virtual) {
		this.table_data_virtual = table_data_virtual;
	}
	public String getTable_data_report() {
		return table_data_report;
	}
	public void setTable_data_report(String table_data_report) {
		this.table_data_report = table_data_report;
	}
	public List getIds_inverter_group() {
		return ids_inverter_group;
	}
	public void setIds_inverter_group(List ids_inverter_group) {
		this.ids_inverter_group = ids_inverter_group;
	}
	public String getBuilt_since_condition() {
		return built_since_condition;
	}
	public void setBuilt_since_condition(String built_since_condition) {
		this.built_since_condition = built_since_condition;
	}
	public String getCommissioning_condition() {
		return commissioning_condition;
	}
	public void setCommissioning_condition(String commissioning_condition) {
		this.commissioning_condition = commissioning_condition;
	}
	public int getVirtual_status() {
		return virtual_status;
	}
	public void setVirtual_status(int virtual_status) {
		this.virtual_status = virtual_status;
	}
	public int getIs_supper_admin() {
		return is_supper_admin;
	}
	public void setIs_supper_admin(int is_supper_admin) {
		this.is_supper_admin = is_supper_admin;
	}
	public Double getDevice_ac_rating() {
		return device_ac_rating;
	}
	public void setDevice_ac_rating(Double device_ac_rating) {
		this.device_ac_rating = device_ac_rating;
	}
	public String getDevice_ac_rating_condition() {
		return device_ac_rating_condition;
	}
	public void setDevice_ac_rating_condition(String device_ac_rating_condition) {
		this.device_ac_rating_condition = device_ac_rating_condition;
	}
	public String getAc_capacity_condition() {
		return ac_capacity_condition;
	}
	public void setAc_capacity_condition(String ac_capacity_condition) {
		this.ac_capacity_condition = ac_capacity_condition;
	}
	public String getDc_capacity_condition() {
		return dc_capacity_condition;
	}
	public void setDc_capacity_condition(String dc_capacity_condition) {
		this.dc_capacity_condition = dc_capacity_condition;
	}
	public String getDiagram() {
		return diagram;
	}
	public void setDiagram(String diagram) {
		this.diagram = diagram;
	}
	public String getFile_diagram_upload() {
		return file_diagram_upload;
	}
	public void setFile_diagram_upload(String file_diagram_upload) {
		this.file_diagram_upload = file_diagram_upload;
	}
	public String getAlert_mail_cc() {
		return alert_mail_cc;
	}
	public void setAlert_mail_cc(String alert_mail_cc) {
		this.alert_mail_cc = alert_mail_cc;
	}
	public String getAlert_mail_bcc() {
		return alert_mail_bcc;
	}
	public void setAlert_mail_bcc(String alert_mail_bcc) {
		this.alert_mail_bcc = alert_mail_bcc;
	}
	public int getIs_hiding() {
		return is_hiding;
	}
	public void setIs_hiding(int is_hiding) {
		this.is_hiding = is_hiding;
	}
	public String getDisplay_timezone() {
		return display_timezone;
	}
	public void setDisplay_timezone(String display_timezone) {
		this.display_timezone = display_timezone;
	}
	public int getDatalogger_type() {
		return datalogger_type;
	}
	public void setDatalogger_type(int datalogger_type) {
		this.datalogger_type = datalogger_type;
	}
	public String getFtp_server() {
		return ftp_server;
	}
	public void setFtp_server(String ftp_server) {
		this.ftp_server = ftp_server;
	}
	public String getFtp_user() {
		return ftp_user;
	}
	public void setFtp_user(String ftp_user) {
		this.ftp_user = ftp_user;
	}
	public String getFtp_pass() {
		return ftp_pass;
	}
	public void setFtp_pass(String ftp_pass) {
		this.ftp_pass = ftp_pass;
	}
	public String getFtp_port() {
		return ftp_port;
	}
	public void setFtp_port(String ftp_port) {
		this.ftp_port = ftp_port;
	}
	public String getFtp_folder() {
		return ftp_folder;
	}
	public void setFtp_folder(String ftp_folder) {
		this.ftp_folder = ftp_folder;
	}
	public int getUnit_wind_speed() {
		return unit_wind_speed;
	}
	public void setUnit_wind_speed(int unit_wind_speed) {
		this.unit_wind_speed = unit_wind_speed;
	}
	public int getUnit_type_temp() {
		return unit_type_temp;
	}
	public void setUnit_type_temp(int unit_type_temp) {
		this.unit_type_temp = unit_type_temp;
	}
	public String getRu_id() {
		return ru_id;
	}
	public void setRu_id(String ru_id) {
		this.ru_id = ru_id;
	}
	public String getGu_id() {
		return gu_id;
	}
	public void setGu_id(String gu_id) {
		this.gu_id = gu_id;
	}
	public List getDevices() {
		return devices;
	}
	public void setDevices(List devices) {
		this.devices = devices;
	}
	public String getRead_data_all() {
		return read_data_all;
	}
	public void setRead_data_all(String read_data_all) {
		this.read_data_all = read_data_all;
	}
	public int getTimezone_datalogger() {
		return timezone_datalogger;
	}
	public void setTimezone_datalogger(int timezone_datalogger) {
		this.timezone_datalogger = timezone_datalogger;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public int getSite_default() {
		return site_default;
	}
	public void setSite_default(int site_default) {
		this.site_default = site_default;
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
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
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
	public Integer getId_site_sub_group() {
		return id_site_sub_group;
	}
	public void setId_site_sub_group(Integer id_site_sub_group) {
		this.id_site_sub_group = id_site_sub_group;
	}
	public int getPv_model() {
		return pv_model;
	}
	public void setPv_model(int pv_model) {
		this.pv_model = pv_model;
	}
	public int getEnable_virtual_device() {
		return enable_virtual_device;
	}
	public void setEnable_virtual_device(int enable_virtual_device) {
		this.enable_virtual_device = enable_virtual_device;
	}
	public String getIds_employee() {
		return ids_employee;
	}
	public void setIds_employee(String ids_employee) {
		this.ids_employee = ids_employee;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getSite_group() {
		return site_group;
	}
	public void setSite_group(String site_group) {
		this.site_group = site_group;
	}
	public String getTime_zone() {
		return time_zone;
	}
	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}
	public String getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(String last_modified) {
		this.last_modified = last_modified;
	}
	public List getHidden_data_list() {
		return hidden_data_list;
	}
	public void setHidden_data_list(List hidden_data_list) {
		this.hidden_data_list = hidden_data_list;
	}
	public String getExpiration() {
		return expiration;
	}
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
	public boolean isFilterEnabled() {
		return filterEnabled;
	}
	public void setFilterEnabled(boolean filterEnabled) {
		this.filterEnabled = filterEnabled;
	}
	public Integer getReporting_region() {
		return reporting_region;
	}
	public void setReporting_region(Integer reporting_region) {
		this.reporting_region = reporting_region;
	}
	public int getIs_solar_site() {
		return is_solar_site;
	}
	public void setIs_solar_site(int is_solar_site) {
		this.is_solar_site = is_solar_site;
	}
	public boolean isHave_poa() {
		return have_poa;
	}
	public void setHave_poa(boolean have_poa) {
		this.have_poa = have_poa;
	}
	
	
	
	
	
	
}
