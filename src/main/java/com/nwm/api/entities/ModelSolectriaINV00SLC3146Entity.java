/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSolectriaINV00SLC3146Entity extends ModelBaseEntity {
	
	private double DCVoltage;
	private double RealACPower;
	private double ACGridFrequency;
	private double ACPowerStageCurrent;
	private double L1toL2ACVoltage;
	private double L2toL3ACVoltage;
	private double L1toL3ACVoltage;
	private double PhaseSequence;
	private double ACEnergy;
	private double OnGridHours;
	private double FanOntimeHours;
	private double ACContactorCycles;
	private double SlaveID;
	private double InformativeAlarms;
	
	public double getDCVoltage() {
		return DCVoltage;
	}
	public void setDCVoltage(double dCVoltage) {
		DCVoltage = dCVoltage;
	}
	public double getRealACPower() {
		return RealACPower;
	}
	public void setRealACPower(double realACPower) {
		RealACPower = realACPower;
	}
	public double getACGridFrequency() {
		return ACGridFrequency;
	}
	public void setACGridFrequency(double aCGridFrequency) {
		ACGridFrequency = aCGridFrequency;
	}
	public double getACPowerStageCurrent() {
		return ACPowerStageCurrent;
	}
	public void setACPowerStageCurrent(double aCPowerStageCurrent) {
		ACPowerStageCurrent = aCPowerStageCurrent;
	}
	public double getL1toL2ACVoltage() {
		return L1toL2ACVoltage;
	}
	public void setL1toL2ACVoltage(double l1toL2ACVoltage) {
		L1toL2ACVoltage = l1toL2ACVoltage;
	}
	public double getL2toL3ACVoltage() {
		return L2toL3ACVoltage;
	}
	public void setL2toL3ACVoltage(double l2toL3ACVoltage) {
		L2toL3ACVoltage = l2toL3ACVoltage;
	}
	public double getL1toL3ACVoltage() {
		return L1toL3ACVoltage;
	}
	public void setL1toL3ACVoltage(double l1toL3ACVoltage) {
		L1toL3ACVoltage = l1toL3ACVoltage;
	}
	public double getPhaseSequence() {
		return PhaseSequence;
	}
	public void setPhaseSequence(double phaseSequence) {
		PhaseSequence = phaseSequence;
	}
	public double getACEnergy() {
		return ACEnergy;
	}
	public void setACEnergy(double aCEnergy) {
		ACEnergy = aCEnergy;
	}
	public double getOnGridHours() {
		return OnGridHours;
	}
	public void setOnGridHours(double onGridHours) {
		OnGridHours = onGridHours;
	}
	public double getFanOntimeHours() {
		return FanOntimeHours;
	}
	public void setFanOntimeHours(double fanOntimeHours) {
		FanOntimeHours = fanOntimeHours;
	}
	public double getACContactorCycles() {
		return ACContactorCycles;
	}
	public void setACContactorCycles(double aCContactorCycles) {
		ACContactorCycles = aCContactorCycles;
	}
	public double getSlaveID() {
		return SlaveID;
	}
	public void setSlaveID(double slaveID) {
		SlaveID = slaveID;
	}
	public double getInformativeAlarms() {
		return InformativeAlarms;
	}
	public void setInformativeAlarms(double informativeAlarms) {
		InformativeAlarms = informativeAlarms;
	}
}
