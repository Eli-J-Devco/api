/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.batchjob;

import com.nwm.api.services.CronJobAlertEmailNotifyService;
import com.nwm.api.services.CronJobCloseDeviceNoComStatusService;
import com.nwm.api.services.CronJobCloseDeviceNoProdStatusService;
import com.nwm.api.services.CronJobDetectDeviceNoComStatusService;
import com.nwm.api.services.CronJobDetectDeviceNoProductionStatusService;
import com.nwm.api.utils.FLLogger;
import org.springframework.stereotype.Component;

/**
 * Batch job component for No Communication alert checking.
 * Follows the same pattern as BatchJobDatalogger.
 *
 * @author chuong.ma
 * @since 2026-09-25
 */
@Component
public class BatchJobDetectDeviceStatus {

    private static final String DETECT_NO_COMM_JOB_CODE = "DETECT_DEVICE_NO_COM_STATUS";
    private static final String DETECT_NO_PROD_JOB_CODE = "DETECT_DEVICE_NO_PROD_STATUS";
    private static final String CLOSE_NO_COMM_JOB_CODE = "CLOSE_DEVICE_NO_COM_STATUS";
    private static final String CLOSE_NO_PROD_JOB_CODE = "CLOSE_DEVICE_NO_PROD_STATUS";
    private static final String EMAIL_NOTIFY_JOB_CODE = "EMAIL_ALERT_NOTIFICATION";
    private static final FLLogger log = FLLogger.getLogger("batchjob/BatchJobDetectDeviceStatus");

    private CronJobDetectDeviceNoComStatusService cronJobDetectDeviceNoComStatusService;
    private CronJobDetectDeviceNoProductionStatusService cronJobDetectDeviceNoProductionStatusService;
    private CronJobCloseDeviceNoComStatusService cronJobCloseDeviceNoComStatusService;
    private CronJobCloseDeviceNoProdStatusService cronJobCloseDeviceNoProductionStatusService;
    private CronJobAlertEmailNotifyService cronJobAlertEmailNotifyService;

    public BatchJobDetectDeviceStatus(CronJobDetectDeviceNoComStatusService cronJobDetectDeviceNoComStatusService,
                                       CronJobDetectDeviceNoProductionStatusService cronJobDetectDeviceNoProductionStatusService,
                                      CronJobCloseDeviceNoComStatusService cronJobCloseDeviceNoComStatusService,
                                      CronJobCloseDeviceNoProdStatusService cronJobCloseDeviceNoProductionStatusService,
                                      CronJobAlertEmailNotifyService cronJobAlertEmailNotifyService) {
        this.cronJobDetectDeviceNoComStatusService = cronJobDetectDeviceNoComStatusService;
        this.cronJobDetectDeviceNoProductionStatusService = cronJobDetectDeviceNoProductionStatusService;
        this.cronJobCloseDeviceNoComStatusService = cronJobCloseDeviceNoComStatusService;
        this.cronJobCloseDeviceNoProductionStatusService = cronJobCloseDeviceNoProductionStatusService;
        this.cronJobAlertEmailNotifyService = cronJobAlertEmailNotifyService;
    }

    /**
     * Entry point called by BatchConfig_DetectDeviceNoComStatus.
     * Delegates to the service which handles server splitting + multi-threading.
     */
    public void runNoCommunicationCheck() {
       log.info("===== No Communication START =====");
        long startTime = System.currentTimeMillis();
        try {
            cronJobDetectDeviceNoComStatusService.updateJobSchedulerStatus(DETECT_NO_COMM_JOB_CODE, "START");
            cronJobDetectDeviceNoComStatusService.execute();
            log.info("No Communication check completed.");
        } catch (Exception e) {
            log.error("No Communication check error: ", e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("===== No Communication check END. Total: " + duration/1000 + "seconds =====");
            cronJobDetectDeviceNoComStatusService.updateJobSchedulerStatus(DETECT_NO_COMM_JOB_CODE, "END");
        }
    }
    public void runCloseNoCommunicationCheck() {
       log.info("===== Close No Communication START =====");
        long startTime = System.currentTimeMillis();
        try {
             cronJobCloseDeviceNoComStatusService.updateJobSchedulerStatus(CLOSE_NO_COMM_JOB_CODE, "START");
             cronJobCloseDeviceNoComStatusService.execute();
            log.info("Close No Communication check completed.");
        } catch (Exception e) {
            log.error("Close No Communication check error: ", e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("===== Close No Communication check END. Total: " + duration/1000 + "seconds =====");
             cronJobCloseDeviceNoComStatusService.updateJobSchedulerStatus(CLOSE_NO_COMM_JOB_CODE, "END");
        }
    }

    public void runNoProductionCheck() {
       log.info("===== No Production START =====");
        long startTime = System.currentTimeMillis();
        try {
            cronJobDetectDeviceNoProductionStatusService.updateJobSchedulerStatus(DETECT_NO_PROD_JOB_CODE, "START");
            cronJobDetectDeviceNoProductionStatusService.execute();
            log.info("No Production check completed.");
        } catch (Exception e) {
            log.error("No Production check error: ", e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("===== No Production check END. Total: " + duration/1000 + "seconds =====");
            cronJobDetectDeviceNoProductionStatusService.updateJobSchedulerStatus(DETECT_NO_PROD_JOB_CODE, "END");
        }
    }
    public void runCloseNoProductionCheck() {
       log.info("===== Close No Production START =====");
        long startTime = System.currentTimeMillis();
        try {
             cronJobCloseDeviceNoProductionStatusService.updateJobSchedulerStatus(CLOSE_NO_PROD_JOB_CODE, "START");
             cronJobCloseDeviceNoProductionStatusService.execute();
            log.info("No Production check completed.");
        } catch (Exception e) {
            log.error("No Production check error: ", e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("===== Close No Production check END. Total: " + duration/1000 + "seconds =====");
             cronJobCloseDeviceNoProductionStatusService.updateJobSchedulerStatus(CLOSE_NO_PROD_JOB_CODE, "END");
        }
    }

    /**
     * @description execute the email notification for alerts
     */
    public void runEmailNotification() {
       log.info("===== Email Notification START =====");
        long startTime = System.currentTimeMillis();
        try {
            cronJobAlertEmailNotifyService.updateJobSchedulerStatus(EMAIL_NOTIFY_JOB_CODE, "START");
            cronJobAlertEmailNotifyService.execute();
            log.info("Email notification completed.");
        } catch (Exception e) {
            log.error("Email notification error: ", e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("===== Email Notification END. Total: " + duration/1000 + "seconds =====");
            cronJobAlertEmailNotifyService.updateJobSchedulerStatus(EMAIL_NOTIFY_JOB_CODE, "END");
        }
    }
    
}

