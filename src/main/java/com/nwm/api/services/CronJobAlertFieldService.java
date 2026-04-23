/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CronJobAlertFieldService extends DB {

    private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobAlertField");
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
    private static final int MAX_SITE_THREADS = 10;
    private static final String THREAD_NAME_PREFIX = "smp4dp-alert";

    private final ThreadPoolExecutor siteExecutor = createSiteExecutor();
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    @Autowired
    private TriggerAlertService triggerAlertService;

    private static ThreadPoolExecutor createSiteExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                MAX_SITE_THREADS, MAX_SITE_THREADS,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                r -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                }
        );
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down siteExecutor thread pool");
        siteExecutor.shutdown();
        try {
            if (!siteExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                log.warn("siteExecutor did not terminate in the specified time.");
                siteExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("Interrupted during siteExecutor shutdown", e);
            siteExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
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

    @PostConstruct
    public void init() {
        String localhost = Lib.getPrivateIP();
        hostnameToServerIds.put(serverName1, server1RunOnId);
        hostnameToServerIds.put(serverName2, server2RunOnId);

        if (localhost != null && !localhost.equals(serverName1) && !localhost.equals(serverName2)) {
            hostnameToServerIds.put(localhost, serverLocalRunOnId);
        }
    }

    /**
     * @description Entry point for SMP4DP alert cron job.
     * @author duc.pham
     * @since 2026-04-21
     */
    public void runAlertCheck() {
        if (!isRunning.compareAndSet(false, true)) {
            log.info("===== runAlertCheck SKIPPED - already running =====");
            return;
        }

        long startTime = System.currentTimeMillis();
        log.info("===== CronJobAlertField START =====");

        try {
            String hostname = Lib.getPrivateIP();
            log.info("Hostname: " + hostname);

            List<Integer> serverIds = hostnameToServerIds.get(hostname);
            if (serverIds == null || serverIds.isEmpty()) {
                log.info("No serverIds found for hostname: " + hostname + " - SKIP");
                return;
            }
            log.info("ServerIds: " + serverIds);

            Map<String, Object> params = new HashMap<>();
            params.put("serverIds", serverIds);

            List<?> listSites = queryForList("CronJobAlertField.getListSiteByServer", params);
            if (listSites == null || listSites.isEmpty()) {
                log.info("No sites found for serverIds: " + serverIds + " - SKIP");
                return;
            }
            log.info("Found " + listSites.size() + " site(s) to process");

            List<Future<?>> futures = new ArrayList<>();

            for (Object o : listSites) {
                SiteEntity site = (SiteEntity) o;
                log.info("Submitting site id=" + site.getId() + " name=" + site.getName());
                try {
                    futures.add(siteExecutor.submit(() -> {
                        Thread t = Thread.currentThread();
                        String oldName = t.getName();
                        t.setName(THREAD_NAME_PREFIX + "-site-" + site.getId());
                        try { processSite(site); }
                        catch (Exception e) { log.error("Site " + site.getId() + " unhandled error: " + e.getMessage(), e); }
                        finally { t.setName(oldName); }
                    }));
                } catch (RejectedExecutionException rex) {
                    log.error("Site " + site.getId() + " task rejected: " + rex.getMessage());
                }
            }

            for (Future<?> f : futures) {
                try { f.get(); }
                catch (InterruptedException ie) { Thread.currentThread().interrupt(); break; }
                catch (ExecutionException ee) { log.error("Site task execution error: " + ee.getMessage(), ee); }
            }

        } catch (Exception e) {
            log.error("Error in runAlertCheck: " + e.getMessage(), e);
        } finally {
            isRunning.set(false);
            long duration = System.currentTimeMillis() - startTime;
            log.info("===== CronJobAlertField END - duration: " + duration + "ms =====");
        }
    }

    /**
     * @description Process each site: get SMP4DP devices and check all alerts.
     * No datalogger check needed for SMP4DP.
     * @author duc.pham
     * @since 2026-04-21
     * @param site SiteEntity
     */
    private void processSite(SiteEntity site) {
        log.info("[Site " + site.getId() + "] START processSite");
        try {
            List<DeviceEntity> devices = queryForList("CronJobAlertField.getListDeviceBySite", site);
            if (devices == null || devices.isEmpty()) {
                log.info("[Site " + site.getId() + "] SKIP - no SMP4DP devices found");
                return;
            }

            log.info("[Site " + site.getId() + "] Found " + devices.size() + " device(s)");

            for (DeviceEntity device : devices) {
                checkAlertByDevice(device);
            }

            log.info("[Site " + site.getId() + "] END processSite - OK");
        } catch (Exception e) {
            log.error("[Site " + site.getId() + "] processSite error: " + e.getMessage(), e);
        }
    }

    /**
     * @description Check all 60 AlertEnum columns for a single SMP4DP device.
     * Delegates to TriggerAlertService which handles:
     *- COMM_FAIL_*  (13 errors): communication failure per sub-device
     *- DI_*         (6 errors) : digital input general faults
     *- PROT_ALARM_* (41 errors): protection relay alarms
     * @author duc.pham
     * @since 2026-04-21
     * @param device DeviceEntity
     */
    private void checkAlertByDevice(DeviceEntity device) {
        if (device == null || device.getId() <= 0 || device.getDatatablename() == null) {
            log.info("[Device null/invalid] SKIP");
            return;
        }

        log.info("[Device " + device.getId() + "] START checkAlertByDevice - table=" + device.getDatatablename());
        try {
            // DB stores time in UTC
            String currentTime = ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).format(DATE_FMT);
            log.info("[Device " + device.getId() + "] currentTime(UTC)=" + currentTime + " - checking " + ModelSMP4DPService.AlertEnum.values().length + " alert columns");

            triggerAlertService.checkTriggerAlert(
                    device.getDatatablename(),
                    currentTime,
                    device.getId(),
                    ModelSMP4DPService.AlertEnum.values()
            );

            log.info("[Device " + device.getId() + "] END checkAlertByDevice - OK");
        } catch (Exception ex) {
            log.error("[Device " + device.getId() + "] checkAlertByDevice error: " + ex.getMessage(), ex);
        }
    }
}
