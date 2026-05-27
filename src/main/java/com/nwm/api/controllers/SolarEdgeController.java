package com.nwm.api.controllers;

import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.SiteService;
import com.nwm.api.services.SolarEdgeService;
import com.nwm.api.utils.Constants;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@RestController
@ApiIgnore
@RequestMapping("/solar-edge")
public class SolarEdgeController extends BaseController {

    /**
     * @description API when init, get solar edge site id & api key
     * @author quan.nguyen
     * @since 2026-05-12
     * @params SiteEntity
     */
    @PostMapping("/init")
    public Object getSolarEdgeInfo(@RequestBody SiteEntity obj) {
        try {
            SolarEdgeService service = new SolarEdgeService();
            Map<String, Object> data = service.getSolarEdgeInfo(obj);
            if (data == null) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
            }
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
        } catch (Exception e) {
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    /**
     * @description API to connect Solar Edge API
     * @author minh.le
     * @since 2026-05-13
     * @params SiteEntity
     */
    @PostMapping("get-site-info")
    public Object getSiteInfo(@RequestBody SiteEntity obj) {
        try {
            SolarEdgeService service = new SolarEdgeService();
            Map<String, Object> data = service.getSolarEdgeSiteInfo(obj);
            if (data == null) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
            }
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
        } catch (Exception e) {
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    /**
     * @description API add Solar Edge Device
     * @author minh.le
     * @since 2026-05-13
     * @params SiteEntity
     */
    @PostMapping("sync-inventory")
    public Object syncInventory(@RequestBody SiteEntity obj) {
        try {
            SolarEdgeService service = new SolarEdgeService();
            List<Map<String, Object>> data = service.syncInventory(obj);
            if (data == null || data.isEmpty()) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
            }
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    /**
     * @description API insert data from solar edge to nw db
     * @author quan.nguyen
     * @since 2026-05-18
     * @params SiteEntity
     */
    @PostMapping("fill-data")
    public Object fillBackData(@RequestBody Map<String, Object> obj) {
        try {
            SolarEdgeService service = new SolarEdgeService();
            boolean res = service.fillBackData(obj, false);
            return this.jsonResult(res, res ? Constants.SAVE_SUCCESS_MSG : Constants.SAVE_ERROR_MSG, null, 0);
        } catch (Exception e) {
            return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
        }
    }
}
