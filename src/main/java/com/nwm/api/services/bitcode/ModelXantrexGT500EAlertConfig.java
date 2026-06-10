package com.nwm.api.services.bitcode;

import java.util.Arrays;
import java.util.List;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

/**
 * @description BitCodeAlertConfig for model_xantrex_gt500e.
 * Uses direct lookup (isBitDecode=false).
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelXantrexGT500EAlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelXantrexGT500E.getListTriggerFaultCode";

    /**
     * @description get fault code field configurations for Xantrex GT500E model
     * @author duc.pham
     * @since 2026-04-24
     * @return list of 1 fault field config (STATUS_FAULT) using direct lookup pattern
     */
    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("STATUS_FAULT", 1, CLOSE_QUERY,
                value -> LibErrorCode.GetAlertModelXantrexGT500E(value), false)
        );
    }
}
