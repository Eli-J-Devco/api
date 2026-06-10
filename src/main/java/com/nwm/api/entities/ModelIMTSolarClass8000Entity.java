/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelIMTSolarClass8000Entity extends ModelBaseEntity {
	private double irradiance;
	private double tcell;
	
	public double getIrradiance() {
		return irradiance;
	}
	public void setIrradiance(double irradiance) {
		this.irradiance = irradiance;
	}
	public double getTcell() {
		return tcell;
	}
	public void setTcell(double tcell) {
		this.tcell = tcell;
	}
	
	
	
	
}
