/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/

package com.nwm.api.entities;

public class MeterWeatherReportDataEntity {
	private String timestamp;

    private String powerHeader;
    private Double power;

    private String energyHeader;
    private Double energy;

    private String irradianceHeader;
    private Double irradiance;

    private String temperatureHeader;
    private Double temperature;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Double getIrradiance() {
        return irradiance;
    }

    public void setIrradiance(Double irradiance) {
        this.irradiance = irradiance;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

	public String getPowerHeader() {
		return powerHeader;
	}

	public void setPowerHeader(String powerHeader) {
		this.powerHeader = powerHeader;
	}

	public String getEnergyHeader() {
		return energyHeader;
	}

	public void setEnergyHeader(String energyHeader) {
		this.energyHeader = energyHeader;
	}

	public String getIrradianceHeader() {
		return irradianceHeader;
	}

	public void setIrradianceHeader(String irradianceHeader) {
		this.irradianceHeader = irradianceHeader;
	}

	public String getTemperatureHeader() {
		return temperatureHeader;
	}

	public void setTemperatureHeader(String temperatureHeader) {
		this.temperatureHeader = temperatureHeader;
	}
}
