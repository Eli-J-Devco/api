/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelKippZonenCMP214PointEntity  extends ModelBaseEntity {
	
	private double TemperatureCompensatedIrradiance;
	private double InternalRelativeHumidity;
	private double SensorTemperature;
	private double WeatherStationtypeandmodel;
	public double getTemperatureCompensatedIrradiance() {
		return TemperatureCompensatedIrradiance;
	}
	public void setTemperatureCompensatedIrradiance(double temperatureCompensatedIrradiance) {
		TemperatureCompensatedIrradiance = temperatureCompensatedIrradiance;
	}
	public double getInternalRelativeHumidity() {
		return InternalRelativeHumidity;
	}
	public void setInternalRelativeHumidity(double internalRelativeHumidity) {
		InternalRelativeHumidity = internalRelativeHumidity;
	}
	public double getSensorTemperature() {
		return SensorTemperature;
	}
	public void setSensorTemperature(double sensorTemperature) {
		SensorTemperature = sensorTemperature;
	}
	public double getWeatherStationtypeandmodel() {
		return WeatherStationtypeandmodel;
	}
	public void setWeatherStationtypeandmodel(double weatherStationtypeandmodel) {
		WeatherStationtypeandmodel = weatherStationtypeandmodel;
	}
	
	
	
	
}
