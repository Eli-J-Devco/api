package com.nwm.api.services.bitcode;

import java.util.Arrays;
import java.util.List;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

/**
 * @description BitCodeAlertConfig for model_PVH_Tbox.
 *              Uses toBinary32Bit decode pattern.
 *              Fault fields: Alarms, Warnings.
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelPVHTboxAlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelPVHTbox.getListTriggerFaultCode";

    /**
     * @description get fault code field configurations for PVH Tbox model
     * @author duc.pham
     * @since 2026-04-24
     * @return list of 2 fault field configs (Alarms, Warnings)
     */
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
