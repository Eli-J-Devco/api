/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


public class TriggerFaultCodeEntity{
	
	private int id;
	private int id_error;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_error() {
		return id_error;
	}
	public void setId_error(int id_error) {
		this.id_error = id_error;
	}
	
	public void setData (int a, int b) {
		id = a;
		id_error = b;
	}
	
	
}
