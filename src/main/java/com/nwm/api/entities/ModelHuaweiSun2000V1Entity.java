/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelHuaweiSun2000V1Entity extends ModelBaseEntity {

	private double ModelID;
	private double NumberOfPVStrings;
	private double NumberOfMPPTrackers;
	private double RatedPower;
	private double MaximumActivePower;
	private double MaximumApparentPower;
	private double MaximumReactivePowerFedToTheGrid;
	private double MaximumReactivePowerAbsorbedFromTheGrid;
	private double State1;
	private double State2;
	private double State3;
	private double Alarm1;
	private double Alarm2;
	private double Alarm3;
	private double PV1Voltage;
	private double PV1Current;
	private double PV2Voltage;
	private double PV2Current;
	private double PV3Voltage;
	private double PV3Current;
	private double PV4Voltage;
	private double PV4Current;
	private double InputPower;
	private double LineVoltageBetweenPhasesAAndB;
	private double LineVoltageBetweenPhasesBAndC;
	private double LineVoltageBetweenPhasesCAndA;
	private double PhaseAVoltage;
	private double PhaseBVoltage;
	private double PhaseCVoltage;
	private double PhaseACurrent;
	private double PhaseBCurrent;
	private double PhaseCCurrent;
	private double PeakActivePowerOfCurrentDay;
	private double ActivePower;
	private double ReactivePower;
	private double PowerFactor;
	private double GridFrequency;
	private double Efficiency;
	private double InternalTemperature;
	private double InsulationResistance;
	private double DeviceStatus;
	private double FaultCode;
	private double StartupTime;
	private double ShutdownTime;
	private double AccumulatedEnergyYield;
	private double DailyEnergyYield;
	public double getModelID() {
		return ModelID;
	}
	public void setModelID(double modelID) {
		ModelID = modelID;
	}
	public double getNumberOfPVStrings() {
		return NumberOfPVStrings;
	}
	public void setNumberOfPVStrings(double numberOfPVStrings) {
		NumberOfPVStrings = numberOfPVStrings;
	}
	public double getNumberOfMPPTrackers() {
		return NumberOfMPPTrackers;
	}
	public void setNumberOfMPPTrackers(double numberOfMPPTrackers) {
		NumberOfMPPTrackers = numberOfMPPTrackers;
	}
	public double getRatedPower() {
		return RatedPower;
	}
	public void setRatedPower(double ratedPower) {
		RatedPower = ratedPower;
	}
	public double getMaximumActivePower() {
		return MaximumActivePower;
	}
	public void setMaximumActivePower(double maximumActivePower) {
		MaximumActivePower = maximumActivePower;
	}
	public double getMaximumApparentPower() {
		return MaximumApparentPower;
	}
	public void setMaximumApparentPower(double maximumApparentPower) {
		MaximumApparentPower = maximumApparentPower;
	}
	public double getMaximumReactivePowerFedToTheGrid() {
		return MaximumReactivePowerFedToTheGrid;
	}
	public void setMaximumReactivePowerFedToTheGrid(double maximumReactivePowerFedToTheGrid) {
		MaximumReactivePowerFedToTheGrid = maximumReactivePowerFedToTheGrid;
	}
	public double getMaximumReactivePowerAbsorbedFromTheGrid() {
		return MaximumReactivePowerAbsorbedFromTheGrid;
	}
	public void setMaximumReactivePowerAbsorbedFromTheGrid(double maximumReactivePowerAbsorbedFromTheGrid) {
		MaximumReactivePowerAbsorbedFromTheGrid = maximumReactivePowerAbsorbedFromTheGrid;
	}
	public double getState1() {
		return State1;
	}
	public void setState1(double state1) {
		State1 = state1;
	}
	public double getState2() {
		return State2;
	}
	public void setState2(double state2) {
		State2 = state2;
	}
	public double getState3() {
		return State3;
	}
	public void setState3(double state3) {
		State3 = state3;
	}
	public double getAlarm1() {
		return Alarm1;
	}
	public void setAlarm1(double alarm1) {
		Alarm1 = alarm1;
	}
	public double getAlarm2() {
		return Alarm2;
	}
	public void setAlarm2(double alarm2) {
		Alarm2 = alarm2;
	}
	public double getAlarm3() {
		return Alarm3;
	}
	public void setAlarm3(double alarm3) {
		Alarm3 = alarm3;
	}
	public double getPV1Voltage() {
		return PV1Voltage;
	}
	public void setPV1Voltage(double pV1Voltage) {
		PV1Voltage = pV1Voltage;
	}
	public double getPV1Current() {
		return PV1Current;
	}
	public void setPV1Current(double pV1Current) {
		PV1Current = pV1Current;
	}
	public double getPV2Voltage() {
		return PV2Voltage;
	}
	public void setPV2Voltage(double pV2Voltage) {
		PV2Voltage = pV2Voltage;
	}
	public double getPV2Current() {
		return PV2Current;
	}
	public void setPV2Current(double pV2Current) {
		PV2Current = pV2Current;
	}
	public double getPV3Voltage() {
		return PV3Voltage;
	}
	public void setPV3Voltage(double pV3Voltage) {
		PV3Voltage = pV3Voltage;
	}
	public double getPV3Current() {
		return PV3Current;
	}
	public void setPV3Current(double pV3Current) {
		PV3Current = pV3Current;
	}
	public double getPV4Voltage() {
		return PV4Voltage;
	}
	public void setPV4Voltage(double pV4Voltage) {
		PV4Voltage = pV4Voltage;
	}
	public double getPV4Current() {
		return PV4Current;
	}
	public void setPV4Current(double pV4Current) {
		PV4Current = pV4Current;
	}
	public double getInputPower() {
		return InputPower;
	}
	public void setInputPower(double inputPower) {
		InputPower = inputPower;
	}
	public double getLineVoltageBetweenPhasesAAndB() {
		return LineVoltageBetweenPhasesAAndB;
	}
	public void setLineVoltageBetweenPhasesAAndB(double lineVoltageBetweenPhasesAAndB) {
		LineVoltageBetweenPhasesAAndB = lineVoltageBetweenPhasesAAndB;
	}
	public double getLineVoltageBetweenPhasesBAndC() {
		return LineVoltageBetweenPhasesBAndC;
	}
	public void setLineVoltageBetweenPhasesBAndC(double lineVoltageBetweenPhasesBAndC) {
		LineVoltageBetweenPhasesBAndC = lineVoltageBetweenPhasesBAndC;
	}
	public double getLineVoltageBetweenPhasesCAndA() {
		return LineVoltageBetweenPhasesCAndA;
	}
	public void setLineVoltageBetweenPhasesCAndA(double lineVoltageBetweenPhasesCAndA) {
		LineVoltageBetweenPhasesCAndA = lineVoltageBetweenPhasesCAndA;
	}
	public double getPhaseAVoltage() {
		return PhaseAVoltage;
	}
	public void setPhaseAVoltage(double phaseAVoltage) {
		PhaseAVoltage = phaseAVoltage;
	}
	public double getPhaseBVoltage() {
		return PhaseBVoltage;
	}
	public void setPhaseBVoltage(double phaseBVoltage) {
		PhaseBVoltage = phaseBVoltage;
	}
	public double getPhaseCVoltage() {
		return PhaseCVoltage;
	}
	public void setPhaseCVoltage(double phaseCVoltage) {
		PhaseCVoltage = phaseCVoltage;
	}
	public double getPhaseACurrent() {
		return PhaseACurrent;
	}
	public void setPhaseACurrent(double phaseACurrent) {
		PhaseACurrent = phaseACurrent;
	}
	public double getPhaseBCurrent() {
		return PhaseBCurrent;
	}
	public void setPhaseBCurrent(double phaseBCurrent) {
		PhaseBCurrent = phaseBCurrent;
	}
	public double getPhaseCCurrent() {
		return PhaseCCurrent;
	}
	public void setPhaseCCurrent(double phaseCCurrent) {
		PhaseCCurrent = phaseCCurrent;
	}
	public double getPeakActivePowerOfCurrentDay() {
		return PeakActivePowerOfCurrentDay;
	}
	public void setPeakActivePowerOfCurrentDay(double peakActivePowerOfCurrentDay) {
		PeakActivePowerOfCurrentDay = peakActivePowerOfCurrentDay;
	}
	public double getActivePower() {
		return ActivePower;
	}
	public void setActivePower(double activePower) {
		ActivePower = activePower;
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
	public double getGridFrequency() {
		return GridFrequency;
	}
	public void setGridFrequency(double gridFrequency) {
		GridFrequency = gridFrequency;
	}
	public double getEfficiency() {
		return Efficiency;
	}
	public void setEfficiency(double efficiency) {
		Efficiency = efficiency;
	}
	public double getInternalTemperature() {
		return InternalTemperature;
	}
	public void setInternalTemperature(double internalTemperature) {
		InternalTemperature = internalTemperature;
	}
	public double getInsulationResistance() {
		return InsulationResistance;
	}
	public void setInsulationResistance(double insulationResistance) {
		InsulationResistance = insulationResistance;
	}
	public double getDeviceStatus() {
		return DeviceStatus;
	}
	public void setDeviceStatus(double deviceStatus) {
		DeviceStatus = deviceStatus;
	}
	public double getFaultCode() {
		return FaultCode;
	}
	public void setFaultCode(double faultCode) {
		FaultCode = faultCode;
	}
	public double getStartupTime() {
		return StartupTime;
	}
	public void setStartupTime(double startupTime) {
		StartupTime = startupTime;
	}
	public double getShutdownTime() {
		return ShutdownTime;
	}
	public void setShutdownTime(double shutdownTime) {
		ShutdownTime = shutdownTime;
	}
	public double getAccumulatedEnergyYield() {
		return AccumulatedEnergyYield;
	}
	public void setAccumulatedEnergyYield(double accumulatedEnergyYield) {
		AccumulatedEnergyYield = accumulatedEnergyYield;
	}
	public double getDailyEnergyYield() {
		return DailyEnergyYield;
	}
	public void setDailyEnergyYield(double dailyEnergyYield) {
		DailyEnergyYield = dailyEnergyYield;
	}
	
	
	
	
}
