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
import com.nwm.api.entities.ModelSatconPowergate225InverterEntity;
import com.nwm.api.utils.Lib;

public class ModelSatconPowergate225InverterService extends DB {

	/**
	 * @description set data ModelSatconPowergate225Inverter
	 * @author Hung.Bui
	 * @since 2023-05-12
	 * @param data
	 */
	
	public ModelSatconPowergate225InverterEntity setModelSatconPowergate225Inverter(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSatconPowergate225InverterEntity dataModelSatcon225 = new ModelSatconPowergate225InverterEntity();
				Double power = Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001");
				if(power < 0) { power = 0.0; };
				dataModelSatcon225.setTime(words.get(0).replace("'", ""));
				dataModelSatcon225.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelSatcon225.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelSatcon225.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelSatcon225.setFault1(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelSatcon225.setFault2(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelSatcon225.setFault3(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelSatcon225.setFault4(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelSatcon225.setGridStatus(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelSatcon225.setStatus6(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelSatcon225.setStatus7(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelSatcon225.setPCSState(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelSatcon225.setDCInputPower(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelSatcon225.setDC_Link_Volts(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelSatcon225.setDCInputVoltage(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelSatcon225.setDCInputCurrent(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelSatcon225.setOutputKVAR(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelSatcon225.setOutputKW(power);
				dataModelSatcon225.setOutputKVA(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelSatcon225.setLine_Volts_A_TEST(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelSatcon225.setLine_Volts_B_TEST(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelSatcon225.setLine_Volts_C_TEST(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelSatcon225.setLine_Amps_A_TEST(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelSatcon225.setLine_Amps_B_TEST(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelSatcon225.setLine_Amps_C_TEST(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelSatcon225.setNeutralCurrent(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelSatcon225.setStopCode(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelSatcon225.setKWHlow(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelSatcon225.setKWH(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelSatcon225.setPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelSatcon225.setLineFreq(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelSatcon225.setOutputPowerLimit(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelSatcon225.setNvmActivePower(power);
				dataModelSatcon225.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				return dataModelSatcon225;
				
			} else {
				return new ModelSatconPowergate225InverterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSatconPowergate225InverterEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_satcon_powergate_225_inverter
	 * @author Hung.Bui
	 * @since 2023-05-12
	 * @param data from datalogger
	 */

	public boolean insertModelSatconPowergate225Inverter(ModelSatconPowergate225InverterEntity obj) {
		try {
			Object insertId = insert("ModelSatconPowergate225Inverter.insertModelSatconPowergate225Inverter", obj);
			if (insertId == null) {
				return false;
			}
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}

}
