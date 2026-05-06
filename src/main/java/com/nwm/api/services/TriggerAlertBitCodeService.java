package com.nwm.api.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * @description check all fault fields for a single device using BitCode pattern.
     *              Fetches data in 2-hour window and processes each fault config.
     * @author duc.pham
     * @since 2026-04-24
     * @param datatablename device data table name
     * @param deviceId device ID
     * @param currentTime current time UTC format "yyyy-MM-dd HH:mm:ss"
     * @param config BitCodeAlertConfig containing fault field configurations
     */
    public void checkTriggerBitCodeAlert(String datatablename, int deviceId,
                                         String currentTime, BitCodeAlertConfig config) {
        try {
            List<String> fieldNames = config.getFaultConfigs().stream()
                    .map(BitCodeFaultConfig::getFieldName)
                    .distinct()
                    .collect(Collectors.toList());

            Map<String, Object> params = new HashMap<>();
            params.put("datatablename", datatablename);
            params.put("id_device", deviceId);
            params.put("time", currentTime);
            params.put("fields", fieldNames);

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> allRows =
                    (List<Map<String, Object>>) queryForList("CronJobAlertField.getBitCodeDataIn2Hours", params);
            if (allRows == null) allRows = new ArrayList<>();

            log.info("[BitCode] device=" + deviceId + " rows=" + allRows.size() + " fields=" + fieldNames);

            for (BitCodeFaultConfig faultCfg : config.getFaultConfigs()) {
                processFaultField(deviceId, faultCfg, allRows);
            }
        } catch (Exception e) {
            log.error("[BitCode] FAIL device=" + deviceId, e);
        }
    }

    /**
     * @description process a single fault field: find continuous streak from newest row,
     *              check duration >= 2h, verify consistent value, then trigger or close alerts.
     *              "Continuous" means from the newest row going backward, all rows must have fault > 0.
     *              Stops at the first row with fault = 0.
     * @author duc.pham
     * @since 2026-04-24
     * @param deviceId device ID
     * @param faultCfg fault code field configuration
     * @param allRows all data rows in 2-hour window (ORDER BY time DESC)
     */
    private void processFaultField(int deviceId, BitCodeFaultConfig faultCfg,
                                   List<Map<String, Object>> allRows) {
        try {
            if (allRows.isEmpty()) return;

            String fieldName = faultCfg.getFieldName();

            // Check newest row (index 0 because ORDER BY time DESC)
            long newestFaultCode = extractFaultCode(allRows.get(0), fieldName);

            if (newestFaultCode <= 0) {
                // Newest row has no fault → close all alerts for this fault level
                String alertCloseTime = (String) allRows.get(0).get(fieldName + "_close_time");
                if (alertCloseTime != null) {
                    closeAlertsForFaultLevel(deviceId, alertCloseTime, faultCfg);
                }
                return;
            }

            // Find CONTINUOUS streak from newest row (newest → oldest)
            // Stop immediately when encountering a row with fault = 0
            List<Map<String, Object>> streak = new ArrayList<>();
            for (Map<String, Object> row : allRows) {
                if (extractFaultCode(row, fieldName) > 0) {
                    streak.add(row);
                } else {
                    break;
                }
            }

            if (streak.isEmpty()) return;

            // Calculate duration of continuous streak
            Date newestTime = parseTime(streak.get(0).get("time"));
            Date oldestTime = parseTime(streak.get(streak.size() - 1).get("time"));
            if (newestTime == null || oldestTime == null) return;

            long durationMin = (newestTime.getTime() - oldestTime.getTime()) / 60000;

            // Not yet 2 hours continuous → skip
            if (durationMin < 120) {
                log.info("[BitCode] device=" + deviceId + " field=" + fieldName
                        + " streak=" + streak.size() + " rows, " + durationMin + "min < 2h → skip");
                return;
            }

            // Verify all rows in streak have the same fault code value
            if (!isConsistentValue(streak, fieldName)) {
                log.info("[BitCode] device=" + deviceId + " field=" + fieldName
                        + " inconsistent fault codes in streak → skip");
                return;
            }

            log.info("[BitCode] device=" + deviceId + " field=" + fieldName
                    + " streak=" + streak.size() + " rows, " + durationMin + "min → TRIGGER");

            // Dispatch based on decode type
            if (faultCfg.isBitDecode()) {
                openBitDecodeAlerts(deviceId, faultCfg, streak);
            } else {
                openDirectLookupAlert(deviceId, faultCfg, streak);
            }
        } catch (Exception e) {
            log.error("[BitCode] FAIL device=" + deviceId + " field=" + faultCfg.getFieldName(), e);
        }
    }

    /**
     * @description open alerts using bit decode pattern: AND all fault codes in streak
     *              to find bits that are consistently set to 1, then trigger alert for each bit
     * @author duc.pham
     * @since 2026-04-24
     * @param deviceId device ID
     * @param faultCfg fault code field configuration
     * @param streak continuous streak of rows with fault > 0
     */
    private void openBitDecodeAlerts(int deviceId, BitCodeFaultConfig faultCfg,
                                     List<Map<String, Object>> streak) {
        String alertTime = (String) streak.get(0).get(faultCfg.getFieldName() + "_start_time");

        // AND all values → only keep bits that are always active throughout the streak
        long consistentBits = 0xFFFFFFFFL;
        for (Map<String, Object> row : streak) {
            consistentBits &= extractFaultCode(row, faultCfg.getFieldName());
        }

        if (consistentBits == 0) return;

        log.info("[BitCode] device=" + deviceId + " field=" + faultCfg.getFieldName()
                + " consistentBits=" + Long.toBinaryString(consistentBits));

        // Trigger alert for each bit = 1
        for (int bitPos = 0; bitPos < 32; bitPos++) {
            if ((consistentBits & (1L << bitPos)) != 0) {
                int errorId = faultCfg.getErrorIdResolver().applyAsInt(bitPos);
                if (errorId > 0) {
                    insertAlertIfNotExists(deviceId, alertTime, errorId);
                }
            }
        }
    }

    /**
     * @description open alert using direct lookup pattern: use raw fault code value to resolve error ID
     * @author duc.pham
     * @since 2026-04-24
     * @param deviceId device ID
     * @param faultCfg fault code field configuration
     * @param streak continuous streak of rows with fault > 0
     */
    private void openDirectLookupAlert(int deviceId, BitCodeFaultConfig faultCfg,
                                       List<Map<String, Object>> streak) {
        long faultCode = extractFaultCode(streak.get(0), faultCfg.getFieldName());
        String alertTime = (String) streak.get(0).get(faultCfg.getFieldName() + "_start_time");

        int errorId = faultCfg.getErrorIdResolver().applyAsInt((int) faultCode);
        if (errorId > 0) {
            insertAlertIfNotExists(deviceId, alertTime, errorId);
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
     * @description check if all rows in streak have the same fault code value
     * @author duc.pham
     * @since 2026-04-24
     * @param streak list of data rows
     * @param fieldName fault code field name
     * @return true if all values are identical
     */
    private boolean isConsistentValue(List<Map<String, Object>> streak, String fieldName) {
        if (streak.size() <= 1) return true;
        long firstCode = extractFaultCode(streak.get(0), fieldName);
        for (int i = 1; i < streak.size(); i++) {
            if (extractFaultCode(streak.get(i), fieldName) != firstCode) {
                return false;
            }
        }
        return true;
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
     * @description parse time value from DB row (can be Date object or String)
     * @author duc.pham
     * @since 2026-04-24
     * @param timeVal time value from row map
     * @return parsed Date, or null if parsing fails
     */
    private Date parseTime(Object timeVal) {
        if (timeVal == null) return null;
        if (timeVal instanceof Date) return (Date) timeVal;
        try {
            synchronized (TIME_FORMAT) {
                return TIME_FORMAT.parse(timeVal.toString());
            }
        } catch (Exception e) {
            return null;
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
