/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.CronJobSchedulerEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CronJobDetectDeviceFaultStatusService extends DB {

	private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobDetectDeviceFaultStatusService");
	private final AtomicBoolean isRunning = new AtomicBoolean(false);
	public enum MECHANISM {
		BIT_ON_OFF,
		ENUM_VALUES,
		VALUE_ON_OFF
	}
	private static final int DATALOGER_ID_DEVICE_TYPE = 5;
	private static final int TIME_FAULT_THRESHOLD_MINUTES = 120;
	private static final List<Integer> LIST_FAULT_ERROR_LEVEL = Arrays.asList(2, 11, 12, 13);
	// List fault code model names
	private static final List<String> LIST_FAULT_TABLE_NAME = Arrays.asList("model_SMP4_DP", "model_SMP4_DPV1", "model_MVPS_HUAWEI", 
		"model_OrionMX_Automation_Platform", "model_protection_relay", "model_protection_relay_v1", "model_SUN2000330KTLH1", 
		"model_SUNGROW_SG6250HV_MV_V1", "model_sungrow_pv_24h_scb", "model_sungrow_sh6250hv_mv", "model_advanced_energy_solaron", 
		"model_chint_solectria_inverter_class9725", "model_PVH_Tbox", "model_phoenix_contact_quint_ups", "model_Kehua_SPI50_60K_inverter", 
		"model_xgi150", "model_satcon_pvs357_inverter", "model_abb_trio_class6210", "model_xantrex_gt100_250_500", "model_xantrex_gt500", 
		"model_ATI_Tracker_Motor", "model_pv_powered_35_50_260_500kw_inverter", "model_ivt_solaron_ext", "model_satcon_powergate_225_inverter", 
		"model_sev_sg110cx", "model_sma_shp7510", "model_smartlogger3000", "model_sma_stp_25_50_us_50", "model_sma_core", "model_huawei_sun2000v1", 
		"model_ADAM6050_Transformer_Specific", "model_PVH_Master", "model_xantrex_gt500e");
	// List fault code models device groups
	public enum FAULT_CODE_MODEL_GROUP {
		ATI_TRACKER_MOTOR(173),
		ADVANCED_ENERGY_SOLARON(6),
		CHINT_SOLECTRIA_INVERTER_CLASS_9725(9),
		HUAWEI_SUN2000_V1(137),
		IVT_SOLARON_EXT(3),
		KEHUA_SPI_50_60K_INVERTER(128),
		PVH_TBOX(172),
		PV_POWERED_3550_260_500_KW_INVERTER(20),
		PHOENIX_CONTACT_QUINT_UPS(73),
		SATCON_POWERGATE_225_INVERTER(41),
		SATCON_PVS_357_INVERTER(12),
		SMA_SHP_75_10(116),
		HUAWEI_SMARTLOGGER_3000(113),
		YASKAWA_SOLECTRIA_XGI_1500(99),
		ABB_TRIO_CLASS_6210(17),
		SEV_SG110CX(60),
		SMA_CORE(119),
		SMA_STP_25_50_US_50(109),
		XANTREX_GT100_250_500(23),
		XANTREX_GT500E(57),
		PVH_MASTER(170),
		ADAM_6050_TRANSFORMER_SPECIFIC(174),
		SMP4_DP(147),
		SMP4_DP_V1(151),
		MVPS_HUAWEI(157),
		ORIONMX_AUTOMATION_PLATFORM(156),
		PROTECTION_RELAY(146),
		PROTECTION_RELAY_V1(150),
		SUN2000_330KTL_H1(152),
		SUNGROW_SG6250HV_MV_V1(167),
		SUNGROW_PV_24H_SCB(145),
		SUNGROW_SH6250HV_MV(144);
		private int groupId;
		FAULT_CODE_MODEL_GROUP(int groupId){
			this.groupId = groupId;
		}
		public int getGroupId(){
			return this.groupId;
		}
		
	}
	

	

	@Value ("${cron.device.alert.fault.maxthread:10}")
	private int MAX_SITE_THREADS;
	@Value ("${cron.device.alert.fault.time.threshold.addition:20}")
	private int TIME_QUERY_FAULT_THRESHOLD_MINUTES_ADDITION;


	private ThreadPoolExecutor siteExecutor;

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private ThreadPoolExecutor createSiteExecutor() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
				MAX_SITE_THREADS, MAX_SITE_THREADS,
				60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>());
		executor.allowCoreThreadTimeOut(true);
		return executor;
	}

	@Value("${server1.name}")
	private String serverName1;

	@Value("${server2.name}")
	private String serverName2;

	@Value("${server1.run_on_id}")
	private List<Integer> server1RunOnId;

	@Value("${server2.run_on_id}")
	private List<Integer> server2RunOnId;

	@Value("${server.local.run_on_id}")
	private List<Integer> serverLocalRunOnId;

	private final Map<String, List<Integer>> hostnameToServerIds = new HashMap<>();

	private Map<Integer, List<FaultCodeField>> deviceFaultCodesMap;

	private Instant nowInstant;

	@PostConstruct
	public void init() {
		deviceFaultCodesMap = deviceFaultCodesConfigure();
		siteExecutor = createSiteExecutor();
		String localhost = Lib.getPrivateIP();
		hostnameToServerIds.put(serverName1, server1RunOnId);
		hostnameToServerIds.put(serverName2, server2RunOnId);

		if (localhost != null && !localhost.equals(serverName1) && !localhost.equals(serverName2)) {
			hostnameToServerIds.put(localhost, serverLocalRunOnId);
		}
	}

	/**
	 * Execute the fault detection cron job.
	 * This method checks if the job is already running, retrieves the list of sites and devices,
	 * and processes them accordingly.
	 */
	public void execute() {
		if (!isRunning.compareAndSet(false, true)) {
			log.info("Fault check is already running. Skipping this execution.");
			return;
		}
		nowInstant = Instant.from(LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC));
		try {
			String hostname = Lib.getPrivateIP();
			List<Integer> serverIds = hostnameToServerIds.get(hostname);
			if (serverIds == null || serverIds.isEmpty()) {
				return;
			}
            
			Map<String, Object> params = new HashMap<>();
      ObjectMapper objectMapper = new ObjectMapper();
			params.put("serverIds", serverIds);
			params.put("error_level_list", LIST_FAULT_ERROR_LEVEL);
      log.info("Process sites query params: "+ objectMapper.writeValueAsString(params));
			List<?> listSites = queryForList("CronJobDetectDeviceStatus.getListSiteByServer", params);
			if (listSites == null || listSites.isEmpty()) {
				return;
			}
			List<Integer> siteIds = listSites.stream().map(site -> (SiteEntity) site)
			.map(s -> s.getId())
			.collect(Collectors.toList());
			// Log site ids and time zone
			List<HashMap<String, Object>> lstSiteTz = listSites.stream().map(site -> (SiteEntity) site)
			.map(s -> {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", s.getId());
				map.put("tz", s.getTime_zone_value());
				return map;
			})
			.collect(Collectors.toList());

			String stringSites = objectMapper.writeValueAsString(lstSiteTz);
			log.info("Process sites: "+ stringSites);

			params.put("siteIds", siteIds);
			params.put("time_execute", formatter.withZone(ZoneOffset.UTC).format(nowInstant));
			params.put("table_name_list", LIST_FAULT_TABLE_NAME);
			// Get list of devices by site IDs
			List<?> listDevicesQuery = queryForList("CronJobDetectDeviceStatus.getListDeviceBySiteIds", params);

			List<DeviceEntity> listDevices = listDevicesQuery.stream().map(device -> (DeviceEntity) device)
			.collect(Collectors.toList());

			// Map -> siteId -> datalogerid -> list of devices
			Map<Integer, Map<String, List<DeviceEntity>>> devicesBySiteIds = new HashMap<>();
			listDevices.stream()
			.forEach(device -> {
				if(device == null) {
					return;
				}
				devicesBySiteIds
				.computeIfAbsent(device.getId_site(), k -> new HashMap<>())
				.computeIfAbsent(device.getSerial_number(), k -> new ArrayList<>())
				.add(device);
			});

			List<Future<?>> futures = new ArrayList<>();

			// debug with single site
			// processForEachSite(devicesBySiteIds.get(126), nowInstant);
			for(int siteId : devicesBySiteIds.keySet()) {
				futures.add(siteExecutor.submit(() -> {
					Thread t = Thread.currentThread();
					String oldName = t.getName();
					t.setName(oldName + "-detect-fault-status-" + siteId);
					processForEachSite(devicesBySiteIds.get(siteId), nowInstant);
				}));
			}

			for (Future<?> f : futures) {
				try {
					f.get();
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					log.error("Site task interrupted: " + ie.getMessage(), ie);
					break;
				} catch (ExecutionException ee) {
					log.error("Error executing site task: " + ee.getMessage(), ee);
				}catch(Exception e) {
					log.error("Unexpected error executing site task: " + e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			log.error("Error in runFaultCheck: " + e.getMessage());
			e.printStackTrace();
		} finally {
			isRunning.set(false);
		}
	}

	/**
	 * @description process for each site
	 * @since 2026-09-23
	 * @param {Map<Integer, Map<String, List<DeviceEntity>>>} devicesBySiteIds
	 */
	private void processForEachSite(Map<String, List<DeviceEntity>> devices, Instant jobStartInstant) {
		try {
			if(devices == null){
				return;
			}

			for (String datalogerSerial : devices.keySet()) {
				// Assuming the first device represents the datalogger
				DeviceEntity dataloger = devices.get(datalogerSerial).stream()
						.filter(d -> d.getId_device_type() == DATALOGER_ID_DEVICE_TYPE)
						.findFirst().orElse(null); 
				if(dataloger != null) {
					if(dataloger.getLast_updated() != null) {
						// Perform any necessary processing for the datalogger here
						LocalDateTime localDateTime = LocalDateTime.parse(dataloger.getLast_updated(), formatter.withZone(ZoneOffset.UTC));
						Instant lastUpdated = localDateTime.toInstant(ZoneOffset.UTC);
            int alertThreshold = dataloger.getCf_alert_threshold() > 0 ? dataloger.getCf_alert_threshold() : TIME_FAULT_THRESHOLD_MINUTES;
						boolean isNoComm = lastUpdated.isBefore(jobStartInstant.minus(alertThreshold, ChronoUnit.MINUTES));
						if (isNoComm) {
							log.info("Dataloger is No Communication, so skip all devices for this dataloger: " + dataloger.getId_device());
							return;
						}
					}
				}
					
				List<DeviceEntity> devicesForDataloger = devices.get(datalogerSerial);
				if(devicesForDataloger == null || devicesForDataloger.isEmpty()) {
					continue;
				}
				// Process devices for this dataloger
				checkFaultStatusByDevices(devicesForDataloger);
			}
		} catch (Exception e) {
			log.error("Error: " + e.getMessage(), e);
		}
	}

	/**
	 * @description process fault status detection for a list of devices
	 * @param {List<DeviceEntity>} devices
	 */
	private void checkFaultStatusByDevices(List<DeviceEntity> devices) {
		if (devices == null || devices.isEmpty()) {
			return;
		}
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
    //         .withZone(ZoneId.systemDefault());
		Map<String, Object> params = new HashMap<>();
		params.put("time_execute", formatter.withZone(ZoneOffset.UTC).format(nowInstant));
		for (DeviceEntity device : devices) {
			try {
				// If the site has a threshold configured for the alert, use the configured value; otherwise, use the default value.
				params.put("time_query_fault_threshold_minutes", TIME_QUERY_FAULT_THRESHOLD_MINUTES_ADDITION + TIME_FAULT_THRESHOLD_MINUTES);
				params.put("time_fault_threshold_minutes", TIME_FAULT_THRESHOLD_MINUTES);
				int cfAlertThreshold = device.getCf_alert_threshold();
				if (cfAlertThreshold > 0) {
						params.put("time_query_fault_threshold_minutes", TIME_QUERY_FAULT_THRESHOLD_MINUTES_ADDITION + cfAlertThreshold);
						params.put("time_fault_threshold_minutes", cfAlertThreshold);
				}

				params.put("id_device", device.getId());
				params.put("id_device_type", device.getId_device_type());
				params.put("data_table_name", device.getDatatablename());

				// check device last data status
        AlertEntity lastDataStatus = (AlertEntity) queryForObject("CronJobDetectDeviceStatus.getDeviceLastDataStatus", params);
				// if the last data status indicates a slow response, create an alert for the device with the last data time as the start date
				if (lastDataStatus != null && lastDataStatus.getIs_slow_response() == 1) {
					log.info("Device id: " + device.getId() + " is slow response, last data time: " + lastDataStatus.getStart_date() +", skip check fault status for this device.");
					continue;
				}
				List<FaultCodeField> faultCodes = deviceFaultCodesMap.get(device.getId_device_group());
				if (faultCodes == null) {
					log.warn("No fault codes configured for device: " + device.getId_device() + ", device group: " + device.getId_device_group() + ", skip check fault status for this device.");
					continue;
				}
				List<FaultCodeQueryParam> bitMapFaultCodes = new ArrayList<FaultCodeQueryParam>();
				List<FaultCodeQueryParam> enumValueFaultCodes = new ArrayList<FaultCodeQueryParam>();
				List<FaultCodeQueryParam> valueOnOffFaultCodes = new ArrayList<FaultCodeQueryParam>();
				for(FaultCodeField faultCodeField : faultCodes) {
					if(faultCodeField == null || faultCodeField.getFieldName() == null || 
						faultCodeField.getFieldName().isEmpty()  || faultCodeField.getMechanism() == null) {
						log.warn("Fault code field is not configured properly for device: " + device.getId_device() + ", device group: " + device.getId_device_group());
						continue;
					}
					// handle BIT_ON_OFF mechanism
					if(faultCodeField.getMechanism() == MECHANISM.BIT_ON_OFF) {
						log.debug("Fault code mechanism is BIT_ON_OFF for device: " + device.getId_device() + ", device group: " + device.getId_device_group() + ", field: " + faultCodeField.getFieldName());
						// fieldname, mechanism, fault_code_mapping
						for(Map.Entry<Integer, Integer> entry : faultCodeField.getFaultCodesMapping().entrySet()) {
							FaultCodeQueryParam subParam = new FaultCodeQueryParam();
							subParam.setFieldName(faultCodeField.getFieldName());
							subParam.setBitIndex(entry.getKey());
							subParam.setErrorId(entry.getValue());
							subParam.setAlias(faultCodeField.getFieldName() + "_" + entry.getKey());
							subParam.setErrorAlias(faultCodeField.getFieldName() + "_" + entry.getKey() + "_error_id");
							bitMapFaultCodes.add(subParam);
						}
					}
					// handle ENUM_VALUES mechanism
					if(faultCodeField.getMechanism() == MECHANISM.ENUM_VALUES) {
						log.debug("Fault code mechanism is ENUM_VALUES for device: " + device.getId_device() + ", device group: " + device.getId_device_group() + ", field: " + faultCodeField.getFieldName());
						for(Map.Entry<Integer, Integer> entry : faultCodeField.getFaultCodesMapping().entrySet()) {
							FaultCodeQueryParam subParam = new FaultCodeQueryParam();
							subParam.setFieldName(faultCodeField.getFieldName());
							subParam.setBitIndex(entry.getKey());
							subParam.setErrorId(entry.getValue());
							subParam.setAlias(faultCodeField.getFieldName() + "_" + entry.getKey());
							subParam.setErrorAlias(faultCodeField.getFieldName() + "_" + entry.getKey() + "_error_id");
							enumValueFaultCodes.add(subParam);
						}
					}
					// handle VALUE_ON_OFF mechanism
					if(faultCodeField.getMechanism() == MECHANISM.VALUE_ON_OFF) {
						log.debug("Fault code mechanism is VALUE_ON_OFF for device: " + device.getId_device() + ", device group: " + device.getId_device_group() + ", field: " + faultCodeField.getFieldName());
						for(Map.Entry<Integer, Integer> entry : faultCodeField.getFaultCodesMapping().entrySet()) {
							FaultCodeQueryParam subParam = new FaultCodeQueryParam();
							subParam.setFieldName(faultCodeField.getFieldName());
							subParam.setBitIndex(entry.getKey());
							subParam.setErrorId(entry.getValue());
							subParam.setAlias(faultCodeField.getFieldName() + "_" + entry.getKey());
							subParam.setErrorAlias(faultCodeField.getFieldName() + "_" + entry.getKey() + "_error_id");
							valueOnOffFaultCodes.add(subParam);
						}
					}
				}
				params.put("bitmap_fault_codes", bitMapFaultCodes);
				params.put("enum_value_fault_codes", enumValueFaultCodes);
				params.put("value_on_off_fault_codes", valueOnOffFaultCodes);

				List<FaultCodeQueryParam> allFaultCodes = new ArrayList<>();
				allFaultCodes.addAll(bitMapFaultCodes);
				allFaultCodes.addAll(enumValueFaultCodes);
				allFaultCodes.addAll(valueOnOffFaultCodes);
				params.put("all_fault_codes", allFaultCodes);
				// Query the database to detect fault status by device
				List<Map<String, Object>> rawItems = (List<Map<String, Object>>) queryForList("CronJobDetectDeviceStatus.getFaultStatusRawDataByDevice", params);
				// If no fault is not detected, skip this device
				if (rawItems == null || rawItems.isEmpty()) {
					log.info("No fault is not detected, skip for device id: " + device.getId() + ", data table: " + device.getDatatablename());
					continue;
				}
				List<DeviceFaultCodeRecord> records = new ArrayList<>();
				for(FaultCodeField faultCodeField : faultCodes) {
					faultCodeField.getFaultCodesMapping().forEach((key, value) -> {
						for(Map<String, Object> rawItem : rawItems) {
							try{
								String rawTime = rawItem.get("time").toString();
								LocalDateTime localDateTime = LocalDateTime.parse(rawTime.substring(0, rawTime.indexOf(".")), formatter.withZone(ZoneOffset.UTC));
								DeviceFaultCodeRecord record = new DeviceFaultCodeRecord();
								record.setId_device(device.getId());
								record.setTime(localDateTime.format(formatter));
								record.setField(faultCodeField.getFieldName());
								record.setIndex(key);
								record.setValue((Integer) rawItem.get(faultCodeField.getFieldName()+"_"+key));
								record.setPre_value((Integer) rawItem.get("prev_"+faultCodeField.getFieldName()+"_"+key));
								record.setError_id(value);
								records.add(record);
							}catch(Exception e){
								log.error("Error processing raw item: " + rawItem, e);
								continue;
							}
						}
					});
				}
				List<AlertEntity> finalResults = new ArrayList<>();
				Map<String, List<DeviceFaultCodeRecord>> groupedByFieldAndBit = records.stream()
            .collect(Collectors.groupingBy(r -> r.getField() + "_" + r.getIndex()));
				groupedByFieldAndBit.forEach((key, group) -> {
					log.info("Grouped key: " + key + ", records count: " + group.size());
					List<DeviceFaultCodeRecord> currentBatch = new ArrayList<>();
					// Initialize with a value that won't match any real fault code	
					int previousFaultCode = -1; 
					for(DeviceFaultCodeRecord record : group) {
						int currentFaultCode = record.getValue();
						if (currentBatch.isEmpty()) {
                    currentBatch.add(record);
                    previousFaultCode = currentFaultCode;
                } else if (currentFaultCode == previousFaultCode) {
                    currentBatch.add(record);
                } else {
                    // Evaluate the current batch before switching to a new fault code
                    evaluateAndAddBatch(currentBatch, TIME_FAULT_THRESHOLD_MINUTES, finalResults);
                    
                    // Initialize a new batch
                    currentBatch = new ArrayList<>();
                    currentBatch.add(record);
                    previousFaultCode = currentFaultCode;
                }
					}
					// Handle the last batch after the loop
					evaluateAndAddBatch(currentBatch, TIME_FAULT_THRESHOLD_MINUTES, finalResults);
				});
				if(finalResults.isEmpty()) {
					log.info("No alerts generated, finalResults is empty.");
					return;	
				}
				List<AlertEntity> alertsToInsert = new ArrayList<>();
				// Process the final results, e.g., save to database or send alerts
				for (AlertEntity alert : finalResults) {
					log.info("Generated alert: device=" + alert.getId_device() + ", error=" + alert.getId_error() + ", start=" + alert.getStart_date());
					// 1. find the start time from DB; 
					params.put("reference_time", alert.getStart_date());
					params.put("field_name", alert.getFieldName());
					String alertStartTime = (String) queryForObject("CronJobDetectDeviceStatus.findFaultStatusStartTime", params);
					if (Lib.isBlank(alertStartTime)) {
						log.info("The issue fault status is from initial state, using min start time for device id: " + device.getId() + ", data table: " + device.getDatatablename());
						alertStartTime = (String) queryForObject("CronJobDetectDeviceStatus.findMinStartTime", params);
					}
					alert.setStart_date(alertStartTime);

					// Check if an alert already exists for this device and error combination
					params.put("id_error", alert.getId_error());
					// 2. check if an alert already exists; 
					AlertEntity alertItem = (AlertEntity)queryForObject("CronJobDetectDeviceStatus.getExistsAlertEvent", params);
					if (alertItem != null) {
						log.debug("Alert event record already exists, skip create event AlertEntity id_device: "+ alertItem.getId_device()+", start_date: "+ alertItem.getStart_date());
						continue;
					}
					alertsToInsert.add(alert);
				}
				if (alertsToInsert.isEmpty()) {
					log.info("No new alerts to insert.");
					return;
				}
				// 3. insert alert if not exists
				// Insert the alerts to the database
				insert("CronJobDetectDeviceStatus.bulkInsertAlertEvent", alertsToInsert);

			} catch (Exception ex) {
				log.error("checkDataloggerIsNotResponding error: " + ex.getMessage(), ex);
			}
		}
	}

	/**
	 * @description Evaluate a batch of device fault code records and add an alert if the duration exceeds the threshold
	 * @param batch
	 * @param minDurationMinutes
	 * @param results
	 */
	private void evaluateAndAddBatch(List<DeviceFaultCodeRecord> batch, long minDurationMinutes, List<AlertEntity> results) {
		if (batch.isEmpty()) {
			return;
		}

		DeviceFaultCodeRecord first = batch.get(0);
		int faultCode = first.getValue();

		// ignore if faultCode is 0 (no error)
		if (faultCode == 0) {
			return;
		}

		String startTime = batch.get(0).getTime();
		String endTime = batch.get(batch.size() - 1).getTime();

		// Check the duration between start and end time
		LocalDateTime startDt = LocalDateTime.parse(startTime, formatter);
		LocalDateTime endDt = LocalDateTime.parse(endTime, formatter);
		long diffMinutes = Duration.between(startDt, endDt).toMinutes();

		// Filter condition time_diff_minutes > minDurationMinutes (e.g., > 120 minutes)
		if (diffMinutes > minDurationMinutes) {
			AlertEntity alertEntity = new AlertEntity();
			alertEntity.setId_device(first.getId_device());
			alertEntity.setId_error(first.getError_id());
			alertEntity.setStart_date(startTime);
			alertEntity.setIs_slow_response(0);
			alertEntity.setFieldName(first.getField());
			alertEntity.setCreated_by("CronJobDetectDeviceFaultStatusService");
			results.add(alertEntity);
		}
  }

	/**
	 * @description check if alert item already exists
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	private boolean checkExistsAlertItem(Map<String, Object> params) throws SQLException {
		AlertEntity alertItem = (AlertEntity)queryForObject("CronJobDetectDeviceStatus.getExistsAlertEvent", params);
		return alertItem != null;
	}

	/**
	 * @description build alert entity
	 * @author chuong.ma
	 * @param device
	 * @param startTime
	 * @return
	 */
	private AlertEntity buildAlertEntity(DeviceEntity device, String startTime) {
		AlertEntity alertEntity = new AlertEntity();
		alertEntity.setId_device(device.getId());
		alertEntity.setId_error(device.getId_error());
		alertEntity.setStart_date(startTime);
		alertEntity.setIs_slow_response(0);
		alertEntity.setCreated_by("CronJobDetectDeviceFaultStatusService");
		return alertEntity;
	}

	/**
	 * @description insert alert queue
	 * @author long.pham
	 * @since 2026-04-07
	 * @param {AlertEntity}
	 */
	private boolean insertAlert(AlertEntity obj) {
		try {
			int result = (Integer) insert("CronJobDetectDeviceStatus.insertAlert", obj);
			return result > 0;
		} catch (Exception ex) {
			log.error("insertAlert obj: " + obj.getId_device() + ", error: " + ex.getMessage(), ex);
			return false;
		}
	}

	/**
	 * @description update job scheduler status
	 * @author chuong.ma
	 * @since 2026-09-25
	 * @param jobCode
	 * @param trigger, trigger can be "START" or "END"
	 */
	public void updateJobSchedulerStatus(String jobCode, String trigger) {
		try {
			String status = "START".equals(trigger) ? "RUNNING" : "COMPLETED";
			CronJobSchedulerEntity jobEntity = (CronJobSchedulerEntity) queryForObject("CronJobScheduler.getJobScheduler", jobCode);
			if(jobEntity == null) {
				jobEntity = new CronJobSchedulerEntity();
				jobEntity.setJob_code(jobCode);
				jobEntity.setJob_name("Detect Device Fault Status");
				jobEntity.setLast_start_time(Date.from(Instant.now()));
				jobEntity.setLast_status(status);
				jobEntity.setDeseription("Detect Device Fault Status");
				insert("CronJobScheduler.insertJobScheduler", jobEntity);
			}else{
				if ("START".equals(trigger)) {
					jobEntity.setLast_start_time(Date.from(Instant.now()));
				}
				if ("END".equals(trigger)) {
					jobEntity.setLast_end_time(Date.from(Instant.now()));
				}
				jobEntity.setLast_status(status);
				update("CronJobScheduler.updateJobScheduler", jobEntity);
			}
		} catch (Exception e) {
			log.error("Error updating job scheduler status: " + e.getMessage(), e);
		}
	}

	/**
	 * @description Returns the configuration of device fault codes for different model groups. The configuration maps each model group ID to a list of fault code fields.
	 * @return A map where the key is the model group ID and the value is a list of fault code fields associated with that model group.
	 */
	private Map<Integer, List<FaultCodeField>> deviceFaultCodesConfigure(){
		Map<Integer, List<FaultCodeField>> map = new HashMap<Integer, List<FaultCodeField>>(){{
			// ATI Tracker Motor model configuration
			put(FAULT_CODE_MODEL_GROUP.ATI_TRACKER_MOTOR.getGroupId(),Arrays.asList(
				new FaultCodeField(){{
					setFieldName("Alarms");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList("0:2548", "1:2549", "2:2550", "3:2551", "4:2552"));
				}}
			));

			// Advanced Energy Solaron model configuration
			put(FAULT_CODE_MODEL_GROUP.ADVANCED_ENERGY_SOLARON.getGroupId(),Arrays.asList(
				new FaultCodeField(){{
					setFieldName("active_faults1");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList("0:71", "2:73", "3:74", "4:75", "5:76", "6:77", "10:78", "11:79", "12:80", "13:81", 
						"14:82", "15:83", "16:84", "17:85", "19:86", "22:87", "24:88", "25:89", "26:90", "29:91", "30:92", "31:93"));
				}},
				new FaultCodeField(){{
					setFieldName("active_faults2");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList("0:94", "1:95", "2:96", "3:97", "4:98", "9:99", "10:100", "11:101", "12:102", "13:103", 
						"14:104", "15:105", "16:106", "17:107", "18:108", "19:109", "20:110", "21:111", "22:112", "23:113", "24:114", "25:115", "26:116", "27:117", "28:118", "29:119"));
				}},
				new FaultCodeField(){{
					setFieldName("active_faults3");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList("0:120", "1:121", "2:122", "3:123", "4:124", "8:125", "9:126", "10:127", "11:128", "12:129", "13:130", "14:131"));
				}},
				new FaultCodeField(){{
					setFieldName("limits");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList("8:138","16:139","17:140","18:141","19:142","20:143","21:144","22:145","23:146","24:147"));
				}},
				new FaultCodeField(){{
					setFieldName("status");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList("0:148","1:149","2:150","3:151","4:152","5:153","6:154","8:155","9:156","10:157","11:158"));
				}},
				new FaultCodeField(){{
					setFieldName("warnings1");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:159","2:160","3:161","4:162","5:163","6:164","7:165","8:166"));
				}}
			));
			

			// Chint Solectria Inverter Class 9725 model configuration
			put(FAULT_CODE_MODEL_GROUP.CHINT_SOLECTRIA_INVERTER_CLASS_9725.getGroupId(),Arrays.asList(
				new FaultCodeField(){{
					setFieldName("PermanentFaultCode");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList("0:167","1:168","2:169","3:170","4:171","5:172","6:173","7:174","8:175","9:176","10:177","11:178","12:179","13:180","14:181","15:182"));
				}},
				new FaultCodeField(){{
					setFieldName("WarnCode");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList("0:183","1:184","2:185","3:186","4:187","5:188","6:189","7:190","8:191","9:192","10:193","11:194","12:195","13:196","14:197","15:198"));
				}},
				new FaultCodeField(){{
					setFieldName("FaultCode0");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList("0:199","1:200","2:201","3:202","4:203","5:204","6:205","7:206","8:207","9:208","10:209","11:210","12:211","13:212","14:213","15:214"));
				}},
				new FaultCodeField(){{
					setFieldName("FaultCode1");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList("0:215","1:216","2:217","3:218","4:219","5:220","6:221","7:222","8:223","9:224","10:225","11:226","12:227","13:228","14:229","15:230"));
				}},
				new FaultCodeField(){{
					setFieldName("FaultCode2");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList("0:231","1:232","2:233","3:234","4:235","5:236","6:237","7:238","8:239","9:240","10:241","11:242","12:243","13:244","14:245","15:246"));
				}}
			));

			// ABB Trio Class 6210 model configuration
			put(FAULT_CODE_MODEL_GROUP.ABB_TRIO_CLASS_6210.getGroupId(),Arrays.asList(
				new FaultCodeField(){{
					setFieldName("StatesByte0");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList("0:247", "1:248", "2:249", "3:250", "4:251", "5:252", "6:253", "7:254", "8:255", "9:256", "10:257", "11:258", 
						"12:259", "13:260", "14:261", "15:262", "16:263", "17:264", "18:265", "19:266", "20:267", "21:268", "22:269", "23:270", "24:271", "25:272", "26:273", 
						"27:274", "30:275", "31:276", "32:277", "33:278", "34:279", "35:280", "36:281", "37:282", "38:283", "98:284", "99:285", "100:286", "101:287", "110:288", 
						"111:289", "112:290", "113:291", "114:292", "115:293", "116:294", "117:295", "150:296", "200:297"));
				}},
				new FaultCodeField(){{
					setFieldName("StatesByte1");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList("0:298", "1:299", "2:300", "3:301", "4:302", "5:303", "6:304", "7:305", "8:306", "9:307", "10:308", "11:309", 
						"12:310", "13:311", "14:312", "15:313", "16:314", "17:315", "18:316", "19:317", "20:318", "21:319", "22:320", "23:321", "24:322", "25:323", "26:324", 
						"27:325", "28:326", "29:327", "30:328", "31:329", "40:330", "41:331", "42:332", "43:333", "44:334", "45:335", "46:336", "47:337", "255:338"));
					
				}},
				new FaultCodeField(){{
					setFieldName("StatesByte2");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList("0:339", "1:340", "2:341", "3:342", "4:343", "5:344", "6:345", "7:346", "8:347", "9:348", "10:349", "11:350", 
						"12:351", "13:352", "14:353", "15:354", "16:355", "17:356", "18:357", "19:358", "255:359"));
				}},
				new FaultCodeField(){{
					setFieldName("StatesByte4");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList("0:360", "1:361", "2:362", "3:363", "4:364", "5:365", "6:366", "7:367", "8:368", "9:369", "10:370", "11:371", 
						"12:372", "13:373", "14:374", "15:375", "16:376", "17:377", "18:378", "19:379", "20:380", "21:381", "22:382", "23:383", "24:384", "25:385", "26:386", 
						"27:387", "28:388", "29:389", "30:390", "31:391", "32:392", "33:393", "34:394", "35:395", "36:396", "37:397", "38:398", "39:399", "40:400", "41:401", 
						"42:402", "43:403", "44:404", "45:405", "46:406", "47:407", "48:408", "49:409", "50:410", "51:411", "52:412", "53:413", "54:414", "55:415", "56:416", 
						"57:417", "58:418", "59:419", "60:420", "61:421", "62:422", "63:423", "64:424", "65:425", "66:326", "67:427", "68:428", "69:429", "70:430", "71:431", 
						"72:432", "73:433", "74:434", "75:435", "76:436", "77:437", "79:438", "80:439", "81:440", "82:441", "83:442"));
				}}
			));

			// Huawei SUN2000 (V1) model configuration
			put(FAULT_CODE_MODEL_GROUP.HUAWEI_SUN2000_V1.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("Alarm1");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:2001", "1:2002", "2:2011", "3:2012", "4:2013", "5:2021", "6:2031", "7:2032", "8:2033", "9:2034", "10:2035", "11:2036", "12:2037",
						"13:2038", "14:2039", "15:2040"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Alarm2");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:2051", "1:2061", "2:2062", "3:2063", "4:2064", "5:2065", "6:2066", "7:61440", "8:2067", "10:2070", "11:2071", "12:2072", "13:2075",
						"14:2077", "15:2080"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Alarm3");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:2081", "1:2085", "2:2014", "3:2086", "5:2082", "6:2015", "7:2087", "8:2088", "9:2089", "10:2090", "11:2091", "12:2092", "13:2003",
						"14:2093"
					));
				}}
			));

			// IVT Solaron EXT model configuration
			put(FAULT_CODE_MODEL_GROUP.IVT_SOLARON_EXT.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("active_faults1");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:671", "2:672", "3:673", "4:674", "5:675", "6:676", "10:677", "11:678", "12:679", "13:680", "14:681", "15:682", "16:683", "17:684",
						"19:685", "22:686", "24:687", "25:688", "26:689", "29:690", "30:691", "31:692"
					));
				}},
				new FaultCodeField(){{
					setFieldName("active_faults2");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:693", "1:694", "2:695", "3:696", "4:697", "9:698", "10:699", "11:700", "12:701", "13:702", "14:703", "15:104", "16:705", "17:706",
						"18:707", "19:708", "20:709", "21:710", "22:711", "23:712", "24:713", "25:714", "26:715", "27:716", "28:717", "29:718"
					));
				}},
				new FaultCodeField(){{
					setFieldName("active_faults3");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:719", "1:720", "2:721", "3:722", "4:723", "8:724", "9:725", "10:726", "11:727", "12:728", "13:729", "14:730"
					));
				}},
				new FaultCodeField(){{
					setFieldName("limits");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"8:731", "16:732", "17:733", "18:734", "19:735", "20:736", "21:737", "22:738", "23:739", "24:740"
					));
				}},
				new FaultCodeField(){{
					setFieldName("status");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:741", "1:742", "2:743", "3:744", "4:745", "5:746", "6:747", "8:748", "9:749", "10:750", "11:751"
					));
				}},
				new FaultCodeField(){{
					setFieldName("warnings1");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"1:752", "2:753", "3:754", "4:755", "5:756", "6:757", "7:758", "8:759"
					));
				}}
			));

			// Kehua SPI 50/60K Inverter model configuration
			put(FAULT_CODE_MODEL_GROUP.KEHUA_SPI_50_60K_INVERTER.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("FaultWord");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:2343", "1:2344", "2:2345"
					));
				}}
			));

			// PVH Tbox model configuration
			put(FAULT_CODE_MODEL_GROUP.PVH_TBOX.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("Alarms");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:2521", "1:2522", "2:2523", "3:2524", "4:2525", "5:2526", "6:2527", "7:2528", "8:2529", "9:2530", "10:2531", "11:2532", "12:2533",
						"13:2534", "14:2535", "15:2536"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Warnings");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:2521", "1:2522", "2:2523", "3:2524", "4:2525", "5:2526", "6:2527", "7:2528", "8:2529", "9:2530", "10:2531", "11:2532", "12:2533",
						"13:2534", "14:2535", "15:2536"
					));
				}}
			));

			// PV Powered 3550/260/500 kW Inverter model configuration
			put(FAULT_CODE_MODEL_GROUP.PV_POWERED_3550_260_500_KW_INVERTER.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("InverterOperatingStatus");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:464", "1:465", "2:466", "3:467", "4:468", "5:469", "6:470", "7:471", "8:472", "9:473", "10:474", "11:475", "12:476"
					));
				}},
				new FaultCodeField(){{
					setFieldName("MainFault");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:477", "1:478", "2:479", "3:480", "4:481", "32768:482"
					));
				}},
				new FaultCodeField(){{
					setFieldName("DriveFault");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:483", "1:484", "2:485", "3:486", "5:487", "6:488", "7:489", "8:490", "9:491", "10:492", "11:493", "12:494"
					));
				}},
				new FaultCodeField(){{
					setFieldName("VoltageFault");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:495", "1:496", "2:497", "3:498", "5:499", "6:500", "7:501", "8:502", "9:503", "10:504", "11:505", "12:506", "13:507"
					));
				}},
				new FaultCodeField(){{
					setFieldName("GridFault");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:508", "1:509", "2:510", "3:511", "4:512", "5:513", "6:514", "7:515", "8:516", "9:517", "10:518", "11:519", "12:520", "13:521"
					));
				}},
				new FaultCodeField(){{
					setFieldName("TemperatureFault");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:522", "1:523", "2:524", "3:525", "4:526", "5:527", "6:528", "7:529", "8:530", "9:531"
					));
				}},
				new FaultCodeField(){{
					setFieldName("SystemFault");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:532", "1:533", "2:534", "3:535", "4:536", "5:537", "6:538", "7:539", "8:540"
					));
				}},
				new FaultCodeField(){{
					setFieldName("SystemWarnings");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:541", "1:542", "2:543", "3:544", "4:545", "5:546", "7:547", "8:548", "9:549", "10:550"
					));
				}},
				new FaultCodeField(){{
					setFieldName("PVMStatusCodes");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:551", "1:552", "2:553", "5:554"
					));
				}}
			));

			// Phoenix Contact QUINT UPS model configuration
			put(FAULT_CODE_MODEL_GROUP.PHOENIX_CONTACT_QUINT_UPS.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("StatusAlarm");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"1:1228", "2:1229", "3:1230", "4:1231", "5:1232", "6:1233", "7:1234", "8:1235", "9:1236", "10:1237", "11:1238", "12:1239", "13:1240",
						"14:1241", "15:1242", "16:1243", "17:1244", "18:1245", "19:1246", "20:1247", "21:1248", "22:1249", "23:1250", "24:1251", "25:1252",
						"26:1253", "27:1254", "28:1255", "29:1256", "30:1257", "31:1258"
					));
				}},
				new FaultCodeField(){{
					setFieldName("StatusWarning");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"1:1259", "2:1260", "3:1261", "4:1262", "5:1263", "6:1264", "7:1265", "8:1266", "9:1267", "10:1268", "11:1269", "12:1270", "13:1271",
						"14:1272", "15:1273", "16:1274", "17:1275", "18:1276", "19:1277", "20:1278", "21:1279", "22:1280", "23:1281", "24:1282", "25:1283",
						"26:1284", "27:1285", "28:1286", "29:1287", "30:1288", "31:1289"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Battery1StateofFuse");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList("23867:1290"));
				}}
			));

			// Satcon PowerGate 225 Inverter model configuration
			put(FAULT_CODE_MODEL_GROUP.SATCON_POWERGATE_225_INVERTER.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("Fault1");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:820", "1:821", "2:822", "3:823", "4:824", "5:825", "6:826", "7:827", "8:828", "9:829", "10:830", "11:831", "12:832", "13:833",
						"14:834", "15:835"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Fault3");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:836", "1:837", "2:838", "3:839", "4:840", "5:841", "6:842", "7:843", "8:844", "9:845", "10:846", "11:847", "12:848", "13:849",
						"14:850", "15:851"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Fault4");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:852", "1:853", "2:854", "3:855", "4:856", "5:857", "6:858", "7:859", "8:860", "9:861", "10:862", "11:863", "12:864", "13:865",
						"14:866", "15:867"
					));
				}},
				new FaultCodeField(){{
					setFieldName("GridStatus");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:868", "1:869", "2:870", "3:871", "4:872", "5:873", "6:874", "7:875", "8:876", "9:877", "10:878", "11:879", "12:880", "13:881",
						"14:882", "15:883"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Status6");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:884", "1:885", "2:886", "3:887", "4:888", "5:889", "6:890", "7:891", "8:892", "9:893", "10:894", "11:895", "12:896", "13:897",
						"14:898", "15:899"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Status7");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:900", "1:901", "2:902", "3:903", "4:904", "5:905", "6:906", "7:907", "8:908", "9:909", "10:910", "11:911", "12:912", "13:913",
						"14:914", "15:915"
					));
				}}
			));

			// Satcon PVS 357 Inverter model configuration
			put(FAULT_CODE_MODEL_GROUP.SATCON_PVS_357_INVERTER.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("Fault_Word1");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:569", "1:570", "2:571", "3:572", "4:573", "5:574", "6:575", "7:576", "8:577", "9:578", "10:579", "11:580", "12:581", "13:582",
						"14:583", "15:584"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Fault_Word2");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:585", "1:586", "2:587", "3:588", "4:589", "5:590", "6:591", "7:592", "8:593", "9:594", "10:595", "11:596", "12:597", "13:598",
						"14:599", "15:600"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Fault_Word3");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:601", "1:602", "2:603", "3:604", "4:605", "5:606", "6:607", "7:608", "8:609", "9:610", "10:611", "11:612", "12:613", "13:614",
						"14:615", "15:616"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Fault_Word4");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:617", "1:618", "2:619", "3:620", "4:621", "5:622", "6:623", "7:624", "8:625", "9:626", "10:627", "11:628", "12:629", "13:630",
						"14:631", "15:632"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Fault_Word5");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:633", "1:634", "2:635", "3:636", "4:637", "5:638", "6:639", "7:640", "8:641", "9:642", "10:643", "11:644", "12:645", "13:646",
						"14:647", "15:648"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Fault_Word6");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:649", "1:650", "2:651", "3:652", "4:653", "5:654", "6:655", "7:656", "8:657", "9:658", "10:659", "11:660", "12:661", "13:662",
						"14:663", "15:664"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Fault_Word7");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:665", "1:666", "2:667", "3:668", "4:669", "5:670"
					));
				}}
			));

			// SMA SHP 75/10 model configuration
			put(FAULT_CODE_MODEL_GROUP.SMA_SHP_75_10.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("ManufacturerspecificeventcodeEvtVnd1");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:1760", "1:1760", "2:1761", "3:1762", "4:1762", "5:1763", "7:1764", "8:1764", "9:1770", "10:1766", "11:1771", "12:1767", "13:1767",
						"14:1768", "15:1772", "16:1773", "25:1781", "26:1778"
					));
				}},
				new FaultCodeField(){{
					setFieldName("ManufacturerspecificeventcodeEvtVnd2");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:1785", "1:1786", "2:1798", "3:1793", "4:1796", "5:1802"
					));
				}},
				new FaultCodeField(){{
					setFieldName("ManufacturerspecificeventcodeEvtVnd3");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:1775", "1:1776"
					));
				}},
				new FaultCodeField(){{
					setFieldName("ManufacturerspecificeventcodeEvtVnd4");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:1779", "1:1780", "2:1777", "3:1783", "4:1784", "5:1788", "6:1782", "7:1789", "8:1790", "9:1791", "10:1792", "11:1794", "13:1795",
						"24:1776", "25:1799", "26:1800"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Manufacturerspecificstatuscode");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList(
						"0:1716","10:1717","20:1718","30:1719","40:1720","50:1721","51:1722","52:1723","53:1724","54:1725","60:1726","70:1727","80:1728"
					));
				}}
			));

			// Huawei SmartLogger 3000 model configuration
			put(FAULT_CODE_MODEL_GROUP.HUAWEI_SMARTLOGGER_3000.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("AlarmInfo1");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"3:1729", "11:1730"
					));
				}},
				new FaultCodeField(){{
					setFieldName("AlarmInfo2");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"1:1731", "2:1732", "3:1733", "4:1734", "5:1735", "6:1736", "7:1737", "8:1738", "9:1739", "10:1740", "11:1741", "12:1742", "13:1743"
					));
				}},
				new FaultCodeField(){{
					setFieldName("AlarmInfo3");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:1744", "1:1745", "2:1746", "3:1747", "4:1748", "5:1749", "6:1750", "7:1751", "8:1752", "9:1753", "10:1754", "11:1755", "12:1756",
						"13:1757", "14:1758"
					));
				}}
			));

			// Yaskawa Solectria XGI 1500 model configuration
			put(FAULT_CODE_MODEL_GROUP.YASKAWA_SOLECTRIA_XGI_1500.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("FaultStatus");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:1572", "1:1573", "2:1574", "4:1575", "6:1576", "7:1577", "8:1578", "9:1579", "10:1580", "11:1581", "14:1582", "15:1583"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Fault1");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:1584", "1:1585", "2:1586", "3:1587", "4:1588", "5:1589", "6:1590", "7:1591", "8:1592", "9:1593", "10:1594", "11:1595"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Fault2");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:1596", "1:1597", "2:1598", "3:1599", "4:1600", "5:1601", "6:1602", "7:1603", "8:1604", "9:1605", "10:1606", "11:1607", "12:1608",
						"23:1621", "24:1609", "25:1610", "26:1611", "27:1612", "28:1613", "29:1614", "30:1615"
					));
				}},
				new FaultCodeField(){{
					setFieldName("Fault3");
					setMechanism(MECHANISM.BIT_ON_OFF);
					addFaultCodeMapping(Arrays.asList(
						"0:1616", "1:1617", "2:1618", "3:1619", "4:1620"
					));
				}}
			));

			// SEV SG110CX model configuration
			put(FAULT_CODE_MODEL_GROUP.SEV_SG110CX.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("FaultCode");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList(
						"2:1036", "3:1037", "4:1038", "5:1039", "7:1040", "8:1041", "9:1042", "10:1043", "11:1044", "12:1045", "13:1046", "14:1047",
						"15:1048", "16:1049", "17:1050", "19:1051", "20:1052", "21:1053", "22:1054", "23:1055", "24:1056", "25:1057", "30:1058", "31:1059",
						"32:1060", "33:1061", "34:1062", "36:1063", "37:1064", "38:1065", "39:1066", "40:1067", "41:1068", "42:1069", "43:1070", "44:1071",
						"45:1072", "46:1073", "47:1074", "48:1075", "49:1076", "50:1077", "53:1078", "54:1079", "55:1080", "56:1081", "59:1082", "60:1083",
						"70:1084", "71:1085", "72:1086", "74:1087", "76:1088", "78:1089", "79:1090", "80:1091", "81:1092", "87:1093", "88:1094", "89:1095",
						"105:1096", "106:1097", "116:1098", "117:1099", "220:1100", "221:1101", "222:1102", "223:1103", "224:1104", "225:1105", "226:1106",
						"227:1107", "448:1157", "449:1158", "450:1159", "451:1160", "452:1161", "453:1162", "454:1163", "455:1164", "456:1165", "457:1166",
						"458:1167", "459:1168", "460:1169", "461:1170", "462:1171", "463:1172", "464:1173", "465:1174", "466:1175", "467:1176", "468:1177",
						"469:1178", "470:1179", "471:1180", "514:1108", "532:1109", "533:1110", "534:1111", "535:1112", "536:1113", "537:1114", "538:1115",
						"539:1116", "540:1117", "541:1118", "542:1119", "543:1120", "544:1121", "545:1122", "546:1123", "547:1124", "548:1133", "549:1134",
						"550:1135", "551:1136", "552:1137", "553:1138", "554:1139", "555:1140", "556:1141", "557:1142", "558:1143", "559:1144", "560:1145",
						"561:1146", "562:1147", "563:1148", "564:1125", "565:1126", "566:1127", "567:1128", "568:1129", "569:1130", "570:1131", "571:1132",
						"580:1149", "581:1150", "582:1151", "583:1152", "584:1153", "585:1154", "586:1155", "587:1156", "1500:1181", "1501:1182", "1502:1183",
						"1503:1184", "1504:1185", "1505:1186", "1506:1187", "1507:1188", "1508:1189", "1509:1190", "1510:1191", "1511:1192", "1512:1193",
						"1513:1194", "1514:1195", "1515:1196", "1516:1197", "1517:1198", "1518:1199", "1519:1200", "1520:1201", "1521:1202", "1522:1203",
						"1523:1204", "1524:1205", "1525:1206", "1526:1207", "1527:1208", "1528:1209", "1529:1210", "1530:1211", "1531:1212"
					));
				}}
			));

			// SMA Core model configuration
			put(FAULT_CODE_MODEL_GROUP.SMA_CORE.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("EventMessage");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList(
						"101:2065", "102:2066", "103:2067", "104:2068", "105:2069", "202:2070", "203:2071", "204:2072", "205:2073", "206:2074", "301:2075",
						"401:2076", "402:2077", "404:2324", "501:2078", "507:2079", "601:2080", "701:2081", "901:2082", "1001:2083", "1101:2084", "1302:2085",
						"1501:2086", "3301:2325", "3302:2326", "3303:2327", "3401:2087", "3402:2088", "3407:2089", "3501:2090", "3601:2091", "3701:2092",
						"3801:2093", "3802:2094", "3805:2095", "3901:2096", "3902:2097", "6001:2328", "6002:2098", "6003:2099", "6004:2100", "6005:2101",
						"6006:2102", "6009:2103", "6101:2104", "6105:2105", "6107:2106", "6109:2107", "6111:2108", "6112:2109", "6121:2110", "6155:2111",
						"6202:2112", "6301:2113", "6304:2114", "6305:2115", "6306:2116", "6401:2117", "6403:2118", "6404:2119", "6405:2120",
						"6406:2121", "6407:2122", "6408:2123", "6409:2124", "6410:2125", "6411:2126", "6412:2127", "6415:2128", "6416:2129", "6462:2130", "6499:2131",
						"6501:2132", "6502:2133", "6509:2134", "6512:2135", "6603:2136", "6604:2137", "6607:2138", "6608:2139", "6609:2140", "6610:2141",
						"6701:2142", "6702:2143", "6801:2144", "6802:2145", "6901:2146", "6902:2147", "7001:2148", "7002:2149", "7106:2150", "7110:2151",
						"7112:2152", "7113:2153", "7201:2154", "7202:2155", "7303:2156", "7320:2157", "7324:2158", "7330:2159", "7331:2160", "7332:2161",
						"7333:2162", "7337:2163", "7340:2164", "7347:2165", "7348:2166", "7349:2167", "7350:2168", "7351:2329", "7352:2169", "7353:2170",
						"7354:2171", "7355:2172", "7356:2173", "7357:2174", "7358:2175", "7359:2176", "7360:2177", "7619:2178", "7623:2179", "7624:2180",
						"7701:2181", "7702:2182", "7703:2183", "8003:2184", "8101:2185", "8102:2186", "8103:2187", "8104:2188", "8501:2189", "8502:2190",
						"8708:2191", "8709:2192", "8710:2193", "9002:2194", "9003:2195", "9005:2196", "9007:2197", "9202:2198", "9203:2199", "9204:2200",
						"9205:2201", "9206:2202", "9207:2203", "9208:2204", "9209:2205", "9211:2206", "9214:2207", "9215:2208", "9216:2209", "9217:2210",
						"9218:2211", "9219:2212", "9220:2213", "9221:2214", "9223:2215", "9301:2216", "9303:2217", "9304:2218", "9305:2219", "9306:2220",
						"9307:2221", "9308:2222", "9311:2223", "9312:2224", "9313:2225", "9314:2226", "9315:2227", "9316:2228", "9334:2229", "9335:2230",
						"9336:2231", "9337:2232", "9338:2233", "9339:2234", "9340:2235", "9345:2236", "9346:2237", "9347:2238", "9351:2239", "9352:2240",
						"9353:2241", "9354:2242", "10100:2243", "10101:2244", "10102:2245", "10103:2246", "10104:2247", "10105:2248", "10108:2249",
						"10109:2250", "10110:2251", "10116:2252", "10118:2253", "10120:2254", "10224:2255", "10248:2256", "10249:2257", "10250:2258",
						"10251:2259", "10252:2260", "10253:2261", "10254:2262", "10255:2263", "10282:2264", "10283:2265", "10284:2266", "10285:2267",
						"10286:2268", "10287:2269", "10339:2270", "10340:2271", "10341:2272", "10343:2273", "10420:2274", "10421:2275", "10513:2276",
						"10517:2277", "10518:2278", "10520:2279", "10521:2280", "10525:2281", "10528:2282", "27107:2283", "27108:2284", "27109:2285",
						"27301:2286", "27302:2287", "27312:2288", "27329:2289", "27331:2290", "27332:2291", "27336:2292", "29001:2293", "29004:2294",
						"29006:2295", "29016:2296", "29252:2297", "29253:2298", "29254:2299"
					));
				}},
				new FaultCodeField(){{
					setFieldName("BlockStatus");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList(
						"257:2300", "1655:2301", "1690:2302", "2386:2303", "2387:2304", "2388:2305", "2389:2306", "2390:2307", "2490:2308", "3165:2309",
						"3166:2310", "3167:2311", "4553:2312", "4570:2313", "16777213:2314"
					));
				}},
				new FaultCodeField(){{
					setFieldName("ReasonforDerating");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList(
						"557:2315", "1705:2316", "3520:2317", "3554:2318", "3556:2319", "4560:2320", "4561:2321", "16777213:2322"
					));
				}}
			));

			// SMA STP 25/50-US-50 model configuration
			put(FAULT_CODE_MODEL_GROUP.SMA_STP_25_50_US_50.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("EventMessage");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList(
						"101:1806", "102:1807", "103:1808", "104:1809", "105:1810", "202:1811", "203:1812", "204:1813", "205:1814", "206:1815", "301:1816",
						"401:1817", "402:1818", "404:2331", "501:1819", "507:1820", "601:1821", "701:1822", "901:1823", "1001:1824", "1101:1825", "1302:1826",
						"1501:1827", "3301:2332", "3302:2333", "3303:2334", "3401:1828", "3402:1829", "3407:1830", "3501:1831", "3601:1832", "3701:1833",
						"3801:1834", "3802:1835", "3805:1836", "3901:1837", "3902:1838", "6001:2335", "6002:1839", "6003:1840", "6004:1841", "6005:1842",
						"6006:1843", "6009:1844", "6101:1845", "6105:1846", "6107:1847", "6109:1848", "6111:1849", "6112:1850", "6121:1851", "6155:1852",
						"6202:1853", "6301:1854", "6304:1855", "6305:1856", "6306:1857", "6401:1858", "6403:1859", "6404:1860", "6405:1861", "6406:1862",
						"6407:1863", "6408:1864", "6409:1865", "6410:1866", "6411:1867", "6412:1868", "6415:1869", "6416:1870", "6462:1871", "6499:1872",
						"6501:1873", "6502:1874", "6509:1875", "6512:1876", "6603:1877", "6604:1878", "6607:1879", "6608:1880", "6609:1881", "6610:1882",
						"6701:1883", "6702:1884", "6801:1885", "6802:1886", "6901:1887", "6902:1888", "7001:1889", "7002:1890", "7106:1891", "7110:1892",
						"7112:1893", "7113:1894", "7201:1895", "7202:1896", "7303:1897", "7320:1898", "7324:1899", "7330:1900", "7331:1901", "7332:1902",
						"7333:1903", "7337:1904", "7340:1905", "7347:1906", "7348:1907", "7349:1908", "7350:1909", "7351:2336", "7352:1910", "7353:1911",
						"7354:1912", "7355:1913", "7356:1914", "7357:1915", "7358:1916", "7359:1917", "7360:1918", "7619:1919", "7623:1920", "7624:1921",
						"7701:1922", "7702:1923", "7703:1924", "8003:1925", "8101:1926", "8102:1927", "8103:1928", "8104:1929", "8501:1930", "8502:1931",
						"8708:1932", "8709:1933", "8710:1934", "9002:1935", "9003:1936", "9005:1937", "9007:1938", "9202:1939", "9203:1940", "9204:1941",
						"9205:1942", "9206:1943", "9207:1944", "9208:1945", "9209:1946", "9211:1947", "9214:1948", "9215:1949", "9216:1950", "9217:1951",
						"9218:1952", "9219:1953", "9220:1954", "9221:1955", "9223:1956", "9301:1957", "9303:1958", "9304:1959", "9305:1960", "9306:1961",
						"9307:1962", "9308:1963", "9311:1964", "9312:1965", "9313:1966", "9314:1967", "9315:1968", "9316:1969", "9334:1970", "9335:1971",
						"9336:1972", "9337:1973", "9338:1974", "9339:1975", "9340:1976", "9345:1977", "9346:1978", "9347:1979", "9351:1980", "9352:1981",
						"9353:1982", "9354:1983", "10100:1984", "10101:1985", "10102:1986", "10103:1987", "10104:1988", "10105:1989", "10108:1990",
						"10109:1991", "10110:1992", "10116:1993", "10118:1994", "10120:1995", "10224:1996", "10248:1997", "10249:1998", "10250:1999",
						"10251:2000", "10252:2001", "10253:2002", "10254:2003", "10255:2004", "10282:2005", "10283:2006", "10284:2007", "10285:2008",
						"10286:2009", "10287:2010", "10339:2011", "10340:2012", "10341:2013", "10343:2014", "10420:2015", "10421:2016", "10513:2017",
						"10517:2018", "10518:2019", "10520:2020", "10521:2021", "10525:2022", "10528:2023", "27107:2024", "27108:2025", "27109:2026",
						"27301:2027", "27302:2028", "27312:2029", "27329:2030", "27331:2031", "27332:2032", "27336:2033", "29001:2034", "29004:2035",
						"29006:2036", "29016:2037", "29252:2038", "29253:2039", "29254:2040"
					));
				}},
				new FaultCodeField(){{
					setFieldName("BlockStatus");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList(
						"257:2042", "1655:2043", "1690:2044", "2386:2045", "2387:2046", "2388:2047", "2389:2048", "2390:2049", "2490:2050", "3165:2051",
						"3166:2052", "3167:2053", "4553:2054", "4570:2055", "16777213:2056"
					));
				}},
				new FaultCodeField(){{
					setFieldName("ReasonforDerating");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList(
						"557:2057", "1705:2058", "3520:2059", "3554:2060", "3556:2061", "4560:2062", "4561:2063", "16777213:2064"
					));
				}}
			));

			// Xantrex GT100/250/500 model configuration
			put(FAULT_CODE_MODEL_GROUP.XANTREX_GT100_250_500.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("FaultCode");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList(
						"16:772", "17:773", "18:774", "19:775", "21:776", "24:777", "33:778", "35:779", "51:786", "64:787", "65:788", "66:789", "67:790",
						"68:791", "69:792", "71:793", "72:794", "96:807", "97:808", "113:809", "115:810", "117:811", "128:812", "129:813", "132:814",
						"133:815", "144:816", "146:817", "147:818", "148:819", "305:780", "336:795", "339:801", "561:781", "592:796", "595:802", "1073:782",
						"1104:797", "1107:803", "2098:783", "2128:798", "2131:804", "4146:784", "4176:799", "4179:805", "8242:785", "8272:800", "8275:806"
					));
				}}
			));

			// Xantrex GT500E model configuration
			put(FAULT_CODE_MODEL_GROUP.XANTREX_GT500E.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("STATUS_FAULT");
					setMechanism(MECHANISM.ENUM_VALUES);
					addFaultCodeMapping(Arrays.asList(
						"16:980", "17:981", "18:982", "19:983", "21:984", "22:985", "23:986", "24:987", "25:988", "32:989", "33:990", "35:991", "51:995",
						"52:996", "53:997", "64:998", "65:999", "66:1000", "67:1001", "68:1002", "69:1003", "71:1004", "72:1005", "73:1006", "84:1007",
						"98:1008", "114:1009", "115:1010", "117:1011", "130:1012", "131:1013", "304:992", "338:1014", "339:1020", "560:993", "594:1015",
						"1072:994", "1106:1016", "2130:1017", "2131:1021", "4178:1018", "8274:1019"
					));
				}}
			));

			// PVH Master model configuration
			put(FAULT_CODE_MODEL_GROUP.PVH_MASTER.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("UPSAlarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2512"));
				}},
				new FaultCodeField(){{
					setFieldName("WindSpeedAlarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2517"));
				}},
				new FaultCodeField(){{
					setFieldName("WindInactivityAlarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2518"));
				}},
				new FaultCodeField(){{
					setFieldName("AnemometerWarning");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2518"));
				}}
			));

			// ADAM 6050 Transformer Specific model configuration
			put(FAULT_CODE_MODEL_GROUP.ADAM_6050_TRANSFORMER_SPECIFIC.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("Vacuum");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2512"));
				}},
				new FaultCodeField(){{
					setFieldName("Pressure");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2517"));
				}},
				new FaultCodeField(){{
					setFieldName("LiquidLevel");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2518"));
				}},
				new FaultCodeField(){{
					setFieldName("LiquidTemperatureHigh");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2518"));
				}},
				new FaultCodeField(){{
					setFieldName("LiquidTemperatureWarning");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2518"));
				}}
			));

			// Protection Relay model configuration
			put(FAULT_CODE_MODEL_GROUP.PROTECTION_RELAY.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("EF_OC_50_51N_1");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3474"));
				}},
				new FaultCodeField(){{
					setFieldName("EF_OC_50_51N_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3475"));
				}},
				new FaultCodeField(){{
					setFieldName("EF_OC_50_51N_5");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3476"));
				}},
				new FaultCodeField(){{
					setFieldName("Overvoltage_59_1");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3477"));
				}},
				new FaultCodeField(){{
					setFieldName("Overvoltage_59_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3478"));
				}},
				new FaultCodeField(){{
					setFieldName("Ph_OC_50_51_1");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3479"));
				}},
				new FaultCodeField(){{
					setFieldName("Ph_OC_50_51_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3480"));
				}},
				new FaultCodeField(){{
					setFieldName("Ph_OC_50_51_3");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3481"));
				}},
				new FaultCodeField(){{
					setFieldName("Underrvoltage_27_1");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3482"));
				}},
				new FaultCodeField(){{
					setFieldName("Underrvoltage_27_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3483"));
				}}
			));

			// Protection Relay V1 model configuration
			put(FAULT_CODE_MODEL_GROUP.PROTECTION_RELAY_V1.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("DI_EF_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3466"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3467"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_OV_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3468"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Phase_A_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3469"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Phase_B_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3470"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Phase_C_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3471"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_SEF_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3472"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_UV_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3473"));
				}}
			));

			// MVPS Huawei model configuration
			put(FAULT_CODE_MODEL_GROUP.MVPS_HUAWEI.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("DI_Circuit_breaker_failure_protection_action");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3545"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LV_Panel_A_ACB_Fault_Tripping");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3546"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LV_Panel_A_Ctrl_System_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3547"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LV_Panel_A_IMD_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3548"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LV_Panel_A_IMD_Warning");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3549"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LV_Panel_A_Ultra_High_Temp_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3550"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LV_Panel_B_ACB_Fault_Tripping");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3551"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LV_Panel_B_Ctrl_System_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3552"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LV_Panel_B_IMD_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3553"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LV_Panel_B_IMD_Warning");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3554"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LV_Panel_B_Ultra_High_Temp_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3555"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LV_Rm_Smoke_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3556"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LV_SPD_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3557"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Maintenance_f_h_f");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3558"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_MV_Rm_Ctrl_System_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3559"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_MV_Rm_LV_Panel_Over_Temp_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3560"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_MV_Rm_Smoke_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3561"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_MV_Rm_Ultra_High_Temp_Alarm_1");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3562"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_MV_Rm_Ultra_High_Temp_Alarm_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3563"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_RMU_SF6_Low_Press");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3564"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_Winding_Temp_too_high");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3565"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_Heavy_Gas");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3566"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_Hi_Oil_Lvl");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3567"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_Hi_Oil_Temp");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3568"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_Hi_Winding_Temp");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3569"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_Light_Gas");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3570"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_Low_Oil_Lvl");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3571"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_Ultra_Hi_Oil_Temp");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3572"));
				}}
			));

			// SMP4 DP model configuration
			put(FAULT_CODE_MODEL_GROUP.SMP4_DP.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("COMM_FAIL_34kv_feed_1_meter_comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2553"));
				}},
				new FaultCodeField(){{
					setFieldName("COMM_FAIL_34kv_feed_2_meter_comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2554"));
				}},
				new FaultCodeField(){{
					setFieldName("COMM_FAIL_34kv_feed_3_meter_comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2555"));
				}},
				new FaultCodeField(){{
					setFieldName("COMM_FAIL_34kv_feed_4_meter_comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2556"));
				}},
				new FaultCodeField(){{
					setFieldName("COMM_FAIL_34kv_outgoing_meter_comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2557"));
				}},
				new FaultCodeField(){{
					setFieldName("COMM_FAIL_34kv_sst_meter_comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2558"));
				}},
				new FaultCodeField(){{
					setFieldName("COMM_FAIL_69kv_meter_comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2559"));
				}},
				new FaultCodeField(){{
					setFieldName("COMM_FAIL_Ge500_rtu_comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2560"));
				}},
				new FaultCodeField(){{
					setFieldName("COMM_FAIL_Idec_plc_comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2561"));
				}},
				new FaultCodeField(){{
					setFieldName("COMM_FAIL_Line_prot_m1_comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2562"));
				}},
				new FaultCodeField(){{
					setFieldName("COMM_FAIL_Ws_comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2563"));
				}},
				new FaultCodeField(){{
					setFieldName("COMM_FAIL_Xf_prot_m1_comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2564"));
				}},
				new FaultCodeField(){{
					setFieldName("COMM_FAIL_Xf_prot_m2_comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2565"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Feed_1_generalFault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2596"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Feed_2_generalFault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2597"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Feed_3_generalFault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2598"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Feed_4_generalFault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2599"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_MVCBGeneralFault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2600"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_SSTGeneralFault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2601"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_DC_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2602"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_GROUND_IOC1_OP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2603"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_GROUND_TOC1_OP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2604"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_IRIG_B_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2605"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_M2_AC_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2606"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_PHASE_IOC1_HIGH_A_OP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2607"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_PHASE_IOC1_HIGH_B_OP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2608"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_PHASE_IOC1_HIGH_C_OP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2609"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_PHASE_IOC1_HIGH_OP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2610"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_PHASE_TOC1_HIGH_A_OP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2611"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_PHASE_TOC1_HIGH_B_OP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2612"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_PHASE_TOC1_HIGH_C_OP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2613"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_PHASE_TOC1_HIGH_OP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2614"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_RELAY_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2615"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M1_VT_FUSE_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2616"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M2_DC_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2617"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M2_G_OC_TRIP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2618"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M2_PH_OC_TRIP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2619"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M2_PT_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2620"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_LINE_M2_RELAY_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2621"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M1_ANY_PKP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2622"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M1_ANY_TRIP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2623"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M1_DC_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2624"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M1_I_DIFF_TRIP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2625"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M1_I_DIFF_TRIP_A");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2626"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M1_I_DIFF_TRIP_B");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2627"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M1_I_DIFF_TRIP_C");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2628"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M1_IRIG_B_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2629"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M1_LOCKOUT");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2630"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M1_M2_AC_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2631"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M1_RELAY_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2632"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M2_ANY_START");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2633"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M2_ANY_TRIP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2634"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M2_ANY_TRIP_A");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2635"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M2_ANY_TRIP_B");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2636"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M2_ANY_TRIP_C");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2637"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M2_AUX_DC_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2638"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M2_CT_FAIL_ALARM");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2639"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M2_DC_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2640"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M2_LOCKOUT");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2641"));
				}},
				new FaultCodeField(){{
					setFieldName("PROT_ALARM_XF_M2_RELAY_FAIL");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2642"));
				}}
			));

			// SMP4 DP V1 model configuration
			put(FAULT_CODE_MODEL_GROUP.SMP4_DP_V1.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M1_Comm_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3392"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M1_General_Start");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3393"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M1_Ground_Overcurrent_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3394"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M1_IRIG_B_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3395"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M1_Phase_Overcurrent_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3396"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M1_Relay_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3397"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M1_Relay_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3398"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M1_VTS_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3399"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M2_Any_Start");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3400"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M2_Comm_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3401"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M2_EF_Trip_50_51N_Stage_1");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3402"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M2_EF_Trip_50_51N_Stage_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3403"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M2_EF_Trip_50_51N_Stage_3");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3404"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M2_EF_Trip_50_51N_Stage_4");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3405"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M2_IRIG_B_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3406"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M2_OC_Trip_50_51_Stage_1");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3407"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M2_OC_Trip_50_51_Stage_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3408"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M2_OC_Trip_50_51_Stage_3");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3409"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M2_OC_Trip_50_51_Stage_4");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3410"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M2_Protection_Disabled");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3411"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_LINE_PROT_M2_VT_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3412"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Meter_69kV_Comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3413"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Meter_Feeder_1_Comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3414"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Meter_Feeder_2_Comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3415"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Meter_Feeder_3_Comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3416"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Meter_Feeder_4_Comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3417"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_Meter_Incomer_Comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3418"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_PLC_Comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3419"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_WS_Comm_fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3420"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Buccholz_of_OLTC_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3421"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Buccholz_of_OLTC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3422"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Buccholz_of_Tank_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3423"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Buccholz_of_Tank_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3424"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Comm_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3425"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Cooling_System_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3426"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Differential_Trip_Biased");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3427"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Differential_Trip_Instant");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3428"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_General_Start");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3429"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_HV_Overcurrent_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3430"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_IRIG_B_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3431"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_LV_Overcurrent_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3432"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Oil_Level_Abn_of_OLTC_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3433"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Oil_Level_Abn_of_Tank_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3434"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Oil_Temp_High_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3435"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Oil_Temp_High_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3436"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Pressure_Relief_of_OLTC_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3437"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Pressure_Relief_of_Tank_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3438"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_REF_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3439"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Relay_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3440"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Relay_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3441"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_VTS_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3442"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Winding_Temp_High_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3443"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M1_Winding_Temp_High_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3444"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_Any_Start");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3445"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_Comm_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3446"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_HV_GOC_Stage_1");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3447"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_HV_GOC_Stage_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3448"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_HV_GOC_Stage_3");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3449"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_HV_GOC_Stage_4");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3450"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_HV_POC_Stage_1");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3451"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_HV_POC_Stage_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3452"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_HV_POC_Stage_3");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3453"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_HV_POC_Stage_4");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3454"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_Idiff_Trip_Biased");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3455"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_IRIG_B_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3456"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_LV_GOC_Stage_1");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3457"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_LV_GOC_Stage_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3458"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_LV_GOC_Stage_3");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3459"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_LV_GOC_Stage_4");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3460"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_LV_POC_Stage_1");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3461"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_LV_POC_Stage_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3462"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_LV_POC_Stage_3");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3463"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_LV_POC_Stage_4");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3464"));
				}},
				new FaultCodeField(){{
					setFieldName("DI_XF_PROT_M2_REF_Trip_LV");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3465"));
				}}
			));

			// SUN2000 330KTL-H1 model configuration
			put(FAULT_CODE_MODEL_GROUP.SUN2000_330KTL_H1.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("alm_1_Abnormal_String_Power");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3484"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_AFCI_Self_Check_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3485"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_DC_Arc_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3486"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_Grid_Loss");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3487"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_Grid_Overfrequency");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3488"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_Grid_Overvoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3489"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_Grid_Underfrequency");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3490"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_Grid_Undervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3491"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_Grid_Volt_Imbalance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3492"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_High_String_Input_Voltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3493"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_Output_DC_Component");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3494"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_Output_Overcurrent");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3495"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_Phase_Wire_Short_Circuited");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3496"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_String_Current_Backfeed");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3497"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_String_Reverse_Connection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3498"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_1_Unstable_Grid_Frequency");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3499"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Abnormal_Grounding");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3500"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Abnormal_PV_module_config");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3501"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Abnormal_Residual_Current");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3502"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Active_Islanding");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3503"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Churn_output_overload");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3504"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Device_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3505"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Faulty_Monitoring_Unit");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3506"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Faulty_Power_Collector");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3507"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_License_Expired");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3508"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Low_Insulation_Resistance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3509"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Overtemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3510"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Passive_Islanding");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3511"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Peripheral_port_short_circuit");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3512"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Transient_AC_Overvoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3513"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_2_Upgrade_Failed_or_Version");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3514"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_Abnormal_CT_wiring");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3515"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_Active_scheduling_instruction");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3516"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_Built_in_PID_operation_abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3517"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_DC_arc_fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3518"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_DC_Protection_Unit_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3519"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_DC_switch_is_abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3520"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_EL_Cel_Abnormality");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3521"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_External_Fan_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3522"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_High_input_string_voltage_to_ground");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3523"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_Internal_Fan_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3524"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_On_grid_Off_grid_controller_abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3525"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_Optimizer_fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3526"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_PV_String_Loss");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3527"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_3_Reactive_power_dispatch_instruction_abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3528"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_AC_side_phase_sequence_reversal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3529"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_Communication_link_failure_protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3530"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_DC_overvoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3531"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_DC_reverse_connection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3532"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_DC_serial_connection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3533"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_DC_short_circuit_or_reverse_connection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3534"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_DC_unreliable_connection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3535"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_Parallel_communication_exception");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3536"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_String_short_circuit_to_ground");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3537"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_The_AC_terminal_temperature_is_abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3538"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_The_local_access_certificate_does_not_take_effect");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3539"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_The_local_access_certificate_is_about_to_expired");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3540"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_The_management_system_does_not_take_effect");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3541"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_The_management_system_has_Expired");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3542"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_4_The_management_system_Is_About_to_Expire");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3543"));
				}},
				new FaultCodeField(){{
					setFieldName("alm_5_The_DC_terminal_temperature_is_abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3544"));
				}}
			));

			// OrionMX Automation Platform model configuration
			put(FAULT_CODE_MODEL_GROUP.ORIONMX_AUTOMATION_PLATFORM.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M1_Bias_Differential_Trip_87T");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3573"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M1_CT_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3574"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M1_HV_Ground_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3575"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M1_HV_Phase_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3576"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M1_Inst_Differential_Trip_87T");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3577"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M1_LV_Ground_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3578"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M1_LV_Phase_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3579"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M1_VT_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3580"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M2_CT_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3581"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M2_Differential_Trip_87T");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3582"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M2_HV_Ground_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3583"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M2_HV_Phase_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3584"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M2_LV_Ground_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3585"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M2_LV_Phase_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3586"));
				}},
				new FaultCodeField(){{
					setFieldName("HV_ALARMS_XF_M2_VT_Fail");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3587"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_01_Ground_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3588"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_01_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3589"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_01_OV_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3590"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_01_Phase_A_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3591"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_01_Phase_B_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3592"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_01_Phase_C_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3593"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_01_UV_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3594"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_01_VTS_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3595"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_02_Ground_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3596"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_02_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3597"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_02_OV_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3598"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_02_Phase_A_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3599"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_02_Phase_B_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3600"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_02_Phase_C_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3601"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_02_UV_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3602"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_02_VTS_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3603"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_03_Ground_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3604"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_03_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3605"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_03_OV_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3606"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_03_Phase_A_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3607"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_03_Phase_B_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3608"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_03_Phase_C_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3609"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_03_UV_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3610"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_03_VTS_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3611"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_04_Ground_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3612"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_04_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3613"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_04_OV_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3614"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_04_Phase_A_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:36015"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_04_Phase_B_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3616"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_04_Phase_C_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3617"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_04_UV_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3618"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Feeder_04_VTS_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3619"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Main_Ground_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3620"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Main_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3621"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Main_OV_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3622"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Main_Phase_A_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3623"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Main_Phase_B_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3624"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Main_Phase_C_OC_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3625"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Main_UV_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3626"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_ALARMS_Main_VTS_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3627"));
				}}
			));

			// Sungrow SG6250HV-MV V1 model configuration
			put(FAULT_CODE_MODEL_GROUP.SUNGROW_SG6250HV_MV_V1.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("M1ASAC_Switch_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3212"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASBypass_Board_Communication_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3213"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASContactor_Contact_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3214"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASCT_Unbalance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3215"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASDC_Bypass_Forward_Overcurrent_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3216"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASDC_Sensor_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3217"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASDC_Switch_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3218"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASElectricity_Meter_Communication_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3219"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASExternal_Power_Supply_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3220"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASFan_2_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3221"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASFan_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3222"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASGFRT_Operation");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3223"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASGround_Fuse_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3224"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASLow_Insulation_Resistance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3225"));
				}},
				new FaultCodeField(){{
					setFieldName("M1ASTemperature_Abnormal_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3226"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1AC_Leakage_Current_Protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3227"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1AC_Overcurrent");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3228"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1AC_Overvoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3229"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1AC_Undervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3230"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Contactor_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3231"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1DC_Fuse_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3232"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1DC_Leakage_Current_Protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3233"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1DC_Overcurrent");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3234"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1DC_Overvoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3235"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1DC_Undervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3236"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Detection_Fuse_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3237"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Fan_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3238"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Frequency_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3239"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1GFDI_Protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3240"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Hardware_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3241"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Heat_Sik_Over_Temperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3242"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Islanding_Protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3243"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Module_Overtemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3244"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Overfrequency");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3245"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Overload_Protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3246"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1PDP_Protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3247"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Reactor_Overtemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3248"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Sensor_Failure");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3249"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Temperature_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3250"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Transformer_Overtemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3251"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS1Underfrequency");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3252"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2AC_Current_Unbalance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3253"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2AC_SPD_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3254"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2DC_SPD_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3255"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2PV_Polarity_Reversed");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3256"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2AC_Fuse_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3257"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2AC_Switch_Disconnection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3258"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2AC_Switch_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3259"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Backup_Power_Supply_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3260"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Buffer_Contactor_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3261"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Bypass_Reverse_Overcurrent");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3262"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Control_Cabinet_Temperature_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3263"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Control_Power_Supply_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3264"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Current_Unbalance_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3265"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Current_Unbalance_3");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3266"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2DC_Cabinet_Over_Temperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3267"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2DC_Fuse_Grounding_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3268"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2DC_Injection_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3269"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2DC_Switch_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3270"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2DC_Switch_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3271"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2DC_Voltage_Sampling_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3272"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Drive_Board_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3273"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Fan_2_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3274"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Grid_DC_Injection_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3275"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Grid_Voltage_Unbalance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3276"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Insulation_Impedance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3277"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Reverse_Charging_Power_Supply_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3278"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Sampling_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3279"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS2Soft_Start_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3280"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS3Carrier_Sync_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3281"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS3Device_Code_Repeat_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3282"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS3Neutral_Point_Potential_Shift");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3283"));
				}},
				new FaultCodeField(){{
					setFieldName("M1FS3Parallel_Operation_Communication_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3284"));
				}},
				new FaultCodeField(){{
					setFieldName("M1NS1ACB_Fault_State");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3285"));
				}},
				new FaultCodeField(){{
					setFieldName("M1WSEmergency_Stop");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3286"));
				}},
				new FaultCodeField(){{
					setFieldName("M1WSFault_Stop");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3287"));
				}},
				new FaultCodeField(){{
					setFieldName("M1WSIO_DSP_Communication_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3288"));
				}},
				new FaultCodeField(){{
					setFieldName("M1WSIO_MDC_Communication_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3289"));
				}},
				new FaultCodeField(){{
					setFieldName("M1WSStopped");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3290"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASAC_Switch_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3291"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASBypass_Board_Communication_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3292"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASContactor_Contact_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3293"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASCT_Unbalance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3294"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASDC_Bypass_Forward_Overcurrent_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3295"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASDC_Sensor_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3296"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASDC_Switch_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3297"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASElectricity_Meter_Communication_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3298"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASExternal_Power_Supply_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3299"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASFan_2_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3300"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASFan_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3301"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASGFRT_Operation");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3302"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASGround_Fuse_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3303"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASLow_Insulation_Resistance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3304"));
				}},
				new FaultCodeField(){{
					setFieldName("M2ASTemperature_Abnormal_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3305"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1AC_Leakage_Current_Protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3306"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1AC_Overcurrent");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3307"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1AC_Overvoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3308"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1AC_Undervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3309"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Contactor_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3310"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1DC_Fuse_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3311"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1DC_Leakage_Current_Protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3312"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1DC_Overcurrent");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3313"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1DC_Overvoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3314"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1DC_Undervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3315"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Detection_Fuse_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3316"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Fan_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3317"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Frequency_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3318"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1GFDI_Protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3319"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Hardware_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3320"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Heat_Sik_Over_Temperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3321"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Islanding_Protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3322"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Module_Overtemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3323"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Overfrequency");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3324"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Overload_Protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3325"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1PDP_Protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3326"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Reactor_Overtemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3327"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Sensor_Failure");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3328"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Temperature_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3329"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Transformer_Overtemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3330"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS1Underfrequency");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3331"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2AC_Current_Unbalance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3332"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2AC_SPD_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3333"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2DC_SPD_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3334"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2PV_Polarity_Reversed");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3335"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2AC_Fuse_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3336"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2AC_Switch_Disconnection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3337"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2AC_Switch_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3338"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Backup_Power_Supply_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3339"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Buffer_Contactor_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3340"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Bypass_Reverse_Overcurrent");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3341"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Control_Cabinet_Temperature_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3342"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Control_Power_Supply_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3343"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Current_Unbalance_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3344"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Current_Unbalance_3");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3345"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2DC_Cabinet_Over_Temperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3346"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2DC_Fuse_Grounding_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3347"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2DC_Injection_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3348"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2DC_Switch_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3349"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2DC_Switch_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3350"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2DC_Voltage_Sampling_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3351"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Drive_Board_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3352"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Fan_2_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3353"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Grid_DC_Injection_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3354"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Grid_Voltage_Unbalance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3355"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Insulation_Impedance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3356"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Reverse_Charging_Power_Supply_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3357"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Sampling_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3358"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS2Soft_Start_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3359"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS3Carrier_Sync_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3360"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS3Device_Code_Repeat_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3361"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS3Neutral_Point_Potential_Shift");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3362"));
				}},
				new FaultCodeField(){{
					setFieldName("M2FS3Parallel_Operation_Communication_Fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3363"));
				}},
				new FaultCodeField(){{
					setFieldName("M2NS1ACB_Fault_State");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3364"));
				}},
				new FaultCodeField(){{
					setFieldName("M2WSEmergency_Stop");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3365"));
				}},
				new FaultCodeField(){{
					setFieldName("M2WSFault_Stop");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3366"));
				}},
				new FaultCodeField(){{
					setFieldName("M2WSIO_DSP_Communication_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3367"));
				}},
				new FaultCodeField(){{
					setFieldName("M2WSIO_MDC_Communication_Abnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3368"));
				}},
				new FaultCodeField(){{
					setFieldName("M2WSStopped");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3369"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_Coil_Temp_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3370"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_Coil_Temp_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3371"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_Gas_Relay_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3372"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_Gas_Relay_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3373"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_Low_Oil_Level_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3374"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_Low_Oil_Level_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3375"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_MV_DS");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3376"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_MV_Load_Switch_1");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3377"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_MV_Load_Switch_2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3378"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_MV_VCB");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3379"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_Oil_Temp_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3380"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_Oil_Temp_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3381"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_Overcurrent_Protection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3382"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_Pressure_Relief_Alarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3383"));
				}},
				new FaultCodeField(){{
					setFieldName("MV_NODE_STATE_Pressure_Relief_Trip");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3384"));
				}},
				new FaultCodeField(){{
					setFieldName("WORK_STATE_DOOR_OPEN_PROT");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3385"));
				}},
				new FaultCodeField(){{
					setFieldName("WORK_STATE_EXTERNAL_EMER_STOP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3386"));
				}},
				new FaultCodeField(){{
					setFieldName("WORK_STATE_LOCAL_EMER_STOP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3387"));
				}},
				new FaultCodeField(){{
					setFieldName("WORK_STATE_MV_FAULT");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3388"));
				}},
				new FaultCodeField(){{
					setFieldName("WORK_STATE_REM_EMER_STOP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3389"));
				}},
				new FaultCodeField(){{
					setFieldName("WORK_STATE_SMOKE_PROT");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3390"));
				}},
				new FaultCodeField(){{
					setFieldName("WORK_STATE_STOPPED");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3391"));
				}}
			));

			// Sungrow PV 24H SCB model configuration
			put(FAULT_CODE_MODEL_GROUP.SUNGROW_PV_24H_SCB.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_01");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3054"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_02");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3055"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_03");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3056"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_04");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3057"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_05");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3058"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_06");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3059"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_07");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3060"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_08");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3061"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_09");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3062"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_10");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3063"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_11");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3064"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_12");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3065"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_13");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3066"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_14");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3067"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_15");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3068"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_16");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3069"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_17");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3070"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_18");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3071"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_19");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3072"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_20");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3073"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_21");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3074"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_22");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3075"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_23");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3076"));
				}},
				new FaultCodeField(){{
					setFieldName("FUSE_BLOW_24");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3077"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_01");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3078"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_02");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3079"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_03");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3080"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_04");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3081"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_05");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3082"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_06");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3083"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_07");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3084"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_08");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3085"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_09");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3086"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_10");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3087"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_11");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3088"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_12");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3089"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_13");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3090"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_14");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3091"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_15");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3092"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_16");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3093"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_17");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3094"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_18");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3095"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_19");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3096"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_20");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3097"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_21");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3098"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_22");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3099"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_23");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3100"));
				}},
				new FaultCodeField(){{
					setFieldName("HIGH_CURRENT_24");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3101"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_01");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3102"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_02");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3103"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_03");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3104"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_04");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3105"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_05");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3106"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_06");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3107"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_07");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3108"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_08");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3109"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_09");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3110"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_10");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3111"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_11");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3112"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_12");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3113"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_13");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3114"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_14");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3115"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_15");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3116"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_16");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3117"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_17");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3118"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_18");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3119"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_19");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3120"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_20");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3121"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_21");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3122"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_22");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3123"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_23");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3124"));
				}},
				new FaultCodeField(){{
					setFieldName("LOW_CURRENT_24");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3125"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_01");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3126"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_02");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3127"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_03");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3128"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_04");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3129"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_05");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3130"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_06");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3131"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_07");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3132"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_08");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3133"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_09");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3134"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_10");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3135"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_11");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3136"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_12");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3137"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_13");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3138"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_14");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3139"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_15");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3140"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_16");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3141"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_17");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3142"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_18");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3143"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_19");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3144"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_20");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3145"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_21");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3146"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_22");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3147"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_23");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3148"));
				}},
				new FaultCodeField(){{
					setFieldName("OPEN_CIRCUIT_24");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3149"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_01");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3150"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_02");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3151"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_03");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3152"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_04");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3153"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_05");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3154"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_06");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3155"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_07");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3156"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_08");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3157"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_09");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3158"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_10");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3159"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_11");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3160"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_12");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3161"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_13");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3162"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_14");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3163"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_15");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3164"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_16");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3165"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_17");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3166"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_18");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3167"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_19");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3168"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_20");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3169"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_21");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3170"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_22");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3171"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_23");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3172"));
				}},
				new FaultCodeField(){{
					setFieldName("REVERSE_CURRENT_24");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3173"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_01");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3174"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_02");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3175"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_03");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3176"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_04");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3177"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_05");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3178"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_06");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3179"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_07");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3180"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_08");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3181"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_09");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3182"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_10");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3183"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_11");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3184"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_12");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3185"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_13");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3186"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_14");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3187"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_15");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3188"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_16");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3189"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_17");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3190"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_18");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3191"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_19");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3192"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_20");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3193"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_21");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3194"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_22");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3195"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_23");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3196"));
				}},
				new FaultCodeField(){{
					setFieldName("SHORT_CIRCUIT_24");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3197"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_FUSE_BLOW");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3198"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_HI_CURRENT");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3199"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_HI_DC_VOLT");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3200"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_HI_TEMP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3201"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_LOW_CURRENT");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3202"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_OPEN_CIRCUIT");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3203"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_REV_CURRENT");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3204"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_SELF_TEST_TRIP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3205"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_SHORT_CIRCUIT");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3206"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_SHUNT_TRIP_COMMAND");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3207"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_SPD_FAULT");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3208"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_SWITCH_TRIP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3209"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_SWITCH_TRIP_ENABLE");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3210"));
				}},
				new FaultCodeField(){{
					setFieldName("STAT_TRIP");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:3211"));
				}}
			));

			// Sungrow SH6250HV-MV model configuration
			put(FAULT_CODE_MODEL_GROUP.SUNGROW_SH6250HV_MV.getGroupId(), Arrays.asList(
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_accircuitbreakerabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2568"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_acspdalarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2569"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_antipidpowersupplyabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2570"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_bypasscircuitbreakerabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2571"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_bypassfuseabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2572"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_contactorcontactabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2573"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_ctunbalance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2574"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_dcbypassforwardovercurrentalarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2575"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_dcbypassreserveovercurrentalarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2576"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_dcfuseabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2577"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_dcsensorabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2578"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_dcspdalarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2579"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_dcswitchabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2580"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_dspmdccommunicationabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2581"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_energymetercommunicationabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2582"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_externalpowersupplyabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2583"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_fan2anomaly");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2584"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_fanabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2585"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_frequencydeviationactivepowerregulation");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2586"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_gfrtoperation");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2587"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_groundfuseabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2588"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_insulationboardcommunicationanomaly");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2589"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_lowinsulationresistance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2590"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_temperatureabnormalalarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2591"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_temperatureandhumidityboardcommerror");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2592"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_tributaryboardcommunicationerror");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2593"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_alarm_state_voltagedeviationreactivepowerregulation");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2594"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_acleakagecurrentprotection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2645"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_acovercurrent");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2646"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_acovervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2647"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_acundervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2648"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_busovervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2649"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_busundervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2650"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_contactorfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2651"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_dcfusefault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2652"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_dcleakagecurrentprotection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2653"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_dcovercurrent");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2654"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_dcovervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2655"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_dcundervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2656"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_detectionfusefault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2657"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_fanfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2658"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_frequencyabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2659"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_gfdiprotection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2660"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_groundingfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2661"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_hardwarefault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2662"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_inverterovervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2663"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_islandingprotection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2664"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_moduleovertemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2665"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_overfrequency");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2666"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_overloadprotection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2667"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_pdpprotection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2668"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_reactorovertemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2669"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_sensorfailure");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2670"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_temperatureabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2671"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_transformerovertemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2672"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_1_underfrequency");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2673"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_accurrentunbalance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2674"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_acspdfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2675"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_dcspdfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2676"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_pvpolarityreversed");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2677"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_accabinetovertemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2678"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_acfusefault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2679"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_acswitchdisconnection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2680"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_acswitchfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2681"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_backuppowersupplyabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2682"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_buffercontactorfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2683"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_carriersyncfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2684"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_controlcabinettemperatureabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2685"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_controlpowersupplyabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2686"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_currentunbalance2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2687"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_currentunbalance3");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2688"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_dccabinetovertemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2689"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_dcfusegroundingfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2690"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_dcinjectionfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2691"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_dcswitchfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2692"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_dcvoltagesamplingfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2693"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_devicecoderepeatfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2694"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_driveboardfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2695"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_fan2fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2696"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_gridvoltageunbalance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2697"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_insulationimpedance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2698"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_neutralpointpotentialshift");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2699"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_paralleloperation");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2700"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_samplingfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2701"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_fault_state_2_softstartfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2702"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_node_state_1_acbfaultstate");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2703"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_work_state_emergencystop");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2704"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_work_state_faultstop");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2705"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_work_state_iodspcommunicationabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2706"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_work_state_iomdccommunicationabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2707"));
				}},
				new FaultCodeField(){{
					setFieldName("m1_work_state_stopped");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2708"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_accircuitbreakerabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2709"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_acspdalarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2710"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_antipidpowersupplyabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2711"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_bypasscircuitbreakerabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2712"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_bypassfuseabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2713"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_contactorcontactabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2714"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_ctunbalance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2715"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_dcbypassforwardovercurrentalarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2716"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_dcbypassreserveovercurrentalarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2717"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_dcfuseabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2718"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_dcsensorabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2719"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_dcspdalarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2720"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_dcswitchabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2721"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_dspmdccommunicationabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2722"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_energymetercommunicationabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2723"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_externalpowersupplyabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2724"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_fan2anomaly");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2725"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_fanabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2726"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_frequencydeviationactivepowerregulation");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2727"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_gfrtoperation");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2728"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_groundfuseabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2729"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_insulationboardcommunicationanomaly");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2730"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_lowinsulationresistance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2731"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_temperatureabnormalalarm");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2732"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_temperatureandhumidityboardcommerror");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2733"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_tributaryboardcommunicationerror");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2734"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_alarm_state_voltagedeviationreactivepowerregulation");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2735"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_acleakagecurrentprotection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2736"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_acovercurrent");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2737"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_acovervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2738"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_acundervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2739"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_busovervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2740"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_busundervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2741"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_contactorfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2742"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_dcfusefault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2743"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_dcleakagecurrentprotection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2744"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_dcovercurrent");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2745"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_dcovervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2746"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_dcundervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2747"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_detectionfusefault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2748"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_fanfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2749"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_frequencyabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2750"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_gfdiprotection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2751"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_groundingfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2752"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_hardwarefault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2753"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_inverterovervoltage");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2754"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_islandingprotection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2755"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_moduleovertemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2756"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_overfrequency");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2757"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_overloadprotection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2758"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_pdpprotection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2759"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_reactorovertemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2760"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_sensorfailure");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2761"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_temperatureabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2762"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_transformerovertemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2763"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_1_underfrequency");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2764"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_accurrentunbalance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2765"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_acspdfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2766"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_dcspdfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2767"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_pvpolarityreversed");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2768"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_accabinetovertemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2769"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_acfusefault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2770"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_acswitchdisconnection");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2771"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_acswitchfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2772"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_backuppowersupplyabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2773"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_buffercontactorfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2774"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_carriersyncfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2775"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_controlcabinettemperatureabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2776"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_controlpowersupplyabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2777"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_currentunbalance2");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2778"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_currentunbalance3");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2779"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_dccabinetovertemperature");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2780"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_dcfusegroundingfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2781"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_dcinjectionfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2782"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_dcswitchfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2783"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_dcvoltagesamplingfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2784"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_devicecoderepeatfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2785"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_driveboardfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2786"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_fan2fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2787"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_gridvoltageunbalance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2788"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_insulationimpedance");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2789"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_neutralpointpotentialshift");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2790"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_paralleloperation");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2791"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_samplingfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2792"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_fault_state_2_softstartfault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2793"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_work_state_emergencystop");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2794"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_work_state_faultstop");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2795"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_work_state_iodspcommunicationabnormal");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2796"));
				}},
				new FaultCodeField(){{
					setFieldName("m2_work_state_stopped");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2797"));
				}},
				new FaultCodeField(){{
					setFieldName("work_state_door_open_prot");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2798"));
				}},
				new FaultCodeField(){{
					setFieldName("work_state_external_emer_stop");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2799"));
				}},
				new FaultCodeField(){{
					setFieldName("work_state_local_emer_stop");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2800"));
				}},
				new FaultCodeField(){{
					setFieldName("work_state_mv_fault");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2801"));
				}},
				new FaultCodeField(){{
					setFieldName("work_state_rem_emer_stop");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2802"));
				}},
				new FaultCodeField(){{
					setFieldName("work_state_smoke_prot");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2803"));
				}},
				new FaultCodeField(){{
					setFieldName("work_state_stopped");
					setMechanism(MECHANISM.VALUE_ON_OFF);
					addFaultCodeMapping(Arrays.asList("1:2804"));
				}}
			));


		}};
		return map;
	}

	/**
	 * @description Represents a query parameter for device fault codes, including the field name, bit index, error ID, alias, and error alias.
	 * FaultCodeQueryParam
	 */
	public static class FaultCodeQueryParam {
		private String fieldName;
		private Integer bitIndex;
		private Integer errorId;
		private String alias;
		private String errorAlias;
		public String getFieldName() {
			return fieldName;
		}
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		
		public Integer getBitIndex() {
			return bitIndex;
		}
		public void setBitIndex(Integer bitIndex) {
			this.bitIndex = bitIndex;
		}
		
		public Integer getErrorId() {
			return errorId;
		}
		public void setErrorId(Integer errorId) {
			this.errorId = errorId;
		}
		
		public String getAlias() {
			return alias;
		}
		public void setAlias(String alias) {
			this.alias = alias;
		}
		
		public String getErrorAlias() {
			return errorAlias;
		}
		public void setErrorAlias(String errorAlias) {
			this.errorAlias = errorAlias;
		}

	}
	/**
	 * @description Represents a field in the device fault code mapping, including the field name, mechanism, and fault code mappings.
	 * FaultCodeField
	 */
	public static class FaultCodeField {
		private String fieldName;
		private MECHANISM mechanism;
		private Map<Integer, Integer> faultCodesMapping;
		public String getFieldName() {
			return fieldName;
		}
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		public MECHANISM getMechanism() {
			return mechanism;
		}
		public void setMechanism(MECHANISM mechanism) {
			this.mechanism = mechanism;
		}
		public Map<Integer, Integer> getFaultCodesMapping() {
			return faultCodesMapping;
		}
		public void setFaultCodesMapping(Map<Integer, Integer> faultCodesMapping) {
			this.faultCodesMapping = faultCodesMapping;
		}
		public void addFaultCodeMapping(List<String> keyValuePairs) {
			if (keyValuePairs == null || keyValuePairs.isEmpty()) {
				return;
			}
			if (this.faultCodesMapping == null) {
				this.faultCodesMapping = new HashMap<>();
			}
			for (String keyValuePair : keyValuePairs) {
				if(keyValuePair == null || !keyValuePair.contains(":")) {
					log.warn("Invalid key-value pair for fault code mapping: " + keyValuePair);
					return;
				}
				String[] parts = keyValuePair.split(":");
				Integer key = Integer.parseInt(parts[0]);
				Integer value = Integer.parseInt(parts[1]);
				this.faultCodesMapping.put(key, value);
			}
		}
	}

	/**
	 * @description Represents a record of a device fault code, including device ID, time, field, index, value, previous value, error ID, and group index.
	 * DeviceFaultCodeRecord
	 */
	public static class DeviceFaultCodeRecord {
		private int id_device;
		private String time;
		private String field;
		private int index;
		private int value;
		private int pre_value;
		private int error_id;
		private int group_idx;
		
		public int getId_device() {
			return id_device;
		}
		public void setId_device(int id_device) {
			this.id_device = id_device;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public int getPre_value() {
			return pre_value;
		}
		public void setPre_value(int pre_value) {
			this.pre_value = pre_value;
		}
		public int getError_id() {
			return error_id;
		}
		public void setError_id(int error_id) {
			this.error_id = error_id;
		}
		public int getGroup_idx() {
			return group_idx;
		}
		public void setGroup_idx(int group_idx) {
			this.group_idx = group_idx;
		}
		
	}
}
