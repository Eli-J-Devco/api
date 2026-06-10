/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class MonthlyDateEntity extends DateTimeReportDataEntity {
	private Double actual;
	private Double estimated;
	private Double percent;
	
	public Double getActual() {
		return actual;
	}
	public void setActual(Double actual) {
		this.actual = actual;
	}
	public Double getEstimated() {
		return estimated;
	}
	public void setEstimated(Double estimated) {
		this.estimated = estimated;
	}
	public Double getPercent() {
		return percent;
	}
	public void setPercent(Double percent) {
		this.percent = percent;
	}
	
	
	
	
	
}
