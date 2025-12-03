/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ReportValueByDatetimeDTO extends DateTimeReportDataEntity {
	private Double value;

	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
}
