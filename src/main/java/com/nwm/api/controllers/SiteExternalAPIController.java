/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

import com.nwm.api.services.ThirdPartyAPIService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nwm.api.services.ApiAccessService;
import com.nwm.api.services.SiteExternalAPIService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/site/external")
@Tag(name = "Sites", description = "External API for site information")
@Validated
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
	public Object getSite(
            @Parameter(description = "Offset for pagination (default is 0)")
            @RequestParam(required = false) @Min(0) Integer offset,
            @RequestHeader(name = "X-NWM-API-KEY", required = true) String key,
            HttpServletRequest request) {
		try {
            ThirdPartyAPIService thirdPartyAPIService = new ThirdPartyAPIService();
			String errMsg = thirdPartyAPIService.checkKey(key, request);
			if (!Lib.isBlank(errMsg)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(this.thirdPartyJsonResult(false, errMsg, null, 0));
			}
            final int realOffset = (offset != null && offset >= 0) ? offset : 0;
            int count = service.getSiteCount(key, request);
            if (realOffset >= count) {
                return this.thirdPartyJsonResult(false, "No data at offset " + realOffset + " max offset is " + (count - 1), new ArrayList(), count);
            }
			List dataList = service.getSite(key, realOffset, request);
			return this.thirdPartyJsonResult(true, Constants.GET_SUCCESS_MSG, dataList, count);
		} catch (Exception e) {
			return this.thirdPartyJsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
}
