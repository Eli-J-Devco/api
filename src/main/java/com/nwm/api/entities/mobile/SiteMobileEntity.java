package com.nwm.api.entities.mobile;

import java.util.Date;

public class SiteMobileEntity {
	private int id;
	private String siteName;
	private Date lastUpdate;
	private String address;
	private String priorityLabel;
	// private String city;
	// private String state;
	// private int totalError;
	// private double acCapacity;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int _id) {
		this.id = _id;
	}
	
	// public int getTotalError() {
	// 	return this.totalError;
	// }
	
	// public void setTotalError(int _totalError) {
	// 	this.totalError = _totalError;
	// }
	
	// public double getAcCapacity() {
	// 	return this.acCapacity;
	// }
	
	// public void setAcCapacity(double _acCapacity) {
	// 	this.acCapacity = _acCapacity;
	// }
	
	public String getSiteName() {
		return this.siteName;
	}
	
	public void setSiteName(String _siteName) {
		this.siteName = _siteName;
	}
	
	public Date getUpdatedDate() {
		return this.lastUpdate;
	}
	
	public void setUpdatedDate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPriorityLabel() {
		return priorityLabel;
	}
	public void setPriorityLabel(String priorityLabel) {
		this.priorityLabel = priorityLabel;
	}
	// public String getCity() {
	// 	return city;
	// }
	// public void setCity(String city) {
	// 	this.city = city;
	// }
	// public String getState() {
	// 	return state;
	// }
	// public void setState(String state) {
	// 	this.state = state;
	// }
}
