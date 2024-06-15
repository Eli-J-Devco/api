/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;
import java.util.Map;

public class AssetManagementAndOperationPerformanceReportEntity {
	private Double dc_capacity;
	private List operationPerformanceData;
	private Map<String, Object> monthlyPerformanceData;
	private List monthlyAssetManagementData;
	
	public Double getDc_capacity() {
		return dc_capacity;
	}
	public void setDc_capacity(Double dc_capacity) {
		this.dc_capacity = dc_capacity;
	}
	public List getOperationPerformanceData() {
		return operationPerformanceData;
	}
	public void setOperationPerformanceData(List operationPerformanceData) {
		this.operationPerformanceData = operationPerformanceData;
	}
	public Map<String, Object> getMonthlyPerformanceData() {
		return monthlyPerformanceData;
	}
	public void setMonthlyPerformanceData(Map<String, Object> monthlyPerformanceData) {
		this.monthlyPerformanceData = monthlyPerformanceData;
	}
	public List getMonthlyAssetManagementData() {
		return monthlyAssetManagementData;
	}
	public void setMonthlyAssetManagementData(List monthlyAssetManagementData) {
		this.monthlyAssetManagementData = monthlyAssetManagementData;
	}
	public List getEstimatedLossByEventData() {
		return estimatedLossByEventData;
	}
	public void setEstimatedLossByEventData(List estimatedLossByEventData) {
		this.estimatedLossByEventData = estimatedLossByEventData;
	}
	private List estimatedLossByEventData;
	
}
