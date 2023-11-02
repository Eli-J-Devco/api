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
import com.nwm.api.entities.ModelWattsunTcuEntity;
import com.nwm.api.utils.Lib;

public class ModelWattsunTcuService extends DB {

	/**
	 * @description set data ModelWattsunTcu
	 * @author long.pham
	 * @since 2023-10-30
	 * @param data
	 */
	
	public ModelWattsunTcuEntity setModelWattsunTcu(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelWattsunTcuEntity dataModelTCU = new ModelWattsunTcuEntity();
				
				dataModelTCU.setTime(words.get(0).replace("'", ""));
				dataModelTCU.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelTCU.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelTCU.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelTCU.setTRACKER_ADDRESS(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelTCU.setHOUR(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelTCU.setMINUTE(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelTCU.setDAY(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelTCU.setDIGITAL_INPUTS(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelTCU.setDIGITAL_OUTPUTS(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelTCU.setWATCHDOG_COUNTER(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelTCU.setANGLE_CALC(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelTCU.setLATITUDE(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelTCU.setLONGITUDE(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				return dataModelTCU;
				
			} else {
				return new ModelWattsunTcuEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelWattsunTcuEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_wattsun_tcu
	 * @author long.pham
	 * @since 2023-10-30
	 * @param data from datalogger
	 */

	public boolean insertModelWattsunTcu(ModelWattsunTcuEntity obj) {
		try {
			Object insertId = insert("ModelWattsunTcu.insertModelWattsunTcu", obj);
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
