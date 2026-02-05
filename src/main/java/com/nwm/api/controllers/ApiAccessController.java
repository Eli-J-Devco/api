package com.nwm.api.controllers;

import com.nwm.api.entities.ApiAccessEntity;
import com.nwm.api.services.ApiAccessService;
import com.nwm.api.utils.Constants;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@RestController
@ApiIgnore
@RequestMapping("/api-access")
public class ApiAccessController extends BaseController {

    @PostMapping("/save-config")
    public Object saveConfig(@RequestBody ApiAccessEntity obj) {
        try {
            ApiAccessService service = new ApiAccessService();
            boolean result = false;
            String msg = Constants.SAVE_SUCCESS_MSG;
            if (obj.getId() == 0) {
                // create new
                result = service.saveConfig(obj);
                msg = Constants.SAVE_SUCCESS_MSG;
            } else {
                // update
            }
            return this.jsonResult(result, msg, null, 0);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
        }
    }

    @GetMapping("/get-api-access-config")
    public Object getApiAccessConfig(@RequestBody ApiAccessEntity obj) {
        try {
            ApiAccessService service = new ApiAccessService();

            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @GetMapping("/get-list-company")
    public Object getListCompany(@RequestBody Map<String, Object> params) {
        try {
            ApiAccessService service = new ApiAccessService();
//            int isSupperAdmin = (int) params.get("is_supper_admin");
//            if (isSupperAdmin != 1) {
//                return this.jsonResult(true, Constants.GET_ERROR_MSG, null, 0);
//            }
            List data = service.getListCompany();
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @GetMapping("/get-list-user")
    public Object getListUser(@RequestBody Map<String, Object> params) {
        try {
            ApiAccessService service = new ApiAccessService();
//            int isSupperAdmin = (int) params.get("is_supper_admin");
//            if (isSupperAdmin != 1) {
//                return this.jsonResult(true, Constants.GET_ERROR_MSG, null, 0);
//            }
            List data = service.getListUser();
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @GetMapping("/get-list-site")
    public Object getListSite(@RequestBody Map<String, Object> params) {
        try {
            ApiAccessService service = new ApiAccessService();
//            int isSupperAdmin = (int) params.get("is_supper_admin");
//            if (isSupperAdmin != 1) {
//                return this.jsonResult(true, Constants.GET_ERROR_MSG, null, 0);
//            }
            List data = service.getListSite(params);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }
}
