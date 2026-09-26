/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.config;

import com.nwm.api.batchjob.BatchJobDetectDeviceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.nwm.api.utils.FLLogger;

/**
 * Scheduled configuration for No Communication alert cron job.
 * Runs every 30 seconds for testing.
 *
 * Enable: alert.nocomm.cronjob.active=true in application-{env}.properties
 */
@Component
@ConditionalOnProperty(
        name = "cron.device.alert.active",
        havingValue = "true"
)
public class BatchConfig_DetectDeviceStatus {

    @Value("${cron.device.alert.nocomm.active:false}")
    private boolean nocommActive;

    @Value("${cron.device.alert.noproduction.active:false}")
    private boolean noproductionActive;

    @Autowired
    private BatchJobDetectDeviceStatus batchJobDetechDeviceNoCommunication;

    private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobDetectDeviceStatus");
    /**
     * Run No Communication check every 30 minutes (cron expression is configurable via application properties).
     */
    @Scheduled(cron = "${cron.device.alert.nocomm.scheduler}")
    public void runNoCommunicationCheck() {
        if (!nocommActive) {
            log.info("No Communication check is disabled.");
            return;
        }
        log.info("Running No Communication check...");
        batchJobDetechDeviceNoCommunication.runNoCommunicationCheck();
    }

    /**
     * Run No Production check every 30 minutes (cron expression is configurable via application properties).
     */
    @Scheduled(cron = "${cron.device.alert.noproduction.scheduler}")
    public void runNoProductionCheck() {
        if (!noproductionActive) {
            log.info("No Production check is disabled.");
            return;
        }
        log.info("Running No Production check...");
    }
}

