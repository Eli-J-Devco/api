package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.List;

/**
 * @description BitCodeAlertConfig for model_xgi1500.
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelXGI1500AlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelXGI1500.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("FaultStatus", 1, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelXGI1500(bitPos, 1)),

            new BitCodeFaultConfig("Fault1", 2, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelXGI1500(bitPos, 2)),

            new BitCodeFaultConfig("Fault2", 3, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelXGI1500(bitPos, 3)),

            new BitCodeFaultConfig("Fault3", 4, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelXGI1500(bitPos, 4))
        );
    }
}
