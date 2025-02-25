/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities.building;

public class SitesOverviewGasEventEntity {
	private String event;
	private String trigger_date;
	private String opened_period;
	private String icon_url;
	private String hardware;
	
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getTrigger_date() {
		return trigger_date;
	}
	public void setTrigger_date(String trigger_date) {
		this.trigger_date = trigger_date;
	}
	public String getOpened_period() {
		return opened_period;
	}
	public void setOpened_period(String opened_period) {
		this.opened_period = opened_period;
	}
	public String getIcon_url() {
		return icon_url;
	}
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}
	public String getHardware() {
		return hardware;
	}
	public void setHardware(String hardware) {
		this.hardware = hardware;
	}
	
}
