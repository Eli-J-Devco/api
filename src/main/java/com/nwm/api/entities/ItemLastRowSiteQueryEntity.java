/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


public class ItemLastRowSiteQueryEntity {
	private int id;
	private int id_device;
	private String description;
	private String error;
	private String times_ago_unit;
	private int times_ago;
	private String key_indicator;
	
	public String getKey_indicator() {
		return key_indicator;
	}
	public void setKey_indicator(String key_indicator) {
		this.key_indicator = key_indicator;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the id_device
	 */
	public int getId_device() {
		return id_device;
	}
	/**
	 * @param id_device the id_device to set
	 */
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
	/**
	 * @return the times_ago_unit
	 */
	public String getTimes_ago_unit() {
		return times_ago_unit;
	}
	/**
	 * @param times_ago_unit the times_ago_unit to set
	 */
	public void setTimes_ago_unit(String times_ago_unit) {
		this.times_ago_unit = times_ago_unit;
	}
	/**
	 * @return the times_ago
	 */
	public int getTimes_ago() {
		return times_ago;
	}
	/**
	 * @param times_ago the times_ago to set
	 */
	public void setTimes_ago(int times_ago) {
		this.times_ago = times_ago;
	}
	
	
}
