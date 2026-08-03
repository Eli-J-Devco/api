/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelIMTWINDAMBIENTEntity extends ModelBaseEntity {
	
	private double Irradiance;
	private double WindSpeed;
	private double CellTemperature;
	private double AmbientTemperature1;
	private double AmbientTemperature2;
	
	public double getIrradiance() {
		return Irradiance;
	}
	public void setIrradiance(double irradiance) {
		Irradiance = irradiance;
	}
	public double getWindSpeed() {
		return WindSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		WindSpeed = windSpeed;
	}
	public double getCellTemperature() {
		return CellTemperature;
	}
	public void setCellTemperature(double cellTemperature) {
		CellTemperature = cellTemperature;
	}
	public double getAmbientTemperature1() {
		return AmbientTemperature1;
	}
	public void setAmbientTemperature1(double ambientTemperature1) {
		AmbientTemperature1 = ambientTemperature1;
	}
	public double getAmbientTemperature2() {
		return AmbientTemperature2;
	}
	public void setAmbientTemperature2(double ambientTemperature2) {
		AmbientTemperature2 = ambientTemperature2;
	}
	
	
	
	
	
	
}
