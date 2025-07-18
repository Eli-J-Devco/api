/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ActivityLogsEntity;
import com.nwm.api.entities.AdminSettingGroupEntity;
import com.nwm.api.entities.EmployeeManageEntity;
import com.nwm.api.entities.EmployeeSiteMapEntity;

public class AdminSettingGroupService extends DB {

	/**
	 * @description get list Employee
	 * @author long.pham
	 * @since 2021-01-06
	 */

	public List getListActivityLogs(ActivityLogsEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AdminSettingGroup.getListActivityLogs", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	/**
	 * @description get total record Employee
	 * @author long.pham
	 * @since 2021-01-06
	 */
	public int getTotalRecordActivityLogs(ActivityLogsEntity obj) {
		try {
			return (int) queryForObject("AdminSettingGroup.getListCountActivityLogs", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description get list dropdown group
	 * @author long.pham
	 * @since 2022-12-16
	 * @returns array
	 */
	
	public List getDropdownList(AdminSettingGroupEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AdminSettingGroup.getDropdownList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list group 
	 * @author long.pham
	 * @since 2022-12-16
	 * @param {}
	 */
	
	
	public List getList(AdminSettingGroupEntity obj) {
		try {
			List dataList = queryForList("AdminSettingGroup.getList", obj);
			if (dataList == null) return new ArrayList();
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	public int getTotalRecord(AdminSettingGroupEntity obj) {
		try {
			return (int)queryForObject("AdminSettingGroup.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	public int getTotalSiteById(AdminSettingGroupEntity obj) {
		try {
			return (int)queryForObject("AdminSettingGroup.getTotalSiteById", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	public int getTotalSiteInSubGroupById(AdminSettingGroupEntity obj) {
		try {
			return (int)queryForObject("AdminSettingGroup.getTotalSiteInSubGroupById", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	public int getExitsCount(AdminSettingGroupEntity obj) {
		try {
			return (int)queryForObject("AdminSettingGroup.getExitsCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	/** @description delete group
	 * @author long.pham
	 * @since 2022-12-16
	 * @param id
	 */
	public boolean delete(AdminSettingGroupEntity obj) {
		try {
			return delete("AdminSettingGroup.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("AdminSettingGroup.delete", ex);
			return false;
		}
	}
	
	/** @description delete sub-group
	 * @author long.pham
	 * @since 2022-12-16
	 * @param id
	 */
	public boolean deleteSubGroup(AdminSettingGroupEntity obj) {
		try {
			return delete("AdminSettingGroup.deleteSubGroup", obj) > 0;
		} catch (Exception ex) {
			log.error("AdminSettingGroup.deleteSubGroup", ex);
			return false;
		}
	}
	
	
	/**
	 * @description insert group
	 * @author long.pham
	 * @since 2022-12-16
	 */
	public AdminSettingGroupEntity insertGroup(AdminSettingGroupEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {

			session.insert("AdminSettingGroup.insertGroup", obj);
			int insertLastId = obj.getId();

			if (insertLastId > 0) {
				if(obj.getSites().size() > 0) {
					session.insert("AdminSettingGroup.insertSiteGroupMap", obj);
				}
				
			} else {
				return null;
			}

			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("Site.insertSite", ex);
			return null;
		} finally {
			session.close();
		}
		
	}
	
	/**
	 * @description insert sub-group
	 * @author Hung.Bui
	 * @since 2023-07-21
	 */
	public AdminSettingGroupEntity insertSubGroup(AdminSettingGroupEntity obj) 
	{
		try
		{
			Object insertId = insert("AdminSettingGroup.insertSubGroup", obj);
			if(insertId != null && insertId instanceof Integer) {
				return obj;
			}else {
				return null;
			}
		}
		catch(Exception ex)
		{
			log.error("insertSubGroup", ex);
			return null;
		}	
	}
	
	/**
	 * @description update group
	 * @author long.pham
	 * @since 2022-12-16
	 * @param id
	 */
	public boolean updateGroup(AdminSettingGroupEntity obj){
		SqlSession session = this.beginTransaction();
		try {
			int insertLastId = obj.getId();
			
			session.delete("AdminSettingGroup.deleteSiteGroupMap", obj);
			session.update("AdminSettingGroup.updateGroup", obj);
			
			if (insertLastId > 0 && obj.getSites().size() > 0) {
				session.insert("AdminSettingGroup.insertSiteGroupMap", obj);
			}

			

			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("AdminSettingGroup.updateGroup", ex);
			return false;
		} finally {
			session.close();
		}
		
	}
	
	/**
	 * @description update sub-group
	 * @author Hung.Bui
	 * @since 2023-07-21
	 * @param id
	 */
	public boolean updateSubGroup(AdminSettingGroupEntity obj){
		try{
			return update("AdminSettingGroup.updateSubGroup", obj)>0;
		}catch (Exception ex) {
			log.error("AdminSettingGroup.updateSubGroup", ex);
			return false;
		}
	}
	
	/**
	 * @description update group status
	 * @author long.pham
	 * @since 2022-12-16
	 * @param id
	 */
	public boolean updateStatus(AdminSettingGroupEntity obj){
		try{
			return update("AdminSettingGroup.updateStatus", obj)>0;
		}catch (Exception ex) {
			log.error("AdminSettingGroup.updateStatus", ex);
			return false;
		}
	}
	

}
