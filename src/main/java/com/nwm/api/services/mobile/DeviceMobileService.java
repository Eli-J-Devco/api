package com.nwm.api.services.mobile;

import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.mobile.device.DeviceMobileEntity;
import com.nwm.api.entities.mobile.device.GetDeviceDetailsDto;
import com.nwm.api.entities.mobile.device.GetInverterFailuresDto;
import com.nwm.api.entities.mobile.device.GetYieldDto;
import com.nwm.api.entities.mobile.device.YieldEntity;

public class DeviceMobileService extends DB {

    public List GetInverterFailures(GetInverterFailuresDto dto){
        try{
            List data = queryForList("DeviceMobile.getInverterFailures", dto);

            return data;
        }catch (Exception ex){

            System.err.println(ex.getMessage());

             return null;
        }
    }

    public Integer CountInverterFailures(GetInverterFailuresDto dto){
        try{
            int data = (int) queryForObject("DeviceMobile.countInvertFail", dto);

            return data;
        }catch (Exception ex){

            System.err.println(ex.getMessage());

            return 0;
        }
    }

    public DeviceMobileEntity GetDeviceDetails(GetDeviceDetailsDto dto){
        try {
            DeviceMobileEntity data = (DeviceMobileEntity) queryForObject("DeviceMobile.getDeviceDetails", dto);

            if(data.getDataTable() != null){
                GetYieldDto yielDto = new GetYieldDto(data.getId(), data.getDataTable(), data.getTimeZone());

                YieldEntity yieldEntity = (YieldEntity) queryForObject("DeviceMobile.getYieldByDevice", yielDto);

                data.setYieldToday(yieldEntity.getYieldToday());
                data.setYieldYesterday(yieldEntity.getYieldYesterday());
                data.setYieldLastSevenDays(yieldEntity.getYieldLastSevenDays());
                data.setYieldYTD(yieldEntity.getYieldYTD());
            }

            return data;
        }catch (Exception ex){
            
            System.err.println(ex.getMessage());

            return new DeviceMobileEntity();
        }
    }
}
