/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class SitesTrackersEntity {
	private int id;
	private int id_site;
	private String hash_id;
	private String devicename;
	private int warning_deviation_tracker;
	private int error_deviation_tracker;
	private double setpoint;
	private double actual_angle;
	private double deviation;
	private int total_error;
	private boolean is_tracker_master;
	private String manufacture;
	
	
	public String getManufacture() {
		return manufacture;
	}
	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}
	public boolean isIs_tracker_master() {
		return is_tracker_master;
	}
	public void setIs_tracker_master(boolean is_tracker_master) {
		this.is_tracker_master = is_tracker_master;
	}
	public int getWarning_deviation_tracker() {
		return warning_deviation_tracker;
	}
	public void setWarning_deviation_tracker(int warning_deviation_tracker) {
		this.warning_deviation_tracker = warning_deviation_tracker;
	}
	public int getError_deviation_tracker() {
		return error_deviation_tracker;
	}
	public void setError_deviation_tracker(int error_deviation_tracker) {
		this.error_deviation_tracker = error_deviation_tracker;
	}
	public String getHash_id() {
		return hash_id;
	}
	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public double getSetpoint() {
		return setpoint;
	}
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}
	public double getActual_angle() {
		return actual_angle;
	}
	public void setActual_angle(double actual_angle) {
		this.actual_angle = actual_angle;
	}
	public double getDeviation() {
		return deviation;
	}
	public void setDeviation(double deviation) {
		this.deviation = deviation;
	}
	public int getTotal_error() {
		return total_error;
	}
	public void setTotal_error(int total_error) {
		this.total_error = total_error;
	}
}
