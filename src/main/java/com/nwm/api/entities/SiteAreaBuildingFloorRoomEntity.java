/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class SiteAreaBuildingFloorRoomEntity {
	private int id;
	private int id_site;
	private String name;
	private int type;
	private int status;
	private double total_area;
	private String description;
	
	
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
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getTotal_area() {
		return total_area;
	}
	public void setTotal_area(double total_area) {
		this.total_area = total_area;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
