package com.nwm.api.batchjob;

import com.nwm.api.services.BatchJobDeviceWorkHourService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.FLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchJobDeviceWorkHour {

    private static final FLLogger log = FLLogger.getLogger("batchjob/BatchJobDeviceWorkHour");
    @Autowired
    BatchJobDeviceWorkHourService service;

    public void startJob(String type) {
        try {
            service.startJob(type);
        } catch (Exception e) {
            log.error("BatchJobDeviceWorkHour.startJob", e);
        }
    }
}
