/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelADAM6050TransformerSpecificEntity extends ModelBaseEntity {
	private double Vacuum;
	private double Pressure;
	private double LiquidLevel;
	private double LiquidTemperatureHigh;
	private double LiquidTemperatureWarning;
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
	public double getLiquidTemperatureHigh() {
		return LiquidTemperatureHigh;
	}
	public void setLiquidTemperatureHigh(double liquidTemperatureHigh) {
		LiquidTemperatureHigh = liquidTemperatureHigh;
	}
	public double getLiquidTemperatureWarning() {
		return LiquidTemperatureWarning;
	}
	public void setLiquidTemperatureWarning(double liquidTemperatureWarning) {
		LiquidTemperatureWarning = liquidTemperatureWarning;
	}
	
	
	
}
