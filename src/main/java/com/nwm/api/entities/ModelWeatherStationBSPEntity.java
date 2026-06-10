/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelWeatherStationBSPEntity extends ModelBaseEntity {
	private double DailyIrradiationAmount;
	private double TotalIrradiance;
	private double PVmoduletemperature;
	private double AmbientTemperature;
	private double WindSpeed;
	private double WindDirection;
	public double getDailyIrradiationAmount() {
		return DailyIrradiationAmount;
	}
	public void setDailyIrradiationAmount(double dailyIrradiationAmount) {
		DailyIrradiationAmount = dailyIrradiationAmount;
	}
	public double getTotalIrradiance() {
		return TotalIrradiance;
	}
	public void setTotalIrradiance(double totalIrradiance) {
		TotalIrradiance = totalIrradiance;
	}
	public double getPVmoduletemperature() {
		return PVmoduletemperature;
	}
	public void setPVmoduletemperature(double pVmoduletemperature) {
		PVmoduletemperature = pVmoduletemperature;
	}
	public double getAmbientTemperature() {
		return AmbientTemperature;
	}
	public void setAmbientTemperature(double ambientTemperature) {
		AmbientTemperature = ambientTemperature;
	}
	public double getWindSpeed() {
		return WindSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		WindSpeed = windSpeed;
	}
	public double getWindDirection() {
		return WindDirection;
	}
	public void setWindDirection(double windDirection) {
		WindDirection = windDirection;
	}
	
	
	
	
	
	
	
}
