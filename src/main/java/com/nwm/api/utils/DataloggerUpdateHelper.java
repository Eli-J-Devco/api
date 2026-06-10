/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 * 
 *********************************************************/
package com.nwm.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelDataloggerEntity;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.ModelDataloggerService;

/**
 * DataloggerUpdateHelper - Helper class to handle datalogger information updates
 * @author Duc.pham
 * @since 2025-01-01
 */
public class DataloggerUpdateHelper {

	/**
	 * Update datalogger information with provided parameters
	 * @param dataloggerItem The device entity representing the datalogger
	 * @param serialNumber Device serial number
	 * @param loopName Loop name configuration
	 * @param modbusIp Modbus IP address
	 * @param modbusPort Modbus port
	 * @param modbusDevice Modbus device ID
	 * @param modbusDeviceName Modbus device name
	 * @param modbusDeviceType Modbus device type
	 * @param modbusDeviceTypeNumber Modbus device type number
	 * @param modbusDeviceClass Modbus device class
	 */
	public static void updateDataloggerInfo(
			DeviceEntity dataloggerItem,
			String serialNumber,
			String loopName,
			String modbusIp,
			String modbusPort,
			String modbusDevice,
			String modbusDeviceName,
			String modbusDeviceType,
			String modbusDeviceTypeNumber,
			String modbusDeviceClass) {
		
		if (dataloggerItem == null || dataloggerItem.getId() <= 0) {
			System.err.println("ERROR - Datalogger item is null or invalid");
			return;
		}
		
		try {
			// Set last update for datalogger 
			Date now = new Date();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
			String currentDate = format.format(now);
			
			// Update device last_updated timestamp
			DeviceEntity deviceUpdateE = new DeviceEntity();
			deviceUpdateE.setLast_updated(currentDate);
			deviceUpdateE.setLast_value(null);
			deviceUpdateE.setId(dataloggerItem.getId());
			
			DeviceService serviceD = new DeviceService();
			serviceD.updateLastUpdated(deviceUpdateE);
			
			System.out.println("DEBUG - Device last_updated set to: " + currentDate);
			
			// Save to datalogger table
			ModelDataloggerEntity dataloggerEntity = new ModelDataloggerEntity();
			dataloggerEntity.setId_device(dataloggerItem.getId());
			dataloggerEntity.setDatatablename(dataloggerItem.getDatatablename());
			dataloggerEntity.setView_tablename(dataloggerItem.getView_tablename());
			dataloggerEntity.setJob_tablename(dataloggerItem.getJob_tablename());
			
			String sDateUTC = format.format(now);
			dataloggerEntity.setTime(sDateUTC);
			dataloggerEntity.setSerialnumber(serialNumber);
			dataloggerEntity.setLoopname(loopName);
			dataloggerEntity.setModbusip(modbusIp);
			dataloggerEntity.setModbusport(modbusPort);
			dataloggerEntity.setModbusdevice(modbusDevice);
			dataloggerEntity.setModbusdevicename(modbusDeviceName);
			dataloggerEntity.setModbusdevicetype(modbusDeviceType);
			dataloggerEntity.setModbusdevicetypenumber(modbusDeviceTypeNumber);
			dataloggerEntity.setModbusdeviceclass(modbusDeviceClass);
			
			ModelDataloggerService dataloggerService = new ModelDataloggerService();
			dataloggerService.insertModelDatalogger(dataloggerEntity);
			
			System.out.println("DEBUG - Datalogger information saved successfully for serial: " + serialNumber);
			
		} catch (Exception e) {
			System.err.println("ERROR - Failed to update datalogger information: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
