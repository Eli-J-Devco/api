/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;
import java.util.List;

public class EmailTrackingEntity {
	private int id_site;
	private String type;
	private String start_date;
	private String end_date;
	private String id_filter;
	private List dataSents;
	private List dataQueues;
	private String offset_timezone;
	
	
	public String getOffset_timezone() {
		return offset_timezone;
	}
	public void setOffset_timezone(String offset_timezone) {
		this.offset_timezone = offset_timezone;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getId_filter() {
		return id_filter;
	}
	public void setId_filter(String id_filter) {
		this.id_filter = id_filter;
	}
	public List getDataSents() {
		return dataSents;
	}
	public void setDataSents(List dataSents) {
		this.dataSents = dataSents;
	}
	public List getDataQueues() {
		return dataQueues;
	}
	public void setDataQueues(List dataQueues) {
		this.dataQueues = dataQueues;
	}
	
	
	
}
