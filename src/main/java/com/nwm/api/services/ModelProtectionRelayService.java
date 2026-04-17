/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BaseAlertEnum;
import com.nwm.api.entities.ModelSMP4DPEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelProtectionRelayEntity;
import com.nwm.api.utils.Lib;

@Service
public class ModelProtectionRelayService extends DB {

    TriggerAlertService service = new TriggerAlertService();

    enum AlertEnum implements BaseAlertEnum {

        EF_OC_50_51N_1(3474, "EF_OC_50_51N_1"),
        EF_OC_50_51N_2(3475, "EF_OC_50_51N_2"),
        EF_OC_50_51N_5(3476, "EF_OC_50_51N_5"),
        Overvoltage_59_1(3477, "Overvoltage_59_1"),
        Overvoltage_59_2(3478, "Overvoltage_59_2"),
        Ph_OC_50_51_1(3479, "Ph_OC_50_51_1"),
        Ph_OC_50_51_2(3480, "Ph_OC_50_51_2"),
        Ph_OC_50_51_3(3481, "Ph_OC_50_51_3"),
        Underrvoltage_27_1(3482, "Underrvoltage_27_1"),
        Underrvoltage_27_2(3483, "Underrvoltage_27_2");

        private final int id;
        private final String column;

        AlertEnum(int id, String column) {
            this.id = id;
            this.column = column;
        }

        public int getId() {
            return id;
        }

        public String getColumn() {
            return column;
        }
    }

	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelProtectionRelayEntity setModelProtectionRelay(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelProtectionRelayEntity dataModel = new ModelProtectionRelayEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setCos(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setEF_OC_50_51N_1(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setEF_OC_50_51N_2(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setEF_OC_50_51N_5(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setFreq(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setIa(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setIb(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setIc(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setOvervoltage_59_1(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setOvervoltage_59_2(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setP(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setPh_OC_50_51_1(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setPh_OC_50_51_2(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setPh_OC_50_51_3(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setQ(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setS(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setUab(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setUan(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setUbc(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setUbn(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setUca(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setUcn(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setUnderrvoltage_27_1(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setUnderrvoltage_27_2(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				
				return dataModel;
				
			} else {
				return new ModelProtectionRelayEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelProtectionRelayEntity();
		}
	}


	/**
	 * @description insert data from datalogger to ModelProtectionRelay
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelProtectionRelay(ModelProtectionRelayEntity obj) {
		try {
			Object insertId = insert("ModelProtectionRelay.insertModelProtectionRelay", obj);
	        if(insertId == null ) {
	        	return false;
	        }
			ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
			ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
			int hours = zdtNow.getHour();
			if (hours >= 9 && hours <= 17 && obj.getEnable_alert() >= 1) {
				service.checkTriggerAlert(obj.getDatatablename(), obj.getTime(), obj.getId_device(), AlertEnum.values());
			}
	        return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
}
