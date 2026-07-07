package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModelHuaweiSun2000V1Config implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelHuaweiSun2000V1.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
                new BitCodeFaultConfig("Alarm1", 1, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelHuaweiSun2000V1(bitPos, 1)),

                new BitCodeFaultConfig("Alarm2", 2, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelHuaweiSun2000V1(bitPos, 2)),

                new BitCodeFaultConfig("Alarm3", 3, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelHuaweiSun2000V1(bitPos, 3))
        );
    }
}
