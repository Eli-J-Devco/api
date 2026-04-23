/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.config;

import com.nwm.api.batchjob.BatchJobAlertField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled configuration for alert cron job.
 *
 * Enable: alert.field.cronjob.active=true in application-{env}.properties
 */
@Component
@ConditionalOnProperty(
        name = "alert.field.cronjob.active",
        havingValue = "true"
)
public class BatchConfig_AlertField {

    @Autowired
    private BatchJobAlertField batchJobAlertField;

    /**
     * Run once immediately when app is ready — useful for testing.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void runOnStartup() {
        batchJobAlertField.runAlertCheck();
        
    }

    /**
     * Run SMP4DP alert check every 1 minute.
     */
    @Scheduled(cron = "0 */1 * * * *")
    public void runAlertCheck() {
        batchJobAlertField.runAlertCheck();
    }
}
