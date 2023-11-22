/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelElkorProductionMeterEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double ActivePowerTotal;
	private double ReactivePowerTotal;
	private double ApparentPowerTotal;
	private double VoltageAverage;
	private double VoltageLLAverage;
	private double CurrentAverage;
	private double SystemPowerFactor;
	private double SystemFrequency;
	private double VoltageAverageAngle;
	private double SystemQuadrant;
	private double VoltageA;
	private double VoltageB;
	private double VoltageC;
	private double VoltageAB;
	private double VoltageBC;
	private double VoltageAC;
	private double CurrentA;
	private double CurrentB;
	private double CurrentC;
	private double ActivePowerA;
	private double ActivePowerB;
	private double ActivePowerC;
	private double ReactivePowerA;
	private double ReactivePowerB;
	private double ReactivePowerC;
	private double ApparentPowerA;
	private double ApparentPowerB;
	private double ApparentPowerC;
	private double PowerFactorA;
	private double PowerFactorB;
	private double PowerFactorC;
	private double VoltageAngleAB;
	private double VoltageAngleBC;
	private double VoltageAngleCA;
	private double QuadrantA;
	private double QuadrantB;
	private double QuadrantC;
	private double SlidingWindowPower;
	private double NetTotalEnergy;
	private double TotalNetApparentEnergy;
	private double TotalImportEnergy;
	private double TotalExportEnergy;
	private double TotalImportApparentEnergy;
	private double TotalExportApparentEnergy;
	private double Q1TotalReactiveEnergy;
	private double Q2TotalReactiveEnergy;
	private double Q3TotalReactiveEnergy;
	private double Q4TotalReactiveEnergy;
	private double Q1Q2TotalInductiveReactiveEnergy;
	private double Q3Q4TotalCapacitiveReactiveEnergy;
	private double NetEnergyA;
	private double NetEnergyB;
	private double NetEnergyC;
	private double NetApparentEnergyA;
	private double NetApparentEnergyB;
	private double NetApparentEnergyC;
	private double ImportEnergyA;
	private double ImportEnergyB;
	private double ImportEnergyC;
	private double ExportEnergyA;
	private double ExportEnergyB;
	private double ExportEnergyC;
	private double ImportApparentEnergyA;
	private double ImportApparentEnergyB;
	private double ImportApparentEnergyC;
	private double ExportApparentEnergyA;
	private double ExportApparentEnergyB;
	private double ExportApparentEnergyC;
	private double Q1ReactiveEnergyA;
	private double Q1ReactiveEnergyB;
	private double Q1ReactiveEnergyC;
	private double Q2ReactiveEnergyA;
	private double Q2ReactiveEnergyB;
	private double Q2ReactiveEnergyC;
	private double Q3ReactiveEnergyA;
	private double Q3ReactiveEnergyB;
	private double Q3ReactiveEnergyC;
	private double Q4ReactiveEnergyA;
	private double Q4ReactiveEnergyB;
	private double Q4ReactiveEnergyC;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	
	
	
	
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
	
	
	
	
	public double getMeasuredProduction() {
		return MeasuredProduction;
	}
	public void setMeasuredProduction(double measuredProduction) {
		MeasuredProduction = measuredProduction;
	}
	
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the id_device
	 */
	public int getId_device() {
		return id_device;
	}
	/**
	 * @param id_device the id_device to set
	 */
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	/**
	 * @return the error
	 */
	public int getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(int error) {
		this.error = error;
	}
	/**
	 * @return the low_alarm
	 */
	public int getLow_alarm() {
		return low_alarm;
	}
	/**
	 * @param low_alarm the low_alarm to set
	 */
	public void setLow_alarm(int low_alarm) {
		this.low_alarm = low_alarm;
	}
	/**
	 * @return the high_alarm
	 */
	public int getHigh_alarm() {
		return high_alarm;
	}
	/**
	 * @param high_alarm the high_alarm to set
	 */
	public void setHigh_alarm(int high_alarm) {
		this.high_alarm = high_alarm;
	}
	/**
	 * @return the activePowerTotal
	 */
	public double getActivePowerTotal() {
		return ActivePowerTotal;
	}
	/**
	 * @param activePowerTotal the activePowerTotal to set
	 */
	public void setActivePowerTotal(double activePowerTotal) {
		ActivePowerTotal = activePowerTotal;
	}
	/**
	 * @return the reactivePowerTotal
	 */
	public double getReactivePowerTotal() {
		return ReactivePowerTotal;
	}
	/**
	 * @param reactivePowerTotal the reactivePowerTotal to set
	 */
	public void setReactivePowerTotal(double reactivePowerTotal) {
		ReactivePowerTotal = reactivePowerTotal;
	}
	/**
	 * @return the apparentPowerTotal
	 */
	public double getApparentPowerTotal() {
		return ApparentPowerTotal;
	}
	/**
	 * @param apparentPowerTotal the apparentPowerTotal to set
	 */
	public void setApparentPowerTotal(double apparentPowerTotal) {
		ApparentPowerTotal = apparentPowerTotal;
	}
	/**
	 * @return the voltageAverage
	 */
	public double getVoltageAverage() {
		return VoltageAverage;
	}
	/**
	 * @param voltageAverage the voltageAverage to set
	 */
	public void setVoltageAverage(double voltageAverage) {
		VoltageAverage = voltageAverage;
	}
	/**
	 * @return the voltageLLAverage
	 */
	public double getVoltageLLAverage() {
		return VoltageLLAverage;
	}
	/**
	 * @param voltageLLAverage the voltageLLAverage to set
	 */
	public void setVoltageLLAverage(double voltageLLAverage) {
		VoltageLLAverage = voltageLLAverage;
	}
	/**
	 * @return the currentAverage
	 */
	public double getCurrentAverage() {
		return CurrentAverage;
	}
	/**
	 * @param currentAverage the currentAverage to set
	 */
	public void setCurrentAverage(double currentAverage) {
		CurrentAverage = currentAverage;
	}
	/**
	 * @return the systemPowerFactor
	 */
	public double getSystemPowerFactor() {
		return SystemPowerFactor;
	}
	/**
	 * @param systemPowerFactor the systemPowerFactor to set
	 */
	public void setSystemPowerFactor(double systemPowerFactor) {
		SystemPowerFactor = systemPowerFactor;
	}
	/**
	 * @return the systemFrequency
	 */
	public double getSystemFrequency() {
		return SystemFrequency;
	}
	/**
	 * @param systemFrequency the systemFrequency to set
	 */
	public void setSystemFrequency(double systemFrequency) {
		SystemFrequency = systemFrequency;
	}
	/**
	 * @return the voltageAverageAngle
	 */
	public double getVoltageAverageAngle() {
		return VoltageAverageAngle;
	}
	/**
	 * @param voltageAverageAngle the voltageAverageAngle to set
	 */
	public void setVoltageAverageAngle(double voltageAverageAngle) {
		VoltageAverageAngle = voltageAverageAngle;
	}
	/**
	 * @return the systemQuadrant
	 */
	public double getSystemQuadrant() {
		return SystemQuadrant;
	}
	/**
	 * @param systemQuadrant the systemQuadrant to set
	 */
	public void setSystemQuadrant(double systemQuadrant) {
		SystemQuadrant = systemQuadrant;
	}
	/**
	 * @return the voltageA
	 */
	public double getVoltageA() {
		return VoltageA;
	}
	/**
	 * @param voltageA the voltageA to set
	 */
	public void setVoltageA(double voltageA) {
		VoltageA = voltageA;
	}
	/**
	 * @return the voltageB
	 */
	public double getVoltageB() {
		return VoltageB;
	}
	/**
	 * @param voltageB the voltageB to set
	 */
	public void setVoltageB(double voltageB) {
		VoltageB = voltageB;
	}
	/**
	 * @return the voltageC
	 */
	public double getVoltageC() {
		return VoltageC;
	}
	/**
	 * @param voltageC the voltageC to set
	 */
	public void setVoltageC(double voltageC) {
		VoltageC = voltageC;
	}
	/**
	 * @return the voltageAB
	 */
	public double getVoltageAB() {
		return VoltageAB;
	}
	/**
	 * @param voltageAB the voltageAB to set
	 */
	public void setVoltageAB(double voltageAB) {
		VoltageAB = voltageAB;
	}
	/**
	 * @return the voltageBC
	 */
	public double getVoltageBC() {
		return VoltageBC;
	}
	/**
	 * @param voltageBC the voltageBC to set
	 */
	public void setVoltageBC(double voltageBC) {
		VoltageBC = voltageBC;
	}
	/**
	 * @return the voltageAC
	 */
	public double getVoltageAC() {
		return VoltageAC;
	}
	/**
	 * @param voltageAC the voltageAC to set
	 */
	public void setVoltageAC(double voltageAC) {
		VoltageAC = voltageAC;
	}
	/**
	 * @return the currentA
	 */
	public double getCurrentA() {
		return CurrentA;
	}
	/**
	 * @param currentA the currentA to set
	 */
	public void setCurrentA(double currentA) {
		CurrentA = currentA;
	}
	/**
	 * @return the currentB
	 */
	public double getCurrentB() {
		return CurrentB;
	}
	/**
	 * @param currentB the currentB to set
	 */
	public void setCurrentB(double currentB) {
		CurrentB = currentB;
	}
	/**
	 * @return the currentC
	 */
	public double getCurrentC() {
		return CurrentC;
	}
	/**
	 * @param currentC the currentC to set
	 */
	public void setCurrentC(double currentC) {
		CurrentC = currentC;
	}
	/**
	 * @return the activePowerA
	 */
	public double getActivePowerA() {
		return ActivePowerA;
	}
	/**
	 * @param activePowerA the activePowerA to set
	 */
	public void setActivePowerA(double activePowerA) {
		ActivePowerA = activePowerA;
	}
	/**
	 * @return the activePowerB
	 */
	public double getActivePowerB() {
		return ActivePowerB;
	}
	/**
	 * @param activePowerB the activePowerB to set
	 */
	public void setActivePowerB(double activePowerB) {
		ActivePowerB = activePowerB;
	}
	/**
	 * @return the activePowerC
	 */
	public double getActivePowerC() {
		return ActivePowerC;
	}
	/**
	 * @param activePowerC the activePowerC to set
	 */
	public void setActivePowerC(double activePowerC) {
		ActivePowerC = activePowerC;
	}
	/**
	 * @return the reactivePowerA
	 */
	public double getReactivePowerA() {
		return ReactivePowerA;
	}
	/**
	 * @param reactivePowerA the reactivePowerA to set
	 */
	public void setReactivePowerA(double reactivePowerA) {
		ReactivePowerA = reactivePowerA;
	}
	/**
	 * @return the reactivePowerB
	 */
	public double getReactivePowerB() {
		return ReactivePowerB;
	}
	/**
	 * @param reactivePowerB the reactivePowerB to set
	 */
	public void setReactivePowerB(double reactivePowerB) {
		ReactivePowerB = reactivePowerB;
	}
	/**
	 * @return the reactivePowerC
	 */
	public double getReactivePowerC() {
		return ReactivePowerC;
	}
	/**
	 * @param reactivePowerC the reactivePowerC to set
	 */
	public void setReactivePowerC(double reactivePowerC) {
		ReactivePowerC = reactivePowerC;
	}
	/**
	 * @return the apparentPowerA
	 */
	public double getApparentPowerA() {
		return ApparentPowerA;
	}
	/**
	 * @param apparentPowerA the apparentPowerA to set
	 */
	public void setApparentPowerA(double apparentPowerA) {
		ApparentPowerA = apparentPowerA;
	}
	/**
	 * @return the apparentPowerB
	 */
	public double getApparentPowerB() {
		return ApparentPowerB;
	}
	/**
	 * @param apparentPowerB the apparentPowerB to set
	 */
	public void setApparentPowerB(double apparentPowerB) {
		ApparentPowerB = apparentPowerB;
	}
	/**
	 * @return the apparentPowerC
	 */
	public double getApparentPowerC() {
		return ApparentPowerC;
	}
	/**
	 * @param apparentPowerC the apparentPowerC to set
	 */
	public void setApparentPowerC(double apparentPowerC) {
		ApparentPowerC = apparentPowerC;
	}
	/**
	 * @return the powerFactorA
	 */
	public double getPowerFactorA() {
		return PowerFactorA;
	}
	/**
	 * @param powerFactorA the powerFactorA to set
	 */
	public void setPowerFactorA(double powerFactorA) {
		PowerFactorA = powerFactorA;
	}
	/**
	 * @return the powerFactorB
	 */
	public double getPowerFactorB() {
		return PowerFactorB;
	}
	/**
	 * @param powerFactorB the powerFactorB to set
	 */
	public void setPowerFactorB(double powerFactorB) {
		PowerFactorB = powerFactorB;
	}
	/**
	 * @return the powerFactorC
	 */
	public double getPowerFactorC() {
		return PowerFactorC;
	}
	/**
	 * @param powerFactorC the powerFactorC to set
	 */
	public void setPowerFactorC(double powerFactorC) {
		PowerFactorC = powerFactorC;
	}
	/**
	 * @return the voltageAngleAB
	 */
	public double getVoltageAngleAB() {
		return VoltageAngleAB;
	}
	/**
	 * @param voltageAngleAB the voltageAngleAB to set
	 */
	public void setVoltageAngleAB(double voltageAngleAB) {
		VoltageAngleAB = voltageAngleAB;
	}
	/**
	 * @return the voltageAngleBC
	 */
	public double getVoltageAngleBC() {
		return VoltageAngleBC;
	}
	/**
	 * @param voltageAngleBC the voltageAngleBC to set
	 */
	public void setVoltageAngleBC(double voltageAngleBC) {
		VoltageAngleBC = voltageAngleBC;
	}
	/**
	 * @return the voltageAngleCA
	 */
	public double getVoltageAngleCA() {
		return VoltageAngleCA;
	}
	/**
	 * @param voltageAngleCA the voltageAngleCA to set
	 */
	public void setVoltageAngleCA(double voltageAngleCA) {
		VoltageAngleCA = voltageAngleCA;
	}
	/**
	 * @return the quadrantA
	 */
	public double getQuadrantA() {
		return QuadrantA;
	}
	/**
	 * @param quadrantA the quadrantA to set
	 */
	public void setQuadrantA(double quadrantA) {
		QuadrantA = quadrantA;
	}
	/**
	 * @return the quadrantB
	 */
	public double getQuadrantB() {
		return QuadrantB;
	}
	/**
	 * @param quadrantB the quadrantB to set
	 */
	public void setQuadrantB(double quadrantB) {
		QuadrantB = quadrantB;
	}
	/**
	 * @return the quadrantC
	 */
	public double getQuadrantC() {
		return QuadrantC;
	}
	/**
	 * @param quadrantC the quadrantC to set
	 */
	public void setQuadrantC(double quadrantC) {
		QuadrantC = quadrantC;
	}
	/**
	 * @return the slidingWindowPower
	 */
	public double getSlidingWindowPower() {
		return SlidingWindowPower;
	}
	/**
	 * @param slidingWindowPower the slidingWindowPower to set
	 */
	public void setSlidingWindowPower(double slidingWindowPower) {
		SlidingWindowPower = slidingWindowPower;
	}
	/**
	 * @return the netTotalEnergy
	 */
	public double getNetTotalEnergy() {
		return NetTotalEnergy;
	}
	/**
	 * @param netTotalEnergy the netTotalEnergy to set
	 */
	public void setNetTotalEnergy(double netTotalEnergy) {
		NetTotalEnergy = netTotalEnergy;
	}
	/**
	 * @return the totalNetApparentEnergy
	 */
	public double getTotalNetApparentEnergy() {
		return TotalNetApparentEnergy;
	}
	/**
	 * @param totalNetApparentEnergy the totalNetApparentEnergy to set
	 */
	public void setTotalNetApparentEnergy(double totalNetApparentEnergy) {
		TotalNetApparentEnergy = totalNetApparentEnergy;
	}
	/**
	 * @return the totalImportEnergy
	 */
	public double getTotalImportEnergy() {
		return TotalImportEnergy;
	}
	/**
	 * @param totalImportEnergy the totalImportEnergy to set
	 */
	public void setTotalImportEnergy(double totalImportEnergy) {
		TotalImportEnergy = totalImportEnergy;
	}
	/**
	 * @return the totalExportEnergy
	 */
	public double getTotalExportEnergy() {
		return TotalExportEnergy;
	}
	/**
	 * @param totalExportEnergy the totalExportEnergy to set
	 */
	public void setTotalExportEnergy(double totalExportEnergy) {
		TotalExportEnergy = totalExportEnergy;
	}
	/**
	 * @return the totalImportApparentEnergy
	 */
	public double getTotalImportApparentEnergy() {
		return TotalImportApparentEnergy;
	}
	/**
	 * @param totalImportApparentEnergy the totalImportApparentEnergy to set
	 */
	public void setTotalImportApparentEnergy(double totalImportApparentEnergy) {
		TotalImportApparentEnergy = totalImportApparentEnergy;
	}
	/**
	 * @return the totalExportApparentEnergy
	 */
	public double getTotalExportApparentEnergy() {
		return TotalExportApparentEnergy;
	}
	/**
	 * @param totalExportApparentEnergy the totalExportApparentEnergy to set
	 */
	public void setTotalExportApparentEnergy(double totalExportApparentEnergy) {
		TotalExportApparentEnergy = totalExportApparentEnergy;
	}
	/**
	 * @return the q1TotalReactiveEnergy
	 */
	public double getQ1TotalReactiveEnergy() {
		return Q1TotalReactiveEnergy;
	}
	/**
	 * @param q1TotalReactiveEnergy the q1TotalReactiveEnergy to set
	 */
	public void setQ1TotalReactiveEnergy(double q1TotalReactiveEnergy) {
		Q1TotalReactiveEnergy = q1TotalReactiveEnergy;
	}
	/**
	 * @return the q2TotalReactiveEnergy
	 */
	public double getQ2TotalReactiveEnergy() {
		return Q2TotalReactiveEnergy;
	}
	/**
	 * @param q2TotalReactiveEnergy the q2TotalReactiveEnergy to set
	 */
	public void setQ2TotalReactiveEnergy(double q2TotalReactiveEnergy) {
		Q2TotalReactiveEnergy = q2TotalReactiveEnergy;
	}
	/**
	 * @return the q3TotalReactiveEnergy
	 */
	public double getQ3TotalReactiveEnergy() {
		return Q3TotalReactiveEnergy;
	}
	/**
	 * @param q3TotalReactiveEnergy the q3TotalReactiveEnergy to set
	 */
	public void setQ3TotalReactiveEnergy(double q3TotalReactiveEnergy) {
		Q3TotalReactiveEnergy = q3TotalReactiveEnergy;
	}
	/**
	 * @return the q4TotalReactiveEnergy
	 */
	public double getQ4TotalReactiveEnergy() {
		return Q4TotalReactiveEnergy;
	}
	/**
	 * @param q4TotalReactiveEnergy the q4TotalReactiveEnergy to set
	 */
	public void setQ4TotalReactiveEnergy(double q4TotalReactiveEnergy) {
		Q4TotalReactiveEnergy = q4TotalReactiveEnergy;
	}
	/**
	 * @return the q1Q2TotalInductiveReactiveEnergy
	 */
	public double getQ1Q2TotalInductiveReactiveEnergy() {
		return Q1Q2TotalInductiveReactiveEnergy;
	}
	/**
	 * @param q1q2TotalInductiveReactiveEnergy the q1Q2TotalInductiveReactiveEnergy to set
	 */
	public void setQ1Q2TotalInductiveReactiveEnergy(double q1q2TotalInductiveReactiveEnergy) {
		Q1Q2TotalInductiveReactiveEnergy = q1q2TotalInductiveReactiveEnergy;
	}
	/**
	 * @return the q3Q4TotalCapacitiveReactiveEnergy
	 */
	public double getQ3Q4TotalCapacitiveReactiveEnergy() {
		return Q3Q4TotalCapacitiveReactiveEnergy;
	}
	/**
	 * @param q3q4TotalCapacitiveReactiveEnergy the q3Q4TotalCapacitiveReactiveEnergy to set
	 */
	public void setQ3Q4TotalCapacitiveReactiveEnergy(double q3q4TotalCapacitiveReactiveEnergy) {
		Q3Q4TotalCapacitiveReactiveEnergy = q3q4TotalCapacitiveReactiveEnergy;
	}
	/**
	 * @return the netEnergyA
	 */
	public double getNetEnergyA() {
		return NetEnergyA;
	}
	/**
	 * @param netEnergyA the netEnergyA to set
	 */
	public void setNetEnergyA(double netEnergyA) {
		NetEnergyA = netEnergyA;
	}
	/**
	 * @return the netEnergyB
	 */
	public double getNetEnergyB() {
		return NetEnergyB;
	}
	/**
	 * @param netEnergyB the netEnergyB to set
	 */
	public void setNetEnergyB(double netEnergyB) {
		NetEnergyB = netEnergyB;
	}
	/**
	 * @return the netEnergyC
	 */
	public double getNetEnergyC() {
		return NetEnergyC;
	}
	/**
	 * @param netEnergyC the netEnergyC to set
	 */
	public void setNetEnergyC(double netEnergyC) {
		NetEnergyC = netEnergyC;
	}
	/**
	 * @return the netApparentEnergyA
	 */
	public double getNetApparentEnergyA() {
		return NetApparentEnergyA;
	}
	/**
	 * @param netApparentEnergyA the netApparentEnergyA to set
	 */
	public void setNetApparentEnergyA(double netApparentEnergyA) {
		NetApparentEnergyA = netApparentEnergyA;
	}
	/**
	 * @return the netApparentEnergyB
	 */
	public double getNetApparentEnergyB() {
		return NetApparentEnergyB;
	}
	/**
	 * @param netApparentEnergyB the netApparentEnergyB to set
	 */
	public void setNetApparentEnergyB(double netApparentEnergyB) {
		NetApparentEnergyB = netApparentEnergyB;
	}
	/**
	 * @return the netApparentEnergyC
	 */
	public double getNetApparentEnergyC() {
		return NetApparentEnergyC;
	}
	/**
	 * @param netApparentEnergyC the netApparentEnergyC to set
	 */
	public void setNetApparentEnergyC(double netApparentEnergyC) {
		NetApparentEnergyC = netApparentEnergyC;
	}
	/**
	 * @return the importEnergyA
	 */
	public double getImportEnergyA() {
		return ImportEnergyA;
	}
	/**
	 * @param importEnergyA the importEnergyA to set
	 */
	public void setImportEnergyA(double importEnergyA) {
		ImportEnergyA = importEnergyA;
	}
	/**
	 * @return the importEnergyB
	 */
	public double getImportEnergyB() {
		return ImportEnergyB;
	}
	/**
	 * @param importEnergyB the importEnergyB to set
	 */
	public void setImportEnergyB(double importEnergyB) {
		ImportEnergyB = importEnergyB;
	}
	/**
	 * @return the importEnergyC
	 */
	public double getImportEnergyC() {
		return ImportEnergyC;
	}
	/**
	 * @param importEnergyC the importEnergyC to set
	 */
	public void setImportEnergyC(double importEnergyC) {
		ImportEnergyC = importEnergyC;
	}
	/**
	 * @return the exportEnergyA
	 */
	public double getExportEnergyA() {
		return ExportEnergyA;
	}
	/**
	 * @param exportEnergyA the exportEnergyA to set
	 */
	public void setExportEnergyA(double exportEnergyA) {
		ExportEnergyA = exportEnergyA;
	}
	/**
	 * @return the exportEnergyB
	 */
	public double getExportEnergyB() {
		return ExportEnergyB;
	}
	/**
	 * @param exportEnergyB the exportEnergyB to set
	 */
	public void setExportEnergyB(double exportEnergyB) {
		ExportEnergyB = exportEnergyB;
	}
	/**
	 * @return the exportEnergyC
	 */
	public double getExportEnergyC() {
		return ExportEnergyC;
	}
	/**
	 * @param exportEnergyC the exportEnergyC to set
	 */
	public void setExportEnergyC(double exportEnergyC) {
		ExportEnergyC = exportEnergyC;
	}
	/**
	 * @return the importApparentEnergyA
	 */
	public double getImportApparentEnergyA() {
		return ImportApparentEnergyA;
	}
	/**
	 * @param importApparentEnergyA the importApparentEnergyA to set
	 */
	public void setImportApparentEnergyA(double importApparentEnergyA) {
		ImportApparentEnergyA = importApparentEnergyA;
	}
	/**
	 * @return the importApparentEnergyB
	 */
	public double getImportApparentEnergyB() {
		return ImportApparentEnergyB;
	}
	/**
	 * @param importApparentEnergyB the importApparentEnergyB to set
	 */
	public void setImportApparentEnergyB(double importApparentEnergyB) {
		ImportApparentEnergyB = importApparentEnergyB;
	}
	/**
	 * @return the importApparentEnergyC
	 */
	public double getImportApparentEnergyC() {
		return ImportApparentEnergyC;
	}
	/**
	 * @param importApparentEnergyC the importApparentEnergyC to set
	 */
	public void setImportApparentEnergyC(double importApparentEnergyC) {
		ImportApparentEnergyC = importApparentEnergyC;
	}
	/**
	 * @return the exportApparentEnergyA
	 */
	public double getExportApparentEnergyA() {
		return ExportApparentEnergyA;
	}
	/**
	 * @param exportApparentEnergyA the exportApparentEnergyA to set
	 */
	public void setExportApparentEnergyA(double exportApparentEnergyA) {
		ExportApparentEnergyA = exportApparentEnergyA;
	}
	/**
	 * @return the exportApparentEnergyB
	 */
	public double getExportApparentEnergyB() {
		return ExportApparentEnergyB;
	}
	/**
	 * @param exportApparentEnergyB the exportApparentEnergyB to set
	 */
	public void setExportApparentEnergyB(double exportApparentEnergyB) {
		ExportApparentEnergyB = exportApparentEnergyB;
	}
	/**
	 * @return the exportApparentEnergyC
	 */
	public double getExportApparentEnergyC() {
		return ExportApparentEnergyC;
	}
	/**
	 * @param exportApparentEnergyC the exportApparentEnergyC to set
	 */
	public void setExportApparentEnergyC(double exportApparentEnergyC) {
		ExportApparentEnergyC = exportApparentEnergyC;
	}
	/**
	 * @return the q1ReactiveEnergyA
	 */
	public double getQ1ReactiveEnergyA() {
		return Q1ReactiveEnergyA;
	}
	/**
	 * @param q1ReactiveEnergyA the q1ReactiveEnergyA to set
	 */
	public void setQ1ReactiveEnergyA(double q1ReactiveEnergyA) {
		Q1ReactiveEnergyA = q1ReactiveEnergyA;
	}
	/**
	 * @return the q1ReactiveEnergyB
	 */
	public double getQ1ReactiveEnergyB() {
		return Q1ReactiveEnergyB;
	}
	/**
	 * @param q1ReactiveEnergyB the q1ReactiveEnergyB to set
	 */
	public void setQ1ReactiveEnergyB(double q1ReactiveEnergyB) {
		Q1ReactiveEnergyB = q1ReactiveEnergyB;
	}
	/**
	 * @return the q1ReactiveEnergyC
	 */
	public double getQ1ReactiveEnergyC() {
		return Q1ReactiveEnergyC;
	}
	/**
	 * @param q1ReactiveEnergyC the q1ReactiveEnergyC to set
	 */
	public void setQ1ReactiveEnergyC(double q1ReactiveEnergyC) {
		Q1ReactiveEnergyC = q1ReactiveEnergyC;
	}
	/**
	 * @return the q2ReactiveEnergyA
	 */
	public double getQ2ReactiveEnergyA() {
		return Q2ReactiveEnergyA;
	}
	/**
	 * @param q2ReactiveEnergyA the q2ReactiveEnergyA to set
	 */
	public void setQ2ReactiveEnergyA(double q2ReactiveEnergyA) {
		Q2ReactiveEnergyA = q2ReactiveEnergyA;
	}
	/**
	 * @return the q2ReactiveEnergyB
	 */
	public double getQ2ReactiveEnergyB() {
		return Q2ReactiveEnergyB;
	}
	/**
	 * @param q2ReactiveEnergyB the q2ReactiveEnergyB to set
	 */
	public void setQ2ReactiveEnergyB(double q2ReactiveEnergyB) {
		Q2ReactiveEnergyB = q2ReactiveEnergyB;
	}
	/**
	 * @return the q2ReactiveEnergyC
	 */
	public double getQ2ReactiveEnergyC() {
		return Q2ReactiveEnergyC;
	}
	/**
	 * @param q2ReactiveEnergyC the q2ReactiveEnergyC to set
	 */
	public void setQ2ReactiveEnergyC(double q2ReactiveEnergyC) {
		Q2ReactiveEnergyC = q2ReactiveEnergyC;
	}
	/**
	 * @return the q3ReactiveEnergyA
	 */
	public double getQ3ReactiveEnergyA() {
		return Q3ReactiveEnergyA;
	}
	/**
	 * @param q3ReactiveEnergyA the q3ReactiveEnergyA to set
	 */
	public void setQ3ReactiveEnergyA(double q3ReactiveEnergyA) {
		Q3ReactiveEnergyA = q3ReactiveEnergyA;
	}
	/**
	 * @return the q3ReactiveEnergyB
	 */
	public double getQ3ReactiveEnergyB() {
		return Q3ReactiveEnergyB;
	}
	/**
	 * @param q3ReactiveEnergyB the q3ReactiveEnergyB to set
	 */
	public void setQ3ReactiveEnergyB(double q3ReactiveEnergyB) {
		Q3ReactiveEnergyB = q3ReactiveEnergyB;
	}
	/**
	 * @return the q3ReactiveEnergyC
	 */
	public double getQ3ReactiveEnergyC() {
		return Q3ReactiveEnergyC;
	}
	/**
	 * @param q3ReactiveEnergyC the q3ReactiveEnergyC to set
	 */
	public void setQ3ReactiveEnergyC(double q3ReactiveEnergyC) {
		Q3ReactiveEnergyC = q3ReactiveEnergyC;
	}
	/**
	 * @return the q4ReactiveEnergyA
	 */
	public double getQ4ReactiveEnergyA() {
		return Q4ReactiveEnergyA;
	}
	/**
	 * @param q4ReactiveEnergyA the q4ReactiveEnergyA to set
	 */
	public void setQ4ReactiveEnergyA(double q4ReactiveEnergyA) {
		Q4ReactiveEnergyA = q4ReactiveEnergyA;
	}
	/**
	 * @return the q4ReactiveEnergyB
	 */
	public double getQ4ReactiveEnergyB() {
		return Q4ReactiveEnergyB;
	}
	/**
	 * @param q4ReactiveEnergyB the q4ReactiveEnergyB to set
	 */
	public void setQ4ReactiveEnergyB(double q4ReactiveEnergyB) {
		Q4ReactiveEnergyB = q4ReactiveEnergyB;
	}
	/**
	 * @return the q4ReactiveEnergyC
	 */
	public double getQ4ReactiveEnergyC() {
		return Q4ReactiveEnergyC;
	}
	/**
	 * @param q4ReactiveEnergyC the q4ReactiveEnergyC to set
	 */
	public void setQ4ReactiveEnergyC(double q4ReactiveEnergyC) {
		Q4ReactiveEnergyC = q4ReactiveEnergyC;
	}
	/**
	 * @return the nvmActivePower
	 */
	public double getNvmActivePower() {
		return nvmActivePower;
	}
	/**
	 * @param nvmActivePower the nvmActivePower to set
	 */
	public void setNvmActivePower(double nvmActivePower) {
		this.nvmActivePower = nvmActivePower;
	}
	/**
	 * @return the nvmActiveEnergy
	 */
	public double getNvmActiveEnergy() {
		return nvmActiveEnergy;
	}
	/**
	 * @param nvmActiveEnergy the nvmActiveEnergy to set
	 */
	public void setNvmActiveEnergy(double nvmActiveEnergy) {
		this.nvmActiveEnergy = nvmActiveEnergy;
	}
	
	
}
