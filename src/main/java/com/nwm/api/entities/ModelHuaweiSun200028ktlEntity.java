/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelHuaweiSun200028ktlEntity extends ModelBaseEntity {
	private double ActivePower;
	private double ReactivePower;
	private double TotalDCInputCurrent;
	private double  TotalInputPower;
	
	private double InsulationResistance;
	private double PowerFactor;
	private double InverterStatus;
	private double CabinetTemperature;
	private double MajorFaultCode;
	private double MinorFaultCode;
	private double WarningCode;
	public double getActivePower() {
		return ActivePower;
	}
	public void setActivePower(double activePower) {
		ActivePower = activePower;
	}
	public double getReactivePower() {
		return ReactivePower;
	}
	public void setReactivePower(double reactivePower) {
		ReactivePower = reactivePower;
	}
	public double getTotalDCInputCurrent() {
		return TotalDCInputCurrent;
	}
	public void setTotalDCInputCurrent(double totalDCInputCurrent) {
		TotalDCInputCurrent = totalDCInputCurrent;
	}
	public double getTotalInputPower() {
		return TotalInputPower;
	}
	public void setTotalInputPower(double totalInputPower) {
		TotalInputPower = totalInputPower;
	}
	public double getInsulationResistance() {
		return InsulationResistance;
	}
	public void setInsulationResistance(double insulationResistance) {
		InsulationResistance = insulationResistance;
	}
	public double getPowerFactor() {
		return PowerFactor;
	}
	public void setPowerFactor(double powerFactor) {
		PowerFactor = powerFactor;
	}
	public double getInverterStatus() {
		return InverterStatus;
	}
	public void setInverterStatus(double inverterStatus) {
		InverterStatus = inverterStatus;
	}
	public double getCabinetTemperature() {
		return CabinetTemperature;
	}
	public void setCabinetTemperature(double cabinetTemperature) {
		CabinetTemperature = cabinetTemperature;
	}
	public double getMajorFaultCode() {
		return MajorFaultCode;
	}
	public void setMajorFaultCode(double majorFaultCode) {
		MajorFaultCode = majorFaultCode;
	}
	public double getMinorFaultCode() {
		return MinorFaultCode;
	}
	public void setMinorFaultCode(double minorFaultCode) {
		MinorFaultCode = minorFaultCode;
	}
	public double getWarningCode() {
		return WarningCode;
	}
	public void setWarningCode(double warningCode) {
		WarningCode = warningCode;
	}
	
	
	
	
}
