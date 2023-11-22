/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelLufftClass8020Entity {
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
	private double PrecipitationType;
	private double WindMeasurementQuality;
	private double IrradianceActual;
	private double IrradianceMin;
	private double IrradianceMax;
	private double IrradianceAvg;
	private double AirTemperatureActual;
	private double AirTemperatureMin;
	private double AirTemperatureMax;
	private double AirTemperatureAvg;
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
	private double PrecipitationQuantityAbsolute;
	private double PrecipitationQuantityDifferential;
	private double PrecipitationIntensity;
	private double AbsoluteHumidityActual;
	private double AbsoluteHumidityMin;
	private double AbsoluteHumidityMax;
	private double AbsoluteHumidityAvg;
	private double MixingRatioActual;
	private double MixingRatioMin;
	private double MixingRatioMax;
	private double MixingRatioAvg;
	private double AbsoluteAirPressureActual;
	private double AbsoluteAirPressureMin;
	private double AbsoluteAirPressureMax;
	private double AbsoluteAirPressureAvg;
	private double WindSpeedStandardDeviation;
	private double WindDirectionStandardDeviation;
	private double WetBulbTemperature;
	private double SpecificEnthalpy;
	private double AirDensityActual;
	private double LeafWetnessActual;
	private double LeafWetnessMin;
	private double LeafWetnessMax;
	private double LeafWetnessAvg;
	private double LeafWetnessState;
	private double ExternalTemperature;
	private double WindValueQualityFast;
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
	
	public double getNvm_temperature() {
		return nvm_temperature;
	}
	public void setNvm_temperature(double nvm_temperature) {
		this.nvm_temperature = nvm_temperature;
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
	public double getPrecipitationType() {
		return PrecipitationType;
	}
	public void setPrecipitationType(double precipitationType) {
		PrecipitationType = precipitationType;
	}
	public double getWindMeasurementQuality() {
		return WindMeasurementQuality;
	}
	public void setWindMeasurementQuality(double windMeasurementQuality) {
		WindMeasurementQuality = windMeasurementQuality;
	}
	public double getIrradianceActual() {
		return IrradianceActual;
	}
	public void setIrradianceActual(double irradianceActual) {
		IrradianceActual = irradianceActual;
	}
	public double getIrradianceMin() {
		return IrradianceMin;
	}
	public void setIrradianceMin(double irradianceMin) {
		IrradianceMin = irradianceMin;
	}
	public double getIrradianceMax() {
		return IrradianceMax;
	}
	public void setIrradianceMax(double irradianceMax) {
		IrradianceMax = irradianceMax;
	}
	public double getIrradianceAvg() {
		return IrradianceAvg;
	}
	public void setIrradianceAvg(double irradianceAvg) {
		IrradianceAvg = irradianceAvg;
	}
	public double getAirTemperatureActual() {
		return AirTemperatureActual;
	}
	public void setAirTemperatureActual(double airTemperatureActual) {
		AirTemperatureActual = airTemperatureActual;
	}
	public double getAirTemperatureMin() {
		return AirTemperatureMin;
	}
	public void setAirTemperatureMin(double airTemperatureMin) {
		AirTemperatureMin = airTemperatureMin;
	}
	public double getAirTemperatureMax() {
		return AirTemperatureMax;
	}
	public void setAirTemperatureMax(double airTemperatureMax) {
		AirTemperatureMax = airTemperatureMax;
	}
	public double getAirTemperatureAvg() {
		return AirTemperatureAvg;
	}
	public void setAirTemperatureAvg(double airTemperatureAvg) {
		AirTemperatureAvg = airTemperatureAvg;
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
	public double getPrecipitationQuantityAbsolute() {
		return PrecipitationQuantityAbsolute;
	}
	public void setPrecipitationQuantityAbsolute(double precipitationQuantityAbsolute) {
		PrecipitationQuantityAbsolute = precipitationQuantityAbsolute;
	}
	public double getPrecipitationQuantityDifferential() {
		return PrecipitationQuantityDifferential;
	}
	public void setPrecipitationQuantityDifferential(double precipitationQuantityDifferential) {
		PrecipitationQuantityDifferential = precipitationQuantityDifferential;
	}
	public double getPrecipitationIntensity() {
		return PrecipitationIntensity;
	}
	public void setPrecipitationIntensity(double precipitationIntensity) {
		PrecipitationIntensity = precipitationIntensity;
	}
	public double getAbsoluteHumidityActual() {
		return AbsoluteHumidityActual;
	}
	public void setAbsoluteHumidityActual(double absoluteHumidityActual) {
		AbsoluteHumidityActual = absoluteHumidityActual;
	}
	public double getAbsoluteHumidityMin() {
		return AbsoluteHumidityMin;
	}
	public void setAbsoluteHumidityMin(double absoluteHumidityMin) {
		AbsoluteHumidityMin = absoluteHumidityMin;
	}
	public double getAbsoluteHumidityMax() {
		return AbsoluteHumidityMax;
	}
	public void setAbsoluteHumidityMax(double absoluteHumidityMax) {
		AbsoluteHumidityMax = absoluteHumidityMax;
	}
	public double getAbsoluteHumidityAvg() {
		return AbsoluteHumidityAvg;
	}
	public void setAbsoluteHumidityAvg(double absoluteHumidityAvg) {
		AbsoluteHumidityAvg = absoluteHumidityAvg;
	}
	public double getMixingRatioActual() {
		return MixingRatioActual;
	}
	public void setMixingRatioActual(double mixingRatioActual) {
		MixingRatioActual = mixingRatioActual;
	}
	public double getMixingRatioMin() {
		return MixingRatioMin;
	}
	public void setMixingRatioMin(double mixingRatioMin) {
		MixingRatioMin = mixingRatioMin;
	}
	public double getMixingRatioMax() {
		return MixingRatioMax;
	}
	public void setMixingRatioMax(double mixingRatioMax) {
		MixingRatioMax = mixingRatioMax;
	}
	public double getMixingRatioAvg() {
		return MixingRatioAvg;
	}
	public void setMixingRatioAvg(double mixingRatioAvg) {
		MixingRatioAvg = mixingRatioAvg;
	}
	public double getAbsoluteAirPressureActual() {
		return AbsoluteAirPressureActual;
	}
	public void setAbsoluteAirPressureActual(double absoluteAirPressureActual) {
		AbsoluteAirPressureActual = absoluteAirPressureActual;
	}
	public double getAbsoluteAirPressureMin() {
		return AbsoluteAirPressureMin;
	}
	public void setAbsoluteAirPressureMin(double absoluteAirPressureMin) {
		AbsoluteAirPressureMin = absoluteAirPressureMin;
	}
	public double getAbsoluteAirPressureMax() {
		return AbsoluteAirPressureMax;
	}
	public void setAbsoluteAirPressureMax(double absoluteAirPressureMax) {
		AbsoluteAirPressureMax = absoluteAirPressureMax;
	}
	public double getAbsoluteAirPressureAvg() {
		return AbsoluteAirPressureAvg;
	}
	public void setAbsoluteAirPressureAvg(double absoluteAirPressureAvg) {
		AbsoluteAirPressureAvg = absoluteAirPressureAvg;
	}
	public double getWindSpeedStandardDeviation() {
		return WindSpeedStandardDeviation;
	}
	public void setWindSpeedStandardDeviation(double windSpeedStandardDeviation) {
		WindSpeedStandardDeviation = windSpeedStandardDeviation;
	}
	public double getWindDirectionStandardDeviation() {
		return WindDirectionStandardDeviation;
	}
	public void setWindDirectionStandardDeviation(double windDirectionStandardDeviation) {
		WindDirectionStandardDeviation = windDirectionStandardDeviation;
	}
	public double getWetBulbTemperature() {
		return WetBulbTemperature;
	}
	public void setWetBulbTemperature(double wetBulbTemperature) {
		WetBulbTemperature = wetBulbTemperature;
	}
	public double getSpecificEnthalpy() {
		return SpecificEnthalpy;
	}
	public void setSpecificEnthalpy(double specificEnthalpy) {
		SpecificEnthalpy = specificEnthalpy;
	}
	public double getAirDensityActual() {
		return AirDensityActual;
	}
	public void setAirDensityActual(double airDensityActual) {
		AirDensityActual = airDensityActual;
	}
	public double getLeafWetnessActual() {
		return LeafWetnessActual;
	}
	public void setLeafWetnessActual(double leafWetnessActual) {
		LeafWetnessActual = leafWetnessActual;
	}
	public double getLeafWetnessMin() {
		return LeafWetnessMin;
	}
	public void setLeafWetnessMin(double leafWetnessMin) {
		LeafWetnessMin = leafWetnessMin;
	}
	public double getLeafWetnessMax() {
		return LeafWetnessMax;
	}
	public void setLeafWetnessMax(double leafWetnessMax) {
		LeafWetnessMax = leafWetnessMax;
	}
	public double getLeafWetnessAvg() {
		return LeafWetnessAvg;
	}
	public void setLeafWetnessAvg(double leafWetnessAvg) {
		LeafWetnessAvg = leafWetnessAvg;
	}
	public double getLeafWetnessState() {
		return LeafWetnessState;
	}
	public void setLeafWetnessState(double leafWetnessState) {
		LeafWetnessState = leafWetnessState;
	}
	public double getExternalTemperature() {
		return ExternalTemperature;
	}
	public void setExternalTemperature(double externalTemperature) {
		ExternalTemperature = externalTemperature;
	}
	public double getWindValueQualityFast() {
		return WindValueQualityFast;
	}
	public void setWindValueQualityFast(double windValueQualityFast) {
		WindValueQualityFast = windValueQualityFast;
	}
	public double getNvm_irradiance() {
		return nvm_irradiance;
	}
	public void setNvm_irradiance(double nvm_irradiance) {
		this.nvm_irradiance = nvm_irradiance;
	}
	
	
	
	
	
	
	
}
