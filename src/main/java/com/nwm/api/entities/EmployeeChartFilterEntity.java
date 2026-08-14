/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmployeeChartFilterEntity {
	private int id;
	private int id_employee;
	private int id_site;
	private String hash_id_site;
	private String params;
	private String name;
	private boolean apply_to_portfolio;
	private boolean favorite;
	private String last_used;
	private boolean addToFavorite;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public String getHash_id_site() {
		return hash_id_site;
	}
	public void setHash_id_site(String hash_id_site) {
		this.hash_id_site = hash_id_site;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isApply_to_portfolio() {
		return apply_to_portfolio;
	}
	public void setApply_to_portfolio(boolean apply_to_portfolio) {
		this.apply_to_portfolio = apply_to_portfolio;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public String getLast_used() {
		return last_used;
	}
	public void setLast_used(String last_used) {
		this.last_used = last_used;
	}
	public boolean isAddToFavorite() {
		return addToFavorite;
	}
	public void setAddToFavorite(boolean addToFavorite) {
		this.addToFavorite = addToFavorite;
	}
	
}
