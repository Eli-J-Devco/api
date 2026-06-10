/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelCampellScientificMeter2Entity extends ModelBaseEntity {
	private double Meter2_ACPower;
	private double Total_Energy;
	
	public double getTotal_Energy() {
		return Total_Energy;
	}
	public void setTotal_Energy(double total_Energy) {
		Total_Energy = total_Energy;
	}
	public double getMeter2_ACPower() {
		return Meter2_ACPower;
	}
	public void setMeter2_ACPower(double meter2_ACPower) {
		Meter2_ACPower = meter2_ACPower;
	}
	
	
	
	
}
