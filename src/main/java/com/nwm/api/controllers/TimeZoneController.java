/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BatchJobTableEntity;
import com.nwm.api.entities.DateFormatEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.TimeZoneEntity;
import com.nwm.api.services.CronJobAlertService;
import com.nwm.api.services.TimeZoneService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/time-zone")
public class TimeZoneController extends BaseController {

	/**
	 * @description Get list time zone
	 * @author long.pham
	 * @since 2020-10-30
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody TimeZoneEntity obj) {
		try {
			TimeZoneService service = new TimeZoneService();
			List data = service.getList(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list date format
	 * @author long.pham
	 * @since 2023-04-17
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/dropdownlist-date-format")
	public Object getListDateFormat(@RequestBody DateFormatEntity obj) {
		try {
			TimeZoneService service = new TimeZoneService();
			List data = service.getListDateFormat(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description set auto update time zone value
	 * @author long.pham
	 * @since 2023-11-07
	 * @return {}
	 */
	
	@GetMapping("/auto-updated-timezone")
	@ResponseBody
	public Object renderAutoUpdateTimeZone(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			String token = (String) params.get("token");
			if (token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			
			TimeZoneEntity obj = new TimeZoneEntity();
			TimeZoneService service = new TimeZoneService();
			List data = service.getList(obj);
			
			if (data.size() > 0) {
				for (int s = 0; s < data.size(); s++) {
					TimeZoneEntity item = (TimeZoneEntity) data.get(s);
					LocalDateTime now = LocalDateTime.now();
					ZoneId zone = ZoneId.of(item.getValue());
					ZoneOffset zoneOffSet = zone.getRules().getOffset(now);
					String timeZoneOffset = "+00:00";
					
					if(zoneOffSet.toString() != "Z") {
						timeZoneOffset = zoneOffSet.toString();
					}
					
					item.setOffset(timeZoneOffset);
					service.updateTimeZoneOffset(item);
				}
			}

			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
