/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.EmployeeFilterFavoritesEntity;
import com.nwm.api.entities.EmployeeFilterRecentlyEntity;
import com.nwm.api.services.SitesAnalyticsService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/sites-analytics")
public class SitesAnalyticsController extends BaseController {
	
	/**
	 * @description Get list device by site
	 * @author long.pham
	 * @since 2022-02-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-device-by-site")
	public Object getListDeviceBySite(@RequestBody DeviceEntity obj) {
		try {
			SitesAnalyticsService service = new SitesAnalyticsService();
			List data = service.getListDeviceBySite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list device parameter
	 * @author long.pham
	 * @since 2022-02-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/chart-parameter-device")
	public Object getChartParameterDevice(@RequestBody DeviceEntity obj) {
		try {
			SitesAnalyticsService service = new SitesAnalyticsService();
			List data = service.getChartParameterDevice(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description Get save name filter
	 * @author long.pham
	 * @since 2022-04-04
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	
	@PostMapping("/save-filter-favorites")
	public Object EmployeeFilterFavorites(@Valid @RequestBody EmployeeFilterFavoritesEntity obj) {
		try {
			SitesAnalyticsService service = new SitesAnalyticsService();
			EmployeeFilterFavoritesEntity data = service.saveEmployeeFilterFavorites(obj);
			if (data != null) {
				
				return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
			} else {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
	/**
	 * @description Get save name filter
	 * @author long.pham
	 * @since 2022-04-04
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	
	@PostMapping("/save-recently-used-filter")
	public Object saveRecentlyUsedFilter(@Valid @RequestBody EmployeeFilterRecentlyEntity obj) {
		try {
			SitesAnalyticsService service = new SitesAnalyticsService();
			EmployeeFilterRecentlyEntity data = service.saveRecentlyUsedFilter(obj);
			
			if (data != null) {
				return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
			} else {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list employee filter charting
	 * @author long.pham
	 * @since 2022-04-04
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-filter-charting-by-employee")
	public Object getListEmployeeFilter(@RequestBody EmployeeFilterFavoritesEntity obj) {
		try {
			SitesAnalyticsService service = new SitesAnalyticsService();
			List data = service.getListEmployeeFilter(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list device by site
	 * @author long.pham
	 * @since 2022-02-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-recently-filter")
	public Object getListRecently(@RequestBody EmployeeFilterRecentlyEntity obj) {
		try {
			SitesAnalyticsService service = new SitesAnalyticsService();
			List data = service.getListRecently(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description Get list device by site
	 * @author long.pham
	 * @since 2022-05-03
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-favorites-filter")
	public Object getListFavorites(@RequestBody EmployeeFilterFavoritesEntity obj) {
		try {
			SitesAnalyticsService service = new SitesAnalyticsService();
			List data = service.getListFavorites(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
}
