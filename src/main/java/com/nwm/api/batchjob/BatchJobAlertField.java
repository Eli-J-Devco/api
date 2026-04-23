/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.batchjob;

import com.nwm.api.services.CronJobAlertFieldService;
import com.nwm.api.utils.FLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Batch job component for Field alert checking.
 * Delegates all logic to CronJobAlertFieldService.
 *
 * @author duc.pham
 * @since 2026-04-21
 */
@Component
public class BatchJobAlertField {

    private static final FLLogger log = FLLogger.getLogger("batchjob/BatchJobAlertField");

    @Autowired
    private CronJobAlertFieldService cronJobAlertFieldService;

    /**
     * Entry point called by BatchConfig_AlertSMP4DP.
     * Delegates to the service which handles server splitting + multi-threading.
     */
    public void runAlertCheck() {
        try {
            cronJobAlertFieldService.runAlertCheck();
        } catch (Exception e) {
            log.error("BatchJobAlertField error: ", e);
        }
    }
}
