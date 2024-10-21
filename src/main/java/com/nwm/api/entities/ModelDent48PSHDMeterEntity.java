/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelDent48PSHDMeterEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double CurrentSum;
	private double CurrentCH1A;
	private double CurrentCH2B;
	private double CurrentCH3C;
	private double VoltageLNAvg;
	private double VoltageL1NAN;
	private double VoltageL2NBN;
	private double VoltageL3NCN;
	private double VoltageLLAvg;
	private double VoltageL1L2;
	private double VoltageL2L3;
	private double VoltageL3L1;
	private double LineFrequency;
	private double PowerSum;
	private double PowerCH1A;
	private double PowerCH2B;
	private double PowerCH3C;
	private double VASum;
	private double VACH1A;
	private double 	VACH2B;
	private double VACH3C;
	private double VARSum;
	private double VARCH1A;
	private double VARCH2B;
	private double VARCH3C;
	private double ApparentPFAvg;
	private double ApparentPFCH1A;
	private double ApparentPFCH2B;
	private double ApparentPFCH3C;
	private double 	ExportedEnergySum;
	private double 	ExportedEnergyCH1A;
	private double 	ExportedEnergyCH2B;
	private double ExportedEnergyCH3C;
	private double ImportedEnergySum;
	private double ImportedEnergyCH1A;
	private double ImportedEnergyCH2B;
	private double 	ImportedEnergyCH3C;
	private double ExportedVAhSum;
	private double ExportedVAhCH1A;
	private double 	ExportedVAhCH2B;
	private double ExportedVAhCH3C;
	private double 	ImportedVAhSum;
	private double ImportedVAhCH1A;
	private double 	ImportedVAhCH2B;
	private double 	ImportedVAhCH3C;
	private double 	ImportedVARhQ1Sum;
	private double 	ImportedVARhQ1A;
	private double 	ImportedVARhQ1B;
	private double 	ImportedVARhQ1C;
	private double 	ImportedVARhQ2Sum;
	private double 	ImportedVARhQ2A;
	private double 	ImportedVARhQ2B;
	private double 	ImportedVARhQ2C;
	private double 	ExportedVARhQ3Sum;
	private double 	ExportedVARhQ3A;
	private double ExportedVARhQ3B;
	private double 	ExportedVARhQ3C;
	private double ExportedVARhQ4Sum;
	private double ExportedVARhQ4A;
	private double 	ExportedVARhQ4B;
	private double 	ExportedVARhQ4C;
	private double WattDemandElement;
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
	public double getCurrentSum() {
		return CurrentSum;
	}
	public void setCurrentSum(double currentSum) {
		CurrentSum = currentSum;
	}
	public double getCurrentCH1A() {
		return CurrentCH1A;
	}
	public void setCurrentCH1A(double currentCH1A) {
		CurrentCH1A = currentCH1A;
	}
	public double getCurrentCH2B() {
		return CurrentCH2B;
	}
	public void setCurrentCH2B(double currentCH2B) {
		CurrentCH2B = currentCH2B;
	}
	public double getCurrentCH3C() {
		return CurrentCH3C;
	}
	public void setCurrentCH3C(double currentCH3C) {
		CurrentCH3C = currentCH3C;
	}
	public double getVoltageLNAvg() {
		return VoltageLNAvg;
	}
	public void setVoltageLNAvg(double voltageLNAvg) {
		VoltageLNAvg = voltageLNAvg;
	}
	public double getVoltageL1NAN() {
		return VoltageL1NAN;
	}
	public void setVoltageL1NAN(double voltageL1NAN) {
		VoltageL1NAN = voltageL1NAN;
	}
	public double getVoltageL2NBN() {
		return VoltageL2NBN;
	}
	public void setVoltageL2NBN(double voltageL2NBN) {
		VoltageL2NBN = voltageL2NBN;
	}
	public double getVoltageL3NCN() {
		return VoltageL3NCN;
	}
	public void setVoltageL3NCN(double voltageL3NCN) {
		VoltageL3NCN = voltageL3NCN;
	}
	public double getVoltageLLAvg() {
		return VoltageLLAvg;
	}
	public void setVoltageLLAvg(double voltageLLAvg) {
		VoltageLLAvg = voltageLLAvg;
	}
	public double getVoltageL1L2() {
		return VoltageL1L2;
	}
	public void setVoltageL1L2(double voltageL1L2) {
		VoltageL1L2 = voltageL1L2;
	}
	public double getVoltageL2L3() {
		return VoltageL2L3;
	}
	public void setVoltageL2L3(double voltageL2L3) {
		VoltageL2L3 = voltageL2L3;
	}
	public double getVoltageL3L1() {
		return VoltageL3L1;
	}
	public void setVoltageL3L1(double voltageL3L1) {
		VoltageL3L1 = voltageL3L1;
	}
	public double getLineFrequency() {
		return LineFrequency;
	}
	public void setLineFrequency(double lineFrequency) {
		LineFrequency = lineFrequency;
	}
	public double getPowerSum() {
		return PowerSum;
	}
	public void setPowerSum(double powerSum) {
		PowerSum = powerSum;
	}
	public double getPowerCH1A() {
		return PowerCH1A;
	}
	public void setPowerCH1A(double powerCH1A) {
		PowerCH1A = powerCH1A;
	}
	public double getPowerCH2B() {
		return PowerCH2B;
	}
	public void setPowerCH2B(double powerCH2B) {
		PowerCH2B = powerCH2B;
	}
	public double getPowerCH3C() {
		return PowerCH3C;
	}
	public void setPowerCH3C(double powerCH3C) {
		PowerCH3C = powerCH3C;
	}
	public double getVASum() {
		return VASum;
	}
	public void setVASum(double vASum) {
		VASum = vASum;
	}
	public double getVACH1A() {
		return VACH1A;
	}
	public void setVACH1A(double vACH1A) {
		VACH1A = vACH1A;
	}
	public double getVACH2B() {
		return VACH2B;
	}
	public void setVACH2B(double vACH2B) {
		VACH2B = vACH2B;
	}
	public double getVACH3C() {
		return VACH3C;
	}
	public void setVACH3C(double vACH3C) {
		VACH3C = vACH3C;
	}
	public double getVARSum() {
		return VARSum;
	}
	public void setVARSum(double vARSum) {
		VARSum = vARSum;
	}
	public double getVARCH1A() {
		return VARCH1A;
	}
	public void setVARCH1A(double vARCH1A) {
		VARCH1A = vARCH1A;
	}
	public double getVARCH2B() {
		return VARCH2B;
	}
	public void setVARCH2B(double vARCH2B) {
		VARCH2B = vARCH2B;
	}
	public double getVARCH3C() {
		return VARCH3C;
	}
	public void setVARCH3C(double vARCH3C) {
		VARCH3C = vARCH3C;
	}
	public double getApparentPFAvg() {
		return ApparentPFAvg;
	}
	public void setApparentPFAvg(double apparentPFAvg) {
		ApparentPFAvg = apparentPFAvg;
	}
	public double getApparentPFCH1A() {
		return ApparentPFCH1A;
	}
	public void setApparentPFCH1A(double apparentPFCH1A) {
		ApparentPFCH1A = apparentPFCH1A;
	}
	public double getApparentPFCH2B() {
		return ApparentPFCH2B;
	}
	public void setApparentPFCH2B(double apparentPFCH2B) {
		ApparentPFCH2B = apparentPFCH2B;
	}
	public double getApparentPFCH3C() {
		return ApparentPFCH3C;
	}
	public void setApparentPFCH3C(double apparentPFCH3C) {
		ApparentPFCH3C = apparentPFCH3C;
	}
	public double getExportedEnergySum() {
		return ExportedEnergySum;
	}
	public void setExportedEnergySum(double exportedEnergySum) {
		ExportedEnergySum = exportedEnergySum;
	}
	public double getExportedEnergyCH1A() {
		return ExportedEnergyCH1A;
	}
	public void setExportedEnergyCH1A(double exportedEnergyCH1A) {
		ExportedEnergyCH1A = exportedEnergyCH1A;
	}
	public double getExportedEnergyCH2B() {
		return ExportedEnergyCH2B;
	}
	public void setExportedEnergyCH2B(double exportedEnergyCH2B) {
		ExportedEnergyCH2B = exportedEnergyCH2B;
	}
	public double getExportedEnergyCH3C() {
		return ExportedEnergyCH3C;
	}
	public void setExportedEnergyCH3C(double exportedEnergyCH3C) {
		ExportedEnergyCH3C = exportedEnergyCH3C;
	}
	public double getImportedEnergySum() {
		return ImportedEnergySum;
	}
	public void setImportedEnergySum(double importedEnergySum) {
		ImportedEnergySum = importedEnergySum;
	}
	public double getImportedEnergyCH1A() {
		return ImportedEnergyCH1A;
	}
	public void setImportedEnergyCH1A(double importedEnergyCH1A) {
		ImportedEnergyCH1A = importedEnergyCH1A;
	}
	public double getImportedEnergyCH2B() {
		return ImportedEnergyCH2B;
	}
	public void setImportedEnergyCH2B(double importedEnergyCH2B) {
		ImportedEnergyCH2B = importedEnergyCH2B;
	}
	public double getImportedEnergyCH3C() {
		return ImportedEnergyCH3C;
	}
	public void setImportedEnergyCH3C(double importedEnergyCH3C) {
		ImportedEnergyCH3C = importedEnergyCH3C;
	}
	public double getExportedVAhSum() {
		return ExportedVAhSum;
	}
	public void setExportedVAhSum(double exportedVAhSum) {
		ExportedVAhSum = exportedVAhSum;
	}
	public double getExportedVAhCH1A() {
		return ExportedVAhCH1A;
	}
	public void setExportedVAhCH1A(double exportedVAhCH1A) {
		ExportedVAhCH1A = exportedVAhCH1A;
	}
	public double getExportedVAhCH2B() {
		return ExportedVAhCH2B;
	}
	public void setExportedVAhCH2B(double exportedVAhCH2B) {
		ExportedVAhCH2B = exportedVAhCH2B;
	}
	public double getExportedVAhCH3C() {
		return ExportedVAhCH3C;
	}
	public void setExportedVAhCH3C(double exportedVAhCH3C) {
		ExportedVAhCH3C = exportedVAhCH3C;
	}
	public double getImportedVAhSum() {
		return ImportedVAhSum;
	}
	public void setImportedVAhSum(double importedVAhSum) {
		ImportedVAhSum = importedVAhSum;
	}
	public double getImportedVAhCH1A() {
		return ImportedVAhCH1A;
	}
	public void setImportedVAhCH1A(double importedVAhCH1A) {
		ImportedVAhCH1A = importedVAhCH1A;
	}
	public double getImportedVAhCH2B() {
		return ImportedVAhCH2B;
	}
	public void setImportedVAhCH2B(double importedVAhCH2B) {
		ImportedVAhCH2B = importedVAhCH2B;
	}
	public double getImportedVAhCH3C() {
		return ImportedVAhCH3C;
	}
	public void setImportedVAhCH3C(double importedVAhCH3C) {
		ImportedVAhCH3C = importedVAhCH3C;
	}
	public double getImportedVARhQ1Sum() {
		return ImportedVARhQ1Sum;
	}
	public void setImportedVARhQ1Sum(double importedVARhQ1Sum) {
		ImportedVARhQ1Sum = importedVARhQ1Sum;
	}
	public double getImportedVARhQ1A() {
		return ImportedVARhQ1A;
	}
	public void setImportedVARhQ1A(double importedVARhQ1A) {
		ImportedVARhQ1A = importedVARhQ1A;
	}
	public double getImportedVARhQ1B() {
		return ImportedVARhQ1B;
	}
	public void setImportedVARhQ1B(double importedVARhQ1B) {
		ImportedVARhQ1B = importedVARhQ1B;
	}
	public double getImportedVARhQ1C() {
		return ImportedVARhQ1C;
	}
	public void setImportedVARhQ1C(double importedVARhQ1C) {
		ImportedVARhQ1C = importedVARhQ1C;
	}
	public double getImportedVARhQ2Sum() {
		return ImportedVARhQ2Sum;
	}
	public void setImportedVARhQ2Sum(double importedVARhQ2Sum) {
		ImportedVARhQ2Sum = importedVARhQ2Sum;
	}
	public double getImportedVARhQ2A() {
		return ImportedVARhQ2A;
	}
	public void setImportedVARhQ2A(double importedVARhQ2A) {
		ImportedVARhQ2A = importedVARhQ2A;
	}
	public double getImportedVARhQ2B() {
		return ImportedVARhQ2B;
	}
	public void setImportedVARhQ2B(double importedVARhQ2B) {
		ImportedVARhQ2B = importedVARhQ2B;
	}
	public double getImportedVARhQ2C() {
		return ImportedVARhQ2C;
	}
	public void setImportedVARhQ2C(double importedVARhQ2C) {
		ImportedVARhQ2C = importedVARhQ2C;
	}
	public double getExportedVARhQ3Sum() {
		return ExportedVARhQ3Sum;
	}
	public void setExportedVARhQ3Sum(double exportedVARhQ3Sum) {
		ExportedVARhQ3Sum = exportedVARhQ3Sum;
	}
	public double getExportedVARhQ3A() {
		return ExportedVARhQ3A;
	}
	public void setExportedVARhQ3A(double exportedVARhQ3A) {
		ExportedVARhQ3A = exportedVARhQ3A;
	}
	public double getExportedVARhQ3B() {
		return ExportedVARhQ3B;
	}
	public void setExportedVARhQ3B(double exportedVARhQ3B) {
		ExportedVARhQ3B = exportedVARhQ3B;
	}
	public double getExportedVARhQ3C() {
		return ExportedVARhQ3C;
	}
	public void setExportedVARhQ3C(double exportedVARhQ3C) {
		ExportedVARhQ3C = exportedVARhQ3C;
	}
	public double getExportedVARhQ4Sum() {
		return ExportedVARhQ4Sum;
	}
	public void setExportedVARhQ4Sum(double exportedVARhQ4Sum) {
		ExportedVARhQ4Sum = exportedVARhQ4Sum;
	}
	public double getExportedVARhQ4A() {
		return ExportedVARhQ4A;
	}
	public void setExportedVARhQ4A(double exportedVARhQ4A) {
		ExportedVARhQ4A = exportedVARhQ4A;
	}
	public double getExportedVARhQ4B() {
		return ExportedVARhQ4B;
	}
	public void setExportedVARhQ4B(double exportedVARhQ4B) {
		ExportedVARhQ4B = exportedVARhQ4B;
	}
	public double getExportedVARhQ4C() {
		return ExportedVARhQ4C;
	}
	public void setExportedVARhQ4C(double exportedVARhQ4C) {
		ExportedVARhQ4C = exportedVARhQ4C;
	}
	public double getWattDemandElement() {
		return WattDemandElement;
	}
	public void setWattDemandElement(double wattDemandElement) {
		WattDemandElement = wattDemandElement;
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
