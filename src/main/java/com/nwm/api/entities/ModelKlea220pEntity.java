/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelKlea220pEntity extends ModelBaseEntity {
	private double TotalEnergy;
	private double TotalActivePower;
	private double TotalReactivePower;
	private double TotalApparentPower;
	private double GridFrequency;
	private double NeutralCurrent;
	private double TotalCurrent;
	private double TotalPowerFactor;
	private double VoltageLN3phases;
	private double VoltageLL3phases;
	private double ActivePowerPhaseA;
	private double ActivePowerPhaseB;
	private double ActivePowerPhaseC;
	private double ReactivePowerPhaseA;
	private double ReactivePowerPhaseB;
	private double ReactivePowerPhaseC;
	private double ApparentPowerPhaseA;
	private double ApparentPowerPhaseB;
	private double ApparentPowerPhaseC;
	private double CosphiPhaseA;
	private double CosphiPhaseB;
	private double CosphiPhaseC;
	private double PowerFactorPhaseA;
	private double PowerFactorPhaseB;
	private double PowerFactorPhaseC;
	private double VoltagePhaseAB;
	private double VoltagePhaseBC;
	private double VoltagePhaseCA;
	private double VoltagePhaseAN;
	private double VoltagePhaseBN;
	private double VoltagePhaseCN;
	private double CurrentPhaseA;
	private double CurrentPhaseB;
	private double CurrentPhaseC;
	
	public double getTotalEnergy() {
		return TotalEnergy;
	}
	public void setTotalEnergy(double totalEnergy) {
		TotalEnergy = totalEnergy;
	}
	public double getTotalActivePower() {
		return TotalActivePower;
	}
	public void setTotalActivePower(double totalActivePower) {
		TotalActivePower = totalActivePower;
	}
	public double getTotalReactivePower() {
		return TotalReactivePower;
	}
	public void setTotalReactivePower(double totalReactivePower) {
		TotalReactivePower = totalReactivePower;
	}
	public double getTotalApparentPower() {
		return TotalApparentPower;
	}
	public void setTotalApparentPower(double totalApparentPower) {
		TotalApparentPower = totalApparentPower;
	}
	public double getGridFrequency() {
		return GridFrequency;
	}
	public void setGridFrequency(double gridFrequency) {
		GridFrequency = gridFrequency;
	}
	public double getNeutralCurrent() {
		return NeutralCurrent;
	}
	public void setNeutralCurrent(double neutralCurrent) {
		NeutralCurrent = neutralCurrent;
	}
	public double getTotalCurrent() {
		return TotalCurrent;
	}
	public void setTotalCurrent(double totalCurrent) {
		TotalCurrent = totalCurrent;
	}
	public double getTotalPowerFactor() {
		return TotalPowerFactor;
	}
	public void setTotalPowerFactor(double totalPowerFactor) {
		TotalPowerFactor = totalPowerFactor;
	}
	public double getVoltageLN3phases() {
		return VoltageLN3phases;
	}
	public void setVoltageLN3phases(double voltageLN3phases) {
		VoltageLN3phases = voltageLN3phases;
	}
	public double getVoltageLL3phases() {
		return VoltageLL3phases;
	}
	public void setVoltageLL3phases(double voltageLL3phases) {
		VoltageLL3phases = voltageLL3phases;
	}
	public double getActivePowerPhaseA() {
		return ActivePowerPhaseA;
	}
	public void setActivePowerPhaseA(double activePowerPhaseA) {
		ActivePowerPhaseA = activePowerPhaseA;
	}
	public double getActivePowerPhaseB() {
		return ActivePowerPhaseB;
	}
	public void setActivePowerPhaseB(double activePowerPhaseB) {
		ActivePowerPhaseB = activePowerPhaseB;
	}
	public double getActivePowerPhaseC() {
		return ActivePowerPhaseC;
	}
	public void setActivePowerPhaseC(double activePowerPhaseC) {
		ActivePowerPhaseC = activePowerPhaseC;
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
	public double getCosphiPhaseA() {
		return CosphiPhaseA;
	}
	public void setCosphiPhaseA(double cosphiPhaseA) {
		CosphiPhaseA = cosphiPhaseA;
	}
	public double getCosphiPhaseB() {
		return CosphiPhaseB;
	}
	public void setCosphiPhaseB(double cosphiPhaseB) {
		CosphiPhaseB = cosphiPhaseB;
	}
	public double getCosphiPhaseC() {
		return CosphiPhaseC;
	}
	public void setCosphiPhaseC(double cosphiPhaseC) {
		CosphiPhaseC = cosphiPhaseC;
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
	
	
}
