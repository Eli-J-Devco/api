/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.BaseAlertEnum;
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
    private static final String THREAD_NAME_PREFIX = "field-alert";

    private final ThreadPoolExecutor siteExecutor = createSiteExecutor();
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    @Autowired
    private TriggerAlertService triggerAlertService;

    @Autowired
    private ModelAdvancedEnergySolaronService modelAdvancedEnergySolaronService;

    @FunctionalInterface
    private interface AlertChecker {
        void check(String tableName, String time, int deviceId);
    }

    private static final Map<String, AlertChecker> ALERT_REGISTRY = new LinkedHashMap<>();
    private static final Set<String> BINARY_FAULT_CODE_MODELS = new HashSet<>();

    private <E extends Enum<E> & BaseAlertEnum> void registerModel(String tableKey, E[] alertEnums) {
        ALERT_REGISTRY.put(tableKey, (tableName, time, deviceId) ->
                triggerAlertService.checkTriggerAlert(tableName, time, deviceId, alertEnums));
    }

    /**
     * @description Register a binary fault code model (like ModelAdvancedEnergySolaron)
     * These models use binary fault codes instead of individual alert fields
     * @since 2026-04-24
     */
    private void registerBinaryFaultCodeModel(String tableKey, Object serviceInstance) {
        BINARY_FAULT_CODE_MODELS.add(tableKey);
        ALERT_REGISTRY.put(tableKey, (tableName, time, deviceId) -> {
            try {
                // Use reflection to call checkTriggerAlertFromCronJob method
                serviceInstance.getClass()
                    .getMethod("checkTriggerAlertFromCronJob", String.class, String.class, int.class)
                    .invoke(serviceInstance, tableName, time, deviceId);
            } catch (Exception e) {
                log.error("Error calling checkTriggerAlertFromCronJob for " + tableKey, e);
            }
        });
    }

    @PostConstruct
    public void init() {
        String localhost = Lib.getPrivateIP();
        hostnameToServerIds.put(serverName1, server1RunOnId);
        hostnameToServerIds.put(serverName2, server2RunOnId);

        if (localhost != null && !localhost.equals(serverName1) && !localhost.equals(serverName2)) {
            hostnameToServerIds.put(localhost, serverLocalRunOnId);
        }

        registerModel("model_SMP4_DP",                     ModelSMP4DPService.AlertEnum.values());
        registerModel("model_SMP4_DPV1",                   ModelSMP4DPV1Service.AlertEnum.values());
        registerModel("model_MVPS_HUAWEI",                 ModelMVPSHUAWEIService.AlertEnum.values());
        registerModel("model_sungrow_sh6250hv_mv",         ModelSungrowSh6250hvMvService.AlertEnum.values());
        registerModel("model_sungrow_pv_24h_scb",          ModelSungrowPv24hScbService.AlertEnum.values());
        registerModel("model_protection_relay",            ModelProtectionRelayService.AlertEnum.values());
        registerModel("model_protection_relay_v1",         ModelProtectionRelayV1Service.AlertEnum.values());
        registerModel("model_SUN2000330KTLH1",             ModelSUN2000330KTLH1Service.AlertEnum.values());
        registerModel("model_SUNGROW_SG6250HV_MV_V1",      ModelSUNGROWSG6250HVMVV1Service.AlertEnum.values());
        registerModel("model_OrionMX_Automation_Platform", ModelOrionMXAutomationPlatformService.AlertEnum.values());

        // Register binary fault code models (use binary fault codes instead of individual fields)
        registerBinaryFaultCodeModel("model_advanced_energy_solaron", modelAdvancedEnergySolaronService);

        log.info("CronJobAlertFieldService initialized. Alert registry covers "
                + ALERT_REGISTRY.size() + " model(s): " + ALERT_REGISTRY.keySet());
        log.info("Binary fault code models: " + BINARY_FAULT_CODE_MODELS);
    }


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

    /**
     * @description Entry point for field-level alert cron job.
     * Processes ALL models that have an AlertEnum registered in ALERT_REGISTRY.
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
     * @description Process each site: get all alert-enabled devices (any model)
     * and check alerts for each device based on its model type.
     * @author duc.pham
     * @since 2026-04-21
     * @param site SiteEntity
     */
    private void processSite(SiteEntity site) {
        log.info("[Site " + site.getId() + "] START processSite");
        try {
            List<DeviceEntity> devices = queryForList("CronJobAlertField.getListDeviceBySite", site);
            if (devices == null || devices.isEmpty()) {
                log.info("[Site " + site.getId() + "] SKIP - no alert-enabled devices found");
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
     * @description Resolve the correct AlertEnum[] for a device based on its
     * device_group.table_name, then delegate to TriggerAlertService.
     *
     * Supported models (add new entries to ALERT_REGISTRY to extend):
     *   Field-based models (120-minute window):
     *   model_SMP4_DP                    – 60 alerts (COMM_FAIL / DI / PROT_ALARM)
     *   model_SMP4_DPV1                  – 65 alerts (DI protection relay)
     *   model_MVPS_HUAWEI                – 28 alerts (DI circuit breaker / relay)
     *   model_sungrow_sh6250hv_mv        – 237 alerts (m1/m2 alarm/fault/work states)
     *   model_sungrow_pv_24h_scb         – 158 alerts (FUSE_BLOW / HIGH_CURRENT / …)
     *   model_protection_relay           – 10 alerts (EF_OC / Ph_OC / voltage)
     *   model_protection_relay_v1        – 8 alerts  (DI trip alerts)
     *   model_SUN2000330KTLH1            – 61 alerts (alm_1 … alm_5)
     *   model_SUNGROW_SG6250HV_MV_V1     – 167 alerts (M1/M2 AS/FS/NS/WS states)
     *   model_OrionMX_Automation_Platform– 63 alerts (HV_ALARMS / MV_ALARMS)
     *
     *   Binary fault code models (120-minute window, 20+ occurrences):
     *   model_advanced_energy_solaron    – Binary fault codes (active_faults1/2/3, status, warnings1, limits)
     *
     * @author duc.pham
     * @since 2026-04-21
     * @param device DeviceEntity (must have datatablename and device_group_table populated)
     */
    private void checkAlertByDevice(DeviceEntity device) {
        if (device == null || device.getId() <= 0 || device.getDatatablename() == null) {
            log.info("[Device null/invalid] SKIP");
            return;
        }

        String groupTable = device.getDevice_group_table();
        if (groupTable == null || groupTable.isEmpty()) {
            log.warn("[Device " + device.getId() + "] SKIP - device_group_table is null/empty");
            return;
        }

        AlertChecker checker = ALERT_REGISTRY.get(groupTable);
        if (checker == null) {
            log.info("[Device " + device.getId() + "] SKIP - no AlertEnum registered for model: " + groupTable);
            return;
        }

        log.info("[Device " + device.getId() + "] START checkAlertByDevice"
                + " - model=" + groupTable
                + " table=" + device.getDatatablename());
        try {
            // DB stores time in UTC
            String currentTime = ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).format(DATE_FMT);
            log.info("[Device " + device.getId() + "] currentTime(UTC)=" + currentTime);

            checker.check(device.getDatatablename(), currentTime, device.getId());

            log.info("[Device " + device.getId() + "] END checkAlertByDevice - OK");
        } catch (Exception ex) {
            log.error("[Device " + device.getId() + "] checkAlertByDevice error: " + ex.getMessage(), ex);
        }
    }
}
