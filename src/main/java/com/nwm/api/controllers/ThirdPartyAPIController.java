/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nwm.api.utils.Lib;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nwm.api.entities.ThirdPartyAPIEntity;
import com.nwm.api.services.ThirdPartyAPIService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@ApiIgnore
@RequestMapping("/3rd-party")
@Tag(name = "Metrics")
public class ThirdPartyAPIController extends BaseController {
	@Autowired
	private ThirdPartyAPIService service;

	/**
	 * @description get energy generation whole sites in portfolio for 3rd party
	 * @author Hung.Bui
	 * @since 2024-05-02
	 * @param params { start_date, end_date }
	 * @return data (status, message, array, total_row)
	 */
	@GetMapping("/energy-generation")
	public Object getEnergyGeneration(
			@RequestHeader(name = "X-NWM-API-KEY", required = false) String key,
			ThirdPartyAPIEntity params,
			HttpServletRequest request
	) {
		try {
			if(key == null || key == "") return this.thirdPartyJsonResult(false, "Key is required.", null, 0);
			
			/**
			 * validate start/end date:
			 * - Start/end date are optional, but if provided, both start and end date are required.
			 * - If start and end date are not provided, data range time will be last 3 days.
			 */
			try {
				if ((params.getStart_date() != null && params.getEnd_date() == null) || (params.getStart_date() == null && params.getEnd_date() != null)) {
					throw new DateTimeException("Both start and end date are required.");
				} else if (params.getStart_date() != null && params.getEnd_date() != null) {
					DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate startLocalDateTime = LocalDate.parse(params.getStart_date(), dateTimeFormatter);
					LocalDate endLocalDateTime = LocalDate.parse(params.getEnd_date(), dateTimeFormatter);
					if (endLocalDateTime.isBefore(startLocalDateTime)) throw new DateTimeException("End date must be same or after start date.");
				}
			} catch (DateTimeParseException e) {
				return this.thirdPartyJsonResult(false, "Invalid start/end date. It's must be in format of YYYY-MM-DD (Ex: 2020-12-31).", null, 0);
			} catch (DateTimeException e) {
				return this.thirdPartyJsonResult(false, e.getMessage(), null, 0);
			}
			
			List dataList = service.getEnergyGeneration(key, request, params);
			
			return this.thirdPartyJsonResult(true, Constants.GET_SUCCESS_MSG, dataList, dataList.size());

		} catch (Exception e) {
			return this.thirdPartyJsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
	/**
	 * @description get device data for 3rd party
	 * @author Hung.Bui
	 * @since 2025-02-20
	 * @param params { start_date, end_date, device_id, data_type, interval }
	 * @return data (status, message, array, total_row)
	 */
	@GetMapping("/device-data")
	public Object getDeviceData(
			@RequestHeader(name = "X-NWM-API-KEY", required = false) String key,
			ThirdPartyAPIEntity params,
            @ApiParam(value = "Page number (optional)")
            @RequestParam(required = false, defaultValue = "1") Integer page,
			HttpServletRequest request
	) {
		try {
            String errMsg = service.checkKey(key, request);
            if (!Lib.isBlank(errMsg)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(this.thirdPartyJsonResult(false, errMsg, null, 0));
            }
            Map<String, Object> data = service.checkSiteDisabled(key, params);
            if (data == null) {
                return this.thirdPartyJsonResult(false, "Device not belong to site", null, 0);
            }
            if ((Integer) data.get("status") <= 0) {
                return this.thirdPartyJsonResult(false, "Site is disabled", null, 0);
            }
			/**
			 *  input validation
			 */
			try {
				if (params.getStart_date() == null || params.getEnd_date() == null || params.getDevice_id() == null || params.getData_type() == null || params.getInterval() == null)
					throw new IllegalArgumentException("Start date/end date/device id/data type/interval are required.");
				if (Arrays.asList(params.getDevice_id().split(",")).size() > 1) throw new IllegalArgumentException("Allow only one device_id.");
				if (Arrays.asList(params.getData_type().split(",")).size() > 2) throw new IllegalArgumentException("Allow only two data_type (params).");
				if (!params.getInterval().replaceAll("\\s+","").equals("15min")) throw new IllegalArgumentException("Allow only 15min interval.");
				
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate startLocalDateTime = LocalDate.parse(params.getStart_date(), dateTimeFormatter);
				LocalDate endLocalDateTime = LocalDate.parse(params.getEnd_date(), dateTimeFormatter);
				if (endLocalDateTime.isBefore(startLocalDateTime)) throw new DateTimeException("End date must be same or after start date.");
				
				Arrays.stream(params.getDevice_id().split(",")).mapToInt(Integer::parseInt);
			} catch (NumberFormatException e) {
				return this.thirdPartyJsonResult(false, "Invalid device id.", null, 0);
			} catch (IllegalArgumentException e) {
				return this.thirdPartyJsonResult(false, e.getMessage(), null, 0);
			} catch (DateTimeParseException e) {
				return this.thirdPartyJsonResult(false, "Invalid start/end date. It's must be in format of YYYY-MM-DD (Ex: 2020-12-31).", null, 0);
			} catch (DateTimeException e) {
				return this.thirdPartyJsonResult(false, e.getMessage(), null, 0);
			}
			/**
			 * 
			 */
			
			List dataList = service.getDeviceData(key, request, params, page);
			
			return this.thirdPartyJsonResult(true, Constants.GET_SUCCESS_MSG, dataList, dataList.size());
		} catch (Exception e) {
			return this.thirdPartyJsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}

    @GetMapping("/device-info")
    public Object getDeviceInfoBySite(@RequestHeader(name = "X-NWM-API-KEY", required = true) String key, HttpServletRequest request) {
        try {
            String errMsg = service.checkKey(key, request);
            if (!Lib.isBlank(errMsg)) {
            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(this.thirdPartyJsonResult(false, errMsg, null, 0));
            }
            List dataList = service.getDeviceInfoBySite(key, request);
            return this.thirdPartyJsonResult(true, Constants.GET_SUCCESS_MSG, dataList, dataList.size());
        } catch (Exception e) {
            return this.thirdPartyJsonResult(false, Constants.GET_ERROR_MSG, null, 0);
        }
    }
}
