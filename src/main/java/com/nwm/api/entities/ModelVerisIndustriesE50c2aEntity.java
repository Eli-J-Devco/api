/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelVerisIndustriesE50c2aEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double RealEnergyConsumption;
	private double TotalInstantaneousRealPower;
	private double TotalInstantaneousReactivePower;
	private double TotalInstantaneousApparentPower;
	private double TotalPowerFactor;
	private double VoltageLL3pAve;
	private double VoltageLN3pAve;
	private double Current3pAve;
	private double RealPowerPhaseA;
	private double RealPowerPhaseB;
	private double RealPowerPhaseC;
	private double PowerFactorPhaseA;
	private double PowerFactorPhaseB;
	private double PowerFactorPhaseC;
	private double VoltagePhaseAB;
	private double VoltagePhaseBC;
	private double VoltagePhaseAC;
	private double VoltagePhaseAN;
	private double VoltagePhaseBN;
	private double VoltagePhaseCN;
	private double CurrentInstantaneousPhaseA;
	private double CurrentInstantaneousPhaseB;
	private double CurrentInstantaneousPhaseC;
	private double Frequency;
	private double ApparentEnergyConsumption;
	private double ReactiveEnergyConsumption;
	private double ApparentPowerPhaseA;
	private double ApparentPowerPhaseB;
	private double ApparentPowerPhaseC;
	private double ReactivePowerPhaseA;
	private double ReactivePowerPhaseB;
	private double ReactivePowerPhaseC;
	private double TotalRealPowerPresentDemand;
	private double TotalReactivePowerPresentDemand;
	private double TotalApparentPowerPresentDemand;
	private double TotalRealPowerMaxDemand;
	private double TotalReactivePowerMaxDemand;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
private int enable_alert;
	
	
	
	public int getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(int enable_alert) {
		this.enable_alert = enable_alert;
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
	
	
	public double getMeasuredProduction() {
		return MeasuredProduction;
	}
	public void setMeasuredProduction(double measuredProduction) {
		MeasuredProduction = measuredProduction;
	}
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
	public double getRealEnergyConsumption() {
		return RealEnergyConsumption;
	}
	public void setRealEnergyConsumption(double realEnergyConsumption) {
		RealEnergyConsumption = realEnergyConsumption;
	}
	public double getTotalInstantaneousRealPower() {
		return TotalInstantaneousRealPower;
	}
	public void setTotalInstantaneousRealPower(double totalInstantaneousRealPower) {
		TotalInstantaneousRealPower = totalInstantaneousRealPower;
	}
	public double getTotalInstantaneousReactivePower() {
		return TotalInstantaneousReactivePower;
	}
	public void setTotalInstantaneousReactivePower(double totalInstantaneousReactivePower) {
		TotalInstantaneousReactivePower = totalInstantaneousReactivePower;
	}
	public double getTotalInstantaneousApparentPower() {
		return TotalInstantaneousApparentPower;
	}
	public void setTotalInstantaneousApparentPower(double totalInstantaneousApparentPower) {
		TotalInstantaneousApparentPower = totalInstantaneousApparentPower;
	}
	public double getTotalPowerFactor() {
		return TotalPowerFactor;
	}
	public void setTotalPowerFactor(double totalPowerFactor) {
		TotalPowerFactor = totalPowerFactor;
	}
	public double getVoltageLL3pAve() {
		return VoltageLL3pAve;
	}
	public void setVoltageLL3pAve(double voltageLL3pAve) {
		VoltageLL3pAve = voltageLL3pAve;
	}
	public double getVoltageLN3pAve() {
		return VoltageLN3pAve;
	}
	public void setVoltageLN3pAve(double voltageLN3pAve) {
		VoltageLN3pAve = voltageLN3pAve;
	}
	public double getCurrent3pAve() {
		return Current3pAve;
	}
	public void setCurrent3pAve(double current3pAve) {
		Current3pAve = current3pAve;
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
	public double getVoltagePhaseAC() {
		return VoltagePhaseAC;
	}
	public void setVoltagePhaseAC(double voltagePhaseAC) {
		VoltagePhaseAC = voltagePhaseAC;
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
	public double getCurrentInstantaneousPhaseA() {
		return CurrentInstantaneousPhaseA;
	}
	public void setCurrentInstantaneousPhaseA(double currentInstantaneousPhaseA) {
		CurrentInstantaneousPhaseA = currentInstantaneousPhaseA;
	}
	public double getCurrentInstantaneousPhaseB() {
		return CurrentInstantaneousPhaseB;
	}
	public void setCurrentInstantaneousPhaseB(double currentInstantaneousPhaseB) {
		CurrentInstantaneousPhaseB = currentInstantaneousPhaseB;
	}
	public double getCurrentInstantaneousPhaseC() {
		return CurrentInstantaneousPhaseC;
	}
	public void setCurrentInstantaneousPhaseC(double currentInstantaneousPhaseC) {
		CurrentInstantaneousPhaseC = currentInstantaneousPhaseC;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getApparentEnergyConsumption() {
		return ApparentEnergyConsumption;
	}
	public void setApparentEnergyConsumption(double apparentEnergyConsumption) {
		ApparentEnergyConsumption = apparentEnergyConsumption;
	}
	public double getReactiveEnergyConsumption() {
		return ReactiveEnergyConsumption;
	}
	public void setReactiveEnergyConsumption(double reactiveEnergyConsumption) {
		ReactiveEnergyConsumption = reactiveEnergyConsumption;
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
	public double getTotalRealPowerPresentDemand() {
		return TotalRealPowerPresentDemand;
	}
	public void setTotalRealPowerPresentDemand(double totalRealPowerPresentDemand) {
		TotalRealPowerPresentDemand = totalRealPowerPresentDemand;
	}
	public double getTotalReactivePowerPresentDemand() {
		return TotalReactivePowerPresentDemand;
	}
	public void setTotalReactivePowerPresentDemand(double totalReactivePowerPresentDemand) {
		TotalReactivePowerPresentDemand = totalReactivePowerPresentDemand;
	}
	public double getTotalApparentPowerPresentDemand() {
		return TotalApparentPowerPresentDemand;
	}
	public void setTotalApparentPowerPresentDemand(double totalApparentPowerPresentDemand) {
		TotalApparentPowerPresentDemand = totalApparentPowerPresentDemand;
	}
	public double getTotalRealPowerMaxDemand() {
		return TotalRealPowerMaxDemand;
	}
	public void setTotalRealPowerMaxDemand(double totalRealPowerMaxDemand) {
		TotalRealPowerMaxDemand = totalRealPowerMaxDemand;
	}
	public double getTotalReactivePowerMaxDemand() {
		return TotalReactivePowerMaxDemand;
	}
	public void setTotalReactivePowerMaxDemand(double totalReactivePowerMaxDemand) {
		TotalReactivePowerMaxDemand = totalReactivePowerMaxDemand;
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
}
