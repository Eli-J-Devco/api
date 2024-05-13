/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSolectriaINV00SLC3146Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double DCVoltage;
	private double RealACPower;
	private double ACGridFrequency;
	private double ACPowerStageCurrent;
	private double L1toL2ACVoltage;
	private double L2toL3ACVoltage;
	private double L1toL3ACVoltage;
	private double PhaseSequence;
	private double ACEnergy;
	private double OnGridHours;
	private double FanOntimeHours;
	private double ACContactorCycles;
	private double SlaveID;
	private double InformativeAlarms;
	
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
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
	public double getDCVoltage() {
		return DCVoltage;
	}
	public void setDCVoltage(double dCVoltage) {
		DCVoltage = dCVoltage;
	}
	public double getRealACPower() {
		return RealACPower;
	}
	public void setRealACPower(double realACPower) {
		RealACPower = realACPower;
	}
	public double getACGridFrequency() {
		return ACGridFrequency;
	}
	public void setACGridFrequency(double aCGridFrequency) {
		ACGridFrequency = aCGridFrequency;
	}
	public double getACPowerStageCurrent() {
		return ACPowerStageCurrent;
	}
	public void setACPowerStageCurrent(double aCPowerStageCurrent) {
		ACPowerStageCurrent = aCPowerStageCurrent;
	}
	public double getL1toL2ACVoltage() {
		return L1toL2ACVoltage;
	}
	public void setL1toL2ACVoltage(double l1toL2ACVoltage) {
		L1toL2ACVoltage = l1toL2ACVoltage;
	}
	public double getL2toL3ACVoltage() {
		return L2toL3ACVoltage;
	}
	public void setL2toL3ACVoltage(double l2toL3ACVoltage) {
		L2toL3ACVoltage = l2toL3ACVoltage;
	}
	public double getL1toL3ACVoltage() {
		return L1toL3ACVoltage;
	}
	public void setL1toL3ACVoltage(double l1toL3ACVoltage) {
		L1toL3ACVoltage = l1toL3ACVoltage;
	}
	public double getPhaseSequence() {
		return PhaseSequence;
	}
	public void setPhaseSequence(double phaseSequence) {
		PhaseSequence = phaseSequence;
	}
	public double getACEnergy() {
		return ACEnergy;
	}
	public void setACEnergy(double aCEnergy) {
		ACEnergy = aCEnergy;
	}
	public double getOnGridHours() {
		return OnGridHours;
	}
	public void setOnGridHours(double onGridHours) {
		OnGridHours = onGridHours;
	}
	public double getFanOntimeHours() {
		return FanOntimeHours;
	}
	public void setFanOntimeHours(double fanOntimeHours) {
		FanOntimeHours = fanOntimeHours;
	}
	public double getACContactorCycles() {
		return ACContactorCycles;
	}
	public void setACContactorCycles(double aCContactorCycles) {
		ACContactorCycles = aCContactorCycles;
	}
	public double getSlaveID() {
		return SlaveID;
	}
	public void setSlaveID(double slaveID) {
		SlaveID = slaveID;
	}
	public double getInformativeAlarms() {
		return InformativeAlarms;
	}
	public void setInformativeAlarms(double informativeAlarms) {
		InformativeAlarms = informativeAlarms;
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
	public double getMeasuredProduction() {
		return MeasuredProduction;
	}
	public void setMeasuredProduction(double measuredProduction) {
		MeasuredProduction = measuredProduction;
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
