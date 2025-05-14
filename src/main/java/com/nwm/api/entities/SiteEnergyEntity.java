/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class SiteEnergyEntity {
	private int id;
	private String name;
	private Double actualPower;
	private Double expectedPower;
	private Double actualEnergy;
	private Double expectedEnergy;
	private Double variance;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getActualPower() {
		return actualPower;
	}

	public void setActualPower(Double actualPower) {
		this.actualPower = actualPower;
	}

	public Double getExpectedPower() {
		return expectedPower;
	}

	public void setExpectedPower(Double expectedPower) {
		this.expectedPower = expectedPower;
	}

	public Double getActualEnergy() {
		return actualEnergy;
	}

	public void setActualEnergy(Double actualEnergy) {
		this.actualEnergy = actualEnergy;
	}

	public Double getExpectedEnergy() {
		return expectedEnergy;
	}

	public void setExpectedEnergy(Double expectedEnergy) {
		this.expectedEnergy = expectedEnergy;
	}

	public Double getVariance() {
		return variance;
	}

	public void setVariance(Double variance) {
		this.variance = variance;
	}
}
