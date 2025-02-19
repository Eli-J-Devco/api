/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities.building;

import com.nwm.api.entities.DateTimeReportDataEntity;

public class ChartConsumptionEntity extends DateTimeReportDataEntity {
	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
}
