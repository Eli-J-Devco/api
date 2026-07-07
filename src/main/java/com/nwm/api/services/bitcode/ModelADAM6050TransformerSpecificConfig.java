package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.List;

public class ModelADAM6050TransformerSpecificConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelADAM6050TransformerSpecific.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
                new BitCodeFaultConfig("Vacuum", 1, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetAlertModelADAM6050TransformerSpecific(bitPos, 1), false),

                new BitCodeFaultConfig("Pressure", 2, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetAlertModelADAM6050TransformerSpecific(bitPos, 2), false),

                new BitCodeFaultConfig("LiquidLevel", 3, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetAlertModelADAM6050TransformerSpecific(bitPos, 3), false),

                new BitCodeFaultConfig("LiquidTemperatureHigh", 4, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetAlertModelADAM6050TransformerSpecific(bitPos, 3), false),

                new BitCodeFaultConfig("LiquidTemperatureWarning", 5, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetAlertModelADAM6050TransformerSpecific(bitPos, 3), false)
        );
    }
}
