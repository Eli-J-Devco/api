package com.nwm.api.entities;

import java.util.List;

/**
 * @description interface that a model must implement to support toBinary32Bit alert processing
 *              in CronJobAlertFieldService. Each model provides a list of BitCodeFaultConfig,
 *              one per fault code field. Data fetching (2-hour window) is handled centrally
 *              by TriggerAlertBitCodeService via CronJobAlertField.getBitCodeDataIn2Hours.
 * @author duc.pham
 * @since 2026-04-24
 */
public interface BitCodeAlertConfig {

    /**
     * @description get list of fault code field configurations for this model.
     *              Each entry describes one numeric fault code column.
     * @return list of BitCodeFaultConfig
     */
    List<BitCodeFaultConfig> getFaultConfigs();
}
