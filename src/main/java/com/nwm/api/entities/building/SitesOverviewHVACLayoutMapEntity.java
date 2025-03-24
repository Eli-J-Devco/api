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
	
}
