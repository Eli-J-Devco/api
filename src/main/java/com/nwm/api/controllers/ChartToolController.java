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

import com.nwm.api.entities.ChartToolEntity;
import com.nwm.api.services.ChartToolService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;
import java.util.List;

@RestController
@ApiIgnore
@RequestMapping("/chart-tool")
public class ChartToolController extends BaseController {
	
	 /* @description Get customer view chart data
	 * @author long.pham
	 * @since 2021-05-12
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/view-data")
	public Object getChartInverterPerformance(@RequestBody ChartToolEntity obj) {
		try {
			ChartToolService service = new ChartToolService();
			List data = service.getChartData(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
