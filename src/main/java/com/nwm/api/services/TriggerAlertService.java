package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BaseAlertEnum;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TriggerAlertService extends DB {

    List<AlertEntity> insertList = new ArrayList<>();
    List<AlertEntity> updateList = new ArrayList<>();

    /**
     * @description check trigger COMM_FAIL alert
     * @since 2026-04-15
     * @param tableName
     * @param time
     * @param deviceId
     * @param alertEnums
     */
    public <E extends Enum<E> & BaseAlertEnum> void checkTriggerAlert(String tableName, String time, int deviceId, E[] alertEnums) {
        try {
            List<String> fieldNames = Arrays.stream(alertEnums)
                    .map(BaseAlertEnum::getColumn)
                    .collect(Collectors.toList());

            Map<String, Object> params = new HashMap<>();
            params.put("fields", fieldNames);
            params.put("data_table_name", tableName);
            params.put("time", time);
            params.put("id_device", deviceId);

            Map<String, Object> row = (Map<String, Object>) queryForObject("BatchJob.getDataIn120Min", params);
            if (row == null || row.isEmpty()) {
                return;
            }
            for (E alert : alertEnums) {
                Object valueObj = row.get(alert.getColumn());
                int isActive = (valueObj != null) ? ((Number) valueObj).intValue() : 0;
                processAlert(deviceId, time, isActive > 0, alert.getId());
            }

            if (!insertList.isEmpty()) {
                insert("BatchJob.batchInsertAlert", insertList);
            }

            if (!updateList.isEmpty()) {
                params = new HashMap<>();
                params.put("list", updateList);
                params.put("end_date", time);
                update("BatchJob.batchUpdateAlert", params);
            }

        } catch (Exception e) {
            log.error("checkTriggerCommFailAlert", e);
        }
    }

    /**
     * @description process alert: insert new alert when error value > 0, update end_date when error value = 0
     * @since 2026-04-15
     * @param deviceId, time, isError, errorId
     */
    private void processAlert(int deviceId, String time, boolean isError, int errorId) {
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
                updateList.add(openedAlert);
            }
        } catch (Exception e) {
            log.error("processAlert", e);
        }
    }
}
