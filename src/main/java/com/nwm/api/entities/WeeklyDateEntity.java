/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class WeeklyDateEntity extends DateTimeReportDataEntity {
	
	private Double actualGeneration;
	private Double expectedGeneration;
	private Double poa;
	private Double modeledGeneration;
	private Double expectedGenerationIndex;
	private Double modeledGenerationIndex;
	
	public Double getActualGeneration() {
		return actualGeneration;
	}
	public void setActualGeneration(Double actualGeneration) {
		this.actualGeneration = actualGeneration;
	}
	public Double getExpectedGeneration() {
		return expectedGeneration;
	}
	public void setExpectedGeneration(Double expectedGeneration) {
		this.expectedGeneration = expectedGeneration;
	}
	public Double getPoa() {
		return poa;
	}
	public void setPoa(Double poa) {
		this.poa = poa;
	}
	public Double getModeledGeneration() {
		return modeledGeneration;
	}
	public void setModeledGeneration(Double modeledGeneration) {
		this.modeledGeneration = modeledGeneration;
	}
	public Double getExpectedGenerationIndex() {
		return expectedGenerationIndex;
	}
	public void setExpectedGenerationIndex(Double expectedGenerationIndex) {
		this.expectedGenerationIndex = expectedGenerationIndex;
	}
	public Double getModeledGenerationIndex() {
		return modeledGenerationIndex;
	}
	public void setModeledGenerationIndex(Double modeledGenerationIndex) {
		this.modeledGenerationIndex = modeledGenerationIndex;
	}
	
}
