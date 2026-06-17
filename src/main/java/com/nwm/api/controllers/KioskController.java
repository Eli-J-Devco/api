/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.controllers;

import com.nwm.api.entities.DashboardEntity;
import com.nwm.api.entities.EnergyEntity;
import com.nwm.api.entities.PortfolioEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.DashboardService;
import com.nwm.api.services.EmployeeService;
import com.nwm.api.services.PortfolioService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@ApiIgnore
@RequestMapping("/kiosk")

public class KioskController extends BaseController{
    /**
     * description Get data for site map in kiosk
     * @author minh le
     * @since 2026-06-05
     * @param body
     * @param authz
     * @return data (status, message, array, total_row)
     */
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
     * @description Get KPI data for kiosk
     * @author minh le
     * @since 2026-06-05
     * @param body
     * @param authz
     * @return
     */
    @PostMapping("/kpi-data")
    public Object getKPIData(@RequestBody Map<String, Object> body, @RequestHeader(name = "Authorization", required = false) String authz) {
        try {
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

            if (Lib.isBlank(filterBy)) {
                obj.setId_filter("today");
                List<Map<String, Object>> energy = service.getKPIData(obj);
                if (energy == null) {
                    return this.jsonResult(true, Constants.GET_ERROR_MSG, res);
                }
                Map<String, Object> power = new HashMap<>();
                double totalExpected = 0;
                double totalActual = 0;
                double totalLoss = 0;
                double totalPower = 0;
                double totalDCCapacity = 0;
                double totalACCapacity = 0;
                for (Map<String, Object> item : energy) {
                    totalExpected += item.get("expected_energy") != null ? (double) item.get("expected_energy") : 0;
                    totalActual += item.get("actual_energy") != null ? (double) item.get("actual_energy") : 0;
                    totalLoss += item.get("loss") != null ? (double) item.get("loss") : 0;
                    totalPower += item.get("active_power") != null ? (double) item.get("active_power") : 0;
                    totalDCCapacity += item.get("dc_capacity") != null ? (double) item.get("dc_capacity") : 0;
                    totalACCapacity += item.get("ac_capacity") != null ? (double) item.get("ac_capacity") : 0;
                }

                power.put("active_power", totalPower);
                power.put("dc_capacity", totalDCCapacity);
                power.put("ac_capacity", totalACCapacity);

                res.put("total_expected_today", totalExpected);
                res.put("total_actual_today", totalActual);
                res.put("total_loss_today", totalLoss);
                res.put("power", power);
                res.put("energy", energy);
                return this.jsonResult(true, Constants.GET_SUCCESS_MSG, res);
            }
            res = service.getKPIDataByKey(obj, filterBy);

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
            DashboardService service = new DashboardService();
            Map<String, Object> data = service.getTopPrioritySite(obj);
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
            List<Map<String, Object>> data = service.getChartEnergyFlow(body);
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
}
