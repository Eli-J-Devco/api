/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class SiteDashboardGenerationEntity {
	private int id;
	private int id_time_zone;
	private String name;
	private int limit;
	private int offset;
	private int totalRecord;
	private double energy_this_month;
	private int id_site;
	private double totalGeneration;
	private double power_today;
	private String current_time;
	private int id_site_type;
	private int id_device;
	private String table_name;
	private List groupMeter;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public double getEnergy_this_month() {
		return energy_this_month;
	}
	public void setEnergy_this_month(double energy_this_month) {
		this.energy_this_month = energy_this_month;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public double getTotalGeneration() {
		return totalGeneration;
	}
	public void setTotalGeneration(double totalGeneration) {
		this.totalGeneration = totalGeneration;
	}
	public double getPower_today() {
		return power_today;
	}
	public void setPower_today(double power_today) {
		this.power_today = power_today;
	}
	public String getCurrent_time() {
		return current_time;
	}
	public void setCurrent_time(String current_time) {
		this.current_time = current_time;
	}
	public int getId_site_type() {
		return id_site_type;
	}
	public void setId_site_type(int id_site_type) {
		this.id_site_type = id_site_type;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public List getGroupMeter() {
		return groupMeter;
	}
	public void setGroupMeter(List groupMeter) {
		this.groupMeter = groupMeter;
	}
	
	
	
	
}
