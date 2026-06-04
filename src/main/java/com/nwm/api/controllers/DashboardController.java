/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.util.ArrayList;
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
	public Object getKPIData(@RequestBody Map<String, Object> body, @RequestHeader(name = "Authorization", required = false) String authz) {
        try {
//            // mode 1 is dashboard, 2 is kiosk
//            int mode = body.get("mode") != null ? (int) body.get("mode") : 1;
//            String filterBy = (String) body.get("filter_by");
//            PortfolioEntity obj = new PortfolioEntity();
//            DashboardService service = new DashboardService();
//            Map<String, Object> res = new HashMap<>();
//            // if mode is dashboard, check user login
//            if (mode == 1) {
//                List sites = Lib.sitesManagedByUser(authz);
//                if (sites == null || sites.isEmpty()) {
//                    return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
//                }
//                obj.setId_sites(sites);
//            }
//
//            // when init, no need pass param filter_by to api
//            // defaul get actual_energy_today, expected_energy_today, ac_capacity, active_power
//            if (Lib.isBlank(filterBy)) {
//                obj.setId_filter("today");
//                List<EnergyEntity> energy = service.getEnergyExpected(obj, true);
//                Map<String, Object> power = service.getTotalPowerAndCapacity(obj);
//                double totalExpected = 0;
//                double totalActual = 0;
//                double totalLoss = 0;
//                for (EnergyEntity item : energy) {
//                    totalExpected += item.getExpected() != null ? item.getExpected() : 0;
//                    totalActual += item.getActual() != null ? item.getActual() : 0;
//                    totalLoss += item.getLoss() != null ? item.getLoss() : 0;
//                }
//                res.put("total_expected_today", totalExpected);
//                res.put("total_actual_today", totalActual);
//                res.put("total_loss_today", totalLoss);
//                res.put("power", power);
//                res.put("energy", energy);
//                return this.jsonResult(true, Constants.GET_SUCCESS_MSG, res, 1);
//            }
//            res = service.getKPIDataByKey(obj, filterBy);

            // mode 1 is dashboard, 2 is kiosk
            int mode = body.get("mode") != null ? (int) body.get("mode") : 1;
            String filterBy = (String) body.get("filter_by");
            PortfolioEntity obj = new PortfolioEntity();
            obj.setId_filter(filterBy);
            DashboardService service = new DashboardService();
            Map<String, Object> res = new HashMap<>();
            // if mode is dashboard, check user login
            if (mode == 1) {
                List sites = Lib.sitesManagedByUser(authz);
                if (sites == null || sites.isEmpty()) {
                    return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
                }
                obj.setId_sites(sites);
            }

            res = service.getKPIData(obj);

            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, res, 1);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }

    @PostMapping("/site-map-data")
    public Object getSiteMapData(@RequestBody Map<String, Object> body, @RequestHeader(name = "Authorization", required = false) String authz) {
        try {
            Map<String, Object> params = new HashMap<>();
            // mode 1 is dashboard, 2 is kiosk
            int mode = body.get("mode") != null ? (int) body.get("mode") : 1;
            if (mode == 1) {
                List sites = Lib.sitesManagedByUser(authz);
                if (sites == null || sites.isEmpty()) {
                    return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
                }
                params.put("ids", sites);
            }

            DashboardService service = new DashboardService();
            List<Map<String, Object>> dataList = service.getSiteMapData(params);
            if (dataList == null) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataList, dataList.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }

    @PostMapping("/site-map-detail")
    public Object getSiteDetail(@RequestBody SiteEntity obj, @RequestHeader(name = "Authorization") String authz) {
        try {
            int userId = Lib.getUserId(authz);
            if (userId <= 0) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            DashboardService service = new DashboardService();
            Map<String, Object> data = service.getSiteDetail(obj);
            if (data == null) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }

    @PostMapping("/power-performance-chart")
    public Object powerPerformanceChart(@RequestBody Map<String, Object> body, @RequestHeader(name = "Authorization", required = false) String authz) {
        try {
            // mode 1 is dashboard, 2 is kiosk
            int mode = body.get("mode") != null ? (int) body.get("mode") : 1;
            DashboardService service = new DashboardService();
            Map<String, Object> res = new HashMap<>();
            // if mode is dashboard, check user login
            if (mode == 1) {
                List sites = Lib.sitesManagedByUser(authz);
                if (sites == null || sites.isEmpty()) {
                    return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
                }
                body.put("id_sites", sites);
            }
            List<Map<String, Object>> data = service.getActualvsExpectedPower(body);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }

    @PostMapping("/test")
    public Object test(@RequestBody Map<String, Object> body, @RequestHeader(name = "Authorization", required = false) String authz) {
        try {
            // mode 1 is dashboard, 2 is kiosk
            int mode = body.get("mode") != null ? (int) body.get("mode") : 1;
            String filterBy = (String) body.get("filter_by");
            PortfolioEntity obj = new PortfolioEntity();
            DashboardService service = new DashboardService();
            Map<String, Object> res = new HashMap<>();
            // if mode is dashboard, check user login
            if (mode == 1) {
                List sites = Lib.sitesManagedByUser(authz);
                if (sites == null || sites.isEmpty()) {
                    return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
                }
                obj.setId_sites(sites);
            }

            res = service.getKPIData(obj);

            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, res, 1);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }
}
