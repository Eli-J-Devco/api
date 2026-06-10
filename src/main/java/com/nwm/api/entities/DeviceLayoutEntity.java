/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;
import java.util.Map;

public class DeviceLayoutEntity {
	
	private int id;
	private String name;
	private String data;
	private int screen_mode;
	
	private int id_template;
	private int id_device_group;
	private int id_categorize_data;
	private List<Map<String, String>> fields;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getScreen_mode() {
		return screen_mode;
	}
	public void setScreen_mode(int screen_mode) {
		this.screen_mode = screen_mode;
	}
	public int getId_template() {
		return id_template;
	}
	public void setId_template(int id_template) {
		this.id_template = id_template;
	}
	public int getId_device_group() {
		return id_device_group;
	}
	public void setId_device_group(int id_device_group) {
		this.id_device_group = id_device_group;
	}
	public int getId_categorize_data() {
		return id_categorize_data;
	}
	public void setId_categorize_data(int id_categorize_data) {
		this.id_categorize_data = id_categorize_data;
	}
	public List<Map<String, String>> getFields() {
		return fields;
	}
	public void setFields(List<Map<String, String>> fields) {
		this.fields = fields;
	}
	
}
