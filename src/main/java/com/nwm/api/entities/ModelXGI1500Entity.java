/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelXGI1500Entity extends ModelBaseEntity {
	private double ACCurrentAverage;
	private double 	ACCurrentA;
	private double 	ACCurrentB;
	private double ACCurrentC;
	private double ACVoltageAB;
	private double 	ACVoltageBC;
	private double ACVoltageCA;
	private double ACVoltageAN;
	private double 	ACVoltageBN;
	private double ACVoltageCN;
	private double ActivePower;
	private double Frequency;
	private double ApparentPower;
	private double ReactivePower;
	private double PowerFactor;
	private double ActiveEnergyRawNet;
	private double DCVoltage;
	private double OperatingState;
	private double OperatingStatus;
	private double FaultStatus;
	private double Fault1;
	private double Fault2;
	private double Fault3;
	private double SerialNumberHex4Reg;
	private double CabinetTemperature;
	private double InternalTemperature;
	private double 	IMITemperature;
	private double ControlDeviceOnOff;
	private double ActivePowerSetpointPercent;
	private double ActivePowerControlEnable;
	private double PowerFactorSetpoint;
	private double PowerFactorControlEnable;
	private double ReactivePowerSetpointPercent;
	private double ReactivePowerControlEnable;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	
	private int totalFaultStatus;
	private int totalFault1;
	private int totalFault2;
	private int totalFault3;
	
	
	public int getTotalFaultStatus() {
		return totalFaultStatus;
	}
	public void setTotalFaultStatus(int totalFaultStatus) {
		this.totalFaultStatus = totalFaultStatus;
	}
	public int getTotalFault1() {
		return totalFault1;
	}
	public void setTotalFault1(int totalFault1) {
		this.totalFault1 = totalFault1;
	}
	public int getTotalFault2() {
		return totalFault2;
	}
	public void setTotalFault2(int totalFault2) {
		this.totalFault2 = totalFault2;
	}
	public int getTotalFault3() {
		return totalFault3;
	}
	public void setTotalFault3(int totalFault3) {
		this.totalFault3 = totalFault3;
	}
	public double getACCurrentAverage() {
		return ACCurrentAverage;
	}
	public void setACCurrentAverage(double aCCurrentAverage) {
		ACCurrentAverage = aCCurrentAverage;
	}
	public double getACCurrentA() {
		return ACCurrentA;
	}
	public void setACCurrentA(double aCCurrentA) {
		ACCurrentA = aCCurrentA;
	}
	public double getACCurrentB() {
		return ACCurrentB;
	}
	public void setACCurrentB(double aCCurrentB) {
		ACCurrentB = aCCurrentB;
	}
	public double getACCurrentC() {
		return ACCurrentC;
	}
	public void setACCurrentC(double aCCurrentC) {
		ACCurrentC = aCCurrentC;
	}
	public double getACVoltageAB() {
		return ACVoltageAB;
	}
	public void setACVoltageAB(double aCVoltageAB) {
		ACVoltageAB = aCVoltageAB;
	}
	public double getACVoltageBC() {
		return ACVoltageBC;
	}
	public void setACVoltageBC(double aCVoltageBC) {
		ACVoltageBC = aCVoltageBC;
	}
	public double getACVoltageCA() {
		return ACVoltageCA;
	}
	public void setACVoltageCA(double aCVoltageCA) {
		ACVoltageCA = aCVoltageCA;
	}
	public double getACVoltageAN() {
		return ACVoltageAN;
	}
	public void setACVoltageAN(double aCVoltageAN) {
		ACVoltageAN = aCVoltageAN;
	}
	public double getACVoltageBN() {
		return ACVoltageBN;
	}
	public void setACVoltageBN(double aCVoltageBN) {
		ACVoltageBN = aCVoltageBN;
	}
	public double getACVoltageCN() {
		return ACVoltageCN;
	}
	public void setACVoltageCN(double aCVoltageCN) {
		ACVoltageCN = aCVoltageCN;
	}
	public double getActivePower() {
		return ActivePower;
	}
	public void setActivePower(double activePower) {
		ActivePower = activePower;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getApparentPower() {
		return ApparentPower;
	}
	public void setApparentPower(double apparentPower) {
		ApparentPower = apparentPower;
	}
	public double getReactivePower() {
		return ReactivePower;
	}
	public void setReactivePower(double reactivePower) {
		ReactivePower = reactivePower;
	}
	public double getPowerFactor() {
		return PowerFactor;
	}
	public void setPowerFactor(double powerFactor) {
		PowerFactor = powerFactor;
	}
	public double getActiveEnergyRawNet() {
		return ActiveEnergyRawNet;
	}
	public void setActiveEnergyRawNet(double activeEnergyRawNet) {
		ActiveEnergyRawNet = activeEnergyRawNet;
	}
	public double getDCVoltage() {
		return DCVoltage;
	}
	public void setDCVoltage(double dCVoltage) {
		DCVoltage = dCVoltage;
	}
	public double getOperatingState() {
		return OperatingState;
	}
	public void setOperatingState(double operatingState) {
		OperatingState = operatingState;
	}
	public double getOperatingStatus() {
		return OperatingStatus;
	}
	public void setOperatingStatus(double operatingStatus) {
		OperatingStatus = operatingStatus;
	}
	public double getFaultStatus() {
		return FaultStatus;
	}
	public void setFaultStatus(double faultStatus) {
		FaultStatus = faultStatus;
	}
	public double getFault1() {
		return Fault1;
	}
	public void setFault1(double fault1) {
		Fault1 = fault1;
	}
	public double getFault2() {
		return Fault2;
	}
	public void setFault2(double fault2) {
		Fault2 = fault2;
	}
	public double getFault3() {
		return Fault3;
	}
	public void setFault3(double fault3) {
		Fault3 = fault3;
	}
	public double getSerialNumberHex4Reg() {
		return SerialNumberHex4Reg;
	}
	public void setSerialNumberHex4Reg(double serialNumberHex4Reg) {
		SerialNumberHex4Reg = serialNumberHex4Reg;
	}
	public double getCabinetTemperature() {
		return CabinetTemperature;
	}
	public void setCabinetTemperature(double cabinetTemperature) {
		CabinetTemperature = cabinetTemperature;
	}
	public double getInternalTemperature() {
		return InternalTemperature;
	}
	public void setInternalTemperature(double internalTemperature) {
		InternalTemperature = internalTemperature;
	}
	public double getIMITemperature() {
		return IMITemperature;
	}
	public void setIMITemperature(double iMITemperature) {
		IMITemperature = iMITemperature;
	}
	public double getControlDeviceOnOff() {
		return ControlDeviceOnOff;
	}
	public void setControlDeviceOnOff(double controlDeviceOnOff) {
		ControlDeviceOnOff = controlDeviceOnOff;
	}
	public double getActivePowerSetpointPercent() {
		return ActivePowerSetpointPercent;
	}
	public void setActivePowerSetpointPercent(double activePowerSetpointPercent) {
		ActivePowerSetpointPercent = activePowerSetpointPercent;
	}
	public double getActivePowerControlEnable() {
		return ActivePowerControlEnable;
	}
	public void setActivePowerControlEnable(double activePowerControlEnable) {
		ActivePowerControlEnable = activePowerControlEnable;
	}
	public double getPowerFactorSetpoint() {
		return PowerFactorSetpoint;
	}
	public void setPowerFactorSetpoint(double powerFactorSetpoint) {
		PowerFactorSetpoint = powerFactorSetpoint;
	}
	public double getPowerFactorControlEnable() {
		return PowerFactorControlEnable;
	}
	public void setPowerFactorControlEnable(double powerFactorControlEnable) {
		PowerFactorControlEnable = powerFactorControlEnable;
	}
	public double getReactivePowerSetpointPercent() {
		return ReactivePowerSetpointPercent;
	}
	public void setReactivePowerSetpointPercent(double reactivePowerSetpointPercent) {
		ReactivePowerSetpointPercent = reactivePowerSetpointPercent;
	}
	public double getReactivePowerControlEnable() {
		return ReactivePowerControlEnable;
	}
	public void setReactivePowerControlEnable(double reactivePowerControlEnable) {
		ReactivePowerControlEnable = reactivePowerControlEnable;
	}
	public double getNvmActivePower() {
		return nvmActivePower;
	}
	public void setNvmActivePower(double nvmActivePower) {
		this.nvmActivePower = nvmActivePower;
	}
	public double getNvmActiveEnergy() {
		return nvmActiveEnergy;
	}
	public void setNvmActiveEnergy(double nvmActiveEnergy) {
		this.nvmActiveEnergy = nvmActiveEnergy;
	}
	public double getMeasuredProduction() {
		return MeasuredProduction;
	}
	public void setMeasuredProduction(double measuredProduction) {
		MeasuredProduction = measuredProduction;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getView_tablename() {
		return view_tablename;
	}
	public void setView_tablename(String view_tablename) {
		this.view_tablename = view_tablename;
	}
	public String getJob_tablename() {
		return job_tablename;
	}
	public void setJob_tablename(String job_tablename) {
		this.job_tablename = job_tablename;
	}
	
}
