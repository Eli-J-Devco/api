/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class WeeklyDateEntity{
	
	private String time_format;
	private String categories_time;
	private String site_name;
	private Double actualGeneration;
	private Double expectedGeneration;
	private Double poa;
	private Double modeledGeneration;
	private Double expectedGenerationIndex;
	private Double modeledGenerationIndex;
	
	public String getTime_format() {
		return time_format;
	}
	public void setTime_format(String time_format) {
		this.time_format = time_format;
	}
	public String getCategories_time() {
		return categories_time;
	}
	public void setCategories_time(String categories_time) {
		this.categories_time = categories_time;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSiteName(String site_name) {
		this.site_name = site_name;
	}
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
