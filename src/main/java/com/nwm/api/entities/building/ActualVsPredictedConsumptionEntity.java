/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities.building;

import java.util.List;

public class ActualVsPredictedConsumptionEntity {
	private String id;
	private List<ChartConsumptionEntity> data;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ChartConsumptionEntity> getData() {
		return data;
	}
	public void setData(List<ChartConsumptionEntity> data) {
		this.data = data;
	}
}
