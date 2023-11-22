/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelPowerMeasurementIon7650Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double Ia;
	private double Ib;
	private double Ic;
	private double I4;
	private double I5;
	private double IAvg;
	private double IAvgMn;
	private double IAvgMx;
	private double IAvgMean;
	private double Freq;
	private double FreqMn;
	private double FreqMx;
	private double FreqMean;
	private double VUnbal;
	private double IUnbal;
	private double PhaseRev;
	private double VInA;
	private double VInB;
	private double VInC;
	private double VInAvg;
	private double VInAvgMx;
	private double unused1;
	private double VIIAb;
	private double VIIBc;
	private double VIICa;
	private double VIIAvg;
	private double VIIAvgMx;
	private double VIIAvgMean;
	private double unused2;
	private double unused3;
	private double unused4;
	private double unused5;
	private double kWA;
	private double kWB;
	private double kWC;
	private double kWTot;
	private double kWTotMax;
	private double kVARA;
	private double kVARB;
	private double kVARC;
	private double kVARTot;
	private double kVARTotMax;
	private double kVAA;
	private double kVAB;
	private double kVAC;
	private double kVATot;
	private double kVATotMax;
	private double unused6;
	private double kWhDel;
	private double kWhRec;
	private double kVARhDel;
	private double kVARhRec;
	private double kVARhDelRec;
	private double unused7;
	private double unused8;
	private double unused9;
	private double unused10;
	private double unused11;
	private double unused12;
	private double unused13;
	private double unused14;
	private double unused15;
	private double unused16;
	private double unused17;
	private double PFSignA;
	private double PFSignB;
	private double PFSignC;
	private double PFSignTot;
	private double V1THDMx;
	private double V2THDMx;
	private double V3THDMx;
	private double I1THDMx;
	private double I2THDMx;
	private double I3THDMx;
	private double I1KFactor;
	private double I2KFactor;
	private double I3KFactor;
	private double I1CrestFactor;
	private double I2CrestFactor;
	private double I3CrestFactor;
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
	public double getIa() {
		return Ia;
	}
	public void setIa(double ia) {
		Ia = ia;
	}
	public double getIb() {
		return Ib;
	}
	public void setIb(double ib) {
		Ib = ib;
	}
	public double getIc() {
		return Ic;
	}
	public void setIc(double ic) {
		Ic = ic;
	}
	public double getI4() {
		return I4;
	}
	public void setI4(double i4) {
		I4 = i4;
	}
	public double getI5() {
		return I5;
	}
	public void setI5(double i5) {
		I5 = i5;
	}
	public double getIAvg() {
		return IAvg;
	}
	public void setIAvg(double iAvg) {
		IAvg = iAvg;
	}
	public double getIAvgMn() {
		return IAvgMn;
	}
	public void setIAvgMn(double iAvgMn) {
		IAvgMn = iAvgMn;
	}
	public double getIAvgMx() {
		return IAvgMx;
	}
	public void setIAvgMx(double iAvgMx) {
		IAvgMx = iAvgMx;
	}
	public double getIAvgMean() {
		return IAvgMean;
	}
	public void setIAvgMean(double iAvgMean) {
		IAvgMean = iAvgMean;
	}
	public double getFreq() {
		return Freq;
	}
	public void setFreq(double freq) {
		Freq = freq;
	}
	public double getFreqMn() {
		return FreqMn;
	}
	public void setFreqMn(double freqMn) {
		FreqMn = freqMn;
	}
	public double getFreqMx() {
		return FreqMx;
	}
	public void setFreqMx(double freqMx) {
		FreqMx = freqMx;
	}
	public double getFreqMean() {
		return FreqMean;
	}
	public void setFreqMean(double freqMean) {
		FreqMean = freqMean;
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
	public double getPhaseRev() {
		return PhaseRev;
	}
	public void setPhaseRev(double phaseRev) {
		PhaseRev = phaseRev;
	}
	public double getVInA() {
		return VInA;
	}
	public void setVInA(double vInA) {
		VInA = vInA;
	}
	public double getVInB() {
		return VInB;
	}
	public void setVInB(double vInB) {
		VInB = vInB;
	}
	public double getVInC() {
		return VInC;
	}
	public void setVInC(double vInC) {
		VInC = vInC;
	}
	public double getVInAvg() {
		return VInAvg;
	}
	public void setVInAvg(double vInAvg) {
		VInAvg = vInAvg;
	}
	public double getVInAvgMx() {
		return VInAvgMx;
	}
	public void setVInAvgMx(double vInAvgMx) {
		VInAvgMx = vInAvgMx;
	}
	public double getUnused1() {
		return unused1;
	}
	public void setUnused1(double unused1) {
		this.unused1 = unused1;
	}
	public double getVIIAb() {
		return VIIAb;
	}
	public void setVIIAb(double vIIAb) {
		VIIAb = vIIAb;
	}
	public double getVIIBc() {
		return VIIBc;
	}
	public void setVIIBc(double vIIBc) {
		VIIBc = vIIBc;
	}
	public double getVIICa() {
		return VIICa;
	}
	public void setVIICa(double vIICa) {
		VIICa = vIICa;
	}
	public double getVIIAvg() {
		return VIIAvg;
	}
	public void setVIIAvg(double vIIAvg) {
		VIIAvg = vIIAvg;
	}
	public double getVIIAvgMx() {
		return VIIAvgMx;
	}
	public void setVIIAvgMx(double vIIAvgMx) {
		VIIAvgMx = vIIAvgMx;
	}
	public double getVIIAvgMean() {
		return VIIAvgMean;
	}
	public void setVIIAvgMean(double vIIAvgMean) {
		VIIAvgMean = vIIAvgMean;
	}
	public double getUnused2() {
		return unused2;
	}
	public void setUnused2(double unused2) {
		this.unused2 = unused2;
	}
	public double getUnused3() {
		return unused3;
	}
	public void setUnused3(double unused3) {
		this.unused3 = unused3;
	}
	public double getUnused4() {
		return unused4;
	}
	public void setUnused4(double unused4) {
		this.unused4 = unused4;
	}
	public double getUnused5() {
		return unused5;
	}
	public void setUnused5(double unused5) {
		this.unused5 = unused5;
	}
	public double getkWA() {
		return kWA;
	}
	public void setkWA(double kWA) {
		this.kWA = kWA;
	}
	public double getkWB() {
		return kWB;
	}
	public void setkWB(double kWB) {
		this.kWB = kWB;
	}
	public double getkWC() {
		return kWC;
	}
	public void setkWC(double kWC) {
		this.kWC = kWC;
	}
	public double getkWTot() {
		return kWTot;
	}
	public void setkWTot(double kWTot) {
		this.kWTot = kWTot;
	}
	public double getkWTotMax() {
		return kWTotMax;
	}
	public void setkWTotMax(double kWTotMax) {
		this.kWTotMax = kWTotMax;
	}
	public double getkVARA() {
		return kVARA;
	}
	public void setkVARA(double kVARA) {
		this.kVARA = kVARA;
	}
	public double getkVARB() {
		return kVARB;
	}
	public void setkVARB(double kVARB) {
		this.kVARB = kVARB;
	}
	public double getkVARC() {
		return kVARC;
	}
	public void setkVARC(double kVARC) {
		this.kVARC = kVARC;
	}
	public double getkVARTot() {
		return kVARTot;
	}
	public void setkVARTot(double kVARTot) {
		this.kVARTot = kVARTot;
	}
	public double getkVARTotMax() {
		return kVARTotMax;
	}
	public void setkVARTotMax(double kVARTotMax) {
		this.kVARTotMax = kVARTotMax;
	}
	public double getkVAA() {
		return kVAA;
	}
	public void setkVAA(double kVAA) {
		this.kVAA = kVAA;
	}
	public double getkVAB() {
		return kVAB;
	}
	public void setkVAB(double kVAB) {
		this.kVAB = kVAB;
	}
	public double getkVAC() {
		return kVAC;
	}
	public void setkVAC(double kVAC) {
		this.kVAC = kVAC;
	}
	public double getkVATot() {
		return kVATot;
	}
	public void setkVATot(double kVATot) {
		this.kVATot = kVATot;
	}
	public double getkVATotMax() {
		return kVATotMax;
	}
	public void setkVATotMax(double kVATotMax) {
		this.kVATotMax = kVATotMax;
	}
	public double getUnused6() {
		return unused6;
	}
	public void setUnused6(double unused6) {
		this.unused6 = unused6;
	}
	public double getkWhDel() {
		return kWhDel;
	}
	public void setkWhDel(double kWhDel) {
		this.kWhDel = kWhDel;
	}
	public double getkWhRec() {
		return kWhRec;
	}
	public void setkWhRec(double kWhRec) {
		this.kWhRec = kWhRec;
	}
	public double getkVARhDel() {
		return kVARhDel;
	}
	public void setkVARhDel(double kVARhDel) {
		this.kVARhDel = kVARhDel;
	}
	public double getkVARhRec() {
		return kVARhRec;
	}
	public void setkVARhRec(double kVARhRec) {
		this.kVARhRec = kVARhRec;
	}
	public double getkVARhDelRec() {
		return kVARhDelRec;
	}
	public void setkVARhDelRec(double kVARhDelRec) {
		this.kVARhDelRec = kVARhDelRec;
	}
	public double getUnused7() {
		return unused7;
	}
	public void setUnused7(double unused7) {
		this.unused7 = unused7;
	}
	public double getUnused8() {
		return unused8;
	}
	public void setUnused8(double unused8) {
		this.unused8 = unused8;
	}
	public double getUnused9() {
		return unused9;
	}
	public void setUnused9(double unused9) {
		this.unused9 = unused9;
	}
	public double getUnused10() {
		return unused10;
	}
	public void setUnused10(double unused10) {
		this.unused10 = unused10;
	}
	public double getUnused11() {
		return unused11;
	}
	public void setUnused11(double unused11) {
		this.unused11 = unused11;
	}
	public double getUnused12() {
		return unused12;
	}
	public void setUnused12(double unused12) {
		this.unused12 = unused12;
	}
	public double getUnused13() {
		return unused13;
	}
	public void setUnused13(double unused13) {
		this.unused13 = unused13;
	}
	public double getUnused14() {
		return unused14;
	}
	public void setUnused14(double unused14) {
		this.unused14 = unused14;
	}
	public double getUnused15() {
		return unused15;
	}
	public void setUnused15(double unused15) {
		this.unused15 = unused15;
	}
	public double getUnused16() {
		return unused16;
	}
	public void setUnused16(double unused16) {
		this.unused16 = unused16;
	}
	public double getUnused17() {
		return unused17;
	}
	public void setUnused17(double unused17) {
		this.unused17 = unused17;
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
	public double getI2THDMx() {
		return I2THDMx;
	}
	public void setI2THDMx(double i2thdMx) {
		I2THDMx = i2thdMx;
	}
	public double getI3THDMx() {
		return I3THDMx;
	}
	public void setI3THDMx(double i3thdMx) {
		I3THDMx = i3thdMx;
	}
	public double getI1KFactor() {
		return I1KFactor;
	}
	public void setI1KFactor(double i1kFactor) {
		I1KFactor = i1kFactor;
	}
	public double getI2KFactor() {
		return I2KFactor;
	}
	public void setI2KFactor(double i2kFactor) {
		I2KFactor = i2kFactor;
	}
	public double getI3KFactor() {
		return I3KFactor;
	}
	public void setI3KFactor(double i3kFactor) {
		I3KFactor = i3kFactor;
	}
	public double getI1CrestFactor() {
		return I1CrestFactor;
	}
	public void setI1CrestFactor(double i1CrestFactor) {
		I1CrestFactor = i1CrestFactor;
	}
	public double getI2CrestFactor() {
		return I2CrestFactor;
	}
	public void setI2CrestFactor(double i2CrestFactor) {
		I2CrestFactor = i2CrestFactor;
	}
	public double getI3CrestFactor() {
		return I3CrestFactor;
	}
	public void setI3CrestFactor(double i3CrestFactor) {
		I3CrestFactor = i3CrestFactor;
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
