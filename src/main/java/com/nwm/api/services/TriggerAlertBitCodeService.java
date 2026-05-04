package com.nwm.api.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.nwm.api.utils.Lib;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.FLLogger;

/**
 * Service xử lý alert cho các model dùng fault code (toBinary32Bit + direct lookup).
 * Lấy tất cả rows trong 2h → per-field tìm chuỗi liên tục fault > 0 từ row mới nhất.
 * Chỉ trigger alert khi chuỗi liên tục >= 2 giờ.
 */
@Service
public class TriggerAlertBitCodeService extends DB {

    // Ghi log vào cùng file với CronJobAlertFieldService
    private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobAlertField");

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
                processFaultField(deviceId, currentTime, faultCfg, allRows);
            }
        } catch (Exception e) {
            log.error("[BitCode] FAIL device=" + deviceId, e);
        }
    }

    private void processFaultField(int deviceId, String currentTime,
                                   BitCodeFaultConfig faultCfg,
                                   List<Map<String, Object>> allRows) {
        try {
            if (allRows.isEmpty()) return;

            String fieldName = faultCfg.getFieldName();

            // Tìm chuỗi liên tục từ row mới nhất mà fault > 0
            List<Map<String, Object>> streak = new ArrayList<>();
            for (Map<String, Object> row : allRows) {
                if (extractFaultCode(row, fieldName) > 0) {
                    streak.add(row);
                } else {
                    break;
                }
            }

            // Không có fault ở row mới nhất → close alerts
            if (streak.isEmpty()) {
                log.info("[BitCode] device=" + deviceId + " field=" + fieldName + " → no fault, closing");
                String alertCloseTime = (String) allRows.get(0).get(fieldName + "_close_time");
                closeAlertsForFaultLevel(deviceId, alertCloseTime, faultCfg);
                return;
            }

            // Check chuỗi liên tục >= 2 giờ
            Date newestTime = parseTime(streak.get(0).get("time"));
            Date oldestTime = parseTime(streak.get(streak.size() - 1).get("time"));
            if (newestTime == null || oldestTime == null) return;

            long durationMin = (newestTime.getTime() - oldestTime.getTime()) / 60000;

            if (durationMin < 120) {
                log.info("[BitCode] device=" + deviceId + " field=" + fieldName
                        + " streak=" + streak.size() + "rows " + durationMin + "min < 2h → skip");
                return;
            }
            // make sure all row is same value
            if (!checkContinuousValue(streak, faultCfg)) {
                log.info("[BitCode] device=" + deviceId + " field=" + faultCfg.getFieldName()
                        + " inconsistent codes → skip");
                return;
            }

            log.info("[BitCode] device=" + deviceId + " field=" + fieldName
                    + " streak=" + streak.size() + "rows " + durationMin + "min → TRIGGER");

            if (faultCfg.isBitDecode()) {
                openBitDecodeAlerts(deviceId, faultCfg, streak);
            } else {
                openDirectLookupAlert(deviceId, faultCfg, streak);
            }
        } catch (Exception e) {
            log.error("[BitCode] FAIL device=" + deviceId + " field=" + faultCfg.getFieldName(), e);
        }
    }

    private boolean checkContinuousValue(List<Map<String, Object>> streak, BitCodeFaultConfig faultCfg) {
        long firstCode = extractFaultCode(streak.get(0), faultCfg.getFieldName());
        for (Map<String, Object> row : streak) {
            if (extractFaultCode(row, faultCfg.getFieldName()) != firstCode) {
                return false;
            }
        }
        return true;
    }

    private void openBitDecodeAlerts(int deviceId,  BitCodeFaultConfig faultCfg, List<Map<String, Object>> streak) {
        long consistentBits = 0xFFFFFFFFL;
        String alertTime = (String) streak.get(0).get(faultCfg.getFieldName() + "_start_time");
        for (Map<String, Object> row : streak) {
            consistentBits &= extractFaultCode(row, faultCfg.getFieldName());
        }

        log.info("[BitCode] device=" + deviceId + " field=" + faultCfg.getFieldName()
                + " consistentBits=" + Long.toBinaryString(consistentBits));

        for (int bitPos = 0; bitPos < 32; bitPos++) {
            if ((consistentBits & (1L << bitPos)) != 0) {
                int errorId = faultCfg.getErrorIdResolver().applyAsInt(bitPos);
                if (errorId > 0) {
                    insertAlertIfNotExists(deviceId, alertTime, errorId);
                }
            }
        }
    }

    private void openDirectLookupAlert(int deviceId, BitCodeFaultConfig faultCfg, List<Map<String, Object>> streak) {
        long firstCode = extractFaultCode(streak.get(0), faultCfg.getFieldName());
        String alertTime = (String) streak.get(0).get(faultCfg.getFieldName() + "_start_time");
        int errorId = faultCfg.getErrorIdResolver().applyAsInt((int) firstCode);
        if (errorId > 0) insertAlertIfNotExists(deviceId, alertTime, errorId);
    }

    private long extractFaultCode(Map<String, Object> row, String fieldName) {
        Object val = row.get(fieldName);
        if (val == null) return 0;
        double d = ((Number) val).doubleValue();
        return (d > 0 && d != 0.001) ? (long) d : 0;
    }

    private Date parseTime(Object timeVal) {
        if (timeVal == null) return null;
        if (timeVal instanceof Date) return (Date) timeVal;
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeVal.toString());
        } catch (Exception e) {
            return null;
        }
    }

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
                log.info("[BitCode] INSERT alert device=" + deviceId + " errorId=" + errorId);
            }
        } catch (Exception e) {
            log.error("[BitCode] FAIL insertAlert device=" + deviceId + " errorId=" + errorId, e);
        }
    }

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
                    log.info("[BitCode] CLOSE alert id=" + toClose.getId()
                            + " device=" + deviceId + " errorId=" + toClose.getId_error());
                } catch (Exception ex) {
                    log.error("[BitCode] FAIL closeAlert device=" + deviceId, ex);
                }
            }
        } catch (Exception e) {
            log.error("[BitCode] FAIL closeAlertsForFaultLevel device=" + deviceId, e);
        }
    }
}
