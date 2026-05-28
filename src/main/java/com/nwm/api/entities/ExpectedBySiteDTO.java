/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class ExpectedBySiteDTO {
	private int data_send_time;
	private String filterBy;
	private String start_date;
	private String end_date;
	private List<DeviceEntity> POAs;
	private List<DeviceEntity> rPOAs;
	private List<DeviceEntity> panelTemps;
	private List<DeviceEntity> ambientTemps;
	private String ids_device_poa = null;
	private String ids_device_rpoa = null;
	private String ids_device_panel_temp = null;
	private String ids_device_ambient_temp = null;
	
	public int getData_send_time() {
		return data_send_time;
	}
	public void setData_send_time(int data_send_time) {
		this.data_send_time = data_send_time;
	}
	public String getFilterBy() {
		return filterBy;
	}
	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public List<DeviceEntity> getPOAs() {
		return POAs;
	}
	public void setPOAs(List<DeviceEntity> pOAs) {
		POAs = pOAs;
	}
	public List<DeviceEntity> getrPOAs() {
		return rPOAs;
	}
	public void setrPOAs(List<DeviceEntity> rPOAs) {
		this.rPOAs = rPOAs;
	}
	public List<DeviceEntity> getPanelTemps() {
		return panelTemps;
	}
	public void setPanelTemps(List<DeviceEntity> panelTemps) {
		this.panelTemps = panelTemps;
	}
	public List<DeviceEntity> getAmbientTemps() {
		return ambientTemps;
	}
	public void setAmbientTemps(List<DeviceEntity> ambientTemps) {
		this.ambientTemps = ambientTemps;
	}
	public String getIds_device_poa() {
		return ids_device_poa;
	}
	public void setIds_device_poa(String ids_device_poa) {
		this.ids_device_poa = ids_device_poa;
	}
	public String getIds_device_rpoa() {
		return ids_device_rpoa;
	}
	public void setIds_device_rpoa(String ids_device_rpoa) {
		this.ids_device_rpoa = ids_device_rpoa;
	}
	public String getIds_device_panel_temp() {
		return ids_device_panel_temp;
	}
	public void setIds_device_panel_temp(String ids_device_panel_temp) {
		this.ids_device_panel_temp = ids_device_panel_temp;
	}
	public String getIds_device_ambient_temp() {
		return ids_device_ambient_temp;
	}
	public void setIds_device_ambient_temp(String ids_device_ambient_temp) {
		this.ids_device_ambient_temp = ids_device_ambient_temp;
	}
}
