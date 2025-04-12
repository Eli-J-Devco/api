/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;



public class WeatherEntity {
	private int id_site;
	private String weather_icon;
	private String weather_description;
	private String sunrise;
	private String sunset;
	private double weather_indoor_temp;
	private String weather_indoor_temp_unit;
	private double weather_outdoor_temp;
	private String weather_outdoor_temp_unit;
	
	private String weather_time;
	private double weather_humidity;
	private String weather_humidity_unit;
	private double weather_wind;
	private String weather_wind_unit;
	private double weather_rain;
	private String weather_rain_unit;
	private double weather_snow;
	private String weather_snow_unit;
	
	
	public double getWeather_snow() {
		return weather_snow;
	}
	public void setWeather_snow(double weather_snow) {
		this.weather_snow = weather_snow;
	}
	public String getWeather_snow_unit() {
		return weather_snow_unit;
	}
	public void setWeather_snow_unit(String weather_snow_unit) {
		this.weather_snow_unit = weather_snow_unit;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public String getWeather_icon() {
		return weather_icon;
	}
	public void setWeather_icon(String weather_icon) {
		this.weather_icon = weather_icon;
	}
	public String getWeather_description() {
		return weather_description;
	}
	public void setWeather_description(String weather_description) {
		this.weather_description = weather_description;
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
	public double getWeather_indoor_temp() {
		return weather_indoor_temp;
	}
	public void setWeather_indoor_temp(double weather_indoor_temp) {
		this.weather_indoor_temp = weather_indoor_temp;
	}
	public String getWeather_indoor_temp_unit() {
		return weather_indoor_temp_unit;
	}
	public void setWeather_indoor_temp_unit(String weather_indoor_temp_unit) {
		this.weather_indoor_temp_unit = weather_indoor_temp_unit;
	}
	public double getWeather_outdoor_temp() {
		return weather_outdoor_temp;
	}
	public void setWeather_outdoor_temp(double weather_outdoor_temp) {
		this.weather_outdoor_temp = weather_outdoor_temp;
	}
	public String getWeather_outdoor_temp_unit() {
		return weather_outdoor_temp_unit;
	}
	public void setWeather_outdoor_temp_unit(String weather_outdoor_temp_unit) {
		this.weather_outdoor_temp_unit = weather_outdoor_temp_unit;
	}
	public String getWeather_time() {
		return weather_time;
	}
	public void setWeather_time(String weather_time) {
		this.weather_time = weather_time;
	}
	public double getWeather_humidity() {
		return weather_humidity;
	}
	public void setWeather_humidity(double weather_humidity) {
		this.weather_humidity = weather_humidity;
	}
	public String getWeather_humidity_unit() {
		return weather_humidity_unit;
	}
	public void setWeather_humidity_unit(String weather_humidity_unit) {
		this.weather_humidity_unit = weather_humidity_unit;
	}
	public double getWeather_wind() {
		return weather_wind;
	}
	public void setWeather_wind(double weather_wind) {
		this.weather_wind = weather_wind;
	}
	public String getWeather_wind_unit() {
		return weather_wind_unit;
	}
	public void setWeather_wind_unit(String weather_wind_unit) {
		this.weather_wind_unit = weather_wind_unit;
	}
	public double getWeather_rain() {
		return weather_rain;
	}
	public void setWeather_rain(double weather_rain) {
		this.weather_rain = weather_rain;
	}
	public String getWeather_rain_unit() {
		return weather_rain_unit;
	}
	public void setWeather_rain_unit(String weather_rain_unit) {
		this.weather_rain_unit = weather_rain_unit;
	}
	
	
}
