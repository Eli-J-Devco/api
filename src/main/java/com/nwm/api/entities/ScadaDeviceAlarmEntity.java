/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ScadaDeviceAlarmEntity {
	private int id;
	private String opened;
	private String issue;
	private String error_level;
	private String timezone_offset;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOpened() {
		return opened;
	}
	public void setOpened(String opened) {
		this.opened = opened;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getError_level() {
		return error_level;
	}
	public void setError_level(String error_level) {
		this.error_level = error_level;
	}
	public String getTimezone_offset() {
		return timezone_offset;
	}
	public void setTimezone_offset(String timezone_offset) {
		this.timezone_offset = timezone_offset;
	}
	
}
