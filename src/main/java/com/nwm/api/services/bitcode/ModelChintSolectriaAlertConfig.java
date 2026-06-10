package com.nwm.api.services.bitcode;

import java.util.Arrays;
import java.util.List;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

/**
 * @description BitCodeAlertConfig for model_chint_solectria_inverter_class9725.
 *              Uses toBinary32Bit decode pattern.
 *              Fault fields: PermanentFaultCode, WarnCode, FaultCode0, FaultCode1, FaultCode2.
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelChintSolectriaAlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelChintSolectriaInverterClass9725.getListTriggerFaultCode";

    /**
     * @description get fault code field configurations for Chint Solectria model
     * @author duc.pham
     * @since 2026-04-24
     * @return list of 5 fault field configs using bit decode pattern
     */
    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("PermanentFaultCode", 1, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetPermanentFaultCodeModelSolectria(bitPos)),

            new BitCodeFaultConfig("WarnCode", 2, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetWarningCodeModelSolectria(bitPos)),

            new BitCodeFaultConfig("FaultCode0", 3, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetFaultCode0ModelSolectria(bitPos)),

            new BitCodeFaultConfig("FaultCode1", 4, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetFaultCode1ModelSolectria(bitPos)),

            new BitCodeFaultConfig("FaultCode2", 5, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetFaultCode2ModelSolectria(bitPos))
        );
    }
}
