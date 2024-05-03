/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SiteEnergyThirdPartyAPIEntity {
	private String site_name;
	private String type;
	private String energyJSON;
	private List<Map<String, Object>> energy;
	
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEnergyJSON() {
		return energyJSON;
	}
	public void setEnergyJSON(String energyJSON) {
		this.energyJSON = energyJSON;
	}
	public List<Map<String, Object>> getEnergy() {
		return energy;
	}
	public void setEnergy(List<Map<String, Object>> energy) {
		this.energy = energy;
	}
	
}
