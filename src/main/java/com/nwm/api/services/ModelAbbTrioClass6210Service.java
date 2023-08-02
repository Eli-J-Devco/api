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
import com.nwm.api.entities.ModelAbbTrioClass6210Entity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelAbbTrioClass6210Service extends DB {

	/**
	 * @description set data ModelAbbTrioClass6210
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelAbbTrioClass6210Entity setModelAbbTrioClass6210(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelAbbTrioClass6210Entity dataModelABB = new ModelAbbTrioClass6210Entity();
				dataModelABB.setTime(words.get(0).replace("'", ""));
				dataModelABB.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				
				dataModelABB.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelABB.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelABB.setAuroraType(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelABB.setGridType(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelABB.setTransformerType(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelABB.setStatesByte0(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelABB.setStatesByte1(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelABB.setStatesByte2(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelABB.setStatesByte3(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelABB.setStatesByte4(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelABB.setTotalEnergy(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelABB.setGridVoltage(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelABB.setGridCurrent(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelABB.setGridPower(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelABB.setFrequency(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelABB.setInput1Voltage(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelABB.setInput1Current(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelABB.setInput2Power(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelABB.setInput2Voltage(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelABB.setInput2Current(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelABB.setInverterTemperature(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelABB.setBooseterTemperature(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelABB.setIslolationResistance(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelABB.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				
				
				
				return dataModelABB;
				
			} else {
				return new ModelAbbTrioClass6210Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelAbbTrioClass6210Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_advanced_energy_solaron
	 * @author long.pham
	 * @since 2021-12-20
	 * @param data from datalogger
	 */

	public boolean insertModelAbbTrioClass6210(ModelAbbTrioClass6210Entity obj) {
		try {
			ModelAbbTrioClass6210Entity dataObj = (ModelAbbTrioClass6210Entity) queryForObject("ModelAbbTrioClass6210.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 }
			 }
			 obj.setMeasuredProduction(measuredProduction);
			 
			Object insertId = insert("ModelAbbTrioClass6210.insertModelAbbTrioClass6210", obj);
			if (insertId == null) {
				return false;
			}
			ZoneId zoneIdLosAngeles = ZoneId.of("America/Los_Angeles"); // "America/Los_Angeles"
			ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
			int hours = zdtNowLosAngeles.getHour();

			if (hours >= 8 && hours <= 18) {
				checkTriggerAlertModelAbbTrioClass6210(obj);
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

	public ModelAbbTrioClass6210Entity checkAlertWriteCode(ModelAbbTrioClass6210Entity obj) {
		ModelAbbTrioClass6210Entity rowItem = new ModelAbbTrioClass6210Entity();
		try {
			rowItem = (ModelAbbTrioClass6210Entity) queryForObject("ModelAbbTrioClass6210.checkAlertWriteCode", obj);
			if (rowItem == null)
				return new ModelAbbTrioClass6210Entity();
		} catch (Exception ex) {
			return new ModelAbbTrioClass6210Entity();
		}
		return rowItem;
	}
	
	/**
	 * @description check trigger alert fault code
	 * @author long.pham
	 * @since 2022-09-26
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelAbbTrioClass6210(ModelAbbTrioClass6210Entity obj) {
		// Check device alert by fault code
		int StatesByte0 = (obj.getStatesByte0() > 0 && obj.getStatesByte0() != 0.001) ? (int) obj.getStatesByte0() : 0;
		int StatesByte1 = (obj.getStatesByte1() > 0 && obj.getStatesByte1() != 0.001) ? (int) obj.getStatesByte1() : 0;
		int StatesByte2 = (obj.getStatesByte2() > 0 && obj.getStatesByte2() != 0.001) ? (int) obj.getStatesByte2() : 0;
//		int StatesByte3 = (obj.getStatesByte3() > 0 && obj.getStatesByte3() != 0.001) ? (int) obj.getStatesByte3() : 0;
		int StatesByte4 = (obj.getStatesByte4() > 0 && obj.getStatesByte4() != 0.001) ? (int) obj.getStatesByte4() : 0;

		ModelAbbTrioClass6210Entity rowItem = (ModelAbbTrioClass6210Entity) checkAlertWriteCode(
				obj);

		if (StatesByte0 > 0 && rowItem.getTotalStatesByte0() >= 4) {
			try {
				int errorId = LibErrorCode.GetStatesByte0ModelABB(StatesByte0);
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
			// Close StatesByte0
			try {
				if(rowItem.getTotalStatesByte0() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 1 is StatesByte0
					alertItemClose.setFaultCodeLevel(1);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelAbbTrioClass6210.getListTriggerFaultCode",
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
		
		
		
		if (StatesByte1 > 0 && rowItem.getTotalStatesByte1() >= 4) {
			try {
				int errorId = LibErrorCode.GetStatesByte1ModelABB(StatesByte1);
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
			// Close StatesByte1
			try {
				if(rowItem.getTotalStatesByte1() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 2 is StatesByte1
					alertItemClose.setFaultCodeLevel(2);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelAbbTrioClass6210.getListTriggerFaultCode",
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
		
		
		if (StatesByte2 > 0 && rowItem.getTotalStatesByte2() >= 4) {
			try {
				int errorId = LibErrorCode.GetStatesByte2ModelABB(StatesByte2);
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
			// Close StatesByte2
			try {
				if(rowItem.getTotalStatesByte2() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 3 is StatesByte2
					alertItemClose.setFaultCodeLevel(3);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelAbbTrioClass6210.getListTriggerFaultCode",
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
		
//		if (StatesByte3 > 0 && rowItem.getTotalStatesByte3() >= 4) {
//			try {
//				int errorId = LibErrorCode.GetStatesByte3ModelABB(StatesByte3);
//				System.out.println("status errorId: " + errorId);
//				if (errorId > 0) {
//					AlertEntity alertDeviceItem = new AlertEntity();
//					alertDeviceItem.setId_device(obj.getId_device());
//					alertDeviceItem.setStart_date(obj.getTime());
//					alertDeviceItem.setId_error(errorId);
//					boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist",
//							alertDeviceItem) > 0;
//					boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
//					if (!checkAlertDeviceExist && errorExits) {
//						insert("BatchJob.insertAlert", alertDeviceItem);
//					}
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		} else {
//			// Close StatesByte3
//			try {
//				if(rowItem.getTotalStatesByte3() == 0) {
//					AlertEntity alertItemClose = new AlertEntity();
//					alertItemClose.setId_device(obj.getId_device());
//					// type 4 is StatesByte3
//					alertItemClose.setFaultCodeLevel(4);
//					List dataListStatusCode = new ArrayList();
//					dataListStatusCode = queryForList("ModelAbbTrioClass6210.getListTriggerFaultCode",
//							alertItemClose);
//					if (dataListStatusCode.size() > 0) {
//						for (int i = 0; i < dataListStatusCode.size(); i++) {
//							Map<String, Object> itemFault = (Map<String, Object>) dataListStatusCode.get(i);
//							int id = Integer.parseInt(itemFault.get("id").toString());
//							int idError = Integer.parseInt(itemFault.get("id_error").toString());
//							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
//							alertItemClose.setId(id);
//							alertItemClose.setId_error(idError);
//							update("Alert.UpdateErrorRow", alertItemClose);
//						}
//					}
//				}
//				
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		
		if (StatesByte4 > 0 && rowItem.getTotalStatesByte4() >= 4) {
			try {
				int errorId = LibErrorCode.GetStatesByte4ModelABB(StatesByte4);
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
			// Close StatesByte4
			try {
				if(rowItem.getTotalStatesByte4() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 5 is StatesByte4
					alertItemClose.setFaultCodeLevel(5);
					List dataListStatusCode = new ArrayList();
					dataListStatusCode = queryForList("ModelAbbTrioClass6210.getListTriggerFaultCode",
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
