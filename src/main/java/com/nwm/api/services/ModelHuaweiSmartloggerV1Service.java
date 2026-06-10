/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelHuaweiSmartloggerV1Entity;
import com.nwm.api.utils.Lib;

@Service
public class ModelHuaweiSmartloggerV1Service extends DB {
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelHuaweiSmartloggerV1Entity setModelHuaweiSmartloggerV1(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelHuaweiSmartloggerV1Entity dataModel = new ModelHuaweiSmartloggerV1Entity();
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setAlarm_1(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setAlarm_2(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setAlarm_3(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setAlarm_4(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setAlarm_5(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setAlarm_6(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setAlm_1_Abn_Active_Sched(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setAlm_1_Abn_Reactive_Sched(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setAlm_2_24V_Power_Fail(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setAlm_2_Abn_Cubicle(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setAlm_2_AC_SPD_Fault(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setAlm_2_Device_ID_Conflict(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setAlm_2_DI_Custom_1(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setAlm_2_DI_Custom_2(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setAlm_2_DI_Custom_3(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setAlm_2_DI_Custom_4(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setAlm_2_DI_Custom_5(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setAlm_2_DI_Custom_6(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setAlm_2_DI_Custom_7(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setAlm_2_DI_Custom_8(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setAlm_2_MCB_Disconnected(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setAlm_3_License_Expired(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setCO2_Reduced(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setDaily_Yield(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setDI_1(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setDI_2(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setDI_3(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel.setDI_4(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setDI_5(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setDI_6(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setDI_7(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModel.setDI_8(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModel.setEfficiency(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setGeneration_Hrs(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModel.setIa(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel.setIb(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setIc(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModel.setLocking_Status(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModel.setPlant_Status_Qinghai(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModel.setPlant_Status_Qinghai_2(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel.setPlant_Status_Shaanxi(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel.setPower_Factor(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModel.setTotal_Active_Power(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModel.setTotal_DC_Input_Current(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModel.setTotal_DC_Input_Power(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModel.setTotal_Rated_Power(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModel.setTotal_Reactive_Power(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				dataModel.setTotal_Yield(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModel.setUab(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModel.setUbc(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModel.setUca(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				
				return dataModel;
				
			} else {
				return new ModelHuaweiSmartloggerV1Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelHuaweiSmartloggerV1Entity();
		}
	}


	/**
	 * @description insert data from datalogger to model_meter_ion_8600
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelHuaweiSmartloggerV1(ModelHuaweiSmartloggerV1Entity obj) {
		try {
			Object insertId = insert("ModelHuaweiSmartloggerV1.insertModelHuaweiSmartloggerV1", obj);
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
