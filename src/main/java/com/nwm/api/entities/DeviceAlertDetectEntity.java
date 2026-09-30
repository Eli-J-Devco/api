/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.Date;

public class DeviceAlertDetectEntity{
	private int id_device;
	private String start_time;
	private String end_time;
	private int points;
	private int time_diff_minutes;

	public int getId_device() {
		return id_device;
	}

	public void setId_device(int id_device) {
		this.id_device = id_device;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getTime_diff_minutes() {
		return time_diff_minutes;
	}

	public void setTime_diff_minutes(int time_diff_minutes) {
		this.time_diff_minutes = time_diff_minutes;
	}
}
