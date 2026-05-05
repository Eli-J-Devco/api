package com.nwm.api.entities;

import java.util.function.IntUnaryOperator;

/**
 * @description Configuration for a single fault code field that uses toBinary32Bit pattern.
 * Each field in a device model that stores a numeric fault code (e.g. active_faults1)
 * is described by one instance of this class.
 *
 * @author duc.pham
 * @since 2026-04-24
 */
public class BitCodeFaultConfig {

    /** Column name in the data table (e.g. "active_faults1") */
    private final String fieldName;

    /**
     * faultCodeLevel used to query open alerts for closing.
     * Maps to the faultCodeLevel filter in each model's getListTriggerFaultCode XML query.
     */
    private final int faultCodeLevel;

    /**
     * MyBatis query ID to fetch open alerts for this fault level.
     * Example: "ModelAdvancedEnergySolaron.getListTriggerFaultCode"
     */
    private final String closeAlertQueryId;

    /**
     * Function that maps bit position (0-based from LSB) to an error ID when isBitDecode=true,
     * OR maps the raw fault code value directly to an error ID when isBitDecode=false.
     * Returns 0 or negative if no error is mapped.
     *
     * toBinary32Bit example: bitPos -> LibErrorCode.GetErrorCodeModelAdvancedSolaron(bitPos, 1)
     * Direct lookup example: value -> LibErrorCode.GetAlertModelXantrexGT100250500((int)value)
     */
    private final IntUnaryOperator errorIdResolver;

    /**
     * true  = decode fault code via toBinary32Bit, call errorIdResolver for each bit=1 position
     * false = call errorIdResolver once with the raw fault code value (direct lookup)
     */
    private final boolean isBitDecode;

    /** Constructor for toBinary32Bit pattern (isBitDecode = true) */
    public BitCodeFaultConfig(String fieldName, int faultCodeLevel,
                              String closeAlertQueryId, IntUnaryOperator errorIdResolver) {
        this(fieldName, faultCodeLevel, closeAlertQueryId, errorIdResolver, true);
    }

    /** Full constructor */
    public BitCodeFaultConfig(String fieldName, int faultCodeLevel,
                              String closeAlertQueryId, IntUnaryOperator errorIdResolver,
                              boolean isBitDecode) {
        this.fieldName = fieldName;
        this.faultCodeLevel = faultCodeLevel;
        this.closeAlertQueryId = closeAlertQueryId;
        this.errorIdResolver = errorIdResolver;
        this.isBitDecode = isBitDecode;
    }

    public String getFieldName() { return fieldName; }
    public int getFaultCodeLevel() { return faultCodeLevel; }
    public String getCloseAlertQueryId() { return closeAlertQueryId; }
    public IntUnaryOperator getErrorIdResolver() { return errorIdResolver; }
    public boolean isBitDecode() { return isBitDecode; }
}
