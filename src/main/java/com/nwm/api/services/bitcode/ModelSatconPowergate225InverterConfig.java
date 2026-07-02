package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModelSatconPowergate225InverterConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelSatconPowergate225Inverter.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
                new BitCodeFaultConfig("Fault1", 1, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelSatconPowergate225Inverter(bitPos, 1)),

                new BitCodeFaultConfig("Fault3", 3, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelSatconPowergate225Inverter(bitPos, 3)),

                new BitCodeFaultConfig("Fault4", 4, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelSatconPowergate225Inverter(bitPos, 4)),

                new BitCodeFaultConfig("GridStatus", 5, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelSatconPowergate225Inverter(bitPos, 5)),

                new BitCodeFaultConfig("Status6", 6, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelSatconPowergate225Inverter(bitPos, 6)),

                new BitCodeFaultConfig("Status7", 7, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelSatconPowergate225Inverter(bitPos, 7))
        );
    }
}
