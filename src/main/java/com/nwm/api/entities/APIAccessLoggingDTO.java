/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class APIAccessLoggingDTO {
	private String route;
	private String method;
	private String security_key;
	
	public APIAccessLoggingDTO(String route, String method, String security_key) {
		this.route = route;
		this.method = method;
		this.security_key = security_key;
	}
	
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getSecurity_key() {
		return security_key;
	}
	public void setSecurity_key(String security_key) {
		this.security_key = security_key;
	}
	
}
