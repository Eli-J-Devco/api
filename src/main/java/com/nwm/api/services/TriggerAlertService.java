package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.*;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TriggerAlertService extends DB {

    private static final int CONTINUOUS_TIME = 120;

    public void triggerAlert(DeviceEntity device, String time, BaseAlertEnum[] alertEnums, BitCodeAlertConfig config) {
        try {
            if (device == null || (alertEnums == null && config == null)) {
                return;
            }
            int dataSendTime = device.getData_send_time();
            int deviceId = device.getId();
            String tableName = device.getDatatablename();
            if (alertEnums != null) {
                log.info("[Device " + device.getId() + "] currentTime(UTC)=" + time
                        + " - AlertEnum mode, checking " + alertEnums.length + " columns for group=" + device.getDevice_group_table());
                checkTriggerAlert(tableName, time, deviceId, alertEnums, dataSendTime);
                log.info("[Device " + device.getId() + "] END checkAlertByDevice (AlertEnum) - OK");
                return;
            }
            log.info("[Device " + device.getId() + "] currentTime(UTC)=" + time
                        + " - BitCode mode for group=" + device.getDevice_group_table());
            checkTriggerBitCodeAlert(tableName, deviceId, time, config, dataSendTime);
            log.info("[Device " + deviceId + "] END checkAlertByDevice (BitCode) - OK");
        } catch (Exception e) {
            this.log.error("TriggerAlertService.triggerAlert", e);
        }
    }

    /**
     * @description check all fault fields with data_send_time validation.
     * @author quan.nguyen
     * @since 2026-06-30
     * @param tableName device data table name
     * @param deviceId device ID
     * @param time current time UTC format "yyyy-MM-dd HH:mm:ss"
     * @param alertEnums Enum containing error code and column name to check
     * @param dataSendTime interval in minutes between data points
     */
    private void checkTriggerAlert(String tableName, String time, int deviceId, BaseAlertEnum[] alertEnums, int dataSendTime) {
        try {
            List<String> fieldNames = Arrays.stream(alertEnums)
                    .map(e -> e.getColumn())
                    .collect(Collectors.toList());
            Map<String, Object> params = new HashMap<>();
            List<Map<String, Object>> allRows = getDataCheckAlert(tableName, deviceId, time, dataSendTime, fieldNames, params);
            if (allRows == null) {
                return;
            }
            Map<String, Object> firstRow = allRows.get(0);
            List<AlertEntity> insertList = new ArrayList<>();
            List<AlertEntity> updateList = new ArrayList<>();
            for (BaseAlertEnum alert : alertEnums) {
                String fieldName = alert.getColumn();
                long lastFaultCode = extractFaultCode(firstRow, fieldName);
                boolean allSame = allRows.stream().allMatch(row -> extractFaultCode(row, fieldName) == lastFaultCode);
                if (!allSame) {
                    continue;
                }

                params.put("field", fieldName);
                params.put("fault_code", lastFaultCode);

                String alertTime = "";
                if (lastFaultCode == 0) {
                    // if allSame true & lastFaultCode = 0, that's mean all row is 0 => get close time and close alert if exist
                    String closeTime = (String) queryForObject("CronJobAlertField.getAlertCloseTime", params);
                    if (Lib.isBlank(closeTime)) {
                        closeTime = (String) allRows.get(allRows.size() - 1).get("time");
                    }
                    alertTime = closeTime;
                } else {
                    String startTime = (String) queryForObject("CronJobAlertField.getAlertStartTime", params);
                    if (Lib.isBlank(startTime)) {
                        startTime = (String) allRows.get(allRows.size() - 1).get("time");
                    }
                    alertTime = startTime;
                }
                processAlert(deviceId, alertTime,lastFaultCode != 0, alert.getId(), insertList, updateList);
            }
            if (!insertList.isEmpty()) {
                insert("BatchJob.batchInsertAlert", insertList);
            }
            if (!updateList.isEmpty()) {
                update("BatchJob.batchUpdateAlertV2", updateList);
            }
        } catch (Exception e) {
            log.error("TriggerAlertService.checkTriggerAlert(BaseAlertEnum[])", e);
        }
    }

    /**
     * @description check all fault fields with data_send_time validation.
     * @author quan.nguyen
     * @since 2026-06-30
     * @param datatablename device data table name
     * @param deviceId device ID
     * @param currentTime current time UTC format "yyyy-MM-dd HH:mm:ss"
     * @param config BitCodeAlertConfig containing fault field configurations
     * @param dataSendTime interval in minutes between data points
     */
    private void checkTriggerBitCodeAlert(String datatablename, int deviceId, String currentTime, BitCodeAlertConfig config, int dataSendTime) {
        try {
            List<String> fieldNames = config.getFaultConfigs().stream()
                    .map(e -> e.getFieldName())
                    .distinct()
                    .collect(Collectors.toList());
            Map<String, Object> params = new HashMap<>();
            List<Map<String, Object>> allRows = getDataCheckAlert(datatablename, deviceId, currentTime, dataSendTime, fieldNames, params);
            if (allRows == null) {
                return;
            }
            Map<String, Object> firstRow = allRows.get(0);
            for (BitCodeFaultConfig faultCfg : config.getFaultConfigs()) {
                String fieldName = faultCfg.getFieldName();
                long lastFaultCode = extractFaultCode(firstRow, fieldName);
                boolean allSame = allRows.stream().allMatch(row -> extractFaultCode(row, fieldName) == lastFaultCode);
                if (!allSame) {
                    continue;
                }
                params.put("field", fieldName);
                params.put("fault_code", lastFaultCode);
                if (lastFaultCode == 0) {
                    // if allSame true & lastFaultCode = 0, that's mean all row is 0 => get close time and close alert if exist
                    String closeTime = (String) queryForObject("CronJobAlertField.getAlertCloseTime", params);
                    if (Lib.isBlank(closeTime)) {
                        closeTime = (String) allRows.get(allRows.size() - 1).get("time");
                    }
                    closeAlertsForFaultLevel(deviceId, closeTime, faultCfg);
                    continue;
                }
                String startTime = (String) queryForObject("CronJobAlertField.getAlertStartTime", params);
                if (Lib.isBlank(startTime)){
                    startTime = (String) allRows.get(allRows.size() - 1).get("time");
                }

                if (faultCfg.isBitDecode()) {
                    String binary = Long.toBinaryString(lastFaultCode);
                    int len = binary.length();
                    for (int i = 0; i < faultCfg.getMaxBitCheck(); i++) {

                        int bitIndex = len - 1 - i;
                        int bitLevel = (bitIndex >= 0 && binary.charAt(bitIndex) == '1') ? 1 : 0;
                        int errorId = faultCfg.getErrorIdResolver().applyAsInt(i);
                        if (bitLevel == 1 && errorId > 0) {
                            insertAlertIfNotExists(deviceId, startTime, errorId);
                        }
                    }
                    continue;
                }
                int errorId = faultCfg.getErrorIdResolver().applyAsInt((int) lastFaultCode);
                if (errorId > 0) {
                    insertAlertIfNotExists(deviceId, startTime, errorId);
                }
            }

        } catch (Exception e) {
            log.error("_checkTriggerBitCodeAlert", e);
        }
    }

    private List<Map<String, Object>> getDataCheckAlert(String datatablename, int deviceId, String time, int dataSendTime, List<String> fields, Map<String, Object> params) {
        try {
            if (dataSendTime <= 0) {
                dataSendTime = 1;
            }
            int interval = Constants.UploadingDataIntervals.fromValue(dataSendTime).getInterval();
            int limit = CONTINUOUS_TIME / interval;
            params.put("datatablename", datatablename);
            params.put("id_device", deviceId);
            params.put("time", time);
            params.put("fields", fields);
            params.put("limit", limit);

            List<Map<String, Object>> allRows = (List<Map<String, Object>>) queryForList("CronJobAlertField.getDataCheckAlert", params);
            if (allRows == null || allRows.isEmpty() || allRows.size() < limit) {
                log.info("device=" + deviceId + " SKIP - not enough data rows in 120 min ("+ (allRows != null ? allRows.size() : 0) + " < " + limit + ")");
                return null;
            }
            if (!isContinuousGap(allRows, interval)) {
                log.info("device=" + deviceId + " SKIP - some row is not continuous time interval " + interval + " min " + "at: " + time);
                return null;
            }
            return allRows;
        } catch (Exception e) {
            log.error("getDataCheckAlert", e);
        }
        return null;
    }

    private long extractFaultCode(Map<String, Object> row, String fieldName) {
        Object val = row.get(fieldName);
        if (val == null) return 0;
        double d = ((Number) val).doubleValue();
        // 0.001 is sentinel value for "no data" in the system
        return (d > 0 && d != 0.001) ? (long) d : 0;
    }

    /**
     * @description process alert: insert new alert when error value > 0, update end_date when error value = 0
     * @since 2026-04-15
     * @param deviceId, time, isError, errorId
     */
    private void processAlert(int deviceId, String time, boolean isError, int errorId,
                              List<AlertEntity> insertList, List<AlertEntity> updateList) {
        if (Lib.isBlank(time)) {
            return;
        }
        AlertEntity alert = new AlertEntity();
        alert.setId_device(deviceId);
        alert.setId_error(errorId);

        try {
            if (isError) {
                boolean checkAlertExist = (int) queryForObject("BatchJob.checkAlertlExist", alert) > 0;
                if (!checkAlertExist) {
                    alert.setStart_date(time);
                    insertList.add(alert);
                }
            } else {
                AlertEntity openedAlert = (AlertEntity) queryForObject("BatchJob.getAlertDetail", alert);
                if (openedAlert == null || openedAlert.getId() == 0) {
                    return;
                }
                openedAlert.setEnd_date(time);
                updateList.add(openedAlert);
            }
        } catch (Exception e) {
            log.error("TriggerAlertService.processAlert", e);
        }
    }

    private boolean isContinuousGap(List<Map<String, Object>> data, int interval) {
        if (data == null || data.size() < 2) {
            return true;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 1; i < data.size(); i++) {
            LocalDateTime prev = LocalDateTime.parse((String) data.get(i - 1).get("time"), formatter);
            LocalDateTime curr = LocalDateTime.parse((String) data.get(i).get("time"), formatter);
            long diffMinutes = ChronoUnit.MINUTES.between(prev, curr);

            if (diffMinutes > interval) {
                return false;
            }
        }
        return true;
    }

    /**
     * @description close all open alerts for a specific fault level when fault is no longer active
     * @author duc.pham
     * @since 2026-04-24
     * @param deviceId device ID
     * @param closeTime time to set as end_date for the alert
     * @param faultCfg fault code field configuration (provides faultCodeLevel and closeAlertQueryId)
     */
    private void closeAlertsForFaultLevel(int deviceId, String closeTime, BitCodeFaultConfig faultCfg) {
        try {
            AlertEntity closeParam = new AlertEntity();
            closeParam.setId_device(deviceId);
            closeParam.setFaultCodeLevel(faultCfg.getFaultCodeLevel());

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> openAlerts =
                    (List<Map<String, Object>>) queryForList(faultCfg.getCloseAlertQueryId(), closeParam);
            if (openAlerts == null || openAlerts.isEmpty()) return;

            for (Map<String, Object> item : openAlerts) {
                try {
                    AlertEntity toClose = new AlertEntity();
                    toClose.setId(Integer.parseInt(item.get("id").toString()));
                    toClose.setId_device(deviceId);
                    toClose.setId_error(Integer.parseInt(item.get("id_error").toString()));
                    toClose.setEnd_date(closeTime);
                    update("Alert.UpdateErrorRow", toClose);
                    log.info("[BitCode] CLOSE alert id=" + toClose.getId()
                            + " device=" + deviceId + " errorId=" + toClose.getId_error());
                } catch (Exception ex) {
                    log.error("[BitCode] FAIL closeAlert item device=" + deviceId, ex);
                }
            }
        } catch (Exception e) {
            log.error("[BitCode] FAIL closeAlertsForFaultLevel device=" + deviceId, e);
        }
    }

    /**
     * @description insert alert if it does not already exist (avoid duplicates).
     *              Also verifies that the error ID exists in the error table.
     * @author duc.pham
     * @since 2026-04-24
     * @param deviceId device ID
     * @param startTime alert start time
     * @param errorId error ID to insert
     */
    private void insertAlertIfNotExists(int deviceId, String startTime, int errorId) {
        try {
            AlertEntity alert = new AlertEntity();
            alert.setId_device(deviceId);
            alert.setId_error(errorId);

            boolean alertExists = (int) queryForObject("BatchJob.checkAlertlExist", alert) > 0;
            boolean errorExists = (int) queryForObject("BatchJob.checkErrorExist", alert) > 0;

            if (!alertExists && errorExists) {
                alert.setStart_date(startTime);
                insert("BatchJob.insertAlert", alert);
                log.info("[BitCode] INSERT alert device=" + deviceId + " errorId=" + errorId);
            }
        } catch (Exception e) {
            log.error("[BitCode] FAIL insertAlert device=" + deviceId + " errorId=" + errorId, e);
        }
    }
}
