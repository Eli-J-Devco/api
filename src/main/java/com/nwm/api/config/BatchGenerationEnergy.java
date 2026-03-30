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

    /**
     * @description batch job generation energy today
     * @author long.pham
     * @since 2022-03-22
     */
    @Scheduled(fixedDelay = 60000)
    public void generationEnergyData()  {
        batchJobGenerationEnergy.generationEnergyData();
    }

    /**
     * @description batch job update energy yesterday - runs daily at 00:00
     * @author long.pham
     * @since 2026-03-26
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void updateEnergyYesterday() {
        batchJobGenerationEnergy.updateEnergyYesterday();
    }

    /**
     * @description batch job update energy last week - runs daily at 14:00
     * @author long.pham
     * @since 2026-03-30
     */
    @Scheduled(cron = "0 0 14 * * *")
    public void updateEnergyLastWeek() {
        batchJobGenerationEnergy.updateEnergyLastWeek();
    }

    /**
     * @description batch job update avg daily 7 days - runs daily at 14:15
     * @author long.pham
     * @since 2026-03-30
     */
    @Scheduled(cron = "0 15 14 * * *")
    public void updateEnergyAVGDaily7Days() {
        batchJobGenerationEnergy.updateEnergyAVGDaily7Days();
    }

    /**
     * @description batch job update avg daily last 7 days - runs daily at 14:30
     * @author long.pham
     * @since 2026-03-30
     */
    @Scheduled(cron = "0 30 14 * * *")
    public void updateEnergyAVGDailyLast7Days() {
        batchJobGenerationEnergy.updateEnergyAVGDailyLast7Days();
    }

    /**
     * @description batch job update energy last month - runs daily at 14:45
     * @author long.pham
     * @since 2026-03-30
     */
    @Scheduled(cron = "0 45 14 * * *")
    public void updateEnergyLastMonth() {
        batchJobGenerationEnergy.updateEnergyLastMonth();
    }

    /**
     * @description batch job update energy compare last month - runs daily at 15:00
     * @author long.pham
     * @since 2026-03-30
     */
    @Scheduled(cron = "0 0 15 * * *")
    public void updateEnergyCompareLastMonth() {
        batchJobGenerationEnergy.updateEnergyCompareLastMonth();
    }

    /**
     * @description batch job update energy last 12 months - runs daily at 15:15
     * @author long.pham
     * @since 2026-03-30
     */
    @Scheduled(cron = "0 15 15 * * *")
    public void updateEnergyLast12Months() {
        batchJobGenerationEnergy.updateEnergyLast12Months();
    }
}
