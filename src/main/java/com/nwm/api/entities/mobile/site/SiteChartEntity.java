package com.nwm.api.entities.mobile.site;

import java.util.List;
public class SiteChartEntity {
    private List<String> labels;
    private List<SiteChartDataSetEntity> dataSets;

    public void setLabels(List<String> _labels){
        this.labels = _labels;
    }

    public void setDataSets(List<SiteChartDataSetEntity> _dataSets){
        this.dataSets = _dataSets;
    }

    public List<String> getLabels(){
        return this.labels;
    }

    public List<SiteChartDataSetEntity> getDataSets(){
        return this.dataSets;
    }
}
