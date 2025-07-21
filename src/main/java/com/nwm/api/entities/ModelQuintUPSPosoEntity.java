/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelQuintUPSPosoEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double OutputVoltage;
	private double StateofCharge;
	private double ResidualBackupTime;
	private double BattteryVoltage;
	private double BatteryTemperature;
	private double ResidualLifeTime;
	private double BatteryDischargingCurrent;
	private double DetectedBatteryUnits;
	private double 	BatteryNominalCapacity;
	private double BatteryModeStatus;
	private double ShutdownEventStatus;
	private double BatteryChargingStatus;
	private double 	ActualAlarm;
	private double ActualWarning;
	private double ChargeTimeout;
	private double 	RemoteStatus;
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
	public double getOutputVoltage() {
		return OutputVoltage;
	}
	public void setOutputVoltage(double outputVoltage) {
		OutputVoltage = outputVoltage;
	}
	public double getStateofCharge() {
		return StateofCharge;
	}
	public void setStateofCharge(double stateofCharge) {
		StateofCharge = stateofCharge;
	}
	public double getResidualBackupTime() {
		return ResidualBackupTime;
	}
	public void setResidualBackupTime(double residualBackupTime) {
		ResidualBackupTime = residualBackupTime;
	}
	public double getBattteryVoltage() {
		return BattteryVoltage;
	}
	public void setBattteryVoltage(double battteryVoltage) {
		BattteryVoltage = battteryVoltage;
	}
	public double getBatteryTemperature() {
		return BatteryTemperature;
	}
	public void setBatteryTemperature(double batteryTemperature) {
		BatteryTemperature = batteryTemperature;
	}
	public double getResidualLifeTime() {
		return ResidualLifeTime;
	}
	public void setResidualLifeTime(double residualLifeTime) {
		ResidualLifeTime = residualLifeTime;
	}
	public double getBatteryDischargingCurrent() {
		return BatteryDischargingCurrent;
	}
	public void setBatteryDischargingCurrent(double batteryDischargingCurrent) {
		BatteryDischargingCurrent = batteryDischargingCurrent;
	}
	public double getDetectedBatteryUnits() {
		return DetectedBatteryUnits;
	}
	public void setDetectedBatteryUnits(double detectedBatteryUnits) {
		DetectedBatteryUnits = detectedBatteryUnits;
	}
	public double getBatteryNominalCapacity() {
		return BatteryNominalCapacity;
	}
	public void setBatteryNominalCapacity(double batteryNominalCapacity) {
		BatteryNominalCapacity = batteryNominalCapacity;
	}
	public double getBatteryModeStatus() {
		return BatteryModeStatus;
	}
	public void setBatteryModeStatus(double batteryModeStatus) {
		BatteryModeStatus = batteryModeStatus;
	}
	public double getShutdownEventStatus() {
		return ShutdownEventStatus;
	}
	public void setShutdownEventStatus(double shutdownEventStatus) {
		ShutdownEventStatus = shutdownEventStatus;
	}
	public double getBatteryChargingStatus() {
		return BatteryChargingStatus;
	}
	public void setBatteryChargingStatus(double batteryChargingStatus) {
		BatteryChargingStatus = batteryChargingStatus;
	}
	public double getActualAlarm() {
		return ActualAlarm;
	}
	public void setActualAlarm(double actualAlarm) {
		ActualAlarm = actualAlarm;
	}
	public double getActualWarning() {
		return ActualWarning;
	}
	public void setActualWarning(double actualWarning) {
		ActualWarning = actualWarning;
	}
	public double getChargeTimeout() {
		return ChargeTimeout;
	}
	public void setChargeTimeout(double chargeTimeout) {
		ChargeTimeout = chargeTimeout;
	}
	public double getRemoteStatus() {
		return RemoteStatus;
	}
	public void setRemoteStatus(double remoteStatus) {
		RemoteStatus = remoteStatus;
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
