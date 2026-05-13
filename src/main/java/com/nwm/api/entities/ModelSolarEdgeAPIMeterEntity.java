/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSolarEdgeAPIMeterEntity extends ModelBaseEntity {
	private double TotalEnergy;
	private double ActivePower;

	public double getTotalEnergy() {
		return TotalEnergy;
	}

	public void setTotalEnergy(double totalEnergy) {
		TotalEnergy = totalEnergy;
	}

	public double getActivePower() {
		return ActivePower;
	}

	public void setActivePower(double activePower) {
		ActivePower = activePower;
	}
}
