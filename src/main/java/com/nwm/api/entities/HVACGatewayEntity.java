/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class HVACGatewayEntity {
	private String id_gateway;
	private Integer id_site;
	
	public String getId_gateway() {
		return id_gateway;
	}
	public void setId_gateway(String id_gateway) {
		this.id_gateway = id_gateway;
	}
	public Integer getId_site() {
		return id_site;
	}
	public void setId_site(Integer id_site) {
		this.id_site = id_site;
	}
	
}
