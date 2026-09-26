/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.Date;

public class DeviceAlertDetectEntity{
	private int id_device;
	private String start_no_com;
	private String end_no_com;
	private int points;
	private int time_diff_minutes;

	public int getId_device() {
		return id_device;
	}

	public void setId_device(int id_device) {
		this.id_device = id_device;
	}

	public String getStart_no_com() {
		return start_no_com;
	}

	public void setStart_no_com(String start_no_com) {
		this.start_no_com = start_no_com;
	}

	public String getEnd_no_com() {
		return end_no_com;
	}

	public void setEnd_no_com(String end_no_com) {
		this.end_no_com = end_no_com;
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
