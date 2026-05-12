package com.nwm.api.controllers;

import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.SiteService;
import com.nwm.api.services.SolarEdgeService;
import com.nwm.api.utils.Constants;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@ApiIgnore
@RequestMapping("/solar-edge")
public class SolarEdgeController extends BaseController {

    @PostMapping("/init")
    public Object getSolarEdgeInfo(@RequestBody SiteEntity obj) {
        try {
            SolarEdgeService service = new SolarEdgeService();
            Map<String, Object> data = service.getSolarEdgeInfo(obj);
            if (data == null) {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
            }
            return this.jsonResult(false, Constants.GET_SUCCESS_MSG, data, 1);
        } catch (Exception e) {
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }
}
