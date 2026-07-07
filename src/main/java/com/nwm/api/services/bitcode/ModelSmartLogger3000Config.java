package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModelSmartLogger3000Config implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelSmartLogger3000.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
                new BitCodeFaultConfig("AlarmInfo1", 1, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelSmartLogger3000(bitPos, 1)),

                new BitCodeFaultConfig("AlarmInfo2", 2, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelSmartLogger3000(bitPos, 2)),

                new BitCodeFaultConfig("AlarmInfo3", 3, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelSmartLogger3000(bitPos, 3))
        );
    }
}
