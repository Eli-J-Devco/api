/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.batchjob;

import com.nwm.api.services.CronJobAlertNoCommunicationService;
import com.nwm.api.utils.FLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Batch job component for No Communication alert checking.
 * Follows the same pattern as BatchJobDatalogger.
 *
 * @author duc.van
 * @since 2026-04-06
 */
@Component
public class BatchJobAlertNoCommunication {

    private static final FLLogger log = FLLogger.getLogger("batchjob/BatchJobAlertNoCommunication");

    @Autowired
    private CronJobAlertNoCommunicationService cronJobAlertNoCommunicationService;

    /**
     * Entry point called by BatchConfig_AlertNoCommunication.
     * Delegates to the service which handles server splitting + multi-threading.
     */
    public void runNoCommunicationCheck() {
//        log.info("===== BatchJobAlertNoCommunication START =====");
//        long startTime = System.currentTimeMillis();

        try {
            cronJobAlertNoCommunicationService.runNoCommunicationCheck();
        } catch (Exception e) {
            log.error("BatchJobAlertNoCommunication error: ", e);
        } finally {
//            long duration = System.currentTimeMillis() - startTime;
//            log.info("===== BatchJobAlertNoCommunication END. Total: " + duration + "ms =====");
        }
    }
}

