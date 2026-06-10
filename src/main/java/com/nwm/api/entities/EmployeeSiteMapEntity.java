/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class EmployeeSiteMapEntity {
	private int id;
	private int id_employee;
	private int id_site;
	private int id_site_group;
	private List siteList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public int getId_site_group() {
		return id_site_group;
	}
	public void setId_site_group(int id_site_group) {
		this.id_site_group = id_site_group;
	}
	public List getSiteList() {
		return siteList;
	}
	public void setSiteList(List siteList) {
		this.siteList = siteList;
	}

	
}
