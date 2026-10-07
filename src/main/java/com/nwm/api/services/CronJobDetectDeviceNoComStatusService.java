/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.CronJobSchedulerEntity;
import com.nwm.api.entities.DeviceAlertDetectEntity;
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
public class CronJobDetectDeviceNoComStatusService extends DB {

	private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobDetectDeviceNoCom");
	private final AtomicBoolean isRunning = new AtomicBoolean(false);
	private static final int TIME_NO_COMM_THRESHOLD_MINUTES = 120;
	private static final int DATALOGER_ID_DEVICE_TYPE = 5;
	private static final int CELL_MODEM_ID_DEVICE_TYPE = 10;
	private static final int CAMERA_ID_DEVICE_TYPE = 19;
	private static final int NO_COMM_ERROR_CODE = 1001; // Assuming 1001 is the error code for no communication
    private static final int NO_COMM_ERROR_LEVEL = 33; // id error level of no communication

	@Value ("${cron.device.alert.nocomm.maxthread:10}")
	private int MAX_SITE_THREADS;
    @Value ("${cron.device.alert.nocomm.time.threshold.addition:20}")
    private int TIME_QUERY_NO_COMM_THRESHOLD_MINUTES_ADDITION;


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

	private Instant nowInstant;

	@PostConstruct
	public void init() {
		siteExecutor = createSiteExecutor();
		nowInstant = Instant.from(LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC));
		String localhost = Lib.getPrivateIP();
		hostnameToServerIds.put(serverName1, server1RunOnId);
		hostnameToServerIds.put(serverName2, server2RunOnId);

		if (localhost != null && !localhost.equals(serverName1) && !localhost.equals(serverName2)) {
			hostnameToServerIds.put(localhost, serverLocalRunOnId);
		}
	}

	public void execute() {
		if (!isRunning.compareAndSet(false, true)) {
			log.info("No Communication check is already running. Skipping this execution.");
			return;
		}

		try {
			String hostname = Lib.getPrivateIP();
			List<Integer> serverIds = hostnameToServerIds.get(hostname);
			if (serverIds == null || serverIds.isEmpty()) {
				return;
			}
            
			Map<String, Object> params = new HashMap<>();
			params.put("serverIds", serverIds);
            params.put("error_level", NO_COMM_ERROR_LEVEL);
			List<?> listSites = queryForList("CronJobDetectDeviceStatus.getListSiteByServer", params);
			if (listSites == null || listSites.isEmpty()) {
				return;
			}
			List<Integer> siteIds = listSites.stream().map(site -> (SiteEntity) site)
			.map(s -> s.getId())
			.collect(Collectors.toList());
			String ids = siteIds.stream().map(String::valueOf).collect(Collectors.joining(", "));
			log.info("Process sites: "+ ids);
			params.put("siteIds", siteIds);
			params.put("error_code", NO_COMM_ERROR_CODE);
			params.put("time_execute", formatter.withZone(ZoneOffset.UTC).format(nowInstant));
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
					t.setName(oldName + "-detect-no-comm-status-" + siteId);
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
			log.error("Error in runNoCommunicationCheck: " + e.getMessage());
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
            int alertThreshold = dataloger.getCf_alert_threshold() > 0 ? dataloger.getCf_alert_threshold() : TIME_NO_COMM_THRESHOLD_MINUTES;
						boolean isNoComm = lastUpdated.isBefore(jobStartInstant.minus(alertThreshold, ChronoUnit.MINUTES));
						log.info("Datalogger " + datalogerSerial + " is no communication: " + isNoComm);
						if (isNoComm) {
							// Handle no communication scenario for the datalogger
							AlertEntity alertEntity = new AlertEntity();
							alertEntity.setId_device(dataloger.getId_device());
							alertEntity.setId_error(dataloger.getId_error());
							alertEntity.setStart_date(dataloger.getLast_updated());

							AlertEntity alertItem = (AlertEntity) queryForObject("CronJobDetectDeviceStatus.getExistsAlertEvent", dataloger);
							// List<AlertEntity> alertItemQueue = queryForList("CronJobDetectDeviceStatus.checkAlertQueueExits", alertEntity);
							// Check if the alert already exists before inserting a new alert
							if (alertItem != null ){
								log.info("Alert event record already exists, skip create event AlertEntity id_device: "+ alertItem.getId_device()+", start_date: "+ alertItem.getStart_date());
								return;
							}
							log.info("Inserting alert into queue for device: " + dataloger.getId_device());
							log.debug("alertItem: id_device=" + alertEntity.getId_device() + ", id_error=" + alertEntity.getId_error() + ", start_date=" + alertEntity.getStart_date());
							insertAlert(alertEntity);
							return;
						}
					}
				}
					
				List<DeviceEntity> devicesForDataloger = devices.get(datalogerSerial);
				if(devicesForDataloger == null || devicesForDataloger.isEmpty()) {
					continue;
				}
				// Process devices for this dataloger
				checkNoCommByDevices(devicesForDataloger);
			}
		} catch (Exception e) {
			log.error("Error: " + e.getMessage(), e);
		}
	}

	/**
	 * @description process no communication detection for a list of devices
	 * @param {List<DeviceEntity>} devices
	 */
	@SuppressWarnings("unchecked")
	private void checkNoCommByDevices(List<DeviceEntity> devices) {
		if (devices == null || devices.isEmpty()) {
			return;
		}
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
    //         .withZone(ZoneId.systemDefault());
		Map<String, Object> params = new HashMap<>();
		params.put("time_execute", formatter.withZone(ZoneOffset.UTC).format(nowInstant));
		for (DeviceEntity device : devices) {
			try {
				// Skip processing for certain device types
				if(device.getId_device_type() == DATALOGER_ID_DEVICE_TYPE || 
						device.getId_device_type() == CELL_MODEM_ID_DEVICE_TYPE || 
						device.getId_device_type() == CAMERA_ID_DEVICE_TYPE) {
					continue;
				}
				// If the site has a threshold configured for the alert, use the configured value; otherwise, use the default value.
				params.put("time_query_no_comm_threshold_minutes", TIME_QUERY_NO_COMM_THRESHOLD_MINUTES_ADDITION + TIME_NO_COMM_THRESHOLD_MINUTES);
				params.put("time_no_comm_threshold_minutes", TIME_NO_COMM_THRESHOLD_MINUTES);
				int cfAlertThreshold = device.getCf_alert_threshold();
				if (cfAlertThreshold > 0) {
						params.put("time_query_no_comm_threshold_minutes", TIME_QUERY_NO_COMM_THRESHOLD_MINUTES_ADDITION + cfAlertThreshold);
						params.put("time_no_comm_threshold_minutes", cfAlertThreshold);
				}

				params.put("id_device", device.getId());
				params.put("data_table_name", device.getDatatablename());
				params.put("id_error", device.getId_error());
				params.put("apply_sunset_sunrise_to_cf_window", device.getApply_sunset_sunrise_to_cf_window());

				// check device last data status
				Map<String, Object> lastDataStatus = (Map<String, Object>) queryForObject("CronJobDetectDeviceStatus.getDeviceLastDataStatus", params);
				// if the last data status indicates a slow response, create an alert for the device with the last data time as the start date
				if (lastDataStatus != null && lastDataStatus.get("is_slow_response") != null 
				&& Integer.valueOf(lastDataStatus.get("is_slow_response").toString()) == 1) {
					log.info("Device id: " + device.getId() + " is slow response, last data time: " + lastDataStatus.get("time").toString());
					// Check if an alert already exists for this device and error combination
					boolean isExists = checkExistsAlertItem(params);
					if (isExists) {
						log.info("Alert event record already exists, skip create event AlertEntity id_device: " + device.getId() + ", start_date: " + lastDataStatus.get("time").toString());
						continue;
					}
					AlertEntity alertEntity = buildAlertEntity(device, lastDataStatus.get("time").toString(), 1);
					insertAlert(alertEntity);
					continue;
				}

				// Query the database to detect no communication by device
				DeviceAlertDetectEntity eventItem = (DeviceAlertDetectEntity) queryForObject("CronJobDetectDeviceStatus.detectNoCommByDevice", params);
				// If no communication is not detected, skip this device
				if (eventItem == null) {
					log.info("No communication is not detected, skip for device id: " + device.getId() + ", data table: " + device.getDatatablename());
					continue;
				}
				// Get the start time of no communication for this device
				params.put("reference_time", eventItem.getStart_time());
				String noCommStartTime = (String) queryForObject("CronJobDetectDeviceStatus.findNoCommStartTime", params);
				if (Lib.isBlank(noCommStartTime)) {
					log.info("The issue no communication is from initial state, using min start time for device id: " + device.getId() + ", data table: " + device.getDatatablename());
					noCommStartTime = (String) queryForObject("CronJobDetectDeviceStatus.findMinStartTime", params);
				}
				eventItem.setStart_time(noCommStartTime);

				// Check if an alert already exists for this device and error combination
				boolean isExists = checkExistsAlertItem(params);
				if (isExists) {
					log.info("Alert event record already exists, skip create event AlertEntity id_device: " + device.getId() + ", start_date: " + eventItem.getStart_time());
					continue;
				}
				// Prepare the alert entity for insertion into the alert queue
				AlertEntity alertEntity = buildAlertEntity(device, eventItem.getStart_time(), 0);
				log.info("Inserting alert into queue for device: " + device.getId());
				log.debug("alertItem: id_device=" + alertEntity.getId_device() + ", id_error=" + alertEntity.getId_error() + ", start_date=" + alertEntity.getStart_date());
				insertAlert(alertEntity);

			} catch (Exception ex) {
				log.error("checkDataloggerIsNotResponding error: " + ex.getMessage(), ex);
			}
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
	private AlertEntity buildAlertEntity(DeviceEntity device, String startTime, int isSlowResponse) {
		AlertEntity alertEntity = new AlertEntity();
		alertEntity.setId_device(device.getId());
		alertEntity.setId_error(device.getId_error());
		alertEntity.setStart_date(startTime);
		alertEntity.setIs_slow_response(isSlowResponse);
		alertEntity.setCreated_by("CronJobDetectDeviceNoComStatusService");
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
				jobEntity.setJob_name("Detect Device No Communication Status");
				jobEntity.setLast_start_time(Date.from(Instant.now()));
				jobEntity.setLast_status(status);
				jobEntity.setDeseription("Detect Device No Communication Status");
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


}
