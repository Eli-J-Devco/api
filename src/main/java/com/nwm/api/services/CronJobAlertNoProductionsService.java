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
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CronJobAlertNoProductionsService extends DB {

	private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobAlertNoProduction");

	private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);

	private static final int MAX_SITE_THREADS = 10;
	private final ThreadPoolExecutor siteExecutor = createSiteExecutor();
	private final AtomicBoolean isRunning = new AtomicBoolean(false);


	private final ConcurrentHashMap<String, Boolean> processingDevices = new ConcurrentHashMap<>();

	private static ThreadPoolExecutor createSiteExecutor() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
				MAX_SITE_THREADS, MAX_SITE_THREADS,
				60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>());
		executor.allowCoreThreadTimeOut(true);
		return executor;
	}

	@Value("${server1.name}") private String serverName1;
	@Value("${server2.name}") private String serverName2;
	@Value("${server1.run_on_id}") private List<Integer> server1RunOnId;
	@Value("${server2.run_on_id}") private List<Integer> server2RunOnId;
	@Value("${server.local.run_on_id}") private List<Integer> serverLocalRunOnId;

	private final Map<String, List<Integer>> hostnameToServerIds = new HashMap<>();

	@PreDestroy
	public void shutdown() {
		siteExecutor.shutdown();
		try {
			if (!siteExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
				siteExecutor.shutdownNow();
			}
		} catch (InterruptedException e) {
			siteExecutor.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

	@PostConstruct
	public void init() {
		String localhost = Lib.getPrivateIP();
		hostnameToServerIds.put(serverName1, server1RunOnId);
		hostnameToServerIds.put(serverName2, server2RunOnId);
		if (localhost != null && !localhost.equals(serverName1) && !localhost.equals(serverName2)) {
			hostnameToServerIds.put(localhost, serverLocalRunOnId);
		}
	}

	public void runNoProductionCheck() {
		if (!isRunning.compareAndSet(false, true)) { return; }
		try {
			String hostname = Lib.getPrivateIP();
			List<Integer> serverIds = hostnameToServerIds.get(hostname);
			if (serverIds == null || serverIds.isEmpty()) { return; }

			Map<String, Object> params = new HashMap<>();
			params.put("serverIds", serverIds);
			List<?> listSites = queryForList("CronJobAlertNoProduction.getListSiteByServer", params);
			if (listSites.isEmpty()) { return; }

			List<Future<?>> futures = new ArrayList<>();
			for (Object o : listSites) {
				SiteEntity site = (SiteEntity) o;
				futures.add(siteExecutor.submit(() -> {
					Thread t = Thread.currentThread();
					String oldName = t.getName();
					t.setName(oldName + "-no-prod-site-" + site.getId());
					try {
						processSite(site);
					} catch (Exception e) {
						log.error("[ERROR] Site " + site.getId() + " lỗi trong siteExecutor: " + e.getMessage(), e);
					} finally {
						t.setName(oldName);
					}
				}));
			}
			
			for (Future<?> f : futures) {
				try {
					f.get();
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					break;
				} catch (ExecutionException ee) {
					log.error("[ERROR] ExecutionException trong siteExecutor task: " + ee.getMessage(), ee);
				}
			}

		} catch (Exception e) {
			log.error("[ERROR] runNoProductionCheck exception: " + e.getMessage(), e);
		} finally {
			isRunning.set(false);
		}
	}

	private void processSite(SiteEntity site) {
		log.info("  [SITE-START] site id=" + site.getId() + " name=" + site.getName());
		try {
			
			String tzValue = site.getTime_zone_value();
            if (Lib.isBlank(tzValue)) { return; }

            Instant nowInstant = Instant.now();
            String startDate = ZonedDateTime.ofInstant(nowInstant, ZoneOffset.UTC).plusHours(-2).format(DATE_FMT);
            String endDate = ZonedDateTime.ofInstant(nowInstant, ZoneOffset.UTC).format(DATE_FMT);
            
            List<DeviceEntity> dataLoggerList = queryForList("CronJobAlertNoProduction.getListDatalogerBySiteId", site);
            if(dataLoggerList.size() > 0) {
            	for (DeviceEntity dataLoggerItem : dataLoggerList) {
            		
            		BatchJobTableEntity itemDevice = new BatchJobTableEntity();
            		itemDevice.setId_device(dataLoggerItem.getId());
            		itemDevice.setDatatablename(dataLoggerItem.getDatatablename());
            		itemDevice.setId_error(dataLoggerItem.getId_error());
            		itemDevice.setStart_date(startDate);
            		itemDevice.setEnd_date(endDate);
            		Boolean checkDataloger = checkDataloggerIsNotResponding(itemDevice);
            		System.out.println(itemDevice.getId_device());
            		
            		if(checkDataloger) {
            			// check no production for each device
                      List<DeviceEntity> devices = queryForList("CronJobAlertNoProduction.getListMeterAndInverterBySite", site);
                    	if (devices != null && !devices.isEmpty()) {
                      	  for (DeviceEntity deviceItem : devices) {
                      		checkNoProductionByDevice(deviceItem);
                      	  }
                        }
            			
//                      List<DeviceEntity> devices = queryForList("CronJobAlertNoComm.getListDeviceCheckNoCom", dataLoggerItem);
//                      if (devices != null && !devices.isEmpty()) {
//                    	  for (DeviceEntity deviceItem : devices) {
//                    		  checkNoCommByDevice(deviceItem);
//                    	  }
//                      }
            		}
                }
            } else {
            	// case data logger is not exits. 
            	List<DeviceEntity> devices = queryForList("CronJobAlertNoComm.getListDeviceBySite", site);
            	if (devices != null && !devices.isEmpty()) {
              	  for (DeviceEntity deviceItem : devices) {
//              		  checkNoProductionByDevice(deviceItem);
              	  }
                }
            }
            
            
            
            
            
            
            
            
//            List<DeviceEntity> devices = queryForList("CronJobAlertNoProduction.getListMeterAndInverterBySite", site);
//        	if (devices != null && !devices.isEmpty()) {
//          	  for (DeviceEntity deviceItem : devices) {
//          		checkNoProductionByDevice(deviceItem);
//          	  }
//            }
		} catch (Exception e) {
			log.error("  [SITE-ERROR] site " + site.getId() + " error: " + e.getMessage(), e);
		}
	}

	private void checkNoProductionByDevice(DeviceEntity device) {
		if (device == null || device.getId() <= 0 || device.getDatatablename() == null) { return; }
		int idError = device.getId_error() != 0 ? device.getId_error() : 1000;
		String lockKey = device.getId() + "_" + idError;
		if (processingDevices.putIfAbsent(lockKey, Boolean.TRUE) != null) { return;}

		try {
			Map<String, Object> params = new HashMap<>();
			params.put("id", device.getId());
			params.put("datatablename", device.getDatatablename());

			BatchJobTableEntity result = (BatchJobTableEntity) queryForObject("CronJobAlertNoProduction.checkDeviceNoProduction", params);
			
			AlertEntity alertEntity = new AlertEntity();
			alertEntity.setId_device(device.getId());
			alertEntity.setId_error(device.getId_error());
			
    		if(result != null && result.getIs_no_production() > 0) {
        		// insert no comm
    			alertEntity.setStart_date(result.getStart_date());
    			alertEntity.setEnd_date(null);
    			// Check alert exits
				List<AlertEntity> alertItemQueue = queryForList("CronJobAlertNoProduction.checkAlertQueueExits", alertEntity);
    			if(alertItemQueue.size() <= 0) {
    				insertAlertQueue(alertEntity);
    			}
        	} else {
        		// close alert no comm
        		AlertEntity alertItem = (AlertEntity) queryForObject("CronJobAlertNoProduction.checkExitsAlertNoProduction", device);
        		if(alertItem != null) {
        			BatchJobTableEntity itemFindEndDate = (BatchJobTableEntity) queryForObject("CronJobAlertNoProduction.findEndDateNoProduction", device);
        			if (itemFindEndDate != null) {
        				alertEntity.setEnd_date(itemFindEndDate.getEnd_date());
            			alertEntity.setId(alertItem.getId());
            	        updateCloseAlert(alertEntity);
        			}
        		}
        	}
		} catch (Exception e) {
			log.error("    [DEVICE-ERROR] device " + device.getId() + " error: " + e.getMessage(), e);
		} finally {
			processingDevices.remove(lockKey);
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
            update("CronJobAlertNoProduction.updateCloseAlert", obj);
        } catch (Exception ex) {
            log.error(ex);
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
            int result = (Integer) insert("CronJobAlertNoProduction.insertAlertQueue", obj);
            return result > 0;
        } catch (Exception ex) {
            return false;
        }
    }
    
    
    /**
   	 * @description check data logger respond
   	 * @author long.pham
   	 * @since 2026-04-07
   	 * @param {serial_number}
   	 */
       private boolean checkDataloggerIsNotResponding(BatchJobTableEntity obj) {
       	boolean status = false;
       	// true - datalogger is Responding
       	// false - datalogger is not responding
       	try {
       		AlertEntity alertEntity = new AlertEntity();
   			alertEntity.setId_device(obj.getId_device());
   			alertEntity.setId_error(obj.getId_error());
   			
       		BatchJobTableEntity item = (BatchJobTableEntity) queryForObject("CronJobAlertNoProduction.getDataloggerItem", obj);
       		AlertEntity alertItem = (AlertEntity) queryForObject("CronJobAlertNoProduction.checkExitsAlert", obj);
       		
   			
       		if(item != null && item.getId_device() > 0 ) {
       			status = true;
       		} else if(item != null && alertItem == null) {
       	        status = true;
       		} else {
       			status = false;
       		}
           } catch (Exception ex) {
               log.error("checkDataloggerIsNotResponding error: " + ex.getMessage());
           }
       	return status;
       }

    
    
}