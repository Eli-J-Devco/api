/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelCampellScientificMeter4Entity extends ModelBaseEntity {
	private double Meter4_ACPower;
	private double Total_Energy;
	
	public double getTotal_Energy() {
		return Total_Energy;
	}
	public void setTotal_Energy(double total_Energy) {
		Total_Energy = total_Energy;
	}
	public double getMeter4_ACPower() {
		return Meter4_ACPower;
	}
	public void setMeter4_ACPower(double meter4_ACPower) {
		Meter4_ACPower = meter4_ACPower;
	}
	
	
	
}
