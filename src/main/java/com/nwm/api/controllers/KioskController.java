/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.controllers;

import com.nwm.api.entities.DashboardEntity;
import com.nwm.api.entities.PortfolioEntity;
import com.nwm.api.entities.SiteEnergyEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.DashboardService;
import com.nwm.api.services.EmployeeService;
import com.nwm.api.services.PortfolioService;
import com.nwm.api.services.SiteService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@ApiIgnore
@RequestMapping("/kiosk")

public class KioskController extends BaseController{
	@Autowired
	DashboardService dashboardService;
    @Autowired
    PortfolioService portfolioService;
	
    /**
     * description Get data for site map in kiosk
     * @author minh le
     * @since 2026-06-05
     * @param body
     * @param authz
     * @return data (status, message, array, total_row)
     */
    @PostMapping("/site-map-data")
    public Object getSiteMapData(@RequestBody Map<String, Object> body) {
        try {
            // mode 1 is dashboard, 2 is kiosk
//            int mode = body.get("mode") != null ? (int) body.get("mode") : 1;
//            if (mode == 1) {
//                List sites = Lib.sitesManagedByUser(authz);
//                if (sites == null || sites.isEmpty()) {
//                    return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
//                }
//                params.put("ids", sites);
//            }

            SiteService siteService = new SiteService();
            Map<String, Object> params = new HashMap<>();
            params.put("company_hash", body.get("company_hash_id"));
            List<SiteEntity> sites = siteService.getSiteByCondition(params);
            List<Integer> siteIds = sites.stream().map(SiteEntity::getId).collect(Collectors.toList());
            if (siteIds.isEmpty()) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            params.put("ids", siteIds);

            List<Map<String, Object>> dataList = dashboardService.getSiteMapData(params);
            if (dataList == null) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataList, dataList.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }

    /**
     * @description Get list actual vs expected energy for site map in kiosk
     * @author minh le
     * @since 2026-06-05
     * @param obj
     * @return data (status, message, array, total_row)
     */
    @PostMapping("/list-actual-vs-expected")
    public Object getListActualvsExpected(@RequestBody DashboardEntity obj){
        try {
            SiteService siteService = new SiteService();

            Map<String, Object> params = new HashMap<>();
            params.put("company_hash", obj.getCompany_hash_id());
            List<SiteEntity> sites = siteService.getSiteByCondition(params);

            if (sites.isEmpty()) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            obj.setId_sites(sites);

            List data = dashboardService.getListActualvsExpected(obj);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    /**
     * @description Get KPI data for kiosk
     * @author minh le
     * @since 2026-06-05
     * @param body
     * @param authz
     * @return
     */
    @PostMapping("/kpi-data")
    public Object getKPIData(@RequestBody PortfolioEntity obj) {
        try {
            SiteService siteService = new SiteService();
            Map<String, Object> res = new HashMap<>();

            Map<String, Object> params = new HashMap<>();
            params.put("company_hash", obj.getCompany_hash_id());
            List<SiteEntity> sites = siteService.getSiteByCondition(params);
            List<Integer> siteIds = sites.stream().map(SiteEntity::getId).collect(Collectors.toList());
            if (siteIds.isEmpty()) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            obj.setId_sites(siteIds);
            if (Lib.isBlank(obj.getId_filter())) {
                obj.setId_filter("today");
                List<Map<String, Object>> energy = dashboardService.getKPIData(obj);
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
                double totalAE = 0;
                int totalDeviceAlert = 0;
                for (Map<String, Object> item : energy) {
                    totalExpected += item.get("expected_energy") != null ? ((Number) item.get("expected_energy")).doubleValue() : 0;
                    totalActual += item.get("actual_energy") != null ? ((Number) item.get("actual_energy")).doubleValue() : 0;
                    totalLoss += item.get("loss") != null ? ((Number) item.get("loss")).doubleValue() : 0;
                    totalPower += item.get("active_power") != null ? ((Number) item.get("active_power")).doubleValue() : 0;
                    totalDCCapacity += item.get("dc_capacity") != null ? ((Number) item.get("dc_capacity")).doubleValue() : 0;
                    totalACCapacity += item.get("ac_capacity") != null ? ((Number) item.get("ac_capacity")).doubleValue() : 0;
                    totalAE += item.get("performance_ratio") != null ? ((Number) item.get("performance_ratio")).doubleValue() : 0;
                    totalDeviceAlert += item.get("warning_count") != null ? ((Number) item.get("warning_count")).intValue() : 0;
                    totalDeviceAlert += item.get("critical_count") != null ? ((Number) item.get("critical_count")).intValue() : 0;
                }

                power.put("active_power", totalPower);
                power.put("dc_capacity", totalDCCapacity);
                power.put("ac_capacity", totalACCapacity);

                res.put("total_expected_today", totalExpected);
                res.put("total_actual_today", totalActual);
                res.put("total_loss_today", totalLoss > 0 ? totalLoss : 0);
                res.put("total_performance_ratio", totalAE);
                res.put("total_device_alert", totalDeviceAlert);
                res.put("power", power);
                res.put("energy", energy);
                return this.jsonResult(true, Constants.GET_SUCCESS_MSG, res);
            }

            res = dashboardService.getKPIDataByKey(obj, obj.getId_filter());
            if (res == null) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, res);
            }
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, res);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }

    /**
     * @description Get top priority site
     * @author minh le
     * @since 2026-06-12
     * @param obj
     * @return
     */
    @PostMapping("/top-priority-site")
    public Object getTopPrioritySite(@RequestBody SiteEntity obj) {
        try {
            Map<String, Object> data = dashboardService.getTopPrioritySite(obj);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }

    /**
     * @description Get data for chart energy flow in kiosk
     * @author minh le
     * @since 2026-06-09
     * @param body
     * @param authz
     * @return
     */
    @PostMapping("/chart-energy-flow")
    public Object getChartEnergyFlow(@RequestBody Map<String, Object> body, @RequestHeader(name = "Authorization", required = false) String authz) {
        try {
            // mode 1 is dashboard, 2 is kiosk
//            int mode = body.get("mode") != null ? (int) body.get("mode") : 1;
            Map<String, Object> res = new HashMap<>();
            // if mode is dashboard, check user login
//            if (mode == 1) {
//                List sites = Lib.sitesManagedByUser(authz);
//                if (sites == null || sites.isEmpty()) {
//                    return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
//                }
//                body.put("id_sites", sites);
//            }

            SiteService siteService = new SiteService();
            Map<String, Object> params = new HashMap<>();
            params.put("company_hash", body.get("company_hash_id"));
            List<SiteEntity> sites = siteService.getSiteByCondition(params);
            List<Integer> siteIds = sites.stream().map(item -> item.getId()).collect(Collectors.toList());
            if (siteIds.isEmpty()) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            body.put("id_sites", siteIds);
            List<Map<String, Object>> data = dashboardService.getChartEnergyFlow(body);

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
            List<Map<String, Object>> data = dashboardService.getChartDataPerformance(body);

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
//            int mode = body.get("mode") != null ? (int) body.get("mode") : 1;
            Map<String, Object> res = new HashMap<>();

//            if (mode == 1) {
//                List sites = Lib.sitesManagedByUser(authz);
//                if (sites == null || sites.isEmpty()) {
//                    return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
//                }
//                body.put("id_sites", sites);
//            }

            SiteService siteService = new SiteService();
            Map<String, Object> params = new HashMap<>();
            params.put("company_hash", body.get("company_hash_id"));
            List<SiteEntity> sites = siteService.getSiteByCondition(params);
            List<Integer> siteIds = sites.stream().map(SiteEntity::getId).collect(Collectors.toList());
            if (siteIds.isEmpty()) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            body.put("id_sites", siteIds);

            List<Map<String, Object>> data = dashboardService.getTopDeviceAlert(body);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, e.getMessage(), null);
        }
    }

    /**
     * @description Get list portfolio for kiosk
     * @author minh le
     * @since 2026-06-05
     * @param obj
     * @return
     */
    @PostMapping("/list-portfolio")
    public Object getList(@RequestBody PortfolioEntity obj) {
        try {
            (new EmployeeService()).getTableSort(obj);
            PortfolioService service = new PortfolioService();
            List data = service.getList(obj);

            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/metrics/actual-vs-expected")
    public Object getSitesMetricsActualVsExpected(@RequestBody PortfolioEntity obj) {
        try {
            if (Lib.isBlank(obj.getCompany_hash_id())) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            SiteService siteService = new SiteService();
            Map<String, Object> params = new HashMap<>();
            params.put("company_hash", obj.getCompany_hash_id());
            List<SiteEntity> siteList = siteService.getSiteByCondition(params);
            if (siteList == null) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
            }
            List sites = siteList.stream().map(item -> item.getId()).collect(Collectors.toList());
            if (sites.size() == 0) return this.jsonResult(false, Constants.GET_ERROR_MSG, null);

            obj.setId_sites(sites);
            List<SiteEnergyEntity> data = portfolioService.getSitesMetricsActualVsExpected(obj);

            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
        }
    }
}
