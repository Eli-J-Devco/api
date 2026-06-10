/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class DevicePanelEntity extends SortEntity {
	private int id;
	private int id_device;
	private int id_panel;
	private String name;
	private List list_devices;
	
	
	public List getList_devices() {
		return list_devices;
	}
	public void setList_devices(List list_devices) {
		this.list_devices = list_devices;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public int getId_panel() {
		return id_panel;
	}
	public void setId_panel(int id_panel) {
		this.id_panel = id_panel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
