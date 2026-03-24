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
import java.net.UnknownHostException;
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
        System.out.println("Private IP: " + Lib.getPrivateIP());
        HOSTNAME_TO_SITE_RUNNING.put(serverName1, server1_run_on_id);
        HOSTNAME_TO_SITE_RUNNING.put(serverName2, server2_run_on_id);

        if(!localhost.equals(serverName1) && !localhost.equals(serverName2)) {
            HOSTNAME_TO_SITE_RUNNING.put(localhost, server_local_run_on_id);
        }
    }

    private final int TABLE_THREAD = 10;
    private final int INSERT_THREAD = 100;
    private final int DATA_GET_LIMIT = 200;

//    private final ExecutorService tableExecutor = Executors.newFixedThreadPool(TABLE_THREAD);
//    ExecutorService tableExecutor = Executors.newFixedThreadPool(TABLE_THREAD, r -> {
//        Thread t = new Thread(r);
//        t.setName("Energy-" + t.getId());
//        return t;
//    });
    
    
    ThreadPoolExecutor tableExecutor = new ThreadPoolExecutor(
    		TABLE_THREAD,                      // core
    	    20,                     // max
    	    60, TimeUnit.SECONDS,
    	    new LinkedBlockingQueue<>(100), // ❗ giới hạn queue
    	    r -> {
    	        Thread t = new Thread(r);
    	        t.setName("energy-worker-" + t.getId());
    	        return t;
    	    },
    	    new ThreadPoolExecutor.CallerRunsPolicy() // backpressure
    	);
 
    private final ExecutorService insertExecutor = Executors.newFixedThreadPool(INSERT_THREAD);
    private final Set<Integer> runningTables = ConcurrentHashMap.newKeySet();
    /**
     *@desciption run generation energy today
     * @author Long Pham
     * @date 22-03-2026
     * @return void
     */
    public void generationEnergyData() throws InterruptedException, UnknownHostException {
    	String hostname = Lib.getPrivateIP();
        List<Integer> deviceList = getSiteList(hostname, HOSTNAME_TO_SITE_RUNNING);
        if (deviceList.isEmpty()) return;
        
        for (Integer site_id : deviceList) {
        	if (!runningTables.add(site_id)) { continue; }
        	
        	tableExecutor.submit(() -> {
                Thread current = Thread.currentThread();
                String oldName = current.getName();
                try {
                    current.setName("generation-energy-site-" + site_id);                    
                    log.info("Start processing site: " + site_id);
                    // 👉 IMPORTANT: xử lý SYNC bên trong
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
    
    
    private CompletableFuture<Void> runAsync(Runnable task) {
        return CompletableFuture.runAsync(() -> {
            try {
                task.run();
            } catch (Exception e) {
                log.error("Task failed", e);
            }
        }, insertExecutor);
    }

    /**
     *@desciption 
     * @author Long.Pham
     * @date 24-03-2026
     * @return 
     */
    public void generationEnergyData(Integer site_id) {
        try {
        	SiteEntity siteEntity = new SiteEntity();
        	siteEntity.setId(site_id);
        	List<CompletableFuture<Void>> futures = new ArrayList<>();
        	futures.add(runAsync(() -> 
	            this.update("GenerationEnergy.updateEnergyToday", siteEntity)
	        ));
        	
//        	futures.add(runAsync(() -> 
//	            this.update("GenerationEnergy.updateEnergyYesterday", siteEntity)
//	        ));
//	
//	        futures.add(runAsync(() -> 
//	            this.update("GenerationEnergy.updateEnergyThreeDays", siteEntity)
//	        ));
//	
//	        futures.add(runAsync(() -> 
//	            this.update("GenerationEnergy.updateEnergyThisMonth", siteEntity)
//	        ));
//	
//	        futures.add(runAsync(() -> 
//	            this.update("GenerationEnergy.updateEnergyThisWeek", siteEntity)
//	        ));
//	
//	        futures.add(runAsync(() -> 
//	            this.update("GenerationEnergy.updateEnergyLastWeek", siteEntity)
//	        ));
//	
//	        futures.add(runAsync(() -> 
//	            this.update("GenerationEnergy.updateEnergyThisYear", siteEntity)
//	        ));
//	
//	        futures.add(runAsync(() -> 
//	            this.update("GenerationEnergy.updateEnergyLast30Days", siteEntity)
//	        ));
//	
//	        futures.add(runAsync(() -> 
//	            this.update("GenerationEnergy.updateEnergyLifetime", siteEntity)
//	        ));
//	        
        	// 👉  wait the task complete
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }    
}
