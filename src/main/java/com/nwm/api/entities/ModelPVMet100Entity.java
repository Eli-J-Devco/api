/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelPVMet100Entity extends ModelBaseEntity {
	private double TransientHorizontalIrradiance;
	private double DailyHorizontalIrradiance;
	private double AmbientTemperature;
	private double Temperature;
	private double WindAngle;
	private double WindSpeed;
	
	public double getTransientHorizontalIrradiance() {
		return TransientHorizontalIrradiance;
	}
	public void setTransientHorizontalIrradiance(double transientHorizontalIrradiance) {
		TransientHorizontalIrradiance = transientHorizontalIrradiance;
	}
	public double getDailyHorizontalIrradiance() {
		return DailyHorizontalIrradiance;
	}
	public void setDailyHorizontalIrradiance(double dailyHorizontalIrradiance) {
		DailyHorizontalIrradiance = dailyHorizontalIrradiance;
	}
	public double getAmbientTemperature() {
		return AmbientTemperature;
	}
	public void setAmbientTemperature(double ambientTemperature) {
		AmbientTemperature = ambientTemperature;
	}
	public double getTemperature() {
		return Temperature;
	}
	public void setTemperature(double temperature) {
		Temperature = temperature;
	}
	public double getWindAngle() {
		return WindAngle;
	}
	public void setWindAngle(double windAngle) {
		WindAngle = windAngle;
	}
	public double getWindSpeed() {
		return WindSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		WindSpeed = windSpeed;
	}
}
