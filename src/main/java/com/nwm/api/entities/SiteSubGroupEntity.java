/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class SiteSubGroupEntity {
	private int id;
	private String name;
	private List<SiteEntity> sites;
	
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
	public List<SiteEntity> getSites() {
		return sites;
	}
	public void setSites(List<SiteEntity> sites) {
		this.sites = sites;
	}
}
