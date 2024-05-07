/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.List;
import java.util.Map;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelLevitonAbviusA891123ChannelEntity;
import com.nwm.api.entities.ModelLevitonVirtualMeterEntity;
import com.nwm.api.utils.Lib;

public class ModelLevitonAbviusA891123ChannelService extends DB {

	/**
	 * @description set data ModelLevitonAbviusA891123Channel
	 * @author long.pham
	 * @since 2024-03-22
	 * @param data
	 */
	
	public ModelLevitonAbviusA891123ChannelEntity setModelLevitonAbviusA891123Channel(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelLevitonAbviusA891123ChannelEntity dataModelLA891123 = new ModelLevitonAbviusA891123ChannelEntity();
				dataModelLA891123.setTime(words.get(0).replace("'", ""));
				dataModelLA891123.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelLA891123.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelLA891123.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelLA891123.setField0(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelLA891123.setField1(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelLA891123.setField2(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelLA891123.setField3(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelLA891123.setField4(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelLA891123.setField5(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelLA891123.setField6(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelLA891123.setField7(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelLA891123.setField8(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelLA891123.setField9(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelLA891123.setField10(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelLA891123.setField11(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelLA891123.setField12(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelLA891123.setField13(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelLA891123.setField14(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelLA891123.setField15(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelLA891123.setField16(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelLA891123.setField17(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelLA891123.setField18(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelLA891123.setField19(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelLA891123.setField20(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelLA891123.setField21(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelLA891123.setField22(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelLA891123.setField23(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelLA891123.setField24(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelLA891123.setField25(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelLA891123.setField26(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelLA891123.setField27(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelLA891123.setField28(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelLA891123.setField29(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelLA891123.setField30(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelLA891123.setField31(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelLA891123.setField32(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelLA891123.setField33(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelLA891123.setField34(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelLA891123.setField35(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelLA891123.setField36(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModelLA891123.setField37(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModelLA891123.setField38(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelLA891123.setField39(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModelLA891123.setField40(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelLA891123.setField41(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModelLA891123.setField42(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelLA891123.setField43(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelLA891123.setField44(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelLA891123.setField45(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				
				return dataModelLA891123;
				
			} else {
				return new ModelLevitonAbviusA891123ChannelEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelLevitonAbviusA891123ChannelEntity();
		}
	}

	/**
	 * @description insert data from datalogger to ModelLevitonAbviusA891123Channel
	 * @author long.pham
	 * @since 2024-03-22
	 * @param data from datalogger
	 */

	public boolean insertModelLevitonAbviusA891123Channel(ModelLevitonAbviusA891123ChannelEntity obj) {
		try {

			 Object insertId = insert("ModelLevitonAbviusA891123Channel.insertModelLevitonAbviusA891123Channel", obj);
		     if(insertId == null ) {
		        return false;
		     }

			// Insert data to virtual meter
			List dataList = queryForList("ModelLevitonVirtualMeter.getListVirtualMeterParameter", obj);
			DeviceEntity objDevice = (DeviceEntity) queryForObject("ModelLevitonVirtualMeter.getDeviceVirtualMeter", obj);
				
				
			double power = 0, energy = 0;
			if (dataList != null && objDevice.getId() > 0) {
				for (int i = 0; i < (dataList.size()); i++) {
					Map<String, Object> itemField = (Map<String, Object>) dataList.get(i);
					switch(Integer.parseInt(itemField.get("id_device_parameter").toString())) {
					case 2852:
						if(itemField.get("slug").toString().equals("field0")) { power = power + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { power = power + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { power = power + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { power = power + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { power = power + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { power = power + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { power = power + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { power = power + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { power = power + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { power = power + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { power = power + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { power = power + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { power = power + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { power = power + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { power = power + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { power = power + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { power = power + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { power = power + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { power = power + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { power = power + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { power = power + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { power = power + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { power = power + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { power = power + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { power = power + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { power = power + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { power = power + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { power = power + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { power = power + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { power = power + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { power = power + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { power = power + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { power = power + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { power = power + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { power = power + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { power = power + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { power = power + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { power = power + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { power = power + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { power = power + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { power = power + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { power = power + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { power = power + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { power = power + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { power = power + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { power = power + obj.getField45(); }
						break;
					case 2853:
						if(itemField.get("slug").toString().equals("field0")) { energy = energy + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { energy = energy + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { energy = energy + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { energy = energy + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { energy = energy + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { energy = energy + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { energy = energy + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { energy = energy + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { energy = energy + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { energy = energy + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { energy = energy + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { energy = energy + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { energy = energy + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { energy = energy + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { energy = energy + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { energy = energy + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { energy = energy + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { energy = energy + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { energy = energy + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { energy = energy + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { energy = energy + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { energy = energy + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { energy = energy + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { energy = energy + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { energy = energy + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { energy = energy + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { energy = energy + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { energy = energy + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { energy = energy + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { energy = energy + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { energy = energy + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { energy = energy + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { energy = energy + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { energy = energy + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { energy = energy + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { energy = energy + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { energy = energy + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { energy = energy + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { energy = energy + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { energy = energy + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { energy = energy + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { energy = energy + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { energy = energy + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { energy = energy + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { energy = energy + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { energy = energy + obj.getField45(); }
						break;
					}
				}
				
				ModelLevitonVirtualMeterEntity virtualMeterEntity = new ModelLevitonVirtualMeterEntity();
				virtualMeterEntity.setTime(obj.getTime());
				virtualMeterEntity.setId_device(objDevice.getId());
				virtualMeterEntity.setId_device_map(obj.getId_device());
				virtualMeterEntity.setDatatablename(objDevice.getDatatablename());
				virtualMeterEntity.setField0(power);
				virtualMeterEntity.setField1(energy);
				Object insertVirtualMeterId = insertModelLevitonVirtualMeter(virtualMeterEntity);
			}
			
			
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}
	}
	
	/**
	 * @description insert data from datalogger to ModelLevitonVirtualMeter
	 * @author long.pham
	 * @since 2024-05-03
	 * @param data from datalogger
	 */
	
	public boolean insertModelLevitonVirtualMeter(ModelLevitonVirtualMeterEntity obj) {
		try {
			 Object insertId = insert("ModelLevitonVirtualMeter.insertModelLevitonVirtualMeter", obj);
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
