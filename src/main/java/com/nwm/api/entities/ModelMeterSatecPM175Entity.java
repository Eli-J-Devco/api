/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelMeterSatecPM175Entity extends ModelBaseEntity {
	
	private double VoltagePhaseAB;
	private double VoltagePhaseBC;
	private double VoltagePhaseCA;
	private double CurrentPhaseA;
	private double CurrentPhaseB;
	private double CurrentPhaseC;
	private double RealPowerPhaseA;
	private double RealPowerPhaseB;
	private double RealPowerPhaseC;
	private double ReactivePowerPhaseA;
	private double ReactivePowerPhaseB;
	private double ReactivePowerPhaseC;
	private double ApparentPowerPhaseA;
	private double ApparentPowerPhaseB;
	private double ApparentPowerPhaseC;
	private double PFPhaseA;
	private double PFPhaseB;
	private double PFPhaseC;
	private double PFTotal3Phase;
	private double RealPowerTotal3Phase;
	private double ReactivePowerTotal3Phase;
	private double ApparentPowerTotal3Phase;
	private double NeutralCurrent;
	private double Frequency;
	private double MaximumRealPowerImportSlidingWindowDemand;
	private double RealPowerImportAccumulatedDemand;
	private double MaximumApparentPowerSlidingWindowDemand;
	private double ApparentPowerAccumulatedDemand;
	private double MaximumCurrentDemandPhaseA;
	private double MaximumCurrentDemandPhaseB;
	private double MaximumCurrentDemandPhaseC;
	private double ActiveEnergyImportLowWord;
	private double ActiveEnergyImportHighWord;
	private double ActiveEnergyExportLowWord;
	private double ActiveEnergyExportHighWord;
	private double ReactiveEnergyNetPositiveLowWord;
	private double ReactiveEnergyNetPositiveHighWord;
	private double ReactiveEnergyNetNegativeLowWord;
	private double ReactiveEnergyNetNegativeHighWord;
	private double VoltageTHDPhaseAB;
	private double VoltageTHDPhaseBC;
	private double VoltageTHDPhaseCA;
	private double CurrentTHDPhaseA;
	private double CurrentTHDPhaseB;
	private double CurrentTHDPhaseC;
	private double ApparentEnergyLowWord;
	private double ApparentEnergyHighWord;
	private double PresentRealPowerImportSlidingWindowDemand;
	private double PresentApparentPowerSlidingWindowDemand;
	private double PFImportAtMaximumApparentPowerDemand;
	private double CurrentTDDPhaseA;
	private double CurrentTDDPhaseB;
	private double CurrentTDDPhaseC;
	private double VoltagePhaseA10minRMS;
	private double VoltagePhaseB10minRMS;
	private double VoltagePhaseC10minRMS;
	private double ActiveEnergyImport;
	private double ActiveEnergyExport;
	private double ReactiveEnergyNetPositive;
	private double ReactiveEnergyNetNegative;
	private double ApparentEnergy;
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
	public double getPFPhaseA() {
		return PFPhaseA;
	}
	public void setPFPhaseA(double pFPhaseA) {
		PFPhaseA = pFPhaseA;
	}
	public double getPFPhaseB() {
		return PFPhaseB;
	}
	public void setPFPhaseB(double pFPhaseB) {
		PFPhaseB = pFPhaseB;
	}
	public double getPFPhaseC() {
		return PFPhaseC;
	}
	public void setPFPhaseC(double pFPhaseC) {
		PFPhaseC = pFPhaseC;
	}
	public double getPFTotal3Phase() {
		return PFTotal3Phase;
	}
	public void setPFTotal3Phase(double pFTotal3Phase) {
		PFTotal3Phase = pFTotal3Phase;
	}
	public double getRealPowerTotal3Phase() {
		return RealPowerTotal3Phase;
	}
	public void setRealPowerTotal3Phase(double realPowerTotal3Phase) {
		RealPowerTotal3Phase = realPowerTotal3Phase;
	}
	public double getReactivePowerTotal3Phase() {
		return ReactivePowerTotal3Phase;
	}
	public void setReactivePowerTotal3Phase(double reactivePowerTotal3Phase) {
		ReactivePowerTotal3Phase = reactivePowerTotal3Phase;
	}
	public double getApparentPowerTotal3Phase() {
		return ApparentPowerTotal3Phase;
	}
	public void setApparentPowerTotal3Phase(double apparentPowerTotal3Phase) {
		ApparentPowerTotal3Phase = apparentPowerTotal3Phase;
	}
	public double getNeutralCurrent() {
		return NeutralCurrent;
	}
	public void setNeutralCurrent(double neutralCurrent) {
		NeutralCurrent = neutralCurrent;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getMaximumRealPowerImportSlidingWindowDemand() {
		return MaximumRealPowerImportSlidingWindowDemand;
	}
	public void setMaximumRealPowerImportSlidingWindowDemand(double maximumRealPowerImportSlidingWindowDemand) {
		MaximumRealPowerImportSlidingWindowDemand = maximumRealPowerImportSlidingWindowDemand;
	}
	public double getRealPowerImportAccumulatedDemand() {
		return RealPowerImportAccumulatedDemand;
	}
	public void setRealPowerImportAccumulatedDemand(double realPowerImportAccumulatedDemand) {
		RealPowerImportAccumulatedDemand = realPowerImportAccumulatedDemand;
	}
	public double getMaximumApparentPowerSlidingWindowDemand() {
		return MaximumApparentPowerSlidingWindowDemand;
	}
	public void setMaximumApparentPowerSlidingWindowDemand(double maximumApparentPowerSlidingWindowDemand) {
		MaximumApparentPowerSlidingWindowDemand = maximumApparentPowerSlidingWindowDemand;
	}
	public double getApparentPowerAccumulatedDemand() {
		return ApparentPowerAccumulatedDemand;
	}
	public void setApparentPowerAccumulatedDemand(double apparentPowerAccumulatedDemand) {
		ApparentPowerAccumulatedDemand = apparentPowerAccumulatedDemand;
	}
	public double getMaximumCurrentDemandPhaseA() {
		return MaximumCurrentDemandPhaseA;
	}
	public void setMaximumCurrentDemandPhaseA(double maximumCurrentDemandPhaseA) {
		MaximumCurrentDemandPhaseA = maximumCurrentDemandPhaseA;
	}
	public double getMaximumCurrentDemandPhaseB() {
		return MaximumCurrentDemandPhaseB;
	}
	public void setMaximumCurrentDemandPhaseB(double maximumCurrentDemandPhaseB) {
		MaximumCurrentDemandPhaseB = maximumCurrentDemandPhaseB;
	}
	public double getMaximumCurrentDemandPhaseC() {
		return MaximumCurrentDemandPhaseC;
	}
	public void setMaximumCurrentDemandPhaseC(double maximumCurrentDemandPhaseC) {
		MaximumCurrentDemandPhaseC = maximumCurrentDemandPhaseC;
	}
	public double getActiveEnergyImportLowWord() {
		return ActiveEnergyImportLowWord;
	}
	public void setActiveEnergyImportLowWord(double activeEnergyImportLowWord) {
		ActiveEnergyImportLowWord = activeEnergyImportLowWord;
	}
	public double getActiveEnergyImportHighWord() {
		return ActiveEnergyImportHighWord;
	}
	public void setActiveEnergyImportHighWord(double activeEnergyImportHighWord) {
		ActiveEnergyImportHighWord = activeEnergyImportHighWord;
	}
	public double getActiveEnergyExportLowWord() {
		return ActiveEnergyExportLowWord;
	}
	public void setActiveEnergyExportLowWord(double activeEnergyExportLowWord) {
		ActiveEnergyExportLowWord = activeEnergyExportLowWord;
	}
	public double getActiveEnergyExportHighWord() {
		return ActiveEnergyExportHighWord;
	}
	public void setActiveEnergyExportHighWord(double activeEnergyExportHighWord) {
		ActiveEnergyExportHighWord = activeEnergyExportHighWord;
	}
	public double getReactiveEnergyNetPositiveLowWord() {
		return ReactiveEnergyNetPositiveLowWord;
	}
	public void setReactiveEnergyNetPositiveLowWord(double reactiveEnergyNetPositiveLowWord) {
		ReactiveEnergyNetPositiveLowWord = reactiveEnergyNetPositiveLowWord;
	}
	public double getReactiveEnergyNetPositiveHighWord() {
		return ReactiveEnergyNetPositiveHighWord;
	}
	public void setReactiveEnergyNetPositiveHighWord(double reactiveEnergyNetPositiveHighWord) {
		ReactiveEnergyNetPositiveHighWord = reactiveEnergyNetPositiveHighWord;
	}
	public double getReactiveEnergyNetNegativeLowWord() {
		return ReactiveEnergyNetNegativeLowWord;
	}
	public void setReactiveEnergyNetNegativeLowWord(double reactiveEnergyNetNegativeLowWord) {
		ReactiveEnergyNetNegativeLowWord = reactiveEnergyNetNegativeLowWord;
	}
	public double getReactiveEnergyNetNegativeHighWord() {
		return ReactiveEnergyNetNegativeHighWord;
	}
	public void setReactiveEnergyNetNegativeHighWord(double reactiveEnergyNetNegativeHighWord) {
		ReactiveEnergyNetNegativeHighWord = reactiveEnergyNetNegativeHighWord;
	}
	public double getVoltageTHDPhaseAB() {
		return VoltageTHDPhaseAB;
	}
	public void setVoltageTHDPhaseAB(double voltageTHDPhaseAB) {
		VoltageTHDPhaseAB = voltageTHDPhaseAB;
	}
	public double getVoltageTHDPhaseBC() {
		return VoltageTHDPhaseBC;
	}
	public void setVoltageTHDPhaseBC(double voltageTHDPhaseBC) {
		VoltageTHDPhaseBC = voltageTHDPhaseBC;
	}
	public double getVoltageTHDPhaseCA() {
		return VoltageTHDPhaseCA;
	}
	public void setVoltageTHDPhaseCA(double voltageTHDPhaseCA) {
		VoltageTHDPhaseCA = voltageTHDPhaseCA;
	}
	public double getCurrentTHDPhaseA() {
		return CurrentTHDPhaseA;
	}
	public void setCurrentTHDPhaseA(double currentTHDPhaseA) {
		CurrentTHDPhaseA = currentTHDPhaseA;
	}
	public double getCurrentTHDPhaseB() {
		return CurrentTHDPhaseB;
	}
	public void setCurrentTHDPhaseB(double currentTHDPhaseB) {
		CurrentTHDPhaseB = currentTHDPhaseB;
	}
	public double getCurrentTHDPhaseC() {
		return CurrentTHDPhaseC;
	}
	public void setCurrentTHDPhaseC(double currentTHDPhaseC) {
		CurrentTHDPhaseC = currentTHDPhaseC;
	}
	public double getApparentEnergyLowWord() {
		return ApparentEnergyLowWord;
	}
	public void setApparentEnergyLowWord(double apparentEnergyLowWord) {
		ApparentEnergyLowWord = apparentEnergyLowWord;
	}
	public double getApparentEnergyHighWord() {
		return ApparentEnergyHighWord;
	}
	public void setApparentEnergyHighWord(double apparentEnergyHighWord) {
		ApparentEnergyHighWord = apparentEnergyHighWord;
	}
	public double getPresentRealPowerImportSlidingWindowDemand() {
		return PresentRealPowerImportSlidingWindowDemand;
	}
	public void setPresentRealPowerImportSlidingWindowDemand(double presentRealPowerImportSlidingWindowDemand) {
		PresentRealPowerImportSlidingWindowDemand = presentRealPowerImportSlidingWindowDemand;
	}
	public double getPresentApparentPowerSlidingWindowDemand() {
		return PresentApparentPowerSlidingWindowDemand;
	}
	public void setPresentApparentPowerSlidingWindowDemand(double presentApparentPowerSlidingWindowDemand) {
		PresentApparentPowerSlidingWindowDemand = presentApparentPowerSlidingWindowDemand;
	}
	public double getPFImportAtMaximumApparentPowerDemand() {
		return PFImportAtMaximumApparentPowerDemand;
	}
	public void setPFImportAtMaximumApparentPowerDemand(double pFImportAtMaximumApparentPowerDemand) {
		PFImportAtMaximumApparentPowerDemand = pFImportAtMaximumApparentPowerDemand;
	}
	public double getCurrentTDDPhaseA() {
		return CurrentTDDPhaseA;
	}
	public void setCurrentTDDPhaseA(double currentTDDPhaseA) {
		CurrentTDDPhaseA = currentTDDPhaseA;
	}
	public double getCurrentTDDPhaseB() {
		return CurrentTDDPhaseB;
	}
	public void setCurrentTDDPhaseB(double currentTDDPhaseB) {
		CurrentTDDPhaseB = currentTDDPhaseB;
	}
	public double getCurrentTDDPhaseC() {
		return CurrentTDDPhaseC;
	}
	public void setCurrentTDDPhaseC(double currentTDDPhaseC) {
		CurrentTDDPhaseC = currentTDDPhaseC;
	}
	public double getVoltagePhaseA10minRMS() {
		return VoltagePhaseA10minRMS;
	}
	public void setVoltagePhaseA10minRMS(double voltagePhaseA10minRMS) {
		VoltagePhaseA10minRMS = voltagePhaseA10minRMS;
	}
	public double getVoltagePhaseB10minRMS() {
		return VoltagePhaseB10minRMS;
	}
	public void setVoltagePhaseB10minRMS(double voltagePhaseB10minRMS) {
		VoltagePhaseB10minRMS = voltagePhaseB10minRMS;
	}
	public double getVoltagePhaseC10minRMS() {
		return VoltagePhaseC10minRMS;
	}
	public void setVoltagePhaseC10minRMS(double voltagePhaseC10minRMS) {
		VoltagePhaseC10minRMS = voltagePhaseC10minRMS;
	}
	public double getActiveEnergyImport() {
		return ActiveEnergyImport;
	}
	public void setActiveEnergyImport(double activeEnergyImport) {
		ActiveEnergyImport = activeEnergyImport;
	}
	public double getActiveEnergyExport() {
		return ActiveEnergyExport;
	}
	public void setActiveEnergyExport(double activeEnergyExport) {
		ActiveEnergyExport = activeEnergyExport;
	}
	public double getReactiveEnergyNetPositive() {
		return ReactiveEnergyNetPositive;
	}
	public void setReactiveEnergyNetPositive(double reactiveEnergyNetPositive) {
		ReactiveEnergyNetPositive = reactiveEnergyNetPositive;
	}
	public double getReactiveEnergyNetNegative() {
		return ReactiveEnergyNetNegative;
	}
	public void setReactiveEnergyNetNegative(double reactiveEnergyNetNegative) {
		ReactiveEnergyNetNegative = reactiveEnergyNetNegative;
	}
	public double getApparentEnergy() {
		return ApparentEnergy;
	}
	public void setApparentEnergy(double apparentEnergy) {
		ApparentEnergy = apparentEnergy;
	}
	
	
	
	
}
