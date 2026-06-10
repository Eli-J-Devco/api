/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelCampellScientificMeter1Entity extends ModelBaseEntity {
	private double Meter1_ACPower;
	private double Total_Energy;
	
	public double getMeter1_ACPower() {
		return Meter1_ACPower;
	}
	public void setMeter1_ACPower(double meter1_ACPower) {
		Meter1_ACPower = meter1_ACPower;
	}
	public double getTotal_Energy() {
		return Total_Energy;
	}
	public void setTotal_Energy(double total_Energy) {
		Total_Energy = total_Energy;
	}
	
	
	
}
