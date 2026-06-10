/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities.building;

import java.util.List;

public class SitesOverviewHVACLayoutMapEntity {
	private int id_hvac_layout;
	private List<HVACMappingPointEntity> points;
	private String id_gateway;
	private List<HVACConfigPointEntity> config_points;
	
	public int getId_hvac_layout() {
		return id_hvac_layout;
	}
	public void setId_hvac_layout(int id_hvac_layout) {
		this.id_hvac_layout = id_hvac_layout;
	}
	public List<HVACMappingPointEntity> getPoints() {
		return points;
	}
	public void setPoints(List<HVACMappingPointEntity> points) {
		this.points = points;
	}
	public String getId_gateway() {
		return id_gateway;
	}
	public void setId_gateway(String id_gateway) {
		this.id_gateway = id_gateway;
	}
	public List<HVACConfigPointEntity> getConfig_points() {
		return config_points;
	}
	public void setConfig_points(List<HVACConfigPointEntity> config_points) {
		this.config_points = config_points;
	}
	
}
