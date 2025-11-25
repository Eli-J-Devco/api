/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.List;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelHuaweiSun200028ktlEntity;
import com.nwm.api.utils.Lib;

/**
 * @description Service class for ModelHuaweiSun200028ktl - Version 2
 * @author Duc.pham
 * @since 2025-11-24
 */
public class ModelHuaweiSun200028ktlService_v2 extends DB {

    /**
     * @description set data ModelHuaweiSun200028ktl
     * @author Duc.pham
     * @since 2022-12-20
     * @param line - CSV line data
     * @return ModelHuaweiSun200028ktlEntity
     */

	public ModelHuaweiSun200028ktlEntity setModelHuaweiSun200028ktl(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelHuaweiSun200028ktlEntity dataModel = new ModelHuaweiSun200028ktlEntity();

				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");


				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));

				dataModel.setActivePower(power);
				dataModel.setReactivePower(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setTotalDCInputCurrent(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setTotalInputPower(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setInsulationResistance(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setInverterStatus(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setCabinetTemperature(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));

				dataModel.setMajorFaultCode(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setMinorFaultCode(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setWarningCode(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));


				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(Double.parseDouble("0.001"));

				return dataModel;

			} else {
				return new ModelHuaweiSun200028ktlEntity();
			}


		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelHuaweiSun200028ktlEntity();
		}
	}
    /**
     * @description insert data from datalogger to model_huawei_sun_2000_28ktl
     * @author Duc.pham
     * @since 2020-10-07
     * @param dataList list of entities to insert
     * @return true if insert successful, false otherwise
     */
    public boolean insertModelHuaweiSun200028ktl_v2(List<ModelHuaweiSun200028ktlEntity> dataList) {
        try {
            if (dataList == null || dataList.isEmpty()) {
                return false;
            }

            Object insertId = insert("ModelHuaweiSun200028ktl_v2.insertModelHuaweiSun200028ktl_v2", dataList);
            return insertId != null;
        } catch (Exception ex) {
            log.error("insertModelHuaweiSun200028ktl_v2", ex);
            return false;
        }
    }

}