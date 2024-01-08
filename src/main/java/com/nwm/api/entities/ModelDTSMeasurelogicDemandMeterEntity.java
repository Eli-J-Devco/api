/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelDTSMeasurelogicDemandMeterEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double Voltage_LN_1;
	private double Voltage_LN_2;
	private double Voltage_LN_3;
	private double Voltage_LL_Average;
	private double Current_1;
	private double Current_2;
	private double Current_3;
	private double Current_Total;
	private double Current_Neutral;
	private double Frequency_Average;
	private double PowerP_Total;
	private double PowerS_Total;
	private double PowerQ_Total;
	private double PowerFactor_DTS_Overall;
	private double EnergyP_Total;
	private double EnergyS_Total;
	private double EnergyQ_Total;
	private double EnergyP_Total_Imp;
	private double EnergyP_Total_Exp;
	private double EnergyQ_Total_Imp;
	private double EnergyQ_Total_Exp;
	
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
	public double getVoltage_LN_1() {
		return Voltage_LN_1;
	}
	public void setVoltage_LN_1(double voltage_LN_1) {
		Voltage_LN_1 = voltage_LN_1;
	}
	public double getVoltage_LN_2() {
		return Voltage_LN_2;
	}
	public void setVoltage_LN_2(double voltage_LN_2) {
		Voltage_LN_2 = voltage_LN_2;
	}
	public double getVoltage_LN_3() {
		return Voltage_LN_3;
	}
	public void setVoltage_LN_3(double voltage_LN_3) {
		Voltage_LN_3 = voltage_LN_3;
	}
	public double getVoltage_LL_Average() {
		return Voltage_LL_Average;
	}
	public void setVoltage_LL_Average(double voltage_LL_Average) {
		Voltage_LL_Average = voltage_LL_Average;
	}
	public double getCurrent_1() {
		return Current_1;
	}
	public void setCurrent_1(double current_1) {
		Current_1 = current_1;
	}
	public double getCurrent_2() {
		return Current_2;
	}
	public void setCurrent_2(double current_2) {
		Current_2 = current_2;
	}
	public double getCurrent_3() {
		return Current_3;
	}
	public void setCurrent_3(double current_3) {
		Current_3 = current_3;
	}
	public double getCurrent_Total() {
		return Current_Total;
	}
	public void setCurrent_Total(double current_Total) {
		Current_Total = current_Total;
	}
	public double getCurrent_Neutral() {
		return Current_Neutral;
	}
	public void setCurrent_Neutral(double current_Neutral) {
		Current_Neutral = current_Neutral;
	}
	public double getFrequency_Average() {
		return Frequency_Average;
	}
	public void setFrequency_Average(double frequency_Average) {
		Frequency_Average = frequency_Average;
	}
	public double getPowerP_Total() {
		return PowerP_Total;
	}
	public void setPowerP_Total(double powerP_Total) {
		PowerP_Total = powerP_Total;
	}
	public double getPowerS_Total() {
		return PowerS_Total;
	}
	public void setPowerS_Total(double powerS_Total) {
		PowerS_Total = powerS_Total;
	}
	public double getPowerQ_Total() {
		return PowerQ_Total;
	}
	public void setPowerQ_Total(double powerQ_Total) {
		PowerQ_Total = powerQ_Total;
	}
	public double getPowerFactor_DTS_Overall() {
		return PowerFactor_DTS_Overall;
	}
	public void setPowerFactor_DTS_Overall(double powerFactor_DTS_Overall) {
		PowerFactor_DTS_Overall = powerFactor_DTS_Overall;
	}
	public double getEnergyP_Total() {
		return EnergyP_Total;
	}
	public void setEnergyP_Total(double energyP_Total) {
		EnergyP_Total = energyP_Total;
	}
	public double getEnergyS_Total() {
		return EnergyS_Total;
	}
	public void setEnergyS_Total(double energyS_Total) {
		EnergyS_Total = energyS_Total;
	}
	public double getEnergyQ_Total() {
		return EnergyQ_Total;
	}
	public void setEnergyQ_Total(double energyQ_Total) {
		EnergyQ_Total = energyQ_Total;
	}
	public double getEnergyP_Total_Imp() {
		return EnergyP_Total_Imp;
	}
	public void setEnergyP_Total_Imp(double energyP_Total_Imp) {
		EnergyP_Total_Imp = energyP_Total_Imp;
	}
	public double getEnergyP_Total_Exp() {
		return EnergyP_Total_Exp;
	}
	public void setEnergyP_Total_Exp(double energyP_Total_Exp) {
		EnergyP_Total_Exp = energyP_Total_Exp;
	}
	public double getEnergyQ_Total_Imp() {
		return EnergyQ_Total_Imp;
	}
	public void setEnergyQ_Total_Imp(double energyQ_Total_Imp) {
		EnergyQ_Total_Imp = energyQ_Total_Imp;
	}
	public double getEnergyQ_Total_Exp() {
		return EnergyQ_Total_Exp;
	}
	public void setEnergyQ_Total_Exp(double energyQ_Total_Exp) {
		EnergyQ_Total_Exp = energyQ_Total_Exp;
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
