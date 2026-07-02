package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModelSevSg110cxConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelSevSg110cx.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Collections.singletonList(
                new BitCodeFaultConfig("FaultCode", 1, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetFaultCodeModelSevSg110cx(bitPos), false)
        );
    }
}
