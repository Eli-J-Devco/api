/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.services;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Lib;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GenerationEnergyService extends DB {
    @Value("${server1.name}")
    private String serverName1;

    @Value("${server2.name}")
    private String serverName2;

    @Value("${server1.run_on_id}")
    private List<Integer> server1_run_on_id;

    @Value("${server2.run_on_id}")
    private List<Integer> server2_run_on_id;

    @Value("${server.local.run_on_id}")
    private List<Integer> server_local_run_on_id;

    private static final Map<String, List<Integer>> HOSTNAME_TO_SITE_RUNNING = new HashMap<>();

    @PostConstruct
    public void init() {
        String localhost = Lib.getPrivateIP();
        log.info("Private IP: " + localhost);

        String server1IP = resolveToIP(serverName1);
        String server2IP = resolveToIP(serverName2);

        log.info("Server1: " + serverName1 + " → " + server1IP + " (run_on_id: " + server1_run_on_id + ")");
        log.info("Server2: " + serverName2 + " → " + server2IP + " (run_on_id: " + server2_run_on_id + ")");

        HOSTNAME_TO_SITE_RUNNING.put(server1IP, server1_run_on_id);
        HOSTNAME_TO_SITE_RUNNING.put(server2IP, server2_run_on_id);

        if (!localhost.equals(server1IP) && !localhost.equals(server2IP)) {
            log.warn("Current IP " + localhost + " does not match server1 (" + server1IP + ") or server2 (" + server2IP + "). Using local run_on_id: " + server_local_run_on_id);
            HOSTNAME_TO_SITE_RUNNING.put(localhost, server_local_run_on_id);
        } else {
            log.info("Matched server: " + localhost + " → run_on_id: " + HOSTNAME_TO_SITE_RUNNING.get(localhost));
        }
    }

    /**
     * Resolve domain name → IP với timeout 2 giây.
     */
    private String resolveToIP(String hostname) {
        try {
            String ip = CompletableFuture.supplyAsync(() -> {
                try {
                    return InetAddress.getByName(hostname).getHostAddress();
                } catch (Exception e) {
                    return null;
                }
            }).get(2, TimeUnit.SECONDS);

            if (ip != null) {
                log.info("Resolved: " + hostname + " → " + ip);
                return ip;
            }
        } catch (TimeoutException e) {
            log.warn("DNS timeout (2s) for: " + hostname + ", using as-is");
        } catch (Exception e) {
            log.warn("Cannot resolve: " + hostname + ", using as-is");
        }
        return hostname;
    }

    // ========================= TODAY (generationEnergyData) =========================

    private volatile ExecutorService tableExecutor;
    private final Set<Integer> runningTables = ConcurrentHashMap.newKeySet();

    /**
     * @description run generation energy today
     * @author Long Pham
     * @date 22-03-2026
     */
    public void generationEnergyData() {
        String hostname = Lib.getPrivateIP();
        List<SiteEntity> sites = getSiteList(hostname, HOSTNAME_TO_SITE_RUNNING);
        if (sites == null || sites.isEmpty()) return;

        int threadCount = Math.max(2, Math.min(sites.size(), 20));
        if (tableExecutor == null) {
            tableExecutor = Executors.newFixedThreadPool(threadCount, r -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                t.setName("generation-energy-" + t.getId());
                return t;
            });
            log.info("Created thread pool: " + threadCount + " threads for " + sites.size() + " sites");
        }

        for (SiteEntity item : sites) {
            if (!runningTables.add(item.getId())) { continue; }

            tableExecutor.submit(() -> {
                Thread current = Thread.currentThread();
                String oldName = current.getName();
                try {
                    current.setName("generation-energy-site-" + item.getId());
                    log.info("Start processing site: " + item.getId());
                    generationEnergyData(item.getId(), item.getTime_zone_value());
                    log.info("Done processing site: " + item.getId());
                } catch (Exception e) {
                    log.error("Error site: " + item.getId(), e);
                } finally {
                    current.setName(oldName);
                    runningTables.remove(item.getId());
                }
            });
        }
    }

    /**
     * Dọn dẹp tableExecutor khi app shutdown → không để thread sống mãi
     */
    @PreDestroy
    public void destroy() {
        if (tableExecutor != null) {
            log.info("Shutting down tableExecutor (today)...");
            tableExecutor.shutdown();
            try {
                if (!tableExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                    tableExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                tableExecutor.shutdownNow();
            }
            log.info("tableExecutor shut down.");
        }
    }


    // ========================= COMMON =========================

    /** Max threads for periodic (non-today) jobs → lower to avoid DB contention */
    private static final int PERIODIC_MAX_THREADS = 5;

    /**
     * @description get site list from DB
     * @author Long.Pham
     * @date 24-03-2026
     */
    private List<SiteEntity> getSiteList(String hostname, Map<String, List<Integer>> hostnameToSiteRunning) {
        List<Integer> runOnServerList = hostnameToSiteRunning.get(hostname);
        try {
            return this.queryForList("GenerationEnergy.getSiteList", runOnServerList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @description Run stored procedure runGenerationEnergy for a single site (today).
     * @author Long.Pham
     * @date 24-03-2026
     */
    public void generationEnergyData(Integer site_id, String timezone) {
        try {
            SiteEntity siteEntity = new SiteEntity();
            siteEntity.setId(site_id);
            siteEntity.setTime_zone_value(timezone);
            this.update("GenerationEnergy.runGenerationEnergy", siteEntity);
        } catch (Exception e) {
            log.error("Error processing energy for site: " + site_id, e);
        }
    }

    /**
     * @description Generic helper to call a stored procedure for a single site.
     * @author Long Pham
     * @date 30-03-2026
     */
    private void callStoredProcedure(String mybatisUpdateId, Integer siteId, String timezone) {
        try {
            SiteEntity siteEntity = new SiteEntity();
            siteEntity.setId(siteId);
            siteEntity.setTime_zone_value(timezone);
            this.update(mybatisUpdateId, siteEntity);
        } catch (Exception e) {
            log.error("Error calling " + mybatisUpdateId + " for site " + siteId, e);
        }
    }

    /**
     * @description Generic runner for periodic energy update jobs.
     *              Creates a short-lived thread pool, runs all sites in parallel,
     *              waits for completion (max 30 min), then shuts down the pool.
     *              Uses AtomicReference<LocalDate> to guarantee at-most-once per UTC day.
     *              Thread pool is always properly closed in finally block.
     * @author Long Pham
     * @date 30-03-2026
     */
    private void runPeriodicEnergyUpdate(
            String jobName,
            String mybatisUpdateId,
            AtomicReference<LocalDate> lastRunDate,
            Set<Integer> runningSites,
            int maxThreads
    ) {
        LocalDate todayUTC = LocalDate.now(ZoneOffset.UTC);

        // Guard: only run once per UTC calendar day
        LocalDate lastRun = lastRunDate.get();
        if (todayUTC.equals(lastRun)) {
            log.info(jobName + " already executed today (" + todayUTC + "). Skipping.");
            return;
        }
        if (!lastRunDate.compareAndSet(lastRun, todayUTC)) {
            log.info(jobName + " concurrent execution detected. Skipping.");
            return;
        }

        log.info("===== START " + jobName + " for date: " + todayUTC + " =====");

        boolean success = false;

        String hostname = Lib.getPrivateIP();
        List<SiteEntity> sites = getSiteList(hostname, HOSTNAME_TO_SITE_RUNNING);
        if (sites == null || sites.isEmpty()) {
            log.warn(jobName + ": No sites found for hostname " + hostname);
            return;
        }

        int threadCount = Math.max(2, Math.min(sites.size(), maxThreads));
        ExecutorService executor = Executors.newFixedThreadPool(threadCount, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName(jobName + "-" + t.getId());
            return t;
        });
        log.info("Created thread pool: " + threadCount + " threads for " + jobName + " (" + sites.size() + " sites)");

        try {
            CountDownLatch latch = new CountDownLatch(sites.size());

            for (SiteEntity item : sites) {
                if (!runningSites.add(item.getId())) {
                    log.warn(jobName + ": site " + item.getId() + " is already running. Skipping.");
                    latch.countDown();
                    continue;
                }

                executor.submit(() -> {
                    Thread current = Thread.currentThread();
                    String oldName = current.getName();
                    try {
                        current.setName(jobName + "-site-" + item.getId());
                        log.info(jobName + ": Start processing site " + item.getId());
                        callStoredProcedure(mybatisUpdateId, item.getId(), item.getTime_zone_value());
                        log.info(jobName + ": Done processing site " + item.getId());
                    } catch (Exception e) {
                        log.error(jobName + ": Error site " + item.getId(), e);
                    } finally {
                        current.setName(oldName);
                        runningSites.remove(item.getId());
                        latch.countDown();
                    }
                });
            }

            boolean completed = latch.await(30, TimeUnit.MINUTES);
            if (!completed) {
                log.warn(jobName + ": Timeout 30 min. Some sites may still be processing.");
            }
            success = completed;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(jobName + ": Interrupted while waiting for completion", e);
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
            log.info(jobName + " thread pool shut down.");

            // If failed → reset lastRunDate so next cron trigger will retry
            if (!success) {
                lastRunDate.set(null);
                log.warn(jobName + " FAILED → reset lastRunDate. Will retry next cron trigger.");
            }
        }

        log.info("===== END " + jobName + " for date: " + todayUTC + " (success=" + success + ") =====");
    }


    // ========================= YESTERDAY (updateEnergyYesterday) =========================
    // Thread pool riêng, set riêng → KHÔNG ảnh hưởng today=20

    /** Giới hạn thread yesterday thấp hơn today để không tranh DB connection */
    private static final int YESTERDAY_MAX_THREADS = 5;

    private final AtomicReference<LocalDate> lastRunDateYesterday = new AtomicReference<>(null);
    private final Set<Integer> runningYesterdaySites = ConcurrentHashMap.newKeySet();

    /**
     * @description Update energy yesterday for all sites.
     *              Guaranteed to execute only ONCE per UTC calendar day.
     *              Cron is scheduled at 00:00:00 UTC in BatchGenerationEnergy.
     *              Dùng tối đa 5 thread (thấp hơn today=20) để không tranh DB connection.
     *              Nếu fail giữa chừng → reset lastRunDate để retry lần sau.
     * @author Long Pham
     * @date 26-03-2026
     */
    public void updateEnergyYesterday() {
        LocalDate todayUTC = LocalDate.now(ZoneOffset.UTC);

        // Guard: chỉ chạy 1 lần / ngày UTC
        LocalDate lastRun = lastRunDateYesterday.get();
        if (todayUTC.equals(lastRun)) {
            log.info("updateEnergyYesterday already executed today (" + todayUTC + "). Skipping.");
            return;
        }
        if (!lastRunDateYesterday.compareAndSet(lastRun, todayUTC)) {
            log.info("updateEnergyYesterday concurrent execution detected. Skipping.");
            return;
        }

        log.info("===== START updateEnergyYesterday for date: " + todayUTC + " =====");

        boolean success = false;

        String hostname = Lib.getPrivateIP();
        List<SiteEntity> sites = getSiteList(hostname, HOSTNAME_TO_SITE_RUNNING);
        if (sites == null || sites.isEmpty()) {
            log.warn("updateEnergyYesterday: No sites found for hostname " + hostname);
            return;
        }

        int threadCount = Math.max(2, Math.min(sites.size(), YESTERDAY_MAX_THREADS));
        ExecutorService executor = Executors.newFixedThreadPool(threadCount, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("energy-yesterday-" + t.getId());
            return t;
        });
        log.info("Created thread pool: " + threadCount + " threads for updateEnergyYesterday (" + sites.size() + " sites)");

        try {
            CountDownLatch latch = new CountDownLatch(sites.size());

            for (SiteEntity item : sites) {
                if (!runningYesterdaySites.add(item.getId())) {
                    log.warn("updateEnergyYesterday: site " + item.getId() + " is already running. Skipping.");
                    latch.countDown();
                    continue;
                }

                executor.submit(() -> {
                    Thread current = Thread.currentThread();
                    String oldName = current.getName();
                    try {
                        current.setName("energy-yesterday-site-" + item.getId());
                        log.info("updateEnergyYesterday: Start processing site " + item.getId());
                        updateEnergyYesterdayForSite(item.getId(), item.getTime_zone_value());
                        log.info("updateEnergyYesterday: Done processing site " + item.getId());
                    } catch (Exception e) {
                        log.error("updateEnergyYesterday: Error site " + item.getId(), e);
                    } finally {
                        current.setName(oldName);
                        runningYesterdaySites.remove(item.getId());
                        latch.countDown();
                    }
                });
            }

            boolean completed = latch.await(30, TimeUnit.MINUTES);
            if (!completed) {
                log.warn("updateEnergyYesterday: Timeout 30 min. Some sites may still be processing.");
            }
            success = completed;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("updateEnergyYesterday: Interrupted while waiting for completion", e);
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
            log.info("Yesterday thread pool shut down.");

            // Nếu fail → reset lastRunDate để cron lần sau retry trong ngày
            if (!success) {
                lastRunDateYesterday.set(null);
                log.warn("updateEnergyYesterday FAILED → reset lastRunDate. Will retry next cron trigger.");
            }
        }

        log.info("===== END updateEnergyYesterday for date: " + todayUTC + " (success=" + success + ") =====");
    }

    /**
     * @description Call stored procedure updateEnergyYesterday for a single site.
     * @author Long Pham
     * @date 26-03-2026
     */
    private void updateEnergyYesterdayForSite(Integer siteId, String timezone) {
        try {
            SiteEntity siteEntity = new SiteEntity();
            siteEntity.setId(siteId);
            siteEntity.setTime_zone_value(timezone);
            this.update("GenerationEnergy.updateEnergyYesterday", siteEntity);
        } catch (Exception e) {
            log.error("updateEnergyYesterdayForSite: Error for site " + siteId, e);
        }
    }


    // ========================= LAST WEEK (updateEnergyLastWeek) =========================
    // Runs daily at 14:00 UTC → covers all timezones (UTC-12 to UTC+14)
    // Stored procedure is idempotent; value only changes when local week boundary crosses

    private final AtomicReference<LocalDate> lastRunDateLastWeek = new AtomicReference<>(null);
    private final Set<Integer> runningLastWeekSites = ConcurrentHashMap.newKeySet();

    /**
     * @description Update energy last week for all sites.
     *              Runs daily at 14:00 UTC to cover all timezones.
     *              Range: previous Monday 00:00 → this Monday 00:00 (site local time).
     * @author Long Pham
     * @date 30-03-2026
     */
    public void updateEnergyLastWeek() {
        runPeriodicEnergyUpdate(
                "updateEnergyLastWeek",
                "GenerationEnergy.updateEnergyLastWeek",
                lastRunDateLastWeek,
                runningLastWeekSites,
                PERIODIC_MAX_THREADS
        );
    }


    // ========================= LAST MONTH (updateEnergyLastMonth) =========================
    // Runs daily at 14:45 UTC

    private final AtomicReference<LocalDate> lastRunDateLastMonth = new AtomicReference<>(null);
    private final Set<Integer> runningLastMonthSites = ConcurrentHashMap.newKeySet();

    /**
     * @description Update energy last month for all sites.
     *              Runs daily at 14:45 UTC to cover all timezones.
     *              Range: 1st of last month 00:00 → 1st of this month 00:00 (site local time).
     * @author Long Pham
     * @date 30-03-2026
     */
    public void updateEnergyLastMonth() {
        runPeriodicEnergyUpdate(
                "updateEnergyLastMonth",
                "GenerationEnergy.updateEnergyLastMonth",
                lastRunDateLastMonth,
                runningLastMonthSites,
                PERIODIC_MAX_THREADS
        );
    }


    // ========================= COMPARE LAST MONTH (updateEnergyCompareLastMonth) =========================
    // Runs daily at 15:00 UTC

    private final AtomicReference<LocalDate> lastRunDateCompareLastMonth = new AtomicReference<>(null);
    private final Set<Integer> runningCompareLastMonthSites = ConcurrentHashMap.newKeySet();

    /**
     * @description Update energy compare last month (month before last) for all sites.
     *              Runs daily at 15:00 UTC to cover all timezones.
     *              Range: 1st of 2 months ago 00:00 → 1st of last month 00:00 (site local time).
     * @author Long Pham
     * @date 30-03-2026
     */
    public void updateEnergyCompareLastMonth() {
        runPeriodicEnergyUpdate(
                "updateEnergyCompareLastMonth",
                "GenerationEnergy.updateEnergyCompareLastMonth",
                lastRunDateCompareLastMonth,
                runningCompareLastMonthSites,
                PERIODIC_MAX_THREADS
        );
    }


    // ========================= LAST 12 MONTHS (updateEnergyLast12Months) =========================
    // Runs daily at 15:15 UTC

    private final AtomicReference<LocalDate> lastRunDateLast12Months = new AtomicReference<>(null);
    private final Set<Integer> runningLast12MonthsSites = ConcurrentHashMap.newKeySet();

    /**
     * @description Update energy last 12 months (last year) for all sites.
     *              Runs daily at 15:15 UTC to cover all timezones.
     *              Range: Jan 1st last year 00:00 → Jan 1st this year 00:00 (site local time).
     * @author Long Pham
     * @date 30-03-2026
     */
    public void updateEnergyLast12Months() {
        runPeriodicEnergyUpdate(
                "updateEnergyLast12Months",
                "GenerationEnergy.updateEnergyLast12Months",
                lastRunDateLast12Months,
                runningLast12MonthsSites,
                PERIODIC_MAX_THREADS
        );
    }


    // ========================= AVG DAILY 7 DAYS (updateEnergyAVGDaily7Days) =========================
    // Runs daily at 14:15 UTC

    private final AtomicReference<LocalDate> lastRunDateAVGDaily7Days = new AtomicReference<>(null);
    private final Set<Integer> runningAVGDaily7DaysSites = ConcurrentHashMap.newKeySet();

    /**
     * @description Update avg daily energy for current 7-day window for all sites.
     *              Runs daily at 14:15 UTC to cover all timezones.
     *              Range: 7 days ago 00:00 → today 00:00 (site local time). Divided by 7.
     * @author Long Pham
     * @date 30-03-2026
     */
    public void updateEnergyAVGDaily7Days() {
        runPeriodicEnergyUpdate(
                "updateEnergyAVGDaily7Days",
                "GenerationEnergy.updateEnergyAVGDaily7Days",
                lastRunDateAVGDaily7Days,
                runningAVGDaily7DaysSites,
                PERIODIC_MAX_THREADS
        );
    }


    // ========================= AVG DAILY LAST 7 DAYS (updateEnergyAVGDailyLast7Days) =========================
    // Runs daily at 14:30 UTC

    private final AtomicReference<LocalDate> lastRunDateAVGDailyLast7Days = new AtomicReference<>(null);
    private final Set<Integer> runningAVGDailyLast7DaysSites = ConcurrentHashMap.newKeySet();

    /**
     * @description Update avg daily energy for previous 7-day window (compare) for all sites.
     *              Runs daily at 14:30 UTC to cover all timezones.
     *              Range: 14 days ago 00:00 → 7 days ago 00:00 (site local time). Divided by 7.
     * @author Long Pham
     * @date 30-03-2026
     */
    public void updateEnergyAVGDailyLast7Days() {
        runPeriodicEnergyUpdate(
                "updateEnergyAVGDailyLast7Days",
                "GenerationEnergy.updateEnergyAVGDailyLast7Days",
                lastRunDateAVGDailyLast7Days,
                runningAVGDailyLast7DaysSites,
                PERIODIC_MAX_THREADS
        );
    }
}
