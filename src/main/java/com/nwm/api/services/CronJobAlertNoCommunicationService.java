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
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CronJobAlertNoCommunicationService extends DB {

    private static final FLLogger noCommLog = FLLogger.getLogger("batchjob/CronJobAlertNoCommunication");
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
    private static final int MAX_DEVICE_THREADS = 10;
    private static final long THREAD_TIMEOUT_MS = 60_000;
    private static final int NO_COMM_THRESHOLD_MINUTES = 120;
    private static final int TEST_SITE_ID = 673;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

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
        noCommLog.info("CronJobAlertNoCommunication initialized. Private IP: " + localhost);

        hostnameToServerIds.put(serverName1, server1RunOnId);
        hostnameToServerIds.put(serverName2, server2RunOnId);

        if (localhost != null && !localhost.equals(serverName1) && !localhost.equals(serverName2)) {
            hostnameToServerIds.put(localhost, serverLocalRunOnId);
        }

        noCommLog.info("Server mapping: " + hostnameToServerIds);
    }
    
    
    

    public void runNoCommunicationCheck() {
        if (!isRunning.compareAndSet(false, true)) {
            noCommLog.info("Previous run still in progress. Skipping.");
            return;
        }

        long startTime = System.currentTimeMillis();
        try {
            noCommLog.info("========== START No Communication Check ==========");

            String hostname = Lib.getPrivateIP();
            List<Integer> serverIds = hostnameToServerIds.get(hostname);

            if (serverIds == null || serverIds.isEmpty()) {
                noCommLog.info("No server IDs mapped for hostname: " + hostname
                        + ". Available mappings: " + hostnameToServerIds.keySet() + ". Skipping.");
                return;
            }

            noCommLog.info("Server: " + hostname + " → handling run_on_server IDs: " + serverIds);

            Map<String, Object> params = new HashMap<>();
            params.put("serverIds", serverIds);
            params.put("id_site", TEST_SITE_ID);
            noCommLog.info("TEST MODE: Only processing site ID = " + TEST_SITE_ID);

            List<?> listSites = queryForList("CronJobAlertNoComm.getListSiteByServer", params);
            if (listSites == null || listSites.isEmpty()) {
                noCommLog.info("No sites to process for server: " + hostname + " (serverIds=" + serverIds + ")");
                return;
            }

            noCommLog.info("Found " + listSites.size() + " site(s) for server " + hostname);

            for (Object siteObj : listSites) {
                SiteEntity site = (SiteEntity) siteObj;
                try {
                    processSite(site);
                } catch (Exception e) {
//                    noCommLog.error(sitePrefix(site.getId()) + "Unhandled error: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            noCommLog.error("Error in runNoCommunicationCheck: " + e.getMessage());
        } finally {
            isRunning.set(false);
            long duration = System.currentTimeMillis() - startTime;
            noCommLog.info("========== END No Communication Check. Duration: " + duration + "ms ==========");
        }
    }

    private void processSite(SiteEntity site) {
        long siteStart = System.currentTimeMillis();
//        noCommLog.info(sitePrefix(siteId) + "Start processing: " + objSite.getName());

        try {
            String tzValue = site.getTime_zone_value();
            if (Lib.isBlank(tzValue)) {
//                noCommLog.info(sitePrefix(siteId) + "No timezone configured. Skipping.");
                return;
            }

            Instant nowInstant = Instant.now();
            String sDateUTC = ZonedDateTime.ofInstant(nowInstant, ZoneOffset.UTC).format(DATE_FMT);
            
            // Kiem tra danh sach datalogger 
            List<DeviceEntity> dataLoggerList = queryForList("CronJobAlertNoComm.getListDatalogerBySiteId", site);
            if(dataLoggerList.size() > 0) {
            	for (DeviceEntity dataLoggerItem : dataLoggerList) {
            		
            		System.out.println(dataLoggerItem.getId());
            		BatchJobTableEntity itemDevice = new BatchJobTableEntity();
            		itemDevice.setId_device(dataLoggerItem.getId());
            		itemDevice.setDatatablename(dataLoggerItem.getDatatablename());
            		itemDevice.setId_error(dataLoggerItem.getId_error());
            		itemDevice.setStart_date(sDateUTC);
            		itemDevice.setEnd_date(sDateUTC);
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
            

//            String flag = checkDataloggerCommunication(siteId, sDateUTC);
//            noCommLog.info(sitePrefix(siteId) + "Datalogger check result: flag=" + flag);
//
//            if ("on".equals(flag)) {
//                checkDevicesWithThreads(siteId, sDateUTC, nowInstant);
//            } else {
//                noCommLog.info(sitePrefix(siteId) + "Datalogger flag=off -> skipping device checks");
//            }

        } catch (Exception e) {
//            noCommLog.error(sitePrefix(siteId) + "Error: " + e.getMessage());
        }

//        noCommLog.info(sitePrefix(siteId) + "Done. Duration: "
//                + (System.currentTimeMillis() - siteStart) + "ms");
    }
    
    
    
    /**
	 * @description check alert by device
	 * @author long.pham
	 * @since 2026-04-07
	 * @param {DeviceEntity}
	 */
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
    			// Check alert queue exits
    			AlertEntity alertItemQueue = (AlertEntity) queryForObject("CronJobAlertNoComm.checkAlertQueueExits", alertEntity);
    			if(alertItemQueue == null) {
    				insertAlertQueue(alertEntity);
    			}
        	} else {
        		// close alert no comm
        		AlertEntity alertItem = (AlertEntity) queryForObject("CronJobAlertNoComm.checkDataloggerExitsAlert", device);
    			alertEntity.setEnd_date(item.getStart_date());
    			alertEntity.setId(alertItem.getId());
    	        updateCloseAlert(alertEntity);
        	}
        } catch (Exception ex) {
            noCommLog.error("checkDataloggerIsNotResponding error: " + ex.getMessage());
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    

//    private String checkDataloggerCommunication(int siteId, String sDateUTC) {
//        try {
//            DeviceEntity objDatalogger = getDeviceDatalogger(siteId);
//            if (objDatalogger.getId() <= 0) {
////                noCommLog.info(sitePrefix(siteId) + "No datalogger -> flag=on");
//                return "on";
//            }
//
//            int deviceId = objDatalogger.getId();
////            noCommLog.info(sitePrefix(siteId) + "Datalogger id=" + deviceId
////                    + ", table=" + objDatalogger.getDatatablename());
//
//            BatchJobTableEntity query = buildBatchJobEntity(deviceId, objDatalogger.getDatatablename());
//
//            boolean hasData = getDataloggerItem(query).getId_device() > 0;
//
//            int dataloggerNoCommErrorId = Math.max(objDatalogger.getId_error(), 0);
//            if (dataloggerNoCommErrorId <= 0) {
////                noCommLog.error(sitePrefix(siteId) + "No no-communication error mapped for datalogger group.");
//                return hasData ? "on" : "off";
//            }
//
//            AlertEntity alertItem = buildAlert(deviceId, dataloggerNoCommErrorId, sDateUTC);
//
//            if (!hasData) {
//                boolean alertCreated = createAlertIfNotExists(alertItem);
//                if (alertCreated) {
////                    noCommLog.info(sitePrefix(siteId) + "Datalogger no comm alert CREATED");
//                } else {
////                    noCommLog.info(sitePrefix(siteId) + "Datalogger no comm alert already exists");
//                }
//                return "off";
//            }
//
//            if (closeAlertIfExists(alertItem, sDateUTC)) {
////                noCommLog.info(sitePrefix(siteId) + "Datalogger no comm alert CLOSED");
//            }
//            return "on";
//
//        } catch (Exception e) {
////            noCommLog.error(sitePrefix(siteId) + "Datalogger check error: " + e.getMessage());
//            return "off";
//        }
//    }

//    private void checkDevicesWithThreads(String serial_number, String sDateUTC, Instant nowInstant) {
//        try {
//            DeviceEntity dEntity = new DeviceEntity();
////            dEntity.setId_site(siteId);
//            dEntity.setSerial_number(serial_number);
//            List<?> devices = queryForList("CronJobAlertNoComm.getListDeviceCheckNoCom", dEntity);
//
//            if (devices == null || devices.isEmpty()) {
////                noCommLog.info(sitePrefix(serial_number) + "No devices to check.");
//                return;
//            }
//
////            noCommLog.info(sitePrefix(siteId) + "Checking " + devices.size()
////                    + " device(s) with batch threads (max " + MAX_DEVICE_THREADS + " per batch)");
//
//            List<Thread> batch = new ArrayList<>();
//            int batchNum = 0;
//            int processedCount = 0;
//
//            for (int i = 0; i < devices.size(); i++) {
//                DeviceEntity device = (DeviceEntity) devices.get(i);
//                Thread thread = startDeviceThreadIfEligible(device, serial_number, sDateUTC, nowInstant);
//                if (thread != null) {
//                    batch.add(thread);
//                    processedCount++;
//                }
//
//                if (!batch.isEmpty() && (batch.size() >= MAX_DEVICE_THREADS || i == devices.size() - 1)) {
//                    batchNum++;
//                    int batchSize = batch.size();
//
//                    joinThreads(serial_number, batch);
//
////                    noCommLog.info(sitePrefix(serial_number) + "Batch #" + batchNum
////                            + " (" + batchSize + " threads) joined & DEAD.");
//                    batch.clear();
//                }
//            }
//
////            noCommLog.info(sitePrefix(siteId) + "All " + processedCount + " device checks completed. "
////                    + batchNum + " batch(es) processed.");
//
//        } catch (Exception e) {
////            noCommLog.error(sitePrefix(siteId) + "Device check error: " + e.getMessage());
//        }
//    }

//    @SuppressWarnings("unchecked")
//    private void checkSingleDevice(DeviceEntity device, String serial_number, String sDateUTC, Instant nowInstant) {
//        int deviceId = device.getId();
//
//        int noCommunication = Math.max(device.getId_error(), 0);
//        if (noCommunication == 0) {
////            noCommLog.info(devicePrefix(siteId, deviceId) + "SKIP - no error code (id_error=0)");
//            return;
//        }
//
//        BatchJobTableEntity bathJobEntity = buildBatchJobEntity(deviceId, resolveDeviceDataTableName(device));
//
//        try {
////            noCommLog.info(devicePrefix(siteId, deviceId)
////                    + "Checking table=" + bathJobEntity.getDatatablename()
////                    + ", id_error=" + noCommunication);
//
//            List<BatchJobTableEntity> dataList = queryForList("CronJobAlertNoComm.checkDeviceNoComm", bathJobEntity);
//
//            AlertEntity alertItem = buildAlert(deviceId, noCommunication, null);
//
//            if (dataList == null || dataList.isEmpty()) {
//                alertItem.setStart_date(
//                        nowInstant.minus(2, ChronoUnit.HOURS)
//                                .atZone(ZoneOffset.UTC)
//                                .format(DATE_FMT));
//
//                boolean alertCreated = createAlertIfNotExists(alertItem);
//                if (alertCreated) {
////                    noCommLog.info(devicePrefix(siteId, deviceId) + "No comm alert CREATED (no data)");
//                } else {
////                    noCommLog.info(devicePrefix(siteId, deviceId) + "No comm alert already exists (no data)");
//                }
//                return;
//            }
//
////            noCommLog.info(devicePrefix(siteId, deviceId)
////                    + "checkDeviceNoComm returned " + dataList.size() + " group(s)");
//
//            BatchJobTableEntity latestGroup = dataList.get(0);
//            int duration = latestGroup.getDuration();
//            int isNoComm = latestGroup.getIs_no_comm() != null ? latestGroup.getIs_no_comm() : 0;
//
////            noCommLog.info(devicePrefix(siteId, deviceId)
////                    + "Latest group: duration=" + duration + "min, is_no_comm=" + isNoComm
////                    + ", start=" + latestGroup.getStart_date() + ", end=" + latestGroup.getEnd_date());
//
//            if (isNoComm != 1) {
//                if (closeAlertIfExists(alertItem, sDateUTC)) {
////                    noCommLog.info(devicePrefix(siteId, deviceId)
////                            + "No comm alert CLOSED (communicating again)");
//                }
//                return;
//            }
//
//            if (duration < NO_COMM_THRESHOLD_MINUTES) {
////                noCommLog.info(devicePrefix(siteId, deviceId)
////                        + "SKIP no-comm group - duration " + duration + " < " + NO_COMM_THRESHOLD_MINUTES + "min");
//                return;
//            }
//
//            alertItem.setStart_date(!Lib.isBlank(latestGroup.getStart_date()) ? latestGroup.getStart_date() : sDateUTC);
//            boolean alertCreated = createAlertIfNotExists(alertItem);
//            if (alertCreated) {
////                noCommLog.info(devicePrefix(siteId, deviceId)
////                        + "No comm alert CREATED (gap=" + duration + "min)");
//            } else {
////                noCommLog.info(devicePrefix(siteId, deviceId)
////                        + "No comm alert already exists (gap=" + duration + "min)");
//            }
//        } catch (Exception e) {
////            noCommLog.error(devicePrefix(siteId, deviceId) + "checkDeviceNoComm error: " + e.getMessage());
//        }
//    }

//    private Thread startDeviceThreadIfEligible(DeviceEntity device, String serial_number, String sDateUTC, Instant nowInstant) {
//        int deviceId = device.getId();
//        if (Lib.isBlank(device.getTimezone_value())) {
////            noCommLog.info(devicePrefix(serial_number, deviceId) + "SKIP - no timezone");
//            return null;
//        }
//
//        Thread t = new Thread(() -> {
//            try {
//                checkSingleDevice(device, serial_number, sDateUTC, nowInstant);
//            } catch (Exception e) {
//                noCommLog.error(devicePrefix(serial_number, deviceId) + "Thread error: " + e.getMessage());
//            }
//        }, "nocomm-dev-" + deviceId);
//
//        t.setDaemon(true);
//        t.start();
//        return t;
//    }

//    private void joinThreads(String serial_number, List<Thread> batch) {
//        for (Thread thread : batch) {
//            try {
//                thread.join(THREAD_TIMEOUT_MS);
//                if (thread.isAlive()) {
//                    noCommLog.error(sitePrefix(serial_number) + "Thread " + thread.getName()
//                            + " still alive after timeout! Interrupting...");
//                    thread.interrupt();
//                }
//            } catch (InterruptedException ie) {
//                noCommLog.error(sitePrefix(serial_number) + "Thread interrupted: " + thread.getName());
//                Thread.currentThread().interrupt();
//            }
//        }
//    }

//    private BatchJobTableEntity buildBatchJobEntity(int deviceId, String tableName) {
//        BatchJobTableEntity entity = new BatchJobTableEntity();
//        entity.setId_device(deviceId);
//        entity.setDatatablename(tableName);
//        return entity;
//    }

//    private String sitePrefix(String serial_number) {
//        return "[Site " + serial_number + "] ";
//    }
//
//    private String devicePrefix(String serial_number, int deviceId) {
//        return "[Site " + serial_number + "][Device " + deviceId + "] ";
//    }

//    private boolean createAlertIfNotExists(AlertEntity alertItem) {
//        if (alertItem.getId_device() <= 0 || alertItem.getId_error() <= 0) return false;
//        if (checkAlertExist(alertItem)) return false;
////        insertAlert(alertItem);
//        return true;
//    }

//    private boolean closeAlertIfExists(AlertEntity alertItem, String endDate) {
//        AlertEntity existing = getAlertDetail(alertItem);
//        if (existing.getId() <= 0) return false;
//        BatchJobTableEntity closeEntity = new BatchJobTableEntity();
//        closeEntity.setId(existing.getId());
//        closeEntity.setId_device(existing.getId_device());
//        closeEntity.setEnd_date(endDate);
////        updateCloseAlert(closeEntity);
//        return true;
//    }
//
//    private AlertEntity buildAlert(int deviceId, int errorId, String startDate) {
//        AlertEntity alert = new AlertEntity();
//        alert.setId_device(deviceId);
//        alert.setId_error(errorId);
//        alert.setStart_date(startDate);
//        return alert;
//    }
//
//    private String resolveDeviceDataTableName(DeviceEntity device) {
//        return !Lib.isBlank(device.getJob_tablename())
//                ? device.getJob_tablename()
//                : device.getDatatablename();
//    }

//    private DeviceEntity getDeviceDatalogger(int idSite) {
//        try {
//            DeviceEntity obj = (DeviceEntity) queryForObject("CronJobAlertNoComm.getDeviceDatalogger", idSite);
//            if (obj == null) return new DeviceEntity();
//            return obj;
//        } catch (Exception ex) {
//            noCommLog.error("getDeviceDatalogger error: " + ex.getMessage());
//            return new DeviceEntity();
//        }
//    }

//    private BatchJobTableEntity getDataloggerItem(BatchJobTableEntity obj) {
//        try {
//            BatchJobTableEntity item = (BatchJobTableEntity) queryForObject("CronJobAlertNoComm.getDataloggerItem", obj);
//            if (item == null) return new BatchJobTableEntity();
//            return item;
//        } catch (Exception ex) {
//            noCommLog.error("getDataloggerItem error: " + ex.getMessage());
//            return new BatchJobTableEntity();
//        }
//    }

//    private boolean checkAlertExist(AlertEntity dataE) {
//        try {
//            return (int) queryForObject("CronJobAlertNoComm.checkAlertExist", dataE) > 0;
//        } catch (Exception e) {
//            noCommLog.error("checkAlertExist error: " + e.getMessage());
//            return true;
//        }
//    }

//    private void insertAlert(AlertEntity obj) {
//        try {
//            insert("CronJobAlertNoComm.insertAlert", obj);
//        } catch (Exception ex) {
//            noCommLog.error("insertAlert error: " + ex.getMessage());
//        }
//    }
    
    private boolean insertAlertQueue(AlertEntity obj) {
        try {
            int result = (Integer) insert("CronJobAlertNoComm.insertAlertQueue", obj);
            return result > 0;
        } catch (Exception ex) {
            noCommLog.error("insertAlert error: " + ex.getMessage(), ex);
            return false;
        }
    }

//    private AlertEntity getAlertDetail(AlertEntity obj) {
//        try {
//            Object dataObj = queryForObject("CronJobAlertNoComm.getAlertDetail", obj);
//            if (dataObj == null) return new AlertEntity();
//            return (AlertEntity) dataObj;
//        } catch (Exception ex) {
//            noCommLog.error("getAlertDetail error: " + ex.getMessage());
//            return new AlertEntity();
//        }
//    }

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
			alertEntity.setStart_date(obj.getStart_date());
			
    		BatchJobTableEntity item = (BatchJobTableEntity) queryForObject("CronJobAlertNoComm.getDataloggerItem", obj);
    		AlertEntity alertItem = (AlertEntity) queryForObject("CronJobAlertNoComm.checkDataloggerExitsAlert", obj);
    		
			
    		if(item == null) {
    			// Data logger is not responding, insert alert queue
    			alertEntity.setEnd_date(null);
    			// Check alert queue exits
    			AlertEntity alertItemQueue = (AlertEntity) queryForObject("CronJobAlertNoComm.checkAlertQueueExits", alertEntity);
    			if(alertItemQueue == null) {
    				insertAlertQueue(alertEntity);
    				status = true;
    			}
    		} else if(item != null && alertItem != null) {
    			// Closed alert Data logger is not responding
    			alertEntity.setEnd_date(obj.getEnd_date());
    			alertEntity.setId(alertItem.getId());
    	        updateCloseAlert(alertEntity);
    	        status = false;
    		}
        } catch (Exception ex) {
            noCommLog.error("checkDataloggerIsNotResponding error: " + ex.getMessage());
        }
    	return status;
    }

}
