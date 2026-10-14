package com.nwm.api.entities.report.invertermeterproductionreport;

public class MeterInverterProductionReportRequest {
	private int reportId;
	private String startDate;
	private String endDate;
	private int dataInterval;
	
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getDataInterval() {
		return dataInterval;
	}
	public void setDataInterval(int dataInterval) {
		this.dataInterval = dataInterval;
	}
}
