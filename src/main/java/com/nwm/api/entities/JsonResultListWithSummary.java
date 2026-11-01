/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class JsonResultListWithSummary<K,V> {
	private boolean status;
	private String mess;
	private List<K> data;
	private V summary;

	public JsonResultListWithSummary(boolean status, String mess, List<K> data, V summary) {
		this.status = status;
		this.mess = mess;
		this.data = data;
		this.summary = summary;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}

	public List<K> getData() {
		return data;
	}

	public void setData(List<K> data) {
		this.data = data;
	}

	public V getSummary() {
		return summary;
	}

	public void setSummary(V summary) {
		this.summary = summary;
	}
}
