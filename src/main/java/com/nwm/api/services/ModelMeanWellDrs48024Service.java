package com.nwm.api.services;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelMeanWellDrs48024Entity;
import com.nwm.api.utils.Lib;

import java.util.List;

public class ModelMeanWellDrs48024Service extends DB {

    /**
     * @description set data ModelMeanwellDrs48024Entity
     * @author quan.nguyen
     * @since 2026-01-14
     * @param line
     */

    public ModelMeanWellDrs48024Entity setModelMeanWellDrs48024(String line) {
        ModelMeanWellDrs48024Entity dataModelMeanwellDrs48024Entity = new ModelMeanWellDrs48024Entity();
        try {
            List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
            if (!words.isEmpty()) {
                dataModelMeanwellDrs48024Entity.setTime(words.get(0).replace("'", ""));
                dataModelMeanwellDrs48024Entity.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
                dataModelMeanwellDrs48024Entity.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
                dataModelMeanwellDrs48024Entity.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));

                dataModelMeanwellDrs48024Entity.setOperation(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
                dataModelMeanwellDrs48024Entity.setVoutSet(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
                dataModelMeanwellDrs48024Entity.setFaultStatus(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
                dataModelMeanwellDrs48024Entity.setReadVin(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
                dataModelMeanwellDrs48024Entity.setReadVout(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
                dataModelMeanwellDrs48024Entity.setReadIOut(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
                dataModelMeanwellDrs48024Entity.setReadTemperature(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
                dataModelMeanwellDrs48024Entity.setConstantCurrentSettingCharger(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
                dataModelMeanwellDrs48024Entity.setConstantVoltageSettingCharger(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
                dataModelMeanwellDrs48024Entity.setFloatingVoltageSettingCharger(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(12) : "0.001"));
                dataModelMeanwellDrs48024Entity.setTaperCurrentSettingCharger(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
                dataModelMeanwellDrs48024Entity.setConfigurationSettingCharger(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
                dataModelMeanwellDrs48024Entity.setCcChargeTimeoutSettingCharger(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
                dataModelMeanwellDrs48024Entity.setCcChargeTimeoutSetting(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
                dataModelMeanwellDrs48024Entity.setFvChargeTimeoutSettingCharger(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
                dataModelMeanwellDrs48024Entity.setChargingStatusReportingCharger(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
                dataModelMeanwellDrs48024Entity.setScalingRatio(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
                dataModelMeanwellDrs48024Entity.setSystemStatus(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
                dataModelMeanwellDrs48024Entity.setSystemConfiguration(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
                dataModelMeanwellDrs48024Entity.setBatLow(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
                dataModelMeanwellDrs48024Entity.setForceBatLow(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
                dataModelMeanwellDrs48024Entity.setUpsConfigSetting(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
                dataModelMeanwellDrs48024Entity.setReadVbat(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
                dataModelMeanwellDrs48024Entity.setReadIbat(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
                dataModelMeanwellDrs48024Entity.setReadBatTemperature(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
                dataModelMeanwellDrs48024Entity.setAcFailLLSet(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
                dataModelMeanwellDrs48024Entity.setAcFailHLSet(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
                dataModelMeanwellDrs48024Entity.setAcOKLLSet(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
                dataModelMeanwellDrs48024Entity.setAcOKHLSet(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
                dataModelMeanwellDrs48024Entity.setTimeBuffering(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
                dataModelMeanwellDrs48024Entity.setUspDelayTime(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
                dataModelMeanwellDrs48024Entity.setUspShutdownTime(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));

            }
        } catch (Exception ex) {
            log.error("insert", ex);
        }
        return dataModelMeanwellDrs48024Entity;
    }


    /**
     * @description insert data from datalogger to ModelMeanWellDrs48024
     * @author quan.nguyen
     * @since 2026-01-14
     * @param obj from datalogger
     */

    public boolean insertModelMeanWellDrs48024(ModelMeanWellDrs48024Entity obj) {
        try {

            Object insertId = insert("ModelMeanWellDrs48024.insertModelMeanWellDrs48024", obj);
            if(insertId == null ) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            log.error("insert", ex);
            return false;
        }

    }
}
