/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelAbbTrio500tkOutdEntity extends ModelBaseEntity {
	
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double RemoteOnOff;
	private double ResetbyHand;
	private double DynamicModePowerFactorSetPoint;
	private double PermanentModePowerFactorSetPoint;
	private double DynamicModeActivePowerSetPoint;
	private double PermanentModeActivePowerSetPoint;
	private double InverterGridReactivePower;
	private double InverterGridVoltage;
	private double InverterGridCurrent;
	private double GlobalState;
	private double AlarmState;
	private double DCDCConverterState;
	private double DCACConverterState;
	private double DailyEnergy;
	private double TotalEnergy;
	private double WeeklyEnergy;
	private double MonthlyEnergy;
	private double YearlyEnergy;
	private double MeanGridVoltage;
	private double MeanOutputCurrent;
	private double OutputActivePower;
	private double ReactivePower;
	private double PowerFactor;
	private double MeanGridFrequency;
	private double ActivePoweronDCInput1;
	private double VoltageonDCInput1;
	private double CurrentonDCInput1;
	private double ActivePoweronDCInput2;
	private double VoltageonDCInput2;
	private double CurrentonDCInput2;
	private double InternalTemperature;
	private double InverterTemperature;
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
	public double getRemoteOnOff() {
		return RemoteOnOff;
	}
	public void setRemoteOnOff(double remoteOnOff) {
		RemoteOnOff = remoteOnOff;
	}
	public double getResetbyHand() {
		return ResetbyHand;
	}
	public void setResetbyHand(double resetbyHand) {
		ResetbyHand = resetbyHand;
	}
	public double getDynamicModePowerFactorSetPoint() {
		return DynamicModePowerFactorSetPoint;
	}
	public void setDynamicModePowerFactorSetPoint(double dynamicModePowerFactorSetPoint) {
		DynamicModePowerFactorSetPoint = dynamicModePowerFactorSetPoint;
	}
	public double getPermanentModePowerFactorSetPoint() {
		return PermanentModePowerFactorSetPoint;
	}
	public void setPermanentModePowerFactorSetPoint(double permanentModePowerFactorSetPoint) {
		PermanentModePowerFactorSetPoint = permanentModePowerFactorSetPoint;
	}
	public double getDynamicModeActivePowerSetPoint() {
		return DynamicModeActivePowerSetPoint;
	}
	public void setDynamicModeActivePowerSetPoint(double dynamicModeActivePowerSetPoint) {
		DynamicModeActivePowerSetPoint = dynamicModeActivePowerSetPoint;
	}
	public double getPermanentModeActivePowerSetPoint() {
		return PermanentModeActivePowerSetPoint;
	}
	public void setPermanentModeActivePowerSetPoint(double permanentModeActivePowerSetPoint) {
		PermanentModeActivePowerSetPoint = permanentModeActivePowerSetPoint;
	}
	public double getInverterGridReactivePower() {
		return InverterGridReactivePower;
	}
	public void setInverterGridReactivePower(double inverterGridReactivePower) {
		InverterGridReactivePower = inverterGridReactivePower;
	}
	public double getInverterGridVoltage() {
		return InverterGridVoltage;
	}
	public void setInverterGridVoltage(double inverterGridVoltage) {
		InverterGridVoltage = inverterGridVoltage;
	}
	public double getInverterGridCurrent() {
		return InverterGridCurrent;
	}
	public void setInverterGridCurrent(double inverterGridCurrent) {
		InverterGridCurrent = inverterGridCurrent;
	}
	public double getGlobalState() {
		return GlobalState;
	}
	public void setGlobalState(double globalState) {
		GlobalState = globalState;
	}
	public double getAlarmState() {
		return AlarmState;
	}
	public void setAlarmState(double alarmState) {
		AlarmState = alarmState;
	}
	public double getDCDCConverterState() {
		return DCDCConverterState;
	}
	public void setDCDCConverterState(double dCDCConverterState) {
		DCDCConverterState = dCDCConverterState;
	}
	public double getDCACConverterState() {
		return DCACConverterState;
	}
	public void setDCACConverterState(double dCACConverterState) {
		DCACConverterState = dCACConverterState;
	}
	public double getDailyEnergy() {
		return DailyEnergy;
	}
	public void setDailyEnergy(double dailyEnergy) {
		DailyEnergy = dailyEnergy;
	}
	public double getTotalEnergy() {
		return TotalEnergy;
	}
	public void setTotalEnergy(double totalEnergy) {
		TotalEnergy = totalEnergy;
	}
	public double getWeeklyEnergy() {
		return WeeklyEnergy;
	}
	public void setWeeklyEnergy(double weeklyEnergy) {
		WeeklyEnergy = weeklyEnergy;
	}
	public double getMonthlyEnergy() {
		return MonthlyEnergy;
	}
	public void setMonthlyEnergy(double monthlyEnergy) {
		MonthlyEnergy = monthlyEnergy;
	}
	public double getYearlyEnergy() {
		return YearlyEnergy;
	}
	public void setYearlyEnergy(double yearlyEnergy) {
		YearlyEnergy = yearlyEnergy;
	}
	public double getMeanGridVoltage() {
		return MeanGridVoltage;
	}
	public void setMeanGridVoltage(double meanGridVoltage) {
		MeanGridVoltage = meanGridVoltage;
	}
	public double getMeanOutputCurrent() {
		return MeanOutputCurrent;
	}
	public void setMeanOutputCurrent(double meanOutputCurrent) {
		MeanOutputCurrent = meanOutputCurrent;
	}
	public double getOutputActivePower() {
		return OutputActivePower;
	}
	public void setOutputActivePower(double outputActivePower) {
		OutputActivePower = outputActivePower;
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
	public double getMeanGridFrequency() {
		return MeanGridFrequency;
	}
	public void setMeanGridFrequency(double meanGridFrequency) {
		MeanGridFrequency = meanGridFrequency;
	}
	public double getActivePoweronDCInput1() {
		return ActivePoweronDCInput1;
	}
	public void setActivePoweronDCInput1(double activePoweronDCInput1) {
		ActivePoweronDCInput1 = activePoweronDCInput1;
	}
	public double getVoltageonDCInput1() {
		return VoltageonDCInput1;
	}
	public void setVoltageonDCInput1(double voltageonDCInput1) {
		VoltageonDCInput1 = voltageonDCInput1;
	}
	public double getCurrentonDCInput1() {
		return CurrentonDCInput1;
	}
	public void setCurrentonDCInput1(double currentonDCInput1) {
		CurrentonDCInput1 = currentonDCInput1;
	}
	public double getActivePoweronDCInput2() {
		return ActivePoweronDCInput2;
	}
	public void setActivePoweronDCInput2(double activePoweronDCInput2) {
		ActivePoweronDCInput2 = activePoweronDCInput2;
	}
	public double getVoltageonDCInput2() {
		return VoltageonDCInput2;
	}
	public void setVoltageonDCInput2(double voltageonDCInput2) {
		VoltageonDCInput2 = voltageonDCInput2;
	}
	public double getCurrentonDCInput2() {
		return CurrentonDCInput2;
	}
	public void setCurrentonDCInput2(double currentonDCInput2) {
		CurrentonDCInput2 = currentonDCInput2;
	}
	public double getInternalTemperature() {
		return InternalTemperature;
	}
	public void setInternalTemperature(double internalTemperature) {
		InternalTemperature = internalTemperature;
	}
	public double getInverterTemperature() {
		return InverterTemperature;
	}
	public void setInverterTemperature(double inverterTemperature) {
		InverterTemperature = inverterTemperature;
	}
	
	
}
