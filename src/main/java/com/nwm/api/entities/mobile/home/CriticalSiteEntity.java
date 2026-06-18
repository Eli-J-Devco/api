package com.nwm.api.entities.mobile.home;

public class CriticalSiteEntity {
    private int id;
	private String siteName;
	private String address;
	private int priorityLevel;
	private String tableDataReport;
	private String timezone;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int _id) {
		this.id = _id;
	}

	public String getTableDataReport() {
		return this.tableDataReport;
	}

	public void setTableDataReport(String _tableDataReport) {
		this.tableDataReport = _tableDataReport;
	}
    
	public String getSiteName() {
		return this.siteName;
	}
	
	public void setSiteName(String _siteName) {
		this.siteName = _siteName;
	}
    
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPriorityLevel() {
		return priorityLevel;
	}
	public void setPriorityLevel(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
}
