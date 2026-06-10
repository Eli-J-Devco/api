/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelPVMet200Entity extends ModelBaseEntity {
	private double Ambient_Air_Temperature;
	private double Relative_Humidity;
	private double Barometric_Pressure;
	private double Wind_Speed;
	private double Wind_Direction;
	private double Rainfall;
	private double Snowfall;
	private double Precipitation_Type;
	private double Electric_Field;
	private double Surface_Wetness;
	private double Soil_Moisture;
	private double Global_Horizontal_Irradiance;
	private double POA_Irradiance;
	private double Diffuse_Irradiance;
	private double Direct_Irradiance;
	private double Other_Irradiance;
	private double BOM_Temp_1;
	private double BOM_Temp_2;
	private double BOM_Temp_3;
	
	public double getAmbient_Air_Temperature() {
		return Ambient_Air_Temperature;
	}
	public void setAmbient_Air_Temperature(double ambient_Air_Temperature) {
		Ambient_Air_Temperature = ambient_Air_Temperature;
	}
	public double getRelative_Humidity() {
		return Relative_Humidity;
	}
	public void setRelative_Humidity(double relative_Humidity) {
		Relative_Humidity = relative_Humidity;
	}
	public double getBarometric_Pressure() {
		return Barometric_Pressure;
	}
	public void setBarometric_Pressure(double barometric_Pressure) {
		Barometric_Pressure = barometric_Pressure;
	}
	public double getWind_Speed() {
		return Wind_Speed;
	}
	public void setWind_Speed(double wind_Speed) {
		Wind_Speed = wind_Speed;
	}
	public double getWind_Direction() {
		return Wind_Direction;
	}
	public void setWind_Direction(double wind_Direction) {
		Wind_Direction = wind_Direction;
	}
	public double getRainfall() {
		return Rainfall;
	}
	public void setRainfall(double rainfall) {
		Rainfall = rainfall;
	}
	public double getSnowfall() {
		return Snowfall;
	}
	public void setSnowfall(double snowfall) {
		Snowfall = snowfall;
	}
	public double getPrecipitation_Type() {
		return Precipitation_Type;
	}
	public void setPrecipitation_Type(double precipitation_Type) {
		Precipitation_Type = precipitation_Type;
	}
	public double getSurface_Wetness() {
		return Surface_Wetness;
	}
	public void setSurface_Wetness(double surface_Wetness) {
		Surface_Wetness = surface_Wetness;
	}
	public double getElectric_Field() {
		return Electric_Field;
	}
	public void setElectric_Field(double electric_Field) {
		Electric_Field = electric_Field;
	}
	public double getSoil_Moisture() {
		return Soil_Moisture;
	}
	public void setSoil_Moisture(double soil_Moisture) {
		Soil_Moisture = soil_Moisture;
	}
	public double getGlobal_Horizontal_Irradiance() {
		return Global_Horizontal_Irradiance;
	}
	public void setGlobal_Horizontal_Irradiance(double global_Horizontal_Irradiance) {
		Global_Horizontal_Irradiance = global_Horizontal_Irradiance;
	}
	public double getPOA_Irradiance() {
		return POA_Irradiance;
	}
	public void setPOA_Irradiance(double pOA_Irradiance) {
		POA_Irradiance = pOA_Irradiance;
	}
	public double getDiffuse_Irradiance() {
		return Diffuse_Irradiance;
	}
	public void setDiffuse_Irradiance(double diffuse_Irradiance) {
		Diffuse_Irradiance = diffuse_Irradiance;
	}
	public double getDirect_Irradiance() {
		return Direct_Irradiance;
	}
	public void setDirect_Irradiance(double direct_Irradiance) {
		Direct_Irradiance = direct_Irradiance;
	}
	public double getOther_Irradiance() {
		return Other_Irradiance;
	}
	public void setOther_Irradiance(double other_Irradiance) {
		Other_Irradiance = other_Irradiance;
	}
	public double getBOM_Temp_1() {
		return BOM_Temp_1;
	}
	public void setBOM_Temp_1(double bOM_Temp_1) {
		BOM_Temp_1 = bOM_Temp_1;
	}
	public double getBOM_Temp_2() {
		return BOM_Temp_2;
	}
	public void setBOM_Temp_2(double bOM_Temp_2) {
		BOM_Temp_2 = bOM_Temp_2;
	}
	public double getBOM_Temp_3() {
		return BOM_Temp_3;
	}
	public void setBOM_Temp_3(double bOM_Temp_3) {
		BOM_Temp_3 = bOM_Temp_3;
	}
}
