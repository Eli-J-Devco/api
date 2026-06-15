package com.nwm.api.batchjob;

import com.nwm.api.services.BatchJobService;
import com.nwm.api.services.BatchJobSolarEdgeAutoFillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchJobSolarEdgeAutoFill {

    @Autowired
    BatchJobSolarEdgeAutoFillService batchJobSolarEdgeAutoFillService;

    public void startBatchJobAutoBackfillSolarEdgeAPI() {
        try {
            batchJobSolarEdgeAutoFillService.startBatchJobAutoBackfillSolarEdgeAPI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
