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
import com.nwm.api.entities.ModelSmaShp7510Entity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelSmaShp7510Service extends DB {
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelSmaShp7510Entity setModelSmaShp7510(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSmaShp7510Entity dataModel = new ModelSmaShp7510Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001");
				
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setACcurrentA(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setCurrentlineconductorL1(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setCurrentlineconductorL2(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setCurrentlineconductorL3(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setScalefactorcurrent(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setVoltagelineconductorL1toL2(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setVoltagelineconductorL2toL3(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				dataModel.setVoltagelineconductorL3toL1(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setVoltagelineconductorL1toN(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setVoltagelineconductorL2toN(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setVoltagelineconductorL3toN(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setScalefactorvoltage(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setActivepower(power);
				dataModel.setScalefactoractivepower(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setPowerfrequency(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setScalefactorpowerfrequency(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				dataModel.setApparentpower(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setScalefactorapparentpower(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setReactivepower(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setScalefactorreactivepower(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setDisplacementpowerfactorcos(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setScalefactordisplacementpowerfactor(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setTotalyield(energy);
				dataModel.setCalefactortotalyield(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setDCcurrent(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				
				dataModel.setScalefactorDCcurrent(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				
				
				dataModel.setDCvoltage(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel.setScalefactorDCvoltage(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				
				dataModel.setDCpower(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setScalefactorDCpower(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setInternaltemperature(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModel.setHeatsinktemperature(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModel.setTransformer(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setOthertemperature(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModel.setScalefactortemperature(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel.setOperatingstatus(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setManufacturerspecificstatuscode(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModel.setEventnumberEvt1(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModel.setEventnumberEvt2(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				
				dataModel.setManufacturerspecificeventcodeEvtVnd1(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel.setManufacturerspecificeventcodeEvtVnd2(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel.setManufacturerspecificeventcodeEvtVnd3(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModel.setManufacturerspecificeventcodeEvtVnd4(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				
				
				
				
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelSmaShp7510Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSmaShp7510Entity();
		}
	}

	
	

	/**
	 * @description insert data from datalogger to model_ivt_solaron_ext
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelSmaShp7510(ModelSmaShp7510Entity obj) {
		try {
			ModelSmaShp7510Entity dataObj = (ModelSmaShp7510Entity) queryForObject("ModelSmaShp7510.getLastRow", obj);
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setTotalyield(energy);
			}
			
			// filter data 
			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() < dataObj.getNvmActiveEnergy() || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setTotalyield(dataObj.getNvmActiveEnergy());
			}
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
		 	Object insertId = insert("ModelSmaShp7510.insertModelSmaShp7510", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        ZoneId zoneIdLosAngeles = ZoneId.of("America/Los_Angeles"); // "America/Los_Angeles"
	        ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
	        int hours = zdtNowLosAngeles.getHour();
	        if (hours >= 9 && hours <= 17 && dataObj.getEnable_alert() >= 1) {
	        	checkTriggerAlertModelSmaShp7510(obj);
	        	
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

	public ModelSmaShp7510Entity checkAlertWriteCode(ModelSmaShp7510Entity obj) {
		ModelSmaShp7510Entity rowItem = new ModelSmaShp7510Entity();
		try {
			List dataList = queryForList("ModelSmaShp7510.checkAlertWriteCode", obj);
			if(dataList.size() > 0) {
				int totalFault1 = 0;
				for(int i =0; i < dataList.size(); i ++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double Mode = (double) item.get("Manufacturerspecificstatuscode");
					if(Double.compare(obj.getManufacturerspecificstatuscode(), Mode) == 0 && obj.getManufacturerspecificstatuscode() > 0 && Mode > 0) { 
						totalFault1++;
					}
				}
				rowItem.setTotalFault1(totalFault1);
				
			}
			
			if (rowItem == null)
				return new ModelSmaShp7510Entity();
		} catch (Exception ex) {
			log.error("ModelSmaShp7510.checkAlertWriteCode", ex);
			return new ModelSmaShp7510Entity();
		}
		return rowItem;
	}
	
	/**
	 * @description check trigger alert fault code
	 * @author long.pham
	 * @since 2022-09-26
	 * @param data from datalogger 
	 */

	public void checkTriggerAlertModelSmaShp7510(ModelSmaShp7510Entity obj) {
		
		
		// Check device alert by fault code
		int fault1 = (obj.getManufacturerspecificstatuscode() > 0 && obj.getManufacturerspecificstatuscode() != 0.001) ? (int) obj.getManufacturerspecificstatuscode() : 0;
		int fault2 = (obj.getManufacturerspecificeventcodeEvtVnd1() > 0 && obj.getManufacturerspecificeventcodeEvtVnd1() != 0.001) ? (int) obj.getManufacturerspecificstatuscode() : 0;
		int fault3 = (obj.getManufacturerspecificeventcodeEvtVnd2() > 0 && obj.getManufacturerspecificeventcodeEvtVnd2() != 0.001) ? (int) obj.getManufacturerspecificstatuscode() : 0;
		int fault4 = (obj.getManufacturerspecificeventcodeEvtVnd3() > 0 && obj.getManufacturerspecificeventcodeEvtVnd3() != 0.001) ? (int) obj.getManufacturerspecificstatuscode() : 0;
		int fault5 = (obj.getManufacturerspecificeventcodeEvtVnd4() > 0 && obj.getManufacturerspecificeventcodeEvtVnd4() != 0.001) ? (int) obj.getManufacturerspecificstatuscode() : 0;
		
		ModelSmaShp7510Entity rowItem = (ModelSmaShp7510Entity) checkAlertWriteCode(obj);
		
		if(fault1 > 0 && rowItem.getTotalFault1() >= 20) {
			try {
				int errorId = LibErrorCode.GetFaultCodeModelSmaShp7510(fault1);
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
			// Close
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 1 is warning code
				alertItemClose.setFaultCodeLevel(1);
				List dataListWarningCode = queryForList("ModelSmaShp7510.getListTriggerFaultCode", alertItemClose);
				if(dataListWarningCode.size() > 0) {
					for(int i = 0; i < dataListWarningCode.size(); i++) {
						Map<String, Object> itemFault = (Map<String, Object>) dataListWarningCode.get(i);
						int id =  Integer.parseInt(itemFault.get("id").toString());
						int idError =  Integer.parseInt(itemFault.get("id_error").toString());
						alertItemClose.setEnd_date(itemFault.get("end_date").toString());
						alertItemClose.setId(id );
						alertItemClose.setId_error(idError);
						update("Alert.UpdateErrorRow", alertItemClose);
					}
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		// check fault code 2
		if (fault2 > 0  && rowItem.getTotalFault2() >= 20) {
			try {
				String toBinary = Integer.toBinaryString(fault2);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelSmaShp7510Bit(v, 2);
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
			// Close fault code 
			try {
				if(rowItem.getTotalFault2() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					alertItemClose.setFaultCodeLevel(2);
					List dataListFault2 = new ArrayList();
					dataListFault2 = queryForList("ModelSmaShp7510.getListTriggerFaultCode", alertItemClose);
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
						int errorId = LibErrorCode.GetErrorCodeModelSmaShp7510Bit(v, 3);
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
					dataListFault3 = queryForList("ModelSmaShp7510.getListTriggerFaultCode", alertItemClose);
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
		if (fault4 > 0  && rowItem.getTotalFault4() >= 20) {
			try {
				String toBinary = Integer.toBinaryString(fault4);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelSmaShp7510Bit(v, 4);
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
					dataListFault4 = queryForList("ModelSmaShp7510.getListTriggerFaultCode", alertItemClose);
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
		if (fault5 > 0  && rowItem.getTotalFault5() >= 20) {
			try {
				String toBinary = Integer.toBinaryString(fault5);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelSmaShp7510Bit(v, 5);
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
				if(rowItem.getTotalFault5() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					alertItemClose.setFaultCodeLevel(5);
					List dataListFault5 = new ArrayList();
					dataListFault5 = queryForList("ModelSmaShp7510.getListTriggerFaultCode", alertItemClose);
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
		

	}
	
	

}
