/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ThirdPartyJsonResultEntity {
	private boolean status;
	private String mess;
	private Object data;
	private int total_row;
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMess() {
		return mess;
	}
	public void setMess(String mess) {
		this.mess = mess;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getTotal_row() {
		return total_row;
	}
	public void setTotal_row(int totalRow) {
		this.total_row = totalRow;
	}
}
