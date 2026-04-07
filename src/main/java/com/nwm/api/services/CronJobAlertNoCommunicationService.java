/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *********************************************************/
package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BatchJobTableEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CronJobAlertNoCommunicationService extends DB {

    private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobAlertNoCommunication");
    private static final int MAX_SITE_THREADS = 10;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final ThreadPoolExecutor siteExecutor = createSiteExecutor();

    private static ThreadPoolExecutor createSiteExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                MAX_SITE_THREADS, MAX_SITE_THREADS,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    @Value("${server1.name}") private String serverName1;
    @Value("${server2.name}") private String serverName2;
    @Value("${server1.run_on_id}") private List<Integer> server1RunOnId;
    @Value("${server2.run_on_id}") private List<Integer> server2RunOnId;
    @Value("${server.local.run_on_id}") private List<Integer> serverLocalRunOnId;

    private final Map<String, List<Integer>> hostnameToServerIds = new HashMap<>();

    /**
     * Initialize server name -> run_on_server IDs mapping to split sites by server.
     * Created by: DucPham-nextwave. Since: 2026-04-06.
     */
    @PostConstruct
    public void init() {
        String localhost = Lib.getPrivateIP();
        hostnameToServerIds.put(serverName1, server1RunOnId);
        hostnameToServerIds.put(serverName2, server2RunOnId);
        if (localhost != null && !localhost.equals(serverName1) && !localhost.equals(serverName2)) {
            hostnameToServerIds.put(localhost, serverLocalRunOnId);
        }
        log.info("Server mapping initialized: " + hostnameToServerIds);
    }

    /**
     * Main cron: load sites for current server and process each one in parallel.
     * Created by: DucPham-nextwave. Since: 2026-04-06.
     */
    public void runNoCommunicationCheck() {
        if (!isRunning.compareAndSet(false, true)) return;
        long start = System.currentTimeMillis();
        try {
            String hostname = Lib.getPrivateIP();
            List<Integer> serverIds = hostnameToServerIds.get(hostname);
            if (serverIds == null || serverIds.isEmpty()) return;

            Map<String, Object> params = new HashMap<>();
            params.put("serverIds", serverIds);
            List<?> sites = queryForList("CronJobAlertNoComm.getListSiteByServer", params);
            if (sites == null || sites.isEmpty()) return;

            log.info("Processing " + sites.size() + " site(s) on " + hostname);
            List<Future<?>> futures = new ArrayList<>();
            for (Object o : sites) {
                SiteEntity site = (SiteEntity) o;
                try {
                    futures.add(siteExecutor.submit(() -> {
                        try { processSite(site); }
                        catch (Exception e) { log.error("Site " + site.getId() + " error: " + e.getMessage()); }
                    }));
                } catch (RejectedExecutionException rex) {
                    log.error("Site " + site.getId() + " task rejected: " + rex.getMessage());
                }
            }
            for (Future<?> f : futures) {
                try { f.get(); }
                catch (InterruptedException ie) { Thread.currentThread().interrupt(); break; }
            }
        } catch (Exception e) {
            log.error("runNoCommunicationCheck error: " + e.getMessage());
        } finally {
            isRunning.set(false);
            log.info("Done in " + (System.currentTimeMillis() - start) + "ms");
        }
    }

    /**
     * Process no-communication for one site: check datalogger first, then all devices.
     * Since: 2026-04-07.
     */
    private void processSite(SiteEntity site) {
        if (Lib.isBlank(site.getTime_zone_value())) return;
        int siteId = site.getId();
        if (!checkDataloggerCommunication(siteId)) return;

        try {
            DeviceEntity dEntity = new DeviceEntity();
            dEntity.setId_site(siteId);
            List<?> devices = queryForList("CronJobAlertNoComm.getListDeviceCheckNoCom", dEntity);
            if (devices == null || devices.isEmpty()) return;

            for (Object obj : devices) {
                DeviceEntity device = (DeviceEntity) obj;
                if (!Lib.isBlank(device.getTimezone_value())) {
                    checkSingleDevice(device, siteId);
                }
            }
        } catch (Exception e) {
            log.error("[Site " + site.getId() + "] error: " + e.getMessage());
        }
    }

    /**
     * Check datalogger communication and open/close alert accordingly.
     * Since: 2026-04-07.
     */
    private boolean checkDataloggerCommunication(int siteId) {
        try {
            DeviceEntity dl = (DeviceEntity) queryForObject("CronJobAlertNoComm.getDeviceDatalogger", siteId);
            if (dl == null || dl.getId() <= 0) return true;

            BatchJobTableEntity q = new BatchJobTableEntity();
            q.setId_device(dl.getId());
            q.setDatatablename(dl.getDatatablename());
            BatchJobTableEntity item = (BatchJobTableEntity) queryForObject("CronJobAlertNoComm.getDataloggerItem", q);
            boolean hasData = item != null && item.getId_device() > 0;

            int errorId = Math.max(dl.getId_error(), 0);
            if (errorId <= 0) return hasData;

            AlertEntity alert = buildAlert(dl.getId(), errorId);
            if (hasData) { closeAlertIfExists(alert); return true; }
            createAlertIfNotExists(alert);
            return false;
        } catch (Exception e) {
            log.error("[Site " + siteId + "] datalogger error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check no-communication for a device and close/open alert based on latest result.
     * Since: 2026-04-07.
     */
    private void checkSingleDevice(DeviceEntity device, int siteId) {
        int deviceId = device.getId();
        int errorId = Math.max(device.getId_error(), 0);
        if (errorId == 0) return;

        String tableName = !Lib.isBlank(device.getJob_tablename()) ? device.getJob_tablename() : device.getDatatablename();
        BatchJobTableEntity entity = new BatchJobTableEntity();
        entity.setId_device(deviceId);
        entity.setDatatablename(tableName);

        try {
            List<BatchJobTableEntity> dataList = queryForList("CronJobAlertNoComm.checkDeviceNoComm", entity);
            AlertEntity alert = buildAlert(deviceId, errorId);

            if (dataList == null || dataList.isEmpty()) {
                createAlertIfNotExists(alert);
                return;
            }

            BatchJobTableEntity latest = dataList.get(0);
            int isNoComm = latest.getIs_no_comm() != null ? latest.getIs_no_comm() : 0;
            if (isNoComm != 1) { closeAlertIfExists(alert); return; }

            alert.setStart_date(latest.getStart_date());
            createAlertIfNotExists(alert);
        } catch (Exception e) {
            log.error("[Site " + siteId + "][Device " + deviceId + "] error: " + e.getMessage());
        }
    }

    /**
     * Insert alert_queue only when no active alert and no pending queue record exist.
     * Since: 2026-04-07.
     */
    private boolean createAlertIfNotExists(AlertEntity alert) {
        if (alert.getId_device() <= 0 || alert.getId_error() <= 0) return false;
        try {
            if ((int) queryForObject("CronJobAlertNoComm.checkAlertExist", alert) > 0) return false;
            if ((int) queryForObject("CronJobAlertNoComm.checkAlertQueueExist", alert) > 0) return false;
            insert("CronJobAlertNoComm.insertAlert", alert);
            log.info("alert.inserted deviceId=" + alert.getId_device() + ", errorId=" + alert.getId_error());
            return true;
        } catch (Exception e) {
            log.error("createAlertIfNotExists error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Close active alert if one exists for current device + error pair.
     * Since: 2026-04-07.
     */
    private boolean closeAlertIfExists(AlertEntity alert) {
        try {
            Object obj = queryForObject("CronJobAlertNoComm.getAlertDetail", alert);
            if (obj == null) return false;
            AlertEntity existing = (AlertEntity) obj;
            if (existing.getId() <= 0) return false;
            BatchJobTableEntity closeEntity = new BatchJobTableEntity();
            closeEntity.setId(existing.getId());
            closeEntity.setId_device(existing.getId_device());
            update("CronJobAlertNoComm.updateCloseAlert", closeEntity);
            log.info("alert.closed alertId=" + existing.getId() + ", deviceId=" + existing.getId_device());
            return true;
        } catch (Exception e) {
            log.error("closeAlertIfExists error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Build AlertEntity with id_device and id_error.
     * Created by: DucPham-nextwave. Since: 2026-04-06.
     */
    private AlertEntity buildAlert(int deviceId, int errorId) {
        AlertEntity alert = new AlertEntity();
        alert.setId_device(deviceId);
        alert.setId_error(errorId);
        return alert;
    }

    @PreDestroy
    public void destroy() {
        siteExecutor.shutdown();
        try {
            if (!siteExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                siteExecutor.shutdownNow();
            }
        } catch (InterruptedException ie) {
            siteExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
