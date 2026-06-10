/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class MonthlyProductionTrendReportEntity extends DateTimeReportDataEntity {
	
	private Double monthlyProduction;
	
	public Double getMonthlyProduction() {
		return monthlyProduction;
	}
	public void setMonthlyProduction(Double monthlyProduction) {
		this.monthlyProduction = monthlyProduction;
	}
	
}
