package com.nwm.api.config;

import com.nwm.api.batchjob.BatchJobDatalogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name = "data_logger_ModelChintSolectriaInverterClass9725_cronjob_active",
        havingValue = "true",
        matchIfMissing = false
)
public class BatchConfig_Datalogger_ModelChintSolectriaInverterClass9725 {
    @Autowired
    BatchJobDatalogger batchJobDatalogger;

    @Scheduled(cron = "0 */1 * * * ?")
    public void syncData_ModelChintSolectriaInverterClass9725()  {
        batchJobDatalogger.syncData_ModelChintSolectriaInverterClass9725();
    }
}
