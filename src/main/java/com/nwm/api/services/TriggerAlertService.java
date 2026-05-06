package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BaseAlertEnum;
import com.nwm.api.utils.Lib;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TriggerAlertService extends DB {

    /**
     * @description check trigger alert for a device using BaseAlertEnum array.
     *              Queries aggregated data in 120-minute window (BatchJob.getDataIn120Min).
     *              For each alert enum: value > 0 and no existing alert → insert new alert;
     *              value = 0 and alert is open → close alert (update end_date).
     * @author duc.pham
     * @since 2026-04-24
     * @param tableName device data table name (datatablename)
     * @param time current time UTC format "yyyy-MM-dd HH:mm:ss"
     * @param deviceId device ID
     * @param alertEnums array of alert enums to check
     */
    public void checkTriggerAlert(String tableName, String time, int deviceId, BaseAlertEnum[] alertEnums) {
        if (alertEnums == null || alertEnums.length == 0) return;

        try {
            List<String> fieldNames = Arrays.stream(alertEnums)
                    .map(BaseAlertEnum::getColumn)
                    .collect(Collectors.toList());

            Map<String, Object> params = new HashMap<>();
            params.put("fields", fieldNames);
            params.put("data_table_name", tableName);
            params.put("time", time);
            params.put("id_device", deviceId);

            @SuppressWarnings("unchecked")
            Map<String, Object> row = (Map<String, Object>) queryForObject("BatchJob.getDataIn120Min", params);
            if (row == null || row.isEmpty()) {
                return;
            }

            List<AlertEntity> insertList = new ArrayList<>();
            List<AlertEntity> updateList = new ArrayList<>();

            for (BaseAlertEnum alert : alertEnums) {
                Object valueObj = row.get(alert.getColumn());
                int isActive = (valueObj != null) ? ((Number) valueObj).intValue() : 0;

                Object timeObj = row.get(alert.getColumn() + "_time");
                String alertTime = (timeObj != null) ? timeObj.toString() : null;

                processAlert(deviceId, alertTime, isActive > 0, alert.getId(), insertList, updateList);
            }

            // Batch insert new alerts
            if (!insertList.isEmpty()) {
                insert("BatchJob.batchInsertAlert", insertList);
            }

            // Batch close alerts that are no longer active
            if (!updateList.isEmpty()) {
                Map<String, Object> updateParams = new HashMap<>();
                updateParams.put("list", updateList);
                updateParams.put("end_date", time);
                update("BatchJob.batchUpdateAlert", updateParams);
            }

        } catch (Exception e) {
            log.error("TriggerAlertService.checkTriggerAlert device=" + deviceId + " table=" + tableName, e);
        }
    }

    /**
     * @description overload for generic enum type - delegates to the main method.
     *              Kept for backward compatibility with existing callers using typed enum arrays.
     * @author duc.pham
     * @since 2026-04-15
     * @param tableName device data table name
     * @param time current time UTC
     * @param deviceId device ID
     * @param alertEnums typed enum array implementing BaseAlertEnum
     */
    public <E extends Enum<E> & BaseAlertEnum> void checkTriggerAlert(String tableName, String time, int deviceId, E[] alertEnums) {
        checkTriggerAlert(tableName, time, deviceId, (BaseAlertEnum[]) alertEnums);
    }

    /**
     * @description process a single alert: insert if error is active and not existing,
     *              or mark for close if error is inactive and alert is open
     * @author duc.pham
     * @since 2026-04-15
     * @param deviceId device ID
     * @param time alert start time
     * @param isError whether the error is currently active
     * @param errorId error ID from alert enum
     * @param insertList accumulator for new alerts to insert
     * @param updateList accumulator for open alerts to close
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
                // Only insert if alert does not already exist (avoid duplicates)
                boolean exists = (int) queryForObject("BatchJob.checkAlertlExist", alert) > 0;
                if (!exists) {
                    alert.setStart_date(time);
                    insertList.add(alert);
                }
            } else {
                // Find open alert to close
                AlertEntity openedAlert = (AlertEntity) queryForObject("BatchJob.getAlertDetail", alert);
                if (openedAlert != null && openedAlert.getId() > 0) {
                    updateList.add(openedAlert);
                }
            }
        } catch (Exception e) {
            log.error("TriggerAlertService.processAlert device=" + deviceId + " errorId=" + errorId, e);
        }
    }
}
