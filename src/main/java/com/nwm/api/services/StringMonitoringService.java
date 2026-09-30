package com.nwm.api.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DevicesByTypeEntity;

/** Read-only device list for the String Monitoring popup. */
@Service
public class StringMonitoringService extends DB {
	@Autowired DeviceService deviceService;
	
	public int resolveSiteId(Map<String, Object> request) {
		if (request == null) {
			return 0;
		}
		Object value = request.get("id_site");
		if (value == null) value = request.get("id");
		try {
			if (value != null) {
				return Integer.parseInt(value.toString());
			}
		} catch (NumberFormatException ex) {
			log.error("StringMonitoring.resolveSiteId", ex);
		}

		Object hash = request.get("hash_id_site");
		if (hash == null) hash = request.get("hash_id");
		if (hash == null || hash.toString().trim().isEmpty()) {
			return 0;
		}

		try {
			Map<String, Object> params = new java.util.HashMap<String, Object>();
			params.put("hash_id_site", hash.toString().trim());
			Object result = queryForObject("StringMonitoring.getSiteIdByHash", params);
			return result == null ? 0 : Integer.parseInt(result.toString());
		} catch (Exception ex) {
			log.error("StringMonitoring.resolveSiteId", ex);
			return 0;
		}
	}

	public List<DeviceEntity> getInverterDevices(Map<String, Object> request) {
		DevicesByTypeEntity device = deviceService.getDevicesBySite(request);
		return device.getInverter();
	}
}
