/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelAcuRevProductionMeterEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double AveragePhaseVoltage;
	private double PhaseAVoltage;
	private double PhaseBVoltage;
	private double PhaseCVoltage;
	private double AverageLineVoltage;
	private double LineVoltageAB;
	private double LineVoltageBC;
	private double LineVoltageCA;
	private double Frequency;
	private double TotalCurrent;
	private double PhaseACurrent;
	private double PhaseBCurrent;
	private double PhaseCCurrent;
	private double TotalRealPower;
	private double PhaseAPower;
	private double PhaseBPower;
	private double PhaseCPower;
	private double TotalApparentPower;
	private double PhaseAApparentPower;
	private double PhaseBApparentPower;
	private double PhaseCApparentPower;
	private double TotalReactivePower;
	private double PhaseAReactivePower;
	private double PhaseBReactivePower;
	private double PhaseCReactivePower;
	private double TotalPowerFactor;
	private double PhaseAPowerFactor;
	private double PhaseBPowerFactor;
	private double PhaseCPowerFactor;
	private double NeutralCurrent;
	private double Temperature;
	private double TotalExportedEnergy;
	private double PhaseAExportedEnergy;
	private double PhaseBExportedEnergy;
	private double PhaseCExportedEnergy;
	private double TotalImportedEnergy;
	private double PhaseAImportedEnergy;
	private double PhaseBImportedEnergy;
	private double PhaseCImportedEnergy;
	private double TotalExportedApparentEnergy;
	private double PhaseAExportedApparentEnergy;
	private double PhaseBExportedApparentEnergy;
	private double PhaseCExportedApparentEnergy;
	private double TotalImportedApparentEnergy;
	private double PhaseAImportedApparentEnergy;
	private double PhaseBImportedApparentEnergy;
	private double PhaseCImportedApparentEnergy;
	private double TotalPowerDemand;
	private double TotalReactivePowerDemand;
	private double TotalApparentPowerDemand;
	private double PhaseACurrentDemand;
	private double PhaseBCurrentDemand;
	private double PhaseCCurrentDemand;
	
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
	public double getAveragePhaseVoltage() {
		return AveragePhaseVoltage;
	}
	public void setAveragePhaseVoltage(double averagePhaseVoltage) {
		AveragePhaseVoltage = averagePhaseVoltage;
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
	public double getAverageLineVoltage() {
		return AverageLineVoltage;
	}
	public void setAverageLineVoltage(double averageLineVoltage) {
		AverageLineVoltage = averageLineVoltage;
	}
	public double getLineVoltageAB() {
		return LineVoltageAB;
	}
	public void setLineVoltageAB(double lineVoltageAB) {
		LineVoltageAB = lineVoltageAB;
	}
	public double getLineVoltageBC() {
		return LineVoltageBC;
	}
	public void setLineVoltageBC(double lineVoltageBC) {
		LineVoltageBC = lineVoltageBC;
	}
	public double getLineVoltageCA() {
		return LineVoltageCA;
	}
	public void setLineVoltageCA(double lineVoltageCA) {
		LineVoltageCA = lineVoltageCA;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getTotalCurrent() {
		return TotalCurrent;
	}
	public void setTotalCurrent(double totalCurrent) {
		TotalCurrent = totalCurrent;
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
	public double getTotalRealPower() {
		return TotalRealPower;
	}
	public void setTotalRealPower(double totalRealPower) {
		TotalRealPower = totalRealPower;
	}
	public double getPhaseAPower() {
		return PhaseAPower;
	}
	public void setPhaseAPower(double phaseAPower) {
		PhaseAPower = phaseAPower;
	}
	public double getPhaseBPower() {
		return PhaseBPower;
	}
	public void setPhaseBPower(double phaseBPower) {
		PhaseBPower = phaseBPower;
	}
	public double getPhaseCPower() {
		return PhaseCPower;
	}
	public void setPhaseCPower(double phaseCPower) {
		PhaseCPower = phaseCPower;
	}
	public double getTotalApparentPower() {
		return TotalApparentPower;
	}
	public void setTotalApparentPower(double totalApparentPower) {
		TotalApparentPower = totalApparentPower;
	}
	public double getPhaseAApparentPower() {
		return PhaseAApparentPower;
	}
	public void setPhaseAApparentPower(double phaseAApparentPower) {
		PhaseAApparentPower = phaseAApparentPower;
	}
	public double getPhaseBApparentPower() {
		return PhaseBApparentPower;
	}
	public void setPhaseBApparentPower(double phaseBApparentPower) {
		PhaseBApparentPower = phaseBApparentPower;
	}
	public double getPhaseCApparentPower() {
		return PhaseCApparentPower;
	}
	public void setPhaseCApparentPower(double phaseCApparentPower) {
		PhaseCApparentPower = phaseCApparentPower;
	}
	public double getTotalReactivePower() {
		return TotalReactivePower;
	}
	public void setTotalReactivePower(double totalReactivePower) {
		TotalReactivePower = totalReactivePower;
	}
	public double getPhaseAReactivePower() {
		return PhaseAReactivePower;
	}
	public void setPhaseAReactivePower(double phaseAReactivePower) {
		PhaseAReactivePower = phaseAReactivePower;
	}
	public double getPhaseBReactivePower() {
		return PhaseBReactivePower;
	}
	public void setPhaseBReactivePower(double phaseBReactivePower) {
		PhaseBReactivePower = phaseBReactivePower;
	}
	public double getPhaseCReactivePower() {
		return PhaseCReactivePower;
	}
	public void setPhaseCReactivePower(double phaseCReactivePower) {
		PhaseCReactivePower = phaseCReactivePower;
	}
	public double getTotalPowerFactor() {
		return TotalPowerFactor;
	}
	public void setTotalPowerFactor(double totalPowerFactor) {
		TotalPowerFactor = totalPowerFactor;
	}
	public double getPhaseAPowerFactor() {
		return PhaseAPowerFactor;
	}
	public void setPhaseAPowerFactor(double phasePowerFactor) {
		PhaseAPowerFactor = phasePowerFactor;
	}
	public double getPhaseBPowerFactor() {
		return PhaseBPowerFactor;
	}
	public void setPhaseBPowerFactor(double phaseBPowerFactor) {
		PhaseBPowerFactor = phaseBPowerFactor;
	}
	public double getPhaseCPowerFactor() {
		return PhaseCPowerFactor;
	}
	public void setPhaseCPowerFactor(double phaseCPowerFactor) {
		PhaseCPowerFactor = phaseCPowerFactor;
	}
	public double getNeutralCurrent() {
		return NeutralCurrent;
	}
	public void setNeutralCurrent(double neutralCurrent) {
		NeutralCurrent = neutralCurrent;
	}
	public double getTemperature() {
		return Temperature;
	}
	public void setTemperature(double temperature) {
		Temperature = temperature;
	}
	public double getTotalExportedEnergy() {
		return TotalExportedEnergy;
	}
	public void setTotalExportedEnergy(double totalExportedEnergy) {
		TotalExportedEnergy = totalExportedEnergy;
	}
	public double getPhaseAExportedEnergy() {
		return PhaseAExportedEnergy;
	}
	public void setPhaseAExportedEnergy(double phaseAExportedEnergy) {
		PhaseAExportedEnergy = phaseAExportedEnergy;
	}
	public double getPhaseBExportedEnergy() {
		return PhaseBExportedEnergy;
	}
	public void setPhaseBExportedEnergy(double phaseBExportedEnergy) {
		PhaseBExportedEnergy = phaseBExportedEnergy;
	}
	public double getPhaseCExportedEnergy() {
		return PhaseCExportedEnergy;
	}
	public void setPhaseCExportedEnergy(double phaseCExportedEnergy) {
		PhaseCExportedEnergy = phaseCExportedEnergy;
	}
	public double getTotalImportedEnergy() {
		return TotalImportedEnergy;
	}
	public void setTotalImportedEnergy(double totalImportedEnergy) {
		TotalImportedEnergy = totalImportedEnergy;
	}
	public double getPhaseAImportedEnergy() {
		return PhaseAImportedEnergy;
	}
	public void setPhaseAImportedEnergy(double phaseAImportedEnergy) {
		PhaseAImportedEnergy = phaseAImportedEnergy;
	}
	public double getPhaseBImportedEnergy() {
		return PhaseBImportedEnergy;
	}
	public void setPhaseBImportedEnergy(double phaseBImportedEnergy) {
		PhaseBImportedEnergy = phaseBImportedEnergy;
	}
	public double getPhaseCImportedEnergy() {
		return PhaseCImportedEnergy;
	}
	public void setPhaseCImportedEnergy(double phaseCImportedEnergy) {
		PhaseCImportedEnergy = phaseCImportedEnergy;
	}
	public double getTotalExportedApparentEnergy() {
		return TotalExportedApparentEnergy;
	}
	public void setTotalExportedApparentEnergy(double totalExportedApparentEnergy) {
		TotalExportedApparentEnergy = totalExportedApparentEnergy;
	}
	public double getPhaseAExportedApparentEnergy() {
		return PhaseAExportedApparentEnergy;
	}
	public void setPhaseAExportedApparentEnergy(double phaseAExportedApparentEnergy) {
		PhaseAExportedApparentEnergy = phaseAExportedApparentEnergy;
	}
	public double getPhaseBExportedApparentEnergy() {
		return PhaseBExportedApparentEnergy;
	}
	public void setPhaseBExportedApparentEnergy(double phaseBExportedApparentEnergy) {
		PhaseBExportedApparentEnergy = phaseBExportedApparentEnergy;
	}
	public double getPhaseCExportedApparentEnergy() {
		return PhaseCExportedApparentEnergy;
	}
	public void setPhaseCExportedApparentEnergy(double phaseCExportedApparentEnergy) {
		PhaseCExportedApparentEnergy = phaseCExportedApparentEnergy;
	}
	public double getTotalImportedApparentEnergy() {
		return TotalImportedApparentEnergy;
	}
	public void setTotalImportedApparentEnergy(double totalImportedApparentEnergy) {
		TotalImportedApparentEnergy = totalImportedApparentEnergy;
	}
	public double getPhaseAImportedApparentEnergy() {
		return PhaseAImportedApparentEnergy;
	}
	public void setPhaseAImportedApparentEnergy(double phaseAImportedApparentEnergy) {
		PhaseAImportedApparentEnergy = phaseAImportedApparentEnergy;
	}
	public double getPhaseBImportedApparentEnergy() {
		return PhaseBImportedApparentEnergy;
	}
	public void setPhaseBImportedApparentEnergy(double phaseBImportedApparentEnergy) {
		PhaseBImportedApparentEnergy = phaseBImportedApparentEnergy;
	}
	public double getPhaseCImportedApparentEnergy() {
		return PhaseCImportedApparentEnergy;
	}
	public void setPhaseCImportedApparentEnergy(double phaseCImportedApparentEnergy) {
		PhaseCImportedApparentEnergy = phaseCImportedApparentEnergy;
	}
	public double getTotalPowerDemand() {
		return TotalPowerDemand;
	}
	public void setTotalPowerDemand(double totalPowerDemand) {
		TotalPowerDemand = totalPowerDemand;
	}
	public double getTotalReactivePowerDemand() {
		return TotalReactivePowerDemand;
	}
	public void setTotalReactivePowerDemand(double totalReactivePowerDemand) {
		TotalReactivePowerDemand = totalReactivePowerDemand;
	}
	public double getTotalApparentPowerDemand() {
		return TotalApparentPowerDemand;
	}
	public void setTotalApparentPowerDemand(double totalApparentPowerDemand) {
		TotalApparentPowerDemand = totalApparentPowerDemand;
	}
	public double getPhaseACurrentDemand() {
		return PhaseACurrentDemand;
	}
	public void setPhaseACurrentDemand(double phaseACurrentDemand) {
		PhaseACurrentDemand = phaseACurrentDemand;
	}
	public double getPhaseBCurrentDemand() {
		return PhaseBCurrentDemand;
	}
	public void setPhaseBCurrentDemand(double phaseBCurrentDemand) {
		PhaseBCurrentDemand = phaseBCurrentDemand;
	}
	public double getPhaseCCurrentDemand() {
		return PhaseCCurrentDemand;
	}
	public void setPhaseCCurrentDemand(double phaseCCurrentDemand) {
		PhaseCCurrentDemand = phaseCCurrentDemand;
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
