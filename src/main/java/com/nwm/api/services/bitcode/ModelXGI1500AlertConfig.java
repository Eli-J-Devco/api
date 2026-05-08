package com.nwm.api.services.bitcode;

import java.util.Arrays;
import java.util.List;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

/**
 * @description BitCodeAlertConfig for model_xgi1500.
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelXGI1500AlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelXGI1500.getListTriggerFaultCode";

    /**
     * @description get fault code field configurations for XGI 1500 model
     * @author duc.pham
     * @since 2026-04-24
     * @return list of 4 fault field configs (FaultStatus, Fault1-3) using bit decode pattern
     */
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
