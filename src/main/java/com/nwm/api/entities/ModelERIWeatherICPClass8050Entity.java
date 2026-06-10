/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelERIWeatherICPClass8050Entity extends ModelBaseEntity {
	private double panel_temp;
	private double ambient_temp;
	private double wind_speed;
	private double solar_irradiation;
	private double wind_direction;
	
	public double getPanel_temp() {
		return panel_temp;
	}
	public void setPanel_temp(double panel_temp) {
		this.panel_temp = panel_temp;
	}
	public double getAmbient_temp() {
		return ambient_temp;
	}
	public void setAmbient_temp(double ambient_temp) {
		this.ambient_temp = ambient_temp;
	}
	public double getWind_speed() {
		return wind_speed;
	}
	public void setWind_speed(double wind_speed) {
		this.wind_speed = wind_speed;
	}
	public double getSolar_irradiation() {
		return solar_irradiation;
	}
	public void setSolar_irradiation(double solar_irradiation) {
		this.solar_irradiation = solar_irradiation;
	}
	public double getWind_direction() {
		return wind_direction;
	}
	public void setWind_direction(double wind_direction) {
		this.wind_direction = wind_direction;
	}
	
}
