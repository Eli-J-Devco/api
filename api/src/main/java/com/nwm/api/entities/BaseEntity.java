package com.nwm.api.entities;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
public class BaseEntity {
	private int totalRecord;

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
}
