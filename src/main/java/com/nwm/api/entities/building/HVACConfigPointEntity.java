/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities.building;

public class HVACConfigPointEntity {
	private String id;
	private String config;
	private String id_gateway;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public String getId_gateway() {
		return id_gateway;
	}
	public void setId_gateway(String id_gateway) {
		this.id_gateway = id_gateway;
	}
	
}
