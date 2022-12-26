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

import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.CustomerViewService;
import com.nwm.api.services.SitesAlertService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@ApiIgnore
@RequestMapping("/customer-view")
public class CustomerViewController extends BaseController {

	/**
	 * @description Get mini site information
	 * @author long.pham
	 * @since 2020-12-02
	 * @param id_site, id_customer
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/get-customer-view-info")
	public Object getCustomerViewInfo(@RequestBody SiteEntity obj) {
		try {
			CustomerViewService service = new CustomerViewService();
			Object dataObj = service.getCustomerViewInfo(obj);
			if (dataObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get customer view chart data
	 * @author long.pham
	 * @since 2020-12-04
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-chart-data")
	public Object getChartDataPerformance(@RequestBody SiteEntity obj) {
		try {
			CustomerViewService service = new CustomerViewService();
			List dataEnergy = service.getChartDataPerformance(obj);
			obj.setEnergy(dataEnergy);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	

	/**
	 * @description Get customer view chart data
	 * @author long.pham
	 * @since 2020-12-04
	 * @param id
	 * @return data (status, message, array, total_row
	 */
//	@PostMapping("/get-chart-data-performance")
//	public Object getChartInverterPerformance(@RequestBody SiteEntity obj) {
//		
//		
//		
//		try {
//			CustomerViewService service = new CustomerViewService();
//			String filterBy = obj.getFilterBy();
//			List dataEnergy = service.getChartDataEnergy(obj);
//			obj.setEnergy(dataEnergy);
//			
////			double totalRecord = service.getTotalGeneration(obj);
////			obj.setTotalGeneration(totalRecord);
//			
////			switch (filterBy) {
////			case "today":
////				
////				break;
////			case "last_month":
////			case "this_month":
////				List dataThisMonthEnergy = service.getChartDataEnergy(obj);
////				obj.setEnergy(dataThisMonthEnergy);
////				break;
////			case "12_month":
////				List data12MonthEnergy = service.getChartDataEnergy(obj);
////				obj.setEnergy(data12MonthEnergy);
////				break;
////			case "lifetime":
////				  List dataLifetimeEnergy = service.getChartDataEnergy(obj);
////				obj.setEnergy(dataLifetimeEnergy);
////				break;
////			}
//
//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1);
//		} catch (Exception e) {
//			log.error(e);
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @description Get list site by id_customer
	 * @author long.pham
	 * @since 2021-03-09
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-site-by-customer")
	public Object getList(@RequestBody SiteEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			CustomerViewService service = new CustomerViewService();
			List data = service.getList(obj);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	
	
	/**
	 * @description Get list alert by site
	 * @author long.pham
	 * @since 2021-03-09
	 * @param id_customer, id_site, start_date, end_date
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/list-alert-by-site")
    public Object getList(@RequestBody AlertEntity obj){
		try {
			if(obj.getLimit() == 0) {
				obj.setLimit(1000);
			}
			
			CustomerViewService service = new CustomerViewService();
			List data = service.getListAlertBySite(obj);
			List newData = new ArrayList();
			if(data.size() > 0 ) {
				for(int i = 0; i < data.size(); i++) {
					Map<String, Object> siteItem = (Map<String, Object>) data.get(i);
					siteItem.put("hash_id", secretCard.encrypt( siteItem.get("id_site").toString()).toLowerCase() );
					newData.add(siteItem);
				}
			}
			
			int totalRecord = service.getAlertCustomerViewTotalCount(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, newData, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
    }
	
	
	
	/**
	 * @description Get count notification alert
	 * @author long.pham
	 * @since 2021-03-10
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/count-nitification-alert")
    public Object countNotificationAlert(@RequestBody AlertEntity obj){
		try {
			if(obj.getId_customer()<= 0) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

			CustomerViewService service = new CustomerViewService();
			int countNotificationAlert = service.countNotificationAlert(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, countNotificationAlert);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
    }
	
	/**
	 * @description Get summary 
	 * @author long.pham
	 * @since 2020-11-24
	 * @param {}
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/alert-summary")
	public Object getAlertSummary(@RequestBody AlertEntity obj) {
		try {
			CustomerViewService service = new CustomerViewService();
			Object detailObj = service.getAlertSummary(obj);
			if (detailObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detailObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	

}
