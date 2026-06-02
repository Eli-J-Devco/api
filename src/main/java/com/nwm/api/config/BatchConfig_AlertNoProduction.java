/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.config;

import com.nwm.api.batchjob.BatchJobAlertNoProduction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name = "alert.noproduction.cronjob.active",
        havingValue = "true"
)
public class BatchConfig_AlertNoProduction {

    @Autowired
    private BatchJobAlertNoProduction batchJobAlertNoProduction;

    @Scheduled(cron = "*/30 * * * * *")
//    @Scheduled(cron = "0 */30 * * * *")
    public void runNoProductionCheck() {
        batchJobAlertNoProduction.runNoProductionCheck();
    }
}

