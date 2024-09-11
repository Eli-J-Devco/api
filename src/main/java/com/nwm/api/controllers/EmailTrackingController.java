/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.AvatarEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.EmailTrackingEntity;
import com.nwm.api.entities.ModelCellModemEntity;
import com.nwm.api.entities.ModelDataloggerEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.entities.TablePreferenceEntity;
import com.nwm.api.services.AlertService;
import com.nwm.api.services.AvatarService;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.ModelCellModemService;
import com.nwm.api.services.ModelDataloggerService;
import com.nwm.api.services.SiteService;
import com.nwm.api.services.EmailTrackingService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/email-tracking")
public class EmailTrackingController extends BaseController {
	
	/**
	 * @description Get info setup
	 * @author long.pham
	 * @since 2024-08-27
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/get-info-setup")
	public Object getDetailSite(@RequestBody SitesDevicesEntity obj) {
		try {			
			EmailTrackingService service = new EmailTrackingService();
			SitesDevicesEntity getDetail = service.getDetail(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description save email tracking setup 
	 * @author long.pham
	 * @since 2021-01-05
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object saveEmailTrackingSetup(@RequestBody SitesDevicesEntity obj) {
		try {
			EmailTrackingService service = new EmailTrackingService();
			boolean insert = service.updateSiteEmailTrackingSetup(obj);
			return insert ? this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1) : this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
		}
	}
	
	
	
	/**
	 * @description Get data charting for email tracking
	 * @author long.pham
	 * @since 2024-08-27
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/get-data-charting-for-email-tracking")
	public Object getDataChartingForEmailTracking(@RequestBody EmailTrackingEntity obj) {
		try {			
			EmailTrackingService service = new EmailTrackingService();
			EmailTrackingEntity getDetail = service.getDataChartingForEmailTracking(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description Get data summary for email tracking
	 * @author long.pham
	 * @since 2024-08-27
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/get-data-summary-for-email-tracking")
	public Object getDataSummaryForEmailTracking(@RequestBody EmailTrackingEntity obj) {
		try {			
			EmailTrackingService service = new EmailTrackingService();
			EmailTrackingEntity getDetail = service.getDataSummaryForEmailTracking(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list alert for email tracking
	 * @author long.pham
	 * @since 2024-09-04
	 * @return data (status, message, array, total_row)
	 */
	
	@PostMapping("/get-list-alert-for-email-tracking")
    public Object getListAlertsForEmailTracking(@RequestBody AlertEntity obj){
		try {
			if(obj.getLimit() == 0) {
				obj.setLimit(1000);
			}
			
			EmailTrackingService service = new EmailTrackingService();
			List data = service.getListAlertsEmailTracking(obj);
			int totalRecord = service.getListAlertsEmailTrackingCount(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord, null);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0, null);
		}
    }
	
	
	
	/**
	 * @description delete queue
	 * @author long.pham
	 * @since 2024-09-10
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-alert-queue")
	public Object deleteALertQueue(@Valid @RequestBody AlertEntity obj) {
		EmailTrackingService service = new EmailTrackingService();
		try {
			boolean result = service.deleteALertQueue(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description run process queue
	 * @author long.pham
	 * @since 2024-09-10
	 * @param id
	 * @return {}
	 */
	@PostMapping("/process-alert-queue")
	public Object processALertQueue(@Valid @RequestBody AlertEntity obj) {  
		try {
			EmailTrackingService service = new EmailTrackingService();
			boolean insert = service.processALertQueue(obj);
			return insert ? this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1) : this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
		}
	}
}
