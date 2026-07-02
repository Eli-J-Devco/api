package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModelSmaShp7510Config implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelSmaShp7510.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
                new BitCodeFaultConfig("Manufacturerspecificstatuscode", 1, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetFaultCodeModelSmaShp7510(bitPos), false),

                new BitCodeFaultConfig("ManufacturerspecificeventcodeEvtVnd1", 2, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelSmaShp7510Bit(bitPos, 2)),

                new BitCodeFaultConfig("ManufacturerspecificeventcodeEvtVnd2", 3, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelSmaShp7510Bit(bitPos, 3)),

                new BitCodeFaultConfig("ManufacturerspecificeventcodeEvtVnd3", 4, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelSmaShp7510Bit(bitPos, 4)),

                new BitCodeFaultConfig("ManufacturerspecificeventcodeEvtVnd4", 5, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetErrorCodeModelSmaShp7510Bit(bitPos, 5))
        );
    }
}
