/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.ModelATITrackerMotorEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelATITrackerMotorService extends DB {

	public ModelATITrackerMotorEntity setModelATITrackerMotor(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelATITrackerMotorEntity dataModel = new ModelATITrackerMotorEntity();
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModel.setSetpointPosition(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setActualPosition(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setAlarms(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setMode(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setManualPosition(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				
				return dataModel;
				
			} else {
				return new ModelATITrackerMotorEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelATITrackerMotorEntity();
		}
	}

	/**
	 * @description insert data from datalogger to model_kippzonen_rt1_class8009
	 * @author long.pham
	 * @since 2021-04-02
	 * @param data from datalogger
	 */
	
	public boolean insertModelATITrackerMotor(ModelATITrackerMotorEntity obj) {
		try {
			 Object insertId = insert("ModelATITrackerMotor.insertModelATITrackerMotor", obj);
		        if(insertId == null ) {
		        	return false;
		        }
            ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
            ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
            int hours = zdtNow.getHour();

            if (hours >= 9 && hours <= 17 && obj.getEnable_alert() >= 1) {
                checkTriggerAlertModelATITrackerMotor(obj);
            }
            return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}

    /**
     * @description get data 20 row to check alarm code
     * @author quan.nguyen
     * @since 2026-04-14
     */
    private ModelATITrackerMotorEntity checkAlertWriteCode(ModelATITrackerMotorEntity obj) {
        ModelATITrackerMotorEntity rowItem = new ModelATITrackerMotorEntity();
        try {
            List dataList = queryForList("ModelATITrackerMotor.checkAlertWriteCode", obj);
            if (dataList == null || dataList.size() < 20) {
                return new ModelATITrackerMotorEntity();
            }
            boolean allSame = dataList.stream().allMatch(o -> {
                Map<String, Object> item = (Map<String, Object>) o;
                double alarms = ((Number) item.get("Alarms")).doubleValue();
                return Double.compare(obj.getAlarms(), alarms) == 0;
            });
            // If even a single item in the list has a different value from the input (meaning the 20 rows are not all consecutive with the same value), then return early.
            if (!allSame) {
                return new ModelATITrackerMotorEntity();
            }
            // At this point, all 20 items in the list are guaranteed to have the same value; set the total to 20 to determine whether to open or close the alert.
            Map<String, Object> last = (Map<String, Object>) dataList.get(dataList.size() - 1);
            String time = (String) last.get("time");
            rowItem.setTotalAlarm(20);
            rowItem.setTime(time);
        } catch (Exception ex) {
            return new ModelATITrackerMotorEntity();
        }
        return rowItem;
    }

    /**
     * @description check open or close alert with data from db
     * @author quan.nguyen
     * @since 2026-04-14
     */
    private void checkTriggerAlertModelATITrackerMotor(ModelATITrackerMotorEntity obj) {
        List<AlertEntity> insertList = new ArrayList<>();
        List<AlertEntity> updateList = new ArrayList<>();
        final int maxBitCheck = 5;
        try {
            ModelATITrackerMotorEntity rowItem = checkAlertWriteCode(obj);
            // do nothing if not enough 20 row
            if (rowItem.getTotalAlarm() < 20) {
                return;
            }
            long alarmCode = (obj.getAlarms() > 0 && obj.getAlarms() != 0.001) ? (long) obj.getAlarms() : 0;
            if (alarmCode > 0) {
                String binary = Long.toBinaryString(alarmCode);
                int len = binary.length();
                for (int i = 0; i < maxBitCheck; i++) {
                    int errorId = LibErrorCode.GetAlarmCodeModelATITrackerMotor(i);
                    if (errorId <= 0) {
                        continue;
                    }
                    int bitIndex = len - 1 - i;
                    int bitLevel = (bitIndex >= 0 && binary.charAt(bitIndex) == '1') ? 1 : 0;
                    AlertEntity alert = new AlertEntity();
                    alert.setId_device(obj.getId_device());
                    alert.setStart_date(rowItem.getTime());
                    alert.setId_error(errorId);
                    if (bitLevel == 1) {
                        boolean exists = (int) queryForObject("BatchJob.checkAlertlExist", alert) > 0;
                        boolean errorExists = (int) queryForObject("BatchJob.checkErrorExist", alert) > 0;

                        if (!exists && errorExists) {
                            insertList.add(alert);
                        }
                    }
                }
            } else {
                for (int i = 0; i < maxBitCheck; i++) {
                    int errorId = LibErrorCode.GetAlarmCodeModelATITrackerMotor(i);
                    if (errorId <= 0) {
                        continue;
                    }
                    AlertEntity alertDeviceItem = new AlertEntity();
                    alertDeviceItem.setId_device(obj.getId_device());
                    alertDeviceItem.setEnd_date(rowItem.getTime());
                    alertDeviceItem.setId_error(errorId);
                    AlertEntity openedAlert = (AlertEntity) queryForObject("BatchJob.getAlertDetail", alertDeviceItem);
                    if (openedAlert == null || openedAlert.getId() == 0) {
                        continue;
                    }
                    updateList.add(openedAlert);
                }
            }

            if (!insertList.isEmpty()) {
                insert("BatchJob.batchInsertAlert", insertList);
            }

            if (!updateList.isEmpty()) {
                Map<String, Object> params = new HashMap<>();
                params.put("list", updateList);
                params.put("end_date", rowItem.getTime());
                update("BatchJob.batchUpdateAlert", params);
            }
        } catch (Exception e) {
            log.error("ModelATITrackerMotorService.checkTriggerAlertModelATITrackerMotor", e);
        }
    }

}