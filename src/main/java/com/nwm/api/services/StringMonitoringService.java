package com.nwm.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DevicesByTypeEntity;
import com.nwm.api.utils.Constants.DeviceType;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** Read-only device list for the String Monitoring popup. */
@Service
public class StringMonitoringService extends DB {
	@Autowired
	private DeviceService deviceService;

	public List<DeviceEntity> getInverterDevices(DeviceEntity request) {
		DevicesByTypeEntity devices = deviceService.getDevicesBySite(request);
		if (devices == null || devices.getAll() == null) return Collections.emptyList();
		return devices.getAll().stream()
				.filter(device -> DeviceType.fromValue(device.getId_device_type()) == DeviceType.PV_SYSTEM_INVERTER)
				.collect(Collectors.toList());
	}
	
}
