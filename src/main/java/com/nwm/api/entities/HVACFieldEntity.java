/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class HVACFieldEntity {
	private String id;
	private String id_gateway;
	private String value;
	private String ts;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId_gateway() {
		return id_gateway;
	}
	public void setId_gateway(String id_gateway) {
		this.id_gateway = id_gateway;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
}
