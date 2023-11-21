/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class ModelVirtualMeterOrInverterEntity {
	private String time;
	private int id_device;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private List data;
	private double nvm_temperature;
	private double nvm_irradiance;
	private double expected_power_ac;
	private double expected_energy;
	private double r_irradiance;
	
	private double nvm_panel_temperature;
	private String table_data_virtual;
	
	
	public double getNvm_panel_temperature() {
		return nvm_panel_temperature;
	}
	public void setNvm_panel_temperature(double nvm_panel_temperature) {
		this.nvm_panel_temperature = nvm_panel_temperature;
	}
	

	
	public double getR_irradiance() {
		return r_irradiance;
	}
	public void setR_irradiance(double r_irradiance) {
		this.r_irradiance = r_irradiance;
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
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	public double getNvm_temperature() {
		return nvm_temperature;
	}
	public void setNvm_temperature(double nvm_temperature) {
		this.nvm_temperature = nvm_temperature;
	}
	public double getNvm_irradiance() {
		return nvm_irradiance;
	}
	public void setNvm_irradiance(double nvm_irradiance) {
		this.nvm_irradiance = nvm_irradiance;
	}

	public double getExpected_power_ac() {
		return expected_power_ac;
	}
	public void setExpected_power_ac(double expected_power_ac) {
		this.expected_power_ac = expected_power_ac;
	}
	public double getExpected_energy() {
		return expected_energy;
	}
	public void setExpected_energy(double expected_energy) {
		this.expected_energy = expected_energy;
	}
	public String getTable_data_virtual() {
		return table_data_virtual;
	}
	public void setTable_data_virtual(String table_data_virtual) {
		this.table_data_virtual = table_data_virtual;
	}
	
	
	
}
