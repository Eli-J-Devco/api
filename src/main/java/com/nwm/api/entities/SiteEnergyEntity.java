/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class SiteEnergyEntity {
	private int id;
	private String hash_id;
	private String name;
	private Double actualPower;
	private Double expectedPower;
	private Double actualEnergy;
	private Double expectedEnergy;
	private Double irradiance;
	private Double variance;
	private Double ae;
	private String last_updated;
	private double overPerformingActualExpected;
	private double onTargetBetweenActualExpected;
	private double onTargetAndActualExpected;
	private double underPerformingActualExpected;
	
	
	public double getOverPerformingActualExpected() {
		return overPerformingActualExpected;
	}

	public void setOverPerformingActualExpected(double overPerformingActualExpected) {
		this.overPerformingActualExpected = overPerformingActualExpected;
	}

	public double getOnTargetBetweenActualExpected() {
		return onTargetBetweenActualExpected;
	}

	public void setOnTargetBetweenActualExpected(double onTargetBetweenActualExpected) {
		this.onTargetBetweenActualExpected = onTargetBetweenActualExpected;
	}

	public double getOnTargetAndActualExpected() {
		return onTargetAndActualExpected;
	}

	public void setOnTargetAndActualExpected(double onTargetAndActualExpected) {
		this.onTargetAndActualExpected = onTargetAndActualExpected;
	}

	public double getUnderPerformingActualExpected() {
		return underPerformingActualExpected;
	}

	public void setUnderPerformingActualExpected(double underPerformingActualExpected) {
		this.underPerformingActualExpected = underPerformingActualExpected;
	}

	public String getLast_updated() {
		return last_updated;
	}

	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}

	public Double getIrradiance() {
		return irradiance;
	}

	public void setIrradiance(Double irradiance) {
		this.irradiance = irradiance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getHash_id() {
		return hash_id;
	}

	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
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

	public Double getAe() {
		return ae;
	}

	public void setAe(Double ae) {
		this.ae = ae;
	}
}
