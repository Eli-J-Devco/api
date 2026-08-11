/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class SiteSubGroupEntity {
	private Integer id;
	private String name;
	private List<SiteDTO> sites;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SiteDTO> getSites() {
		return sites;
	}
	public void setSites(List<SiteDTO> sites) {
		this.sites = sites;
	}
}
