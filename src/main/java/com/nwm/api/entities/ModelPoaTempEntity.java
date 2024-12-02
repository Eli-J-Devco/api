/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelPoaTempEntity extends ModelBaseEntity {
	private double T_AMB;
	private double T_MOD;
	
	public double getT_AMB() {
		return T_AMB;
	}
	public void setT_AMB(double t_AMB) {
		T_AMB = t_AMB;
	}
	public double getT_MOD() {
		return T_MOD;
	}
	public void setT_MOD(double t_MOD) {
		T_MOD = t_MOD;
	}
	
}
