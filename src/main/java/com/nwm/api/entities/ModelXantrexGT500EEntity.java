/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelXantrexGT500EEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double AC_CURRENT_A;
	private double AC_CURRENT_B;
	private double AC_CURRENT_C;
	private double AC_POWER;
	private double AC_VOLTAGE_AB;
	private double AC_VOLTAGE_BC;
	private double AC_VOLTAGE_CA;
	private double DC_CURRENT;
	private double DC_POWER;
	private double DC_VOLTAGE;
	private double ENERGY_DELIVERED;
	private double FREQUENCY;
	private double STATUS_FAULT;
	private double STATUS_GOAL;
	private double STATUS_INVERTER;
	private double STATUS_OPERATING;
	private double STATUS_PV;
	private double T_LEFT_MATRIX;
	private double T_RIGHT_MATRIX;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	
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
	public double getAC_CURRENT_A() {
		return AC_CURRENT_A;
	}
	public void setAC_CURRENT_A(double aC_CURRENT_A) {
		AC_CURRENT_A = aC_CURRENT_A;
	}
	public double getAC_CURRENT_B() {
		return AC_CURRENT_B;
	}
	public void setAC_CURRENT_B(double aC_CURRENT_B) {
		AC_CURRENT_B = aC_CURRENT_B;
	}
	public double getAC_CURRENT_C() {
		return AC_CURRENT_C;
	}
	public void setAC_CURRENT_C(double aC_CURRENT_C) {
		AC_CURRENT_C = aC_CURRENT_C;
	}
	public double getAC_POWER() {
		return AC_POWER;
	}
	public void setAC_POWER(double aC_POWER) {
		AC_POWER = aC_POWER;
	}
	public double getAC_VOLTAGE_AB() {
		return AC_VOLTAGE_AB;
	}
	public void setAC_VOLTAGE_AB(double aC_VOLTAGE_AB) {
		AC_VOLTAGE_AB = aC_VOLTAGE_AB;
	}
	public double getAC_VOLTAGE_BC() {
		return AC_VOLTAGE_BC;
	}
	public void setAC_VOLTAGE_BC(double aC_VOLTAGE_BC) {
		AC_VOLTAGE_BC = aC_VOLTAGE_BC;
	}
	public double getAC_VOLTAGE_CA() {
		return AC_VOLTAGE_CA;
	}
	public void setAC_VOLTAGE_CA(double aC_VOLTAGE_CA) {
		AC_VOLTAGE_CA = aC_VOLTAGE_CA;
	}
	public double getDC_CURRENT() {
		return DC_CURRENT;
	}
	public void setDC_CURRENT(double dC_CURRENT) {
		DC_CURRENT = dC_CURRENT;
	}
	public double getDC_POWER() {
		return DC_POWER;
	}
	public void setDC_POWER(double dC_POWER) {
		DC_POWER = dC_POWER;
	}
	public double getDC_VOLTAGE() {
		return DC_VOLTAGE;
	}
	public void setDC_VOLTAGE(double dC_VOLTAGE) {
		DC_VOLTAGE = dC_VOLTAGE;
	}
	public double getENERGY_DELIVERED() {
		return ENERGY_DELIVERED;
	}
	public void setENERGY_DELIVERED(double eNERGY_DELIVERED) {
		ENERGY_DELIVERED = eNERGY_DELIVERED;
	}
	public double getFREQUENCY() {
		return FREQUENCY;
	}
	public void setFREQUENCY(double fREQUENCY) {
		FREQUENCY = fREQUENCY;
	}
	public double getSTATUS_FAULT() {
		return STATUS_FAULT;
	}
	public void setSTATUS_FAULT(double sTATUS_FAULT) {
		STATUS_FAULT = sTATUS_FAULT;
	}
	public double getSTATUS_GOAL() {
		return STATUS_GOAL;
	}
	public void setSTATUS_GOAL(double sTATUS_GOAL) {
		STATUS_GOAL = sTATUS_GOAL;
	}
	public double getSTATUS_INVERTER() {
		return STATUS_INVERTER;
	}
	public void setSTATUS_INVERTER(double sTATUS_INVERTER) {
		STATUS_INVERTER = sTATUS_INVERTER;
	}
	public double getSTATUS_OPERATING() {
		return STATUS_OPERATING;
	}
	public void setSTATUS_OPERATING(double sTATUS_OPERATING) {
		STATUS_OPERATING = sTATUS_OPERATING;
	}
	public double getSTATUS_PV() {
		return STATUS_PV;
	}
	public void setSTATUS_PV(double sTATUS_PV) {
		STATUS_PV = sTATUS_PV;
	}
	public double getT_LEFT_MATRIX() {
		return T_LEFT_MATRIX;
	}
	public void setT_LEFT_MATRIX(double t_LEFT_MATRIX) {
		T_LEFT_MATRIX = t_LEFT_MATRIX;
	}
	public double getT_RIGHT_MATRIX() {
		return T_RIGHT_MATRIX;
	}
	public void setT_RIGHT_MATRIX(double t_RIGHT_MATRIX) {
		T_RIGHT_MATRIX = t_RIGHT_MATRIX;
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
