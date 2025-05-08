/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


public class PortfolioAvailabilityVsPerformanceEntity extends SortEntity {
	private int id_site;
	private String date_range;
	
	
	public int getSite_id() {
		return id_site;
	}
	public void setSite_id(int id_site) {
		this.id_site = id_site;
	}

	public String getDate_range() {
		return date_range;
	}
	public void setDate_range(String date_range) {
		this.date_range = date_range;
	}

}