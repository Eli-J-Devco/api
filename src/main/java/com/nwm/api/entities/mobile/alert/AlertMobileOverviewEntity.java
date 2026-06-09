package com.nwm.api.entities.mobile.alert;

public class AlertMobileOverviewEntity {
    private int total = 0;
    private int highPriority = 0;
    private int medPriority = 0;
    private int lowPriority = 0;

    public int getHighPriority(){
        return this.highPriority;
    }
    public int getLowPriority(){
        return this.lowPriority;
    }
    public int getMedPriority(){
        return this.medPriority;
    }
    public int getTotal(){
        return this.total;
    }

    public void setTotal(int _total){
        this.total = _total;
    }
    public void setHighPriority(int _highPriority){
        this.highPriority = _highPriority;
    }
    public void setMedPriority(int _medPriority){
        this.medPriority = _medPriority;
    }
    public void setLowPriority(int _lowPriority){
        this.lowPriority = _lowPriority;
    }
}
