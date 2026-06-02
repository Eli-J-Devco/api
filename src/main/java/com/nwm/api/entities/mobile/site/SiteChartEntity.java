package com.nwm.api.entities.mobile.site;

import java.util.List;
import java.util.Map;

public class SiteChartEntity {
    private List<ChartDataEntity> chartData;

    public List<ChartDataEntity> getChartData() {
        return this.chartData;
    }

    public void setChartData(List<ChartDataEntity> _chartData) {
        this.chartData = _chartData;
    }
}
