package com.nwm.api.entities.mobile.site;

import java.util.List;

public class GetChartParameterContext {
    private GetSiteChartDto request;
    private List<SiteDeviceDto> listMeter;
    private int totalMeter;

    public GetSiteChartDto getGetSiteChartDto() {
        return this.request;
    }

    public void setGetSiteChartDto(GetSiteChartDto _request) {
        this.request = _request;
    }

    public List<SiteDeviceDto> getListMeter() {
        return this.listMeter;
    }

    public void setListMeter(List<SiteDeviceDto> _listMeter) {
        this.listMeter = _listMeter;
    }

    public int getTotalMeter() {
        return this.totalMeter;
    }

    public void setTotalMeter(int _totalMeter) {
        this.totalMeter = _totalMeter;
    }
}