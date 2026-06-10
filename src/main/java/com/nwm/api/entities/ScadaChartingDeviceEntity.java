/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;
import java.util.Map;

public class ScadaChartingDeviceEntity {
	private int id;
	private int id_device_group;
	private int id_device_type;
	private int hidden;
	private int is_hidden;
	private int checked;
	private String name;
	private String group_name;
	private String table_name;
	private String datatablename;
	private String view_tablename;
	private String device_type_name;
	private String parametersJSON;
	private List<Map<String, Object>> parameters;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_device_group() {
		return id_device_group;
	}
	public void setId_device_group(int id_device_group) {
		this.id_device_group = id_device_group;
	}
	public int getId_device_type() {
		return id_device_type;
	}
	public void setId_device_type(int id_device_type) {
		this.id_device_type = id_device_type;
	}
	public int getHidden() {
		return hidden;
	}
	public void setHidden(int hidden) {
		this.hidden = hidden;
	}
	public int getIs_hidden() {
		return is_hidden;
	}
	public void setIs_hidden(int is_hidden) {
		this.is_hidden = is_hidden;
	}
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getView_tablename() {
		return view_tablename;
	}
	public void setView_tablename(String view_tablename) {
		this.view_tablename = view_tablename;
	}
	public String getDevice_type_name() {
		return device_type_name;
	}
	public void setDevice_type_name(String device_type_name) {
		this.device_type_name = device_type_name;
	}
	public String getParametersJSON() {
		return parametersJSON;
	}
	public void setParametersJSON(String parametersJSON) {
		this.parametersJSON = parametersJSON;
	}
	public List<Map<String, Object>> getParameters() {
		return parameters;
	}
	public void setParameters(List<Map<String, Object>> parameters) {
		this.parameters = parameters;
	}
	
}
