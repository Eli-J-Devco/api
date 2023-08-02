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
import com.nwm.api.entities.ModelSatconPowergate225InverterEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

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
			ModelSatconPowergate225InverterEntity dataObj = (ModelSatconPowergate225InverterEntity) queryForObject("ModelSatconPowergate225Inverter.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 }
			 }
			 obj.setMeasuredProduction(measuredProduction);
			 
			Object insertId = insert("ModelSatconPowergate225Inverter.insertModelSatconPowergate225Inverter", obj);
			if (insertId == null) {
				return false;
			}
			
			ZoneId zoneIdLosAngeles = ZoneId.of("America/Los_Angeles"); // "America/Los_Angeles"
	        ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
	        int hours = zdtNowLosAngeles.getHour();
	        
	        if(hours >=8 && hours <= 18) {
	        	checkTriggerAlertModelSatconPVS357Inverter(obj);
	        }
	        
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
	
	
	/**
	 * @description get last row
	 * @author long.pham
	 * @since 2023-05-23
	 * @param id_device
	 * @return Object
	 */

	public ModelSatconPowergate225InverterEntity getLastRow(ModelSatconPowergate225InverterEntity obj) {
		ModelSatconPowergate225InverterEntity dataObj = new ModelSatconPowergate225InverterEntity();
		try {
			dataObj = (ModelSatconPowergate225InverterEntity) queryForObject("ModelSatconPowergate225Inverter.getLastRow", obj);
			if (dataObj == null)
				return new ModelSatconPowergate225InverterEntity();
		} catch (Exception ex) {
			return new ModelSatconPowergate225InverterEntity();
		}
		return dataObj;
	}
	
	

	
	/**
	 * @description get last row "data table name" by device
	 * @author long.pham
	 * @since 2023-05-23
	 * @param datatablename
	 */

	public ModelSatconPowergate225InverterEntity checkAlertWriteCode(ModelSatconPowergate225InverterEntity obj) {
		ModelSatconPowergate225InverterEntity rowItem = new ModelSatconPowergate225InverterEntity();
		try {
			rowItem = (ModelSatconPowergate225InverterEntity) queryForObject("ModelSatconPowergate225Inverter.checkAlertWriteCode", obj);
			if (rowItem == null)
				return new ModelSatconPowergate225InverterEntity();
		} catch (Exception ex) {
			return new ModelSatconPowergate225InverterEntity();
		}
		return rowItem;
	}
	

	/**
	 * @description check trigger alert fault code
	 * @author long.pham
	 * @since  2023-04-03
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelSatconPVS357Inverter(ModelSatconPowergate225InverterEntity obj) {
		// Check device alert by fault code
		int fault1 = (obj.getFault1() > 0 && obj.getFault1() != 0.001) ? (int) obj.getFault1() : 0;
		int fault2 = (obj.getFault2() > 0 && obj.getFault2() != 0.001) ? (int) obj.getFault2() : 0;
		int fault3 = (obj.getFault3() > 0 && obj.getFault3() != 0.001) ? (int) obj.getFault3() : 0;
		int fault4 = (obj.getFault4() > 0 && obj.getFault4() != 0.001) ? (int) obj.getFault4() : 0;
		int fault5 = (obj.getGridStatus() > 0 && obj.getGridStatus() != 0.001) ? (int) obj.getGridStatus() : 0;
		int fault6 = (obj.getStatus6() > 0 && obj.getStatus6() != 0.001) ? (int) obj.getStatus6() : 0;
		int fault7 = (obj.getStatus7() > 0 && obj.getStatus7() != 0.001) ? (int) obj.getStatus7() : 0;
		
		
		ModelSatconPowergate225InverterEntity rowItem = (ModelSatconPowergate225InverterEntity) checkAlertWriteCode(obj);
		
		
		// check fault code 1
		if (fault1 > 0  && rowItem.getTotalFault1() >= 4) {
			try {
				String toBinary = Integer.toBinaryString(fault1);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelSatconPowergate225Inverter(v, 1); 
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
					alertItemClose.setFaultCodeLevel(1);
					List dataListFault1 = new ArrayList();
					dataListFault1 = queryForList("ModelSatconPowergate225Inverter.getListTriggerFaultCode", alertItemClose);
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
//		if (fault2 > 0  && rowItem.getTotalFaultWord2() >= 4) {
//			try {
//				String toBinary = Integer.toBinaryString(fault2);
//				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
//				int v = 0;
//				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
//					int index = b;
//					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
//					if (bitLevel == 1) {
//						int errorId = LibErrorCode.GetErrorCodeModelSatconPVS357Inverter(v, 2);
//						if (errorId > 0) {
//							AlertEntity alertDeviceItem = new AlertEntity();
//							alertDeviceItem.setId_device(obj.getId_device());
//							alertDeviceItem.setStart_date(obj.getTime());
//							alertDeviceItem.setId_error(errorId);
//							boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist", alertDeviceItem) > 0;
//							boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
//							if (!checkAlertDeviceExist && errorExits) {
//								insert("BatchJob.insertAlert", alertDeviceItem);
//							}
//						}
//					}
//					v += 1;
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		} else {
//			// Close fault code 
//			try {
//				if(rowItem.getTotalFaultWord2() == 0) {
//					AlertEntity alertItemClose = new AlertEntity();
//					alertItemClose.setId_device(obj.getId_device());
//					alertItemClose.setFaultCodeLevel(2);
//					List dataListFault2 = new ArrayList();
//					dataListFault2 = queryForList("ModelSatconPvs357Inverter.getListTriggerFaultCode", alertItemClose);
//					if(dataListFault2.size() > 0) {
//						for(int i = 0; i < dataListFault2.size(); i++) {
//							Map<String, Object> itemFault = (Map<String, Object>) dataListFault2.get(i);
//							int id =  Integer.parseInt(itemFault.get("id").toString());
//							int idError =  Integer.parseInt(itemFault.get("id_error").toString());
//							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
//							alertItemClose.setId(id );
//							alertItemClose.setId_error(idError);
//							update("Alert.UpdateErrorRow", alertItemClose);
//						}
//					}
//				}
//				
//			}
//			catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		
		
		// check fault code 3
		if (fault3 > 0  && rowItem.getTotalFault3() >= 4) {
			try {
				String toBinary = Integer.toBinaryString(fault3);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelSatconPowergate225Inverter(v, 3);
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
					alertItemClose.setFaultCodeLevel(3);
					List dataListFault3 = new ArrayList();
					dataListFault3 = queryForList("ModelSatconPowergate225Inverter.getListTriggerFaultCode", alertItemClose);
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

		
		
		// check fault code 4
		if (fault4 > 0  && rowItem.getTotalFault4() >= 4) {
			try {
				String toBinary = Integer.toBinaryString(fault4);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelSatconPowergate225Inverter(v, 4);
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
			// Close fault code 4
			try {
				if(rowItem.getTotalFault4() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					alertItemClose.setFaultCodeLevel(4);
					List dataListFault4 = new ArrayList();
					dataListFault4 = queryForList("ModelSatconPowergate225Inverter.getListTriggerFaultCode", alertItemClose);
					if(dataListFault4.size() > 0) {
						for(int i = 0; i < dataListFault4.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListFault4.get(i);
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
		
		
		
		// check fault code 5
		if (fault5 > 0  && rowItem.getTotalGridStatus() >= 4) {
			try {
				String toBinary = Integer.toBinaryString(fault5);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelSatconPowergate225Inverter(v, 5);
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
			// Close fault code 5
			try {
				if(rowItem.getTotalGridStatus() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					alertItemClose.setFaultCodeLevel(5);
					List dataListFault5 = new ArrayList();
					dataListFault5 = queryForList("ModelSatconPowergate225Inverter.getListTriggerFaultCode", alertItemClose);
					if(dataListFault5.size() > 0) {
						for(int i = 0; i < dataListFault5.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListFault5.get(i);
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
		
		
		// check fault code 6
		if (fault6 > 0  && rowItem.getTotalStatus6() >= 4) {
			try {
				String toBinary = Integer.toBinaryString(fault6);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelSatconPowergate225Inverter(v, 6);
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
			// Close fault code 6
			try {
				if(rowItem.getTotalStatus6() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					alertItemClose.setFaultCodeLevel(6);
					List dataListFault6 = new ArrayList();
					dataListFault6 = queryForList("ModelSatconPowergate225Inverter.getListTriggerFaultCode", alertItemClose);
					if(dataListFault6.size() > 0) {
						for(int i = 0; i < dataListFault6.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListFault6.get(i);
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
		
		
		// check fault code 7
		if (fault7 > 0  && rowItem.getTotalStatus7() >= 4) {
			try {
				String toBinary = Integer.toBinaryString(fault7);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelSatconPowergate225Inverter(v, 7);
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
			// Close fault code 7
			try {
				if(rowItem.getTotalStatus7() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					alertItemClose.setFaultCodeLevel(7);
					List dataListFault7 = new ArrayList();
					dataListFault7 = queryForList("ModelSatconPowergate225Inverter.getListTriggerFaultCode", alertItemClose);
					if(dataListFault7.size() > 0) {
						for(int i = 0; i < dataListFault7.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListFault7.get(i);
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
