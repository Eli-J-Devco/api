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
	 * @description get Scada devices list by site
	 * @author Hung.Bui
	 * @since 2024-03-26
	 * @param id_site
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
	 * @param id_site
	 * @param modbusdevicenumber
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
}
