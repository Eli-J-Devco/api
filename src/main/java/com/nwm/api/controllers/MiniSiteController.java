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
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.MiniSiteService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/minisite")
public class MiniSiteController extends BaseController {

	/**
	 * @description Get mini site information
	 * @author long.pham
	 * @since 2020-11-02
	 * @param id_site
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/info")
	public Object getSummarySiteByCustomerId(@RequestBody SiteEntity obj) {
		try {
			MiniSiteService service = new MiniSiteService();
			Object getMiniSite = service.getMiniSiteInfo(obj);
			if (getMiniSite != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getMiniSite, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get chart inverter performance by site
	 * @author long.pham
	 * @since 2020-11-02
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-chart-minisite-performance")
	public Object getChartInverterPerformance(@RequestBody SiteEntity obj) {
		try {

			MiniSiteService service = new MiniSiteService();
			SiteEntity dataObj = (SiteEntity) service.getChartPerformance(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
}
