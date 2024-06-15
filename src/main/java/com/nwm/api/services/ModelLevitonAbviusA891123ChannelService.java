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
				
				
			double power = 0, energy = 0, 
					warehouseLights = 0,
					WarehouseLightsDemand = 0,
					OfficeLights = 0,
					OfficeLightsDemand = 0,
					ServerRoom = 0,
					ServerRoomDemand = 0,
					ServerRoomReceptacles = 0,
					ServerRoomReceptaclesDemand = 0,
					ReceptaclesPannel1A = 0,
					ReceptaclesPannel1ADemand = 0,
					ReceptaclesPannel2A = 0,
					ReceptaclesPannel2ADemand = 0,
					ReceptaclesPannel2B = 0,
					ReceptaclesPannel2BDemand = 0,
					Compressor = 0,
					CompressorDemand = 0,
					TonHVACUnit = 0,
					TonHVACUnitDemand = 0,
					HVACLoads = 0,
					HVACLoadsDemand = 0;
			
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
					case 2854:
						if(itemField.get("slug").toString().equals("field0")) { warehouseLights = warehouseLights + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { warehouseLights = warehouseLights + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { warehouseLights = warehouseLights + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { warehouseLights = warehouseLights + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { warehouseLights = warehouseLights + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { warehouseLights = warehouseLights + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { warehouseLights = warehouseLights + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { warehouseLights = warehouseLights + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { warehouseLights = warehouseLights + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { warehouseLights = warehouseLights + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { warehouseLights = warehouseLights + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { warehouseLights = warehouseLights + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { warehouseLights = warehouseLights + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { warehouseLights = warehouseLights + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { warehouseLights = warehouseLights + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { warehouseLights = warehouseLights + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { warehouseLights = warehouseLights + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { warehouseLights = warehouseLights + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { warehouseLights = warehouseLights + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { warehouseLights = warehouseLights + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { warehouseLights = warehouseLights + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { warehouseLights = warehouseLights + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { warehouseLights = warehouseLights + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { warehouseLights = warehouseLights + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { warehouseLights = warehouseLights + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { warehouseLights = warehouseLights + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { warehouseLights = warehouseLights + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { warehouseLights = warehouseLights + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { warehouseLights = warehouseLights + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { warehouseLights = warehouseLights + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { warehouseLights = warehouseLights + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { warehouseLights = warehouseLights + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { warehouseLights = warehouseLights + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { warehouseLights = warehouseLights + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { warehouseLights = warehouseLights + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { warehouseLights = warehouseLights + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { warehouseLights = warehouseLights + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { warehouseLights = warehouseLights + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { warehouseLights = warehouseLights + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { warehouseLights = warehouseLights + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { warehouseLights = warehouseLights + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { warehouseLights = warehouseLights + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { warehouseLights = warehouseLights + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { warehouseLights = warehouseLights + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { warehouseLights = warehouseLights + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { warehouseLights = warehouseLights + obj.getField45(); }
						break;
					case 2855:
						if(itemField.get("slug").toString().equals("field0")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getField45(); }
						break;
					case 2856:
						if(itemField.get("slug").toString().equals("field0")) { OfficeLights = OfficeLights + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { OfficeLights = OfficeLights + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { OfficeLights = OfficeLights + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { OfficeLights = OfficeLights + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { OfficeLights = OfficeLights + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { OfficeLights = OfficeLights + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { OfficeLights = OfficeLights + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { OfficeLights = OfficeLights + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { OfficeLights = OfficeLights + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { OfficeLights = OfficeLights + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { OfficeLights = OfficeLights + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { OfficeLights = OfficeLights + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { OfficeLights = OfficeLights + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { OfficeLights = OfficeLights + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { OfficeLights = OfficeLights + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { OfficeLights = OfficeLights + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { OfficeLights = OfficeLights + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { OfficeLights = OfficeLights + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { OfficeLights = OfficeLights + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { OfficeLights = OfficeLights + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { OfficeLights = OfficeLights + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { OfficeLights = OfficeLights + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { OfficeLights = OfficeLights + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { OfficeLights = OfficeLights + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { OfficeLights = OfficeLights + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { OfficeLights = OfficeLights + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { OfficeLights = OfficeLights + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { OfficeLights = OfficeLights + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { OfficeLights = OfficeLights + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { OfficeLights = OfficeLights + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { OfficeLights = OfficeLights + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { OfficeLights = OfficeLights + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { OfficeLights = OfficeLights + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { OfficeLights = OfficeLights + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { OfficeLights = OfficeLights + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { OfficeLights = OfficeLights + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { OfficeLights = OfficeLights + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { OfficeLights = OfficeLights + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { OfficeLights = OfficeLights + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { OfficeLights = OfficeLights + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { OfficeLights = OfficeLights + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { OfficeLights = OfficeLights + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { OfficeLights = OfficeLights + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { OfficeLights = OfficeLights + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { OfficeLights = OfficeLights + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { OfficeLights = OfficeLights + obj.getField45(); }
						break;
					case 2857:
						if(itemField.get("slug").toString().equals("field0")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { OfficeLightsDemand = OfficeLightsDemand + obj.getField45(); }
						break;
					case 2858:
						if(itemField.get("slug").toString().equals("field0")) { ServerRoom = ServerRoom + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { ServerRoom = ServerRoom + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { ServerRoom = ServerRoom + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { ServerRoom = ServerRoom + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { ServerRoom = ServerRoom + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { ServerRoom = ServerRoom + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { ServerRoom = ServerRoom + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { ServerRoom = ServerRoom + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { ServerRoom = ServerRoom + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { ServerRoom = ServerRoom + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { ServerRoom = ServerRoom + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { ServerRoom = ServerRoom + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { ServerRoom = ServerRoom + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { ServerRoom = ServerRoom + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { ServerRoom = ServerRoom + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { ServerRoom = ServerRoom + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { ServerRoom = ServerRoom + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { ServerRoom = ServerRoom + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { ServerRoom = ServerRoom + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { ServerRoom = ServerRoom + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { ServerRoom = ServerRoom + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { ServerRoom = ServerRoom + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { ServerRoom = ServerRoom + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { ServerRoom = ServerRoom + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { ServerRoom = ServerRoom + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { ServerRoom = ServerRoom + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { ServerRoom = ServerRoom + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { ServerRoom = ServerRoom + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { ServerRoom = ServerRoom + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { ServerRoom = ServerRoom + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { ServerRoom = ServerRoom + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { ServerRoom = ServerRoom + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { ServerRoom = ServerRoom + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { ServerRoom = ServerRoom + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { ServerRoom = ServerRoom + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { ServerRoom = ServerRoom + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { ServerRoom = ServerRoom + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { ServerRoom = ServerRoom + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { ServerRoom = ServerRoom + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { ServerRoom = ServerRoom + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { ServerRoom = ServerRoom + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { ServerRoom = ServerRoom + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { ServerRoom = ServerRoom + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { ServerRoom = ServerRoom + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { ServerRoom = ServerRoom + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { ServerRoom = ServerRoom + obj.getField45(); }
						break;
					case 2859:
						if(itemField.get("slug").toString().equals("field0")) { ServerRoomDemand = ServerRoomDemand + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { ServerRoomDemand = ServerRoomDemand + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { ServerRoomDemand = ServerRoomDemand + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { ServerRoomDemand = ServerRoomDemand + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { ServerRoomDemand = ServerRoomDemand + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { ServerRoomDemand = ServerRoomDemand + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { ServerRoomDemand = ServerRoomDemand + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { ServerRoomDemand = ServerRoomDemand + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { ServerRoomDemand = ServerRoomDemand + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { ServerRoomDemand = ServerRoomDemand + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { ServerRoomDemand = ServerRoomDemand + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { ServerRoomDemand = ServerRoomDemand + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { ServerRoomDemand = ServerRoomDemand + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { ServerRoomDemand = ServerRoomDemand + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { ServerRoomDemand = ServerRoomDemand + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { ServerRoomDemand = ServerRoomDemand + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { ServerRoomDemand = ServerRoomDemand + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { ServerRoomDemand = ServerRoomDemand + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { ServerRoomDemand = ServerRoomDemand + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { ServerRoomDemand = ServerRoomDemand + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { ServerRoomDemand = ServerRoomDemand + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { ServerRoomDemand = ServerRoomDemand + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { ServerRoomDemand = ServerRoomDemand + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { ServerRoomDemand = ServerRoomDemand + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { ServerRoomDemand = ServerRoomDemand + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { ServerRoomDemand = ServerRoomDemand + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { ServerRoomDemand = ServerRoomDemand + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { ServerRoomDemand = ServerRoomDemand + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { ServerRoomDemand = ServerRoomDemand + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { ServerRoomDemand = ServerRoomDemand + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { ServerRoomDemand = ServerRoomDemand + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { ServerRoomDemand = ServerRoomDemand + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { ServerRoomDemand = ServerRoomDemand + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { ServerRoomDemand = ServerRoomDemand + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { ServerRoomDemand = ServerRoomDemand + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { ServerRoomDemand = ServerRoomDemand + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { ServerRoomDemand = ServerRoomDemand + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { ServerRoomDemand = ServerRoomDemand + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { ServerRoomDemand = ServerRoomDemand + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { ServerRoomDemand = ServerRoomDemand + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { ServerRoomDemand = ServerRoomDemand + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { ServerRoomDemand = ServerRoomDemand + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { ServerRoomDemand = ServerRoomDemand + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { ServerRoomDemand = ServerRoomDemand + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { ServerRoomDemand = ServerRoomDemand + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { ServerRoomDemand = ServerRoomDemand + obj.getField45(); }
						break;
					case 2860:
						if(itemField.get("slug").toString().equals("field0")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getField45(); }
						break;
					case 2861:
						if(itemField.get("slug").toString().equals("field0")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getField45(); }
						break;
					case 2862:
						if(itemField.get("slug").toString().equals("field0")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getField45(); }
						break;
					case 2863:
						if(itemField.get("slug").toString().equals("field0")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getField45(); }
						break;
					case 2864:
						if(itemField.get("slug").toString().equals("field0")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getField45(); }
						break;
					case 2865:
						if(itemField.get("slug").toString().equals("field0")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getField45(); }
						break;
					case 2866:
						if(itemField.get("slug").toString().equals("field0")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getField45(); }
						break;
					case 2867:
						if(itemField.get("slug").toString().equals("field0")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getField45(); }
						break;
					case 2868:
						if(itemField.get("slug").toString().equals("field0")) { Compressor = Compressor + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { Compressor = Compressor + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { Compressor = Compressor + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { Compressor = Compressor + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { Compressor = Compressor + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { Compressor = Compressor + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { Compressor = Compressor + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { Compressor = Compressor + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { Compressor = Compressor + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { Compressor = Compressor + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { Compressor = Compressor + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { Compressor = Compressor + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { Compressor = Compressor + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { Compressor = Compressor + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { Compressor = Compressor + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { Compressor = Compressor + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { Compressor = Compressor + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { Compressor = Compressor + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { Compressor = Compressor + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { Compressor = Compressor + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { Compressor = Compressor + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { Compressor = Compressor + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { Compressor = Compressor + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { Compressor = Compressor + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { Compressor = Compressor + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { Compressor = Compressor + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { Compressor = Compressor + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { Compressor = Compressor + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { Compressor = Compressor + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { Compressor = Compressor + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { Compressor = Compressor + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { Compressor = Compressor + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { Compressor = Compressor + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { Compressor = Compressor + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { Compressor = Compressor + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { Compressor = Compressor + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { Compressor = Compressor + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { Compressor = Compressor + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { Compressor = Compressor + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { Compressor = Compressor + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { Compressor = Compressor + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { Compressor = Compressor + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { Compressor = Compressor + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { Compressor = Compressor + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { Compressor = Compressor + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { Compressor = Compressor + obj.getField45(); }
						break;
					case 2869:
						if(itemField.get("slug").toString().equals("field0")) { CompressorDemand = CompressorDemand + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { CompressorDemand = CompressorDemand + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { CompressorDemand = CompressorDemand + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { CompressorDemand = CompressorDemand + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { CompressorDemand = CompressorDemand + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { CompressorDemand = CompressorDemand + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { CompressorDemand = CompressorDemand + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { CompressorDemand = CompressorDemand + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { CompressorDemand = CompressorDemand + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { CompressorDemand = CompressorDemand + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { CompressorDemand = CompressorDemand + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { CompressorDemand = CompressorDemand + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { CompressorDemand = CompressorDemand + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { CompressorDemand = CompressorDemand + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { CompressorDemand = CompressorDemand + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { CompressorDemand = CompressorDemand + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { CompressorDemand = CompressorDemand + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { CompressorDemand = CompressorDemand + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { CompressorDemand = CompressorDemand + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { CompressorDemand = CompressorDemand + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { CompressorDemand = CompressorDemand + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { CompressorDemand = CompressorDemand + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { CompressorDemand = CompressorDemand + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { CompressorDemand = CompressorDemand + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { CompressorDemand = CompressorDemand + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { CompressorDemand = CompressorDemand + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { CompressorDemand = CompressorDemand + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { CompressorDemand = CompressorDemand + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { CompressorDemand = CompressorDemand + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { CompressorDemand = CompressorDemand + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { CompressorDemand = CompressorDemand + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { CompressorDemand = CompressorDemand + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { CompressorDemand = CompressorDemand + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { CompressorDemand = CompressorDemand + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { CompressorDemand = CompressorDemand + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { CompressorDemand = CompressorDemand + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { CompressorDemand = CompressorDemand + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { CompressorDemand = CompressorDemand + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { CompressorDemand = CompressorDemand + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { CompressorDemand = CompressorDemand + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { CompressorDemand = CompressorDemand + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { CompressorDemand = CompressorDemand + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { CompressorDemand = CompressorDemand + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { CompressorDemand = CompressorDemand + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { CompressorDemand = CompressorDemand + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { CompressorDemand = CompressorDemand + obj.getField45(); }
						break;
					case 2870:
						if(itemField.get("slug").toString().equals("field0")) { TonHVACUnit = TonHVACUnit + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { TonHVACUnit = TonHVACUnit + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { TonHVACUnit = TonHVACUnit + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { TonHVACUnit = TonHVACUnit + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { TonHVACUnit = TonHVACUnit + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { TonHVACUnit = TonHVACUnit + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { TonHVACUnit = TonHVACUnit + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { TonHVACUnit = TonHVACUnit + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { TonHVACUnit = TonHVACUnit + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { TonHVACUnit = TonHVACUnit + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { TonHVACUnit = TonHVACUnit + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { TonHVACUnit = TonHVACUnit + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { TonHVACUnit = TonHVACUnit + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { TonHVACUnit = TonHVACUnit + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { TonHVACUnit = TonHVACUnit + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { TonHVACUnit = TonHVACUnit + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { TonHVACUnit = TonHVACUnit + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { TonHVACUnit = TonHVACUnit + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { TonHVACUnit = TonHVACUnit + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { TonHVACUnit = TonHVACUnit + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { TonHVACUnit = TonHVACUnit + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { TonHVACUnit = TonHVACUnit + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { TonHVACUnit = TonHVACUnit + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { TonHVACUnit = TonHVACUnit + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { TonHVACUnit = TonHVACUnit + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { TonHVACUnit = TonHVACUnit + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { TonHVACUnit = TonHVACUnit + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { TonHVACUnit = TonHVACUnit + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { TonHVACUnit = TonHVACUnit + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { TonHVACUnit = TonHVACUnit + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { TonHVACUnit = TonHVACUnit + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { TonHVACUnit = TonHVACUnit + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { TonHVACUnit = TonHVACUnit + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { TonHVACUnit = TonHVACUnit + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { TonHVACUnit = TonHVACUnit + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { TonHVACUnit = TonHVACUnit + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { TonHVACUnit = TonHVACUnit + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { TonHVACUnit = TonHVACUnit + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { TonHVACUnit = TonHVACUnit + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { TonHVACUnit = TonHVACUnit + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { TonHVACUnit = TonHVACUnit + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { TonHVACUnit = TonHVACUnit + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { TonHVACUnit = TonHVACUnit + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { TonHVACUnit = TonHVACUnit + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { TonHVACUnit = TonHVACUnit + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { TonHVACUnit = TonHVACUnit + obj.getField45(); }
						break;
					case 2871:
						if(itemField.get("slug").toString().equals("field0")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getField45(); }
						break;
					case 2872:
						if(itemField.get("slug").toString().equals("field0")) { HVACLoads = HVACLoads + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { HVACLoads = HVACLoads + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { HVACLoads = HVACLoads + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { HVACLoads = HVACLoads + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { HVACLoads = HVACLoads + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { HVACLoads = HVACLoads + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { HVACLoads = HVACLoads + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { HVACLoads = HVACLoads + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { HVACLoads = HVACLoads + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { HVACLoads = HVACLoads + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { HVACLoads = HVACLoads + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { HVACLoads = HVACLoads + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { HVACLoads = HVACLoads + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { HVACLoads = HVACLoads + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { HVACLoads = HVACLoads + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { HVACLoads = HVACLoads + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { HVACLoads = HVACLoads + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { HVACLoads = HVACLoads + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { HVACLoads = HVACLoads + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { HVACLoads = HVACLoads + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { HVACLoads = HVACLoads + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { HVACLoads = HVACLoads + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { HVACLoads = HVACLoads + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { HVACLoads = HVACLoads + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { HVACLoads = HVACLoads + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { HVACLoads = HVACLoads + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { HVACLoads = HVACLoads + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { HVACLoads = HVACLoads + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { HVACLoads = HVACLoads + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { HVACLoads = HVACLoads + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { HVACLoads = HVACLoads + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { HVACLoads = HVACLoads + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { HVACLoads = HVACLoads + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { HVACLoads = HVACLoads + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { HVACLoads = HVACLoads + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { HVACLoads = HVACLoads + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { HVACLoads = HVACLoads + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { HVACLoads = HVACLoads + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { HVACLoads = HVACLoads + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { HVACLoads = HVACLoads + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { HVACLoads = HVACLoads + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { HVACLoads = HVACLoads + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { HVACLoads = HVACLoads + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { HVACLoads = HVACLoads + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { HVACLoads = HVACLoads + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { HVACLoads = HVACLoads + obj.getField45(); }
						break;
					case 2873:
						if(itemField.get("slug").toString().equals("field0")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField0(); }
						if(itemField.get("slug").toString().equals("field1")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField1(); }
						if(itemField.get("slug").toString().equals("field2")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField2(); }
						if(itemField.get("slug").toString().equals("field3")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField3(); }
						if(itemField.get("slug").toString().equals("field4")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField4(); }
						if(itemField.get("slug").toString().equals("field5")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField5(); }
						if(itemField.get("slug").toString().equals("field6")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField6(); }
						if(itemField.get("slug").toString().equals("field7")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField7(); }
						if(itemField.get("slug").toString().equals("field8")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField8(); }
						if(itemField.get("slug").toString().equals("field9")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField9(); }
						if(itemField.get("slug").toString().equals("field10")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField10(); }
						if(itemField.get("slug").toString().equals("field11")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField11(); }
						if(itemField.get("slug").toString().equals("field12")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField12(); }
						if(itemField.get("slug").toString().equals("field13")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField13(); }
						if(itemField.get("slug").toString().equals("field14")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField14(); }
						if(itemField.get("slug").toString().equals("field15")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField15(); }
						if(itemField.get("slug").toString().equals("field16")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField16(); }
						if(itemField.get("slug").toString().equals("field17")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField17(); }
						if(itemField.get("slug").toString().equals("field18")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField18(); }
						if(itemField.get("slug").toString().equals("field19")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField19(); }
						if(itemField.get("slug").toString().equals("field20")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField20(); }
						if(itemField.get("slug").toString().equals("field21")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField21(); }
						if(itemField.get("slug").toString().equals("field22")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField22(); }
						if(itemField.get("slug").toString().equals("field23")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField23(); }
						if(itemField.get("slug").toString().equals("field24")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField24(); }
						if(itemField.get("slug").toString().equals("field25")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField25(); }
						if(itemField.get("slug").toString().equals("field26")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField26(); }
						if(itemField.get("slug").toString().equals("field27")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField27(); }
						if(itemField.get("slug").toString().equals("field28")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField28(); }
						if(itemField.get("slug").toString().equals("field29")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField29(); }
						if(itemField.get("slug").toString().equals("field30")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField30(); }
						if(itemField.get("slug").toString().equals("field31")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField31(); }
						if(itemField.get("slug").toString().equals("field32")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField32(); }
						if(itemField.get("slug").toString().equals("field33")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField33(); }
						if(itemField.get("slug").toString().equals("field34")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField34(); }
						if(itemField.get("slug").toString().equals("field35")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField35(); }
						if(itemField.get("slug").toString().equals("field36")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField36(); }
						if(itemField.get("slug").toString().equals("field37")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField37(); }
						if(itemField.get("slug").toString().equals("field38")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField38(); }
						if(itemField.get("slug").toString().equals("field39")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField39(); }
						if(itemField.get("slug").toString().equals("field40")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField40(); }
						if(itemField.get("slug").toString().equals("field41")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField41(); }
						if(itemField.get("slug").toString().equals("field42")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField42(); }
						if(itemField.get("slug").toString().equals("field43")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField43(); }
						if(itemField.get("slug").toString().equals("field44")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField44(); }
						if(itemField.get("slug").toString().equals("field45")) { HVACLoadsDemand = HVACLoadsDemand + obj.getField45(); }
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
				virtualMeterEntity.setField2(warehouseLights);
				virtualMeterEntity.setField3(WarehouseLightsDemand);
				virtualMeterEntity.setField4(OfficeLights);
				virtualMeterEntity.setField5(OfficeLightsDemand);
				virtualMeterEntity.setField6(ServerRoom);
				virtualMeterEntity.setField7(ServerRoomDemand);
				virtualMeterEntity.setField8(ServerRoomReceptacles);
				virtualMeterEntity.setField9(ServerRoomReceptaclesDemand);
				virtualMeterEntity.setField10(ReceptaclesPannel1A);
				virtualMeterEntity.setField11(ReceptaclesPannel1ADemand);
				virtualMeterEntity.setField12(ReceptaclesPannel2A);
				virtualMeterEntity.setField13(ReceptaclesPannel2ADemand);
				virtualMeterEntity.setField14(ReceptaclesPannel2B);
				virtualMeterEntity.setField15(ReceptaclesPannel2BDemand);
				virtualMeterEntity.setField16(Compressor);
				virtualMeterEntity.setField17(CompressorDemand);
				virtualMeterEntity.setField18(TonHVACUnit);
				virtualMeterEntity.setField19(TonHVACUnitDemand);
				virtualMeterEntity.setField20(HVACLoads);
				virtualMeterEntity.setField21(HVACLoadsDemand);
				
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
