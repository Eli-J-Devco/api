/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


public class AiDataMapEntity extends SortEntity {
	private String time;
	private int id_device;
	private double ai_power;
	private double ai_energy;
	private String datatablename;
	private int ai_train_type;
	
	
	public int getAi_train_type() {
		return ai_train_type;
	}
	public void setAi_train_type(int ai_train_type) {
		this.ai_train_type = ai_train_type;
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
	public double getAi_power() {
		return ai_power;
	}
	public void setAi_power(double ai_power) {
		this.ai_power = ai_power;
	}
	public double getAi_energy() {
		return ai_energy;
	}
	public void setAi_energy(double ai_energy) {
		this.ai_energy = ai_energy;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	
	
	
	
}
