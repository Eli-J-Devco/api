/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelKlea220pEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double Phase1VoltageLN;
	private double Phase12VoltageLL;
	private double Phase1Current;
	private double Phase1CosPhi;
	private double Phase1PowerFactor;
	private double Phase1ActivePower;
	private double Phase1ReactivePower;
	private double Phase1ApparentPower;
	private double Phase1THDV;
	private double Phase1THDI;
	private double Phase2VoltageLN;
	private double Phase23VoltageLL;
	private double Phase2Current;
	private double Phase2Cos;
	private double Phase2PowerFactor;
	private double Phase2ActivePower;
	private double Phase2ReactivePower;
	private double Phase2ApparentPower;
	private double Phase2THDV;
	private double Phase2THDI;
	private double Phase3VoltageLN; 
	private double Phase31VoltageLL;
	private double Phase3Current;
	private double Phase3Cos;
	private double Phase3PowerFactor;
	private double Phase3ActivePower;
	private double Phase3ReactivePower;
	private double Phase3ApparentPower;
	private double Phase3THDV;
	private double Phase3THDI;
	private double AverageVoltageLN;
	private double AverageVoltageLL;
	private double TotalCurrent;
	private double SystemPowerFactor;
	private double TotalActivePower;
	private double TotalReactivePower;
	private double TotalApparentPower;
	private double SystemFrequency;
	private double NeutralCurrent;
	private double Energytotal;
	
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	private int enable_alert;
	
	
	public double getPhase2THDI() {
		return Phase2THDI;
	}
	public void setPhase2THDI(double phase2thdi) {
		Phase2THDI = phase2thdi;
	}
	public double getPhase3VoltageLN() {
		return Phase3VoltageLN;
	}
	public void setPhase3VoltageLN(double phase3VoltageLN) {
		Phase3VoltageLN = phase3VoltageLN;
	}
	public double getPhase31VoltageLL() {
		return Phase31VoltageLL;
	}
	public void setPhase31VoltageLL(double phase31VoltageLL) {
		Phase31VoltageLL = phase31VoltageLL;
	}
	public double getPhase3Current() {
		return Phase3Current;
	}
	public void setPhase3Current(double phase3Current) {
		Phase3Current = phase3Current;
	}
	public double getPhase3Cos() {
		return Phase3Cos;
	}
	public void setPhase3Cos(double phase3Cos) {
		Phase3Cos = phase3Cos;
	}
	public double getPhase3PowerFactor() {
		return Phase3PowerFactor;
	}
	public void setPhase3PowerFactor(double phase3PowerFactor) {
		Phase3PowerFactor = phase3PowerFactor;
	}
	public double getPhase3ActivePower() {
		return Phase3ActivePower;
	}
	public void setPhase3ActivePower(double phase3ActivePower) {
		Phase3ActivePower = phase3ActivePower;
	}
	public double getPhase3ReactivePower() {
		return Phase3ReactivePower;
	}
	public void setPhase3ReactivePower(double phase3ReactivePower) {
		Phase3ReactivePower = phase3ReactivePower;
	}
	public double getPhase3ApparentPower() {
		return Phase3ApparentPower;
	}
	public void setPhase3ApparentPower(double phase3ApparentPower) {
		Phase3ApparentPower = phase3ApparentPower;
	}
	public double getPhase3THDV() {
		return Phase3THDV;
	}
	public void setPhase3THDV(double phase3thdv) {
		Phase3THDV = phase3thdv;
	}
	public double getPhase3THDI() {
		return Phase3THDI;
	}
	public void setPhase3THDI(double phase3thdi) {
		Phase3THDI = phase3thdi;
	}
	public double getAverageVoltageLN() {
		return AverageVoltageLN;
	}
	public void setAverageVoltageLN(double averageVoltageLN) {
		AverageVoltageLN = averageVoltageLN;
	}
	public double getAverageVoltageLL() {
		return AverageVoltageLL;
	}
	public void setAverageVoltageLL(double averageVoltageLL) {
		AverageVoltageLL = averageVoltageLL;
	}
	public double getTotalCurrent() {
		return TotalCurrent;
	}
	public void setTotalCurrent(double totalCurrent) {
		TotalCurrent = totalCurrent;
	}
	public double getSystemPowerFactor() {
		return SystemPowerFactor;
	}
	public void setSystemPowerFactor(double systemPowerFactor) {
		SystemPowerFactor = systemPowerFactor;
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
	public double getTotalApparentPower() {
		return TotalApparentPower;
	}
	public void setTotalApparentPower(double totalApparentPower) {
		TotalApparentPower = totalApparentPower;
	}
	public double getSystemFrequency() {
		return SystemFrequency;
	}
	public void setSystemFrequency(double systemFrequency) {
		SystemFrequency = systemFrequency;
	}
	public double getNeutralCurrent() {
		return NeutralCurrent;
	}
	public void setNeutralCurrent(double neutralCurrent) {
		NeutralCurrent = neutralCurrent;
	}
	public double getEnergytotal() {
		return Energytotal;
	}
	public void setEnergytotal(double energytotal) {
		Energytotal = energytotal;
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
	public double getPhase1VoltageLN() {
		return Phase1VoltageLN;
	}
	public void setPhase1VoltageLN(double phase1VoltageLN) {
		Phase1VoltageLN = phase1VoltageLN;
	}
	public double getPhase12VoltageLL() {
		return Phase12VoltageLL;
	}
	public void setPhase12VoltageLL(double phase12VoltageLL) {
		Phase12VoltageLL = phase12VoltageLL;
	}
	public double getPhase1Current() {
		return Phase1Current;
	}
	public void setPhase1Current(double phase1Current) {
		Phase1Current = phase1Current;
	}
	public double getPhase1CosPhi() {
		return Phase1CosPhi;
	}
	public void setPhase1CosPhi(double phase1CosPhi) {
		Phase1CosPhi = phase1CosPhi;
	}
	public double getPhase1PowerFactor() {
		return Phase1PowerFactor;
	}
	public void setPhase1PowerFactor(double phase1PowerFactor) {
		Phase1PowerFactor = phase1PowerFactor;
	}
	public double getPhase1ActivePower() {
		return Phase1ActivePower;
	}
	public void setPhase1ActivePower(double phase1ActivePower) {
		Phase1ActivePower = phase1ActivePower;
	}
	public double getPhase1ReactivePower() {
		return Phase1ReactivePower;
	}
	public void setPhase1ReactivePower(double phase1ReactivePower) {
		Phase1ReactivePower = phase1ReactivePower;
	}
	public double getPhase1ApparentPower() {
		return Phase1ApparentPower;
	}
	public void setPhase1ApparentPower(double phase1ApparentPower) {
		Phase1ApparentPower = phase1ApparentPower;
	}
	public double getPhase1THDV() {
		return Phase1THDV;
	}
	public void setPhase1THDV(double phase1thdv) {
		Phase1THDV = phase1thdv;
	}
	public double getPhase1THDI() {
		return Phase1THDI;
	}
	public void setPhase1THDI(double phase1thdi) {
		Phase1THDI = phase1thdi;
	}
	public double getPhase2VoltageLN() {
		return Phase2VoltageLN;
	}
	public void setPhase2VoltageLN(double phase2VoltageLN) {
		Phase2VoltageLN = phase2VoltageLN;
	}
	public double getPhase23VoltageLL() {
		return Phase23VoltageLL;
	}
	public void setPhase23VoltageLL(double phase23VoltageLL) {
		Phase23VoltageLL = phase23VoltageLL;
	}
	public double getPhase2Current() {
		return Phase2Current;
	}
	public void setPhase2Current(double phase2Current) {
		Phase2Current = phase2Current;
	}
	public double getPhase2Cos() {
		return Phase2Cos;
	}
	public void setPhase2Cos(double phase2Cos) {
		Phase2Cos = phase2Cos;
	}
	public double getPhase2PowerFactor() {
		return Phase2PowerFactor;
	}
	public void setPhase2PowerFactor(double phase2PowerFactor) {
		Phase2PowerFactor = phase2PowerFactor;
	}
	public double getPhase2ActivePower() {
		return Phase2ActivePower;
	}
	public void setPhase2ActivePower(double phase2ActivePower) {
		Phase2ActivePower = phase2ActivePower;
	}
	public double getPhase2ReactivePower() {
		return Phase2ReactivePower;
	}
	public void setPhase2ReactivePower(double phase2ReactivePower) {
		Phase2ReactivePower = phase2ReactivePower;
	}
	public double getPhase2ApparentPower() {
		return Phase2ApparentPower;
	}
	public void setPhase2ApparentPower(double phase2ApparentPower) {
		Phase2ApparentPower = phase2ApparentPower;
	}
	public double getPhase2THDV() {
		return Phase2THDV;
	}
	public void setPhase2THDV(double phase2thdv) {
		Phase2THDV = phase2thdv;
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
