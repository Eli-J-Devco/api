/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;
import java.util.List;

public class TopChangeContributorsEntity extends SortEntity {
	private int id;
	private int id_company;
	private int id_country;
	private int id_time_zone;
	private int limit;
	private int offset;
	private int totalRecord;
	private double electric_current;
	private double electric_compare;
	private double pv_current;
	private double pv_compare;
	private double gas_current;
	private double gas_compare;
	private double water_current;
	private double water_compare;
	private double lighting_current;
	private double lighting_compare;
	private double hvac_current;
	private double hvac_compare;
	private double current;
	private double current_compare;
	private String start_date;
	private String end_date;
	private int id_site;
	private String table_data_virtual;
	private String table_data_report;
	private List devices;
	private String id_filter;
	
	
	
	public String getId_filter() {
		return id_filter;
	}
	public void setId_filter(String id_filter) {
		this.id_filter = id_filter;
	}
	public double getCurrent() {
		return current;
	}
	public void setCurrent(double current) {
		this.current = current;
	}
	public double getCurrent_compare() {
		return current_compare;
	}
	public void setCurrent_compare(double current_compare) {
		this.current_compare = current_compare;
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
	public double getElectric_current() {
		return electric_current;
	}
	public void setElectric_current(double electric_current) {
		this.electric_current = electric_current;
	}
	public double getElectric_compare() {
		return electric_compare;
	}
	public void setElectric_compare(double electric_compare) {
		this.electric_compare = electric_compare;
	}
	public double getPv_current() {
		return pv_current;
	}
	public void setPv_current(double pv_current) {
		this.pv_current = pv_current;
	}
	public double getPv_compare() {
		return pv_compare;
	}
	public void setPv_compare(double pv_compare) {
		this.pv_compare = pv_compare;
	}
	public double getGas_current() {
		return gas_current;
	}
	public void setGas_current(double gas_current) {
		this.gas_current = gas_current;
	}
	public double getGas_compare() {
		return gas_compare;
	}
	public void setGas_compare(double gas_compare) {
		this.gas_compare = gas_compare;
	}
	public double getWater_current() {
		return water_current;
	}
	public void setWater_current(double water_current) {
		this.water_current = water_current;
	}
	public double getWater_compare() {
		return water_compare;
	}
	public void setWater_compare(double water_compare) {
		this.water_compare = water_compare;
	}
	public double getLighting_current() {
		return lighting_current;
	}
	public void setLighting_current(double lighting_current) {
		this.lighting_current = lighting_current;
	}
	public double getLighting_compare() {
		return lighting_compare;
	}
	public void setLighting_compare(double lighting_compare) {
		this.lighting_compare = lighting_compare;
	}
	public double getHvac_current() {
		return hvac_current;
	}
	public void setHvac_current(double hvac_current) {
		this.hvac_current = hvac_current;
	}
	public double getHvac_compare() {
		return hvac_compare;
	}
	public void setHvac_compare(double hvac_compare) {
		this.hvac_compare = hvac_compare;
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
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
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
	public List getDevices() {
		return devices;
	}
	public void setDevices(List devices) {
		this.devices = devices;
	}
	
	
	
}
