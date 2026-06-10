/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelOpenMeteoWeatherEntity extends ModelBaseEntity {
	private double irradiance;
	private double temperature;
	private double humid;
	private double wind_speed;
	private double wind_direction;
	private double surface_pressure;
	private double total_precipitation;
	private double rain;
	private double snowfall;
	private double cloud_cover;
	private String sunrise;
	private String sunset;
	private double uv_index;
	public double getIrradiance() {
		return irradiance;
	}
	public void setIrradiance(double irradiance) {
		this.irradiance = irradiance;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getHumid() {
		return humid;
	}
	public void setHumid(double humid) {
		this.humid = humid;
	}
	public double getWind_speed() {
		return wind_speed;
	}
	public void setWind_speed(double wind_speed) {
		this.wind_speed = wind_speed;
	}
	public double getWind_direction() {
		return wind_direction;
	}
	public void setWind_direction(double wind_direction) {
		this.wind_direction = wind_direction;
	}
	public double getSurface_pressure() {
		return surface_pressure;
	}
	public void setSurface_pressure(double surface_pressure) {
		this.surface_pressure = surface_pressure;
	}
	public double getTotal_precipitation() {
		return total_precipitation;
	}
	public void setTotal_precipitation(double total_precipitation) {
		this.total_precipitation = total_precipitation;
	}
	public double getRain() {
		return rain;
	}
	public void setRain(double rain) {
		this.rain = rain;
	}
	public double getSnowfall() {
		return snowfall;
	}
	public void setSnowfall(double snowfall) {
		this.snowfall = snowfall;
	}
	public double getCloud_cover() {
		return cloud_cover;
	}
	public void setCloud_cover(double cloud_cover) {
		this.cloud_cover = cloud_cover;
	}
	public String getSunrise() {
		return sunrise;
	}
	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}
	public String getSunset() {
		return sunset;
	}
	public void setSunset(String sunset) {
		this.sunset = sunset;
	}
	public double getUv_index() {
		return uv_index;
	}
	public void setUv_index(double uv_index) {
		this.uv_index = uv_index;
	}
	
	
	
}
