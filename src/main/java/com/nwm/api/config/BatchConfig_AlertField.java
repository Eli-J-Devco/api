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
 * @description scheduled configuration for field alert cron job.
 *              Enable by setting alert.field.cronjob.active=true in application-{env}.properties
 * @author duc.pham
 * @since 2026-04-21
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
     * @description run once immediately when application is ready (useful for testing/debugging)
     * @author duc.pham
     * @since 2026-04-21
     */
    @EventListener(ApplicationReadyEvent.class)
    public void runOnStartup() {
        batchJobAlertField.runAlertCheck();
    }

    /**
     * @description run field alert check every 1 minute
     * @author duc.pham
     * @since 2026-04-21
     */
    @Scheduled(cron = "0 */1 * * * *")
    public void runAlertCheck() {
        batchJobAlertField.runAlertCheck();
    }
}
