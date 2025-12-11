/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.ModelHuaweiSun200028ktlEntity;
import com.nwm.api.utils.LibErrorCode;

/**
 * @description Async service for processing alerts in background (NO Spring DI)
 * @author Duc.pham
 * @since 2025-12-05
 */
public class AsyncAlertProcessorService extends DB {
	
	// Thread pool configuration (optimized for high volume)
	private static final int CORE_POOL_SIZE = 20;       // Minimum threads (increased)
	private static final int MAX_POOL_SIZE = 50;        // Maximum threads (increased for burst)
	private static final int QUEUE_CAPACITY = 10000;    // Max queued tasks (10K for high volume)
	private static final long KEEP_ALIVE_TIME = 60L;    // Idle thread timeout (seconds)
	
	private static final ExecutorService executorService = new ThreadPoolExecutor(
		CORE_POOL_SIZE,
		MAX_POOL_SIZE,
		KEEP_ALIVE_TIME,
		TimeUnit.SECONDS,
		new LinkedBlockingQueue<>(QUEUE_CAPACITY),
		new ThreadPoolExecutor.DiscardOldestPolicy() // If queue full, discard oldest task (non-blocking)
	);
	
	/**
	 * @description Process alert asynchronously - non-blocking
	 * Creates a new async task for each alert check
	 * @author Duc.pham
	 * @since 2025-12-05
	 * @param obj data object to check alerts for
	 * @param datatablename table name for data queries
	 * @param viewTablename view table name for queries
	 */
	public void processAlertAsync(ModelHuaweiSun200028ktlEntity obj, String datatablename, String viewTablename) {
		// Set table names once here
		obj.setDatatablename(datatablename);
		obj.setView_tablename(viewTablename);
		// Create async task - returns immediately without blocking
		CompletableFuture.runAsync(() -> {
			long startTime = System.currentTimeMillis();
			String taskId = "Device-" + obj.getId_device() + "-" + startTime;
			
			try {
				
				log.info("[TASK START] " + taskId + " | Starting async alert processing");
				checkTriggerAlertModelHuaweiSun200028ktl(obj);
				
				long duration = System.currentTimeMillis() - startTime;
				log.info("[TASK COMPLETE] " + taskId + " | Completed successfully in " + duration + "ms");
			} catch (Exception ex) {
				long duration = System.currentTimeMillis() - startTime;
				log.error("[TASK FAILED] " + taskId + " | Failed after " + duration + "ms: " + ex.getMessage(), ex);
			} finally {
				// Task auto-closes here
				long duration = System.currentTimeMillis() - startTime;
				log.info("[TASK CLOSED] " + taskId + " | Task finished and resources released (" + duration + "ms)");
			}
		}, executorService);
	}
	
	/**
	 * @description Check trigger alert fault code (optimized logic)
	 * @author Duc.pham
	 * @since 2025-12-05
	 * @param obj entity object from datalogger
	 */
	private void checkTriggerAlertModelHuaweiSun200028ktl(ModelHuaweiSun200028ktlEntity obj) {
		// Check device alert by fault code
		long fault1 = (obj.getMajorFaultCode() > 0 && obj.getMajorFaultCode() != 0.001) ? (long) obj.getMajorFaultCode() : 0;
		long fault2 = (obj.getMinorFaultCode() > 0 && obj.getMinorFaultCode() != 0.001) ? (long) obj.getMinorFaultCode() : 0;
		long fault3 = (obj.getWarningCode() > 0 && obj.getWarningCode() != 0.001) ? (long) obj.getWarningCode() : 0;

		ModelHuaweiSun200028ktlEntity rowItem = checkAlertWriteCode(obj);
		
		// Process all 3 fault levels using reusable method
		processFaultLevel(obj, fault1, rowItem.getTotalFault1(), 1);
		processFaultLevel(obj, fault2, rowItem.getTotalFault2(), 2);
		processFaultLevel(obj, fault3, rowItem.getTotalFault3(), 3);
	}
	
	/**
	 * @description Process single fault level - trigger or close alert
	 * @author Duc.pham
	 * @since 2025-12-05
	 * @param obj entity object
	 * @param faultCode fault code value
	 * @param totalFault total fault count from last 20 records
	 * @param faultLevel fault level (1=Major, 2=Minor, 3=Warning)
	 */
	private void processFaultLevel(ModelHuaweiSun200028ktlEntity obj, long faultCode, int totalFault, int faultLevel) {
		if (faultCode > 0 && totalFault >= 20) {
			triggerAlert(obj, faultCode, faultLevel);
		} else if (totalFault == 0) {
			closeAlert(obj, faultLevel);
		}
	}
	
	/**
	 * @description Trigger alert for fault code
	 * @author Duc.pham
	 * @since 2025-12-05
	 */
	private void triggerAlert(ModelHuaweiSun200028ktlEntity obj, long faultCode, int faultLevel) {
		try {
			String toBinary = Long.toBinaryString(faultCode);
			String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
			String causeId = toBinary32Bit.substring(17, 32);
			String alarmId = toBinary32Bit.substring(0, 16);

			if (!causeId.isEmpty() && !alarmId.isEmpty()) {
				int decimalCauseId = Integer.parseInt(causeId, 2);
				int decimalAlarmId = Integer.parseInt(alarmId, 2);
				if (decimalCauseId > 0 && decimalAlarmId > 0) {
					int errorId = LibErrorCode.GetErrorCodeModelHuaweiSun200028kt(decimalAlarmId, decimalCauseId, faultLevel);
					if (errorId > 0) {
						AlertEntity alertDeviceItem = new AlertEntity();
						alertDeviceItem.setId_device(obj.getId_device());
						alertDeviceItem.setStart_date(obj.getTime());
						alertDeviceItem.setId_error(errorId);
						boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist", alertDeviceItem) > 0;
						boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
						if (!checkAlertDeviceExist && errorExits) {
							insert("BatchJob.insertAlert", alertDeviceItem);
							log.info("Alert triggered - Device: " + obj.getId_device() + ", Fault Level " + faultLevel + ", Error ID: " + errorId);
						}
					}
				}
			}
		} catch (SQLException e) {
			log.error("Error handling fault code " + faultLevel, e);
		}
	}
	
	/**
	 * @description Close alert for fault level
	 * @author Duc.pham
	 * @since 2025-12-05
	 */
	private void closeAlert(ModelHuaweiSun200028ktlEntity obj, int faultLevel) {
		try {
			AlertEntity alertItemClose = new AlertEntity();
			alertItemClose.setId_device(obj.getId_device());
			alertItemClose.setFaultCodeLevel(faultLevel);
			List dataList = queryForList("ModelHuaweiSun200028ktl_v2.getListTriggerFaultCode_v2", alertItemClose);
			if (!dataList.isEmpty()) {
				for (int i = 0; i < dataList.size(); i++) {
					Map<String, Object> itemFault = (Map<String, Object>) dataList.get(i);
					alertItemClose.setEnd_date(itemFault.get("end_date").toString());
					alertItemClose.setId(Integer.parseInt(itemFault.get("id").toString()));
					alertItemClose.setId_error(Integer.parseInt(itemFault.get("id_error").toString()));
					update("Alert.UpdateErrorRow", alertItemClose);
				}
				log.info("Alert closed - Device: " + obj.getId_device() + ", Fault Level " + faultLevel);
			}
		} catch (SQLException e) {
			log.error("Error closing fault code " + faultLevel, e);
		}
	}
	
	/**
	 * @description Check alert write code - count fault occurrences
	 * @author Duc.pham
	 * @since 2025-12-05
	 */
	private ModelHuaweiSun200028ktlEntity checkAlertWriteCode(ModelHuaweiSun200028ktlEntity obj) {
		ModelHuaweiSun200028ktlEntity rowItem = new ModelHuaweiSun200028ktlEntity();
		try {
			List dataList = queryForList("ModelHuaweiSun200028ktl_v2.checkAlertWriteCode_v2", obj);
			if (dataList.size() > 0) {
				int totalFault1 = 0, totalFault2 = 0, totalFault3 = 0;
				for (int i = 0; i < dataList.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double MajorFaultCode = (double) item.get("MajorFaultCode");
					if (Double.compare(obj.getMajorFaultCode(), MajorFaultCode) == 0 && obj.getMajorFaultCode() > 0 && MajorFaultCode > 0) {
						totalFault1++;
					}

					double MinorFaultCode = (double) item.get("MinorFaultCode");
					if (Double.compare(obj.getMinorFaultCode(), MinorFaultCode) == 0 && obj.getMinorFaultCode() > 0 && MinorFaultCode > 0) {
						totalFault2++;
					}

					double WarningCode = (double) item.get("WarningCode");
					if (Double.compare(obj.getWarningCode(), WarningCode) == 0 && obj.getWarningCode() > 0 && WarningCode > 0) {
						totalFault3++;
					}
				}
				rowItem.setTotalFault1(totalFault1);
				rowItem.setTotalFault2(totalFault2);
				rowItem.setTotalFault3(totalFault3);
			}
		} catch (Exception ex) {
			log.error("checkAlertWriteCode", ex);
		}
		return rowItem;
	}
}
