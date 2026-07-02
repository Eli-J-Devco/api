package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.List;

public class ModelSmaCoreConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelSmaCore.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
                new BitCodeFaultConfig("EventMessage", 1, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetAlertModelSmaStp2550us50(bitPos, 1), false),

                new BitCodeFaultConfig("BlockStatus", 2, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetAlertModelSmaStp2550us50(bitPos, 2), false),

                new BitCodeFaultConfig("ReasonforDerating", 3, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetAlertModelSmaStp2550us50(bitPos, 3), false)
        );
    }
}
