/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nwm.api.entities.*;
import com.nwm.api.services.PortfolioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.services.DashboardService;
import com.nwm.api.services.EmployeeService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

	/**
	 * @description Get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/list")
    public Object getList(@RequestBody AlertEntity obj, @RequestHeader(name = "Authorization") String authz){
		try {
			obj.setIsUserNW(Lib.isUserNW(authz));
			(new EmployeeService()).getTableSort(obj);
			DashboardService service = new DashboardService();
			List data = service.getList(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
    }
	
	
	/**
	 * @description Get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/list-actual-vs-expected")
    public Object getListActualvsExpected(@RequestBody DashboardEntity obj){
		try {
			(new EmployeeService()).getTableSort(obj);
			DashboardService service = new DashboardService();
			List data = service.getListActualvsExpected(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
    }
	
	
	/**
	 * @description Get detail alert
	 * @author long.pham
	 * @since 2020-11-24
	 * @param id_site, id_customer, id_alert, current_time
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/alert-summary")
	public Object getAlertSummary(@RequestBody AlertEntity obj, @RequestHeader(name = "Authorization") String authz) {
		try {
			obj.setIsUserNW(Lib.isUserNW(authz));
			DashboardService service = new DashboardService();
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

    @PostMapping("/kpi-data")
	public Object getKPIData(@RequestBody PortfolioEntity obj, @RequestHeader(name = "Authorization") String authz) {
        try {
            List sites = Lib.sitesManagedByUser(authz);
            if (sites == null || sites.isEmpty()) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }

            obj.setId_sites(sites);
            DashboardService service = new DashboardService();
            List<EnergyEntity> energy = service.getTotalEnergyToday(obj);
            Map<String, Object> power = service.getTotalPowerAndCapacity(obj);
            Map<String, Object> res = new HashMap<>();
            res.put("energy", energy);
            res.put("power", power);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, res, 1);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
        }
    }

//    @PostMapping("/get-power-capacity")
//    public Object getTotalPowerAndCapacity(@RequestHeader(name = "Authorization") String authz) {
//        try {
//            List sites = Lib.sitesManagedByUser(authz);
//            if (sites == null || sites.isEmpty()) {
//                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
//            }
//            Map<String, Object> params = new HashMap<>();
//            params.put("id_sites", sites);
//            DashboardService service = new DashboardService();
//            Map<String, Object> data = service.getTotalPowerAndCapacity(params);
//            if (data == null) {
//                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
//            }
//            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
//        } catch (Exception e) {
//            log.error(e);
//        }
//        return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
//    }

    @PostMapping("/site-map-data")
    public Object getSiteMapData(@RequestHeader(name = "Authorization") String authz) {
        try {
            List sites = Lib.sitesManagedByUser(authz);
            if (sites == null || sites.isEmpty()) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }

            Map<String, Object> params = new HashMap<>();
            params.put("ids", sites);
            DashboardService service = new DashboardService();
            List<SiteEntity> dataList = service.getSiteMapData(params);
            if (dataList == null) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataList, dataList.size());
        } catch (Exception e) {
            log.error(e);
        }
        return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
    }
}
