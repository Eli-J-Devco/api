/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.config;

import com.nwm.api.batchjob.BatchJobGenerationEnergy;
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
public class BatchGenerationEnergy {
    @Autowired
    BatchJobGenerationEnergy batchJobGenerationEnergy;

    private final boolean isFirstRun = true;

    @Scheduled(fixedDelay = 60000) // 60s =
    public void generationEnergyData()  {
        batchJobGenerationEnergy.generationEnergyData();
    }
}
