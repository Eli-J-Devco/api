package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModelATITrackerMotorAlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelATITrackerMotor.getListTriggerFaultCode";
    private static final int MAX_BIT_CHECK = 5;

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Collections.singletonList(
                new BitCodeFaultConfig("Alarms", 1, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetAlarmCodeModelATITrackerMotor(bitPos), MAX_BIT_CHECK)
        );
    }
}
