/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AdminSettingRoleEntity;
import com.nwm.api.entities.RoleScreenMapEntity;

public class AdminSettingRoleService extends DB {

	/**
	 * @description get list role
	 * @author long.pham
	 * @since 2020-12-30
	 */
	
	public List getList(AdminSettingRoleEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AdminSettingRole.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get total record role
	 * @author long.pham
	 * @since 2020-12-30
	 */
	public int getTotalRecord(AdminSettingRoleEntity obj) {
		try {
			return (int)queryForObject("AdminSettingRole.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * @description insert role
	 * @author long.pham
	 * @since 2025-0717
	 * @param id
	 */
	public AdminSettingRoleEntity insertRole(AdminSettingRoleEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {
			session.insert("AdminSettingRole.insertRole", obj);
			int insertLastId = obj.getId();

			if (insertLastId > 0) {
				// Insert permissions by is_admin_role
				List dataList = queryForList("AdminSettingRole.getListScreenByIsAdminRole", obj);
				if(dataList.size() <= 0) { return null; }
				obj.setScreens(dataList);
				session.insert("AdminSettingRole.insertScreenRoleMap", obj);
				
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
	 * @description get list screen permission by role
	 * @author long.pham
	 * @since 2020-12-31
	 */
	
	public int updateRolePermissions(RoleScreenMapEntity dataE) {
		SqlSession session = this.beginTransaction();
		try {
			
			session.update("AdminSettingRole.updateRoleScreenMap", dataE);
			session.commit();
			return 0;
		}catch (Exception e) {
			log.error(e);
			session.rollback();
			return 1;
		} finally {
			session.close();
		}
	}
	
	/**
	 * @description delete role
	 * @author long.pham
	 * @since 2020-12-30
	 * @param id
	 */
	public boolean deleteRole(AdminSettingRoleEntity obj){
		try{
			return update("AdminSettingRole.deleteRole", obj)>0;
		}catch (Exception ex) {
			log.error("AdminSettingRole.deleteRole", ex);
			return false;
		}
	}
	
	
	/**
	 * @description update role
	 * @author long.pham
	 * @since 2020-12-30
	 * @param id
	 */
	public boolean updateRole(AdminSettingRoleEntity obj){
		try{
			return update("AdminSettingRole.updateRole", obj)>0;
		}catch (Exception ex) {
			log.error("AdminSettingRole.updateRole", ex);
			return false;
		}
	}
	
	
	

	/**
	 * @description get all role
	 * @author long.pham
	 * @since 2021-01-06
	 */
	
	public List getAllRole(AdminSettingRoleEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AdminSettingRole.getAllRole", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
}
