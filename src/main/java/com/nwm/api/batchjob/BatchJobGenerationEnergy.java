/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.batchjob;

import com.nwm.api.services.GenerationEnergyService;
import com.nwm.api.utils.FLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchJobGenerationEnergy {

    @Autowired
    private GenerationEnergyService generationEnergyService;
    /**
     * @description: generation energy today
     * @author: {@link Long} Pham
     * @date: 22-03-2026
     */
    public void generationEnergyData() {
        final FLLogger log = com.nwm.api.utils.FLLogger.getLogger("batchjob/BatchJobGenerationEnergy");
        log.info("Start generation energy today");

        try {
        	generationEnergyService.generationEnergyData();
        } catch (Exception e) {
            log.error("Cron error: ", e);
        } finally {
            log.info("Cron end !!!");
        }
    }

    /**
     * @description Update energy yesterday for all sites.
     *              Called once per day at 00:00:00 UTC.
     *              The service layer guarantees at-most-once execution per UTC day.
     * @author Long Pham
     * @date 26-03-2026
     */
    public void updateEnergyYesterday() {
        final FLLogger log = com.nwm.api.utils.FLLogger.getLogger("batchjob/BatchJobGenerationEnergy");
        log.info("Start updateEnergyYesterday cron job");

        try {
            generationEnergyService.updateEnergyYesterday();
        } catch (Exception e) {
            log.error("updateEnergyYesterday cron error: ", e);
        } finally {
            log.info("updateEnergyYesterday cron end !!!");
        }
    }
}
