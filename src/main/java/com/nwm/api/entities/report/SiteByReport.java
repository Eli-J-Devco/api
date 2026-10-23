package com.nwm.api.entities.report;

import java.util.ArrayList;
import java.util.List;

public class SiteByReport {
	private int id;
	private String name;
	private String table_data_report;
	private String table_data_virtual;
	private double ac_capacity;
	private double dc_capacity;
	private boolean is_rec_report;
	private String commissioning;
	private int data_send_time;
	private String datatablename_weather;
	private String logo;
	private String company_name;
	private boolean enable_virtual_device;
	private boolean have_poa;
	private boolean have_meter;
	private boolean have_inverter;
	private List<?> dataReports = new ArrayList<>();
	
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
	public boolean isIs_rec_report() {
		return is_rec_report;
	}
	public void setIs_rec_report(boolean is_rec_report) {
		this.is_rec_report = is_rec_report;
	}
	public String getCommissioning() {
		return commissioning;
	}
	public void setCommissioning(String commissioning) {
		this.commissioning = commissioning;
	}
	public int getData_send_time() {
		return data_send_time;
	}
	public void setData_send_time(int data_send_time) {
		this.data_send_time = data_send_time;
	}
	public String getDatatablename_weather() {
		return datatablename_weather;
	}
	public void setDatatablename_weather(String datatablename_weather) {
		this.datatablename_weather = datatablename_weather;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public boolean isEnable_virtual_device() {
		return enable_virtual_device;
	}
	public void setEnable_virtual_device(boolean enable_virtual_device) {
		this.enable_virtual_device = enable_virtual_device;
	}
	public boolean isHave_poa() {
		return have_poa;
	}
	public void setHave_poa(boolean have_poa) {
		this.have_poa = have_poa;
	}
	public boolean isHave_meter() {
		return have_meter;
	}
	public void setHave_meter(boolean have_meter) {
		this.have_meter = have_meter;
	}
	public boolean isHave_inverter() {
		return have_inverter;
	}
	public void setHave_inverter(boolean have_inverter) {
		this.have_inverter = have_inverter;
	}
	public List<?> getDataReports() {
		return dataReports;
	}
	public void setDataReports(List<?> dataReports) {
		this.dataReports = dataReports;
	}
}
