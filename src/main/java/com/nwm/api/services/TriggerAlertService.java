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

    List<AlertEntity> insertList = new ArrayList<>();
    List<AlertEntity> updateList = new ArrayList<>();

    /**
     * @description check trigger alert for field-based models (120 minutes window)
     * @since 2026-04-15
     * @param tableName
     * @param time
     * @param deviceId
     * @param alertEnums
     */
    public <E extends Enum<E> & BaseAlertEnum> void checkTriggerAlert(String tableName, String time, int deviceId, E[] alertEnums) {
        try {
        	List<String> fieldNames = Arrays.stream(alertEnums)
                    .map(e -> e.getColumn())
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
                Object timeObj = row.get(alert.getColumn() + "_time");
                String alertTime = (timeObj != null) ? timeObj.toString() : null;
                processAlert(deviceId, alertTime, isActive > 0, alert.getId());
            }

            if (!insertList.isEmpty()) {
                insert("BatchJob.batchInsertAlert", insertList);
                insertList = new ArrayList<>();
            }

            if (!updateList.isEmpty()) {
                params = new HashMap<>();
                params.put("list", updateList);
                params.put("end_date", time);
                update("BatchJob.batchUpdateAlert", params);
                updateList = new ArrayList<>();
            }

        } catch (Exception e) {
            log.error("TriggerAlertService.checkTriggerAlert", e);
        }
    }

    /**
     * @description process alert: insert new alert when error value > 0, update end_date when error value = 0
     * @since 2026-04-15
     * @param deviceId, time, isError, errorId
     */
    private void processAlert(int deviceId, String time, boolean isError, int errorId) {
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
                updateList.add(openedAlert);
            }
        } catch (Exception e) {
            log.error("TriggerAlertService.processAlert", e);
        }
    }
     /**
     * @description Process binary 32-bit fault code and trigger alerts
     * @since 2026-04-24
     * @param faultCode The fault code value to convert to binary
     * @param deviceId The device ID
     * @param time The timestamp
     * @param faultCodeLevel The fault code level (1=fault1, 2=fault2, 3=fault3, 4=limits, 5=status, 6=warnings)
     * @param alreadyValidated Set to true if already validated by getDataIn120Min (skip occurrence check)
     * @param errorCodeMapper Function to map bit position to error ID
     */
    public void checkTriggerAlertToBinary32Bit(long faultCode, int deviceId, String time, int faultCodeLevel, 
                                                boolean alreadyValidated, FaultCodeMapper errorCodeMapper) {
        // Only trigger alert if fault code exists (validation already done by getDataIn120Min)
        if (faultCode > 0 && alreadyValidated) {
            try {
                String toBinary = Long.toBinaryString(faultCode);
                String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
                
                int bitPosition = 0;
                for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
                    int index = b;
                    int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
                    
                    if (bitLevel == 1) {
                        int errorId = errorCodeMapper.getErrorId(bitPosition, faultCodeLevel);
                        
                        if (errorId > 0) {
                            AlertEntity alertDeviceItem = new AlertEntity();
                            alertDeviceItem.setId_device(deviceId);
                            alertDeviceItem.setStart_date(time);
                            alertDeviceItem.setId_error(errorId);
                            
                            boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist", alertDeviceItem) > 0;
                            boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
                            
                            if (!checkAlertDeviceExist && errorExits) {
                                insert("BatchJob.insertAlert", alertDeviceItem);
                            }
                        }
                    }
                    bitPosition += 1;
                }
            } catch (Exception e) {
                log.error("TriggerAlertService.checkTriggerAlertToBinary32Bit", e);
            }
        }
    }

    /**
     * @description Close alerts for a specific fault code level when fault is resolved
     * @since 2026-04-24
     * @param deviceId The device ID
     * @param time The end timestamp
     * @param faultCodeLevel The fault code level to close
     * @param totalOccurrences Total occurrences of this fault code (should be 0 to close)
     */
    public void closeFaultCodeAlerts(int deviceId, String time, int faultCodeLevel, int totalOccurrences) {
        if (totalOccurrences == 0) {
            try {
                AlertEntity alertItemClose = new AlertEntity();
                alertItemClose.setId_device(deviceId);
                alertItemClose.setFaultCodeLevel(faultCodeLevel);
                
                List dataListFaultCode = queryForList("ModelAdvancedEnergySolaron.getListTriggerFaultCode", alertItemClose);
                
                if (dataListFaultCode != null && dataListFaultCode.size() > 0) {
                    for (int i = 0; i < dataListFaultCode.size(); i++) {
                        Map<String, Object> itemFault = (Map<String, Object>) dataListFaultCode.get(i);
                        int id = Integer.parseInt(itemFault.get("id").toString());
                        int idError = Integer.parseInt(itemFault.get("id_error").toString());
                        
                        alertItemClose.setEnd_date(time);
                        alertItemClose.setId(id);
                        alertItemClose.setId_error(idError);
                        update("Alert.UpdateErrorRow", alertItemClose);
                    }
                }
            } catch (Exception e) {
                log.error("TriggerAlertService.closeFaultCodeAlerts", e);
            }
        }
    }

    /**
     * @description Functional interface for mapping bit position to error ID
     * @since 2026-04-24
     */
    @FunctionalInterface
    public interface FaultCodeMapper {
        int getErrorId(int bitPosition, int faultCodeLevel);
    }

    public void _checkTriggerBinaryFaultCodeAlert(String tableName, String time, int deviceId,
                                                  String[] faultCodeFields,
                                                  FaultCodeMapper[] faultCodeMappers,
                                                  int[] faultCodeLevels) {
        if (Lib.isBlank(tableName) || faultCodeFields == null || faultCodeMappers == null || faultCodeLevels == null) {
            log.error("Invalid parameters");
            return;
        }

        if (faultCodeFields.length != faultCodeMappers.length
                || faultCodeFields.length != faultCodeLevels.length) {
            log.error("Array length mismatch");
            return;
        }

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("fields", Arrays.asList(faultCodeFields));
            params.put("data_table_name", tableName);
            params.put("time", time);
            params.put("id_device", deviceId);

            List<Map<String, Object>> rows = queryForList("BatchJob.getContinuousErrorFields", params);
            if (rows == null || rows.isEmpty()) {
                log.info("[Device " + deviceId + "] No data in 120 minutes");
                return;
            }
            Map<String, FaultCodeMapper> mapperMap = new HashMap<>();
            Map<String, Integer> levelMap = new HashMap<>();

            for (int i = 0; i < faultCodeFields.length; i++) {
                mapperMap.put(faultCodeFields[i], faultCodeMappers[i]);
                levelMap.put(faultCodeFields[i], faultCodeLevels[i]);
            }
            for (Map<String, Object> row : rows) {
                String fieldName = (String) row.get("field_name");
                int duration = (int) row.get("duration");
                if (Lib.isBlank(fieldName) || duration < 120) continue;

                FaultCodeMapper mapper = mapperMap.get(fieldName);
                Integer faultCodeLevel = levelMap.get(fieldName);
                if (mapper == null || faultCodeLevel == null) continue;

                Long fieldValue = (Long) row.get("field_value");
                String alertTime = (String) row.get("start_time");

                if (fieldValue > 0) {
                    // Fault code exists for 120+ minutes, need to get the actual fault code value
                    processBinaryFaultCodeFieldWithValue(tableName, deviceId, alertTime, fieldName, mapper, faultCodeLevel);
                    return;
                }
                // Fault code is 0 or doesn't meet 120-minute threshold, close alerts
                closeFaultCodeAlerts(deviceId, time, faultCodeLevel, 0);
            }
        } catch (Exception e) {
            log.error("TriggerAlertService.checkTriggerBinaryFaultCodeAlert", e);
        }
    }

    /**
     * @description Check trigger alert for binary fault code models (120 minutes window)
     * Supports models like ModelAdvancedEnergySolaron that use binary fault codes
     * Uses the same getDataIn120Min query as field-based models
     * @since 2026-04-24
     * @param tableName Data table name
     * @param time Current time
     * @param deviceId Device ID
     * @param faultCodeFields Array of fault code field names (e.g., ["active_faults1", "active_faults2", "active_faults3", "status", "warnings1", "limits"])
     * @param faultCodeMappers Array of FaultCodeMapper corresponding to each fault code field
     * @param faultCodeLevels Array of fault code levels corresponding to each fault code field
     */
    public void checkTriggerBinaryFaultCodeAlert(String tableName, String time, int deviceId, 
                                                   String[] faultCodeFields, 
                                                   FaultCodeMapper[] faultCodeMappers,
                                                   int[] faultCodeLevels) {
        if (faultCodeFields == null || faultCodeMappers == null || faultCodeLevels == null) {
            log.error("TriggerAlertService.checkTriggerBinaryFaultCodeAlert - Invalid parameters");
            return;
        }
        
        if (faultCodeFields.length != faultCodeMappers.length || faultCodeFields.length != faultCodeLevels.length) {
            log.error("TriggerAlertService.checkTriggerBinaryFaultCodeAlert - Array length mismatch");
            return;
        }

        try {
            // Use the same getDataIn120Min query as field-based models
            List<String> fieldNames = Arrays.asList(faultCodeFields);
            
            Map<String, Object> params = new HashMap<>();
            params.put("fields", fieldNames);
            params.put("data_table_name", tableName);
            params.put("time", time);
            params.put("id_device", deviceId);

            Map<String, Object> row = (Map<String, Object>) queryForObject("BatchJob.getDataIn120Min", params);
            
            if (row == null || row.isEmpty()) {
                log.info("[Device " + deviceId + "] No data found in last 120 minutes");
                return;
            }

            // Process each fault code field
            for (int i = 0; i < faultCodeFields.length; i++) {
                String fieldName = faultCodeFields[i];
                FaultCodeMapper mapper = faultCodeMappers[i];
                int faultCodeLevel = faultCodeLevels[i];
                
                // Get the field value (0 or 1 indicating if error exists for 120+ minutes)
                Object valueObj = row.get(fieldName);
                int isActive = (valueObj != null) ? ((Number) valueObj).intValue() : 0;
                
                // Get the timestamp when the error first occurred
                Object timeObj = row.get(fieldName + "_time");
                String alertTime = (timeObj != null) ? timeObj.toString() : null;
                
                if (isActive > 0 && alertTime != null) {
                    // Fault code exists for 120+ minutes, need to get the actual fault code value
                    processBinaryFaultCodeFieldWithValue(tableName, deviceId, alertTime, fieldName, mapper, faultCodeLevel);
                } else {
                    // Fault code is 0 or doesn't meet 120-minute threshold, close alerts
                    closeFaultCodeAlerts(deviceId, time, faultCodeLevel, 0);
                }
            }

        } catch (Exception e) {
            log.error("TriggerAlertService.checkTriggerBinaryFaultCodeAlert", e);
        }
    }

    /**
     * @description Get the actual fault code value and process binary alerts
     * @since 2026-04-24
     */
    private void processBinaryFaultCodeFieldWithValue(String tableName, int deviceId, String time, 
                                                       String fieldName, FaultCodeMapper mapper, int faultCodeLevel) {
        try {
            // Get the actual fault code value from the most recent record
            Map<String, Object> params = new HashMap<>();
            params.put("data_table_name", tableName);
            params.put("id_device", deviceId);
            params.put("field_name", fieldName);
            
            Map<String, Object> result = (Map<String, Object>) queryForObject("BatchJob.getLatestFaultCodeValue", params);
            
            if (result != null && result.get(fieldName) != null) {
                long faultCode = ((Number) result.get(fieldName)).longValue();
                
                if (faultCode > 0) {
                    // Trigger alerts for this fault code (already validated by getDataIn120Min - 120 minutes + TIMESTAMPDIFF)
                    checkTriggerAlertToBinary32Bit(
                        faultCode, 
                        deviceId, 
                        time, 
                        faultCodeLevel, 
                        true, // Already validated by getDataIn120Min query
                        mapper
                    );
                }
            }
            
        } catch (Exception e) {
            log.error("TriggerAlertService.processBinaryFaultCodeFieldWithValue - field: " + fieldName, e);
        }
    }

}
