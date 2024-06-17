/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class AssetManagementAndOperationPerformanceDataEntity {
	private String time_full;
	private Double actualEnergy;
	private Double modeledEnergy;
	private Double expectedEnergy;
	private Double actualPOA;
	private Double modeledPOA;
	private Double actualPerformanceRatio;
	private Double modeledPerformanceRatio;
	private Double energyIndex;
	private Double weatherIndex;
	private Double weatherAdjustedIndex;
	private Double inverterAvailability;
	
	private Double expectedGenerationIndex;
	private Double modeledGenerationIndex;
	
	private Double energyDifference;
	private Double actualEnergyRevenue;
	private Double estimatedEnergyRevenue;
	private Double energyRevenueDifference;
	
	private String event;
	private String devicename;
	private String startTime;
	private String endTime;
	private Integer duration;
	private Double estimatedLoss;
	
	public String getTime_full() {
		return time_full;
	}
	public void setTime_full(String time_full) {
		this.time_full = time_full;
	}
	public Double getActualEnergy() {
		return actualEnergy;
	}
	public void setActualEnergy(Double actualEnergy) {
		this.actualEnergy = actualEnergy;
	}
	public Double getModeledEnergy() {
		return modeledEnergy;
	}
	public void setModeledEnergy(Double modeledEnergy) {
		this.modeledEnergy = modeledEnergy;
	}
	public Double getExpectedEnergy() {
		return expectedEnergy;
	}
	public void setExpectedEnergy(Double expectedEnergy) {
		this.expectedEnergy = expectedEnergy;
	}
	public Double getActualPOA() {
		return actualPOA;
	}
	public void setActualPOA(Double actualPOA) {
		this.actualPOA = actualPOA;
	}
	public Double getModeledPOA() {
		return modeledPOA;
	}
	public void setModeledPOA(Double modeledPOA) {
		this.modeledPOA = modeledPOA;
	}
	public Double getActualPerformanceRatio() {
		return actualPerformanceRatio;
	}
	public void setActualPerformanceRatio(Double actualPerformanceRatio) {
		this.actualPerformanceRatio = actualPerformanceRatio;
	}
	public Double getModeledPerformanceRatio() {
		return modeledPerformanceRatio;
	}
	public void setModeledPerformanceRatio(Double modeledPerformanceRatio) {
		this.modeledPerformanceRatio = modeledPerformanceRatio;
	}
	public Double getEnergyIndex() {
		return energyIndex;
	}
	public void setEnergyIndex(Double energyIndex) {
		this.energyIndex = energyIndex;
	}
	public Double getWeatherIndex() {
		return weatherIndex;
	}
	public void setWeatherIndex(Double weatherIndex) {
		this.weatherIndex = weatherIndex;
	}
	public Double getWeatherAdjustedIndex() {
		return weatherAdjustedIndex;
	}
	public void setWeatherAdjustedIndex(Double weatherAdjustedIndex) {
		this.weatherAdjustedIndex = weatherAdjustedIndex;
	}
	public Double getInverterAvailability() {
		return inverterAvailability;
	}
	public void setInverterAvailability(Double inverterAvailability) {
		this.inverterAvailability = inverterAvailability;
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
	public Double getEnergyDifference() {
		return energyDifference;
	}
	public void setEnergyDifference(Double energyDifference) {
		this.energyDifference = energyDifference;
	}
	public Double getActualEnergyRevenue() {
		return actualEnergyRevenue;
	}
	public void setActualEnergyRevenue(Double actualEnergyRevenue) {
		this.actualEnergyRevenue = actualEnergyRevenue;
	}
	public Double getEstimatedEnergyRevenue() {
		return estimatedEnergyRevenue;
	}
	public void setEstimatedEnergyRevenue(Double estimatedEnergyRevenue) {
		this.estimatedEnergyRevenue = estimatedEnergyRevenue;
	}
	public Double getEnergyRevenueDifference() {
		return energyRevenueDifference;
	}
	public void setEnergyRevenueDifference(Double energyRevenueDifference) {
		this.energyRevenueDifference = energyRevenueDifference;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Double getEstimatedLoss() {
		return estimatedLoss;
	}
	public void setEstimatedLoss(Double estimatedLoss) {
		this.estimatedLoss = estimatedLoss;
	}
	
}
