package com.nwm.api.entities;


public class A8810Entity {

	private String time;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private float ion6200_kwh;
	private float ion6200_demand_kw;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public int getLow_alarm() {
		return low_alarm;
	}

	public void setLow_alarm(int low_alarm) {
		this.low_alarm = low_alarm;
	}

	public int getHigh_alarm() {
		return high_alarm;
	}

	public void setHigh_alarm(int high_alarm) {
		this.high_alarm = high_alarm;
	}

	public float getIon6200_kwh() {
		return ion6200_kwh;
	}

	public void setIon6200_kwh(float ion6200_kwh) {
		this.ion6200_kwh = ion6200_kwh;
	}

	public float getIon6200_demand_kw() {
		return ion6200_demand_kw;
	}

	public void setIon6200_demand_kw(float ion6200_demand_kw) {
		this.ion6200_demand_kw = ion6200_demand_kw;
	}

}
