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

@Component
@ConditionalOnProperty(
        name = "datalogger.cronjob.active",
        havingValue = "true",
        matchIfMissing = false
)
public class BatchGenerationEnergy {
    @Autowired
    BatchJobGenerationEnergy batchJobGenerationEnergy;

    @Scheduled(fixedDelay = 60000) // 60s =
    public void generationEnergyData()  {
        batchJobGenerationEnergy.generationEnergyData();
    }

    /**
     * @description Cron job to update energy yesterday for all sites.
     *              Runs every day at 00:00:00 UTC.
     *              The service layer uses AtomicReference<LocalDate> to guarantee
     *              at-most-once execution per UTC calendar day, even if the cron
     *              fires multiple times or the server restarts.
     * @author Long Pham
     * @date 26-03-2026
     */
    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void updateEnergyYesterday() {
        batchJobGenerationEnergy.updateEnergyYesterday();
    }
}
