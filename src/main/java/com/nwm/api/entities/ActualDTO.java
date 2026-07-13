/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ActualDTO extends DateTimeReportDataEntity {
	private Double energy;
	
	public Double getEnergy() {
		return energy;
	}
	public void setEnergy(Double actual) {
		this.energy = actual;
	}
}
