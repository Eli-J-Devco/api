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

    @Value ("${cron.device.alert.nocomm.close.active:false}")
    private boolean closeNoCommActive;

    @Value ("${cron.device.alert.noproduction.close.active:false}")
    private boolean closeNoProdActive;

    @Value("${cron.device.alert.email.notify.active:true}")
    private boolean emailNotifyActive;

    @Autowired
    private BatchJobDetectDeviceStatus batchJobDetechDeviceStatus;

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
        batchJobDetechDeviceStatus.runNoCommunicationCheck();
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
        batchJobDetechDeviceStatus.runNoProductionCheck();
    }

    /**
     * Run Close No Communication check every 30 minutes (cron expression is configurable via application properties).
     */
    @Scheduled(cron = "${cron.device.alert.nocomm.close.scheduler}")
    public void runCloseNoCommunicationCheck() {
        if (!closeNoCommActive) {
            log.info("Close No Communication check is disabled.");
            return;
        }
        log.info("Running Close No Communication check...");
        batchJobDetechDeviceStatus.runCloseNoCommunicationCheck();
    }

    /**
     * Run Close No Production check every 30 minutes (cron expression is configurable via application properties).
     */
    @Scheduled(cron = "${cron.device.alert.noproduction.close.scheduler}")
    public void runCloseNoProductionCheck() {
        if (!closeNoProdActive) {
            log.info("Close No Production check is disabled.");
            return;
        }
        log.info("Running Close No Production check...");
        batchJobDetechDeviceStatus.runCloseNoProductionCheck();
    }

    /**
     * Run Email Notification check every 30 minutes (cron expression is configurable via application properties).
     */
    @Scheduled(cron = "${cron.device.alert.email.notify.scheduler}")
    public void runEmailNotification() {
        if (!emailNotifyActive) {
            log.info("Email Notification check is disabled.");
            return;
        }
        log.info("Running Email Notification check...");
        batchJobDetechDeviceStatus.runEmailNotification();
    }
}

