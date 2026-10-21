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
	private static final List<String> LIST_FAULT_TABLE_NAME = Arrays.asList("model_SMP4_DP", "model_SMP4_DPV1", "model_MVPS_HUAWEI", 
		"model_OrionMX_Automation_Platform", "model_protection_relay", "model_protection_relay_v1", "model_SUN2000330KTLH1", 
		"model_SUNGROW_SG6250HV_MV_V1", "model_sungrow_pv_24h_scb", "model_sungrow_sh6250hv_mv", "model_advanced_energy_solaron", 
		"model_chint_solectria_inverter_class9725", "model_PVH_Tbox", "model_phoenix_contact_quint_ups", "model_Kehua_SPI50_60K_inverter", 
		"model_xgi150", "model_satcon_pvs357_inverter", "model_abb_trio_class6210", "model_xantrex_gt100_250_500", "model_xantrex_gt500", 
		"model_ATI_Tracker_Motor", "model_pv_powered_35_50_260_500kw_inverter", "model_ivt_solaron_ext", "model_satcon_powergate_225_inverter", 
		"model_sev_sg110cx", "model_sma_shp7510", "model_smartlogger3000", "model_sma_stp_25_50_us_50", "model_sma_core", "model_huawei_sun2000v1", 
		"model_ADAM6050_Transformer_Specific", "model_PVH_Master", "model_xantrex_gt500e");
	
	

	

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

	private Instant nowInstant;

	@PostConstruct
	public void init() {
		deviceFaultCodesConfigure();
		siteExecutor = createSiteExecutor();
		String localhost = Lib.getPrivateIP();
		hostnameToServerIds.put(serverName1, server1RunOnId);
		hostnameToServerIds.put(serverName2, server2RunOnId);

		if (localhost != null && !localhost.equals(serverName1) && !localhost.equals(serverName2)) {
			hostnameToServerIds.put(localhost, serverLocalRunOnId);
		}
	}

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
	@SuppressWarnings("unchecked")
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
				params.put("apply_sunset_sunrise_to_cf_window", device.getApply_sunset_sunrise_to_cf_window());

				// check device last data status
        AlertEntity lastDataStatus = (AlertEntity) queryForObject("CronJobDetectDeviceStatus.getDeviceLastDataStatus", params);
				// if the last data status indicates a slow response, create an alert for the device with the last data time as the start date
				if (lastDataStatus != null && lastDataStatus.getIs_slow_response() == 1) {
					log.info("Device id: " + device.getId() + " is slow response, last data time: " + lastDataStatus.getStart_date());
					// Check if an alert already exists for this device and error combination
					boolean isExists = checkExistsAlertItem(params);
					if (isExists) {
						log.info("Alert event record already exists, skip create event AlertEntity id_device: " + device.getId() + ", start_date: " + lastDataStatus.getStart_date());
						continue;
					}
          // If the final status is identified as Slow Response, check whether there was a No Communication status before it and determine when the No Communication period started.
          String noCommStartTime = lastDataStatus.getStart_date();
          if (lastDataStatus.getError() != 0) {
            params.put("reference_time", lastDataStatus.getStart_date());
            noCommStartTime = (String) queryForObject("CronJobDetectDeviceStatus.findNoCommStartTime", params);
            if (Lib.isBlank(noCommStartTime)) {
              log.info("The issue no communication is from initial state, using min start time for device id: " + device.getId() + ", data table: " + device.getDatatablename());
              noCommStartTime = (String) queryForObject("CronJobDetectDeviceStatus.findMinStartTime", params);
            }
          }

					AlertEntity alertEntity = buildAlertEntity(device, noCommStartTime);
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
				AlertEntity alertEntity = buildAlertEntity(device, eventItem.getStart_time());
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
	private AlertEntity buildAlertEntity(DeviceEntity device, String startTime) {
		AlertEntity alertEntity = new AlertEntity();
		alertEntity.setId_device(device.getId());
		alertEntity.setId_error(device.getId_error());
		alertEntity.setStart_date(startTime);
		alertEntity.setIs_slow_response(0);
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

	private Map<Integer, List<FaultCodeField>> deviceFaultCodesConfigure(){
		Map<Integer, List<FaultCodeField>> map = new HashMap<Integer, List<FaultCodeField>>(){{
			// ATI Tracker Motor model configuration
			put(173,Arrays.asList(
				new FaultCodeField(){{
					setFiledName("Alarms");
					setMechanism(MECHANISM.BIT_ON_OFF);
					setFaultCodesMapping(new HashMap<Integer, Integer>() {{
						put(0, 2548);
						put(1, 2549);
						put(2, 2550);
						put(3, 2551);
						put(4, 2552);
					}});
				}}
			));
			// ABB Trio Class 6210 model configuration
			put(17,Arrays.asList(
				new FaultCodeField(){{
					setFiledName("StatesByte0");
					setMechanism(MECHANISM.ENUM_VALUES);
					setFaultCodesMapping(new HashMap<Integer, Integer>() {{
						put(0, 247);
						put(1, 248);
						put(2, 249);
						put(3, 250);
						put(4, 251);
						put(5, 252);
						put(6, 253);
						put(7, 254);
						put(8, 255);
						put(9, 256);
						put(10, 257);
						put(11, 258);
						put(12, 259);
						put(13, 260);
						put(14, 261);
						put(15, 262);
						put(16, 263);
						put(17, 264);
						put(18, 265);
						put(19, 266);
						put(20, 267);
						put(21, 268);
						put(22, 269);
						put(23, 270);
						put(24, 271);
						put(25, 272);
						put(26, 273);
						put(27, 274);
						put(30, 275);
						put(31, 276);
						put(32, 277);
						put(33, 278);
						put(34, 279);
						put(35, 280);
						put(36, 281);
						put(37, 282);
						put(38, 283);
						put(98, 284);
						put(99, 285);
						put(100, 286);
						put(101, 287);
						put(110, 288);
						put(111, 289);
						put(112, 290);
						put(113, 291);
						put(114, 292);
						put(115, 293);
						put(116, 294);
						put(117, 295);
						put(150, 296);
						put(200, 297);
					}});
				}},
				new FaultCodeField(){{
					setFiledName("StatesByte1");
					setMechanism(MECHANISM.ENUM_VALUES);
					setFaultCodesMapping(new HashMap<Integer, Integer>() {{
						put(0, 298);
						put(1, 299);
						put(2, 300);
						put(3, 301);
						put(4, 302);
						put(5, 303);
						put(6, 304);
						put(7, 305);
						put(8, 306);
						put(9, 307);
						put(10, 308);
						put(11, 309);
						put(12, 310);
						put(13, 311);
						put(14, 312);
						put(15, 313);
						put(16, 314);
						put(17, 315);
						put(18, 316);
						put(19, 317);
						put(20, 318);
						put(21, 319);
						put(22, 320);
						put(23, 321);
						put(24, 322);
						put(25, 323);
						put(26, 324);
						put(27, 325);
						put(28, 326);
						put(29, 327);
						put(30, 328);
						put(31, 329);
						put(40, 330);
						put(41, 331);
						put(42, 332);
						put(43, 333);
						put(44, 334);
						put(45, 335);
						put(46, 336);
						put(47, 337);
						put(255, 338);
					}});
				}},
				new FaultCodeField(){{
					setFiledName("StatesByte2");
					setMechanism(MECHANISM.ENUM_VALUES);
					setFaultCodesMapping(new HashMap<Integer, Integer>() {{
						put(0, 339);
						put(1, 340);
						put(2, 341);
						put(3, 342);
						put(4, 343);
						put(5, 344);
						put(6, 345);
						put(7, 346);
						put(8, 347);
						put(9, 348);
						put(10, 349);
						put(11, 350);
						put(12, 351);
						put(13, 352);
						put(14, 353);
						put(15, 354);
						put(16, 355);
						put(17, 356);
						put(18, 357);
						put(19, 358);
						put(255, 359);
					}});
				}},
				new FaultCodeField(){{
					setFiledName("StatesByte4");
					setMechanism(MECHANISM.ENUM_VALUES);
					setFaultCodesMapping(new HashMap<Integer, Integer>() {{
						put(0, 360);
						put(1, 361);
						put(2, 362);
						put(3, 363);
						put(4, 364);
						put(5, 365);
						put(6, 366);
						put(7, 367);
						put(8, 368);
						put(9, 369);
						put(10, 370);
						put(11, 371);
						put(12, 372);
						put(13, 373);
						put(14, 374);
						put(15, 375);
						put(16, 376);
						put(17, 377);
						put(18, 378);
						put(19, 379);
						put(20, 380);
						put(21, 381);
						put(22, 382);
						put(23, 383);
						put(24, 384);
						put(25, 385);
						put(26, 386);
						put(27, 387);
						put(28, 388);
						put(29, 389);
						put(30, 390);
						put(31, 391);
						put(32, 392);
						put(33, 393);
						put(34, 394);
						put(35, 395);
						put(36, 396);
						put(37, 397);
						put(38, 398);
						put(39, 399);
						put(40, 400);
						put(41, 401);
						put(42, 402);
						put(43, 403);
						put(44, 404);
						put(45, 405);
						put(46, 406);
						put(47, 407);
						put(48, 408);
						put(49, 409);
						put(50, 410);
						put(51, 411);
						put(52, 412);
						put(53, 413);
						put(54, 414);
						put(55, 415);
						put(56, 416);
						put(57, 417);
						put(58, 418);
						put(59, 419);
						put(60, 420);
						put(61, 421);
						put(62, 422);
						put(63, 423);
						put(64, 424);
						put(65, 425);
						put(66, 326);
						put(67, 427);
						put(68, 428);
						put(69, 429);
						put(70, 430);
						put(71, 431);
						put(72, 432);
						put(73, 433);
						put(74, 434);
						put(75, 435);
						put(76, 436);
						put(77, 437);
						put(79, 438);
						put(80, 439);
						put(81, 440);
						put(82, 441);
						put(83, 442);
					}});
				}}
			));
		}};
		return map;
	}

	public static class FaultCodeField {
		private String filedName;
		private MECHANISM mechanism;
		private Map<Integer, Integer> faultCodesMapping;
		public String getFiledName() {
			return filedName;
		}
		public void setFiledName(String filedName) {
			this.filedName = filedName;
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
	}
}
