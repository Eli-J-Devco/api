/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelABBCentralInverterPVS800570630kWBEntity extends ModelBaseEntity {
	
	private double FailSafeMode;
	private double OperationMode;
	private double Controlzeromode;
	private double CutOfftime;
	private double StatusCode;
	private double Inverterstatus;
	private double ACVoltage;
	private double InverterTemperature;
	private double ActivePower;
	private double kWhCounterLow;
	private double kWhCounterHigh;
	private double EnergyTotal;
	private double TotalInputPower;
	private double DCVoltage;
	private double DCCurrent;
	public double getFailSafeMode() {
		return FailSafeMode;
	}
	public void setFailSafeMode(double failSafeMode) {
		FailSafeMode = failSafeMode;
	}
	public double getOperationMode() {
		return OperationMode;
	}
	public void setOperationMode(double operationMode) {
		OperationMode = operationMode;
	}
	public double getControlzeromode() {
		return Controlzeromode;
	}
	public void setControlzeromode(double controlzeromode) {
		Controlzeromode = controlzeromode;
	}
	public double getCutOfftime() {
		return CutOfftime;
	}
	public void setCutOfftime(double cutOfftime) {
		CutOfftime = cutOfftime;
	}
	public double getStatusCode() {
		return StatusCode;
	}
	public void setStatusCode(double statusCode) {
		StatusCode = statusCode;
	}
	public double getInverterstatus() {
		return Inverterstatus;
	}
	public void setInverterstatus(double inverterstatus) {
		Inverterstatus = inverterstatus;
	}
	public double getACVoltage() {
		return ACVoltage;
	}
	public void setACVoltage(double aCVoltage) {
		ACVoltage = aCVoltage;
	}
	public double getInverterTemperature() {
		return InverterTemperature;
	}
	public void setInverterTemperature(double inverterTemperature) {
		InverterTemperature = inverterTemperature;
	}
	public double getActivePower() {
		return ActivePower;
	}
	public void setActivePower(double activePower) {
		ActivePower = activePower;
	}
	public double getkWhCounterLow() {
		return kWhCounterLow;
	}
	public void setkWhCounterLow(double kWhCounterLow) {
		this.kWhCounterLow = kWhCounterLow;
	}
	public double getkWhCounterHigh() {
		return kWhCounterHigh;
	}
	public void setkWhCounterHigh(double kWhCounterHigh) {
		this.kWhCounterHigh = kWhCounterHigh;
	}
	public double getEnergyTotal() {
		return EnergyTotal;
	}
	public void setEnergyTotal(double energyTotal) {
		EnergyTotal = energyTotal;
	}
	public double getTotalInputPower() {
		return TotalInputPower;
	}
	public void setTotalInputPower(double totalInputPower) {
		TotalInputPower = totalInputPower;
	}
	public double getDCVoltage() {
		return DCVoltage;
	}
	public void setDCVoltage(double dCVoltage) {
		DCVoltage = dCVoltage;
	}
	public double getDCCurrent() {
		return DCCurrent;
	}
	public void setDCCurrent(double dCCurrent) {
		DCCurrent = dCCurrent;
	}
	
	
	
}
