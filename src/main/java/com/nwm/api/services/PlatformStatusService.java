/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.EmailAnnouncementRequest;
import com.nwm.api.entities.EmployeeManageEntity;
import com.nwm.api.entities.SystemAnnouncementEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;

@Service
public class PlatformStatusService extends DB {
	/**
	 * @description get system announcement
	 * @author Hung.Bui
	 * @since 2026-09-04
	 * @param obj
	 */
	public SystemAnnouncementEntity getSystemAnnouncement() {
		try {
			return (SystemAnnouncementEntity) queryForObject("PlatformStatus.getSystemAnnouncement", null);
		} catch (Exception ex) {
			log.error("PlatformStatus.getSystemAnnouncement", ex);
			return null;
		}
	}
	
	/**
	 * @description save system announcement
	 * @author Hung.Bui
	 * @since 2026-09-04
	 * @param obj
	 */
	public boolean saveSystemAnnouncement(SystemAnnouncementEntity obj) {
		try {
			return update("PlatformStatus.updateSystemAnnouncement", obj) > 0;
		} catch (Exception ex) {
			log.error("PlatformStatus.updateSystemAnnouncement", ex);
			return false;
		}
	}
	
	/**
	 * @description send mail system announcement
	 * @author Hung.Bui
	 * @since 2026-09-04
	 * @param obj
	 */
	public boolean sendMailSystemAnnouncement(EmailAnnouncementRequest obj) {
		try {
			EmployeeService employeeService = new EmployeeService();
			String subscribers = Optional.ofNullable((List<EmployeeManageEntity>) employeeService.getList(new EmployeeManageEntity()))
					.map(data -> data.stream()
							.filter(item -> item.getStatus() == 1 && item.getIs_delete() == 0)
							.filter(item -> Objects.nonNull(item.getGroup_roles()))
							.filter(item -> item.getGroup_roles().toLowerCase().contains("client"))
							.map(EmployeeManageEntity::getEmail)
							.collect(Collectors.joining(","))
					)
					.orElse("");
			if (subscribers.isEmpty()) return false;
			
			String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
			
			return SendMail.SendGmailTLS(mailFromContact,  "NEXT WAVE ENERGY MONITORING INC", "", "", subscribers, obj.getSubject(), obj.getMessage(), "system_announcement");
		} catch (Exception ex) {
			log.error("PlatformStatus.sendMailSystemAnnouncement", ex);
			return false;
		}
	}
}
