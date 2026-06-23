package com.nwm.api.controllers.mobile;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.controllers.BaseController;
import com.nwm.api.entities.mobile.alert.ErrorTypeMobileEntity;
import com.nwm.api.services.mobile.ErrorTypeMobileService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;


@ApiIgnore
@RestController
@RequestMapping("/mobile/error")
public class ErrorTypeMobileController extends BaseController {
    private final ErrorTypeMobileService service;

    public ErrorTypeMobileController(){
        this.service = new ErrorTypeMobileService();
    }

    @GetMapping("/error-types")
    public Object GetErrorTypes() {
        try {
            List<ErrorTypeMobileEntity> result = service.GetErrorTypes();

             return this.jsonResult(true, "Get Error Types Success", result, result.size());
        } catch (Exception ex){
            return this.jsonResult(false, Constants.GET_ERROR_MSG, "Get Error Types Fail", 0);
        }
    }
}
