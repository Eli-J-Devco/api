/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelElsterA1700Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double PhaseAVoltage;
	private double PhaseBVoltage;
	private double PhaseCVoltage;
	private double ABLineVoltage;
	private double BCLineVoltage;
	private double CALineVoltage; 
	private double PhaseACurrent;
	private double PhaseBCurrent;
	private double PhaseCCurrent;
	private double PhaseAActivePower;
	private double PhaseBActivePower;
	private double PhaseCActivePower;
	private double TotalActivePower;
	private double TotalReactivePower;
	private double TotalApparentPower;
	private double TotalPowerFactor;
	private double GridFrequency;
	private double TotalForwardActiveEnergy;
	private double TotalReverseActiveEnergy;
	private double TotalForwardReactiveEnergy;
	private double TotalReverseReactiveEnergy;
	private double PhaseAForwardActivePower;
	private double PhaseBForwardActivePower;
	private double PhaseCForwardActivePower;
	private double PhaseAReverseActivePower;
	private double PhaseBReverseActivePower;
	private double PhaseCReverseActivePower;
	private double PhaseAForwardReactivePower;
	private double PhaseBForwardReactivePower;
	private double PhaseCForwardReactivePower;
	private double PhaseAReverseReactivePower;
	private double PhaseBReverseReactivePower;
	private double PhaseCReverseReactivePower;
	private double FlatForwardActiveEnergy;
	private double PeakForwardActiveEnergy;
	private double ValleyForwardActiveEnergy;
	private double FlatReverseActiveEnergy;
	private double PeakReverseActiveEnergy;
	private double ValleyReverseActiveEnergy;
	
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
private int enable_alert;
	
	
	
	public int getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(int enable_alert) {
		this.enable_alert = enable_alert;
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
	public double getABLineVoltage() {
		return ABLineVoltage;
	}
	public void setABLineVoltage(double aBLineVoltage) {
		ABLineVoltage = aBLineVoltage;
	}
	public double getBCLineVoltage() {
		return BCLineVoltage;
	}
	public void setBCLineVoltage(double bCLineVoltage) {
		BCLineVoltage = bCLineVoltage;
	}
	public double getCALineVoltage() {
		return CALineVoltage;
	}
	public void setCALineVoltage(double cALineVoltage) {
		CALineVoltage = cALineVoltage;
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
	public double getPhaseAActivePower() {
		return PhaseAActivePower;
	}
	public void setPhaseAActivePower(double phaseAActivePower) {
		PhaseAActivePower = phaseAActivePower;
	}
	public double getPhaseBActivePower() {
		return PhaseBActivePower;
	}
	public void setPhaseBActivePower(double phaseBActivePower) {
		PhaseBActivePower = phaseBActivePower;
	}
	public double getPhaseCActivePower() {
		return PhaseCActivePower;
	}
	public void setPhaseCActivePower(double phaseCActivePower) {
		PhaseCActivePower = phaseCActivePower;
	}
	public double getTotalActivePower() {
		return TotalActivePower;
	}
	public void setTotalActivePower(double totalActivePower) {
		TotalActivePower = totalActivePower;
	}
	public double getTotalReactivePower() {
		return TotalReactivePower;
	}
	public void setTotalReactivePower(double totalReactivePower) {
		TotalReactivePower = totalReactivePower;
	}
	public double getTotalApparentPower() {
		return TotalApparentPower;
	}
	public void setTotalApparentPower(double totalApparentPower) {
		TotalApparentPower = totalApparentPower;
	}
	public double getTotalPowerFactor() {
		return TotalPowerFactor;
	}
	public void setTotalPowerFactor(double totalPowerFactor) {
		TotalPowerFactor = totalPowerFactor;
	}
	public double getGridFrequency() {
		return GridFrequency;
	}
	public void setGridFrequency(double gridFrequency) {
		GridFrequency = gridFrequency;
	}
	public double getTotalForwardActiveEnergy() {
		return TotalForwardActiveEnergy;
	}
	public void setTotalForwardActiveEnergy(double totalForwardActiveEnergy) {
		TotalForwardActiveEnergy = totalForwardActiveEnergy;
	}
	public double getTotalReverseActiveEnergy() {
		return TotalReverseActiveEnergy;
	}
	public void setTotalReverseActiveEnergy(double totalReverseActiveEnergy) {
		TotalReverseActiveEnergy = totalReverseActiveEnergy;
	}
	public double getTotalForwardReactiveEnergy() {
		return TotalForwardReactiveEnergy;
	}
	public void setTotalForwardReactiveEnergy(double totalForwardReactiveEnergy) {
		TotalForwardReactiveEnergy = totalForwardReactiveEnergy;
	}
	public double getTotalReverseReactiveEnergy() {
		return TotalReverseReactiveEnergy;
	}
	public void setTotalReverseReactiveEnergy(double totalReverseReactiveEnergy) {
		TotalReverseReactiveEnergy = totalReverseReactiveEnergy;
	}
	public double getPhaseAForwardActivePower() {
		return PhaseAForwardActivePower;
	}
	public void setPhaseAForwardActivePower(double phaseAForwardActivePower) {
		PhaseAForwardActivePower = phaseAForwardActivePower;
	}
	public double getPhaseBForwardActivePower() {
		return PhaseBForwardActivePower;
	}
	public void setPhaseBForwardActivePower(double phaseBForwardActivePower) {
		PhaseBForwardActivePower = phaseBForwardActivePower;
	}
	public double getPhaseCForwardActivePower() {
		return PhaseCForwardActivePower;
	}
	public void setPhaseCForwardActivePower(double phaseCForwardActivePower) {
		PhaseCForwardActivePower = phaseCForwardActivePower;
	}
	public double getPhaseAReverseActivePower() {
		return PhaseAReverseActivePower;
	}
	public void setPhaseAReverseActivePower(double phaseAReverseActivePower) {
		PhaseAReverseActivePower = phaseAReverseActivePower;
	}
	public double getPhaseBReverseActivePower() {
		return PhaseBReverseActivePower;
	}
	public void setPhaseBReverseActivePower(double phaseBReverseActivePower) {
		PhaseBReverseActivePower = phaseBReverseActivePower;
	}
	public double getPhaseCReverseActivePower() {
		return PhaseCReverseActivePower;
	}
	public void setPhaseCReverseActivePower(double phaseCReverseActivePower) {
		PhaseCReverseActivePower = phaseCReverseActivePower;
	}
	public double getPhaseAForwardReactivePower() {
		return PhaseAForwardReactivePower;
	}
	public void setPhaseAForwardReactivePower(double phaseAForwardReactivePower) {
		PhaseAForwardReactivePower = phaseAForwardReactivePower;
	}
	public double getPhaseBForwardReactivePower() {
		return PhaseBForwardReactivePower;
	}
	public void setPhaseBForwardReactivePower(double phaseBForwardReactivePower) {
		PhaseBForwardReactivePower = phaseBForwardReactivePower;
	}
	public double getPhaseCForwardReactivePower() {
		return PhaseCForwardReactivePower;
	}
	public void setPhaseCForwardReactivePower(double phaseCForwardReactivePower) {
		PhaseCForwardReactivePower = phaseCForwardReactivePower;
	}
	public double getPhaseAReverseReactivePower() {
		return PhaseAReverseReactivePower;
	}
	public void setPhaseAReverseReactivePower(double phaseAReverseReactivePower) {
		PhaseAReverseReactivePower = phaseAReverseReactivePower;
	}
	public double getPhaseBReverseReactivePower() {
		return PhaseBReverseReactivePower;
	}
	public void setPhaseBReverseReactivePower(double phaseBReverseReactivePower) {
		PhaseBReverseReactivePower = phaseBReverseReactivePower;
	}
	public double getPhaseCReverseReactivePower() {
		return PhaseCReverseReactivePower;
	}
	public void setPhaseCReverseReactivePower(double phaseCReverseReactivePower) {
		PhaseCReverseReactivePower = phaseCReverseReactivePower;
	}
	public double getFlatForwardActiveEnergy() {
		return FlatForwardActiveEnergy;
	}
	public void setFlatForwardActiveEnergy(double flatForwardActiveEnergy) {
		FlatForwardActiveEnergy = flatForwardActiveEnergy;
	}
	public double getPeakForwardActiveEnergy() {
		return PeakForwardActiveEnergy;
	}
	public void setPeakForwardActiveEnergy(double peakForwardActiveEnergy) {
		PeakForwardActiveEnergy = peakForwardActiveEnergy;
	}
	public double getValleyForwardActiveEnergy() {
		return ValleyForwardActiveEnergy;
	}
	public void setValleyForwardActiveEnergy(double valleyForwardActiveEnergy) {
		ValleyForwardActiveEnergy = valleyForwardActiveEnergy;
	}
	public double getFlatReverseActiveEnergy() {
		return FlatReverseActiveEnergy;
	}
	public void setFlatReverseActiveEnergy(double flatReverseActiveEnergy) {
		FlatReverseActiveEnergy = flatReverseActiveEnergy;
	}
	public double getPeakReverseActiveEnergy() {
		return PeakReverseActiveEnergy;
	}
	public void setPeakReverseActiveEnergy(double peakReverseActiveEnergy) {
		PeakReverseActiveEnergy = peakReverseActiveEnergy;
	}
	public double getValleyReverseActiveEnergy() {
		return ValleyReverseActiveEnergy;
	}
	public void setValleyReverseActiveEnergy(double valleyReverseActiveEnergy) {
		ValleyReverseActiveEnergy = valleyReverseActiveEnergy;
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
	
}
