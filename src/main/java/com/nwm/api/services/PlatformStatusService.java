/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.services;

import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SystemAnnouncementEntity;

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
}
