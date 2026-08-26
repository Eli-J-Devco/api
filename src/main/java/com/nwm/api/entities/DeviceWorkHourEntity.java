/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class DeviceWorkHourEntity {
	private Integer id_device;
    private Integer work_hour_today;
    private Integer work_hour_yesterday;
    private Integer work_hour_last_week;
    
    public DeviceWorkHourEntity() {
    }

    public DeviceWorkHourEntity(DeviceWorkHourEntity other) {
        this.id_device = other.id_device;
        this.work_hour_today = other.work_hour_today;
        this.work_hour_yesterday = other.work_hour_yesterday;
        this.work_hour_last_week = other.work_hour_last_week;
    }

	public Integer getId_device() {
		return id_device;
	}

	public void setId_device(Integer id_device) {
		this.id_device = id_device;
	}

	public Integer getWork_hour_today() {
		return work_hour_today;
	}

	public void setWork_hour_today(Integer work_hour_today) {
		this.work_hour_today = work_hour_today;
	}

	public Integer getWork_hour_yesterday() {
		return work_hour_yesterday;
	}

	public void setWork_hour_yesterday(Integer work_hour_yesterday) {
		this.work_hour_yesterday = work_hour_yesterday;
	}

	public Integer getWork_hour_last_week() {
		return work_hour_last_week;
	}

	public void setWork_hour_last_week(Integer work_hour_last_week) {
		this.work_hour_last_week = work_hour_last_week;
	}
    
    
}
