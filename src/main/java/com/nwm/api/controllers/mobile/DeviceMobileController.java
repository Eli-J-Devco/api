package com.nwm.api.controllers.mobile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.controllers.BaseController;
import com.nwm.api.entities.mobile.device.DeviceMobileEntity;
import com.nwm.api.entities.mobile.device.GetDeviceDetailsDto;
import com.nwm.api.services.mobile.DeviceMobileService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/mobile/device")
public class DeviceMobileController extends BaseController {
    private final DeviceMobileService sevice;

    public DeviceMobileController() {
        this.sevice = new DeviceMobileService();
    }

    @PostMapping("/get-device-details")
    public Object GetDeviceDetails(@RequestBody GetDeviceDetailsDto body) {
        try {
            DeviceMobileEntity result = sevice.GetDeviceDetails(body);

            return this.jsonResult(true, "Get  Device Details Success", result, 1);
        } catch (Exception ex) {

            return this.jsonResult(false, Constants.GET_ERROR_MSG, "Get Device Details Fail", 0);
        }
    }
}
