/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class CameraImageEntity {	
	private String time;
	private int id_device;
	private String image_url;
	private String datatablename;
	private String start_date;
	private String end_date;
	private String times_ago;
	private String times_ago_unit;
	private String last_updated;
	
	
	
	public String getTimes_ago() {
		return times_ago;
	}
	public void setTimes_ago(String times_ago) {
		this.times_ago = times_ago;
	}
	public String getTimes_ago_unit() {
		return times_ago_unit;
	}
	public void setTimes_ago_unit(String times_ago_unit) {
		this.times_ago_unit = times_ago_unit;
	}
	public String getLast_updated() {
		return last_updated;
	}
	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
}
