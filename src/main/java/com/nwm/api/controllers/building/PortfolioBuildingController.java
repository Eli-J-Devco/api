/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers.building;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.controllers.BaseController;
import com.nwm.api.entities.building.PortfolioBuildingEntity;
import com.nwm.api.services.building.PortfolioBuildingService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("building-portfolio")
public class PortfolioBuildingController extends BaseController {
	
	/**
	 * @description Get list data for portfolio
	 * @author duy.phan 
	 * @since 2025-11-20
	 * @param array id_site
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody PortfolioBuildingEntity obj) {
		try {
			
			PortfolioBuildingService service = new PortfolioBuildingService();
			List data = service.getList(obj);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list site by employee
	 * @author long.pham
	 * @since 2021-12-27
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-site-by-customer")
	public Object getListSiteByEmployee(@RequestBody PortfolioBuildingEntity obj, @RequestHeader(name = "Authorization") String authz) {
		try {
			obj.setId_sites(Lib.sitesManagedByUser(authz).stream().map(item -> {
				Map<String, Integer> map = new HashMap<>();
				map.put("id", item);
				return map;
			}).collect(Collectors.toList()));
			PortfolioBuildingService service = new PortfolioBuildingService();
			List data = service.getListSiteByEmployee(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
