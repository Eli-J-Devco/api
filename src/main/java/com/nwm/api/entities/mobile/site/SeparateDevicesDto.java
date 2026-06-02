package com.nwm.api.entities.mobile.site;

import java.util.List;

public class SeparateDevicesDto {
    private List<SiteDeviceDto> meter;
    private List<SiteDeviceDto> inverter;
    private List<SiteDeviceDto> irradiance;

    public SeparateDevicesDto(List<SiteDeviceDto> _meter, List<SiteDeviceDto> _inverter, List<SiteDeviceDto> _irradiance){
        this.meter = _meter;
        this.inverter = _inverter;
        this.irradiance = _irradiance;
    }


    public List<SiteDeviceDto> getMeter() {
        return this.meter;
    }

    public List<SiteDeviceDto> getInverter(){
        return this.inverter;
    }

    public List<SiteDeviceDto> getIrradiance(){
        return this.irradiance;
    }
}
