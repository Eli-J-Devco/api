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
import com.nwm.api.entities.ModelPVPowered3550260500kwInverterEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelPVPowered3550260500kwInverterService extends DB {

	/**
	 * @description set data ModelPVPowered3550260KWInverter
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelPVPowered3550260500kwInverterEntity setModelPVPowered3550260KWInverter(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelPVPowered3550260500kwInverterEntity dataModelPVPowered = new ModelPVPowered3550260500kwInverterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001");
				if(power < 0) { power = 0.0; };
				
				dataModelPVPowered.setTime(words.get(0).replace("'", ""));
				dataModelPVPowered.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelPVPowered.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelPVPowered.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelPVPowered.setInverterIDASCIICHAR0001((!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelPVPowered.setInverterIDASCIICHAR0203((!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelPVPowered.setInverterIDASCIICHAR0405((!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelPVPowered.setInverterIDASCIICHAR0607((!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelPVPowered.setInverterIDASCIICHAR0809((!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelPVPowered.setInverterIDASCIICHAR1011((!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelPVPowered.setInverterIDASCIICHAR1213((!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelPVPowered.setInverterIDASCIICHAR1415((!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelPVPowered.setFirmwareVersionASCIICHAR0001((!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelPVPowered.setFirmwareVersionASCIICHAR0203((!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelPVPowered.setFirmwareVersionASCIICHAR0405((!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelPVPowered.setFirmwareVersionASCIICHAR0607((!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelPVPowered.setMapVersion((!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelPVPowered.setInverterConfiguration((!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelPVPowered.setInverterSerialASCIICHAR0001((!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelPVPowered.setInverterSerialASCIICHAR0203((!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelPVPowered.setInverterSerialASCIICHAR0405((!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelPVPowered.setInverterSerialASCIICHAR0607((!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelPVPowered.setInverterSerialASCIICHAR0809((!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelPVPowered.setInverterSerialASCIICHAR1011((!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelPVPowered.setInverterSerialASCIICHAR1213((!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelPVPowered.setInverterSerialASCIICHAR1415((!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelPVPowered.setInverterSerialASCIICHAR1617((!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelPVPowered.setInverterSerialASCIICHAR1819((!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelPVPowered.setVoltageAN(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelPVPowered.setVoltageBN(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelPVPowered.setVoltageCN(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelPVPowered.setCurrentA(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelPVPowered.setCurrentB(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelPVPowered.setCurrentC(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelPVPowered.setDCInputVoltage(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelPVPowered.setDCInputCurrent(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelPVPowered.setLineFrequency(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelPVPowered.setOutputGeneration(power);
				dataModelPVPowered.setTotalEnergyGeneration(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelPVPowered.setPVInputVoltage(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelPVPowered.setInputGenerationCalculated(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModelPVPowered.setInverterOperatingStatus(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModelPVPowered.setMainFault(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelPVPowered.setDriveFault(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModelPVPowered.setVoltageFault(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelPVPowered.setGridFault(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModelPVPowered.setTemperatureFault(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelPVPowered.setSystemFault(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelPVPowered.setSystemWarnings(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelPVPowered.setPVMStatusCodes(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelPVPowered.setNvmActivePower(power);
				dataModelPVPowered.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				
				
				return dataModelPVPowered;
				
			} else {
				return new ModelPVPowered3550260500kwInverterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelPVPowered3550260500kwInverterEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_pv_powered_35_50_260_500kw_inverter
	 * @author long.pham
	 * @since 2022-04-28
	 * @param data from datalogger
	 */
	
	public boolean insertModelPVPowered3550260KWInverter(ModelPVPowered3550260500kwInverterEntity obj) {
		try {
			ModelPVPowered3550260500kwInverterEntity dataObj = (ModelPVPowered3550260500kwInverterEntity) queryForObject("ModelPVPowered3550260500kwInverter.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 }
			 }
			 obj.setMeasuredProduction(measuredProduction);
			 
			 	Object insertId = insert("ModelPVPowered3550260500kwInverter.insertModelPVPowered3550260KWInverter", obj);
		        if(insertId == null ) {
		        	return false;
		        }
		        
		        ZoneId zoneIdLosAngeles = ZoneId.of("America/Los_Angeles"); // "America/Los_Angeles"
				ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
				int hours = zdtNowLosAngeles.getHour();

				if (hours >= 8 && hours <= 18) {
					checkTriggerAlertModelPVPowered3550260500kwInverter(obj);
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
	 * @since 2021-05-18
	 * @param datatablename
	 */

	public ModelPVPowered3550260500kwInverterEntity checkAlertWriteCode(ModelPVPowered3550260500kwInverterEntity obj) {
		ModelPVPowered3550260500kwInverterEntity rowItem = new ModelPVPowered3550260500kwInverterEntity();
		try {
			rowItem = (ModelPVPowered3550260500kwInverterEntity) queryForObject("ModelPVPowered3550260500kwInverter.checkAlertWriteCode", obj);
			if (rowItem == null)
				return new ModelPVPowered3550260500kwInverterEntity();
		} catch (Exception ex) {
			return new ModelPVPowered3550260500kwInverterEntity();
		}
		return rowItem;
	}
	
	/**
	 * @description check trigger alert fault code
	 * @author long.pham
	 * @since 2022-09-26
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelPVPowered3550260500kwInverter(ModelPVPowered3550260500kwInverterEntity obj) {
		// Check device alert by fault code
		
		int InverterOperatingStatus = (obj.getInverterOperatingStatus() > 0 && obj.getInverterOperatingStatus() != 0.001) ? (int) obj.getInverterOperatingStatus() : 0;
		int MainFault = (obj.getMainFault() > 0 && obj.getMainFault() != 0.001) ? (int) obj.getMainFault() : 0;
		int DriveFault = (obj.getDriveFault() > 0 && obj.getDriveFault() != 0.001) ? (int) obj.getDriveFault() : 0;
		int VoltageFault = (obj.getVoltageFault() > 0 && obj.getVoltageFault() != 0.001) ? (int) obj.getVoltageFault() : 0;
		int GridFault = (obj.getGridFault() > 0 && obj.getGridFault() != 0.001) ? (int) obj.getGridFault() : 0;
		int TemperatureFault = (obj.getTemperatureFault() > 0 && obj.getTemperatureFault() != 0.001) ? (int) obj.getTemperatureFault() : 0;
		int SystemFault = (obj.getSystemFault() > 0 && obj.getSystemFault() != 0.001) ? (int) obj.getSystemFault() : 0;
		int SystemWarnings = (obj.getSystemWarnings() > 0 && obj.getSystemWarnings() != 0.001) ? (int) obj.getSystemWarnings() : 0;
		int PVMStatusCodes = (obj.getPVMStatusCodes() > 0 && obj.getPVMStatusCodes() != 0.001) ? (int) obj.getPVMStatusCodes() : 0;
		

		ModelPVPowered3550260500kwInverterEntity rowItem = (ModelPVPowered3550260500kwInverterEntity) checkAlertWriteCode(
				obj);
		
		if (PVMStatusCodes > 0 && rowItem.getTotalPVMStatusCodes() >= 4) {
			try {
				int errorId = LibErrorCode.GetPVMStatusCodesModelPVP260(PVMStatusCodes);
				System.out.println("status errorId: " + errorId);
				if (errorId > 0) {
					AlertEntity alertDeviceItem = new AlertEntity();
					alertDeviceItem.setId_device(obj.getId_device());
					alertDeviceItem.setStart_date(obj.getTime());
					alertDeviceItem.setId_error(errorId);
					boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist",
							alertDeviceItem) > 0;
					boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
					if (!checkAlertDeviceExist && errorExits) {
						insert("BatchJob.insertAlert", alertDeviceItem);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close PVMStatusCodes
			try {
				if(rowItem.getTotalPVMStatusCodes() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 9 is PVMStatusCodes
					alertItemClose.setFaultCodeLevel(9);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelPVPowered3550260500kwInverter.getListTriggerFaultCode",
							alertItemClose);
					if (dataListStatusCode.size() > 0) {
						for (int i = 0; i < dataListStatusCode.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListStatusCode.get(i);
							int id = Integer.parseInt(itemFault.get("id").toString());
							int idError = Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id);
							alertItemClose.setId_error(idError);
							update("Alert.UpdateErrorRow", alertItemClose);
						}
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if (SystemWarnings > 0 && rowItem.getTotalSystemWarnings() >= 4) {
			try {
				int errorId = LibErrorCode.GetSystemWarningsModelPVP260(SystemWarnings);
				System.out.println("status errorId: " + errorId);
				if (errorId > 0) {
					AlertEntity alertDeviceItem = new AlertEntity();
					alertDeviceItem.setId_device(obj.getId_device());
					alertDeviceItem.setStart_date(obj.getTime());
					alertDeviceItem.setId_error(errorId);
					boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist",
							alertDeviceItem) > 0;
					boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
					if (!checkAlertDeviceExist && errorExits) {
						insert("BatchJob.insertAlert", alertDeviceItem);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close SystemWarnings
			try {
				if(rowItem.getTotalSystemWarnings() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 8 is SystemWarnings
					alertItemClose.setFaultCodeLevel(8);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelPVPowered3550260500kwInverter.getListTriggerFaultCode",
							alertItemClose);
					if (dataListStatusCode.size() > 0) {
						for (int i = 0; i < dataListStatusCode.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListStatusCode.get(i);
							int id = Integer.parseInt(itemFault.get("id").toString());
							int idError = Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id);
							alertItemClose.setId_error(idError);
							update("Alert.UpdateErrorRow", alertItemClose);
						}
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if (SystemFault > 0 && rowItem.getTotalSystemFault() >= 4) {
			try {
				int errorId = LibErrorCode.GetSystemFaultModelPVP260(SystemFault);
				System.out.println("status errorId: " + errorId);
				if (errorId > 0) {
					AlertEntity alertDeviceItem = new AlertEntity();
					alertDeviceItem.setId_device(obj.getId_device());
					alertDeviceItem.setStart_date(obj.getTime());
					alertDeviceItem.setId_error(errorId);
					boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist",
							alertDeviceItem) > 0;
					boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
					if (!checkAlertDeviceExist && errorExits) {
						insert("BatchJob.insertAlert", alertDeviceItem);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close SystemFault
			try {
				if(rowItem.getTotalSystemFault() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 7 is SystemFault
					alertItemClose.setFaultCodeLevel(7);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelPVPowered3550260500kwInverter.getListTriggerFaultCode",
							alertItemClose);
					if (dataListStatusCode.size() > 0) {
						for (int i = 0; i < dataListStatusCode.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListStatusCode.get(i);
							int id = Integer.parseInt(itemFault.get("id").toString());
							int idError = Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id);
							alertItemClose.setId_error(idError);
							update("Alert.UpdateErrorRow", alertItemClose);
						}
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if (TemperatureFault > 0 && rowItem.getTotalTemperatureFault() >= 4) {
			try {
				int errorId = LibErrorCode.GetTemperatureFaultModelPVP260(TemperatureFault);
				System.out.println("status errorId: " + errorId);
				if (errorId > 0) {
					AlertEntity alertDeviceItem = new AlertEntity();
					alertDeviceItem.setId_device(obj.getId_device());
					alertDeviceItem.setStart_date(obj.getTime());
					alertDeviceItem.setId_error(errorId);
					boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist",
							alertDeviceItem) > 0;
					boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
					if (!checkAlertDeviceExist && errorExits) {
						insert("BatchJob.insertAlert", alertDeviceItem);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close TemperatureFault
			try {
				if(rowItem.getTotalTemperatureFault()  == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 6 is TemperatureFault
					alertItemClose.setFaultCodeLevel(6);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelPVPowered3550260500kwInverter.getListTriggerFaultCode",
							alertItemClose);
					if (dataListStatusCode.size() > 0) {
						for (int i = 0; i < dataListStatusCode.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListStatusCode.get(i);
							int id = Integer.parseInt(itemFault.get("id").toString());
							int idError = Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id);
							alertItemClose.setId_error(idError);
							update("Alert.UpdateErrorRow", alertItemClose);
						}
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if (GridFault > 0 && rowItem.getTotalGridFault() >= 4) {
			try {
				int errorId = LibErrorCode.GetGridFaultModelPVP260(GridFault);
				System.out.println("status errorId: " + errorId);
				if (errorId > 0) {
					AlertEntity alertDeviceItem = new AlertEntity();
					alertDeviceItem.setId_device(obj.getId_device());
					alertDeviceItem.setStart_date(obj.getTime());
					alertDeviceItem.setId_error(errorId);
					boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist",
							alertDeviceItem) > 0;
					boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
					if (!checkAlertDeviceExist && errorExits) {
						insert("BatchJob.insertAlert", alertDeviceItem);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close GridFault
			try {
				if(rowItem.getTotalGridFault() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 5 is GridFault
					alertItemClose.setFaultCodeLevel(5);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelPVPowered3550260500kwInverter.getListTriggerFaultCode",
							alertItemClose);
					if (dataListStatusCode.size() > 0) {
						for (int i = 0; i < dataListStatusCode.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListStatusCode.get(i);
							int id = Integer.parseInt(itemFault.get("id").toString());
							int idError = Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id);
							alertItemClose.setId_error(idError);
							update("Alert.UpdateErrorRow", alertItemClose);
						}
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if (VoltageFault > 0 && rowItem.getTotalVoltageFault() >= 4) {
			try {
				int errorId = LibErrorCode.GetVoltageFaultModelPVP260(VoltageFault);
				System.out.println("status errorId: " + errorId);
				if (errorId > 0) {
					AlertEntity alertDeviceItem = new AlertEntity();
					alertDeviceItem.setId_device(obj.getId_device());
					alertDeviceItem.setStart_date(obj.getTime());
					alertDeviceItem.setId_error(errorId);
					boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist",
							alertDeviceItem) > 0;
					boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
					if (!checkAlertDeviceExist && errorExits) {
						insert("BatchJob.insertAlert", alertDeviceItem);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close VoltageFault
			try {
				if(rowItem.getTotalVoltageFault() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 4 is VoltageFault
					alertItemClose.setFaultCodeLevel(4);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelPVPowered3550260500kwInverter.getListTriggerFaultCode",
							alertItemClose);
					if (dataListStatusCode.size() > 0) {
						for (int i = 0; i < dataListStatusCode.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListStatusCode.get(i);
							int id = Integer.parseInt(itemFault.get("id").toString());
							int idError = Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id);
							alertItemClose.setId_error(idError);
							update("Alert.UpdateErrorRow", alertItemClose);
						}
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		if (DriveFault > 0 && rowItem.getTotalDriveFault() >= 4) {
			try {
				int errorId = LibErrorCode.GetDriveFaultModelPVP260(DriveFault);
				System.out.println("status errorId: " + errorId);
				if (errorId > 0) {
					AlertEntity alertDeviceItem = new AlertEntity();
					alertDeviceItem.setId_device(obj.getId_device());
					alertDeviceItem.setStart_date(obj.getTime());
					alertDeviceItem.setId_error(errorId);
					boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist",
							alertDeviceItem) > 0;
					boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
					if (!checkAlertDeviceExist && errorExits) {
						insert("BatchJob.insertAlert", alertDeviceItem);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close DriveFault
			try {
				if(rowItem.getTotalDriveFault() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 3 is DriveFault
					alertItemClose.setFaultCodeLevel(3);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelPVPowered3550260500kwInverter.getListTriggerFaultCode",
							alertItemClose);
					if (dataListStatusCode.size() > 0) {
						for (int i = 0; i < dataListStatusCode.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListStatusCode.get(i);
							int id = Integer.parseInt(itemFault.get("id").toString());
							int idError = Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id);
							alertItemClose.setId_error(idError);
							update("Alert.UpdateErrorRow", alertItemClose);
						}
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if (MainFault > 0 && rowItem.getTotalMainFault() >= 4) {
			try {
				int errorId = LibErrorCode.GetMainFaultModelPVP260(MainFault);
				System.out.println("status errorId: " + errorId);
				if (errorId > 0) {
					AlertEntity alertDeviceItem = new AlertEntity();
					alertDeviceItem.setId_device(obj.getId_device());
					alertDeviceItem.setStart_date(obj.getTime());
					alertDeviceItem.setId_error(errorId);
					boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist",
							alertDeviceItem) > 0;
					boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
					if (!checkAlertDeviceExist && errorExits) {
						insert("BatchJob.insertAlert", alertDeviceItem);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close MainFault
			try {
				if(rowItem.getTotalMainFault() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 2 is MainFault
					alertItemClose.setFaultCodeLevel(2);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelPVPowered3550260500kwInverter.getListTriggerFaultCode",
							alertItemClose);
					if (dataListStatusCode.size() > 0) {
						for (int i = 0; i < dataListStatusCode.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListStatusCode.get(i);
							int id = Integer.parseInt(itemFault.get("id").toString());
							int idError = Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id);
							alertItemClose.setId_error(idError);
							update("Alert.UpdateErrorRow", alertItemClose);
						}
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		if (InverterOperatingStatus > 0 && rowItem.getTotalInverterOperatingStatus() >= 4) {
			try {
				int errorId = LibErrorCode.GetInverterOperatingStatusModelPVP260(InverterOperatingStatus);
				System.out.println("status errorId: " + errorId);
				if (errorId > 0) {
					AlertEntity alertDeviceItem = new AlertEntity();
					alertDeviceItem.setId_device(obj.getId_device());
					alertDeviceItem.setStart_date(obj.getTime());
					alertDeviceItem.setId_error(errorId);
					boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist",
							alertDeviceItem) > 0;
					boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
					if (!checkAlertDeviceExist && errorExits) {
						insert("BatchJob.insertAlert", alertDeviceItem);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close InverterOperatingStatus
			try {
				if(rowItem.getTotalInverterOperatingStatus() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 1 is InverterOperatingStatus
					alertItemClose.setFaultCodeLevel(1);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelPVPowered3550260500kwInverter.getListTriggerFaultCode",
							alertItemClose);
					if (dataListStatusCode.size() > 0) {
						for (int i = 0; i < dataListStatusCode.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListStatusCode.get(i);
							int id = Integer.parseInt(itemFault.get("id").toString());
							int idError = Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id);
							alertItemClose.setId_error(idError);
							update("Alert.UpdateErrorRow", alertItemClose);
						}
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		

	}

}
