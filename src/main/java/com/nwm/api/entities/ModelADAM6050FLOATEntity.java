/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelADAM6050FLOATEntity extends ModelBaseEntity {
	
	private double Vacuum;
	private double Pressure;
	private double LiquidLevel;
	private double LiquidTemp;
	private double Alarm;
	private double Float;
	
	public double getVacuum() {
		return Vacuum;
	}
	public void setVacuum(double vacuum) {
		Vacuum = vacuum;
	}
	public double getPressure() {
		return Pressure;
	}
	public void setPressure(double pressure) {
		Pressure = pressure;
	}
	public double getLiquidLevel() {
		return LiquidLevel;
	}
	public void setLiquidLevel(double liquidLevel) {
		LiquidLevel = liquidLevel;
	}
	public double getLiquidTemp() {
		return LiquidTemp;
	}
	public void setLiquidTemp(double liquidTemp) {
		LiquidTemp = liquidTemp;
	}
	public double getAlarm() {
		return Alarm;
	}
	public void setAlarm(double alarm) {
		Alarm = alarm;
	}
	public double getFloat() {
		return Float;
	}
	public void setFloat(double f) {
		Float = f;
	}
	
	
	

}
