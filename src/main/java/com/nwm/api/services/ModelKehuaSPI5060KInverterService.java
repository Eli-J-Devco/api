package com.nwm.api.services;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.ModelKehuaSPI5060KInverterEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

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
				
				
				dataModelIon.setTotalInsulationImpedance(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelIon.setMPPT1Voltage(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelIon.setMPPT2Voltage(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelIon.setMPPT3Voltage(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelIon.setMPPT4Voltage(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelIon.setMPPT1Current(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelIon.setMPPT2Current(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelIon.setMPPT3Current(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelIon.setMPPT4Current(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelIon.setBusVoltage(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelIon.setFaultWord(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				
				
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
			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setTotalEnergy(dataObj.getNvmActiveEnergy());	
			}
			 
			Object insertId = insert("ModelKehuaSPI5060KInverter.insertModelKehuaSPI5060KInverter", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        // Update measuredProduction 
 			if (dataObj != null && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy() >= 0 ) {
 				ModelKehuaSPI5060KInverterEntity objUpdateMeasured = new ModelKehuaSPI5060KInverterEntity();
 				objUpdateMeasured.setDatatablename(obj.getDatatablename());
 				objUpdateMeasured.setTime(dataObj.getTime());
 				objUpdateMeasured.setMeasuredProduction(obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy());
 				update("Device.updateMeasuredProduction", objUpdateMeasured);
 			}
	        
	        ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
			ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
			int hours = zdtNow.getHour();
	        
	        if (hours >= 9 && hours <= 17 && dataObj.getEnable_alert() >= 1) {
	        	checkTriggerAlertModelKehuaSPI5060KInverter(obj);
	        }
	        
		        return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
	
	/**
	 * @description get last row "data table name" by device
	 * @author long.pham
	 * @since 2023-04-03
	 * @param datatablename
	 */

	public ModelKehuaSPI5060KInverterEntity checkAlertWriteCode(ModelKehuaSPI5060KInverterEntity obj) {
		ModelKehuaSPI5060KInverterEntity rowItem = new ModelKehuaSPI5060KInverterEntity();
		try {
			List dataList = queryForList("ModelKehuaSPI5060KInverter.checkAlertWriteCode", obj);
			if(dataList.size() > 0) {
				int totalFault = 0;
				for(int i =0; i < dataList.size(); i ++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double fault1 = (double) item.get("FaultWord");
					if(Double.compare(obj.getFaultWord(), fault1) == 0 && obj.getFaultWord() > 0 && fault1 > 0) { 
						totalFault++;
					}
				}
				rowItem.setTotalFaultWord(totalFault);
			}
			
			if (rowItem == null)
				return new ModelKehuaSPI5060KInverterEntity();
		} catch (Exception ex) {
			return new ModelKehuaSPI5060KInverterEntity();
		}
		return rowItem;
	}
	

	/**
	 * @description check trigger alert fault code
	 * @author long.pham
	 * @since  2023-04-03
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelKehuaSPI5060KInverter(ModelKehuaSPI5060KInverterEntity obj) {
		// Check device alert by fault code
		int fault = (obj.getFaultWord() > 0 && obj.getFaultWord() != 0.001) ? (int) obj.getFaultWord() : 0;
		
		
		ModelKehuaSPI5060KInverterEntity rowItem = (ModelKehuaSPI5060KInverterEntity) checkAlertWriteCode(obj);
		
		
		// check fault code 1
		if (fault > 0  && rowItem.getTotalFaultWord() >= 20) {
			try {
				String toBinary = Integer.toBinaryString(fault);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelKehuaSPI5060KInverter(v, 1);
						if (errorId > 0) {
							AlertEntity alertDeviceItem = new AlertEntity();
							alertDeviceItem.setId_device(obj.getId_device());
							alertDeviceItem.setStart_date(obj.getTime());
							alertDeviceItem.setId_error(errorId);
							boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist", alertDeviceItem) > 0;
							boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
							if (!checkAlertDeviceExist && errorExits) {
								insert("BatchJob.insertAlert", alertDeviceItem);
							}
						}
					}
					v += 1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// Close fault code 1
			try {
				if(rowItem.getTotalFaultWord() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					alertItemClose.setFaultCodeLevel(1);
					List dataListFault1 = new ArrayList();
					dataListFault1 = queryForList("ModelKehuaSPI5060KInverter.getListTriggerFaultCode", alertItemClose);
					if(dataListFault1.size() > 0) {
						for(int i = 0; i < dataListFault1.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListFault1.get(i);
							int id =  Integer.parseInt(itemFault.get("id").toString());
							int idError =  Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id );
							alertItemClose.setId_error(idError);
							update("Alert.UpdateErrorRow", alertItemClose);
						}
					}
				}
				
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
}
