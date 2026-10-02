package com.nwm.api.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceParameterEntity;
import com.nwm.api.entities.DevicesByTypeEntity;
import com.nwm.api.entities.StringMonitoringConfiguredDeviceResponse;

/** Read-only device list for the String Monitoring popup. */
@Service
public class StringMonitoringService extends DB {
	@Autowired
	private DeviceService deviceService;
	@Autowired
	SitesAnalyticsService sitesAnalyticsService;
	@Autowired
	@Qualifier("deviceDataExecutor")
	Executor executor;

	public List<DeviceEntity> getListDeviceBySite(DeviceEntity request) {
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
			List<StringMonitoringConfiguredDeviceResponse> devices = Optional.ofNullable((List<StringMonitoringConfiguredDeviceResponse>) queryForList("StringMonitoring.getConfiguredDevicesBySite", request))
					.orElse(new ArrayList<>());
			
			List<CompletableFuture<StringMonitoringConfiguredDeviceResponse>> futures = devices.stream()
					.map(device -> CompletableFuture.supplyAsync(() -> {
						DeviceEntity deviceDetail = deviceService.getDeviceDetail(device.getId(), request.get("domain").toString());
						String powerSlug = deviceDetail.getParameters().stream()
								.filter(item -> item.isIs_active_power())
								.findFirst()
								.orElse(new DeviceParameterEntity())
								.getSlug();
						
						Map<String, Object> deviceMap = new HashMap<>();
						deviceMap.put("id_device", device.getId());
						deviceMap.put("view_tablename", device.getDatatablename());
						
						Map<String, Object> lastValue = deviceService.getLastValue(deviceMap);
						
						Optional.ofNullable(lastValue.get(powerSlug)).ifPresent(value -> device.setValue((Double) value));
						
						device.getMppts().stream()
						.forEach(mppt -> {
							mppt.getParameters().stream()
							.forEach(parameter -> {
								Optional.ofNullable(lastValue.get(parameter.getSlug())).ifPresent(value -> parameter.setValue((Double) value));
							});
							
							mppt.getStrings().stream()
							.forEach(string -> {
								string.getParameters().stream()
								.forEach(parameter -> {
									Optional.ofNullable(lastValue.get(parameter.getSlug())).ifPresent(value -> parameter.setValue((Double) value));
								});
							});
						});
						
						return device;
					}, executor))
					.collect(Collectors.toList());
			
			return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
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
