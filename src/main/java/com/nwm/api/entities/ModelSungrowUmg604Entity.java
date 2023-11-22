/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSungrowUmg604Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double M_AC_U1;
	private double M_AC_U2;
	private double M_AC_U3;
	private double M_AC_U_L1L2;
	private double M_AC_U_L2L3;
	private double M_AC_U_L3L1;
	private double M_AC_I1;
	private double M_AC_I2;
	private double M_AC_I3;
	private double M_AC_P1;
	private double M_AC_P2;
	private double M_AC_P3;
	private double M_AC_P;
	private double M_AC_E_M1;
	private double M_AC_Q1;
	private double M_AC_Q2;
	private double M_AC_Q3;
	private double M_AC_Q;
	private double M_AC_S1;
	private double M_AC_S2;
	private double M_AC_S3;
	private double M_AC_S;
	private double M_AC_PF;
	private double M_AC_F;
	private double M_AC_E_EXP;
	private double M_AC_E_IMP;
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
	public double getM_AC_U1() {
		return M_AC_U1;
	}
	public void setM_AC_U1(double m_AC_U1) {
		M_AC_U1 = m_AC_U1;
	}
	public double getM_AC_U2() {
		return M_AC_U2;
	}
	public void setM_AC_U2(double m_AC_U2) {
		M_AC_U2 = m_AC_U2;
	}
	public double getM_AC_U3() {
		return M_AC_U3;
	}
	public void setM_AC_U3(double m_AC_U3) {
		M_AC_U3 = m_AC_U3;
	}
	public double getM_AC_U_L1L2() {
		return M_AC_U_L1L2;
	}
	public void setM_AC_U_L1L2(double m_AC_U_L1L2) {
		M_AC_U_L1L2 = m_AC_U_L1L2;
	}
	public double getM_AC_U_L2L3() {
		return M_AC_U_L2L3;
	}
	public void setM_AC_U_L2L3(double m_AC_U_L2L3) {
		M_AC_U_L2L3 = m_AC_U_L2L3;
	}
	public double getM_AC_U_L3L1() {
		return M_AC_U_L3L1;
	}
	public void setM_AC_U_L3L1(double m_AC_U_L3L1) {
		M_AC_U_L3L1 = m_AC_U_L3L1;
	}
	public double getM_AC_I1() {
		return M_AC_I1;
	}
	public void setM_AC_I1(double m_AC_I1) {
		M_AC_I1 = m_AC_I1;
	}
	public double getM_AC_I2() {
		return M_AC_I2;
	}
	public void setM_AC_I2(double m_AC_I2) {
		M_AC_I2 = m_AC_I2;
	}
	public double getM_AC_I3() {
		return M_AC_I3;
	}
	public void setM_AC_I3(double m_AC_I3) {
		M_AC_I3 = m_AC_I3;
	}
	public double getM_AC_P1() {
		return M_AC_P1;
	}
	public void setM_AC_P1(double m_AC_P1) {
		M_AC_P1 = m_AC_P1;
	}
	public double getM_AC_P2() {
		return M_AC_P2;
	}
	public void setM_AC_P2(double m_AC_P2) {
		M_AC_P2 = m_AC_P2;
	}
	public double getM_AC_P3() {
		return M_AC_P3;
	}
	public void setM_AC_P3(double m_AC_P3) {
		M_AC_P3 = m_AC_P3;
	}
	public double getM_AC_P() {
		return M_AC_P;
	}
	public void setM_AC_P(double m_AC_P) {
		M_AC_P = m_AC_P;
	}
	public double getM_AC_E_M1() {
		return M_AC_E_M1;
	}
	public void setM_AC_E_M1(double m_AC_E_M1) {
		M_AC_E_M1 = m_AC_E_M1;
	}
	public double getM_AC_Q1() {
		return M_AC_Q1;
	}
	public void setM_AC_Q1(double m_AC_Q1) {
		M_AC_Q1 = m_AC_Q1;
	}
	public double getM_AC_Q2() {
		return M_AC_Q2;
	}
	public void setM_AC_Q2(double m_AC_Q2) {
		M_AC_Q2 = m_AC_Q2;
	}
	public double getM_AC_Q3() {
		return M_AC_Q3;
	}
	public void setM_AC_Q3(double m_AC_Q3) {
		M_AC_Q3 = m_AC_Q3;
	}
	public double getM_AC_Q() {
		return M_AC_Q;
	}
	public void setM_AC_Q(double m_AC_Q) {
		M_AC_Q = m_AC_Q;
	}
	public double getM_AC_S1() {
		return M_AC_S1;
	}
	public void setM_AC_S1(double m_AC_S1) {
		M_AC_S1 = m_AC_S1;
	}
	public double getM_AC_S2() {
		return M_AC_S2;
	}
	public void setM_AC_S2(double m_AC_S2) {
		M_AC_S2 = m_AC_S2;
	}
	public double getM_AC_S3() {
		return M_AC_S3;
	}
	public void setM_AC_S3(double m_AC_S3) {
		M_AC_S3 = m_AC_S3;
	}
	public double getM_AC_S() {
		return M_AC_S;
	}
	public void setM_AC_S(double m_AC_S) {
		M_AC_S = m_AC_S;
	}
	public double getM_AC_PF() {
		return M_AC_PF;
	}
	public void setM_AC_PF(double m_AC_PF) {
		M_AC_PF = m_AC_PF;
	}
	public double getM_AC_F() {
		return M_AC_F;
	}
	public void setM_AC_F(double m_AC_F) {
		M_AC_F = m_AC_F;
	}
	public double getM_AC_E_EXP() {
		return M_AC_E_EXP;
	}
	public void setM_AC_E_EXP(double m_AC_E_EXP) {
		M_AC_E_EXP = m_AC_E_EXP;
	}
	public double getM_AC_E_IMP() {
		return M_AC_E_IMP;
	}
	public void setM_AC_E_IMP(double m_AC_E_IMP) {
		M_AC_E_IMP = m_AC_E_IMP;
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
