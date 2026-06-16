package com.nwm.api.controllers.mobile;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.controllers.BaseController;
import com.nwm.api.entities.mobile.device.DeviceMobileEntity;
import com.nwm.api.entities.mobile.device.GetDeviceDetailsDto;
import com.nwm.api.entities.mobile.device.GetInverterAvailabilityDto;
import com.nwm.api.entities.mobile.device.GetInverterFailuresDto;
import com.nwm.api.entities.mobile.device.InverterAvailabilitySummary;
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

    @PostMapping("/get-inverter-failures")
    public Object GetInverterFailures(@RequestBody GetInverterFailuresDto dto) {
         try {
            List result = sevice.GetInverterFailures(dto);
            int count = sevice.CountInverterFailures(dto);

            return this.jsonResult(true, "Get Inverter Failures Success", result, count);
        } catch (Exception ex) {

            return this.jsonResult(false, Constants.GET_ERROR_MSG, "Get Inverter Failures Fail", 0);
        }
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

    @PostMapping("/inverters/status-summary")
    public Object GetInverterStatusSummary(@RequestBody GetInverterAvailabilityDto dto) {
        try {
            InverterAvailabilitySummary result = sevice.GetInverterStatusSummary(dto);

            return this.jsonResult(true, "Get Inverter Status Success", result, 1);
        } catch (Exception ex) {

            return this.jsonResult(false, Constants.GET_ERROR_MSG, "Get Inverter Status Fail", 0);
        }
    }

    @PostMapping("/get-inverter-availability-list")
    public Object GetInverterAvailabilityList(@RequestBody GetInverterAvailabilityDto dto) {
        try {
            Object result = sevice.GetInverterAvailabilityList(dto);

            return this.jsonResult(true, "Get Inverter Availability List Success", result, 1);
        } catch (Exception ex) {

            return this.jsonResult(false, Constants.GET_ERROR_MSG, "Get Device Details Fail", 0);
        }
    }
    
}
