package com.nwm.api.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import com.nwm.api.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;

/** Read-only device list for the String Monitoring popup. */
@Service
public class StringMonitoringService extends DB {
	private enum ParameterType {
		CURRENT(1),
		VOLTAGE(2),
		POWER(3);
		
		private final int value;
		
		ParameterType(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return this.value;
		}
	}
	
	@Autowired
	private DeviceService deviceService;
	@Autowired
	SitesAnalyticsService sitesAnalyticsService;
	@Autowired
	SiteService SiteService;
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
						
						Optional.ofNullable((Double) lastValue.get(powerSlug)).ifPresent(value -> device.setValue(value));
						
						device.getMppts().stream()
						.forEach(mppt -> {
							mppt.getParameters().stream()
							.forEach(parameter -> Optional.ofNullable((Double) lastValue.get(parameter.getSlug())).ifPresent(value -> parameter.setValue(value)));
							
							mppt.getStrings().stream()
							.forEach(string -> {
								string.getParameters().stream()
								.forEach(stringParameter -> {
									Optional.ofNullable((Double) lastValue.get(stringParameter.getSlug())).ifPresent(value -> {
										stringParameter.setValue(value);
										
										if (stringParameter.getParameter_type() == ParameterType.CURRENT.getValue()) {
											mppt.getParameters().stream()
											.filter(mpptParameter -> mpptParameter.getParameter_type() == ParameterType.CURRENT.getValue())
											.findFirst()
											.ifPresent(currentParameter -> {
												Optional.ofNullable((Double) currentParameter.getValue()).ifPresent(median -> {
													if (median > 0) string.setDeviation(value / median);
												});
											});
										}
									});
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

	/**
	 * @description get chart data
	 * @author Minh Le
	 * @since 2026-10-05
	 * @param request { obj }
	 */
	public List getTrendAnalysisChartData(DeviceEntity obj) {
        Optional<SiteEntity> siteOptional = SiteService.getSiteById(obj.getId_site());
		if (!siteOptional.isPresent()) {
			return Collections.emptyList();
		}

		SiteEntity site = siteOptional.get();

		obj.setTimezone_value(site.getTime_zone_value());
		obj.setData_send_time(site.getData_send_time());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		ZonedDateTime now = ZonedDateTime.now();

		ZonedDateTime siteEndDate = now.withZoneSameInstant(ZoneId.of(obj.getTimezone_value()));
		ZonedDateTime siteStartDate = siteEndDate.minusDays(1);

		String startDateStr = siteStartDate.format(formatter);
		String endDateStr = siteEndDate.format(formatter);

		obj.setStart_date(startDateStr);
		obj.setEnd_date(endDateStr);

		List chartData = sitesAnalyticsService.getChartParameterDevice(obj);

		Map<String, Object> chartItem = (Map<String, Object>) chartData.get(0);
		List<Map<String, Object>> data = (List<Map<String, Object>>) chartItem.get("data");

		DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime filterStartDate = LocalDateTime.parse(startDateStr, formatter);
		LocalDateTime filterEndDate = LocalDateTime.parse(endDateStr, formatter);

		data.removeIf(item -> {
			String timeStr = (String) item.get("time");

			if (timeStr == null) {
				return true;
			}

			LocalDateTime time = LocalDateTime.parse(timeStr, dataFormatter);
			return time.isBefore(filterStartDate) || time.isAfter(filterEndDate);
		});

		return data;
	}
}
