/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSungrowLogger1000Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double TotalNumberOfConnectedDevices;
	private double TotalFaultDeviceNumber;
	private double TotalActivePower;
	private double DailyYield;
	private double TotalReactivePower;
	private double TotalYield;
	private double DigitalInputState;
	private double PT1001;
	private double PT1002;
	private double ADC1Voltage;
	private double ADC1Current;
	private double ADC2Voltage;
	private double ADC2Current;
	private double ADC3Voltage;
	private double ADC3Current;
	private double ADC4Voltage;
	private double ADC4Current;
	private double Longitude;
	private double Latitude;
	private double MaxTotalRatedActivePower;
	private double MinTotalRatedActivePower;
	private double MaxTotalRatedReactivePower;
	private double MinTotalRatedReactivePower;
	private double ActualTotalInverterActivePower;
	private double ActualTotalInverterReactivePower;
	private double OnOffStateOfDataLogger;
	private double LockingStateOfDataLogger;
	private double MinAdjustableActivePower;
	private double MaxAdjustableActivePower;
	private double MinAdjustableReactivePower;
	private double MaxAdjustableReactivePower;
	private double RatedActivePower;
	private double RatedReactivePower;
	private double NumberOfOnGridDevices;
	private double NumberOfOffGridDevices;
	
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	
	
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
	public double getTotalNumberOfConnectedDevices() {
		return TotalNumberOfConnectedDevices;
	}
	public void setTotalNumberOfConnectedDevices(double totalNumberOfConnectedDevices) {
		TotalNumberOfConnectedDevices = totalNumberOfConnectedDevices;
	}
	public double getTotalFaultDeviceNumber() {
		return TotalFaultDeviceNumber;
	}
	public void setTotalFaultDeviceNumber(double totalFaultDeviceNumber) {
		TotalFaultDeviceNumber = totalFaultDeviceNumber;
	}
	public double getTotalActivePower() {
		return TotalActivePower;
	}
	public void setTotalActivePower(double totalActivePower) {
		TotalActivePower = totalActivePower;
	}
	public double getDailyYield() {
		return DailyYield;
	}
	public void setDailyYield(double dailyYield) {
		DailyYield = dailyYield;
	}
	public double getTotalReactivePower() {
		return TotalReactivePower;
	}
	public void setTotalReactivePower(double totalReactivePower) {
		TotalReactivePower = totalReactivePower;
	}
	public double getTotalYield() {
		return TotalYield;
	}
	public void setTotalYield(double totalYield) {
		TotalYield = totalYield;
	}
	public double getDigitalInputState() {
		return DigitalInputState;
	}
	public void setDigitalInputState(double digitalInputState) {
		DigitalInputState = digitalInputState;
	}
	public double getPT1001() {
		return PT1001;
	}
	public void setPT1001(double pT1001) {
		PT1001 = pT1001;
	}
	public double getPT1002() {
		return PT1002;
	}
	public void setPT1002(double pT1002) {
		PT1002 = pT1002;
	}
	public double getADC1Voltage() {
		return ADC1Voltage;
	}
	public void setADC1Voltage(double aDC1Voltage) {
		ADC1Voltage = aDC1Voltage;
	}
	public double getADC1Current() {
		return ADC1Current;
	}
	public void setADC1Current(double aDC1Current) {
		ADC1Current = aDC1Current;
	}
	public double getADC2Voltage() {
		return ADC2Voltage;
	}
	public void setADC2Voltage(double aDC2Voltage) {
		ADC2Voltage = aDC2Voltage;
	}
	public double getADC2Current() {
		return ADC2Current;
	}
	public void setADC2Current(double aDC2Current) {
		ADC2Current = aDC2Current;
	}
	public double getADC3Voltage() {
		return ADC3Voltage;
	}
	public void setADC3Voltage(double aDC3Voltage) {
		ADC3Voltage = aDC3Voltage;
	}
	public double getADC3Current() {
		return ADC3Current;
	}
	public void setADC3Current(double aDC3Current) {
		ADC3Current = aDC3Current;
	}
	public double getADC4Voltage() {
		return ADC4Voltage;
	}
	public void setADC4Voltage(double aDC4Voltage) {
		ADC4Voltage = aDC4Voltage;
	}
	public double getADC4Current() {
		return ADC4Current;
	}
	public void setADC4Current(double aDC4Current) {
		ADC4Current = aDC4Current;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	public double getMaxTotalRatedActivePower() {
		return MaxTotalRatedActivePower;
	}
	public void setMaxTotalRatedActivePower(double maxTotalRatedActivePower) {
		MaxTotalRatedActivePower = maxTotalRatedActivePower;
	}
	public double getMinTotalRatedActivePower() {
		return MinTotalRatedActivePower;
	}
	public void setMinTotalRatedActivePower(double minTotalRatedActivePower) {
		MinTotalRatedActivePower = minTotalRatedActivePower;
	}
	public double getMaxTotalRatedReactivePower() {
		return MaxTotalRatedReactivePower;
	}
	public void setMaxTotalRatedReactivePower(double maxTotalRatedReactivePower) {
		MaxTotalRatedReactivePower = maxTotalRatedReactivePower;
	}
	public double getMinTotalRatedReactivePower() {
		return MinTotalRatedReactivePower;
	}
	public void setMinTotalRatedReactivePower(double minTotalRatedReactivePower) {
		MinTotalRatedReactivePower = minTotalRatedReactivePower;
	}
	public double getActualTotalInverterActivePower() {
		return ActualTotalInverterActivePower;
	}
	public void setActualTotalInverterActivePower(double actualTotalInverterActivePower) {
		ActualTotalInverterActivePower = actualTotalInverterActivePower;
	}
	public double getActualTotalInverterReactivePower() {
		return ActualTotalInverterReactivePower;
	}
	public void setActualTotalInverterReactivePower(double actualTotalInverterReactivePower) {
		ActualTotalInverterReactivePower = actualTotalInverterReactivePower;
	}
	public double getOnOffStateOfDataLogger() {
		return OnOffStateOfDataLogger;
	}
	public void setOnOffStateOfDataLogger(double onOffStateOfDataLogger) {
		OnOffStateOfDataLogger = onOffStateOfDataLogger;
	}
	public double getLockingStateOfDataLogger() {
		return LockingStateOfDataLogger;
	}
	public void setLockingStateOfDataLogger(double lockingStateOfDataLogger) {
		LockingStateOfDataLogger = lockingStateOfDataLogger;
	}
	public double getMinAdjustableActivePower() {
		return MinAdjustableActivePower;
	}
	public void setMinAdjustableActivePower(double minAdjustableActivePower) {
		MinAdjustableActivePower = minAdjustableActivePower;
	}
	public double getMaxAdjustableActivePower() {
		return MaxAdjustableActivePower;
	}
	public void setMaxAdjustableActivePower(double maxAdjustableActivePower) {
		MaxAdjustableActivePower = maxAdjustableActivePower;
	}
	public double getMinAdjustableReactivePower() {
		return MinAdjustableReactivePower;
	}
	public void setMinAdjustableReactivePower(double minAdjustableReactivePower) {
		MinAdjustableReactivePower = minAdjustableReactivePower;
	}
	public double getMaxAdjustableReactivePower() {
		return MaxAdjustableReactivePower;
	}
	public void setMaxAdjustableReactivePower(double maxAdjustableReactivePower) {
		MaxAdjustableReactivePower = maxAdjustableReactivePower;
	}
	public double getRatedActivePower() {
		return RatedActivePower;
	}
	public void setRatedActivePower(double ratedActivePower) {
		RatedActivePower = ratedActivePower;
	}
	public double getRatedReactivePower() {
		return RatedReactivePower;
	}
	public void setRatedReactivePower(double ratedReactivePower) {
		RatedReactivePower = ratedReactivePower;
	}
	public double getNumberOfOnGridDevices() {
		return NumberOfOnGridDevices;
	}
	public void setNumberOfOnGridDevices(double numberOfOnGridDevices) {
		NumberOfOnGridDevices = numberOfOnGridDevices;
	}
	public double getNumberOfOffGridDevices() {
		return NumberOfOffGridDevices;
	}
	public void setNumberOfOffGridDevices(double numberOfOffGridDevices) {
		NumberOfOffGridDevices = numberOfOffGridDevices;
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
