/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
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

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CronJobAlertNoCommunicationService extends DB {

    private static final FLLogger noCommLog = FLLogger.getLogger("batchjob/CronJobAlertNoCommunication");
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
    private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobAlertNoCommunication");
    private static final int MAX_SITE_THREADS = 10;
    private final ThreadPoolExecutor siteExecutor = createSiteExecutor();
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    
    private static ThreadPoolExecutor createSiteExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                MAX_SITE_THREADS, MAX_SITE_THREADS,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
        executor.allowCoreThreadTimeOut(true);
        return executor;
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
    
    
    
    public void runNoCommunicationCheck() {
        if (!isRunning.compareAndSet(false, true)) { return; }

        try {
            String hostname = Lib.getPrivateIP();
            List<Integer> serverIds = hostnameToServerIds.get(hostname);
            if (serverIds == null || serverIds.isEmpty()) { return; }

            Map<String, Object> params = new HashMap<>();
            params.put("serverIds", serverIds);

            List<?> listSites = queryForList("CronJobAlertNoComm.getListSiteByServer", params);
            if (listSites == null || listSites.isEmpty()) { return; }

            List<Future<?>> futures = new ArrayList<>();
            
            for (Object o : listSites) {
                SiteEntity site = (SiteEntity) o;
                try {
                    futures.add(siteExecutor.submit(() -> {
                    	Thread t = Thread.currentThread();
                    	String oldName = t.getName();
                    	t.setName(oldName + "-check-no-comm-site-" + site.getId());
                        try { processSite(site); }
                        catch (Exception e) { log.error("Site " + site.getId() + " error: " + e.getMessage()); }
                        finally { t.setName(oldName);  }
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
            noCommLog.error("Error in runNoCommunicationCheck: " + e.getMessage());
        } finally {
            isRunning.set(false);
        }
    }

    
    /**
	 * @description process for each site
	 * @author long.pham
	 * @since 2026-04-07
	 * @param {SiteEntity}
	 */
    
    @SuppressWarnings("unchecked")
	private void processSite(SiteEntity site) {
        try {
            String tzValue = site.getTime_zone_value();
            if (Lib.isBlank(tzValue)) { return; }

            Instant nowInstant = Instant.now();
            String startDate = ZonedDateTime.ofInstant(nowInstant, ZoneOffset.UTC).plusHours(-2).format(DATE_FMT);
            String endDate = ZonedDateTime.ofInstant(nowInstant, ZoneOffset.UTC).format(DATE_FMT);
            
            // Check data logger list 
            List<DeviceEntity> dataLoggerList = queryForList("CronJobAlertNoComm.getListDatalogerBySiteId", site);
            if(dataLoggerList.size() > 0) {
            	for (DeviceEntity dataLoggerItem : dataLoggerList) {
            		
            		BatchJobTableEntity itemDevice = new BatchJobTableEntity();
            		itemDevice.setId_device(dataLoggerItem.getId());
            		itemDevice.setDatatablename(dataLoggerItem.getDatatablename());
            		itemDevice.setId_error(dataLoggerItem.getId_error());
            		itemDevice.setStart_date(startDate);
            		itemDevice.setEnd_date(endDate);
            		Boolean checkDataloger = checkDataloggerIsNotResponding(itemDevice);
            		
            		if(!checkDataloger) {
            			// check no comm for each device
                      List<DeviceEntity> devices = queryForList("CronJobAlertNoComm.getListDeviceCheckNoCom", dataLoggerItem);
                      if (devices != null && !devices.isEmpty()) {
                    	  for (DeviceEntity deviceItem : devices) {
                    		  checkNoCommByDevice(deviceItem);
                    	  }
                      }
            		}
                }
            } else {
            	// case data logger is not exits. 
            	List<DeviceEntity> devices = queryForList("CronJobAlertNoComm.getListDeviceBySite", site);
            	if (devices != null && !devices.isEmpty()) {
              	  for (DeviceEntity deviceItem : devices) {
              		  checkNoCommByDevice(deviceItem);
              	  }
                }
            }

        } catch (Exception e) {
            noCommLog.error("Error: " + e.getMessage());
        }
    }
    
    
    
    /**
	 * @description check alert by device
	 * @author long.pham
	 * @since 2026-04-07
	 * @param {DeviceEntity}
	 */
    @SuppressWarnings("unchecked")
	private void checkNoCommByDevice(DeviceEntity device) {
    	if (device == null || device.getId() <= 0 || device.getDatatablename() == null) {
			return;
		}
    	
    	try {
    		BatchJobTableEntity item = (BatchJobTableEntity) queryForObject("CronJobAlertNoComm.checkDeviceNoComm", device);
    		AlertEntity alertEntity = new AlertEntity();
			alertEntity.setId_device(device.getId());
			alertEntity.setId_error(device.getId_error());
			
    		if(item != null && item.getIs_no_comm() > 0) {
        		// insert no comm
    			alertEntity.setStart_date(item.getStart_date());
    			alertEntity.setEnd_date(null);
    			// Check alert exits
				List<AlertEntity> alertItemQueue = queryForList("CronJobAlertNoComm.checkAlertQueueExits", alertEntity);
    			if(alertItemQueue.size() <= 0) {
    				insertAlertQueue(alertEntity);
    			}
    			
    			// Close no production
//				AlertEntity noProductionItem = (AlertEntity) queryForObject("CronJobAlertNoComm.getNoProduction", alertEntity);
//				if(noProductionItem != null && noProductionItem.getId() > 0) {
//					AlertEntity alertNoProEntity = new AlertEntity();
//					alertNoProEntity.setId_device(device.getId());
//					alertNoProEntity.setEnd_date(item.getStart_date());
//					alertNoProEntity.setId(noProductionItem.getId());
//					updateCloseAlert(alertNoProEntity);
//					
//				}
				
        	} else {
        		// close alert no comm
        		AlertEntity alertItem = (AlertEntity) queryForObject("CronJobAlertNoComm.checkExitsAlertNoComm", device);
        		if(alertItem != null) {
        			BatchJobTableEntity itemFindEndDate = (BatchJobTableEntity) queryForObject("CronJobAlertNoComm.findEndDateNoComm", device);
        			if (itemFindEndDate != null) {
        				alertEntity.setEnd_date(itemFindEndDate.getEnd_date());
            			alertEntity.setId(alertItem.getId());
            	        updateCloseAlert(alertEntity);
        			}
        			
        		}
    			
        	}
        } catch (Exception ex) {
            noCommLog.error("checkDataloggerIsNotResponding error: " + ex.getMessage());
        }
    }
    
    
    /**
	 * @description insert alert queue
	 * @author long.pham
	 * @since 2026-04-07
	 * @param {AlertEntity}
	 */
    
    private boolean insertAlertQueue(AlertEntity obj) {
        try {
            int result = (Integer) insert("CronJobAlertNoComm.insertAlertQueue", obj);
            return result > 0;
        } catch (Exception ex) {
            noCommLog.error("insertAlert error: " + ex.getMessage(), ex);
            return false;
        }
    }

    /**
	 * @description update alert
	 * @author long.pham
	 * @since 2026-04-07
	 * @param {AlertEntity}
	 */
    private void updateCloseAlert(AlertEntity obj) {
        try {
            update("CronJobAlertNoComm.updateCloseAlert", obj);
        } catch (Exception ex) {
            noCommLog.error("updateCloseAlert error: " + ex.getMessage());
        }
    }
    
    
    
    
    /**
	 * @description check data logger respond
	 * @author long.pham
	 * @since 2026-04-07
	 * @param {serial_number}
	 */
    private boolean checkDataloggerIsNotResponding(BatchJobTableEntity obj) {
    	boolean status = true;
    	// true - datalogger is Responding
    	// false - datalogger is not responding
    	try {
    		AlertEntity alertEntity = new AlertEntity();
			alertEntity.setId_device(obj.getId_device());
			alertEntity.setId_error(obj.getId_error());
			
    		BatchJobTableEntity item = (BatchJobTableEntity) queryForObject("CronJobAlertNoComm.getDataloggerItem", obj);
    		AlertEntity alertItem = (AlertEntity) queryForObject("CronJobAlertNoComm.checkExitsAlert", obj);
    		
			
    		if(item == null || (item != null && item.getId_device() == 0 )) {
    			// Data logger is not responding, insert alert queue
    			alertEntity.setStart_date(item.getStart_date());
    			alertEntity.setEnd_date(null);
    			// Check alert queue exits
//    			AlertEntity alertItemQueue = (AlertEntity) queryForObject("CronJobAlertNoComm.checkExitsAlert", alertEntity);
    			List<AlertEntity> alertItemQueue = queryForList("CronJobAlertNoComm.checkAlertQueueExits", alertEntity);
    			
    			if(alertItemQueue.size() <= 0) {
    				insertAlertQueue(alertEntity);
    				status = true;
    			}
    		} else if(item != null && alertItem != null) {
    			// Closed alert Data logger is not responding
    			alertEntity.setEnd_date(obj.getEnd_date());
    			alertEntity.setId(alertItem.getId());
    	        updateCloseAlert(alertEntity);
    	        status = false;
    		} else {
    			status = false;
    		}
        } catch (Exception ex) {
            noCommLog.error("checkDataloggerIsNotResponding error: " + ex.getMessage());
        }
    	return status;
    }

}
