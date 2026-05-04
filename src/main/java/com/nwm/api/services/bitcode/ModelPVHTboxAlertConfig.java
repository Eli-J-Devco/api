package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.List;

/**
 * @description BitCodeAlertConfig for model_pvh_master (ModelPVHTbox).
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelPVHTboxAlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelPVHTbox.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("Alarms", 1, CLOSE_QUERY,
                bitPos -> LibErrorCode.GeAlarmPVHTbox(bitPos))
        );
    }
}
