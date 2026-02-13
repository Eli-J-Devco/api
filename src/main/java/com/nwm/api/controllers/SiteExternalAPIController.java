/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.services.ApiAccessService;
import com.nwm.api.services.SiteExternalAPIService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/site/external")
@Tag(name = "Site External API", description = "External API for site information")
public class SiteExternalAPIController extends BaseController {

	@Autowired
	private SiteExternalAPIService service;

	/**
	 * @description Get site information for external API
	 * @author Duc.Pham
	 * @since 2025-02-13
	 * @return data (status, message, array, total_row)
	 */
	@Operation(summary = "Get site information", description = "Retrieve site information using API key authentication")
	@GetMapping("/get-site")
	public Object getSite(@RequestHeader(name = "X-NWM-API-KEY", required = true) String key, HttpServletRequest request) {
		try {
			String errMsg = checkKey(key, request);
			if (!Lib.isBlank(errMsg)) {
				return this.thirdPartyJsonResult(false, errMsg, null, 0);
			}
			List dataList = service.getSite(key, request);
			return this.thirdPartyJsonResult(true, Constants.GET_SUCCESS_MSG, dataList, dataList.size());
		} catch (Exception e) {
			return this.thirdPartyJsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}

	/**
	 * @description validate user security key (reused logic from ThirdPartyAPIController)
	 * @param key API security key
	 * @param request HTTP request
	 * @return Error message if invalid, null if valid
	 */
	private String checkKey(String key, HttpServletRequest request) {
		try {
			if (Lib.isBlank(key)) {
				return "Key is required.";
			}
			ApiAccessService apiAccessService = new ApiAccessService();
			if (!apiAccessService.validateApiKey(key)) {
				return "Key is invalid.";
			}
			String endpoint = request.getRequestURI().substring(request.getContextPath().length());
			String method = request.getMethod();
			if (!service.checkUserCanAccessEndPoint(key, endpoint, method)) {
				return "Can not access this endpoint";
			}
			if (!service.checkRateLimit(key)) {
				return "Rate limit is full this month";
			}
			return null;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
