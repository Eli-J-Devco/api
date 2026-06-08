package com.nwm.api.config;

import com.nwm.api.batchjob.BatchJob;
import com.nwm.api.batchjob.BatchJobSolarEdgeAutoFill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnProperty(name = "solaredge.cronjob.auto_backfill", havingValue = "true")
public class BatchConfigSolarEdgeAutoFill {
    @Autowired
    private BatchJobSolarEdgeAutoFill batchJobSolarEdgeAutoFill;

    @Scheduled(cron = "0 */5 * * * *")
    public void startBatchJobAutoBackfillSolarEdgeAPI() {
        try {
            batchJobSolarEdgeAutoFill.startBatchJobAutoBackfillSolarEdgeAPI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
