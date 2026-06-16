package com.nwm.api.entities.mobile.device;

public class InverterAvailabilitySummary {
    private int inverterOffilne;
    private int totalInverter;
    private int inverterOnline;

    public int getInverterOffilne() {
		return this.inverterOffilne;
	}
	
	public void setInverterOffilne(int inverterOffilne) {
		this.inverterOffilne = inverterOffilne;
	}

    public int getTotalInverter() {
		return this.totalInverter;
	}
	
	public void setTotalInverter(int totalInverter) {
		this.totalInverter = totalInverter;
	}
    
    public int getInverterOnline() {
		return this.inverterOnline;
	}
	
	public void setInverterOnline(int inverterOnline) {
		this.inverterOnline = inverterOnline;
	}
}
