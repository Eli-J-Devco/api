package com.nwm.api.events;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelBaseEntity;

public class LowProductionAlertEvent extends ApplicationEvent {

	private static final long serialVersionUID = 2385571491438533570L;
	private DeviceEntity device;
	private ModelBaseEntity data;
	private List<DeviceEntity> devicesBySite;

	public LowProductionAlertEvent(Object source, DeviceEntity device, ModelBaseEntity data, List<DeviceEntity> devicesBySite) {
		super(source);
		this.device = device;
		this.data = data;
		this.devicesBySite = devicesBySite;
	}

	public DeviceEntity getDevice() {
		return device;
	}
	
	public ModelBaseEntity getData() {
		return data;
	}

	public List<DeviceEntity> getDevicesBySite() {
		return devicesBySite;
	}


}
