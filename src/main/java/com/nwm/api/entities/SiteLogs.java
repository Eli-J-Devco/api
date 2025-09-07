/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SiteLogs extends LogBase {
	private String employee_mapping;
	private Integer id_country;
	private Integer id_time_zone;
	private Integer id_company;
	private String name;
	private String street;
	private Double lat;
	private Double lng;
	private LocalDate built_since;
	private String old_data;
	private String number;
	private String postal_code;
	private String city;
	private String state;
	private LocalDate commissioning;
	private String emergency_contact;
	private Double ac_capacity;
	private Double dc_capacity;
	private String gallery;
	private Boolean status;
	private Boolean is_delete;
	private Integer site_default;
	private Integer data_send_time;
	private Integer start_date_time;
	private Integer end_date_time;
	private Integer config_sunset_sunrise;
	private String note;
	private Boolean is_rec_report;
	private String datalogger_ip;
	private Integer cf_start_time;
	private Integer cf_end_time;
	private Integer cf_alert_threshold;
	private String cf_email_subscribers;
	private String rec_id;
	private String about;
	private Boolean kiosk_view;
	private String site_logo;
	private Integer id_site_group;
	private Integer timezone_datalogger;
	private String gu_id;
	private Integer unit_type_temp;
	private Integer unit_wind_speed;
	private Integer display_timezone;
	private Integer display_date_format;
	private Integer display_time_format;
	private Integer pv_model;
	private Double t_avg_nrel;
	private String ftp_server;
	private String ftp_user;
	private String ftp_pass;
	private String ftp_port;
	private String ftp_folder;
	private Integer datalogger_type;
	private Double pv_module_temperature_coeff;
	private Double global_solar_irradiance_at_stc;
	private Double stc_temperature;
	private Double inverter_efficiency;
	private Double t_avg;
	private Double system_loss;
	private Integer min_irradiance_limit;
	private Double clip;
	private Double bifaciality_factor;
	private String ids_device_poa;
	private String ids_device_rpoa;
	private String ids_device_panel_temp;
	private String ids_device_ambient_temp;
	private Integer modules_per_string;
	private Integer number_of_strings;
	private Double kwp_dc_for_each_solar_module;
	private Double data_interval_factor;
	private Integer id_site_sub_group;
	private String alert_mail_cc;
	private String alert_mail_bcc;
	private String diagram;
	private Double annual_pv_module_degradation;
	private Double cable_losses;
	private Double transformer_losses;
	private Double soiling;
	private Double other_losses;
	private LocalDate expiration;
	private Boolean is_show_each_meter;
	private Boolean enable_alert;
	private String communication;
	private String serial_number;
	private String mqtt_host;
	private Integer mqtt_port;
	private String mqtt_protocol;
	private String mqtt_username;
	private String mqtt_password;
	private String third_party_key;
	private Integer reporting_region;
	private Double cost;
	private String cost_unit;
	private Boolean is_solar_site;
	private Integer site_domain_type;
	private String b_year_built;
	private Double b_total_area;
	private Integer b_type;
	private Integer b_status;
	private String g_company_name;
	private String g_account_number;
	private Integer g_voltage_level_type;
	private String g_voltage_level;
	private String g_pcc_location;
	private Double g_maximum_capacity;
	private String g_interconnection_agreement_details;
	private String g_grid_contraints;
	private String g_existing_ders;
	private Double g_der_capacities;
	private Double g_tariff_structure_rate_schedule;
	private Double g_net_metering_feed_in_tariff;
	private String g_power_quality_requirements;
	private Double g_transformer_size;
	private Double g_transformer_type;
	private Integer g_status;
	private Double g_lat;
	private Double g_lng;
	private String gas_company_name;
	private String gas_account_number;
	private Double gas_average_daily_consumption;
	private Double gas_peak_demand;
	private Integer gas_service_type;
	private Double gas_service_pressure;
	private String gas_interconnection_agreement;
	private String gas_complicance_standard;
	private String gas_safety_features;
	private String gas_additional_notes;
	private Integer gas_billing_cycle;
	private String gas_tariff_structure;
	private String gas_billing_address;
	private Integer site_type;
	private String dashboard_overview_note;
	private Integer time_queue;
	private Integer threshold_item_max;
	private String advance_tech_host;
	private String advance_tech_pass;
	private Integer advance_tech_status;
	private Integer advance_tech_control_enable;
	private Double total_area;
	private Double total_unit;
	private Boolean inv_level;
	
	public String getEmployee_mapping() {
		return employee_mapping;
	}
	public void setEmployee_mapping(String employee_mapping) {
		this.employee_mapping = employee_mapping;
	}
	public Integer getId_country() {
		return id_country;
	}
	public void setId_country(Integer id_country) {
		this.id_country = id_country;
	}
	public Integer getId_time_zone() {
		return id_time_zone;
	}
	public void setId_time_zone(Integer id_time_zone) {
		this.id_time_zone = id_time_zone;
	}
	public Integer getId_company() {
		return id_company;
	}
	public void setId_company(Integer id_company) {
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
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public LocalDate getBuilt_since() {
		return built_since;
	}
	public void setBuilt_since(LocalDateTime built_since) {
		this.built_since = built_since.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDate();
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
	public LocalDate getCommissioning() {
		return commissioning;
	}
	public void setCommissioning(LocalDateTime commissioning) {
		this.commissioning = commissioning.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDate();
	}
	public String getEmergency_contact() {
		return emergency_contact;
	}
	public void setEmergency_contact(String emergency_contact) {
		this.emergency_contact = emergency_contact;
	}
	public Double getAc_capacity() {
		return ac_capacity;
	}
	public void setAc_capacity(Double ac_capacity) {
		this.ac_capacity = ac_capacity;
	}
	public Double getDc_capacity() {
		return dc_capacity;
	}
	public void setDc_capacity(Double dc_capacity) {
		this.dc_capacity = dc_capacity;
	}
	public String getGallery() {
		return gallery;
	}
	public void setGallery(String gallery) {
		this.gallery = gallery;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Boolean getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(Boolean is_delete) {
		this.is_delete = is_delete;
	}
	public Integer getSite_default() {
		return site_default;
	}
	public void setSite_default(Integer site_default) {
		this.site_default = site_default;
	}
	public Integer getData_send_time() {
		return data_send_time;
	}
	public void setData_send_time(Integer data_send_time) {
		this.data_send_time = data_send_time;
	}
	public Integer getStart_date_time() {
		return start_date_time;
	}
	public void setStart_date_time(Integer start_date_time) {
		this.start_date_time = start_date_time;
	}
	public Integer getEnd_date_time() {
		return end_date_time;
	}
	public void setEnd_date_time(Integer end_date_time) {
		this.end_date_time = end_date_time;
	}
	public Integer getConfig_sunset_sunrise() {
		return config_sunset_sunrise;
	}
	public void setConfig_sunset_sunrise(Integer config_sunset_sunrise) {
		this.config_sunset_sunrise = config_sunset_sunrise;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Boolean getIs_rec_report() {
		return is_rec_report;
	}
	public void setIs_rec_report(Boolean is_rec_report) {
		this.is_rec_report = is_rec_report;
	}
	public String getDatalogger_ip() {
		return datalogger_ip;
	}
	public void setDatalogger_ip(String datalogger_ip) {
		this.datalogger_ip = datalogger_ip;
	}
	public Integer getCf_start_time() {
		return cf_start_time;
	}
	public void setCf_start_time(Integer cf_start_time) {
		this.cf_start_time = cf_start_time;
	}
	public Integer getCf_end_time() {
		return cf_end_time;
	}
	public void setCf_end_time(Integer cf_end_time) {
		this.cf_end_time = cf_end_time;
	}
	public Integer getCf_alert_threshold() {
		return cf_alert_threshold;
	}
	public void setCf_alert_threshold(Integer cf_alert_threshold) {
		this.cf_alert_threshold = cf_alert_threshold;
	}
	public String getCf_email_subscribers() {
		return cf_email_subscribers;
	}
	public void setCf_email_subscribers(String cf_email_subscribers) {
		this.cf_email_subscribers = cf_email_subscribers;
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
	public Boolean getKiosk_view() {
		return kiosk_view;
	}
	public void setKiosk_view(Boolean kiosk_view) {
		this.kiosk_view = kiosk_view;
	}
	public String getSite_logo() {
		return site_logo;
	}
	public void setSite_logo(String site_logo) {
		this.site_logo = site_logo;
	}
	public Integer getId_site_group() {
		return id_site_group;
	}
	public void setId_site_group(Integer id_site_group) {
		this.id_site_group = id_site_group;
	}
	public Integer getTimezone_datalogger() {
		return timezone_datalogger;
	}
	public void setTimezone_datalogger(Integer timezone_datalogger) {
		this.timezone_datalogger = timezone_datalogger;
	}
	public String getGu_id() {
		return gu_id;
	}
	public void setGu_id(String gu_id) {
		this.gu_id = gu_id;
	}
	public Integer getUnit_type_temp() {
		return unit_type_temp;
	}
	public void setUnit_type_temp(Integer unit_type_temp) {
		this.unit_type_temp = unit_type_temp;
	}
	public Integer getUnit_wind_speed() {
		return unit_wind_speed;
	}
	public void setUnit_wind_speed(Integer unit_wind_speed) {
		this.unit_wind_speed = unit_wind_speed;
	}
	public Integer getDisplay_timezone() {
		return display_timezone;
	}
	public void setDisplay_timezone(Integer display_timezone) {
		this.display_timezone = display_timezone;
	}
	public Integer getDisplay_date_format() {
		return display_date_format;
	}
	public void setDisplay_date_format(Integer display_date_format) {
		this.display_date_format = display_date_format;
	}
	public Integer getDisplay_time_format() {
		return display_time_format;
	}
	public void setDisplay_time_format(Integer display_time_format) {
		this.display_time_format = display_time_format;
	}
	public Integer getPv_model() {
		return pv_model;
	}
	public void setPv_model(Integer pv_model) {
		this.pv_model = pv_model;
	}
	public Double getT_avg_nrel() {
		return t_avg_nrel;
	}
	public void setT_avg_nrel(Double t_avg_nrel) {
		this.t_avg_nrel = t_avg_nrel;
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
	public Integer getDatalogger_type() {
		return datalogger_type;
	}
	public void setDatalogger_type(Integer datalogger_type) {
		this.datalogger_type = datalogger_type;
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
	public Integer getMin_irradiance_limit() {
		return min_irradiance_limit;
	}
	public void setMin_irradiance_limit(Integer min_irradiance_limit) {
		this.min_irradiance_limit = min_irradiance_limit;
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
	public Integer getModules_per_string() {
		return modules_per_string;
	}
	public void setModules_per_string(Integer modules_per_string) {
		this.modules_per_string = modules_per_string;
	}
	public Integer getNumber_of_strings() {
		return number_of_strings;
	}
	public void setNumber_of_strings(Integer number_of_strings) {
		this.number_of_strings = number_of_strings;
	}
	public Double getKwp_dc_for_each_solar_module() {
		return kwp_dc_for_each_solar_module;
	}
	public void setKwp_dc_for_each_solar_module(Double kwp_dc_for_each_solar_module) {
		this.kwp_dc_for_each_solar_module = kwp_dc_for_each_solar_module;
	}
	public Double getData_interval_factor() {
		return data_interval_factor;
	}
	public void setData_interval_factor(Double data_interval_factor) {
		this.data_interval_factor = data_interval_factor;
	}
	public Integer getId_site_sub_group() {
		return id_site_sub_group;
	}
	public void setId_site_sub_group(Integer id_site_sub_group) {
		this.id_site_sub_group = id_site_sub_group;
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
	public String getDiagram() {
		return diagram;
	}
	public void setDiagram(String diagram) {
		this.diagram = diagram;
	}
	public Double getAnnual_pv_module_degradation() {
		return annual_pv_module_degradation;
	}
	public void setAnnual_pv_module_degradation(Double annual_pv_module_degradation) {
		this.annual_pv_module_degradation = annual_pv_module_degradation;
	}
	public Double getCable_losses() {
		return cable_losses;
	}
	public void setCable_losses(Double cable_losses) {
		this.cable_losses = cable_losses;
	}
	public Double getTransformer_losses() {
		return transformer_losses;
	}
	public void setTransformer_losses(Double transformer_losses) {
		this.transformer_losses = transformer_losses;
	}
	public Double getSoiling() {
		return soiling;
	}
	public void setSoiling(Double soiling) {
		this.soiling = soiling;
	}
	public Double getOther_losses() {
		return other_losses;
	}
	public void setOther_losses(Double other_losses) {
		this.other_losses = other_losses;
	}
	public LocalDate getExpiration() {
		return expiration;
	}
	public void setExpiration(LocalDateTime expiration) {
		this.expiration = expiration.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDate();
	}
	public Boolean getIs_show_each_meter() {
		return is_show_each_meter;
	}
	public void setIs_show_each_meter(Boolean is_show_each_meter) {
		this.is_show_each_meter = is_show_each_meter;
	}
	public Boolean getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(Boolean enable_alert) {
		this.enable_alert = enable_alert;
	}
	public String getCommunication() {
		return communication;
	}
	public void setCommunication(String communication) {
		this.communication = communication;
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
	public Integer getMqtt_port() {
		return mqtt_port;
	}
	public void setMqtt_port(Integer mqtt_port) {
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
	public String getThird_party_key() {
		return third_party_key;
	}
	public void setThird_party_key(String third_party_key) {
		this.third_party_key = third_party_key;
	}
	public Integer getReporting_region() {
		return reporting_region;
	}
	public void setReporting_region(Integer reporting_region) {
		this.reporting_region = reporting_region;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public String getCost_unit() {
		return cost_unit;
	}
	public void setCost_unit(String cost_unit) {
		this.cost_unit = cost_unit;
	}
	public Boolean getIs_solar_site() {
		return is_solar_site;
	}
	public void setIs_solar_site(Boolean is_solar_site) {
		this.is_solar_site = is_solar_site;
	}
	public Integer getSite_domain_type() {
		return site_domain_type;
	}
	public void setSite_domain_type(Integer site_domain_type) {
		this.site_domain_type = site_domain_type;
	}
	public String getB_year_built() {
		return b_year_built;
	}
	public void setB_year_built(String b_year_built) {
		this.b_year_built = b_year_built;
	}
	public Double getB_total_area() {
		return b_total_area;
	}
	public void setB_total_area(Double b_total_area) {
		this.b_total_area = b_total_area;
	}
	public Integer getB_type() {
		return b_type;
	}
	public void setB_type(Integer b_type) {
		this.b_type = b_type;
	}
	public Integer getB_status() {
		return b_status;
	}
	public void setB_status(Integer b_status) {
		this.b_status = b_status;
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
	public Integer getG_voltage_level_type() {
		return g_voltage_level_type;
	}
	public void setG_voltage_level_type(Integer g_voltage_level_type) {
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
	public Double getG_maximum_capacity() {
		return g_maximum_capacity;
	}
	public void setG_maximum_capacity(Double g_maximum_capacity) {
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
	public Double getG_der_capacities() {
		return g_der_capacities;
	}
	public void setG_der_capacities(Double g_der_capacities) {
		this.g_der_capacities = g_der_capacities;
	}
	public Double getG_tariff_structure_rate_schedule() {
		return g_tariff_structure_rate_schedule;
	}
	public void setG_tariff_structure_rate_schedule(Double g_tariff_structure_rate_schedule) {
		this.g_tariff_structure_rate_schedule = g_tariff_structure_rate_schedule;
	}
	public Double getG_net_metering_feed_in_tariff() {
		return g_net_metering_feed_in_tariff;
	}
	public void setG_net_metering_feed_in_tariff(Double g_net_metering_feed_in_tariff) {
		this.g_net_metering_feed_in_tariff = g_net_metering_feed_in_tariff;
	}
	public String getG_power_quality_requirements() {
		return g_power_quality_requirements;
	}
	public void setG_power_quality_requirements(String g_power_quality_requirements) {
		this.g_power_quality_requirements = g_power_quality_requirements;
	}
	public Double getG_transformer_size() {
		return g_transformer_size;
	}
	public void setG_transformer_size(Double g_transformer_size) {
		this.g_transformer_size = g_transformer_size;
	}
	public Double getG_transformer_type() {
		return g_transformer_type;
	}
	public void setG_transformer_type(Double g_transformer_type) {
		this.g_transformer_type = g_transformer_type;
	}
	public Integer getG_status() {
		return g_status;
	}
	public void setG_status(Integer g_status) {
		this.g_status = g_status;
	}
	public Double getG_lat() {
		return g_lat;
	}
	public void setG_lat(Double g_lat) {
		this.g_lat = g_lat;
	}
	public Double getG_lng() {
		return g_lng;
	}
	public void setG_lng(Double g_lng) {
		this.g_lng = g_lng;
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
	public Double getGas_average_daily_consumption() {
		return gas_average_daily_consumption;
	}
	public void setGas_average_daily_consumption(Double gas_average_daily_consumption) {
		this.gas_average_daily_consumption = gas_average_daily_consumption;
	}
	public Double getGas_peak_demand() {
		return gas_peak_demand;
	}
	public void setGas_peak_demand(Double gas_peak_demand) {
		this.gas_peak_demand = gas_peak_demand;
	}
	public Integer getGas_service_type() {
		return gas_service_type;
	}
	public void setGas_service_type(Integer gas_service_type) {
		this.gas_service_type = gas_service_type;
	}
	public Double getGas_service_pressure() {
		return gas_service_pressure;
	}
	public void setGas_service_pressure(Double gas_service_pressure) {
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
	public Integer getGas_billing_cycle() {
		return gas_billing_cycle;
	}
	public void setGas_billing_cycle(Integer gas_billing_cycle) {
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
	public Integer getSite_type() {
		return site_type;
	}
	public void setSite_type(Integer site_type) {
		this.site_type = site_type;
	}
	public String getDashboard_overview_note() {
		return dashboard_overview_note;
	}
	public void setDashboard_overview_note(String dashboard_overview_note) {
		this.dashboard_overview_note = dashboard_overview_note;
	}
	public Integer getTime_queue() {
		return time_queue;
	}
	public void setTime_queue(Integer time_queue) {
		this.time_queue = time_queue;
	}
	public Integer getThreshold_item_max() {
		return threshold_item_max;
	}
	public void setThreshold_item_max(Integer threshold_item_max) {
		this.threshold_item_max = threshold_item_max;
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
	public Integer getAdvance_tech_status() {
		return advance_tech_status;
	}
	public void setAdvance_tech_status(Integer advance_tech_status) {
		this.advance_tech_status = advance_tech_status;
	}
	public Integer getAdvance_tech_control_enable() {
		return advance_tech_control_enable;
	}
	public void setAdvance_tech_control_enable(Integer advance_tech_control_enable) {
		this.advance_tech_control_enable = advance_tech_control_enable;
	}
	public Double getTotal_area() {
		return total_area;
	}
	public void setTotal_area(Double total_area) {
		this.total_area = total_area;
	}
	public Double getTotal_unit() {
		return total_unit;
	}
	public void setTotal_unit(Double total_unit) {
		this.total_unit = total_unit;
	}
	public Boolean getInv_level() {
		return inv_level;
	}
	public void setInv_level(Boolean inv_level) {
		this.inv_level = inv_level;
	}
}
