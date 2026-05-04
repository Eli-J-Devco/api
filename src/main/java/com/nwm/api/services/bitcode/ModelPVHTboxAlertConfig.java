package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.List;

/**
 * BitCodeAlertConfig for model_PVH_Tbox.
 * Fields from original: Alarms, Warnings
 */
public class ModelPVHTboxAlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelPVHTbox.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("Alarms", 1, CLOSE_QUERY,
                bitPos -> LibErrorCode.GeAlarmPVHTbox(bitPos)),

            new BitCodeFaultConfig("Warnings", 2, CLOSE_QUERY,
                bitPos -> LibErrorCode.GeAlarmPVHTbox(bitPos))
        );
    }
}
