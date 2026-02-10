/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class APIAccessLoggingDTO {
	private String endpoint;
	private String method;
	private String security_key;
	
	public APIAccessLoggingDTO(String endpoint, String method, String security_key) {
		this.endpoint = endpoint;
		this.method = method;
		this.security_key = security_key;
	}
	
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
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
