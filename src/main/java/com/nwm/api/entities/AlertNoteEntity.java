/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


public class AlertNoteEntity extends SortEntity {
	private int id_employee;
	private int id_alert;
	private String note;
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	public int getId_alert() {
		return id_alert;
	}
	public void setId_alert(int id_alert) {
		this.id_alert = id_alert;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
