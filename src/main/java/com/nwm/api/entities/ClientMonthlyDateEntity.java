/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientMonthlyDateEntity extends DateTimeReportDataEntity {
	
	private Integer id;
	private String download_time;
	private Double chart_energy_kwh;
	private Double nvm_irradiance;
	private Double expected_power;
	private Double expected_energy;
	private Double nvmActivePower;
	private Double nvmActiveEnergy;
	private Double energy_output;
	private Double energy_usage;
	private Double energy;
	private Double avgEnergy;
	
	
	public static Map<String, Object> convertDateTimeToMap(ClientMonthlyDateEntity obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (obj == null) return map;
		
		map.put("time_full", obj.getTime_full());
		map.put("categories_time", obj.getCategories_time());
		
		return map;
	}
	
	public static ClientMonthlyDateEntity convertDateTimeToEntity(Map<String, Object> map) {
		ClientMonthlyDateEntity entity = new ClientMonthlyDateEntity();
		if (map == null) return entity;
		
		entity.setTime_full((String) map.get("time_full"));
		entity.setCategories_time((String) map.get("categories_time"));
		
		return entity;
	}
	
	public Double getAvgEnergy() {
		return avgEnergy;
	}
	public void setAvgEnergy(Double avgEnergy) {
		this.avgEnergy = avgEnergy;
	}
	public Double getEnergy_output() {
		return energy_output;
	}
	public void setEnergy_output(Double energy_output) {
		this.energy_output = energy_output;
	}
	public Double getEnergy_usage() {
		return energy_usage;
	}
	public void setEnergy_usage(Double energy_usage) {
		this.energy_usage = energy_usage;
	}
	public Double getEnergy() {
		return energy;
	}
	public void setEnergy(Double energy) {
		this.energy = energy;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDownload_time() {
		return download_time;
	}
	public void setDownload_time(String download_time) {
		this.download_time = download_time;
	}
	public Double getChart_energy_kwh() {
		return chart_energy_kwh;
	}
	public void setChart_energy_kwh(Double chart_energy_kwh) {
		this.chart_energy_kwh = chart_energy_kwh;
	}
	public Double getNvm_irradiance() {
		return nvm_irradiance;
	}
	public void setNvm_irradiance(Double nvm_irradiance) {
		this.nvm_irradiance = nvm_irradiance;
	}
	public Double getExpected_power() {
		return expected_power;
	}
	public void setExpected_power(Double expected_power) {
		this.expected_power = expected_power;
	}
	public Double getExpected_energy() {
		return expected_energy;
	}
	public void setExpected_energy(Double expected_energy) {
		this.expected_energy = expected_energy;
	}
	public Double getNvmActivePower() {
		return nvmActivePower;
	}
	public void setNvmActivePower(Double nvmActivePower) {
		this.nvmActivePower = nvmActivePower;
	}
	public Double getNvmActiveEnergy() {
		return nvmActiveEnergy;
	}
	public void setNvmActiveEnergy(Double nvmActiveEnergy) {
		this.nvmActiveEnergy = nvmActiveEnergy;
	}
}
