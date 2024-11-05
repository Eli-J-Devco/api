/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelPowerLogicPM8000LoadMeterEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double ActiveEnergyDelivered;
	private double ActiveEnergyReceived;
	private double ActiveEnergyDeliveredPlusReceived;
	private double ActiveEnergyDeliveredMinusReceived;
	private double ReactiveEnergyDelivered;
	private double ReactiveEnergyReceived;
	private double ReactiveEnergyDeliveredPlusReceived;
	private double ReactiveEnergyDeliveredMinusReceived;
	private double ApparentEnergyDelivered;
	private double ApparentEnergyReceived;
	private double ApparentEnergyDeliveredPlusReceived;
	private double ApparentEnergyDeliveredMinusReceived;
	private double ActiveEnergyinQuadrantI;
	private double ActiveEnergyinQuadrantII;
	private double ActiveEnergyinQuadrantIII;
	private double ActiveEnergyinQuadrantIV;
	private double ReactiveEnergyinQuadrantI;
	private double ReactiveEnergyinQuadrantII;
	private double ReactiveEnergyinQuadrantIII;
	private double ReactiveEnergyinQuadrantIV;
	private double ApparentEnergyinQuadrantI;
	private double ApparentEnergyinQuadrantII;
	private double ApparentEnergyinQuadrantIII;
	private double ApparentEnergyinQuadrantIV;
	private double CurrentPhaseA;
	private double CurrentPhaseB;
	private double CurrentPhaseC;
	private double CurrentNeutralLine;
	private double CurrentGroundLine;
	private double CurrentAverage;
	private double CurrentUnbalanceWorst;
	private double VoltagePhaseAB;
	private double VoltagePhaseBC;
	private double VoltagePhaseCA;
	private double VoltageLLAverage;
	private double VoltageAN;
	private double VoltageBN;
	private double VoltageCN;
	private double VoltageLNAverage;
	private double VoltageUnbalanceLNWorst;
	private double ActivePowerPhaseA;
	private double ActivePowerPhaseB;
	private double ActivePowerPhaseC;
	private double ActivePowerTotal;
	private double ReactivePowerPhaseA;
	private double ReactivePowerPhaseB;
	private double ReactivePowerPhaseC;
	private double ReactivePowerTotal;
	private double ApparentPowerPhaseA;
	private double ApparentPowerPhaseB;
	private double ApparentPowerPhaseC;
	private double ApparentPowerTotal;
	private double Frequency;
	private double PowerFactorPhaseA;
	private double PowerFactorPhaseB;
	private double PowerFactorPhaseC;
	private double PowerFactorTotal;
	
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	
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
	public double getActiveEnergyDelivered() {
		return ActiveEnergyDelivered;
	}
	public void setActiveEnergyDelivered(double activeEnergyDelivered) {
		ActiveEnergyDelivered = activeEnergyDelivered;
	}
	public double getActiveEnergyReceived() {
		return ActiveEnergyReceived;
	}
	public void setActiveEnergyReceived(double activeEnergyReceived) {
		ActiveEnergyReceived = activeEnergyReceived;
	}
	public double getActiveEnergyDeliveredPlusReceived() {
		return ActiveEnergyDeliveredPlusReceived;
	}
	public void setActiveEnergyDeliveredPlusReceived(double activeEnergyDeliveredPlusReceived) {
		ActiveEnergyDeliveredPlusReceived = activeEnergyDeliveredPlusReceived;
	}
	public double getActiveEnergyDeliveredMinusReceived() {
		return ActiveEnergyDeliveredMinusReceived;
	}
	public void setActiveEnergyDeliveredMinusReceived(double activeEnergyDeliveredMinusReceived) {
		ActiveEnergyDeliveredMinusReceived = activeEnergyDeliveredMinusReceived;
	}
	public double getReactiveEnergyDelivered() {
		return ReactiveEnergyDelivered;
	}
	public void setReactiveEnergyDelivered(double reactiveEnergyDelivered) {
		ReactiveEnergyDelivered = reactiveEnergyDelivered;
	}
	public double getReactiveEnergyReceived() {
		return ReactiveEnergyReceived;
	}
	public void setReactiveEnergyReceived(double reactiveEnergyReceived) {
		ReactiveEnergyReceived = reactiveEnergyReceived;
	}
	public double getReactiveEnergyDeliveredPlusReceived() {
		return ReactiveEnergyDeliveredPlusReceived;
	}
	public void setReactiveEnergyDeliveredPlusReceived(double reactiveEnergyDeliveredPlusReceived) {
		ReactiveEnergyDeliveredPlusReceived = reactiveEnergyDeliveredPlusReceived;
	}
	public double getReactiveEnergyDeliveredMinusReceived() {
		return ReactiveEnergyDeliveredMinusReceived;
	}
	public void setReactiveEnergyDeliveredMinusReceived(double reactiveEnergyDeliveredMinusReceived) {
		ReactiveEnergyDeliveredMinusReceived = reactiveEnergyDeliveredMinusReceived;
	}
	public double getApparentEnergyDelivered() {
		return ApparentEnergyDelivered;
	}
	public void setApparentEnergyDelivered(double apparentEnergyDelivered) {
		ApparentEnergyDelivered = apparentEnergyDelivered;
	}
	public double getApparentEnergyReceived() {
		return ApparentEnergyReceived;
	}
	public void setApparentEnergyReceived(double apparentEnergyReceived) {
		ApparentEnergyReceived = apparentEnergyReceived;
	}
	public double getApparentEnergyDeliveredPlusReceived() {
		return ApparentEnergyDeliveredPlusReceived;
	}
	public void setApparentEnergyDeliveredPlusReceived(double apparentEnergyDeliveredPlusReceived) {
		ApparentEnergyDeliveredPlusReceived = apparentEnergyDeliveredPlusReceived;
	}
	public double getApparentEnergyDeliveredMinusReceived() {
		return ApparentEnergyDeliveredMinusReceived;
	}
	public void setApparentEnergyDeliveredMinusReceived(double apparentEnergyDeliveredMinusReceived) {
		ApparentEnergyDeliveredMinusReceived = apparentEnergyDeliveredMinusReceived;
	}
	public double getActiveEnergyinQuadrantI() {
		return ActiveEnergyinQuadrantI;
	}
	public void setActiveEnergyinQuadrantI(double activeEnergyinQuadrantI) {
		ActiveEnergyinQuadrantI = activeEnergyinQuadrantI;
	}
	public double getActiveEnergyinQuadrantII() {
		return ActiveEnergyinQuadrantII;
	}
	public void setActiveEnergyinQuadrantII(double activeEnergyinQuadrantII) {
		ActiveEnergyinQuadrantII = activeEnergyinQuadrantII;
	}
	public double getActiveEnergyinQuadrantIII() {
		return ActiveEnergyinQuadrantIII;
	}
	public void setActiveEnergyinQuadrantIII(double activeEnergyinQuadrantIII) {
		ActiveEnergyinQuadrantIII = activeEnergyinQuadrantIII;
	}
	public double getActiveEnergyinQuadrantIV() {
		return ActiveEnergyinQuadrantIV;
	}
	public void setActiveEnergyinQuadrantIV(double activeEnergyinQuadrantIV) {
		ActiveEnergyinQuadrantIV = activeEnergyinQuadrantIV;
	}
	public double getReactiveEnergyinQuadrantI() {
		return ReactiveEnergyinQuadrantI;
	}
	public void setReactiveEnergyinQuadrantI(double reactiveEnergyinQuadrantI) {
		ReactiveEnergyinQuadrantI = reactiveEnergyinQuadrantI;
	}
	public double getReactiveEnergyinQuadrantII() {
		return ReactiveEnergyinQuadrantII;
	}
	public void setReactiveEnergyinQuadrantII(double reactiveEnergyinQuadrantII) {
		ReactiveEnergyinQuadrantII = reactiveEnergyinQuadrantII;
	}
	public double getReactiveEnergyinQuadrantIII() {
		return ReactiveEnergyinQuadrantIII;
	}
	public void setReactiveEnergyinQuadrantIII(double reactiveEnergyinQuadrantIII) {
		ReactiveEnergyinQuadrantIII = reactiveEnergyinQuadrantIII;
	}
	public double getReactiveEnergyinQuadrantIV() {
		return ReactiveEnergyinQuadrantIV;
	}
	public void setReactiveEnergyinQuadrantIV(double reactiveEnergyinQuadrantIV) {
		ReactiveEnergyinQuadrantIV = reactiveEnergyinQuadrantIV;
	}
	public double getApparentEnergyinQuadrantI() {
		return ApparentEnergyinQuadrantI;
	}
	public void setApparentEnergyinQuadrantI(double apparentEnergyinQuadrantI) {
		ApparentEnergyinQuadrantI = apparentEnergyinQuadrantI;
	}
	public double getApparentEnergyinQuadrantII() {
		return ApparentEnergyinQuadrantII;
	}
	public void setApparentEnergyinQuadrantII(double apparentEnergyinQuadrantII) {
		ApparentEnergyinQuadrantII = apparentEnergyinQuadrantII;
	}
	public double getApparentEnergyinQuadrantIII() {
		return ApparentEnergyinQuadrantIII;
	}
	public void setApparentEnergyinQuadrantIII(double apparentEnergyinQuadrantIII) {
		ApparentEnergyinQuadrantIII = apparentEnergyinQuadrantIII;
	}
	public double getApparentEnergyinQuadrantIV() {
		return ApparentEnergyinQuadrantIV;
	}
	public void setApparentEnergyinQuadrantIV(double apparentEnergyinQuadrantIV) {
		ApparentEnergyinQuadrantIV = apparentEnergyinQuadrantIV;
	}
	public double getCurrentPhaseA() {
		return CurrentPhaseA;
	}
	public void setCurrentPhaseA(double currentPhaseA) {
		CurrentPhaseA = currentPhaseA;
	}
	public double getCurrentPhaseB() {
		return CurrentPhaseB;
	}
	public void setCurrentPhaseB(double currentPhaseB) {
		CurrentPhaseB = currentPhaseB;
	}
	public double getCurrentPhaseC() {
		return CurrentPhaseC;
	}
	public void setCurrentPhaseC(double currentPhaseC) {
		CurrentPhaseC = currentPhaseC;
	}
	public double getCurrentNeutralLine() {
		return CurrentNeutralLine;
	}
	public void setCurrentNeutralLine(double currentNeutralLine) {
		CurrentNeutralLine = currentNeutralLine;
	}
	public double getCurrentGroundLine() {
		return CurrentGroundLine;
	}
	public void setCurrentGroundLine(double currentGroundLine) {
		CurrentGroundLine = currentGroundLine;
	}
	public double getCurrentAverage() {
		return CurrentAverage;
	}
	public void setCurrentAverage(double currentAverage) {
		CurrentAverage = currentAverage;
	}
	public double getCurrentUnbalanceWorst() {
		return CurrentUnbalanceWorst;
	}
	public void setCurrentUnbalanceWorst(double currentUnbalanceWorst) {
		CurrentUnbalanceWorst = currentUnbalanceWorst;
	}
	public double getVoltagePhaseAB() {
		return VoltagePhaseAB;
	}
	public void setVoltagePhaseAB(double voltagePhaseAB) {
		VoltagePhaseAB = voltagePhaseAB;
	}
	public double getVoltagePhaseBC() {
		return VoltagePhaseBC;
	}
	public void setVoltagePhaseBC(double voltagePhaseBC) {
		VoltagePhaseBC = voltagePhaseBC;
	}
	public double getVoltagePhaseCA() {
		return VoltagePhaseCA;
	}
	public void setVoltagePhaseCA(double voltagePhaseCA) {
		VoltagePhaseCA = voltagePhaseCA;
	}
	public double getVoltageLLAverage() {
		return VoltageLLAverage;
	}
	public void setVoltageLLAverage(double voltageLLAverage) {
		VoltageLLAverage = voltageLLAverage;
	}
	public double getVoltageAN() {
		return VoltageAN;
	}
	public void setVoltageAN(double voltageAN) {
		VoltageAN = voltageAN;
	}
	public double getVoltageBN() {
		return VoltageBN;
	}
	public void setVoltageBN(double voltageBN) {
		VoltageBN = voltageBN;
	}
	public double getVoltageCN() {
		return VoltageCN;
	}
	public void setVoltageCN(double voltageCN) {
		VoltageCN = voltageCN;
	}
	public double getVoltageLNAverage() {
		return VoltageLNAverage;
	}
	public void setVoltageLNAverage(double voltageLNAverage) {
		VoltageLNAverage = voltageLNAverage;
	}
	public double getVoltageUnbalanceLNWorst() {
		return VoltageUnbalanceLNWorst;
	}
	public void setVoltageUnbalanceLNWorst(double voltageUnbalanceLNWorst) {
		VoltageUnbalanceLNWorst = voltageUnbalanceLNWorst;
	}
	public double getActivePowerPhaseA() {
		return ActivePowerPhaseA;
	}
	public void setActivePowerPhaseA(double activePowerPhaseA) {
		ActivePowerPhaseA = activePowerPhaseA;
	}
	public double getActivePowerPhaseB() {
		return ActivePowerPhaseB;
	}
	public void setActivePowerPhaseB(double activePowerPhaseB) {
		ActivePowerPhaseB = activePowerPhaseB;
	}
	public double getActivePowerPhaseC() {
		return ActivePowerPhaseC;
	}
	public void setActivePowerPhaseC(double activePowerPhaseC) {
		ActivePowerPhaseC = activePowerPhaseC;
	}
	public double getActivePowerTotal() {
		return ActivePowerTotal;
	}
	public void setActivePowerTotal(double activePowerTotal) {
		ActivePowerTotal = activePowerTotal;
	}
	public double getReactivePowerPhaseA() {
		return ReactivePowerPhaseA;
	}
	public void setReactivePowerPhaseA(double reactivePowerPhaseA) {
		ReactivePowerPhaseA = reactivePowerPhaseA;
	}
	public double getReactivePowerPhaseB() {
		return ReactivePowerPhaseB;
	}
	public void setReactivePowerPhaseB(double reactivePowerPhaseB) {
		ReactivePowerPhaseB = reactivePowerPhaseB;
	}
	public double getReactivePowerPhaseC() {
		return ReactivePowerPhaseC;
	}
	public void setReactivePowerPhaseC(double reactivePowerPhaseC) {
		ReactivePowerPhaseC = reactivePowerPhaseC;
	}
	public double getReactivePowerTotal() {
		return ReactivePowerTotal;
	}
	public void setReactivePowerTotal(double reactivePowerTotal) {
		ReactivePowerTotal = reactivePowerTotal;
	}
	public double getApparentPowerPhaseA() {
		return ApparentPowerPhaseA;
	}
	public void setApparentPowerPhaseA(double apparentPowerPhaseA) {
		ApparentPowerPhaseA = apparentPowerPhaseA;
	}
	public double getApparentPowerPhaseB() {
		return ApparentPowerPhaseB;
	}
	public void setApparentPowerPhaseB(double apparentPowerPhaseB) {
		ApparentPowerPhaseB = apparentPowerPhaseB;
	}
	public double getApparentPowerPhaseC() {
		return ApparentPowerPhaseC;
	}
	public void setApparentPowerPhaseC(double apparentPowerPhaseC) {
		ApparentPowerPhaseC = apparentPowerPhaseC;
	}
	public double getApparentPowerTotal() {
		return ApparentPowerTotal;
	}
	public void setApparentPowerTotal(double apparentPowerTotal) {
		ApparentPowerTotal = apparentPowerTotal;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getPowerFactorPhaseA() {
		return PowerFactorPhaseA;
	}
	public void setPowerFactorPhaseA(double powerFactorPhaseA) {
		PowerFactorPhaseA = powerFactorPhaseA;
	}
	public double getPowerFactorPhaseB() {
		return PowerFactorPhaseB;
	}
	public void setPowerFactorPhaseB(double powerFactorPhaseB) {
		PowerFactorPhaseB = powerFactorPhaseB;
	}
	public double getPowerFactorPhaseC() {
		return PowerFactorPhaseC;
	}
	public void setPowerFactorPhaseC(double powerFactorPhaseC) {
		PowerFactorPhaseC = powerFactorPhaseC;
	}
	public double getPowerFactorTotal() {
		return PowerFactorTotal;
	}
	public void setPowerFactorTotal(double powerFactorTotal) {
		PowerFactorTotal = powerFactorTotal;
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
	
}
