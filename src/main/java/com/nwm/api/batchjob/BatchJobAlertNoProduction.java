/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.batchjob;

import com.nwm.api.services.CronJobAlertNoProductionsService;
import com.nwm.api.utils.FLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchJobAlertNoProduction {

    private static final FLLogger log = FLLogger.getLogger("batchjob/BatchJobAlertNoProduction");

    @Autowired
    private CronJobAlertNoProductionsService cronJobAlertNoProductionsService;

    public void runNoProductionCheck() {
        log.info("===== BatchJobAlertNoProduction START =====");
        long startTime = System.currentTimeMillis();

        try {
            cronJobAlertNoProductionsService.runNoProductionCheck();
        } catch (Exception e) {
            log.error("BatchJobAlertNoProduction error: ", e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("===== BatchJobAlertNoProduction END. Total: " + duration + "ms =====");
        }
    }
}

