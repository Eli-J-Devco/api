/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelXantrexGT100250500Entity extends ModelBaseEntity {
	private double VAB;
	private double VBC;
	private double VCA;
	private double CurrentA;
	private double CurrentB;
	private double CurrentC;
	private double ReadPower;
	private double PVVoltage;
	private double PVCurrent;
	private double PVPower;
	private double GridFrequency;
	private double SystemState;
	private double GoalState;
	private double FaultCode;
	private double AccumulatedEnergy;
	private double RMatrixTemp;
	private double LMatrixTemp;
	private double IntakeAirTemperature;
	private int totalFaultCode;
	
	
	
	public int getTotalFaultCode() {
		return totalFaultCode;
	}
	public void setTotalFaultCode(int totalFaultCode) {
		this.totalFaultCode = totalFaultCode;
	}
	public double getVAB() {
		return VAB;
	}
	public void setVAB(double vAB) {
		VAB = vAB;
	}
	public double getVBC() {
		return VBC;
	}
	public void setVBC(double vBC) {
		VBC = vBC;
	}
	public double getVCA() {
		return VCA;
	}
	public void setVCA(double vCA) {
		VCA = vCA;
	}
	public double getCurrentA() {
		return CurrentA;
	}
	public void setCurrentA(double currentA) {
		CurrentA = currentA;
	}
	public double getCurrentB() {
		return CurrentB;
	}
	public void setCurrentB(double currentB) {
		CurrentB = currentB;
	}
	public double getCurrentC() {
		return CurrentC;
	}
	public void setCurrentC(double currentC) {
		CurrentC = currentC;
	}
	public double getReadPower() {
		return ReadPower;
	}
	public void setReadPower(double readPower) {
		ReadPower = readPower;
	}
	public double getPVVoltage() {
		return PVVoltage;
	}
	public void setPVVoltage(double pVVoltage) {
		PVVoltage = pVVoltage;
	}
	public double getPVCurrent() {
		return PVCurrent;
	}
	public void setPVCurrent(double pVCurrent) {
		PVCurrent = pVCurrent;
	}
	public double getPVPower() {
		return PVPower;
	}
	public void setPVPower(double pVPower) {
		PVPower = pVPower;
	}
	public double getGridFrequency() {
		return GridFrequency;
	}
	public void setGridFrequency(double gridFrequency) {
		GridFrequency = gridFrequency;
	}
	public double getSystemState() {
		return SystemState;
	}
	public void setSystemState(double systemState) {
		SystemState = systemState;
	}
	public double getGoalState() {
		return GoalState;
	}
	public void setGoalState(double goalState) {
		GoalState = goalState;
	}
	public double getFaultCode() {
		return FaultCode;
	}
	public void setFaultCode(double faultCode) {
		FaultCode = faultCode;
	}
	public double getAccumulatedEnergy() {
		return AccumulatedEnergy;
	}
	public void setAccumulatedEnergy(double accumulatedEnergy) {
		AccumulatedEnergy = accumulatedEnergy;
	}
	public double getRMatrixTemp() {
		return RMatrixTemp;
	}
	public void setRMatrixTemp(double rMatrixTemp) {
		RMatrixTemp = rMatrixTemp;
	}
	public double getLMatrixTemp() {
		return LMatrixTemp;
	}
	public void setLMatrixTemp(double lMatrixTemp) {
		LMatrixTemp = lMatrixTemp;
	}
	public double getIntakeAirTemperature() {
		return IntakeAirTemperature;
	}
	public void setIntakeAirTemperature(double intakeAirTemperature) {
		IntakeAirTemperature = intakeAirTemperature;
	}
	
	
	
}
