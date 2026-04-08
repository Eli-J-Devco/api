package com.nwm.api.controllers;

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceParameterEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.DeviceParameterService;
import com.nwm.api.services.SiteMapService;
import com.nwm.api.utils.Constants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@ApiIgnore
@RequestMapping("/site-map")
public class SiteMapController extends BaseController {
    @PostMapping("/site-info")
    public Object getSiteInfo(@RequestBody SiteEntity site) {
        try {
            SiteMapService service = new SiteMapService();
            Object data = service.getSiteInfo(site);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e);
        }
    }

    @PostMapping("/device-list")
    public Object getDeviceList(@RequestBody SiteEntity site) {
        try {
            SiteMapService service = new SiteMapService();
            Object data = service.getDeviceList(site);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e);
        }
    }

    @PostMapping("/device-detail")
    public Object getDeviceDetail(@RequestBody DeviceEntity obj) {
        try {
            SiteMapService service = new SiteMapService();
            List data = service.getDeviceDetail(obj);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/parameter-list")
    public Object getListParameterByDeviceGroup(@RequestBody DeviceParameterEntity obj) {
        try {
            SiteMapService service = new SiteMapService();
            List data = service.getListParameterByDeviceGroup(obj);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/get-device-parameter-list")
    public Object getDeviceParameterList(@RequestBody DeviceParameterEntity obj) {
        try {
            SiteMapService service = new SiteMapService();
            List data = service.getListParameterByDeviceGroup(obj);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }
}
