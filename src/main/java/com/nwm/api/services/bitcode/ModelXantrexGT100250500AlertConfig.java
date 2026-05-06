package com.nwm.api.services.bitcode;

import java.util.Arrays;
import java.util.List;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

/**
 * @description BitCodeAlertConfig for model_xantrex_gt100_250_500.
 * Uses direct lookup (isBitDecode=false).
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelXantrexGT100250500AlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelXantrexGT100250500.getListTriggerFaultCode";

    /**
     * @description get fault code field configurations for Xantrex GT100/250/500 model
     * @author duc.pham
     * @since 2026-04-24
     * @return list of 1 fault field config (FaultCode) using direct lookup pattern
     */
    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("FaultCode", 1, CLOSE_QUERY,
                value -> LibErrorCode.GetAlertModelXantrexGT100250500(value), false)
        );
    }
}
