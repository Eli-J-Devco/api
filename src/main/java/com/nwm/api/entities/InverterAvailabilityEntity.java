/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class InverterAvailabilityEntity {
	private Integer id_device;
	private Double inverter_availability_today;
	private Double inverter_availability_yesterday;
	private Double inverter_availability_yesterday_last_week;
	
	public InverterAvailabilityEntity() {
    }

    public InverterAvailabilityEntity(InverterAvailabilityEntity other) {
        this.id_device = other.id_device;
        this.inverter_availability_today = other.inverter_availability_today;
        this.inverter_availability_yesterday = other.inverter_availability_yesterday;
        this.inverter_availability_yesterday_last_week = other.inverter_availability_yesterday_last_week;
    }

	public Integer getId_device() {
		return id_device;
	}

	public void setId_device(Integer id_device) {
		this.id_device = id_device;
	}

	public Double getInverter_availability_today() {
		return inverter_availability_today;
	}

	public void setInverter_availability_today(Double inverter_availability_today) {
		this.inverter_availability_today = inverter_availability_today;
	}

	public Double getInverter_availability_yesterday() {
		return inverter_availability_yesterday;
	}

	public void setInverter_availability_yesterday(Double inverter_availability_yesterday) {
		this.inverter_availability_yesterday = inverter_availability_yesterday;
	}

	public Double getInverter_availability_yesterday_last_week() {
		return inverter_availability_yesterday_last_week;
	}

	public void setInverter_availability_yesterday_last_week(Double inverter_availability_yesterday_last_week) {
		this.inverter_availability_yesterday_last_week = inverter_availability_yesterday_last_week;
	}
}
