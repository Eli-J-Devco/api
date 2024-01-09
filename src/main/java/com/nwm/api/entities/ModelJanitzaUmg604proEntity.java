/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelJanitzaUmg604proEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double PhaseAVoltage;
	private double PhaseBVoltage;
	private double PhaseCVoltage;
	private double ABVoltage;
	private double BCVoltage;
	private double CAVoltage;
	private double PhaseACurrent;
	private double PhaseBCurrent;
	private double PhaseCCurrent;
	private double TotalCurrent;
	private double PhaseAPower;
	private double PhaseBPower;
	private double PhaseCPower;
	private double TotalPower;
	private double PhaseAApparentPower;
	private double PhaseBApparentPower;
	private double PhaseCApparentPower;
	private double TotalApparentPower;
	private double PhaseAReactivePower;
	private double PhaseBReactivePower;
	private double PhaseCReactivePower;
	private double TotalReactivePower;
	private double PhaseAPowerFactor;
	private double PhaseBPowerFactor;
	private double PhaseCPowerFactor;
	private double PowerFactor;
	private double Frequency;
	private double TotalForwardActiveEnergy;
	private double TotalReverseActiveEnergy;
	private double TotalForwardReactiveEnergy;
	private double TotalReverseReactiveEnergy;
	private double PhaseAForwardActiveEnergy;
	private double PhaseBForwardActiveEnergy;
	private double PhaseCForwardActiveEnergy;
	private double PhaseAReverseActiveEnergy;
	private double PhaseBReverseActiveEnergy;
	private double PhaseCReverseActiveEnergy;
	private double PhaseAForwardReactiveEnergy;
	private double PhaseBForwardReactiveEnergy;
	private double PhaseCForwardReactiveEnergy;
	private double PhaseAReverseReactiveEnergy;
	private double PhaseBReverseReactiveEnergy;
	private double PhaseCReverseReactiveEnergy;
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
	public double getABVoltage() {
		return ABVoltage;
	}
	public void setABVoltage(double aBVoltage) {
		ABVoltage = aBVoltage;
	}
	public double getBCVoltage() {
		return BCVoltage;
	}
	public void setBCVoltage(double bCVoltage) {
		BCVoltage = bCVoltage;
	}
	public double getCAVoltage() {
		return CAVoltage;
	}
	public void setCAVoltage(double cAVoltage) {
		CAVoltage = cAVoltage;
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
	public double getTotalCurrent() {
		return TotalCurrent;
	}
	public void setTotalCurrent(double totalCurrent) {
		TotalCurrent = totalCurrent;
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
	public double getTotalPower() {
		return TotalPower;
	}
	public void setTotalPower(double totalPower) {
		TotalPower = totalPower;
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
	public double getTotalApparentPower() {
		return TotalApparentPower;
	}
	public void setTotalApparentPower(double totalApparentPower) {
		TotalApparentPower = totalApparentPower;
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
	public double getTotalReactivePower() {
		return TotalReactivePower;
	}
	public void setTotalReactivePower(double totalReactivePower) {
		TotalReactivePower = totalReactivePower;
	}
	public double getPhaseAPowerFactor() {
		return PhaseAPowerFactor;
	}
	public void setPhaseAPowerFactor(double phaseAPowerFactor) {
		PhaseAPowerFactor = phaseAPowerFactor;
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
	public double getPowerFactor() {
		return PowerFactor;
	}
	public void setPowerFactor(double powerFactor) {
		PowerFactor = powerFactor;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
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
	public double getPhaseAForwardActiveEnergy() {
		return PhaseAForwardActiveEnergy;
	}
	public void setPhaseAForwardActiveEnergy(double phaseAForwardActiveEnergy) {
		PhaseAForwardActiveEnergy = phaseAForwardActiveEnergy;
	}
	public double getPhaseBForwardActiveEnergy() {
		return PhaseBForwardActiveEnergy;
	}
	public void setPhaseBForwardActiveEnergy(double phaseBForwardActiveEnergy) {
		PhaseBForwardActiveEnergy = phaseBForwardActiveEnergy;
	}
	public double getPhaseCForwardActiveEnergy() {
		return PhaseCForwardActiveEnergy;
	}
	public void setPhaseCForwardActiveEnergy(double phaseCForwardActiveEnergy) {
		PhaseCForwardActiveEnergy = phaseCForwardActiveEnergy;
	}
	public double getPhaseAReverseActiveEnergy() {
		return PhaseAReverseActiveEnergy;
	}
	public void setPhaseAReverseActiveEnergy(double phaseAReverseActiveEnergy) {
		PhaseAReverseActiveEnergy = phaseAReverseActiveEnergy;
	}
	public double getPhaseBReverseActiveEnergy() {
		return PhaseBReverseActiveEnergy;
	}
	public void setPhaseBReverseActiveEnergy(double phaseBReverseActiveEnergy) {
		PhaseBReverseActiveEnergy = phaseBReverseActiveEnergy;
	}
	public double getPhaseCReverseActiveEnergy() {
		return PhaseCReverseActiveEnergy;
	}
	public void setPhaseCReverseActiveEnergy(double phaseCReverseActiveEnergy) {
		PhaseCReverseActiveEnergy = phaseCReverseActiveEnergy;
	}
	public double getPhaseAForwardReactiveEnergy() {
		return PhaseAForwardReactiveEnergy;
	}
	public void setPhaseAForwardReactiveEnergy(double phaseAForwardReactiveEnergy) {
		PhaseAForwardReactiveEnergy = phaseAForwardReactiveEnergy;
	}
	public double getPhaseBForwardReactiveEnergy() {
		return PhaseBForwardReactiveEnergy;
	}
	public void setPhaseBForwardReactiveEnergy(double phaseBForwardReactiveEnergy) {
		PhaseBForwardReactiveEnergy = phaseBForwardReactiveEnergy;
	}
	public double getPhaseCForwardReactiveEnergy() {
		return PhaseCForwardReactiveEnergy;
	}
	public void setPhaseCForwardReactiveEnergy(double phaseCForwardReactiveEnergy) {
		PhaseCForwardReactiveEnergy = phaseCForwardReactiveEnergy;
	}
	public double getPhaseAReverseReactiveEnergy() {
		return PhaseAReverseReactiveEnergy;
	}
	public void setPhaseAReverseReactiveEnergy(double phaseAReverseReactiveEnergy) {
		PhaseAReverseReactiveEnergy = phaseAReverseReactiveEnergy;
	}
	public double getPhaseBReverseReactiveEnergy() {
		return PhaseBReverseReactiveEnergy;
	}
	public void setPhaseBReverseReactiveEnergy(double phaseBReverseReactiveEnergy) {
		PhaseBReverseReactiveEnergy = phaseBReverseReactiveEnergy;
	}
	public double getPhaseCReverseReactiveEnergy() {
		return PhaseCReverseReactiveEnergy;
	}
	public void setPhaseCReverseReactiveEnergy(double phaseCReverseReactiveEnergy) {
		PhaseCReverseReactiveEnergy = phaseCReverseReactiveEnergy;
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
