/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelHoneywellEMON3200Entity extends ModelBaseEntity {
	private double AuxilaryPulseInput1;
	private double AuxilaryPulseInput2;
	private double EnergyDelivered;
	private double EnergyReceived;
	private double ReactiveEnergyDelivered;
	private double ReactiveEnergyReceived;
	private double RealPower;
	private double ReactivePower;
	private double ApparentPower;
	private double PowerFactor;
	private double CurrentTotal;
	private double CurrentAverage;
	private double VoltageLineNeutral;
	private double VoltageLineLine;
	private double Frequency;
	private double PhaseAngle;
	private double RealPowerPhaseA;
	private double RealPowerPhaseB;
	private double RealPowerPhaseC;
	private double ReactivePowerPhaseA;
	private double ReactivePowerPhaseB;
	private double ReactivePowerPhaseC;
	private double ApparentPowerPhaseA;
	private double ApparentPowerPhaseB;
	private double ApparentPowerPhaseC;
	private double PowerFactorPhaseA;
	private double PowerFactorPhaseB;
	private double PowerFactorPhaseC;
	private double CurrentPhaseA;
	private double CurrentPhaseB;
	private double CurrentPhaseC;
	private double VoltagePhaseAN;
	private double VoltagePhaseBN;
	private double VoltagePhaseCN;
	private double VoltagePhaseAB;
	private double VoltagePhaseBC;
	private double VoltagePhaseCA;
	private double PhaseAngleA;
	private double PhaseAngleB;
	private double PhaseAngleC;
	
	public double getAuxilaryPulseInput1() {
		return AuxilaryPulseInput1;
	}
	public void setAuxilaryPulseInput1(double auxilaryPulseInput1) {
		AuxilaryPulseInput1 = auxilaryPulseInput1;
	}
	public double getAuxilaryPulseInput2() {
		return AuxilaryPulseInput2;
	}
	public void setAuxilaryPulseInput2(double auxilaryPulseInput2) {
		AuxilaryPulseInput2 = auxilaryPulseInput2;
	}
	public double getEnergyDelivered() {
		return EnergyDelivered;
	}
	public void setEnergyDelivered(double energyDelivered) {
		EnergyDelivered = energyDelivered;
	}
	public double getEnergyReceived() {
		return EnergyReceived;
	}
	public void setEnergyReceived(double energyReceived) {
		EnergyReceived = energyReceived;
	}
	public double getReactiveEnergyDelivered() {
		return ReactiveEnergyDelivered;
	}
	public void setReactiveEnergyDelivered(double reactiveEnergyDelivered) {
		ReactiveEnergyDelivered = reactiveEnergyDelivered;
	}
	public double getReactiveEnergyReceived() {
		return ReactiveEnergyReceived;
	}
	public void setReactiveEnergyReceived(double reactiveEnergyReceived) {
		ReactiveEnergyReceived = reactiveEnergyReceived;
	}
	public double getRealPower() {
		return RealPower;
	}
	public void setRealPower(double realPower) {
		RealPower = realPower;
	}
	public double getReactivePower() {
		return ReactivePower;
	}
	public void setReactivePower(double reactivePower) {
		ReactivePower = reactivePower;
	}
	public double getApparentPower() {
		return ApparentPower;
	}
	public void setApparentPower(double apparentPower) {
		ApparentPower = apparentPower;
	}
	public double getPowerFactor() {
		return PowerFactor;
	}
	public void setPowerFactor(double powerFactor) {
		PowerFactor = powerFactor;
	}
	public double getCurrentTotal() {
		return CurrentTotal;
	}
	public void setCurrentTotal(double currentTotal) {
		CurrentTotal = currentTotal;
	}
	public double getCurrentAverage() {
		return CurrentAverage;
	}
	public void setCurrentAverage(double currentAverage) {
		CurrentAverage = currentAverage;
	}
	public double getVoltageLineNeutral() {
		return VoltageLineNeutral;
	}
	public void setVoltageLineNeutral(double voltageLineNeutral) {
		VoltageLineNeutral = voltageLineNeutral;
	}
	public double getVoltageLineLine() {
		return VoltageLineLine;
	}
	public void setVoltageLineLine(double voltageLineLine) {
		VoltageLineLine = voltageLineLine;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getPhaseAngle() {
		return PhaseAngle;
	}
	public void setPhaseAngle(double phaseAngle) {
		PhaseAngle = phaseAngle;
	}
	public double getRealPowerPhaseA() {
		return RealPowerPhaseA;
	}
	public void setRealPowerPhaseA(double realPowerPhaseA) {
		RealPowerPhaseA = realPowerPhaseA;
	}
	public double getRealPowerPhaseB() {
		return RealPowerPhaseB;
	}
	public void setRealPowerPhaseB(double realPowerPhaseB) {
		RealPowerPhaseB = realPowerPhaseB;
	}
	public double getRealPowerPhaseC() {
		return RealPowerPhaseC;
	}
	public void setRealPowerPhaseC(double realPowerPhaseC) {
		RealPowerPhaseC = realPowerPhaseC;
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
	public double getCurrentPhaseA() {
		return CurrentPhaseA;
	}
	public void setCurrentPhaseA(double currentPhaseA) {
		CurrentPhaseA = currentPhaseA;
	}
	public double getCurrentPhaseB() {
		return CurrentPhaseB;
	}
	public void setCurrentPhaseB(double currentPhaseB) {
		CurrentPhaseB = currentPhaseB;
	}
	public double getCurrentPhaseC() {
		return CurrentPhaseC;
	}
	public void setCurrentPhaseC(double currentPhaseC) {
		CurrentPhaseC = currentPhaseC;
	}
	public double getVoltagePhaseAN() {
		return VoltagePhaseAN;
	}
	public void setVoltagePhaseAN(double voltagePhaseAN) {
		VoltagePhaseAN = voltagePhaseAN;
	}
	public double getVoltagePhaseBN() {
		return VoltagePhaseBN;
	}
	public void setVoltagePhaseBN(double voltagePhaseBN) {
		VoltagePhaseBN = voltagePhaseBN;
	}
	public double getVoltagePhaseCN() {
		return VoltagePhaseCN;
	}
	public void setVoltagePhaseCN(double voltagePhaseCN) {
		VoltagePhaseCN = voltagePhaseCN;
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
	public double getPhaseAngleA() {
		return PhaseAngleA;
	}
	public void setPhaseAngleA(double phaseAngleA) {
		PhaseAngleA = phaseAngleA;
	}
	public double getPhaseAngleB() {
		return PhaseAngleB;
	}
	public void setPhaseAngleB(double phaseAngleB) {
		PhaseAngleB = phaseAngleB;
	}
	public double getPhaseAngleC() {
		return PhaseAngleC;
	}
	public void setPhaseAngleC(double phaseAngleC) {
		PhaseAngleC = phaseAngleC;
	}
	
}
