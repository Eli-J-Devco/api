/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;
import java.util.List;

public class BuildingReportEntity extends SortEntity {
	private int id;
	private int id_site;
	private int id_company;
	private int id_employee;
	private String start_date;
	private String end_date;
	private List devices;
	private int meter_type;
	
	private double pv_current_month;
	private double pv_compare_current_month;
	
	private double gas_current_month;
	private double gas_compare_current_month;
	
	private double water_current_month;
	private double water_compare_current_month;
	
	private double electric_current_month;
	private double electric_compare_current_month;
	
	private double current_month;
	private double compare_current_month;
	
	
	
	
	
	public double getCurrent_month() {
		return current_month;
	}
	public void setCurrent_month(double current_month) {
		this.current_month = current_month;
	}
	public double getCompare_current_month() {
		return compare_current_month;
	}
	public void setCompare_current_month(double compare_current_month) {
		this.compare_current_month = compare_current_month;
	}
	public double getPv_current_month() {
		return pv_current_month;
	}
	public void setPv_current_month(double pv_current_month) {
		this.pv_current_month = pv_current_month;
	}
	public double getPv_compare_current_month() {
		return pv_compare_current_month;
	}
	public void setPv_compare_current_month(double pv_compare_current_month) {
		this.pv_compare_current_month = pv_compare_current_month;
	}
	public double getGas_current_month() {
		return gas_current_month;
	}
	public void setGas_current_month(double gas_current_month) {
		this.gas_current_month = gas_current_month;
	}
	public double getGas_compare_current_month() {
		return gas_compare_current_month;
	}
	public void setGas_compare_current_month(double gas_compare_current_month) {
		this.gas_compare_current_month = gas_compare_current_month;
	}
	public double getWater_current_month() {
		return water_current_month;
	}
	public void setWater_current_month(double water_current_month) {
		this.water_current_month = water_current_month;
	}
	public double getWater_compare_current_month() {
		return water_compare_current_month;
	}
	public void setWater_compare_current_month(double water_compare_current_month) {
		this.water_compare_current_month = water_compare_current_month;
	}
	public double getElectric_current_month() {
		return electric_current_month;
	}
	public void setElectric_current_month(double electric_current_month) {
		this.electric_current_month = electric_current_month;
	}
	public double getElectric_compare_current_month() {
		return electric_compare_current_month;
	}
	public void setElectric_compare_current_month(double electric_compare_current_month) {
		this.electric_compare_current_month = electric_compare_current_month;
	}
	public int getMeter_type() {
		return meter_type;
	}
	public void setMeter_type(int meter_type) {
		this.meter_type = meter_type;
	}
	public List getDevices() {
		return devices;
	}
	public void setDevices(List devices) {
		this.devices = devices;
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
	public int getId_company() {
		return id_company;
	}
	public void setId_company(int id_company) {
		this.id_company = id_company;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
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
	
	
	
	
	
	
	
	
}
