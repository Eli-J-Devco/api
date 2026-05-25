/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class SiteAiModelEntity {
	private int id;
	private String time;
	private int id_site;
	private String ai_model_name;
	private String model_name;
	private String output_fields;
	private String input_datas;
	private String predictions;
	private String data_send_time;
	private int year;
	
	
	
	
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getData_send_time() {
		return data_send_time;
	}
	public void setData_send_time(String data_send_time) {
		this.data_send_time = data_send_time;
	}
	public String getPredictions() {
		return predictions;
	}
	public void setPredictions(String predictions) {
		this.predictions = predictions;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInput_datas() {
		return input_datas;
	}
	public void setInput_datas(String input_datas) {
		this.input_datas = input_datas;
	}
	public String getAi_model_name() {
		return ai_model_name;
	}
	public void setAi_model_name(String ai_model_name) {
		this.ai_model_name = ai_model_name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public String getModel_name() {
		return model_name;
	}
	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}
	public String getOutput_fields() {
		return output_fields;
	}
	public void setOutput_fields(String output_fields) {
		this.output_fields = output_fields;
	}
	
	
}
