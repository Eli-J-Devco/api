/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;
import java.util.Date;
import java.util.List;


public class SitesDevicesEntity extends SortEntity {
	private int id;
	private int id_customer;
	private int id_country;
	private int id_company;
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
	private Integer updated_by;
	private String built_since;
	private int limit;
	private int offset;
	private int totalRecord;
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
	private String hash_site_id;
	private int data_send_time;
	private String site_name;
	private String hash_id_site;
	private String last_updated;
	private int totalError;
	private List dataParameter;
	private String devicename;
	private String group_name;
	private int id_device_group;
	private String datatablename;
	private String times_ago;
	private String times_ago_unit;
	private int id_device_type;
	private int is_paramerter_expand;
	private int is_calculation_expand;
	private int is_checked;
	private String timezone_value;
	private String timezone_offset;
	private String datalogger_ip;
	private int cf_start_time;
	private int cf_end_time;
	private int cf_alert_threshold;
	private String cf_email_subscribers;
	private List deviceDisableAlerts;
	private String keyword;
	private int kiosk_view;	
	private String read_data_all;
	private String view_tablename;
	private int unit_type_temp;
	private int unit_wind_speed;
	private int display_time_format;
	private int display_date_format;
	private String date_format;
	private String java_date_format;
	private int checkall;
	private int pv_model;
	private Double pv_module_temperature_coeff;
	private Double global_solar_irradiance_at_stc;
	private Double stc_temperature;
	private Double inverter_efficiency;
	private Double t_avg;
	private Double system_loss;
	private Double clip;
	private	Double bifaciality_factor;
	private double annual_pv_module_degradation;
	private double cable_losses;
	private double transformer_losses;
	private double soiling;
	private double other_losses;
	private int min_irradiance_limit;
	private int number_devices;
	
	private String ssh_host;
	private String ssh_user;
	private String ssh_pass;
	private String ssh_port;
	private String job_tablename;
	private int enable_virtual_device; 
	private String ids_device_poa;
	private String ids_device_rpoa;
	private String ids_device_panel_temp;
	private String ids_device_ambient_temp;
	
	private int modules_per_string;
	private int number_of_strings;
	private double kwp_dc_for_each_solar_module;
	private double data_interval_factor;
	
	private String alert_mail_cc;
	private String alert_mail_bcc;
	private int is_hidden;
	private int is_supper_admin;
	private boolean is_hiding;
	private int hidden;
	private String table_data_report;
	private String table_data_virtual;
	private String expiration;
	private int is_show_each_meter;
	private List errorLevel;
	
	private String serial_number;
	private String mqtt_host;
	private int mqtt_port;
	private String mqtt_protocol;
	private String mqtt_username;
	private String mqtt_password;
	
	private String verifyCode;
	private String sunrise;
	private String sunset;
	private String start_date;
	private String end_date;
	private String id_filter;
	private String id_time_filter;
	private double cost;
	private String cost_unit;
	private int site_domain_type;
	private int time_queue;
	private int threshold_item_max;
	private int disable_alert;
	private String advance_tech_host;
	private String advance_tech_pass;
	private int advance_tech_status;
	private String advance_tech_field_restart;
	private String advance_tech_field_stop;
	private String advance_tech_field_start;
	private String advance_tech_control_tag;
	private int advance_tech_control_enable = 0;
	private String domain;
	private boolean isUserNW;
	private double total_area;
	private double total_unit;
	private int meter_type;
	private double weather_indoor_temp;
	private String weather_indoor_temp_unit;
	private double weather_outdoor_temp;
	private String weather_outdoor_temp_unit;
	private String weather_time;
	private double weather_humidity;
	private String weather_humidity_unit;
	private String weather_description;
	private String weather_icon;
	private String weather_time_string;
	
	private double weather_snow;
	private String weather_snow_unit;
	private double weather_rain;
	private String weather_rain_unit;
	private double weather_wind;
	private String weather_wind_unit;
	private double temperature_2m_max;
	private double temperature_2m_min;
	
	private String domain_role;
	
	private String hvac_gateway;
	private String hvac_layout;
	private int id_hvac_layout;
	private String device_group_title_trans;
	private String device_type_title_trans;
	private String advance_tech_field_device_status;
	private int id_error_level;
	private int id_error_type;
	private int id_sort_by;
	private int id_site_group;
	private int is_subtract_pv;
    private String bems_commissioning;
	
	
	
	public int getIs_subtract_pv() {
		return is_subtract_pv;
	}
	public void setIs_subtract_pv(int is_subtract_pv) {
		this.is_subtract_pv = is_subtract_pv;
	}
	public double getTemperature_2m_max() {
		return temperature_2m_max;
	}
	public void setTemperature_2m_max(double temperature_2m_max) {
		this.temperature_2m_max = temperature_2m_max;
	}
	public double getTemperature_2m_min() {
		return temperature_2m_min;
	}
	public void setTemperature_2m_min(double temperature_2m_min) {
		this.temperature_2m_min = temperature_2m_min;
	}
	public String getId_time_filter() {
		return id_time_filter;
	}
	public void setId_time_filter(String id_time_filter) {
		this.id_time_filter = id_time_filter;
	}
	public int getId_site_group() {
		return id_site_group;
	}
	public void setId_site_group(int id_site_group) {
		this.id_site_group = id_site_group;
	}
	public int getId_sort_by() {
		return id_sort_by;
	}
	public void setId_sort_by(int id_sort_by) {
		this.id_sort_by = id_sort_by;
	}
	public int getId_error_level() {
		return id_error_level;
	}
	public void setId_error_level(int id_error_level) {
		this.id_error_level = id_error_level;
	}
	public int getId_error_type() {
		return id_error_type;
	}
	public void setId_error_type(int id_error_type) {
		this.id_error_type = id_error_type;
	}
	public String getAdvance_tech_field_device_status() {
		return advance_tech_field_device_status;
	}
	public void setAdvance_tech_field_device_status(String advance_tech_field_device_status) {
		this.advance_tech_field_device_status = advance_tech_field_device_status;
	}
	public String getAdvance_tech_control_tag() {
		return advance_tech_control_tag;
	}
	public void setAdvance_tech_control_tag(String advance_tech_control_tag) {
		this.advance_tech_control_tag = advance_tech_control_tag;
	}
	public String getDevice_group_title_trans() {
		return device_group_title_trans;
	}
	public void setDevice_group_title_trans(String device_group_title_trans) {
		this.device_group_title_trans = device_group_title_trans;
	}
	public String getDevice_type_title_trans() {
		return device_type_title_trans;
	}
	public void setDevice_type_title_trans(String device_type_title_trans) {
		this.device_type_title_trans = device_type_title_trans;
	}
	public double getWeather_snow() {
		return weather_snow;
	}
	public void setWeather_snow(double weather_snow) {
		this.weather_snow = weather_snow;
	}
	public String getWeather_snow_unit() {
		return weather_snow_unit;
	}
	public void setWeather_snow_unit(String weather_snow_unit) {
		this.weather_snow_unit = weather_snow_unit;
	}
	public double getWeather_rain() {
		return weather_rain;
	}
	public void setWeather_rain(double weather_rain) {
		this.weather_rain = weather_rain;
	}
	public String getWeather_rain_unit() {
		return weather_rain_unit;
	}
	public void setWeather_rain_unit(String weather_rain_unit) {
		this.weather_rain_unit = weather_rain_unit;
	}
	public double getWeather_wind() {
		return weather_wind;
	}
	public void setWeather_wind(double weather_wind) {
		this.weather_wind = weather_wind;
	}
	public String getWeather_wind_unit() {
		return weather_wind_unit;
	}
	public void setWeather_wind_unit(String weather_wind_unit) {
		this.weather_wind_unit = weather_wind_unit;
	}
	public String getWeather_indoor_temp_unit() {
		return weather_indoor_temp_unit;
	}
	public void setWeather_indoor_temp_unit(String weather_indoor_temp_unit) {
		this.weather_indoor_temp_unit = weather_indoor_temp_unit;
	}
	public String getWeather_outdoor_temp_unit() {
		return weather_outdoor_temp_unit;
	}
	public void setWeather_outdoor_temp_unit(String weather_outdoor_temp_unit) {
		this.weather_outdoor_temp_unit = weather_outdoor_temp_unit;
	}
	public String getWeather_humidity_unit() {
		return weather_humidity_unit;
	}
	public void setWeather_humidity_unit(String weather_humidity_unit) {
		this.weather_humidity_unit = weather_humidity_unit;
	}
	public double getWeather_outdoor_temp() {
		return weather_outdoor_temp;
	}
	public void setWeather_outdoor_temp(double weather_outdoor_temp) {
		this.weather_outdoor_temp = weather_outdoor_temp;
	}
	public String getDomain_role() {
		return domain_role;
	}
	public void setDomain_role(String domain_role) {
		this.domain_role = domain_role;
	}
	
	
	
	public String getWeather_description() {
		return weather_description;
	}
	public void setWeather_description(String weather_description) {
		this.weather_description = weather_description;
	}
	public String getWeather_icon() {
		return weather_icon;
	}
	public void setWeather_icon(String weather_icon) {
		this.weather_icon = weather_icon;
	}
	public String getWeather_time_string() {
		return weather_time_string;
	}
	public void setWeather_time_string(String weather_time_string) {
		this.weather_time_string = weather_time_string;
	}
	public double getWeather_indoor_temp() {
		return weather_indoor_temp;
	}
	public void setWeather_indoor_temp(double weather_indoor_temp) {
		this.weather_indoor_temp = weather_indoor_temp;
	}

	public String getWeather_time() {
		return weather_time;
	}
	public void setWeather_time(String weather_time) {
		this.weather_time = weather_time;
	}
	public double getWeather_humidity() {
		return weather_humidity;
	}
	public void setWeather_humidity(double weather_humidity) {
		this.weather_humidity = weather_humidity;
	}
	public int getMeter_type() {
		return meter_type;
	}
	public void setMeter_type(int meter_type) {
		this.meter_type = meter_type;
	}
	public void setUserNW(boolean isUserNW) {
		this.isUserNW = isUserNW;
	}
	public double getTotal_area() {
		return total_area;
	}
	public void setTotal_area(double total_area) {
		this.total_area = total_area;
	}
	public double getTotal_unit() {
		return total_unit;
	}
	public void setTotal_unit(double total_unit) {
		this.total_unit = total_unit;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getAdvance_tech_control_enable() {
		return advance_tech_control_enable;
	}
	public void setAdvance_tech_control_enable(int advance_tech_control_enable) {
		this.advance_tech_control_enable = advance_tech_control_enable;
	}
	public String getAdvance_tech_field_restart() {
		return advance_tech_field_restart;
	}
	public void setAdvance_tech_field_restart(String advance_tech_field_restart) {
		this.advance_tech_field_restart = advance_tech_field_restart;
	}
	public String getAdvance_tech_field_stop() {
		return advance_tech_field_stop;
	}
	public void setAdvance_tech_field_stop(String advance_tech_field_stop) {
		this.advance_tech_field_stop = advance_tech_field_stop;
	}
	public String getAdvance_tech_field_start() {
		return advance_tech_field_start;
	}
	public void setAdvance_tech_field_start(String advance_tech_field_start) {
		this.advance_tech_field_start = advance_tech_field_start;
	}
	public String getAdvance_tech_host() {
		return advance_tech_host;
	}
	public void setAdvance_tech_host(String advance_tech_host) {
		this.advance_tech_host = advance_tech_host;
	}
	public String getAdvance_tech_pass() {
		return advance_tech_pass;
	}
	public void setAdvance_tech_pass(String advance_tech_pass) {
		this.advance_tech_pass = advance_tech_pass;
	}
	public int getAdvance_tech_status() {
		return advance_tech_status;
	}
	public void setAdvance_tech_status(int advance_tech_status) {
		this.advance_tech_status = advance_tech_status;
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
	public int getSite_domain_type() {
		return site_domain_type;
	}
	public void setSite_domain_type(int site_domain_type) {
		this.site_domain_type = site_domain_type;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getCost_unit() {
		return cost_unit;
	}
	public void setCost_unit(String cost_unit) {
		this.cost_unit = cost_unit;
	}
	public int getId_company() {
		return id_company;
	}
	public void setId_company(int id_company) {
		this.id_company = id_company;
	}
	public String getId_filter() {
		return id_filter;
	}
	public void setId_filter(String id_filter) {
		this.id_filter = id_filter;
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
	public int getNumber_devices() {
		return number_devices;
	}
	public void setNumber_devices(int number_devices) {
		this.number_devices = number_devices;
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
	public List getErrorLevel() {
		return errorLevel;
	}
	public void setErrorLevel(List errorLevel) {
		this.errorLevel = errorLevel;
	}
	public int getIs_show_each_meter() {
		return is_show_each_meter;
	}
	public void setIs_show_each_meter(int is_show_each_meter) {
		this.is_show_each_meter = is_show_each_meter;
	}
	public int getIs_supper_admin() {
		return is_supper_admin;
	}
	public void setIs_supper_admin(int is_supper_admin) {
		this.is_supper_admin = is_supper_admin;
	}
	public double getAnnual_pv_module_degradation() {
		return annual_pv_module_degradation;
	}
	public void setAnnual_pv_module_degradation(double annual_pv_module_degradation) {
		this.annual_pv_module_degradation = annual_pv_module_degradation;
	}
	public double getCable_losses() {
		return cable_losses;
	}
	public void setCable_losses(double cable_losses) {
		this.cable_losses = cable_losses;
	}
	public double getTransformer_losses() {
		return transformer_losses;
	}
	public void setTransformer_losses(double transformer_losses) {
		this.transformer_losses = transformer_losses;
	}
	public double getSoiling() {
		return soiling;
	}
	public void setSoiling(double soiling) {
		this.soiling = soiling;
	}
	public double getOther_losses() {
		return other_losses;
	}
	public void setOther_losses(double other_losses) {
		this.other_losses = other_losses;
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
	public int getModules_per_string() {
		return modules_per_string;
	}
	public void setModules_per_string(int modules_per_string) {
		this.modules_per_string = modules_per_string;
	}
	public int getNumber_of_strings() {
		return number_of_strings;
	}
	public void setNumber_of_strings(int number_of_strings) {
		this.number_of_strings = number_of_strings;
	}
	public double getKwp_dc_for_each_solar_module() {
		return kwp_dc_for_each_solar_module;
	}
	public void setKwp_dc_for_each_solar_module(double kwp_dc_for_each_solar_module) {
		this.kwp_dc_for_each_solar_module = kwp_dc_for_each_solar_module;
	}
	public double getData_interval_factor() {
		return data_interval_factor;
	}
	public void setData_interval_factor(double data_interval_factor) {
		this.data_interval_factor = data_interval_factor;
	}
	public Double getClip() {
		return clip;
	}
	public void setClip(Double clip) {
		this.clip = clip;
	}
	public Double getBifaciality_factor() {
		return bifaciality_factor;
	}
	public void setBifaciality_factor(Double bifaciality_factor) {
		this.bifaciality_factor = bifaciality_factor;
	}
	public int getMin_irradiance_limit() {
		return min_irradiance_limit;
	}
	public void setMin_irradiance_limit(int min_irradiance_limit) {
		this.min_irradiance_limit = min_irradiance_limit;
	}
	public int getEnable_virtual_device() {
		return enable_virtual_device;
	}
	public void setEnable_virtual_device(int enable_virtual_device) {
		this.enable_virtual_device = enable_virtual_device;
	}
	public Double getPv_module_temperature_coeff() {
		return pv_module_temperature_coeff;
	}
	public void setPv_module_temperature_coeff(Double pv_module_temperature_coeff) {
		this.pv_module_temperature_coeff = pv_module_temperature_coeff;
	}
	public Double getGlobal_solar_irradiance_at_stc() {
		return global_solar_irradiance_at_stc;
	}
	public void setGlobal_solar_irradiance_at_stc(Double global_solar_irradiance_at_stc) {
		this.global_solar_irradiance_at_stc = global_solar_irradiance_at_stc;
	}
	public Double getStc_temperature() {
		return stc_temperature;
	}
	public void setStc_temperature(Double stc_temperature) {
		this.stc_temperature = stc_temperature;
	}
	public Double getInverter_efficiency() {
		return inverter_efficiency;
	}
	public void setInverter_efficiency(Double inverter_efficiency) {
		this.inverter_efficiency = inverter_efficiency;
	}
	public Double getT_avg() {
		return t_avg;
	}
	public void setT_avg(Double t_avg) {
		this.t_avg = t_avg;
	}
	public Double getSystem_loss() {
		return system_loss;
	}
	public void setSystem_loss(Double system_loss) {
		this.system_loss = system_loss;
	}
	public String getJob_tablename() {
		return job_tablename;
	}
	public void setJob_tablename(String job_tablename) {
		this.job_tablename = job_tablename;
	}
	public String getSsh_host() {
		return ssh_host;
	}
	public void setSsh_host(String ssh_host) {
		this.ssh_host = ssh_host;
	}
	public String getSsh_user() {
		return ssh_user;
	}
	public void setSsh_user(String ssh_user) {
		this.ssh_user = ssh_user;
	}
	public String getSsh_pass() {
		return ssh_pass;
	}
	public void setSsh_pass(String ssh_pass) {
		this.ssh_pass = ssh_pass;
	}
	public String getSsh_port() {
		return ssh_port;
	}
	public void setSsh_port(String ssh_port) {
		this.ssh_port = ssh_port;
	}
	public int getCheckall() {
		return checkall;
	}
	public void setCheckall(int checkall) {
		this.checkall = checkall;
	}
	public String getDate_format() {
		return date_format;
	}
	public void setDate_format(String date_format) {
		this.date_format = date_format;
	}
	public int getDisplay_time_format() {
		return display_time_format;
	}
	public void setDisplay_time_format(int display_time_format) {
		this.display_time_format = display_time_format;
	}
	public int getDisplay_date_format() {
		return display_date_format;
	}
	public void setDisplay_date_format(int display_date_format) {
		this.display_date_format = display_date_format;
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
	public String getView_tablename() {
		return view_tablename;
	}
	public void setView_tablename(String view_tablename) {
		this.view_tablename = view_tablename;
	}
	public String getRead_data_all() {
		return read_data_all;
	}
	public void setRead_data_all(String read_data_all) {
		this.read_data_all = read_data_all;
	}
	public int getKiosk_view() {
		return kiosk_view;
	}
	public void setKiosk_view(int kiosk_view) {
		this.kiosk_view = kiosk_view;
	}
	public String getHash_site_id() {
		return hash_site_id;
	}
	public void setHash_site_id(String hash_site_id) {
		this.hash_site_id = hash_site_id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public List getDeviceDisableAlerts() {
		return deviceDisableAlerts;
	}
	public void setDeviceDisableAlerts(List deviceDisableAlerts) {
		this.deviceDisableAlerts = deviceDisableAlerts;
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
	public int getId_site_type() {
		return id_site_type;
	}
	public void setId_site_type(int id_site_type) {
		this.id_site_type = id_site_type;
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
	public Integer getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(Integer updated_by) {
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
	public String getOffset_timezone() {
		return offset_timezone;
	}
	public void setOffset_timezone(String offset_timezone) {
		this.offset_timezone = offset_timezone;
	}
	public List getList_device() {
		return list_device;
	}
	public void setList_device(List list_device) {
		this.list_device = list_device;
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
	public String getCurrent_time() {
		return current_time;
	}
	public void setCurrent_time(String current_time) {
		this.current_time = current_time;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public int getTotal_error() {
		return total_error;
	}
	public void setTotal_error(int total_error) {
		this.total_error = total_error;
	}
	public String getHash_id() {
		return hash_id;
	}
	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}
	public int getData_send_time() {
		return data_send_time;
	}
	public void setData_send_time(int data_send_time) {
		this.data_send_time = data_send_time;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getHash_id_site() {
		return hash_id_site;
	}
	public void setHash_id_site(String hash_id_site) {
		this.hash_id_site = hash_id_site;
	}
	public String getLast_updated() {
		return last_updated;
	}
	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}
	public int getTotalError() {
		return totalError;
	}
	public void setTotalError(int totalError) {
		this.totalError = totalError;
	}
	public List getDataParameter() {
		return dataParameter;
	}
	public void setDataParameter(List dataParameter) {
		this.dataParameter = dataParameter;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public int getId_device_group() {
		return id_device_group;
	}
	public void setId_device_group(int id_device_group) {
		this.id_device_group = id_device_group;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getTimes_ago() {
		return times_ago;
	}
	public void setTimes_ago(String times_ago) {
		this.times_ago = times_ago;
	}
	public String getTimes_ago_unit() {
		return times_ago_unit;
	}
	public void setTimes_ago_unit(String times_ago_unit) {
		this.times_ago_unit = times_ago_unit;
	}
	public int getId_device_type() {
		return id_device_type;
	}
	public void setId_device_type(int id_device_type) {
		this.id_device_type = id_device_type;
	}
	public int getIs_paramerter_expand() {
		return is_paramerter_expand;
	}
	public void setIs_paramerter_expand(int is_paramerter_expand) {
		this.is_paramerter_expand = is_paramerter_expand;
	}
	public int getIs_checked() {
		return is_checked;
	}
	public void setIs_checked(int is_checked) {
		this.is_checked = is_checked;
	}
	public String getTimezone_value() {
		return timezone_value;
	}
	public void setTimezone_value(String timezone_value) {
		this.timezone_value = timezone_value;
	}
	public String getTimezone_offset() {
		return timezone_offset;
	}
	public void setTimezone_offset(String timezone_offset) {
		this.timezone_offset = timezone_offset;
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
	public int getIs_calculation_expand() {
		return is_calculation_expand;
	}
	public void setIs_calculation_expand(int is_calculation_expand) {
		this.is_calculation_expand = is_calculation_expand;
	}
	public int getPv_model() {
		return pv_model;
	}
	public void setPv_model(int pv_model) {
		this.pv_model = pv_model;
	}
	public String getIds_device_poa() {
		return ids_device_poa;
	}
	public void setIds_device_poa(String ids_device_poa) {
		this.ids_device_poa = ids_device_poa;
	}
	public String getIds_device_rpoa() {
		return ids_device_rpoa;
	}
	public void setIds_device_rpoa(String ids_device_rpoa) {
		this.ids_device_rpoa = ids_device_rpoa;
	}
	public String getIds_device_panel_temp() {
		return ids_device_panel_temp;
	}
	public void setIds_device_panel_temp(String ids_device_panel_temp) {
		this.ids_device_panel_temp = ids_device_panel_temp;
	}
	public String getIds_device_ambient_temp() {
		return ids_device_ambient_temp;
	}
	public void setIds_device_ambient_temp(String ids_device_ambient_temp) {
		this.ids_device_ambient_temp = ids_device_ambient_temp;
	}
	public int getIs_hidden() {
		return is_hidden;
	}
	public void setIs_hidden(int is_hidden) {
		this.is_hidden = is_hidden;
	}
	public boolean isIs_hiding() {
		return is_hiding;
	}
	public void setIs_hiding(boolean is_hiding) {
		this.is_hiding = is_hiding;
	}
	public int getHidden() {
		return hidden;
	}
	public void setHidden(int hidden) {
		this.hidden = hidden;
	}
	public String getTable_data_report() {
		return table_data_report;
	}
	public void setTable_data_report(String table_data_report) {
		this.table_data_report = table_data_report;
	}
	public String getTable_data_virtual() {
		return table_data_virtual;
	}
	public void setTable_data_virtual(String table_data_virtual) {
		this.table_data_virtual = table_data_virtual;
	}
	public String getExpiration() {
		return expiration;
	}
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
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
	public int getDisable_alert() {
		return disable_alert;
	}
	public void setDisable_alert(int disable_alert) {
		this.disable_alert = disable_alert;
	}
	public String getJava_date_format() {
		return java_date_format;
	}
	public void setJava_date_format(String java_date_format) {
		this.java_date_format = java_date_format;
	}
	public boolean isUserNW() {
		return isUserNW;
	}
	public void setIsUserNW(boolean isUserNW) {
		this.isUserNW = isUserNW;
	}
	public String getHvac_gateway() {
		return hvac_gateway;
	}
	public void setHvac_gateway(String hvac_gateway) {
		this.hvac_gateway = hvac_gateway;
	}
	public String getHvac_layout() {
		return hvac_layout;
	}
	public void setHvac_layout(String hvac_layout) {
		this.hvac_layout = hvac_layout;
	}
	public int getId_hvac_layout() {
		return id_hvac_layout;
	}
	public void setId_hvac_layout(int id_hvac_layout) {
		this.id_hvac_layout = id_hvac_layout;
	}

    public String getBems_commissioning() {
        return bems_commissioning;
    }

    public void setBems_commissioning(String bems_commissioning) {
        this.bems_commissioning = bems_commissioning;
    }
}
