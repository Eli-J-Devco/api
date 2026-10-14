package com.nwm.api.entities.report.invertermeterproductionreport;

import java.util.List;

public class MeterInverterProductionReportResponse {
	private int siteName;
	private List<ValueDTO> data;
	
	public int getSiteName() {
		return siteName;
	}
	public void setSiteName(int siteName) {
		this.siteName = siteName;
	}
	public List<ValueDTO> getData() {
		return data;
	}
	public void setData(List<ValueDTO> data) {
		this.data = data;
	}
}
