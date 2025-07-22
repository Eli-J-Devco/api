/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSmaCoreEntity extends ModelBaseEntity {
	private double CurrentPhase1;
	private double CurrentPhase2;
	private double CurrentPhase3;
	private double GridFrequency;
	private double VoltagePhase1;
	private double VoltagePhase2;
	private double VoltagePhase3;
	private double VoltagePhase12;
	private double VoltagePhase23;
	private double VoltagePhase31;
	private double PowerFactor;
	private double ActivePower;
	private double ActivePowerPhase1;
	private double ActivePowerPhase2;
	private double ActivePowerPhase3;
	private double ApparentPower;
	private double ApparentPowerPhase1;
	private double ApparentPowerPhase2;
	private double ApparentPowerPhase3;
	private double ReactivePower;
	private double ReactivePowerPhase1;
	private double ReactivePowerPhase2;
	private double ReactivePowerPhase3;
	private double ResidualCurrent;
	private double InternalTemperature;
	private double TotalYield;
	private double InsulationResistance;
	private double DCCurrentInputA;
	private double DCCurrentInputB;
	private double DCCurrentInputC;
	private double DCCurrentInputD;
	private double DCCurrentInputE;
	private double DCVoltageInputA;
	private double DCVoltageInputB;
	private double DCVoltageInputC;
	private double DCVoltageInputD;
	private double DCVoltageInputE;
	private double DCPowerInputA;
	private double DCPowerInputB;
	private double DCPowerInputC;
	private double DCPowerInputD;
	private double DCPowerInputE;
	private double DCCurrentInputF;
	private double DCVoltageInputF;
	private double DCPowerInputF;
	private double EventMessage;
	private double HealthCondiiton;
	private double Faultcorrectionmeasure;
	private double BlockStatus;
	private double ReasonforDerating;
	private double FeedinTime;
	private double OperatingTime;
	private double SerialNumber;
	private int totalFaultCode1;
	private int totalFaultCode2;
	private int totalFaultCode3;
	public double getCurrentPhase1() {
		return CurrentPhase1;
	}
	public void setCurrentPhase1(double currentPhase1) {
		CurrentPhase1 = currentPhase1;
	}
	public double getCurrentPhase2() {
		return CurrentPhase2;
	}
	public void setCurrentPhase2(double currentPhase2) {
		CurrentPhase2 = currentPhase2;
	}
	public double getCurrentPhase3() {
		return CurrentPhase3;
	}
	public void setCurrentPhase3(double currentPhase3) {
		CurrentPhase3 = currentPhase3;
	}
	public double getGridFrequency() {
		return GridFrequency;
	}
	public void setGridFrequency(double gridFrequency) {
		GridFrequency = gridFrequency;
	}
	public double getVoltagePhase1() {
		return VoltagePhase1;
	}
	public void setVoltagePhase1(double voltagePhase1) {
		VoltagePhase1 = voltagePhase1;
	}
	public double getVoltagePhase2() {
		return VoltagePhase2;
	}
	public void setVoltagePhase2(double voltagePhase2) {
		VoltagePhase2 = voltagePhase2;
	}
	public double getVoltagePhase3() {
		return VoltagePhase3;
	}
	public void setVoltagePhase3(double voltagePhase3) {
		VoltagePhase3 = voltagePhase3;
	}
	public double getVoltagePhase12() {
		return VoltagePhase12;
	}
	public void setVoltagePhase12(double voltagePhase12) {
		VoltagePhase12 = voltagePhase12;
	}
	public double getVoltagePhase23() {
		return VoltagePhase23;
	}
	public void setVoltagePhase23(double voltagePhase23) {
		VoltagePhase23 = voltagePhase23;
	}
	public double getVoltagePhase31() {
		return VoltagePhase31;
	}
	public void setVoltagePhase31(double voltagePhase31) {
		VoltagePhase31 = voltagePhase31;
	}
	public double getPowerFactor() {
		return PowerFactor;
	}
	public void setPowerFactor(double powerFactor) {
		PowerFactor = powerFactor;
	}
	public double getActivePower() {
		return ActivePower;
	}
	public void setActivePower(double activePower) {
		ActivePower = activePower;
	}
	public double getActivePowerPhase1() {
		return ActivePowerPhase1;
	}
	public void setActivePowerPhase1(double activePowerPhase1) {
		ActivePowerPhase1 = activePowerPhase1;
	}
	public double getActivePowerPhase2() {
		return ActivePowerPhase2;
	}
	public void setActivePowerPhase2(double activePowerPhase2) {
		ActivePowerPhase2 = activePowerPhase2;
	}
	public double getActivePowerPhase3() {
		return ActivePowerPhase3;
	}
	public void setActivePowerPhase3(double activePowerPhase3) {
		ActivePowerPhase3 = activePowerPhase3;
	}
	public double getApparentPower() {
		return ApparentPower;
	}
	public void setApparentPower(double apparentPower) {
		ApparentPower = apparentPower;
	}
	public double getApparentPowerPhase1() {
		return ApparentPowerPhase1;
	}
	public void setApparentPowerPhase1(double apparentPowerPhase1) {
		ApparentPowerPhase1 = apparentPowerPhase1;
	}
	public double getApparentPowerPhase2() {
		return ApparentPowerPhase2;
	}
	public void setApparentPowerPhase2(double apparentPowerPhase2) {
		ApparentPowerPhase2 = apparentPowerPhase2;
	}
	public double getApparentPowerPhase3() {
		return ApparentPowerPhase3;
	}
	public void setApparentPowerPhase3(double apparentPowerPhase3) {
		ApparentPowerPhase3 = apparentPowerPhase3;
	}
	public double getReactivePower() {
		return ReactivePower;
	}
	public void setReactivePower(double reactivePower) {
		ReactivePower = reactivePower;
	}
	public double getReactivePowerPhase1() {
		return ReactivePowerPhase1;
	}
	public void setReactivePowerPhase1(double reactivePowerPhase1) {
		ReactivePowerPhase1 = reactivePowerPhase1;
	}
	public double getReactivePowerPhase2() {
		return ReactivePowerPhase2;
	}
	public void setReactivePowerPhase2(double reactivePowerPhase2) {
		ReactivePowerPhase2 = reactivePowerPhase2;
	}
	public double getReactivePowerPhase3() {
		return ReactivePowerPhase3;
	}
	public void setReactivePowerPhase3(double reactivePowerPhase3) {
		ReactivePowerPhase3 = reactivePowerPhase3;
	}
	public double getResidualCurrent() {
		return ResidualCurrent;
	}
	public void setResidualCurrent(double residualCurrent) {
		ResidualCurrent = residualCurrent;
	}
	public double getInternalTemperature() {
		return InternalTemperature;
	}
	public void setInternalTemperature(double internalTemperature) {
		InternalTemperature = internalTemperature;
	}
	public double getTotalYield() {
		return TotalYield;
	}
	public void setTotalYield(double totalYield) {
		TotalYield = totalYield;
	}
	public double getInsulationResistance() {
		return InsulationResistance;
	}
	public void setInsulationResistance(double insulationResistance) {
		InsulationResistance = insulationResistance;
	}
	public double getDCCurrentInputA() {
		return DCCurrentInputA;
	}
	public void setDCCurrentInputA(double dCCurrentInputA) {
		DCCurrentInputA = dCCurrentInputA;
	}
	public double getDCCurrentInputB() {
		return DCCurrentInputB;
	}
	public void setDCCurrentInputB(double dCCurrentInputB) {
		DCCurrentInputB = dCCurrentInputB;
	}
	public double getDCCurrentInputC() {
		return DCCurrentInputC;
	}
	public void setDCCurrentInputC(double dCCurrentInputC) {
		DCCurrentInputC = dCCurrentInputC;
	}
	public double getDCCurrentInputD() {
		return DCCurrentInputD;
	}
	public void setDCCurrentInputD(double dCCurrentInputD) {
		DCCurrentInputD = dCCurrentInputD;
	}
	public double getDCCurrentInputE() {
		return DCCurrentInputE;
	}
	public void setDCCurrentInputE(double dCCurrentInputE) {
		DCCurrentInputE = dCCurrentInputE;
	}
	public double getDCVoltageInputA() {
		return DCVoltageInputA;
	}
	public void setDCVoltageInputA(double dCVoltageInputA) {
		DCVoltageInputA = dCVoltageInputA;
	}
	public double getDCVoltageInputB() {
		return DCVoltageInputB;
	}
	public void setDCVoltageInputB(double dCVoltageInputB) {
		DCVoltageInputB = dCVoltageInputB;
	}
	public double getDCVoltageInputC() {
		return DCVoltageInputC;
	}
	public void setDCVoltageInputC(double dCVoltageInputC) {
		DCVoltageInputC = dCVoltageInputC;
	}
	public double getDCVoltageInputD() {
		return DCVoltageInputD;
	}
	public void setDCVoltageInputD(double dCVoltageInputD) {
		DCVoltageInputD = dCVoltageInputD;
	}
	public double getDCVoltageInputE() {
		return DCVoltageInputE;
	}
	public void setDCVoltageInputE(double dCVoltageInputE) {
		DCVoltageInputE = dCVoltageInputE;
	}
	public double getDCPowerInputA() {
		return DCPowerInputA;
	}
	public void setDCPowerInputA(double dCPowerInputA) {
		DCPowerInputA = dCPowerInputA;
	}
	public double getDCPowerInputB() {
		return DCPowerInputB;
	}
	public void setDCPowerInputB(double dCPowerInputB) {
		DCPowerInputB = dCPowerInputB;
	}
	public double getDCPowerInputC() {
		return DCPowerInputC;
	}
	public void setDCPowerInputC(double dCPowerInputC) {
		DCPowerInputC = dCPowerInputC;
	}
	public double getDCPowerInputD() {
		return DCPowerInputD;
	}
	public void setDCPowerInputD(double dCPowerInputD) {
		DCPowerInputD = dCPowerInputD;
	}
	public double getDCPowerInputE() {
		return DCPowerInputE;
	}
	public void setDCPowerInputE(double dCPowerInputE) {
		DCPowerInputE = dCPowerInputE;
	}
	public double getDCCurrentInputF() {
		return DCCurrentInputF;
	}
	public void setDCCurrentInputF(double dCCurrentInputF) {
		DCCurrentInputF = dCCurrentInputF;
	}
	public double getDCVoltageInputF() {
		return DCVoltageInputF;
	}
	public void setDCVoltageInputF(double dCVoltageInputF) {
		DCVoltageInputF = dCVoltageInputF;
	}
	public double getDCPowerInputF() {
		return DCPowerInputF;
	}
	public void setDCPowerInputF(double dCPowerInputF) {
		DCPowerInputF = dCPowerInputF;
	}
	public double getEventMessage() {
		return EventMessage;
	}
	public void setEventMessage(double eventMessage) {
		EventMessage = eventMessage;
	}
	public double getHealthCondiiton() {
		return HealthCondiiton;
	}
	public void setHealthCondiiton(double healthCondiiton) {
		HealthCondiiton = healthCondiiton;
	}
	public double getFaultcorrectionmeasure() {
		return Faultcorrectionmeasure;
	}
	public void setFaultcorrectionmeasure(double faultcorrectionmeasure) {
		Faultcorrectionmeasure = faultcorrectionmeasure;
	}
	public double getBlockStatus() {
		return BlockStatus;
	}
	public void setBlockStatus(double blockStatus) {
		BlockStatus = blockStatus;
	}
	public double getReasonforDerating() {
		return ReasonforDerating;
	}
	public void setReasonforDerating(double reasonforDerating) {
		ReasonforDerating = reasonforDerating;
	}
	public double getFeedinTime() {
		return FeedinTime;
	}
	public void setFeedinTime(double feedinTime) {
		FeedinTime = feedinTime;
	}
	public double getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(double operatingTime) {
		OperatingTime = operatingTime;
	}
	public double getSerialNumber() {
		return SerialNumber;
	}
	public void setSerialNumber(double serialNumber) {
		SerialNumber = serialNumber;
	}
	public int getTotalFaultCode1() {
		return totalFaultCode1;
	}
	public void setTotalFaultCode1(int totalFaultCode1) {
		this.totalFaultCode1 = totalFaultCode1;
	}
	public int getTotalFaultCode2() {
		return totalFaultCode2;
	}
	public void setTotalFaultCode2(int totalFaultCode2) {
		this.totalFaultCode2 = totalFaultCode2;
	}
	public int getTotalFaultCode3() {
		return totalFaultCode3;
	}
	public void setTotalFaultCode3(int totalFaultCode3) {
		this.totalFaultCode3 = totalFaultCode3;
	}
	
	
	
	
	
}
