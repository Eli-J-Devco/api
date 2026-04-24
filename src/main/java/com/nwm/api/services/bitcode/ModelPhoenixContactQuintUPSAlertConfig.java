package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.List;

/**
 * @description BitCodeAlertConfig for model_phoenix_contact_quint_ups.
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelPhoenixContactQuintUPSAlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelPhoenixContactQuintUPS.getListTriggerFaultCode";

    @Override
    public String getCheckAlertWriteCodeQueryId() {
        return "ModelPhoenixContactQuintUPS.checkAlertWriteCode";
    }

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("StatusAlarm", 1, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetAlarmCodeModelPhoenixContactQuintUPS(bitPos)),

            new BitCodeFaultConfig("StatusWarning", 2, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetWarningCodeModelPhoenixContactQuintUPS(bitPos)),

            // Battery1StateofFuse uses direct lookup (not bit decode)
            new BitCodeFaultConfig("Battery1StateofFuse", 3, CLOSE_QUERY,
                value -> LibErrorCode.GetFuse1CodeModelPhoenixContactQuintUPS(value), false)
        );
    }
}
