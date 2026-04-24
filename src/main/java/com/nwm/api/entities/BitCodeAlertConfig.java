package com.nwm.api.entities;

import java.util.List;

/**
 * @description Interface that a model must implement to support toBinary32Bit alert processing
 * in CronJobAlertFieldService.
 *
 * Each model provides:
 * 1. The MyBatis query ID to fetch the last N rows for consistency check (checkAlertWriteCode)
 * 2. A list of BitCodeFaultConfig — one per fault code field
 *
 * @author duc.pham
 * @since 2026-04-24
 */
public interface BitCodeAlertConfig {

    /**
     * MyBatis query ID to fetch last ~20 rows for consistency check.
     * Example: "ModelAdvancedEnergySolaron.checkAlertWriteCode"
     */
    String getCheckAlertWriteCodeQueryId();

    /**
     * Minimum number of consistent rows required before triggering an alert.
     * Default is 20 (same as existing logic).
     */
    default int getMinConsistentRows() { return 20; }

    /**
     * List of fault code field configurations for this model.
     * Each entry describes one numeric fault code column.
     */
    List<BitCodeFaultConfig> getFaultConfigs();
}
