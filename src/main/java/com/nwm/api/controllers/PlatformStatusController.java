/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.EmailAnnouncementRequest;
import com.nwm.api.entities.SystemAnnouncementEntity;
import com.nwm.api.services.PlatformStatusService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/platform-status")
public class PlatformStatusController extends BaseController {
	@Autowired
	PlatformStatusService service;
	
	/**
	 * @description get system announcement
	 * @author Hung.Bui
	 * @since 2026-09-04
	 */
	@PostMapping("/system-announcement/get")
	public Object getSystemAnnouncement() {
		SystemAnnouncementEntity obj = service.getSystemAnnouncement();
		return Objects.nonNull(obj) ? this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1) : this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
	}
	
	/**
	 * @description save system announcement
	 * @author Hung.Bui
	 * @since 2026-09-04
	 * @param obj
	 */
	@PostMapping("/system-announcement/save")
	public Object saveSystemAnnouncement(@RequestBody SystemAnnouncementEntity obj) {
		return service.saveSystemAnnouncement(obj) ? this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, obj, 1) : this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
	}
	
	/**
	 * @description send mail system announcement
	 * @author Hung.Bui
	 * @since 2026-09-04
	 * @param obj
	 */
	@PostMapping("/system-announcement/send-mail")
	public Object sendMailSystemAnnouncement(@RequestBody EmailAnnouncementRequest obj) {
		return service.sendMailSystemAnnouncement(obj) ? this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj) : this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null);
	}
}
