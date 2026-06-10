/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSungrowSg50cxEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;

	private double E_DAY;
	private double E_TOTAL;
	private double E_YEAR;
	private double OT_AC_TOTAL;
	private double T_WR;
	private double U_DC1;
	private double I_DC1;
	private double U_DC2;
	private double I_DC2;
	private double U_DC3;
	private double I_DC3;
	private double U_DC4;
	private double I_DC4;
	private double U_DC5;
	private double I_DC5;
	private double U_DC6;
	private double I_DC6;
	private double U_DC7;
	private double I_DC7;
	private double U_DC8;
	private double I_DC8;
	private double U_DC9;
	private double I_DC9;
	private double U_DC10;
	private double I_DC10;
	private double U_DC11;
	private double I_DC11;
	private double U_DC12;
	private double I_DC12;
	private double U_DC13;
	private double I_DC13;
	private double U_DC14;
	private double I_DC14;
	private double U_DC15;
	private double I_DC15;
	private double U_DC16;
	private double I_DC16;
	private double U_DC17;
	private double I_DC17;
	private double U_DC18;
	private double I_DC18;
	private double U_DC19;
	private double I_DC19;
	private double U_DC20;
	private double I_DC20;

	private double U_STR1;
	private double I_STR1;
	private double U_STR2;
	private double I_STR2;
	private double U_STR3;
	private double I_STR3;
	private double U_STR4;
	private double I_STR4;
	private double U_STR5;
	private double I_STR5;
	private double U_STR6;
	private double I_STR6;
	private double U_STR7;
	private double I_STR7;
	private double U_STR8;
	private double I_STR8;
	private double U_STR9;
	private double I_STR9;
	private double U_STR10;
	private double I_STR10;
	private double I_STR11;
	private double I_STR12;
	private double I_STR13;
	private double I_STR14;
	private double I_STR15;
	private double I_STR16;
	private double I_STR17;
	private double I_STR18;
	private double I_STR19;
	private double I_STR20;
	private double I_STR21;
	private double I_STR22;
	private double P_DC;
	private double U_AC1;
	private double U_AC2;
	private double U_AC3;
	private double I_AC1;
	private double I_AC2;
	private double I_AC3;
	private double S_AC;
	private double P_AC;
	private double Q_AC;
	private double COS_PHI;
	private double F_AC;
	private double FT_AC_DAY;
	private double R_ISO;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
private int enable_alert;
	
	
	
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
	public double getE_DAY() {
		return E_DAY;
	}
	public void setE_DAY(double e_DAY) {
		E_DAY = e_DAY;
	}
	public double getE_TOTAL() {
		return E_TOTAL;
	}
	public void setE_TOTAL(double e_TOTAL) {
		E_TOTAL = e_TOTAL;
	}
	public double getE_YEAR() {
		return E_YEAR;
	}
	public void setE_YEAR(double e_YEAR) {
		E_YEAR = e_YEAR;
	}
	public double getOT_AC_TOTAL() {
		return OT_AC_TOTAL;
	}
	public void setOT_AC_TOTAL(double oT_AC_TOTAL) {
		OT_AC_TOTAL = oT_AC_TOTAL;
	}
	public double getT_WR() {
		return T_WR;
	}
	public void setT_WR(double t_WR) {
		T_WR = t_WR;
	}
	public double getU_DC1() {
		return U_DC1;
	}
	public void setU_DC1(double u_DC1) {
		U_DC1 = u_DC1;
	}
	public double getI_DC1() {
		return I_DC1;
	}
	public void setI_DC1(double i_DC1) {
		I_DC1 = i_DC1;
	}
	public double getU_DC2() {
		return U_DC2;
	}
	public void setU_DC2(double u_DC2) {
		U_DC2 = u_DC2;
	}
	public double getI_DC2() {
		return I_DC2;
	}
	public void setI_DC2(double i_DC2) {
		I_DC2 = i_DC2;
	}
	public double getU_DC3() {
		return U_DC3;
	}
	public void setU_DC3(double u_DC3) {
		U_DC3 = u_DC3;
	}
	public double getI_DC3() {
		return I_DC3;
	}
	public void setI_DC3(double i_DC3) {
		I_DC3 = i_DC3;
	}
	public double getU_DC4() {
		return U_DC4;
	}
	public void setU_DC4(double u_DC4) {
		U_DC4 = u_DC4;
	}
	public double getI_DC4() {
		return I_DC4;
	}
	public void setI_DC4(double i_DC4) {
		I_DC4 = i_DC4;
	}
	public double getU_DC5() {
		return U_DC5;
	}
	public void setU_DC5(double u_DC5) {
		U_DC5 = u_DC5;
	}
	public double getI_DC5() {
		return I_DC5;
	}
	public void setI_DC5(double i_DC5) {
		I_DC5 = i_DC5;
	}
	public double getU_DC6() {
		return U_DC6;
	}
	public void setU_DC6(double u_DC6) {
		U_DC6 = u_DC6;
	}
	public double getI_DC6() {
		return I_DC6;
	}
	public void setI_DC6(double i_DC6) {
		I_DC6 = i_DC6;
	}
	public double getU_DC7() {
		return U_DC7;
	}
	public void setU_DC7(double u_DC7) {
		U_DC7 = u_DC7;
	}
	public double getI_DC7() {
		return I_DC7;
	}
	public void setI_DC7(double i_DC7) {
		I_DC7 = i_DC7;
	}
	public double getU_DC8() {
		return U_DC8;
	}
	public void setU_DC8(double u_DC8) {
		U_DC8 = u_DC8;
	}
	public double getI_DC8() {
		return I_DC8;
	}
	public void setI_DC8(double i_DC8) {
		I_DC8 = i_DC8;
	}
	public double getU_DC9() {
		return U_DC9;
	}
	public void setU_DC9(double u_DC9) {
		U_DC9 = u_DC9;
	}
	public double getI_DC9() {
		return I_DC9;
	}
	public void setI_DC9(double i_DC9) {
		I_DC9 = i_DC9;
	}
	public double getU_DC10() {
		return U_DC10;
	}
	public void setU_DC10(double u_DC10) {
		U_DC10 = u_DC10;
	}
	public double getI_DC10() {
		return I_DC10;
	}
	public void setI_DC10(double i_DC10) {
		I_DC10 = i_DC10;
	}
	public double getU_DC11() {
		return U_DC11;
	}
	public void setU_DC11(double u_DC11) {
		U_DC11 = u_DC11;
	}
	public double getI_DC11() {
		return I_DC11;
	}
	public void setI_DC11(double i_DC11) {
		I_DC11 = i_DC11;
	}
	public double getU_DC12() {
		return U_DC12;
	}
	public void setU_DC12(double u_DC12) {
		U_DC12 = u_DC12;
	}
	public double getI_DC12() {
		return I_DC12;
	}
	public void setI_DC12(double i_DC12) {
		I_DC12 = i_DC12;
	}
	public double getU_DC13() {
		return U_DC13;
	}
	public void setU_DC13(double u_DC13) {
		U_DC13 = u_DC13;
	}
	public double getI_DC13() {
		return I_DC13;
	}
	public void setI_DC13(double i_DC13) {
		I_DC13 = i_DC13;
	}
	public double getU_DC14() {
		return U_DC14;
	}
	public void setU_DC14(double u_DC14) {
		U_DC14 = u_DC14;
	}
	public double getI_DC14() {
		return I_DC14;
	}
	public void setI_DC14(double i_DC14) {
		I_DC14 = i_DC14;
	}
	public double getU_DC15() {
		return U_DC15;
	}
	public void setU_DC15(double u_DC15) {
		U_DC15 = u_DC15;
	}
	public double getI_DC15() {
		return I_DC15;
	}
	public void setI_DC15(double i_DC15) {
		I_DC15 = i_DC15;
	}
	public double getU_DC16() {
		return U_DC16;
	}
	public void setU_DC16(double u_DC16) {
		U_DC16 = u_DC16;
	}
	public double getI_DC16() {
		return I_DC16;
	}
	public void setI_DC16(double i_DC16) {
		I_DC16 = i_DC16;
	}
	public double getU_DC17() {
		return U_DC17;
	}
	public void setU_DC17(double u_DC17) {
		U_DC17 = u_DC17;
	}
	public double getI_DC17() {
		return I_DC17;
	}
	public void setI_DC17(double i_DC17) {
		I_DC17 = i_DC17;
	}
	public double getU_DC18() {
		return U_DC18;
	}
	public void setU_DC18(double u_DC18) {
		U_DC18 = u_DC18;
	}
	public double getI_DC18() {
		return I_DC18;
	}
	public void setI_DC18(double i_DC18) {
		I_DC18 = i_DC18;
	}
	public double getU_DC19() {
		return U_DC19;
	}
	public void setU_DC19(double u_DC19) {
		U_DC19 = u_DC19;
	}
	public double getI_DC19() {
		return I_DC19;
	}
	public void setI_DC19(double i_DC19) {
		I_DC19 = i_DC19;
	}
	public double getU_DC20() {
		return U_DC20;
	}
	public void setU_DC20(double u_DC20) {
		U_DC20 = u_DC20;
	}
	public double getI_DC20() {
		return I_DC20;
	}
	public void setI_DC20(double i_DC20) {
		I_DC20 = i_DC20;
	}
	public double getU_STR1() {
		return U_STR1;
	}
	public void setU_STR1(double u_STR1) {
		U_STR1 = u_STR1;
	}
	public double getI_STR1() {
		return I_STR1;
	}
	public void setI_STR1(double i_STR1) {
		I_STR1 = i_STR1;
	}
	public double getU_STR2() {
		return U_STR2;
	}
	public void setU_STR2(double u_STR2) {
		U_STR2 = u_STR2;
	}
	public double getI_STR2() {
		return I_STR2;
	}
	public void setI_STR2(double i_STR2) {
		I_STR2 = i_STR2;
	}
	public double getU_STR3() {
		return U_STR3;
	}
	public void setU_STR3(double u_STR3) {
		U_STR3 = u_STR3;
	}
	public double getI_STR3() {
		return I_STR3;
	}
	public void setI_STR3(double i_STR3) {
		I_STR3 = i_STR3;
	}
	public double getU_STR4() {
		return U_STR4;
	}
	public void setU_STR4(double u_STR4) {
		U_STR4 = u_STR4;
	}
	public double getI_STR4() {
		return I_STR4;
	}
	public void setI_STR4(double i_STR4) {
		I_STR4 = i_STR4;
	}
	public double getU_STR5() {
		return U_STR5;
	}
	public void setU_STR5(double u_STR5) {
		U_STR5 = u_STR5;
	}
	public double getI_STR5() {
		return I_STR5;
	}
	public void setI_STR5(double i_STR5) {
		I_STR5 = i_STR5;
	}
	public double getU_STR6() {
		return U_STR6;
	}
	public void setU_STR6(double u_STR6) {
		U_STR6 = u_STR6;
	}
	public double getI_STR6() {
		return I_STR6;
	}
	public void setI_STR6(double i_STR6) {
		I_STR6 = i_STR6;
	}
	public double getU_STR7() {
		return U_STR7;
	}
	public void setU_STR7(double u_STR7) {
		U_STR7 = u_STR7;
	}
	public double getI_STR7() {
		return I_STR7;
	}
	public void setI_STR7(double i_STR7) {
		I_STR7 = i_STR7;
	}
	public double getU_STR8() {
		return U_STR8;
	}
	public void setU_STR8(double u_STR8) {
		U_STR8 = u_STR8;
	}
	public double getI_STR8() {
		return I_STR8;
	}
	public void setI_STR8(double i_STR8) {
		I_STR8 = i_STR8;
	}
	public double getU_STR9() {
		return U_STR9;
	}
	public void setU_STR9(double u_STR9) {
		U_STR9 = u_STR9;
	}
	public double getI_STR9() {
		return I_STR9;
	}
	public void setI_STR9(double i_STR9) {
		I_STR9 = i_STR9;
	}
	public double getU_STR10() {
		return U_STR10;
	}
	public void setU_STR10(double u_STR10) {
		U_STR10 = u_STR10;
	}
	public double getI_STR10() {
		return I_STR10;
	}
	public void setI_STR10(double i_STR10) {
		I_STR10 = i_STR10;
	}
	public double getI_STR11() {
		return I_STR11;
	}
	public void setI_STR11(double i_STR11) {
		I_STR11 = i_STR11;
	}
	public double getI_STR12() {
		return I_STR12;
	}
	public void setI_STR12(double i_STR12) {
		I_STR12 = i_STR12;
	}
	public double getI_STR13() {
		return I_STR13;
	}
	public void setI_STR13(double i_STR13) {
		I_STR13 = i_STR13;
	}
	public double getI_STR14() {
		return I_STR14;
	}
	public void setI_STR14(double i_STR14) {
		I_STR14 = i_STR14;
	}
	public double getI_STR15() {
		return I_STR15;
	}
	public void setI_STR15(double i_STR15) {
		I_STR15 = i_STR15;
	}
	public double getI_STR16() {
		return I_STR16;
	}
	public void setI_STR16(double i_STR16) {
		I_STR16 = i_STR16;
	}
	public double getI_STR17() {
		return I_STR17;
	}
	public void setI_STR17(double i_STR17) {
		I_STR17 = i_STR17;
	}
	public double getI_STR18() {
		return I_STR18;
	}
	public void setI_STR18(double i_STR18) {
		I_STR18 = i_STR18;
	}
	public double getI_STR19() {
		return I_STR19;
	}
	public void setI_STR19(double i_STR19) {
		I_STR19 = i_STR19;
	}
	public double getI_STR20() {
		return I_STR20;
	}
	public void setI_STR20(double i_STR20) {
		I_STR20 = i_STR20;
	}
	public double getI_STR21() {
		return I_STR21;
	}
	public void setI_STR21(double i_STR21) {
		I_STR21 = i_STR21;
	}
	public double getI_STR22() {
		return I_STR22;
	}
	public void setI_STR22(double i_STR22) {
		I_STR22 = i_STR22;
	}
	public double getP_DC() {
		return P_DC;
	}
	public void setP_DC(double p_DC) {
		P_DC = p_DC;
	}
	public double getU_AC1() {
		return U_AC1;
	}
	public void setU_AC1(double u_AC1) {
		U_AC1 = u_AC1;
	}
	public double getU_AC2() {
		return U_AC2;
	}
	public void setU_AC2(double u_AC2) {
		U_AC2 = u_AC2;
	}
	public double getU_AC3() {
		return U_AC3;
	}
	public void setU_AC3(double u_AC3) {
		U_AC3 = u_AC3;
	}
	public double getI_AC1() {
		return I_AC1;
	}
	public void setI_AC1(double i_AC1) {
		I_AC1 = i_AC1;
	}
	public double getI_AC2() {
		return I_AC2;
	}
	public void setI_AC2(double i_AC2) {
		I_AC2 = i_AC2;
	}
	public double getI_AC3() {
		return I_AC3;
	}
	public void setI_AC3(double i_AC3) {
		I_AC3 = i_AC3;
	}
	public double getS_AC() {
		return S_AC;
	}
	public void setS_AC(double s_AC) {
		S_AC = s_AC;
	}
	public double getP_AC() {
		return P_AC;
	}
	public void setP_AC(double p_AC) {
		P_AC = p_AC;
	}
	public double getQ_AC() {
		return Q_AC;
	}
	public void setQ_AC(double q_AC) {
		Q_AC = q_AC;
	}
	public double getCOS_PHI() {
		return COS_PHI;
	}
	public void setCOS_PHI(double cOS_PHI) {
		COS_PHI = cOS_PHI;
	}
	public double getF_AC() {
		return F_AC;
	}
	public void setF_AC(double f_AC) {
		F_AC = f_AC;
	}
	public double getFT_AC_DAY() {
		return FT_AC_DAY;
	}
	public void setFT_AC_DAY(double fT_AC_DAY) {
		FT_AC_DAY = fT_AC_DAY;
	}
	public double getR_ISO() {
		return R_ISO;
	}
	public void setR_ISO(double r_ISO) {
		R_ISO = r_ISO;
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
