/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeValueDTO {
	private LocalDateTime time;
	private Double value;
	
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
}
