/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.EmailAnnouncementRequest;
import com.nwm.api.entities.EmployeeManageEntity;
import com.nwm.api.entities.StatusManagementCategoryEntity;
import com.nwm.api.entities.StatusManagementEventEntity;
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

	public List getStatusManagementCategories(boolean archived) {
		try {
			StatusManagementCategoryEntity request = new StatusManagementCategoryEntity();
			request.setArchived(archived);
			List data = queryForList("StatusManagement.getCategories", request);
			return data == null ? new ArrayList() : data;
		} catch (Exception ex) {
			log.error("StatusManagement.getCategories", ex);
			return new ArrayList();
		}
	}

	public StatusManagementCategoryEntity getStatusManagementCategory(Integer id) {
		if (id == null) return null;
		try {
			StatusManagementCategoryEntity request = new StatusManagementCategoryEntity();
			request.setId(id);
			return (StatusManagementCategoryEntity) queryForObject("StatusManagement.getCategoryById", request);
		} catch (Exception ex) {
			log.error("StatusManagement.getCategoryById", ex);
			return null;
		}
	}

	public StatusManagementCategoryEntity addStatusManagementCategory(StatusManagementCategoryEntity request) {
		if (request == null || clean(request.getName()).isEmpty() || clean(request.getName()).length() > 191
				|| checkStatusManagementCategoryName(request.getName()) > 0) return null;
		SqlSession session = beginTransaction();
		if (session == null) return null;
		try {
			request.setName(clean(request.getName()));
			session.insert("StatusManagement.insertCategory", request);
			session.commit();
			return getStatusManagementCategory(request.getId());
		} catch (Exception ex) {
			session.rollback();
			log.error("StatusManagement.insertCategory", ex);
			return null;
		} finally {
			session.close();
		}
	}

	public boolean setStatusManagementCategoryArchived(Integer id, boolean archived) {
		if (id == null) return false;
		StatusManagementCategoryEntity request = new StatusManagementCategoryEntity();
		request.setId(id);
		request.setArchived(archived);
		try {
			return update("StatusManagement.setCategoryArchived", request) > 0;
		} catch (Exception ex) {
			log.error("StatusManagement.setCategoryArchived", ex);
			return false;
		}
	}

	public boolean deleteArchivedStatusManagementCategory(Integer id) {
		if (id == null) return false;
		try {
			StatusManagementCategoryEntity request = new StatusManagementCategoryEntity();
			request.setId(id);
			return delete("StatusManagement.deleteArchivedCategory", request) > 0;
		} catch (Exception ex) {
			log.error("StatusManagement.deleteArchivedCategory", ex);
			return false;
		}
	}

	public StatusManagementCategoryEntity addStatusManagementEvent(StatusManagementEventEntity request) {
		if (!validStatusManagementEventRequest(request)) return null;
		StatusManagementCategoryEntity category = getStatusManagementCategory(request.getIdCategory());
		if (category == null || Boolean.TRUE.equals(category.getArchived()) || hasOpenStatusManagementEvent(request.getIdCategory())) return null;
		SqlSession session = beginTransaction();
		if (session == null) return null;
		try {
			StatusManagementEventEntity event = toStatusManagementEvent(request, category.getId());
			event.setStatusNumber(nextStatusManagementNumber(session));
			session.insert("StatusManagement.insertEvent", event);
			session.commit();
			return getStatusManagementCategory(category.getId());
		} catch (Exception ex) {
			session.rollback();
			log.error("StatusManagement.insertEvent", ex);
			return null;
		} finally {
			session.close();
		}
	}

	public StatusManagementCategoryEntity updateStatusManagementEvent(StatusManagementEventEntity request) {
		if (request == null || request.getId() == null || !validStatus(request.getStatus())) return null;
		StatusManagementEventEntity current = getOpenStatusManagementEventById(request.getId());
		if (current == null) return null;
		SqlSession session = beginTransaction();
		if (session == null) return null;
		try {
			StatusManagementEventEntity event = toStatusManagementEvent(request, current.getIdCategory());
			event.setId(current.getId());
			event.setStatusNumber(nextStatusManagementNumber(session));
			session.update("StatusManagement.updateEvent", event);
			session.commit();
			return getStatusManagementCategory(current.getIdCategory());
		} catch (Exception ex) {
			session.rollback();
			log.error("StatusManagement.updateEvent", ex);
			return null;
		} finally {
			session.close();
		}
	}

	public int closeStatusManagementEvents(StatusManagementEventEntity request) {
		if (request == null) return 0;
		List<Integer> eventIds = request.getSelectedEvents();
		if (eventIds == null || eventIds.isEmpty()) return 0;
		SqlSession session = beginTransaction();
		if (session == null) return 0;
		int closed = 0;
		try {
			for (Integer eventId : eventIds) {
				if (eventId == null) continue;
				StatusManagementEventEntity eventRequest = new StatusManagementEventEntity();
				eventRequest.setId(eventId);
				StatusManagementEventEntity event = (StatusManagementEventEntity) session.selectOne("StatusManagement.getOpenEventById", eventRequest);
				if (event == null) continue;
				StatusManagementEventEntity closing = toStatusManagementEvent(request, event.getIdCategory());
				closing.setStatus("Operational");
				closing.setId(event.getId());
				closing.setStatusNumber(nextStatusManagementNumber(session));
				if (session.update("StatusManagement.closeEvent", closing) > 0) closed++;
			}
			session.commit();
			return closed;
		} catch (Exception ex) {
			session.rollback();
			log.error("StatusManagement.closeEvent", ex);
			return 0;
		} finally {
			session.close();
		}
	}

	private int checkStatusManagementCategoryName(String name) {
		try {
			StatusManagementCategoryEntity request = new StatusManagementCategoryEntity();
			request.setName(clean(name));
			Integer count = (Integer) queryForObject("StatusManagement.checkCategoryName", request);
			return count == null ? 0 : count;
		} catch (Exception ex) {
			log.error("StatusManagement.checkCategoryName", ex);
			return 0;
		}
	}

	private StatusManagementEventEntity getOpenStatusManagementEvent(StatusManagementEventEntity request) {
		try {
			return (StatusManagementEventEntity) queryForObject("StatusManagement.getOpenEventByCategory", request);
		} catch (Exception ex) {
			log.error("StatusManagement.getOpenEventByCategory", ex);
			return null;
		}
	}

	private StatusManagementEventEntity getOpenStatusManagementEventById(Integer id) {
		try {
			StatusManagementEventEntity request = new StatusManagementEventEntity();
			request.setId(id);
			return (StatusManagementEventEntity) queryForObject("StatusManagement.getOpenEventById", request);
		} catch (Exception ex) {
			log.error("StatusManagement.getOpenEventById", ex);
			return null;
		}
	}

	private String nextStatusManagementNumber(SqlSession session) {
		try {
			String current = (String) session.selectOne("StatusManagement.getLastStatusNumber");
			return nextStatusManagementNumber(current);
		} catch (Exception ex) {
			log.error("StatusManagement.getLastStatusNumber", ex);
			return "001A";
		}
	}

	private boolean hasOpenStatusManagementEvent(Integer categoryId) {
		StatusManagementEventEntity request = new StatusManagementEventEntity();
		request.setIdCategory(categoryId);
		return getOpenStatusManagementEvent(request) != null;
	}

	private StatusManagementEventEntity toStatusManagementEvent(StatusManagementEventEntity request, Integer categoryId) {
		StatusManagementEventEntity event = new StatusManagementEventEntity();
		event.setIdCategory(categoryId);
		event.setStatus(clean(request.getStatus()).isEmpty() ? "Operational" : clean(request.getStatus()));
		event.setNotes(firstNonEmpty(request.getNotes(), request.getClosingNotes(), "Operating normally."));
		event.setAdminNotes(firstNonEmpty(request.getAdminNotes(), null, ""));
		event.setUpdatedBy(firstNonEmpty(request.getUpdatedBy(), null, "system"));
		return event;
	}

	private boolean validStatusManagementEventRequest(StatusManagementEventEntity request) {
		if (request == null || request.getIdCategory() == null) return false;
		return validStatus(request.getStatus());
	}

	private boolean validStatus(String status) {
		String value = clean(status);
		return value.isEmpty() || "Operational".equals(value) || "Limited Operations".equals(value) || "Not Operational".equals(value);
	}

	private static String nextStatusManagementNumber(String statusNumber) {
		String value = clean(statusNumber).toUpperCase();
		if (value.isEmpty()) return "001A";
		int split = 0;
		while (split < value.length() && Character.isDigit(value.charAt(split))) split++;
		if (split == 0 || split == value.length()) return "001A";
		String number = value.substring(0, split);
		String letters = value.substring(split);
		char[] chars = letters.toCharArray();
		for (char character : chars) {
			if (character < 'A' || character > 'Z') return "001A";
		}
		int index = chars.length - 1;
		while (index >= 0 && chars[index] == 'Z') {
			chars[index] = 'A';
			index--;
		}
		if (index < 0) return number + "A" + new String(chars);
		chars[index]++;
		return number + new String(chars);
	}

	private static String firstNonEmpty(String first, String second, String fallback) {
		if (!clean(first).isEmpty()) return first.trim();
		if (!clean(second).isEmpty()) return second.trim();
		return fallback;
	}

	private static String clean(String value) {
		return value == null ? "" : value.trim();
	}
}
