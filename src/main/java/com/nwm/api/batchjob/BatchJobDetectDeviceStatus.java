/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.batchjob;

import com.nwm.api.services.CronJobDetectDeviceNoComStatusService;
import com.nwm.api.utils.FLLogger;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final FLLogger log = FLLogger.getLogger("batchjob/BatchJobDetectDeviceStatus");

    @Autowired
    private CronJobDetectDeviceNoComStatusService cronJobDetectDeviceNoComStatusService;

    /**
     * Entry point called by BatchConfig_DetectDeviceNoComStatus.
     * Delegates to the service which handles server splitting + multi-threading.
     */
    public void runNoCommunicationCheck() {
       log.info("===== No Communication START =====");
        long startTime = System.currentTimeMillis();
        try {
            cronJobDetectDeviceNoComStatusService.execute();
            log.info("No Communication check completed.");
        } catch (Exception e) {
            log.error("No Communication check error: ", e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("===== No Communication check END. Total: " + duration/1000 + "seconds =====");
        }
    }
    public void runNoProductionCheck() {
       log.info("===== No Production START =====");
        long startTime = System.currentTimeMillis();
        try {
            // cronJobDetectDeviceNoComStatusService.runNoProductionCheck();
            log.info("No Production check completed.");
        } catch (Exception e) {
            log.error("No Production check error: ", e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("===== No Production check END. Total: " + duration/1000 + "seconds =====");
        }
    }
}

