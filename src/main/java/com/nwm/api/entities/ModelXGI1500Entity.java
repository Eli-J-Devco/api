/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelXGI1500Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double ACCurrentAverage;
	private double ACCurrentA;
	private double ACCurrentB;
	private double ACCurrentC;
	private double ACVoltageAB;
	private double ACVoltageBC;
	private double ACVoltageCA;
	private double ACVoltageAN;
	private double ACVoltageBN;
	private double ACVoltageCN;
	private double ActivePower;
	private double Frequency;
	private double ApparentPower;
	private double ReactivePower;
	private double PowerFactor;
	private double ActiveEnergyGross;
	private double ActiveEnergyRawNet;
	private double DCCurrentTotal;
	private double DCPowerTotal;
	private double DCVoltageAvg;
	private double OperatingState;
	private double OperatingStatus;
	private double FaultStatus;
	private double Fault1;
	private double Fault2;
	private double Fault3;
	private double SerialNumberHex4Reg;
	private double CabinetTemperature;
	private double HeatSinkTemperature;
	private double OtherTemperature;
	private double Currentscalefactor;
	private double VoltageScaleFactor;
	private double ActivePowerScaleFactor;
	private double FrequencyScaleFactor;
	private double ApparentPowerScaleFactor;
	private double ReactivePowerScaleFactor;
	private double PowerFactorScaleFactor;
	private double EnergyScaleFactor;
	private double DCCurrentscalefactor;
	private double DCVoltagescalefactor;
	private double DCPowerScaleFactor;
	private double Temperaturescalefactor;
	private double ID;
	private double Length;
	private double Manufacturer;
	private double Model;
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
	private int enable_alert;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public int getLow_alarm() {
		return low_alarm;
	}
	public void setLow_alarm(int low_alarm) {
		this.low_alarm = low_alarm;
	}
	public int getHigh_alarm() {
		return high_alarm;
	}
	public void setHigh_alarm(int high_alarm) {
		this.high_alarm = high_alarm;
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
	public double getActiveEnergyGross() {
		return ActiveEnergyGross;
	}
	public void setActiveEnergyGross(double activeEnergyGross) {
		ActiveEnergyGross = activeEnergyGross;
	}
	public double getActiveEnergyRawNet() {
		return ActiveEnergyRawNet;
	}
	public void setActiveEnergyRawNet(double activeEnergyRawNet) {
		ActiveEnergyRawNet = activeEnergyRawNet;
	}
	public double getDCCurrentTotal() {
		return DCCurrentTotal;
	}
	public void setDCCurrentTotal(double dCCurrentTotal) {
		DCCurrentTotal = dCCurrentTotal;
	}
	public double getDCPowerTotal() {
		return DCPowerTotal;
	}
	public void setDCPowerTotal(double dCPowerTotal) {
		DCPowerTotal = dCPowerTotal;
	}
	public double getDCVoltageAvg() {
		return DCVoltageAvg;
	}
	public void setDCVoltageAvg(double dCVoltageAvg) {
		DCVoltageAvg = dCVoltageAvg;
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
	public double getHeatSinkTemperature() {
		return HeatSinkTemperature;
	}
	public void setHeatSinkTemperature(double heatSinkTemperature) {
		HeatSinkTemperature = heatSinkTemperature;
	}
	public double getOtherTemperature() {
		return OtherTemperature;
	}
	public void setOtherTemperature(double otherTemperature) {
		OtherTemperature = otherTemperature;
	}
	public double getCurrentscalefactor() {
		return Currentscalefactor;
	}
	public void setCurrentscalefactor(double currentscalefactor) {
		Currentscalefactor = currentscalefactor;
	}
	public double getVoltageScaleFactor() {
		return VoltageScaleFactor;
	}
	public void setVoltageScaleFactor(double voltageScaleFactor) {
		VoltageScaleFactor = voltageScaleFactor;
	}
	public double getActivePowerScaleFactor() {
		return ActivePowerScaleFactor;
	}
	public void setActivePowerScaleFactor(double activePowerScaleFactor) {
		ActivePowerScaleFactor = activePowerScaleFactor;
	}
	public double getFrequencyScaleFactor() {
		return FrequencyScaleFactor;
	}
	public void setFrequencyScaleFactor(double frequencyScaleFactor) {
		FrequencyScaleFactor = frequencyScaleFactor;
	}
	public double getApparentPowerScaleFactor() {
		return ApparentPowerScaleFactor;
	}
	public void setApparentPowerScaleFactor(double apparentPowerScaleFactor) {
		ApparentPowerScaleFactor = apparentPowerScaleFactor;
	}
	public double getReactivePowerScaleFactor() {
		return ReactivePowerScaleFactor;
	}
	public void setReactivePowerScaleFactor(double reactivePowerScaleFactor) {
		ReactivePowerScaleFactor = reactivePowerScaleFactor;
	}
	public double getPowerFactorScaleFactor() {
		return PowerFactorScaleFactor;
	}
	public void setPowerFactorScaleFactor(double powerFactorScaleFactor) {
		PowerFactorScaleFactor = powerFactorScaleFactor;
	}
	public double getEnergyScaleFactor() {
		return EnergyScaleFactor;
	}
	public void setEnergyScaleFactor(double energyScaleFactor) {
		EnergyScaleFactor = energyScaleFactor;
	}
	public double getDCCurrentscalefactor() {
		return DCCurrentscalefactor;
	}
	public void setDCCurrentscalefactor(double dCCurrentscalefactor) {
		DCCurrentscalefactor = dCCurrentscalefactor;
	}
	public double getDCVoltagescalefactor() {
		return DCVoltagescalefactor;
	}
	public void setDCVoltagescalefactor(double dCVoltagescalefactor) {
		DCVoltagescalefactor = dCVoltagescalefactor;
	}
	public double getDCPowerScaleFactor() {
		return DCPowerScaleFactor;
	}
	public void setDCPowerScaleFactor(double dCPowerScaleFactor) {
		DCPowerScaleFactor = dCPowerScaleFactor;
	}
	public double getTemperaturescalefactor() {
		return Temperaturescalefactor;
	}
	public void setTemperaturescalefactor(double temperaturescalefactor) {
		Temperaturescalefactor = temperaturescalefactor;
	}
	public double getID() {
		return ID;
	}
	public void setID(double iD) {
		ID = iD;
	}
	public double getLength() {
		return Length;
	}
	public void setLength(double length) {
		Length = length;
	}
	public double getManufacturer() {
		return Manufacturer;
	}
	public void setManufacturer(double manufacturer) {
		Manufacturer = manufacturer;
	}
	public double getModel() {
		return Model;
	}
	public void setModel(double model) {
		Model = model;
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
	public int getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(int enable_alert) {
		this.enable_alert = enable_alert;
	}
	
	
	
}
