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
	private double PV5Voltage;
	private double PV5Current;
	private double PV6Voltage;
	private double PV6Current;
	private double PV7Voltage;
	private double PV7Current;
	private double PV8Voltage;
	private double PV8Current;
	private double PV9Voltage;
	private double PV9Current;
	private double PV10Voltage;
	private double PV10Current;
	private double PV11Voltage;
	private double PV11Current;
	private double PV12Voltage;
	private double PV12Current;
	private double PV13Voltage;
	private double PV13Current;
	private double PV14Voltage;
	private double PV14Current;
	private double PV15Voltage;
	private double PV15Current;
	private double PV16Voltage;
	private double PV16Current;
	private double PV17Voltage;
	private double PV17Current;
	private double PV18Voltage;
	private double PV18Current;
	private double PV19Voltage;
	private double PV19Current;
	private double PV20Voltage;
	private double PV20Current;
	private double PV21Voltage;
	private double PV21Current;
	private double PV22Voltage;
	private double PV22Current;
	private double PV23Voltage;
	private double PV23Current;
	private double PV24Voltage;
	private double PV24Current;
	private double PV25Voltage;
	private double PV25Current;
	private double PV26Voltage;
	private double PV26Current;
	private double PV27Voltage;
	private double PV27Current;
	private double PV28Voltage;
	private double PV28Current;
	private double MPPT1Voltage;
	private double MPPT1Current;
	private double MPPT1Power;
	private double MPPT2Voltage;
	private double MPPT2Current;
	private double MPPT2Power;
	private double MPPT3Voltage;
	private double MPPT3Current;
	private double MPPT3Power;
	private double MPPT4Voltage;
	private double MPPT4Current;
	private double MPPT4Power;
	private double MPPT5Voltage;
	private double MPPT5Current;
	private double MPPT5Power;
	private double MPPT6Voltage;
	private double MPPT6Current;
	private double MPPT6Power;

	
	public double getMPPT1Voltage() {
		return MPPT1Voltage;
	}
	public void setMPPT1Voltage(double mPPT1Voltage) {
		MPPT1Voltage = mPPT1Voltage;
	}
	public double getMPPT1Current() {
		return MPPT1Current;
	}
	public void setMPPT1Current(double mPPT1Current) {
		MPPT1Current = mPPT1Current;
	}
	public double getMPPT1Power() {
		return MPPT1Power;
	}
	public void setMPPT1Power(double mPPT1Power) {
		MPPT1Power = mPPT1Power;
	}
	public double getMPPT2Voltage() {
		return MPPT2Voltage;
	}
	public void setMPPT2Voltage(double mPPT2Voltage) {
		MPPT2Voltage = mPPT2Voltage;
	}
	public double getMPPT2Current() {
		return MPPT2Current;
	}
	public void setMPPT2Current(double mPPT2Current) {
		MPPT2Current = mPPT2Current;
	}
	public double getMPPT2Power() {
		return MPPT2Power;
	}
	public void setMPPT2Power(double mPPT2Power) {
		MPPT2Power = mPPT2Power;
	}
	public double getMPPT3Voltage() {
		return MPPT3Voltage;
	}
	public void setMPPT3Voltage(double mPPT3Voltage) {
		MPPT3Voltage = mPPT3Voltage;
	}
	public double getMPPT3Current() {
		return MPPT3Current;
	}
	public void setMPPT3Current(double mPPT3Current) {
		MPPT3Current = mPPT3Current;
	}
	public double getMPPT3Power() {
		return MPPT3Power;
	}
	public void setMPPT3Power(double mPPT3Power) {
		MPPT3Power = mPPT3Power;
	}
	public double getMPPT4Voltage() {
		return MPPT4Voltage;
	}
	public void setMPPT4Voltage(double mPPT4Voltage) {
		MPPT4Voltage = mPPT4Voltage;
	}
	public double getMPPT4Current() {
		return MPPT4Current;
	}
	public void setMPPT4Current(double mPPT4Current) {
		MPPT4Current = mPPT4Current;
	}
	public double getMPPT4Power() {
		return MPPT4Power;
	}
	public void setMPPT4Power(double mPPT4Power) {
		MPPT4Power = mPPT4Power;
	}
	public double getMPPT5Voltage() {
		return MPPT5Voltage;
	}
	public void setMPPT5Voltage(double mPPT5Voltage) {
		MPPT5Voltage = mPPT5Voltage;
	}
	public double getMPPT5Current() {
		return MPPT5Current;
	}
	public void setMPPT5Current(double mPPT5Current) {
		MPPT5Current = mPPT5Current;
	}
	public double getMPPT5Power() {
		return MPPT5Power;
	}
	public void setMPPT5Power(double mPPT5Power) {
		MPPT5Power = mPPT5Power;
	}
	public double getMPPT6Voltage() {
		return MPPT6Voltage;
	}
	public void setMPPT6Voltage(double mPPT6Voltage) {
		MPPT6Voltage = mPPT6Voltage;
	}
	public double getMPPT6Current() {
		return MPPT6Current;
	}
	public void setMPPT6Current(double mPPT6Current) {
		MPPT6Current = mPPT6Current;
	}
	public double getMPPT6Power() {
		return MPPT6Power;
	}
	public void setMPPT6Power(double mPPT6Power) {
		MPPT6Power = mPPT6Power;
	}
	public double getPV5Voltage() {
		return PV5Voltage;
	}
	public void setPV5Voltage(double pV5Voltage) {
		PV5Voltage = pV5Voltage;
	}
	public double getPV5Current() {
		return PV5Current;
	}
	public void setPV5Current(double pV5Current) {
		PV5Current = pV5Current;
	}
	public double getPV6Voltage() {
		return PV6Voltage;
	}
	public void setPV6Voltage(double pV6Voltage) {
		PV6Voltage = pV6Voltage;
	}
	public double getPV6Current() {
		return PV6Current;
	}
	public void setPV6Current(double pV6Current) {
		PV6Current = pV6Current;
	}
	public double getPV7Voltage() {
		return PV7Voltage;
	}
	public void setPV7Voltage(double pV7Voltage) {
		PV7Voltage = pV7Voltage;
	}
	public double getPV7Current() {
		return PV7Current;
	}
	public void setPV7Current(double pV7Current) {
		PV7Current = pV7Current;
	}
	public double getPV8Voltage() {
		return PV8Voltage;
	}
	public void setPV8Voltage(double pV8Voltage) {
		PV8Voltage = pV8Voltage;
	}
	public double getPV8Current() {
		return PV8Current;
	}
	public void setPV8Current(double pV8Current) {
		PV8Current = pV8Current;
	}
	public double getPV9Voltage() {
		return PV9Voltage;
	}
	public void setPV9Voltage(double pV9Voltage) {
		PV9Voltage = pV9Voltage;
	}
	public double getPV9Current() {
		return PV9Current;
	}
	public void setPV9Current(double pV9Current) {
		PV9Current = pV9Current;
	}
	public double getPV10Voltage() {
		return PV10Voltage;
	}
	public void setPV10Voltage(double pV10Voltage) {
		PV10Voltage = pV10Voltage;
	}
	public double getPV10Current() {
		return PV10Current;
	}
	public void setPV10Current(double pV10Current) {
		PV10Current = pV10Current;
	}
	public double getPV11Voltage() {
		return PV11Voltage;
	}
	public void setPV11Voltage(double pV11Voltage) {
		PV11Voltage = pV11Voltage;
	}
	public double getPV11Current() {
		return PV11Current;
	}
	public void setPV11Current(double pV11Current) {
		PV11Current = pV11Current;
	}
	public double getPV12Voltage() {
		return PV12Voltage;
	}
	public void setPV12Voltage(double pV12Voltage) {
		PV12Voltage = pV12Voltage;
	}
	public double getPV12Current() {
		return PV12Current;
	}
	public void setPV12Current(double pV12Current) {
		PV12Current = pV12Current;
	}
	public double getPV13Voltage() {
		return PV13Voltage;
	}
	public void setPV13Voltage(double pV13Voltage) {
		PV13Voltage = pV13Voltage;
	}
	public double getPV13Current() {
		return PV13Current;
	}
	public void setPV13Current(double pV13Current) {
		PV13Current = pV13Current;
	}
	public double getPV14Voltage() {
		return PV14Voltage;
	}
	public void setPV14Voltage(double pV14Voltage) {
		PV14Voltage = pV14Voltage;
	}
	public double getPV14Current() {
		return PV14Current;
	}
	public void setPV14Current(double pV14Current) {
		PV14Current = pV14Current;
	}
	public double getPV15Voltage() {
		return PV15Voltage;
	}
	public void setPV15Voltage(double pV15Voltage) {
		PV15Voltage = pV15Voltage;
	}
	public double getPV15Current() {
		return PV15Current;
	}
	public void setPV15Current(double pV15Current) {
		PV15Current = pV15Current;
	}
	public double getPV16Voltage() {
		return PV16Voltage;
	}
	public void setPV16Voltage(double pV16Voltage) {
		PV16Voltage = pV16Voltage;
	}
	public double getPV16Current() {
		return PV16Current;
	}
	public void setPV16Current(double pV16Current) {
		PV16Current = pV16Current;
	}
	public double getPV17Voltage() {
		return PV17Voltage;
	}
	public void setPV17Voltage(double pV17Voltage) {
		PV17Voltage = pV17Voltage;
	}
	public double getPV17Current() {
		return PV17Current;
	}
	public void setPV17Current(double pV17Current) {
		PV17Current = pV17Current;
	}
	public double getPV18Voltage() {
		return PV18Voltage;
	}
	public void setPV18Voltage(double pV18Voltage) {
		PV18Voltage = pV18Voltage;
	}
	public double getPV18Current() {
		return PV18Current;
	}
	public void setPV18Current(double pV18Current) {
		PV18Current = pV18Current;
	}
	public double getPV19Voltage() {
		return PV19Voltage;
	}
	public void setPV19Voltage(double pV19Voltage) {
		PV19Voltage = pV19Voltage;
	}
	public double getPV19Current() {
		return PV19Current;
	}
	public void setPV19Current(double pV19Current) {
		PV19Current = pV19Current;
	}
	public double getPV20Voltage() {
		return PV20Voltage;
	}
	public void setPV20Voltage(double pV20Voltage) {
		PV20Voltage = pV20Voltage;
	}
	public double getPV20Current() {
		return PV20Current;
	}
	public void setPV20Current(double pV20Current) {
		PV20Current = pV20Current;
	}
	public double getPV21Voltage() {
		return PV21Voltage;
	}
	public void setPV21Voltage(double pV21Voltage) {
		PV21Voltage = pV21Voltage;
	}
	public double getPV21Current() {
		return PV21Current;
	}
	public void setPV21Current(double pV21Current) {
		PV21Current = pV21Current;
	}
	public double getPV22Voltage() {
		return PV22Voltage;
	}
	public void setPV22Voltage(double pV22Voltage) {
		PV22Voltage = pV22Voltage;
	}
	public double getPV22Current() {
		return PV22Current;
	}
	public void setPV22Current(double pV22Current) {
		PV22Current = pV22Current;
	}
	public double getPV23Voltage() {
		return PV23Voltage;
	}
	public void setPV23Voltage(double pV23Voltage) {
		PV23Voltage = pV23Voltage;
	}
	public double getPV23Current() {
		return PV23Current;
	}
	public void setPV23Current(double pV23Current) {
		PV23Current = pV23Current;
	}
	public double getPV24Voltage() {
		return PV24Voltage;
	}
	public void setPV24Voltage(double pV24Voltage) {
		PV24Voltage = pV24Voltage;
	}
	public double getPV24Current() {
		return PV24Current;
	}
	public void setPV24Current(double pV24Current) {
		PV24Current = pV24Current;
	}
	public double getPV25Voltage() {
		return PV25Voltage;
	}
	public void setPV25Voltage(double pV25Voltage) {
		PV25Voltage = pV25Voltage;
	}
	public double getPV25Current() {
		return PV25Current;
	}
	public void setPV25Current(double pV25Current) {
		PV25Current = pV25Current;
	}
	public double getPV26Voltage() {
		return PV26Voltage;
	}
	public void setPV26Voltage(double pV26Voltage) {
		PV26Voltage = pV26Voltage;
	}
	public double getPV26Current() {
		return PV26Current;
	}
	public void setPV26Current(double pV26Current) {
		PV26Current = pV26Current;
	}
	public double getPV27Voltage() {
		return PV27Voltage;
	}
	public void setPV27Voltage(double pV27Voltage) {
		PV27Voltage = pV27Voltage;
	}
	public double getPV27Current() {
		return PV27Current;
	}
	public void setPV27Current(double pV27Current) {
		PV27Current = pV27Current;
	}
	public double getPV28Voltage() {
		return PV28Voltage;
	}
	public void setPV28Voltage(double pV28Voltage) {
		PV28Voltage = pV28Voltage;
	}
	public double getPV28Current() {
		return PV28Current;
	}
	public void setPV28Current(double pV28Current) {
		PV28Current = pV28Current;
	}
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
