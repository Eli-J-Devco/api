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
import com.nwm.api.entities.ModelAesTxInverterEntity;
import com.nwm.api.utils.Lib;

public class ModelAesTxInverterService extends DB {
	/**
	 * @description set data model_aes_tx_inverter
	 * @author long.pham
	 * @since 2023-08-02
	 * @param data
	 */
	
	public ModelAesTxInverterEntity setModelAesTxInverter(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelAesTxInverterEntity dataModelAesTxInverter = new ModelAesTxInverterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001");
				
				
				dataModelAesTxInverter.setTime(words.get(0).replace("'", ""));
				dataModelAesTxInverter.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelAesTxInverter.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelAesTxInverter.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelAesTxInverter.setPt0(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				dataModelAesTxInverter.setPt1(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001");
				dataModelAesTxInverter.setPt2(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001");
				dataModelAesTxInverter.setPt3(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001");
				dataModelAesTxInverter.setPt4(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001");
				dataModelAesTxInverter.setPt5(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001");
				dataModelAesTxInverter.setPt6(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001");
				dataModelAesTxInverter.setPt7(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001");
				
				dataModelAesTxInverter.setPt8(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001");
				dataModelAesTxInverter.setPt9(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001");
				dataModelAesTxInverter.setPt10(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001");
				dataModelAesTxInverter.setPt11(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001");
				dataModelAesTxInverter.setPt12(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001");
				dataModelAesTxInverter.setPt13(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001");
				dataModelAesTxInverter.setPt14(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001");
				dataModelAesTxInverter.setPt15(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001");
				dataModelAesTxInverter.setPt16(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001");
				dataModelAesTxInverter.setPt17(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001");
				
				dataModelAesTxInverter.setPt18(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001");
				dataModelAesTxInverter.setPt19(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001");
				dataModelAesTxInverter.setPt20(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001");
				dataModelAesTxInverter.setPt21(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001");
				dataModelAesTxInverter.setPt22(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001");
				dataModelAesTxInverter.setPt23(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001");
				dataModelAesTxInverter.setPt24(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelAesTxInverter.setPt25(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelAesTxInverter.setPt26(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelAesTxInverter.setPt27(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				
				dataModelAesTxInverter.setPt28(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelAesTxInverter.setPt29(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelAesTxInverter.setPt30(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelAesTxInverter.setPt31(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelAesTxInverter.setPt32(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelAesTxInverter.setPt33(power);
				dataModelAesTxInverter.setPt34(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelAesTxInverter.setPt35(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelAesTxInverter.setPt36(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModelAesTxInverter.setPt37(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				
				dataModelAesTxInverter.setPt38(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelAesTxInverter.setPt39(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModelAesTxInverter.setPt40(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelAesTxInverter.setPt41(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModelAesTxInverter.setPt42(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelAesTxInverter.setPt43(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelAesTxInverter.setPt44(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelAesTxInverter.setPt45(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));

				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelAesTxInverter.setNvmActivePower(power);
				dataModelAesTxInverter.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				
				return dataModelAesTxInverter;
				
			} else {
				return new ModelAesTxInverterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelAesTxInverterEntity();
		}
	}


	/**
	 * @description insert data from datalogger to model_aes_tx_inverter
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelAesTxInverter(ModelAesTxInverterEntity obj) {
		try {
			ModelAesTxInverterEntity dataObj = (ModelAesTxInverterEntity) queryForObject("ModelAesTxInverter.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
		 	Object insertId = insert("ModelAesTxInverter.insertModelAesTxInverter", obj);
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
