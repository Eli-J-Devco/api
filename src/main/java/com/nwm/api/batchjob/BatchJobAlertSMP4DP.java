/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.batchjob;

import com.nwm.api.services.CronJobAlertSMP4DPService;
import com.nwm.api.utils.FLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Batch job component for SMP4DP alert checking.
 * Delegates all logic to CronJobAlertSMP4DPService.
 *
 * @author duc.pham
 * @since 2026-04-21
 */
@Component
public class BatchJobAlertSMP4DP {

    private static final FLLogger log = FLLogger.getLogger("batchjob/BatchJobAlertSMP4DP");

    @Autowired
    private CronJobAlertSMP4DPService cronJobAlertSMP4DPService;

    /**
     * Entry point called by BatchConfig_AlertSMP4DP.
     * Delegates to the service which handles server splitting + multi-threading.
     */
    public void runAlertCheck() {
        try {
            cronJobAlertSMP4DPService.runAlertCheck();
        } catch (Exception e) {
            log.error("BatchJobAlertSMP4DP error: ", e);
        }
    }
}
