/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.util.*;
import java.util.stream.Collectors;

import com.nwm.api.entities.*;

import com.nwm.api.services.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	DashboardService service;

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

    @PostMapping("/ae-last-week")
    public Object getPerformanceRatioLastWeek(@RequestBody PortfolioEntity obj, @RequestHeader(name = "Authorization") String authz) {
        try {
            List sites = Lib.sitesManagedByUser(authz);
            if (sites == null || sites.isEmpty()) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            obj.setId_sites(sites);
            Map<String, Object> res = service.getActualExpectLastWeek(obj);
            return this.jsonResult(res != null, res != null ? Constants.GET_SUCCESS_MSG : Constants.GET_ERROR_MSG, res);
        } catch (Exception e) {
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
            Map<String, Object> res = new HashMap<>();
            obj.setId_sites(sites);
            if (Lib.isBlank(obj.getId_filter())) {
                obj.setId_filter("today");
                List<Map<String, Object>> energy = service.getKPIData(obj);
                if (energy == null) {
                    return this.jsonResult(false, Constants.GET_ERROR_MSG, res);
                }
                Map<String, Object> power = new HashMap<>();
                double totalExpected = 0;
                double totalActual = 0;
                double totalPower = 0;
                double totalDCCapacity = 0;
                double totalACCapacity = 0;
                double totalLoss = 0;
//                double totalAE = 0;
                double totalInverterRatio = 0;
                double totalInverterAvailability = 0;
                int totalDeviceAlert = 0;
                for (Map<String, Object> item : energy) {
                    totalExpected += item.get("expected_energy") != null ? ((Number) item.get("expected_energy")).doubleValue() : 0;
                    totalActual += item.get("actual_energy") != null ? ((Number) item.get("actual_energy")).doubleValue() : 0;
                    totalLoss += item.get("loss") != null ? ((Number) item.get("loss")).doubleValue() : 0;
                    totalPower += item.get("active_power") != null ? ((Number) item.get("active_power")).doubleValue() : 0;
                    totalDCCapacity += item.get("dc_capacity") != null ? ((Number) item.get("dc_capacity")).doubleValue() : 0;
                    totalACCapacity += item.get("ac_capacity") != null ? ((Number) item.get("ac_capacity")).doubleValue() : 0;
//                    totalAE += item.get("performance_ratio") != null ? ((Number) item.get("performance_ratio")).doubleValue() : 0;
                    totalDeviceAlert += item.get("warning_count") != null ? ((Number) item.get("warning_count")).intValue() : 0;
                    totalDeviceAlert += item.get("critical_count") != null ? ((Number) item.get("critical_count")).intValue() : 0;
                    totalInverterRatio += item.get("inverter_ratio") != null ? ((Number) item.get("inverter_ratio")).doubleValue() : 0;
                    totalInverterAvailability += item.get("inverter_availability") != null ? ((Number) item.get("inverter_availability")).doubleValue() : 0;
                }

                power.put("active_power", totalPower);
                power.put("dc_capacity", totalDCCapacity);
                power.put("ac_capacity", totalACCapacity);

                res.put("total_expected_today", totalExpected);
                res.put("total_actual_today", totalActual);
                res.put("total_loss_today", totalLoss > 0 ? totalLoss : 0);
                res.put("inverter_ratio", totalInverterRatio / sites.size());
                res.put("inverter_availability", totalInverterAvailability / sites.size());
                res.put("total_performance_ratio", (totalActual / totalExpected) * 100);
                res.put("total_device_alert", totalDeviceAlert);
                res.put("power", power);
                res.put("energy", energy);
                return this.jsonResult(true, Constants.GET_SUCCESS_MSG, res);
            }

            res = service.getKPIDataByKey(obj, obj.getId_filter());
            if (res == null) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, res);
            }
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, res);
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

    @PostMapping("/chart-energy-flow")
    public Object getChartEnergyFlow(@RequestBody Map<String, Object> body, @RequestHeader(name = "Authorization") String authz) {
        try {
            String companyIdHash = (String) body.get("company_hash_id");
            if (Lib.isBlank(companyIdHash)) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            String chartType = (String) body.get("chart_setting_type");
            SiteService siteService = new SiteService();
            Map<String, Object> params = new HashMap<>();
            params.put("company_hash", body.get("company_hash_id"));
            if ("type_timezone".equalsIgnoreCase(chartType)) {
                params.put("time_zone_id", body.get("time_zone_id"));
            }
            List<SiteEntity> sites = siteService.getSiteByCondition(params);

            if (sites == null || sites.isEmpty()) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            List<Integer> siteIds = sites.stream().map(item -> item.getId()).collect(Collectors.toList());
            body.put("id_sites", siteIds);
            List<Map<String, Object>> data = service.getChartEnergyFlow(body);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }

    @PostMapping("/chart-data-performance")
    public Object getChartDataPerformance(@RequestBody Map<String, Object> body) {
        try {
            SiteService siteService = new SiteService();
            Map<String, Object> params = new HashMap<>();
            params.put("company_hash", body.get("company_hash_id"));
            List<SiteEntity> sites = siteService.getSiteByCondition(params);
            List<Integer> siteIds = sites.stream().map(item -> item.getId()).collect(Collectors.toList());
            if (siteIds.isEmpty()) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            body.put("id_sites", siteIds);
            List<Map<String, Object>> data = service.getChartDataPerformance(body);

            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }

    @PostMapping("/top-priority-site")
    public Object getTopPrioritySite(@RequestBody SiteEntity obj) {
        try {
            Map<String, Object> data = service.getTopPrioritySite(obj);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }

    /**
     * @description Get top device alert
     * @author minh le
     * @since 2026-06-17
     * @param body
     * @param authz
     * @return
     */
    @PostMapping("get-top-device-alert")
    public Object getTopDeviceAlert(@RequestBody Map<String, Object> body, @RequestHeader(name = "Authorization", required = false) String authz) {
        try {
            int mode = body.get("mode") != null ? (int) body.get("mode") : 1;
            Map<String, Object> res = new HashMap<>();

            if (mode == 1) {
                List sites = Lib.sitesManagedByUser(authz);
                if (sites == null || sites.isEmpty()) {
                    return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
                }
                body.put("id_sites", sites);
            }

            List<Map<String, Object>> data = service.getTopDeviceAlert(body);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }

    @PostMapping("list-company-of-user")
    public Object getListCompanyOfUser(@RequestBody Map<String, Object> body, @RequestHeader(name = "Authorization") String authz) {
        try {
            Map<String, Object> res = new HashMap<>();
            List sites = Lib.sitesManagedByUser(authz);
            if (sites == null || sites.isEmpty()) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            body.put("id_sites", sites);
            List<Map<String, Object>> listCompany = service.getListCompanyOfUser(body);
            res.put("companies", listCompany);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, res);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }


}
