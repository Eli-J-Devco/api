/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities.building;

import java.util.List;

import com.nwm.api.entities.DeviceEntity;

public class SitesOverviewGasConsumptionEntity {
	private List<ChartConsumptionEntity> data;
	private Double average;
	
	public List<ChartConsumptionEntity> getData() {
		return data;
	}
	public void setData(List<ChartConsumptionEntity> data) {
		this.data = data;
	}
	public Double getAverage() {
		return average;
	}
	public void setAverage(Double average) {
		this.average = average;
	}
	
}
