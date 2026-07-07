package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.List;

public class ModelPVHMasterConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelPVHMaster.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
                new BitCodeFaultConfig("UPSAlarm", 1, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetAlertModelADAM6050TransformerSpecific(bitPos, 1), false),

                new BitCodeFaultConfig("WindSpeedAlarm", 2, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetAlertModelADAM6050TransformerSpecific(bitPos, 2), false),

                new BitCodeFaultConfig("WindInactivityAlarm", 3, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetAlertModelADAM6050TransformerSpecific(bitPos, 3), false),

                new BitCodeFaultConfig("AnemometerWarning", 4, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetAlertModelADAM6050TransformerSpecific(bitPos, 3), false)
        );
    }
}
