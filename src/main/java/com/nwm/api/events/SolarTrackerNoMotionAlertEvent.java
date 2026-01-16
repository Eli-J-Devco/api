package com.nwm.api.events;

import org.springframework.context.ApplicationEvent;

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelBaseEntity;

public class SolarTrackerNoMotionAlertEvent extends ApplicationEvent {

	private static final long serialVersionUID = 2385571491438533570L;
	private DeviceEntity device;
	private ModelBaseEntity data;

	public SolarTrackerNoMotionAlertEvent(Object source, DeviceEntity device, ModelBaseEntity data) {
		super(source);
		this.device = device;
		this.data = data;
	}

	public DeviceEntity getDevice() {
		return device;
	}

	public ModelBaseEntity getData() {
		return data;
	}

}
