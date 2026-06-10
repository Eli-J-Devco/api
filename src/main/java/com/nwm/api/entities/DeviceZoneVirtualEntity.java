/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class DeviceZoneVirtualEntity{
	
	private int id;
	private int id_zone;
	private String title0l;
	private String title0r;
	private String field_0L;
	private String field_0R;
	private String field_0L_verify;
	private String field_0R_verify;
	private List listDataMaps;
	
	
	
	public List getListDataMaps() {
		return listDataMaps;
	}
	public void setListDataMaps(List listDataMaps) {
		this.listDataMaps = listDataMaps;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_zone() {
		return id_zone;
	}
	public void setId_zone(int id_zone) {
		this.id_zone = id_zone;
	}
	public String getTitle0l() {
		return title0l;
	}
	public void setTitle0l(String title0l) {
		this.title0l = title0l;
	}
	public String getTitle0r() {
		return title0r;
	}
	public void setTitle0r(String title0r) {
		this.title0r = title0r;
	}
	public String getField_0L() {
		return field_0L;
	}
	public void setField_0L(String field_0l) {
		field_0L = field_0l;
	}
	public String getField_0R() {
		return field_0R;
	}
	public void setField_0R(String field_0r) {
		field_0R = field_0r;
	}
	public String getField_0L_verify() {
		return field_0L_verify;
	}
	public void setField_0L_verify(String field_0l_verify) {
		field_0L_verify = field_0l_verify;
	}
	public String getField_0R_verify() {
		return field_0R_verify;
	}
	public void setField_0R_verify(String field_0r_verify) {
		field_0R_verify = field_0r_verify;
	}
	
}
