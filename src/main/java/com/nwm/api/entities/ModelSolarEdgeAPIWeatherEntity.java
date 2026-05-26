/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSolarEdgeAPIWeatherEntity extends ModelBaseEntity {
	private double ambientTemperature;
	private double planeOfArrayIrradiance;

	public double getAmbientTemperature() {
		return ambientTemperature;
	}

	public void setAmbientTemperature(double ambientTemperature) {
		this.ambientTemperature = ambientTemperature;
	}

	public double getPlaneOfArrayIrradiance() {
		return planeOfArrayIrradiance;
	}

	public void setPlaneOfArrayIrradiance(double planeOfArrayIrradiance) {
		this.planeOfArrayIrradiance = planeOfArrayIrradiance;
	}
}
