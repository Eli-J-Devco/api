/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.services.SiteConfigService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;


@RestController
@ApiIgnore
@RequestMapping("/site-config")
public class SiteConfigController extends BaseController {

	
	/**
	 * @description save customer
	 * @author long.pham
	 * @since 2021-01-05
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/update")
	public Object updateSiteConfig(@Valid @RequestBody SitesDevicesEntity obj) {
		try {
			SiteConfigService service = new SiteConfigService();
			boolean insert = service.updateSiteConfig(obj);
			if (insert == true) {
				return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
			} else {
				return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description Get list site for page employee manage site
	 * @author long.pham
	 * @since 2021-01-07
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-config")
	public Object getListSiteConfig(@RequestBody SitesDevicesEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			SiteConfigService service = new SiteConfigService();
			List data = service.getListSiteConfig(obj);
			
			List newData = new ArrayList();
			for(int i = 0; i < data.size(); i++) {
				Map<String, Object> siteItem = (Map<String, Object>) data.get(i);
				siteItem.put("hash_id", secretCard.encrypt( siteItem.get("id").toString()).toLowerCase() );
				newData.add(siteItem);
			}
			
			int totalRecord = service.getSiteConfigTotal(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, newData, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
}
