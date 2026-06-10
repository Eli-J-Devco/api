package com.nwm.api.services.bitcode;

import java.util.Arrays;
import java.util.List;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

/**
 * @description BitCodeAlertConfig for model_abb_trio_class6210.
 *              Uses direct lookup (isBitDecode=false) - LibErrorCode maps the raw byte value to errorId.
 *              Fault fields: StatesByte0, StatesByte1, StatesByte2, StatesByte4.
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelAbbTrioClass6210AlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelAbbTrioClass6210.getListTriggerFaultCode";

    /**
     * @description get fault code field configurations for ABB Trio Class 6210 model
     * @author duc.pham
     * @since 2026-04-24
     * @return list of 4 fault field configs using direct lookup pattern
     */
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
