/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelLovatoDmg800Entity extends ModelBaseEntity {
	private double L1phasevoltage;
	private double L2phasevoltage;
	private double L3phasevoltage;
	private double L1current;
	private double L2current;
	private double L3current;
	private double L1L2voltage;
	private double L2L3voltage;
	private double L3L1voltage;
	private double L1activepower;
	private double L2activepower;
	private double L3activepower;
	private double L1reactivepower;
	private double L2reactivepower;
	private double L3reactivepower;
	private double L1apparentpower;
	private double L2apparentpower;
	private double L3apparentpower;
	private double L1powerfactor;
	private double L2powerfactor;
	private double L3powerfactor;
	private double Frequency;
	private double EqvPhasevoltage;
	private double EqvPhasetophasevoltage;
	private double EqvCurrent;
	private double EqvActivepower;
	private double EqvReactivepower;
	private double EqvApparentpower;
	private double Eqvpowerfactor;
	private double Asymmetriyphasephasevoltage;
	private double Asymmetriyphaseneuralvoltage;
	private double Asymmetriyneutralcurrent;
	private double ThdL1voltage;
	private double ThdL2voltage;
	private double ThdL3voltage;
	private double ThdL1current;
	private double ThdL2current;
	private double ThdL3current;
	private double ThdL12voltage;
	private double ThdL23voltage;
	private double ThdL31voltage;
	private double ActiveEnergyImport;
	private double ActiveEnergyExport;
	private double ReactivgEnergyImport;
	private double ReactiveEnergyExport;
	private double ApparentEnergy;
	public double getL1phasevoltage() {
		return L1phasevoltage;
	}
	public void setL1phasevoltage(double l1phasevoltage) {
		L1phasevoltage = l1phasevoltage;
	}
	public double getL2phasevoltage() {
		return L2phasevoltage;
	}
	public void setL2phasevoltage(double l2phasevoltage) {
		L2phasevoltage = l2phasevoltage;
	}
	public double getL3phasevoltage() {
		return L3phasevoltage;
	}
	public void setL3phasevoltage(double l3phasevoltage) {
		L3phasevoltage = l3phasevoltage;
	}
	public double getL1current() {
		return L1current;
	}
	public void setL1current(double l1current) {
		L1current = l1current;
	}
	public double getL2current() {
		return L2current;
	}
	public void setL2current(double l2current) {
		L2current = l2current;
	}
	public double getL3current() {
		return L3current;
	}
	public void setL3current(double l3current) {
		L3current = l3current;
	}
	public double getL1L2voltage() {
		return L1L2voltage;
	}
	public void setL1L2voltage(double l1l2voltage) {
		L1L2voltage = l1l2voltage;
	}
	public double getL2L3voltage() {
		return L2L3voltage;
	}
	public void setL2L3voltage(double l2l3voltage) {
		L2L3voltage = l2l3voltage;
	}
	public double getL3L1voltage() {
		return L3L1voltage;
	}
	public void setL3L1voltage(double l3l1voltage) {
		L3L1voltage = l3l1voltage;
	}
	public double getL1activepower() {
		return L1activepower;
	}
	public void setL1activepower(double l1activepower) {
		L1activepower = l1activepower;
	}
	public double getL2activepower() {
		return L2activepower;
	}
	public void setL2activepower(double l2activepower) {
		L2activepower = l2activepower;
	}
	public double getL3activepower() {
		return L3activepower;
	}
	public void setL3activepower(double l3activepower) {
		L3activepower = l3activepower;
	}
	public double getL1reactivepower() {
		return L1reactivepower;
	}
	public void setL1reactivepower(double l1reactivepower) {
		L1reactivepower = l1reactivepower;
	}
	public double getL2reactivepower() {
		return L2reactivepower;
	}
	public void setL2reactivepower(double l2reactivepower) {
		L2reactivepower = l2reactivepower;
	}
	public double getL3reactivepower() {
		return L3reactivepower;
	}
	public void setL3reactivepower(double l3reactivepower) {
		L3reactivepower = l3reactivepower;
	}
	public double getL1apparentpower() {
		return L1apparentpower;
	}
	public void setL1apparentpower(double l1apparentpower) {
		L1apparentpower = l1apparentpower;
	}
	public double getL2apparentpower() {
		return L2apparentpower;
	}
	public void setL2apparentpower(double l2apparentpower) {
		L2apparentpower = l2apparentpower;
	}
	public double getL3apparentpower() {
		return L3apparentpower;
	}
	public void setL3apparentpower(double l3apparentpower) {
		L3apparentpower = l3apparentpower;
	}
	public double getL1powerfactor() {
		return L1powerfactor;
	}
	public void setL1powerfactor(double l1powerfactor) {
		L1powerfactor = l1powerfactor;
	}
	public double getL2powerfactor() {
		return L2powerfactor;
	}
	public void setL2powerfactor(double l2powerfactor) {
		L2powerfactor = l2powerfactor;
	}
	public double getL3powerfactor() {
		return L3powerfactor;
	}
	public void setL3powerfactor(double l3powerfactor) {
		L3powerfactor = l3powerfactor;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getEqvPhasevoltage() {
		return EqvPhasevoltage;
	}
	public void setEqvPhasevoltage(double eqvPhasevoltage) {
		EqvPhasevoltage = eqvPhasevoltage;
	}
	public double getEqvPhasetophasevoltage() {
		return EqvPhasetophasevoltage;
	}
	public void setEqvPhasetophasevoltage(double eqvPhasetophasevoltage) {
		EqvPhasetophasevoltage = eqvPhasetophasevoltage;
	}
	public double getEqvCurrent() {
		return EqvCurrent;
	}
	public void setEqvCurrent(double eqvCurrent) {
		EqvCurrent = eqvCurrent;
	}
	public double getEqvActivepower() {
		return EqvActivepower;
	}
	public void setEqvActivepower(double eqvActivepower) {
		EqvActivepower = eqvActivepower;
	}
	public double getEqvReactivepower() {
		return EqvReactivepower;
	}
	public void setEqvReactivepower(double eqvReactivepower) {
		EqvReactivepower = eqvReactivepower;
	}
	public double getEqvApparentpower() {
		return EqvApparentpower;
	}
	public void setEqvApparentpower(double eqvApparentpower) {
		EqvApparentpower = eqvApparentpower;
	}
	public double getEqvpowerfactor() {
		return Eqvpowerfactor;
	}
	public void setEqvpowerfactor(double eqvpowerfactor) {
		Eqvpowerfactor = eqvpowerfactor;
	}
	public double getAsymmetriyphasephasevoltage() {
		return Asymmetriyphasephasevoltage;
	}
	public void setAsymmetriyphasephasevoltage(double asymmetriyphasephasevoltage) {
		Asymmetriyphasephasevoltage = asymmetriyphasephasevoltage;
	}
	public double getAsymmetriyphaseneuralvoltage() {
		return Asymmetriyphaseneuralvoltage;
	}
	public void setAsymmetriyphaseneuralvoltage(double asymmetriyphaseneuralvoltage) {
		Asymmetriyphaseneuralvoltage = asymmetriyphaseneuralvoltage;
	}
	public double getAsymmetriyneutralcurrent() {
		return Asymmetriyneutralcurrent;
	}
	public void setAsymmetriyneutralcurrent(double asymmetriyneutralcurrent) {
		Asymmetriyneutralcurrent = asymmetriyneutralcurrent;
	}

	public double getThdL1voltage() {
		return ThdL1voltage;
	}
	public void setThdL1voltage(double thdL1voltage) {
		ThdL1voltage = thdL1voltage;
	}
	public double getThdL2voltage() {
		return ThdL2voltage;
	}
	public void setThdL2voltage(double thdL2voltage) {
		ThdL2voltage = thdL2voltage;
	}
	public double getThdL3voltage() {
		return ThdL3voltage;
	}
	public void setThdL3voltage(double thdL3voltage) {
		ThdL3voltage = thdL3voltage;
	}
	public double getThdL1current() {
		return ThdL1current;
	}
	public void setThdL1current(double thdL1current) {
		ThdL1current = thdL1current;
	}
	public double getThdL2current() {
		return ThdL2current;
	}
	public void setThdL2current(double thdL2current) {
		ThdL2current = thdL2current;
	}
	public double getThdL3current() {
		return ThdL3current;
	}
	public void setThdL3current(double thdL3current) {
		ThdL3current = thdL3current;
	}
	public double getThdL12voltage() {
		return ThdL12voltage;
	}
	public void setThdL12voltage(double thdL12voltage) {
		ThdL12voltage = thdL12voltage;
	}
	public double getThdL23voltage() {
		return ThdL23voltage;
	}
	public void setThdL23voltage(double thdL23voltage) {
		ThdL23voltage = thdL23voltage;
	}
	public double getThdL31voltage() {
		return ThdL31voltage;
	}
	public void setThdL31voltage(double thdL31voltage) {
		ThdL31voltage = thdL31voltage;
	}
	public double getActiveEnergyImport() {
		return ActiveEnergyImport;
	}
	public void setActiveEnergyImport(double activeEnergyImport) {
		ActiveEnergyImport = activeEnergyImport;
	}
	public double getActiveEnergyExport() {
		return ActiveEnergyExport;
	}
	public void setActiveEnergyExport(double activeEnergyExport) {
		ActiveEnergyExport = activeEnergyExport;
	}
	public double getReactivgEnergyImport() {
		return ReactivgEnergyImport;
	}
	public void setReactivgEnergyImport(double reactivgEnergyImport) {
		ReactivgEnergyImport = reactivgEnergyImport;
	}
	public double getReactiveEnergyExport() {
		return ReactiveEnergyExport;
	}
	public void setReactiveEnergyExport(double reactiveEnergyExport) {
		ReactiveEnergyExport = reactiveEnergyExport;
	}
	public double getApparentEnergy() {
		return ApparentEnergy;
	}
	public void setApparentEnergy(double apparentEnergy) {
		ApparentEnergy = apparentEnergy;
	}
	
	
}
