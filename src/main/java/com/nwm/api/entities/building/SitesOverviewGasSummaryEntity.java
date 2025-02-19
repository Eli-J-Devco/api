/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities.building;

public class SitesOverviewGasSummaryEntity {
	private Double value;
	private String unit;
	private Double trend;
	
	public SitesOverviewGasSummaryEntity() {}
	
	public SitesOverviewGasSummaryEntity(Double value, String unit, Double trend) {
		this.value = value;
		this.unit = unit;
		this.trend = trend;
	}
	
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getTrend() {
		return trend;
	}

	public void setTrend(Double trend) {
		this.trend = trend;
	}
	
}
