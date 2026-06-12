package com.nwm.api.entities.mobile.device;

public class YieldEntity {
     private double yieldToday;
    private double yieldYesterday;
    private double yieldLastSevenDays;
    private double yieldYTD;

      public double getYieldToday() {
        return yieldToday;
    }

    public void setYieldToday(double yieldToday) {
        this.yieldToday = yieldToday;
    }

    public double getYieldYesterday() {
        return yieldYesterday;
    }

    public void setYieldYesterday(double yieldYesterday) {
        this.yieldYesterday = yieldYesterday;
    }

    public double getYieldLastSevenDays() {
        return yieldLastSevenDays;
    }

    public void setYieldLastSevenDays(double yieldLastSevenDays) {
        this.yieldLastSevenDays = yieldLastSevenDays;
    }

    public double getYieldYTD() {
        return yieldYTD;
    }

    public void setYieldYTD(double yieldYTD) {
        this.yieldYTD = yieldYTD;
    }
}
