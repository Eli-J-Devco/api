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
	private int totalFaultCode1;
	private int totalFaultCode2;
	private int totalFaultCode3;
	private int totalFaultCode4;
	private int totalFaultCode5;
	
	
	
	public int getTotalFaultCode1() {
		return totalFaultCode1;
	}
	public void setTotalFaultCode1(int totalFaultCode1) {
		this.totalFaultCode1 = totalFaultCode1;
	}
	public int getTotalFaultCode2() {
		return totalFaultCode2;
	}
	public void setTotalFaultCode2(int totalFaultCode2) {
		this.totalFaultCode2 = totalFaultCode2;
	}
	public int getTotalFaultCode3() {
		return totalFaultCode3;
	}
	public void setTotalFaultCode3(int totalFaultCode3) {
		this.totalFaultCode3 = totalFaultCode3;
	}
	public int getTotalFaultCode4() {
		return totalFaultCode4;
	}
	public void setTotalFaultCode4(int totalFaultCode4) {
		this.totalFaultCode4 = totalFaultCode4;
	}
	public int getTotalFaultCode5() {
		return totalFaultCode5;
	}
	public void setTotalFaultCode5(int totalFaultCode5) {
		this.totalFaultCode5 = totalFaultCode5;
	}
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
