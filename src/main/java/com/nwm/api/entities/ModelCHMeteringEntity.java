/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelCHMeteringEntity extends ModelBaseEntity {
	private double VoltageL1;
	private double VoltageL2;
	private double VoltageL3;
	private double CurrentL1;
	private double CurrentL2;
	private double CurrentL3;
	private double CurrentN;
	private double Totalcurrent;
	private double ActivepowerL1;
	private double ActivepowerL2;
	private double ActivepowerL3;
	private double Totalactivepower;
	private double ReactivepowerL1;
	private double ReactivepowerL2;
	private double ReactivepowerL3;
	private double Totalreactivepower;
	private double ApparentpowerL1;
	private double ApparentpowerL2;
	private double ApparentpowerL3;
	private double Totalapparentpower;
	private double PowerfactorL1;
	private double PowerfactorL2;
	private double PowerfactorL3;
	private double Totalpowerfactor;
	private double Frequency;
	private double Activeenergynet;
	private double Activeenergyimport;
	private double Activeenergyexport;
	private double Reactiveenergyimport;
	private double ReactiveEnergyexport;
	private double Modbusid;
	public double getVoltageL1() {
		return VoltageL1;
	}
	public void setVoltageL1(double voltageL1) {
		VoltageL1 = voltageL1;
	}
	public double getVoltageL2() {
		return VoltageL2;
	}
	public void setVoltageL2(double voltageL2) {
		VoltageL2 = voltageL2;
	}
	public double getVoltageL3() {
		return VoltageL3;
	}
	public void setVoltageL3(double voltageL3) {
		VoltageL3 = voltageL3;
	}
	public double getCurrentL1() {
		return CurrentL1;
	}
	public void setCurrentL1(double currentL1) {
		CurrentL1 = currentL1;
	}
	public double getCurrentL2() {
		return CurrentL2;
	}
	public void setCurrentL2(double currentL2) {
		CurrentL2 = currentL2;
	}
	public double getCurrentL3() {
		return CurrentL3;
	}
	public void setCurrentL3(double currentL3) {
		CurrentL3 = currentL3;
	}
	public double getCurrentN() {
		return CurrentN;
	}
	public void setCurrentN(double currentN) {
		CurrentN = currentN;
	}
	public double getTotalcurrent() {
		return Totalcurrent;
	}
	public void setTotalcurrent(double totalcurrent) {
		Totalcurrent = totalcurrent;
	}
	public double getActivepowerL1() {
		return ActivepowerL1;
	}
	public void setActivepowerL1(double activepowerL1) {
		ActivepowerL1 = activepowerL1;
	}
	public double getActivepowerL2() {
		return ActivepowerL2;
	}
	public void setActivepowerL2(double activepowerL2) {
		ActivepowerL2 = activepowerL2;
	}
	public double getActivepowerL3() {
		return ActivepowerL3;
	}
	public void setActivepowerL3(double activepowerL3) {
		ActivepowerL3 = activepowerL3;
	}
	public double getTotalactivepower() {
		return Totalactivepower;
	}
	public void setTotalactivepower(double totalactivepower) {
		Totalactivepower = totalactivepower;
	}
	public double getReactivepowerL1() {
		return ReactivepowerL1;
	}
	public void setReactivepowerL1(double reactivepowerL1) {
		ReactivepowerL1 = reactivepowerL1;
	}
	public double getReactivepowerL2() {
		return ReactivepowerL2;
	}
	public void setReactivepowerL2(double reactivepowerL2) {
		ReactivepowerL2 = reactivepowerL2;
	}
	public double getReactivepowerL3() {
		return ReactivepowerL3;
	}
	public void setReactivepowerL3(double reactivepowerL3) {
		ReactivepowerL3 = reactivepowerL3;
	}
	public double getTotalreactivepower() {
		return Totalreactivepower;
	}
	public void setTotalreactivepower(double totalreactivepower) {
		Totalreactivepower = totalreactivepower;
	}
	public double getApparentpowerL1() {
		return ApparentpowerL1;
	}
	public void setApparentpowerL1(double apparentpowerL1) {
		ApparentpowerL1 = apparentpowerL1;
	}
	public double getApparentpowerL2() {
		return ApparentpowerL2;
	}
	public void setApparentpowerL2(double apparentpowerL2) {
		ApparentpowerL2 = apparentpowerL2;
	}
	public double getApparentpowerL3() {
		return ApparentpowerL3;
	}
	public void setApparentpowerL3(double apparentpowerL3) {
		ApparentpowerL3 = apparentpowerL3;
	}
	public double getTotalapparentpower() {
		return Totalapparentpower;
	}
	public void setTotalapparentpower(double totalapparentpower) {
		Totalapparentpower = totalapparentpower;
	}
	public double getPowerfactorL1() {
		return PowerfactorL1;
	}
	public void setPowerfactorL1(double powerfactorL1) {
		PowerfactorL1 = powerfactorL1;
	}
	public double getPowerfactorL2() {
		return PowerfactorL2;
	}
	public void setPowerfactorL2(double powerfactorL2) {
		PowerfactorL2 = powerfactorL2;
	}
	public double getPowerfactorL3() {
		return PowerfactorL3;
	}
	public void setPowerfactorL3(double powerfactorL3) {
		PowerfactorL3 = powerfactorL3;
	}
	public double getTotalpowerfactor() {
		return Totalpowerfactor;
	}
	public void setTotalpowerfactor(double totalpowerfactor) {
		Totalpowerfactor = totalpowerfactor;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getActiveenergynet() {
		return Activeenergynet;
	}
	public void setActiveenergynet(double activeenergynet) {
		Activeenergynet = activeenergynet;
	}
	public double getActiveenergyimport() {
		return Activeenergyimport;
	}
	public void setActiveenergyimport(double activeenergyimport) {
		Activeenergyimport = activeenergyimport;
	}
	public double getActiveenergyexport() {
		return Activeenergyexport;
	}
	public void setActiveenergyexport(double activeenergyexport) {
		Activeenergyexport = activeenergyexport;
	}
	public double getReactiveenergyimport() {
		return Reactiveenergyimport;
	}
	public void setReactiveenergyimport(double reactiveenergyimport) {
		Reactiveenergyimport = reactiveenergyimport;
	}
	public double getReactiveEnergyexport() {
		return ReactiveEnergyexport;
	}
	public void setReactiveEnergyexport(double reactiveEnergyexport) {
		ReactiveEnergyexport = reactiveEnergyexport;
	}
	public double getModbusid() {
		return Modbusid;
	}
	public void setModbusid(double modbusid) {
		Modbusid = modbusid;
	}
	
	
	
}
