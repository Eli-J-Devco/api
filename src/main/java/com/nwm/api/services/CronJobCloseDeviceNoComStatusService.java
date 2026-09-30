package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.*;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CronJobCloseDeviceNoComStatusService extends DB {
    private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobDetectDeviceNoCom");
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private static final int TIME_CLOSE_NO_COMM_THRESHOLD_MINUTES = 120;
    private static final int DATALOGER_ID_DEVICE_TYPE = 5;
    private static final int CELL_MODEM_ID_DEVICE_TYPE = 10;
    private static final int NO_COMM_ERROR_CODE = 1001;
    @Value ("${cron.device.alert.nocomm.close.maxthread:1}")
    private int MAX_SITE_THREADS = 1;

    private ThreadPoolExecutor siteExecutor;

    private ThreadPoolExecutor createSiteExecutor() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
				MAX_SITE_THREADS, MAX_SITE_THREADS,
				60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>());
		executor.allowCoreThreadTimeOut(true);
		return executor;
	}

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    Instant nowInstant = Instant.now();



    @PostConstruct
    public void init() {
        nowInstant = Instant.from(LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC));
        siteExecutor = createSiteExecutor();
        log.info("CronJobCloseDeviceNoComStatusService initialized. Current UTC time: " + formatter.withZone(ZoneOffset.UTC).format(nowInstant));
    }

    public void execute() {
        if (!isRunning.compareAndSet(false, true)) {
            log.info("Close No Communication check is already running. Skipping this execution.");
            return;
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("error_code", NO_COMM_ERROR_CODE);
            List<AlertEntity> listOpenAlert = (List<AlertEntity>) queryForList("CronJobDetectDeviceStatus.getListOpenAlert", params);
            if (listOpenAlert == null || listOpenAlert.isEmpty()) {
                return;
            }
            for (AlertEntity alert : listOpenAlert) {
                if (alert == null || Lib.isBlank(alert.getDataTableName()) || !Lib.isBlank(alert.getEnd_date())) {
                    continue;
                }
                siteExecutor.submit(() -> processAlert(alert));
            }
        } catch (Exception e) {
            log.error("Error in Close No Communication Check: " + e.getMessage(), e);
        } finally {
            isRunning.set(false);
        }
    }

    private Map<String, Object> processAlert(AlertEntity alert) throws SQLException {
        if (alert == null) {
            return null;
        }
        int alertThreshold = alert.getCfAlertThreshold() > 0 ? alert.getCfAlertThreshold() : TIME_CLOSE_NO_COMM_THRESHOLD_MINUTES;
        Map<String, Object> params;
        params = new HashMap<>();
        params.put("time_no_comm_threshold_minutes", alertThreshold);
        params.put("data_table_name", alert.getDataTableName());
        params.put("id_device", alert.getId_device());
        params.put("time_execute", formatter.withZone(ZoneOffset.UTC).format(nowInstant));

        if (alert.getId_device_type_int() == DATALOGER_ID_DEVICE_TYPE) {
            // check no comm status of device datalogger
            ModelDataloggerEntity datalogger = (ModelDataloggerEntity) queryForObject("CronJobDetectDeviceStatus.getLastTimeResponseDatalogger", params);
            LocalDateTime localDateTime = LocalDateTime.parse(datalogger.getTime(), formatter.withZone(ZoneOffset.UTC));
            Instant lastUpdated = localDateTime.toInstant(ZoneOffset.UTC);
            LocalDateTime alertLocalDateTime = LocalDateTime.parse(datalogger.getTime(), formatter.withZone(ZoneOffset.UTC));
            Instant alertStartInstant = alertLocalDateTime.toInstant(ZoneOffset.UTC);
            // check if last updated time is after threshold time
            boolean isAfterThreshold = lastUpdated.isAfter(alertStartInstant.plus(alertThreshold, ChronoUnit.MINUTES));
            if (!isAfterThreshold) {
                log.info("Device dataloger is still [no communication] state and has not returned to normal operation for a continuous period of more than " + alertThreshold +
                    " minutes since the alert was issued: {dataloger: " + 
                    alert.getId_device() + ", device: " + datalogger.getId_device() + ", site: " + alert.getId_site() + ", data table: " + datalogger.getDatatablename() + "}");
                return params;
            }
            // check device of site to make sure datalogger is communication
            List<Integer> siteIds = new ArrayList<>();
            siteIds.add(alert.getId_site());
            params.put("siteIds", siteIds);
            params.put("serial_number", datalogger.getSerialnumber());
            // get list device of datalogger with condition: site_id and serial number
            List<?> listDevicesQuery = queryForList("CronJobDetectDeviceStatus.getListDeviceBySiteIds", params);
            List<DeviceEntity> listDevices = listDevicesQuery.stream()
                    .map(device -> (DeviceEntity) device)
                    .filter(device -> device.getId() != alert.getId_device() && device.getId_device_type() != CELL_MODEM_ID_DEVICE_TYPE)
                    .collect(Collectors.toList());
            String noCommEndTime = null;
            // loop through list device to check if any device is communication to close no comm alert for datalogger
            for (DeviceEntity device : listDevices) {
                Map<String, Object> deviceParams = new HashMap<>();
                deviceParams.put("id_device", device.getId());
                deviceParams.put("data_table_name", device.getDatatablename());
                deviceParams.put("time_alert_start", alert.getStart_date());
                noCommEndTime = (String) queryForObject("CronJobDetectDeviceStatus.getDeviceNoCommReturnedTime", deviceParams);
                if (!Lib.isBlank(noCommEndTime)) {
                    log.info("Detect device of dataloger is returned normal: {dataloger: " + alert.getId_device() + ", device: " + device.getId() + ", site: " + alert.getId_site() + ", data table: " + device.getDatatablename() + "}");
                    break;
                }
            }
            if (!Lib.isBlank(noCommEndTime)) {
                log.info("Closed alert for dataloger id:"+ alert.getId_device()+", alert time: "+ alert.getStart_date() +", alert id: "+ alert.getId() +", data table: "+ alert.getDataTableName() +", end time: "+ noCommEndTime);
                alert.setNote("Batch job detect dataloger is returned responding");
                alert.setEnd_date(noCommEndTime);
                update("CronJobDetectDeviceStatus.closeAlert", alert);
            }
            return params;
        }
        params.put("time_alert_start", alert.getStart_date());
        // check device is returned normal after no comm alert
        DeviceAlertDetectEntity eventItem = (DeviceAlertDetectEntity) queryForObject("CronJobDetectDeviceStatus.detectDeviceNoCommReturnedNormal", params);
        if (eventItem == null) {
            log.info("Device is still no communication, skip for device id: " + alert.getId_device() + ", data table: " + alert.getDataTableName());
            return params;
        }

        // close alert no communication device
        log.info("Closed alert for device id:"+ alert.getId_device()+", alert time: "+ alert.getStart_date() +", alert id: "+ alert.getId() +", data table: "+ alert.getDataTableName() +", end time: "+ eventItem.getStart_time());
        alert.setEnd_date(eventItem.getStart_time());
        alert.setNote("Batch job detect device is returned normal");
        update("CronJobDetectDeviceStatus.closeAlert", alert);
        return params;
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
