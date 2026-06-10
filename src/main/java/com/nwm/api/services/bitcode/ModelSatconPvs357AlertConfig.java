package com.nwm.api.services.bitcode;

import java.util.Arrays;
import java.util.List;

import com.nwm.api.entities.BitCodeAlertConfig;
import com.nwm.api.entities.BitCodeFaultConfig;
import com.nwm.api.utils.LibErrorCode;

/**
 * @description BitCodeAlertConfig for model_satcon_pvs357_inverter.
 * @author duc.pham
 * @since 2026-04-24
 */
public class ModelSatconPvs357AlertConfig implements BitCodeAlertConfig {

    private static final String CLOSE_QUERY = "ModelSatconPvs357Inverter.getListTriggerFaultCode";

    /**
     * @description get fault code field configurations for Satcon PVS357 inverter model
     * @author duc.pham
     * @since 2026-04-24
     * @return list of 7 fault field configs (Fault_Word1 through Fault_Word7) using bit decode pattern
     */
    @Override
    public List<BitCodeFaultConfig> getFaultConfigs() {
        return Arrays.asList(
            new BitCodeFaultConfig("Fault_Word1", 1, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelSatconPVS357Inverter(bitPos, 1)),

            new BitCodeFaultConfig("Fault_Word2", 2, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelSatconPVS357Inverter(bitPos, 2)),

            new BitCodeFaultConfig("Fault_Word3", 3, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelSatconPVS357Inverter(bitPos, 3)),

            new BitCodeFaultConfig("Fault_Word4", 4, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelSatconPVS357Inverter(bitPos, 4)),

            new BitCodeFaultConfig("Fault_Word5", 5, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelSatconPVS357Inverter(bitPos, 5)),

            new BitCodeFaultConfig("Fault_Word6", 6, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelSatconPVS357Inverter(bitPos, 6)),

            new BitCodeFaultConfig("Fault_Word7", 7, CLOSE_QUERY,
                bitPos -> LibErrorCode.GetErrorCodeModelSatconPVS357Inverter(bitPos, 7))
        );
    }
}
