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
import java.time.ZoneId;
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

    @Autowired
    private TriggerAlertBitCodeService triggerAlertBitCodeService;

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
                ZoneId zoneId = ZoneId.of(site.getTime_zone_value());
                ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
                int hours = zdtNow.getHour();
                if (site.getEnable_alert() < 1 || hours < 9 || hours > 17) {
                    continue;
                }
                log.info("Submitting site id=" + site.getId() + " name=" + site.getName());
                try {
                    futures.add(siteExecutor.submit(() -> {
                        Thread t = Thread.currentThread();
                        String oldName = t.getName();
                        t.setName(THREAD_NAME_PREFIX + "-site-" + site.getId());
                        try {
                            processSite(site);
                        } catch (Exception e) {
                            log.error("Site " + site.getId() + " unhandled error: " + e.getMessage(), e);
                        }
                        finally {
                            t.setName(oldName);
                        }
                    }));
                } catch (RejectedExecutionException rex) {
                    log.error("Site " + site.getId() + " task rejected: " + rex.getMessage());
                }
            }

            for (Future<?> f : futures) {
                try {
                    f.get();
                }
                catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); break;
                }
                catch (ExecutionException ee) {
                    log.error("Site task execution error: " + ee.getMessage(), ee);
                }
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
     * @description Check alerts for a single device.
     * Dispatches to the correct handler based on device_group_table:
     *   - AlertEnum models  → TriggerAlertService.checkTriggerAlert()
     *   - BitCode models    → TriggerAlertBitCodeService.checkTriggerBitCodeAlert()
     * @author duc.pham
     * @since 2026-04-24
     * @param device DeviceEntity
     */
    private void checkAlertByDevice(DeviceEntity device) {
        if (device == null || device.getId() <= 0 || device.getDatatablename() == null) {
            log.info("[Device null/invalid] SKIP");
            return;
        }

        String groupTable = device.getDevice_group_table();
        if (groupTable == null || groupTable.isEmpty()) {
            log.info("[Device " + device.getId() + "] SKIP - device_group_table is null");
            return;
        }

        log.info("[Device " + device.getId() + "] START checkAlertByDevice - table=" + device.getDatatablename() + " group=" + groupTable);
        try {
            String currentTime = ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).format(DATE_FMT);

            // Try AlertEnum dispatch first
            BaseAlertEnum[] alertEnums = resolveAlertEnums(groupTable);
            if (alertEnums != null && alertEnums.length > 0) {
                log.info("[Device " + device.getId() + "] currentTime(UTC)=" + currentTime
                        + " - AlertEnum mode, checking " + alertEnums.length + " columns for group=" + groupTable);
                triggerAlertService.checkTriggerAlert(
                        device.getDatatablename(), currentTime, device.getId(), alertEnums);
                log.info("[Device " + device.getId() + "] END checkAlertByDevice (AlertEnum) - OK");
                return;
            }

            // Try BitCode dispatch
            BitCodeAlertConfig bitCodeConfig = resolveBitCodeConfig(groupTable);
            if (bitCodeConfig != null) {
                log.info("[Device " + device.getId() + "] currentTime(UTC)=" + currentTime
                        + " - BitCode mode for group=" + groupTable);
                triggerAlertBitCodeService.checkTriggerBitCodeAlert(
                        device.getDatatablename(), device.getId(), currentTime, bitCodeConfig);
                log.info("[Device " + device.getId() + "] END checkAlertByDevice (BitCode) - OK");
                return;
            }

            log.info("[Device " + device.getId() + "] SKIP - no alert config found for group=" + groupTable);

        } catch (Exception ex) {
            log.error("[Device " + device.getId() + "] checkAlertByDevice error: " + ex.getMessage(), ex);
        }
    }

    /**
     * @description Resolve AlertEnum array for AlertEnum-based models.
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
     * @description Resolve BitCodeAlertConfig for toBinary32Bit-based models.
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
}
