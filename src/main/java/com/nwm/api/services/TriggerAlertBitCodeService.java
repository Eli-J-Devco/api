package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description Generic service for processing toBinary32Bit fault code alerts in CronJob.
 *
 * This service handles the pattern used by models like ModelAdvancedEnergySolaron,
 * ModelChintSolectriaInverterClass9725, ModelHuaweiSun200028ktl, etc.
 *
 * Flow for each fault code field:
 *   1. Query last N rows from data table → count how many rows have the same fault code value
 *   2. If count >= minConsistentRows AND faultCode > 0:
 *      → Decode each bit via toBinary32Bit
 *      → For each bit=1: resolve errorId via BitCodeFaultConfig.errorIdResolver
 *      → Insert alert if not already open
 *   3. If count == 0 (no fault in recent rows):
 *      → Close all open alerts for this faultCodeLevel
 *
 * This service does NOT modify any existing Model*Service files.
 *
 * @author duc.pham
 * @since 2026-04-24
 */
@Service
public class TriggerAlertBitCodeService extends DB {

    /**
     * @description Main entry point. Process all fault code fields for a device.
     *
     * @param datatablename  device data table name
     * @param deviceId       device ID
     * @param currentTime    current UTC time string (yyyy-MM-dd HH:mm:ss)
     * @param config         BitCodeAlertConfig implementation for this model
     */
    public void checkTriggerBitCodeAlert(String datatablename, int deviceId,
                                         String currentTime, BitCodeAlertConfig config) {
        try {
            // Step 1: fetch last N rows for consistency check
            Map<String, Object> params = new HashMap<>();
            params.put("datatablename", datatablename);
            params.put("id_device", deviceId);

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> recentRows =
                    (List<Map<String, Object>>) queryForList(config.getCheckAlertWriteCodeQueryId(), params);

            if (recentRows == null) recentRows = new ArrayList<>();

            // Step 2: process each fault code field
            for (BitCodeFaultConfig faultCfg : config.getFaultConfigs()) {
                processFaultField(deviceId, currentTime, faultCfg, recentRows, config.getMinConsistentRows());
            }

        } catch (Exception e) {
            log.error("TriggerAlertBitCodeService.checkTriggerBitCodeAlert device=" + deviceId, e);
        }
    }

    /**
     * @description Process a single fault code field.
     */
    private void processFaultField(int deviceId, String currentTime,
                                   BitCodeFaultConfig faultCfg,
                                   List<Map<String, Object>> recentRows,
                                   int minConsistentRows) {
        try {
            // Count consistent rows (same non-zero fault code value)
            long latestFaultCode = extractLatestFaultCode(recentRows, faultCfg.getFieldName());
            int consistentCount = countConsistentRows(recentRows, faultCfg.getFieldName(), latestFaultCode);

            if (latestFaultCode > 0 && consistentCount >= minConsistentRows) {
                // Decode bits and open alerts
                openAlertsFromBitCode(deviceId, currentTime, latestFaultCode, faultCfg);
            } else if (consistentCount == 0) {
                // No fault in recent rows → close open alerts for this level
                closeAlertsForFaultLevel(deviceId, currentTime, faultCfg);
            }
        } catch (Exception e) {
            log.error("TriggerAlertBitCodeService.processFaultField device=" + deviceId
                    + " field=" + faultCfg.getFieldName(), e);
        }
    }

    /**
     * @description Extract the latest (most recent) fault code value for a field.
     * Returns 0 if no valid value found.
     */
    private long extractLatestFaultCode(List<Map<String, Object>> rows, String fieldName) {
        if (rows == null || rows.isEmpty()) return 0;
        Object val = rows.get(0).get(fieldName);
        if (val == null) return 0;
        double d = ((Number) val).doubleValue();
        return (d > 0 && d != 0.001) ? (long) d : 0;
    }

    /**
     * @description Count how many rows have the same non-zero fault code value as the latest.
     */
    private int countConsistentRows(List<Map<String, Object>> rows, String fieldName, long latestFaultCode) {
        if (latestFaultCode == 0) return 0;
        int count = 0;
        for (Map<String, Object> row : rows) {
            Object val = row.get(fieldName);
            if (val == null) continue;
            double d = ((Number) val).doubleValue();
            long rowCode = (d > 0 && d != 0.001) ? (long) d : 0;
            if (rowCode == latestFaultCode) count++;
        }
        return count;
    }

    /**
     * @description Decode fault code via toBinary32Bit and open alerts for each active bit,
     * OR call errorIdResolver directly with the raw fault code value (isBitDecode=false).
     */
    private void openAlertsFromBitCode(int deviceId, String currentTime,
                                       long faultCode, BitCodeFaultConfig faultCfg) {
        try {
            if (faultCfg.isBitDecode()) {
                // Pattern A: decode each bit
                String binary = Long.toBinaryString(faultCode);
                String binary32 = String.format("%32s", binary).replace(' ', '0');

                int bitPos = 0;
                for (int b = binary32.length() - 1; b >= 0; b--) {
                    int bitLevel = Character.getNumericValue(binary32.charAt(b));
                    if (bitLevel == 1) {
                        int errorId = faultCfg.getErrorIdResolver().applyAsInt(bitPos);
                        if (errorId > 0) {
                            insertAlertIfNotExists(deviceId, currentTime, errorId);
                        }
                    }
                    bitPos++;
                }
            } else {
                // Pattern B: direct lookup with raw fault code value
                int errorId = faultCfg.getErrorIdResolver().applyAsInt((int) faultCode);
                if (errorId > 0) {
                    insertAlertIfNotExists(deviceId, currentTime, errorId);
                }
            }
        } catch (Exception e) {
            log.error("TriggerAlertBitCodeService.openAlertsFromBitCode device=" + deviceId
                    + " field=" + faultCfg.getFieldName(), e);
        }
    }

    /**
     * @description Insert a new alert if it doesn't already exist (in alert or alert_queue).
     */
    private void insertAlertIfNotExists(int deviceId, String currentTime, int errorId) {
        try {
            AlertEntity alert = new AlertEntity();
            alert.setId_device(deviceId);
            alert.setId_error(errorId);

            boolean exists = (int) queryForObject("BatchJob.checkAlertlExist", alert) > 0;
            boolean errorExists = (int) queryForObject("BatchJob.checkErrorExist", alert) > 0;

            if (!exists && errorExists) {
                alert.setStart_date(currentTime);
                insert("BatchJob.insertAlert", alert);
                log.info("TriggerAlertBitCodeService: inserted alert device=" + deviceId + " errorId=" + errorId);
            }
        } catch (Exception e) {
            log.error("TriggerAlertBitCodeService.insertAlertIfNotExists device=" + deviceId
                    + " errorId=" + errorId, e);
        }
    }

    /**
     * @description Close all open alerts for a given faultCodeLevel.
     */
    private void closeAlertsForFaultLevel(int deviceId, String currentTime, BitCodeFaultConfig faultCfg) {
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
                    toClose.setEnd_date(currentTime);
                    update("Alert.UpdateErrorRow", toClose);
                    log.info("TriggerAlertBitCodeService: closed alert id=" + toClose.getId()
                            + " device=" + deviceId + " errorId=" + toClose.getId_error());
                } catch (Exception ex) {
                    log.error("TriggerAlertBitCodeService.closeAlertsForFaultLevel item error device="
                            + deviceId, ex);
                }
            }
        } catch (Exception e) {
            log.error("TriggerAlertBitCodeService.closeAlertsForFaultLevel device=" + deviceId
                    + " field=" + faultCfg.getFieldName(), e);
        }
    }
}
