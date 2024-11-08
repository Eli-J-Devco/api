/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelShark250Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double ActivePower;
	private double ReactivePower;
	private double ApparentPower;
	private double ActiveEnergyReceived;
	private double ActiveEnergyDelivered;
	private double ActiveEnergyNet; 
	private double ReactiveEnergyReceived;
	private double ReactiveEnergyDelivered;
	private double ReactiveEnergyNet;
	private double ApparentEnergy;
	private double Frequency;
	private double PowerFactor;
	private double ActiveEnergyReceivedRaw;
	private double ActiveEnergyDeliveredRaw;
	private double ActiveEnergyNetRaw;
	private double 	VoltageAN;
	private double VoltageBN;
	private double VoltageCN;
	private double VoltageAB;
	private double VoltageBC;
	private double VoltageCA;
	private double ACCurrentA;
	private double ACCurrentB;
	private double ACCurrentC;
	private double ACCurrentNeutral;
	private double ActivePowerA;
	private double ActivePowerB;
	private double ActivePowerC;
	private double ReactivePowerA;
	private double ReactivePowerB;
	private double 	ReactivePowerC;
	private double PowerFactorA;
	private double PowerFactorB;
	private double PowerFactorC;
	private double Serialnumber;
	private double Firmwareversion;
	private double CurrentTransformerPrimary;
	private double PotentialTransformerPrimary;
	private double PotentialTransformerSecondary;
	private double 	CurrentTransformerSecondary;
	
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
	public double getActivePower() {
		return ActivePower;
	}
	public void setActivePower(double activePower) {
		ActivePower = activePower;
	}
	public double getReactivePower() {
		return ReactivePower;
	}
	public void setReactivePower(double reactivePower) {
		ReactivePower = reactivePower;
	}
	public double getApparentPower() {
		return ApparentPower;
	}
	public void setApparentPower(double apparentPower) {
		ApparentPower = apparentPower;
	}
	public double getActiveEnergyReceived() {
		return ActiveEnergyReceived;
	}
	public void setActiveEnergyReceived(double activeEnergyReceived) {
		ActiveEnergyReceived = activeEnergyReceived;
	}
	public double getActiveEnergyDelivered() {
		return ActiveEnergyDelivered;
	}
	public void setActiveEnergyDelivered(double activeEnergyDelivered) {
		ActiveEnergyDelivered = activeEnergyDelivered;
	}
	public double getActiveEnergyNet() {
		return ActiveEnergyNet;
	}
	public void setActiveEnergyNet(double activeEnergyNet) {
		ActiveEnergyNet = activeEnergyNet;
	}
	public double getReactiveEnergyReceived() {
		return ReactiveEnergyReceived;
	}
	public void setReactiveEnergyReceived(double reactiveEnergyReceived) {
		ReactiveEnergyReceived = reactiveEnergyReceived;
	}
	public double getReactiveEnergyDelivered() {
		return ReactiveEnergyDelivered;
	}
	public void setReactiveEnergyDelivered(double reactiveEnergyDelivered) {
		ReactiveEnergyDelivered = reactiveEnergyDelivered;
	}
	public double getReactiveEnergyNet() {
		return ReactiveEnergyNet;
	}
	public void setReactiveEnergyNet(double reactiveEnergyNet) {
		ReactiveEnergyNet = reactiveEnergyNet;
	}
	public double getApparentEnergy() {
		return ApparentEnergy;
	}
	public void setApparentEnergy(double apparentEnergy) {
		ApparentEnergy = apparentEnergy;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getPowerFactor() {
		return PowerFactor;
	}
	public void setPowerFactor(double powerFactor) {
		PowerFactor = powerFactor;
	}
	public double getActiveEnergyReceivedRaw() {
		return ActiveEnergyReceivedRaw;
	}
	public void setActiveEnergyReceivedRaw(double activeEnergyReceivedRaw) {
		ActiveEnergyReceivedRaw = activeEnergyReceivedRaw;
	}
	public double getActiveEnergyDeliveredRaw() {
		return ActiveEnergyDeliveredRaw;
	}
	public void setActiveEnergyDeliveredRaw(double activeEnergyDeliveredRaw) {
		ActiveEnergyDeliveredRaw = activeEnergyDeliveredRaw;
	}
	public double getActiveEnergyNetRaw() {
		return ActiveEnergyNetRaw;
	}
	public void setActiveEnergyNetRaw(double activeEnergyNetRaw) {
		ActiveEnergyNetRaw = activeEnergyNetRaw;
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
	public double getVoltageAB() {
		return VoltageAB;
	}
	public void setVoltageAB(double voltageAB) {
		VoltageAB = voltageAB;
	}
	public double getVoltageBC() {
		return VoltageBC;
	}
	public void setVoltageBC(double voltageBC) {
		VoltageBC = voltageBC;
	}
	public double getVoltageCA() {
		return VoltageCA;
	}
	public void setVoltageCA(double voltageCA) {
		VoltageCA = voltageCA;
	}
	public double getACCurrentA() {
		return ACCurrentA;
	}
	public void setACCurrentA(double aCCurrentA) {
		ACCurrentA = aCCurrentA;
	}
	public double getACCurrentB() {
		return ACCurrentB;
	}
	public void setACCurrentB(double aCCurrentB) {
		ACCurrentB = aCCurrentB;
	}
	public double getACCurrentC() {
		return ACCurrentC;
	}
	public void setACCurrentC(double aCCurrentC) {
		ACCurrentC = aCCurrentC;
	}
	public double getACCurrentNeutral() {
		return ACCurrentNeutral;
	}
	public void setACCurrentNeutral(double aCCurrentNeutral) {
		ACCurrentNeutral = aCCurrentNeutral;
	}
	public double getActivePowerA() {
		return ActivePowerA;
	}
	public void setActivePowerA(double activePowerA) {
		ActivePowerA = activePowerA;
	}
	public double getActivePowerB() {
		return ActivePowerB;
	}
	public void setActivePowerB(double activePowerB) {
		ActivePowerB = activePowerB;
	}
	public double getActivePowerC() {
		return ActivePowerC;
	}
	public void setActivePowerC(double activePowerC) {
		ActivePowerC = activePowerC;
	}
	public double getReactivePowerA() {
		return ReactivePowerA;
	}
	public void setReactivePowerA(double reactivePowerA) {
		ReactivePowerA = reactivePowerA;
	}
	public double getReactivePowerB() {
		return ReactivePowerB;
	}
	public void setReactivePowerB(double reactivePowerB) {
		ReactivePowerB = reactivePowerB;
	}
	public double getReactivePowerC() {
		return ReactivePowerC;
	}
	public void setReactivePowerC(double reactivePowerC) {
		ReactivePowerC = reactivePowerC;
	}
	public double getPowerFactorA() {
		return PowerFactorA;
	}
	public void setPowerFactorA(double powerFactorA) {
		PowerFactorA = powerFactorA;
	}
	public double getPowerFactorB() {
		return PowerFactorB;
	}
	public void setPowerFactorB(double powerFactorB) {
		PowerFactorB = powerFactorB;
	}
	public double getPowerFactorC() {
		return PowerFactorC;
	}
	public void setPowerFactorC(double powerFactorC) {
		PowerFactorC = powerFactorC;
	}
	public double getSerialnumber() {
		return Serialnumber;
	}
	public void setSerialnumber(double serialnumber) {
		Serialnumber = serialnumber;
	}
	public double getFirmwareversion() {
		return Firmwareversion;
	}
	public void setFirmwareversion(double firmwareversion) {
		Firmwareversion = firmwareversion;
	}
	public double getCurrentTransformerPrimary() {
		return CurrentTransformerPrimary;
	}
	public void setCurrentTransformerPrimary(double currentTransformerPrimary) {
		CurrentTransformerPrimary = currentTransformerPrimary;
	}
	public double getPotentialTransformerPrimary() {
		return PotentialTransformerPrimary;
	}
	public void setPotentialTransformerPrimary(double potentialTransformerPrimary) {
		PotentialTransformerPrimary = potentialTransformerPrimary;
	}
	public double getPotentialTransformerSecondary() {
		return PotentialTransformerSecondary;
	}
	public void setPotentialTransformerSecondary(double potentialTransformerSecondary) {
		PotentialTransformerSecondary = potentialTransformerSecondary;
	}
	public double getCurrentTransformerSecondary() {
		return CurrentTransformerSecondary;
	}
	public void setCurrentTransformerSecondary(double currentTransformerSecondary) {
		CurrentTransformerSecondary = currentTransformerSecondary;
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
