/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class SiteGasWaterElectricityRateScheduleEntity {
	private int id;
	private int id_site_water;
	private int id_site_gas;
	private int id_site_electricity;
	private int status;
	private double cost;
	private String unit;
	private String date_from;
	private String date_to;
	private String time_to;
	private String time_from;
	private double kwh_from;
	private double kwh_to;
	private double cost_to;
	private double cost_from;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_site_water() {
		return id_site_water;
	}
	public void setId_site_water(int id_site_water) {
		this.id_site_water = id_site_water;
	}
	public int getId_site_gas() {
		return id_site_gas;
	}
	public void setId_site_gas(int id_site_gas) {
		this.id_site_gas = id_site_gas;
	}
	public int getId_site_electricity() {
		return id_site_electricity;
	}
	public void setId_site_electricity(int id_site_electricity) {
		this.id_site_electricity = id_site_electricity;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDate_from() {
		return date_from;
	}
	public void setDate_from(String date_from) {
		this.date_from = date_from;
	}
	public String getDate_to() {
		return date_to;
	}
	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}
	public String getTime_to() {
		return time_to;
	}
	public void setTime_to(String time_to) {
		this.time_to = time_to;
	}
	public String getTime_from() {
		return time_from;
	}
	public void setTime_from(String time_from) {
		this.time_from = time_from;
	}
	public double getKwh_from() {
		return kwh_from;
	}
	public void setKwh_from(double kwh_from) {
		this.kwh_from = kwh_from;
	}
	public double getKwh_to() {
		return kwh_to;
	}
	public void setKwh_to(double kwh_to) {
		this.kwh_to = kwh_to;
	}
	public double getCost_to() {
		return cost_to;
	}
	public void setCost_to(double cost_to) {
		this.cost_to = cost_to;
	}
	public double getCost_from() {
		return cost_from;
	}
	public void setCost_from(double cost_from) {
		this.cost_from = cost_from;
	}
}
