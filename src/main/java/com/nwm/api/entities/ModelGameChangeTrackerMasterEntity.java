/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelGameChangeTrackerMasterEntity extends ModelBaseEntity {
	private double HardwareVersionMajor;
	private double HardwareVersionMinor;
	private double HardwareVersionFix;
	private double SoftwareVersionMajor;
	private double SoftwareVersionMinor;
	private double SoftwareVersionFix;
	private double Year;
	private double Month;
	private double Day;
	private double Hour;
	private double Minute;
	private double Second;
	private double TargetAngle;
	private double AverageTrackerAngle;
	private double AverageTrackerBatteryVoltage;
	private double StowEnvelopeEastLimit;
	private double StowEnvelopeWestLimit;
	private double NumberoftrackerNodes;
	private double WindInstant;
	private double WindPeak;
	private double WindAverage;
	private double SnowWaterDepth;
	private double SnowWaterBaseline;
	private double EnabledStowModes;
	private double ActiveStowMode;
	private double BatteryVoltage;
	private double SystemAlarms;
	private double StatusFlags;
	private double TrackingMode;
	private double HailStowResumeTimer;
	private double Nocommunicationcounter;
	private double TrackingDisabledCounter;
	private double EStopPressedCounter;
	private double OvercurrentLimitCounter;
	private double LowBatteryCounter;
	private double CriticalBatteryCounter;
	private double NoMotionCounter;
	private double WrongDirectionCounter;
	private double BatteryOverVoltageCounter;
	private double FailedEastCounter;
	private double HighPriorityStowCounter;
	private double LowPriorityStowCounter;
	private double MissedStatusCounter;
	private double NoDataCounter;
	private double NotChargingCounter;
	private double BODResetCounter;
	private double FailedWestCounter;
	private double AngleDeviationCounter;
	private double BatteryMaintenance;
	private double LowTempStow;
	
	public double getHardwareVersionMajor() {
		return HardwareVersionMajor;
	}
	public void setHardwareVersionMajor(double hardwareVersionMajor) {
		HardwareVersionMajor = hardwareVersionMajor;
	}
	public double getHardwareVersionMinor() {
		return HardwareVersionMinor;
	}
	public void setHardwareVersionMinor(double hardwareVersionMinor) {
		HardwareVersionMinor = hardwareVersionMinor;
	}
	public double getHardwareVersionFix() {
		return HardwareVersionFix;
	}
	public void setHardwareVersionFix(double hardwareVersionFix) {
		HardwareVersionFix = hardwareVersionFix;
	}
	public double getSoftwareVersionMajor() {
		return SoftwareVersionMajor;
	}
	public void setSoftwareVersionMajor(double softwareVersionMajor) {
		SoftwareVersionMajor = softwareVersionMajor;
	}
	public double getSoftwareVersionMinor() {
		return SoftwareVersionMinor;
	}
	public void setSoftwareVersionMinor(double softwareVersionMinor) {
		SoftwareVersionMinor = softwareVersionMinor;
	}
	public double getSoftwareVersionFix() {
		return SoftwareVersionFix;
	}
	public void setSoftwareVersionFix(double softwareVersionFix) {
		SoftwareVersionFix = softwareVersionFix;
	}
	public double getYear() {
		return Year;
	}
	public void setYear(double year) {
		Year = year;
	}
	public double getMonth() {
		return Month;
	}
	public void setMonth(double month) {
		Month = month;
	}
	public double getDay() {
		return Day;
	}
	public void setDay(double day) {
		Day = day;
	}
	public double getHour() {
		return Hour;
	}
	public void setHour(double hour) {
		Hour = hour;
	}
	public double getMinute() {
		return Minute;
	}
	public void setMinute(double minute) {
		Minute = minute;
	}
	public double getSecond() {
		return Second;
	}
	public void setSecond(double second) {
		Second = second;
	}
	public double getTargetAngle() {
		return TargetAngle;
	}
	public void setTargetAngle(double targetAngle) {
		TargetAngle = targetAngle;
	}
	public double getAverageTrackerAngle() {
		return AverageTrackerAngle;
	}
	public void setAverageTrackerAngle(double averageTrackerAngle) {
		AverageTrackerAngle = averageTrackerAngle;
	}
	public double getAverageTrackerBatteryVoltage() {
		return AverageTrackerBatteryVoltage;
	}
	public void setAverageTrackerBatteryVoltage(double averageTrackerBatteryVoltage) {
		AverageTrackerBatteryVoltage = averageTrackerBatteryVoltage;
	}
	public double getStowEnvelopeEastLimit() {
		return StowEnvelopeEastLimit;
	}
	public void setStowEnvelopeEastLimit(double stowEnvelopeEastLimit) {
		StowEnvelopeEastLimit = stowEnvelopeEastLimit;
	}
	public double getStowEnvelopeWestLimit() {
		return StowEnvelopeWestLimit;
	}
	public void setStowEnvelopeWestLimit(double stowEnvelopeWestLimit) {
		StowEnvelopeWestLimit = stowEnvelopeWestLimit;
	}
	public double getNumberoftrackerNodes() {
		return NumberoftrackerNodes;
	}
	public void setNumberoftrackerNodes(double numberoftrackerNodes) {
		NumberoftrackerNodes = numberoftrackerNodes;
	}
	public double getWindInstant() {
		return WindInstant;
	}
	public void setWindInstant(double windInstant) {
		WindInstant = windInstant;
	}
	public double getWindPeak() {
		return WindPeak;
	}
	public void setWindPeak(double windPeak) {
		WindPeak = windPeak;
	}
	public double getWindAverage() {
		return WindAverage;
	}
	public void setWindAverage(double windAverage) {
		WindAverage = windAverage;
	}
	public double getSnowWaterDepth() {
		return SnowWaterDepth;
	}
	public void setSnowWaterDepth(double snowWaterDepth) {
		SnowWaterDepth = snowWaterDepth;
	}
	public double getSnowWaterBaseline() {
		return SnowWaterBaseline;
	}
	public void setSnowWaterBaseline(double snowWaterBaseline) {
		SnowWaterBaseline = snowWaterBaseline;
	}
	public double getEnabledStowModes() {
		return EnabledStowModes;
	}
	public void setEnabledStowModes(double enabledStowModes) {
		EnabledStowModes = enabledStowModes;
	}
	public double getActiveStowMode() {
		return ActiveStowMode;
	}
	public void setActiveStowMode(double activeStowMode) {
		ActiveStowMode = activeStowMode;
	}
	public double getBatteryVoltage() {
		return BatteryVoltage;
	}
	public void setBatteryVoltage(double batteryVoltage) {
		BatteryVoltage = batteryVoltage;
	}
	public double getSystemAlarms() {
		return SystemAlarms;
	}
	public void setSystemAlarms(double systemAlarms) {
		SystemAlarms = systemAlarms;
	}
	public double getStatusFlags() {
		return StatusFlags;
	}
	public void setStatusFlags(double statusFlags) {
		StatusFlags = statusFlags;
	}
	public double getTrackingMode() {
		return TrackingMode;
	}
	public void setTrackingMode(double trackingMode) {
		TrackingMode = trackingMode;
	}
	public double getHailStowResumeTimer() {
		return HailStowResumeTimer;
	}
	public void setHailStowResumeTimer(double hailStowResumeTimer) {
		HailStowResumeTimer = hailStowResumeTimer;
	}
	public double getNocommunicationcounter() {
		return Nocommunicationcounter;
	}
	public void setNocommunicationcounter(double nocommunicationcounter) {
		Nocommunicationcounter = nocommunicationcounter;
	}
	public double getTrackingDisabledCounter() {
		return TrackingDisabledCounter;
	}
	public void setTrackingDisabledCounter(double trackingDisabledCounter) {
		TrackingDisabledCounter = trackingDisabledCounter;
	}
	public double getEStopPressedCounter() {
		return EStopPressedCounter;
	}
	public void setEStopPressedCounter(double eStopPressedCounter) {
		EStopPressedCounter = eStopPressedCounter;
	}
	public double getOvercurrentLimitCounter() {
		return OvercurrentLimitCounter;
	}
	public void setOvercurrentLimitCounter(double overcurrentLimitCounter) {
		OvercurrentLimitCounter = overcurrentLimitCounter;
	}
	public double getLowBatteryCounter() {
		return LowBatteryCounter;
	}
	public void setLowBatteryCounter(double lowBatteryCounter) {
		LowBatteryCounter = lowBatteryCounter;
	}
	public double getCriticalBatteryCounter() {
		return CriticalBatteryCounter;
	}
	public void setCriticalBatteryCounter(double criticalBatteryCounter) {
		CriticalBatteryCounter = criticalBatteryCounter;
	}
	public double getNoMotionCounter() {
		return NoMotionCounter;
	}
	public void setNoMotionCounter(double noMotionCounter) {
		NoMotionCounter = noMotionCounter;
	}
	public double getWrongDirectionCounter() {
		return WrongDirectionCounter;
	}
	public void setWrongDirectionCounter(double wrongDirectionCounter) {
		WrongDirectionCounter = wrongDirectionCounter;
	}
	public double getBatteryOverVoltageCounter() {
		return BatteryOverVoltageCounter;
	}
	public void setBatteryOverVoltageCounter(double batteryOverVoltageCounter) {
		BatteryOverVoltageCounter = batteryOverVoltageCounter;
	}
	public double getFailedEastCounter() {
		return FailedEastCounter;
	}
	public void setFailedEastCounter(double failedEastCounter) {
		FailedEastCounter = failedEastCounter;
	}
	public double getHighPriorityStowCounter() {
		return HighPriorityStowCounter;
	}
	public void setHighPriorityStowCounter(double highPriorityStowCounter) {
		HighPriorityStowCounter = highPriorityStowCounter;
	}
	public double getLowPriorityStowCounter() {
		return LowPriorityStowCounter;
	}
	public void setLowPriorityStowCounter(double lowPriorityStowCounter) {
		LowPriorityStowCounter = lowPriorityStowCounter;
	}
	public double getMissedStatusCounter() {
		return MissedStatusCounter;
	}
	public void setMissedStatusCounter(double missedStatusCounter) {
		MissedStatusCounter = missedStatusCounter;
	}
	public double getNoDataCounter() {
		return NoDataCounter;
	}
	public void setNoDataCounter(double noDataCounter) {
		NoDataCounter = noDataCounter;
	}
	public double getNotChargingCounter() {
		return NotChargingCounter;
	}
	public void setNotChargingCounter(double notChargingCounter) {
		NotChargingCounter = notChargingCounter;
	}
	public double getBODResetCounter() {
		return BODResetCounter;
	}
	public void setBODResetCounter(double bODResetCounter) {
		BODResetCounter = bODResetCounter;
	}
	public double getFailedWestCounter() {
		return FailedWestCounter;
	}
	public void setFailedWestCounter(double failedWestCounter) {
		FailedWestCounter = failedWestCounter;
	}
	public double getAngleDeviationCounter() {
		return AngleDeviationCounter;
	}
	public void setAngleDeviationCounter(double angleDeviationCounter) {
		AngleDeviationCounter = angleDeviationCounter;
	}
	public double getBatteryMaintenance() {
		return BatteryMaintenance;
	}
	public void setBatteryMaintenance(double batteryMaintenance) {
		BatteryMaintenance = batteryMaintenance;
	}
	public double getLowTempStow() {
		return LowTempStow;
	}
	public void setLowTempStow(double lowTempStow) {
		LowTempStow = lowTempStow;
	}
	
}
