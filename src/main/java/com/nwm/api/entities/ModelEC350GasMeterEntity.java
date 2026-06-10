/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelEC350GasMeterEntity extends ModelBaseEntity {
	private double Pressure;
	private double Temperature;
	private double FlowRateCorrectedVolume;
	private double BatteryVoltage;
	private double CaseTemperature;
	private double P1HighAlarmLimit;
	private double P1LowAlarmLimit;
	private double FlowRateHighAlarmLimit;
	private double BasePressure;
	private double AtmosphericPressure;
	private double GasEnergyValue;
	private double TotalCorrectionValue;
	private double FirmwareVersion;
	private double CorrectedVolume;
	private double UncorrectedVolume;
	private double Energy;
	private double MasterAlarmStatus;
	private double VolumeSensor1Alarm;
	private double VolumeSensor2Alarm;
	private double BatteryLowAlarm;
	private double P1PressureLowAlarm;
	private double P1HighPressureAlarm;
	private double TemperatureLowAlarm;
	private double TemperatureHighAlarm;
	private double FlowRateHighAlarm;
	
	public double getPressure() {
		return Pressure;
	}
	public void setPressure(double pressure) {
		Pressure = pressure;
	}
	public double getTemperature() {
		return Temperature;
	}
	public void setTemperature(double temperature) {
		Temperature = temperature;
	}
	public double getFlowRateCorrectedVolume() {
		return FlowRateCorrectedVolume;
	}
	public void setFlowRateCorrectedVolume(double flowRateCorrectedVolume) {
		FlowRateCorrectedVolume = flowRateCorrectedVolume;
	}
	public double getBatteryVoltage() {
		return BatteryVoltage;
	}
	public void setBatteryVoltage(double batteryVoltage) {
		BatteryVoltage = batteryVoltage;
	}
	public double getCaseTemperature() {
		return CaseTemperature;
	}
	public void setCaseTemperature(double caseTemperature) {
		CaseTemperature = caseTemperature;
	}
	public double getP1HighAlarmLimit() {
		return P1HighAlarmLimit;
	}
	public void setP1HighAlarmLimit(double p1HighAlarmLimit) {
		P1HighAlarmLimit = p1HighAlarmLimit;
	}
	public double getP1LowAlarmLimit() {
		return P1LowAlarmLimit;
	}
	public void setP1LowAlarmLimit(double p1LowAlarmLimit) {
		P1LowAlarmLimit = p1LowAlarmLimit;
	}
	public double getFlowRateHighAlarmLimit() {
		return FlowRateHighAlarmLimit;
	}
	public void setFlowRateHighAlarmLimit(double flowRateHighAlarmLimit) {
		FlowRateHighAlarmLimit = flowRateHighAlarmLimit;
	}
	public double getBasePressure() {
		return BasePressure;
	}
	public void setBasePressure(double basePressure) {
		BasePressure = basePressure;
	}
	public double getAtmosphericPressure() {
		return AtmosphericPressure;
	}
	public void setAtmosphericPressure(double atmosphericPressure) {
		AtmosphericPressure = atmosphericPressure;
	}
	public double getGasEnergyValue() {
		return GasEnergyValue;
	}
	public void setGasEnergyValue(double gasEnergyValue) {
		GasEnergyValue = gasEnergyValue;
	}
	public double getTotalCorrectionValue() {
		return TotalCorrectionValue;
	}
	public void setTotalCorrectionValue(double totalCorrectionValue) {
		TotalCorrectionValue = totalCorrectionValue;
	}
	public double getFirmwareVersion() {
		return FirmwareVersion;
	}
	public void setFirmwareVersion(double firmwareVersion) {
		FirmwareVersion = firmwareVersion;
	}
	public double getCorrectedVolume() {
		return CorrectedVolume;
	}
	public void setCorrectedVolume(double correctedVolume) {
		CorrectedVolume = correctedVolume;
	}
	public double getUncorrectedVolume() {
		return UncorrectedVolume;
	}
	public void setUncorrectedVolume(double uncorrectedVolume) {
		UncorrectedVolume = uncorrectedVolume;
	}
	public double getEnergy() {
		return Energy;
	}
	public void setEnergy(double energy) {
		Energy = energy;
	}
	public double getMasterAlarmStatus() {
		return MasterAlarmStatus;
	}
	public void setMasterAlarmStatus(double masterAlarmStatus) {
		MasterAlarmStatus = masterAlarmStatus;
	}
	public double getVolumeSensor1Alarm() {
		return VolumeSensor1Alarm;
	}
	public void setVolumeSensor1Alarm(double volumeSensor1Alarm) {
		VolumeSensor1Alarm = volumeSensor1Alarm;
	}
	public double getVolumeSensor2Alarm() {
		return VolumeSensor2Alarm;
	}
	public void setVolumeSensor2Alarm(double volumeSensor2Alarm) {
		VolumeSensor2Alarm = volumeSensor2Alarm;
	}
	public double getBatteryLowAlarm() {
		return BatteryLowAlarm;
	}
	public void setBatteryLowAlarm(double batteryLowAlarm) {
		BatteryLowAlarm = batteryLowAlarm;
	}
	public double getP1PressureLowAlarm() {
		return P1PressureLowAlarm;
	}
	public void setP1PressureLowAlarm(double p1PressureLowAlarm) {
		P1PressureLowAlarm = p1PressureLowAlarm;
	}
	public double getP1HighPressureAlarm() {
		return P1HighPressureAlarm;
	}
	public void setP1HighPressureAlarm(double p1HighPressureAlarm) {
		P1HighPressureAlarm = p1HighPressureAlarm;
	}
	public double getTemperatureLowAlarm() {
		return TemperatureLowAlarm;
	}
	public void setTemperatureLowAlarm(double temperatureLowAlarm) {
		TemperatureLowAlarm = temperatureLowAlarm;
	}
	public double getTemperatureHighAlarm() {
		return TemperatureHighAlarm;
	}
	public void setTemperatureHighAlarm(double temperatureHighAlarm) {
		TemperatureHighAlarm = temperatureHighAlarm;
	}
	public double getFlowRateHighAlarm() {
		return FlowRateHighAlarm;
	}
	public void setFlowRateHighAlarm(double flowRateHighAlarm) {
		FlowRateHighAlarm = flowRateHighAlarm;
	}
}
