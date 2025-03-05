/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities.building;

public class SitesOverviewGasSummaryEntity {
	private Double value;
	private Double trend;
	
	public SitesOverviewGasSummaryEntity() {}
	
	public SitesOverviewGasSummaryEntity(Double value, Double trend) {
		this.value = value;
		this.trend = trend;
	}
	
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getTrend() {
		return trend;
	}

	public void setTrend(Double trend) {
		this.trend = trend;
	}
	
}
