package com.nwm.api.entities;

/**
 * @description base interface for alert enum definitions used in AlertEnum-based models.
 *              Each enum constant represents one alert field column with its corresponding error ID.
 * @author duc.pham
 * @since 2026-04-15
 */
public interface BaseAlertEnum {

    /**
     * @description get the error ID mapped to this alert
     * @return error ID from the error table
     */
    int getId();

    /**
     * @description get the column name in the device data table
     * @return column name (e.g. "COMM_FAIL_34kv_feed_1_meter_comm_fail")
     */
    String getColumn();
}
