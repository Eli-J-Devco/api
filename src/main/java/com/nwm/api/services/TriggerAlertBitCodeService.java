package com.nwm.api.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.FLLogger;

/**
 * Service for processing alerts on models using fault code pattern (toBinary32Bit + direct lookup).
 * Fetches all rows within 2-hour window, then per-field finds continuous streak of fault > 0
 * from the newest row. Only triggers alert when continuous streak >= 2 hours.
 */
@Service
public class TriggerAlertBitCodeService extends DB {

    private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobAlertField");
    private static final int CONTINUOUS_TIME = 120;

    /**
     * @description check all fault fields with data_send_time validation.
     *              Only triggers when continuous rows >= expected count (120 / dataSendTime).
     * @author quan.nguyen
     * @since 2026-06-30
     * @param datatablename device data table name
     * @param deviceId device ID
     * @param currentTime current time UTC format "yyyy-MM-dd HH:mm:ss"
     * @param config BitCodeAlertConfig containing fault field configurations
     * @param dataSendTime interval in minutes between data points
     */
    public void checkTriggerBitCodeAlert(String datatablename, int deviceId, String currentTime, BitCodeAlertConfig config, int dataSendTime) {
        try {
            if (dataSendTime <= 0) {
                dataSendTime = 1;
            }
            int limit = CONTINUOUS_TIME / Constants.UploadingDataIntervals.fromValue(dataSendTime).getInterval();

            List<String> fieldNames = config.getFaultConfigs().stream()
                    .map(e -> e.getFieldName())
                    .distinct()
                    .collect(Collectors.toList());
            Map<String, Object> params = new HashMap<>();
            params.put("datatablename", datatablename);
            params.put("id_device", deviceId);
            params.put("time", currentTime);
            params.put("fields", fieldNames);
            params.put("limit", limit);

            List<Map<String, Object>> allRows = (List<Map<String, Object>>) queryForList("CronJobAlertField.getDataCheckAlert", params);
            if (allRows == null || allRows.isEmpty() || allRows.size() < limit) {
                log.info("[BitCode] device=" + deviceId + " SKIP - not enough data rows in 120 min ("+ (allRows != null ? allRows.size() : 0) + " < " + limit + ")");
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
                Map<String, Object> timeParams = new HashMap<>();
                timeParams.put("datatablename", datatablename);
                timeParams.put("id_device", deviceId);
                timeParams.put("field", fieldName);
                timeParams.put("limit", limit);
                timeParams.put("fault_code", lastFaultCode);
                timeParams.put("time", currentTime);
                // if allSame true & lastFaultCode = 0, that's mean all row is 0 => get close time and close alert if exist
                if (lastFaultCode == 0) {
                    String closeTime = (String) queryForObject("CronJobAlertField.getAlertCloseTime", timeParams);
                    if (Lib.isBlank(closeTime)) {
                        closeTime = (String) allRows.get(allRows.size() - 1).get("time");
                    }
                    closeAlertsForFaultLevel(deviceId, closeTime, faultCfg);
                    continue;
                }
                String startTime = (String) queryForObject("CronJobAlertField.getAlertStartTime", timeParams);
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
     * @description extract fault code value from a data row. Returns 0 if null or sentinel value (0.001)
     * @author duc.pham
     * @since 2026-04-24
     * @param row data row map
     * @param fieldName fault code field name
     * @return fault code as long, or 0 if no fault
     */
    private long extractFaultCode(Map<String, Object> row, String fieldName) {
        Object val = row.get(fieldName);
        if (val == null) return 0;
        double d = ((Number) val).doubleValue();
        // 0.001 is sentinel value for "no data" in the system
        return (d > 0 && d != 0.001) ? (long) d : 0;
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
