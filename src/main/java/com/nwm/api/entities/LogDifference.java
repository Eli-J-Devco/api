/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class LogDifference {
	private String field;
	private Object oldValue;
	private Object newValue;
	
	public LogDifference() {}
	
	public LogDifference(String field, Object oldValue, Object newValue) {
		super();
		this.field = field;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Object getOldValue() {
		return oldValue;
	}
	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}
	public Object getNewValue() {
		return newValue;
	}
	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}
}
