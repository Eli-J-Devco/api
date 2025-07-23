/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelEatonNova6RecloserEntity extends ModelBaseEntity {
	private double CurrentPhase1;
	private double CurrentPhase2;
	private double CurrentPhase3;
	private double VoltagePhase1;
	private double VoltagePhase2;
	private double VoltagePhase3;
	private double PowerFactorPhase1;
	private double PowerFactorPhase2;
	private double PowerFactorPhase3;
	private double ApparentPowerPhase1;
	private double ApparentPowerPhase2;
	private double ApparentPowerPhase3;
	private double RealPowerPhase1;
	private double RealPowerPhase2;
	private double RealPowerPhase3;
	private double ReactivePowerPhase1;
	private double ReactivePowerPhase2;
	private double ReactivePowerPhase3;
	private double LineFrequency;
	private double DemandCurrentPhase1;
	private double DemandCurrentPhase2;
	private double DemandCurrentPhase3;
	private double BatteryVoltage;
	private double BatteryCurrent;
	private double TotalTripCounter;
	private double RecloserClosed;
	private double RecloserOpened;
	private double ControlisLocked;
	private double ControlOK;
	private double SystemAlarm;
	private double PhaseATrip;
	private double PhaseBTrip;
	private double PhaseCTrip;
	private double ACPowerAlarm;
	private double BatteryAlarm;
	private double PhaseOvercurrentAlarm;
	private double GroundOvercurrentAlarm;
	private double NegativeSequenceOCAlarm;
	private double Close;
	private double Open;
	public double getCurrentPhase1() {
		return CurrentPhase1;
	}
	public void setCurrentPhase1(double currentPhase1) {
		CurrentPhase1 = currentPhase1;
	}
	public double getCurrentPhase2() {
		return CurrentPhase2;
	}
	public void setCurrentPhase2(double currentPhase2) {
		CurrentPhase2 = currentPhase2;
	}
	public double getCurrentPhase3() {
		return CurrentPhase3;
	}
	public void setCurrentPhase3(double currentPhase3) {
		CurrentPhase3 = currentPhase3;
	}
	public double getVoltagePhase1() {
		return VoltagePhase1;
	}
	public void setVoltagePhase1(double voltagePhase1) {
		VoltagePhase1 = voltagePhase1;
	}
	public double getVoltagePhase2() {
		return VoltagePhase2;
	}
	public void setVoltagePhase2(double voltagePhase2) {
		VoltagePhase2 = voltagePhase2;
	}
	public double getVoltagePhase3() {
		return VoltagePhase3;
	}
	public void setVoltagePhase3(double voltagePhase3) {
		VoltagePhase3 = voltagePhase3;
	}
	public double getPowerFactorPhase1() {
		return PowerFactorPhase1;
	}
	public void setPowerFactorPhase1(double powerFactorPhase1) {
		PowerFactorPhase1 = powerFactorPhase1;
	}
	public double getPowerFactorPhase2() {
		return PowerFactorPhase2;
	}
	public void setPowerFactorPhase2(double powerFactorPhase2) {
		PowerFactorPhase2 = powerFactorPhase2;
	}
	public double getPowerFactorPhase3() {
		return PowerFactorPhase3;
	}
	public void setPowerFactorPhase3(double powerFactorPhase3) {
		PowerFactorPhase3 = powerFactorPhase3;
	}
	public double getApparentPowerPhase1() {
		return ApparentPowerPhase1;
	}
	public void setApparentPowerPhase1(double apparentPowerPhase1) {
		ApparentPowerPhase1 = apparentPowerPhase1;
	}
	public double getApparentPowerPhase2() {
		return ApparentPowerPhase2;
	}
	public void setApparentPowerPhase2(double apparentPowerPhase2) {
		ApparentPowerPhase2 = apparentPowerPhase2;
	}
	public double getApparentPowerPhase3() {
		return ApparentPowerPhase3;
	}
	public void setApparentPowerPhase3(double apparentPowerPhase3) {
		ApparentPowerPhase3 = apparentPowerPhase3;
	}
	public double getRealPowerPhase1() {
		return RealPowerPhase1;
	}
	public void setRealPowerPhase1(double realPowerPhase1) {
		RealPowerPhase1 = realPowerPhase1;
	}
	public double getRealPowerPhase2() {
		return RealPowerPhase2;
	}
	public void setRealPowerPhase2(double realPowerPhase2) {
		RealPowerPhase2 = realPowerPhase2;
	}
	public double getRealPowerPhase3() {
		return RealPowerPhase3;
	}
	public void setRealPowerPhase3(double realPowerPhase3) {
		RealPowerPhase3 = realPowerPhase3;
	}
	public double getReactivePowerPhase1() {
		return ReactivePowerPhase1;
	}
	public void setReactivePowerPhase1(double reactivePowerPhase1) {
		ReactivePowerPhase1 = reactivePowerPhase1;
	}
	public double getReactivePowerPhase2() {
		return ReactivePowerPhase2;
	}
	public void setReactivePowerPhase2(double reactivePowerPhase2) {
		ReactivePowerPhase2 = reactivePowerPhase2;
	}
	public double getReactivePowerPhase3() {
		return ReactivePowerPhase3;
	}
	public void setReactivePowerPhase3(double reactivePowerPhase3) {
		ReactivePowerPhase3 = reactivePowerPhase3;
	}
	public double getLineFrequency() {
		return LineFrequency;
	}
	public void setLineFrequency(double lineFrequency) {
		LineFrequency = lineFrequency;
	}
	public double getDemandCurrentPhase1() {
		return DemandCurrentPhase1;
	}
	public void setDemandCurrentPhase1(double demandCurrentPhase1) {
		DemandCurrentPhase1 = demandCurrentPhase1;
	}
	public double getDemandCurrentPhase2() {
		return DemandCurrentPhase2;
	}
	public void setDemandCurrentPhase2(double demandCurrentPhase2) {
		DemandCurrentPhase2 = demandCurrentPhase2;
	}
	public double getDemandCurrentPhase3() {
		return DemandCurrentPhase3;
	}
	public void setDemandCurrentPhase3(double demandCurrentPhase3) {
		DemandCurrentPhase3 = demandCurrentPhase3;
	}
	public double getBatteryVoltage() {
		return BatteryVoltage;
	}
	public void setBatteryVoltage(double batteryVoltage) {
		BatteryVoltage = batteryVoltage;
	}
	public double getBatteryCurrent() {
		return BatteryCurrent;
	}
	public void setBatteryCurrent(double batteryCurrent) {
		BatteryCurrent = batteryCurrent;
	}
	public double getTotalTripCounter() {
		return TotalTripCounter;
	}
	public void setTotalTripCounter(double totalTripCounter) {
		TotalTripCounter = totalTripCounter;
	}
	public double getRecloserClosed() {
		return RecloserClosed;
	}
	public void setRecloserClosed(double recloserClosed) {
		RecloserClosed = recloserClosed;
	}
	public double getRecloserOpened() {
		return RecloserOpened;
	}
	public void setRecloserOpened(double recloserOpened) {
		RecloserOpened = recloserOpened;
	}
	public double getControlisLocked() {
		return ControlisLocked;
	}
	public void setControlisLocked(double controlisLocked) {
		ControlisLocked = controlisLocked;
	}
	public double getControlOK() {
		return ControlOK;
	}
	public void setControlOK(double controlOK) {
		ControlOK = controlOK;
	}
	public double getSystemAlarm() {
		return SystemAlarm;
	}
	public void setSystemAlarm(double systemAlarm) {
		SystemAlarm = systemAlarm;
	}
	public double getPhaseATrip() {
		return PhaseATrip;
	}
	public void setPhaseATrip(double phaseATrip) {
		PhaseATrip = phaseATrip;
	}
	public double getPhaseBTrip() {
		return PhaseBTrip;
	}
	public void setPhaseBTrip(double phaseBTrip) {
		PhaseBTrip = phaseBTrip;
	}
	public double getPhaseCTrip() {
		return PhaseCTrip;
	}
	public void setPhaseCTrip(double phaseCTrip) {
		PhaseCTrip = phaseCTrip;
	}
	public double getACPowerAlarm() {
		return ACPowerAlarm;
	}
	public void setACPowerAlarm(double aCPowerAlarm) {
		ACPowerAlarm = aCPowerAlarm;
	}
	public double getBatteryAlarm() {
		return BatteryAlarm;
	}
	public void setBatteryAlarm(double batteryAlarm) {
		BatteryAlarm = batteryAlarm;
	}
	public double getPhaseOvercurrentAlarm() {
		return PhaseOvercurrentAlarm;
	}
	public void setPhaseOvercurrentAlarm(double phaseOvercurrentAlarm) {
		PhaseOvercurrentAlarm = phaseOvercurrentAlarm;
	}
	public double getGroundOvercurrentAlarm() {
		return GroundOvercurrentAlarm;
	}
	public void setGroundOvercurrentAlarm(double groundOvercurrentAlarm) {
		GroundOvercurrentAlarm = groundOvercurrentAlarm;
	}
	public double getNegativeSequenceOCAlarm() {
		return NegativeSequenceOCAlarm;
	}
	public void setNegativeSequenceOCAlarm(double negativeSequenceOCAlarm) {
		NegativeSequenceOCAlarm = negativeSequenceOCAlarm;
	}
	public double getClose() {
		return Close;
	}
	public void setClose(double close) {
		Close = close;
	}
	public double getOpen() {
		return Open;
	}
	public void setOpen(double open) {
		Open = open;
	}
	
	
}
