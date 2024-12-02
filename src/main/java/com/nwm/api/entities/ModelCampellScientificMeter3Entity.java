/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelCampellScientificMeter3Entity extends ModelBaseEntity {
	private double Meter3_ACPower;
	private double Total_Energy;
	
	public double getTotal_Energy() {
		return Total_Energy;
	}
	public void setTotal_Energy(double total_Energy) {
		Total_Energy = total_Energy;
	}
	public double getMeter3_ACPower() {
		return Meter3_ACPower;
	}
	public void setMeter3_ACPower(double meter3_ACPower) {
		Meter3_ACPower = meter3_ACPower;
	}
	
	
}
