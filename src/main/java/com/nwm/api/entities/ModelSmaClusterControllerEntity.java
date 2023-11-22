/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSmaClusterControllerEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double Metering_TotWhOut;
	private double Operation_GriSwCnt;
	private double Metering_TotOpTms;
	private double Metering_TotFeedTms;
	private double GridMs_TotW;
	private double GridMs_Hz;
	private double Isolation_FltA;
	private double Isolation_LeakRis;
	private double DcMs_VolA;
	private double DcMs_VolB;
	private double DcMs_AmpA;
	private double DcMs_AmpB;
	private double GridMs_PhV_phsA;
	private double GridMs_PhV_phsB;
	private double GridMs_PhV_phsC;
	private double GridMs_A_phsA;
	private double GridMs_A_phsB;
	private double GridMs_A_phsC;
	private double DcMs_WattA;
	private double DcMs_WattB;
	private String Operation_Health;
	private String Operation_Evt_Prio;
	private String Operation_Evt_Msg;
	private String Operation_Evt_Dsc;
	private double InOut_AnInA1;
	private double InOut_AnInA2;
	private double InOut_AnInA3;
	private double InOut_AnInVol4;
	private double Env_ExInsol;
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
	public double getMetering_TotWhOut() {
		return Metering_TotWhOut;
	}
	public void setMetering_TotWhOut(double metering_TotWhOut) {
		Metering_TotWhOut = metering_TotWhOut;
	}
	public double getOperation_GriSwCnt() {
		return Operation_GriSwCnt;
	}
	public void setOperation_GriSwCnt(double operation_GriSwCnt) {
		Operation_GriSwCnt = operation_GriSwCnt;
	}
	public double getMetering_TotOpTms() {
		return Metering_TotOpTms;
	}
	public void setMetering_TotOpTms(double metering_TotOpTms) {
		Metering_TotOpTms = metering_TotOpTms;
	}
	public double getMetering_TotFeedTms() {
		return Metering_TotFeedTms;
	}
	public void setMetering_TotFeedTms(double metering_TotFeedTms) {
		Metering_TotFeedTms = metering_TotFeedTms;
	}
	public double getGridMs_TotW() {
		return GridMs_TotW;
	}
	public void setGridMs_TotW(double gridMs_TotW) {
		GridMs_TotW = gridMs_TotW;
	}
	public double getGridMs_Hz() {
		return GridMs_Hz;
	}
	public void setGridMs_Hz(double gridMs_Hz) {
		GridMs_Hz = gridMs_Hz;
	}
	public double getIsolation_FltA() {
		return Isolation_FltA;
	}
	public void setIsolation_FltA(double isolation_FltA) {
		Isolation_FltA = isolation_FltA;
	}
	public double getIsolation_LeakRis() {
		return Isolation_LeakRis;
	}
	public void setIsolation_LeakRis(double isolation_LeakRis) {
		Isolation_LeakRis = isolation_LeakRis;
	}
	public double getDcMs_VolA() {
		return DcMs_VolA;
	}
	public void setDcMs_VolA(double dcMs_VolA) {
		DcMs_VolA = dcMs_VolA;
	}
	public double getDcMs_VolB() {
		return DcMs_VolB;
	}
	public void setDcMs_VolB(double dcMs_VolB) {
		DcMs_VolB = dcMs_VolB;
	}
	public double getDcMs_AmpA() {
		return DcMs_AmpA;
	}
	public void setDcMs_AmpA(double dcMs_AmpA) {
		DcMs_AmpA = dcMs_AmpA;
	}
	public double getDcMs_AmpB() {
		return DcMs_AmpB;
	}
	public void setDcMs_AmpB(double dcMs_AmpB) {
		DcMs_AmpB = dcMs_AmpB;
	}
	public double getGridMs_PhV_phsA() {
		return GridMs_PhV_phsA;
	}
	public void setGridMs_PhV_phsA(double gridMs_PhV_phsA) {
		GridMs_PhV_phsA = gridMs_PhV_phsA;
	}
	public double getGridMs_PhV_phsB() {
		return GridMs_PhV_phsB;
	}
	public void setGridMs_PhV_phsB(double gridMs_PhV_phsB) {
		GridMs_PhV_phsB = gridMs_PhV_phsB;
	}
	public double getGridMs_PhV_phsC() {
		return GridMs_PhV_phsC;
	}
	public void setGridMs_PhV_phsC(double gridMs_PhV_phsC) {
		GridMs_PhV_phsC = gridMs_PhV_phsC;
	}
	public double getGridMs_A_phsA() {
		return GridMs_A_phsA;
	}
	public void setGridMs_A_phsA(double gridMs_A_phsA) {
		GridMs_A_phsA = gridMs_A_phsA;
	}
	public double getGridMs_A_phsB() {
		return GridMs_A_phsB;
	}
	public void setGridMs_A_phsB(double gridMs_A_phsB) {
		GridMs_A_phsB = gridMs_A_phsB;
	}
	public double getGridMs_A_phsC() {
		return GridMs_A_phsC;
	}
	public void setGridMs_A_phsC(double gridMs_A_phsC) {
		GridMs_A_phsC = gridMs_A_phsC;
	}
	public double getDcMs_WattA() {
		return DcMs_WattA;
	}
	public void setDcMs_WattA(double dcMs_WattA) {
		DcMs_WattA = dcMs_WattA;
	}
	public double getDcMs_WattB() {
		return DcMs_WattB;
	}
	public void setDcMs_WattB(double dcMs_WattB) {
		DcMs_WattB = dcMs_WattB;
	}
	public String getOperation_Health() {
		return Operation_Health;
	}
	public void setOperation_Health(String operation_Health) {
		Operation_Health = operation_Health;
	}
	public String getOperation_Evt_Prio() {
		return Operation_Evt_Prio;
	}
	public void setOperation_Evt_Prio(String operation_Evt_Prio) {
		Operation_Evt_Prio = operation_Evt_Prio;
	}
	public String getOperation_Evt_Msg() {
		return Operation_Evt_Msg;
	}
	public void setOperation_Evt_Msg(String operation_Evt_Msg) {
		Operation_Evt_Msg = operation_Evt_Msg;
	}
	public String getOperation_Evt_Dsc() {
		return Operation_Evt_Dsc;
	}
	public void setOperation_Evt_Dsc(String operation_Evt_Dsc) {
		Operation_Evt_Dsc = operation_Evt_Dsc;
	}
	public double getInOut_AnInA1() {
		return InOut_AnInA1;
	}
	public void setInOut_AnInA1(double inOut_AnInA1) {
		InOut_AnInA1 = inOut_AnInA1;
	}
	public double getInOut_AnInA2() {
		return InOut_AnInA2;
	}
	public void setInOut_AnInA2(double inOut_AnInA2) {
		InOut_AnInA2 = inOut_AnInA2;
	}
	public double getInOut_AnInA3() {
		return InOut_AnInA3;
	}
	public void setInOut_AnInA3(double inOut_AnInA3) {
		InOut_AnInA3 = inOut_AnInA3;
	}
	public double getInOut_AnInVol4() {
		return InOut_AnInVol4;
	}
	public void setInOut_AnInVol4(double inOut_AnInVol4) {
		InOut_AnInVol4 = inOut_AnInVol4;
	}
	public double getEnv_ExInsol() {
		return Env_ExInsol;
	}
	public void setEnv_ExInsol(double env_ExInsol) {
		Env_ExInsol = env_ExInsol;
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
	
	
}
