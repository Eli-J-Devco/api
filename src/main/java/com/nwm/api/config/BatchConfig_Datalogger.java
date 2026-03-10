/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.config;

import com.nwm.api.batchjob.BatchJobDatalogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@ConditionalOnProperty(
        name = "datalogger.cronjob.active",
        havingValue = "true",
        matchIfMissing = false
)
public class BatchConfig_Datalogger {
    @Autowired
    BatchJobDatalogger batchJobDatalogger;

    final boolean isFirstRun = true;

    @Scheduled(cron = "*/15 * * * * ?")
    public void syncData()  {
        batchJobDatalogger.syncData(isFirstRun);
    }

    @Scheduled(cron = "*/30 * * * * ?")
    public void syncDataCheck()  {
        batchJobDatalogger.syncData(!isFirstRun);
    }
}
