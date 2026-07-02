package com.nwm.api.services.bitcode;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModelPVPowered3550260500kwInverterConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelPVPowered3550260500kwInverter.getListTriggerFaultCode";

    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
                new BitCodeFaultConfig("InverterOperatingStatus", 1, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetInverterOperatingStatusModelPVP260(bitPos), false),

                new BitCodeFaultConfig("MainFault", 2, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetMainFaultModelPVP260(bitPos), false),

                new BitCodeFaultConfig("DriveFault", 3, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetDriveFaultModelPVP260(bitPos), false),

                new BitCodeFaultConfig("VoltageFault", 4, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetVoltageFaultModelPVP260(bitPos), false),

                new BitCodeFaultConfig("GridFault", 5, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetGridFaultModelPVP260(bitPos), false),

                new BitCodeFaultConfig("TemperatureFault", 6, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetTemperatureFaultModelPVP260(bitPos), false),

                new BitCodeFaultConfig("SystemFault", 7, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetSystemFaultModelPVP260(bitPos), false),

                new BitCodeFaultConfig("SystemWarnings", 8, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetSystemWarningsModelPVP260(bitPos), false),

                new BitCodeFaultConfig("PVMStatusCodes", 9, CLOSE_QUERY,
                        bitPos -> LibErrorCode.GetPVMStatusCodesModelPVP260(bitPos), false)
        );
    }
}
