/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class DeviceEntity {
	private int id;
	private int id_site;
	private int id_vendor;
	private String serial_number;
	private String serialnumber;
	private String modbusdevicenumber;
	private String devicename;
	private String devicetype;
	private int deviceclass;
	private String configuration;
	private String configurationchangetime;
	private String configurationchecksum;
	private String datatablename;
	private int id_device_type;
	private int id_device_group;
	private int id_customer;
	private int active;
	private int limit;
	private int offset;
	private int totalRecord;
	private String order_by;
	private String sort_by;
	private String keyword;
	private Date created_date;
	private String created_by;
	private Date updated_date;
	private String updated_by;
	private String sort_column;
	private int status;
	private int is_delete;
	private int screen_mode;
	private String code_prefix;
	private List list_parameters;
	private String last_attempt;
	private String last_communication;
	private String device_type_name;
	private String image;
	private String hash_id;
	private String site_name;
	private String hash_site_id;
	private int start_date_time;
	private int end_date_time;
	private String current_time;
	private String timezone_offset;
	private String timezone_value;
	private String last_updated;
	private List dataDevice;
	private String filterBy;
	private  int data_send_time;
	private String end_date;
	private String start_date;
	private Double last_value;
	private Double energy_lifetime;
	private Double energy_today;
	private Double energy_this_month;
	private int type;
	private String abbreviation_std;
	private String timezone;
	private String hash_id_site;
	private int total_error;
	
	private List groupMeter;
	private List groupInverter;
	private List groupWeather;
	private int year;
	private Double lat;
	private Double lng;
	private int kiosk_view;
	private int id_employee;
	private Integer order_id;
	private String read_data_all;
	private String view_tablename;
	private int con_power;
	private int con_setpoint;
	private int con_temp;
	private Double max_ac_power;
	private int reverse_poa;
	private String field_value_default;
	private Double field_value1;
	private Double field_value2;
	private Double field_value3;
	private Double rating_ac_power;
	
	
	
	
	public Double getField_value1() {
		return field_value1;
	}
	public void setField_value1(Double field_value1) {
		this.field_value1 = field_value1;
	}
	public Double getField_value2() {
		return field_value2;
	}
	public void setField_value2(Double field_value2) {
		this.field_value2 = field_value2;
	}
	public Double getField_value3() {
		return field_value3;
	}
	public void setField_value3(Double field_value3) {
		this.field_value3 = field_value3;
	}

	public String getField_value_default() {
		return field_value_default;
	}
	public void setField_value_default(String field_value_default) {
		this.field_value_default = field_value_default;
	}
	public int getReverse_poa() {
		return reverse_poa;
	}
	public void setReverse_poa(int reverse_poa) {
		this.reverse_poa = reverse_poa;
	}
	public Double getMax_ac_power() {
		return max_ac_power;
	}
	public void setMax_ac_power(Double max_ac_power) {
		this.max_ac_power = max_ac_power;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public int getId_vendor() {
		return id_vendor;
	}
	public void setId_vendor(int id_vendor) {
		this.id_vendor = id_vendor;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getModbusdevicenumber() {
		return modbusdevicenumber;
	}
	public void setModbusdevicenumber(String modbusdevicenumber) {
		this.modbusdevicenumber = modbusdevicenumber;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public int getDeviceclass() {
		return deviceclass;
	}
	public void setDeviceclass(int deviceclass) {
		this.deviceclass = deviceclass;
	}
	public String getConfiguration() {
		return configuration;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	public String getConfigurationchangetime() {
		return configurationchangetime;
	}
	public void setConfigurationchangetime(String configurationchangetime) {
		this.configurationchangetime = configurationchangetime;
	}
	public String getConfigurationchecksum() {
		return configurationchecksum;
	}
	public void setConfigurationchecksum(String configurationchecksum) {
		this.configurationchecksum = configurationchecksum;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public int getId_device_type() {
		return id_device_type;
	}
	public void setId_device_type(int id_device_type) {
		this.id_device_type = id_device_type;
	}
	public int getId_device_group() {
		return id_device_group;
	}
	public void setId_device_group(int id_device_group) {
		this.id_device_group = id_device_group;
	}
	public int getId_customer() {
		return id_customer;
	}
	public void setId_customer(int id_customer) {
		this.id_customer = id_customer;
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
	public String getSort_by() {
		return sort_by;
	}
	public void setSort_by(String sort_by) {
		this.sort_by = sort_by;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
	public String getSort_column() {
		return sort_column;
	}
	public void setSort_column(String sort_column) {
		this.sort_column = sort_column;
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
	public int getScreen_mode() {
		return screen_mode;
	}
	public void setScreen_mode(int screen_mode) {
		this.screen_mode = screen_mode;
	}
	public String getCode_prefix() {
		return code_prefix;
	}
	public void setCode_prefix(String code_prefix) {
		this.code_prefix = code_prefix;
	}
	public List getList_parameters() {
		return list_parameters;
	}
	public void setList_parameters(List list_parameters) {
		this.list_parameters = list_parameters;
	}
	public String getLast_attempt() {
		return last_attempt;
	}
	public void setLast_attempt(String last_attempt) {
		this.last_attempt = last_attempt;
	}
	public String getLast_communication() {
		return last_communication;
	}
	public void setLast_communication(String last_communication) {
		this.last_communication = last_communication;
	}
	public String getDevice_type_name() {
		return device_type_name;
	}
	public void setDevice_type_name(String device_type_name) {
		this.device_type_name = device_type_name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getHash_id() {
		return hash_id;
	}
	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getHash_site_id() {
		return hash_site_id;
	}
	public void setHash_site_id(String hash_site_id) {
		this.hash_site_id = hash_site_id;
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
	public String getCurrent_time() {
		return current_time;
	}
	public void setCurrent_time(String current_time) {
		this.current_time = current_time;
	}
	public String getTimezone_offset() {
		return timezone_offset;
	}
	public void setTimezone_offset(String timezone_offset) {
		this.timezone_offset = timezone_offset;
	}
	public String getTimezone_value() {
		return timezone_value;
	}
	public void setTimezone_value(String timezone_value) {
		this.timezone_value = timezone_value;
	}
	public String getLast_updated() {
		return last_updated;
	}
	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}
	public List getDataDevice() {
		return dataDevice;
	}
	public void setDataDevice(List dataDevice) {
		this.dataDevice = dataDevice;
	}
	public String getFilterBy() {
		return filterBy;
	}
	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
	}
	public int getData_send_time() {
		return data_send_time;
	}
	public void setData_send_time(int data_send_time) {
		this.data_send_time = data_send_time;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public Double getLast_value() {
		return last_value;
	}
	public void setLast_value(Double last_value) {
		this.last_value = last_value;
	}
	public Double getEnergy_lifetime() {
		return energy_lifetime;
	}
	public void setEnergy_lifetime(Double energy_lifetime) {
		this.energy_lifetime = energy_lifetime;
	}
	public Double getEnergy_today() {
		return energy_today;
	}
	public void setEnergy_today(Double energy_today) {
		this.energy_today = energy_today;
	}
	public Double getEnergy_this_month() {
		return energy_this_month;
	}
	public void setEnergy_this_month(Double energy_this_month) {
		this.energy_this_month = energy_this_month;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAbbreviation_std() {
		return abbreviation_std;
	}
	public void setAbbreviation_std(String abbreviation_std) {
		this.abbreviation_std = abbreviation_std;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getHash_id_site() {
		return hash_id_site;
	}
	public void setHash_id_site(String hash_id_site) {
		this.hash_id_site = hash_id_site;
	}
	public int getTotal_error() {
		return total_error;
	}
	public void setTotal_error(int total_error) {
		this.total_error = total_error;
	}
	public List getGroupMeter() {
		return groupMeter;
	}
	public void setGroupMeter(List groupMeter) {
		this.groupMeter = groupMeter;
	}
	public List getGroupInverter() {
		return groupInverter;
	}
	public void setGroupInverter(List groupInverter) {
		this.groupInverter = groupInverter;
	}
	public List getGroupWeather() {
		return groupWeather;
	}
	public void setGroupWeather(List groupWeather) {
		this.groupWeather = groupWeather;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
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
	public int getKiosk_view() {
		return kiosk_view;
	}
	public void setKiosk_view(int kiosk_view) {
		this.kiosk_view = kiosk_view;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public String getRead_data_all() {
		return read_data_all;
	}
	public void setRead_data_all(String read_data_all) {
		this.read_data_all = read_data_all;
	}
	public String getView_tablename() {
		return view_tablename;
	}
	public void setView_tablename(String view_tablename) {
		this.view_tablename = view_tablename;
	}
	public int getCon_power() {
		return con_power;
	}
	public void setCon_power(int con_power) {
		this.con_power = con_power;
	}
	public int getCon_setpoint() {
		return con_setpoint;
	}
	public void setCon_setpoint(int con_setpoint) {
		this.con_setpoint = con_setpoint;
	}
	public int getCon_temp() {
		return con_temp;
	}
	public void setCon_temp(int con_temp) {
		this.con_temp = con_temp;
	}
	public Double getRating_ac_power() {
		return rating_ac_power;
	}
	public void setRating_ac_power(Double rating_ac_power) {
		this.rating_ac_power = rating_ac_power;
	}
	
	
	
	
}
