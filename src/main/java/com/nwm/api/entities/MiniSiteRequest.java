/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class MiniSiteRequest {
	private int id;
	private int id_site;
	private String filterBy;
	private String start_date;
	private String end_date;
	private String hash_id;
	private int data_send_time;
	private List energy;
	private List groupDevices;
	private List groupMeter;
	private int enable_virtual_device;
	private String datatablename;
	private String table_data_virtual;
	private String table_data_report;
	private int device_mode;
	private boolean have_poa;
	private int totalMeter;
	private int is_supper_admin;
	private String domain;
	private String domain_role;
	
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
	public List getEnergy() {
		return energy;
	}
	public void setEnergy(List energy) {
		this.energy = energy;
	}
	public List getGroupDevices() {
		return groupDevices;
	}
	public void setGroupDevices(List groupDevices) {
		this.groupDevices = groupDevices;
	}
	public List getGroupMeter() {
		return groupMeter;
	}
	public void setGroupMeter(List groupMeter) {
		this.groupMeter = groupMeter;
	}
	public int getEnable_virtual_device() {
		return enable_virtual_device;
	}
	public void setEnable_virtual_device(int enable_virtual_device) {
		this.enable_virtual_device = enable_virtual_device;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
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
	public int getDevice_mode() {
		return device_mode;
	}
	public void setDevice_mode(int device_mode) {
		this.device_mode = device_mode;
	}
	public boolean isHave_poa() {
		return have_poa;
	}
	public void setHave_poa(boolean have_poa) {
		this.have_poa = have_poa;
	}
	public int getTotalMeter() {
		return totalMeter;
	}
	public void setTotalMeter(int totalMeter) {
		this.totalMeter = totalMeter;
	}
	public int getIs_supper_admin() {
		return is_supper_admin;
	}
	public void setIs_supper_admin(int is_supper_admin) {
		this.is_supper_admin = is_supper_admin;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDomain_role() {
		return domain_role;
	}
	public void setDomain_role(String domain_role) {
		this.domain_role = domain_role;
	}
}
