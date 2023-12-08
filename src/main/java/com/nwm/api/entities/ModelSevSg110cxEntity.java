/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSevSg110cxEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double TotalYield;
	private double DailyYield;
	private double ArrayInsulationResistance;
	private double InteriorTemperature;
	private double TotalDCPower;
	private double TotalApparentPower;
	private double TotalActivePower;
	private double TotalReactivePower;
	private double TotalPowerFactor;
	private double GridFrequency;
	private double PhaseAVoltage;
	private double PhaseBVoltage;
	private double PhaseCVoltage;
	private double PhaseACurrent;
	private double PhaseBCurrent;
	private double PhaseCCurrent;
	private double FaultCode;
	private double NominalReactivePower;
	private double NominalActivePower;
	private double BusVoltage;
	private double NegativeVoltageToGround;
	private double WorkState1;
	private double WorkState2;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	private String timezone_value;
	private int totalFaultCode;
	private int totalWorkState1;
	private int totalWorkState2;
	
	
	
	
	public int getTotalFaultCode() {
		return totalFaultCode;
	}
	public void setTotalFaultCode(int totalFaultCode) {
		this.totalFaultCode = totalFaultCode;
	}
	public int getTotalWorkState1() {
		return totalWorkState1;
	}
	public void setTotalWorkState1(int totalWorkState1) {
		this.totalWorkState1 = totalWorkState1;
	}
	public int getTotalWorkState2() {
		return totalWorkState2;
	}
	public void setTotalWorkState2(int totalWorkState2) {
		this.totalWorkState2 = totalWorkState2;
	}
	public double getNominalReactivePower() {
		return NominalReactivePower;
	}
	public void setNominalReactivePower(double nominalReactivePower) {
		NominalReactivePower = nominalReactivePower;
	}
	public double getNominalActivePower() {
		return NominalActivePower;
	}
	public void setNominalActivePower(double nominalActivePower) {
		NominalActivePower = nominalActivePower;
	}
	public double getBusVoltage() {
		return BusVoltage;
	}
	public void setBusVoltage(double busVoltage) {
		BusVoltage = busVoltage;
	}
	public double getNegativeVoltageToGround() {
		return NegativeVoltageToGround;
	}
	public void setNegativeVoltageToGround(double negativeVoltageToGround) {
		NegativeVoltageToGround = negativeVoltageToGround;
	}
	public double getWorkState1() {
		return WorkState1;
	}
	public void setWorkState1(double workState1) {
		WorkState1 = workState1;
	}
	public double getWorkState2() {
		return WorkState2;
	}
	public void setWorkState2(double workState2) {
		WorkState2 = workState2;
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
	public double getTotalYield() {
		return TotalYield;
	}
	public void setTotalYield(double totalYield) {
		TotalYield = totalYield;
	}
	public double getDailyYield() {
		return DailyYield;
	}
	public void setDailyYield(double dailyYield) {
		DailyYield = dailyYield;
	}
	public double getArrayInsulationResistance() {
		return ArrayInsulationResistance;
	}
	public void setArrayInsulationResistance(double arrayInsulationResistance) {
		ArrayInsulationResistance = arrayInsulationResistance;
	}
	public double getInteriorTemperature() {
		return InteriorTemperature;
	}
	public void setInteriorTemperature(double interiorTemperature) {
		InteriorTemperature = interiorTemperature;
	}
	public double getTotalDCPower() {
		return TotalDCPower;
	}
	public void setTotalDCPower(double totalDCPower) {
		TotalDCPower = totalDCPower;
	}
	public double getTotalApparentPower() {
		return TotalApparentPower;
	}
	public void setTotalApparentPower(double totalApparentPower) {
		TotalApparentPower = totalApparentPower;
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
	public double getFaultCode() {
		return FaultCode;
	}
	public void setFaultCode(double faultCode) {
		FaultCode = faultCode;
	}
	public String getTimezone_value() {
		return timezone_value;
	}
	public void setTimezone_value(String timezone_value) {
		this.timezone_value = timezone_value;
	}
	
	
	
}
