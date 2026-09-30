package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.CronJobSchedulerEntity;
import com.nwm.api.entities.DeviceAlertDetectEntity;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CronJobCloseDeviceNoProdStatusService extends DB {
    private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobCloseDeviceNoProdStatus");
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private static final int TIME_NO_PROD_THRESHOLD_MINUTES = 120;
    private static final int NO_PROD_ERROR_CODE = 1000;
    @Value("${cron.device.alert.noproduction.close.maxthread:1}")
    private int MAX_SITE_THREADS = 1;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private ThreadPoolExecutor siteExecutor;
    Instant nowInstant = Instant.now();

    private ThreadPoolExecutor createSiteExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                MAX_SITE_THREADS, MAX_SITE_THREADS,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    @PostConstruct
    public void init() {
        nowInstant = Instant.from(LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC));
        siteExecutor = createSiteExecutor();
        log.info("CronJobCloseDeviceNoProdStatusService initialized. Current UTC time: "
                + formatter.withZone(ZoneOffset.UTC).format(nowInstant));
    }

    public void execute() {
        if (!isRunning.compareAndSet(false, true)) {
            log.info("Close No Production check is already running. Skipping this execution.");
            return;
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("error_code", NO_PROD_ERROR_CODE);
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
            log.error("Error in Close No Production Check: " + e.getMessage());
            e.printStackTrace();
        } finally {
            isRunning.set(false);
        }
    }

    private Map<String, Object> processAlert(AlertEntity alert) throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("data_table_name", alert.getDataTableName());
        params.put("id_device", alert.getId_device());
        params.put("time_alert_start", alert.getStart_date());
        params.put("time_no_comm_threshold_minutes", TIME_NO_PROD_THRESHOLD_MINUTES);
        DeviceAlertDetectEntity eventItem = (DeviceAlertDetectEntity) queryForObject(
                "CronJobDetectDeviceStatus.checkDeviceIsProd", params);
        if (eventItem == null) {
            log.info("Device is still no production, skip for device id: " + alert.getId_device() + ", data table: "
                    + alert.getDataTableName());
            return params;
        }
        log.info("Closed No Prod alert for device id:" + alert.getId_device() + ", alert time: " + alert.getStart_date()
                + ", alert id: " + alert.getId() + ", data table: " + alert.getDataTableName() + ", end time: "
                + eventItem.getStart_time());
        alert.setEnd_date(eventItem.getStart_time());
        alert.setNote("Auto Close Alert Production By Cronjob");
        // close alert no production device
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
            CronJobSchedulerEntity jobEntity = (CronJobSchedulerEntity) queryForObject(
                    "CronJobScheduler.getJobScheduler", jobCode);
            if (jobEntity == null) {
                jobEntity = new CronJobSchedulerEntity();
                jobEntity.setJob_code(jobCode);
                jobEntity.setJob_name("Close Device No Production Status");
                jobEntity.setLast_start_time(Date.from(nowInstant));
                jobEntity.setLast_status(status);
                jobEntity.setDeseription("Close Device No Production Status");
                insert("CronJobScheduler.insertJobScheduler", jobEntity);
            } else {
                if ("START".equals(trigger)) {
                    jobEntity.setLast_start_time(Date.from(nowInstant));
                }
                if ("END".equals(trigger)) {
                    jobEntity.setLast_end_time(Date.from(nowInstant));
                }
                jobEntity.setLast_status(status);
                update("CronJobScheduler.updateJobScheduler", jobEntity);
            }
        } catch (Exception e) {
            log.error("Error updating job scheduler status: " + e.getMessage(), e);
        }
    }
}
