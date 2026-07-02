package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModelIVTSolaronEXTConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelIVTSolaronEXT.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
                new BitCodeFaultConfig("active_faults1", 1, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelIVTSolaronEXT(bitPos, 1)),

                new BitCodeFaultConfig("active_faults2", 2, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelIVTSolaronEXT(bitPos, 2)),

                new BitCodeFaultConfig("active_faults3", 3, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelIVTSolaronEXT(bitPos, 3)),

                new BitCodeFaultConfig("limits", 4, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetLimitCodeModelIVTSolaronEXT(bitPos)),

                new BitCodeFaultConfig("status", 5, CLOSE_QUERY,
                    bitPos -> LibErrorCode.GetStatusCodeModelIVTSolaronEXT(bitPos)),

                new BitCodeFaultConfig("warnings1", 6, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetWarningsCodeModelIVTSolaronEXT(bitPos))
        );
    }
}
