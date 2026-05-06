package com.nwm.api.services.bitcode;

import java.util.Arrays;
import java.util.List;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

/**
 * @description BitCodeAlertConfig for model_kehua_spi5060k_inverter.
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelKehuaSPI5060KAlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelKehuaSPI5060KInverter.getListTriggerFaultCode";

    /**
     * @description get fault code field configurations for Kehua SPI 50/60K inverter model
     * @author duc.pham
     * @since 2026-04-24
     * @return list of 1 fault field config (FaultWord) using bit decode pattern
     */
    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("FaultWord", 1, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelKehuaSPI5060KInverter(bitPos, 1))
        );
    }
}
