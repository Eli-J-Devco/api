/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;
import java.util.Map;

public class AssetManagementAndOperationPerformanceReportEntity {
	private ViewReportEntity reportDetail;
	private List<AssetManagementAndOperationPerformanceDataEntity> operationPerformanceData;
	private Map<String, Object> monthlyPerformanceData;
	private List<AssetManagementAndOperationPerformanceDataEntity> monthlyAssetManagementData;
	private List<AssetManagementAndOperationPerformanceDataEntity> estimatedLossByEventData;
	
	public ViewReportEntity getReportDetail() {
		return reportDetail;
	}
	public void setReportDetail(ViewReportEntity reportDetail) {
		this.reportDetail = reportDetail;
	}
	public List<AssetManagementAndOperationPerformanceDataEntity> getOperationPerformanceData() {
		return operationPerformanceData;
	}
	public void setOperationPerformanceData(List<AssetManagementAndOperationPerformanceDataEntity> operationPerformanceData) {
		this.operationPerformanceData = operationPerformanceData;
	}
	public Map<String, Object> getMonthlyPerformanceData() {
		return monthlyPerformanceData;
	}
	public void setMonthlyPerformanceData(Map<String, Object> monthlyPerformanceData) {
		this.monthlyPerformanceData = monthlyPerformanceData;
	}
	public List<AssetManagementAndOperationPerformanceDataEntity> getMonthlyAssetManagementData() {
		return monthlyAssetManagementData;
	}
	public void setMonthlyAssetManagementData(List<AssetManagementAndOperationPerformanceDataEntity> monthlyAssetManagementData) {
		this.monthlyAssetManagementData = monthlyAssetManagementData;
	}
	public List<AssetManagementAndOperationPerformanceDataEntity> getEstimatedLossByEventData() {
		return estimatedLossByEventData;
	}
	public void setEstimatedLossByEventData(List<AssetManagementAndOperationPerformanceDataEntity> estimatedLossByEventData) {
		this.estimatedLossByEventData = estimatedLossByEventData;
	}
	
}
