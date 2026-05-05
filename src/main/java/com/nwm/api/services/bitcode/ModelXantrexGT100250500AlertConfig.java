package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.List;

/**
 * @description BitCodeAlertConfig for model_xantrex_gt100_250_500.
 * Uses direct lookup (isBitDecode=false).
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelXantrexGT100250500AlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelXantrexGT100250500.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("FaultCode", 1, CLOSE_QUERY,
                value -> LibErrorCode.GetAlertModelXantrexGT100250500(value), false)
        );
    }
}
