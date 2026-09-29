package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.*;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CronJobCloseDeviceNoComStatusService extends DB {
    private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobDetectDeviceNoCom");
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private static final int TIME_NO_COMM_THRESHOLD_MINUTES = 120;
    private static final int DATALOGER_ID_DEVICE_TYPE = 5;
    private static final int CELL_MODEM_ID_DEVICE_TYPE = 10;
    private static final int NO_COMM_ERROR_CODE = 1001;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void execute() {
        if (!isRunning.compareAndSet(false, true)) {
            log.info("Close No Communication check is already running. Skipping this execution.");
            return;
        }
        Instant nowInstant = Instant.now();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("error_code", NO_COMM_ERROR_CODE);
            List<AlertEntity> listOpenAlert = (List<AlertEntity>) queryForList("CronJobDetectDeviceStatus.getListOpenAlert", params);
            if (listOpenAlert == null || listOpenAlert.isEmpty()) {
                return;
            }
            for (AlertEntity alert : listOpenAlert) {
                if (alert == null || Lib.isBlank(alert.getDataTableName()) || !Lib.isBlank(alert.getEnd_date())) {
                    continue;
                }
                params = new HashMap<>();
                params.put("time_no_comm_threshold_minutes", TIME_NO_COMM_THRESHOLD_MINUTES);
                params.put("data_table_name", alert.getDataTableName());
                params.put("id_device", alert.getId_device());
                params.put("time_execute", formatter.withZone(ZoneOffset.UTC).format(nowInstant));

                if (alert.getId_device_type_int() == DATALOGER_ID_DEVICE_TYPE) {
                    // check no comm status of device datalogger
                    ModelDataloggerEntity datalogger = (ModelDataloggerEntity) queryForObject("CronJobDetectDeviceStatus.getLastTimeResponseDatalogger", params);
                    LocalDateTime localDateTime = LocalDateTime.parse(datalogger.getTime(), formatter);
                    Instant lastUpdated = localDateTime.toInstant(ZoneOffset.UTC);
                    boolean isNoComm = lastUpdated.isBefore(nowInstant.minus(TIME_NO_COMM_THRESHOLD_MINUTES, ChronoUnit.MINUTES));
                    if (isNoComm) {
                        continue;
                    }
                    // check device of site to make sure datalogger is communication
                    List<Integer> siteIds = new ArrayList<>();
                    siteIds.add(alert.getId_site());
                    params.put("siteIds", siteIds);
                    params.put("serial_number", datalogger.getSerialnumber());
                    List<?> listDevicesQuery = queryForList("CronJobDetectDeviceStatus.getListDeviceBySiteIds", params);
                    List<DeviceEntity> listDevices = listDevicesQuery.stream()
                            .map(device -> (DeviceEntity) device)
                            .filter(device -> device.getId() != alert.getId_device() && device.getId_device_type() != CELL_MODEM_ID_DEVICE_TYPE)
                            .collect(Collectors.toList());
                    String noCommEndTime = null;
                    for (DeviceEntity device : listDevices) {
                        Map<String, Object> deviceParams = new HashMap<>();
                        deviceParams.put("id_device", device.getId());
                        deviceParams.put("data_table_name", device.getDatatablename());
                        deviceParams.put("time_no_comm_threshold_minutes", TIME_NO_COMM_THRESHOLD_MINUTES);
                        deviceParams.put("time_execute", formatter.withZone(ZoneOffset.UTC).format(nowInstant));
                        // check device của site theo serial number datalogger để tìm device đầu tiên có comm
                        // nếu có device có comm trong 2 tiếng thì datalogger đã communication lại
                        DeviceAlertDetectEntity eventItem = (DeviceAlertDetectEntity) queryForObject("CronJobDetectDeviceStatus.checkDeviceIsComm", deviceParams);
                        if (eventItem != null) {
                            // if 1 device of site is communication => datalogger is communication
                            // get end time no comm for datalogger
                            log.info("Not detect no comm for device: " + alert.getId_device() + " of site: " + alert.getId_site() + ", data table: " + device.getDatatablename());
                            log.info("Data logger of site: " + alert.getId_site() + "is not detect");
                            deviceParams.put("reference_time", alert.getStart_date());
                            noCommEndTime = (String) queryForObject("CronJobDetectDeviceStatus.findNoCommEndTime", deviceParams);
                            break;
                        }
                    }
                    if (!Lib.isBlank(noCommEndTime)) {
                        alert.setEnd_date(noCommEndTime);
                        update("CronJobDetectDeviceStatus.closeAlert", alert);
                    }
                    continue;
                }
                // kiểm tra lại device có comm lại trong 2 tiếng chưa
                DeviceAlertDetectEntity eventItem = (DeviceAlertDetectEntity) queryForObject("CronJobDetectDeviceStatus.checkDeviceIsComm", params);
                // device is still no communication
                if (eventItem == null) {
                    log.info("Device is still no communication, skip for device id: " + alert.getId_device() + ", data table: " + alert.getDataTableName());
                    continue;
                }
                // get end time no comm alert
                params.put("reference_time", alert.getStart_date());
                String noCommEndTime = (String) queryForObject("CronJobDetectDeviceStatus.findNoCommEndTime", params);
                if (Lib.isBlank(noCommEndTime)) {
                    continue;
                }
                alert.setEnd_date(noCommEndTime);
                // close alert no communication device
                update("CronJobDetectDeviceStatus.closeAlert", alert);
            }
        } catch (Exception e) {
            log.error("Error in Close No Communication Check: " + e.getMessage());
            e.printStackTrace();
        } finally {
            isRunning.set(false);
        }
    }

    /**
     * @description update job scheduler status
     * @author chuong.ma
     * @since 2026-09-25
     * @param jobCode
     * @param trigger, trigger can be "START" or "END"
     */
    public void updateJobSchedulerStatus(String jobCode, String trigger) {
        try {
            String status = "START".equals(trigger) ? "RUNNING" : "COMPLETED";
            CronJobSchedulerEntity jobEntity = (CronJobSchedulerEntity) queryForObject("CronJobScheduler.getJobScheduler", jobCode);
            if(jobEntity == null) {
                jobEntity = new CronJobSchedulerEntity();
                jobEntity.setJob_code(jobCode);
                jobEntity.setJob_name("Detect Device No Communication Status");
                jobEntity.setLast_start_time(Date.from(Instant.now()));
                jobEntity.setLast_status(status);
                jobEntity.setDeseription("Detect Device No Communication Status");
                insert("CronJobScheduler.insertJobScheduler", jobEntity);
            }else{
                if ("START".equals(trigger)) {
                    jobEntity.setLast_start_time(Date.from(Instant.now()));
                }
                if ("END".equals(trigger)) {
                    jobEntity.setLast_end_time(Date.from(Instant.now()));
                }
                jobEntity.setLast_status(status);
                update("CronJobScheduler.updateJobScheduler", jobEntity);
            }
        } catch (Exception e) {
            log.error("Error updating job scheduler status: " + e.getMessage(), e);
        }
    }
}
