/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.BaseAlertEnum;
import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.bitcode.ModelAbbTrioClass6210AlertConfig;
import com.nwm.api.services.bitcode.ModelAdvancedEnergySolaronAlertConfig;
import com.nwm.api.services.bitcode.ModelChintSolectriaAlertConfig;
import com.nwm.api.services.bitcode.ModelKehuaSPI5060KAlertConfig;
import com.nwm.api.services.bitcode.ModelPhoenixContactQuintUPSAlertConfig;
import com.nwm.api.services.bitcode.ModelPVHTboxAlertConfig;
import com.nwm.api.services.bitcode.ModelSatconPvs357AlertConfig;
import com.nwm.api.services.bitcode.ModelXantrexGT100250500AlertConfig;
import com.nwm.api.services.bitcode.ModelXantrexGT500EAlertConfig;
import com.nwm.api.services.bitcode.ModelXGI1500AlertConfig;
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
    private TriggerAlertBitCodeService triggerAlertBitCodeService;

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
     * @description create thread pool executor for parallel site processing
     * @author duc.pham
     * @since 2026-04-21
     * @return ThreadPoolExecutor with daemon threads
     */
    private static ThreadPoolExecutor createSiteExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                MAX_SITE_THREADS, MAX_SITE_THREADS,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                r -> {
                    Thread t = new Thread(r);
                    t.setName(THREAD_NAME_PREFIX + "-idle");
                    t.setDaemon(true);
                    return t;
                }
        );
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    /**
     * @description initialize hostname-to-serverIds mapping on startup
     * @author duc.pham
     * @since 2026-04-21
     */
    @PostConstruct
    public void init() {
        String localhost = Lib.getPrivateIP();
        hostnameToServerIds.put(serverName1, server1RunOnId);
        hostnameToServerIds.put(serverName2, server2RunOnId);

        if (localhost != null && !localhost.equals(serverName1) && !localhost.equals(serverName2)) {
            hostnameToServerIds.put(localhost, serverLocalRunOnId);
        }
        log.info("CronJobAlertFieldService initialized. hostname=" + localhost
                + " mappings=" + hostnameToServerIds.size());
    }

    /**
     * @description gracefully shutdown thread pool on application stop
     * @author duc.pham
     * @since 2026-04-21
     */
    @PreDestroy
    public void shutdown() {
        log.info("Shutting down siteExecutor thread pool");
        siteExecutor.shutdown();
        try {
            if (!siteExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                log.warn("siteExecutor did not terminate in 30s, forcing shutdown");
                siteExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("Interrupted during siteExecutor shutdown", e);
            siteExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * @description entry point for field alert cron job, called every 1 minute by BatchConfig_AlertField.
     *              Uses AtomicBoolean to prevent overlapping executions.
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
            List<Integer> serverIds = hostnameToServerIds.get(hostname);
            if (serverIds == null || serverIds.isEmpty()) {
                log.info("No serverIds for hostname=" + hostname + " → SKIP");
                return;
            }

            Map<String, Object> params = new HashMap<>();
            params.put("serverIds", serverIds);

            @SuppressWarnings("unchecked")
            List<SiteEntity> listSites = (List<SiteEntity>) queryForList("CronJobAlertField.getListSiteByServer", params);
            if (listSites == null || listSites.isEmpty()) {
                log.info("No sites found for serverIds=" + serverIds + " → SKIP");
                return;
            }
            log.info("Processing " + listSites.size() + " site(s) on serverIds=" + serverIds);

            List<Future<?>> futures = new ArrayList<>(listSites.size());
            for (SiteEntity site : listSites) {
                try {
                    futures.add(siteExecutor.submit(() -> executeSiteTask(site)));
                } catch (RejectedExecutionException rex) {
                    log.error("[Site " + site.getId() + "] task rejected: " + rex.getMessage());
                }
            }

            waitForCompletion(futures);

        } catch (Exception e) {
            log.error("runAlertCheck error: " + e.getMessage(), e);
        } finally {
            isRunning.set(false);
            long duration = System.currentTimeMillis() - startTime;
            log.info("===== CronJobAlertField END - duration=" + duration + "ms =====");
        }
    }

    /**
     * @description wrapper for thread pool task - sets thread name and catches unhandled exceptions
     * @author duc.pham
     * @since 2026-04-21
     * @param site the site to process
     */
    private void executeSiteTask(SiteEntity site) {
        Thread t = Thread.currentThread();
        String oldName = t.getName();
        t.setName(THREAD_NAME_PREFIX + "-site-" + site.getId());
        try {
            processSite(site);
        } catch (Exception e) {
            log.error("[Site " + site.getId() + "] unhandled error: " + e.getMessage(), e);
        } finally {
            t.setName(oldName);
        }
    }

    /**
     * @description process a single site: fetch devices with alert config, check each device
     * @author duc.pham
     * @since 2026-04-21
     * @param site the site entity to process
     */
    private void processSite(SiteEntity site) {
        log.info("[Site " + site.getId() + "] START");
        try {
            @SuppressWarnings("unchecked")
            List<DeviceEntity> devices = (List<DeviceEntity>) queryForList("CronJobAlertField.getListDeviceBySite", site);
            if (devices == null || devices.isEmpty()) {
                log.info("[Site " + site.getId() + "] no devices → SKIP");
                return;
            }

            log.info("[Site " + site.getId() + "] " + devices.size() + " device(s)");
            for (DeviceEntity device : devices) {
                checkAlertByDevice(device);
            }
            log.info("[Site " + site.getId() + "] END - OK");
        } catch (Exception e) {
            log.error("[Site " + site.getId() + "] error: " + e.getMessage(), e);
        }
    }

    /**
     * @description check alerts for a single device. Dispatches to the correct handler based on device_group_table:
     *              AlertEnum models → TriggerAlertService.checkTriggerAlert()
     *              BitCode models   → TriggerAlertBitCodeService.checkTriggerBitCodeAlert()
     * @author duc.pham
     * @since 2026-04-24
     * @param device the device entity to check
     */
    private void checkAlertByDevice(DeviceEntity device) {
        if (device == null || device.getId() <= 0 || Lib.isBlank(device.getDatatablename())) {
            log.info("[Device invalid] SKIP - null or missing datatablename");
            return;
        }

        String groupTable = device.getDevice_group_table();
        if (Lib.isBlank(groupTable)) {
            log.info("[Device " + device.getId() + "] SKIP - device_group_table is blank");
            return;
        }

        try {
            String currentTime = ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).format(DATE_FMT);

            // Try AlertEnum dispatch first
            BaseAlertEnum[] alertEnums = resolveAlertEnums(groupTable);
            if (alertEnums != null && alertEnums.length > 0) {
                log.info("[Device " + device.getId() + "] AlertEnum mode, " + alertEnums.length
                        + " columns, group=" + groupTable);
                triggerAlertService.checkTriggerAlert(
                        device.getDatatablename(), currentTime, device.getId(), alertEnums);
                return;
            }

            // Try BitCode dispatch
            BitCodeAlertConfig bitCodeConfig = resolveBitCodeConfig(groupTable);
            if (bitCodeConfig != null) {
                log.info("[Device " + device.getId() + "] BitCode mode, group=" + groupTable);
                triggerAlertBitCodeService.checkTriggerBitCodeAlert(
                        device.getDatatablename(), device.getId(), currentTime, bitCodeConfig);
                return;
            }

            log.info("[Device " + device.getId() + "] no alert config for group=" + groupTable);

        } catch (Exception ex) {
            log.error("[Device " + device.getId() + "] checkAlertByDevice error: " + ex.getMessage(), ex);
        }
    }

    /**
     * @description resolve AlertEnum array for models using 0/1 active/inactive pattern
     * @author duc.pham
     * @since 2026-04-24
     * @param groupTable device_group.table_name
     * @return array of BaseAlertEnum, or null if not an AlertEnum model
     */
    private BaseAlertEnum[] resolveAlertEnums(String groupTable) {
        switch (groupTable) {
            case "model_SMP4_DP":
                return ModelSMP4DPService.AlertEnum.values();
            case "model_SMP4_DPV1":
                return ModelSMP4DPV1Service.AlertEnum.values();
            case "model_MVPS_HUAWEI":
                return ModelMVPSHUAWEIService.AlertEnum.values();
            case "model_OrionMX_Automation_Platform":
                return ModelOrionMXAutomationPlatformService.AlertEnum.values();
            case "model_protection_relay":
                return ModelProtectionRelayService.AlertEnum.values();
            case "model_protection_relay_v1":
                return ModelProtectionRelayV1Service.AlertEnum.values();
            case "model_SUN2000330KTLH1":
                return ModelSUN2000330KTLH1Service.AlertEnum.values();
            case "model_SUNGROW_SG6250HV_MV_V1":
                return ModelSUNGROWSG6250HVMVV1Service.AlertEnum.values();
            case "model_sungrow_pv_24h_scb":
                return ModelSungrowPv24hScbService.AlertEnum.values();
            case "model_sungrow_sh6250hv_mv":
                return ModelSungrowSh6250hvMvService.AlertEnum.values();
            default:
                return null;
        }
    }

    /**
     * @description resolve BitCodeAlertConfig for models using toBinary32Bit or direct lookup pattern
     * @author duc.pham
     * @since 2026-04-24
     * @param groupTable device_group.table_name
     * @return BitCodeAlertConfig instance, or null if not a BitCode model
     */
    private BitCodeAlertConfig resolveBitCodeConfig(String groupTable) {
        switch (groupTable) {
            case "model_advanced_energy_solaron":
                return new ModelAdvancedEnergySolaronAlertConfig();
            case "model_chint_solectria_inverter_class9725":
                return new ModelChintSolectriaAlertConfig();
            case "model_PVH_Tbox":
                return new ModelPVHTboxAlertConfig();
            case "model_phoenix_contact_quint_ups":
                return new ModelPhoenixContactQuintUPSAlertConfig();
            case "model_Kehua_SPI50_60K_inverter":
                return new ModelKehuaSPI5060KAlertConfig();
            case "model_xgi150":
                return new ModelXGI1500AlertConfig();
            case "model_satcon_pvs357_inverter":
                return new ModelSatconPvs357AlertConfig();
            case "model_abb_trio_class6210":
                return new ModelAbbTrioClass6210AlertConfig();
            case "model_xantrex_gt100_250_500":
                return new ModelXantrexGT100250500AlertConfig();
            case "model_xantrex_gt500e":
                return new ModelXantrexGT500EAlertConfig();
            default:
                return null;
        }
    }

    /**
     * @description wait for all submitted futures to complete, log errors if any
     * @author duc.pham
     * @since 2026-04-21
     * @param futures list of futures to wait on
     */
    private void waitForCompletion(List<Future<?>> futures) {
        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                log.warn("Interrupted while waiting for site tasks");
                break;
            } catch (ExecutionException ee) {
                log.error("Site task execution error: " + ee.getMessage(), ee);
            }
        }
    }
}
