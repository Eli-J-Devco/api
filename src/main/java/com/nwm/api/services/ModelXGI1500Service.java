/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
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
import com.nwm.api.entities.ModelXGI1500Entity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelXGI1500Service extends DB {

	/**
	 * @description set data ModelXGI1500
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelXGI1500Entity setModelXGI1500(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelXGI1500Entity dataModelXGI1500 = new ModelXGI1500Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001");
				
				dataModelXGI1500.setTime(words.get(0).replace("'", ""));
				dataModelXGI1500.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelXGI1500.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelXGI1500.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelXGI1500.setACCurrentAverage(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelXGI1500.setACCurrentA(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelXGI1500.setACCurrentB(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelXGI1500.setACCurrentC(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelXGI1500.setACVoltageAB(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelXGI1500.setACVoltageBC(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelXGI1500.setACVoltageCA(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelXGI1500.setACVoltageAN(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelXGI1500.setACVoltageBN(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelXGI1500.setACVoltageCN(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelXGI1500.setActivePower(power);
				dataModelXGI1500.setFrequency(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelXGI1500.setApparentPower(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelXGI1500.setReactivePower(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelXGI1500.setPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelXGI1500.setActiveEnergyRawNet(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				dataModelXGI1500.setDCVoltage(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelXGI1500.setOperatingState(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelXGI1500.setOperatingStatus(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelXGI1500.setFaultStatus(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelXGI1500.setFault1(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelXGI1500.setFault2(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelXGI1500.setFault3(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				
				String hex =  !Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"; // "0x3157323334333837"; 
				String dec = "0.001";
		        if(!hex.equals("0.001")) {  dec = Integer.toString(Lib.hexToDec(hex));   }
		        
				dataModelXGI1500.setSerialNumberHex4Reg(Double.parseDouble(dec));
				dataModelXGI1500.setCabinetTemperature(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelXGI1500.setInternalTemperature(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelXGI1500.setIMITemperature(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelXGI1500.setControlDeviceOnOff(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelXGI1500.setActivePowerSetpointPercent(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelXGI1500.setActivePowerControlEnable(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelXGI1500.setPowerFactorSetpoint(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelXGI1500.setPowerFactorControlEnable(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelXGI1500.setReactivePowerSetpointPercent(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelXGI1500.setReactivePowerControlEnable(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelXGI1500.setNvmActivePower(power);
				dataModelXGI1500.setNvmActiveEnergy(energy);
				
				
				return dataModelXGI1500;
				
			} else {
				return new ModelXGI1500Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelXGI1500Entity();
		}
	}
	/**
	 * @description insert data from datalogger to model shark 100
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelXGI1500(ModelXGI1500Entity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setActiveEnergyRawNet(energy);
			}
			
			 ModelXGI1500Entity dataObj = (ModelXGI1500Entity) queryForObject("ModelXGI1500.getLastRow", obj);
			// filter data 
//			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() < dataObj.getNvmActiveEnergy() || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
//				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				obj.setActiveEnergyRawNet(dataObj.getNvmActiveEnergy());
//			}
				
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelXGI1500.insertModelXGI1500", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        ZoneId zoneIdLosAngeles = ZoneId.of("America/Los_Angeles"); // "America/Los_Angeles"
	        ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
	        int hours = zdtNowLosAngeles.getHour();
	        if (hours >= 9 && hours <= 17 && dataObj.getEnable_alert() >= 1) {
	        	checkTriggerAlertModelXGI1500(obj);
	        	
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

	public ModelXGI1500Entity checkAlertWriteCode(ModelXGI1500Entity obj) {
		ModelXGI1500Entity rowItem = new ModelXGI1500Entity();
		try {

			List dataList = queryForList("ModelXGI1500.checkAlertWriteCode", obj);
			if(dataList.size() > 0) {
				int totalFault1 = 0, totalFault2 = 0, totalFault3 = 0, totalFaultStatus = 0;
				for(int i =0; i < dataList.size(); i ++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double fault1 = (double) item.get("Fault1");
					if(Double.compare(obj.getFault1(), fault1) == 0 && obj.getFault1() > 0 && fault1 > 0) { 
						totalFault1++;
					}
					
					double fault2 = (double) item.get("Fault2");
					if(Double.compare(obj.getFault2(), fault2) == 0 && obj.getFault2() > 0 && fault2 > 0) { 
						totalFault2++;
					}
					
					double fault3 = (double) item.get("Fault3");
					if(Double.compare(obj.getFault3(), fault3) == 0 && obj.getFault3() > 0 && fault3 > 0) { 
						totalFault3++;
					}
					
					double faultStatus = (double) item.get("FaultStatus");
					if(Double.compare(obj.getFaultStatus(), faultStatus) == 0 && obj.getFaultStatus() > 0 && faultStatus > 0) { 
						totalFaultStatus++;
					}
					
					
				}
				rowItem.setTotalFault1(totalFault1);
				rowItem.setTotalFault2(totalFault2);
				rowItem.setTotalFault3(totalFault3);
				rowItem.setTotalFaultStatus(totalFaultStatus);
				
			}
			
			if (rowItem == null)
				return new ModelXGI1500Entity();
		} catch (Exception ex) {
			return new ModelXGI1500Entity();
		}
		return rowItem;
	}
	
	
	
	/**
	 * @description check trigger alert fault code
	 * @author duy.phan
	 * @since  2025-05-07
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelXGI1500(ModelXGI1500Entity obj) {
		// Check device alert by fault code
		long fault1 = (obj.getFault1() > 0 && obj.getFault1() != 0.001) ? (long) obj.getFault1() : 0;
		long fault2 = (obj.getFault2() > 0 && obj.getFault2() != 0.001) ? (long) obj.getFault2() : 0;
		int fault3 = (obj.getFault3() > 0 && obj.getFault3() != 0.001) ? (int) obj.getFault3() : 0;
		long faultStatus = (obj.getFaultStatus() > 0 && obj.getFaultStatus() != 0.001) ? (long) obj.getFaultStatus() : 0;
		
		
		ModelXGI1500Entity rowItem = (ModelXGI1500Entity) checkAlertWriteCode(obj);
		
		
		// check faultStatus
		if (faultStatus > 0  && rowItem.getTotalFaultStatus() >= 20) {
			try {
				String toBinary = Long.toBinaryString(faultStatus);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelXGI1500(v, 1);
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
			// Close faultStatus 
			try {
				if(rowItem.getTotalFaultStatus() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 1 is faultStatus
					alertItemClose.setFaultCodeLevel(1);
					List dataListFaultStatus = new ArrayList();
					dataListFaultStatus = queryForList("ModelXGI1500.getListTriggerFaultCode", alertItemClose);
					if(dataListFaultStatus.size() > 0) {
						for(int i = 0; i < dataListFaultStatus.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListFaultStatus.get(i);
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
		
		
		// check fault code 1
		if (fault1 > 0  && rowItem.getTotalFault1() >= 20) {
			try {
				String toBinary = Long.toBinaryString(fault1);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelXGI1500(v, 2);
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
				if(rowItem.getTotalFault1() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 2 is fault1
					alertItemClose.setFaultCodeLevel(2);
					List dataListFault1 = new ArrayList();
					dataListFault1 = queryForList("ModelXGI1500.getListTriggerFaultCode", alertItemClose);
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
		
		
		
		// check fault code 2
		if (fault2 > 0  && rowItem.getTotalFault2() >= 20) {
			try {
				String toBinary = Long.toBinaryString(fault2);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelXGI1500(v, 3);
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
			// Close fault code 2
			try {
				if(rowItem.getTotalFault2() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 3 is fault2
					alertItemClose.setFaultCodeLevel(3);
					List dataListFault2 = new ArrayList();
					dataListFault2 = queryForList("ModelXGI1500.getListTriggerFaultCode", alertItemClose);
					if(dataListFault2.size() > 0) {
						for(int i = 0; i < dataListFault2.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListFault2.get(i);
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

		
		
		// check fault code 3
		if (fault3 > 0  && rowItem.getTotalFault3() >= 20) {
			try {
				String toBinary = Integer.toBinaryString(fault3);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelXGI1500(v, 4);
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
			// Close fault code 3
			try {
				if(rowItem.getTotalFault3() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 4 is fault3
					alertItemClose.setFaultCodeLevel(4);
					List dataListFault3 = new ArrayList();
					dataListFault3 = queryForList("ModelXGI1500.getListTriggerFaultCode", alertItemClose);
					if(dataListFault3.size() > 0) {
						for(int i = 0; i < dataListFault3.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListFault3.get(i);
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
