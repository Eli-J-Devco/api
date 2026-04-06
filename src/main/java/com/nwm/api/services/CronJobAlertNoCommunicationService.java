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

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CronJobAlertNoCommunicationService extends DB {

    private static final FLLogger noCommLog = FLLogger.getLogger("batchjob/CronJobAlertNoCommunication");
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
    private static final int MAX_DEVICE_THREADS = 10;
    private static final long THREAD_TIMEOUT_MS = 60_000;
    private static final int NO_COMM_THRESHOLD_MINUTES = 120;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    // TODO: Bỏ hardcode khi deploy production, set = 0 để xử lý tất cả sites
    private static final int TEST_SITE_ID = 566;

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
        String localhost = getPrivateIP();
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

            String hostname = getPrivateIP();
            List<Integer> serverIds = hostnameToServerIds.get(hostname);

            if (serverIds == null || serverIds.isEmpty()) {
                noCommLog.info("No server IDs mapped for hostname: " + hostname
                        + ". Available mappings: " + hostnameToServerIds.keySet() + ". Skipping.");
                return;
            }

            noCommLog.info("Server: " + hostname + " → handling run_on_server IDs: " + serverIds);

            Map<String, Object> params = new HashMap<>();
            params.put("serverIds", serverIds);
            if (TEST_SITE_ID > 0) {
                params.put("id_site", TEST_SITE_ID);
                noCommLog.info("TEST MODE: Only processing site ID = " + TEST_SITE_ID);
            } else {
                params.put("id_site", 0);
            }

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
                    noCommLog.error("[Site " + site.getId() + "] Unhandled error: " + e.getMessage());
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

    private void processSite(SiteEntity objSite) {
        int siteId = objSite.getId();
        long siteStart = System.currentTimeMillis();
        noCommLog.info("[Site " + siteId + "] Start processing: " + objSite.getName());

        try {
            String tzValue = objSite.getTime_zone_value();
            if (tzValue == null || tzValue.isEmpty()) {
                noCommLog.info("[Site " + siteId + "] No timezone configured. Skipping.");
                return;
            }

            ZoneId zoneId = ZoneId.of(tzValue);
            ZonedDateTime nowAtSite = ZonedDateTime.now(zoneId);
            int hourOfDay = nowAtSite.getHour();

            // TODO: Re-enable cho production - bỏ comment block bên dưới
            // int startHour = objSite.getStart_date_time() + 1;
            // int endHour = objSite.getEnd_date_time() - 1;
            // if (hourOfDay < startHour || hourOfDay > endHour) {
            //     noCommLog.info("[Site " + siteId + "] Outside operational hours (hour=" + hourOfDay
            //             + ", range=" + startHour + "-" + endHour + "). Skipping.");
            //     return;
            // }
            noCommLog.info("[Site " + siteId + "] Current hour at site: " + hourOfDay
                    + " (operational hours check DISABLED for testing)");

            Instant nowInstant = Instant.now();
            String sDateUTC = ZonedDateTime.ofInstant(nowInstant, ZoneOffset.UTC).format(DATE_FMT);

            String flag = checkDataloggerCommunication(siteId, sDateUTC);
            noCommLog.info("[Site " + siteId + "] Datalogger check result: flag=" + flag);

            if ("on".equals(flag)) {
                checkDevicesWithThreads(siteId, sDateUTC, nowInstant);
            } else {
                noCommLog.info("[Site " + siteId + "] Datalogger flag=off → skipping device checks");
            }

        } catch (Exception e) {
            noCommLog.error("[Site " + siteId + "] Error: " + e.getMessage());
        }

        noCommLog.info("[Site " + siteId + "] Done. Duration: "
                + (System.currentTimeMillis() - siteStart) + "ms");
    }

    private String checkDataloggerCommunication(int siteId, String sDateUTC) {
        String flag = "off";
        try {
            DeviceEntity objDatalogger = getDeviceDatalogger(siteId);
            if (objDatalogger.getId() <= 0) {
                noCommLog.info("[Site " + siteId + "] No datalogger device → flag=on");
                return "on";
            }

            noCommLog.info("[Site " + siteId + "] Datalogger: id=" + objDatalogger.getId()
                    + ", table=" + objDatalogger.getDatatablename());

            BatchJobTableEntity bathJobEntity = new BatchJobTableEntity();
            bathJobEntity.setId_device(objDatalogger.getId());
            bathJobEntity.setDatatablename(objDatalogger.getDatatablename());

            AlertEntity alertItem = new AlertEntity();
            alertItem.setId_device(objDatalogger.getId());
            alertItem.setId_error(136);
            alertItem.setStart_date(sDateUTC);

            BatchJobTableEntity dataDatalogger = getDataloggerItem(bathJobEntity);

            if (dataDatalogger.getId_device() <= 0) {
                noCommLog.info("[Site " + siteId + "] Datalogger has NO recent data → flag=off");
                flag = "off";
                boolean alertExists = checkAlertExist(alertItem);
                if (!alertExists && alertItem.getId_device() > 0 && alertItem.getId_error() > 0) {
                    insertAlert(alertItem);
                    noCommLog.info("[Site " + siteId + "] Datalogger no comm alert CREATED for device " + objDatalogger.getId());
                }
            } else {
                flag = "on";
                noCommLog.info("[Site " + siteId + "] Datalogger OK → flag=on");
                AlertEntity existingAlert = (AlertEntity) getAlertDetail(alertItem);
                if (existingAlert.getId() > 0) {
                    bathJobEntity.setEnd_date(sDateUTC);
                    bathJobEntity.setId(existingAlert.getId());
                    bathJobEntity.setId_device(existingAlert.getId_device());
                    updateCloseAlert(bathJobEntity);
                    noCommLog.info("[Site " + siteId + "] Datalogger no comm alert CLOSED for device " + objDatalogger.getId());
                }
            }
        } catch (Exception e) {
            noCommLog.error("[Site " + siteId + "] Error checking datalogger: " + e.getMessage());
        }
        return flag;
    }

    private void checkDevicesWithThreads(int siteId, String sDateUTC, Instant nowInstant) {
        try {
            DeviceEntity dEntity = new DeviceEntity();
            dEntity.setId_site(siteId);
            List<?> devices = queryForList("CronJobAlertNoComm.getListDeviceCheckNoCom", dEntity);

            if (devices == null || devices.isEmpty()) {
                noCommLog.info("[Site " + siteId + "] No devices to check.");
                return;
            }

            noCommLog.info("[Site " + siteId + "] Checking " + devices.size()
                    + " device(s) with batch threads (max " + MAX_DEVICE_THREADS + " per batch)");

            List<Thread> batch = new ArrayList<>();
            int batchNum = 0;
            int processedCount = 0;

            for (int i = 0; i < devices.size(); i++) {
                DeviceEntity device = (DeviceEntity) devices.get(i);

                if (device.getTimezone_value() != null) {
                    final int deviceId = device.getId();
                    Thread t = new Thread(() -> {
                        try {
                            checkSingleDevice(device, siteId, sDateUTC, nowInstant);
                        } catch (Exception e) {
                            noCommLog.error("[Site " + siteId + "][Device " + deviceId + "] Thread error: " + e.getMessage());
                        }
                    }, "nocomm-dev-" + deviceId);

                    t.setDaemon(true);
                    t.start();
                    batch.add(t);
                    processedCount++;
                } else {
                    noCommLog.info("[Site " + siteId + "][Device " + device.getId() + "] SKIP - no timezone");
                }

                if (!batch.isEmpty() && (batch.size() >= MAX_DEVICE_THREADS || i == devices.size() - 1)) {
                    batchNum++;
                    int batchSize = batch.size();

                    for (Thread thread : batch) {
                        try {
                            thread.join(THREAD_TIMEOUT_MS);
                            if (thread.isAlive()) {
                                noCommLog.error("[Site " + siteId + "] Thread " + thread.getName()
                                        + " still alive after timeout! Interrupting...");
                                thread.interrupt();
                            }
                        } catch (InterruptedException ie) {
                            noCommLog.error("[Site " + siteId + "] Thread interrupted: " + thread.getName());
                            Thread.currentThread().interrupt();
                        }
                    }

                    noCommLog.info("[Site " + siteId + "] Batch #" + batchNum
                            + " (" + batchSize + " threads) joined & DEAD.");
                    batch.clear();
                }
            }

            noCommLog.info("[Site " + siteId + "] All " + processedCount + " device checks completed. "
                    + batchNum + " batch(es) processed.");

        } catch (Exception e) {
            noCommLog.error("[Site " + siteId + "] Device check error: " + e.getMessage());
        }
    }

    private void checkSingleDevice(DeviceEntity device, int siteId, String sDateUTC, Instant nowInstant) {
        int deviceId = device.getId();

        int noCommunication = Math.max(device.getId_error(), 0);
        if (noCommunication == 0) {
            noCommLog.info("[Site " + siteId + "][Device " + deviceId + "] SKIP - no error code (id_error=0)");
            return;
        }

        BatchJobTableEntity bathJobEntity = new BatchJobTableEntity();
        bathJobEntity.setDatatablename(
                device.getJob_tablename() != null ? device.getJob_tablename() : device.getDatatablename());
        bathJobEntity.setId_device(deviceId);

        try {
            noCommLog.info("[Site " + siteId + "][Device " + deviceId
                    + "] Checking table=" + bathJobEntity.getDatatablename()
                    + ", id_error=" + noCommunication);

            List<BatchJobTableEntity> dataList = queryForList("CronJobAlertNoComm.checkDeviceNoComm", bathJobEntity);

            AlertEntity alertItem = new AlertEntity();
            alertItem.setId_device(deviceId);
            alertItem.setId_error(noCommunication);

            if (dataList == null || dataList.isEmpty()) {
                noCommLog.info("[Site " + siteId + "][Device " + deviceId
                        + "] No data in last 2 hours → creating alert");

                alertItem.setStart_date(
                        nowInstant.minus(2, ChronoUnit.HOURS)
                                .atZone(ZoneOffset.UTC)
                                .format(DATE_FMT));

                boolean alertExists = checkAlertExist(alertItem);
                if (!alertExists && alertItem.getId_device() > 0 && alertItem.getId_error() > 0) {
                    insertAlert(alertItem);
                    noCommLog.info("[Site " + siteId + "][Device " + deviceId + "] No comm alert CREATED (no data)");
                }
            } else {
                noCommLog.info("[Site " + siteId + "][Device " + deviceId
                        + "] checkDeviceNoComm returned " + dataList.size() + " group(s)");

                for (BatchJobTableEntity item : dataList) {
                    int duration = item.getDuration();
                    int isNoComm = item.getIs_no_comm() != null ? item.getIs_no_comm() : 0;

                    noCommLog.info("[Site " + siteId + "][Device " + deviceId
                            + "] Group: duration=" + duration + "min, is_no_comm=" + isNoComm
                            + ", start=" + item.getStart_date() + ", end=" + item.getEnd_date());

                    if (isNoComm == 1) {
                        if (duration < NO_COMM_THRESHOLD_MINUTES) {
                            noCommLog.info("[Site " + siteId + "][Device " + deviceId
                                    + "] SKIP no-comm group - duration " + duration + " < " + NO_COMM_THRESHOLD_MINUTES + "min");
                            continue;
                        }

                        alertItem.setStart_date(
                                !Lib.isBlank(item.getStart_date()) ? item.getStart_date() : sDateUTC);

                        boolean alertExists = checkAlertExist(alertItem);
                        if (!alertExists && alertItem.getId_device() > 0 && alertItem.getId_error() > 0) {
                            insertAlert(alertItem);
                            noCommLog.info("[Site " + siteId + "][Device " + deviceId
                                    + "] No comm alert CREATED (gap=" + duration + "min)");
                        }
                    } else {
                        AlertEntity existingAlert = (AlertEntity) getAlertDetail(alertItem);
                        if (existingAlert.getId() > 0) {
                            bathJobEntity.setEnd_date(sDateUTC);
                            bathJobEntity.setId(existingAlert.getId());
                            bathJobEntity.setId_device(existingAlert.getId_device());
                            updateCloseAlert(bathJobEntity);
                            noCommLog.info("[Site " + siteId + "][Device " + deviceId
                                    + "] No comm alert CLOSED (communicating again)");
                        }
                    }
                }
            }
        } catch (Exception e) {
            noCommLog.error("[Site " + siteId + "][Device " + deviceId
                    + "] checkDeviceNoComm error: " + e.getMessage());
        }
    }

    private DeviceEntity getDeviceDatalogger(int idSite) {
        try {
            DeviceEntity obj = (DeviceEntity) queryForObject("CronJobAlertNoComm.getDeviceDatalogger", idSite);
            if (obj == null) return new DeviceEntity();
            return obj;
        } catch (Exception ex) {
            noCommLog.error("getDeviceDatalogger error: " + ex.getMessage());
            return new DeviceEntity();
        }
    }

    private BatchJobTableEntity getDataloggerItem(BatchJobTableEntity obj) {
        try {
            BatchJobTableEntity item = (BatchJobTableEntity) queryForObject("CronJobAlertNoComm.getDataloggerItem", obj);
            if (item == null) return new BatchJobTableEntity();
            return item;
        } catch (Exception ex) {
            noCommLog.error("getDataloggerItem error: " + ex.getMessage());
            return new BatchJobTableEntity();
        }
    }

    private boolean checkAlertExist(AlertEntity dataE) {
        try {
            return (int) queryForObject("CronJobAlertNoComm.checkAlertExist", dataE) > 0;
        } catch (Exception e) {
            noCommLog.error("checkAlertExist error: " + e.getMessage());
            return true;
        }
    }

    private void insertAlert(AlertEntity obj) {
        try {
            insert("CronJobAlertNoComm.insertAlert", obj);
        } catch (Exception ex) {
            noCommLog.error("insertAlert error: " + ex.getMessage());
        }
    }

    private Object getAlertDetail(AlertEntity obj) {
        try {
            Object dataObj = queryForObject("CronJobAlertNoComm.getAlertDetail", obj);
            if (dataObj == null) return new AlertEntity();
            return dataObj;
        } catch (Exception ex) {
            noCommLog.error("getAlertDetail error: " + ex.getMessage());
            return new AlertEntity();
        }
    }

    private void updateCloseAlert(BatchJobTableEntity obj) {
        try {
            update("CronJobAlertNoComm.updateCloseAlert", obj);
        } catch (Exception ex) {
            noCommLog.error("updateCloseAlert error: " + ex.getMessage());
        }
    }

    public static String getPrivateIP() {
        try {
            List<String> candidateIps = new ArrayList<>();
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                String name = ni.getName();

                if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()) continue;
                if (name.startsWith("docker") || name.startsWith("veth") || name.startsWith("cni")) continue;

                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address && addr.isSiteLocalAddress()) {
                        String ip = addr.getHostAddress();
                        if (name.startsWith("eth") || name.startsWith("ens") || name.startsWith("enp")) {
                            return ip;
                        }
                        candidateIps.add(ip);
                    }
                }
            }

            if (!candidateIps.isEmpty()) return candidateIps.get(0);
        } catch (Exception ignored) {}
        return null;
    }
}
