/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.EmailAnnouncementRequest;
import com.nwm.api.entities.StatusManagementCategoryEntity;
import com.nwm.api.entities.StatusManagementEventEntity;
import com.nwm.api.entities.SystemAnnouncementEntity;
import com.nwm.api.services.PlatformStatusService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/platform-status")
public class PlatformStatusController extends BaseController {
	@Autowired
	private PlatformStatusService service;
	
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

	/**
	 * Return active or archived status-management categories.
	 */
	@PostMapping("/category/list")
	public Object getCategoryList(@RequestBody(required = false) StatusManagementCategoryEntity obj) {
		try {
			boolean archived = obj != null && Boolean.TRUE.equals(obj.getArchived());
			List data = service.getStatusManagementCategories(archived);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception ex) {
			log.error("PlatformStatus.getCategoryList", ex);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}

	@PostMapping("/category/add")
	public Object addCategory(@RequestBody StatusManagementCategoryEntity obj) {
		try {
			if (obj == null || obj.getName() == null || obj.getName().trim().isEmpty()) {
				return this.jsonResult(false, "Category name is required", null, 0);
			}
			Object data = service.addStatusManagementCategory(obj);
			return data != null ? this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1)
					: this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
		} catch (Exception ex) {
			log.error("PlatformStatus.addCategory", ex);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
		}
	}

	@PostMapping("/category/archive")
	public Object archiveCategory(@RequestBody StatusManagementCategoryEntity obj) {
		return archive(obj, true);
	}

	@PostMapping("/category/restore")
	public Object restoreCategory(@RequestBody StatusManagementCategoryEntity obj) {
		return archive(obj, false);
	}

	private Object archive(StatusManagementCategoryEntity obj, boolean archived) {
		try {
			boolean result = service.setStatusManagementCategoryArchived(obj == null ? null : obj.getId(), archived);
			if (result) {
				String message = archived ? Constants.UPDATE_SUCCESS_MSG : Constants.RESTORE_SUCCESS_MSG;
				return this.jsonResult(true, message, obj, 1);
			}
			return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
		} catch (Exception ex) {
			log.error("PlatformStatus.archiveCategory", ex);
			return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
		}
	}

	@PostMapping("/category/delete-permanently")
	public Object deleteArchivedCategory(@RequestBody StatusManagementCategoryEntity obj) {
		try {
			boolean result = service.deleteArchivedStatusManagementCategory(obj == null ? null : obj.getId());
			return result ? this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1)
					: this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception ex) {
			log.error("PlatformStatus.deleteArchivedCategory", ex);
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		}
	}

	@PostMapping("/event/add")
	public Object addEvent(@RequestBody StatusManagementEventEntity obj) {
		try {
			Object data = service.addStatusManagementEvent(obj);
			return data != null ? this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1)
					: this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
		} catch (Exception ex) {
			log.error("PlatformStatus.addEvent", ex);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
		}
	}

	@PostMapping("/event/update")
	public Object updateEvent(@RequestBody StatusManagementEventEntity obj) {
		try {
			Object data = service.updateStatusManagementEvent(obj);
			return data != null ? this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, data, 1)
					: this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
		} catch (Exception ex) {
			log.error("PlatformStatus.updateEvent", ex);
			return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
		}
	}

	@PostMapping("/event/close")
	public Object closeEvents(@RequestBody StatusManagementEventEntity obj) {
		try {
			int closed = service.closeStatusManagementEvents(obj);
			return closed > 0 ? this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, closed)
					: this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
		} catch (Exception ex) {
			log.error("PlatformStatus.closeEvents", ex);
			return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
		}
	}
}
