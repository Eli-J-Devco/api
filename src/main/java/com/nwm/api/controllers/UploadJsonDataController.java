/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

 

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@RestController
@ApiIgnore
@RequestMapping("/files")
public class UploadJsonDataController extends BaseController {
	public static String message = "";

	@Autowired
	private com.nwm.api.services.UploadJsonIngestService uploadJsonIngestService;

	/**
	 * Accept JSON payload (no file I/O) and process datalogger data.
	 * Mirrors the high-level flow of UploadFilesController: validate → find devices
	 * by serial → update device last_updated → insert datalogger metadata row.
	 */
	@PostMapping(value = "/upload-json", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    public String uploadJson(@RequestBody UploadJsonRequest body) {
		return uploadJsonIngestService.ingest(body);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class UploadJsonRequest {
		@JsonProperty("SENDDATATRACE")
		public String senddatatrace;
		@JsonProperty("MODE")
		public String mode;
		@JsonProperty("SERIALNUMBER")
		public String serialnumber;
		@JsonProperty("PASSWORD")
		public String password;
		@JsonProperty("LOOPNAME")
		public String loopname;
		@JsonProperty("MODBUSIP")
		public String modbusip;
		@JsonProperty("MODBUSPORT")
		public String modbusport;
		@JsonProperty("MODBUSDEVICE")
		public String modbusdevice;
		@JsonProperty("MODBUSDEVICENAME")
		public String modbusdevicename;
		@JsonProperty("MODBUSDEVICETYPE")
		public String modbusdevicetype;
		@JsonProperty("MODBUSDEVICETYPENUMBER")
		public String modbusdevicetypenumber;
		@JsonProperty("MODBUSDEVICECLASS")
		public String modbusdeviceclass;
		@JsonProperty("LINES")
		public List<String> lines;
		@JsonProperty("DATA")
		public List<String> data;
		@JsonProperty("DATATABLE")
		public String datatable;
		@JsonProperty("DATALOGGER_TABLE")
		public String dataloggerTable;
	}
}
                        