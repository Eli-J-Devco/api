package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.List;

/**
 * @description BitCodeAlertConfig for model_abb_trio_class6210.
 * Uses direct lookup (isBitDecode=false) — LibErrorCode maps the raw byte value to errorId.
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelAbbTrioClass6210AlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelAbbTrioClass6210.getListTriggerFaultCode";

    @Override
    public String getCheckAlertWriteCodeQueryId() {
        return "ModelAbbTrioClass6210.checkAlertWriteCode";
    }

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("StatesByte0", 1, CLOSE_QUERY,
                value -> LibErrorCode.GetStatesByte0ModelABB(value), false),

            new BitCodeFaultConfig("StatesByte1", 2, CLOSE_QUERY,
                value -> LibErrorCode.GetStatesByte1ModelABB(value), false),

            new BitCodeFaultConfig("StatesByte2", 3, CLOSE_QUERY,
                value -> LibErrorCode.GetStatesByte2ModelABB(value), false),

            new BitCodeFaultConfig("StatesByte4", 5, CLOSE_QUERY,
                value -> LibErrorCode.GetStatesByte4ModelABB(value), false)
        );
    }
}
