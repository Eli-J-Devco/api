/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class DeviceYieldEntity {
	private int id;
	private String hash_id;
	private String devicename;
	private String ip_address;
	private int id_device_type;
	private Double rating_ac_power;
	private String image;
	private int totalError;
	private Integer upTime;
	private Double currentPower;
	private Double yieldToday;
	private Double yieldYesterday;
	private Double yieldLast7Days;
	private Double yieldYTD;
	private List parameters;
	private String json_last_data;
	private String advance_tech_control_tag;
	
	
	
	public String getAdvance_tech_control_tag() {
		return advance_tech_control_tag;
	}
	public void setAdvance_tech_control_tag(String advance_tech_control_tag) {
		this.advance_tech_control_tag = advance_tech_control_tag;
	}
	public String getJson_last_data() {
		return json_last_data;
	}
	public void setJson_last_data(String json_last_data) {
		this.json_last_data = json_last_data;
	}
	public List getParameters() {
		return parameters;
	}
	public void setParameters(List parameters) {
		this.parameters = parameters;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHash_id() {
		return hash_id;
	}
	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public int getId_device_type() {
		return id_device_type;
	}
	public void setId_device_type(int id_device_type) {
		this.id_device_type = id_device_type;
	}
	public Double getRating_ac_power() {
		return rating_ac_power;
	}
	public void setRating_ac_power(Double rating_ac_power) {
		this.rating_ac_power = rating_ac_power;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getTotalError() {
		return totalError;
	}
	public void setTotalError(int totalError) {
		this.totalError = totalError;
	}
	public Integer getUpTime() {
		return upTime;
	}
	public void setUpTime(Integer upTime) {
		this.upTime = upTime;
	}
	public Double getCurrentPower() {
		return currentPower;
	}
	public void setCurrentPower(Double currentPower) {
		this.currentPower = currentPower;
	}
	public Double getYieldToday() {
		return yieldToday;
	}
	public void setYieldToday(Double yieldToday) {
		this.yieldToday = yieldToday;
	}
	public Double getYieldYesterday() {
		return yieldYesterday;
	}
	public void setYieldYesterday(Double yieldYesterday) {
		this.yieldYesterday = yieldYesterday;
	}
	public Double getYieldLast7Days() {
		return yieldLast7Days;
	}
	public void setYieldLast7Days(Double yieldLast7Days) {
		this.yieldLast7Days = yieldLast7Days;
	}
	public Double getYieldYTD() {
		return yieldYTD;
	}
	public void setYieldYTD(Double yieldYTD) {
		this.yieldYTD = yieldYTD;
	}
	
}
