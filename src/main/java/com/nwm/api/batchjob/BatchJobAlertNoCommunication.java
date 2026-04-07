/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.batchjob;

import com.nwm.api.services.CronJobAlertNoCommunicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(BatchJobAlertNoCommunication.class);
    private final CronJobAlertNoCommunicationService cronJobAlertNoCommunicationService;

    public BatchJobAlertNoCommunication(CronJobAlertNoCommunicationService cronJobAlertNoCommunicationService) {
        this.cronJobAlertNoCommunicationService = cronJobAlertNoCommunicationService;
    }

    /**
     * Entry point called by BatchConfig_AlertNoCommunication.
     * Delegates to the service which handles server splitting and alert checks.
     */
    public void runNoCommunicationCheck() {
        log.info("===== BatchJobAlertNoCommunication START =====");
        long startTime = System.currentTimeMillis();

        try {
            cronJobAlertNoCommunicationService.runNoCommunicationCheck();
        } catch (Exception e) {
            log.error("BatchJobAlertNoCommunication error", e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("===== BatchJobAlertNoCommunication END. Total: {}ms =====", duration);
        }
    }
}

