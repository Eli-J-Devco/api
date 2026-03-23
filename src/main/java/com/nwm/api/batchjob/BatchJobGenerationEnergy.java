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
}
