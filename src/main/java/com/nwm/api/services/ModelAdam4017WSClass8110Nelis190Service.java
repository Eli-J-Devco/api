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
import com.nwm.api.entities.ModelAdam4017WSClass8110Nelis190Entity;
import com.nwm.api.utils.Lib;

public class ModelAdam4017WSClass8110Nelis190Service extends DB {

	/**
	 * @description set data ModelAbbTrioClass6210
	 * @author long.pham
	 * @since 2023-04-21
	 * @param data
	 */
	
	public ModelAdam4017WSClass8110Nelis190Entity setModelAdam4017WSClass8110Nelis190(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			
			if (words.size() > 0) {
				ModelAdam4017WSClass8110Nelis190Entity dataModelAdam4017 = new ModelAdam4017WSClass8110Nelis190Entity();
				
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				dataModelAdam4017.setTime(words.get(0).replace("'", ""));
				dataModelAdam4017.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				
				dataModelAdam4017.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelAdam4017.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelAdam4017.setAmbientTemp(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelAdam4017.setPVPanelTemp(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelAdam4017.setPVPanelTemp1(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelAdam4017.setWindSpeed(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelAdam4017.setPOACMP11(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelAdam4017.setIrradiancePOA(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelAdam4017.setIMTInternalTemp(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelAdam4017.setPVPanelTemp2(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelAdam4017.setCode_ch0(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelAdam4017.setCode_ch1(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelAdam4017.setCode_ch2(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelAdam4017.setCode_ch3(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelAdam4017.setCode_ch4(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelAdam4017.setCode_ch5(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelAdam4017.setCode_ch6(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelAdam4017.setCode_ch7(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				
				// set custom field nvm_irradiance
				dataModelAdam4017.setNvm_irradiance(irradiance);
				dataModelAdam4017.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelAdam4017.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				
				
				
				return dataModelAdam4017;
				
			} else {
				return new ModelAdam4017WSClass8110Nelis190Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			System.out.println("ex: " + ex);
			return new ModelAdam4017WSClass8110Nelis190Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_advanced_energy_solaron
	 * @author long.pham
	 * @since 2021-12-20
	 * @param data from datalogger
	 */

	public boolean inserModelAdam4017WSClass8110Nelis190(ModelAdam4017WSClass8110Nelis190Entity obj) {
		try {
			Object insertId = insert("ModelAdam4017WSClass8110Nelis190.insertModelAdam4017WSClass8110Nelis190", obj);
			if (insertId == null) {
				return false;
			}
//			ZoneId zoneIdLosAngeles = ZoneId.of("America/Los_Angeles"); // "America/Los_Angeles"
//			ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
//			int hours = zdtNowLosAngeles.getHour();
//
//			if (hours >= 8 && hours <= 18) {
//				checkTriggerAlertModelAbbTrioClass6210(obj);
//			}
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

//	
//	public ModelAbbTrioClass6210Entity checkAlertWriteCode(ModelAbbTrioClass6210Entity obj) {
//		ModelAbbTrioClass6210Entity rowItem = new ModelAbbTrioClass6210Entity();
//		try {
//			rowItem = (ModelAbbTrioClass6210Entity) queryForObject("ModelAbbTrioClass6210.checkAlertWriteCode", obj);
//			if (rowItem == null)
//				return new ModelAbbTrioClass6210Entity();
//		} catch (Exception ex) {
//			return new ModelAbbTrioClass6210Entity();
//		}
//		return rowItem;
//	}
	
	/**
	 * @description check trigger alert fault code
	 * @author long.pham
	 * @since 2022-09-26
	 * @param data from datalogger
	 */

//	public void checkTriggerAlertModelAbbTrioClass6210(ModelAbbTrioClass6210Entity obj) {
//		// Check device alert by fault code
//		int StatesByte0 = (obj.getStatesByte0() > 0 && obj.getStatesByte0() != 0.001) ? (int) obj.getStatesByte0() : 0;
//		int StatesByte1 = (obj.getStatesByte1() > 0 && obj.getStatesByte1() != 0.001) ? (int) obj.getStatesByte1() : 0;
//		int StatesByte2 = (obj.getStatesByte2() > 0 && obj.getStatesByte2() != 0.001) ? (int) obj.getStatesByte2() : 0;
//		int StatesByte3 = (obj.getStatesByte3() > 0 && obj.getStatesByte3() != 0.001) ? (int) obj.getStatesByte3() : 0;
//		int StatesByte4 = (obj.getStatesByte4() > 0 && obj.getStatesByte4() != 0.001) ? (int) obj.getStatesByte4() : 0;
//
//		ModelAbbTrioClass6210Entity rowItem = (ModelAbbTrioClass6210Entity) checkAlertWriteCode(
//				obj);
//
//		if (StatesByte0 > 0 && rowItem.getTotalStatesByte0() >= 4) {
//			try {
//				int errorId = LibErrorCode.GetStatesByte0ModelABB(StatesByte0);
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
//			// Close StatesByte0
//			try {
//				if(rowItem.getTotalStatesByte0() == 0) {
//					AlertEntity alertItemClose = new AlertEntity();
//					alertItemClose.setId_device(obj.getId_device());
//					// type 1 is StatesByte0
//					alertItemClose.setFaultCodeLevel(1);
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
//		
//		
//		
//		if (StatesByte1 > 0 && rowItem.getTotalStatesByte1() >= 4) {
//			try {
//				int errorId = LibErrorCode.GetStatesByte1ModelABB(StatesByte1);
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
//			// Close StatesByte1
//			try {
//				if(rowItem.getTotalStatesByte1() == 0) {
//					AlertEntity alertItemClose = new AlertEntity();
//					alertItemClose.setId_device(obj.getId_device());
//					// type 2 is StatesByte1
//					alertItemClose.setFaultCodeLevel(2);
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
//		
//		
//		if (StatesByte2 > 0 && rowItem.getTotalStatesByte2() >= 4) {
//			try {
//				int errorId = LibErrorCode.GetStatesByte2ModelABB(StatesByte2);
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
//			// Close StatesByte2
//			try {
//				if(rowItem.getTotalStatesByte2() == 0) {
//					AlertEntity alertItemClose = new AlertEntity();
//					alertItemClose.setId_device(obj.getId_device());
//					// type 3 is StatesByte2
//					alertItemClose.setFaultCodeLevel(3);
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
//				
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
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
//		
//		
//		if (StatesByte4 > 0 && rowItem.getTotalStatesByte4() >= 4) {
//			try {
//				int errorId = LibErrorCode.GetStatesByte4ModelABB(StatesByte4);
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
//			// Close StatesByte4
//			try {
//				if(rowItem.getTotalStatesByte4() == 0) {
//					AlertEntity alertItemClose = new AlertEntity();
//					alertItemClose.setId_device(obj.getId_device());
//					// type 5 is StatesByte4
//					alertItemClose.setFaultCodeLevel(5);
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
//		
//
//	}

}
