/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class EEREntity {
	private int id;
	private Double ActualGeneration;
	private Double EstimatedGeneration;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getActualGeneration() {
		return ActualGeneration;
	}
	public void setActualGeneration(Double actualGeneration) {
		ActualGeneration = actualGeneration;
	}
	public Double getEstimatedGeneration() {
		return EstimatedGeneration;
	}
	public void setEstimatedGeneration(Double estimatedGeneration) {
		EstimatedGeneration = estimatedGeneration;
	}
}
