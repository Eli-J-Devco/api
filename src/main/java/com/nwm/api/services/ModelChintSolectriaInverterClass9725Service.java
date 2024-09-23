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
import com.nwm.api.entities.ModelChintSolectriaInverterClass9725Entity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelChintSolectriaInverterClass9725Service extends DB {

	
	/**
	 * @description set data ModelAdvancedEnergySolaron
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelChintSolectriaInverterClass9725Entity setModelChintSolectriaInverterClass9725(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelChintSolectriaInverterClass9725Entity dataModelChint = new ModelChintSolectriaInverterClass9725Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001");
				
				
				dataModelChint.setTime(words.get(0).replace("'", ""));
				dataModelChint.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelChint.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelChint.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelChint.setPowerOnOff(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelChint.setPActiveSet(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelChint.setPFactorSet(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelChint.setPReactiveSet(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelChint.setGridVMax(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelChint.setGridVmaxTripT(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelChint.setGridVMin(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelChint.setGridVminTripT(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelChint.setGridFMax(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelChint.setGridFMin(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelChint.setGridFTripT(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelChint.setActivePower(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelChint.setPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelChint.setStartDelayTime(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelChint.setRisomin(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelChint.setPVStartVol(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelChint.setDCIMax(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelChint.setTambientMax(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelChint.setTmoduleMax(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelChint.setOffsetDiffMax(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelChint.setGridVolUnbalance(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelChint.setSoftPowerStep(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelChint.setTotalEnergyToEnergy(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelChint.setTotalEnergyToday(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelChint.setInverterEfficiency(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelChint.setPowerFactor1(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelChint.setMaxActivePowerToday(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelChint.setRunTimeToGrid(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelChint.setAC_ActivePower(power);
				dataModelChint.setAC_ApparentPower(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelChint.setGridVoltageUab(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelChint.setGridVoltageUbc(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelChint.setGridVoltageUca(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelChint.setGridA_PhaseCurrent(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelChint.setGridB_PhaseCurrent(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelChint.setGridC_PhaseCurrent(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelChint.setPV1_Voltage(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModelChint.setPV1_Current(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModelChint.setPV2_Voltage(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelChint.setPV2_Current(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModelChint.setPV3_Voltage(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelChint.setPV3_Current(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModelChint.setGrid_Frequency(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelChint.setModuleTemp(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelChint.setInternalTemp(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelChint.setTransformerTemp(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModelChint.setInverterModeCode(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				dataModelChint.setPermanentFaultCode(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModelChint.setWarnCode(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModelChint.setFaultCode0(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModelChint.setFaultCode1(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModelChint.setFaultCode2(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				
				// this field can or can't be included in uploaded file
				try {
					dataModelChint.setSerialNumber(!Lib.isBlank(words.get(56)) ? words.get(56) : null);
				} catch (IndexOutOfBoundsException e) {
					dataModelChint.setSerialNumber(null);
				}
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelChint.setNvmActivePower(power);
				dataModelChint.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				return dataModelChint;
				
			} else {
				return new ModelChintSolectriaInverterClass9725Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelChintSolectriaInverterClass9725Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model chint solectria inverter
	 *              class 9725
	 * @author long.pham
	 * @since 2021-03-19
	 * @param data from datalogger
	 */

	public boolean insertModelChintSolectriaInverterClass9725(ModelChintSolectriaInverterClass9725Entity obj) {
		try {
			ModelChintSolectriaInverterClass9725Entity dataObj = (ModelChintSolectriaInverterClass9725Entity) queryForObject("ModelChintSolectriaInverterClass9725.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }
			 
			 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
				 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 obj.setTotalEnergyToEnergy(dataObj.getNvmActiveEnergy());
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
			Object insertId = insert("ModelChintSolectriaInverterClass9725.insertModelChintSolectriaInverterClass9725",
					obj);
			if (insertId == null) {
				return false;
			}
			ZoneId zoneIdLosAngeles = ZoneId.of("America/Los_Angeles"); // "America/Los_Angeles"
			ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
			int hours = zdtNowLosAngeles.getHour();

			if (hours >= 9 && hours <= 17 && dataObj.getEnable_alert() >= 1) {
				checkTriggerAlertModelChintSolectriaInverterClass9725(obj);
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

	public ModelChintSolectriaInverterClass9725Entity checkAlertWriteCode(ModelChintSolectriaInverterClass9725Entity obj) {
		ModelChintSolectriaInverterClass9725Entity rowItem = new ModelChintSolectriaInverterClass9725Entity();
		try {
//			rowItem = (ModelChintSolectriaInverterClass9725Entity) queryForObject("ModelChintSolectriaInverterClass9725.checkAlertWriteCode", obj);
			List dataList = queryForList("ModelChintSolectriaInverterClass9725.checkAlertWriteCode", obj);
			if(dataList.size() > 0) {
				int totalWarnCode = 0, totalFaultCode0 = 0, totalFaultCode1 = 0, totalFaultCode2 = 0;
				for(int i =0; i < dataList.size(); i ++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double WarnCode = (double) item.get("active_faults1");
					if(Double.compare(obj.getWarnCode(), WarnCode) == 0 && obj.getWarnCode() > 0 && WarnCode > 0) { 
						totalWarnCode++;
					}
					
					double faultCode0 = (double) item.get("faultCode0");
					if(Double.compare(obj.getFaultCode0(), faultCode0) == 0 && obj.getFaultCode0() > 0 && faultCode0 > 0) { 
						totalFaultCode0++;
					}
					
					double faultCode1 = (double) item.get("faultCode1");
					if(Double.compare(obj.getFaultCode1(), faultCode1) == 0 && obj.getFaultCode1() > 0 && faultCode1 > 0) { 
						totalFaultCode1++;
					}
					
					double faultCode2 = (double) item.get("faultCode2");
					if(Double.compare(obj.getFaultCode2(), faultCode2) == 0 && obj.getFaultCode2() > 0 && faultCode2 > 0) { 
						totalFaultCode2++;
					}
					
				}
				rowItem.setTotalWarnCode(totalWarnCode);
				rowItem.setTotalFaultCode0(totalFaultCode0);
				rowItem.setTotalFaultCode1(totalFaultCode1);
				rowItem.setTotalFaultCode2(totalFaultCode2);
				
			}
			if (rowItem == null)
				return new ModelChintSolectriaInverterClass9725Entity();
		} catch (Exception ex) {
			log.error("ModelChintSolectriaInverterClass9725.ModelChintSolectriaInverterClass9725Entity", ex);
			return new ModelChintSolectriaInverterClass9725Entity();
		}
		return rowItem;
	}
	
	/**
	 * @description check trigger alert fault code
	 * @author long.pham
	 * @since 2022-09-26
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelChintSolectriaInverterClass9725(ModelChintSolectriaInverterClass9725Entity obj) {
		// Check device alert by fault code
		int PermanentFaultCode = (obj.getPermanentFaultCode() > 0 && obj.getPermanentFaultCode() != 0.001)
				? (int) obj.getPermanentFaultCode()
				: 0;
		int WarnCode = (obj.getWarnCode() > 0 && obj.getWarnCode() != 0.001) ? (int) obj.getWarnCode() : 0;
		int faultCode0 = (obj.getFaultCode0() > 0 && obj.getFaultCode0() != 0.001) ? (int) obj.getFaultCode0() : 0;
		int faultCode1 = (obj.getFaultCode1() > 0 && obj.getFaultCode1() != 0.001) ? (int) obj.getFaultCode1() : 0;
		int faultCode2 = (obj.getFaultCode2() > 0 && obj.getFaultCode2() != 0.001) ? (int) obj.getFaultCode2() : 0;

		ModelChintSolectriaInverterClass9725Entity rowItem = (ModelChintSolectriaInverterClass9725Entity) checkAlertWriteCode(
				obj);

		if (faultCode2 > 0 && rowItem.getTotalFaultCode2() >= 20) {
			try {
				String toBinary = Integer.toBinaryString(faultCode2);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer
							.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetFaultCode2ModelSolectria(v);
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
					}
					v += 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close faultCode2
			try {
				if(rowItem.getTotalFaultCode2() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 5 is faultCode2
					alertItemClose.setFaultCodeLevel(5);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelChintSolectriaInverterClass9725.getListTriggerFaultCode",
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

		if (faultCode1 > 0 && rowItem.getTotalFaultCode1() >= 20) {

			try {
				String toBinary = Integer.toBinaryString(faultCode1);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer
							.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetFaultCode1ModelSolectria(v);
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
					}
					v += 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close faultCode1
			try {
				if(rowItem.getTotalFaultCode1() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 4 is faultCode1
					alertItemClose.setFaultCodeLevel(4);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelChintSolectriaInverterClass9725.getListTriggerFaultCode",
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

		if (faultCode0 > 0 && rowItem.getTotalFaultCode0() >= 20) {

			try {
				String toBinary = Integer.toBinaryString(faultCode0);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer
							.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetFaultCode0ModelSolectria(v);
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
					}
					v += 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close faultCode0
			try {
				if(rowItem.getTotalFaultCode0() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 3 is faultCode0
					alertItemClose.setFaultCodeLevel(3);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelChintSolectriaInverterClass9725.getListTriggerFaultCode",
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

		if (WarnCode > 0 && rowItem.getTotalWarnCode() >= 20) {

			try {
				String toBinary = Integer.toBinaryString(WarnCode);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer
							.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetWarningCodeModelSolectria(v);
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
					}
					v += 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close WarnCode
			try {
				if(rowItem.getTotalWarnCode() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 2 is WarnCode
					alertItemClose.setFaultCodeLevel(2);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelChintSolectriaInverterClass9725.getListTriggerFaultCode",
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

		if (PermanentFaultCode > 0 && rowItem.getTotalPermanentFaultCode() >= 20) {
			try {
				String toBinary = Integer.toBinaryString(PermanentFaultCode);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer
							.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetPermanentFaultCodeModelSolectria(v);
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
					}
					v += 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close PermanentFaultCode
			try {
				if(rowItem.getTotalPermanentFaultCode() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 1 is PermanentFaultCode
					alertItemClose.setFaultCodeLevel(1);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelChintSolectriaInverterClass9725.getListTriggerFaultCode",
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
