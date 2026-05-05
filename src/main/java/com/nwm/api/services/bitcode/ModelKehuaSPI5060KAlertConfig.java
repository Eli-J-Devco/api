package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.List;

/**
 * @description BitCodeAlertConfig for model_kehua_spi5060k_inverter.
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelKehuaSPI5060KAlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelKehuaSPI5060KInverter.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("FaultWord", 1, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelKehuaSPI5060KInverter(bitPos, 1))
        );
    }
}
