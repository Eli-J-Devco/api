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
import java.net.InetAddress;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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

        // Resolve domain names → IP để so sánh đúng với getPrivateIP()
        String server1IP = resolveToIP(serverName1);
        String server2IP = resolveToIP(serverName2);

        log.info("Server1: " + serverName1 + " → " + server1IP + " (run_on_id: " + server1_run_on_id + ")");
        log.info("Server2: " + serverName2 + " → " + server2IP + " (run_on_id: " + server2_run_on_id + ")");

        HOSTNAME_TO_SITE_RUNNING.put(server1IP, server1_run_on_id);
        HOSTNAME_TO_SITE_RUNNING.put(server2IP, server2_run_on_id);

        if (!localhost.equals(server1IP) && !localhost.equals(server2IP)) {
            // Local dev / server không nằm trong config → dùng fallback
            log.warn("Current IP " + localhost + " does not match server1 (" + server1IP + ") or server2 (" + server2IP + "). Using local run_on_id: " + server_local_run_on_id);
            HOSTNAME_TO_SITE_RUNNING.put(localhost, server_local_run_on_id);
        } else {
            log.info("Matched server: " + localhost + " → run_on_id: " + HOSTNAME_TO_SITE_RUNNING.get(localhost));
        }
    }

    /**
     * Resolve domain name → IP với timeout 2 giây.
     * Tránh treo 12s/hostname khi DNS không resolve được (local dev).
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

    private ExecutorService tableExecutor;

    private final Set<Integer> runningTables = ConcurrentHashMap.newKeySet();

    /**
     * Tracks the last UTC date on which updateEnergyYesterday was executed.
     * Ensures the job runs at most once per calendar day (UTC).
     */
    private final AtomicReference<LocalDate> lastRunDateYesterday = new AtomicReference<>(null);

    private final Set<Integer> runningYesterdaySites = ConcurrentHashMap.newKeySet();

    /**
     * @description run generation energy today
     * @author Long Pham
     * @date 22-03-2026
     */
    public void generationEnergyData() {
        String hostname = Lib.getPrivateIP();
        List<SiteEntity> sites = getSiteList(hostname, HOSTNAME_TO_SITE_RUNNING);
        if (sites.size() <= 0) return;

        int threadCount = Math.max(2, Math.min(sites.size(), 20));
        if (tableExecutor == null) {
            tableExecutor = Executors.newFixedThreadPool(threadCount, r -> {
                Thread t = new Thread(r);
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
     * @desciption get table name from Postgres DB
     * @author Long.Pham
     * @date 24-03-2026
     * @return List
     */
    private List<SiteEntity> getSiteList(String hostname, Map<String, List<Integer>> hostnameToSiteRunning) {
        List<SiteEntity> siteList;
//        List<Integer> talbeNameList = new ArrayList<>();
        List<Integer> runOnServerList = hostnameToSiteRunning.get(hostname);
        try {
            siteList = this.queryForList("GenerationEnergy.getSiteList", runOnServerList);
            return siteList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        if (siteList != null && !siteList.isEmpty()) {
//            talbeNameList = siteList.stream().map(s -> s.getId()).collect(Collectors.toList());
//            return talbeNameList;
//        }
//        return siteList;
    }


    /**
     *@desciption Run optimized energy calculation for a single site.
     * The stored procedure updateEnergyToday has been optimized:
     *   - Pre-computes UTC time boundaries (sargable → uses INDEX on time)
     *   - Simplified query: ORDER BY time LIMIT 1 instead of LEAD window function
     *   - DEALLOCATE PREPARE to prevent memory leaks
     * @author Long.Pham
     * @date 24-03-2026
     */
    public void generationEnergyData(Integer site_id, String timezone) {
        try {
            SiteEntity siteEntity = new SiteEntity();
            siteEntity.setId(site_id);
            siteEntity.setTime_zone_value(timezone);
            this.update("GenerationEnergy.runGenerationEnergy", siteEntity);

            // Uncomment and use parallel execution when enabling multiple energy calculations:
            // List<CompletableFuture<Void>> futures = new ArrayList<>();
            // futures.add(runAsync(() -> this.update("GenerationEnergy.updateEnergyToday", siteEntity)));
            // futures.add(runAsync(() -> this.update("GenerationEnergy.updateEnergyYesterday", siteEntity)));
            // futures.add(runAsync(() -> this.update("GenerationEnergy.updateEnergyThreeDays", siteEntity)));
            // futures.add(runAsync(() -> this.update("GenerationEnergy.updateEnergyThisMonth", siteEntity)));
            // futures.add(runAsync(() -> this.update("GenerationEnergy.updateEnergyThisWeek", siteEntity)));
            // futures.add(runAsync(() -> this.update("GenerationEnergy.updateEnergyLastWeek", siteEntity)));
            // futures.add(runAsync(() -> this.update("GenerationEnergy.updateEnergyThisYear", siteEntity)));
            // futures.add(runAsync(() -> this.update("GenerationEnergy.updateEnergyLast30Days", siteEntity)));
            // futures.add(runAsync(() -> this.update("GenerationEnergy.updateEnergyLifetime", siteEntity)));
            // CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        } catch (Exception e) {
            log.error("Error processing energy for site: " + site_id, e);
        }
    }


    /**
     * @description Update energy yesterday for all sites.
     *              Guaranteed to execute only ONCE per UTC calendar day.
     *              Cron is scheduled at 00:00:00 UTC in BatchGenerationEnergy.
     * @author Long Pham
     * @date 26-03-2026
     */
    public void updateEnergyYesterday() {
        LocalDate todayUTC = LocalDate.now(ZoneOffset.UTC);

        // Compare-and-set: if lastRunDate already equals today → skip (already ran today)
        LocalDate lastRun = lastRunDateYesterday.get();
        if (todayUTC.equals(lastRun)) {
            log.info("updateEnergyYesterday already executed today (" + todayUTC + "). Skipping.");
            return;
        }
        // Attempt to claim this run; if another thread beat us, skip
        if (!lastRunDateYesterday.compareAndSet(lastRun, todayUTC)) {
            log.info("updateEnergyYesterday concurrent execution detected. Skipping.");
            return;
        }

        log.info("===== START updateEnergyYesterday for date: " + todayUTC + " =====");

        String hostname = Lib.getPrivateIP();
        List<SiteEntity> sites = getSiteList(hostname, HOSTNAME_TO_SITE_RUNNING);
        if (sites == null || sites.isEmpty()) {
            log.warn("updateEnergyYesterday: No sites found for hostname " + hostname);
            return;
        }

        int threadCount = Math.max(2, Math.min(sites.size(), 20));
        ExecutorService executor = Executors.newFixedThreadPool(threadCount, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("energy-yesterday-" + t.getId());
            return t;
        });
        log.info("Created thread pool: " + threadCount + " threads for updateEnergyYesterday (" + sites.size() + " sites)");

        try {
            // Use a CountDownLatch to wait for all sites to finish
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
                        updateEnergyYesterdayForSite(item.getId());
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

            // Wait up to 30 minutes for all sites to finish
            boolean completed = latch.await(30, TimeUnit.MINUTES);
            if (!completed) {
                log.warn("updateEnergyYesterday: Timeout waiting for all sites. Some sites may still be processing.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("updateEnergyYesterday: Interrupted while waiting for completion", e);
        } finally {
            // Shutdown thread pool → giải phóng tất cả threads
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
            log.info("Thread pool shut down successfully.");
        }

        log.info("===== END updateEnergyYesterday for date: " + todayUTC + " =====");
    }


    /**
     * @description Call stored procedure updateEnergyYesterday for a single site.
     * @author Long Pham
     * @date 26-03-2026
     * @param siteId the site id
     */
    private void updateEnergyYesterdayForSite(Integer siteId) {
        try {
            SiteEntity siteEntity = new SiteEntity();
            siteEntity.setId(siteId);
            this.update("GenerationEnergy.updateEnergyYesterday", siteEntity);
        } catch (Exception e) {
            log.error("updateEnergyYesterdayForSite: Error for site " + siteId, e);
        }
    }
}
