package com.nwm.api.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DevicesByTypeEntity;
import com.nwm.api.entities.StringMonitoringConfiguredDeviceResponse;

/** Read-only device list for the String Monitoring popup. */
@Service
public class StringMonitoringService extends DB {
	@Autowired
	private DeviceService deviceService;

	@Autowired
	SitesAnalyticsService sitesAnalyticsService;

	public List<DeviceEntity> getInverterDevices(DeviceEntity request) {
		DevicesByTypeEntity device = deviceService.getDevicesBySite(request);
		return device.getInverter();
	}

	/**
	 * @description get configured devices
	 * @author Hung.Bui
	 * @since 2026-09-30
	 * @param request { hash_id_site }
	 */
	public List<StringMonitoringConfiguredDeviceResponse> getConfiguredDevicesBySite(Map<String, Object> request) {
		try {
			return Optional.ofNullable((List<StringMonitoringConfiguredDeviceResponse>) queryForList("StringMonitoring.getConfiguredDevicesBySite", request))
					.orElse(new ArrayList<>());
		} catch(Exception ex) {
			log.error("StringMonitoring.getConfiguredDevicesBySite", ex);
			return new ArrayList<>();
		}
	}

	public List getTrendAnalysisChartData(DeviceEntity obj) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

		ZonedDateTime now = ZonedDateTime.now();

		ZonedDateTime siteEndDate = now.withZoneSameInstant(ZoneId.of(obj.getTimezone_value()));
		ZonedDateTime siteStartDate = siteEndDate.minusDays(1);

		String startDateStr = siteStartDate.format(formatter);
		String endDateStr = siteEndDate.format(formatter);

		obj.setStart_date(startDateStr);
		obj.setEnd_date(endDateStr);

		return sitesAnalyticsService.getChartParameterDevice(obj);
	}
}
