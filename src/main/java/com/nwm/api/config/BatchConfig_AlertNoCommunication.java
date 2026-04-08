/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.config;

import com.nwm.api.batchjob.BatchJobAlertNoCommunication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled configuration for No Communication alert cron job.
 * Runs every 30 seconds for testing.
 *
 * Enable: alert.nocomm.cronjob.active=true in application-{env}.properties
 */
@Component
@ConditionalOnProperty(
        name = "alert.nocomm.cronjob.active",
        havingValue = "true"
)
public class BatchConfig_AlertNoCommunication {

    @Autowired
    private BatchJobAlertNoCommunication batchJobAlertNoCommunication;

    /**
     * Run No Communication check every 30 seconds
     */
    @Scheduled(cron = "0 */2 * * * *")
    public void runNoCommunicationCheck() {
        batchJobAlertNoCommunication.runNoCommunicationCheck();
    }
}

