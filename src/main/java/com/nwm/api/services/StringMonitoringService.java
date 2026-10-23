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
import org.apache.ibatis.session.SqlSession;
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

	public List<DeviceEntity> getListDeviceBySite(DeviceEntity obj) {
		obj.setHash_id(obj.getHash_id_site());
		DevicesByTypeEntity devices = deviceService.getDevicesBySite(obj);
		return devices.getAll();
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
						Object domain = request.get("domain");
						DeviceEntity deviceDetail = deviceService.getDeviceDetail(device.getId(), domain == null ? null : domain.toString());
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
		List list = obj.getDataDevice();
		int time_interval = 0;
		if (list != null && !list.isEmpty()) {
		    Map<String, Object> device = (Map<String, Object>) list.get(0);
		    time_interval = ((Number) device.get("data_send_time")).intValue();
		}
		
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
		ZonedDateTime siteStartDate;
		
		// 3 = 7days
	    if (time_interval == 3) {
	        siteStartDate = siteEndDate.minusDays(7);
	    } else {
	        siteStartDate = siteEndDate.minusDays(1);
	    }

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

	public boolean saveConfiguration(Map<String, Object> request) {
		return replaceConfiguration(request);
	}

	public boolean updateConfiguration(Map<String, Object> request) {
		return replaceConfiguration(request);
	}

	/**
	 * Replaces the complete site configuration in one transaction. This keeps
	 * add, edit and delete operations consistent when the popup changes several
	 * levels of the configuration at once.
	 */
	@SuppressWarnings("unchecked")
	private boolean replaceConfiguration(Map<String, Object> request) {
		if (!hasSiteHash(request)) {
			return false;
		}

		SqlSession session = beginTransaction();
		if (session == null) {
			return false;
		}

		try {
			deleteConfigurationRows(session, request, false);
			Map<String, Object> thresholds = (Map<String, Object>) request.get("thresholds");
			if (thresholds == null) {
				thresholds = Collections.emptyMap();
			}
			Map<String, Object> thresholdParams = new HashMap<>();
			thresholdParams.put("hash_id_site", request.get("hash_id_site"));
			putThreshold(thresholdParams, thresholds, "normal_threshold");
			putThreshold(thresholdParams, thresholds, "underperforming_threshold");
			putThreshold(thresholdParams, thresholds, "critical_threshold");
			putThreshold(thresholdParams, thresholds, "zero_offline_threshold");
			if (session.insert("StringMonitoring.saveThresholds", thresholdParams) <= 0) {
				throw new IllegalArgumentException("Site was not found");
			}

			for (Map<String, Object> inverter : (List<Map<String, Object>>) request.get("inverters")) {
				int deviceId = ((Number) inverter.get("id")).intValue();

				for (Map<String, Object> mppt : (List<Map<String, Object>>) inverter.get("mppts")) {
					Map<String, Object> mpptParams = new HashMap<>();
					mpptParams.put("hash_id_site", request.get("hash_id_site"));
					mpptParams.put("id_device", deviceId);
					if (session.insert("StringMonitoring.saveInverterMppt", mpptParams) <= 0) {
						throw new IllegalArgumentException("Inverter does not belong to the selected site");
					}
					int mpptId = ((Number) mpptParams.get("id")).intValue();

					Map<String, Object> currentParameter = parameterMap(deviceId, (String) mppt.get("current_parameter"), ParameterType.CURRENT.getValue(), mpptId);
					if (currentParameter != null && session.insert("StringMonitoring.saveMpptParameter", currentParameter) <= 0) {
						throw new IllegalArgumentException("Invalid MPPT current parameter");
					}
					Map<String, Object> voltageParameter = parameterMap(deviceId, (String) mppt.get("voltage_parameter"), ParameterType.VOLTAGE.getValue(), mpptId);
					if (voltageParameter != null && session.insert("StringMonitoring.saveMpptParameter", voltageParameter) <= 0) {
						throw new IllegalArgumentException("Invalid MPPT voltage parameter");
					}
					Map<String, Object> powerParameter = parameterMap(deviceId, (String) mppt.get("active_power_parameter"), ParameterType.POWER.getValue(), mpptId);
					if (powerParameter != null && session.insert("StringMonitoring.saveMpptParameter", powerParameter) <= 0) {
						throw new IllegalArgumentException("Invalid MPPT power parameter");
					}

					for (Map<String, Object> string : (List<Map<String, Object>>) mppt.get("strings")) {
						Map<String, Object> stringParams = new HashMap<>();
						stringParams.put("id_mppt", mpptId);
						if (session.insert("StringMonitoring.saveMpptString", stringParams) <= 0) {
							throw new IllegalStateException("Unable to create string mapping");
						}
						int stringId = ((Number) stringParams.get("id")).intValue();

						Map<String, Object> stringCurrent = parameterMap(deviceId, (String) string.get("current_parameter"), ParameterType.CURRENT.getValue(), stringId);
						if (stringCurrent != null && session.insert("StringMonitoring.saveStringParameter", stringCurrent) <= 0) {
							throw new IllegalArgumentException("Invalid string current parameter");
						}
						Map<String, Object> stringVoltage = parameterMap(deviceId, (String) string.get("voltage_parameter"), ParameterType.VOLTAGE.getValue(), stringId);
						if (stringVoltage != null && session.insert("StringMonitoring.saveStringParameter", stringVoltage) <= 0) {
							throw new IllegalArgumentException("Invalid string voltage parameter");
						}
					}
				}
			}

			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("StringMonitoring.replaceConfiguration", ex);
			return false;
		} finally {
			session.close();
		}
	}

	public boolean deleteConfiguration(Map<String, Object> request) {
		if (!hasSiteHash(request)) {
			return false;
		}

		SqlSession session = beginTransaction();
		if (session == null) {
			return false;
		}
		try {
			deleteConfigurationRows(session, request, true);
			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("StringMonitoring.deleteConfiguration", ex);
			return false;
		} finally {
			session.close();
		}
	}

	private void deleteConfigurationRows(SqlSession session, Map<String, Object> params, boolean deleteThresholds) {
		session.delete("StringMonitoring.deleteStringParameters", params);
		session.delete("StringMonitoring.deleteMpptStrings", params);
		session.delete("StringMonitoring.deleteMpptParameters", params);
		session.delete("StringMonitoring.deleteInverterMppts", params);
		if (deleteThresholds) {
			session.delete("StringMonitoring.deleteThresholds", params);
		}
	}

	private Map<String, Object> parameterMap(int deviceId, String parameter, int parameterType, int ownerId) {
		if (parameter == null || parameter.trim().isEmpty()) {
			return null;
		}

		Map<String, Object> mapping = new HashMap<>();
		mapping.put("owner_id", ownerId);
		mapping.put("id_device", deviceId);
		mapping.put("parameter", parameter);
		mapping.put("parameter_type", parameterType);
		return mapping;
	}

	private void putThreshold(Map<String, Object> target, Map<String, Object> thresholds, String name) {
		target.put(name, thresholds.get(name));
		target.put(name + "_present", thresholds.containsKey(name));
	}

	private boolean hasSiteHash(Map<String, Object> request) {
		Object hash = request == null ? null : request.get("hash_id_site");
		return hash != null && !hash.toString().trim().isEmpty();
	}
}
