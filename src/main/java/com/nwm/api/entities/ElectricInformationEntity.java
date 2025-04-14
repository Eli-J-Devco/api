/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;
import java.util.List;

public class ElectricInformationEntity extends SortEntity {
	private int id;
	private int id_company;
	private int limit;
	private int offset;
	private int totalRecord;
	private double energy_today;
	private double energy_yesterday;
	private double energy_this_month;
	private double energy_last_month;
	private double energy_compare_last_month;
	private double energy_last30_days;
	private double energy_this_year;
	private double energy_lifetime;
	private double energy_this_week;
	private double energy_last_week;
	private String filterBy;
	private String start_date;
	private String end_date;
	private int id_site;
	private String hash_id;
	private List devices;
	private int meter_type;
	private double avg_this_month;
	private double avg_last_month;
	private double monthly_lighting;
	private double monthly_hvac;
	private double monthly_solar;
	private double monthly_electricity;
	private double monthly_gas;
	private double gas_today;
	private double water_today;
	private double gas_yesterday;
	private double solar_yesterday;
	private double last_month_gas;
	private double last_month_water;
	private double last_month_electricity;
	private double monthly_water;
	private double avg_daily_7days;
	private double avg_daily_last_7days;
	
	
	
	
	
	public double getAvg_daily_7days() {
		return avg_daily_7days;
	}
	public void setAvg_daily_7days(double avg_daily_7days) {
		this.avg_daily_7days = avg_daily_7days;
	}
	public double getAvg_daily_last_7days() {
		return avg_daily_last_7days;
	}
	public void setAvg_daily_last_7days(double avg_daily_last_7days) {
		this.avg_daily_last_7days = avg_daily_last_7days;
	}
	public double getMonthly_water() {
		return monthly_water;
	}
	public void setMonthly_water(double monthly_water) {
		this.monthly_water = monthly_water;
	}
	public double getGas_yesterday() {
		return gas_yesterday;
	}
	public void setGas_yesterday(double gas_yesterday) {
		this.gas_yesterday = gas_yesterday;
	}
	public double getSolar_yesterday() {
		return solar_yesterday;
	}
	public void setSolar_yesterday(double solar_yesterday) {
		this.solar_yesterday = solar_yesterday;
	}
	public double getLast_month_gas() {
		return last_month_gas;
	}
	public void setLast_month_gas(double last_month_gas) {
		this.last_month_gas = last_month_gas;
	}
	public double getLast_month_water() {
		return last_month_water;
	}
	public void setLast_month_water(double last_month_water) {
		this.last_month_water = last_month_water;
	}
	public double getLast_month_electricity() {
		return last_month_electricity;
	}
	public void setLast_month_electricity(double last_month_electricity) {
		this.last_month_electricity = last_month_electricity;
	}
	public double getGas_today() {
		return gas_today;
	}
	public void setGas_today(double gas_today) {
		this.gas_today = gas_today;
	}
	public double getWater_today() {
		return water_today;
	}
	public void setWater_today(double water_today) {
		this.water_today = water_today;
	}
	public double getMonthly_electricity() {
		return monthly_electricity;
	}
	public void setMonthly_electricity(double monthly_electricity) {
		this.monthly_electricity = monthly_electricity;
	}
	public double getMonthly_solar() {
		return monthly_solar;
	}
	public void setMonthly_solar(double monthly_solar) {
		this.monthly_solar = monthly_solar;
	}
	
	public double getMonthly_gas() {
		return monthly_gas;
	}
	public void setMonthly_gas(double monthly_gas) {
		this.monthly_gas = monthly_gas;
	}
	public double getMonthly_lighting() {
		return monthly_lighting;
	}
	public void setMonthly_lighting(double monthly_lighting) {
		this.monthly_lighting = monthly_lighting;
	}
	public double getMonthly_hvac() {
		return monthly_hvac;
	}
	public void setMonthly_hvac(double monthly_hvac) {
		this.monthly_hvac = monthly_hvac;
	}
	public double getAvg_this_month() {
		return avg_this_month;
	}
	public void setAvg_this_month(double avg_this_month) {
		this.avg_this_month = avg_this_month;
	}
	public double getAvg_last_month() {
		return avg_last_month;
	}
	public void setAvg_last_month(double avg_last_month) {
		this.avg_last_month = avg_last_month;
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
	public double getEnergy_today() {
		return energy_today;
	}
	public void setEnergy_today(double energy_today) {
		this.energy_today = energy_today;
	}
	public double getEnergy_yesterday() {
		return energy_yesterday;
	}
	public void setEnergy_yesterday(double energy_yesterday) {
		this.energy_yesterday = energy_yesterday;
	}
	public double getEnergy_this_month() {
		return energy_this_month;
	}
	public void setEnergy_this_month(double energy_this_month) {
		this.energy_this_month = energy_this_month;
	}
	public double getEnergy_last_month() {
		return energy_last_month;
	}
	public void setEnergy_last_month(double energy_last_month) {
		this.energy_last_month = energy_last_month;
	}
	public double getEnergy_compare_last_month() {
		return energy_compare_last_month;
	}
	public void setEnergy_compare_last_month(double energy_compare_last_month) {
		this.energy_compare_last_month = energy_compare_last_month;
	}
	public double getEnergy_last30_days() {
		return energy_last30_days;
	}
	public void setEnergy_last30_days(double energy_last30_days) {
		this.energy_last30_days = energy_last30_days;
	}
	public double getEnergy_this_year() {
		return energy_this_year;
	}
	public void setEnergy_this_year(double energy_this_year) {
		this.energy_this_year = energy_this_year;
	}
	public double getEnergy_lifetime() {
		return energy_lifetime;
	}
	public void setEnergy_lifetime(double energy_lifetime) {
		this.energy_lifetime = energy_lifetime;
	}
	public double getEnergy_this_week() {
		return energy_this_week;
	}
	public void setEnergy_this_week(double energy_this_week) {
		this.energy_this_week = energy_this_week;
	}
	public double getEnergy_last_week() {
		return energy_last_week;
	}
	public void setEnergy_last_week(double energy_last_week) {
		this.energy_last_week = energy_last_week;
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
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public String getHash_id() {
		return hash_id;
	}
	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}
	public List getDevices() {
		return devices;
	}
	public void setDevices(List devices) {
		this.devices = devices;
	}
	public int getMeter_type() {
		return meter_type;
	}
	public void setMeter_type(int meter_type) {
		this.meter_type = meter_type;
	}
	
	
	
}
