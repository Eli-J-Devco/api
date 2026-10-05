/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class CurrentStatusPathForwardUpdateEntity {
	private Integer id_device;
    private String date;
    private Integer id_current_status;
    private Integer id_path_forward_update;
    private String current_status;
    private String path_forward_update;
    
	public Integer getId_device() {
		return id_device;
	}
	public void setId_device(Integer id_device) {
		this.id_device = id_device;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getId_current_status() {
		return id_current_status;
	}
	public void setId_current_status(Integer id_current_status) {
		this.id_current_status = id_current_status;
	}
	public Integer getId_path_forward_update() {
		return id_path_forward_update;
	}
	public void setId_path_forward_update(Integer id_path_forward_update) {
		this.id_path_forward_update = id_path_forward_update;
	}
	public String getCurrent_status() {
		return current_status;
	}
	public void setCurrent_status(String current_status) {
		this.current_status = current_status;
	}
	public String getPath_forward_update() {
		return path_forward_update;
	}
	public void setPath_forward_update(String path_forward_update) {
		this.path_forward_update = path_forward_update;
	}
}
