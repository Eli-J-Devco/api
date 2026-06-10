/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class ScadaOverviewEntity {
	private int id_site;
	private String hash_id;
	private int id_employee;
	private String sort_column;
	private String order_by;
	private List groupMeter;
	private String read_data_all;
	
	
	
	public String getHash_id() {
		return hash_id;
	}
	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}
	public String getRead_data_all() {
		return read_data_all;
	}
	public void setRead_data_all(String read_data_all) {
		this.read_data_all = read_data_all;
	}
	public List getGroupMeter() {
		return groupMeter;
	}
	public void setGroupMeter(List groupMeter) {
		this.groupMeter = groupMeter;
	}
	public String getOrder_by() {
		return order_by;
	}
	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}
	public String getSort_column() {
		return sort_column;
	}
	public void setSort_column(String sort_column) {
		this.sort_column = sort_column;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
}
