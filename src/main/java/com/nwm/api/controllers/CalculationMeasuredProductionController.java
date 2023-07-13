/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.CalculationMeasuredProductionEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelVirtualMeterOrInverterEntity;
import com.nwm.api.entities.VirtualDeviceEntity;
import com.nwm.api.services.CalculationMeasureProductionService;
import com.nwm.api.services.VirtualDeviceService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/calculations")
public class CalculationMeasuredProductionController extends BaseController {

	/**
	 * @description Import table virtual device
	 * @author long.pham
	 * @since 2023-06-16
	 * @return {}
	 */
	@GetMapping("/measured-production-device")
	@ResponseBody
	public Object renderCalculationMeasuredProduction(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			
			String token = (String) params.get("token");
			if(token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			int day = 1;
			String totalDay = (String) params.get("day");
			if(totalDay != null && Integer.parseInt(totalDay) > 0 ) {
				day = Integer.parseInt(totalDay);
			}
		    
			String idSite = (String) params.get("id_site");
			int id_site = 0;
			
			if(idSite != null && Integer.parseInt(idSite) > 0 ) {
				id_site = Integer.parseInt(idSite);
			}
			
			CalculationMeasuredProductionEntity entity = new CalculationMeasuredProductionEntity();
			entity.setId_site(id_site);
			entity.setTotalDay(day);
		    
			CalculationMeasureProductionService service = new CalculationMeasureProductionService();
			List<?> listDevices = service.getListDevice(entity);
			
			if (listDevices == null || listDevices.size() == 0) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

			for (int i = 0; i < listDevices.size(); i++) {
				CalculationMeasuredProductionEntity deviceItem = (CalculationMeasuredProductionEntity) listDevices.get(i);
				if(day <= 60) {
					deviceItem.setDatatablename(deviceItem.getView_tablename());
				}
				
				Date now = new Date();
				TimeZone.setDefault(TimeZone.getTimeZone(deviceItem.getTime_zone_value()));
				SimpleDateFormat dateFormatCurrent = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				Calendar calCurrent = Calendar.getInstance();
				calCurrent.setTime(dateFormatCurrent.parse(dateFormatCurrent.format(now)));
				calCurrent.add(Calendar.DATE, -day);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				Date currentDate = calCurrent.getTime();
				
				for(int t = 0; t <= day; t++) {
					cal.setTime(currentDate);
					cal.add(Calendar.DATE, t);
					System.out.println("Calculation Measured productuon: "+ dateFormat.format(cal.getTime()) + " 00:00:00");
					deviceItem.setStart_date(dateFormat.format(cal.getTime()) + " 00:00:00");
					deviceItem.setEnd_date(dateFormat.format(cal.getTime()) + " 23:59:59");
					service.updateMeasuredProduction(deviceItem);
				}
			}

			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
