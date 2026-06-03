package com.nwm.api.entities.mobile.site;

import java.util.List;

import com.nwm.api.entities.mobile.common.MetricType;

public class SiteChartEntity {
    private List<ChartDataEntity> chartData;
    private String unit;
    private String legend;
    private MetricType metricType;

    public List<ChartDataEntity> getChartData() {
        return this.chartData;
    }

    public void setChartData(List<ChartDataEntity> _chartData) {
        this.chartData = _chartData;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String _unit) {
        this.unit = _unit;
    }

    public String getLegend() {
        return this.legend;
    }

    public void setLegend(String _legend) {
        this.legend = _legend;
    }

    public MetricType getMetricType() {
        return this.metricType;
    }

    public void setMetricType(MetricType _metricType) {
        this.metricType = _metricType;
    }
}
