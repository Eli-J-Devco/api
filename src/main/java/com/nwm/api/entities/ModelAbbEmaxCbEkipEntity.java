/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelAbbEmaxCbEkipEntity extends ModelBaseEntity {
	private double PhaseACurrent;
	private double PhaseBCurrent;	
	private double PhaseCCurrent;	
	private double NeutralCurrent;	
	private double PowerFactorPhaseA;
	private double PowerFactorPhaseB;
	private double PowerFactorPhaseC;	
	private double PowerFactor3PhaseTotal;
	private double VoltagePhaseA;
	private double VoltagePhaseB;
	private double VoltagePhaseC;	
	private double VoltagePhaseAB;
	private double VoltagePhaseBC;
	private double VoltagePhaseCA;
	private double ActivePowerPhaseA;
	private double ActivePowerPhaseB;
	private double ActivePowerPhaseC;
	private double ActivePower3PhaseTotal;	
	private double ReactivePowerPhaseA;
	private double ReactivePowerPhaseB;
	private double ReactivePowerPhaseC;	
	private double ReactivePower3PhaseTotal;	
	private double ApparentPowerPhaseA;
	private double ApparentPowerPhaseB;
	private double ApparentPowerPhaseC;
	private double ApparentPower3PhaseTotal;
	private double Frequency;
	private double PeakFactorPhaseA;
	private double PeakFactorPhaseB;
	private double PeakFactorPhaseC;
	private double PeakFactorNeutral;
	private double THDPhaseA;
	private double THDPhaseB;
	private double THDPhaseC;
	private double THDNeutral;
	private double TotalActiveEnergy;
	private double TotalReactiveEnergy;
	private double TotalApparentEnergy;
	private double Command;
	
	public double getPhaseACurrent() {
		return PhaseACurrent;
	}
	public void setPhaseACurrent(double phaseACurrent) {
		PhaseACurrent = phaseACurrent;
	}
	public double getPhaseBCurrent() {
		return PhaseBCurrent;
	}
	public void setPhaseBCurrent(double phaseBCurrent) {
		PhaseBCurrent = phaseBCurrent;
	}
	public double getPhaseCCurrent() {
		return PhaseCCurrent;
	}
	public void setPhaseCCurrent(double phaseCCurrent) {
		PhaseCCurrent = phaseCCurrent;
	}
	public double getNeutralCurrent() {
		return NeutralCurrent;
	}
	public void setNeutralCurrent(double neutralCurrent) {
		NeutralCurrent = neutralCurrent;
	}
	public double getPowerFactorPhaseA() {
		return PowerFactorPhaseA;
	}
	public void setPowerFactorPhaseA(double powerFactorPhaseA) {
		PowerFactorPhaseA = powerFactorPhaseA;
	}
	public double getPowerFactorPhaseB() {
		return PowerFactorPhaseB;
	}
	public void setPowerFactorPhaseB(double powerFactorPhaseB) {
		PowerFactorPhaseB = powerFactorPhaseB;
	}
	public double getPowerFactorPhaseC() {
		return PowerFactorPhaseC;
	}
	public void setPowerFactorPhaseC(double powerFactorPhaseC) {
		PowerFactorPhaseC = powerFactorPhaseC;
	}
	public double getPowerFactor3PhaseTotal() {
		return PowerFactor3PhaseTotal;
	}
	public void setPowerFactor3PhaseTotal(double powerFactor3PhaseTotal) {
		PowerFactor3PhaseTotal = powerFactor3PhaseTotal;
	}
	public double getVoltagePhaseA() {
		return VoltagePhaseA;
	}
	public void setVoltagePhaseA(double voltagePhaseA) {
		VoltagePhaseA = voltagePhaseA;
	}
	public double getVoltagePhaseB() {
		return VoltagePhaseB;
	}
	public void setVoltagePhaseB(double voltagePhaseB) {
		VoltagePhaseB = voltagePhaseB;
	}
	public double getVoltagePhaseC() {
		return VoltagePhaseC;
	}
	public void setVoltagePhaseC(double voltagePhaseC) {
		VoltagePhaseC = voltagePhaseC;
	}
	public double getVoltagePhaseAB() {
		return VoltagePhaseAB;
	}
	public void setVoltagePhaseAB(double voltagePhaseAB) {
		VoltagePhaseAB = voltagePhaseAB;
	}
	public double getVoltagePhaseBC() {
		return VoltagePhaseBC;
	}
	public void setVoltagePhaseBC(double voltagePhaseBC) {
		VoltagePhaseBC = voltagePhaseBC;
	}
	public double getVoltagePhaseCA() {
		return VoltagePhaseCA;
	}
	public void setVoltagePhaseCA(double voltagePhaseCA) {
		VoltagePhaseCA = voltagePhaseCA;
	}
	public double getActivePowerPhaseA() {
		return ActivePowerPhaseA;
	}
	public void setActivePowerPhaseA(double acitvePowerPhaseA) {
		ActivePowerPhaseA = acitvePowerPhaseA;
	}
	public double getActivePowerPhaseB() {
		return ActivePowerPhaseB;
	}
	public void setActivePowerPhaseB(double acitvePowerPhaseB) {
		ActivePowerPhaseB = acitvePowerPhaseB;
	}
	public double getActivePowerPhaseC() {
		return ActivePowerPhaseC;
	}
	public void setActivePowerPhaseC(double acitvePowerPhaseC) {
		ActivePowerPhaseC = acitvePowerPhaseC;
	}
	public double getActivePower3PhaseTotal() {
		return ActivePower3PhaseTotal;
	}
	public void setActivePower3PhaseTotal(double activePower3PhaseTotal) {
		ActivePower3PhaseTotal = activePower3PhaseTotal;
	}
	public double getReactivePowerPhaseA() {
		return ReactivePowerPhaseA;
	}
	public void setReactivePowerPhaseA(double reactivePowerPhaseA) {
		ReactivePowerPhaseA = reactivePowerPhaseA;
	}
	public double getReactivePowerPhaseB() {
		return ReactivePowerPhaseB;
	}
	public void setReactivePowerPhaseB(double reactivePowerPhaseB) {
		ReactivePowerPhaseB = reactivePowerPhaseB;
	}
	public double getReactivePowerPhaseC() {
		return ReactivePowerPhaseC;
	}
	public void setReactivePowerPhaseC(double reactivePowerPhaseC) {
		ReactivePowerPhaseC = reactivePowerPhaseC;
	}
	public double getReactivePower3PhaseTotal() {
		return ReactivePower3PhaseTotal;
	}
	public void setReactivePower3PhaseTotal(double reactivePower3PhaseTotal) {
		ReactivePower3PhaseTotal = reactivePower3PhaseTotal;
	}
	public double getApparentPowerPhaseA() {
		return ApparentPowerPhaseA;
	}
	public void setApparentPowerPhaseA(double apparentPowerPhaseA) {
		ApparentPowerPhaseA = apparentPowerPhaseA;
	}
	public double getApparentPowerPhaseB() {
		return ApparentPowerPhaseB;
	}
	public void setApparentPowerPhaseB(double apparentPowerPhaseB) {
		ApparentPowerPhaseB = apparentPowerPhaseB;
	}
	public double getApparentPowerPhaseC() {
		return ApparentPowerPhaseC;
	}
	public void setApparentPowerPhaseC(double apparentPowerPhaseC) {
		ApparentPowerPhaseC = apparentPowerPhaseC;
	}
	public double getApparentPower3PhaseTotal() {
		return ApparentPower3PhaseTotal;
	}
	public void setApparentPower3PhaseTotal(double apparentPower3PhaseTotal) {
		ApparentPower3PhaseTotal = apparentPower3PhaseTotal;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getPeakFactorPhaseA() {
		return PeakFactorPhaseA;
	}
	public void setPeakFactorPhaseA(double peakFactorPhaseA) {
		PeakFactorPhaseA = peakFactorPhaseA;
	}
	public double getPeakFactorPhaseB() {
		return PeakFactorPhaseB;
	}
	public void setPeakFactorPhaseB(double peakFactorPhaseB) {
		PeakFactorPhaseB = peakFactorPhaseB;
	}
	public double getPeakFactorPhaseC() {
		return PeakFactorPhaseC;
	}
	public void setPeakFactorPhaseC(double peakFactorPhaseC) {
		PeakFactorPhaseC = peakFactorPhaseC;
	}
	public double getPeakFactorNeutral() {
		return PeakFactorNeutral;
	}
	public void setPeakFactorNeutral(double peakFactorNeutral) {
		PeakFactorNeutral = peakFactorNeutral;
	}
	public double getTHDPhaseA() {
		return THDPhaseA;
	}
	public void setTHDPhaseA(double tHDPhaseA) {
		THDPhaseA = tHDPhaseA;
	}
	public double getTHDPhaseB() {
		return THDPhaseB;
	}
	public void setTHDPhaseB(double tHDPhaseB) {
		THDPhaseB = tHDPhaseB;
	}
	public double getTHDPhaseC() {
		return THDPhaseC;
	}
	public void setTHDPhaseC(double tHDPhaseC) {
		THDPhaseC = tHDPhaseC;
	}
	public double getTHDNeutral() {
		return THDNeutral;
	}
	public void setTHDNeutral(double tHDNeutral) {
		THDNeutral = tHDNeutral;
	}
	public double getTotalActiveEnergy() {
		return TotalActiveEnergy;
	}
	public void setTotalActiveEnergy(double totalActiveEnergy) {
		TotalActiveEnergy = totalActiveEnergy;
	}
	public double getTotalReactiveEnergy() {
		return TotalReactiveEnergy;
	}
	public void setTotalReactiveEnergy(double totalReactiveEnergy) {
		TotalReactiveEnergy = totalReactiveEnergy;
	}
	public double getTotalApparentEnergy() {
		return TotalApparentEnergy;
	}
	public void setTotalApparentEnergy(double totalApparentEnergy) {
		TotalApparentEnergy = totalApparentEnergy;
	}
	public double getCommand() {
		return Command;
	}
	public void setCommand(double command) {
		Command = command;
	}
}
