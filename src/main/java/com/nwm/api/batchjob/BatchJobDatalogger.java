/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.batchjob;

import com.nwm.api.services.DataloggerSyncService;
import com.nwm.api.utils.FLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchJobDatalogger {

    @Autowired
    private DataloggerSyncService dataloggerSyncService;
    /**
     * @description: Handle logic for syncdata from Posgres Db data654_1000000094a21ccb
     * @author: Minh Le
     * @date: 15-01-2026
     */
    public void syncData() {
        final FLLogger log = com.nwm.api.utils.FLLogger.getLogger("batchjob/BatchJobDatalogger");
        log.info("Start sync data from Postgres database");

        try {
            dataloggerSyncService.syncData();
        } catch (Exception e) {
            log.error("Cron error: ", e);
        } finally {
            log.info("Cron end !!!");
        }
    }
}
