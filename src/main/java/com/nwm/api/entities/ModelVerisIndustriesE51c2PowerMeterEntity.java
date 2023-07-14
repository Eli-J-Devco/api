/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelVerisIndustriesE51c2PowerMeterEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double AccumulatedRealEnergyNet;
	private double RealEnergyQuadrants14Import;
	private double RealEnergyQuadrants23Export;
	private double ReactiveEnergyQuadrant1;
	private double ReactiveEnergyQuadrant2;
	private double ReactiveEnergyQuadrant3;
	private double ReactiveEnergyQuadrant4;
	private double ApparentEnergyNet;
	private double ApparentEnergyQuadrants14;
	private double ApparentEnergyQuadrants23;
	private double TotalNetInstantaneousRealPower;
	private double TotalNetInstantaneousReactivePower;
	private double TotalNetInstantaneousApparentPower;
	private double TotalPowerFactor;
	private double VoltageLL3pAve;
	private double VoltageLN3pAve;
	private double Current3pAve;
	private double Frequency;
	private double TotalRealPowerPresentDemand;
	private double TotalReactivePowerPresentDemand;
	private double TotalApparentPowerPresentDemand;
	private double TotalRealPowerMaxDemandImport;
	private double TotalReactivePowerMaxDemandImport;
	private double TotalApparentPowerMaxDemandImport;
	private double TotalRealPowerMaxDemandExport;
	private double TotalReactivePowerMaxDemandExport;
	private double TotalApparentPowerMaxDemandExport;
	private double AccumulatedRealEnergyPhaseAImport;
	private double AccumulatedRealEnergyPhaseBImport;
	private double AccumulatedRealEnergyPhaseCImport;
	private double AccumulatedRealEnergyPhaseAExport;
	private double AccumulatedRealEnergyPhaseBExport;
	private double AccumulatedRealEnergyPhaseCExport;
	private double AccumulatedQ1ReactiveEnergyPhaseAImport;
	private double AccumulatedQ1ReactiveEnergyPhaseBImport;
	private double AccumulatedQ1ReactiveEnergyPhaseCImport;
	private double AccumulatedQ2ReactiveEnergyPhaseAImport;
	private double AccumulatedQ2ReactiveEnergyPhaseBImport;
	private double AccumulatedQ2ReactiveEnergyPhaseCImport;
	private double AccumulatedQ3ReactiveEnergyPhaseAExport;
	private double AccumulatedQ3ReactiveEnergyPhaseBExport;
	private double AccumulatedQ3ReactiveEnergyPhaseCExport;
	private double AccumulatedQ4ReactiveEnergyPhaseAExport;
	private double AccumulatedQ4ReactiveEnergyPhaseBExport;
	private double AccumulatedQ4ReactiveEnergyPhaseCExport;
	private double AccumulatedApparentEnergyPhaseAImport;
	private double AccumulatedApparentEnergyPhaseBImport;
	private double AccumulatedApparentEnergyPhaseCImport;
	private double AccumulatedApparentEnergyPhaseAExport;
	private double AccumulatedApparentEnergyPhaseBExport;
	private double AccumulatedApparentEnergyPhaseCExport;
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
	private double VoltagePhaseAB;
	private double VoltagePhaseBC;
	private double VoltagePhaseAC;
	private double VoltagePhaseAN;
	private double VoltagePhaseBN;
	private double VoltagePhaseCN;
	private double CurrentPhaseA;
	private double CurrentPhaseB;
	private double CurrentPhaseC;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	
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
	public double getAccumulatedRealEnergyNet() {
		return AccumulatedRealEnergyNet;
	}
	public void setAccumulatedRealEnergyNet(double accumulatedRealEnergyNet) {
		AccumulatedRealEnergyNet = accumulatedRealEnergyNet;
	}
	public double getRealEnergyQuadrants14Import() {
		return RealEnergyQuadrants14Import;
	}
	public void setRealEnergyQuadrants14Import(double realEnergyQuadrants14Import) {
		RealEnergyQuadrants14Import = realEnergyQuadrants14Import;
	}
	public double getRealEnergyQuadrants23Export() {
		return RealEnergyQuadrants23Export;
	}
	public void setRealEnergyQuadrants23Export(double realEnergyQuadrants23Export) {
		RealEnergyQuadrants23Export = realEnergyQuadrants23Export;
	}
	public double getReactiveEnergyQuadrant1() {
		return ReactiveEnergyQuadrant1;
	}
	public void setReactiveEnergyQuadrant1(double reactiveEnergyQuadrant1) {
		ReactiveEnergyQuadrant1 = reactiveEnergyQuadrant1;
	}
	public double getReactiveEnergyQuadrant2() {
		return ReactiveEnergyQuadrant2;
	}
	public void setReactiveEnergyQuadrant2(double reactiveEnergyQuadrant2) {
		ReactiveEnergyQuadrant2 = reactiveEnergyQuadrant2;
	}
	public double getReactiveEnergyQuadrant3() {
		return ReactiveEnergyQuadrant3;
	}
	public void setReactiveEnergyQuadrant3(double reactiveEnergyQuadrant3) {
		ReactiveEnergyQuadrant3 = reactiveEnergyQuadrant3;
	}
	public double getReactiveEnergyQuadrant4() {
		return ReactiveEnergyQuadrant4;
	}
	public void setReactiveEnergyQuadrant4(double reactiveEnergyQuadrant4) {
		ReactiveEnergyQuadrant4 = reactiveEnergyQuadrant4;
	}
	public double getApparentEnergyNet() {
		return ApparentEnergyNet;
	}
	public void setApparentEnergyNet(double apparentEnergyNet) {
		ApparentEnergyNet = apparentEnergyNet;
	}
	public double getApparentEnergyQuadrants14() {
		return ApparentEnergyQuadrants14;
	}
	public void setApparentEnergyQuadrants14(double apparentEnergyQuadrants14) {
		ApparentEnergyQuadrants14 = apparentEnergyQuadrants14;
	}
	public double getApparentEnergyQuadrants23() {
		return ApparentEnergyQuadrants23;
	}
	public void setApparentEnergyQuadrants23(double apparentEnergyQuadrants23) {
		ApparentEnergyQuadrants23 = apparentEnergyQuadrants23;
	}
	public double getTotalNetInstantaneousRealPower() {
		return TotalNetInstantaneousRealPower;
	}
	public void setTotalNetInstantaneousRealPower(double totalNetInstantaneousRealPower) {
		TotalNetInstantaneousRealPower = totalNetInstantaneousRealPower;
	}
	public double getTotalNetInstantaneousReactivePower() {
		return TotalNetInstantaneousReactivePower;
	}
	public void setTotalNetInstantaneousReactivePower(double totalNetInstantaneousReactivePower) {
		TotalNetInstantaneousReactivePower = totalNetInstantaneousReactivePower;
	}
	public double getTotalNetInstantaneousApparentPower() {
		return TotalNetInstantaneousApparentPower;
	}
	public void setTotalNetInstantaneousApparentPower(double totalNetInstantaneousApparentPower) {
		TotalNetInstantaneousApparentPower = totalNetInstantaneousApparentPower;
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
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
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
	public double getTotalRealPowerMaxDemandImport() {
		return TotalRealPowerMaxDemandImport;
	}
	public void setTotalRealPowerMaxDemandImport(double totalRealPowerMaxDemandImport) {
		TotalRealPowerMaxDemandImport = totalRealPowerMaxDemandImport;
	}
	public double getTotalReactivePowerMaxDemandImport() {
		return TotalReactivePowerMaxDemandImport;
	}
	public void setTotalReactivePowerMaxDemandImport(double totalReactivePowerMaxDemandImport) {
		TotalReactivePowerMaxDemandImport = totalReactivePowerMaxDemandImport;
	}
	public double getTotalApparentPowerMaxDemandImport() {
		return TotalApparentPowerMaxDemandImport;
	}
	public void setTotalApparentPowerMaxDemandImport(double totalApparentPowerMaxDemandImport) {
		TotalApparentPowerMaxDemandImport = totalApparentPowerMaxDemandImport;
	}
	public double getTotalRealPowerMaxDemandExport() {
		return TotalRealPowerMaxDemandExport;
	}
	public void setTotalRealPowerMaxDemandExport(double totalRealPowerMaxDemandExport) {
		TotalRealPowerMaxDemandExport = totalRealPowerMaxDemandExport;
	}
	public double getTotalReactivePowerMaxDemandExport() {
		return TotalReactivePowerMaxDemandExport;
	}
	public void setTotalReactivePowerMaxDemandExport(double totalReactivePowerMaxDemandExport) {
		TotalReactivePowerMaxDemandExport = totalReactivePowerMaxDemandExport;
	}
	public double getTotalApparentPowerMaxDemandExport() {
		return TotalApparentPowerMaxDemandExport;
	}
	public void setTotalApparentPowerMaxDemandExport(double totalApparentPowerMaxDemandExport) {
		TotalApparentPowerMaxDemandExport = totalApparentPowerMaxDemandExport;
	}
	public double getAccumulatedRealEnergyPhaseAImport() {
		return AccumulatedRealEnergyPhaseAImport;
	}
	public void setAccumulatedRealEnergyPhaseAImport(double accumulatedRealEnergyPhaseAImport) {
		AccumulatedRealEnergyPhaseAImport = accumulatedRealEnergyPhaseAImport;
	}
	public double getAccumulatedRealEnergyPhaseBImport() {
		return AccumulatedRealEnergyPhaseBImport;
	}
	public void setAccumulatedRealEnergyPhaseBImport(double accumulatedRealEnergyPhaseBImport) {
		AccumulatedRealEnergyPhaseBImport = accumulatedRealEnergyPhaseBImport;
	}
	public double getAccumulatedRealEnergyPhaseCImport() {
		return AccumulatedRealEnergyPhaseCImport;
	}
	public void setAccumulatedRealEnergyPhaseCImport(double accumulatedRealEnergyPhaseCImport) {
		AccumulatedRealEnergyPhaseCImport = accumulatedRealEnergyPhaseCImport;
	}
	public double getAccumulatedRealEnergyPhaseAExport() {
		return AccumulatedRealEnergyPhaseAExport;
	}
	public void setAccumulatedRealEnergyPhaseAExport(double accumulatedRealEnergyPhaseAExport) {
		AccumulatedRealEnergyPhaseAExport = accumulatedRealEnergyPhaseAExport;
	}
	public double getAccumulatedRealEnergyPhaseBExport() {
		return AccumulatedRealEnergyPhaseBExport;
	}
	public void setAccumulatedRealEnergyPhaseBExport(double accumulatedRealEnergyPhaseBExport) {
		AccumulatedRealEnergyPhaseBExport = accumulatedRealEnergyPhaseBExport;
	}
	public double getAccumulatedRealEnergyPhaseCExport() {
		return AccumulatedRealEnergyPhaseCExport;
	}
	public void setAccumulatedRealEnergyPhaseCExport(double accumulatedRealEnergyPhaseCExport) {
		AccumulatedRealEnergyPhaseCExport = accumulatedRealEnergyPhaseCExport;
	}
	public double getAccumulatedQ1ReactiveEnergyPhaseAImport() {
		return AccumulatedQ1ReactiveEnergyPhaseAImport;
	}
	public void setAccumulatedQ1ReactiveEnergyPhaseAImport(double accumulatedQ1ReactiveEnergyPhaseAImport) {
		AccumulatedQ1ReactiveEnergyPhaseAImport = accumulatedQ1ReactiveEnergyPhaseAImport;
	}
	public double getAccumulatedQ1ReactiveEnergyPhaseBImport() {
		return AccumulatedQ1ReactiveEnergyPhaseBImport;
	}
	public void setAccumulatedQ1ReactiveEnergyPhaseBImport(double accumulatedQ1ReactiveEnergyPhaseBImport) {
		AccumulatedQ1ReactiveEnergyPhaseBImport = accumulatedQ1ReactiveEnergyPhaseBImport;
	}
	public double getAccumulatedQ1ReactiveEnergyPhaseCImport() {
		return AccumulatedQ1ReactiveEnergyPhaseCImport;
	}
	public void setAccumulatedQ1ReactiveEnergyPhaseCImport(double accumulatedQ1ReactiveEnergyPhaseCImport) {
		AccumulatedQ1ReactiveEnergyPhaseCImport = accumulatedQ1ReactiveEnergyPhaseCImport;
	}
	public double getAccumulatedQ2ReactiveEnergyPhaseAImport() {
		return AccumulatedQ2ReactiveEnergyPhaseAImport;
	}
	public void setAccumulatedQ2ReactiveEnergyPhaseAImport(double accumulatedQ2ReactiveEnergyPhaseAImport) {
		AccumulatedQ2ReactiveEnergyPhaseAImport = accumulatedQ2ReactiveEnergyPhaseAImport;
	}
	public double getAccumulatedQ2ReactiveEnergyPhaseBImport() {
		return AccumulatedQ2ReactiveEnergyPhaseBImport;
	}
	public void setAccumulatedQ2ReactiveEnergyPhaseBImport(double accumulatedQ2ReactiveEnergyPhaseBImport) {
		AccumulatedQ2ReactiveEnergyPhaseBImport = accumulatedQ2ReactiveEnergyPhaseBImport;
	}
	public double getAccumulatedQ2ReactiveEnergyPhaseCImport() {
		return AccumulatedQ2ReactiveEnergyPhaseCImport;
	}
	public void setAccumulatedQ2ReactiveEnergyPhaseCImport(double accumulatedQ2ReactiveEnergyPhaseCImport) {
		AccumulatedQ2ReactiveEnergyPhaseCImport = accumulatedQ2ReactiveEnergyPhaseCImport;
	}
	public double getAccumulatedQ3ReactiveEnergyPhaseAExport() {
		return AccumulatedQ3ReactiveEnergyPhaseAExport;
	}
	public void setAccumulatedQ3ReactiveEnergyPhaseAExport(double accumulatedQ3ReactiveEnergyPhaseAExport) {
		AccumulatedQ3ReactiveEnergyPhaseAExport = accumulatedQ3ReactiveEnergyPhaseAExport;
	}
	public double getAccumulatedQ3ReactiveEnergyPhaseBExport() {
		return AccumulatedQ3ReactiveEnergyPhaseBExport;
	}
	public void setAccumulatedQ3ReactiveEnergyPhaseBExport(double accumulatedQ3ReactiveEnergyPhaseBExport) {
		AccumulatedQ3ReactiveEnergyPhaseBExport = accumulatedQ3ReactiveEnergyPhaseBExport;
	}
	public double getAccumulatedQ3ReactiveEnergyPhaseCExport() {
		return AccumulatedQ3ReactiveEnergyPhaseCExport;
	}
	public void setAccumulatedQ3ReactiveEnergyPhaseCExport(double accumulatedQ3ReactiveEnergyPhaseCExport) {
		AccumulatedQ3ReactiveEnergyPhaseCExport = accumulatedQ3ReactiveEnergyPhaseCExport;
	}
	public double getAccumulatedQ4ReactiveEnergyPhaseAExport() {
		return AccumulatedQ4ReactiveEnergyPhaseAExport;
	}
	public void setAccumulatedQ4ReactiveEnergyPhaseAExport(double accumulatedQ4ReactiveEnergyPhaseAExport) {
		AccumulatedQ4ReactiveEnergyPhaseAExport = accumulatedQ4ReactiveEnergyPhaseAExport;
	}
	public double getAccumulatedQ4ReactiveEnergyPhaseBExport() {
		return AccumulatedQ4ReactiveEnergyPhaseBExport;
	}
	public void setAccumulatedQ4ReactiveEnergyPhaseBExport(double accumulatedQ4ReactiveEnergyPhaseBExport) {
		AccumulatedQ4ReactiveEnergyPhaseBExport = accumulatedQ4ReactiveEnergyPhaseBExport;
	}
	public double getAccumulatedQ4ReactiveEnergyPhaseCExport() {
		return AccumulatedQ4ReactiveEnergyPhaseCExport;
	}
	public void setAccumulatedQ4ReactiveEnergyPhaseCExport(double accumulatedQ4ReactiveEnergyPhaseCExport) {
		AccumulatedQ4ReactiveEnergyPhaseCExport = accumulatedQ4ReactiveEnergyPhaseCExport;
	}
	public double getAccumulatedApparentEnergyPhaseAImport() {
		return AccumulatedApparentEnergyPhaseAImport;
	}
	public void setAccumulatedApparentEnergyPhaseAImport(double accumulatedApparentEnergyPhaseAImport) {
		AccumulatedApparentEnergyPhaseAImport = accumulatedApparentEnergyPhaseAImport;
	}
	public double getAccumulatedApparentEnergyPhaseBImport() {
		return AccumulatedApparentEnergyPhaseBImport;
	}
	public void setAccumulatedApparentEnergyPhaseBImport(double accumulatedApparentEnergyPhaseBImport) {
		AccumulatedApparentEnergyPhaseBImport = accumulatedApparentEnergyPhaseBImport;
	}
	public double getAccumulatedApparentEnergyPhaseCImport() {
		return AccumulatedApparentEnergyPhaseCImport;
	}
	public void setAccumulatedApparentEnergyPhaseCImport(double accumulatedApparentEnergyPhaseCImport) {
		AccumulatedApparentEnergyPhaseCImport = accumulatedApparentEnergyPhaseCImport;
	}
	public double getAccumulatedApparentEnergyPhaseAExport() {
		return AccumulatedApparentEnergyPhaseAExport;
	}
	public void setAccumulatedApparentEnergyPhaseAExport(double accumulatedApparentEnergyPhaseAExport) {
		AccumulatedApparentEnergyPhaseAExport = accumulatedApparentEnergyPhaseAExport;
	}
	public double getAccumulatedApparentEnergyPhaseBExport() {
		return AccumulatedApparentEnergyPhaseBExport;
	}
	public void setAccumulatedApparentEnergyPhaseBExport(double accumulatedApparentEnergyPhaseBExport) {
		AccumulatedApparentEnergyPhaseBExport = accumulatedApparentEnergyPhaseBExport;
	}
	public double getAccumulatedApparentEnergyPhaseCExport() {
		return AccumulatedApparentEnergyPhaseCExport;
	}
	public void setAccumulatedApparentEnergyPhaseCExport(double accumulatedApparentEnergyPhaseCExport) {
		AccumulatedApparentEnergyPhaseCExport = accumulatedApparentEnergyPhaseCExport;
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
