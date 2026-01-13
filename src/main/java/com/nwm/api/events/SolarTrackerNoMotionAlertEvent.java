package com.nwm.api.events;

import org.springframework.context.ApplicationEvent;

import com.nwm.api.entities.DeviceEntity;

public class SolarTrackerNoMotionAlertEvent extends ApplicationEvent {

	private static final long serialVersionUID = 2385571491438533570L;
	private DeviceEntity event;

	public SolarTrackerNoMotionAlertEvent(Object source, DeviceEntity event) {
		super(source);
		this.setEvent(event);
	}

	public DeviceEntity getEvent() {
		return event;
	}

	private void setEvent(DeviceEntity event) {
		this.event = event;
	}

}
