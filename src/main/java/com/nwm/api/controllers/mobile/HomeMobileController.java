package com.nwm.api.controllers.mobile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.controllers.BaseController;
import com.nwm.api.entities.mobile.home.GetSummaryDto;
import com.nwm.api.services.mobile.HomeMobileService;

import springfox.documentation.annotations.ApiIgnore;


@ApiIgnore
@RestController
@RequestMapping("/mobile/home")
public class HomeMobileController extends BaseController {
    private final HomeMobileService service;

    public HomeMobileController(){
        this.service = new HomeMobileService();
    }

    @PostMapping("/get-summary")
    public Object GetSummary(@RequestBody GetSummaryDto body) {
       	try {
			Object data = this.service.GetSummary(body);

			return this.jsonResult(true, "Get Home Summary Success", data, 1);

		} catch (Exception ex) {
			log.error(ex);

			return this.jsonResult(false, "Get Home Summary By User faild", ex, 0);
		}
    }
    
}
