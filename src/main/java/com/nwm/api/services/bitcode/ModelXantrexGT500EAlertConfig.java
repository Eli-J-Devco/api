package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.List;

/**
 * @description BitCodeAlertConfig for model_xantrex_gt500e.
 * Uses direct lookup (isBitDecode=false).
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelXantrexGT500EAlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelXantrexGT500E.getListTriggerFaultCode";

    @Override
    public String getCheckAlertWriteCodeQueryId() {
        return "ModelXantrexGT500E.checkAlertWriteCode";
    }

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("STATUS_FAULT", 1, CLOSE_QUERY,
                value -> LibErrorCode.GetAlertModelXantrexGT500E(value), false)
        );
    }
}
