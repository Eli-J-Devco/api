/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.batchjob;

import com.nwm.api.services.DataloggerSynchronizeService;
import com.nwm.api.utils.FLLogger;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchJobDatalogger {
    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * @description: Cronjob for syndata from Posgres Db data654_1000000094a21ccb
     * @author: Minh Le
     * @date: 15-01-2026
     */
    @Scheduled(cron = "0 */2 * * * ?")
    public synchronized void syncData_ModelChintSolectriaInverterClass9725() {
        final String modelName = "ModelChintSolectriaInverterClass9725";
        final String dbName = "data654_1000000094a21ccb";

        final FLLogger log = com.nwm.api.utils.FLLogger.getLogger("batchjob/BatchJobDatalogger" +  "_" + modelName);
        log.info("Start sync data from Postgres database: " + dbName);

        if (!running.compareAndSet(false, true)) {
            log.warn("Sync data cron is running, skip !!!");
            return;
        }

        DataloggerSynchronizeService dataloggerSynchronizeService = new DataloggerSynchronizeService();

        try {
            dataloggerSynchronizeService.handleData_ModelChintSolectriaInverterClass9725(dbName);
        } catch (Exception e) {
            log.error("Cron error: ", e);
        } finally {
            running.set(false);
            log.info("Cron end !!!");
        }
    }
}
