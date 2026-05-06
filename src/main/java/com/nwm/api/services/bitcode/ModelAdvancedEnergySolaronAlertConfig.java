package com.nwm.api.services.bitcode;

import java.util.Arrays;
import java.util.List;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

/**
 * @description BitCodeAlertConfig implementation for model_advanced_energy_solaron.
 *              Fault fields: active_faults1, active_faults2, active_faults3, limits, status, warnings1.
 *              All fields use toBinary32Bit decode pattern.
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelAdvancedEnergySolaronAlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelAdvancedEnergySolaron.getListTriggerFaultCode";

    /**
     * @description get fault code field configurations for Advanced Energy Solaron model
     * @author duc.pham
     * @since 2026-04-24
     * @return list of 6 fault field configs (active_faults1-3, limits, status, warnings1)
     */
    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("active_faults1", 1, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelAdvancedSolaron(bitPos, 1)),

            new BitCodeFaultConfig("active_faults2", 2, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelAdvancedSolaron(bitPos, 2)),

            new BitCodeFaultConfig("active_faults3", 3, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelAdvancedSolaron(bitPos, 3)),

            new BitCodeFaultConfig("limits", 4, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetLimitCodeModelAdvancedSolaron(bitPos)),

            new BitCodeFaultConfig("status", 5, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetStatusCodeModelAdvancedSolaron(bitPos)),

            new BitCodeFaultConfig("warnings1", 6, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetWarningsCodeModelAdvancedSolaron(bitPos))
        );
    }
}
