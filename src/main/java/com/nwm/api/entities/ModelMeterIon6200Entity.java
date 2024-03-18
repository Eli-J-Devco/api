/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelMeterIon6200Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double Vlna;
	private double Vlnb;
	private double Vlnc;
	private double Vlnave;
	private double Vllab;
	private double Vllbc;
	private double Vllca;
	private double Vllave;
	private double Ia;
	private double Ib;
	private double Ic;
	private double Iave;
	private double Idemand;
	private double Ipeakdemand;
	private double I4;
	private double Frequency;
	private double PFsigntotal;
	private double PFsigna;
	private double PFsignb;
	private double PFsignc;
	private double kWtotal;
	private double kVARtotal;
	private double kVAtotal;
	private double kWa;
	private double kWb;
	private double kWc;
	private double kVARa;
	private double kVARb;
	private double kVARc;
	private double kVAa;
	private double kVAb;
	private double kVAc;
	private double kWdemand;
	private double kWpeakdemand;
	private double kVARdemand;
	private double kVAdemand;
	private double kVARpeakdemand;
	private double kVApeakdemand;
	private double kWhdel;
	private double kWhrec;
	private double kVARhdel;
	private double kVARhrec;
	private double kVAhdelrec;
	private double V1THD;
	private double V2THD;
	private double V3THD;
	private double I1THD;
	private double I2THD;
	private double I3THD;
	private double Iademand;
	private double Ibdemand;
	private double Icdemand;
	private double Iapeakdemand;
	private double Ibpeakdemand;
	private double Icpeakdemand;
	private double kWhadel;
	private double kWhbdel;
	private double kWhcdel;
	private double kWharec;
	private double kWhbrec;
	private double kWhcrec;
	
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
	public double getVlna() {
		return Vlna;
	}
	public void setVlna(double vlna) {
		Vlna = vlna;
	}
	public double getVlnb() {
		return Vlnb;
	}
	public void setVlnb(double vlnb) {
		Vlnb = vlnb;
	}
	public double getVlnc() {
		return Vlnc;
	}
	public void setVlnc(double vlnc) {
		Vlnc = vlnc;
	}
	public double getVlnave() {
		return Vlnave;
	}
	public void setVlnave(double vlnave) {
		Vlnave = vlnave;
	}
	public double getVllab() {
		return Vllab;
	}
	public void setVllab(double vllab) {
		Vllab = vllab;
	}
	public double getVllbc() {
		return Vllbc;
	}
	public void setVllbc(double vllbc) {
		Vllbc = vllbc;
	}
	public double getVllca() {
		return Vllca;
	}
	public void setVllca(double vllca) {
		Vllca = vllca;
	}
	public double getVllave() {
		return Vllave;
	}
	public void setVllave(double vllave) {
		Vllave = vllave;
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
	public double getIave() {
		return Iave;
	}
	public void setIave(double iave) {
		Iave = iave;
	}
	public double getIdemand() {
		return Idemand;
	}
	public void setIdemand(double idemand) {
		Idemand = idemand;
	}
	public double getIpeakdemand() {
		return Ipeakdemand;
	}
	public void setIpeakdemand(double ipeakdemand) {
		Ipeakdemand = ipeakdemand;
	}
	public double getI4() {
		return I4;
	}
	public void setI4(double i4) {
		I4 = i4;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getPFsigntotal() {
		return PFsigntotal;
	}
	public void setPFsigntotal(double pFsigntotal) {
		PFsigntotal = pFsigntotal;
	}
	public double getPFsigna() {
		return PFsigna;
	}
	public void setPFsigna(double pFsigna) {
		PFsigna = pFsigna;
	}
	public double getPFsignb() {
		return PFsignb;
	}
	public void setPFsignb(double pFsignb) {
		PFsignb = pFsignb;
	}
	public double getPFsignc() {
		return PFsignc;
	}
	public void setPFsignc(double pFsignc) {
		PFsignc = pFsignc;
	}
	public double getkWtotal() {
		return kWtotal;
	}
	public void setkWtotal(double kWtotal) {
		this.kWtotal = kWtotal;
	}
	public double getkVARtotal() {
		return kVARtotal;
	}
	public void setkVARtotal(double kVARtotal) {
		this.kVARtotal = kVARtotal;
	}
	public double getkVAtotal() {
		return kVAtotal;
	}
	public void setkVAtotal(double kVAtotal) {
		this.kVAtotal = kVAtotal;
	}
	public double getkWa() {
		return kWa;
	}
	public void setkWa(double kWa) {
		this.kWa = kWa;
	}
	public double getkWb() {
		return kWb;
	}
	public void setkWb(double kWb) {
		this.kWb = kWb;
	}
	public double getkWc() {
		return kWc;
	}
	public void setkWc(double kWc) {
		this.kWc = kWc;
	}
	public double getkVARa() {
		return kVARa;
	}
	public void setkVARa(double kVARa) {
		this.kVARa = kVARa;
	}
	public double getkVARb() {
		return kVARb;
	}
	public void setkVARb(double kVARb) {
		this.kVARb = kVARb;
	}
	public double getkVARc() {
		return kVARc;
	}
	public void setkVARc(double kVARc) {
		this.kVARc = kVARc;
	}
	public double getkVAa() {
		return kVAa;
	}
	public void setkVAa(double kVAa) {
		this.kVAa = kVAa;
	}
	public double getkVAb() {
		return kVAb;
	}
	public void setkVAb(double kVAb) {
		this.kVAb = kVAb;
	}
	public double getkVAc() {
		return kVAc;
	}
	public void setkVAc(double kVAc) {
		this.kVAc = kVAc;
	}
	public double getkWdemand() {
		return kWdemand;
	}
	public void setkWdemand(double kWdemand) {
		this.kWdemand = kWdemand;
	}
	public double getkWpeakdemand() {
		return kWpeakdemand;
	}
	public void setkWpeakdemand(double kWpeakdemand) {
		this.kWpeakdemand = kWpeakdemand;
	}
	public double getkVARdemand() {
		return kVARdemand;
	}
	public void setkVARdemand(double kVARdemand) {
		this.kVARdemand = kVARdemand;
	}
	public double getkVAdemand() {
		return kVAdemand;
	}
	public void setkVAdemand(double kVAdemand) {
		this.kVAdemand = kVAdemand;
	}
	public double getkVARpeakdemand() {
		return kVARpeakdemand;
	}
	public void setkVARpeakdemand(double kVARpeakdemand) {
		this.kVARpeakdemand = kVARpeakdemand;
	}
	public double getkVApeakdemand() {
		return kVApeakdemand;
	}
	public void setkVApeakdemand(double kVApeakdemand) {
		this.kVApeakdemand = kVApeakdemand;
	}
	public double getkWhdel() {
		return kWhdel;
	}
	public void setkWhdel(double kWhdel) {
		this.kWhdel = kWhdel;
	}
	public double getkWhrec() {
		return kWhrec;
	}
	public void setkWhrec(double kWhrec) {
		this.kWhrec = kWhrec;
	}
	public double getkVARhdel() {
		return kVARhdel;
	}
	public void setkVARhdel(double kVARhdel) {
		this.kVARhdel = kVARhdel;
	}
	public double getkVARhrec() {
		return kVARhrec;
	}
	public void setkVARhrec(double kVARhrec) {
		this.kVARhrec = kVARhrec;
	}
	public double getkVAhdelrec() {
		return kVAhdelrec;
	}
	public void setkVAhdelrec(double kVAhdelrec) {
		this.kVAhdelrec = kVAhdelrec;
	}
	public double getV1THD() {
		return V1THD;
	}
	public void setV1THD(double v1thd) {
		V1THD = v1thd;
	}
	public double getV2THD() {
		return V2THD;
	}
	public void setV2THD(double v2thd) {
		V2THD = v2thd;
	}
	public double getV3THD() {
		return V3THD;
	}
	public void setV3THD(double v3thd) {
		V3THD = v3thd;
	}
	public double getI1THD() {
		return I1THD;
	}
	public void setI1THD(double i1thd) {
		I1THD = i1thd;
	}
	public double getI2THD() {
		return I2THD;
	}
	public void setI2THD(double i2thd) {
		I2THD = i2thd;
	}
	public double getI3THD() {
		return I3THD;
	}
	public void setI3THD(double i3thd) {
		I3THD = i3thd;
	}
	public double getIademand() {
		return Iademand;
	}
	public void setIademand(double iademand) {
		Iademand = iademand;
	}
	public double getIbdemand() {
		return Ibdemand;
	}
	public void setIbdemand(double ibdemand) {
		Ibdemand = ibdemand;
	}
	public double getIcdemand() {
		return Icdemand;
	}
	public void setIcdemand(double icdemand) {
		Icdemand = icdemand;
	}
	public double getIapeakdemand() {
		return Iapeakdemand;
	}
	public void setIapeakdemand(double iapeakdemand) {
		Iapeakdemand = iapeakdemand;
	}
	public double getIbpeakdemand() {
		return Ibpeakdemand;
	}
	public void setIbpeakdemand(double ibpeakdemand) {
		Ibpeakdemand = ibpeakdemand;
	}
	public double getIcpeakdemand() {
		return Icpeakdemand;
	}
	public void setIcpeakdemand(double icpeakdemand) {
		Icpeakdemand = icpeakdemand;
	}
	public double getkWhadel() {
		return kWhadel;
	}
	public void setkWhadel(double kWhadel) {
		this.kWhadel = kWhadel;
	}
	public double getkWhbdel() {
		return kWhbdel;
	}
	public void setkWhbdel(double kWhbdel) {
		this.kWhbdel = kWhbdel;
	}
	public double getkWhcdel() {
		return kWhcdel;
	}
	public void setkWhcdel(double kWhcdel) {
		this.kWhcdel = kWhcdel;
	}
	public double getkWharec() {
		return kWharec;
	}
	public void setkWharec(double kWharec) {
		this.kWharec = kWharec;
	}
	public double getkWhbrec() {
		return kWhbrec;
	}
	public void setkWhbrec(double kWhbrec) {
		this.kWhbrec = kWhbrec;
	}
	public double getkWhcrec() {
		return kWhcrec;
	}
	public void setkWhcrec(double kWhcrec) {
		this.kWhcrec = kWhcrec;
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
