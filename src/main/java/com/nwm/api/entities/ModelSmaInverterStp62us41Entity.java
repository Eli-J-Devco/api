/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSmaInverterStp62us41Entity {
	private String time = null;
	private int id_device = 0;
	private int error = 0;
	private int low_alarm = 0;
	private int high_alarm = 0 ;
	
	private double VA_phsA = 0.001;
	private double VA_phsB = 0.001;
	private double DcMs_Vol0 = 0.001;
	private double DcMs_Vol1 = 0.001;
	private double DcMs_Vol2 = 0.001;
	private double DcMs_Vol3 = 0.001;
	private double DcMs_Vol4 = 0.001;
	private double DcMs_Vol5 = 0.001;
	private double TotW_Pv = 0.001;
	private double Isolation_LeakRis = 0.001;
	private double PhV_phsC = 0.001;
	private double GridMs_Hz = 0.001;
	private double W_phsB = 0.001;
	private double GridMs_TotW = 0.001;
	private double W_phsC = 0.001;
	private double VAr_phsC = 0.001;
	private double DcMs_Watt0 = 0.001;
	private double DcMs_Watt1 = 0.001;
	private double DcMs_Watt2 = 0.001;
	private double DcMs_Watt3 = 0.001;
	private double DcMs_Watt4 = 0.001;
	private double DcMs_Watt5 = 0.001;
	private double W_phsA = 0.001;
	private double VAr_phsB = 0.001;
	private double TotVAr_Pv = 0.001;
	private double PhV_phsA2B = 0.001;
	private double VAr_phsA = 0.001;
	private double GridMs_TotVA = 0.001;
	private double GridMs_TotVAr = 0.001;
	private double DcMs_Amp0 = 0.001;
	private double DcMs_Amp1 = 0.001;
	private double DcMs_Amp2 = 0.001;
	private double DcMs_Amp3 = 0.001;
	private double DcMs_Amp4 = 0.001;
	private double DcMs_Amp5 = 0.001;
	private double PhV_phsB2C = 0.001;
	private double PhV_phsB = 0.001;
	private double A_phsA = 0.001;
	private double PhV_phsC2A = 0.001;
	private double A_phsB = 0.001;
	private double PhV_phsA = 0.001;
	private double VA_phsC = 0.001;
	private double A_phsC = 0.001;
	private double Metering_TotWhOut = 0.001;
	private String Operation_Health = null;
	private double Operation_GriSwCnt = 0.001;
	private double TotWhOut_Pv = 0.001;
	private double Metering_TotFeedTms = 0.001;
	private double Metering_TotOpTms = 0.001;
	private double nvmActivePower = 0.001;
	private double nvmActiveEnergy = 0.001;
	
	private double MeasuredProduction = 0.001;
	
	
	
	
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
	public double getVA_phsA() {
		return VA_phsA;
	}
	public void setVA_phsA(double vA_phsA) {
		VA_phsA = vA_phsA;
	}
	public double getVA_phsB() {
		return VA_phsB;
	}
	public void setVA_phsB(double vA_phsB) {
		VA_phsB = vA_phsB;
	}
	public double getDcMs_Vol0() {
		return DcMs_Vol0;
	}
	public void setDcMs_Vol0(double dcMs_Vol0) {
		DcMs_Vol0 = dcMs_Vol0;
	}
	public double getDcMs_Vol1() {
		return DcMs_Vol1;
	}
	public void setDcMs_Vol1(double dcMs_Vol1) {
		DcMs_Vol1 = dcMs_Vol1;
	}
	public double getDcMs_Vol2() {
		return DcMs_Vol2;
	}
	public void setDcMs_Vol2(double dcMs_Vol2) {
		DcMs_Vol2 = dcMs_Vol2;
	}
	public double getDcMs_Vol3() {
		return DcMs_Vol3;
	}
	public void setDcMs_Vol3(double dcMs_Vol3) {
		DcMs_Vol3 = dcMs_Vol3;
	}
	public double getDcMs_Vol4() {
		return DcMs_Vol4;
	}
	public void setDcMs_Vol4(double dcMs_Vol4) {
		DcMs_Vol4 = dcMs_Vol4;
	}
	public double getDcMs_Vol5() {
		return DcMs_Vol5;
	}
	public void setDcMs_Vol5(double dcMs_Vol5) {
		DcMs_Vol5 = dcMs_Vol5;
	}
	public double getTotW_Pv() {
		return TotW_Pv;
	}
	public void setTotW_Pv(double totW_Pv) {
		TotW_Pv = totW_Pv;
	}
	public double getIsolation_LeakRis() {
		return Isolation_LeakRis;
	}
	public void setIsolation_LeakRis(double isolation_LeakRis) {
		Isolation_LeakRis = isolation_LeakRis;
	}
	public double getPhV_phsC() {
		return PhV_phsC;
	}
	public void setPhV_phsC(double phV_phsC) {
		PhV_phsC = phV_phsC;
	}
	public double getGridMs_Hz() {
		return GridMs_Hz;
	}
	public void setGridMs_Hz(double gridMs_Hz) {
		GridMs_Hz = gridMs_Hz;
	}
	public double getW_phsB() {
		return W_phsB;
	}
	public void setW_phsB(double w_phsB) {
		W_phsB = w_phsB;
	}
	public double getGridMs_TotW() {
		return GridMs_TotW;
	}
	public void setGridMs_TotW(double gridMs_TotW) {
		GridMs_TotW = gridMs_TotW;
	}
	public double getW_phsC() {
		return W_phsC;
	}
	public void setW_phsC(double w_phsC) {
		W_phsC = w_phsC;
	}
	public double getVAr_phsC() {
		return VAr_phsC;
	}
	public void setVAr_phsC(double vAr_phsC) {
		VAr_phsC = vAr_phsC;
	}
	public double getDcMs_Watt0() {
		return DcMs_Watt0;
	}
	public void setDcMs_Watt0(double dcMs_Watt0) {
		DcMs_Watt0 = dcMs_Watt0;
	}
	public double getDcMs_Watt1() {
		return DcMs_Watt1;
	}
	public void setDcMs_Watt1(double dcMs_Watt1) {
		DcMs_Watt1 = dcMs_Watt1;
	}
	public double getDcMs_Watt2() {
		return DcMs_Watt2;
	}
	public void setDcMs_Watt2(double dcMs_Watt2) {
		DcMs_Watt2 = dcMs_Watt2;
	}
	public double getDcMs_Watt3() {
		return DcMs_Watt3;
	}
	public void setDcMs_Watt3(double dcMs_Watt3) {
		DcMs_Watt3 = dcMs_Watt3;
	}
	public double getDcMs_Watt4() {
		return DcMs_Watt4;
	}
	public void setDcMs_Watt4(double dcMs_Watt4) {
		DcMs_Watt4 = dcMs_Watt4;
	}
	public double getDcMs_Watt5() {
		return DcMs_Watt5;
	}
	public void setDcMs_Watt5(double dcMs_Watt5) {
		DcMs_Watt5 = dcMs_Watt5;
	}
	public double getW_phsA() {
		return W_phsA;
	}
	public void setW_phsA(double w_phsA) {
		W_phsA = w_phsA;
	}
	public double getVAr_phsB() {
		return VAr_phsB;
	}
	public void setVAr_phsB(double vAr_phsB) {
		VAr_phsB = vAr_phsB;
	}
	public double getTotVAr_Pv() {
		return TotVAr_Pv;
	}
	public void setTotVAr_Pv(double totVAr_Pv) {
		TotVAr_Pv = totVAr_Pv;
	}
	public double getPhV_phsA2B() {
		return PhV_phsA2B;
	}
	public void setPhV_phsA2B(double phV_phsA2B) {
		PhV_phsA2B = phV_phsA2B;
	}
	public double getVAr_phsA() {
		return VAr_phsA;
	}
	public void setVAr_phsA(double vAr_phsA) {
		VAr_phsA = vAr_phsA;
	}
	public double getGridMs_TotVA() {
		return GridMs_TotVA;
	}
	public void setGridMs_TotVA(double gridMs_TotVA) {
		GridMs_TotVA = gridMs_TotVA;
	}
	public double getGridMs_TotVAr() {
		return GridMs_TotVAr;
	}
	public void setGridMs_TotVAr(double gridMs_TotVAr) {
		GridMs_TotVAr = gridMs_TotVAr;
	}
	public double getDcMs_Amp0() {
		return DcMs_Amp0;
	}
	public void setDcMs_Amp0(double dcMs_Amp0) {
		DcMs_Amp0 = dcMs_Amp0;
	}
	public double getDcMs_Amp1() {
		return DcMs_Amp1;
	}
	public void setDcMs_Amp1(double dcMs_Amp1) {
		DcMs_Amp1 = dcMs_Amp1;
	}
	public double getDcMs_Amp2() {
		return DcMs_Amp2;
	}
	public void setDcMs_Amp2(double dcMs_Amp2) {
		DcMs_Amp2 = dcMs_Amp2;
	}
	public double getDcMs_Amp3() {
		return DcMs_Amp3;
	}
	public void setDcMs_Amp3(double dcMs_Amp3) {
		DcMs_Amp3 = dcMs_Amp3;
	}
	public double getDcMs_Amp4() {
		return DcMs_Amp4;
	}
	public void setDcMs_Amp4(double dcMs_Amp4) {
		DcMs_Amp4 = dcMs_Amp4;
	}
	public double getDcMs_Amp5() {
		return DcMs_Amp5;
	}
	public void setDcMs_Amp5(double dcMs_Amp5) {
		DcMs_Amp5 = dcMs_Amp5;
	}
	public double getPhV_phsB2C() {
		return PhV_phsB2C;
	}
	public void setPhV_phsB2C(double phV_phsB2C) {
		PhV_phsB2C = phV_phsB2C;
	}
	public double getPhV_phsB() {
		return PhV_phsB;
	}
	public void setPhV_phsB(double phV_phsB) {
		PhV_phsB = phV_phsB;
	}
	public double getA_phsA() {
		return A_phsA;
	}
	public void setA_phsA(double a_phsA) {
		A_phsA = a_phsA;
	}
	public double getPhV_phsC2A() {
		return PhV_phsC2A;
	}
	public void setPhV_phsC2A(double phV_phsC2A) {
		PhV_phsC2A = phV_phsC2A;
	}
	public double getA_phsB() {
		return A_phsB;
	}
	public void setA_phsB(double a_phsB) {
		A_phsB = a_phsB;
	}
	public double getPhV_phsA() {
		return PhV_phsA;
	}
	public void setPhV_phsA(double phV_phsA) {
		PhV_phsA = phV_phsA;
	}
	public double getVA_phsC() {
		return VA_phsC;
	}
	public void setVA_phsC(double vA_phsC) {
		VA_phsC = vA_phsC;
	}
	public double getA_phsC() {
		return A_phsC;
	}
	public void setA_phsC(double a_phsC) {
		A_phsC = a_phsC;
	}
	public double getMetering_TotWhOut() {
		return Metering_TotWhOut;
	}
	public void setMetering_TotWhOut(double metering_TotWhOut) {
		Metering_TotWhOut = metering_TotWhOut;
	}
	public String getOperation_Health() {
		return Operation_Health;
	}
	public void setOperation_Health(String operation_Health) {
		Operation_Health = operation_Health;
	}
	public double getOperation_GriSwCnt() {
		return Operation_GriSwCnt;
	}
	public void setOperation_GriSwCnt(double operation_GriSwCnt) {
		Operation_GriSwCnt = operation_GriSwCnt;
	}
	public double getTotWhOut_Pv() {
		return TotWhOut_Pv;
	}
	public void setTotWhOut_Pv(double totWhOut_Pv) {
		TotWhOut_Pv = totWhOut_Pv;
	}
	public double getMetering_TotFeedTms() {
		return Metering_TotFeedTms;
	}
	public void setMetering_TotFeedTms(double metering_TotFeedTms) {
		Metering_TotFeedTms = metering_TotFeedTms;
	}
	public double getMetering_TotOpTms() {
		return Metering_TotOpTms;
	}
	public void setMetering_TotOpTms(double metering_TotOpTms) {
		Metering_TotOpTms = metering_TotOpTms;
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
