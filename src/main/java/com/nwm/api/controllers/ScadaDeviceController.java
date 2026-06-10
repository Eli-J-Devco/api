/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.ScadaDeviceAlarmEntity;
import com.nwm.api.entities.ScadaDeviceChartDataEntity;
import com.nwm.api.entities.ScadaDeviceEntity;
import com.nwm.api.services.ScadaDeviceService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("scada/device")
public class ScadaDeviceController extends BaseController {

	/**
	 * @description get devices list by site
	 * @author Hung.Bui
	 * @since 2024-03-26
	 * @param obj { id_site }
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-device-by-site")
	public Object getListDeviceBySite(@RequestBody ScadaDeviceEntity obj) {
		try {
			ScadaDeviceService service = new ScadaDeviceService();
			List<ScadaDeviceEntity> data = service.getListDeviceBySite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description get chart data
	 * @author Hung.Bui
	 * @since 2024-04-05
	 * @param obj { id, datatablename, timezone_value, id_filter, start_date, end_date, data_send_time }
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/get-chart-data")
	public Object getChartData(@RequestBody ScadaDeviceEntity obj) {
		try {
			ScadaDeviceService service = new ScadaDeviceService();
			List<ScadaDeviceChartDataEntity> data = service.getChartData(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description get alarms list by device
	 * @author Hung.Bui
	 * @since 2024-04-12
	 * @param obj { hash_id_site, modbusdevicenumber }
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-active-alarm-by-device")
	public Object getActiveAlarmsListByDevice(@RequestBody ScadaDeviceEntity obj) {
		try {
			ScadaDeviceService service = new ScadaDeviceService();
			List<ScadaDeviceAlarmEntity> data = service.getActiveAlarmsListByDevice(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description get device detail
	 * @author Hung.Bui
	 * @since 2024-04-12
	 * @param obj { hash_id_site, modbusdevicenumber }
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/device-detail")
	public Object getDeviceDetail(@RequestBody ScadaDeviceEntity obj) {
		try {
			ScadaDeviceService service = new ScadaDeviceService();
			ScadaDeviceEntity data = service.getDeviceDetail(obj);
			if (data != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
