/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class EmployeeRoleMapEntity {
	private int id;
	private int id_employee;
	private int id_role;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the id_employee
	 */
	public int getId_employee() {
		return id_employee;
	}
	/**
	 * @param id_employee the id_employee to set
	 */
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	/**
	 * @return the id_role
	 */
	public int getId_role() {
		return id_role;
	}
	/**
	 * @param id_role the id_role to set
	 */
	public void setId_role(int id_role) {
		this.id_role = id_role;
	}
	
	
}
