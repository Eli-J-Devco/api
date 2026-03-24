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
import java.util.*;
import java.util.concurrent.*;
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
     * @description run generation energy today
     * @author Long Pham
     * @date 22-03-2026
     */
    public void generationEnergyData() {
        String hostname = Lib.getPrivateIP();
        List<Integer> siteIds = getSiteList(hostname, HOSTNAME_TO_SITE_RUNNING);
        if (siteIds.isEmpty()) return;

        int threadCount = Math.max(2, Math.min(siteIds.size(), 20));
        if (tableExecutor == null) {
            tableExecutor = Executors.newFixedThreadPool(threadCount, r -> {
                Thread t = new Thread(r);
                t.setName("generation-energy-" + t.getId());
                return t;
            });
            log.info("Created thread pool: " + threadCount + " threads for " + siteIds.size() + " sites");
        }

        for (Integer site_id : siteIds) {
            if (!runningTables.add(site_id)) { continue; }

            tableExecutor.submit(() -> {
                Thread current = Thread.currentThread();
                String oldName = current.getName();
                try {
                    current.setName("generation-energy-site-" + site_id);
                    log.info("Start processing site: " + site_id);
                    generationEnergyData(site_id);
                    log.info("Done processing site: " + site_id);
                } catch (Exception e) {
                    log.error("Error site: " + site_id, e);
                } finally {
                    current.setName(oldName);
                    runningTables.remove(site_id);
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
    private List<Integer> getSiteList(String hostname, Map<String, List<Integer>> hostnameToSiteRunning) {
        List<SiteEntity> siteList;
        List<Integer> talbeNameList = new ArrayList<>();
        List<Integer> runOnServerList = hostnameToSiteRunning.get(hostname);
        try {
            siteList = this.queryForList("GenerationEnergy.getSiteList", runOnServerList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (siteList != null && !siteList.isEmpty()) {
            talbeNameList = siteList.stream().map(s -> s.getId()).collect(Collectors.toList());
            return talbeNameList;
        }
        return talbeNameList;
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
    public void generationEnergyData(Integer site_id) {
        try {
            SiteEntity siteEntity = new SiteEntity();
            siteEntity.setId(site_id);
            this.update("GenerationEnergy.updateEnergyToday", siteEntity);

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
}
