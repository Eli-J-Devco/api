package com.nwm.api.services;

import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelKehuaSPI5060KInverterEntity;
import com.nwm.api.utils.Lib;

public class ModelKehuaSPI5060KInverterService extends DB{
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelKehuaSPI5060KInverterEntity setModelKehuaSPI5060KInverter(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelKehuaSPI5060KInverterEntity dataModelIon = new ModelKehuaSPI5060KInverterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001");
				
				dataModelIon.setTime(words.get(0).replace("'", ""));
				dataModelIon.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelIon.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelIon.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelIon.setDeviceStatus(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelIon.setDailyEnergy(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelIon.setTotalEnergy(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelIon.setGridfrequency(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelIon.setUphaseUVgridvoltage(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelIon.setVphaseVWgridvoltage(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelIon.setWphaseWUgridvoltage(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelIon.setUphasegridcurrent(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				
				dataModelIon.setVphasegridcurrent(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				
				dataModelIon.setWphasegridcurrent(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelIon.setGridconnectedtotalactivepower(power);
				dataModelIon.setGridconnectedtotalreactivepower(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelIon.setHeatsinktemperature(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelIon.setInnertemperature(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelIon.setGridconnectedtotalapparentpower(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelIon.setIGBTtemperature(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelIon.setOutputpowerfactor(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelIon.setPVinputtotalpower(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelIon.setACleakagecurrent(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				
				dataModelIon.setDailypowerconsumption(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelIon.setTotalpowerconsumption(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelIon.setOngridactivepower(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelIon.setOngridapparentpower(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelIon.setOngridreactivepower(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelIon.setOngridPowerfactor(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelIon.setNvmActivePower(power);
				dataModelIon.setNvmActiveEnergy(energy);
				
				return dataModelIon;
				
			} else {
				return new ModelKehuaSPI5060KInverterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelKehuaSPI5060KInverterEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_meter_ion_8600
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelKehuaSPI5060KInverter(ModelKehuaSPI5060KInverterEntity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setTotalEnergy(energy);
			}
			
			ModelKehuaSPI5060KInverterEntity dataObj = (ModelKehuaSPI5060KInverterEntity) queryForObject("ModelKehuaSPI5060KInverter.getLastRow", obj);
			// filter data 
			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() < dataObj.getNvmActiveEnergy() || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setTotalEnergy(dataObj.getNvmActiveEnergy());	
			}
						
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }

			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelKehuaSPI5060KInverter.insertModelKehuaSPI5060KInverter", obj);
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
