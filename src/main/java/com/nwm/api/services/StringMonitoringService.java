package com.nwm.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DevicesByTypeEntity;

/** Read-only device list for the String Monitoring popup. */
@Service
public class StringMonitoringService extends DB {
	@Autowired
	private DeviceService deviceService;

	public List<DeviceEntity> getInverterDevices(DeviceEntity request) {
		DevicesByTypeEntity device = deviceService.getDevicesBySite(request);
		return device.getInverter();
	}
	
}
