package com.nwm.api.controllers;

import com.nwm.api.entities.ApiAccessEntity;
import com.nwm.api.services.ApiAccessService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@ApiIgnore
@RequestMapping("/api-access")
public class ApiAccessController extends BaseController {

    /**
     * @description save api access config
     * @return data (status, message, array, total_row
     */
    @PostMapping("/save-config")
    public Object saveConfig(@RequestBody Map<String, Object> params) {
        try {
            ApiAccessService service = new ApiAccessService();
            boolean result = service.saveConfig(params);
            String msg = result ? Constants.SAVE_SUCCESS_MSG : Constants.SAVE_ERROR_MSG;
            return this.jsonResult(result, msg, null, 0);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/check-api-access-config")
    public Object checkApiAccessConfig(@RequestBody Map<String, Object> params) {
        try {
            ApiAccessService service = new ApiAccessService();
            ApiAccessEntity res = service.checkApiAccessConfig(params);
            return this.jsonResult(true, res == null ? Constants.GET_ERROR_MSG : Constants.GET_SUCCESS_MSG, res, 0);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/get-list-company")
    public Object getListCompany(@RequestBody Map<String, Object> params) {
        try {
            ApiAccessService service = new ApiAccessService();
            List data = service.getListCompany(params);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/get-list-user")
    public Object getListUser(@RequestBody Map<String, Object> params) {
        try {
            ApiAccessService service = new ApiAccessService();
            List data = service.getListUser(params);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/get-list-site")
    public Object getListSite(@RequestBody Map<String, Object> params) {
        try {
            ApiAccessService service = new ApiAccessService();
            List data = service.getListSite(params);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/get-api-access-config")
    public Object getApiAccessConfig(@RequestBody Map<String, Object> params) {
        try{
            ApiAccessService service = new ApiAccessService();
            Map<String, Object> data = service.getApiAccessConfig(params);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/list-end-point")
    public Object getListEndPoint(@RequestBody Map<String, Object> params) {
        try{
            ApiAccessService service = new ApiAccessService();
            Object data = service.getListEndPoint(params);
            int totalRow = service.getTotalEndPoint(params);

            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRow);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    /**
     * @description get list endpoint user can access
     * @return data (status, message, array, total_row
     */
    @PostMapping("/user-list-end-point")
    public Object getUserListEndPoint(@RequestBody Map<String, Object> params, @RequestHeader(name = "Authorization") String authz) {
        try{
            ApiAccessService service = new ApiAccessService();
            int userId = Lib.getUserId(authz);
            if (userId < 1) {
                return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
            }
            params.put("employee_id", userId);
            Map<String, Object> res = service.getListEndPointOfUser(params);
            if (res == null || res.isEmpty()) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
            }
            List data = (List) res.get("data");
            Integer totalRow = (Integer) res.get("total_row");

            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRow);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/get-chart-data")
    public Object getChartData(@RequestBody Map<String, Object> params, @RequestHeader(name = "Authorization") String authz) {
        try{
            int userId = Lib.getUserId(authz);
            if (userId < 1) {
                return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
            }
            params.put("employee_id", userId);
            ApiAccessService service = new ApiAccessService();
            List data = service.getChartData(params);

            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 0);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/create-security-key")
    public Object createSecurityKey(@RequestBody Map<String, Object> params, @RequestHeader(name = "Authorization") String authz) {
        try{
            int userId = Lib.getUserId(authz);
            if (userId < 1) {
                return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
            }
            ApiAccessService service = new ApiAccessService();
            params.put("employee_id", userId);
            boolean res = service.createSecurityKey(params);

            return this.jsonResult(res, res ? Constants.SAVE_SUCCESS_MSG : Constants.SAVE_ERROR_MSG, null, 0);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/get-user-security-key")
    public Object getUserSecurityKey(@RequestHeader(name = "Authorization") String authz) {
        try {
            int userId = Lib.getUserId(authz);
            if (userId < 1) {
                return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
            }
            ApiAccessService service = new ApiAccessService();
            Map<String, Object> params = new HashMap<>();
            params.put("employee_id", userId);
            Map<String, Object> data = service.getUserSecurityKey(params);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }
}
