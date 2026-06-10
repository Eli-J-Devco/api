/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelPiCameraEntity;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.PiCameraService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/pi-camera")
public class PiCameraController extends BaseController {

	/**
	 * @description Get data from PI camera water meter
	 * @author long.pham
	 * @since 2021-01-12
	 * @param ser_number, modbus_port
	 * @return {}
	 */
	@PostMapping("/upload")
	public Object getDataFromPiCamera(@RequestBody ModelPiCameraEntity obj) {
		try {
			if(obj.getSerial_number() != null && obj.getModbus_port() != null) {
				// validate data info
				DeviceEntity device = new DeviceEntity();
				device.setSerial_number(obj.getSerial_number());
				device.setModbusdevicenumber(obj.getModbus_port());
				PiCameraService piService = new PiCameraService();
				DeviceEntity objDevice = piService.getDeviceByModbusAndSerNumber(device);
				if(objDevice.getId() <= 0) { return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0); }
				
				obj.setDatatablename(objDevice.getDatatablename());
				obj.setView_tablename(objDevice.getView_tablename());
				obj.setId_device(objDevice.getId());
				piService.insertModelPiCamraWaterMeter(obj);
				
				
				DeviceEntity deviceUpdateE = new DeviceEntity();
				if(obj.getReading_value() != 0.001 && obj.getReading_value() >= 0){
					deviceUpdateE.setLast_updated(obj.getTime());
				}
				
				deviceUpdateE.setLast_value(obj.getReading_value() != 0.001 ? obj.getReading_value() : null);
				deviceUpdateE.setField_value1(obj.getReading_value() != 0.001 ? obj.getReading_value() : null);
				deviceUpdateE.setField_value2(null);
				deviceUpdateE.setField_value3(null);
				deviceUpdateE.setId(objDevice.getId());
				DeviceService serviceD = new DeviceService();
				serviceD.updateLastUpdated(deviceUpdateE);
				
				return this.jsonResult(false, Constants.GET_ERROR_MSG, obj, 1);
				
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
