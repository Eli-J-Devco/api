package com.nwm.api.batchjob;

import com.nwm.api.services.DataloggerSynchronizeService;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchJobDatalogger {
    @Scheduled(cron = "* */3 * * * ?")
    public void dataSynchronize() throws SQLException {
        DataloggerSynchronizeService dataloggerSynchronizeService = new DataloggerSynchronizeService();

        dataloggerSynchronizeService.handleData_ModelChintSolectriaInverterClass9725("data654_1000000094a21ccb");
    }
}
