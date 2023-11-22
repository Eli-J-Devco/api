/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelLufftWS501UMBWeatherEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double RelativeHumidityActual;
	private double RelativeHumidityMin;
	private double RelativeHumidityMax;
	private double RelativeHumidityAvg;
	private double RelativeAirPressureActual;
	private double RelativeAirPressureMin;
	private double RelativeAirPressureMax;
	private double RelativeAirPressureAvg;
	private double WindDirectionActual;
	private double WindDirectionMin;
	private double WindDirectionMax;
	private double WindDirectionVct;
	private double WindDirectionFast;
	private double WindDirectionCompassCorrected;
	private double Compass;
	private double WindMeasurementQuality;
	private double PrecipitationType;
	private double GlobalRadiation;
	private double GlobalRadiation2;
	private double GlobalRadiation3;
	private double GlobalRadiation4;
	private double AirTemperatureCActual;
	private double AirTemperatureCMin;
	private double AirTemperatureCMax;
	private double AirTemperatureCAvg;
	private double DewPointActual;
	private double DewPointMin;
	private double DewPointMax;
	private double DewPointAvg;
	private double WindChillTemperature;
	private double HeatingTemperatureWind;
	private double HeatingTemperatureR2S;
	private double WindSpeedActual;
	private double WindSpeedMin;
	private double WindSpeedMax;
	private double WindSpeedAvg;
	private double WindSpeedVct;
	private double WindSpeedFast;
	private double PrecipitationAbsolute;
	private double PrecipitationDifferential;
	private double PrecipitationIntensity;
	private double AirTemperatureFActual;
	private double AirTemperatureFMin;
	private double AirTemperatureFMax;
	private double AirTemperatureFAvg;
	private double ExternalTemperatureC;
	private double ExternalTemperatureF;
	private double nvm_irradiance;
	private double nvm_temperature;
	
	private double nvm_panel_temperature;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	
	
	
	
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getView_tablename() {
		return view_tablename;
	}
	public void setView_tablename(String view_tablename) {
		this.view_tablename = view_tablename;
	}
	public String getJob_tablename() {
		return job_tablename;
	}
	public void setJob_tablename(String job_tablename) {
		this.job_tablename = job_tablename;
	}
	
	
	public double getNvm_panel_temperature() {
		return nvm_panel_temperature;
	}
	public void setNvm_panel_temperature(double nvm_panel_temperature) {
		this.nvm_panel_temperature = nvm_panel_temperature;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public int getLow_alarm() {
		return low_alarm;
	}
	public void setLow_alarm(int low_alarm) {
		this.low_alarm = low_alarm;
	}
	public int getHigh_alarm() {
		return high_alarm;
	}
	public void setHigh_alarm(int high_alarm) {
		this.high_alarm = high_alarm;
	}
	public double getRelativeHumidityActual() {
		return RelativeHumidityActual;
	}
	public void setRelativeHumidityActual(double relativeHumidityActual) {
		RelativeHumidityActual = relativeHumidityActual;
	}
	public double getRelativeHumidityMin() {
		return RelativeHumidityMin;
	}
	public void setRelativeHumidityMin(double relativeHumidityMin) {
		RelativeHumidityMin = relativeHumidityMin;
	}
	public double getRelativeHumidityMax() {
		return RelativeHumidityMax;
	}
	public void setRelativeHumidityMax(double relativeHumidityMax) {
		RelativeHumidityMax = relativeHumidityMax;
	}
	public double getRelativeHumidityAvg() {
		return RelativeHumidityAvg;
	}
	public void setRelativeHumidityAvg(double relativeHumidityAvg) {
		RelativeHumidityAvg = relativeHumidityAvg;
	}
	public double getRelativeAirPressureActual() {
		return RelativeAirPressureActual;
	}
	public void setRelativeAirPressureActual(double relativeAirPressureActual) {
		RelativeAirPressureActual = relativeAirPressureActual;
	}
	public double getRelativeAirPressureMin() {
		return RelativeAirPressureMin;
	}
	public void setRelativeAirPressureMin(double relativeAirPressureMin) {
		RelativeAirPressureMin = relativeAirPressureMin;
	}
	public double getRelativeAirPressureMax() {
		return RelativeAirPressureMax;
	}
	public void setRelativeAirPressureMax(double relativeAirPressureMax) {
		RelativeAirPressureMax = relativeAirPressureMax;
	}
	public double getRelativeAirPressureAvg() {
		return RelativeAirPressureAvg;
	}
	public void setRelativeAirPressureAvg(double relativeAirPressureAvg) {
		RelativeAirPressureAvg = relativeAirPressureAvg;
	}
	public double getWindDirectionActual() {
		return WindDirectionActual;
	}
	public void setWindDirectionActual(double windDirectionActual) {
		WindDirectionActual = windDirectionActual;
	}
	public double getWindDirectionMin() {
		return WindDirectionMin;
	}
	public void setWindDirectionMin(double windDirectionMin) {
		WindDirectionMin = windDirectionMin;
	}
	public double getWindDirectionMax() {
		return WindDirectionMax;
	}
	public void setWindDirectionMax(double windDirectionMax) {
		WindDirectionMax = windDirectionMax;
	}
	public double getWindDirectionVct() {
		return WindDirectionVct;
	}
	public void setWindDirectionVct(double windDirectionVct) {
		WindDirectionVct = windDirectionVct;
	}
	public double getWindDirectionFast() {
		return WindDirectionFast;
	}
	public void setWindDirectionFast(double windDirectionFast) {
		WindDirectionFast = windDirectionFast;
	}
	public double getWindDirectionCompassCorrected() {
		return WindDirectionCompassCorrected;
	}
	public void setWindDirectionCompassCorrected(double windDirectionCompassCorrected) {
		WindDirectionCompassCorrected = windDirectionCompassCorrected;
	}
	public double getCompass() {
		return Compass;
	}
	public void setCompass(double compass) {
		Compass = compass;
	}
	public double getWindMeasurementQuality() {
		return WindMeasurementQuality;
	}
	public void setWindMeasurementQuality(double windMeasurementQuality) {
		WindMeasurementQuality = windMeasurementQuality;
	}
	public double getPrecipitationType() {
		return PrecipitationType;
	}
	public void setPrecipitationType(double precipitationType) {
		PrecipitationType = precipitationType;
	}
	public double getGlobalRadiation() {
		return GlobalRadiation;
	}
	public void setGlobalRadiation(double globalRadiation) {
		GlobalRadiation = globalRadiation;
	}
	public double getGlobalRadiation2() {
		return GlobalRadiation2;
	}
	public void setGlobalRadiation2(double globalRadiation2) {
		GlobalRadiation2 = globalRadiation2;
	}
	public double getGlobalRadiation3() {
		return GlobalRadiation3;
	}
	public void setGlobalRadiation3(double globalRadiation3) {
		GlobalRadiation3 = globalRadiation3;
	}
	public double getGlobalRadiation4() {
		return GlobalRadiation4;
	}
	public void setGlobalRadiation4(double globalRadiation4) {
		GlobalRadiation4 = globalRadiation4;
	}
	public double getAirTemperatureCActual() {
		return AirTemperatureCActual;
	}
	public void setAirTemperatureCActual(double airTemperatureCActual) {
		AirTemperatureCActual = airTemperatureCActual;
	}
	public double getAirTemperatureCMin() {
		return AirTemperatureCMin;
	}
	public void setAirTemperatureCMin(double airTemperatureCMin) {
		AirTemperatureCMin = airTemperatureCMin;
	}
	public double getAirTemperatureCMax() {
		return AirTemperatureCMax;
	}
	public void setAirTemperatureCMax(double airTemperatureCMax) {
		AirTemperatureCMax = airTemperatureCMax;
	}
	public double getAirTemperatureCAvg() {
		return AirTemperatureCAvg;
	}
	public void setAirTemperatureCAvg(double airTemperatureCAvg) {
		AirTemperatureCAvg = airTemperatureCAvg;
	}
	public double getDewPointActual() {
		return DewPointActual;
	}
	public void setDewPointActual(double dewPointActual) {
		DewPointActual = dewPointActual;
	}
	public double getDewPointMin() {
		return DewPointMin;
	}
	public void setDewPointMin(double dewPointMin) {
		DewPointMin = dewPointMin;
	}
	public double getDewPointMax() {
		return DewPointMax;
	}
	public void setDewPointMax(double dewPointMax) {
		DewPointMax = dewPointMax;
	}
	public double getDewPointAvg() {
		return DewPointAvg;
	}
	public void setDewPointAvg(double dewPointAvg) {
		DewPointAvg = dewPointAvg;
	}
	public double getWindChillTemperature() {
		return WindChillTemperature;
	}
	public void setWindChillTemperature(double windChillTemperature) {
		WindChillTemperature = windChillTemperature;
	}
	public double getHeatingTemperatureWind() {
		return HeatingTemperatureWind;
	}
	public void setHeatingTemperatureWind(double heatingTemperatureWind) {
		HeatingTemperatureWind = heatingTemperatureWind;
	}
	public double getHeatingTemperatureR2S() {
		return HeatingTemperatureR2S;
	}
	public void setHeatingTemperatureR2S(double heatingTemperatureR2S) {
		HeatingTemperatureR2S = heatingTemperatureR2S;
	}
	public double getWindSpeedActual() {
		return WindSpeedActual;
	}
	public void setWindSpeedActual(double windSpeedActual) {
		WindSpeedActual = windSpeedActual;
	}
	public double getWindSpeedMin() {
		return WindSpeedMin;
	}
	public void setWindSpeedMin(double windSpeedMin) {
		WindSpeedMin = windSpeedMin;
	}
	public double getWindSpeedMax() {
		return WindSpeedMax;
	}
	public void setWindSpeedMax(double windSpeedMax) {
		WindSpeedMax = windSpeedMax;
	}
	public double getWindSpeedAvg() {
		return WindSpeedAvg;
	}
	public void setWindSpeedAvg(double windSpeedAvg) {
		WindSpeedAvg = windSpeedAvg;
	}
	public double getWindSpeedVct() {
		return WindSpeedVct;
	}
	public void setWindSpeedVct(double windSpeedVct) {
		WindSpeedVct = windSpeedVct;
	}
	public double getWindSpeedFast() {
		return WindSpeedFast;
	}
	public void setWindSpeedFast(double windSpeedFast) {
		WindSpeedFast = windSpeedFast;
	}
	public double getPrecipitationAbsolute() {
		return PrecipitationAbsolute;
	}
	public void setPrecipitationAbsolute(double precipitationAbsolute) {
		PrecipitationAbsolute = precipitationAbsolute;
	}
	public double getPrecipitationDifferential() {
		return PrecipitationDifferential;
	}
	public void setPrecipitationDifferential(double precipitationDifferential) {
		PrecipitationDifferential = precipitationDifferential;
	}
	public double getPrecipitationIntensity() {
		return PrecipitationIntensity;
	}
	public void setPrecipitationIntensity(double precipitationIntensity) {
		PrecipitationIntensity = precipitationIntensity;
	}
	public double getAirTemperatureFActual() {
		return AirTemperatureFActual;
	}
	public void setAirTemperatureFActual(double airTemperatureFActual) {
		AirTemperatureFActual = airTemperatureFActual;
	}
	public double getAirTemperatureFMin() {
		return AirTemperatureFMin;
	}
	public void setAirTemperatureFMin(double airTemperatureFMin) {
		AirTemperatureFMin = airTemperatureFMin;
	}
	public double getAirTemperatureFMax() {
		return AirTemperatureFMax;
	}
	public void setAirTemperatureFMax(double airTemperatureFMax) {
		AirTemperatureFMax = airTemperatureFMax;
	}
	public double getAirTemperatureFAvg() {
		return AirTemperatureFAvg;
	}
	public void setAirTemperatureFAvg(double airTemperatureFAvg) {
		AirTemperatureFAvg = airTemperatureFAvg;
	}
	public double getExternalTemperatureC() {
		return ExternalTemperatureC;
	}
	public void setExternalTemperatureC(double externalTemperatureC) {
		ExternalTemperatureC = externalTemperatureC;
	}
	public double getExternalTemperatureF() {
		return ExternalTemperatureF;
	}
	public void setExternalTemperatureF(double externalTemperatureF) {
		ExternalTemperatureF = externalTemperatureF;
	}
	public double getNvm_irradiance() {
		return nvm_irradiance;
	}
	public void setNvm_irradiance(double nvm_irradiance) {
		this.nvm_irradiance = nvm_irradiance;
	}
	public double getNvm_temperature() {
		return nvm_temperature;
	}
	public void setNvm_temperature(double nvm_temperature) {
		this.nvm_temperature = nvm_temperature;
	}
	
}
