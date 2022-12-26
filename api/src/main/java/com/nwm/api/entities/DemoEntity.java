package com.nwm.api.entities;

import java.util.Date;



public class DemoEntity {
	
	private Date time;
	private String error;
	private String low_alarm;
	private String high_alarm;
	private String ion6200_kwh;
	private String ion6200_demand_kw;
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getLow_alarm() {
		return low_alarm;
	}
	public void setLow_alarm(String low_alarm) {
		this.low_alarm = low_alarm;
	}
	public String getHigh_alarm() {
		return high_alarm;
	}
	public void setHigh_alarm(String high_alarm) {
		this.high_alarm = high_alarm;
	}
	public String getIon6200_kwh() {
		return ion6200_kwh;
	}
	public void setIon6200_kwh(String ion6200_kwh) {
		this.ion6200_kwh = ion6200_kwh;
	}
	public String getIon6200_demand_kw() {
		return ion6200_demand_kw;
	}
	public void setIon6200_demand_kw(String ion6200_demand_kw) {
		this.ion6200_demand_kw = ion6200_demand_kw;
	}
	


}
