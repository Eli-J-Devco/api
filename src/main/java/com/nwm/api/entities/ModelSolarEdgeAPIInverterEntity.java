/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSolarEdgeAPIInverterEntity extends ModelBaseEntity {
	private double totalActivePower;
	private double dcVoltage;
	private double powerLimit;
	private double totalEnergy;
	private double temperature;
	private String inverterMode;
	private double operationMode;
	private double vL1To2;
	private double vL2To3;
	private double vL3To1;
	private double L1_acCurrent;
	private double L1_acVoltage;
	private double L1_acFrequency;
	private double L1_apparentPower;
	private double L1_activePower;
	private double L1_reactivePower;
	private double L2_acCurrent;
	private double L2_acVoltage;
	private double L2_acFrequency;
	private double L2_apparentPower;
	private double L2_activePower;
	private double L2_reactivePower;
	private double L3_acCurrent;
	private double L3_acVoltage;
	private double L3_acFrequency;
	private double L3_apparentPower;
	private double L3_activePower;
	private double L3_reactivePower;

	public double getTotalActivePower() {
		return totalActivePower;
	}

	public void setTotalActivePower(double totalActivePower) {
		this.totalActivePower = totalActivePower;
	}

	public double getDcVoltage() {
		return dcVoltage;
	}

	public void setDcVoltage(double dcVoltage) {
		this.dcVoltage = dcVoltage;
	}

	public double getPowerLimit() {
		return powerLimit;
	}

	public void setPowerLimit(double powerLimit) {
		this.powerLimit = powerLimit;
	}

	public double getTotalEnergy() {
		return totalEnergy;
	}

	public void setTotalEnergy(double totalEnergy) {
		this.totalEnergy = totalEnergy;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public String getInverterMode() {
		return inverterMode;
	}

	public void setInverterMode(String inverterMode) {
		this.inverterMode = inverterMode;
	}

	public double getOperationMode() {
		return operationMode;
	}

	public void setOperationMode(double operationMode) {
		this.operationMode = operationMode;
	}

	public double getvL1To2() {
		return vL1To2;
	}

	public void setvL1To2(double vL1To2) {
		this.vL1To2 = vL1To2;
	}

	public double getvL2To3() {
		return vL2To3;
	}

	public void setvL2To3(double vL2To3) {
		this.vL2To3 = vL2To3;
	}

	public double getvL3To1() {
		return vL3To1;
	}

	public void setvL3To1(double vL3To1) {
		this.vL3To1 = vL3To1;
	}

	public double getL1_acCurrent() {
		return L1_acCurrent;
	}

	public void setL1_acCurrent(double l1_acCurrent) {
		L1_acCurrent = l1_acCurrent;
	}

	public double getL1_acVoltage() {
		return L1_acVoltage;
	}

	public void setL1_acVoltage(double l1_acVoltage) {
		L1_acVoltage = l1_acVoltage;
	}

	public double getL1_acFrequency() {
		return L1_acFrequency;
	}

	public void setL1_acFrequency(double l1_acFrequency) {
		L1_acFrequency = l1_acFrequency;
	}

	public double getL1_apparentPower() {
		return L1_apparentPower;
	}

	public void setL1_apparentPower(double l1_apparentPower) {
		L1_apparentPower = l1_apparentPower;
	}

	public double getL1_activePower() {
		return L1_activePower;
	}

	public void setL1_activePower(double l1_activePower) {
		L1_activePower = l1_activePower;
	}

	public double getL1_reactivePower() {
		return L1_reactivePower;
	}

	public void setL1_reactivePower(double l1_reactivePower) {
		L1_reactivePower = l1_reactivePower;
	}

	public double getL2_acCurrent() {
		return L2_acCurrent;
	}

	public void setL2_acCurrent(double l2_acCurrent) {
		L2_acCurrent = l2_acCurrent;
	}

	public double getL2_acVoltage() {
		return L2_acVoltage;
	}

	public void setL2_acVoltage(double l2_acVoltage) {
		L2_acVoltage = l2_acVoltage;
	}

	public double getL2_acFrequency() {
		return L2_acFrequency;
	}

	public void setL2_acFrequency(double l2_acFrequency) {
		L2_acFrequency = l2_acFrequency;
	}

	public double getL2_apparentPower() {
		return L2_apparentPower;
	}

	public void setL2_apparentPower(double l2_apparentPower) {
		L2_apparentPower = l2_apparentPower;
	}

	public double getL2_activePower() {
		return L2_activePower;
	}

	public void setL2_activePower(double l2_activePower) {
		L2_activePower = l2_activePower;
	}

	public double getL2_reactivePower() {
		return L2_reactivePower;
	}

	public void setL2_reactivePower(double l2_reactivePower) {
		L2_reactivePower = l2_reactivePower;
	}

	public double getL3_acCurrent() {
		return L3_acCurrent;
	}

	public void setL3_acCurrent(double l3_acCurrent) {
		L3_acCurrent = l3_acCurrent;
	}

	public double getL3_acVoltage() {
		return L3_acVoltage;
	}

	public void setL3_acVoltage(double l3_acVoltage) {
		L3_acVoltage = l3_acVoltage;
	}

	public double getL3_acFrequency() {
		return L3_acFrequency;
	}

	public void setL3_acFrequency(double l3_acFrequency) {
		L3_acFrequency = l3_acFrequency;
	}

	public double getL3_apparentPower() {
		return L3_apparentPower;
	}

	public void setL3_apparentPower(double l3_apparentPower) {
		L3_apparentPower = l3_apparentPower;
	}

	public double getL3_activePower() {
		return L3_activePower;
	}

	public void setL3_activePower(double l3_activePower) {
		L3_activePower = l3_activePower;
	}

	public double getL3_reactivePower() {
		return L3_reactivePower;
	}

	public void setL3_reactivePower(double l3_reactivePower) {
		L3_reactivePower = l3_reactivePower;
	}
}
