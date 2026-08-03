/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class SiteGroupEntity {
	private int id;
	private String name;
	private String icon;
	private List<SiteSubGroupEntity> subGroups;
	
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<SiteSubGroupEntity> getSubGroups() {
		return subGroups;
	}
	public void setSubGroups(List<SiteSubGroupEntity> subGroups) {
		this.subGroups = subGroups;
	}
}
