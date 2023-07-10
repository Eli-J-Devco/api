/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.services.SiteConfigService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

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
	 * @description update site setting
	 * @author long.pham
	 * @since 2023-04-17
	 * @param  {}
	 */

	@PostMapping("/update-site-setting")
	public Object updateSiteSetting(@Valid @RequestBody SitesDevicesEntity obj) {
		try {
			SiteConfigService service = new SiteConfigService();
			boolean insert = service.updateSiteSetting(obj);
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
	 * @description update pv model setting
	 * @author Hung.Bui
	 * @since 2023-06-26
	 * @param {}
	 * @throws ParseException
	 */

	@PostMapping("/update-pv-model-setting")
	public Object updatePVModelSetting(@Valid @RequestBody SitesDevicesEntity obj) throws ParseException {
		String domainCronJob = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.domainCronJob);
		String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
		int total_day = 10;
		String url = domainCronJob + "/api-server/virtual-device/render-data?token=" + privateKey + "&id_site="+ obj.getId();
		try {
			SiteConfigService service = new SiteConfigService();
			boolean insert = service.updatePVModelSetting(obj);
			if (insert == true) {

				String command = "curl -X GET " + url + "&total_day=" + total_day;
				Runtime.getRuntime().exec(command);
				return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
			} else {
				return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		} finally {
			try {
				DateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date currentDate = new Date();
				Date date1 = null;
				Date date2 = null;
				String startDate = obj.getCommissioning();
				if (startDate != null) {
					String endDate = simpleDateFormat.format(currentDate);
					date1 = simpleDateFormat.parse(startDate);
					date2 = simpleDateFormat.parse(endDate);
					long getDiff = date2.getTime() - date1.getTime();
					long getDaysDiff = getDiff / (24 * 60 * 60 * 1000);
//					total_day = Integer.parseInt(String.valueOf(getDaysDiff));
					total_day = 10;
//					String commandUpdate = "curl -X GET " + url + "&total_day=" + total_day;
//					Runtime.getRuntime().exec(commandUpdate);
				}
			} catch (Exception e) {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
			}
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
	
	/**
	 * @description Get list weather station
	 * @author Hung.Bui
	 * @since 2023-07-07
	 * @return data (status, message, array)
	 */
	@PostMapping("/ws-list-config")
	public Object getWeatherStationListSiteConfig(@RequestBody SitesDevicesEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			SiteConfigService service = new SiteConfigService();
			List data = service.getWeatherStationListSiteConfig(obj);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e);
		}
	}
	
}
