/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class UploadScadaEntity {
	private int id_channel;
	private int id_device;
	private Object datas;
	private String datetime;
	public int getId_channel() {
		return id_channel;
	}
	public void setId_channel(int id_channel) {
		this.id_channel = id_channel;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public Object getDatas() {
		return datas;
	}
	public void setDatas(Object datas) {
		this.datas = datas;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	
	
	
	
	
	
}
