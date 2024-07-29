/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelMeterIon8600V3Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double VlnA;
	private double VlnB;
	private double VlnC;
	private double VlnAve;
	private double VllAb;
	private double VllAc;
	private double VllCa;
	private double VllAve;
	private double IA;
	private double IB;
	private double IC;
	private double IAve;
	private double VUnbal;
	private double IUnbal;
	private double Freq;
	private double I4;
	private double kWA;
	private double kWB;
	private double kWC;
	private double kWTot;
	private double kVARA;
	private double kVARB;
	private double kVARC;
	private double kVARTot;
	private double KVAA;
	private double KVAB;
	private double KVAC;
	private double KVATot;
	private double PFSignA;
	private double PFSignB;
	private double PFSignC;
	private double PFSignTot;
	private double VIIAveMx;
	private double IAveMx;
	private double kWTotMx;
	private double kVARTotMx;
	private double kVATotMx;
	private double FreqMx;
	private double VIIAveMn;
	private double IAveMn;
	private double FreqMn;
	private double kWSdDelRec;
	private double kVASdDelRec;
	private double kVARSdDelRec;
	private double kWSdMxDR;
	private double kVASdMxDR;
	private double kVARSdMxDR;
	private double PhaseRev;
	private double kWhDel;
	private double kWhRec;
	private double kWhDelRec;
	private double kWhDel_Rec;
	private double kVARhDel;
	private double kVARhRec;
	private double kVARhDelRec;
	private double kVARhDel_Rec;
	private double kVAhDelRec;
	private double V1THDMx;
	private double V2THDMx;
	private double V3THDMx;
	private double I1THDMx;	
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	private int enable_alert;
	private int data_send_time;
	
	
	
	public int getData_send_time() {
		return data_send_time;
	}
	public void setData_send_time(int data_send_time) {
		this.data_send_time = data_send_time;
	}
	
	public int getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(int enable_alert) {
		this.enable_alert = enable_alert;
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
	
	
	public double getVllAve() {
		return VllAve;
	}
	public void setVllAve(double vllAve) {
		VllAve = vllAve;
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
	public double getVlnA() {
		return VlnA;
	}
	public void setVlnA(double vlnA) {
		VlnA = vlnA;
	}
	public double getVlnB() {
		return VlnB;
	}
	public void setVlnB(double vlnB) {
		VlnB = vlnB;
	}
	public double getVlnC() {
		return VlnC;
	}
	public void setVlnC(double vlnC) {
		VlnC = vlnC;
	}
	public double getVlnAve() {
		return VlnAve;
	}
	public void setVlnAve(double vlnAve) {
		VlnAve = vlnAve;
	}
	public double getVllAb() {
		return VllAb;
	}
	public void setVllAb(double vllAb) {
		VllAb = vllAb;
	}
	public double getVllAc() {
		return VllAc;
	}
	public void setVllAc(double vllAc) {
		VllAc = vllAc;
	}
	public double getVllCa() {
		return VllCa;
	}
	public void setVllCa(double vllCa) {
		VllCa = vllCa;
	}
	public double getIA() {
		return IA;
	}
	public void setIA(double iA) {
		IA = iA;
	}
	public double getIB() {
		return IB;
	}
	public void setIB(double iB) {
		IB = iB;
	}
	public double getIC() {
		return IC;
	}
	public void setIC(double iC) {
		IC = iC;
	}
	public double getIAve() {
		return IAve;
	}
	public void setIAve(double iAve) {
		IAve = iAve;
	}
	public double getVUnbal() {
		return VUnbal;
	}
	public void setVUnbal(double vUnbal) {
		VUnbal = vUnbal;
	}
	public double getIUnbal() {
		return IUnbal;
	}
	public void setIUnbal(double iUnbal) {
		IUnbal = iUnbal;
	}
	public double getFreq() {
		return Freq;
	}
	public void setFreq(double freq) {
		Freq = freq;
	}
	public double getI4() {
		return I4;
	}
	public void setI4(double i4) {
		I4 = i4;
	}
	public double getKWA() {
		return kWA;
	}
	public void setKWA(double kWA) {
		this.kWA = kWA;
	}
	public double getKWB() {
		return kWB;
	}
	public void setKWB(double kWB) {
		this.kWB = kWB;
	}
	public double getKWC() {
		return kWC;
	}
	public void setKWC(double kWC) {
		this.kWC = kWC;
	}
	public double getKWTot() {
		return kWTot;
	}
	public void setKWTot(double kWTot) {
		this.kWTot = kWTot;
	}
	public double getKVARA() {
		return kVARA;
	}
	public void setKVARA(double kVARA) {
		this.kVARA = kVARA;
	}
	public double getKVARB() {
		return kVARB;
	}
	public void setKVARB(double kVARB) {
		this.kVARB = kVARB;
	}
	public double getKVARC() {
		return kVARC;
	}
	public void setKVARC(double kVARC) {
		this.kVARC = kVARC;
	}
	public double getKVARTot() {
		return kVARTot;
	}
	public void setKVARTot(double kVARTot) {
		this.kVARTot = kVARTot;
	}
	public double getKVAA() {
		return KVAA;
	}
	public void setKVAA(double kVAA) {
		KVAA = kVAA;
	}
	public double getKVAB() {
		return KVAB;
	}
	public void setKVAB(double kVAB) {
		KVAB = kVAB;
	}
	public double getKVAC() {
		return KVAC;
	}
	public void setKVAC(double kVAC) {
		KVAC = kVAC;
	}
	public double getKVATot() {
		return KVATot;
	}
	public void setKVATot(double kVATot) {
		KVATot = kVATot;
	}
	public double getPFSignA() {
		return PFSignA;
	}
	public void setPFSignA(double pFSignA) {
		PFSignA = pFSignA;
	}
	public double getPFSignB() {
		return PFSignB;
	}
	public void setPFSignB(double pFSignB) {
		PFSignB = pFSignB;
	}
	public double getPFSignC() {
		return PFSignC;
	}
	public void setPFSignC(double pFSignC) {
		PFSignC = pFSignC;
	}
	public double getPFSignTot() {
		return PFSignTot;
	}
	public void setPFSignTot(double pFSignTot) {
		PFSignTot = pFSignTot;
	}
	public double getVIIAveMx() {
		return VIIAveMx;
	}
	public void setVIIAveMx(double vIIAveMx) {
		VIIAveMx = vIIAveMx;
	}
	public double getIAveMx() {
		return IAveMx;
	}
	public void setIAveMx(double iAveMx) {
		IAveMx = iAveMx;
	}
	public double getKWTotMx() {
		return kWTotMx;
	}
	public void setKWTotMx(double kWTotMx) {
		this.kWTotMx = kWTotMx;
	}
	public double getKVARTotMx() {
		return kVARTotMx;
	}
	public void setKVARTotMx(double kVARTotMx) {
		this.kVARTotMx = kVARTotMx;
	}
	public double getKVATotMx() {
		return kVATotMx;
	}
	public void setKVATotMx(double kVATotMx) {
		this.kVATotMx = kVATotMx;
	}
	public double getFreqMx() {
		return FreqMx;
	}
	public void setFreqMx(double freqMx) {
		FreqMx = freqMx;
	}
	public double getVIIAveMn() {
		return VIIAveMn;
	}
	public void setVIIAveMn(double vIIAveMn) {
		VIIAveMn = vIIAveMn;
	}
	public double getIAveMn() {
		return IAveMn;
	}
	public void setIAveMn(double iAveMn) {
		IAveMn = iAveMn;
	}
	public double getFreqMn() {
		return FreqMn;
	}
	public void setFreqMn(double freqMn) {
		FreqMn = freqMn;
	}
	public double getKWSdDelRec() {
		return kWSdDelRec;
	}
	public void setKWSdDelRec(double kWSdDelRec) {
		this.kWSdDelRec = kWSdDelRec;
	}
	public double getKVASdDelRec() {
		return kVASdDelRec;
	}
	public void setKVASdDelRec(double kVASdDelRec) {
		this.kVASdDelRec = kVASdDelRec;
	}
	public double getKVARSdDelRec() {
		return kVARSdDelRec;
	}
	public void setKVARSdDelRec(double kVARSdDelRec) {
		this.kVARSdDelRec = kVARSdDelRec;
	}
	public double getKWSdMxDR() {
		return kWSdMxDR;
	}
	public void setKWSdMxDR(double kWSdMxDR) {
		this.kWSdMxDR = kWSdMxDR;
	}
	public double getKVASdMxDR() {
		return kVASdMxDR;
	}
	public void setKVASdMxDR(double kVASdMxDR) {
		this.kVASdMxDR = kVASdMxDR;
	}
	public double getKVARSdMxDR() {
		return kVARSdMxDR;
	}
	public void setKVARSdMxDR(double kVARSdMxDR) {
		this.kVARSdMxDR = kVARSdMxDR;
	}
	public double getPhaseRev() {
		return PhaseRev;
	}
	public void setPhaseRev(double phaseRev) {
		PhaseRev = phaseRev;
	}
	public double getKWhDel() {
		return kWhDel;
	}
	public void setKWhDel(double kWhDel) {
		this.kWhDel = kWhDel;
	}
	public double getKWhRec() {
		return kWhRec;
	}
	public void setKWhRec(double kWhRec) {
		this.kWhRec = kWhRec;
	}
	public double getKWhDelRec() {
		return kWhDelRec;
	}
	public void setKWhDelRec(double kWhDelRec) {
		this.kWhDelRec = kWhDelRec;
	}
	public double getKWhDel_Rec() {
		return kWhDel_Rec;
	}
	public void setKWhDel_Rec(double kWhDel_Rec) {
		this.kWhDel_Rec = kWhDel_Rec;
	}
	public double getKVARhDel() {
		return kVARhDel;
	}
	public void setKVARhDel(double kVARhDel) {
		this.kVARhDel = kVARhDel;
	}
	public double getKVARhRec() {
		return kVARhRec;
	}
	public void setKVARhRec(double kVARhRec) {
		this.kVARhRec = kVARhRec;
	}
	public double getKVARhDelRec() {
		return kVARhDelRec;
	}
	public void setKVARhDelRec(double kVARhDelRec) {
		this.kVARhDelRec = kVARhDelRec;
	}
	public double getKVARhDel_Rec() {
		return kVARhDel_Rec;
	}
	public void setKVARhDel_Rec(double kVARhDel_Rec) {
		this.kVARhDel_Rec = kVARhDel_Rec;
	}
	public double getKVAhDelRec() {
		return kVAhDelRec;
	}
	public void setKVAhDelRec(double kVAhDelRec) {
		this.kVAhDelRec = kVAhDelRec;
	}
	public double getV1THDMx() {
		return V1THDMx;
	}
	public void setV1THDMx(double v1thdMx) {
		V1THDMx = v1thdMx;
	}
	public double getV2THDMx() {
		return V2THDMx;
	}
	public void setV2THDMx(double v2thdMx) {
		V2THDMx = v2thdMx;
	}
	public double getV3THDMx() {
		return V3THDMx;
	}
	public void setV3THDMx(double v3thdMx) {
		V3THDMx = v3thdMx;
	}
	public double getI1THDMx() {
		return I1THDMx;
	}
	public void setI1THDMx(double i1thdMx) {
		I1THDMx = i1thdMx;
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
